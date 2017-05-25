package com.wencharm.fun.app;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Wencharm on 18/10/2016.
 */

public class Network {

	public final OkHttpClient httpClient;

	public Network(Context context) {
		Cache cache = new Cache(new File(context.getCacheDir(), "network cache"), 1024 * 1024 * 200);
		httpClient = new OkHttpClient.Builder()
				.cache(cache)
				.connectTimeout(30, TimeUnit.SECONDS)
				.readTimeout(30, TimeUnit.SECONDS)
				.writeTimeout(30, TimeUnit.SECONDS)
				.retryOnConnectionFailure(true)
				.build();
	}
}
