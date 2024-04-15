package org.example.timecoinweb.controller;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.timecoinweb.service.RegisterService;
import org.example.timecoinweb.service.UserService;
import org.example.utils.AliOSSUtils;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

//用于显示当前用户自己的信息

@RestController
@Slf4j
@RequestMapping("/info")
public class SelfController {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private AliOSSUtils aliOSSUtils;
    @Autowired
    private UserService userService;

    /**
     * 查询用户个人信息
     * @param token
     * @return
     */
    @GetMapping
    public Result selectSelf(@RequestHeader("token")String token){

        //解析token
        Claims claims=JwtUtils.parseJWT(token);

        //找到id
        Integer id=(Integer) claims.get("id");;

        //利用service获取全部信息
        User user=registerService.selectSelf(id);

        return Result.success(user);
    }


    /**
     * 上传到阿里云OSS，并生成url地址
     * 前端用update更新
     * @param image
     * @return
     * @throws IOException
     */
//    //设置头像,上传到阿里云OSS
//    @PostMapping
//    public Result setPhoto(MultipartFile image) throws IOException {
//        log.info("文件上传，文件名:{}",image.getOriginalFilename());
//
//        //调用阿里云OSS工具类进行文件上传
//        String url=aliOSSUtils.upload(image);
//        log.info("访问的url为:{}",url);
//
//        return Result.success(url);
//    }


    @PostMapping
    public  Result setPhoto(@RequestParam("image") MultipartFile image,@RequestHeader("token") String token) throws IOException{
        //解析token
        Claims claims=JwtUtils.parseJWT(token);
        //找到id
        Integer id=(Integer) claims.get("id");;

        log.info("文件上传：userId为{}，图片为{}",id,image);

        //获取原始文件名
        String originalFilename=image.getName();
        int index=originalFilename.lastIndexOf(".");
        String extname=originalFilename.substring(index);//获取后缀名
        log.info("获取的新文件名：{}",id+extname);

        //将文件存储在服务器的磁盘目录中D:\java\image
        image.transferTo(new File("D:\\java\\image\\"+id+extname));
        String local=id+extname;

        //存图片名到数据库
        userService.updateImage(local,id);

        return Result.success(local);
    }

}
