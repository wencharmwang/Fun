package com.wencharm.fun.presentation.home;

import com.wencharm.fun.app.App;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Wencharm on 19/10/2016.
 */

public class GanksPresenter implements GanksContract.IPresenter {

	GanksContract.IView view;
	Subscription subscription;

	public GanksPresenter(GanksFragment view) {
		this.view = view;
	}

	@Override
	public void loadGanks() {
		if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
		subscription = App.domain.gankApi.ganks().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(ganks -> {
			view.showGanks(ganks.results);
		});
	}

	@Override
	public void subscribe() {
		loadGanks();
	}

	@Override
	public void unsubscribe() {
		subscription.unsubscribe();
	}
}
