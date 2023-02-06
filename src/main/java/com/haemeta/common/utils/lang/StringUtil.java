package com.haemeta.common.utils.lang;

import cn.hutool.core.codec.Base32;
import cn.hutool.core.codec.Base32Codec;
import org.apache.commons.text.StringSubstitutor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class StringUtil {

    public static String stringNull_TO_StringWithValue(String str){
        return null == str? "":str;
    }

    public static final Base64.Decoder base64Decoder = Base64.getDecoder();

    public static Boolean isEmpty(String target){
        return target == null || target.length() == 0;
    }
    public static Boolean isEmptyWithOutSpace(String target){
        return target == null || target.trim().length() == 0;
    }

    public static Boolean notEmpty(String target){
        return target != null && target.length() != 0;
    }
    public static Boolean notEmptyWithOutSpace(String target){
        return target != null && target.trim().length() != 0;
    }

    public static String stringWithValue_TO_StringNull(String str){
        return null == str ? null:str.trim().equals("")? null:str;
    }

    public static String randomString_09azAz(int lenth){
        char [] randomList = new char[]{
                'A','B','C','D','E','F','G','H','I','J','K',
                'L','M','N','O','P','Q','R','S','T','U','V','W',
                'X','Y','Z',
                'a','b','c','d','e','f','g','h','i','j','k',
                'l','m','n','o','p','q','r','s','t','u','v','w',
                'x','y','z',
                '1','2','3','4','5','6','7','8','9','0',
        };
        StringBuffer buffer = new StringBuffer();
        while (buffer.length()!=lenth){
            int ends = (int)(Math.random()*62);
            buffer.append(randomList[ends]);
        }

        return buffer.toString();
    }

    public static String randomString_az(int lenth){
        char [] randomList = new char[]{
                'a','b','c','d','e','f','g','h','i','j','k',
                'l','m','n','o','p','q','r','s','t','u','v','w',
                'x','y','z',
        };
        StringBuffer buffer = new StringBuffer();
        while (buffer.length()!=lenth){
            int ends = (int)(Math.random()*26);
            buffer.append(randomList[ends]);
        }

        return buffer.toString();
    }

    public static String randomString_09(int lenth){
        char [] randomList = new char[]{
                '1','2','3','4','5','6','7','8','9','0'
        };
        StringBuffer buffer = new StringBuffer();
        while (buffer.length()!=lenth){
            int ends = (int)(Math.random()*10);
            buffer.append(randomList[ends]);
        }

        return buffer.toString();
    }

    public static String getFromMap(String key, Map map){
        String value = String.valueOf(map.get(key));
        value = value.equals("null")? null:value.trim().length()==0? null:value.trim();
        return value;
    }

    /** 下划线转驼峰
     *       user_name  ---->  userName
     * house.user_name  ---->  userName
     *        userName   --->  userName
     * @param underlineName 带有下划线的名字
     * @return 驼峰字符串
     */
    public static String underlineToHump(String underlineName) {
        //截取下划线分成数组
        char[] charArray = underlineName.toCharArray();
        //判断上次循环的字符是否是"_"
        boolean underlineBefore = false;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0,l = charArray.length; i < l; i++) {
            //判断当前字符是否是"_",如果跳出本次循环
            if (charArray[i] == 95) {
                underlineBefore = true;
            } else if (underlineBefore) {
                //如果为true，代表上次的字符是"_",当前字符需要转成大写
                buffer.append(charArray[i] -= 32);
                underlineBefore = false;
            } else { //不是"_"后的字符就直接追加
                buffer.append(charArray[i]);
            }
        }
        return buffer.toString();
    }

    /** 驼峰转 下划线
     *       userName  ---->  user_name
     *       user_name  ---->  user_name
     * @param humpName  驼峰命名
     * @return  带下滑线的String
     */
    public static String humpToUnderline(String humpName) {
        //截取下划线分成数组，
        char[] charArray = humpName.toCharArray();
        StringBuffer buffer = new StringBuffer();
        //处理字符串
        for (int i = 0,l=charArray.length; i < l; i++) {
            if (charArray[i] >= 65 && charArray[i] <= 90) {
                buffer.append("_").append(charArray[i] += 32);
            }else {
                buffer.append(charArray[i]);
            }
        }
        return buffer.toString();
    }

    /**
     * 前缀补充
     * @param filler
     * @param length
     * @param target
     * @return
     */
    public static String prefixComplete(char filler,Integer length,Object target){
        return String.format("%"+filler+ length +"d",target);

    }

    /**
     * 替换占位符
     * @param target        被替换目标
     * @param strings       替换的参数
     * @return
     */
    public static String replace(String target,String[]...strings){
        Map<String,String> data = new HashMap<>();
        for(String[] strs : strings){
            data.put(strs[0],strs[1]);
        }
        StringSubstitutor sub = new StringSubstitutor(data);
        return sub.replace(target);
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String lowerFirstCase(String str){
        char[] chars = str.toCharArray();
        //首字母小写方法，大写会变成小写，如果小写首字母会消失
        chars[0] +=32;
        return String.valueOf(chars);
    }

    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String upperFirstCase(String str){
        char[] chars = str.toCharArray();
        //首字母小写方法，大写会变成小写，如果小写首字母会消失
        chars[0] -=32;
        return String.valueOf(chars);
    }

}
