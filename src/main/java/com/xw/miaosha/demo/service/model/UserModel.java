package com.xw.miaosha.demo.service.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserModel {
    private Integer id;
    @NotBlank(message = "姓名不能为空")
    private String name;
    @NotNull(message = "性别不能为空")
    private Byte gender;
    @NotNull(message = "年龄不能为空")
    private Integer age;
    @NotBlank(message = "手机号不能为空")
    private String telephone;
    private String registerMode;
    private String thirdPartyId;
    @NotBlank(message = "密码不能为空")
    private String encryptedPassword;

//    public String getName() {
//        return name;
//    }
//
//    public String getEncryptedPassword() {
//        return encryptedPassword;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Byte getGender() {
//        return gender;
//    }
//
//    public void setGender(Byte gender) {
//        this.gender = gender;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    public String getTelephone() {
//        return telephone;
//    }
//
//    public void setTelephone(String telephone) {
//        this.telephone = telephone;
//    }
//
//    public String getRegisterMode() {
//        return registerMode;
//    }
//
//    public void setRegisterMode(String registerMode) {
//        this.registerMode = registerMode;
//    }
//
//    public String getThirdPartyId() {
//        return thirdPartyId;
//    }
//
//    public void setThirdPartyId(String thirdPartyId) {
//        this.thirdPartyId = thirdPartyId;
//    }
//
//    public void setEncryptedPassword(String encryptedPassword) {
//        this.encryptedPassword = encryptedPassword;
//    }
}
