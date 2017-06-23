package net.egordmitriev.cheatsheets.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.adapters.CheatsheetAdapter;
import net.egordmitriev.cheatsheets.api.API;
import net.egordmitriev.cheatsheets.pojo.Cheat;
import net.egordmitriev.cheatsheets.pojo.CheatGroup;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;
import net.egordmitriev.cheatsheets.utils.DataCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by EgorDm on 14-Jun-2017.
 */

public class DetailActivity extends SearchBarActivity {

    public static final String CHEATSHEET_ID_KEY = "cheatsheet_id_key";

    @BindView(R.id.dataContainer)
    RecyclerView mCheatsheetContainer;

    protected CheatSheet mCheatSheet;

    protected CheatsheetAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = -1;
        if(getIntent() != null) {
            id = getIntent().getIntExtra(CHEATSHEET_ID_KEY, -1);
            //mCheatSheet = API.getCheatSheet(slug);
        }
        if (id == -1) {
            finish();
            return;
        }

        Logger.d("Find sheet id:" + id);
        API.requestCheatSheet(getCallback(), id);


        mCheatsheetContainer.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CheatsheetAdapter(this);
        mCheatsheetContainer.setAdapter(mAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(mCheatSheet == null) return false;
        List<CheatGroup> data = new ArrayList<>();
        boolean empty = (query == null || query.isEmpty());
        for(int i = 0; i < mCheatSheet.cheat_groups.size(); i++) {
            boolean matchesCategory = empty || mCheatSheet.cheat_groups.get(i).matchesString(query, false);
            if(matchesCategory || mCheatSheet.cheat_groups.get(i).matchesString(query, true)) {
                //TODO: expand
                CheatGroup cheatGroup = mCheatSheet.cheat_groups.get(i).cloneMe();
                data.add(cheatGroup);
                for(int j = cheatGroup.cheats.size() - 1; j >= 0 ; j--) {
                    Cheat cheatSheet = cheatGroup.cheats.get(j);
                    if(!(matchesCategory || cheatSheet.matchesString(query, false))) {
                        cheatGroup.cheats.remove(j);
                    }
                }
            } else {
                //TODO: collapse
            }
        }
        mAdapter.replaceAll(data);
        return true;
    }

    private void setupWithData(CheatSheet data) {
        mCheatSheet = data;
        mSearchView.setQueryHint("Search in "+mCheatSheet.title);
        mAdapter.add(mCheatSheet.cheat_groups);
    }

    private DataCallback<CheatSheet> getCallback() {
        return new DataCallback<CheatSheet>() {
            @Override
            public void onData(CheatSheet data) {
                setupWithData(data);
            }

            @Override
            public void onError(Throwable t) {
                //TODO: show error;
            }
        };
    }
}
