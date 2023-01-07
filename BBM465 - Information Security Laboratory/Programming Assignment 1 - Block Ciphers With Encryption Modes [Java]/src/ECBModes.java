package FileCipherv2;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ECBModes {
		
	public static byte[] encryptMessage(byte[] message, byte[] keyBytes, String algorithm, String key) {		
		try {
			Cipher cipher = Cipher.getInstance(algorithm + "/ECB/NoPadding");
			SecretKey secretKey = new SecretKeySpec(keyBytes, algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipher.doFinal(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decryptMessage(byte[] message, byte[] keyBytes, String algorithm, String key) {
		try {
			Cipher cipher = Cipher.getInstance(algorithm + "/ECB/NoPadding");
			SecretKey secretKey = new SecretKeySpec(keyBytes, algorithm);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			System.out.println(Arrays.toString(message));
			return cipher.doFinal(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<byte[]> encrypt(List<String> plainTextList, String algorithm, String mode, String IV, String key, String nonce) {
				
		// Initializing keys
		
		ArrayList<ArrayList<Byte>> plaintext_byte = new ArrayList<ArrayList<Byte>>();
		ArrayList<ArrayList<Byte>> encrypted_plainText = new ArrayList<ArrayList<Byte>>();

		// Converting input list into arraylists of byte
		for(int i=0; i<plainTextList.size(); i++) {

			ArrayList<Byte> temp = new ArrayList<Byte>();
			int size = plainTextList.get(i).length();
			for(int j=0; j<plainTextList.get(i).getBytes().length; j++) {
				temp.add(plainTextList.get(i).getBytes(StandardCharsets.UTF_8)[j]);
			}
			plaintext_byte.add(temp);	
		}
		
		System.out.println("Original Bytes: " + plaintext_byte);

		// Selecting mode for encrypting plainText
		if(mode.compareTo("CBC") == 0) {
			encrypted_plainText = CBC.CBC_Encryption(plaintext_byte, IV, key);
		}
		else if(mode.compareTo("OFB") == 0) {
			encrypted_plainText = OFB.OFB_Encryption(plaintext_byte, IV, key);
		}
		else if(mode.compareTo("CFB") == 0) {
			encrypted_plainText = CFB.CFB_Encryption(plaintext_byte, IV, key);
		}
		else if(mode.compareTo("CTR") == 0) {
			encrypted_plainText = CTR.CTR_Encryption(plaintext_byte, IV, key, nonce);
		}
		
		// Converting key to byte arraylist
		ArrayList<Byte> key_byte = new ArrayList<Byte>();		
		for(int i=0; i<key.getBytes().length; i++) {
			key_byte.add(key.getBytes()[i]);
		}
		
		// Converting key to 128 bit (16 byte)
		// Method: Adding key again and again to tail with using mod opeartion
		if(key_byte.size() < 24) {
			int index = 0;
			while(key_byte.size() < 24) {	
				key_byte.add(Byte.valueOf(key_byte.get((index % key_byte.size()))));
				index += 1;
			}			
		}
		if(key_byte.size() > 24) {
			while(key_byte.size() > 24) {	
				key_byte.remove(key_byte.size()-1);
			}			
		}
		
		byte[] key_array;
		// Converting key to array
		if (algorithm.compareTo("3DES") == 0) {
			key_array = new byte[24]; 
			for(int j=0; j<key_array.length; j++) {
				key_array[j] = key_byte.get(j);
			}
			algorithm = "DES";
		} else {
			key_array = new byte[8]; 
			for(int j=0; j<key_array.length; j++) {
				key_array[j] = key_byte.get(j);
			}
		}
			
		byte currInput[];
		byte currOutput[];
		ArrayList<byte[]> output = new ArrayList<byte[]>();

		// Executing encryption for each of rows in plaintext_byte
		for(int i=0; i<encrypted_plainText.size(); i++) {
									
			int size = encrypted_plainText.get(i).size();
			// Converting input into array
			if(size % 8 != 0) {
				while(size % 8 != 0) {
					size += 1;
				}
			}

			currInput = new byte[size];

	        for (int j = 0; j < size; j++) {
	        	currInput[j] = (byte) 0;
	        }
	        for (int j = 0; j < encrypted_plainText.get(i).size(); j++) {
	        	currInput[j] = encrypted_plainText.get(i).get(j);
	        }
						
			// Calling encryption module and storing answer	
			currOutput = new byte[currInput.length];
			currOutput = encryptMessage(currInput, key_array, algorithm, key);
						
			// Storing output in order to write it in a txt file
			output.add(currOutput);
		}
						
		return output;
	}

	public static List<byte[]> decrypt(List<String> plainTextList, String algorithm, String mode, String IV, String key, String nonce) {
				
		// Initializing keys
		
		ArrayList<ArrayList<Byte>> plaintext_byte = new ArrayList<ArrayList<Byte>>();
		ArrayList<ArrayList<Byte>> decrypted_plainText = new ArrayList<ArrayList<Byte>>();
		
		Charset charset = StandardCharsets.ISO_8859_1;
				
		// Converting input list into arraylists of byte
		for(int i=0; i<plainTextList.size(); i++) {
			ArrayList<Byte> temp = new ArrayList<Byte>();
			byte[] arr = charset.encode(plainTextList.get(i)).array();
			for(int j=0; j<arr.length; j++) {
				temp.add(arr[j]);
			}
			plaintext_byte.add(temp);
		}
		
		System.out.println("Converted Bytes (From File): " + plaintext_byte);
		
		// Converting key to byte arraylist
		ArrayList<Byte> key_byte = new ArrayList<Byte>();		
		for(int i=0; i<key.getBytes().length; i++) {
			key_byte.add(key.getBytes()[i]);
		}
				
		// Converting key to 128 bit (16 byte)
		// Method: Adding key again and again to tail with using mod opeartion
		if(key_byte.size() < 24) {
			int index = 0;
			while(key_byte.size() < 24) {	
				key_byte.add(Byte.valueOf(key_byte.get((index % key_byte.size()))));
				index += 1;
			}			
		}
		if(key_byte.size() > 24) {
			while(key_byte.size() > 24) {	
				key_byte.remove(key_byte.size()-1);
			}			
		}
				
		byte[] key_array;
		// Converting key to array
		if (algorithm.compareTo("3DES") == 0) {
			key_array = new byte[24]; 
			for(int j=0; j<key_array.length; j++) {
				key_array[j] = key_byte.get(j);
			}
			algorithm = "DESede";
		} else {
			key_array = new byte[8]; 
			for(int j=0; j<key_array.length; j++) {
				key_array[j] = key_byte.get(j);
			}
		}
					
		byte currInput[];
		byte currOutput[];
		ArrayList<byte[]> output = new ArrayList<byte[]>();
		
		// Executing encryption for each of rows in plaintext_byte
		for(int i=0; i<plaintext_byte.size(); i++) {
			
			// Converting input into array
			int size = plaintext_byte.get(i).size();
			if(size % 8 != 0) {
				size += 1;
			}

			currInput = new byte[size];
						
			//currInput = new byte[plaintext_byte.get(i).size()];
			for(int j=0; j<plaintext_byte.get(i).size(); j++) {
				currInput[j] = plaintext_byte.get(i).get(j);
			}	
			for(int j=plaintext_byte.get(i).size(); j<size; j++) {
				currInput[j] = (byte) 0;
			}

			// Calling decryption module and storing answer
			currOutput = new byte[currInput.length];
			currOutput = decryptMessage(currInput, key_array, algorithm, key);
						
			// Storing output in order to write it in a txt file
			output.add(currOutput);
		}
		
		ArrayList<ArrayList<Byte>> temp = new ArrayList<ArrayList<Byte>>();
		for(int i=0; i<output.size(); i++) {
			ArrayList<Byte> temp2 = new ArrayList<Byte>();
			for(int j=0; j<output.get(i).length; j++) {
				temp2.add(output.get(i)[j]);
			}
			temp.add(temp2);
		}
		
		// Selecting mode for decrypting plainText
		if(mode.compareTo("CBC") == 0) {
			decrypted_plainText = CBC.CBC_Decryption(temp, IV, key);
		}
		else if(mode.compareTo("OFB") == 0) {
			decrypted_plainText = OFB.OFB_Decryption(temp, IV, key);
		}
		else if(mode.compareTo("CFB") == 0) {
			decrypted_plainText = CFB.CFB_Decryption(temp, IV, key);
		}
		else if(mode.compareTo("CTR") == 0) {
			decrypted_plainText = CTR.CTR_Decryption(temp, IV, key, nonce);
		}
		
		// Converting it into correct form for last time		
		ArrayList<byte[]> last_out = new ArrayList<byte[]>();
		for(int i=0; i<decrypted_plainText.size(); i++) {
			byte[] tempx = new byte[output.get(i).length];
			for(int j=0; j<decrypted_plainText.get(i).size(); j++) {
				tempx[j] = decrypted_plainText.get(i).get(j);
			}
			last_out.add(tempx);
		}
				
		return last_out;	
	}
}
