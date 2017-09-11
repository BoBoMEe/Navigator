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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.bobomee.android.common.util.DisplayUtil;
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

  public static View provideLinearLayoutRecycler(Context pContext) {

    //init view
    View inflate = View.inflate(pContext, R.layout.drop_down_recycler_layout, null);
    RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view);

    ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
    layoutParams.height = 500;
    layoutParams.width = -1;
    recyclerView.setLayoutParams(layoutParams);

    recyclerView.setLayoutManager(new LinearLayoutManager(pContext));

    //init adapter
    List<RecyclerModel> lRecyclerModelList = RecyclerModel.getModels(100);
    RecyclerAdapter lAdapter = RecyclerAdapterProvider.createAdapter(lRecyclerModelList);
    recyclerView.setAdapter(lAdapter);

    //init divider
    recyclerView.addItemDecoration(new Builder(pContext)//divider 颜色
        .colorResId(R.color.tab_line_color).size(2)//高度
        .margin(DisplayUtil.dp2px(12.f))//边距
        .build());

    //init item click
    ItemClickSupport lItemClickSupport = ItemClickSupport.from(recyclerView).add();
    lItemClickSupport.addOnItemClickListener((parent1, view, position1, id) -> {
      Log.d("BoBoMEe", "checked position:" + position1);
      ObjectAnimatorUtils.object_left_right(view);
      lAdapter.checkedData(position1);
    });

    return inflate;
  }

  public static View provideGridLayoutRecycler(Context pContext) {

    //init view
    View inflate = View.inflate(pContext, R.layout.drop_down_recycler_layout, null);
    RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view);

    ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
    layoutParams.height = 300;
    layoutParams.width = -1;
    recyclerView.setLayoutParams(layoutParams);

    recyclerView.setLayoutManager(new GridLayoutManager(pContext, 2));

    //init adapter
    List<RecyclerModel> lRecyclerModelList = RecyclerModel.getModels(50);
    RecyclerAdapter lAdapter = RecyclerAdapterProvider.createAdapter(lRecyclerModelList);
    recyclerView.setAdapter(lAdapter);

    //init divider
    recyclerView.addItemDecoration(new Builder(pContext)//divider 颜色
        .colorResId(R.color.tab_line_color).size(2)//高度
        .margin(DisplayUtil.dp2px(12.f))//边距
        .build());

    //init item click
    ItemClickSupport lItemClickSupport = ItemClickSupport.from(recyclerView).add();
    lItemClickSupport.addOnItemClickListener((parent1, view, position1, id) -> {
      Log.d("BoBoMEe", "checked position:" + position1);
      ObjectAnimatorUtils.object_left_right(view);
      lAdapter.checkedData(position1);
    });

    return inflate;
  }

  public static List<RecyclerModel> provideCheckedDatas(
      @NonNull CheckedDataProvider<RecyclerModel> pCheckedDataProvider) {
    return pCheckedDataProvider.provideCheckedDatas();
  }

  @SuppressWarnings("unchecked")
  public static CheckedDataProvider<RecyclerModel> provideView(Context pContext,
      ViewGroup viewGroup, int position) {

    View lChildAt = viewGroup.getChildAt(position);

    return (CheckDataProvideRecycler<RecyclerModel>) lChildAt.findViewById(R.id.recycler_view);
  }
}
