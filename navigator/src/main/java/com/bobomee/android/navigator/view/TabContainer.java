package com.bobomee.android.navigator.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import com.bobomee.android.navigator.R;
import com.bobomee.android.navigator.adapter.IAdapter;

/**
 * Created on 2016/10/24.下午11:29.
 *
 * @author bobomee.
 * @description
 */

public class TabContainer extends TabGroup {
  public TabContainer(Context context) {
    super(context);
  }

  public TabContainer(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TabContainer(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  private DataSetObserver mDataSetObserver = new DataSetObserver() {
    @Override public void onChanged() {
      onChage();
    }

    @Override public void onInvalidated() {
      super.onInvalidated();
    }
  };

  @SuppressWarnings("unchecked") private void onChage() {
    removeAllViews();

    for (int i = 0; i < mTAdapter.getCount(); ++i) {
      View tabView = mTAdapter.getView(i, this, mTAdapter.getItem(i));

      LinearLayoutCompat.LayoutParams layoutParams = new LayoutParams(0, -1);
      layoutParams.weight = 1;

      addView(tabView, i, layoutParams);
    }

    invalidate();
  }

  private IAdapter mTAdapter;

  public <T> void setTabAdapter(IAdapter<T> _tAdapter) {
    if (mTAdapter == _tAdapter) {
      return;
    }

    if (null != mTAdapter) {
      mTAdapter.unregisterDataSetObserver(mDataSetObserver);
    }

    mTAdapter = _tAdapter;

    if (null != mTAdapter) {
      mTAdapter.registerDataSetObserver(mDataSetObserver);
      mTAdapter.notifyDataSetChanged();
    }
  }
}
