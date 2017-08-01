package net.egordmitriev.cheatsheets.widgets;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.activities.DetailActivity;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by EgorDm on 13-Jun-2017.
 */

public class SheetItemHolder extends ViewHolder<CheatSheet>{

    protected Activity mActivity;

    @BindView(R.id.title)
    TextView mTitleView;

    @BindView(R.id.local_status)
    ImageView mLocalStatusView;

    public SheetItemHolder(Activity activity, View view) {
        super(view);
        mActivity = activity;
        ButterKnife.bind(this, view);
    }

    public void onBind(CheatSheet data, int position) {
        onBind(data);
        mTitleView.setText(data.title);
        mLocalStatusView.setImageResource(data.isLocal ? R.drawable.ic_local : R.drawable.ic_cloud);
        mView.setBackgroundResource((position % 2 == 0) ? R.color.tableEven : R.color.tableUneven);
    }

    @Override
    public void onBind(CheatSheet data) {
        super.onBind(data);
    }

    @OnClick(R.id.wrapper)
    public void onClick() {
        Intent intent = new Intent(mActivity, DetailActivity.class);
        intent.putExtra(DetailActivity.CHEATSHEET_ID_KEY, mData.id);
        mActivity.startActivity(intent);

    }

    public static View inflate(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.cheatsheet_item, parent, false);
        parent.addView(view);
        return view;
    }


    public boolean applyQuery(String query) {
        if (mData.matchesString(query, false)) {
            forceVisibility(true);
            return true;
        }
        forceVisibility(false);
        return false;
    }


    public void forceVisibility(boolean visible) {
        getView().setVisibility(visible ? View.VISIBLE : View.GONE);
    }


}
