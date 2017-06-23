package net.egordmitriev.cheatsheets.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.LinearLayout;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.api.API;
import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;
import net.egordmitriev.cheatsheets.utils.DataCallback;
import net.egordmitriev.cheatsheets.widgets.CategoryGroupHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends SearchBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.dataContainer)
    LinearLayout mCategoryContainer;

    private List<Category> mCategories;
    private List<CategoryGroupHolder> mHolders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        API.requestCategories(getCallback());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(mCategories == null) return false;
        boolean empty = (query == null || query.isEmpty());
        for (int i = 0; i < mCategories.size(); i++) {
            CategoryGroupHolder holder = mHolders.get(i);
            boolean matchesCategory = empty || mCategories.get(i).matchesString(query, false);
            if (matchesCategory || mCategories.get(i).matchesString(query, true)) {
                holder.collapse(false, true);
                holder.getView().setVisibility(View.VISIBLE);
                for (int j = 0; j < mCategories.get(i).cheat_sheets.size(); j++) {
                    CheatSheet cheatSheet = mCategories.get(i).cheat_sheets.get(j);
                    holder.getSheetsList().getChildAt(j).setVisibility(
                            (matchesCategory || cheatSheet.matchesString(query, false)) ? View.VISIBLE : View.GONE
                    );
                }
            } else {
                holder.collapse(true, true);
                mHolders.get(i).getView().setVisibility(View.GONE);
            }
        }
        return true;
    }

    private void setupWithData(ArrayList<Category> data) {
        mSearchView.setQueryHint("Search for cheatsheets");
        mCategories = data; //TODO: switch between loader
        mHolders = new ArrayList<>();
        for (Category category : mCategories) {
            View view = CategoryGroupHolder.inflate(getLayoutInflater(), mCategoryContainer);
            CategoryGroupHolder holder = new CategoryGroupHolder(this, view);
            holder.onBind(category);
            mHolders.add(holder);
        }
    }

    private DataCallback<ArrayList<Category>> getCallback() {
        return new DataCallback<ArrayList<Category>>() {
            @Override
            public void onData(ArrayList<Category> data) {
                setupWithData(data);
            }

            @Override
            public void onError(Throwable t) {
                //TODO: show error;
            }
        };
    }
}
