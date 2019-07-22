package com.xw.miaosha.demo.service;

import com.xw.miaosha.demo.error.BusinessException;
import com.xw.miaosha.demo.service.model.UserModel;


public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String telephone,String encryptedPassword) throws BusinessException;
}
