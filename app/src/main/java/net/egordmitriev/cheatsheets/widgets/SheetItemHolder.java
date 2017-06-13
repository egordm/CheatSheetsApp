package net.egordmitriev.cheatsheets.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by EgorDm on 13-Jun-2017.
 */

public class SheetItemHolder extends ViewHolder<CheatSheet>{

    @BindView(R.id.title)
    TextView mTitleView;

    public SheetItemHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void onBind(CheatSheet data, int position) {
        mTitleView.setText(data.title);
        mView.setBackgroundResource((position % 2 == 0) ? R.color.tableEven : R.color.tableUneven);
    }

    @Override
    public void onBind(CheatSheet data) {
        onBind(data, 0);
    }

    @OnClick(R.id.wrapper)
    public void onClick() {
        //Logger.d("Click wrapper");
    }

    public static View inflate(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.cheatsheet_item, parent, false);
        parent.addView(view);
        return view;
    }

}
