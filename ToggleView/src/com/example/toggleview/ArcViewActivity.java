package com.example.toggleview;

import com.example.toggleview.view.ArcBarView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ArcViewActivity extends Activity {

	///再测试
	private ArcBarView circle_progress;
	private TextView percent_tv;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				circle_progress.redraw((Integer) msg.obj);
				percent_tv.setText(msg.obj.toString());
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_arcbar);
		circle_progress = (ArcBarView) findViewById(R.id.circle_progress);
		percent_tv = (TextView) findViewById(R.id.progress_percent);
		circle_progress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new Task(mHandler).execute();
			}
		});
	}

	class Task extends AsyncTask<String, Integer, String> {

		private Handler handler;

		public Task(Handler mHandler) {
			this.handler = mHandler;
		}

		@Override
		protected String doInBackground(String... params) {
			for (int i = 0; i <= 360; i++) {
				Message msg = handler.obtainMessage();
				msg.what = 0;
				msg.obj = i;
				handler.sendMessage(msg);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
