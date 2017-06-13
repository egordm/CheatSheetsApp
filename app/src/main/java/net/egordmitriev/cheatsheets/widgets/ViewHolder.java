package net.egordmitriev.cheatsheets.widgets;

import android.view.View;

/**
 * Created by EgorDm on 13-Jun-2017.
 */

public abstract class ViewHolder<T> {

    protected View mView;

    public ViewHolder(View view) {
        mView = view;
    }

    public abstract void onBind(T data);

    public View getView() {
        return mView;
    }
}
