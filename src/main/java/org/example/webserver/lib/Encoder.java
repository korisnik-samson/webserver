package org.example.webserver.lib;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {
    public static String getMd5(String input) {
        try {
            MessageDigest msgDst = MessageDigest.getInstance("MD5");
            byte[] msgArr = msgDst.digest(input.getBytes());

            BigInteger bi = new BigInteger(1, msgArr);
            StringBuilder hashValue = new StringBuilder(bi.toString(16));

            while (hashValue.length() < 32) hashValue.insert(0, "0");

            return hashValue.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
