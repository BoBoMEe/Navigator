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
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Space;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bobomee.android.navigator.adapter.TabAdapter;
import com.bobomee.android.navigator.adapter.DropDownMenuAdapter;
import com.bobomee.android.navigator.dropdown.DropDownMenu;
import com.bobomee.android.navigator.dropdown.TabContainer;
import com.bobomee.android.navigator.expandable.Utils;
import com.bobomee.android.tab_navigator.animator.ObjectAnimatorUtils;
import com.bobomee.android.tab_navigator.recyclerview.CheckedDataProvider;
import com.bobomee.android.tab_navigator.recyclerview.RecyclerModel;
import com.bobomee.android.tab_navigator.recyclerview.RecyclerProvider;
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
  private DropDownMenu_Activity mDropDownMenu_activity;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_drop_down_menu_sample);
    ButterKnife.bind(this);
    mDropDownMenu_activity = this;

    initData();

    initTabContainer();
    initTabContainer1();
  }

  private void initTabContainer() {

    mDropDownMenu.setTabAdapter(new DropDownMenuAdapter<String>(mTitles) {
      @Override public View getView(int position, ViewGroup parent, String object) {
        DropTabView dropdownButton = new DropTabView(getApplicationContext());

        dropdownButton.setText(object);
        dropdownButton.setId(position);

        dropdownButton.addOnCheckedChangeListener((tabView, isChecked) -> {
          DropTabView tabView1 = (DropTabView) tabView;
          if (isChecked) {
            ObjectAnimatorUtils.object_animator(tabView1.getTextView());
          }
          ObjectAnimatorUtils.object_rotate(tabView1.getRightImage(), isChecked);
        });

        return dropdownButton;
      }

      @Override public View getDropView(int position, ViewGroup parent, String object) {

        View view;

        switch (position) {
          case 0:
            view = RecyclerProvider.provideLinearLayoutRecycler(mDropDownMenu_activity);
            break;
          case 1:
            view = RecyclerProvider.provideGridLayoutRecycler(mDropDownMenu_activity);
            break;
          case 2:
            view = RecyclerProvider.provideLinearLayoutRecycler(mDropDownMenu_activity);
            break;
          case 3:
            view = RecyclerProvider.provideGridLayoutRecycler(mDropDownMenu_activity);
            break;
          default:
            view = new Space(mDropDownMenu_activity);
            break;
        }

        return view;
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

        mDropDownMenu.setExpanded(0, true);
      }
    });

    mDropDownMenu.addDropDownMenuCheckedListener((tabContainer, dropContainer, tabPos, opened) -> {
      if (!opened) {
        CheckedDataProvider<RecyclerModel> checkedDataProvider =
            RecyclerProvider.provideView(DropDownMenu_Activity.this, dropContainer, tabPos);
        if (null != checkedDataProvider) {

          List<RecyclerModel> recyclerModels =
              RecyclerProvider.provideCheckedDatas(checkedDataProvider);

          DropTabView lChildAt = (DropTabView) tabContainer.getChildAt(tabPos);
          lChildAt.setText(recyclerModels.size() + "");
        }
      }
    });

    ///
    mDropDownMenu.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
  }

  private void initTabContainer1() {
    mTabContainer1.setTabAdapter(new TabAdapter<String>(mTitles) {
      @Override public View getView(int position, ViewGroup parent, String object) {
        ItemTabView itemTabView = new ItemTabView(getApplicationContext());

        itemTabView.setText(object);
        itemTabView.setId(position);
        itemTabView.addOnCheckedChangeListener(
            (tabView, isChecked) -> ObjectAnimatorUtils.object_animator((View) tabView));

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
