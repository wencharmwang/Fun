package com.wencharm.fun.app;

import android.app.Application;

import com.wencharm.fun.data.datasource.Cache;
import com.wencharm.fun.domain.Domain;

/**
 * Created by Wencharm on 18/10/2016.
 */

public class App extends Application {

	public App app;
	public static Network network;
	public static Domain domain;
	public static Image image;
	public static Cache cache;

	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		network = new Network(this);
		domain = new Domain();
		image = new Image(this, network);
		cache = new Cache(1024 * 2014 * 100);
	}
}
