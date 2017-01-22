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

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.bobomee.android.navigator.R;
import com.bobomee.android.navigator.adapter.AdapterBase;
import com.bobomee.android.navigator.expandable.ExpandableRelativeLayout;
import com.bobomee.android.navigator.view.ITabGroup;
import com.bobomee.android.navigator.view.ITabView;
import com.bobomee.android.navigator.view.OnTabGroupCheckedChangeListener;
import com.bobomee.android.navigator.view.TabContainer;

/**
 * Created on 2017/1/22.下午2:14.
 *
 * @author bobomee.
 */

public class DropDownMenu extends LinearLayoutCompat {
  
  private TabContainer mTabContainer;
  private ExpandableRelativeLayout mExpandableRelativeLayout;
  
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
    LayoutInflater inflater = LayoutInflater.from(getContext());
    inflater.inflate(R.layout.drop_down_menu_layout, this, true);
    
    mTabContainer = (TabContainer) findViewById(R.id.drop_tab_container);
    mExpandableRelativeLayout = (ExpandableRelativeLayout) findViewById(R.id.expandable_layout);
    

    
  }

  public <T> void setTabAdapter(AdapterBase<T> _tAdapter){
    if (null != mTabContainer){
      mTabContainer.setTabAdapter(_tAdapter);
    }
  }
}
