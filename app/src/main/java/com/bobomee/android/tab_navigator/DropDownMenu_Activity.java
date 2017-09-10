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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Space;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bobomee.android.navigator.adapter.AdapterBase;
import com.bobomee.android.navigator.adapter.AdapterDropBase;
import com.bobomee.android.navigator.dropdown.DropDownMenu;
import com.bobomee.android.navigator.dropdown.ExpandableContainer;
import com.bobomee.android.navigator.dropdown.TabContainer;
import com.bobomee.android.navigator.dropdown.interfaces.DropDownMenuCheckedListener;
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

    mDropDownMenu.setTabAdapter(new AdapterDropBase<String>(mTitles) {
      @Override public View getView(int position, ViewGroup parent, String object) {
        DropTabView dropdownButton = new DropTabView(getApplicationContext());

        dropdownButton.setText(object);
        dropdownButton.setId(position);

        dropdownButton.addOnCheckedChangeListener((tabView, isChecked) -> {
          DropTabView lTabView = (DropTabView) tabView;
          if (isChecked) {
            ObjectAnimatorUtils.object_animator(lTabView.getTextView());
          }
          ObjectAnimatorUtils.object_rotate(lTabView.getRightImage(), isChecked);
        });

        return dropdownButton;
      }

      @Override public View getDropView(int position, ViewGroup parent, String object) {

        View lView;

        switch (position) {
          case 0:
            lView = RecyclerProvider.provideLinearLayoutRecycler(mDropDownMenu_activity, 300);
            break;
          case 1:
            lView = RecyclerProvider.provideGridLayoutRecycler(mDropDownMenu_activity, 500);
            break;
          case 2:
            lView = RecyclerProvider.provideLinearLayoutRecycler(mDropDownMenu_activity, 600);
            break;
          case 3:
            lView = RecyclerProvider.provideGridLayoutRecycler(mDropDownMenu_activity, 800);
            break;
          default:
            lView = new Space(DropDownMenu_Activity.this);
            break;
        }

        return lView;
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

    mDropDownMenu.addDropDownMenuCheckedListener(new DropDownMenuCheckedListener() {
      @Override public void onCheckedChange(int position, boolean checked) {
        Log.d("BoBoMEe", "onCheckedChange: pos : " + position + ", checked: " + checked);

        if (!checked) {
          ExpandableContainer lExpandableRelativeLayout =
              mDropDownMenu.getExpandableRelativeLayout();
          CheckedDataProvider<RecyclerModel> lCheckedDataProvider =
              RecyclerProvider.provideView(DropDownMenu_Activity.this, lExpandableRelativeLayout,
                  position);
          if (null != lCheckedDataProvider) {

            List<RecyclerModel> lRecyclerModels =
                RecyclerProvider.provideCheckedDatas(lCheckedDataProvider);

            TabContainer lTabContainer = mDropDownMenu.getTabContainer();

            DropTabView lChildAt = (DropTabView) lTabContainer.getChildAt(position);
            lChildAt.setText(lRecyclerModels.size()+"");
          }
        }
      }
    });

    ///
    mDropDownMenu.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));

    mDropDownMenu.getExpandableParent().setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        mDropDownMenu.toggle();
      }
    });
  }

  private void initTabContainer1() {
    mTabContainer1.setTabAdapter(new AdapterBase<String>(mTitles) {
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
