package cn.cqray.springboot.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 分页类型Dto
 * @author Cqray
 */
@Data
public class PageDto implements Serializable {

    //@ApiModelProperty("页码")
    private Integer pageNum = 1;
    //@ApiModelProperty("分页大小")
    private Integer pageSize = 20;
    //@ApiModelProperty("是否分页")
    private boolean pageable = true;
    //@ApiModelProperty("搜索关键字")
    private String search;
}
