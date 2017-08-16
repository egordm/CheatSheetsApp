package net.egordmitriev.cheatsheets.utils;

import android.content.Context;
import android.content.SharedPreferences;

import net.egordmitriev.cheatsheets.CheatSheetsApp;

/**
 * Created by EgorDm on 23-Jun-2017.
 */

public class PreferenceManager {
	public static final String PREFS_NAME = "CHEATSHEETS_PREFERENCES";
	
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
	
	private SharedPreferences.Editor getEditor() {
		return mPreferences.edit();
	}
}
