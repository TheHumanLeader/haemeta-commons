package com.haemeta.common.utils.lang;

import java.util.stream.IntStream;

public class ByteUtil {


    /**
     * @method: hexToBytes
     * @description: 16进制字符串转byte数组
     * @param hex 16进制字符串
     * @return: byte[]
     * @author: xj
     * @date: 2020/9/15
     */
    public static byte[] hexToBytes(String hex) {
        String[] hexData = hex.replaceAll("(.{2})", "$1 ").split(" ");
        byte[] bytes = new byte[hexData.length];
        IntStream.range(0, hexData.length).forEach(i ->
                bytes[i] = (byte) Integer.parseInt(hexData[i], 16));
        return bytes;
    }

    /**
     * @method: bytesToHex
     * @description: byte数组转16进制字符串
     * @param bytes byte数组
     * @return: java.lang.String
     * @author: xj
     * @date: 2020/9/15
     */
    public static String toHexString1(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        IntStream.range(0, bytes.length).forEach(i -> {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            builder.append(hex);
        });
        return builder.toString();
    }

}
