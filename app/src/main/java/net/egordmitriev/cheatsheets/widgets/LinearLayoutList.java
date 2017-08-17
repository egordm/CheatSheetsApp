package net.egordmitriev.cheatsheets.widgets;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import net.egordmitriev.cheatsheets.adapters.LinearAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egordm on 17-8-2017.
 */

public class LinearLayoutList extends LinearLayout {
	
	private LinearAdapter mAdapter;
	private List<ViewHolder> mViewHolders = new ArrayList<>();
	
	private DataSetObserver mObserver = new DataSetObserver() {
		@Override
		public void onChanged() {
			super.onChanged();
			reloadChildViews();
		}
	};
	
	public LinearLayoutList(Context context) {
		super(context);
		setOrientation(LinearLayout.VERTICAL);
	}
	
	public LinearLayoutList(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		setOrientation(LinearLayout.VERTICAL);
	}
	
	public LinearLayoutList(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setOrientation(LinearLayout.VERTICAL);
	}
	
	public void setAdapter(LinearAdapter adapter) {
		mAdapter = adapter;
		mAdapter.registerDataSetObserver(mObserver);
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mAdapter != null) mAdapter.unregisterDataSetObserver(mObserver);
	}
	
	private void reloadChildViews() {
		while (mViewHolders.size() < mAdapter.getCount()) {
			ViewHolder holder = mAdapter.onCreateViewHolder(this);
			mViewHolders.add(holder);
		}
		if (mViewHolders.size() > mAdapter.getCount()) {
			int end = mViewHolders.size();
			int count = end - mAdapter.getCount();
			mViewHolders.subList(end - count, end).clear();
			removeViews(end - count, count);
		}
		for(int i = 0; i < mViewHolders.size(); i++) {
			mAdapter.onBindViewHolder(mViewHolders.get(i), i);
		}
	}
	
}
