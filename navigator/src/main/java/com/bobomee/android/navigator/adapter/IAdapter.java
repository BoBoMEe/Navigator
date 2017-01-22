package com.bobomee.android.navigator.adapter;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created on 2016/10/24.下午9:04.
 *
 * @author bobomee.
 * @description
 */

public interface IAdapter<T> {

  void registerDataSetObserver(DataSetObserver observer);

  void unregisterDataSetObserver(DataSetObserver observer);

  int getCount();

  void notifyDataSetChanged();

  void notifyDataSetInvalidated();

  T getItem(int position);

  View getView(int position, ViewGroup parent, T object);

  View getDropView(int position, ViewGroup parent, T object);
}
