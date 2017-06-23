package net.egordmitriev.cheatsheets.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import net.egordmitriev.loaderview.LoaderView;

/**
 * Created by EgorDm on 23-Jun-2017.
 */

public class CustomLoaderView extends LoaderView {
    public CustomLoaderView(Context context) {
        super(context);
    }

    public CustomLoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLoaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void changeVisibleView(View newView, View oldView, boolean animate) {
        if(newView != null)newView.animate().cancel(); //TODO: Update library
        if(oldView != null)oldView.animate().cancel();
        super.changeVisibleView(newView, oldView, animate);
    }
}
