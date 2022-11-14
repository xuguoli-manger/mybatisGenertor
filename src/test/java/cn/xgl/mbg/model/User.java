package cn.xgl.mbg.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
* Created by Mybatis Generator 2022/11/14 16
*/
@Data
@ApiModel(value ="用户信息表")
public class User {
    @ApiModelProperty(value ="用户id")
    private String userid;

    @ApiModelProperty(value ="角色id")
    private String roleid;

    @ApiModelProperty(value ="用户名")
    private String username;

    @ApiModelProperty(value ="电话")
    private Long phone;

    @ApiModelProperty(value ="邮箱")
    private String email;
}