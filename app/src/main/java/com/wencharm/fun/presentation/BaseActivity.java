package com.wencharm.fun.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Wencharm on 18/10/2016.
 */

public class BaseActivity extends AppCompatActivity {

	public LayoutInflater inflater() {
		return LayoutInflater.from(this);
	}

	public View inflate(int res, ViewGroup parent) {
		return inflater().inflate(res, parent, false);
	}
	public View inflate(int res, ViewGroup parent, boolean attachToRoot) {
		return inflater().inflate(res, parent, attachToRoot);
	}

	public String string(int resId) {
		return getResources().getString(resId);
	}

	public int color(int resId) {
		return getResources().getColor(resId);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
}
