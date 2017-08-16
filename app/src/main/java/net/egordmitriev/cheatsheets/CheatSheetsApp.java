package net.egordmitriev.cheatsheets;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import net.egordmitriev.cheatsheets.api.Registry;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class CheatSheetsApp extends Application {
	
	private static Context sContext;
	private static Registry sRegistry;
	
	@Override
	public void onCreate() {
		super.onCreate();
		sContext = getApplicationContext();
		Logger.addLogAdapter(new AndroidLogAdapter());
		sRegistry = new Registry(sContext);
	}
	
	public static Context getAppContext() {
		return sContext;
	}
	
	public static Registry getRegistry() {
		return sRegistry;
	}
	
	public static boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		sRegistry.close();
	}
}
