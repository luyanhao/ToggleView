package com.example.toggleview.dialog;

import com.example.toggleview.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class DefaultDialog extends Dialog {

	public DefaultDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.default_dialog);
	}

}
