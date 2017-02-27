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

package com.bobomee.android.tab_navigator.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.bobomee.android.common.util.DisplayUtil;
import com.bobomee.android.navigator.dropdown.ExpandableContainer;
import com.bobomee.android.recyclerviewhelper.selectclick.click.ItemClickSupport;
import com.bobomee.android.tab_navigator.R;
import com.bobomee.android.tab_navigator.animator.ObjectAnimatorUtils;
import com.bobomee.android.tab_navigator.recyclerview.HorizontalDividerItemDecoration.Builder;
import com.bobomee.android.tab_navigator.recyclerview.RecyclerAdapterProvider.RecyclerAdapter;
import java.util.List;

/**
 * Project ID：400YF17050
 * Resume:     提供Recyclerview的provider<br/>
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/2/21.汪波.
 */
public class RecyclerProvider {

  public static RecyclerView provideLinearLayoutRecycler(Context pContext, int bottomMargin) {
    
    //init view
    RecyclerView inflate =
        (RecyclerView) View.inflate(pContext, R.layout.drop_down_recycler_layout, null);
    inflate.setLayoutManager(new LinearLayoutManager(pContext));

    //init layoutparams
    LayoutParams lLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    lLayoutParams.bottomMargin = bottomMargin;
    inflate.setLayoutParams(lLayoutParams);

    //init adapter
    List<RecyclerModel> lRecyclerModelList = RecyclerModel.getModels(15);
    RecyclerAdapter lAdapter = RecyclerAdapterProvider.createAdapter(lRecyclerModelList);
    inflate.setAdapter(lAdapter);
    
    //init divider
    inflate.addItemDecoration(new Builder(pContext)//divider 颜色
        .colorResId(R.color.tab_line_color).size(2)//高度
        .margin(DisplayUtil.dp2px(12.f))//边距
        .build());

    //init item click
    ItemClickSupport lItemClickSupport = ItemClickSupport.from(inflate).add();
    lItemClickSupport.addOnItemClickListener((parent1, view, position1, id) -> {
      ObjectAnimatorUtils.object_left_right(view);
      lAdapter.checkedData(position1);
    });

    return inflate;
  }


  public static RecyclerView provideGridLayoutRecycler(Context pContext, int bottomMargin) {

    //init view
    RecyclerView inflate =
        (RecyclerView) View.inflate(pContext, R.layout.drop_down_recycler_layout, null);
    inflate.setLayoutManager(new GridLayoutManager(pContext,2));

    //init layoutparams
    LayoutParams lLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    lLayoutParams.bottomMargin = bottomMargin;
    inflate.setLayoutParams(lLayoutParams);

    //init adapter
    List<RecyclerModel> lRecyclerModelList = RecyclerModel.getModels(15);
    RecyclerAdapter lAdapter = RecyclerAdapterProvider.createAdapter(lRecyclerModelList);
    inflate.setAdapter(lAdapter);

    //init divider
    inflate.addItemDecoration(new Builder(pContext)//divider 颜色
        .colorResId(R.color.tab_line_color).size(2)//高度
        .margin(DisplayUtil.dp2px(12.f))//边距
        .build());

    //init item click
    ItemClickSupport lItemClickSupport = ItemClickSupport.from(inflate).add();
    lItemClickSupport.addOnItemClickListener((parent1, view, position1, id) -> {
      ObjectAnimatorUtils.object_left_right(view);
      lAdapter.checkedData(position1);
    });

    return inflate;
  }

  public static List<RecyclerModel> provideCheckedDatas(
      @NonNull CheckedDataProvider<RecyclerModel> pCheckedDataProvider) {
    return pCheckedDataProvider.provideCheckedDatas();
  }

  @SuppressWarnings("unchecked") public static CheckedDataProvider<RecyclerModel> provideView(Context pContext,
      ExpandableContainer pExpandableContainer, int position) {

    CheckDataProvideRecycler<RecyclerModel> lRecyclerView =
        new CheckDataProvideRecycler<RecyclerModel>(pContext);
    View lChildAt = pExpandableContainer.getChildAt(position);

    if (lChildAt instanceof CheckDataProvideRecycler) {
      lRecyclerView = (CheckDataProvideRecycler<RecyclerModel>) lChildAt;
    }

    return lRecyclerView;
  }
}
