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
		barDialog.setTitle("���ڼ����С���");
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
		// ֪ͨ��Ĭ�ϲ��� DEFAULT_SOUND, DEFAULT_VIBRATE, DEFAULT_LIGHTS.
		// ���Ҫȫ������Ĭ��ֵ, �� DEFAULT_ALL.
		// �˴�����Ĭ������,�����ǽ��������ص���������
		// notifycation.defaults |= Notification.DEFAULT_SOUND;
		// notifycation.defaults |= Notification.DEFAULT_VIBRATE;
		// notifycation.defaults |= Notification.DEFAULT_LIGHTS;
		// ��������������ѭ����ֱ���û���Ӧ
		// baseNF.flags |= Notification.FLAG_INSISTENT;
		// ֪ͨ��������Զ���ʧ
		notifycation.flags |= Notification.FLAG_AUTO_CANCEL;
		// //���'Clear'ʱ���������֪ͨ(QQ��֪ͨ�޷�����������õ����)
		notifycation.flags |= Notification.FLAG_NO_CLEAR;
		notifycation.icon = R.drawable.ic_launcher;// ����������ã�Ҫ����ʾ������
		notifycation.tickerText = "���ڼ������Ժ�";
		PendingIntent pIntent = PendingIntent.getActivity(ProgressBarActivity.this, 0, intent, 0);
		RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.progressbar_notify);
		// contentView1.setImageViewResource(R.id.icon,
		// R.drawable.ic_launcher);
		contentView.setTextViewText(R.id.title, "���ڼ������Ժ�");
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
