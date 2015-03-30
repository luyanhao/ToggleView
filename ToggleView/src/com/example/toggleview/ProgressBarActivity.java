package com.example.toggleview;

import com.example.toggleview.dialog.CommonDialog;
import com.example.toggleview.dialog.DefaultDialog;
import com.example.toggleview.dialog.ProgressBarDialog;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;

public class ProgressBarActivity extends ActionBarActivity implements OnClickListener {

	private Button dialog;
	private Button notify;
	private Button common;
	private Button mdefault;
	private NotificationManager manager;
	private Notification notifycation;
	private static final int UPDATE_PROGRESS = 1;
	private ProgressBarDialog barDialog;
	private CommonDialog commonDialog;
	private String update_which;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progressbar);
		dialog = (Button) findViewById(R.id.dialog);
		notify = (Button) findViewById(R.id.notify);
		common = (Button) findViewById(R.id.common);
		mdefault = (Button) findViewById(R.id.mdefault);
		common.setOnClickListener(this);
		dialog.setOnClickListener(this);
		notify.setOnClickListener(this);
		mdefault.setOnClickListener(this);
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPDATE_PROGRESS:
				// progressBar.setProgress((Integer) msg.obj);
				if (update_which.equals("dialog")) {
					barDialog.updateProgress((Integer) msg.obj);
				} else {
					notifycation.contentView.setProgressBar(R.id.myprogressBar, 100, (Integer) msg.obj, false);
					manager.notify(0, notifycation);
				}
				break;

			default:
				break;
			}
		};
	};
	private DefaultDialog defaultDialog;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog:
			showProgressBar();
			break;

		case R.id.notify:
			showNotification();
			new UpdateProgress("notify").execute();
			break;
		case R.id.common:
			showCommon();
			break;
		case R.id.mdefault:
			showDefault();
			break;
		default:
			break;
		}

	}

	private void showDefault() {
		defaultDialog = new DefaultDialog(ProgressBarActivity.this, R.style.ProgressBarDialog);

		defaultDialog.show();
	}

	private void showProgressBar() {
		barDialog = new ProgressBarDialog(ProgressBarActivity.this, R.style.ProgressBarDialog);
		barDialog.show();
		barDialog.setTitle("正在加载中……");
		barDialog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				barDialog.dismiss();
			}
		});
		new UpdateProgress("dialog").execute();
	}

	private void showCommon() {
		commonDialog = new CommonDialog(ProgressBarActivity.this, R.style.ProgressBarDialog);
		commonDialog.show();
	}

	private void showNotification() {
		Intent intent = new Intent(ProgressBarActivity.this, MainActivity.class);
		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notifycation = new Notification();
		// 通知的默认参数 DEFAULT_SOUND, DEFAULT_VIBRATE, DEFAULT_LIGHTS.
		// 如果要全部采用默认值, 用 DEFAULT_ALL.
		// 此处采用默认声音,这里是进度条，关掉声音和振动
		// notifycation.defaults |= Notification.DEFAULT_SOUND;
		// notifycation.defaults |= Notification.DEFAULT_VIBRATE;
		// notifycation.defaults |= Notification.DEFAULT_LIGHTS;
		// 让声音、振动无限循环，直到用户响应
		// baseNF.flags |= Notification.FLAG_INSISTENT;
		// 通知被点击后，自动消失
		notifycation.flags |= Notification.FLAG_AUTO_CANCEL;
		// //点击'Clear'时，不清楚该通知(QQ的通知无法清除，就是用的这个)
		notifycation.flags |= Notification.FLAG_NO_CLEAR;
		notifycation.icon = R.drawable.ic_launcher;// 这个必须设置，要不显示不出来
		notifycation.tickerText = "正在加载请稍候";
		PendingIntent pIntent = PendingIntent.getActivity(ProgressBarActivity.this, 0, intent, 0);
		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.progressbar_notify);
		// contentView1.setImageViewResource(R.id.icon,
		// R.drawable.ic_launcher);
		contentView.setTextViewText(R.id.title, "正在加载请稍候");
		notifycation.contentIntent = pIntent;
		notifycation.contentView = contentView;
		manager.notify(0, notifycation);

	}

	class UpdateProgress extends AsyncTask<String, String, String> {

		public UpdateProgress(String which) {
			super();
			update_which = which;
		}

		@Override
		protected String doInBackground(String... params) {
			for (int i = 0; i <= 100; i++) {
				Message msg = mHandler.obtainMessage();
				msg.what = UPDATE_PROGRESS;
				msg.obj = i;
				mHandler.sendMessage(msg);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

	}
}
