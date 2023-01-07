using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace pi
{
    static class Program
    {
        static void Main(String[] args)
        {

            // Step-1: Extracting command line arguments
            //string command_line_arguments = "-dataset C:\\Users\\MERT\\Desktop\\Project\\phishIRIS_DL_Dataset\\ -mode trainval";
            string command_line_arguments = args[0] + " " + args[1] + " " + args[2] + " " + args[3];
            string[] arguments = command_line_arguments.Split((char)32); // 32 = " "

            // Step-2: Defining required constants
            ArrayList allFileTypes = new ArrayList { "train", "val" };
            ArrayList supportedPrecomputingModes = new ArrayList { "FCTH", "CEDD", "SCD", "SIFT", "HOG" };
            List<String> supportedMachineLearningModes = new List<String> { "Random Forest", "SVM          ", "KNN          " };
            ArrayList allCompanyNames = new ArrayList { "adobe", "alibaba", "amazon", "apple", "boa", "chase", "dhl", "dropbox", "facebook", "linkedin", "microsoft", "other", "paypal", "wellsfargo", "yahoo" };
            List<String> requiredPrecomputingModes = new List<String>(); // Modes that their .csv files doesn't exist

            // Step-3: Checking precomputed files in order to boost execution performance
            for (int i = 0; i < allFileTypes.Count; i++)
            {
                for (int j = 0; j < supportedPrecomputingModes.Count; j++)
                {
                    string root_folder_location = System.IO.Path.Combine(arguments[1], "pre-computed");
                    string file_name = "precomputed_" + supportedPrecomputingModes[j] + "_" + allFileTypes[i] + ".csv";
                    if (!File.Exists(root_folder_location + "\\" + file_name))
                    {
                        // Storing information about missing files
                        requiredPrecomputingModes.Add(allFileTypes[i] + " " + supportedPrecomputingModes[j]);
                    }
                }
            }


            Console.WriteLine("Checking whether missing .csv file exist...");
            if (requiredPrecomputingModes.Count > 0)
            {
                Console.WriteLine("Missing .csv files found: ");
                for (int i = 0; i < requiredPrecomputingModes.Count; i++)
                {
                    string[] missing_item_data = requiredPrecomputingModes[i].Split((char)32);
                    Console.WriteLine(i + 1 + "- .csv file for " + missing_item_data[0] + " for " + missing_item_data[1] + " mode is missing.");
                }
                Console.WriteLine("");
                Console.WriteLine("Precomputing missing .csv files...");
                ImageOperations.precompute(arguments[1], allFileTypes, requiredPrecomputingModes, true, allCompanyNames);
            }
            else
            {
                Console.WriteLine("Luckily no missing .csv file found.");
            }

            if (String.Equals(arguments[3], "trainval")) { 
                bool willNormalizeDataSet = false;
                MachineLearningOperations.train_and_test_models(arguments[1], allFileTypes, supportedPrecomputingModes, supportedMachineLearningModes, allCompanyNames, willNormalizeDataSet);
            }
        }
    }
}
