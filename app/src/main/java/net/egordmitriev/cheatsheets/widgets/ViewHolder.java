package net.egordmitriev.cheatsheets.widgets;

import android.view.View;

/**
 * Created by EgorDm on 13-Jun-2017.
 */

public abstract class ViewHolder<T> {

    protected T mData;

    protected View mView;

    public ViewHolder(View view) {
        mView = view;
    }

    public void onBind(T data) {
        mData = data;
    }

    public View getView() {
        return mView;
    }
}
