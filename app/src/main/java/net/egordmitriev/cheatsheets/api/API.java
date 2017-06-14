package net.egordmitriev.cheatsheets.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.egordmitriev.cheatsheets.CheatSheets;
import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.pojo.Category;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class API {
    public static Gson sGson = new Gson();

    public static List<Category> getCategories() {
        //todo: request data
        try {
            return readDataLocally();
        } catch (IOException e) {
            e.printStackTrace(); //TODO: handle exception correctly
        }
        return new ArrayList<>();
    }

    private static List<Category> readDataLocally() throws IOException {
        //TODO: database? no json file?
        InputStream rawData = CheatSheets.getAppContext().getResources().openRawResource(R.raw.data);
        Reader reader = new BufferedReader(new InputStreamReader(rawData, "UTF8"));

        Type retType = new TypeToken<ArrayList<Category>>(){}.getType();
        return sGson.fromJson(reader, retType);
    }

    public static CheatSheet getCheatSheet(String slug) {
        CheatSheet ret = getCheatsheetLocally(slug);
        if(ret != null) return ret;
        return requestCheatsheet(slug);
    }

    private static CheatSheet requestCheatsheet(String slug) {
        return null; //TODO request
    }

    private static CheatSheet getCheatsheetLocally(String slug) {
        for(Category category : getCategories()) { //TODO file or db
            for(CheatSheet cheatSheet : category.sheets) {
                if (cheatSheet.slug.equals(slug)) return cheatSheet;
            }
        }
        return null;
    }
}
