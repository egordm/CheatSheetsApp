package net.egordmitriev.cheatsheets.widgets;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by EgorDm on 15-Jun-2017.
 */

public abstract class RecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    protected T mData;

    protected View mView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public abstract void onBind(T data);
}
