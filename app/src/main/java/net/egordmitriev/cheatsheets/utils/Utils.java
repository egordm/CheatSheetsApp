package net.egordmitriev.cheatsheets.utils;

import android.widget.TextView;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class Utils {

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public static void applyWorkaround(TextView textView) {
        int padding = 12;
        textView.setShadowLayer(padding /* radius */, 0, 0, 0 /* transparent */);
        textView.setPadding(padding, padding, padding, padding);
    }
}
