package com.xw.miaosha.demo.service.impl;

import com.xw.miaosha.demo.dao.UserInfoMapper;
import com.xw.miaosha.demo.dao.UserPasswordMapper;
import com.xw.miaosha.demo.error.BusinessException;
import com.xw.miaosha.demo.error.EmBusinessError;
import com.xw.miaosha.demo.model.UserInfo;
import com.xw.miaosha.demo.service.model.UserModel;
import com.xw.miaosha.demo.model.UserPassword;
import com.xw.miaosha.demo.service.UserService;
import com.xw.miaosha.demo.validator.ValidatorImpl;
import com.xw.miaosha.demo.validator.ValidatorResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;
    @Autowired(required = false)
    private UserPasswordMapper userPasswordMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey (id);
        if(userInfo==null) return null;
        UserPassword userPassword = userPasswordMapper.selectByUserId (userInfo.getId ());
        return convertFromDataObject(userInfo,userPassword);
    }


    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if(userModel==null) throw new BusinessException (EmBusinessError.PARAMETER_VALIDATION_ERROR);
        ValidatorResult result = validator.validate (userModel);
        if(result.isHasErrors ()) throw new BusinessException (EmBusinessError.PARAMETER_VALIDATION_ERROR);
        UserInfo user = generateUserInfoFromUserModel(userModel);
        try {
            userInfoMapper.insertSelective (user);
        } catch (Exception e) {
            throw new BusinessException (EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已被注册");
        }
        userModel.setId (user.getId ());
        UserPassword userPassword = generatePasswordFromUserModel(userModel);
        userPasswordMapper.insertSelective (userPassword);
    }



    @Override
    public UserModel validateLogin(String telephone, String encryptedPassword) throws BusinessException {
        UserInfo userInfo = userInfoMapper.selectByTelephone (telephone);
        if(userInfo==null) throw new BusinessException (EmBusinessError.USER_LOGIN_FAILED);
        UserPassword userPassword = userPasswordMapper.selectByUserId (userInfo.getId ());
        if(!encryptedPassword.equals (userPassword.getEncrptPassword ())){
            throw new BusinessException (EmBusinessError.USER_LOGIN_FAILED);
        }else {
            UserModel userModel = convertFromDataObject (userInfo,userPassword);
            return userModel;
        }
    }

    private UserModel convertFromDataObject(UserInfo userInfo, UserPassword userPassword) {
        if(userInfo==null) return null;
        UserModel userModel = new UserModel ();
        BeanUtils.copyProperties (userInfo,userModel);
//        int b = 1/0;
        userModel.setEncryptedPassword (userPassword.getEncrptPassword ());
        return userModel;
    }

    private UserInfo generateUserInfoFromUserModel(UserModel userModel) {
        UserInfo userInfo = new UserInfo ();
        BeanUtils.copyProperties (userModel,userInfo);
        return userInfo;
    }

    private UserPassword generatePasswordFromUserModel(UserModel userModel) {
        UserPassword userPassword = new UserPassword ();
        BeanUtils.copyProperties (userModel,userPassword);
        userPassword.setUserId (userModel.getId ());
        return userPassword;
    }
}
