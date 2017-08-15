package net.egordmitriev.cheatsheets.api;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;

import net.egordmitriev.cheatsheets.api.RegistryContract.CategoryEntry;
import net.egordmitriev.cheatsheets.api.RegistryContract.CheatSheetEntry;
import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.pojo.CheatGroup;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	private static final String[] categoryProjection = {
			CheatSheetEntry.FULL_ID, CheatSheetEntry.fullColumn(CheatSheetEntry.COLUMN_NAME_TITLE), CheatSheetEntry.fullColumn(CheatSheetEntry.COLUMN_NAME_SUBTITLE),
			CheatSheetEntry.fullColumn(CheatSheetEntry.COLUMN_NAME_DESCRIPTION), CheatSheetEntry.COLUMN_NAME_TAGS, CheatSheetEntry.COLUMN_NAME_LOCAL,
			CategoryEntry.FULL_ID, CategoryEntry.fullColumn(CategoryEntry.COLUMN_NAME_TITLE), CategoryEntry.fullColumn(CategoryEntry.COLUMN_NAME_DESCRIPTION)
	};
	
	public boolean tryPutCategories(List<Category> categories) {
		mDatabase.beginTransaction();
		try {
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
	
	public void putCategory(Category category) {
		SQLiteStatement catInsert = mDatabase.compileStatement(RegistryContract.SQL_UPSERT_CATEGORY);
		catInsert.bindLong(1, category.id);
		bindString(catInsert, 2, category.title);
		bindString(catInsert, 3, category.description);
		catInsert.execute();
		
		for (CheatSheet cheatSheet : category.cheat_sheets) {
			putCheatSheet(cheatSheet, category.id);
		}
	}
	
	
	public void putCheatSheet(CheatSheet cheatSheet, int categoryId) {
		SQLiteStatement catInsert = mDatabase.compileStatement(RegistryContract.SQL_UPSERT_CHEAT_SHEET);
		catInsert.bindLong(1, cheatSheet.id);
		catInsert.bindLong(2, categoryId);
		bindString(catInsert, 3, cheatSheet.title);
		bindString(catInsert, 4, cheatSheet.subtitle);
		bindString(catInsert, 5, cheatSheet.description);
		bindString(catInsert, 6, TextUtils.join(",", cheatSheet.tags));
		catInsert.execute();
	}
	
	public List<Category> getCategories() {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(CheatSheetEntry.TABLE_NAME + " LEFT OUTER JOIN " + CategoryEntry.TABLE_NAME + " ON " +
				CheatSheetEntry.COLUMN_NAME_CATEGORY_ID + " = " + CategoryEntry.FULL_ID);
		String orderBy = CategoryEntry.FULL_ID + " ASC";
		
		Cursor cursor = builder.query(mDatabase, categoryProjection, null, null, null, null, orderBy);
		
		List<Category> ret = new ArrayList<>();
		Category current = null;
		while (cursor.moveToNext()) {
			if (current == null || current.id != cursor.getInt(5)) {
				current = new Category(cursor.getInt(6), cursor.getString(7), cursor.getString(8), new ArrayList<CheatSheet>());
				ret.add(current);
			}
			CheatSheet cs = new CheatSheet(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
					new ArrayList<CheatGroup>(), Arrays.asList(cursor.getString(4).split(",")));
			cs.isLocal = cursor.getInt(5) > 0;
			current.cheat_sheets.add(cs);
		}
		return ret;
	}
	
	private void bindString(SQLiteStatement stmt, int index, String s) {
		if (s == null) {
			stmt.bindNull(index);
		} else {
			stmt.bindString(index, s);
		}
	}
}
