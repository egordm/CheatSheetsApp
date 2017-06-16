package net.egordmitriev.cheatsheets.utils.spans;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EgorDm on 16-Jun-2017.
 */

public class CodeSpan implements LineBackgroundSpan {

    private List<TagArea> mTags = new ArrayList<>();

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        int curLeft = left;
        int pos = start;
        while (pos < end) {

        }

        for(TagArea tag : mTags) {
            tag.draw(c, p, left, right, top, baseline, bottom, text, start,end,lnum);
        }
    }

    public void addTag(TagArea tag) {
        mTags.add(tag);
    }

    public static abstract class TagArea {
        public final int start;
        public final int end;


        public TagArea(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public boolean isWithin(int pos) {
            return pos < end && pos >= start;
        }

        public abstract void draw(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum);

        //public abstract void width()
    }




}
