package com.bobomee.android.navigator.interfaces;

import android.widget.Checkable;

/**
 * Created on 2016/10/24.下午11:52.
 *
 * @author bobomee.
 * @description
 */

public interface ITabView extends Checkable {

  interface OnCheckedChangeListener {
    /**
     * Called when the checked state of a TabView has changed.
     *
     * @param tabView The TabView whose state has changed.
     * @param isChecked The new checked state of TabView.
     */
    void onCheckedChanged(ITabView tabView, boolean isChecked);
  }

  void setOnCheckedChangeWidgetListener(OnCheckedChangeListener listener);

  void addOnCheckedChangeListener(OnCheckedChangeListener listener);

  /**
   * {@link android.view.View#setId(int)}
   */
  int getId();
}
