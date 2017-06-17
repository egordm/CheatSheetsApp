package net.egordmitriev.cheatsheets.widgets;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
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

/*
    @BindView(R.id.expandable_contents)
    TableLayout mTableLayout;
*/

    @BindView(R.id.content_left)
    TextView mContentLeft;

    @BindView(R.id.content_right)
    TextView mContentRight;

    public CheatItemHolder(Activity activity, View view) {
        super(view);
        mActivity = activity;
        ButterKnife.bind(this, view);
    }

    public void onBind(Cheat data, int position) {
        onBind(data);

        applyWorkaround(mContentLeft);
        applyWorkaround(mContentRight);

        try {
            mContentLeft.setText(CodeSpannableBuilder.fromHtml(data.content.get(0)));
            if (data.layout == 0 && data.content.size() > 1) {
                mContentRight.setText(CodeSpannableBuilder.fromHtml(data.content.get(1)));
            } else if (data.layout == 1 && !TextUtils.isEmpty(data.description)) {
                mContentRight.setText(CodeSpannableBuilder.fromHtml(data.description));
            } else {
                mContentRight.setVisibility(View.GONE);
                TableRow.LayoutParams params = (TableRow.LayoutParams) mContentLeft.getLayoutParams();
                params.span = 2;
                mContentLeft.setLayoutParams(params);
            }

        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        mView.setBackgroundResource((position % 2 == 0) ? R.color.tableEven : R.color.tableUneven);
    }

    public void applyWorkaround(TextView textView) {
        int padding = 12;
        textView.setShadowLayer(padding /* radius */, 0, 0, 0 /* transparent */);
        textView.setPadding(padding, padding, padding, padding);
    }

    @Override
    public void onBind(Cheat data) {
        super.onBind(data);
    }

    public static View inflate(LayoutInflater inflater, ViewGroup parent) {
        int tempI = parent.getChildCount();
        View view = inflater.inflate(R.layout.cheat_item, parent, true);
        return parent.getChildAt(tempI);
    }
}
