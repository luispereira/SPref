package com.lib.spref.Utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author lpereira on 05/05/2016.
 */
public class EncryptionUtils {
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String ALGORITHM_SHA1 = "SHA-1";
    private static final String SECRET_KEY = "AES";
    private static final String STRING_ENCODING = "UTF-8";


    /**
     * Encrypts a string into an byte[] using AES ECB
     * @param seed key to be used
     * @param clearMessage the message to encrypt
     * @return encrypted information in byte array format
     */
    public static byte[] encrypt(byte[] seed, String clearMessage) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(seed, SECRET_KEY);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(ByteUtils.getByteArray(clearMessage));
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Decrypts an byte[] into a string using AES ECB
     * @param seed key
     * @param content message to decrypt
     * @return the string decrypted
     */
    public static String decrypt(byte[] seed, byte[] content) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(seed, SECRET_KEY);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decrypted = cipher.doFinal(content);
            if (decrypted == null) {
                return null;
            }
            return new String(decrypted, STRING_ENCODING);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * This is generate the same key according to the input value
     * @param value key
     * @return the key in bytes to be used later on
     */
    public static byte[] generateKey(String value) {
        MessageDigest sha;
        try {
            byte[] key = value.getBytes(STRING_ENCODING);
            sha = MessageDigest.getInstance(ALGORITHM_SHA1);
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use only first 128 bit

            SecretKeySpec secretKeySpec = new SecretKeySpec(key, SECRET_KEY);
            return secretKeySpec.getEncoded();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return null;
        }
    }
}
