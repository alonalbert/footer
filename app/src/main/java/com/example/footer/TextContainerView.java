package com.example.footer;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class TextContainerView extends LinearLayout {
    protected TextView text1;
    private TextView text2;
    TextView text3;


    public TextContainerView(Context context, int layoutId) {
        super(context);
        inflate(context, layoutId, this);

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
    }


    protected void preMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.measure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setText1(String text) {
        text1.setText(text);
    }

    public String getText1() {
        return text1.getText().toString();
    }

    public void setText2(String text) {
        text2.setText(text);
    }

    public String getText2() {
        return text2.getText().toString();
    }

    public void setText3(String text) {
        text3.setText(text);
    }

    public String getText3() {
        return text3.getText().toString();
    }
}
