package com.chunhe.custom.framework.service;

import com.chunhe.custom.framework.exception.RFException;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by qiumy on 2017/6/13.
 */
@Service
@PropertySource(value = {"classpath:config/config.properties"})
public class UploadService {

    @Value("${localTarget}")
    private String localTarget;

    @Value("${localUrl}")
    private String localUrl;

    /**
     * 文件上传-本地上传函数
     *
     * @param request
     * @param file
     * @param fileDir
     * @return
     * @throws IOException
     */
    public String uploadFile(HttpServletRequest request, MultipartFile file, String fileDir) {
        long size = file.getSize();
        if (size > 10 * 1024 * 1024) {
            throw new RFException("文件大于10M，请先压缩");
        }
        String realPath;
        if (localTarget.equals("")) {
            realPath = request.getSession().getServletContext().getRealPath(fileDir);
        } else {
            realPath = localTarget + fileDir;
        }
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int rannum = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        String fileName = time + "_" + rannum + "_" + file.getOriginalFilename();
        //本地存放的路径
        String localFilePath = realPath + "/" + fileName;

        //判断文件保存是否存在
        File checkFile = new File(localFilePath);
        if (checkFile.getParentFile() != null || !checkFile.getParentFile().isDirectory()) {
            //创建文件
            checkFile.getParentFile().mkdirs();
        }
        //判断资源类型，属于图片的进行尺寸不变，压缩大小
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (suffix.equalsIgnoreCase("jpg")
                || suffix.equalsIgnoreCase("jpeg")
                || suffix.equalsIgnoreCase("png")) {
            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;
            try {
                inputStream = file.getInputStream();
                fileOutputStream = new FileOutputStream(checkFile);
                //写出文件
                byte[] buffer = new byte[2048];
                IOUtils.copyLarge(inputStream, fileOutputStream, buffer);
                buffer = null;
            } catch (IOException e) {
                System.out.println(e.toString().concat("error:1"));
                throw new RFException("上传异常，请重试");
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                } catch (IOException e) {
                    System.out.println(e.toString().concat("error:2"));
                    throw new RFException("上传异常，请重试");
                }
            }

            try {
                if (size >= 1024 * 1024) {
                    Thumbnails.of(file.getInputStream()).scale(1f).outputQuality((1024 * 1024f) / size).outputFormat("jpg").toFile(localFilePath);
                } else {
                    Thumbnails.of(file.getInputStream()).scale(1f).outputFormat("jpg").toFile(localFilePath);
                }
            } catch (Exception e) {
                System.out.println(e.toString().concat("error:3"));
                throw new RFException("上传异常，请重试");
            }
        } else {
            File saveFile = new File(realPath, fileName);
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return localUrl + "/" + fileDir + "/" + fileName;
    }

}
