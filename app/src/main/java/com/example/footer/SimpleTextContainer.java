package com.example.footer;

import android.content.Context;
import android.view.View;

public class SimpleTextContainer extends TextContainerView {
    final View separator2;

    public SimpleTextContainer(Context context, int orientation) {
        super(context, R.layout.simple_container);

        setOrientation(orientation);
        separator2 = findViewById(R.id.separator2);
    }

    @Override
    public void setText3(String text) {
        if (text == null) {
            text3.setVisibility(GONE);
            separator2.setVisibility(GONE);
        } else {
            text3.setVisibility(VISIBLE);
            separator2.setVisibility(VISIBLE);
        }
        super.setText3(text);
    }
}
