package com.wencharm.fun.model.network;

import com.wencharm.fun.model.entity.Gank;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Wencharm on 19/10/2016.
 */

public interface GankService {

	String BASE_URL = "http://gank.io/api/";

	@GET("data/{type}/{number}/{page}")
	Observable<Gank> getGanks(@Path("type") String type, @Path("number") int number, @Path("page") int page);
}
