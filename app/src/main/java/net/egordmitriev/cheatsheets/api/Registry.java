package net.egordmitriev.cheatsheets.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;

import net.egordmitriev.cheatsheets.CheatSheetsApp;
import net.egordmitriev.cheatsheets.api.RegistryContract.CategoryEntry;
import net.egordmitriev.cheatsheets.api.RegistryContract.CheatSheetEntry;
import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.pojo.CheatGroup;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.egordmitriev.cheatsheets.utils.Constants.CACHE_LIFETIME;

/**
 * Created by egordm on 15-8-2017.
 */

public class Registry {
	private RegistryDbHelper mDbHelper;
	private SQLiteDatabase mDatabase;
	
	public Registry(Context context) {
		mDbHelper = new RegistryDbHelper(context);
		mDatabase = mDbHelper.getWritableDatabase();
	}
	
	public void close() {
		mDatabase.close();
	}
	
	public boolean tryPutCategories(Category[] categories) {
		mDatabase.beginTransaction();
		try {
			mDatabase.delete(CategoryEntry.TABLE_NAME, null, null);
			for (Category category : categories) {
				putCategory(category);
			}
			mDatabase.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			mDatabase.endTransaction();
		}
		return true;
	}
	
	public long putCategory(Category category) {
		ContentValues vals = new ContentValues();
		vals.put(CategoryEntry._ID, category.id);
		vals.put(CategoryEntry.TITLE, category.title);
		vals.put(CategoryEntry.DESCRIPTION, category.description);
		long ret = mDatabase.insertWithOnConflict(CategoryEntry.TABLE_NAME, null, vals, SQLiteDatabase.CONFLICT_REPLACE);
		
		for (CheatSheet cheatSheet : category.cheat_sheets) {
			putCheatSheet(cheatSheet, category.id);
		}
		return ret;
	}
	
	public long putCheatSheet(CheatSheet cheatSheet, int categoryId) {
		ContentValues vals = new ContentValues();
		vals.put(CheatSheetEntry.CATEGORY_ID, categoryId);
		vals.put(CheatSheetEntry._ID, cheatSheet.id);
		vals.put(CheatSheetEntry.TYPE, cheatSheet.type);
		vals.put(CheatSheetEntry.TITLE, cheatSheet.title);
		vals.put(CheatSheetEntry.SUBTITLE, cheatSheet.subtitle);
		vals.put(CheatSheetEntry.DESCRIPTION, cheatSheet.description);
		vals.put(CheatSheetEntry.TAGS, TextUtils.join(",", cheatSheet.tags));
		return mDatabase.insertWithOnConflict(CheatSheetEntry.TABLE_NAME, null, vals, SQLiteDatabase.CONFLICT_REPLACE);
	}
	
	public void updateCheatSheetContent(int id, String content) {
		ContentValues vals = new ContentValues();
		vals.put(CheatSheetEntry.CONTENT, content);
		vals.put(CheatSheetEntry.LAST_SYNC, System.currentTimeMillis());
		
		String selection = CheatSheetEntry._ID + " = ?";
		String[] selectionArgs = {String.valueOf(id)};
		
		mDatabase.update(CheatSheetEntry.TABLE_NAME, vals, selection, selectionArgs);
	}
	
	public void updateCheatSheetsUsed(int id) {
		ContentValues values = new ContentValues();
		values.put(CheatSheetEntry.LAST_USED, System.currentTimeMillis());
		String selection = CheatSheetEntry._ID + " = ?";
		String[] selectionArgs = {String.valueOf(id)};
		
		mDatabase.update(CheatSheetEntry.TABLE_NAME, values, selection, selectionArgs);
	}
	
	public Category[] getCategories() {
		int categoryID;
		
		SparseArray<List<CheatSheet>> cheatSheets = new SparseArray<>();
		Cursor cursor = mDatabase.query(CheatSheetEntry.TABLE_NAME, CheatSheetEntry.PROJECTION,
				null, null, null, null, CheatSheetEntry.CATEGORY_ID + " ASC");
		while (cursor.moveToNext()) {
			categoryID = cursor.getInt(0);
			if (cheatSheets.get(categoryID) == null)
				cheatSheets.append(categoryID, new ArrayList<CheatSheet>());
			cheatSheets.get(categoryID).add(
					new CheatSheet(cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4),
							cursor.getString(5), null, cursor.getString(6).split(","), new Date(cursor.getLong(1)))
			);
		}
		cursor.close();
		
		
		cursor = mDatabase.query(CategoryEntry.TABLE_NAME, CategoryEntry.PROJECTION,
				null, null, null, null, CategoryEntry._ID + " ASC");
		Category[] ret = new Category[cursor.getCount()];
		int index = 0;
		while (cursor.moveToNext()) {
			categoryID = cursor.getInt(0);
			List<CheatSheet> cs = cheatSheets.get(categoryID);
			ret[index] = new Category(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
					(cs != null) ? cs.toArray(new CheatSheet[cs.size()]) : null);
			index++;
		}
		cursor.close();
		
		return ret;
	}
	
	public List<Integer> getCheatSheetsCached() {
		Cursor cursor = mDatabase.query(CheatSheetEntry.TABLE_NAME, new String[]{CheatSheetEntry._ID},
				RegistryContract.SQL_WHERE_CACHED, null, null, null, null);
		List<Integer> ret = new ArrayList<>();
		while (cursor.moveToNext()) {
			ret.add(cursor.getInt(0));
		}
		cursor.close();
		return ret;
	}
	
	public List<Integer> getCheatSheetsRecent(int count) {
		Cursor cursor = mDatabase.query(CheatSheetEntry.TABLE_NAME, new String[]{CheatSheetEntry._ID},
				CheatSheetEntry.LAST_USED + " > 0", null, null, null,
				CheatSheetEntry.LAST_USED + " DESC", String.valueOf(count));
		List<Integer> ret = new ArrayList<>();
		while (cursor.moveToNext()) {
			ret.add(cursor.getInt(0));
		}
		cursor.close();
		return ret;
	}
	
	private static final Type contentType = new TypeToken<CheatGroup[]>() {
	}.getType();
	
	public CheatSheet getCheatSheetContent(int id) {
		String selection = CheatSheetEntry._ID + "=" + id;
		if (CheatSheetsApp.isNetworkAvailable()) {
			selection += " and " + CheatSheetEntry.LAST_SYNC + ">" + (System.currentTimeMillis() - CACHE_LIFETIME);
		}
		Cursor cursor = mDatabase.query(CheatSheetEntry.TABLE_NAME, CheatSheetEntry.PROJECTION_CONTENT,
				selection, null, null, null, null, "1");
		try {
			if (cursor.getCount() == 0) return null;
			cursor.moveToNext();
			CheatGroup[] cheat_groups = API.sGson.fromJson(cursor.getString(6), contentType);
			return new CheatSheet(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
					cursor.getString(3), cursor.getString(4), cheat_groups, cursor.getString(5).split(","),
					new Date(cursor.getLong(7)));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}
		return null;
	}
}
