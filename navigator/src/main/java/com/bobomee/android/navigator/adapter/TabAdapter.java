package com.bobomee.android.navigator.adapter;

import android.view.View;
import android.view.ViewGroup;
import com.bobomee.android.navigator.adapter.interfaces.AbsAdapter;
import java.util.List;

/**
 * Created on 2016/10/24.下午11:22.
 *
 * @author bobomee.
 */

public abstract class TabAdapter<T> extends AbsAdapter<T> {

  protected TabAdapter(List<T> mDatas) {
    super(mDatas);
  }

  protected TabAdapter(T[] mStrs) {
    super(mStrs);
  }

  @Override public View getDropView(int position, ViewGroup parent, T object) {
    return getView(position, parent, object);
  }
}
