using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Emgu.CV;
using Emgu.CV.Structure;
using Emgu.CV.ML;

using Accord;
using Accord.MachineLearning;
using Microsoft.ML;

namespace pi
{
    class MachineLearningOperations
    {
        public static double getMaxOfList(ArrayList list)
        {
            int max = int.MinValue;
            foreach (var type in list) { if (Int32.Parse(type.ToString()) > max) { max = Int32.Parse(type.ToString()); } }
            return Convert.ToDouble(max);
        }

        public static double getMinOfList(ArrayList list)
        {
            int min = int.MaxValue;
            foreach (var type in list) { if (Int32.Parse(type.ToString()) < min) { min = Int32.Parse(type.ToString()); } }
            return Convert.ToDouble(min);
        }

        public static ArrayList normalize_image_features(ArrayList image_features, string num_of_features)
        {
            // This function normalizes (in other words scales) features between 0 and 1
            // With this way, each feature will have similar impacts on the result in the ML operations

            // Step-1: Determining the feature count
            ArrayList normalized_image_features = new ArrayList();
            int feature_count = Int32.Parse(num_of_features);

            // Step-2: Creating a nested feature data structure
            List<ArrayList> features_as_grouped = new List<ArrayList>();
            ArrayList labels_as_ordered = new ArrayList();
            for (int i = 0; i < feature_count; i++) { features_as_grouped.Add(new ArrayList { }); }

            // Step-3: Storing image features as grouped
            for (int i = 0; i < image_features.Count; i++)
            { // each image
                String features_as_string = image_features[i].ToString();
                for (int j = 0; j < feature_count; j++)
                { // each feature
                    int splitIndex = features_as_string.IndexOf(',');
                    features_as_grouped[j].Add(features_as_string.Substring(0, splitIndex));
                    features_as_string = features_as_string.Substring(splitIndex + 1);
                }
                labels_as_ordered.Add(features_as_string);
            }

            // Step-4: Min-max scaling for image features (will be scaled between 0 and 1)
            for (int i = 0; i < features_as_grouped.Count; i++)
            {
                double max_value = getMaxOfList(features_as_grouped[i]);
                double min_value = getMinOfList(features_as_grouped[i]);

                for (int j = 0; j < features_as_grouped[i].Count; j++)
                {
                    if ((max_value - min_value) == 0) { features_as_grouped[i][j] = 0; }
                    else { features_as_grouped[i][j] = ((Int32.Parse(features_as_grouped[i][j].ToString()) - min_value) / (max_value - min_value)); }
                }
            }

            // Step-5: Returning normalized image features
            for (int i = 0; i < features_as_grouped[0].Count; i++)
            { // Each image
                String curr_image_features = "";
                for (int j = 0; j < features_as_grouped.Count; j++)
                { // Each feature
                    curr_image_features += features_as_grouped[j][i].ToString() + "-";
                }
                curr_image_features += labels_as_ordered[i].ToString();
                normalized_image_features.Add(curr_image_features);
            }
            return normalized_image_features;
        }

        public static Tuple<List<float[]>, List<int>> x_and_y_split(String dataFilePath, ArrayList allCompanyNames, bool willNormalize)
        {
            // This function normalizes (scales) and splits dataset into different parts of features (x) and labels (y)

            StreamReader reader = new StreamReader(dataFilePath);
            ArrayList image_features = new ArrayList();

            string line = line = reader.ReadLine(); // We added this since need to skip line of headers
            if (!File.Exists(dataFilePath)) { throw new Exception("File not found!"); }
            while ((line = reader.ReadLine()) != null)
            {
                image_features.Add(line);
            }

            int feature_count_except_label = 0;
            if (dataFilePath.Contains("FCTH")) { feature_count_except_label = 192; }
            else if (dataFilePath.Contains("CEDD")) { feature_count_except_label = 144; }
            else if (dataFilePath.Contains("SCD")) { feature_count_except_label = 256; }
            else if (dataFilePath.Contains("SIFT")) { feature_count_except_label = 128; }
            else if (dataFilePath.Contains("HOG")) { feature_count_except_label = 3760; }

            char seperator = ',';
            if (willNormalize)
            {
                image_features = MachineLearningOperations.normalize_image_features(image_features, feature_count_except_label.ToString());
                seperator = '-';
            }

            List<float[]> featureList = new List<float[]>();
            List<int> labelList = new List<int>();
            for (int i = 0; i < image_features.Count; i++)
            {
                int splitIndex = image_features[i].ToString().LastIndexOf(seperator);
                string currentData = image_features[i].ToString().Substring(0, splitIndex);
                string currentLabel = image_features[i].ToString().Substring(splitIndex + 1);
                float[] data = currentData.Split(seperator).Select(x => float.Parse(x)).ToArray();
                featureList.Add(data);
                for (int j = 0; j < allCompanyNames.Count; j++)
                { if (String.Equals(currentLabel, allCompanyNames[j])) { labelList.Add(j); } }
            }

            return Tuple.Create(featureList, labelList);
        }

