package com.example.heartratedect.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.heartratedect.R;

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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TuCaoActivity extends Activity {
	private ImageView ivTucao;
	private ImageView ivBackToSetting;
	private TextView tvName;
	private EditText etTuCaoContent;
	private String content;
	private final String serverUrl = "http://192.168.191.1:8080/msgserver/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tucao);
		TelephonyManager phoneMgr = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String strPhoneNum = phoneMgr.getLine1Number();
		initview();
		ivBackToSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TuCaoActivity.this.finish();
			}
		});
		ivTucao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				content = etTuCaoContent.getText().toString();
				// TODO 需要在服务器端插入一个用户反馈表，得到用户的反馈信息，并添加相应的action。
				final SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm");
				new Thread(new Runnable() {

					String result;

					@Override
					public void run() {
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair(
								"suggestion.suggestDate", df.format(new Date())));
						params.add(new BasicNameValuePair(
								"suggestion.suggestUser", tvName.getText()
										.toString()));
						params.add(new BasicNameValuePair(
								"suggestion.suggestPhoneModel",
								android.os.Build.MODEL));
						params.add(new BasicNameValuePair(
								"suggestion.suggestContent", content));
						params.add(new BasicNameValuePair(
								"suggestion.suggestPhoneNumber", strPhoneNum));
						HttpEntity entity;
						try {
							entity = new UrlEncodedFormEntity(params,
									HTTP.UTF_8);
							// HttpPost httpPost = connNet.getHttpPost(action);
							HttpPost httpPost = new HttpPost(serverUrl
									+ "addSuggestion");
							httpPost.setEntity(entity);
							HttpClient client = new DefaultHttpClient();
							HttpResponse httpResponse = client
									.execute(httpPost);
							if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
								// result = "网络连接成功";
								result = EntityUtils.toString(
										httpResponse.getEntity(), "utf-8");
								// if (result.equals("success")) {
								// Toast.makeText(TuCaoActivity.this,
								// "感谢您的吐槽", Toast.LENGTH_SHORT)
								// .show();
								// }
							} else {
								result = "fail";
							}
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Message msg = new Message();
						msg.obj = result;
						handler.sendMessage(msg);
					}
				}).start();
				TuCaoActivity.this.finish();
			}
		});
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.obj.toString().equals("success")) {
				Toast.makeText(TuCaoActivity.this, "感谢您的吐槽", Toast.LENGTH_SHORT)
						.show();
			}
			super.handleMessage(msg);
		}
	};

	private void initview() {
		Bundle bundle = getIntent().getExtras();
		ivTucao = (ImageView) findViewById(R.id.iv_tucao);
		ivBackToSetting = (ImageView) findViewById(R.id.iv_backtosetting);
		tvName = (TextView) findViewById(R.id.tvName);
		tvName.setText(bundle.getString("userName"));
		etTuCaoContent = (EditText) findViewById(R.id.etTuCaoContent);
	}
}
