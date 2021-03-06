package cn.cqray.springboot.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 列表类型Dto
 * @param <T>
 * @author Cqray
 */
@Data
public class ListDto<T> implements Serializable {

    @NotEmpty(message = "列表不能为空")
    @ApiModelProperty(value = "列表数据", required = true)
    private List<T> list;
}
