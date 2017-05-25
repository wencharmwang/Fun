package com.wencharm.fun.presentation.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wencharm.fun.R;
import com.wencharm.fun.app.App;
import com.wencharm.fun.data.entity.Gank;
import com.wencharm.fun.presentation.BaseActivity;
import com.wencharm.fun.presentation.BaseFragment;
import com.wencharm.fun.presentation.RecyclerAdapter;
import com.wencharm.fun.presentation.profile.ProfileFragment;
import com.wencharm.fun.presentation.profile.ProfileTransiton;

import java.util.List;

/**
 * Created by Wencharm on 19/10/2016.
 */

public class GanksFragment extends BaseFragment implements GanksContract.IView {

	public static final String TAG = "GanksFragment";

	RecyclerView gank_list;

	private GanksContract.IPresenter presenter;
	private Adapter adapter = new Adapter();
	RecyclerView.LayoutManager layoutManager;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setPresenter(new GanksPresenter(this));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.home_ganks, container, false);
		ViewGroup parent = (ViewGroup) view;
		gank_list = (RecyclerView) parent.findViewById(R.id.gank_list);
		gank_list.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(activity());
		gank_list.setLayoutManager(layoutManager);
		gank_list.setAdapter(adapter);
		return view;
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
	public void showGanks(List<Gank.GankInfo> data) {
		adapter.setData(data);
	}

	@Override
	public void setPresenter(GanksContract.IPresenter presenter) {
		this.presenter = presenter;
	}

	class Adapter extends RecyclerAdapter<Gank.GankInfo, GankHolder> {

		@Override
		public GankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new GankHolder(activity().inflate(R.layout.home_ganks_item, parent, false));
		}

		@Override
		public void onBindViewHolder(GankHolder holder, int position) {
			holder.bind(activity(), getItem(position), position);
		}

	}

	static class GankHolder extends RecyclerView.ViewHolder {
		CardView card;
		SimpleDraweeView image;
		TextView provider;

		GankHolder(View itemView) {
			super(itemView);
			ViewGroup parent = (ViewGroup) itemView;
			card = (CardView) parent.findViewById(R.id.item);
			image = (SimpleDraweeView) parent.findViewById(R.id.image);
			provider = (TextView) parent.findViewById(R.id.provider);
		}

		void bind(BaseActivity activity, Gank.GankInfo gankInfo, int position) {
			App.image.load(image, gankInfo.url);
			provider.setText(gankInfo.who);
			ViewCompat.setTransitionName(image, "image" + position);
			card.setOnClickListener(v -> {
				Bundle bundle = new Bundle();
				bundle.putString(ProfileFragment.URL, gankInfo.url);
				ProfileFragment fragment = new ProfileFragment();
				fragment.setArguments(bundle);
				fragment.setSharedElementEnterTransition(new ProfileTransiton());
				fragment.setSharedElementReturnTransition(new ProfileTransiton());
				BaseFragment current = (BaseFragment) activity.getSupportFragmentManager().findFragmentById(R.id.content);
//				current.setEnterTransition(new Fade());
//				current.setExitTransition(new Fade());
				activity.getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.content, fragment)
						.addToBackStack(TAG)
						.addSharedElement(image, "image")
						.commit();
			});
		}

	}
}
