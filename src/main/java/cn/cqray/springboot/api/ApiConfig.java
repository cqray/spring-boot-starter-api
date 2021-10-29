package cn.cqray.springboot.api;

import lombok.Data;

/**
 * 接口编写相关配置
 * @author Cqray
 */
@Data
public class ApiConfig {

    /** Redis默认超时时间，需要useExpire为true **/
    private long redisExpireTime = 60 * 60 * 24;
    /** Redis默认不设置时间是否是无限时长 **/
    private boolean redisUseExpire = true;
    /** 是否使用RequestFilter **/
    private boolean useRequestFilter = true;
}
