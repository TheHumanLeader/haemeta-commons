package com.haemeta.common.utils.crypto;

import com.haemeta.common.exception.system.EncryptionException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author 佘纪凡
 * base64 解密：该类仅用于普通字符，不包含url/mime
 * 推荐使用原 java.util.Base64
 * 该工具功能更多
 */
public class Base64Util {

    public static final Base64.Decoder base64Decoder = Base64.getDecoder();
    public static final Base64.Encoder base64Encoder = Base64.getEncoder();

    /**
     * 加密
     * @param source
     * @return
     */
    public static String encode(String source){
        return base64Encoder.encodeToString(source.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 解密有风险
     * @param source
     * @return
     * @throws EncryptionException
     */
    public static String decode(String source) throws EncryptionException {
        try {
            return new String(base64Decoder.decode(source),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new EncryptionException(e);
        }
    }

}
