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
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Project ID：400YF17050
 * Resume:     <br/>
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/2/26.汪波.
 */
public class CheckDataProvideRecycler<T> extends RecyclerView implements CheckedDataProvider<T> {
  public CheckDataProvideRecycler(Context context) {
    super(context);
  }

  public CheckDataProvideRecycler(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public CheckDataProvideRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @SuppressWarnings("unchecked") @Override public List<T> provideCheckedDatas() {

    List<T> result = new ArrayList<T>();

    Adapter lAdapter = getAdapter();

    if (null != lAdapter && lAdapter instanceof CheckedDataProvider) {
      result = ((CheckedDataProvider<T>) lAdapter).provideCheckedDatas();
    }

    return result;
  }
}
