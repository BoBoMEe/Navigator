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

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.common.util.DisplayUtil;
import com.bobomee.android.navigator.adapter.AdapterBase;
import com.bobomee.android.navigator.adapter.AdapterDropBase;
import com.bobomee.android.navigator.dropdown.DropDownMenu;
import com.bobomee.android.navigator.dropdown.ExpandableContainer;
import com.bobomee.android.navigator.dropdown.TabContainer;
import com.bobomee.android.navigator.expandable.Utils;
import com.bobomee.android.navigator.expandable.interfaces.ExpandableLayoutListenerAdapter;
import com.bobomee.android.navigator.tab.TabGroup;
import com.bobomee.android.navigator.tab.TabView;
import com.bobomee.android.recyclerviewhelper.selectclick.click.ItemClickSupport;
import com.bobomee.android.tab_navigator.animator.ObjectAnimatorUtils;
import com.bobomee.android.tab_navigator.recyclerview.HorizontalDividerItemDecoration.Builder;
import com.bobomee.android.tab_navigator.recyclerview.RecyclerAdapterProvider;
import com.bobomee.android.tab_navigator.recyclerview.RecyclerAdapterProvider.RecyclerAdapter;
import com.bobomee.android.tab_navigator.recyclerview.RecyclerModel;
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
  @BindView(R.id.text_content) TextView mTextContent;
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

        dropdownButton.addOnCheckedChangeListener((tabView, isChecked) -> {
          TabView lTabView = (TabView) tabView;
          ViewGroup lViewGroup = (ViewGroup) lTabView.getParent();
          int index = lViewGroup.indexOfChild(lTabView);

          Log.d("BoBoMEe", "Tab CheckedChange, index :  " + index + " ,isChecked : " + isChecked);

          if (isChecked) {
            View vtabView = (View) tabView;
            ObjectAnimatorUtils.object_rotate(vtabView);
          }
        });

        dropdownButton.removeOnCheckedChangeListener((tabView, isChecked) -> {
          if (mDropDownMenu.isExpanded()) mDropDownMenu.toggle();
        });

        return dropdownButton;
      }

      @Override public View getDropView(int position, ViewGroup parent, String object) {

        RecyclerView inflate = (RecyclerView) View.inflate(DropDownMenu_Activity.this,
            R.layout.drop_down_recycler_layout, null);

        LayoutParams lLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);

        inflate.setLayoutManager(new LinearLayoutManager(DropDownMenu_Activity.this));

        List<RecyclerModel> lRecyclerModelList;

        if (position == 0) {
          lRecyclerModelList = RecyclerModel.getModels(5);
          lLayoutParams.bottomMargin = 300;
        } else if (position == 1) {
          lRecyclerModelList = RecyclerModel.getModels(15);
          lLayoutParams.bottomMargin = 300;
        } else if (position == 2) {
          lRecyclerModelList = RecyclerModel.getModels(25);
          lLayoutParams.bottomMargin = 600;
        } else {
          lRecyclerModelList = RecyclerModel.getModels(5);
          lLayoutParams.bottomMargin = 800;
        }

        inflate.setLayoutParams(lLayoutParams);

        RecyclerAdapter lAdapter = RecyclerAdapterProvider.createAdapter(lRecyclerModelList);

        inflate.addItemDecoration(new Builder(DropDownMenu_Activity.this)//divider 颜色
            .colorResId(R.color.colorPrimary).size(2)//高度
            .margin(DisplayUtil.dp2px(12.f))//边距
            .build());
        inflate.setAdapter(lAdapter);

        ItemClickSupport lItemClickSupport = ItemClickSupport.from(inflate).add();
        lItemClickSupport.addOnItemClickListener((parent1, view, position1, id) -> {

          ObjectAnimatorUtils.object_left_right(view);

          RecyclerModel lRecyclerModel = lAdapter.getData().get(position1);

          lRecyclerModel.setChecked(!lRecyclerModel.isChecked());

          lAdapter.notifyItemChanged(position1);
        });
        
        inflate.setOnClickListener(v -> {
          
        });

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
        if (VERSION.SDK_INT < VERSION_CODES.JELLY_BEAN) {
          mDropDownMenu.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
          mDropDownMenu.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }

        lTabContainer.setCheckedStateForView(true, 0);
        lExpandableRelativeLayout.checkState(0, true);
      }
    });

    ///
    mDropDownMenu.setInterpolator(
        Utils.createInterpolator(Utils.ANTICIPATE_OVERSHOOT_INTERPOLATOR));

  }

  private void initTabContainer1() {
    mTabContainer1.setTabAdapter(new AdapterBase<String>(mTitles) {
      @Override public View getView(int position, ViewGroup parent, String object) {
        ItemTabView itemTabView = new ItemTabView(getApplicationContext());

        itemTabView.setText(object);
        itemTabView.setId(position);
        itemTabView.addOnCheckedChangeListener((tabView, isChecked) -> {
          ObjectAnimatorUtils.object_animator((View) tabView);
        });

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

  @OnClick(R.id.text_content) public void onClick() {
    if (mDropDownMenu.isExpanded()) {
      mDropDownMenu.collapse();
    }
  }
}
