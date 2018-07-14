package com.example.heartratedect.Web;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这个类用来进行登录判断的工具类
 * 
 * @author ubuntu
 * 
 */
public class LoginJudge {

	private final String serverUrl = "http://192.168.137.1:8080/";

	/**
	 * 使用get方式向服务器发送请求，如http://localhost:8080/login.action?user.userName=cjs&
	 * user.userPassword=123 向服务器发出action请求，附带用户名和密码
	 * 
	 * @param url
	 * @param userName
	 * @param userPassword
	 * @return
	 */
	public String login(String action, String userName, String userPassword) {
		String result = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user.userName", userName));
		params.add(new BasicNameValuePair("user.userPassword", userPassword));
		HttpEntity entity;
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(serverUrl + "login");
		HttpResponse response;
		try {
			entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			httpPost.setEntity(entity);
			response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// result = "网络连接成功";
				result = EntityUtils.toString(response.getEntity(), "utf-8");
			} else {
				result = "InternalFail";
			}
			// if (response.getStatusLine().getStatusCode() == 200) {
			// HttpEntity entity = response.getEntity();
			// if (entity != null) {
			// result = EntityUtils.toString(entity);
			// }
			// }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public String httpPost(String urlStr, String userName, String userPassword) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", userName);
		params.put("passWord", userPassword);
		String result = null;
		URL url = null;
		HttpURLConnection conn = null;
		InputStreamReader in = null;
		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", "utf-8");
			DataOutputStream dop = new DataOutputStream(conn.getOutputStream());
			// dop.wr
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}
}
