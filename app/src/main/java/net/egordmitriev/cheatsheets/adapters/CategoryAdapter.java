package net.egordmitriev.cheatsheets.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.egordmitriev.cheatsheets.activities.MainActivity;
import net.egordmitriev.cheatsheets.widgets.CategoryGroupHolder;
import net.egordmitriev.cheatsheets.widgets.RecentsGroupHolder;
import net.egordmitriev.cheatsheets.widgets.RecyclerViewHolder;
import net.egordmitriev.cheatsheets.widgets.presenters.CategoryGroupPresenter;

/**
 * Created by egordm on 16-8-2017.
 */

public class CategoryAdapter extends AdvancedRecyclerAdapterPr<CategoryGroupPresenter, RecyclerViewHolder<CategoryGroupPresenter>> {
	
	public static final int TYPE_CATEGORY = 0;
	public static final int TYPE_RECENTS = 1;
	
	protected MainActivity mActivity;
	protected CategoryGroupPresenter mRecents;
	protected boolean mShowRecents = true;
	
	public CategoryAdapter(MainActivity activity) {
		mActivity = activity;
	}
	//todo hide when searching; mayby hop to recents = null
	
	@Override
	public RecyclerViewHolder<CategoryGroupPresenter> onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = CategoryGroupHolder.inflate(LayoutInflater.from(parent.getContext()), parent, false);
		switch (viewType) {
			case TYPE_RECENTS:
				return new RecentsGroupHolder(mActivity, view);
			default:
				return new CategoryGroupHolder(mActivity, view);
		}
	}
	
	@Override
	public void onBindViewHolder(RecyclerViewHolder<CategoryGroupPresenter> holder, int position) {
		if(holder instanceof RecentsGroupHolder) {
			holder.onBind(mRecents);
		} else if (holder instanceof CategoryGroupHolder) {
			holder.onBind(mData.get(usingRecents() ? position - 1 : position));
		}
	}
	
	@Override
	public int getItemCount() {
		return (mRecents == null || !mShowRecents) ? super.getItemCount() : super.getItemCount() + 1;
	}
	
	@Override
	public int getItemViewType(int position) {
		if(usingRecents() && position == 0) {
			return TYPE_RECENTS;
		}
		return TYPE_CATEGORY;
	}
	
	public boolean usingRecents() {
		return mRecents != null && mShowRecents;
	}
	
	public void setRecents(CategoryGroupPresenter recents) {
		boolean inserted = mRecents == null;
		mRecents = recents;
		if(inserted) notifyItemInserted(0);
		else notifyItemChanged(0);
	}
	
	public void setShowRecents(boolean showRecents) {
		if (!showRecents && getItemCount() == mData.size() + 1) notifyItemRemoved(0);
		else if(showRecents && mRecents != null && getItemCount() < mData.size() + 1) notifyItemInserted(0);
		mShowRecents = showRecents;
	}
}
