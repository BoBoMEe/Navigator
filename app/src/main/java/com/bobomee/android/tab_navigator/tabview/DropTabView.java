package com.bobomee.android.tab_navigator.tabview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bobomee.android.navigator.tab.TabView;
import com.bobomee.android.tab_navigator.R;

/**
 * Created on 2016/11/16.下午3:26.
 *
 * @author bobomee.
 * @description
 */

public class DropTabView extends TabView {
  TextView textView;
  View bottomLine;
  private ImageView mRightImage;

  public DropTabView(Context context) {
    this(context, null);
  }

  public DropTabView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public DropTabView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    View view =  LayoutInflater.from(getContext()).inflate(R.layout.layout_tab_drop_down,this, true);
    textView = (TextView) view.findViewById(R.id.textView);
    bottomLine = view.findViewById(R.id.bottomLine);
    mRightImage = (ImageView) view.findViewById(R.id.iv_tab_img);

  }


  public void setText(CharSequence text) {
    textView.setText(text);
  }

  public void setChecked(boolean checked) {
    super.setChecked(checked);
    Drawable icon;
    if (checked) {
      icon = getResources().getDrawable(R.drawable.ic_dropdown_actived);
      bottomLine.setVisibility(VISIBLE);
    } else {
      icon = getResources().getDrawable(R.drawable.ic_dropdown_normal);
      bottomLine.setVisibility(INVISIBLE);
    }

    mRightImage.setImageDrawable(icon);
  }

  public View getBottomLine() {
    return bottomLine;
  }

  public void setBottomLine(View pBottomLine) {
    bottomLine = pBottomLine;
  }

  public ImageView getRightImage() {
    return mRightImage;
  }

  public TextView getTextView() {
    return textView;
  }

  public void setTextView(TextView pTextView) {
    textView = pTextView;
  }
}
