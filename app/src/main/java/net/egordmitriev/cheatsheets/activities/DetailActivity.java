package net.egordmitriev.cheatsheets.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.adapters.CheatsheetAdapter;
import net.egordmitriev.cheatsheets.api.API;
import net.egordmitriev.cheatsheets.pojo.CheatGroup;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;
import net.egordmitriev.cheatsheets.utils.DataCallback;
import net.egordmitriev.cheatsheets.utils.PreferenceManager;
import net.egordmitriev.cheatsheets.widgets.CustomLoaderView;
import net.egordmitriev.loaderview.LoaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EgorDm on 14-Jun-2017.
 */

public class DetailActivity extends SearchBarActivity {

    public static final String CHEATSHEET_ID_KEY = "cheatsheet_id_key";

    @BindView(R.id.dataContainer)
    RecyclerView mCheatsheetContainer;

    @BindView(R.id.loaderview)
    CustomLoaderView mLoaderView;

    protected CheatSheet mCheatSheet;

    protected CheatsheetAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoaderView.setState(LoaderView.STATE_LOADING);
        int id = -1;
        if(getIntent() != null) {
            id = getIntent().getIntExtra(CHEATSHEET_ID_KEY, -1);
        }
        if (id == -1) {
            finish();
            return;
        }
        PreferenceManager.getInstance().putRecentlyOpened(id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mCheatsheetContainer.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CheatsheetAdapter(this);
        mCheatsheetContainer.setAdapter(mAdapter);

        API.requestCheatSheet(getCallback(), id);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(mCheatSheet == null) return false;
        List<CheatGroup> data = new ArrayList<>();
        for (CheatGroup cheatGroup : mCheatSheet.cheat_groups) {
            CheatGroup temp = cheatGroup.applyQuery(query);
            if (temp != null) data.add(temp);
        }

        mAdapter.replaceAll(data);
        return true;
    }

    private void setupWithData(CheatSheet data) {
        mCheatSheet = data;
        mSearchView.setQueryHint("Search in "+mCheatSheet.title);
        mAdapter.add(mCheatSheet.cheat_groups);
        mLoaderView.setState(LoaderView.STATE_IDLE);
    }

    private DataCallback<CheatSheet> getCallback() {
        return new DataCallback<CheatSheet>() {
            @Override
            public void init() {
                super.init();
                Button retryButton = ButterKnife.findById(mLoaderView, R.id.loaderview_retry);
                retryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (retry()) {
                            mLoaderView.setState(LoaderView.STATE_LOADING);
                        }
                    }
                });
            }

            @Override
            public void onData(CheatSheet data) {
                setupWithData(data);
            }

            @Override
            public void onError(Throwable t) {
                TextView errorText = ButterKnife.findById(mLoaderView, R.id.loaderview_errormsg);
                errorText.setText(t.getMessage());
                mLoaderView.setState(LoaderView.STATE_ERROR, true);
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
