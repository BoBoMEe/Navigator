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
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import com.bobomee.android.navigator.adapter.interfaces.IAdapter;
import com.bobomee.android.navigator.expandable.ExpandableRelativeLayout;

/**
 * Created on 2017/1/22.下午8:47.
 *
 * @author bobomee.
 */

public class ExpandableContainer extends ExpandableRelativeLayout {

  private DataSetObserver mDataSetObserver = new DataSetObserver() {
    @Override public void onChanged() {
      onChage();
    }

    @Override public void onInvalidated() {
      super.onInvalidated();
    }
  };

  public ExpandableContainer(Context context) {
    this(context, null);
  }

  public ExpandableContainer(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ExpandableContainer(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = VERSION_CODES.LOLLIPOP)
  public ExpandableContainer(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @SuppressWarnings("unchecked") private void onChage() {
    removeAllViews();

    for (int i = 0; i < mTAdapter.getCount(); ++i) {
      View tabView = mTAdapter.getDropView(i, this, mTAdapter.getItem(i));
      addView(tabView, i);
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

  public void checkState(int position, boolean checked) {

    if (checked) {
      expand();
    } else {
      collapse();
    }

    int lChildCount = getChildCount();
    for (int i = 0; i < lChildCount; i++) {
      View lChildAt = getChildAt(i);
      if (i == position) {
        lChildAt.setVisibility(checked ? VISIBLE : INVISIBLE);
      } else {
        lChildAt.setVisibility(INVISIBLE);
      }
    }
  }
}
