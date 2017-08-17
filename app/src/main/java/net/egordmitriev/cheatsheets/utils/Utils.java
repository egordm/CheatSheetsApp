package net.egordmitriev.cheatsheets.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.widget.TextView;

import net.egordmitriev.cheatsheets.CheatSheetsApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	
	public static Uri writeFile(String filename, InputStream inputStream) throws IOException {
		File file = new File(CheatSheetsApp.getAppContext().getFilesDir(), filename);
		OutputStream outputStream = null;
		try {
			byte[] fileReader = new byte[4096];
			outputStream = new FileOutputStream(file);
			
			while (true) {
				int read = inputStream.read(fileReader);
				if (read == -1) {
					break;
				}
				outputStream.write(fileReader, 0, read);
			}
			
			outputStream.flush();
			return Uri.fromFile(file);
		} catch (Exception e) {
			return null;
		} finally {
			if (inputStream != null) inputStream.close();
			if (outputStream != null) outputStream.close();
		}
		
	}
}
