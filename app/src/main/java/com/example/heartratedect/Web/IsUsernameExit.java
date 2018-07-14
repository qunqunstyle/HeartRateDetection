package com.example.heartratedect.Web;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

//用来判断注册的用户名是否已经存在。
public class IsUsernameExit {

	private String serverUrl = "http://39.106.28.218:8888/msgserver/";
	//private String serverUrl = "http://192.168.137.1:8080/";
	private String httpResult;

	public Boolean isExit(String username) {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(serverUrl+"getAllUserName");
		try {
			HttpResponse response = client.execute(httpGet);
			// 判断网络是否连接成功
			if (response.getStatusLine().getStatusCode() == 200) {
				httpResult = EntityUtils
						.toString(response.getEntity(), "utf-8");
//				Log.i("TAG", httpResult);
			} else {
				httpResult = "网络连接失败";
			}
			JSONArray jsonArray = new JSONArray(httpResult);
			Log.i("TAG", jsonArray.length() + "");
			for (int i = 0; i < jsonArray.length(); i++) {
				Log.i("TAG", jsonArray.getJSONObject(i).getString("userName"));
				if (username.equals(jsonArray.getJSONObject(i).getString(
						"userName"))) {
					return true;
				}
			}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
