package net.egordmitriev.cheatsheets;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class Utils {

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public static Spanned htmlFromString(String s) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(s);
        }
    }
}
