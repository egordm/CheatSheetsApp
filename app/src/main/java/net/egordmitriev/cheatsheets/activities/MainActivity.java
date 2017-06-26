package net.egordmitriev.cheatsheets.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.api.API;
import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.utils.DataCallback;
import net.egordmitriev.cheatsheets.widgets.CategoryGroupHolder;
import net.egordmitriev.cheatsheets.widgets.CustomLoaderView;
import net.egordmitriev.loaderview.LoaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends SearchBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.dataContainer)
    LinearLayout mCategoryContainer;

    @BindView(R.id.loaderview)
    CustomLoaderView mLoaderView;

    private List<Category> mCategories;
    private List<CategoryGroupHolder> mHolders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoaderView.setState(LoaderView.STATE_LOADING);
        mSearchView.setQueryHint("Search for cheatsheets");
        API.requestCategories(getCallback());
    }

    @Override
    protected void onResume() {
        super.onResume();
        addRecents(mCategories);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (mCategories == null) return false;
        for(CategoryGroupHolder holder : mHolders) {
            holder.applyQuery(query);
        }
        return true;
    }

    private void setupWithData(List<Category> data) {
        mCategories = data;
        mHolders = new ArrayList<>();
        mCategoryContainer.removeAllViews();
        addRecents(data);
        for (Category category : mCategories) {
           addCategory(category);
        }
        mLoaderView.setState(LoaderView.STATE_IDLE);
    }

    public void addCategory(Category category) {
        View view = CategoryGroupHolder.inflate(getLayoutInflater(), mCategoryContainer, true);
        CategoryGroupHolder holder = new CategoryGroupHolder(this, view);
        holder.onBind(category);
        mHolders.add(holder);
        holder.collapse(false, true);
    }

    public void addRecents(List<Category> data) {
        if(data == null || data.isEmpty()) return;
        if(mHolders != null && !mHolders.isEmpty()) {
            for(CategoryGroupHolder holder : mHolders) {
                if(holder.getCategory().temp) mCategoryContainer.removeView(holder.getView());
            }
        }

        Category recents = API.addRecentlyOpened(data);
        View view = CategoryGroupHolder.inflate(getLayoutInflater(), mCategoryContainer, false);
        mCategoryContainer.addView(view, 0);
        CategoryGroupHolder holder = new CategoryGroupHolder(this, view);
        holder.onBind(recents);
        mHolders.add(0, holder);
        holder.collapse(false, true);

    }

    private DataCallback<ArrayList<Category>> getCallback() {
        return new DataCallback<ArrayList<Category>>() {
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
            public void onData(ArrayList<Category> data) {
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
