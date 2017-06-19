package net.egordmitriev.cheatsheets.utils.spans;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.style.ReplacementSpan;

import net.egordmitriev.cheatsheets.CheatSheets;
import net.egordmitriev.cheatsheets.R;

/**
 * Created by EgorDm on 17-Jun-2017.
 */


//https://developer.android.com/reference/android/text/StaticLayout.html
public class KbdPartSpan extends ReplacementSpan { //TODO: merge with paragraph

    private static final int PADDING_X = 12;
    private static final int CORNER_RADIUS = 8;
    private static final int BUTTON_RELIEF = 4;

    private RectF mBorderRect = null;
    private RectF mBgRect = null;
    private Paint mBorderPaint;
    private Paint mBackgroundPaint;

    public KbdPartSpan() {
        mBorderPaint = new Paint(){{
            setStyle(Style.FILL);
            setAntiAlias(true);
            setColor(CheatSheets.getAppContext().getResources().getColor(R.color.kbdSpanBorder));
        }};

        mBackgroundPaint = new Paint(){{
            setStyle(Style.FILL);
            setAntiAlias(true);
            setColor(CheatSheets.getAppContext().getResources().getColor(R.color.kbdSpanBackground));
        }};
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return (int) (PADDING_X + paint.measureText(text.subSequence(start, end).toString()) + PADDING_X);
    }



    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        float width = paint.measureText(text.subSequence(start, end).toString());
        if(mBorderRect == null) {
            mBorderRect = new RectF(x, top-PADDING_X/2, x + width + 2 * PADDING_X, y+PADDING_X + 6);
            mBgRect = new RectF(mBorderRect);
            mBgRect.offset(0, -BUTTON_RELIEF);
        }
        //mBorderRect.set(x, top-PADDING_X/2, x + width + 2 * PADDING_X, y+PADDING_X);
        canvas.drawRoundRect(mBorderRect, CORNER_RADIUS, CORNER_RADIUS, mBorderPaint);
        canvas.drawRoundRect(mBgRect, CORNER_RADIUS, CORNER_RADIUS, mBackgroundPaint);
        canvas.drawText(text, start, end, x + PADDING_X, y, paint);
    }

}
