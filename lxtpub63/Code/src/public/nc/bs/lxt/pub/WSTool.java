package nc.bs.lxt.pub;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import nc.vo.pub.BusinessException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
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
			RequestEntity re = new InputStreamRequestEntity(is, b.length, "text/xml;charset=utf-8");
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
	
	public static String callByHttpBasicAuth(String urlStr, String soapMsg, String user, String pass) throws BusinessException {
		try {
			URL url = new URL(urlStr);
			PostMethod postMethod = new PostMethod(urlStr);

			byte[] b = soapMsg.getBytes("utf-8");
			InputStream is = new ByteArrayInputStream(b, 0, b.length);
			RequestEntity re = new InputStreamRequestEntity(is, b.length, "text/xml;charset=utf-8");
			postMethod.setRequestEntity(re);
			
			HttpClient httpClient = new HttpClient();
			httpClient.getParams().setAuthenticationPreemptive(true);
			httpClient.getState().setCredentials(new AuthScope(url.getHost(), url.getPort(), AuthScope.ANY_REALM), 
					new UsernamePasswordCredentials(user, pass));
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);  
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(10000); 
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
