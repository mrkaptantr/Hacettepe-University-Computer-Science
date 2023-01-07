package com.mycompany.messageboxsystem;

import java.util.*;
import java.io.*;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

public class FileOperations {
    
    // Variables
    public static boolean isInitialized = false;
    private static ArrayList<User> users;
    private static ArrayList<Message> messages;
    private static ArrayList<String> original_user_data = new ArrayList<>();
    private static ArrayList<String> original_message_data = new ArrayList<>();
    private static ArrayList<String> enc_user_data = new ArrayList<>();
    private static ArrayList<String> enc_message_data = new ArrayList<>();

    // Getter and setter methods
    public static ArrayList<User> getUsers(){
        return users;
    }
    
    public static void setUsers(ArrayList<User> newUsers){
        users = newUsers;
    }
    
    public static ArrayList<Message> getMessages(){
        return messages;
    }
    
    public static void setMessages(ArrayList<Message> newMessages){
        messages = newMessages;
    }
    
    // System initializator
    public static void initializeSystem() {
        
        if(isInitialized == false) {
            
            users = new ArrayList<>();
            messages = new ArrayList<>();
            original_user_data = new ArrayList<>();
            original_message_data = new ArrayList<>();
            enc_user_data = new ArrayList<>();
            enc_message_data = new ArrayList<>();
            
            // Mode-1: System Initializator For First Time
            // Reading data files (for initalizing system, raw (not encoded) datas will be read first)
            // This place should be commented after first execution
            
            // 1.1 - Taking raw (not encoded) user and message data
            /*readLineByLine("raw_users.data");
            ArrayList<String> out = new ArrayList<>();
            // System.out.println("Original Input-1: " + original_user_data);
            String original = "";
            for(int i=0; i<original_user_data.size(); i++) {
                original += original_user_data.get(i);
            }
            
            readLineByLine("raw_messages.data");
            ArrayList<String> out2 = new ArrayList<>();
            System.out.println("Original Input-2: " + original_message_data);
            String original2 = "";
            for(int i=0; i<original_message_data.size(); i++) {
                original2 += original_message_data.get(i);
            }
            
            // 1.2 - Padding for encryption
            if (original.length() % 8 != 0) {
                while(original.length() % 8 != 0) {
                    original += " ";
                }
            }
            if (original2.length() % 8 != 0) {
                while(original2.length() % 8 != 0) {
                    original2 += " ";
                }
            }
            
            // 1.3 - Encrypting data sets
            try {
                out.add(Arrays.toString( SecurityFunctions.encryptMessage(original.getBytes("UTF-8") )));
            } catch (Exception e) { }
            // System.out.println("If Decrypt Encrypted Stuff Without Writing It Into File-1: " + new String( SecurityFunctions.decryptMessage( SecurityFunctions.encryptMessage(original.getBytes())) , StandardCharsets.UTF_8 ).strip());                     
            
            try {
                out2.add(Arrays.toString( SecurityFunctions.encryptMessage(original2.getBytes("UTF-8") )));
            } catch (Exception e) { }
            // System.out.println("If Decrypt Encrypted Stuff Without Writing It Into File-2: " + new String( SecurityFunctions.decryptMessage( SecurityFunctions.encryptMessage(original2.getBytes())) , StandardCharsets.UTF_8 ).strip());                     

            // System.out.println("Byte Array After Encryption-1: " + Arrays.toString(SecurityFunctions.encryptMessage(original.getBytes())));
            // System.out.println("Byte Array After Encryption-2: " + Arrays.toString(SecurityFunctions.encryptMessage(original2.getBytes())));
            
            // 1.4 - Storing encrypted data
            for(int i=0; i<out.size(); i++) {
                writeOutput("users.data", out);
            }
            for(int i=0; i<out2.size(); i++) {
                writeOutput("messages.data", out2);
            }
            */
            // Mode-2: System Initializator For Next Times (With Encoded Files)
            // This place should be commented at first execution
            
            // 2.1 - Reading datas
            readLineByLine("users.data");
            readLineByLine("messages.data");
            
            // 2.2 - Preparations of decryption
            String part2 = "";
            try {
                for(int i=0; i<enc_user_data.size()-1; i++) {
                    part2 += enc_user_data.get(i) + "\n";
                }
                part2 += enc_user_data.get(enc_user_data.size()-1);
            } catch(Exception e) {}
            
            String part2_2 = "";
            try {
                for(int i=0; i<enc_message_data.size()-1; i++) {
                    part2_2 += enc_message_data.get(i) + "\n";
                }
                part2_2 += enc_message_data.get(enc_message_data.size()-1);
            } catch(Exception e) {}
            //System.out.println("If We Read Encrypted File-1: " + part2);
            //System.out.println("If We Read Encrypted File-2: " + part2_2);
            
            // 2.3 - Decrypting datasets
            String user_data_string = "";
            if(part2.length() > 0) {
                byte[] enc_byte = new byte[part2.substring(1,part2.length()-1).split(",").length];

                for(int i=0; i<part2.substring(1,part2.length()-1).split(",").length; i++) {
                    enc_byte[i] = (byte) Integer.parseInt(part2.substring(1,part2.length()-1).split(",")[i].trim());
                }
                
                user_data_string = new String(SecurityFunctions.decryptMessage(enc_byte), StandardCharsets.UTF_8).strip();
            }
            
            String message_data_string = "";
            if(part2_2.length() > 0) {
                byte[] enc_byte2 = new byte[part2_2.substring(1,part2_2.length()-1).split(",").length];
                
                for(int i=0; i<part2_2.substring(1,part2_2.length()-1).split(",").length; i++) {
                    enc_byte2[i] = (byte) Integer.parseInt(part2_2.substring(1,part2_2.length()-1).split(",")[i].trim());
                }
                
                message_data_string = new String(SecurityFunctions.decryptMessage(enc_byte2), StandardCharsets.UTF_8).strip();
            }
            
            //System.out.println("After Decrpytion-1 (UTF): " + user_data_string);
            //System.out.println("After Decryption-2 (UTF): " + message_data_string);

            // Finally, updating database in the program
            for(int i=0; i<user_data_string.split(" ").length-1; i++){
                //System.out.println("User Number: " + i/2 + "- " + user_data_string.split(" ")[i] + " " + user_data_string.split(" ")[i+1]);
                users.add(new User(user_data_string.split(" ")[i],user_data_string.split(" ")[i+1]));
                i++;
            }
            for(int i=0; i<(message_data_string.split("_").length)/5; i++){
                //System.out.println("Message Number: " + i + "- " + message_data_string.split("_")[i*5] + " " + message_data_string.split("_")[(i*5)+1] + " " + message_data_string.split("_")[(i*5)+2] + " " + message_data_string.split("_")[(i*5)+3] + " " + message_data_string.split("_")[(i*5)+4]);
                messages.add(new Message(message_data_string.split("_")[i*5], message_data_string.split("_")[(i*5)+1], message_data_string.split("_")[(i*5)+2], message_data_string.split("_")[(i*5)+3], message_data_string.split("_")[(i*5)+4]));
            }
            isInitialized = true;
        } 
    }
    
