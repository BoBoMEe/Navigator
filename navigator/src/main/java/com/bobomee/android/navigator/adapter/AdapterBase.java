package com.bobomee.android.navigator.adapter;

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

abstract class AdapterBase<T> implements IAdapter<T> {
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

  final List<T> mDatas;

  AdapterBase(List<T> mDatas) {
    if (null == mDatas) {
      mDatas = new ArrayList<>();
    }
    this.mDatas = mDatas;
  }

  AdapterBase(T[] mStrs) {
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