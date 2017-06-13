package net.egordmitriev.cheatsheets.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import net.egordmitriev.cheatsheets.Utils;

/**
 * Created by EgorDm on 13-Jun-2017.
 */

public class AdvancedRecyclerView extends RecyclerView {
    public AdvancedRecyclerView(Context context) {
        super(context);
    }

    public AdvancedRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AdvancedRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void scrollOnView(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        smoothScrollBy(0, Utils.clamp(location[1], 0, computeVerticalScrollRange()) - computeVerticalScrollOffset());
    }


}
