package com.wencharm.fun.api;

/**
 * Created by Wencharm on 18/10/2016.
 */

public class Domain {

	public final GankApi gankApi;

	public Domain() {
		this.gankApi = new GankApi(this);
	}
}
