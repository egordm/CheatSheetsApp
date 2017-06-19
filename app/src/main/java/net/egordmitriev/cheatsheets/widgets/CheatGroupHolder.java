package net.egordmitriev.cheatsheets.widgets;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;
import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.listeners.ExpansionArrowListener;
import net.egordmitriev.cheatsheets.pojo.CheatGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by EgorDm on 14-Jun-2017.
 */

public class CheatGroupHolder extends RecyclerViewHolder<CheatGroup> {

    @BindView(R.id.expandable)
    ExpandableLayout mExpandableLayout;

    @BindView(R.id.header_title)
    TextView mTitle;

    @BindView(R.id.expandable_contents)
    LinearLayout mCheatsList;

    public CheatGroupHolder(Activity activity, View itemView) {
        super(activity, itemView);
        mExpandableLayout.setOnExpansionUpdateListener(new ExpansionArrowListener(ButterKnife.findById(itemView, R.id.expandable_arrow)));
    }

    @Override
    public void onBind(CheatGroup data) {
        mExpandableLayout.setExpansion(1);
        mTitle.setText(Html.fromHtml(data.title));
        mCheatsList.removeAllViews();
        for(int i = 0; i < data.cheats.size(); i++) {
            CheatItemHolder viewHolder = CheatItemHolder.inflate(LayoutInflater.from(mActivity), mCheatsList, mActivity);
            viewHolder.onBind(data.cheats.get(i), i);
        }
        if(data.notes != null && data.notes.size() > 0) {
            for(int i = 0; i < data.notes.size(); i++) {
                View view = NoteItemHolder.inflate(LayoutInflater.from(mActivity), mCheatsList, true);
                NoteItemHolder holder = new NoteItemHolder(view);
                holder.onBind(data.notes.get(i));
            }
        }
    }

    @OnClick(R.id.header)
    public void onClickHeader(View view) {
        if(mExpandableLayout.isExpanded()) {
            view.setSelected(false);
            mExpandableLayout.collapse();
        }else {
            view.setSelected(true);
            mExpandableLayout.expand();
        }
    }

    public static View inflate(LayoutInflater inflater, ViewGroup parent, boolean bind) {
        View view = inflater.inflate(R.layout.group_item, parent, false);
        if(bind) parent.addView(view);
        return view;
    }
}
