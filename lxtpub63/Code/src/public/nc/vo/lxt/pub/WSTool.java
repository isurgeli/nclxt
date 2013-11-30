package nc.vo.lxt.pub;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import nc.vo.pub.BusinessException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;


public class WSTool {
	public static String callByHttp(String url, String soapMsg) throws BusinessException {
		try {
			PostMethod postMethod = new PostMethod(url);
			// 然后把Soap请求数据添加到PostMethod中
			byte[] b = soapMsg.getBytes("utf-8");
			InputStream is = new ByteArrayInputStream(b, 0, b.length);
			RequestEntity re = new InputStreamRequestEntity(is, b.length, "application/soap+xml; charset=utf-8");
			postMethod.setRequestEntity(re);
	
			// 最后生成一个HttpClient对象，并发出postMethod请求
			HttpClient httpClient = new HttpClient();
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode == 200) {
				String soapResponseData = postMethod.getResponseBodyAsString();
				return soapResponseData;
			} else {
				throw new BusinessException("错误 HTTP "+statusCode);
			}
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException(e);
		} catch (HttpException e) {
			throw new BusinessException(e);
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}
}