    // Database updater for users
    public static void updateUserDatabase() {
        
        // Storing each of users
        String curr = "";
        for(int i=0; i<users.size(); i++) {
            curr += (users.get(i).getUserName() + " " + users.get(i).getPassword() + " ");
        }
        //curr += "";
                
        // Appyling padding for encryption
        if(curr.length() % 8 != 0) {
            while(curr.length() % 8 != 0) {
                curr += " ";
            }
        }

        // Converting string to byte array in order to encrypt it
        byte[] byte_arr = new byte[curr.getBytes().length];
        for(int i=0; i<curr.getBytes().length; i++) {
            byte_arr[i] = curr.getBytes()[i];
        }

        // Encrypting byte array and writing output
        ArrayList<String> output_file = new ArrayList<>();
        try {
            output_file.add(Arrays.toString( SecurityFunctions.encryptMessage(byte_arr)));
        } catch (Exception e) { }

        writeOutput("users.data", output_file);
        
        output_file = new ArrayList<>();
        curr = curr.strip();
        output_file.add(curr.substring(1,curr.length()-1));
        writeOutput("raw_users.data", output_file);
        
        // Making last system configurations
        users = new ArrayList<>();
        isInitialized = false;
        initializeSystem();
    }
    
    // Database updater for messages
    public static void updateMessageDatabase() {
        // Storing each of messages
        String curr = "";
        for(int i=0; i<messages.size(); i++) {
            curr += (messages.get(i).getMessageId() + "_" + messages.get(i).getContent() + "_" + messages.get(i).getSender() + "_" + messages.get(i).getReceiver() + "_" + messages.get(i).getPassword() + "_");
        }
        //curr += "]";
        
        // Appyling padding for encryption
        if(curr.length() % 8 != 0) {
            while(curr.length() % 8 != 0) {
                curr += " ";
            }
        }
        
        // Converting string to byte array in order to encrypt it
        byte[] byte_arr = new byte[curr.getBytes().length];
        for(int i=0; i<curr.getBytes().length; i++) {
            byte_arr[i] = curr.getBytes()[i];
        }
        
        // Encrypting byte array and writing output
        ArrayList<String> output_file = new ArrayList<>();
        try {
            output_file.add(Arrays.toString( SecurityFunctions.encryptMessage(byte_arr)));
        } catch (Exception e) { }

        writeOutput("messages.data", output_file);
        
        output_file = new ArrayList<>();
        curr = curr.strip();
        output_file.add(curr.substring(1,curr.length()-1));
        writeOutput("raw_messages.data", output_file);

        // Making last system configurations
        messages = new ArrayList<>();
        isInitialized = false;
        initializeSystem();
    }
     
    public static void readLineByLine(String fileName){
        // This method reads files, line by line with Scanner object.
	File file = new File(fileName);
        try {
            Scanner scan;
            if(fileName.compareTo("raw_users.data") == 0) {
                scan = new Scanner(file, "UTF-8");
            } else {
                scan = new Scanner(file, "UTF-8");
            }
            while(scan.hasNextLine()) {
                // Program continues until file ends.
                String line = scan.nextLine();
                if(fileName.compareTo("raw_users.data") == 0) {
                    original_user_data.add(line);
                } else if (fileName.compareTo("raw_messages.data") == 0) {
                    original_message_data.add(line);
                } else if (fileName.compareTo("users.data") == 0) {
                    enc_user_data.add(line);
                } else {
                    enc_message_data.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
	}
    }
    
    // .data File Writer Method
    public static void writeOutput(String fileName, List<String> lines){
        BufferedWriter bw = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            for(int i=0; i<lines.size(); i++) {	
                bw.write(lines.get(i));
            }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally { 
        try{
            if(bw!=null)
                bw.close();
            } catch(Exception ex){
                System.out.println("Error in closing the Buffered Writer: " + ex);
            }
	}
    } 
}
