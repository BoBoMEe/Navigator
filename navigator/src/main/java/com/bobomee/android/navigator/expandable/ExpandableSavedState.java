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

package com.bobomee.android.navigator.expandable;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class ExpandableSavedState extends View.BaseSavedState {
  private int size;
  private float weight;

  ExpandableSavedState(Parcelable superState) {
    super(superState);
  }

  private ExpandableSavedState(Parcel in) {
    super(in);
    this.size = in.readInt();
    this.weight = in.readFloat();
  }

  public int getSize() {
    return this.size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public float getWeight() {
    return this.weight;
  }

  public void setWeight(float weight) {
    this.weight = weight;
  }

  @Override public void writeToParcel(Parcel out, int flags) {
    super.writeToParcel(out, flags);
    out.writeInt(this.size);
    out.writeFloat(this.weight);
  }

  public static final Creator<ExpandableSavedState> CREATOR = new Creator<ExpandableSavedState>() {
    public ExpandableSavedState createFromParcel(Parcel in) {
      return new ExpandableSavedState(in);
    }

    public ExpandableSavedState[] newArray(int size) {
      return new ExpandableSavedState[size];
    }
  };
}
