package com.lib.spref.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author lpereira on 05/05/2016.
 */
public class ByteUtils {
    /**
     * Get the byte array for the string received
     *
     * @param clear The content to be encrypted.
     * @return The byte array representing the string received
     * @throws UnsupportedEncodingException
     */
    public static byte[] getByteArray(String clear) throws UnsupportedEncodingException {
        ByteArrayInputStream input = new ByteArrayInputStream(clear.getBytes("UTF-8"));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int r = input.read();
        while (r != Utils.INVALID_ID) {
            baos.write(r);
            r = input.read();
        }

        return baos.toByteArray();
    }
}
