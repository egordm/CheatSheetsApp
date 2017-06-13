package net.egordmitriev.cheatsheets.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.adapters.CategoryListAdapter;
import net.egordmitriev.cheatsheets.api.API;
import net.egordmitriev.cheatsheets.widgets.AdvancedRecyclerView;

import butterknife.BindView;

public class MainActivity extends SearchBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.recycler)
    AdvancedRecyclerView mRecyclerView;

    CategoryListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Logger.json(API.sGson.toJson(API.getData()));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mListAdapter = new CategoryListAdapter(this, mRecyclerView, API.getData());
        mRecyclerView.setAdapter(mListAdapter);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mListAdapter.onQuery(query);
        return true;
    }
}
