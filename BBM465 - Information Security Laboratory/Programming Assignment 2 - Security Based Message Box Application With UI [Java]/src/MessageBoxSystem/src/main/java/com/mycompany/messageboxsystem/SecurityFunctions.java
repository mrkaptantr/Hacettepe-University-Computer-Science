package com.mycompany.messageboxsystem;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecurityFunctions {
        
    private final static String key = "u0!F5pil";
    
    // Hashing function
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
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Encryption function
    public static byte[] encryptMessage(byte[] message) {
        
        // Initializing key
        byte[] keyBytes = new byte[8];
        for(int j=0; j<keyBytes.length; j++) {
            keyBytes[j] = key.getBytes()[j];
	}
        
        // Initializing cipher
	try {
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            SecretKey secretKey = new SecretKeySpec(keyBytes, "DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(message);
	} catch (Exception e) {
            e.printStackTrace();
	}
        return null;        
    }
    
    // Decryption function
    public static byte[] decryptMessage(byte[] message) {

        // Initializing key
        byte[] keyBytes = new byte[8];
        for(int j=0; j<keyBytes.length; j++) {
            keyBytes[j] = key.getBytes()[j];
	}

        // Initializing cipher
        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            SecretKey secretKey = new SecretKeySpec(keyBytes, "DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;  
    }
}
