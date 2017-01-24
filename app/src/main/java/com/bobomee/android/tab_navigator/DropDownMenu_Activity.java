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

package com.bobomee.android.tab_navigator;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bobomee.android.navigator.adapter.AdapterBase;
import com.bobomee.android.navigator.adapter.AdapterDropBase;
import com.bobomee.android.navigator.dropdown.DropDownMenu;
import com.bobomee.android.navigator.dropdown.ExpandableContainer;
import com.bobomee.android.navigator.dropdown.TabContainer;
import com.bobomee.android.navigator.expandable.interfaces.ExpandableLayoutListenerAdapter;
import com.bobomee.android.navigator.tab.TabGroup;
import com.bobomee.android.navigator.tab.TabView;
import com.bobomee.android.navigator.tab.interfaces.ITabView;
import com.bobomee.android.navigator.tab.interfaces.OnTabViewCheckedChangeListener;
import com.bobomee.android.tab_navigator.tabview.DropTabView;
import com.bobomee.android.tab_navigator.tabview.ItemTabView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/1/21.下午7:46.
 *
 * @author bobomee.
 */

public class DropDownMenu_Activity extends AppCompatActivity {

  @BindView(R.id.tab_container1) TabContainer mTabContainer1;
  @BindView(R.id.drop_down_menu) DropDownMenu mDropDownMenu;
  private List<String> mTitles;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_drop_down_menu_sample);
    ButterKnife.bind(this);

    initData();

    initTabContainer();
    initTabContainer1();
  }

  private void initTabContainer() {
    mDropDownMenu.setTabAdapter(new AdapterDropBase<String>(mTitles) {
      @Override public View getView(int position, ViewGroup parent, String object) {
        DropTabView dropdownButton = new DropTabView(getApplicationContext());

        dropdownButton.setText(object);
        dropdownButton.setId(position);

        dropdownButton.addOnCheckedChangeListener(new OnTabViewCheckedChangeListener() {
          @Override public void onCheckedChange(ITabView tabView, boolean isChecked) {
            TabView lTabView = (TabView) tabView;
            ViewGroup lViewGroup = (ViewGroup) lTabView.getParent();
            int index = lViewGroup.indexOfChild(lTabView);

            Log.d("BoBoMEe", "Tab CheckedChange, index :  " + index + " ,isChecked : " + isChecked);
          }
        });

        dropdownButton.removeOnCheckedChangeListener(new OnTabViewCheckedChangeListener() {
          @Override public void onCheckedChange(ITabView tabView, boolean isChecked) {

          }
        });

        return dropdownButton;
      }

      @Override public View getDropView(int position, ViewGroup parent, String object) {

        TextView inflate =
            (TextView) View.inflate(DropDownMenu_Activity.this, R.layout.drop_down_text_layout,
                null);

        inflate.setText(getResources().getString(R.string.drop_content) +"\n"+ String.valueOf(position));

        return inflate;
      }
    });
    
    mDropDownMenu.setExpanded(false);

    TabContainer lTabContainer = mDropDownMenu.getTabContainer();
    ExpandableContainer lExpandableRelativeLayout = mDropDownMenu.getExpandableRelativeLayout();

    //////
    lExpandableRelativeLayout.addExpandableLayoutListener(new ExpandableLayoutListenerAdapter() {
      @Override public void onAnimationEnd() {
        super.onAnimationEnd();
        Log.d("BoBoMEe", "onAnimationEnd: ");
      }

      @Override public void onAnimationStart() {
        super.onAnimationStart();
        Log.d("BoBoMEe", "onAnimationStart: ");
      }

      @Override public void onClosed() {
        super.onClosed();
        Log.d("BoBoMEe", "onClosed: ");
      }

      @Override public void onOpened() {
        super.onOpened();
        Log.d("BoBoMEe", "onOpened: ");
      }

      @Override public void onPreClose() {
        super.onPreClose();
        Log.d("BoBoMEe", "onPreClose: ");
      }

      @Override public void onPreOpen() {
        super.onPreOpen();
        Log.d("BoBoMEe", "onPreOpen: ");
      }
    });

    lExpandableRelativeLayout.removeExpandableLayoutListener(new ExpandableLayoutListenerAdapter() {
    });

    ////////
    lTabContainer.addOnCheckedChangeListener((group, checkedId) -> {

      TabGroup tabGroup = (TabGroup) group;
      TabView tabview = (TabView) tabGroup.findViewById(checkedId);
      int index = tabGroup.indexOfChild(tabview);
      boolean lChecked = tabview.isChecked();

      Log.d("BoBoMEe", "Container CheckedChange , index : " + index + " , lChecked : " + lChecked);
    });

    lTabContainer.removeOnCheckedChangeListener((group, checkedId) -> {

    });

    ////
    lTabContainer.setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
      @Override public void onChildViewAdded(View parent, View child) {
        Log.d("BoBoMEe", "onChildViewAdded: ");
      }

      @Override public void onChildViewRemoved(View parent, View child) {
        Log.d("BoBoMEe", "onChildViewRemoved: ");
      }
    });

    /////
    mDropDownMenu.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
      @Override public void onGlobalLayout() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
          mDropDownMenu.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
          mDropDownMenu.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }

        lTabContainer.setCheckedStateForView(true, 0);
        lExpandableRelativeLayout.checkState(0, true);
      }
    });
    
  }

  private void initTabContainer1() {
    mTabContainer1.setTabAdapter(new AdapterBase<String>(mTitles) {
      @Override public View getView(int position, ViewGroup parent, String object) {
        ItemTabView itemTabView = new ItemTabView(getApplicationContext());

        itemTabView.setText(object);
        itemTabView.setId(position);

        return itemTabView;
      }
    });
  }

  private void initData() {
    mTitles = new ArrayList<>();
    for (int i = 0; i < 4; ++i) {
      mTitles.add("第" + i + "个");
    }
  }
}
