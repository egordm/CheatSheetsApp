package net.egordmitriev.cheatsheets.api;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import net.egordmitriev.cheatsheets.CheatSheetsApp;
import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;
import net.egordmitriev.cheatsheets.utils.Constants;
import net.egordmitriev.cheatsheets.utils.DataCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class API {
	public static final Gson sGson = new Gson();
	public static final CheatSheetService sService = new Retrofit.Builder()
			.baseUrl(Constants.API_BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(CheatSheetService.class);
	
	private static List<Category> mCheatSheets = null;
	
	public static void requestCategories(DataCallback<ArrayList<Category>> callback) {
		if (mCheatSheets == null && Constants.USE_CACHE) {
			mCheatSheets = CheatSheetsApp.getRegistry().getCategories();
		}
		if (mCheatSheets != null && mCheatSheets.size() > 0) {
			callback.onData((ArrayList<Category>) mCheatSheets);
			return;
		}
		callback.setInterceptor(new DataCallback.SuccessInterceptor<ArrayList<Category>>() {
			@Override
			public void success(ArrayList<Category> data) {
				mCheatSheets = data;
				CheatSheetsApp.getRegistry().tryPutCategories(data);
			}
		});
		Call<ArrayList<Category>> call = sService.getCategories(Constants.BETA_BUILD ? 1 : 0);
		call.enqueue(callback);
	}
	
	public static void requestCheatSheet(DataCallback<CheatSheet> callback, final int cheatSheetId) {
		CheatSheet ret;
		if (Constants.USE_CACHE) {
			ret = CheatSheetsApp.getRegistry().getCheatSheetContent(cheatSheetId);
		}
		if (ret != null) {
			callback.onData(ret);
			return;
		}
		callback.setInterceptor(new DataCallback.SuccessInterceptor<CheatSheet>() {
			@Override
			public void success(CheatSheet data) {
				CheatSheetsApp.getRegistry().updateCheatSheetContent(cheatSheetId, sGson.toJson(data.cheat_groups));
				if (sCachedIds != null && !sCachedIds.contains(cheatSheetId)) {
					sCachedIds.add(cheatSheetId);
					Logger.d(cheatSheetId);
				}
			}
		});
		Call<CheatSheet> call = sService.getCheatSheet(cheatSheetId);
		call.enqueue(callback);
	}
	
	private static List<Integer> sCachedIds = null;
	
	public static List<Integer> getCachedIds() {
		if (sCachedIds != null) return sCachedIds;
		sCachedIds = CheatSheetsApp.getRegistry().getCheatSheetsCached();
		return sCachedIds;
	}
	
	public static Category addRecentlyOpened(List<Category> categories) {
		List<Integer> recentIds = CheatSheetsApp.getRegistry().getCheatSheetsRecent(10);
		if (recentIds.size() == 0) return null;
		for (int i = categories.size() - 1; i >= 0; i--) {
			if (categories.get(i).temp) categories.remove(i);
		}
		
		List<CheatSheet> recents = new ArrayList<>();
		for (int id : recentIds) {
			CheatSheet temp = Category.getCheatSheet(id, categories);
			if (temp != null) recents.add(temp);
		}
		if (recents.size() == 0) return null;
		
		Category ret = new Category(
				CheatSheetsApp.getAppContext().getString(R.string.recently_opened),
				null,
				recents
		);
		ret.temp = true;
		return ret;
	}
}
