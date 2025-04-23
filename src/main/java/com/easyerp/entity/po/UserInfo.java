package com.easyerp.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.easyerp.entity.constants.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Jagger
 * @since 2025-04-11 12:52:27
 */
@Accessors(chain = true)
@TableName("user_info")
@ApiModel(value = "UserInfo对象", description = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 6130585599180315386L;

    @ApiModelProperty("用户id")
    @TableId(value = "user_id", type = IdType.AUTO)
    private String userId;

    @ApiModelProperty("姓名")
    @TableField("name")
    @NotNull(message = "姓名不能为空")
    @Size(max = 10, min = 1, message = "姓名长度不能超过10")
    private String name;

    @ApiModelProperty("手机号")
    @TableField("phone")
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = Constants.REGEX_PHONE, message = "手机号格式不正确")
    private String phone;

    @ApiModelProperty("密码")
    @TableField("password")
    @NotNull(message = "密码不能为空")
    @Pattern(regexp = Constants.REGEX_PASSWORD, message = "密码只能是数字，字母，特殊字符 8-18位")
    @JsonIgnore
    private String password;

    @ApiModelProperty("生日")
    @TableField("birthday")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "生日不能为空")
    private Date birthday;

    @ApiModelProperty("性别，0：女，1：男")
    @TableField("sex")
    @NotNull(message = "性别不能为空")
    private Integer sex;

    @ApiModelProperty("部门，0：开发部，1：测试部，2：需求部")
    @TableField("department")
    @NotNull(message = "部门不能为空")
    private Integer department;

    @ApiModelProperty("职位，0：部门经理，1：小组组长，2：普通成员，3：优秀员工")
    @TableField("position")
    @NotNull(message = "职位不能为空")
    private String position;

    @ApiModelProperty("入职年月")
    @TableField("join_time")
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date joinTime;

}
