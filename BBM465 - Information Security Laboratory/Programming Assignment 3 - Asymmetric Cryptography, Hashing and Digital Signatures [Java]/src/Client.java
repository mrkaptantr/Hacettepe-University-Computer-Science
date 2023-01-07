import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;

public class Client {
	
	private static String macID;  // Mac ID of computer
	private static String diskID; // Not general HDD's but current logical disk's serial number (for example C drive)
	private static String motherboardID; // Motherboard ID of computer
	private static String userName = "RJ+Xpxn7EppJeMl3esZQZixsjS4jqf6DXPSpNJF8DS1N8YMd4rYP5wn1+Z3LoTb0CWYp+eAceo8WKZ1MoZVpcR3W5Vh26tFF6BndC1YuKq1+h/KH7v++PTm3oFAz2FUYuLTXtIRaO/lvSVJPLuh1Ia75pSTjj6zVT60+5SxDxto="; // Username = Admin
	private static String serialNumber = "UVMggFxKTh6v+mWEATedrXo63i4eVPC7ukAm8MATLtzPGMFy6RODWGAHavq1Yi0RcqmglSkn/DV6Ke45BBNiwjztt1RucYqKT902ZYqSYsu1f/GoVXo+1s1hF8tU/nIUTg9CpDiFIq48xLLZl3L9mNVy9edFioEqQQqVOVrSSoo="; // Serial Number = A068-01A1-1201
	public static PublicKey public_key;
	
	// Simple method to find Mac ID of computer
	public static String getMacID() throws SocketException, UnknownHostException {
		InetAddress localHost;
		localHost = InetAddress.getLocalHost();
		NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
		byte[] hardwareAddress = ni.getHardwareAddress();
		
		String[] hexadecimal = new String[hardwareAddress.length];
		for (int i = 0; i < hardwareAddress.length; i++) {
		    hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
		}
		String macAddress = String.join(":", hexadecimal);
		
		return macAddress;
	}
	
