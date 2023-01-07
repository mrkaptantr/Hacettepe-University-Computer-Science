import javax.crypto.Cipher;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class LicenseManager {
	
	private static PrivateKey private_key;
	public static PublicKey public_key;
	
	// Simple method to initialize public key of server
	public static void initializePublicKey() throws IOException, NoSuchAlgorithmException {
		byte[] keyBytes = Files.readAllBytes(Paths.get("public.key"));
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		try {
			public_key = kf.generatePublic(spec);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}
	
	// Simple method to initialize private key of server
	public static void initializePrivateKey() throws IOException, NoSuchAlgorithmException {
		byte[] keyBytes = Files.readAllBytes(Paths.get("private.key"));
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		try {
			private_key = kf.generatePrivate(spec);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
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
    
    // Simple method to make asymmetric decryption with RSA algorithm
    public static String decodeWithRSA(String toDecode) throws Exception {
    	Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    	cipher.init(Cipher.DECRYPT_MODE, private_key);
    	byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(toDecode));
    	return new String(bytes);
    }
    
    // Simple method to create and sign a signature with 'SHA256withRSA' scheme
    private static Signature signSHA256RSA(String input, PrivateKey priv_key, boolean willPrint) throws Exception {
		Signature privateSignature = Signature.getInstance("SHA256withRSA");
		privateSignature.initSign(priv_key);
		privateSignature.update(input.getBytes("UTF-8"));
		byte[] s = privateSignature.sign();
		if(willPrint) { System.out.println("Server -- Digital Signature: " + Base64.getEncoder().encodeToString(s)); }
		return privateSignature;
    }
	
    // System initializator method for server, it returns created signature to Client side
	public static Signature initializeServer(String encryptedText, boolean willPrint) throws Exception {
		if(willPrint) { System.out.println("Server -- Server is being requested..."); }
		if(willPrint) { System.out.println("Server -- Incoming Encrypted Text: " + encryptedText); }
		Signature digitalSignature = null;
		try { 
			String decodedText = decodeWithRSA(encryptedText); 
			if(willPrint) { System.out.println("Server -- Decrypted Text: " + decodedText); }
			String hashedText = getMD5Hash(decodedText);
			if(willPrint) { System.out.println("Server -- MD5 Plain License Text: " + hashedText); } 
			digitalSignature = signSHA256RSA(hashedText, private_key, willPrint);
		} catch (Exception e) { System.out.println("Server -- Attack! High probability to change of encrypted text content!"); }
		return digitalSignature;
	}
}