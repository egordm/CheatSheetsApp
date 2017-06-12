package net.egordmitriev.cheatsheets.pojo;

import java.util.List;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class CheatGroup {
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
}
