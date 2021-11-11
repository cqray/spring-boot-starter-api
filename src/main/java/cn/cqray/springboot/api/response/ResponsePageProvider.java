package cn.cqray.springboot.api.response;

import java.util.List;

/**
 * 分页信息提供器
 * @author Cqray
 */
public interface ResponsePageProvider {

    /**
     * 根据列表获取分页数据
     * @param data 列表数据
     * @return 分页信息
     */
    ResponsePage getPage(List<?> data);
}
