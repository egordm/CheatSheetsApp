package net.egordmitriev.cheatsheets.utils.spans;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TypefaceSpan;

import com.orhanobut.logger.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by EgorDm on 16-Jun-2017.
 */

public class CodeSpannableBuilder {

    public static Spannable fromHtml(String html) throws ParserConfigurationException, SAXException {
        XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        CodeContentHandler handler = new CodeContentHandler(reader, html);
        try {
            return handler.convert();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class CodeContentHandler extends DefaultHandler {
        private XMLReader mReader;
        private String mSource;
        private SpannableStringBuilder mSpannableBuilder;

        private List<ElementHolder> mElementHolders = new ArrayList<>();

        public CodeContentHandler(XMLReader reader, String source) {
            mReader = reader;
            mSource = "<root>"+source+"</root>";
            mSpannableBuilder = new SpannableStringBuilder();
        }

        public Spannable convert() throws IOException, SAXException {
            mReader.setContentHandler(this);
            try {
                mReader.parse(new InputSource(new StringReader(mSource)));
            } catch (Exception e) {
                Logger.e("Error parsing: "+mSource);
                throw e;
            }
            return mSpannableBuilder;
        }

        @Override
        public void startElement(String uri, String tag, String qName, Attributes attributes) throws SAXException {
            if (tag.equalsIgnoreCase("code")) {
                mSpannableBuilder.setSpan(new Code(),mSpannableBuilder.length(), mSpannableBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            } else if (tag.equalsIgnoreCase("kbd")) {
                mSpannableBuilder.setSpan(new Kbd(),mSpannableBuilder.length(), mSpannableBuilder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }

        @Override
        public void endElement(String uri, String tag, String qName) throws SAXException {
            if (tag.equalsIgnoreCase("code")) {
                addTag(Code.class);
            } else if (tag.equalsIgnoreCase("kbd")) {
                addTag(Kbd.class);
            }
        }

        public void addTag(Class type) {
            int end = mSpannableBuilder.length();
            Object mark = getLast(mSpannableBuilder, type);
            if (mark != null) {
                int start = mSpannableBuilder.getSpanStart(mark);
                mSpannableBuilder.removeSpan(mark);
                mElementHolders.add(new ElementHolder(type, start, end));
            }
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            for(ElementHolder holder : mElementHolders) {
                boolean isParagraph = (holder.end - holder.start) >= mSpannableBuilder.length();
                /*if (isParagraph) {
                    mSpannableBuilder.append("\n");
                    holder.end += 1;
                }*/
                applySpan(holder, isParagraph);
            }
        }

        public void applySpan(ElementHolder holder, boolean isParagraph) {
            if(holder.type == Code.class) {
                mSpannableBuilder.setSpan(new TypefaceSpan("monospace"), holder.start, holder.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                if(isParagraph) {
                    mSpannableBuilder.setSpan(new CodeParagraphSpan(), holder.start, holder.end, Spanned.SPAN_PARAGRAPH);
                } else {
                    mSpannableBuilder.setSpan(new CodePartSpan(), holder.start, holder.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else if(holder.type == Kbd.class) {
                mSpannableBuilder.setSpan(new TypefaceSpan("monospace"), holder.start, holder.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mSpannableBuilder.setSpan(new KbdPartSpan(), holder.start, holder.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            StringBuilder sb = new StringBuilder();
        /*
         * Ignore whitespace that immediately follows other whitespace;
         * newlines count as spaces.
         */
            for (int i = 0; i < length; i++) {
                char c = ch[i + start];
                if (c == ' ' || c == '\n') {
                    char pred;
                    int len = sb.length();
                    if (len == 0) {
                        len = mSpannableBuilder.length();
                        if (len == 0) {
                            pred = '\n';
                        } else {
                            pred = mSpannableBuilder.charAt(len - 1);
                        }
                    } else {
                        pred = sb.charAt(len - 1);
                    }
                    if (pred != ' ' && pred != '\n') {
                        sb.append(' ');
                    }
                } else {
                    sb.append(c);
                }
            }
            mSpannableBuilder.append(sb);
        }

        private static <T> T getLast(Spanned text, Class<T> kind) {
        /*
         * This knows that the last returned object from getSpans()
         * will be the most recently added.
         */
            T[] objs = text.getSpans(0, text.length(), kind);
            if (objs.length == 0) {
                return null;
            } else {
                return objs[objs.length - 1];
            }
        }
    }

    private static class ElementHolder {
        public final Class type;
        public int start;
        public int end;

        public ElementHolder(Class type, int start, int end) {
            this.type = type;
            this.start = start;
            this.end = end;
        }
    }

    private static class Code{}

    private static class Kbd{}
}
