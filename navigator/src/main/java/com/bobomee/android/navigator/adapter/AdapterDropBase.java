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

package com.bobomee.android.navigator.adapter;

import com.bobomee.android.navigator.adapter.interfaces.TAdapter;
import java.util.List;

/**
 * Created on 2016/10/24.下午11:22.
 *
 * @author bobomee.
 */

public abstract class AdapterDropBase<T> extends TAdapter<T> {

  protected AdapterDropBase(List<T> mDatas) {
    super(mDatas);
  }

  protected AdapterDropBase(T[] mStrs) {
    super(mStrs);
  }

  public void addItem(T _t) {
    this.mDatas.add(_t);
    notifyDataSetChanged();
  }

  public void remove(T _t) {
    if (this.mDatas.indexOf(_t) < 0) return;
    this.mDatas.remove(_t);
    notifyDataSetChanged();
  }

  public void remove(int index) {
    if (index < 0 || index > getCount() - 1) return;
    this.mDatas.remove(index);
    notifyDataSetChanged();
  }
}
