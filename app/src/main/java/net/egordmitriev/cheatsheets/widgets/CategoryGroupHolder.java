package net.egordmitriev.cheatsheets.widgets;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.cachapa.expandablelayout.ExpandableLayout;
import net.egordmitriev.cheatsheets.CheatSheetsApp;
import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.activities.MainActivity;
import net.egordmitriev.cheatsheets.adapters.AdvancedRecyclerAdapter;
import net.egordmitriev.cheatsheets.api.API;
import net.egordmitriev.cheatsheets.listeners.ExpansionArrowListener;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;
import net.egordmitriev.cheatsheets.widgets.presenters.CategoryGroupPresenter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by EgorDm on 13-Jun-2017.
 */

public class CategoryGroupHolder extends RecyclerViewHolder<CategoryGroupPresenter> implements View.OnClickListener, View.OnLongClickListener {
	
	@BindView(R.id.expandable)
	ExpandableLayout mExpandableLayout;
	
	@BindView(R.id.header_title)
	TextView mTitle;
	
	@BindView(R.id.group_contents)
	RecyclerView mSheetsList;
	
	CategoryChildAdapter mAdapter;
	
	public CategoryGroupHolder(Activity activity, View view) {
		super(activity, view);
		mExpandableLayout.setOnExpansionUpdateListener(new ExpansionArrowListener(ButterKnife.findById(view, R.id.expandable_arrow)));
		mSheetsList.setLayoutManager(new LinearLayoutManager(activity) {
			@Override
			public boolean canScrollVertically() {
				return false;
			}
		});
		mAdapter = new CategoryChildAdapter();
		mSheetsList.setAdapter(mAdapter);
	}
	
	@Override
	public void onBind(CategoryGroupPresenter data) {
		mExpandableLayout.setExpansion(1);
		mTitle.setText(Html.fromHtml(data.title));
		mAdapter.replaceAll(new ArrayList<>(Arrays.asList(data.children)));
	}
	
	public void onUpdate() {
		for(int i = 0; i < mSheetsList.getChildCount(); i++) {
			SheetItemHolder holder = (SheetItemHolder) mSheetsList.getChildViewHolder(mSheetsList.getChildAt(i));
			holder.onUpdate();
		}
	}
	
	@OnClick(R.id.header)
	public void onClickHeader(View view) {
		if (mExpandableLayout.isExpanded()) {
			view.setSelected(false);
			mExpandableLayout.collapse();
		} else {
			view.setSelected(true);
			mExpandableLayout.expand();
		}
	}
	
	public void collapse(boolean collapse, boolean animate) {
		if (collapse) {
			mExpandableLayout.collapse(animate);
		} else {
			mExpandableLayout.expand(animate);
		}
	}
	
	public static View inflate(LayoutInflater inflater, ViewGroup parent, boolean bind) {
		View view = inflater.inflate(R.layout.group_item_category, parent, false);
		if (bind) parent.addView(view);
		return view;
	}
	
	@Override
	public void onClick(View view) {
		int position = mSheetsList.getChildLayoutPosition(view);
		if(position == -1) return;
		CheatSheetsApp.openCheatSheet(mActivity, mAdapter.getItem(position));
	}
	
	@Override
	public boolean onLongClick(View view) {
		int position = mSheetsList.getChildLayoutPosition(view);
		if(position == -1) return false;
		final CheatSheet cheatSheet = mAdapter.getItem(position);
		if(!cheatSheet.isLocal()) return false;
		PopupMenu popup = new PopupMenu(mActivity, view);
		MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(R.menu.cheat_sheet_entry_menu, popup.getMenu());
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				if(item.getItemId() == R.id.delete_local) {
					API.deleteCheatSheetCache(cheatSheet);
					((MainActivity)mActivity).onSoftUpdate();
					Toast.makeText(mActivity, "Deleted local cheats sheet.", Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});
		
		popup.show();
		return true;
	}
	
	
	public class CategoryChildAdapter extends AdvancedRecyclerAdapter<CheatSheet, SheetItemHolder> {
		@Override
		public SheetItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View holder = SheetItemHolder.inflate(LayoutInflater.from(parent.getContext()), parent, false);
			holder.setOnClickListener(CategoryGroupHolder.this);
			holder.setOnLongClickListener(CategoryGroupHolder.this);
			return new SheetItemHolder(mActivity, holder);
		}
		
		@Override
		public void onBindViewHolder(SheetItemHolder holder, int position) {
			holder.onBind(mData.get(position));
			holder.getView().setBackgroundResource((position % 2 == 0) ? R.color.tableEven : R.color.tableUneven);
		}
	}
}
