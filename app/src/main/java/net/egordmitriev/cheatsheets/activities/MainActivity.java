package net.egordmitriev.cheatsheets.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.LinearLayout;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.api.API;
import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.widgets.CategoryGroupHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends SearchBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.categoryContainer)
    LinearLayout mCategoryContainer;

    private List<Category> mCategories;
    private List<CategoryGroupHolder> mHolders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategories = API.getData();
        mHolders = new ArrayList<>();
        for(Category category : mCategories) {
            View view = CategoryGroupHolder.inflate(getLayoutInflater(), mCategoryContainer);
            CategoryGroupHolder holder = new CategoryGroupHolder(this, view);
            holder.onBind(category);
            mHolders.add(holder);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        for(int i = 0; i < mCategories.size(); i++) {
            mHolders.get(i).getView().setVisibility(
                    (mCategories.get(i).matchesString(query)) ? View.VISIBLE : View.GONE
            );
        }
        //mListAdapter.onQuery(query);
        return true;
    }
}
