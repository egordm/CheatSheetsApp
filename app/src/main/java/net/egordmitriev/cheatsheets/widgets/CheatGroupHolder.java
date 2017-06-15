package net.egordmitriev.cheatsheets.widgets;

import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;
import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.listeners.ExpansionArrowListener;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EgorDm on 14-Jun-2017.
 */

public class CheatGroupHolder extends RecyclerViewHolder<CheatSheet> {

    @BindView(R.id.expandable)
    ExpandableLayout mExpandableLayout;

    @BindView(R.id.header_title)
    TextView mTitle;

    @BindView(R.id.expandable_contents)
    LinearLayout mSheetsList;

    public CheatGroupHolder(View itemView) {
        super(itemView);
        mExpandableLayout.setOnExpansionUpdateListener(new ExpansionArrowListener(ButterKnife.findById(itemView, R.id.expandable_arrow)));
    }

    @Override
    public void onBind(CheatSheet data) {
        mTitle.setText(Html.fromHtml(data.title));
    }
}