	// Simple method to find disk ID and motherboard ID of computer with using CMD
	public static String getWindowsSystemData(String keyword, String secondParameter, String lastParameter) throws IOException, InterruptedException {
	    ProcessBuilder pb = new ProcessBuilder("wmic", secondParameter, "get", keyword);
	    Process process = pb.start();
	    process.waitFor();
	    String output = "";
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
	        for (String line = br.readLine(); line != null; line = br.readLine()) {
	            if (line.length() < 1 || line.startsWith(lastParameter)) { continue; }
	            output = line;
	            break;
	        }
	    }
	    return output.trim();
	}
	
	// Simple method to convert hexadecimal string to decimal format
	public static int hexadecimalToDecimal(String hexVal) {
		
		int len = hexVal.length();
		int base = 1;
		int dec_val = 0;
		
		// Extracting characters as digits from last character
		for (int i = len - 1; i >= 0; i--) {
			if (hexVal.charAt(i) >= '0' && hexVal.charAt(i) <= '9') {
				dec_val += (hexVal.charAt(i) - 48) * base;
				base = base * 16;
			}
			else if (hexVal.charAt(i) >= 'A' && hexVal.charAt(i) <= 'F') {
				dec_val += (hexVal.charAt(i) - 55) * base;
				base = base * 16;
			}
		}
		// Returning the decimal value
		return dec_val;
	}
	
	// Simple method to check whether license exists
	public static boolean isLicenseExist() {
		File license = new File("license.txt");
		if(license.exists()) {
			System.out.println("Client -- License file is found.");
			return true;
		} else {
			System.out.println("Client -- License file is not found.");
			return false;
		}
	}
	
	// Simple method to make asymmetric encryption with RSA algorithm
	public static String encodeWithRSA(String toEncode) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, public_key);
	    byte[] bytes = cipher.doFinal(toEncode.getBytes(StandardCharsets.UTF_8));
	    return new String(Base64.getEncoder().encode(bytes));
	}

	// Simple method to create MD-5 hash values of input strings
    public static String getMD5Hash(String input){
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
 
            // digest() method is called to calculate message digest of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
            
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
            
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) { hashtext = "0" + hashtext; }
            return hashtext;
        }    
        catch (NoSuchAlgorithmException e) { throw new RuntimeException(e); }
    }


	public static void main(String[] args) throws Exception {
		// Starting the system and initializing required important data
		System.out.println("Client started...");
		macID = getMacID();
		diskID = String.valueOf(hexadecimalToDecimal(getWindowsSystemData("volumeserialnumber", "logicaldisk", "VolumeSerialNumber")));
		motherboardID = getWindowsSystemData("serialnumber", "baseboard", "SerialNumber");
		System.out.println("My MAC: " + macID);
		System.out.println("My Disk ID: " + diskID);
		System.out.println("My Motherboard ID: " + motherboardID);
		
		// Initializing public key (and private key for License Manager)
		LicenseManager.initializePublicKey();
		public_key = LicenseManager.public_key; // Private key will be used by both of classes
		LicenseManager.initializePrivateKey();
		
		// Checking whether the license exist
		System.out.println("LiscenseManager service started...");
		boolean isExist = isLicenseExist();
		
		// Creating license text
		String licenseText = LicenseManager.decodeWithRSA(userName) + "$" + LicenseManager.decodeWithRSA(serialNumber) + "$" + macID + "$" + diskID + "$" + motherboardID;
		if(!isExist) { System.out.println("Client -- Raw License Text: " + licenseText); }
		byte lines[] = new byte[licenseText.getBytes().length];
		for(int i=0; i<licenseText.getBytes().length; i++) { lines[i] = licenseText.getBytes()[i]; }
			
		// Encrypting license text with using RSA algorithm
		String encryptedLicenseText = encodeWithRSA(licenseText);
		if(!isExist) { System.out.println("Client -- Encrypted License Text: " + encryptedLicenseText); }

		// Hashing license text with using MD5 algorithm
		String hashedLicenseText = getMD5Hash(licenseText);
		if(!isExist) { System.out.println("Client -- MD5 License Text: " + hashedLicenseText); }
					
		// Sending request to server and returning the signed signature
		Signature signature = LicenseManager.initializeServer(encryptedLicenseText, !isExist);
					
		// Verifying signed signature from server
		byte[] sign_byte = signature.sign();
		String signature_str = Arrays.toString(signature.sign());
		signature_str = signature_str.substring(1, signature_str.length()-1); // Byte array version of signature (with out '[' and ']')	
		signature.initVerify(public_key);
		boolean bool = signature.verify(sign_byte);
		
		// ******* FIRST LICENSING CASES (if license doesn't exist) *******
		// Case-1: If license file doesn't exist and if signature is verified
		// In that case write license text to byte array version of signed signature
		if(bool) {
			if(!isExist) { 
				System.out.println("Client -- License is not found.");
				System.out.println("Client -- Succeed. The license file content is secured and signed by the server.");
				try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("license.txt"), "UTF-8"))) {	
					writer.write(signature_str);
					writer.close();
				}
			}
		// Case-2: If signature is not verified
		// This can be only caused from errors of program. For example if you change code above (for example adding extra bytes to signature independently), than it will give that error
		// In that case write a console message to show the error
		} else {
			if(!isExist) {
				System.out.println("Client -- License is not found.");
				System.out.println("Client -- Failed. The license file content is not secured and not signed by the server.");
			}
		}		
		
		// ******* OTHER LICENSING CASES (if license exist) *******
		if(isExist) {
			// Step-1: Reading license.txt and storing string value
			String license_text_str = "";
			File file = new File("license.txt");
			try {
				Scanner scan = new Scanner(file, "UTF-8");	
				while(scan.hasNextLine()) {
					String line = scan.nextLine();
					license_text_str += line;
				}
				scan.close();
			}
			catch (Exception e) { e.printStackTrace(); }
			
			// Step-2: Converting existing license from string to byte array format
			byte[] license_text = new byte[license_text_str.split(",").length];
			try {
				for(int i=0; i<license_text.length; i++) {
					license_text[i] = Byte.valueOf(license_text_str.split(",")[i].strip());
				}
				
				// Step-3: Verifying signature and authorization
				boolean checker = signature.verify(license_text);
				if(checker) {
					System.out.println("Client -- Succeed. The license is correct.");
				} else {
					System.out.println("Client -- The license file has been broken!!");
				}
			} catch (Exception e) {
				// Step-4: (Additional condition) If license file is changed in a way that cause an exception in program, than don't authorize user
				// For example, license.txt includes integers and we read integer files. If integer values will be changed, then step-3 is enough. But if attacker adds non-integer values, then this error handling is required.
				System.out.println("Client -- The license file has been broken!!");
			}
		}
	}
}
