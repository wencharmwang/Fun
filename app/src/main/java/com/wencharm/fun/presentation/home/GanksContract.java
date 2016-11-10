package com.wencharm.fun.presentation.home;

import com.wencharm.fun.data.entity.Gank;
import com.wencharm.fun.presentation.BasePresenter;
import com.wencharm.fun.presentation.BaseView;

import java.util.List;

/**
 * Created by Wencharm on 19/10/2016.
 */

public interface GanksContract {

	interface IPresenter extends BasePresenter {
		void loadGanks();
	}

	interface IView extends BaseView<IPresenter> {
		void showGanks(List<Gank.GankInfo> data);
	}

}
