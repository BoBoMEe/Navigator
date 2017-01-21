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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bobomee.android.navigator.adapter.TabAdapter;
import com.bobomee.android.navigator.expandable.ExpandableRelativeLayout;
import com.bobomee.android.navigator.interfaces.ITabGroup;
import com.bobomee.android.navigator.interfaces.ITabGroup.OnCheckedChangeListener;
import com.bobomee.android.navigator.view.TabContainer;
import com.bobomee.android.tab_navigator.recycler.DividerItemDecoration;
import com.bobomee.android.tab_navigator.recycler.RecyclerViewItem;
import com.bobomee.android.tab_navigator.tabview.DropTabView;
import com.bobomee.android.tab_navigator.tabview.ItemTabView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/1/21.下午7:46.
 *
 * @author bobomee.
 */

public class Drop_Filter_Activity extends AppCompatActivity {

  @BindView(R.id.drop_tab_container) TabContainer mDropTabContainer;
  @BindView(R.id.tab_container1) TabContainer mTabContainer1;
  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
  @BindView(R.id.expandable_layout) ExpandableRelativeLayout mExpandableLayout;
  private List<String> mTitles;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.drop_down_recycler_expand);
    ButterKnife.bind(this);

    initData();

    initTabContainer();
    initTabContainer1();

    initRecyclerView();
  }

  private void initRecyclerView() {
    mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.setAdapter(RecyclerViewItem.sStringCommonRcvAdapter());
  }

  private void initTabContainer() {
    mDropTabContainer.setTabAdapter(new TabAdapter<String>(mTitles) {
      @Override public View getView(int position, ViewGroup parent, String object) {
        DropTabView dropdownButton = new DropTabView(getApplicationContext());

        dropdownButton.setText(object);
        dropdownButton.setId(position);

        return dropdownButton;
      }
    });

    mDropTabContainer.addOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override public void onCheckedChanged(ITabGroup group, int checkedId) {
        if (mExpandableLayout.isExpanded()) {
          mExpandableLayout.collapse();
        } else {
          mExpandableLayout.expand();
        }
      }
    });
    
    mExpandableLayout.collapse();
  }

  private void initTabContainer1() {
    mTabContainer1.setTabAdapter(new TabAdapter<String>(mTitles) {
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
