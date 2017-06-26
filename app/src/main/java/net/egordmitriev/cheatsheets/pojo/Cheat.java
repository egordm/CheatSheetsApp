package net.egordmitriev.cheatsheets.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class Cheat extends MatchableModel {
    public List<String> cheat_contents;
    public String description;
    public int layout;
    public String usage;
    public String source;
    public List<String> tags;

    public Cheat() {
    }

    public Cheat(List<String> cheat_contents, String description, String usage, String source, List<String> tags) {
        this.cheat_contents = cheat_contents;
        this.description = description;
        this.usage = usage;
        this.source = source;
        this.tags = tags;
    }

    @Override
    protected List<String> getSearchableStrings(boolean recursive) {
        return new ArrayList<String>() {{
            addAll(cheat_contents);
            add(description);
            add(usage);
            add(source);
            addAll(tags);
        }};
    }

    public boolean applyQuery(String query) {
        return matchesString(query, false);
    }

}
