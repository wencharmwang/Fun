package com.wencharm.fun.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wencharm.fun.R;
import com.wencharm.fun.ui.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Wencharm on 06/11/2016.
 */

public class ProfileFragment extends BaseFragment {

	public static final String URL = "image_url";

	@BindView(R.id.image)
	SimpleDraweeView image;
	@BindView(R.id.name)
	TextView name;
	@BindView(R.id.status)
	TextView status;

	private String url;
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		url = getArguments().getString(URL);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.profile, container, false);
		ButterKnife.bind(this, view);
		image.setImageDrawable(activity().getDrawable(R.drawable.ic_image_holder));
//		App.image.load(image, url);
		name.setText("Charming");
		status.setText("To be a lazy cat");
		return view;
	}
}
