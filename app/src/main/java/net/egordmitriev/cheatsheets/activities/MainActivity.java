package net.egordmitriev.cheatsheets.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;

import com.orhanobut.logger.Logger;

import net.egordmitriev.cheatsheets.api.API;

public class MainActivity extends SearchBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CheatSheets","waaat");
        Logger.d("Hello world");
        Logger.json(API.sGson.toJson(API.getData()));

    }


}
