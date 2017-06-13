package net.egordmitriev.cheatsheets.widgets;

import android.view.View;

/**
 * Created by EgorDm on 13-Jun-2017.
 */

public abstract class ViewHolder<T>  {

    public ViewHolder(View view) {
    }

    public abstract void onBind(T data);
}
