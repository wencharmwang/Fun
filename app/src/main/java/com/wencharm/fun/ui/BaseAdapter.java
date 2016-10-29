package com.wencharm.fun.ui;

import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by Wencharm on 29/10/2016.
 */

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

	public List<T> data = Collections.emptyList();

	public List<T> data() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return data().size();
	}

	@Override
	public Object getItem(int position) {
		return data().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public abstract View getItemView(int viewType);
	public abstract void adapt(View view, int position);

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) view = getItemView(getItemViewType(position));
		else view = convertView;
		adapt(view, position);
		return view;
	}

}
