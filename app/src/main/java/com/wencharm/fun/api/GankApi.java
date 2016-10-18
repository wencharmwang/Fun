package com.wencharm.fun.api;

import com.wencharm.fun.app.App;
import com.wencharm.fun.model.entity.Gank;
import com.wencharm.fun.model.network.GankService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;

/**
 * Created by Wencharm on 18/10/2016.
 */

public class GankApi extends BaseApi {

	private final GankService service;

	public GankApi(Domain domain) {
		super(domain);
		Retrofit retrofit = new Retrofit.Builder()
				.client(App.network.httpClient)
				.baseUrl(GankService.BASE_URL)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(JacksonConverterFactory.create())
				.build();
		service = retrofit.create(GankService.class);
	}

	public Observable<Gank> ganks() {
		return service.getGanks("all", 10, 1);
	}
}
