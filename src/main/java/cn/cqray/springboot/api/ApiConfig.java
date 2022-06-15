package cn.cqray.springboot.api;

import cn.cqray.springboot.api.response.ResponsePageProvider;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 接口编写相关配置
 * @author Cqray
 */
@Data
@Builder
@Accessors(chain = true)
public class ApiConfig {
    
    /** 调试模式 **/
    @Builder.Default
    private boolean debug = false;
    /** 是否修复API开发流问题 **/
    @Builder.Default
    private boolean fixApiStream = true;
    /** 成功代码 **/
    @Builder.Default
    private String successCode = "200";
    /** 失败代码 **/
    @Builder.Default
    private String failureCode = "0";
    /** 成功提示 **/
    @Builder.Default
    private String successMessage = "操作成功";
    /** 失败提示 **/
    @Builder.Default
    private String failureMessage = "操作失败";
    /** 代码键名 **/
    @Builder.Default
    private String codeKey = "code";
    /** 数据键名 **/
    @Builder.Default
    private String dataKey = "data";
    /** 消息键名 **/
    @Builder.Default
    private String messageKey = "message";
    /** 分页信息键名 **/
    @Builder.Default
    private String pageKey = "page";
    /** 是否返回值为NULL的键 **/
    @Builder.Default
    private boolean responseShowNull = false;
    /** 是否启用响应体Advice功能 **/
    @Builder.Default
    private boolean responseAdviceEnable = true;
    /** 不使用默认返回体包裹的类 **/
    private Class<?> [] responseExcludeClasses;
    /** 分页数据提供器 **/
    private ResponsePageProvider pageProvider;
}
