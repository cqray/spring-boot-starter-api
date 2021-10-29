package cn.cqray.springboot.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 数组类型Dto
 * @param <T>
 * @author Cqray
 */
@Data
public class ArrayDto<T> implements Serializable {

    @NotEmpty(message = "数组不能为空")
    @ApiModelProperty(value = "数组数据", required = true)
    private T[] array;
}
