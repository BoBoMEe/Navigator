package com.bobomee.android.tab_navigator.tabview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bobomee.android.navigator.view.TabView;
import com.bobomee.android.tab_navigator.R;

public class ImageTabView extends TabView {

  public static final int IMG_DEFAULT_SIZE = 30;
  public static final int TEXT_DEFAULT_SIZE = 14;
  private static final float IMG_DEFAULT_MARGIN = 0;
  ImageView mTabImgView;
  TextView mTabTextView;

  private String mTextString; // 文本
  private ColorStateList mTextColor; // 文字颜色
  //文字大小 默认单位sp
  private float mTextSize = TEXT_DEFAULT_SIZE;
  //默认图片尺寸 默认单位dp
  private float mImgDimension =
      TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, IMG_DEFAULT_SIZE,
          getResources().getDisplayMetrics());
  //图片外边距
  private float mImgMargin =
      TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, IMG_DEFAULT_MARGIN,
          getResources().getDisplayMetrics());

  private Drawable mImgDrawable;//图片资源

  public ImageTabView(Context context) {
    this(context, null);
  }

  public ImageTabView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ImageTabView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(attrs, defStyle);
  }

  private void init(AttributeSet attrs, int defStyle) {
    // Load attributes
    final TypedArray a =
        getContext().obtainStyledAttributes(attrs, R.styleable.TabView, defStyle, 0);

    mTextString = a.getString(R.styleable.TabView_textString);
    mTextColor = a.getColorStateList(R.styleable.TabView_textColor);
    mTextSize = a.getDimensionPixelSize(R.styleable.TabView_textSize, -1);
    //文字大小 特殊处理
    if (mTextSize != -1) {
      mTextSize = px2sp(getContext(), mTextSize);
    } else {
      mTextSize = TEXT_DEFAULT_SIZE;
    }
    // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
    // values that should fall on pixel boundaries.
    mImgDimension = a.getDimension(R.styleable.TabView_imgDimension, mImgDimension);
    mImgMargin = a.getDimension(R.styleable.TabView_imgMargin, mImgMargin);

    if (a.hasValue(R.styleable.TabView_imgDrawable)) {
      mImgDrawable = a.getDrawable(R.styleable.TabView_imgDrawable);
    }

    a.recycle();

    //初始化
    initView();
  }

  /**
   * 初始化界面
   */
  private void initView() {
    View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_tab_txt_image, null);
    mTabImgView = (ImageView) inflate.findViewById(R.id.iv_tab_img);
    mTabTextView = (TextView) inflate.findViewById(R.id.tv_tab_text);

    //设置自定义属性
    setTextString(mTextString);
    setTextColor(mTextColor);
    setTextSize(mTextSize);
    setImgDrawable(mImgDrawable);
    setImgDimension(mImgDimension);
    setImgMargin(mImgMargin);

    setContentView(inflate);
  }

  //////////////////////////////////////set/get////
  public float getTextSize() {
    return mTextSize;
  }

  public ImageTabView setTextSize(float mTextSize) {
    if (mTextSize > 0) {
      this.mTextSize = mTextSize;
      mTabTextView.setTextSize(mTextSize);
    }
    return this;
  }

  public float getImgDimension() {
    return mImgDimension;
  }

  public ImageTabView setImgDimension(float mImgDimension) {
    if (mImgDimension > 0) {
      this.mImgDimension = mImgDimension;
      //如果自定义
      LinearLayout.LayoutParams layoutParams =
          (LinearLayout.LayoutParams) mTabImgView.getLayoutParams();
      layoutParams.height = (int) mImgDimension;
    }
    return this;
  }

  public Drawable getImgDrawable() {
    return mImgDrawable;
  }

  public ImageTabView setImgDrawable(Drawable mImgDrawable) {
    if (mImgDrawable != null) {
      this.mImgDrawable = mImgDrawable;
      mTabImgView.setImageDrawable(mImgDrawable);
    }
    return this;
  }

  public ImageView getTabImgView() {
    return mTabImgView;
  }

  public TextView getTabTextView() {
    return mTabTextView;
  }

  public String getTextString() {
    return mTextString;
  }

  public ImageTabView setTextString(String mTextString) {
    this.mTextString = mTextString;
    mTabTextView.setText(mTextString);
    return this;
  }

  public ColorStateList getTextColor() {
    return mTextColor;
  }

  public ImageTabView setTextColor(ColorStateList mTextColor) {
    if (mTextColor != null) {
      this.mTextColor = mTextColor;
      mTabTextView.setTextColor(mTextColor);
    }
    return this;
  }

  public float getImgMargin() {
    return mImgMargin;
  }

  public ImageTabView setImgMargin(float mImgMargin) {
    this.mImgMargin = mImgMargin;
    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mTabImgView.getLayoutParams();
    marginLayoutParams.setMargins(((int) mImgMargin), ((int) mImgMargin), ((int) mImgMargin),
        ((int) mImgMargin));
    return this;
  }

  public int px2sp(Context context, float pxValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (pxValue / fontScale + 0.5f);
  }
}
