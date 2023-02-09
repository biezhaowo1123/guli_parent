package com.hyj.commonutils.vo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author test.java
 * @since 2023-02-04
 */
@Data


public class UcenterMember implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "会员id")
	private String id;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "用户头像")
	private String avatar;

}