        public static T[,] To2D<T>(T[][] source)
        {
            // This function converts a array into a great format for matrix creation
            try
            {
                int FirstDim = source.Length;
                int SecondDim = source.GroupBy(row => row.Length).Single().Key; // throws InvalidOperationException
                var result = new T[FirstDim, SecondDim];
                for (int i = 0; i < FirstDim; i++)
                    for (int j = 0; j < SecondDim; j++)
                        result[i, j] = source[i][j];
                return result;
            }
            catch (InvalidOperationException) { throw new InvalidOperationException("The given jagged array is not rectangular."); }
        }

        public static int IntLength(int i)
        {
            // This function returns length of integer.
            if (i < 0) { throw new ArgumentOutOfRangeException(); }
            if (i == 0) { return 1; }
            return (int)Math.Floor(Math.Log10(i)) + 1;
        }

        public static void evaluate_models(string algorithmName, List<int> actualValues, List<float> predictedValues)
        {
            int class_number = actualValues.Distinct().ToList().Count; // 14

            // Step-1: Creating a (class_number) x (class_number) confusion matrix
            int[][] confusion_matrix = new int[class_number][];
            for (int i = 0; i < class_number; i++)
            {
                int[] sub_list = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
                confusion_matrix[i] = sub_list;
            }

            // Step-2: Filling the confusion matrix
            for (int i = 0; i < actualValues.Count; i++)
            {
                if (actualValues[i] == predictedValues[i]) { confusion_matrix[(actualValues[i])][(actualValues[i])] += 1; }
                else { confusion_matrix[(actualValues[i])][(int)Math.Round(predictedValues[i], 0)] += 1; }
            }

            // Step-3: Printing the confusion matrix (for debugging)
            /*Console.WriteLine("");
            Console.WriteLine("Confusion Matrix:");
            foreach (var item in confusion_matrix) {
                foreach (int subitem in item) { Console.Write(subitem.ToString() + ("          ").Substring(0, 4 - IntLength(subitem))); }
                Console.WriteLine("");
            }
            Console.WriteLine("");*/

            // Step-4: Calculating the correctly predicted values (TP)
            double true_positive = 0.00;
            for (int i = 0; i < class_number; i++)
            {
                if (confusion_matrix[i][i] > 0)
                {
                    true_positive += confusion_matrix[i][i];
                }
            }

            // Step-5: Calculating true positive rate (TPR) [Recall]
            // TPR = Recall = True Positives / Positives
            double TPR = 0.00;
            for (int i = 0; i < class_number; i++)
            {
                double total_positive = 0.0;
                for (int j = 0; j < class_number; j++) { total_positive += confusion_matrix[j][i]; }
                TPR += (confusion_matrix[i][i] / total_positive);
            }
            TPR /= class_number; // Calculating the average value (not sum)

            // Step-6: Calculating the precision (required for F1 score)
            // Precision = True Positives / (True Positives + False Negatives)
            double precision = 0.00;
            for (int i = 0; i < class_number; i++)
            {
                double temp = 0.0;
                for (int j = 0; j < class_number; j++) { temp += confusion_matrix[i][j]; }
                precision += (confusion_matrix[i][i] / temp);
            }
            precision /= class_number; // Calculating the average value (not sum)

            // Step-7: Calculating the F1 score
            // F1 Score = 2 x [(Precision x Recall) / (Precision + Recall)]
            // Note that TPR is actually recall
            double F1_Score = (2 * precision * TPR) / (precision + TPR);

            // Step-8: Calculating false positive rate (FPR) 
            // FPR = False Positives / (False Positives + True Negatives)
            // What we do in step-8 is -> https://i.stack.imgur.com/AuTKP.png
            double FPR = 0.0;
            for (int i = 0; i < class_number; i++)
            {
                double current_true_positive = 0.0;
                double current_false_positive = 0.0;
                double current_true_negative = 0.0;
                double current_false_negative = 0.0;
                for (int j = 0; j < class_number; j++)
                {
                    if (i == j) { current_true_positive += confusion_matrix[i][j]; }
                    else
                    {
                        current_false_positive += confusion_matrix[j][i];
                        current_false_negative += confusion_matrix[i][j];
                    }
                    current_true_negative = actualValues.Count - current_true_positive - current_false_positive - current_false_negative;
                    FPR += (current_false_positive / (current_false_positive + current_true_negative));
                }
            }
            FPR /= class_number; // Calculating the average value (not sum)

            // Step-9: Printing the results
            Console.WriteLine(algorithmName + " | Accuracy: " + Math.Round((true_positive / actualValues.Count), 3) + " | TPR: " + Math.Round(TPR, 3) + " | FPR: " + Math.Round(FPR, 3) + " | F1-Score: " + Math.Round(F1_Score, 3));

        }

