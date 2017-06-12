package net.egordmitriev.cheatsheets;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class CheatSheets extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        CheatSheets.context = getApplicationContext();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static Context getAppContext() {
        return CheatSheets.context;
    }
}
