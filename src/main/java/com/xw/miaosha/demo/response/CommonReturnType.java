package com.xw.miaosha.demo.response;
import lombok.Data;

//@Data//lombok 自动生成get/set方法、toString()、equals()、无参构造方法
public class CommonReturnType {
    private String status;
    private Object data;
    public static CommonReturnType create(Object result){
        return CommonReturnType.create (result,"success");
    }

    public String getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static CommonReturnType create(Object result, String status){
        CommonReturnType type = new CommonReturnType ();
        type.setData (result);
        type.setStatus (status);
        return type;
    }
}
