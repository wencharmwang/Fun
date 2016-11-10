package com.wencharm.fun.domain;

import com.wencharm.fun.data.datasource.GanksStore;

/**
 * Created by Wencharm on 18/10/2016.
 */

public class Domain {

	public final GankActor gankActor;

	public Domain() {
		this.gankActor = new GankActor(new GanksStore());
	}
}
