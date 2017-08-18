package net.egordmitriev.cheatsheets.utils;

import java.util.regex.Pattern;

/**
 * Created by EgorDm on 22-Jun-2017.
 */

public class Constants {
    public static final String API_BASE_URL = "http://cheat-sheets.ga/";

    public static final long CACHE_LIFETIME = 86400000;

    public static final String CACHE_FILENAME_CATEGORIES = "categories";
    public static final String CACHE_FILENAME_CHEATSHEET = "cheat_sheet_";
    public static final Pattern CHEATSHEET_ID_REGEX = Pattern.compile(CACHE_FILENAME_CHEATSHEET + "(.*?).json");

    public static final boolean USE_CACHE = true;
    public static final boolean BETA_BUILD = true;
}
