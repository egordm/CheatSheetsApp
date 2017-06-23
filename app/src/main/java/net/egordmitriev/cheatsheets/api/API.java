package net.egordmitriev.cheatsheets.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.egordmitriev.cheatsheets.CheatSheetsApp;
import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;
import net.egordmitriev.cheatsheets.utils.Constants;
import net.egordmitriev.cheatsheets.utils.DataCallback;
import net.egordmitriev.cheatsheets.utils.PreferenceManager;
import net.egordmitriev.cheatsheets.utils.Utils;

import java.lang.reflect.Type;
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

    public static void requestCategories(DataCallback<ArrayList<Category>> callback) {
        Type retType = new TypeToken<ArrayList<Category>>() {
        }.getType();
        ArrayList<Category> ret = Utils.readCache(Constants.CACHE_FILENAME_CATEGORIES, retType);
        if (ret != null) {
            callback.onData(ret);
            return;
        }
        callback.setInterceptor(new DataCallback.SuccessInterceptor<ArrayList<Category>>() {
            @Override
            public void success(ArrayList<Category> data) {
                Utils.writeCache(data, Constants.CACHE_FILENAME_CATEGORIES);
            }
        });
        Call<ArrayList<Category>> call = sService.getCategories(); //TODO: save locally
        call.enqueue(callback);
    }

    public static void requestCheatSheet(DataCallback<CheatSheet> callback, final int cheatSheetId) {
        Type retType = new TypeToken<CheatSheet>() {
        }.getType();
        CheatSheet ret = Utils.readCache(Constants.CACHE_FILENAME_CHEATSHEET + cheatSheetId, retType);
        if (ret != null) {
            callback.onData(ret);
            return;
        }
        callback.setInterceptor(new DataCallback.SuccessInterceptor<CheatSheet>() {
            @Override
            public void success(CheatSheet data) {
                Utils.writeCache(data, Constants.CACHE_FILENAME_CHEATSHEET + cheatSheetId);
            }
        });
        Call<CheatSheet> call = sService.getCheatSheet(cheatSheetId);
        call.enqueue(callback);
    }

    public static ArrayList<Category> addRecentlyOpened(ArrayList<Category> categories) {
        List<Integer> recentIds = PreferenceManager.getInstance().getRecentlyOpened();
        if (recentIds.size() == 0) return categories;

        List<CheatSheet> recents = new ArrayList<>();
        for (int id : recentIds) {
            CheatSheet temp = Category.getCheatSheet(id, categories);
            if (temp != null) recents.add(temp);
        }
        if (recents.size() == 0) return categories;

        categories.add(0, new Category(
                CheatSheetsApp.getAppContext().getString(R.string.recently_opened),
                null,
                recents
        ));
        return categories;
    }
}
