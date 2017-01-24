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

package com.bobomee.android.navigator.adapter.interfaces;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created on 2016/10/24.下午11:19.
 *
 * @author bobomee.
 * @description
 */

public abstract class TAdapter<T> implements IAdapter<T> {
  private final DataSetObservable mDataSetObservable = new DataSetObservable();

  public void registerDataSetObserver(DataSetObserver observer) {
    mDataSetObservable.registerObserver(observer);
  }

  public void unregisterDataSetObserver(DataSetObserver observer) {
    mDataSetObservable.unregisterObserver(observer);
  }

  /**
   * Notifies the attached observers that the underlying data has been changed
   * and any View reflecting the data set should refresh itself.
   */
  public void notifyDataSetChanged() {
    mDataSetObservable.notifyChanged();
  }

  /**
   * Notifies the attached observers that the underlying data is no longer valid
   * or available. Once invoked this adapter is no longer valid and should
   * not report further data set changes.
   */
  public void notifyDataSetInvalidated() {
    mDataSetObservable.notifyInvalidated();
  }

  protected final List<T> mDatas;

  public TAdapter(List<T> mDatas) {
    if (null == mDatas) {
      mDatas = new ArrayList<>();
    }
    this.mDatas = mDatas;
  }

  public TAdapter(T[] mStrs) {
    List<T> mDatas = new ArrayList<>();
    if (null != mStrs) {
      mDatas.addAll(Arrays.asList(mStrs));
    }
    this.mDatas = mDatas;
  }

  @Override public int getCount() {
    return mDatas.size();
  }

  @Override public T getItem(int position) {
    return mDatas.get(position);
  }
}