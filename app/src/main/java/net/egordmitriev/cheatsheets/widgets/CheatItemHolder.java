package net.egordmitriev.cheatsheets.widgets;

import android.app.Activity;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.egordmitriev.cheatsheets.CheatSheets;
import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.pojo.Cheat;
import net.egordmitriev.cheatsheets.utils.Utils;
import net.egordmitriev.cheatsheets.utils.spans.CodeSpannableBuilder;
import net.egordmitriev.cheatsheets.utils.spans.QuoteSpan;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import butterknife.ButterKnife;

/**
 * Created by EgorDm on 15-Jun-2017.
 */

public class CheatItemHolder extends ViewHolder<Cheat> {

    protected Activity mActivity;

    public CheatItemHolder(Activity activity, ViewGroup parent, Cheat data, int position) {
        super(parent);
        mActivity = activity;
        LayoutInflater inf = activity.getLayoutInflater();
        try {
            inflateAndBind(inf, parent, data, position);
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    private void inflateAndBind(LayoutInflater inflater, ViewGroup parent, Cheat data, int position) throws ParserConfigurationException, SAXException {
        View contentRow = null;
        View descRow = null;
        TextView contentLeft = null;
        TextView contentRight = null;
        TextView contentDescription = null;
        if((data.layout == 1 && !TextUtils.isEmpty(data.description)) || (data.layout == 0 && data.content.size() > 1)) {
            contentRow = inflater.inflate(R.layout.cheat_layout_dual, parent, false);
            if(data.layout == 1) {
                contentDescription = ButterKnife.findById(contentRow, R.id.content_right);
            } else {
                contentRight = ButterKnife.findById(contentRow, R.id.content_right);
            }
        } else if(data.layout == 2 && !TextUtils.isEmpty(data.description)) {
            contentRow = inflater.inflate(R.layout.cheat_layout_single, parent, false);
            descRow = inflater.inflate(R.layout.cheat_layout_description, parent, false);
            contentDescription = ButterKnife.findById(descRow, R.id.description);
        } else {
            contentRow = inflater.inflate(R.layout.cheat_layout_single, parent, false);
        }
        contentRow.setBackgroundResource((position % 2 == 0) ? R.color.tableEven : R.color.tableUneven);
        parent.addView(contentRow);
        if(descRow != null) {
            descRow.setBackgroundResource((position % 2 == 0) ? R.color.tableEven : R.color.tableUneven);
            parent.addView(descRow);
        }

        contentLeft = ButterKnife.findById(contentRow, R.id.content_left);
        Utils.applyWorkaround(contentLeft);
        contentLeft.setText(CodeSpannableBuilder.fromHtml(data.content.get(0)));
        if(contentRight != null) {
            Utils.applyWorkaround(contentRight);
            contentRight.setText(CodeSpannableBuilder.fromHtml(data.content.get(1)));
        }
        if(contentDescription != null) {
            Utils.applyWorkaround(contentDescription);
            Spannable span = CodeSpannableBuilder.fromHtml(data.description);
            if(data.layout == 2) {
                span.setSpan(
                        new QuoteSpan(CheatSheets.getAppContext().getResources().getColor(R.color.quoteBorder), 8, 16),
                        0, span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            contentDescription.setText(span);
        }



    }

    @Override
    public void onBind(Cheat data) {
        super.onBind(data);
    }
}
