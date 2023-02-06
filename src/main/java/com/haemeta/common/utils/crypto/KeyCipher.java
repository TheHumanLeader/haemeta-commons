package com.haemeta.common.utils.crypto;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.haemeta.common.utils.lang.ByteUtil;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class KeyCipher {

    /**
     * AES 加密
     *
     * @param val
     * @param key
     * @return
     */
    public static String enCryptAES(String val, String key) throws NoSuchAlgorithmException {
        // 随机生成密钥
        byte[] keys = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(), key.getBytes()).getEncoded();
        keys = fillingAESBytes(keys);
        // 创建 AES 对象
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, keys);
        return aes.encryptHex(val);
    }

    /**
     * AES 解密
     *
     * @param val
     * @param key
     * @return
     */
    public static String deCryptAES(String val, String key) {
        // 随机生成密钥
        byte[] keys = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(), key.getBytes()).getEncoded();
        keys = fillingAESBytes(keys);
        // 创建 AES 对象
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, keys);
        return aes.decryptStr(val);
    }

    /**
     * AES 加密秘钥填充
     * 不满32/48/64，按32/48/64处理
     * 大于64的按64处理
     * @param source
     * @return
     */
    public static byte[] fillingAESBytes(byte[] source) {
        Integer keySize;
        if (source.length <= 32) {
            keySize = 32;
        } else if (32 < source.length && source.length <= 48) {
            keySize = 48;
        } else if (48 < source.length && source.length <= 64) {
            keySize = 64;
        } else {
            keySize = 64;
        }
        byte[] keys = new byte[keySize];
        for (int i = 0; i < keys.length; i++) {
            if (i < source.length)
                keys[i] = source[i];
            else
                keys[i] = -1;
        }
        return keys;
    }


    public static final Integer ASE_SIZE_128 = 128;
    public static final Integer ASE_SIZE_192 = 192;
    public static final Integer ASE_SIZE_256 = 256;

    public static String createAESKey(Integer size) throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance("AES");

        //要生成多少位，只需要修改这里即可128, 192或256
        //128:32 位字符，192:48 , 256:64
        kg.init(size);
        SecretKey sk = kg.generateKey();
        byte[] b = sk.getEncoded();
        return ByteUtil.toHexString1(b);
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        String key = "haemeta.comwqafdafsgdfg';h.m][g[r2";
        String data = encryptDES("haemeta.comffffffffffff",key);
        System.out.println(data);
        System.out.println(decryptDES(data,key));
    }

    /**
     * DES 加密
     * @param data 加密数据
     */
    public static String encryptDES(String data, String secret) {
        // 随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue(), secret.getBytes()).getEncoded();
        // 创建 DES 对象
        SymmetricCrypto des = new SymmetricCrypto(SymmetricAlgorithm.DES, key);
        // 加密
        return des.encryptHex(data);
    }

    /**
     * DES 解密
     * @param data 加密数据
     * @param secret 至少8位
     */
    public static String decryptDES(String data, String secret) {
        // 随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue(), secret.getBytes()).getEncoded();
        // 创建 DES 对象
        SymmetricCrypto des = new SymmetricCrypto(SymmetricAlgorithm.DES, key);
        // 解密
        return des.decryptStr(data);
    }


}
