package net.egordmitriev.cheatsheets.widgets;

import android.view.View;
import android.widget.TextView;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.pojo.CheatSheet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by EgorDm on 13-Jun-2017.
 */

public class SheetItemViewHolder extends ViewHolder<CheatSheet>{

    @BindView(R.id.title)
    TextView mTitleView;

    public SheetItemViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onBind(CheatSheet data) {
        mTitleView.setText(data.title);
    }

    @OnClick(R.id.wrapper)
    public void onClick() {
        //Logger.d("Click wrapper");
    }



}
