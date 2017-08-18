package net.egordmitriev.cheatsheets.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import net.egordmitriev.cheatsheets.api.API;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class CheatSheet extends MatchableModel implements Parcelable {
    public static final int TYPE_NATIVE = 0;
    public static final int TYPE_PDF = 1;
    
    public int id;
    public int type;
    public String title;
    public String subtitle;
    public String description;
    public CheatGroup[] cheat_groups;
    public String[] tags;
    public Date last_sync;
    public boolean isLocal;

    public CheatSheet() {
    }
    
    private CheatSheet(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        title = in.readString();
        subtitle = in.readString();
        description = in.readString();
        tags = in.createStringArray();
    }
    
    public CheatSheet(int id, int type, String title, String subtitle, String description, CheatGroup[] cheat_groups, String[] tags, Date last_sync) {
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
            addAll(Arrays.asList(tags));
        }};
    }
    
    public boolean isLocal() {
        return API.getCachedIds().contains(id);
    }
    
    public boolean filter(String query) {
        return matchesString(query, false);
    }
    
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeInt(type);
        out.writeString(title);
        out.writeString(subtitle);
        out.writeString(description);
        out.writeStringArray(tags);
    }
    
    public static final Parcelable.Creator<CheatSheet> CREATOR = new Parcelable.Creator<CheatSheet>() {
        public CheatSheet createFromParcel(Parcel in) {
            return new CheatSheet(in);
        }
        
        public CheatSheet[] newArray(int size) {
            return new CheatSheet[size];
        }
    };
}
