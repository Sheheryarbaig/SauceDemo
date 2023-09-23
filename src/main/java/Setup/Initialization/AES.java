package Setup.Initialization;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


/**
 *
 * @author josepholaoye
 */
public class AES {



    String key;



    public AES(String myEncryptionKey) {
        key = myEncryptionKey;
    }



    /**
     * Method To Encrypt The String
     *
     * @param unencryptedString
     * @return encrpted string
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public String harden(String unencryptedString) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        MessageDigest md = MessageDigest.getInstance("md5");
        byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);



        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }



        SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);



        byte[] plainTextBytes = unencryptedString.getBytes("utf-8");
        byte[] buf = cipher.doFinal(plainTextBytes);
        byte[] base64Bytes = Base64.encodeBase64(buf);
        String base64EncryptedString = new String(base64Bytes);



        return base64EncryptedString;
    }



    /**
     * Method To Decrypt An Ecrypted String
     *
     * @param encryptedString
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public  String soften(String encryptedString) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if(encryptedString == null)
        {
            return "";
        }
        byte[] message = Base64.decodeBase64(encryptedString.getBytes("utf-8"));



        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
        byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

        for (int j = 0, k = 16; j < 8;) {
            keyBytes[k++] = keyBytes[j++];
        }

        SecretKey secretKey = new SecretKeySpec(keyBytes, "TripleDES");



        Cipher decipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        decipher.init(Cipher.DECRYPT_MODE, secretKey);



        byte[] plainText = decipher.doFinal(message);



        return new String(plainText, "UTF-8");



    }

    public static void main(String args []) throws Exception
    {





        //  System.out.println("String To Encrypt: "+ target);
        // System.out.println("Encrypted String:" + encrypted);
        //System.out.println("Decrypted String:" +     );



        String Result;
        AES obj1 = new AES("#PSW-OTP-KEY-123");
        Result = obj1.soften("9uvZpAoEQBY=");



    }




}