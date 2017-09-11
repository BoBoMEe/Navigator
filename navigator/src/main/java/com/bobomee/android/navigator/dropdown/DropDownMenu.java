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

import android.animation.TimeInterpolator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bobomee.android.navigator.R;
import com.bobomee.android.navigator.adapter.interfaces.IAdapter;
import com.bobomee.android.navigator.dropdown.interfaces.DropDownMenuCheckedChange;
import com.bobomee.android.navigator.dropdown.interfaces.DropDownMenuCheckedListener;
import com.bobomee.android.navigator.dropdown.interfaces.Expandable;
import com.bobomee.android.navigator.expandable.interfaces.ExpandableLayoutListenerAdapter;
import com.bobomee.android.navigator.tab.TabGroup;
import com.bobomee.android.navigator.tab.TabView;
import com.bobomee.android.navigator.tab.interfaces.ITabGroup;
import com.bobomee.android.navigator.tab.interfaces.OnTabGroupCheckedChangeListener;

/**
 * Created on 2017/1/22.下午2:14.
 *
 * @author bobomee.
 */

public class DropDownMenu extends LinearLayoutCompat implements Expandable {

  private TabContainer mTabContainer;
  private ExpandableContainer mExpandableRelativeLayout;

  private ViewGroup mExpandableParent;

  private CheckObj mCheckObj = new CheckObj(false, 0);

  private DropDownMenuCheckedChange mDropDownMenuCheckedChange;

  public void addDropDownMenuCheckedListener(
      DropDownMenuCheckedListener pDropDownMenuCheckedListener) {
    if (null == mDropDownMenuCheckedChange) {
      mDropDownMenuCheckedChange = new DropDownMenuCheckedChange();
    }
    mDropDownMenuCheckedChange.addListener(pDropDownMenuCheckedListener);
  }

  public boolean removeDropDownMenuCheckedListener(
      DropDownMenuCheckedListener pDropDownMenuCheckedListener) {
    if (null != mDropDownMenuCheckedChange) {
      return mDropDownMenuCheckedChange.removeListener(pDropDownMenuCheckedListener);
    } else {
      return false;
    }
  }

  public DropDownMenu(Context context) {
    super(context);
    init();
  }

  public DropDownMenu(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public DropDownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    setOrientation(VERTICAL);
    LayoutInflater.from(getContext()).inflate(R.layout.dropdown_menu_layout, this);
    mTabContainer = (TabContainer) findViewById(R.id.drop_tab_container);

    mExpandableRelativeLayout =
        (ExpandableContainer) findViewById(R.id.expandable_layout_container);
    mExpandableParent = (ViewGroup) findViewById(R.id.expandable_parent);
    mExpandableParent.setVisibility(INVISIBLE);

    mExpandableParent.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        toggle();
      }
    });

    initListener();
  }

  private void initListener() {
    mTabContainer.addOnCheckedChangeListener(new OnTabGroupCheckedChangeListener() {
      @Override public void onCheckedChange(ITabGroup group, int checkedId) {
        TabGroup tabGroup = (TabGroup) group;
        TabView tabView = (TabView) tabGroup.findViewById(checkedId);

        if (null != tabView) {
          boolean checked = tabView.isChecked();

          int index = tabGroup.indexOfChild(tabView);
          //notify last

          if (mCheckObj.isChecked() && checked) {
            int position = mCheckObj.getPosition();
            if (null != mDropDownMenuCheckedChange) {
              mDropDownMenuCheckedChange.onCheckedChange(mTabContainer, mExpandableRelativeLayout,
                  position, false);
            }
          }

          //notify current
          if (null != mDropDownMenuCheckedChange) {
            mDropDownMenuCheckedChange.onCheckedChange(mTabContainer, mExpandableRelativeLayout,
                index, checked);
          }

          // init value
          mCheckObj.set(index, checked);

          if (null != mExpandableRelativeLayout) {
            mExpandableRelativeLayout.checkState(index, checked);
          }
        }
      }
    });

    mExpandableRelativeLayout.addExpandableLayoutListener(new ExpandableLayoutListenerAdapter() {
      @Override public void onPreClose() {
        super.onPreClose();
        mExpandableParent.setVisibility(INVISIBLE);
      }

      @Override public void onPreOpen() {
        super.onPreOpen();
        mExpandableParent.setVisibility(VISIBLE);
      }

      @Override public void onAnimationEnd() {
        super.onAnimationEnd();
        if (null != mTabContainer) {
          int position = mCheckObj.getPosition();
          boolean checked = mExpandableRelativeLayout.isExpanded();
          mTabContainer.setCheckedStateForView(checked, position);
        }
        mCheckObj.setChecked(mExpandableRelativeLayout.isExpanded());
      }
    });
  }

  public <T> void setTabAdapter(final IAdapter<T> adapter) {
    if (null != mTabContainer) {
      mTabContainer.setTabAdapter(adapter);
      mExpandableRelativeLayout.setTabAdapter(adapter);
    }
  }

  @Override public boolean isExpanded() {
    boolean result = false;
    if (null != mExpandableRelativeLayout) result = mExpandableRelativeLayout.isExpanded();
    return result;
  }

  @Override public void toggle() {
    if (null != mTabContainer && null != mExpandableRelativeLayout) {
      mExpandableRelativeLayout.addExpandableLayoutListener(new ExpandableLayoutListenerAdapter() {
        @Override public void onAnimationEnd() {
          super.onAnimationEnd();
          mTabContainer.setCheckedStateForView(mExpandableRelativeLayout.isExpanded(),
              mCheckObj.getPosition());
          mCheckObj.setChecked(mExpandableRelativeLayout.isExpanded());
        }
      });
      mExpandableRelativeLayout.checkState(mCheckObj.getPosition(),
          !mExpandableRelativeLayout.isExpanded());
    }
  }

  @Override public void expand(int position) {
    if (null != mTabContainer && null != mExpandableRelativeLayout) {
      mTabContainer.setCheckedStateForView(true, position);
      mExpandableRelativeLayout.checkState(position, true);
    }
    mCheckObj.set(position, true);
  }

  @Override public void collapse(int position) {
    if (null != mTabContainer && null != mExpandableRelativeLayout) {
      mTabContainer.setCheckedStateForView(false, position);
      mExpandableRelativeLayout.checkState(position, false);
    }
    mCheckObj.set(position, false);
  }

  @Override public void setExpanded(int position, boolean expanded) {
    if (null != mTabContainer && null != mExpandableRelativeLayout) {
      mTabContainer.setCheckedStateForView(expanded, position);
      mExpandableRelativeLayout.checkState(position, expanded);
    }
    mCheckObj.set(position, expanded);
  }

  @Override public void setDuration(int duration) {
    if (null != mExpandableRelativeLayout) mExpandableRelativeLayout.setDuration(duration);
  }

  @Override public void setInterpolator(@NonNull TimeInterpolator interpolator) {
    if (null != mExpandableRelativeLayout) mExpandableRelativeLayout.setInterpolator(interpolator);
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    if (null != mDropDownMenuCheckedChange) {
      mDropDownMenuCheckedChange.clearDropDownMenuCheckedListener();
    }
  }
}
