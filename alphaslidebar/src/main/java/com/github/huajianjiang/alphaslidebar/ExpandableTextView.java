package com.github.huajianjiang.alphaslidebar;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.util.AttributeSet;

/**
 * <p>Author: Huajian Jiang
 * <br>Date: 2017/7/9
 * <br>Email: developer.huajianjiang@gmail.com
 */
public class ExpandableTextView extends AppCompatTextView {
    private static final String TAG = ExpandableTextView.class.getSimpleName();

    private boolean hasSet = false;
    private int mOrgMaxLines = -1;

    private OnExpandableStateChangeListener mListener;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Logger.e(TAG, "getMaxLines==>" + getMaxLines() + ",mOrgMaxLines=" + mOrgMaxLines);
    }

    public void setOnExpandableStateChangeListener(OnExpandableStateChangeListener listener)
    {
        mListener = listener;
    }

    @Override
    public void setMaxLines(int maxlines) {
        if (!hasSet) {
            mOrgMaxLines = maxlines;
            hasSet = true;
            Logger.e(TAG, "setMaxLines==>" + maxlines + ",mOrgMaxLines=" + mOrgMaxLines);
        }
        super.setMaxLines(maxlines);
    }

    public int getCompatMaxLines() {
        return mOrgMaxLines;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Layout layout = getLayout();
        if (layout == null || mOrgMaxLines <= 0) return;

        int lineCount = layout.getLineCount();
        boolean expandable =
                layout.getEllipsisCount(lineCount-1) > 0 || lineCount > mOrgMaxLines;

        if (mListener != null) {
            mListener.onExpandableStateChanged(this, expandable);
        }

//        if (e) {
//            Logger.e(TAG, layout.getEllipsisStart(getLineCount() - 1) + ","+layout.getLineStart(getLineCount()-1));
////            getText().toString().repl
//
//        }
       Logger.e(TAG, "onDraw==>" + mOrgMaxLines);
    }

    public interface OnExpandableStateChangeListener {
        void onExpandableStateChanged(ExpandableTextView who, boolean expandable);
    }
}
