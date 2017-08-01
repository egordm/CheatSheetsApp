package net.egordmitriev.cheatsheets.api;

import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by EgorDm on 22-Jun-2017.
 */

public interface CheatSheetService {

    @GET("api")
    Call<ArrayList<Category>> getCategories(@Query("beta") int beta);

    @GET("api/cheatsheet/{id}")
    Call<CheatSheet> getCheatSheet(@Path("id") int cheatSheetId);
}
