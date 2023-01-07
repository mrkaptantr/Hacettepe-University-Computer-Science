package FileCipherv2;

import java.util.ArrayList;

public class OFB {
	
	static String IV_key = "";
	static String main_key = "";
	
	public static ArrayList<ArrayList<Byte>> OFB_Encryption(ArrayList<ArrayList<Byte>> plaintext_byte, String IV, String main) {
		// Initializing keys
		IV_key = IV;
		main_key = main;
				
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

		//System.out.println("Original Input: " + plaintext_byte);
		
		ArrayList<Byte> tmp_IV = new ArrayList<Byte>();
		tmp_IV.addAll(IV_byte);
		
		// Dealing with exceptions about size difference
		if(tmp_IV.size() < key_byte.size()) {
			while(tmp_IV.size() < key_byte.size()) {	
				tmp_IV.add(Byte.valueOf("0"));
			}			
		}
		if(tmp_IV.size() > key_byte.size()) {
			while(tmp_IV.size() > key_byte.size()) {	
				tmp_IV.remove(tmp_IV.size()-1);
			}
		}
		
		ArrayList<Byte> temp = new ArrayList<Byte>();
		// Summing IV and key
		for(int j=0; j<tmp_IV.size(); j++) {
			temp.add((byte) (tmp_IV.get(j) + key_byte.get(j)));
		}
						
		// Making operations for each row in inputfile
		// We will deal with last row at the end
		for(int i=0; i<plaintext_byte.size()-1; i++) {
			
			// Dealing with exceptions about size difference
			ArrayList<Byte> tmp_key = new ArrayList<Byte>();
			tmp_key.addAll(key_byte);
			
			if(tmp_key.size() < plaintext_byte.get(i).size()) {
				while(tmp_key.size() < plaintext_byte.get(i).size()) {	
					tmp_key.add(Byte.valueOf("0"));
				}			
			}
			if(tmp_key.size() > plaintext_byte.get(i).size()) {
				while(tmp_key.size() > plaintext_byte.get(i).size()) {	
					tmp_key.remove(tmp_key.size()-1);
				}			
			}
				
			ArrayList<Byte> temp_out = new ArrayList<Byte>();
			// Summing plaintext and key
			for(int j=0; j<tmp_key.size(); j++) {
				temp_out.add((byte) (tmp_key.get(j) + plaintext_byte.get(i).get(j)));
			}
			plaintext_byte.set(i, temp_out);
			
			// Retrieving IV
			tmp_IV = new ArrayList<Byte>();
			tmp_IV.addAll(IV_byte);
			
			// Dealing with exceptions about size difference
			if(tmp_IV.size() < tmp_key.size()) {
				while(tmp_IV.size() < tmp_key.size()) {	
					tmp_IV.add(Byte.valueOf("0"));
				}			
			}
			if(tmp_IV.size() > tmp_key.size()) {
				while(tmp_IV.size() > tmp_key.size()) {	
					tmp_IV.remove(tmp_IV.size()-1);
				}
			}
			
			// Updating temp key, which connects nodes (Mathematically -> (IV + K) + (LoopTime x (K))
			for(int j=0; j<Math.min(temp.size(),tmp_IV.size()); j++) {
				temp.set(j, (byte)(temp.get(j) + tmp_IV.get(j)));
			}
		}
		
		// Dealing with exceptions about size difference
		if(original_key.size() < plaintext_byte.get(plaintext_byte.size()-1).size()) {
			while(original_key.size() < plaintext_byte.get(plaintext_byte.size()-1).size()) {	
				original_key.add(Byte.valueOf("0"));
			}			
		}
		if(original_key.size() > plaintext_byte.get(plaintext_byte.size()-1).size()) {
			while(original_key.size() > plaintext_byte.get(plaintext_byte.size()-1).size()) {	
				original_key.remove(original_key.size()-1);
			}
		}
				
		// Updating last element
		ArrayList<Byte> last_out = new ArrayList<Byte>();
		for(int i=0; i<plaintext_byte.get(plaintext_byte.size()-1).size(); i++) {
			last_out.add((byte) (plaintext_byte.get(plaintext_byte.size()-1).get(i) + original_key.get(i)));
		}
		plaintext_byte.set(plaintext_byte.size()-1, last_out);
		
		//System.out.println("Encrypted Input: " + plaintext_byte);
		
		//OFB_Decryption(plaintext_byte, IV_key, main_key);
			
		return plaintext_byte;
	}
	
	public static ArrayList<ArrayList<Byte>> OFB_Decryption(ArrayList<ArrayList<Byte>> plaintext_byte, String IV, String main) {
		// Initializing keys
		IV_key = IV;
		main_key = main;		
				
		ArrayList<Byte> temp = new ArrayList<Byte>();

		// Converting key to byte arraylist
		ArrayList<Byte> key_byte = new ArrayList<Byte>();		
		ArrayList<Byte> original_key = new ArrayList<Byte>();
		for(int i=0; i<main_key.getBytes().length; i++) {
			key_byte.add(main_key.getBytes()[i]);
			original_key.add(main_key.getBytes()[i]);
		}

		// Dealing with exceptions about size difference
		if(original_key.size() < plaintext_byte.get(plaintext_byte.size()-1).size()) {
			while(original_key.size() < plaintext_byte.get(plaintext_byte.size()-1).size()) {	
				original_key.add(Byte.valueOf("0"));
			}			
		}
		if(original_key.size() > plaintext_byte.get(plaintext_byte.size()-1).size()) {
			while(original_key.size() > plaintext_byte.get(plaintext_byte.size()-1).size()) {	
				original_key.remove(original_key.size()-1);
			}
		}

		// Updating last element
		ArrayList<Byte> last_out = new ArrayList<Byte>();
		for(int i=0; i<plaintext_byte.get(plaintext_byte.size()-1).size(); i++) {
			last_out.add((byte) (plaintext_byte.get(plaintext_byte.size()-1).get(i) - original_key.get(i)));
		}
		plaintext_byte.set(plaintext_byte.size()-1, last_out);
		
		// Making operations for each row in inputfile
		// We will deal with first row at the end
		for(int i=0; i<plaintext_byte.size()-1; i++) {	
			
			// Dealing with exceptions about size difference
			ArrayList<Byte> tmp_key = new ArrayList<Byte>();
			tmp_key.addAll(key_byte);
						
			if(tmp_key.size() < plaintext_byte.get(i).size()) {
				while(tmp_key.size() < plaintext_byte.get(i).size()) {	
					tmp_key.add(Byte.valueOf("0"));
				}			
			}
			if(tmp_key.size() > plaintext_byte.get(i).size()) {
				while(tmp_key.size() > plaintext_byte.get(i).size()) {	
					tmp_key.remove(tmp_key.size()-1);
				}			
			}
			
			ArrayList<Byte> temp_out = new ArrayList<Byte>();
			// Subtracting plaintext and key
			for(int j=0; j<tmp_key.size(); j++) {
				temp_out.add((byte) (plaintext_byte.get(i).get(j) - tmp_key.get(j)));
			}
			plaintext_byte.set(i, temp_out);
		}
		//System.out.println("Decrypted Output: " + plaintext_byte);
		return plaintext_byte;
	}

}
