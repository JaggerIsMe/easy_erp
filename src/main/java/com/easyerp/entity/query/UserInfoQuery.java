package com.easyerp.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "查询UserInfo对象", description = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoQuery extends PageParam{
    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("姓名")
    private String name;
    private String nameFuzzy;

    @ApiModelProperty("手机")
    private String phone;
    private String phoneFuzzy;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("性别，0：女，1：男")
    private Integer sex;

    @ApiModelProperty("部门，0：开发部，1：测试部，2：需求部")
    private Integer department;

    @ApiModelProperty("职位，0：部门经理，1：小组组长，2：成员，3：普通员工，4：优秀员工")
    private String position;


}
