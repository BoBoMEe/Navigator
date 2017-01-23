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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created on 2017/1/21.下午7:41.
 *
 * @author bobomee.
 */

public class NavigateActivity extends AppCompatActivity {

  @BindView(R.id.tab_view) Button mTabView;
  @BindView(R.id.filter_view) Button mFilterView;
  @BindView(R.id.menu_view) Button mMenuView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.navigate_layout_activity);
    ButterKnife.bind(this);
  }

  @OnClick({ R.id.tab_view, R.id.filter_view, R.id.menu_view }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tab_view:
        start(TabView_Activity.class);
        break;
      case R.id.filter_view:
        start(DropDown_Activity.class);
        break;
      case R.id.menu_view:
        start(DropDownMenu_Activity.class);
        break;
    }
  }

  private void start(Class pClass) {
    Intent lIntent = new Intent(this, pClass);
    startActivity(lIntent);
  }
}
