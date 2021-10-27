package cn.cqray.springboot.response;

import lombok.Builder;
import lombok.Getter;

/**
 * ResponseData相关配置
 * @author Cqray
 */
@Getter
//@Builder
public class ResponseDataConfig {

    private String successCode;
    private String failureCode;
    private String successMessage;
    private String failureMessage;
    private String codeKey;
    private String dataKey;
    private String messageKey;
    private String pageKey;
    private boolean showNull;
    private boolean enable;
    private Class<?> [] excludeClasses;
    //private ResponseDataPageProvider pageProvider;

    public ResponseDataConfig() {
        successCode = "200";
        failureCode = "0";
        successMessage = "操作成功";
        failureMessage = "操作失败";
        codeKey = "code";
        dataKey = "data";
        messageKey = "message";
        pageKey = "page";
        showNull = true;
        enable = true;
        //pageProvider = null;
    }

//    public String getSuccessCode() {
//        return successCode;
//    }
//
//    public String getFailureCode() {
//        return failureCode;
//    }
//
//    public String getSuccessMessage() {
//        return successMessage;
//    }
//
//    public String getFailureMessage() {
//        return failureMessage;
//    }
//
//    public String getCodeKey() {
//        return codeKey;
//    }
//
//    public String getDataKey() {
//        return dataKey;
//    }
//
//    public String getMessageKey() {
//        return messageKey;
//    }
//
//    public boolean isShowNull() {
//        return showNull;
//    }
//
//    public boolean isEnable() {
//        return enable;
//    }
//
//    public Class<?>[] getExcludeClasses() {
//        return excludeClasses;
//    }

    public ResponseDataConfig successCode(String code) {
        successCode = code;
        return this;
    }

    public ResponseDataConfig failureCode(String code) {
        failureCode = code;
        return this;
    }

    public ResponseDataConfig successMessage(String message) {
        successMessage = message;
        return this;
    }

    public ResponseDataConfig failureMessage(String message) {
        failureMessage = message;
        return this;
    }

    public ResponseDataConfig codeKey(String key) {
        codeKey = key;
        return this;
    }

    public ResponseDataConfig dataKey(String key) {
        dataKey = key;
        return this;
    }

    public ResponseDataConfig messageKey(String key) {
        messageKey = key;
        return this;
    }

    public ResponseDataConfig showNull(boolean show) {
        showNull = show;
        return this;
    }

    public ResponseDataConfig enable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public ResponseDataConfig excludeClasses(Class<?> ...classes) {
        excludeClasses = classes;
        return this;
    }
}
