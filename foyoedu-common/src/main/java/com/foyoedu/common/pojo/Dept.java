package com.foyoedu.common.pojo;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
//@Accessors(chain=true)
public class Dept implements Serializable {
    @ApiModelProperty(value = "部门id", allowEmptyValue = true)
    private Long 	deptno;
    @ApiModelProperty(value = "部门名称", allowEmptyValue = false)
    @NotBlank(message = "部门名称不能为空")
    private String 	dname;

    private String 	db_source;// 来自那个数据库，因为微服务架构可以一个服务对应一个数据库，同一个信息被存储到不同数据库

}
