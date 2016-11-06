package com.wencharm.fun.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wencharm.fun.R;
import com.wencharm.fun.ui.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Wencharm on 06/11/2016.
 */

public class ProfileFragment extends BaseFragment {

	@BindView(R.id.image)
	SimpleDraweeView image;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.profile, container, false);
		ButterKnife.bind(this, view);
		return view;
	}
}
