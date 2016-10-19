package com.wencharm.fun.ui.home;

import com.wencharm.fun.ui.BasePresenter;
import com.wencharm.fun.ui.BaseView;

/**
 * Created by Wencharm on 19/10/2016.
 */

public interface GanksContract {

	interface IPresenter extends BasePresenter {
		void loadGanks();
	}

	interface IView extends BaseView<IPresenter> {
		void showGanks();
	}

}
