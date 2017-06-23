package net.egordmitriev.cheatsheets.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class CheatSheet extends MatchableModel {
    public int id;
    public String title;
    public String subtitle;
    public String description;
    public List<CheatGroup> cheat_groups;
    public List<String> tags;

    public CheatSheet() {
    }

    public CheatSheet(int id, String title, String subtitle, String description, List<CheatGroup> cheat_groups, List<String> tags) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.cheat_groups = cheat_groups;
        this.tags = tags;
    }

    @Override
    protected List<String> getSearchableStrings(final boolean recursive) {
        return new ArrayList<String>() {{
            add(title);
            add(subtitle);
            add(description);
            addAll(tags);
        }};
    }
}
