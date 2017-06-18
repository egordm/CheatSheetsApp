package net.egordmitriev.cheatsheets.pojo;

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

    public CheatGroup() {
    }

    public CheatGroup(String title, String description, List<Cheat> cheats, List<String> tags) {
        this.title = title;
        this.description = description;
        this.cheats = cheats;
        this.tags = tags;
    }

    @Override
    protected List<String> getSearchableStrings(final boolean recursive) {
        List<String> ret =  new ArrayList<String>() {{
            add(title);
            add(description);
            addAll(tags);

        }};
        if(recursive) {
            for (Cheat cheat : cheats) {
                ret.addAll(cheat.getSearchableStrings(false));
            }
        }
        return ret;
    }

    public CheatGroup cloneMe() {
        return new CheatGroup(title, description, (List<Cheat>) ((ArrayList<Cheat>)cheats).clone(), tags);
    }
}
