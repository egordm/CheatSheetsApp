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
import net.egordmitriev.cheatsheets.pojo.Category;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by EgorDm on 13-Jun-2017.
 */

public class CategoryGroupHolder extends ViewHolder<Category> {

    protected Activity mActivity;

    @BindView(R.id.expandable)
    ExpandableLayout mExpandableLayout;

    @BindView(R.id.header_title)
    TextView mTitle;

    @BindView(R.id.expandable_contents)
    LinearLayout mSheetsList;


    public CategoryGroupHolder(Activity activity, View view) {
        super(view);
        mActivity = activity;
        ButterKnife.bind(this, view);
        mExpandableLayout.setOnExpansionUpdateListener(new ExpansionArrowListener(ButterKnife.findById(view, R.id.expandable_arrow)));
    }

    @Override
    public void onBind(Category data) {
        super.onBind(data);
        mTitle.setText(Html.fromHtml(data.title));
        for(int i = 0; i < data.sheets.size(); i++) {
            View view = SheetItemHolder.inflate(LayoutInflater.from(mActivity), mSheetsList);
            SheetItemHolder viewHolder = new SheetItemHolder(mActivity, view);
            viewHolder.onBind(data.sheets.get(i), i);
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

    public LinearLayout getSheetsList() {
        return mSheetsList;
    }

    public void collapse(boolean collapse, boolean animate) {
        if(collapse) {
            mExpandableLayout.collapse(animate);
        } else {
            mExpandableLayout.expand(animate);
        }
    }

    public static View inflate(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.group_item, parent, false);
        parent.addView(view);
        return view;
    }
}
