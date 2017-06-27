package net.egordmitriev.cheatsheets.widgets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.egordmitriev.cheatsheets.R;
import net.egordmitriev.cheatsheets.utils.Utils;
import net.egordmitriev.cheatsheets.utils.spans.CodeSpannableBuilder;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EgorDm on 19-Jun-2017.
 */

public class NoteItemHolder extends ViewHolder<String> {

    @BindView(R.id.note)
    TextView mTextView;

    public NoteItemHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }


    @Override
    public void onBind(String data) {
        super.onBind(data);
        Utils.applyTextSpanWorkaround(mTextView);
        try {
            mTextView.setText(CodeSpannableBuilder.fromHtml(data));
            Utils.applyLinks(mTextView);
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    public static View inflate(LayoutInflater inflater, ViewGroup parent, boolean bind) {
        View view = inflater.inflate(R.layout.note_item, parent, false);
        if(bind) parent.addView(view);
        return view;
    }
}
