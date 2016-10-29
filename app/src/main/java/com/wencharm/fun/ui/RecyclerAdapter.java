package com.wencharm.fun.ui;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by Wencharm on 29/10/2016.
 */

public class RecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

	public List<T> data = Collections.emptyList();

	public List<T> data() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public VH onCreateViewHolder(ViewGroup parent, int viewType) {
		return null;
	}

	@Override
	public void onBindViewHolder(VH holder, int position) {

	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	public T getItem(int position) {
		return data.get(position);
	}
}
