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
import android.widget.RelativeLayout;
import com.bobomee.android.navigator.R;
import com.bobomee.android.navigator.adapter.interfaces.IAdapter;
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
  private int actionPos;
  private RelativeLayout mExpandableParent;

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

    final View lInflate =
        LayoutInflater.from(getContext()).inflate(R.layout.drop_down_menu_layout, this, true);

    mTabContainer = (TabContainer) lInflate.findViewById(R.id.drop_tab_container);
    mExpandableRelativeLayout =
        (ExpandableContainer) lInflate.findViewById(R.id.expandable_layout_container);
    mExpandableParent = (RelativeLayout) lInflate.findViewById(R.id.expandable_parent_releative);
    mExpandableParent.setVisibility(INVISIBLE);

    mTabContainer.addOnCheckedChangeListener(new OnTabGroupCheckedChangeListener() {
      @Override public void onCheckedChange(ITabGroup group, int checkedId) {
        TabGroup lTabGroup = (TabGroup) group;
        TabView lITabView = (TabView) lTabGroup.findViewById(checkedId);

        if (null != lITabView) {
          boolean lChecked = lITabView.isChecked();

          int lPosition = lTabGroup.indexOfChild(lITabView);
          actionPos = lPosition;

          if (null != mExpandableRelativeLayout) {
            mExpandableRelativeLayout.checkState(lPosition, lChecked);
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
    });
    
  }

  public <T> void setTabAdapter(final IAdapter<T> _tAdapter) {
    if (null != mTabContainer) {
      mTabContainer.setTabAdapter(_tAdapter);
      mExpandableRelativeLayout.setTabAdapter(_tAdapter);
    }
  }

  public ExpandableContainer getExpandableRelativeLayout() {
    return mExpandableRelativeLayout;
  }

  public TabContainer getTabContainer() {
    return mTabContainer;
  }

  @Override public void toggle() {
    if (null != mExpandableRelativeLayout) mExpandableRelativeLayout.toggle();
    if (null != mTabContainer && null != mExpandableRelativeLayout) {
      mExpandableRelativeLayout.addExpandableLayoutListener(new ExpandableLayoutListenerAdapter() {
        @Override public void onAnimationEnd() {
          super.onAnimationEnd();
          mTabContainer.setCheckedStateForView(mExpandableRelativeLayout.isExpanded(), actionPos);
        }
      });
    }
  }

  @Override public void expand() {
    if (null != mExpandableRelativeLayout) mExpandableRelativeLayout.expand();
    if (null != mTabContainer && null != mExpandableRelativeLayout) {
      mExpandableRelativeLayout.addExpandableLayoutListener(new ExpandableLayoutListenerAdapter() {
        @Override public void onAnimationEnd() {
          super.onAnimationEnd();
          mTabContainer.setCheckedStateForView(mExpandableRelativeLayout.isExpanded(), actionPos);
        }
      });
    }
  }

  @Override public void collapse() {
    if (null != mExpandableRelativeLayout) mExpandableRelativeLayout.collapse();
    if (null != mTabContainer && null != mExpandableRelativeLayout) {
     mExpandableRelativeLayout.addExpandableLayoutListener(new ExpandableLayoutListenerAdapter() {
       @Override public void onAnimationEnd() {
         super.onAnimationEnd();
         mTabContainer.setCheckedStateForView(mExpandableRelativeLayout.isExpanded(), actionPos);
       }
     });
    }
  }

  @Override public boolean isExpanded() {
    boolean result = false;
    if (null != mExpandableRelativeLayout) result = mExpandableRelativeLayout.isExpanded();
    return result;
  }

  @Override public void setExpanded(boolean expanded) {
    if (null != mExpandableRelativeLayout) {
      mExpandableRelativeLayout.setExpanded(expanded);
    }
  }

  @Override public void setDuration(int duration) {
    if (null != mExpandableRelativeLayout) mExpandableRelativeLayout.setDuration(duration);
  }

  @Override public void setInterpolator(@NonNull TimeInterpolator interpolator) {
    if (null != mExpandableRelativeLayout) mExpandableRelativeLayout.setInterpolator(interpolator);
  }
}
