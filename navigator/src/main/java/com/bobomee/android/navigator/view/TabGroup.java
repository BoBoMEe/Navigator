/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bobomee.android.navigator.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.bobomee.android.navigator.R;

/**
 * <pre>
 * 仿RadioGroup 的底部tab布局
 * Copyright (c) 2016 isanwenyu@163.com. All rights reserved.
 * </pre>
 *
 * @see ITabView
 * @see android.widget.RadioGroup
 */
public class TabGroup extends LinearLayoutCompat implements ITabGroup {
  // holds the checked id; the selection is empty by default
  private int mCheckedId = -1;
  // tracks children  tab views checked state
  private OnTabViewCheckedChangeListener mChildOnCheckedChangeListener;
  // when true, mOnCheckedChangeListener discards events
  private boolean mProtectFromCheckedChange = false;
  private PassThroughHierarchyChangeListener mPassThroughListener;
  private TabGroupCheckedChange mTabGroupCheckedChange;

  /**
   * {@inheritDoc}
   */
  public TabGroup(Context context) {
    super(context);
    init(null, 0);
  }

  public TabGroup(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0);
  }

  /**
   * {@inheritDoc}
   */
  public TabGroup(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs);
    init(attrs, defStyle);
  }

  private void init(AttributeSet attrs, int defStyle) {

    mChildOnCheckedChangeListener = new CheckedStateTracker();
    mPassThroughListener = new PassThroughHierarchyChangeListener();
    super.setOnHierarchyChangeListener(mPassThroughListener);

    // retrieve selected  tab view as requested by the user in the
    // XML layout file
    TypedArray attributes =
        getContext().obtainStyledAttributes(attrs, R.styleable.TabGroup, defStyle, 0);

    int value = attributes.getResourceId(R.styleable.TabGroup_checkedTab, View.NO_ID);
    if (value != View.NO_ID) {
      mCheckedId = value;
    }

    attributes.recycle();

    setOrientation(HORIZONTAL);
  }

  /**
   * {@inheritDoc}
   */
  @Override protected void onFinishInflate() {
    super.onFinishInflate();

    // checks the appropriate  tab view as requested in the XML file
    if (mCheckedId != -1) {
      mProtectFromCheckedChange = true;
      setCheckedStateForView(mCheckedId, true);
      mProtectFromCheckedChange = false;
      setCheckedId(mCheckedId);
    }
  }

  @Override public void addView(View child, int index, ViewGroup.LayoutParams params) {
    if (child instanceof ITabView) {
      final ITabView button = (ITabView) child;
      if (button.isChecked()) {
        mProtectFromCheckedChange = true;
        if (mCheckedId != -1) {
          setCheckedStateForView(mCheckedId, false);
        }
        mProtectFromCheckedChange = false;
        setCheckedId(button.getId());
      }
    }

    super.addView(child, index, params);
  }

  /**
   * <p>Sets the selection to the  tab view whose identifier is passed in
   * parameter. Using -1 as the selection identifier clears the selection;
   * such an operation is equivalent to invoking {@link #clearCheck()}.</p>
   *
   * @param id the unique id of the  tab view to select in this group
   * @see #getCheckedTabViewId()
   * @see #clearCheck()
   */
  public void check(int id) {
    // don't even bother
    if (id != -1 && (id == mCheckedId)) {
      return;
    }

    if (mCheckedId != -1) {
      setCheckedStateForView(mCheckedId, false);
    }

    if (id != -1) {
      setCheckedStateForView(id, true);
    }

    setCheckedId(id);
  }

  public void setCheckedId(int id) {
    mCheckedId = id;
    if (null != mTabGroupCheckedChange) mTabGroupCheckedChange.onCheckedChanged(this, mCheckedId);
  }

  private void setCheckedStateForView(int viewId, boolean checked) {
    View checkedView = findViewById(viewId);
    if (checkedView != null && checkedView instanceof ITabView) {
      ((ITabView) checkedView).setChecked(checked);
    }
  }

  /**
   * <p>Returns the identifier of the selected  tab view in this group.
   * Upon empty selection, the returned value is -1.</p>
   *
   * @return the unique id of the selected  tab view in this group
   * @see #check(int)
   * @see #clearCheck()
   */
  public int getCheckedTabViewId() {
    return mCheckedId;
  }

  /**
   * <p>Clears the selection. When the selection is cleared, no  tab view
   * in this group is selected and {@link #getCheckedTabViewId()} returns
   * null.</p>
   *
   * @see #check(int)
   * @see #getCheckedTabViewId()
   */
  public void clearCheck() {
    check(-1);
  }

  @Override public CharSequence getAccessibilityClassName() {
    return TabGroup.class.getName();
  }

  //////////////////////////////////////////////////listener//////
  private class CheckedStateTracker implements OnTabViewCheckedChangeListener {
    public void onCheckedChanged(ITabView tabView, boolean isChecked) {
      // prevents from infinite recursion
      if (mProtectFromCheckedChange) {
        return;
      }

      mProtectFromCheckedChange = true;
      int id = tabView.getId();
      if (mCheckedId == id) {
        mCheckedId = View.NO_ID;
      }
      if (mCheckedId != -1) {
        setCheckedStateForView(mCheckedId, false);
      }
      mProtectFromCheckedChange = false;

      setCheckedId(id);
    }
  }

  /**
   * <p>A pass-through listener acts upon the events and dispatches them
   * to another listener. This allows the table layout to set its own internal
   * hierarchy change listener without preventing the user to setup his.</p>
   */
  public class PassThroughHierarchyChangeListener implements OnHierarchyChangeListener {
    private OnHierarchyChangeListener mOnHierarchyChangeListener;

    /**
     * {@inheritDoc}
     */
    public void onChildViewAdded(View parent, View child) {
      if (parent == TabGroup.this && child instanceof ITabView) {
        int id = child.getId();
        // generates an id if it's missing
        if (id == View.NO_ID) {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            id = View.generateViewId();
          } else {
            id = child.hashCode();
          }
          child.setId(id);
        }

        if (((ITabView) child).isChecked()) {
          mCheckedId = child.getId();
        }

        ((ITabView) child).addOnCheckedChangeListener(mChildOnCheckedChangeListener);
      }

      if (mOnHierarchyChangeListener != null) {
        mOnHierarchyChangeListener.onChildViewAdded(parent, child);
      }
    }

    /**
     * {@inheritDoc}
     */
    public void onChildViewRemoved(View parent, View child) {
      if (parent == TabGroup.this && child instanceof ITabView) {
        ((ITabView) child).removeOnCheckedChangeListener(null);
      }

      if (mOnHierarchyChangeListener != null) {
        mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
    // the user listener is delegated to our pass-through listener
    mPassThroughListener.mOnHierarchyChangeListener = listener;
  }

  /**
   * <p>Register a callback to be invoked when the checked  tab view
   * changes in this group.</p>
   *
   * @param listener the callback to call on checked state change
   */
  public void addOnCheckedChangeListener(OnTabGroupCheckedChangeListener listener) {
    if (null == mTabGroupCheckedChange) {
      mTabGroupCheckedChange = new TabGroupCheckedChange();
    }
    mTabGroupCheckedChange.addListener(listener);
  }
}
