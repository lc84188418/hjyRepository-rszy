package com.hjy.cloud.utils.file;

import com.hjy.cloud.utils.IDUtils;
import com.hjy.cloud.utils.PropertiesUtil;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传工具类
 */
public  class FileUtil {

    /**
     * 判断是否为视频格式
     * @param file
     * @return
     */
    public static boolean isVideoFile(MultipartFile file){
        if(file !=null){
            //文件全名
            String fileName = file.getOriginalFilename();
            //文件名后缀，即文件类型
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String[] s ={".avi",".baimov",".rmvb",".rm",".flv",".mp4",".3gp",".mpeg",".asf",".wmv",".navi",".f4v",".mpg",".dat"};
            if(ArrayUtils.contains(s,suffixName.toLowerCase())){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    /**
     * 判断是否为文档格式
     * @param file
     * @return
     */
    public static boolean isDocFile(MultipartFile file){
        if(file !=null){
            //文件全名
            String fileName = file.getOriginalFilename();
            //文件名后缀，即文件类型
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String[] s ={".doc",".docx",".ppt",".pptx",".xls",".xlsx"};
            if(ArrayUtils.contains(s,suffixName.toLowerCase())){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
    /**
     * 判断是否为图片格式
     * @param file
     * @return
     */
    public static boolean isPicFile(MultipartFile file){
        if(file !=null){
            //文件全名
            String fileName = file.getOriginalFilename();
            //文件名后缀，即文件类型
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String[] s ={".jpg",".png",".jpeg",".gif"};
            if(ArrayUtils.contains(s,suffixName.toLowerCase())){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    /**
     * 批量上传文件
     * @param files
     * @param username
     * @return
     */
    public static Map<String,Object> fileBatchUpload(MultipartFile[] files, String username,String url){
        Map<String,Object> pathMap = new HashMap<String,Object>();
        if(files!=null && files.length>0){
            for(int i = 0;i < files.length; i++){
                MultipartFile file = files[i];
                if(!file.isEmpty()){
                    String filePath = fileUpload(file,username);
                    pathMap.put("path"+i,url+filePath);
                }
            }
        }
        return pathMap;
    }

    /**
     * 单文件上传
     * @param file
     * @param username
     * @return
     */
    public static String fileUpload(MultipartFile file,String username){
        String upLoadPath = "";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            upLoadPath = PropertiesUtil.getValue("file.upload.path.win");
        } else {
            upLoadPath = PropertiesUtil.getValue("file.upload.path.other");
        }
        String fileName = file.getOriginalFilename();
        //文件名后缀，即文件类型
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //获取时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String time = sdf.format(date.getTime());
        time = time +"/"+suffixName.replace(".","");
        //1.创建目录
        StringBuffer dirpath = new StringBuffer(time);
        dirpath.insert(0,upLoadPath);
        File targetFile = new File(dirpath.toString());
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        File saveFile = new File(targetFile, username+IDUtils.getUUID()+suffixName.toLowerCase());
        try {
            file.transferTo(saveFile);
        } catch (Exception e) {
        }
        return saveFile.getPath().replace(upLoadPath,"/upload/");
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
    public static String universalFileUpload(MultipartFile file, String shijiancuo, String qianzhui) {
        StringBuffer dbfilePathsb = new StringBuffer();
        if (!file.isEmpty()) {
            //获取时间
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
            String time = sdf.format(date.getTime());
            String times[] = time.split("_");
            //拿到的该文件名
            String fileName = file.getOriginalFilename();
            //文件名后缀，即文件类型
            String suffixName = file.getOriginalFilename().substring(fileName.lastIndexOf("."));
            //重命名文件名
            dbfilePathsb.append(qianzhui + "_" + times[0] + "_" + times[1] + "_" + times[2] + "_" + shijiancuo + suffixName);
            //调用文件上传工具类
            String filePath = universalFileSave(dbfilePathsb.toString(), file);
        }
        //为文件1路径及其名称
        return dbfilePathsb.toString();
    }

    //文件保存工具
    public static String universalFileSave(String fileName, MultipartFile file) {
        StringBuffer dirpath = new StringBuffer();
        String filePath = null;
        if(fileName.contains("icon")){
            dirpath.insert(0, "D:/hjy/rszy/picture_file/icon/");
            filePath = "D:/hjy/rszy/picture_file/icon/";
        }else {
            dirpath.insert(0, "D:/hjy/rszy/picture_file/other/");
            filePath = "D:/hjy/rszy/picture_file/other/";
        }
        File targetFile = new File(dirpath.toString());
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        File saveFile = new File(targetFile, fileName);
        try {
            file.transferTo(saveFile);
        } catch (Exception e) {
        }
        return filePath;
    }

}
