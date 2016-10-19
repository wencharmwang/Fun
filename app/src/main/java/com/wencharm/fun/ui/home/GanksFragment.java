package com.wencharm.fun.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wencharm.fun.R;
import com.wencharm.fun.ui.BaseFragment;

/**
 * Created by Wencharm on 19/10/2016.
 */

public class GanksFragment extends BaseFragment implements GanksContract.IView {

	public static final String TAG = "GanksFragment";

	private GanksContract.IPresenter presenter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setPresenter(new GanksPresenter());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		return inflater.inflate(R.layout.home_ganks, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		presenter.subscribe();
	}

	@Override
	public void onStop() {
		super.onStop();
		presenter.unsubscribe();
	}

	@Override
	public void showGanks() {

	}

	@Override
	public void setPresenter(GanksContract.IPresenter presenter) {
		this.presenter = presenter;
	}
}
