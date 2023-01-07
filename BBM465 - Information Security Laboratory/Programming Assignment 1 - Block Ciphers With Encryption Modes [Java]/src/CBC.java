package FileCipherv2;

import java.util.ArrayList;

public class CBC {

	static String IV_key = "";
	static String main_key = "";
	
	public static ArrayList<ArrayList<Byte>> CBC_Encryption(ArrayList<ArrayList<Byte>> plaintext_byte, String IV, String main) {
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
		for(int i=0; i<main_key.getBytes().length; i++) {
			key_byte.add(main_key.getBytes()[i]);
		}
		
		// Output (encrypted plaintext)
		ArrayList<ArrayList<Byte>> plainText = new ArrayList<ArrayList<Byte>>();

		//System.out.println("Original Input: " + plaintext_byte);
		
		ArrayList<Byte> tmp_IV = new ArrayList<Byte>();
		tmp_IV.addAll(IV_byte);
		
		// Dealing with exceptions about size difference
		if(tmp_IV.size() < plaintext_byte.get(0).size()) {
			while(tmp_IV.size() < plaintext_byte.get(0).size()) {	
				tmp_IV.add(Byte.valueOf("0"));
			}			
		}
		if(tmp_IV.size() > plaintext_byte.get(0).size()) {
			while(tmp_IV.size() > plaintext_byte.get(0).size()) {	
				tmp_IV.remove(tmp_IV.size()-1);
			}
		}			

		ArrayList<Byte> temp = new ArrayList<Byte>();
		// Summing IV and plaintext
		for(int j=0; j<tmp_IV.size(); j++) {
			temp.add((byte) (tmp_IV.get(j) + plaintext_byte.get(0).get(j)));
		}
		plainText.add(temp);
		
		for(int j=1; j<plaintext_byte.size(); j++) {
			plainText.add(plaintext_byte.get(j));
		}	
			
		// Making operations for each row in inputfile
		for(int i=0; i<plaintext_byte.size(); i++) {

			// Dealing with exceptions about size difference
			ArrayList<Byte> tmp_key = new ArrayList<Byte>();
			tmp_key.addAll(key_byte);
			
			if(tmp_key.size() < plainText.get(i).size()) {
				while(tmp_key.size() < plainText.get(i).size()) {	
					tmp_key.add(Byte.valueOf("0"));
				}			
			}
			if(tmp_key.size() > plainText.get(i).size()) {
				while(tmp_key.size() > plainText.get(i).size()) {	
					tmp_key.remove(tmp_key.size()-1);
				}			
			}
			
			temp = new ArrayList<Byte>();
			// Summing plaintext and key
			for(int j=0; j<tmp_key.size(); j++) {
				temp.add((byte) (tmp_key.get(j) + plainText.get(i).get(j)));
			}
			plainText.set(i, temp);
		}
		//System.out.println("Encrypted Input: " + plainText);
		
		//CBC_Decryption(plainText, IV_key, main_key);
		
		return plainText;
	}
	
	public static ArrayList<ArrayList<Byte>> CBC_Decryption(ArrayList<ArrayList<Byte>> plaintext_byte, String IV, String main) {
		// Initializing keys
		IV_key = IV;
		main_key = main;		
		
		ArrayList<Byte> temp = new ArrayList<Byte>();

		// Converting key to byte arraylist
		ArrayList<Byte> key_byte = new ArrayList<Byte>();		
		for(int i=0; i<main_key.getBytes().length; i++) {
			key_byte.add(main_key.getBytes()[i]);
		}
		
		// Output (decrypted plaintext)
		ArrayList<ArrayList<Byte>> plainText = new ArrayList<ArrayList<Byte>>();

		// Making operations for each row in inputfile in reverse order
		for(int i=plaintext_byte.size()-1; i>-1; i--) {	
			
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
						
			temp = new ArrayList<Byte>();
			// Subtracting plaintext and key
			for(int j=0; j<tmp_key.size(); j++) {
				temp.add((byte) (plaintext_byte.get(i).get(j) - tmp_key.get(j)));
			}
			plaintext_byte.set(i, temp);
		}
		
		// Converting IV to byte arraylist
		ArrayList<Byte> IV_byte = new ArrayList<Byte>();		
		for(int i=0; i<IV_key.getBytes().length; i++) {
			IV_byte.add(IV_key.getBytes()[i]);
		}
		
		// Dealing with exceptions about size difference
		ArrayList<Byte> tmp_IV = new ArrayList<Byte>();
		tmp_IV.addAll(IV_byte);
					
		if(tmp_IV.size() < plaintext_byte.get(0).size()) {
			while(tmp_IV.size() < plaintext_byte.get(0).size()) {	
				tmp_IV.add(Byte.valueOf("0"));
			}			
		}
		if(tmp_IV.size() > plaintext_byte.get(0).size()) {
			while(tmp_IV.size() > plaintext_byte.get(0).size()) {	
				tmp_IV.remove(tmp_IV.size()-1);
			}			
		}
		
		temp = new ArrayList<Byte>();
		// Subtracting plaintext and IV
		for(int i=0; i<tmp_IV.size(); i++) {
			temp.add((byte) (plaintext_byte.get(0).get(i) - tmp_IV.get(i)));
		}
		plaintext_byte.set(0, temp);
		
		//System.out.println("Decrypted Input: " + plaintext_byte);
		return plaintext_byte;
	}
	
}
