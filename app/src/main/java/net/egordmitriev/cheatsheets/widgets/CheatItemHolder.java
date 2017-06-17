package net.egordmitriev.cheatsheets.widgets;

import android.app.Activity;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import net.egordmitriev.cheatsheets.CheatSheets;
import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.pojo.Cheat;
import net.egordmitriev.cheatsheets.utils.spans.CodeSpannableBuilder;
import net.egordmitriev.cheatsheets.utils.spans.QuoteSpan;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EgorDm on 15-Jun-2017.
 */

public class CheatItemHolder extends ViewHolder<Cheat> {

    protected Activity mActivity;

    @BindView(R.id.content_left)
    TextView mContentLeft;

    @BindView(R.id.content_right)
    TextView mContentRight;

    View mDescriptionRow;

    TextView mDescription;

    public CheatItemHolder(Activity activity, View contentRow, View descriptionRow) {
        super(contentRow);
        mActivity = activity;
        ButterKnife.bind(this, contentRow);
        mDescription = ButterKnife.findById(descriptionRow, R.id.description);
        mDescriptionRow = descriptionRow;
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
            if (data.layout == 2 && !TextUtils.isEmpty(data.description)) {
                Spannable span = CodeSpannableBuilder.fromHtml(data.description);
                span.setSpan(
                        new QuoteSpan(CheatSheets.getAppContext().getResources().getColor(R.color.quoteBorder), 8, 16),
                        0, span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mDescription.setText(span);
            } else {
                mDescriptionRow.setVisibility(View.GONE);
            }

        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        mView.setBackgroundResource((position % 2 == 0) ? R.color.tableEven : R.color.tableUneven);
        mDescriptionRow.setBackgroundResource((position % 2 == 0) ? R.color.tableEven : R.color.tableUneven);
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

    public static CheatItemHolder inflate(LayoutInflater inflater, ViewGroup parent, Activity activity) {
        int tempI = parent.getChildCount();
        View view = inflater.inflate(R.layout.cheat_item, parent, true);
        return new CheatItemHolder(activity, parent.getChildAt(tempI), parent.getChildAt(tempI + 1));
    }
}
