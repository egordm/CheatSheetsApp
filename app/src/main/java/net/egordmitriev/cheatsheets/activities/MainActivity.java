package net.egordmitriev.cheatsheets.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.orhanobut.logger.Logger;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.adapters.CategoryListAdapter;
import net.egordmitriev.cheatsheets.api.API;

import butterknife.BindView;

public class MainActivity extends SearchBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CheatSheets","waaat");
        Logger.d("Hello world");
        //Logger.json(API.sGson.toJson(API.getData()));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new CategoryListAdapter(API.getData()));

    }


}
