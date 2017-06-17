package net.egordmitriev.cheatsheets.utils.spans;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.style.LineBackgroundSpan;

import net.egordmitriev.cheatsheets.CheatSheets;
import net.egordmitriev.cheatsheets.R;

/**
 * Created by EgorDm on 17-Jun-2017.
 */

public class CodeParagraphSpan implements LineBackgroundSpan {
    private static final int PADDING_X = 12;
    private static final int CORNER_RADIUS = 8;

    private RectF mBorderRect;
    private Paint mBorderPaint;
    private Paint mBackgroundPaint;

    public CodeParagraphSpan() {
        //target.setSpan(new TypefaceSpan("monospace"), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBorderRect = new RectF();
        mBorderPaint = new Paint(){{
            setStyle(Paint.Style.STROKE);
            setAntiAlias(true);
            setStrokeWidth(3);
            setColor(CheatSheets.getAppContext().getResources().getColor(R.color.codeSpanBorder));
        }};

        mBackgroundPaint = new Paint(){{
            setStyle(Style.FILL);
            setAntiAlias(true);
            setColor(CheatSheets.getAppContext().getResources().getColor(R.color.codeSpanBackground));
        }};
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        Paint pain = new Paint(p);
        Typeface tf = Typeface.create("monospace", p.getTypeface().getStyle());
        pain.setTypeface(tf);

        Rect bounds = new Rect();
        pain.getTextBounds(text.toString(), start, end, bounds);

        mBorderRect.set(left-PADDING_X,
                top-PADDING_X/2, left + bounds.width() + PADDING_X, baseline +  bounds.height()/3 + PADDING_X/2);

        c.drawRoundRect(mBorderRect, CORNER_RADIUS, CORNER_RADIUS, mBorderPaint);
        c.drawRoundRect(mBorderRect, CORNER_RADIUS, CORNER_RADIUS, mBackgroundPaint);
    }
}
