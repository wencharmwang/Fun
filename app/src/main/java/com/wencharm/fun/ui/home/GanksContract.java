package com.wencharm.fun.ui.home;

import com.wencharm.fun.model.entity.Gank;
import com.wencharm.fun.ui.BasePresenter;
import com.wencharm.fun.ui.BaseView;

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
