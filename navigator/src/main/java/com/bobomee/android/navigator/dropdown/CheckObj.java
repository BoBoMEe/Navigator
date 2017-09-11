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

  public CheckObj(boolean checked, int position) {
    this.checked = checked;
    this.position = position;
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

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CheckObj checkObj = (CheckObj) o;

    if (position != checkObj.position) return false;
    return checked == checkObj.checked;
  }

  @Override public int hashCode() {
    int result = position;
    result = 31 * result + (checked ? 1 : 0);
    return result;
  }

  @Override public String toString() {
    return "CheckObj{" +
        "checked=" + checked +
        ", position=" + position +
        '}';
  }
}
  
