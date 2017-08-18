package net.egordmitriev.cheatsheets;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import net.egordmitriev.cheatsheets.activities.DetailActivity;
import net.egordmitriev.cheatsheets.activities.PDFActivity;
import net.egordmitriev.cheatsheets.api.Registry;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;

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
	
	public static void openCheatSheet(Activity activity, CheatSheet cheatSheet) {
		Intent intent;
		if(cheatSheet.type == CheatSheet.TYPE_PDF) {
			intent = new Intent(activity, PDFActivity.class);
		} else {
			intent = new Intent(activity, DetailActivity.class);
		}
		intent.putExtra(DetailActivity.CHEATSHEET_ID_KEY, cheatSheet.id);
		activity.startActivity(intent);
	}
}
