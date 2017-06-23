package net.egordmitriev.cheatsheets.utils;

import android.content.Context;
import android.content.SharedPreferences;

import net.egordmitriev.cheatsheets.CheatSheetsApp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by EgorDm on 23-Jun-2017.
 */

public class PreferenceManager {
    public static final String PREFS_NAME = "CHEATSHEETS_PREFERENCES";

    public static final int MAX_RECENTLY_OPENED = 8;
    private static final String PREF_KEY_RECENTLY_OPENED = "RECENTLY_OPENED";

    private static PreferenceManager sPreferenceManager;

    public static PreferenceManager getInstance() {
        if (sPreferenceManager == null) {
            sPreferenceManager = new PreferenceManager();
        }
        return sPreferenceManager;
    }

    private final SharedPreferences mPreferences;

    public PreferenceManager() {
        mPreferences = CheatSheetsApp.getAppContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void putRecentlyOpened(int id) {
        List<Integer> data = getRecentlyOpened();
        while (data.contains(id)) {
            data.remove(new Integer(id));
        }
        data.add(0, id);
        setRecentlyOpened(data);
    }

    public void setRecentlyOpened(List<Integer> data) {
        StringBuilder rawData = new StringBuilder();
        data = data.subList(0, (data.size() >= MAX_RECENTLY_OPENED) ? MAX_RECENTLY_OPENED : data.size());

        Iterator<Integer> iter = data.iterator();
        while (iter.hasNext()) {
            rawData.append(iter.next());
            if(iter.hasNext()) rawData.append(',');
        }
        SharedPreferences.Editor editor = getEditor();
        editor.putString(PREF_KEY_RECENTLY_OPENED, rawData.toString());
        editor.commit();
    }

    public List<Integer> getRecentlyOpened() {
        String[] rawData = mPreferences.getString(PREF_KEY_RECENTLY_OPENED, "").split(",");
        List<Integer> ret = new ArrayList<>();
        for(String raw : rawData) {
            try {
                ret.add(Integer.parseInt(raw));
            } catch (Exception ignored) {}
        }
        return ret;
    }

    private SharedPreferences.Editor getEditor() {
        return mPreferences.edit();
    }
}
