package net.egordmitriev.cheatsheets.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.egordmitriev.cheatsheets.activities.DetailActivity;
import net.egordmitriev.cheatsheets.pojo.CheatGroup;
import net.egordmitriev.cheatsheets.widgets.CheatGroupHolder;

/**
 * Created by EgorDm on 15-Jun-2017.
 */

public class CheatsheetAdapter extends AdvancedRecyclerAdapter<CheatGroup, CheatGroupHolder> {

    protected DetailActivity mActivity;

    public CheatsheetAdapter(DetailActivity activity) {
        mActivity = activity;
    }

    @Override
    public CheatGroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View holder = CheatGroupHolder.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CheatGroupHolder(mActivity, holder);
    }

    @Override
    public void onBindViewHolder(CheatGroupHolder holder, int position) {
        holder.onBind(mData.get(position));
    }
}
