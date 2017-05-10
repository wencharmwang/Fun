package com.wencharm.fun.presentation.home;

import com.wencharm.fun.app.App;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Wencharm on 19/10/2016.
 */

public class GanksPresenter implements GanksContract.IPresenter {

	GanksContract.IView view;
	Disposable subscription;

	public GanksPresenter(GanksFragment view) {
		this.view = view;
	}

	@Override
	public void loadGanks() {
		if (subscription != null && !subscription.isDisposed()) subscription.dispose();
		subscription = App.domain.gankActor.ganks().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(ganks -> {
			view.showGanks(ganks.results);
		});
	}

	@Override
	public void subscribe() {
		loadGanks();
	}

	@Override
	public void unsubscribe() {
		subscription.dispose();
	}
}
