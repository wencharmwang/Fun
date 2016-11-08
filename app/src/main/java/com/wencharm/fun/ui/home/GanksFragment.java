package com.wencharm.fun.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wencharm.fun.R;
import com.wencharm.fun.app.App;
import com.wencharm.fun.model.entity.Gank;
import com.wencharm.fun.ui.BaseActivity;
import com.wencharm.fun.ui.BaseFragment;
import com.wencharm.fun.ui.RecyclerAdapter;
import com.wencharm.fun.ui.profile.ProfileFragment;
import com.wencharm.fun.ui.profile.ProfileTransiton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Wencharm on 19/10/2016.
 */

public class GanksFragment extends BaseFragment implements GanksContract.IView {

	public static final String TAG = "GanksFragment";

	@BindView(R.id.gank_list)
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
		ButterKnife.bind(this, view);
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
		@BindView(R.id.item)
		CardView card;
		@BindView(R.id.image)
		SimpleDraweeView image;
		@BindView(R.id.provider)
		TextView provider;

		GankHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
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
				current.setEnterTransition(new Fade());
				current.setExitTransition(new Fade());
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
