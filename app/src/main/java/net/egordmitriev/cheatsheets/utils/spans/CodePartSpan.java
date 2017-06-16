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

public class CodePartSpan extends ReplacementSpan {

    private static final int PADDING_X = 12;
    private static final int CORNER_RADIUS = 8;

    private RectF mBorderRect;
    private Paint mBorderPaint;

    public CodePartSpan() {
        mBorderRect = new RectF();
        mBorderPaint = new Paint(){{
            setStyle(Paint.Style.STROKE);
            setAntiAlias(true);
            setStrokeWidth(3);
            setColor(CheatSheets.getAppContext().getResources().getColor(R.color.codeSpanBorder));
        }};
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return (int) (PADDING_X + paint.measureText(text.subSequence(start, end).toString()) + PADDING_X);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        float width = paint.measureText(text.subSequence(start, end).toString());
        mBorderRect.set(x, top, x + width + 2 * PADDING_X, bottom);
        canvas.drawRoundRect(mBorderRect, CORNER_RADIUS, CORNER_RADIUS, mBorderPaint);
        canvas.drawText(text, start, end, x + PADDING_X, y, paint);
    }
}
