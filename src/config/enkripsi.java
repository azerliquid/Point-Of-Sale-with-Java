package config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @author Reza Azmi
 */
public class enkripsi {
    public static String username;
    public static String password;
    public static String iduser;
    
     
    public static String encrypt(String str) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString((str.getBytes()));
    }
    
    public static String decrypt(String str) {
        if(str.length() > 0){
            String cipher = str.substring(0);
            return new String (Base64.getDecoder().decode(cipher));
        }
        return null;
    }
}
