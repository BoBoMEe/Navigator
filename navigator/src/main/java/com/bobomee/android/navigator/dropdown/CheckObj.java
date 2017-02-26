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

package com.bobomee.android.navigator.dropdown;

/**
 * Project ID：400YF17050
 * Resume:    用于保存位置和 check 状态的bean
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/2/26.汪波.
 */
public class CheckObj {
  private int position;
  private boolean checked;

  public CheckObj(boolean pChecked, int pPosition) {
    checked = pChecked;
    position = pPosition;
  }

  public boolean isChecked() {
    return checked;
  }

  public int getPosition() {
    return position;
  }

  public void setChecked(boolean pChecked) {
    checked = pChecked;
  }

  public void setPosition(int pPosition) {
    position = pPosition;
  }

  public void set(int pPosition,boolean pChecked){
    position = pPosition;
    checked = pChecked;
  }

  @Override public boolean equals(Object lpO) {
    if (this == lpO) return true;
    if (lpO == null || getClass() != lpO.getClass()) return false;

    CheckObj llCheckObj = (CheckObj) lpO;

    if (position != llCheckObj.position) return false;
    return checked == llCheckObj.checked;
  }

  @Override public int hashCode() {
    int lresult = position;
    lresult = 31 * lresult + (checked ? 1 : 0);
    return lresult;
  }

  @Override public String toString() {
    return "CheckObj{" +
        "checked=" + checked +
        ", position=" + position +
        '}';
  }
}
  
