package com.haemeta.common.utils.crypto;

import com.haemeta.common.exception.system.EncryptionException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 佘纪凡
 * 加密工具:md5加密
 */
public class MD5Util {

    private static final String MD5_ALGORITHM_NAME = "MD5";
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String encode(String source) throws EncryptionException {
        try {
            return new String(
                    encodeHex(
                            MessageDigest.getInstance(MD5_ALGORITHM_NAME).digest(source.getBytes(StandardCharsets.UTF_8))
                    )
            );
        } catch (NoSuchAlgorithmException var2) {
            throw new IllegalStateException("Could not find MessageDigest with algorithm \"MD5\"", var2);
        }
    }

    private static char[] encodeHex(byte[] bytes) {
        char[] chars = new char[32];

        for (int i = 0; i < chars.length; i += 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_CHARS[b >>> 4 & 15];
            chars[i + 1] = HEX_CHARS[b & 15];
        }

        return chars;
    }



}
