package cn.cqray.springboot.api;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 接口编写相关配置
 * @author Cqray
 */
@Data
@Accessors(chain = true)
public class ApiConfig {

    /** Redis默认超时时间，需要useExpire为true **/
    private long redisExpireTime = 60 * 60 * 24;
    /** Redis默认不设置时间是否是无限时长 **/
    private boolean redisUseExpire = true;
    /** 是否修复API开发流问题 **/
    private boolean fixApiStream = true;
}
