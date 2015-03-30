package com.example.toggleview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InitActivity extends ActionBarActivity implements OnClickListener {

	private Button main;
	private Button progressBar;
	private Button arcview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init);
		main = (Button) findViewById(R.id.main);
		progressBar = (Button) findViewById(R.id.progressBar);
		arcview = (Button) findViewById(R.id.arcview);
		main.setOnClickListener(this);
		progressBar.setOnClickListener(this);
		arcview.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.main:
			intent = new Intent(InitActivity.this, MainActivity.class);
			startActivity(intent);
			break;
		case R.id.progressBar:
			intent = new Intent(InitActivity.this, ProgressBarActivity.class);
			startActivity(intent);
			break;
		case R.id.arcview:
			intent = new Intent(InitActivity.this, ArcViewActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}
}
