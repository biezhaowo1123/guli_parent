package com.hyj.ucenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class LoginVo implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "手机号")
	private String mobile;

	@ApiModelProperty(value = "密码")
	private String password;
}
