package net.egordmitriev.cheatsheets.pojo;

import java.util.List;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class Cheat {
    public List<String> content;
    public String description;
    public int layout;
    public String usage;
    public String source;
    public List<String> tags;

    public Cheat() {
    }

    public Cheat(List<String> content, String description, String usage, String source, List<String> tags) {
        this.content = content;
        this.description = description;
        this.usage = usage;
        this.source = source;
        this.tags = tags;
    }
}
