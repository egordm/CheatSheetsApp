package net.egordmitriev.cheatsheets.api;

import android.provider.BaseColumns;
import android.text.TextUtils;

/**
 * Created by egordm on 15-8-2017.
 */

public class RegistryContract {
	private RegistryContract() {
	}
	
	public static class CategoryEntry implements BaseColumns {
		public static final String TABLE_NAME = "categories";
		public static final String FULL_ID = TABLE_NAME + "." + _ID;
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		
		public static final String[] PROJECTION = {
				_ID,
				COLUMN_NAME_TITLE,
				COLUMN_NAME_DESCRIPTION
		};
		
		public static String fullColumn(String column) {
			return TABLE_NAME + "." + column;
		}
	}
	
	public static class CheatSheetEntry implements BaseColumns {
		public static final String TABLE_NAME = "cheat_sheets";
		public static final String FULL_ID = TABLE_NAME + "." + _ID;
		public static final String COLUMN_NAME_CATEGORY_ID = "category_id";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_SUBTITLE = "subtitle";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		public static final String COLUMN_NAME_TAGS = "tags";
		public static final String COLUMN_NAME_LOCAL = "local";
		
		public static final String[] PROJECTION = {
				_ID,
				COLUMN_NAME_CATEGORY_ID,
				COLUMN_NAME_TITLE,
				COLUMN_NAME_SUBTITLE,
				COLUMN_NAME_DESCRIPTION,
				COLUMN_NAME_TAGS
		};
		
		public static String fullColumn(String column) {
			return TABLE_NAME + "." + column;
		}
	}
	
	public static final String SQL_CREATE_CATEGORIES = "CREATE TABLE " + CategoryEntry.TABLE_NAME + "(" +
			CategoryEntry._ID + " INTEGER PRIMARY KEY," +
			CategoryEntry.COLUMN_NAME_TITLE + " VARCHAR(255)," +
			CategoryEntry.COLUMN_NAME_DESCRIPTION + " TEXT)";
	
	public static final String SQL_UPSERT_CATEGORY = "INSERT OR REPLACE INTO " + CategoryEntry.TABLE_NAME +
			" (" + TextUtils.join(",", CategoryEntry.PROJECTION) + ") values(?,?,?)";
	
	public static final String SQL_CREATE_CHEAT_SHEETS = "CREATE TABLE " + CheatSheetEntry.TABLE_NAME + "(" +
			CheatSheetEntry._ID + " INTEGER PRIMARY KEY," +
			CheatSheetEntry.COLUMN_NAME_TITLE + " VARCHAR(255)," +
			CheatSheetEntry.COLUMN_NAME_SUBTITLE + " VARCHAR(255)," +
			CheatSheetEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
			CheatSheetEntry.COLUMN_NAME_TAGS + " TEXT," +
			CheatSheetEntry.COLUMN_NAME_LOCAL + " BOOLEAN DEFAULT 0," +
			CheatSheetEntry.COLUMN_NAME_CATEGORY_ID + " INTEGER REFERENCES " + CheatSheetEntry.TABLE_NAME
			+ "(" + CategoryEntry._ID + ") ON DELETE CASCADE)";
	
	public static final String SQL_UPSERT_CHEAT_SHEET = "INSERT OR REPLACE INTO " + CheatSheetEntry.TABLE_NAME +
			" (" + TextUtils.join(",", CheatSheetEntry.PROJECTION) + ") values(?,?,?,?,?,?)";
}
