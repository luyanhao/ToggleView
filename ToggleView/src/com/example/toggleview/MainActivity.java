package com.example.toggleview;

import com.example.toggleview.animation.ExpandCollapseAnimation;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private Button toggle_btn;

	private View itemToolBar;

	private final int EXPAND = 0;// togleviewչ��
	private final int COLLAPSE = 1;// togleview�ر�

	private int type = EXPAND;

	private PopupWindow popupWindow;// popupwindow

	private Button show_popupwindow;// ��ʾpopupwindow��ť
	private Button startProgress;// ��������ʼ��ť
	private ProgressBar mProgressBar;// �����������Զ��壩
	private LayoutInflater inflater = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		View parent = inflater.inflate(R.layout.activity_main, null);
		setContentView(parent);
		startProgress = (Button) findViewById(R.id.startProgress);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		toggle_btn = (Button) findViewById(R.id.expandable_toggle_button);
		itemToolBar = findViewById(R.id.expandable);
		show_popupwindow = (Button) findViewById(R.id.popupwindow);
		itemToolBar.measure(parent.getWidth(), parent.getHeight());// Ϊ�����صĲ��ּ����ʼ���

		// ViewTreeObserver vto = itemToolBar.getViewTreeObserver();
		// vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
		// public boolean onPreDraw() {
		int height = itemToolBar.getMeasuredHeight();
		// int width = itemToolBar.getMeasuredWidth();
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) itemToolBar.getLayoutParams();
		lp.bottomMargin = -height;
		itemToolBar.setLayoutParams(lp);
		itemToolBar.requestLayout();
		// return true;
		// }
		// });
		toggle_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				animateView(itemToolBar, type);
			}
		});
		show_popupwindow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPopUp(v);
			}
		});
		startProgress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new ProgressAsy().execute();
			}
		});

	}

	/**
	 * 
	 * �������������½���
	 * 
	 * @author ·���
	 * 
	 */
	class ProgressAsy extends AsyncTask<String, Integer, String> {

		private int length = 1000;

		@Override
		protected void onPreExecute() {
			mProgressBar.setVisibility(View.VISIBLE);
			mProgressBar.setMax(length);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			for (int i = 0; i <= length; i++) {
				mProgressBar.setProgress(i);
				publishProgress(i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return "ִ�����";
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			startProgress.setText(values[0] + "");
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			startProgress.setText(result);
		}

	}

	/**
	 * popupwindow��ʾ����
	 * 
	 * @param v
	 */
	@SuppressWarnings("deprecation")
	private void showPopUp(View v) {
		// LinearLayout layout = new LinearLayout(this);
		// layout.setBackgroundColor(Color.GRAY);
		// TextView tv = new TextView(this);
		// tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT));
		// tv.setText("I'm a pop -----------------------------!");
		// tv.setTextColor(Color.WHITE);
		// layout.addView(tv);

		popupWindow = new PopupWindow(inflater.inflate(R.layout.popuwindowlayout, null), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// ����popupWindow.setFocusable(true);
		// ����������popupWindow����Ĳ��ֿؼ���õ�����¼�������ͱ����ĸ���view�������ˡ�
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		// ��������һ�¾Ϳ���ʵ�ֱ���˵���ˣ����popupWindow���������Ϳ���������ʧ�ˡ�
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.AnimationDialog);
		int[] location = new int[2];
		v.getLocationOnScreen(location);

		popupWindow.showAtLocation(findViewById(R.id.main), Gravity.CENTER, 0, 0);// ��ʾ����Ļ�м�

		// popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0],
		// location[1] - popupWindow.getHeight());//�ؼ��Ϸ�
		// popupWindow.showAsDropDown(v);// �·�
		// popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
		// location[0]-popupWindow.getWidth(), location[1]);// ���
		// popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
		// location[0]+v.getWidth(), location[1]);// �ұ�
	}

	/**
	 * togleview����չ����ر�
	 * 
	 * @param target
	 * @param type
	 */
	private void animateView(final View target, int type) {
		Animation anim = new ExpandCollapseAnimation(target, type);
		anim.setDuration(330);
		target.startAnimation(anim);
		if (this.type == EXPAND) {
			this.type = COLLAPSE;
		} else {
			this.type = EXPAND;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
