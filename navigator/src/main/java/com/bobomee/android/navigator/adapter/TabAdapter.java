package com.bobomee.android.navigator.adapter;

import java.util.List;

/**
 * Created on 2016/10/24.下午11:22.
 *
 * @author bobomee.
 * @description
 */

public abstract class TabAdapter<T extends Object> extends AdapterBase<T> {

  protected TabAdapter(List<T> mDatas) {
    super(mDatas);
  }

  public TabAdapter(T[] mStrs) {
    super(mStrs);
  }

  @SuppressWarnings("unchecked") public void addItem(T _t) {
    this.mDatas.add(_t);
    notifyDataSetChanged();
  }

  public void remove(T _t) {
    this.mDatas.remove(_t);
    notifyDataSetChanged();
  }

  public void remove(int index) {
    if (index < 0 || index > getCount() - 1) return;
    this.mDatas.remove(index);
    notifyDataSetChanged();
  }
}
