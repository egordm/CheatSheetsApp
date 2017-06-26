package net.egordmitriev.cheatsheets.pojo;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class CheatGroup extends MatchableModel {
    public String title;
    public String description;
    public List<Cheat> cheats;
    public List<String> tags;
    public List<String> notes;

    public CheatGroup() {
    }

    public CheatGroup(String title, String description, List<Cheat> cheats, List<String> tags, List<String> notes) {
        this.title = title;
        this.description = description;
        this.cheats = cheats;
        this.tags = tags;
        this.notes = notes;
    }

    @Override
    protected List<String> getSearchableStrings(final boolean recursive) {
        List<String> ret =  new ArrayList<String>() {{
            add(title);
            add(description);
            addAll(tags);
            addAll(notes);
        }};
        if(recursive) {
            for (Cheat cheat : cheats) {
                ret.addAll(cheat.getSearchableStrings(false));
            }
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    public CheatGroup cloneMe() {
        return new CheatGroup(title, description, (List<Cheat>) ((ArrayList<Cheat>)cheats).clone(), tags, notes);
    }

    public CheatGroup applyQuery(String query) {
        if(TextUtils.isEmpty(query) || matchesString(query, false)) {
            return this;
        }
        List<Cheat> results = new ArrayList<>();
        for (Cheat cheat : cheats) {
            if(cheat.applyQuery(query)) results.add(cheat);
        }
        if(results.isEmpty()) return null;
        CheatGroup ret = cloneMe();
        ret.cheats = results;
        return ret;
    }
}
