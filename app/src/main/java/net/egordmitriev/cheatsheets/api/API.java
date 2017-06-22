package net.egordmitriev.cheatsheets.api;

import com.google.gson.Gson;

import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;
import net.egordmitriev.cheatsheets.utils.Constants;
import net.egordmitriev.cheatsheets.utils.DataCallback;
import net.egordmitriev.cheatsheets.utils.Utils;

import java.util.ArrayList;

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
        ArrayList<Category> ret = Utils.readCache(Constants.CACHE_FILENAME_CATEGORIES);
        if (ret != null) callback.onData(ret);
        Call<ArrayList<Category>> call = sService.getCategories();
        call.enqueue(callback);
    }

    public static void requestCheatSheet(DataCallback<CheatSheet> callback, int cheatSheetId) {
        CheatSheet ret = Utils.readCache(Constants.CACHE_FILENAME_CHEATSHEET + cheatSheetId);
        if (ret != null) callback.onData(ret);
        Call<CheatSheet> call = sService.getCheatSheet(cheatSheetId);
        call.enqueue(callback);
    }
}
