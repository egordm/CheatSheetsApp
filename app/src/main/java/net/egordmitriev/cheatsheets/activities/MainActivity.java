package net.egordmitriev.cheatsheets.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.adapters.CategoryAdapter;
import net.egordmitriev.cheatsheets.api.API;
import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;
import net.egordmitriev.cheatsheets.utils.DataCallback;
import net.egordmitriev.cheatsheets.widgets.CategoryGroupHolder;
import net.egordmitriev.cheatsheets.widgets.CustomLoaderView;
import net.egordmitriev.cheatsheets.widgets.presenters.CategoryGroupPresenter;
import net.egordmitriev.loaderview.LoaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends SearchBarActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	
	@BindView(R.id.dataContainer)
	RecyclerView mCategoryContainer;
	
	@BindView(R.id.loaderview)
	CustomLoaderView mLoaderView;
	
	private CategoryAdapter mAdapter;
	private Category[] mCategories;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLoaderView.setState(LoaderView.STATE_LOADING);
		mSearchView.setQueryHint("Search for cheatsheets");
		
		mCategoryContainer.setLayoutManager(new LinearLayoutManager(this));
		mAdapter = new CategoryAdapter(this);
		mCategoryContainer.setAdapter(mAdapter);
		
		API.requestCategories(getCallback());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		onUpdate();
	}
	
	@Override
	public boolean onQueryTextSubmit(String query) {
		if (mCategories == null) return false;
		List<CategoryGroupPresenter> data = new ArrayList<>();
		for (Category category : mCategories) {
			if(category.filter(query, false)) {
				data.add(new CategoryGroupPresenter(category));
				continue;
			}
			List<CheatSheet> cheatSheets = new ArrayList<>();
			for(CheatSheet cheatSheet : category.cheat_sheets) {
				if(cheatSheet.filter(query)) cheatSheets.add(cheatSheet);
			}
			if (cheatSheets.size() > 0)
				data.add(new CategoryGroupPresenter(category, cheatSheets.toArray(new CheatSheet[cheatSheets.size()])));
		}
		mAdapter.replaceAll(data);
		mAdapter.setShowRecents(TextUtils.isEmpty(query));
		return true;
	}
	
	private void onUpdate() {
		for(int i = 0; i < mCategoryContainer.getChildCount(); i++) {
			CategoryGroupHolder holder = (CategoryGroupHolder) mCategoryContainer.getChildViewHolder(mCategoryContainer.getChildAt(i));
			holder.onUpdate();
		}
		addRecents(mCategories);
	}
	
	private void setupWithData(Category[] data) {
		mCategories = data;
		mLoaderView.setState(LoaderView.STATE_IDLE);
		mAdapter.add(CategoryGroupPresenter.create(mCategories));
		addRecents(data);
	}
	
	public void addRecents(Category[] data) {
		if (data == null || data.length == 0) return;
		
		List<Integer> locals = API.getCachedIds();
		for (Category category : data) {
			for (CheatSheet cheatSheet : category.cheat_sheets) {
				if (locals.contains(cheatSheet.id)) cheatSheet.isLocal = true;
			}
		}
		
		Category recents = API.addRecentlyOpened(data);
		if(recents == null) return;
		mAdapter.setRecents(new CategoryGroupPresenter(recents, recents.cheat_sheets));
	}
	
	private DataCallback<Category[]> getCallback() {
		return new DataCallback<Category[]>() {
			@Override
			public void init() {
				super.init();
				Button retryButton = ButterKnife.findById(mLoaderView, R.id.loaderview_retry);
				retryButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (retry()) mLoaderView.setState(LoaderView.STATE_LOADING);
					}
				});
			}
			
			@Override
			public void onData(Category[] data) {
				setupWithData(data);
			}
			
			@Override
			public void onError(Throwable t) {
				TextView errorText = ButterKnife.findById(mLoaderView, R.id.loaderview_errormsg);
				errorText.setText(t.getMessage());
				mLoaderView.setState(LoaderView.STATE_ERROR);
				
			}
		};
	}
}
