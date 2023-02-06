package com.haemeta.common.utils.file;


import java.io.*;

public class FileIOUtil {

    /**
     * 创建文件
     * @param path 完整文件夹路径(绝对路径，例: D:/test)
     * @param fullFileName        完整的文件名
     * @param isover              是否覆盖
     * @return 返回Null, 则已存在该文件，拒绝创建
     */
    public static Boolean createFile(String path, String fullFileName, Boolean isover) {
        isover = null != isover ? isover : false;//默认 fasle
        File file = new File(path);

        //判断path后是否跟了/或\\
        if ((path.lastIndexOf("/") != path.length()) || (path.lastIndexOf("\\") != path.length() - 1)) {
            path = path + "/";
        }

        //判断该文件夹是否存在
        if (!file.exists())
            file.mkdirs();

        FileWriter fw = null;
        try {
            fw = new FileWriter(path + fullFileName,isover);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            printlnError(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName()
                            + "；\t第" + Thread.currentThread().getStackTrace()[1].getLineNumber() + "行"
                    , "new FileWriter(fileName)时发生错误，fileName为" + path + fullFileName
                    , "检查路径值是否正确");
            return false;
        } finally {
            try {
                if (null != fw)
                    fw.close();
            } catch (IOException e) {
                printlnError(Thread.currentThread().getStackTrace()[1].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName()
                                + "；\t第" + Thread.currentThread().getStackTrace()[1].getLineNumber() + "行"
                        , "FileWriter IO流关闭失败"
                        , null);
                return false;
            }

        }

    }

    /**
     * 创建指定路径的文件夹
     * @param path 文件夹路径
     * @return
     */
    public static Boolean createFolder(String path){
        File file = new File(path);

        if(!(file.exists())){
            return file.mkdirs();
        }

        return false;
    }

    /**
     * 判断文件/文件夹是否存在
     * @param path 路径
     * @return
     */
    public static Boolean fileExists(String path){
        return new File(path).exists();
    }

    public static Boolean deleteFile(String path){
        File file = new File(path);
        if(!file.exists()){return false;}

        return file.delete();
    }

    public static Boolean deleteFile(File file){
        if(!file.exists()){return false;}

        return file.delete();
    }

    /**
     * 将IO输入到本地文件
     * @param input
     * @param path
     * @return
     */
    public static Boolean InputIO_TO_LocalFile(InputStream input,String path){

        FileOutputStream out = null;

        File folder = new File( new File(path).getParent());
        if(!(folder.exists())){
            folder.mkdirs();
        }

        try {
            out = new FileOutputStream(path);
            byte[]b=new byte[1024];
            //int常量用来接收位置
            int len;
            //开始循环读取字节，写入文件
            while((len=input.read(b))!=-1){
                out.write(b,0,len);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                if(null != out){
                    out.flush();
                    out.close();
                }
                if( null != input){
                    input.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;

    }


    private static void printlnError(String where, String why, String endway) {
        System.err.println("*-----------------------------------------------------------------------------------------*");
        if (where != null)
            System.err.println("*异常发生点\t：\t" + where);
        if (why != null)
            System.err.println("*异常原因\t：\t" + why);
        if (endway != null)
            System.err.println("*解决方案\t：\t" + endway);
        System.err.println("*-----------------------------------------------------------------------------------------*");
    }




}
