package org.example.timecoinweb.exception;

import org.example.pojo.Result;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

<<<<<<< HEAD
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public Result handleDataIntegrityViolationException(DataIntegrityViolationException ex){
//        ex.printStackTrace();
//
//        return Result.error("用户名已存在");
//    }
=======
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        ex.printStackTrace();

        return Result.error("用户名已存在");
    }
>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f


    @ExceptionHandler(Exception.class)//捕获所有异常
    public Result ex(Exception ex){
        ex.printStackTrace();
        return Result.error("对不起操作失败，请联系管理员");
    }
}
