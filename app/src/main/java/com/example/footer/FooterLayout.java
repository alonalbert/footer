package com.example.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import static androidx.constraintlayout.widget.ConstraintSet.BOTTOM;
import static androidx.constraintlayout.widget.ConstraintSet.END;
import static androidx.constraintlayout.widget.ConstraintSet.RIGHT;
import static androidx.constraintlayout.widget.ConstraintSet.START;
import static androidx.constraintlayout.widget.ConstraintSet.TOP;

public class FooterLayout extends FrameLayout {
    private ConstraintLayout parent;
    private TextView text1;
    private ImageView separator1;
    private TextView text2;
    private ImageView separator2;
    private TextView text3;
    private View[] views;
    private final ConstraintSet set = new ConstraintSet();

    public FooterLayout(@NonNull Context context) {
        this(context, null);
    }

    public FooterLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.footer, this);

        parent = findViewById(R.id.parent);
        text1 = findViewById(R.id.text1);
        separator1 = findViewById(R.id.separator1);
        text2 = findViewById(R.id.text2);
        separator2 = findViewById(R.id.separator2);
        text3 = findViewById(R.id.text3);

        views = new View[]{text1, separator1, text2, separator2, text3};
    }

    public void setText1(String text) {
        text1.setText(text);
        forceLayout();
    }

    String getText1() {
        return text1.getText().toString();
    }

    void setText2(String text) {
        text2.setText(text);
        forceLayout();
    }

    String getText2() {
        return text2.getText().toString();
    }

    void setText3(String text) {
        text3.setText(text);
        forceLayout();
    }

    String getText3() {
        return text3.getText().toString();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int maxWidth = MeasureSpec.getSize(widthMeasureSpec);

        final int widthConstraints = getPaddingLeft() + getPaddingRight();
        final int heightConstraints = getPaddingTop() + getPaddingBottom();

        for (View view : views) {
            measureChildWithMargins(
                    view,
                    widthMeasureSpec,
                    widthConstraints,
                    heightMeasureSpec,
                    heightConstraints);
        }

        final int widthText1 = getChildWidthWithMargin(text1);
        final int widthSeparator1 = getChildWidthWithMargin(separator1);
        final int widthText2 = getChildWidthWithMargin(text2);
        final int widthSeparator2 = getChildWidthWithMargin(separator2);
        final int widthText3 = getChildWidthWithMargin(text3);

        set.clone(parent);
        final int seperator1Visibility;
        final int seperator2Visibility;
        if (widthText1 + widthSeparator1 + widthText2 + widthSeparator2 + widthText3 > maxWidth) {
            if (widthText1 + widthSeparator1 + widthText2 <= maxWidth) {
                connectRow(set, R.id.parent, TOP, R.id.text3, TOP, R.id.text1, R.id.separator1, R.id.text2);
                connectRow(set, R.id.text1, BOTTOM, R.id.parent, BOTTOM, R.id.text3);
                seperator1Visibility = VISIBLE;
                seperator2Visibility = GONE;
                text1.setMaxLines(1);
                text2.setMaxLines(1);
                text3.setMaxLines(3);
            } else if (widthText2 + widthSeparator2 + widthText3 <= maxWidth) {
                connectRow(set, R.id.parent, TOP, R.id.text2, TOP, R.id.text1);
                connectRow(set, R.id.text1, BOTTOM, R.id.parent, BOTTOM, R.id.text2, R.id.separator2, R.id.text3);
                seperator1Visibility = GONE;
                seperator2Visibility = VISIBLE;
                text1.setMaxLines(3);
                text2.setMaxLines(1);
                text3.setMaxLines(1);
            } else {
                connectCol(set, R.id.text1, R.id.separator1, R.id.text2, R.id.separator2, R.id.text3);
                seperator1Visibility = VISIBLE;
                seperator2Visibility = VISIBLE;
                text1.setMaxLines(3);
                text2.setMaxLines(3);
                text3.setMaxLines(3);
            }
        } else {
            connectRow(set, R.id.parent, TOP, R.id.parent, BOTTOM, R.id.text1, R.id.separator1, R.id.text2, R.id.separator2, R.id.text3);
            seperator1Visibility = VISIBLE;
            seperator2Visibility = VISIBLE;
            text1.setMaxLines(1);
            text2.setMaxLines(1);
            text3.setMaxLines(1);
        }
        set.applyTo(parent);

        // Visibility must be set after applying constraints
        separator1.setVisibility(seperator1Visibility);
        separator2.setVisibility(seperator2Visibility);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void connectRow(ConstraintSet set, int top, int topSide, int bottom, int bottomSide, int... ids) {
        final int len = ids.length;

        set.connect(ids[0], TOP, top, topSide);
        set.connect(ids[0], BOTTOM, bottom, bottomSide);

        set.connect(ids[0], START, R.id.parent, START);
        set.connect(ids[len - 1], END, R.id.parent, END);

        for (int i = 0; i < len - 1; i++) {
            set.connect(ids[i], END, ids[i + 1], START);
        }
        for (int i = 1; i < len; i++) {
            set.connect(ids[i], TOP, ids[0], TOP);
            set.connect(ids[i], BOTTOM, ids[0], BOTTOM);
            set.connect(ids[i], START, ids[i - 1], END);
        }
    }

    private void connectCol(ConstraintSet set, int... ids) {
        for (int id : ids) {
            set.connect(id, START, R.id.parent, START);
            set.connect(id, END, R.id.parent, END);
        }

        final int len = ids.length;
        set.connect(ids[0], TOP, R.id.parent, TOP);
        set.connect(ids[len - 1], BOTTOM, R.id.parent, BOTTOM);

        for (int i = 0; i < len - 1; i++) {
            set.connect(ids[i], BOTTOM, ids[i + 1], TOP);
        }
        for (int i = 1; i < len; i++) {
            set.connect(ids[i], TOP, ids[i - 1], BOTTOM);
        }

    }

    private int getChildWidthWithMargin(View view) {
        MarginLayoutParams lp = getChildLayoutParams(view);
        return view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
    }

    private MarginLayoutParams getChildLayoutParams(View view) {
        return (MarginLayoutParams) view.getLayoutParams();
    }
}
