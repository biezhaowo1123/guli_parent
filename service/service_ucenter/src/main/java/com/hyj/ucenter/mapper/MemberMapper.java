package com.hyj.ucenter.mapper;

import com.hyj.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author test.java
 * @since 2023-02-04
 */
public interface MemberMapper extends BaseMapper<Member> {

	Integer selectRegisterCount(String day);
}
