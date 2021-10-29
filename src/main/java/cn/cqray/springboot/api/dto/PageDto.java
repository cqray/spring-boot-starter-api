package cn.cqray.springboot.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页类型Dto
 * @author Cqray
 */
@Data
public class PageDto implements Serializable {

    @ApiModelProperty("分页页码")
    private Integer pageNum;
    @ApiModelProperty("分页大小")
    private Integer pageSize;
    @ApiModelProperty("是否分页")
    private boolean pageable;
    @ApiModelProperty("搜索关键字")
    private String search;
}
