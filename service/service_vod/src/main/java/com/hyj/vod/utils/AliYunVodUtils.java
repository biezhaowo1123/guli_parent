package com.hyj.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class AliYunVodUtils {
	public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
		String regionId = "cn-shanghai";  // 点播服务接入地域
		DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
		DefaultAcsClient client = new DefaultAcsClient(profile);
		return client;
	}

	public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
		GetPlayInfoRequest request = new GetPlayInfoRequest();
		request.setVideoId("7dd461d0a13671ed80706732b68e0102");
		return client.getAcsResponse(request);
	}

	public static void main(String[] args) throws ClientException {
		getVideoPlayAuth();
	}

	public static void getPlayInfo() throws ClientException {
		DefaultAcsClient client = initVodClient("LTAI5tFXc3pFMwYQYpdyuJCZ", "GdNf83PRunrvfxlvIeP3ccK2siUNAt");
		GetPlayInfoResponse response = new GetPlayInfoResponse();
		try {
			response = getPlayInfo(client);
			List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
			//播放地址
			for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
				System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
			}
			//Base信息
			System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
		} catch (Exception e) {
			System.out.print("ErrorMessage = " + e.getLocalizedMessage());
		}
		System.out.print("RequestId = " + response.getRequestId() + "\n");
	}

	public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
		GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
		request.setVideoId("31a23d30a13871eda4ee6732b78e0102");
		return client.getAcsResponse(request);
	}
	public static void getVideoPlayAuth() throws ClientException {
		DefaultAcsClient client = initVodClient("LTAI5tFXc3pFMwYQYpdyuJCZ", "GdNf83PRunrvfxlvIeP3ccK2siUNAt");
		GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
		try {
			response = getVideoPlayAuth(client);
			//播放凭证
			System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
			//VideoMeta信息
			System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
		} catch (Exception e) {
			System.out.print("ErrorMessage = " + e.getLocalizedMessage());
		}
		System.out.print("RequestId = " + response.getRequestId() + "\n");
	}
}