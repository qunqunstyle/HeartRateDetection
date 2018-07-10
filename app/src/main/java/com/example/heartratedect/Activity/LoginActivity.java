package com.example.heartratedect.Activity;




import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heartratedect.MainActivity;
import com.example.heartratedect.R;
import com.example.heartratedect.Web.LoginJudge;

public class LoginActivity extends Activity {
	private EditText et_name;
	private EditText et_password;
	private Button btn_login;
	private TextView tv_regist;
	String username;
	String password;
	ProgressDialog p;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		init();
		tv_regist.setOnClickListener(new registListener());
		btn_login.setOnClickListener(new loginListener());
	}

	private void init() {
		// TODO Auto-generated method stub
		et_name = (EditText) findViewById(R.id.et_login_name);
		et_password = (EditText) findViewById(R.id.et_login_password);
		SharedPreferences sharedPreferences = getSharedPreferences("test",
				Activity.MODE_PRIVATE);
		String name = sharedPreferences.getString("name", "");
		String password = sharedPreferences.
				getString("password", "");
		et_name.setText(name);
		et_password.setText(password);
		btn_login = (Button) findViewById(R.id.btn_login);
		tv_regist = (TextView) findViewById(R.id.tv_login_regist);
		p = new ProgressDialog(LoginActivity.this);
		p.setTitle("登录中");
		p.setMessage("登录中，马上就好");
	}

	// 当没有帐号时候点击注册，跳转到注册页面
	private class registListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, RegistActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();
		}

	}

	// 点击登录的监听事件
	private class loginListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// Intent intent =new Intent();
			// intent.setClass(LoginActivity.this, MainActivity.class);
			// startActivity(intent);
			// LoginActivity.this.finish();
			username = et_name.getText().toString().trim();
			if (username == null || username.length() <= 0) {
				et_name.requestFocus();
				et_name.setError("对不起，用户名不能为空");
				return;
			} else {
				username = et_name.getText().toString().trim();
			}
			password = et_password.getText().toString().trim();
			if (password == null || password.length() <= 0) {
				et_password.requestFocus();
				et_password.setError("对不起，密码不能为空");
				return;
			} else {
				password = et_password.getText().toString().trim();
			}
			p.show();
			new Thread(new Runnable() {

				public void run() {
					LoginJudge judge = new LoginJudge();
					String result = judge.login("login", username, password);
					Log.i("TAG", "result=" + result);
					Message msg = new Message();
					msg.obj = result;
					try {
						// 延时一秒操作
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.sendMessage(msg);
				}
			}).start();
		}

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String result = (String) msg.obj;
			Log.i("TAG", "string=" + result);
			p.dismiss();
			super.handleMessage(msg);
			if ("Success".equals(result)) {
				Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG)
						.show();
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				intent.putExtra("userName", username);
				startActivity(intent);
				LoginActivity.this.finish();
				SharedPreferences mySharedPreferences = getSharedPreferences(
						"test", Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = mySharedPreferences.edit();
				editor.putString("name", username);
				editor.putString("password", password);
				editor.commit();
			} else if ("InternalFail".equals(result)) {
				Toast.makeText(LoginActivity.this, "网络似乎出了点问题哦 ‘(*>﹏<*)’",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(LoginActivity.this, "用户名或密码不正确",
						Toast.LENGTH_SHORT).show();
			}
		}
	};
}
