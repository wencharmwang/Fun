package com.wencharm.fun.data.network;

import com.wencharm.fun.data.entity.Gank;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Wencharm on 19/10/2016.
 */

public interface GankService {

	String BASE_URL = "http://gank.io/api/";

	@GET("data/{type}/{number}/{page}")
	Observable<Gank> getGanks(@Path("type") String type, @Path("number") int number, @Path("page") int page);
}
