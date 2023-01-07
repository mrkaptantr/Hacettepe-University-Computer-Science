package FileCipherv2;

import java.util.ArrayList;

public class CFB {
	static String IV_key = "";
	static String main_key = "";
	
	public static ArrayList<ArrayList<Byte>> CFB_Encryption(ArrayList<ArrayList<Byte>> plaintext_byte, String IV, String main) {
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
		
		// Dealing with exceptions about size difference
		if(temp.size() < plaintext_byte.get(0).size()) {
			while(temp.size() < plaintext_byte.get(0).size()) {	
				temp.add(Byte.valueOf("0"));
			}			
		}
		if(temp.size() > plaintext_byte.get(0).size()) {
			while(temp.size() > plaintext_byte.get(0).size()) {	
				temp.remove(temp.size()-1);
			}
		}

		ArrayList<Byte> temp2 = new ArrayList<Byte>();
		// Summing temp and plaintext
		for(int j=0; j<temp.size(); j++) {
			temp2.add((byte) (temp.get(j) + plaintext_byte.get(0).get(j)));
		}
		plaintext_byte.set(0, temp2);
				
		// Making operations for each row in inputfile
		// We will deal with last row at the end
		for(int i=1; i<plaintext_byte.size(); i++) {
			ArrayList<Byte> tmp_keyy = new ArrayList<Byte>();
			tmp_keyy.addAll(original_key);
			
			// Dealing with exceptions about size difference
			if(tmp_keyy.size() < temp2.size()) {
				while(tmp_keyy.size() < temp2.size()) {	
					tmp_keyy.add(Byte.valueOf("0"));
				}
			}
			if(tmp_keyy.size() > temp2.size()) {
				while(tmp_keyy.size() > temp2.size()) {	
					tmp_keyy.remove(tmp_keyy.size()-1);
				}
			}
						
			// Summing temp-2 (temp and plaintext) and primary key
			for(int j=0; j<tmp_keyy.size(); j++) {
				tmp_keyy.set(j ,((byte) (temp2.get(j) + tmp_keyy.get(j))));
			}
						
			// Dealing with exceptions about size difference
			if(tmp_keyy.size() < plaintext_byte.get(i).size()) {
				while(tmp_keyy.size() < plaintext_byte.get(i).size()) {	
					tmp_keyy.add(Byte.valueOf("0"));
				}			
			}
			if(tmp_keyy.size() > plaintext_byte.get(i).size()) {
				while(tmp_keyy.size() > plaintext_byte.get(i).size()) {	
					tmp_keyy.remove(tmp_keyy.size()-1);
				}
			}
						
			ArrayList<Byte> temp_result = new ArrayList<Byte>();
			// Summping tmp_keyy with plaintext
			for(int j=0; j<tmp_keyy.size(); j++) {
				temp_result.add((byte) (tmp_keyy.get(j) + plaintext_byte.get(i).get(j)));
			}
			plaintext_byte.set(i, temp_result);
			temp2 = temp_result;	
		}
		
		//System.out.println("Encrypted Input: " + plaintext_byte);

		//CFB_Decryption(plaintext_byte, IV_key, main_key);

		return plaintext_byte;
	}
	
	public static ArrayList<ArrayList<Byte>> CFB_Decryption(ArrayList<ArrayList<Byte>> plaintext_byte, String IV, String main) {
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
		
		// Converting IV to byte arraylist
		ArrayList<Byte> IV_byte = new ArrayList<Byte>();		
		for(int i=0; i<IV_key.getBytes().length; i++) {
			IV_byte.add(IV_key.getBytes()[i]);
		}

		// Taking care of first row (ciphertext - Key - IV)
		
		// vector = IV + (key * i)
		ArrayList<Byte> vector = new ArrayList<Byte>();
				
		// Dealing with exceptions about size difference
		if(key_byte.size() < IV_byte.size()) {
			while(key_byte.size() < IV_byte.size()) {	
				key_byte.add(Byte.valueOf("0"));
			}			
		}
		if(key_byte.size() > IV_byte.size()) {
			while(key_byte.size() > IV_byte.size()) {	
				key_byte.remove(key_byte.size()-1);
			}
		}
				
		// Summing IV and key
		for(int j=0; j<IV_byte.size(); j++) {
			vector.add((byte) (IV_byte.get(j) + key_byte.get(j)));
		}
		
		// Dealing with exceptions about size difference
		if(vector.size() < plaintext_byte.get(0).size()) {
			while(vector.size() < plaintext_byte.get(0).size()) {	
				vector.add(Byte.valueOf("0"));
			}			
		}
		if(vector.size() > plaintext_byte.get(0).size()) {
			while(vector.size() > plaintext_byte.get(0).size()) {	
				vector.remove(vector.size()-1);
			}
		}
				
		ArrayList<Byte> temp_result = new ArrayList<Byte>();
		// Subtracting current plaintext and vector
		for(int j=0; j<vector.size(); j++) {
			temp_result.add((byte) (plaintext_byte.get(0).get(j) - vector.get(j)));
		}
		plaintext_byte.set(0, temp_result);
		
		// Updating vector (which is temp in encryption)
		for(int j=0; j<temp_result.size(); j++) {
			vector.set(j, (byte) (vector.get(j) + temp_result.get(j)));
		}
		
		// Making operations for each row in inputfile
		for(int i=1; i<plaintext_byte.size(); i++) {
						
			// Refreshing key_byte
			key_byte = new ArrayList<Byte>();
			for(int j=0; j<main_key.getBytes().length; j++) {
				key_byte.add(main_key.getBytes()[j]);
			}
			
			// Dealing with exceptions about size difference
			if(key_byte.size() < vector.size()) {
				while(key_byte.size() < vector.size()) {	
					key_byte.add(Byte.valueOf("0"));
				}			
			}
			if(key_byte.size() > vector.size()) {
				while(key_byte.size() > vector.size()) {	
					key_byte.remove(key_byte.size()-1);
				}
			}
			
			// Summing vector and key (and updating vector)
			for(int j=0; j<vector.size(); j++) {
				vector.set(j, (byte) (key_byte.get(j) + vector.get(j)));
			}
					
			// Dealing with exceptions about size difference
			if(vector.size() < plaintext_byte.get(i).size()) {
				while(vector.size() < plaintext_byte.get(i).size()) {	
					vector.add(Byte.valueOf("0"));
				}			
			}
			if(vector.size() > plaintext_byte.get(i).size()) {
				while(vector.size() > plaintext_byte.get(i).size()) {	
					vector.remove(vector.size()-1);
				}
			}
			
			ArrayList<Byte> temp_resultt = new ArrayList<Byte>();
			// Subtracting current ciphertext and vector			
			for(int j=0; j<vector.size(); j++) {
				temp_resultt.add((byte) (plaintext_byte.get(i).get(j) - vector.get(j)));
				vector.set(j, (byte) (plaintext_byte.get(i).get(j)));
			}
			
			// Updating platintext_byte
			plaintext_byte.set(i, temp_resultt);
			
		}	
		
		// Taking care of first row of input file
			
		//System.out.println("Decrypted Output: " + plaintext_byte);

		return plaintext_byte;
	}
	

}
