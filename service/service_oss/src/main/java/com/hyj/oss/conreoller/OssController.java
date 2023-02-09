package com.hyj.oss.conreoller;

import com.hyj.commonutils.R;
import com.hyj.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/upload")
    public R uploadOssFile(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        System.out.println(url);
        return R.ok().data("url", url);
    }
}
