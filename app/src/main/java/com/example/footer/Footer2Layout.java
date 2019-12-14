package com.example.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class Footer2Layout extends LinearLayout {

    private final List<TextContainerView> views = new ArrayList<>();
    private TextContainerView currentView;

    private final List<TextContainerView> options = new ArrayList<>();

    public Footer2Layout(Context context) {
        this(context, null);
    }

    public Footer2Layout(Context context, AttributeSet attrs) {
        super(context, attrs);

        setGravity(Gravity.CENTER);

        views.add(new SimpleTextContainer(context, HORIZONTAL));
        views.add(new TwoRowTextContainer(context, R.layout.two_above_container));
        views.add(new TwoRowTextContainer(context, R.layout.one_above_container));
        views.add(new SimpleTextContainer(context, VERTICAL));

        resetConfiguration();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        while (!options.isEmpty()) {
            currentView.preMeasure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), heightMeasureSpec);
            if (currentView.getMeasuredWidth() <= MeasureSpec.getSize(widthMeasureSpec)) {
                break;
            }
            setView(options.remove(0));
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void setView(TextContainerView view) {
        currentView = view;
        removeAllViews();
        addView(view);
    }

    public void setText1(String text) {
        for (TextContainerView view : views) {
            view.setText1(text);
        }
        resetConfiguration();
    }

    public String getText1() {
        return currentView.getText1();
    }

    public void setText2(String text) {
        for (TextContainerView view : views) {
            view.setText2(text);
        }
        resetConfiguration();
    }

    public String getText2() {
        return currentView.getText2();
    }

    public void setText3(String text) {
        for (TextContainerView view : views) {
            view.setText3(text);
        }
        resetConfiguration();
    }

    public String getText3() {
        return currentView.getText3();
    }

    private void resetConfiguration() {
        options.clear();
        for (int i = 1; i < views.size(); i++) {
            options.add(views.get(i));
        }
        setView(views.get(0));
        invalidate();
    }
}
