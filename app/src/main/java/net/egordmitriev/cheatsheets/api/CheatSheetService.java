package net.egordmitriev.cheatsheets.api;

import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by EgorDm on 22-Jun-2017.
 */

public interface CheatSheetService {

    @GET("/")
    Call<List<Category>> getCategories();

    @GET("cheatsheet/{id}")
    Call<CheatSheet> getCheatSheet(@Path("id") int cheatSheetId);
}
