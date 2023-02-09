package com.hyj.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.commonutils.JWTUtils;
import com.hyj.commonutils.R;
import com.hyj.commonutils.vo.UcenterMember;
import com.hyj.edu.client.UcenterClient;
import com.hyj.edu.entity.Comment;
import com.hyj.edu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/comment")
public class CommentFrontController {
	@Autowired
	private CommentService commentService;

	@Autowired
	private UcenterClient ucenterClient;

	@PostMapping("/saveComment")
	public R saveComment(@RequestBody Comment comment, HttpServletRequest request) {
		String memberId = JWTUtils.getMemberIdByJwtToken(request);
		if(StringUtils.isEmpty(memberId)) {
			return R.error().code(28004).message("请登录");
		}
		comment.setMemberId(memberId);
		UcenterMember ucenterInfo = ucenterClient.getMember(memberId);
		comment.setNickname(ucenterInfo.getNickname());
		comment.setAvatar(ucenterInfo.getAvatar());
		commentService.save(comment);
		return R.ok();
	}

	@GetMapping("/{page}/{limit}")
	public R getPageComment(@PathVariable long page, @PathVariable long limit, String courseId) {
		Page<Comment> pageParam = new Page<>(page, limit);
		QueryWrapper<Comment> wrapper = new QueryWrapper<>();
		wrapper.eq("course_id",courseId);
		commentService.page(pageParam,wrapper);
		List<Comment> commentList = pageParam.getRecords();
		Map<String, Object> map = new HashMap<>();
		map.put("items", commentList);
		map.put("current", pageParam.getCurrent());
		map.put("pages", pageParam.getPages());
		map.put("size", pageParam.getSize());
		map.put("total", pageParam.getTotal());
		map.put("hasNext", pageParam.hasNext());
		map.put("hasPrevious", pageParam.hasPrevious());
		map.put("count", commentService.count(new QueryWrapper<Comment>().eq("course_id", courseId)));
		return R.ok().data(map);
	}
}
