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

package com.bobomee.android.tab_navigator.recycler;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.bobomee.android.common.adapter.CommonRcvAdapter;
import com.bobomee.android.common.adapter.interfaces.AdapterItem;
import com.bobomee.android.common.util.StringUtil;
import com.bobomee.android.tab_navigator.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/1/21.下午7:29.
 *
 * @author bobomee.
 */

public class RecyclerViewItem {

  static class RecyclerAdapterItem implements AdapterItem<String> {
    @BindView(R.id.textView) TextView mTextView;

    @Override public int getLayoutResId() {
      return R.layout.simple_list_row;
    }

    @Override public void bindViews(View root) {

    }

    @Override public void setViews(String pS) {

    }

    @Override public void handleData(String pS, int position) {
      mTextView.setText(pS);
    }
  }

  public static CommonRcvAdapter<String> sStringCommonRcvAdapter() {

    List<String> lStrings = prepareData();

    return new CommonRcvAdapter<String>(lStrings) {
      @NonNull @Override public AdapterItem<String> createItem(int type) {
        return new RecyclerAdapterItem();
      }
    };
  }

  private static List<String> prepareData() {
    List<String> result = new ArrayList<>();

    for (int lI = 0; lI < 50; lI++) {
      String string = StringUtil.getRandomString() + ",.,." + String.valueOf(lI);
      result.add(string);
    }

    return result;
  }
}
