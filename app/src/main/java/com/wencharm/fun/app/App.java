package com.wencharm.fun.app;

import android.app.Application;

import com.wencharm.fun.api.Domain;

/**
 * Created by Wencharm on 18/10/2016.
 */

public class App extends Application {

	public App app;
	public static Network network;
	public static Domain domain;

	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		network = new Network(app);
		domain = new Domain();
	}
}
