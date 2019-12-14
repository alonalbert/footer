package com.example.footer;

import android.content.Context;
import android.widget.TextView;

public class TwoRowTextContainer extends TextContainerView {
    private final TextView singleText;

    public TwoRowTextContainer(Context context, int layoutId) {
        super(context, layoutId);
        setOrientation(VERTICAL);

        if (getChildAt(0) instanceof TextView) {
            singleText = (TextView) getChildAt(0);
        } else {
            singleText = (TextView) getChildAt(1);
        }
    }

    TwoRowTextContainer(Context context) {
        super(context, R.layout.one_above_container);
        singleText = (TextView) getChildAt(0);
    }

    @Override
    protected void preMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        CharSequence text = singleText.getText();
        singleText.setText("");
        super.measure(widthMeasureSpec, heightMeasureSpec);
        singleText.setText(text);
    }
}
