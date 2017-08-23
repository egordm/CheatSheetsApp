package net.egordmitriev.cheatsheets.utils;

import android.content.Context;
import android.content.SharedPreferences;

import net.egordmitriev.cheatsheets.CheatSheetsApp;

import java.util.Date;

/**
 * Created by EgorDm on 23-Jun-2017.
 */

public class PreferenceManager {
	public static final String PREFS_NAME = "CHEATSHEETS_PREFERENCES";
	public static final String CATEGORY_CACHE = "CATEGORY_CACHE";
	
	private static PreferenceManager sPreferenceManager;
	
	public static PreferenceManager getInstance() {
		if (sPreferenceManager == null) {
			sPreferenceManager = new PreferenceManager();
		}
		return sPreferenceManager;
	}
	
	private final SharedPreferences mPreferences;
	
	public PreferenceManager() {
		mPreferences = CheatSheetsApp.getAppContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	}
	
	public void putCategoryExpires() {
		SharedPreferences.Editor editor = getEditor();
		editor.putLong(CATEGORY_CACHE, System.currentTimeMillis() + Constants.CACHE_LIFETIME);
		editor.commit();
	}
	
	public Date getCategoryExpires() {
		long time = mPreferences.getLong(CATEGORY_CACHE, -1L);
		return time == -1L ? null : new Date(time);
	}
	
	private SharedPreferences.Editor getEditor() {
		return mPreferences.edit();
	}
}
