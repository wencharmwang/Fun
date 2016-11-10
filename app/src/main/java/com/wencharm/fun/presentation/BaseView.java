package com.wencharm.fun.presentation;

/**
 * Created by Wencharm on 18/10/2016.
 */

public interface BaseView<T extends BasePresenter> {

	void setPresenter(T presenter);
}
