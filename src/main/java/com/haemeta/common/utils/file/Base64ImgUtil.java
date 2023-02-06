package com.haemeta.common.utils.file;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

public class Base64ImgUtil {

    /**
     * base64编码字符串转换为图片
     * @param base64 base64编码字符串
     * @param path 图片路径
     * @return
     */
    public static void base64StrToImage(String base64, String path) throws Exception {
        //去除前缀
        base64 = base64.substring(base64.indexOf(",")+1);
        if (base64 == null) throw new Exception("图片内容为空");
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(base64);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //文件夹不存在则自动创建
            File tempFile = new File(path);
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(tempFile,false);
            out.write(b);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void base64StrToImage(String base64, File file) throws Exception {
        //去除前缀
        base64 = base64.substring(base64.indexOf(",")+1);
        if (base64 == null) throw new Exception("图片内容为空");
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(base64);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            //文件夹不存在则自动创建
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file,false);
            out.write(b);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 图片转base64字符串
     * @param imgFile 图片路径
     * @return
     */
    public static String imageToBase64Str(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

}
