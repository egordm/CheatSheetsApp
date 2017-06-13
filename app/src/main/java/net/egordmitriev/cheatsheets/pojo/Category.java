package net.egordmitriev.cheatsheets.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class Category extends MatchableModel{
    public String slug;
    public String title;
    public String description;
    public List<CheatSheet> sheets;

    public Category() {
    }

    public Category(String slug, String title, String description, List<CheatSheet> sheets) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.sheets = sheets;
    }

    @Override
    protected List<String> getSearchableStrings() {
        List<String> ret = new ArrayList<String>() {{
            add(slug);
            add(title);
            add(description);
        }};
        for (CheatSheet cheatSheet : sheets) {
            ret.addAll(cheatSheet.getSearchableStrings());
        }
        return ret;
    }


}
