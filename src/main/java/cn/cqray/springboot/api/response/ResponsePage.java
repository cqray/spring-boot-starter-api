package cn.cqray.springboot.api.response;

import lombok.Data;

/**
 * 响应体分页数据
 * @author Cqray
 */
@Data
public class ResponsePage {
    /** 当前页码 **/
    private int pageNum;
    /** 分页数量 **/
    private int pageSize;
    /** 当前页的数量 **/
    private int size;
    /** 总纪录数 **/
    private int total;
    /** 总页数 **/
    private int pages;
    /** 前一页 **/
    private int prePage;
    /** 下一页 **/
    private int nextPage;
    /** 是否为第一页 **/
    private boolean isFirstPage;
    /** 是否为最后一页 **/
    private boolean isLastPage;
    /** 是否有前一页 **/
    private boolean hasPreviousPage;
    /** 是否有下一页 **/
    private boolean hasNextPage;
}
