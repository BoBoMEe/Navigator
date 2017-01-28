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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bobomee.android.common.adapter.CommonRcvAdapter;
import com.bobomee.android.common.adapter.interfaces.AdapterItem;
import com.bobomee.android.tab_navigator.R;
import java.util.List;

/**
 * Created on 2017/1/26.下午8:15.
 *
 * @author bobomee.
 */

public class RecyclerAdapterProvider {

  static class RecyclerAdapterItem implements AdapterItem<RecyclerModel> {

    @BindView(R.id.textView) TextView mTextView;
    @BindView(R.id.image) ImageView mImage;
    private View root;

    @Override public int getLayoutResId() {
      return R.layout.layout_tab_txt_checkbox;
    }

    @Override public void bindViews(View root) {
      this.root = root;
      ButterKnife.bind(this, root);
    }

    @Override public void setViews(RecyclerModel pRecyclerModel) {

    }

    @Override public void handleData(RecyclerModel pRecyclerModel, int position) {
      mTextView.setText(pRecyclerModel.getText());
      mImage.setVisibility(pRecyclerModel.isChecked()?View.VISIBLE:View.GONE);
      mTextView.setSelected(pRecyclerModel.isChecked());
      root.setSelected(pRecyclerModel.isChecked());
    }
  }

  public static class RecyclerAdapter extends CommonRcvAdapter<RecyclerModel> {

    public RecyclerAdapter(@Nullable List<RecyclerModel> data) {
      super(data);
    }

    @NonNull @Override public AdapterItem<RecyclerModel> createItem(int type) {
      return new RecyclerAdapterItem();
    }
  }

  public static RecyclerAdapter createAdapter(List<RecyclerModel> pModelList) {
    return new RecyclerAdapter(pModelList);
  }
}
