package com.xw.miaosha.demo.controller;


import com.alibaba.druid.util.StringUtils;
import com.xw.miaosha.demo.error.BusinessException;
import com.xw.miaosha.demo.error.EmBusinessError;
import com.xw.miaosha.demo.model.UserInfo;
import com.xw.miaosha.demo.response.CommonReturnType;
import com.xw.miaosha.demo.service.UserService;
import com.xw.miaosha.demo.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


@RestController
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    /*单例  ThreadLocal形式*/
    private HttpServletRequest httpServletRequest;
    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam("telephone")String telephone,@RequestParam("password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
//        UserModel userModel = userService.getUserById (1);
        /*参数校验是否为空*/
        if(StringUtils.isEmpty (telephone)||StringUtils.isEmpty (password)){
            throw new BusinessException (EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        /*用户信息校验*/
        UserModel userModel = userService.validateLogin (telephone,this.encryptPassWord (password));
        /*登录成功后将用户状态信息保存至缓存*/
        this.httpServletRequest.getSession ().setAttribute ("IS_LOGIN",true);
        this.httpServletRequest.getSession ().setAttribute ("USER_INFO",userModel);
        return CommonReturnType.create ("登录成功！");
    }

    /*添加事务管理*/
    @Transactional
    @RequestMapping(value = "/register",method = RequestMethod.POST,consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam("telephone")String telephone,
                                     @RequestParam("otpCode")String otpCode,
                                     @RequestParam("name")String name,
                                     @RequestParam("gender")Byte gender,
                                     @RequestParam("age")Integer age,
                                     @RequestParam("password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if(StringUtils.isEmpty (telephone)||StringUtils.isEmpty (otpCode)||StringUtils.isEmpty (name)
                ||StringUtils.isEmpty (String.valueOf (gender))||StringUtils.isEmpty (String.valueOf (age))||
                StringUtils.isEmpty (password)){
            throw new BusinessException (EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        String originOtpCode = "123";
//        String originOtpCode = (String) this.httpServletRequest.getSession ().getAttribute (telephone);
        if(!StringUtils.equals (otpCode,originOtpCode)){
            throw new BusinessException (EmBusinessError.VERIFICATION_OTPCODE_ERROR);
        }
        /*将用户信息写入用户表*/
        UserModel userModel = new UserModel ();
        userModel.setTelephone (telephone);
        userModel.setName (name);
        userModel.setAge (age);
        userModel.setGender (gender);
        userModel.setEncryptedPassword (this.encryptPassWord (password));

        userService.register (userModel);


        return CommonReturnType.create ("注册成功");

    }

    private String encryptPassWord(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        /*使用 MD5 对密码加密*/
        MessageDigest md5 = MessageDigest.getInstance ("MD5");
        BASE64Encoder encoder = new BASE64Encoder ();
        String encryptPassword = encoder.encode (md5.digest (password.getBytes ("utf-8")));
        return encryptPassword;
    }

    private String generateOtpCode(int telephone){
        Random random = new Random ();
        int val = Math.abs (random.nextInt ()%10000);
        val = val<1000?val+1000:val;
        StringRedisTemplate template = new StringRedisTemplate ();
        template.opsForValue ().set (String.valueOf (telephone),String.valueOf (val));
        return null;
    }
}
