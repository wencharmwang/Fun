package com.wencharm.fun.data.datasource;

import com.wencharm.fun.app.App;
import com.wencharm.fun.data.entity.Gank;
import com.wencharm.fun.data.network.GankService;
import com.wencharm.fun.domain.GankActor;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Wencharm on 10/11/2016.
 */

public class GanksStore implements GankActor.GanksRespository {

	public static final String KEY = "GanksStore";
	private GankService gankService;
	private BehaviorSubject<Gank> subject = BehaviorSubject.create();

	public GanksStore() {
	}

	@Override
	public Observable<Gank> getGanks() {
		if (App.cache.get(KEY) == null) {
			if (gankService == null) {
				Retrofit retrofit = new Retrofit.Builder()
						.client(App.network.httpClient)
						.baseUrl(GankService.BASE_URL)
						.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
						.addConverterFactory(JacksonConverterFactory.create())
						.build();
				gankService = retrofit.create(GankService.class);
			}
			gankService.getGanks("福利", 10, 1).doOnNext(l -> App.cache.put(KEY, l)).subscribe(ganks -> {
				subject.onNext(ganks);
			});
		}
		return subject;
	}
}
