package cn.cqray.springboot.api;

import cn.cqray.springboot.api.response.ResponsePageProvider;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 接口编写相关配置
 * @author Cqray
 */
@Data
@Accessors(chain = true)
public class ApiConfig {

    /** 是否修复API开发流问题 **/
    private boolean fixApiStream = true;
    /** 成功代码 **/
    private String successCode = "200";
    /** 失败代码 **/
    private String failureCode = "0";
    /** 成功提示 **/
    private String successMessage = "操作成功";
    /** 失败提示 **/
    private String failureMessage = "操作失败";
    /** 代码键名 **/
    private String codeKey = "code";
    /** 数据键名 **/
    private String dataKey = "data";
    /** 消息键名 **/
    private String messageKey = "message";
    /** 分页信息键名 **/
    private String pageKey = "page";
    /** 是否返回值为NULL的键 **/
    private boolean responseShowNull = false;
    /** 是否启用响应体Advice功能 **/
    private boolean responseAdviceEnable = true;
    /** 不使用默认返回体包裹的类 **/
    private Class<?> [] responseExcludeClasses;
    /** 分页数据提供器 **/
    private ResponsePageProvider pageProvider;
}
