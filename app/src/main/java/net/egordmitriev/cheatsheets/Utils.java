package net.egordmitriev.cheatsheets;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class Utils {

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
}
