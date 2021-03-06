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

package com.bobomee.android.navigator.dropdown.interfaces;

import android.view.ViewGroup;
import com.bobomee.android.navigator.listener.ListenerImpl;
import java.util.List;

/**
 * Project ID：400YF17050
 * Resume:     监听tab是否checked
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/2/26.汪波.
 */
public class DropDownMenuCheckedChange extends ListenerImpl<DropDownMenuCheckedListener> {

  public void onCheckedChange(ViewGroup tabContainer, ViewGroup dropContainer, int tabPos,
      boolean opened) {
    List<DropDownMenuCheckedListener> from = from();

    if (null != from && !from.isEmpty()) {
      for (DropDownMenuCheckedListener dropDownMenuCheckedListener : from) {
        if (null != dropDownMenuCheckedListener) {
          dropDownMenuCheckedListener.onCheckedChange(tabContainer, dropContainer, tabPos, opened);
        }
      }
    }
  }

  public void clearDropDownMenuCheckedListener(){
    List<DropDownMenuCheckedListener> from = from();
    from.clear();
  }
}
