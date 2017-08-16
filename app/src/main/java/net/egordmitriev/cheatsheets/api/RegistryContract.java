package net.egordmitriev.cheatsheets.api;

import android.provider.BaseColumns;

/**
 * Created by egordm on 15-8-2017.
 */

public class RegistryContract {
	private RegistryContract() {
	}
	
	public static class CategoryEntry implements BaseColumns {
		public static final String TABLE_NAME = "categories";
		public static final String TITLE = "title";
		public static final String DESCRIPTION = "description";
		
		public static final String[] PROJECTION = {
				_ID,
				TITLE,
				DESCRIPTION
		};
	}
	
	public static class CheatSheetEntry implements BaseColumns {
		public static final String TABLE_NAME = "cheat_sheets";
		public static final String CATEGORY_ID = "category_id";
		public static final String TYPE = "ctype";
		public static final String TITLE = "title";
		public static final String SUBTITLE = "subtitle";
		public static final String DESCRIPTION = "description";
		public static final String TAGS = "tags";
		public static final String LAST_USED = "last_used";
		public static final String CONTENT = "content";
		public static final String LAST_SYNC = "last_sync";
		
		public static final String[] PROJECTION = {
				CATEGORY_ID,
				_ID,
				TYPE,
				TITLE,
				SUBTITLE,
				DESCRIPTION,
				TAGS,
				LAST_SYNC
		};
		
		public static final String[] PROJECTION_CONTENT = {
				_ID,
				TYPE,
				TITLE,
				SUBTITLE,
				DESCRIPTION,
				TAGS,
				CONTENT,
				LAST_SYNC
		};
	}
	
	public static final String SQL_CREATE_CATEGORIES = "CREATE TABLE " + CategoryEntry.TABLE_NAME + "(" +
			CategoryEntry._ID + " INTEGER PRIMARY KEY," +
			CategoryEntry.TITLE + " VARCHAR(255)," +
			CategoryEntry.DESCRIPTION + " TEXT)";
	
	public static final String SQL_CREATE_CHEAT_SHEETS = "CREATE TABLE " + CheatSheetEntry.TABLE_NAME + "(" +
			CheatSheetEntry._ID + " INTEGER PRIMARY KEY," +
			CheatSheetEntry.TYPE + " INTEGER," +
			CheatSheetEntry.TITLE + " VARCHAR(255)," +
			CheatSheetEntry.SUBTITLE + " VARCHAR(255)," +
			CheatSheetEntry.DESCRIPTION + " TEXT," +
			CheatSheetEntry.TAGS + " TEXT," +
			CheatSheetEntry.CONTENT + " TEXT," +
			CheatSheetEntry.LAST_USED + " INTEGER," +
			CheatSheetEntry.LAST_SYNC + " INTEGER," +
			CheatSheetEntry.CATEGORY_ID + " INTEGER REFERENCES " + CheatSheetEntry.TABLE_NAME
			+ "(" + CategoryEntry._ID + ") ON DELETE CASCADE)";
	
	public static final String SQL_WHERE_CACHED = "(" + CheatSheetEntry.TYPE + "=0 and " + CheatSheetEntry.CONTENT +
			" not NULL) or (" + CheatSheetEntry.TYPE + "=1 and " + CheatSheetEntry.LAST_SYNC + " not NULL)";
}
