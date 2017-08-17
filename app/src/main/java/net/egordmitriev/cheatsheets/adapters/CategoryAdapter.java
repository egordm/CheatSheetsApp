package net.egordmitriev.cheatsheets.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.egordmitriev.cheatsheets.activities.MainActivity;
import net.egordmitriev.cheatsheets.widgets.CategoryGroupHolder;
import net.egordmitriev.cheatsheets.widgets.presenters.CategoryGroupPresenter;

/**
 * Created by egordm on 16-8-2017.
 */

public class CategoryAdapter extends AdvancedRecyclerAdapterPr<CategoryGroupPresenter, CategoryGroupHolder> {
	
	protected MainActivity mActivity;
	protected CategoryGroupPresenter mRecents;
	protected boolean mShowRecents = true;
	
	public CategoryAdapter(MainActivity activity) {
		mActivity = activity;
	}
	//todo hide when searching; mayby hop to recents = null
	
	@Override
	public CategoryGroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View holder = CategoryGroupHolder.inflate(LayoutInflater.from(parent.getContext()), parent, false);
		CategoryGroupHolder ret = new CategoryGroupHolder(mActivity, holder);
		return ret;
	}
	
	@Override
	public void onBindViewHolder(CategoryGroupHolder holder, int position) {
		if(mRecents == null || !mShowRecents) {
			holder.onBind(mData.get(position));
		} else {
			if (position == 0) holder.onBind(mRecents);
			else holder.onBind(mData.get(position - 1));
		}
	}
	
	@Override
	public int getItemCount() {
		return (mRecents == null || !mShowRecents) ? super.getItemCount() : super.getItemCount() + 1;
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
