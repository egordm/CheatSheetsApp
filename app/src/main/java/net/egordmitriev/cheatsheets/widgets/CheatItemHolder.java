package net.egordmitriev.cheatsheets.widgets;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.pojo.Cheat;
import net.egordmitriev.cheatsheets.utils.spans.CodeSpannableBuilder;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EgorDm on 15-Jun-2017.
 */

public class CheatItemHolder extends ViewHolder<Cheat> {

    protected Activity mActivity;

    @BindView(R.id.title)
    TextView mTitleView;

    public CheatItemHolder(Activity activity, View view) {
        super(view);
        mActivity = activity;
        ButterKnife.bind(this, view);
    }

    public void onBind(Cheat data, int position) {
        onBind(data);

        //Workaround padding
        int padding = 12; // in pixels
        mTitleView.setShadowLayer(padding /* radius */, 0, 0, 0 /* transparent */);
        mTitleView.setPadding(padding, padding, padding, padding);

        try {
            mTitleView.setText(CodeSpannableBuilder.fromHtml(data.content.get(0)));
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        mView.setBackgroundResource((position % 2 == 0) ? R.color.tableEven : R.color.tableUneven);
    }

    @Override
    public void onBind(Cheat data) {
        super.onBind(data);
    }

    public static View inflate(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.cheat_item, parent, false);
        parent.addView(view);
        return view;
    }
}
