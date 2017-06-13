package net.egordmitriev.cheatsheets.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class CheatSheet extends MatchableModel {
    public String slug;
    public String title;
    public String subtitle;
    public String description;
    public List<CheatGroup> groups;
    public List<String> tags;

    public CheatSheet() {
    }

    public CheatSheet(String slug, String title, String subtitle, String description, List<CheatGroup> groups, List<String> tags) {
        this.slug = slug;
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.groups = groups;
        this.tags = tags;
    }

    @Override
    protected List<String> getSearchableStrings(final boolean recursive) {
        return new ArrayList<String>() {{
            add(slug);
            add(title);
            add(subtitle);
            add(description);
            addAll(tags);
        }};
    }
}
