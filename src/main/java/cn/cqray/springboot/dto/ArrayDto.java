package cn.cqray.springboot.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 数组类型Dto
 * @param <T>
 * @author Cqray
 */
@Data
public class ArrayDto<T> implements Serializable {

    @NotNull(message = "数组不能为空")
    //@ApiModelProperty(value = "数组数据", required = true)
    private T[] array;
}
