package net.egordmitriev.cheatsheets.utils;

import android.support.annotation.StringRes;

import net.egordmitriev.cheatsheets.CheatSheets;
import net.egordmitriev.cheatsheets.R;

import java.net.UnknownHostException;

/**
 * Created by EgorDm on 23-Jun-2017.
 */

public class LocalizedException extends Exception {
    @StringRes
    private int mMessageID = R.string.exception_unknown;

    public LocalizedException(@StringRes int messageID) {
        super("Oh no! Something has gone wrong.");
        mMessageID = messageID;
    }

    public LocalizedException(Throwable t) {
        super(t);
        if(t instanceof NullPointerException) {
            mMessageID = R.string.exception_null_pointer;
        } else if(t instanceof UnknownHostException) {
            mMessageID = R.string.exception_no_network;
        }
    }

    @Override
    public String getMessage() {
        return CheatSheets.getAppContext().getString(mMessageID);
    }
}
