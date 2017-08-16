package net.egordmitriev.cheatsheets.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by egordm on 15-8-2017.
 */

public class RegistryDbHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 2;
	public static final String DATABASE_NAME = "cs_registry.db";
	
	public RegistryDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(RegistryContract.SQL_CREATE_CATEGORIES);
		db.execSQL(RegistryContract.SQL_CREATE_CHEAT_SHEETS);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(deleteQuery(RegistryContract.CheatSheetEntry.TABLE_NAME));
		db.execSQL(deleteQuery(RegistryContract.CategoryEntry.TABLE_NAME));
		onCreate(db);
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
	
	public static String deleteQuery(String table) {
		return "DROP TABLE IF EXISTS " + table;
	}
}
