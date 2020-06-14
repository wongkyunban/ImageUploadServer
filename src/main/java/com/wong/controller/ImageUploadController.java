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
import java.util.UUID;

@RestController
public class ImageUploadController {

    private Logger logger = LoggerFactory.getLogger(ImageUploadController.class);


    /**
     * 单文件上传
     * @param file
     * @param model
     * @param request
     * @return
     */
    @PostMapping("/api/upload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }

        // BMP、JPG、JPEG、PNG、GIF
        String fileName = file.getOriginalFilename();  // 文件名
        logger.info("上传文件名：",fileName);
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        // 验证上传的文件是否图片
        if(!".bmp".equalsIgnoreCase(suffixName) && !".jpg".equalsIgnoreCase(suffixName)
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
    public String hello(){
        return "Hello World!";
    }
}

