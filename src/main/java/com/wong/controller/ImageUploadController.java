package com.wong.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

@RestController
public class ImageUploadController {

    private Logger logger = LoggerFactory.getLogger(ImageUploadController.class);


    /**
     * 单文件上传
     *
     * @param file
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/api/upload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, Model model, HttpServletRequest request, @RequestParam(value = "type") int type, @RequestHeader(value = "user-agent") String userAgent) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }

        logger.info("获得的其他参数type=" + type);

        logger.info("获得的Header user-agent=" + userAgent.toString());

        // 如果参数比较少可以直接在方法上使用注解@RequestParam来映射到不同的名称上获得，当然如果不用此注解，也可以定义一个与传过来的参数名一样的形参来获得
        // 蒜从客户端传过来的其他参数
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement().toString();
            logger.info("客户端传过来的参数＃key＝" + key + ",value=" + request.getParameterValues(key).toString());
        }
        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String key = headers.nextElement().toString();
            String info = "客户端传过来的Header参数:key＝" + key + ",value=" + request.getHeader(key);
            logger.info(info);
        }

        // BMP、JPG、JPEG、PNG、GIF
        String fileName = file.getOriginalFilename();  // 文件名
        logger.info("上传文件名：" + fileName);
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        // 验证上传的文件是否图片
        if (!".bmp".equalsIgnoreCase(suffixName) && !".jpg".equalsIgnoreCase(suffixName)
                && !".jpeg".equalsIgnoreCase(suffixName)
                && !".png".equalsIgnoreCase(suffixName)
                && !".gif".equalsIgnoreCase(suffixName)) {
            return "上传失败，请选择BMP、JPG、JPEG、PNG、GIF文件！";
        }

        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(fileName);
        // 如果文件的父路径不存在，则创建
        if (fileName.startsWith("/") && !dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        // 开始存放文件到指定目录去
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";

        }
    }


    /**
     * 多文件上传
     *
     * @param files
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/api/multi_upload")
    public String multiFileUpload(@RequestParam(value = "images") MultipartFile[] files, Model model, HttpServletRequest request, @RequestParam(value = "type") int[] type, @RequestHeader(value = "user-agent") String userAgent) {
        if (files != null && files.length > 0) {
            System.out.println("文件为空空");
        }

        for (int i : type) {
            logger.info("获得的其他参数type=" + i);
        }

        logger.info("获得的Header user-agent=" + userAgent);


        // 如果参数比较少可以直接在方法上使用注解@RequestParam来映射到不同的名称上获得，当然如果不用此注解，也可以定义一个与传过来的参数名一样的形参来获得
        // 蒜从客户端传过来的其他参数
        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement().toString();
            String[] values = request.getParameterValues(key);
            for(String str: values){
                String info = "客户端传过来的参数:key＝" + key + ",value=" + str;
                logger.info(info);
            }

        }

        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String key = headers.nextElement().toString();
            String info = "客户端传过来的Header参数:key＝" + key + ",value=" + request.getHeader(key);
            logger.info(info);
        }


        for (MultipartFile file : files) {
            saveFile(file);
        }
        return "上传成功";
    }

    private String saveFile(MultipartFile file) {
        // BMP、JPG、JPEG、PNG、GIF
        String fileName = file.getOriginalFilename();  // 文件名
        logger.info("上传文件名：" + fileName);
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        // 验证上传的文件是否图片
        if (!".bmp".equalsIgnoreCase(suffixName) && !".jpg".equalsIgnoreCase(suffixName)
                && !".jpeg".equalsIgnoreCase(suffixName)
                && !".png".equalsIgnoreCase(suffixName)
                && !".gif".equalsIgnoreCase(suffixName)) {
            return "上传失败，请选择BMP、JPG、JPEG、PNG、GIF文件！";
        }

        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(fileName);
        // 如果文件的父路径不存在，则创建
        if (fileName.startsWith("/") && !dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        // 开始存放文件到指定目录去
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";

        }
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello World!";
    }
}

