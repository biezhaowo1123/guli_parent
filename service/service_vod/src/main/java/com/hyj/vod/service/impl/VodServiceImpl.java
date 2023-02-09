package com.hyj.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.hyj.servicebase.handler.GuliException;
import com.hyj.vod.service.VodService;
import com.hyj.vod.utils.AliYunVodUtils;
import com.hyj.vod.utils.ConstantPropertiesUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
	@Override
	public String uploadVideo(MultipartFile file) {
		try {
			InputStream inputStream = file.getInputStream();
			String originalFilename = file.getOriginalFilename();
			String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
			UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET, title, originalFilename, inputStream);
			UploadVideoImpl uploader = new UploadVideoImpl();
			UploadStreamResponse response = uploader.uploadStream(request);
			String videoId = response.getVideoId();
			return videoId;
		} catch (IOException e) {
			throw new GuliException(20001, "服务上传失败");
		}

	}

	@Override
	public void removeVideo(String videoId) {
		try{
			DefaultAcsClient client = AliYunVodUtils.initVodClient(
					ConstantPropertiesUtils.ACCESS_KEY_ID,
					ConstantPropertiesUtils.ACCESS_KEY_SECRET);
			DeleteVideoRequest request = new DeleteVideoRequest();
			request.setVideoIds(videoId);
			DeleteVideoResponse response = client.getAcsResponse(request);
			System.out.print("RequestId = " + response.getRequestId() + "\n");
		}catch (ClientException e){
			throw new GuliException(20001, "视频删除失败");
		}
	}

	@Override
	public void removeMoreVideos(List<String> videolist) {
		try{
			DefaultAcsClient client = AliYunVodUtils.initVodClient(
					ConstantPropertiesUtils.ACCESS_KEY_ID,
					ConstantPropertiesUtils.ACCESS_KEY_SECRET);
			DeleteVideoRequest request = new DeleteVideoRequest();
			String ids = StringUtils.join(videolist.toArray(), ",");
			request.setVideoIds(ids);
			DeleteVideoResponse response = client.getAcsResponse(request);
		}catch (ClientException e){
			throw new GuliException(20001, "视频删除失败");
		}
	}
}
