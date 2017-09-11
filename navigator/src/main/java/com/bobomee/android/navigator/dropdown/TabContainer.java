/*
 * Copyright (C) 2017.  BoBoMEe(wbwjx115@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.bobomee.android.navigator.dropdown;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import com.bobomee.android.navigator.adapter.interfaces.IAdapter;
import com.bobomee.android.navigator.tab.TabGroup;

/**
 * Created on 2016/10/24.下午11:29.
 *
 * @author bobomee.
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

  public <T> void setTabAdapter(IAdapter<T> adapter) {
    if (mTAdapter == adapter) {
      return;
    }

    if (null != mTAdapter) {
      mTAdapter.unregisterDataSetObserver(mDataSetObserver);
    }

    mTAdapter = adapter;

    if (null != mTAdapter) {
      mTAdapter.registerDataSetObserver(mDataSetObserver);
      mTAdapter.notifyDataSetChanged();
    }
  }
}
