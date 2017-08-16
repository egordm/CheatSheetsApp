package net.egordmitriev.cheatsheets.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class CheatSheet extends MatchableModel {
    public int id;
    public int type;
    public String title;
    public String subtitle;
    public String description;
    public List<CheatGroup> cheat_groups;
    public List<String> tags;
    public Date last_sync;
    public boolean isLocal;

    public CheatSheet() {
    }
    
    public CheatSheet(int id, int type, String title, String subtitle, String description, List<CheatGroup> cheat_groups, List<String> tags, Date last_sync) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.cheat_groups = cheat_groups;
        this.tags = tags;
        this.last_sync = last_sync;
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
