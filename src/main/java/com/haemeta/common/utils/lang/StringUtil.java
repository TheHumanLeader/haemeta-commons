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

    /** ??????????????????
     *       user_name  ---->  userName
     * house.user_name  ---->  userName
     *        userName   --->  userName
     * @param underlineName ????????????????????????
     * @return ???????????????
     */
    public static String underlineToHump(String underlineName) {
        //???????????????????????????
        char[] charArray = underlineName.toCharArray();
        //????????????????????????????????????"_"
        boolean underlineBefore = false;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0,l = charArray.length; i < l; i++) {
            //???????????????????????????"_",????????????????????????
            if (charArray[i] == 95) {
                underlineBefore = true;
            } else if (underlineBefore) {
                //?????????true???????????????????????????"_",??????????????????????????????
                buffer.append(charArray[i] -= 32);
                underlineBefore = false;
            } else { //??????"_"???????????????????????????
                buffer.append(charArray[i]);
            }
        }
        return buffer.toString();
    }

    /** ????????? ?????????
     *       userName  ---->  user_name
     *       user_name  ---->  user_name
     * @param humpName  ????????????
     * @return  ???????????????String
     */
    public static String humpToUnderline(String humpName) {
        //??????????????????????????????
        char[] charArray = humpName.toCharArray();
        StringBuffer buffer = new StringBuffer();
        //???????????????
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
     * ????????????
     * @param filler
     * @param length
     * @param target
     * @return
     */
    public static String prefixComplete(char filler,Integer length,Object target){
        return String.format("%"+filler+ length +"d",target);

    }

    /**
     * ???????????????
     * @param target        ???????????????
     * @param strings       ???????????????
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
     * ???????????????
     * @param str
     * @return
     */
    public static String lowerFirstCase(String str){
        char[] chars = str.toCharArray();
        //??????????????????????????????????????????????????????????????????????????????
        chars[0] +=32;
        return String.valueOf(chars);
    }

    /**
     * ???????????????
     * @param str
     * @return
     */
    public static String upperFirstCase(String str){
        char[] chars = str.toCharArray();
        //??????????????????????????????????????????????????????????????????????????????
        chars[0] -=32;
        return String.valueOf(chars);
    }

}
