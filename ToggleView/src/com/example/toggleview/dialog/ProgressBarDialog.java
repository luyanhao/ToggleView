package com.example.toggleview.dialog;

import com.example.toggleview.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarDialog extends Dialog {

	private ProgressBar progressBar;
	private TextView textView;

	public ProgressBarDialog(Context context, int progressbardialog) {
		super(context, progressbardialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressbar_dialog);
		progressBar = (ProgressBar) findViewById(R.id.myprogressBar);
		textView = (TextView) findViewById(R.id.title);
		this.setCancelable(false);
	}

	public void setTitle(String titile) {
		textView.setText(titile);
	}

	public void setOnClickListener(android.view.View.OnClickListener listener) {
		textView.setOnClickListener(listener);
	}

	public void updateProgress(int length) {
		progressBar.setProgress(length);
	}
}
