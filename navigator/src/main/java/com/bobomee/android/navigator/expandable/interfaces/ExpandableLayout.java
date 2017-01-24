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

package com.bobomee.android.navigator.expandable.interfaces;

import android.animation.TimeInterpolator;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bobomee.android.navigator.dropdown.interfaces.Expandable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface ExpandableLayout extends Expandable {

  /**
   * Duration of expand animation
   */
  int DEFAULT_DURATION = 300;
  /**
   * Visibility of the layout when the layout attaches
   */
  boolean DEFAULT_EXPANDED = false;
  /**
   * Orientation of child views
   */
  int HORIZONTAL = 0;
  /**
   * Orientation of child views
   */
  int VERTICAL = 1;

  /**
   * Orientation of layout
   */
  @Retention(RetentionPolicy.SOURCE) @IntDef({ HORIZONTAL, VERTICAL }) @interface Orientation {
  }

  /**
   * Starts animation the state of the view to the inverse of its current state.
   *
   * @param interpolator use the default interpolator if the argument is null.
   */
  void toggle(final long duration, @Nullable final TimeInterpolator interpolator);

  /**
   * Starts expand animation.
   *
   * @param interpolator use the default interpolator if the argument is null.
   */
  void expand(final long duration, @Nullable final TimeInterpolator interpolator);

  /**
   * Starts collapse animation.
   *
   * @param interpolator use the default interpolator if the argument is null.
   */
  void collapse(final long duration, @Nullable final TimeInterpolator interpolator);

  /**
   * Sets the expandable layout listener.
   *
   * @param listener ExpandableLayoutListener
   */
  void addExpandableLayoutListener(@NonNull final ExpandableLayoutListener listener);

  /**
   * Sets the expandable layout listener.
   *
   * @param listener ExpandableLayoutListener
   */
  boolean removeExpandableLayoutListener(@NonNull final ExpandableLayoutListener listener);
}