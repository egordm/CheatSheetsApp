package net.egordmitriev.cheatsheets.pojo;

import java.util.List;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class Category {
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
}
