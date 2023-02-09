package com.hyj.msm.service.Impl;


import com.aliyun.tea.TeaException;

import com.hyj.msm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
	@Override
	public boolean send(String phone, String templateCode, Map<String, Object> param) throws Exception {
		if (StringUtils.isEmpty(phone)) return false;

		com.aliyun.dysmsapi20170525.Client client = this.createClient("LTAI5tFXc3pFMwYQYpdyuJCZ", "GdNf83PRunrvfxlvIeP3ccK2siUNAt");
		com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
				.setSignName("阿里云短信测试")
				.setTemplateCode(templateCode)
				.setPhoneNumbers(phone)
				.setTemplateParam("{\"code\":\"" + param.get("code").toString() + "\"}");
		com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
		try {
			// 复制代码运行请自行打印 API 的返回值
			client.sendSmsWithOptions(sendSmsRequest, runtime);
			return true;
		} catch (TeaException error) {
			// 如有需要，请打印 error
			com.aliyun.teautil.Common.assertAsString(error.message);
			return false;
		} catch (Exception _error) {
			TeaException error = new TeaException(_error.getMessage(), _error);
			// 如有需要，请打印 error
			com.aliyun.teautil.Common.assertAsString(error.message);
			return false;
		}


	}

	public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
		com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
				// 必填，您的 AccessKey ID
				.setAccessKeyId(accessKeyId)
				// 必填，您的 AccessKey Secret
				.setAccessKeySecret(accessKeySecret);
		// 访问的域名
		config.endpoint = "dysmsapi.aliyuncs.com";
		return new com.aliyun.dysmsapi20170525.Client(config);
	}
}
