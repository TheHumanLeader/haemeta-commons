package com.haemeta.common.utils.crypto;

import com.haemeta.common.exception.system.EncryptionException;
import com.haemeta.common.utils.lang.StringUtil;
import org.apache.commons.lang3.CharSet;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.CharsetDecoder;
import java.security.Security;

public class Crypto {

    public static final String AES = "AES";

    private static final String ALGORITHMSTR = "AES/ECB/PKCS7Padding";

    private static final String PUBLIC_KEY;

    private static final String defaultCharset = "UTF-8";

    static {
        Security.addProvider(new BouncyCastleProvider());
        PUBLIC_KEY = StringUtil.randomString_09(16);
    }

    /**
     * encrypt input text
     *
     * @param target
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String target, String key) throws EncryptionException {
        return encrypt(target,key,defaultCharset);
    }

    public static String encrypt(String target, String key,String charsetName) throws EncryptionException {
        if(target == null || key == null) throw new EncryptionException("解密异常");
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), AES);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            byte[] crypted = cipher.doFinal(target.getBytes());
            return new String(Base64.encodeBase64(crypted),charsetName);
        }catch (Exception e){
            throw new EncryptionException(e);
        }
    }

    /**
     * decrypt input text
     *
     * @param target
     * @param key
     * @return
     */
    public static String decrypt(String target, String key) throws EncryptionException {
        return decrypt(target, key,defaultCharset);
    }

    public static String decrypt(String target, String key,String charset) throws EncryptionException {
        if(target == null || key == null) throw new EncryptionException("解密异常");
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), AES);
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            cipher.init(Cipher.DECRYPT_MODE, skey);
            byte[] output = cipher.doFinal(Base64.decodeBase64(target));

            return new String(output,charset);
        }catch (Exception e){
            throw new EncryptionException(e);
        }
    }

    public static String getTimeKey(Long time) {
        String timeStr = String.valueOf(time).substring(1, 9);
        StringBuffer timeStrBuffer = new StringBuffer().append(timeStr);
        timeStrBuffer.append(timeStr);
        return timeStrBuffer.toString();
    }

}
