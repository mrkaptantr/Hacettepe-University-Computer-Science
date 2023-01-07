using System;
using System.Collections;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Emgu.CV;
using Emgu.CV.Features2D;
using Emgu.CV.Util;
using Emgu.CV.Structure;
using Emgu.CV.CvEnum;

namespace pi
{
    class ImageOperations
    {
        public static void precompute(string path_of_dataset, ArrayList allOfFileTypes, List<String> requiredPrecomputingModes, bool willPrint, ArrayList allCompanyNames)
        {

            // Step-1: Calculating and printing image and class counts
            int train_count = 0;
            int val_count = 0;

            if (willPrint) { Console.WriteLine("Reading phishIRIS_DL_Dataset..."); }
            for (int i = 0; i < allOfFileTypes.Count; i++)
            {
                for (int j = 0; j < allCompanyNames.Count; j++)
                {
                    DirectoryInfo dir = new DirectoryInfo(path_of_dataset + "\\" + "phishIRIS_DL_Dataset\\" + allOfFileTypes[i] + "\\" + allCompanyNames[j]);                   
                    FileInfo[] imageFiles = dir.GetFiles();
                    if (String.Equals(allOfFileTypes[i], "train")) { train_count += imageFiles.Length; }
                    if (String.Equals(allOfFileTypes[i], "val")) { val_count += imageFiles.Length; }
                }
            }
            if (willPrint) { Console.WriteLine(train_count + " images were found in train folder."); }
            if (willPrint) { Console.WriteLine(val_count + " images were found in val folder."); }
            if (willPrint) { Console.WriteLine(allCompanyNames.Count - 1 + " classes exist."); }

            // Step-2: Making precomputation operations
            for (int i = 0; i < requiredPrecomputingModes.Count; i++)
            {
                string[] data_of_required_operation = requiredPrecomputingModes[i].Split((char)32);

                if (willPrint) { Console.WriteLine(data_of_required_operation[1] + " features are being extracted for " + data_of_required_operation[0] + "..."); }
                ArrayList image_features = new ArrayList { };
                DateTime start_time = DateTime.Now;

                // Mode-1: Fuzzy Color and Texture Histogram (FCTH) [Global]
                if (String.Equals(data_of_required_operation[1], "FCTH"))
                {
                    for (int j = 0; j < allCompanyNames.Count; j++)
                    {

                        DirectoryInfo dir = new DirectoryInfo(path_of_dataset + "\\" + "phishIRIS_DL_Dataset\\" + data_of_required_operation[0] + "\\" + allCompanyNames[j]);
                        FileInfo[] imageFiles = dir.GetFiles();

                        foreach (FileInfo f in imageFiles)
                        {
                            double[] FCTHTable = new double[192];
                            Bitmap ImageData = new Bitmap(path_of_dataset + "\\" + "phishIRIS_DL_Dataset\\" + data_of_required_operation[0] + "\\" + allCompanyNames[j] + "\\" + f.Name);
                            FCTH_Descriptor.FCTH GetFCTH = new FCTH_Descriptor.FCTH();
                            FCTHTable = GetFCTH.Apply(ImageData, 2);
                            String image_fcth_values = "";
                            for (int k = 0; k < FCTHTable.Length; k++) { image_fcth_values += (FCTHTable[k] + ","); }
                            image_fcth_values += allCompanyNames[j];
                            image_features.Add(image_fcth_values);
                        }
                    }
                }

                // Mode-2: Color and Edge Directivity Descriptor (CEDD) [Global]
                else if (String.Equals(data_of_required_operation[1], "CEDD"))
                {
                    for (int j = 0; j < allCompanyNames.Count; j++)
                    {

                        DirectoryInfo dir = new DirectoryInfo(path_of_dataset + "\\" + "phishIRIS_DL_Dataset\\" + data_of_required_operation[0] + "\\" + allCompanyNames[j]);
                        FileInfo[] imageFiles = dir.GetFiles();

                        foreach (FileInfo f in imageFiles)
                        {
                            double[] CEDDTable = new double[144];
                            Bitmap ImageData = new Bitmap(path_of_dataset + "\\" + "phishIRIS_DL_Dataset\\" + data_of_required_operation[0] + "\\" + allCompanyNames[j] + "\\" + f.Name);
                            CEDD_Descriptor.CEDD GetCEDD = new CEDD_Descriptor.CEDD();
                            CEDDTable = GetCEDD.Apply(ImageData);
                            String image_cedd_values = "";
                            for (int k = 0; k < CEDDTable.Length; k++) { image_cedd_values += (CEDDTable[k] + ","); }
                            image_cedd_values += allCompanyNames[j];
                            image_features.Add(image_cedd_values);
                        }
                    }
                }

                // Mode-3: Scalable Color Descriptor (SCD) [Global]
                else if (String.Equals(data_of_required_operation[1], "SCD"))
                {
                    for (int j = 0; j < allCompanyNames.Count; j++)
                    {

                        DirectoryInfo dir = new DirectoryInfo(path_of_dataset + "\\" + "phishIRIS_DL_Dataset\\" + data_of_required_operation[0] + "\\" + allCompanyNames[j]);
                        FileInfo[] imageFiles = dir.GetFiles();

                        foreach (FileInfo f in imageFiles)
                        {
                            Bitmap ImageData = new Bitmap(path_of_dataset + "\\" + "phishIRIS_DL_Dataset\\" + data_of_required_operation[0] + "\\" + allCompanyNames[j] + "\\" + f.Name);
                            SCD_Descriptor SCDDescriptor = new SCD_Descriptor();
                            int number_of_coefficients = 256;
                            int number_of_bitplane = 0;
                            SCDDescriptor.Apply(ImageData, number_of_coefficients, number_of_bitplane);
                            double[] Quantizied_Descriptor = new double[number_of_coefficients];
                            Quantizied_Descriptor = SCDDescriptor.Norm4BitHistogram;
                            String image_scd_values = "";
                            for (int k = 0; k < Quantizied_Descriptor.Length; k++) { image_scd_values += (Quantizied_Descriptor[k] + ","); }
                            image_scd_values += allCompanyNames[j];
                            image_features.Add(image_scd_values);
                        }
                    }
                }

                // Mode-4: Scale Invariant Feature Transform (SIFT) [Local]
                else if (String.Equals(data_of_required_operation[1], "SIFT"))
                {
                    for (int j = 0; j < allCompanyNames.Count; j++)
                    {
                        DirectoryInfo dir = new DirectoryInfo(path_of_dataset + "\\" + "phishIRIS_DL_Dataset\\" + data_of_required_operation[0] + "\\" + allCompanyNames[j]);
                        FileInfo[] imageFiles = dir.GetFiles();

                        foreach (FileInfo f in imageFiles)
                        {
                            SIFT SIFT_Descriptor = new SIFT(400); // DO NOT CHANGE 400, OTHERWISE PROGRAM WON'T WORK!
                            Bitmap ImageData = new Bitmap(path_of_dataset + "\\" + "phishIRIS_DL_Dataset\\" + data_of_required_operation[0] + "\\" + allCompanyNames[j] + "\\" + f.Name);
                            Image<Bgr, Byte> image = new Image<Bgr, Byte>(path_of_dataset + "\\" + "phishIRIS_DL_Dataset\\" + data_of_required_operation[0] + "\\" + allCompanyNames[j] + "\\" + f.Name);
                            Point[] p = new Point[image.Width * image.Height];
                            int k = 0;
                            for (int l = 0; l < image.Width; l++)
                            {
                                for (int h = 0; h < image.Height; h++)
                                {
                                    Point p1 = new Point(l, h);
                                    p[k++] = p1;
                                }
                            }
                            VectorOfKeyPoint modelKeyPoints = new VectorOfKeyPoint();
                            MKeyPoint[] mKeyPoints = SIFT_Descriptor.Detect(image);
                            modelKeyPoints.Push(mKeyPoints);
                            Mat vector = new Mat();
                            SIFT_Descriptor.Compute(image, modelKeyPoints, vector);

                            int[] avg_vector = new int[vector.Cols];

                            for (int l = 0; l < vector.Cols; l++)
                            { // Cols
                                int sum_val = 0;
                                for (int h = 0; h < vector.Rows; h++)
                                {
                                    sum_val += Int32.Parse(vector.GetData().GetValue(h, l).ToString());
                                }
                                avg_vector[l] = sum_val / vector.Rows;
                            }

                            String image_sift_values = "";
                            for (int v = 0; v < avg_vector.Length; v++) { image_sift_values += (avg_vector[v] + ","); }
                            image_sift_values += allCompanyNames[j];
                            image_features.Add(image_sift_values);

                        }
                    }
                }

                // Mode-5: Histogram Of Oriented Gradients (HOG) [Local]
                // Reference: https://stackoverflow.com/questions/37028423/emgu-train-svm-for-people-detection-c-sharp
                else if (String.Equals(data_of_required_operation[1], "HOG"))
                {
                    for (int j = 0; j < allCompanyNames.Count; j++)
                    {
                        DirectoryInfo dir = new DirectoryInfo(path_of_dataset + "\\" + "phishIRIS_DL_Dataset\\" + data_of_required_operation[0] + "\\" + allCompanyNames[j]);
                        FileInfo[] imageFiles = dir.GetFiles();
                        foreach (FileInfo f in imageFiles)
                        {

                            HOGDescriptor HogDescriptor = new HOGDescriptor();
                            Image<Bgr, Byte> image = new Image<Bgr, Byte>(path_of_dataset + "\\" + "phishIRIS_DL_Dataset\\" + data_of_required_operation[0] + "\\" + allCompanyNames[j] + "\\" + f.Name);
                            Image<Bgr, Byte> imageOfInterest = Resize(image);

                            //Point[] p = new Point[imageOfInterest.Width * imageOfInterest.Height];
                            //int k = 0;
                            //for (int l = 0; l < imageOfInterest.Width; l++) { for (int h = 0; h < imageOfInterest.Height; h++) { Point p1 = new Point(l, h); p[k++] = p1; } }

                            float[] vector = HogDescriptor.Compute(imageOfInterest);//, new Size(8, 8), new Size(0, 0), p);

                            String image_hog_values = "";
                            for (int v = 0; v < vector.Length; v++)
                            {
                                image_hog_values += (Convert.ToInt32(Convert.ToDouble(vector[v].ToString()) * 100)) + ",";
                            }
                            image_hog_values += allCompanyNames[j];
                            image_features.Add(image_hog_values);

                        }
                    }
                }

                // Step-3: Writing precomputed values into .csv files
                write_to_csv_file("precomputed_" + data_of_required_operation[1] + "_" + data_of_required_operation[0] + ".csv", start_time, path_of_dataset, image_features, willPrint, data_of_required_operation[1]);

            }
        }

