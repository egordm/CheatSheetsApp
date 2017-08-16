package net.egordmitriev.cheatsheets.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.egordmitriev.cheatsheets.activities.MainActivity;
import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.widgets.CategoryGroupHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egordm on 16-8-2017.
 */

public class CategoryAdapter extends AdvancedRecyclerAdapter<Category, CategoryGroupHolder> {
	
	protected MainActivity mActivity;
	protected List<CategoryGroupHolder> mHolders;
	
	
	public CategoryAdapter(MainActivity activity) {
		mActivity = activity;
		mHolders = new ArrayList<>();
	}
	
	@Override
	public CategoryGroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View holder = CategoryGroupHolder.inflate(LayoutInflater.from(parent.getContext()), parent, false);
		CategoryGroupHolder ret = new CategoryGroupHolder(mActivity, holder);
		mHolders.add(ret);
		return ret;
	}
	
	@Override
	public void onBindViewHolder(CategoryGroupHolder holder, int position) {
		holder.onBind(mData.get(position));
	}
}
