package net.egordmitriev.cheatsheets;

import android.support.test.runner.AndroidJUnit4;

import com.orhanobut.logger.Logger;

import net.egordmitriev.cheatsheets.api.API;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by EgorDm on 12-Jun-2017.
 */
@RunWith(AndroidJUnit4.class)
public class DataTests {

    @Test
    public void recieveApiData() {
        Logger.json(API.sGson.toJson(API.getCategories()));
    }
}
