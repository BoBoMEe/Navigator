package com.bobomee.android.navigator.adapter;

import java.util.List;

/**
 * Created on 2016/10/24.下午11:22.
 *
 * @author bobomee.
 * @description
 */

public abstract class AdapterBase<T> extends TAdapter<T> {

  protected AdapterBase(List<T> mDatas) {
    super(mDatas);
  }

  public AdapterBase(T[] mStrs) {
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
