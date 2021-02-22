package com.hjy.cloud.utils.file;

import com.hjy.cloud.utils.PropertiesUtil;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public  class MyFileUtil {

    //判断是否为图片文件
    public static boolean PictureFileUtil(MultipartFile file){
        if(file !=null){
            //文件全名
            String fileName = file.getOriginalFilename();
            //文件名后缀，即文件类型
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            if(suffixName.equals(".jpg")){
                return true;
            }else {
               return false;
            }
        }
        return false;
    }

    /**
     * 文件保存逻辑处理
     * 无法上传大型文件
     * @param username
     * @param files
     * @return
     */
    public static String[] FileUtil(String username, MultipartFile[] files){
        //文件保存路径
        //获取时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
        String time = sdf.format(date.getTime());
        String times[] = time.split("_");
        //1.创建目录
        StringBuffer dbfilePathsb = new StringBuffer(times[0] + "/" + times[1] + "/" + times[2] + "/"+username+"/");
        String fileDir = PropertiesUtil.getValue("upload.file.directory");
        dbfilePathsb.insert(0,fileDir);
        //所有文件名
        StringBuffer dbfilePathsb2 = new StringBuffer();
        String[]strings = new String[2];
        if(files!=null && files.length>0){
            for(int i = 0,j = 0,k = 0;i < files.length; i++){
                MultipartFile file = files[i];
                if(!file.isEmpty()){
                    //拿到的该文件名
                    String fileName = file.getOriginalFilename();
                    //文件名后缀，即文件类型
                    String suffixName = file.getOriginalFilename().substring(fileName.lastIndexOf("."));
                    k++;
                    if(k>0){
                        if(k==1){
                            dbfilePathsb2.append(fileName);
                        }else {
                            dbfilePathsb2.append("+"+fileName);
                        }
                    }
                    //调用文件上传工具类
                    String filePath = FileUpload(dbfilePathsb.toString(),fileName,file);
                    System.err.println(filePath);
//                    dbfilePathsb.insert(0,filePath);
                }
            }
        }
        //为文件1路径及其名称
        strings[0] =dbfilePathsb.toString();
        System.err.println("path1:"+dbfilePathsb.toString());
        //为文件2路径及其名称
        strings[1] =dbfilePathsb2.toString();
        System.err.println("path2:"+dbfilePathsb2.toString());
        return strings;
    }
    //文件保存工具
    public static String FileUpload(String dirPath,String fileName, MultipartFile file){
        File targetFile = new File(dirPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        File saveFile = new File(targetFile, fileName);
        try {
            file.transferTo(saveFile);
        } catch (Exception e) {
        }
        String filePath = dirPath+fileName;
        return filePath;
    }

}
