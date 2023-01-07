package FileCipherv2;

import java.util.ArrayList;

public class CTR {
	
	static String IV_key = "";
	static String main_key = "";
	static String nonce_key = "";
	
	public static ArrayList<ArrayList<Byte>> CTR_Encryption(ArrayList<ArrayList<Byte>> plaintext_byte, String IV, String main, String nonce) {
		// Initializing keys
		IV_key = IV;
		main_key = main;
		nonce_key = nonce;
						
		// Converting IV to byte arraylist
		ArrayList<Byte> IV_byte = new ArrayList<Byte>();		
		for(int i=0; i<IV_key.getBytes().length; i++) {
			IV_byte.add(IV_key.getBytes()[i]);
		}
						
		// Converting key to byte arraylist
		ArrayList<Byte> key_byte = new ArrayList<Byte>();	
		ArrayList<Byte> original_key = new ArrayList<Byte>();
		for(int i=0; i<main_key.getBytes().length; i++) {
			key_byte.add(main_key.getBytes()[i]);
			original_key.add(main_key.getBytes()[i]);
		}
		
		// Converting nonce to byte arraylist
		ArrayList<Byte> nonce_byte = new ArrayList<Byte>();		
		for(int i=0; i<nonce_key.getBytes().length; i++) {
			nonce_byte.add(nonce_key.getBytes()[i]);
		}
		
		// Vector (which is sum of all stuff)
		ArrayList<Byte> vector = new ArrayList<Byte>();
		for(int i=0; i<nonce_key.getBytes().length; i++) {
			vector.add(nonce_key.getBytes()[i]);
		}
				
		//System.out.println("Original Input: " + plaintext_byte);		
		
		for(int i=0; i<plaintext_byte.size(); i++) {

			// Summing counter and nonce
			for(int j=0; j<vector.size(); j++) {
				vector.set(j, (byte)(vector.get(j) + 1));
			}

			ArrayList<Byte> tmp_mainkey = new ArrayList<Byte>();
			tmp_mainkey.addAll(original_key);

			// Dealing with exceptions about size difference
			if(tmp_mainkey.size() < vector.size()) {
				while(tmp_mainkey.size() < vector.size()) {	
					tmp_mainkey.add(Byte.valueOf("0"));
				}			
			}
			if(tmp_mainkey.size() > vector.size()) {
				while(tmp_mainkey.size() > vector.size()) {	
					tmp_mainkey.remove(tmp_mainkey.size()-1);
				}
			}	
			
			// Summing vector (counter + nonce) and key
			for(int j=0; j<vector.size(); j++) {
				vector.set(j, (byte) (vector.get(j) + tmp_mainkey.get(j)));
			}
			
			// Dealing with exceptions about size difference
			int max = 0;
			for(int j=0; j<plaintext_byte.size(); j++) {
				if(max<plaintext_byte.get(j).size()) { max = plaintext_byte.get(j).size(); } 
			}
			if(vector.size() < max) {
				while(vector.size() < max) {	
					vector.add(Byte.valueOf("0"));
				}			
			}
			
			ArrayList<Byte> temp = new ArrayList<Byte>();
			// Updating output
			for(int j=0; j<plaintext_byte.get(i).size(); j++) {
				temp.add((byte)(plaintext_byte.get(i).get(j) + vector.get(j)));
			}
			plaintext_byte.set(i, temp);
		}

		//System.out.println("Encrypted Input: " + plaintext_byte);
		
		//CTR_Decryption(plaintext_byte, IV_key, main_key, nonce_key);
		
		return plaintext_byte;
	}

	public static ArrayList<ArrayList<Byte>> CTR_Decryption(ArrayList<ArrayList<Byte>> plaintext_byte, String IV, String main, String nonce) {
		// Initializing keys
		IV_key = IV;
		main_key = main;
		nonce_key = nonce;
								
		// Converting IV to byte arraylist
		ArrayList<Byte> IV_byte = new ArrayList<Byte>();		
		for(int i=0; i<IV_key.getBytes().length; i++) {
			IV_byte.add(IV_key.getBytes()[i]);
		}
								
		// Converting key to byte arraylist
		ArrayList<Byte> key_byte = new ArrayList<Byte>();	
		ArrayList<Byte> original_key = new ArrayList<Byte>();
		for(int i=0; i<main_key.getBytes().length; i++) {
			key_byte.add(main_key.getBytes()[i]);
			original_key.add(main_key.getBytes()[i]);
		}
				
		// Converting nonce to byte arraylist
		ArrayList<Byte> nonce_byte = new ArrayList<Byte>();		
		for(int i=0; i<nonce_key.getBytes().length; i++) {
			nonce_byte.add(nonce_key.getBytes()[i]);
		}
				
		// Vector (which is sum of all stuff)
		ArrayList<Byte> vector = new ArrayList<Byte>();
		for(int i=0; i<nonce_key.getBytes().length; i++) {
			vector.add(nonce_key.getBytes()[i]);
		}
		
		for(int i=0; i<plaintext_byte.size(); i++) {
			
			// Summing counter and nonce
			for(int j=0; j<vector.size(); j++) {
				vector.set(j, (byte)(vector.get(j) + 1));
			}

			ArrayList<Byte> tmp_mainkey = new ArrayList<Byte>();
			tmp_mainkey.addAll(original_key);

			// Dealing with exceptions about size difference
			if(tmp_mainkey.size() < vector.size()) {
				while(tmp_mainkey.size() < vector.size()) {	
					tmp_mainkey.add(Byte.valueOf("0"));
				}			
			}
			if(tmp_mainkey.size() > vector.size()) {
				while(tmp_mainkey.size() > vector.size()) {	
					tmp_mainkey.remove(tmp_mainkey.size()-1);
				}
			}	
			
			// Summing vector (counter + nonce) and key
			for(int j=0; j<vector.size(); j++) {
				vector.set(j, (byte) (vector.get(j) + tmp_mainkey.get(j)));
			}
			
			// Dealing with exceptions about size difference
			int max = 0;
			for(int j=0; j<plaintext_byte.size(); j++) {
				if(max<plaintext_byte.get(j).size()) { max = plaintext_byte.get(j).size(); } 
			}
			if(vector.size() < max) {
				while(vector.size() < max) {	
					vector.add(Byte.valueOf("0"));
				}			
			}
			
			ArrayList<Byte> temp = new ArrayList<Byte>();
			// Decrypting text with formula of (plaintext - vector)
			for(int j=0; j<plaintext_byte.get(i).size(); j++) {
				temp.add((byte)(plaintext_byte.get(i).get(j) - vector.get(j)));
			}
			plaintext_byte.set(i, temp);
		}
		
		//System.out.println("Decrypted Input: " + plaintext_byte);
				
		return plaintext_byte;
	}
	
		
}
