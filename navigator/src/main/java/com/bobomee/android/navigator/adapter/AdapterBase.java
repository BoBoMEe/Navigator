package com.bobomee.android.navigator.adapter;

import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created on 2016/10/24.下午11:22.
 *
 * @author bobomee.
 * @description
 */

public abstract class AdapterBase<T> extends AdapterDropBase<T> {

  protected AdapterBase(List<T> mDatas) {
    super(mDatas);
  }

  public AdapterBase(T[] mStrs) {
    super(mStrs);
  }

  @Override public View getDropView(int position, ViewGroup parent, T object) {
    return getView(position, parent, object);
  }
}
