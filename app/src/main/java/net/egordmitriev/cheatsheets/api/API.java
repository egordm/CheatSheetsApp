package net.egordmitriev.cheatsheets.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.egordmitriev.cheatsheets.BuildConfig;
import net.egordmitriev.cheatsheets.CheatSheetsApp;
import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;
import net.egordmitriev.cheatsheets.utils.Constants;
import net.egordmitriev.cheatsheets.utils.DataCallback;
import net.egordmitriev.cheatsheets.utils.PreferenceManager;
import net.egordmitriev.cheatsheets.utils.Utils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;

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
        ArrayList<Category> ret = null;
        if (Constants.USE_CACHE) {
            ret = Utils.readCache(Constants.CACHE_FILENAME_CATEGORIES, retType);
        }
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
        Call<ArrayList<Category>> call = sService.getCategories(Constants.BETA_BUILD ? 1 : 0);
        call.enqueue(callback);
    }

    public static void requestCheatSheet(DataCallback<CheatSheet> callback, final int cheatSheetId) {
        Type retType = new TypeToken<CheatSheet>() {
        }.getType();
        CheatSheet ret = null;
        if (Constants.USE_CACHE) {
            ret = Utils.readCache(Constants.CACHE_FILENAME_CHEATSHEET + cheatSheetId, retType);
        }
        if (ret != null) {
            callback.onData(ret);
            return;
        }
        callback.setInterceptor(new DataCallback.SuccessInterceptor<CheatSheet>() {
            @Override
            public void success(CheatSheet data) {
                Utils.writeCache(data, Constants.CACHE_FILENAME_CHEATSHEET + cheatSheetId);
                if (sCachedIds != null && !sCachedIds.contains(cheatSheetId))
                    sCachedIds.add(cheatSheetId);
            }
        });
        Call<CheatSheet> call = sService.getCheatSheet(cheatSheetId);
        call.enqueue(callback);
    }

    private static List<Integer> sCachedIds = null;

    public static List<Integer> getCachedIds() {
        if (sCachedIds != null) return sCachedIds;

        sCachedIds = new ArrayList<>();
        File cacheDir = CheatSheetsApp.getAppContext().getCacheDir();
        for (File f : cacheDir.listFiles()) {
            if (f.isFile()) {
                String name = f.getName();
                if (!name.startsWith(Constants.CACHE_FILENAME_CHEATSHEET)) continue;
                Matcher matcher = Constants.CHEATSHEET_ID_REGEX.matcher(name);
                while (matcher.find()) {
                    sCachedIds.add(Integer.parseInt(matcher.group(1)));
                }
            }
        }

        return sCachedIds;
    }

    public static Category addRecentlyOpened(List<Category> categories) {
        List<Integer> recentIds = PreferenceManager.getInstance().getRecentlyOpened();
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
