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

public class DropDownMenu1 extends LinearLayoutCompat implements Expandable {

  private TabContainer mTabContainer;
  private ExpandableContainer mExpandableRelativeLayout;

  private ViewGroup mExpandableParent;
  private View mContent;

  private CheckObj mCheckObj = new CheckObj(false, 0);

  private DropDownMenuCheckedChange mDropDownMenuCheckedChange = new DropDownMenuCheckedChange();

  public void addDropDownMenuCheckedListener(
      DropDownMenuCheckedListener pDropDownMenuCheckedListener) {
    mDropDownMenuCheckedChange.addListener(pDropDownMenuCheckedListener);
  }

  public void removeDropDownMenuCheckedListener(
      DropDownMenuCheckedListener pDropDownMenuCheckedListener) {
    mDropDownMenuCheckedChange.removeListener(pDropDownMenuCheckedListener);
  }

  public DropDownMenu1(Context context) {
    super(context);
    init();
  }

  public DropDownMenu1(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public DropDownMenu1(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    setOrientation(VERTICAL);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    
    mTabContainer = (TabContainer) findViewById(R.id.drop_tab_container);
    mExpandableRelativeLayout =
        (ExpandableContainer) findViewById(R.id.expandable_layout_container);
    mExpandableParent =  (ViewGroup) findViewById(R.id.expandable_parent);
    mContent = findViewById(R.id.content);
    mExpandableParent.setVisibility(INVISIBLE);
    initListener();
  }

  private void initListener() {
    mTabContainer.addOnCheckedChangeListener(new OnTabGroupCheckedChangeListener() {
      @Override public void onCheckedChange(ITabGroup group, int checkedId) {
        TabGroup lTabGroup = (TabGroup) group;
        TabView lITabView = (TabView) lTabGroup.findViewById(checkedId);

        if (null != lITabView) {
          boolean lChecked = lITabView.isChecked();

          int lPosition = lTabGroup.indexOfChild(lITabView);
          //notify last

          if (mCheckObj.isChecked() && lChecked) {
            int lPosition1 = mCheckObj.getPosition();
            mDropDownMenuCheckedChange.onCheckedChange(lPosition1, false);
          }

          //notify current
          mDropDownMenuCheckedChange.onCheckedChange(lPosition, lChecked);

          // init value
          mCheckObj.set(lPosition, lChecked);
          
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

      @Override public void onAnimationEnd() {
        super.onAnimationEnd();
        if (null != mTabContainer) {
          int position = mCheckObj.getPosition();
          boolean lChecked = mExpandableRelativeLayout.isExpanded();
          mTabContainer.setCheckedStateForView(lChecked, position);
        }
        mCheckObj.setChecked(mExpandableRelativeLayout.isExpanded());
      }
    });
  }

  public <T> void setTabAdapter(final IAdapter<T> _tAdapter) {
    if (null != mTabContainer) {
      mTabContainer.setTabAdapter(_tAdapter);
      mExpandableRelativeLayout.setTabAdapter(_tAdapter);
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

  public View getContent() {
    return mContent;
  }

  public ViewGroup getExpandableParent() {
    return mExpandableParent;
  }

  public ExpandableContainer getExpandableRelativeLayout() {
    return mExpandableRelativeLayout;
  }

  public TabContainer getTabContainer() {
    return mTabContainer;
  }
}
