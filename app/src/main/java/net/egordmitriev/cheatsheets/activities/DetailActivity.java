package net.egordmitriev.cheatsheets.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

import net.egordmitriev.cheatsheets.api.API;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;

/**
 * Created by EgorDm on 14-Jun-2017.
 */

public class DetailActivity extends SearchBarActivity {

    public static final String CHEATSHEET_SLUG_KEY = "cheatsheet_slug_key";

    protected CheatSheet mCheatSheet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent() != null) {
            String slug = getIntent().getStringExtra(CHEATSHEET_SLUG_KEY);
            mCheatSheet = API.getCheatSheet(slug);
        }
        if (mCheatSheet == null) {
            finish();
            return;
        }

        mSearchView.setQueryHint("Search in "+mCheatSheet.title);

        Logger.d(mCheatSheet.title);


        //TODO: parcelable data
    }
}