        public static void train_and_test_models(string rootFolder, ArrayList allFileTypes, ArrayList supportedPrecomputingModes, List<String> supportedMachineLearningModes, ArrayList allCompanyNames, bool willNormalizeDataSet)
        {

            for (int j = 0; j < supportedPrecomputingModes.Count; j++)
            {

                // Step-1: Creating input files and required variables
                string root_folder_location = System.IO.Path.Combine(rootFolder, "pre-computed");
                string train_file_name = "precomputed_" + supportedPrecomputingModes[j] + "_train.csv";
                string val_file_name = "precomputed_" + supportedPrecomputingModes[j] + "_val.csv";
                Console.WriteLine("Training with " + train_file_name + " with " + File.ReadLines(root_folder_location + "\\" + train_file_name).Count() + " samples.");
                DateTime start_time = DateTime.Now;

                // Step-2: Creating required constants and variables
                string trainDataPath = root_folder_location + "\\" + train_file_name;
                string testDataPath = root_folder_location + "\\" + val_file_name;
                List<List<int>> actual_Values = new List<List<int>>();
                List<List<float>> predicted_Values = new List<List<float>>();
                Accord.MachineLearning.DecisionTrees.RandomForestLearning rfl;
                SVM svm;
                KNearest knn;

                // Step-3: X (feature) and Y (label) split for both of train and test sets
                Tuple<List<float[]>, List<int>> trainData = x_and_y_split(trainDataPath, allCompanyNames, willNormalizeDataSet);
                Matrix<float> TrainFeatures = new Matrix<float>(To2D<float>(trainData.Item1.ToArray()));
                Matrix<int> TrainLabels = new Matrix<int>(trainData.Item2.ToArray());
                Tuple<List<float[]>, List<int>> testData = x_and_y_split(testDataPath, allCompanyNames, willNormalizeDataSet);
                Matrix<float> TestFeatures = new Matrix<float>(To2D<float>(testData.Item1.ToArray()));
                Matrix<int> TestLabels = new Matrix<int>(testData.Item2.ToArray());

                // Step-4: Training models with using all of supported algorithms
                for (int i = 0; i < supportedMachineLearningModes.Count; i++)
                {

                    List<int> actualValues = new List<int>();
                    List<float> predictedValues = new List<float>();

                    // Mode-1: Random forest algorithm
                    if (String.Equals(supportedMachineLearningModes[i], "Random Forest"))
                    {
                        try
                        {
                            // Step-4.1: Creating random forest model and initializing it's parameters
                            rfl = new Accord.MachineLearning.DecisionTrees.RandomForestLearning();
                            double[][] TrainFeaturesAsArray = new double[trainData.Item1.Count][];
                            for (int h = 0; h < trainData.Item1.Count; h++)
                            {
                                double[] temp = new double[trainData.Item1[h].Length];
                                for (int g = 0; g < trainData.Item1[h].Length; g++)
                                {
                                    temp[g] = trainData.Item1[h][g];
                                }
                                TrainFeaturesAsArray[h] = temp;
                            }
                            int[] TrainLabelsAsArray = trainData.Item2.ToArray();
                            int[] TestLabelsAsArray = testData.Item2.ToArray();

                            rfl.NumberOfTrees = 10;
                            rfl.SampleRatio = 1; // Since we have different datasets for both of train and validation we can use all trainset for training
                            rfl.ParallelOptions = new ParallelOptions(); // Required, otherwise program sucks if number of trees are greater

                            // This is required in order to handle 'Attribute is constant' error
                            // If an attribute has the same value in all images, it gives this error because of the min and max values
                            for (int g = 0; g < TrainFeaturesAsArray[0].Length; g++)
                            {
                                TrainFeaturesAsArray[0][g] = 1; // Change first sample's each of attributes to 1 (maybe not legal but simple :D)
                            }

                            // Step-4.2: Training the random forest model
                            var forest = rfl.Learn(TrainFeaturesAsArray, TrainLabelsAsArray);

                            // Step-4.3: Testing the random forest model
                            if (testData == null) { return; }
                            if (forest == null) { return; }
                            try
                            {
                                double[][] TestFeaturesAsArray = new double[testData.Item1.Count][];
                                for (int h = 0; h < testData.Item1.Count; h++)
                                {
                                    double[] temp = new double[testData.Item1[h].Length];
                                    for (int g = 0; g < testData.Item1[h].Length; g++) { temp[g] = testData.Item1[h][g]; }
                                    TestFeaturesAsArray[h] = temp;
                                }

                                int[] predicted = forest.Decide(TestFeaturesAsArray);

                                for (int k = 0; k < TestLabelsAsArray.Length; k++) { actualValues.Add(TestLabelsAsArray[k]); }
                                for (int k = 0; k < predicted.Length; k++) { predictedValues.Add(predicted[k]); }

                                actual_Values.Add(actualValues);
                                predicted_Values.Add(predictedValues);
                            }
                            catch (Exception ex) { Console.WriteLine(ex.Message); }
                        }
                        catch (Exception ex) { Console.WriteLine(ex.Message); }
                    }

                    // Mode-2: Support vector machine algorithm (SVM)
                    else if (String.Equals(supportedMachineLearningModes[i], "SVM          "))
                    {
                        try
                        {
                            // Step-4.1: Creating SVM model and initializing it's parameters
                            svm = new SVM();
                            svm.C = 100;
                            svm.Type = SVM.SvmType.CSvc; // Classification
                            svm.Gamma = 0.005;
                            svm.SetKernel(SVM.SvmKernelType.Linear); // Linear SVM
                            svm.TermCriteria = new MCvTermCriteria(1000, 1e-6);

                            // Step-4.2: Training and saving the SVM model
                            svm.Train(TrainFeatures, Emgu.CV.ML.MlEnum.DataLayoutType.RowSample, TrainLabels);
                            svm.Save("SVM_With_" + supportedPrecomputingModes[j] + ".txt");

                            // Step-4.3: Testing the SVM model
                            if (testData == null) { return; }
                            if (svm == null) { return; }
                            try
                            {
                                for (int k = 0; k < TestFeatures.Rows; k++)
                                {
                                    Matrix<float> row = TestFeatures.GetRow(k);
                                    float predict = svm.Predict(row);
                                    actualValues.Add(TestLabels[k, 0]);
                                    predictedValues.Add(predict);
                                }
                                actual_Values.Add(actualValues);
                                predicted_Values.Add(predictedValues);
                            }
                            catch (Exception ex) { Console.WriteLine(ex.Message); }
                        }
                        catch (Exception ex) { Console.WriteLine(ex.Message); }
                    }

                    // Mode-3: K-nearest neighour algorithm (KNN)
                    else if (String.Equals(supportedMachineLearningModes[i], "KNN          "))
                    {
                        try
                        {
                            // Step-4.1: Creating KNN model and initializing it's parameters
                            knn = new KNearest();
                            knn.DefaultK = 5;
                            knn.IsClassifier = true;
                            knn.AlgorithmType = KNearest.Types.BruteForce;

                            // Step-4.2: Training and saving the KNN model
                            knn.Train(TrainFeatures, Emgu.CV.ML.MlEnum.DataLayoutType.RowSample, TrainLabels);
                            knn.Save("KNN_With_" + supportedPrecomputingModes[j] + ".txt");

                            // Step-4.3: Testing the KNN model
                            if (testData == null) { return; }
                            if (knn == null) { return; }
                            try
                            {
                                for (int k = 0; k < TestFeatures.Rows; k++)
                                {
                                    Matrix<float> row = TestFeatures.GetRow(k);
                                    float predict = knn.Predict(row);
                                    actualValues.Add(TestLabels[k, 0]);
                                    predictedValues.Add(predict);
                                }
                                actual_Values.Add(actualValues);
                                predicted_Values.Add(predictedValues);
                            }
                            catch (Exception ex) { Console.WriteLine(ex.Message); }
                        }
                        catch (Exception ex) { Console.WriteLine(ex.Message); }
                    }
                }
                Console.WriteLine("Done in " + Math.Round(DateTime.Now.Subtract(start_time).TotalSeconds, 3) + " seconds.");

                // Step-5: Evaluating the supported machine learning models
                Console.WriteLine("Testing with " + val_file_name + " with " + File.ReadLines(root_folder_location + "\\" + val_file_name).Count() + " samples.");
                for (int q = 0; q < supportedMachineLearningModes.Count; q++) { evaluate_models(supportedMachineLearningModes[q], actual_Values[q], predicted_Values[q]); }
                Console.WriteLine("------------------------------------------------");

            }
        }
    }
}