        public static Image<Bgr, Byte> Resize(Image<Bgr, Byte> im)
        {
            return im.Resize(64, 128, Inter.Linear);
        }

        public static ArrayList get_headers_for_csv_file(string data_type)
        {
            ArrayList headers = new ArrayList();
            string all_headers = "";
            int feature_count_except_label = 0;

            // Step-1: Determining number of features
            if (String.Equals(data_type, "FCTH")) { feature_count_except_label = 192; }
            else if (String.Equals(data_type, "CEDD")) { feature_count_except_label = 144; }
            else if (String.Equals(data_type, "SCD")) { feature_count_except_label = 256; }
            else if (String.Equals(data_type, "SIFT")) { feature_count_except_label = 128; }
            else if (String.Equals(data_type, "HOG")) { feature_count_except_label = 3760; }

            // Step-2: Writing features as a string
            for (int i = 1; i < feature_count_except_label + 1; i++)
            {
                all_headers += "ftr_" + i + ",";
            }

            // Step-3: Adding label (class) value and returning all headers
            all_headers += "label";
            headers.Add(all_headers);
            return headers;
        }

        public static void write_to_csv_file(string file_name, DateTime start_time, String root_folder_location, ArrayList image_features, bool willPrint, String data_type)
        {

            // Step-1: Checking whether 'pre-computed' folder exists
            // If file exists it skips, otherwise it creates 'pre-computed' folder
            string pathString = System.IO.Path.Combine(root_folder_location, "pre-computed");
            System.IO.Directory.CreateDirectory(pathString);

            // Step-2: Checking whether the file exists
            if (!File.Exists(root_folder_location + "\\pre-computed\\" + file_name))
            {

                // Step-3: Creating and writing file if it doesn't exits
                File.AppendAllLines(root_folder_location + "\\pre-computed\\" + file_name, get_headers_for_csv_file(data_type).Cast<string>());
                File.AppendAllLines(root_folder_location + "\\pre-computed\\" + file_name, image_features.Cast<string>());

            }

            // Step-4: Logging required details
            Console.WriteLine("Done " + file_name + " is regenerated in " + Math.Round(DateTime.Now.Subtract(start_time).TotalSeconds, 3) + " seconds.");
        }
    }
}
