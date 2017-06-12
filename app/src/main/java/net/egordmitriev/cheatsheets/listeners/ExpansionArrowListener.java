package net.egordmitriev.cheatsheets.listeners;

import android.view.View;
import android.view.animation.DecelerateInterpolator;

import net.cachapa.expandablelayout.ExpandableLayout;

/**
 * Created by EgorDm on 12-Jun-2017.
 */

public class ExpansionArrowListener implements ExpandableLayout.OnExpansionUpdateListener {

    private int mPreviousState = ExpandableLayout.State.COLLAPSED;
    private View mArrow;

    public ExpansionArrowListener(View arrow) {
        mArrow = arrow;
    }

    @Override
    public void onExpansionUpdate(float expansionFraction, int state) {
        if(state != mPreviousState) {
            mPreviousState = state;
            if(state == ExpandableLayout.State.EXPANDING) {
                mArrow.animate().setInterpolator(new DecelerateInterpolator()).rotation(180);

            } else if(state == ExpandableLayout.State.COLLAPSING) {
                mArrow.animate().setInterpolator(new DecelerateInterpolator()).rotation(0);
            }
        }
    }
}
