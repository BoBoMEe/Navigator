package com.bobomee.android.tab_navigator.tabview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bobomee.android.navigator.view.TabView;
import com.bobomee.android.tab_navigator.R;

/**
 * Created on 2016/11/16.下午3:26.
 *
 * @author bobomee.
 * @description
 */

public class ItemTabView extends TabView {
  TextView textView;
  ImageView mImageView;

  public ItemTabView(Context context) {
    this(context, null);
  }

  public ItemTabView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ItemTabView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    View view =  LayoutInflater.from(getContext()).inflate(R.layout.layout_tab_txt_checkbox,this, true);
    textView = (TextView) view.findViewById(R.id.textView);
    mImageView = (ImageView) view.findViewById(R.id.image);
  }


  public void setText(CharSequence text) {
    textView.setText(text);
  }

  public void setChecked(boolean checked) {
    super.setChecked(checked);

    mImageView.setVisibility(checked?VISIBLE:GONE);
  }


}
