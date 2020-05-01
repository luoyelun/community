package com.community.controller;

import com.community.dto.FileDTO;
import com.community.provider.QiNiuProvider;
import com.qiniu.storage.model.DefaultPutRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * @author luoyelun
 * @date 2020/5/1 13:18
 */
@Controller
public class FileController {
    @Autowired
    QiNiuProvider qiNiuProvider;


    @ResponseBody
    @RequestMapping("/file/upload")
    public FileDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        FileDTO fileDTO = new FileDTO();
        try {
            DefaultPutRet upload = qiNiuProvider.upload(Objects.requireNonNull(file).getInputStream(), file.getOriginalFilename(), file.getContentType());
            fileDTO.setSuccess(1);
            fileDTO.setUrl("http://q9l0owvom.bkt.clouddn.com/" + upload.key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileDTO;
    }
}
