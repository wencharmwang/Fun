package com.wencharm.fun.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Wencharm on 18/10/2016.
 */

public class BaseFragment extends Fragment {

	public BaseActivity activity() {
		return (BaseActivity) this.getContext();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
}
