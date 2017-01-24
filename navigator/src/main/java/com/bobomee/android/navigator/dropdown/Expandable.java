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

import android.animation.TimeInterpolator;
import android.support.annotation.NonNull;

/**
 * Created on 2017/1/22.下午8:05.
 *
 * @author bobomee.
 */

public interface Expandable {

  /**
   * Starts animation the state of the view to the inverse of its current state.
   */
  void toggle();

  /**
   * Starts expand animation.
   */
  void expand();

  /**
   * Starts collapse animation.
   */
  void collapse();

  /**
   * Gets state of expanse.
   *
   * @return true if the layout is visible
   */
  boolean isExpanded();

  /**
   * Sets state of expanse.
   *
   * @param expanded The layout is visible if expanded is true
   */
  void setExpanded(final boolean expanded);


  /**
   * Sets the length of the animation.
   * The default duration is 300 milliseconds.
   *
   * @param duration
   */
  void setDuration(final int duration);

  /**
   * The time interpolator used in calculating the elapsed fraction of this animation. The
   * interpolator determines whether the animation runs with linear or non-linear motion,
   * such as acceleration and deceleration.
   * The default value is  {@link android.view.animation.AccelerateDecelerateInterpolator}
   *
   * @param interpolator
   */
  void setInterpolator(@NonNull final TimeInterpolator interpolator);
}
