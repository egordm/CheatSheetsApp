package net.egordmitriev.cheatsheets.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;

import net.egordmitriev.cheatsheets.R;

import butterknife.BindView;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class SearchBarActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    @BindView(R.id.search_view)
    SearchView mSearchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return onQueryTextSubmit(newText);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}
