package com.hyj.edu.service.impl;

import com.hyj.edu.entity.Comment;
import com.hyj.edu.mapper.CommentMapper;
import com.hyj.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2023-02-07
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
