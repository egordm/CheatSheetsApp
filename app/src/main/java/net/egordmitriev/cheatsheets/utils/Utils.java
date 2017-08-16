package net.egordmitriev.cheatsheets.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class Utils { //Test

    public static final Pattern URL_PATTERN = Pattern.compile("[a-z]+:\\/\\/[^ \\n]*");

    public static void applyLinks(TextView textView) {
        textView.setLinksClickable(true);
        Linkify.addLinks(textView, URL_PATTERN, "");
    }

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public static void applyTextSpanWorkaround(TextView textView) {
        int padding = 12;
        textView.setShadowLayer(padding /* radius */, 0, 0, 0 /* transparent */);
        textView.setPadding(padding, padding, padding, padding);
    }

    public static void openPlayPage(Activity activity) {
        final String appPackageName = activity.getPackageName(); // getPackageName() from Context or Activity object
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
    
    public static <C> List<C> ConvertToList(SparseArray<C> sparseArray) {
        if (sparseArray == null) return null;
        List<C> arrayList = new ArrayList<>(sparseArray.size());
        
        for (int i = 0; i < sparseArray.size(); i++)
            arrayList.add(sparseArray.valueAt(i));
        return arrayList;
    }
}
