package com.bobomee.android.navigator.interfaces;

import android.view.ViewGroup;

/**
 * Created on 2016/10/24.下午11:55.
 *
 * @author bobomee.
 * @description
 */

public interface ITabGroup {

  /**
   * <p>Interface definition for a callback to be invoked when the checked
   * tab view changed in this group.</p>
   */
  interface OnCheckedChangeListener {
    /**
     * <p>Called when the checked  tab view has changed. When the
     * selection is cleared, checkedId is -1.</p>
     *
     * @param group the group in which the checked tab view has changed
     * @param checkedId the unique identifier of the newly checked tab view
     */
    void onCheckedChanged(ITabGroup group, int checkedId);
  }

  void addOnCheckedChangeListener(OnCheckedChangeListener listener);

  void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener listener);
}
