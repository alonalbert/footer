package com.example.footer;

import static androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID;
import static androidx.constraintlayout.widget.ConstraintSet.BOTTOM;
import static androidx.constraintlayout.widget.ConstraintSet.END;
import static androidx.constraintlayout.widget.ConstraintSet.START;
import static androidx.constraintlayout.widget.ConstraintSet.TOP;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

class FooterLayout extends ConstraintLayout {
  private static final int[][] VERTICAL_LAYOUT =
      new int[][] {{R.id.text1}, {R.id.separator1}, {R.id.text2}, {R.id.separator2}, {R.id.text3}};

  private static final int[][] HORIZONTAL_LAYOUT =
      new int[][] {{R.id.text1, R.id.separator1, R.id.text2, R.id.separator2, R.id.text3}};

  private static final int[][] TWO_THEN_ONE_LAYOUT =
      new int[][] {{R.id.text1, R.id.separator1, R.id.text2}, {R.id.text3}};

  private static final int[][] ONE_THEN_TWO_LAYOUT =
      new int[][] {{R.id.text1}, {R.id.text2, R.id.separator2, R.id.text3}};

  private TextView text1;
  private ImageView separator1;
  private TextView text2;
  private ImageView separator2;
  private TextView text3;
  private final ConstraintSet set = new ConstraintSet();

  public FooterLayout(Context context) {
    this(context, null);
  }

  public FooterLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    inflate(context, R.layout.footer, this);

    text1 = findViewById(R.id.text1);
    separator1 = findViewById(R.id.separator1);
    text2 = findViewById(R.id.text2);
    separator2 = findViewById(R.id.separator2);
    text3 = findViewById(R.id.text3);
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

    for (int i = 0, n = getChildCount(); i < n; i++) {
      measureChildWithMargins(
          getChildAt(i), widthMeasureSpec, widthConstraints, heightMeasureSpec, heightConstraints);
    }

    final int widthText1 = getChildWidthWithMargin(text1);
    final int widthSeparator1 = getChildWidthWithMargin(separator1);
    final int widthText2 = getChildWidthWithMargin(text2);
    final int widthSeparator2 = getChildWidthWithMargin(separator2);
    final int widthText3 = getChildWidthWithMargin(text3);

    set.clone(this);
    final int separator1Visibility;
    final int separator2Visibility;
    final int[][] layout;
    if (widthText1 + widthSeparator1 + widthText2 + widthSeparator2 + widthText3 > maxWidth) {
      if (widthText1 + widthSeparator1 + widthText2 <= maxWidth) {
        layout = TWO_THEN_ONE_LAYOUT;
        separator1Visibility = VISIBLE;
        separator2Visibility = GONE;
        text1.setMaxLines(1);
        text2.setMaxLines(1);
        text3.setMaxLines(3);
      } else if (widthText2 + widthSeparator2 + widthText3 <= maxWidth) {
        layout = ONE_THEN_TWO_LAYOUT;
        separator1Visibility = GONE;
        separator2Visibility = VISIBLE;
        text1.setMaxLines(3);
        text2.setMaxLines(1);
        text3.setMaxLines(1);
      } else {
        layout = VERTICAL_LAYOUT;
        separator1Visibility = VISIBLE;
        separator2Visibility = VISIBLE;
        text1.setMaxLines(3);
        text2.setMaxLines(3);
        text3.setMaxLines(3);
      }
    } else {
      layout = HORIZONTAL_LAYOUT;
      separator1Visibility = VISIBLE;
      separator2Visibility = VISIBLE;
      text1.setMaxLines(1);
      text2.setMaxLines(1);
      text3.setMaxLines(1);
    }

    updateConnections(set, layout);
    set.applyTo(this);

    // Visibility must be set after applying constraints
    separator1.setVisibility(separator1Visibility);
    separator2.setVisibility(separator2Visibility);

    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  private void updateConnections(ConstraintSet set, int[][] rows) {

    for (int i = 0; i < rows.length; i++) {
      final int prev = i == 0 ? PARENT_ID : rows[i - 1][0];
      final int next = i == rows.length - 1 ? PARENT_ID : rows[i + 1][0];
      addToVerticalChain(set, rows[i][0], prev, next);
    }
    for (int[] row : rows) {
      for (int i = 0; i < row.length; i++) {
        final int prev = i == 0 ? PARENT_ID : row[i - 1];
        final int next = i == row.length - 1 ? PARENT_ID : row[i + 1];
        addToHorizontalChain(set, row[i], prev, next);
        if (i > 0) {
          set.connect(row[i], TOP, row[0], TOP);
          set.connect(row[i], BOTTOM, row[0], BOTTOM);
        }
      }
    }
  }

  // Like ConstraintSet#addToVerticalChain() but with proper margin
  private void addToVerticalChain(ConstraintSet set, int viewId, int topId, int bottomId) {
    set.connect(viewId, TOP, topId, (topId == PARENT_ID) ? TOP : BOTTOM);
    set.connect(viewId, BOTTOM, bottomId, (bottomId == PARENT_ID) ? BOTTOM : TOP);
    if (topId != PARENT_ID) {
      set.connect(topId, BOTTOM, viewId, TOP);
    }
    if (topId != PARENT_ID) {
      set.connect(bottomId, TOP, viewId, BOTTOM);
    }
  }

  // Like ConstraintSet#addToHorizontalChainRtl() but with proper margin
  public void addToHorizontalChain(ConstraintSet set, int viewId, int leftId, int rightId) {
    set.connect(viewId, START, leftId, (leftId == PARENT_ID) ? START : END);
    set.connect(viewId, END, rightId, (rightId == PARENT_ID) ? END : START);
    if (leftId != PARENT_ID) {
      set.connect(leftId, END, viewId, START);
    }
    if (rightId != PARENT_ID) {
      set.connect(rightId, START, viewId, END);
    }
  }

  private int getChildWidthWithMargin(View view) {
    MarginLayoutParams lp = (MarginLayoutParams) view.getLayoutParams();
    return view.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
  }
}
