package net.egordmitriev.cheatsheets.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class Category extends MatchableModel{
    public String title;
    public String description;
    public List<CheatSheet> cheat_sheets;

    public Category() {
    }

    public Category(String title, String description, List<CheatSheet> cheat_sheets) {
        this.title = title;
        this.description = description;
        this.cheat_sheets = cheat_sheets;
    }

    @Override
    protected List<String> getSearchableStrings(boolean recursive) {
        List<String> ret = new ArrayList<String>() {{
            add(title);
            add(description);
        }};
        if(recursive) {
            for (CheatSheet cheatSheet : cheat_sheets) {
                ret.addAll(cheatSheet.getSearchableStrings(false));
            }
        }
        return ret;
    }


}
