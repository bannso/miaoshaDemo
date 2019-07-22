package com.xw.miaosha.demo.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ValidatorResult {
    private boolean hasErrors = false;
    private Map<String,String> errorMsgMap = new HashMap<> ();

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    public String getErrMsg(){
        return StringUtils.join (errorMsgMap.values ().toArray (),",");
    }
}
