package net.egordmitriev.cheatsheets.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.LinearLayout;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.api.API;
import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;
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
        boolean empty = (query == null || query.isEmpty());
        for(int i = 0; i < mCategories.size(); i++) {
            CategoryGroupHolder holder = mHolders.get(i);
            boolean matchesCategory = empty || mCategories.get(i).matchesString(query, false);
            if(matchesCategory || mCategories.get(i).matchesString(query, true)) {
                holder.collapse(false, true);
                holder.getView().setVisibility(View.VISIBLE);
                for(int j = 0; j < mCategories.get(i).sheets.size(); j++) {
                    CheatSheet cheatSheet = mCategories.get(i).sheets.get(j);
                    holder.getSheetsList().getChildAt(j).setVisibility(
                            (matchesCategory || cheatSheet.matchesString(query, false) ) ? View.VISIBLE : View.GONE
                    );
                }
            } else {
                holder.collapse(true, true);
                mHolders.get(i).getView().setVisibility(View.GONE);
            }


        }
        return true;
    }
}
