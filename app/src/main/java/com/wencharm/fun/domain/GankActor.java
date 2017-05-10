package com.wencharm.fun.domain;

import com.wencharm.fun.data.entity.Gank;

import io.reactivex.Observable;


/**
 * Created by Wencharm on 18/10/2016.
 */

public class GankActor extends BaseActor {

	private final GanksRespository ganksRespository;

	public GankActor(GanksRespository respository) {
		ganksRespository = respository;
	}

	public Observable<Gank> ganks() {
		return ganksRespository.getGanks();
	}

	public interface GanksRespository {
		Observable<Gank> getGanks();
	}
}
