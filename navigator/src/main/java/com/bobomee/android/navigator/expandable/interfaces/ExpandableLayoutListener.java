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

public interface ExpandableLayoutListener {
  /**
   * Notifies the start of the animation.
   * Sync from android.animation.Animator.AnimatorListener.onAnimationStart(Animator animation)
   */
  void onAnimationStart();

  /**
   * Notifies the end of the animation.
   * Sync from android.animation.Animator.AnimatorListener.onAnimationEnd(Animator animation)
   */
  void onAnimationEnd();

  /**
   * Notifies the layout is going to open.
   */
  void onPreOpen();

  /**
   * Notifies the layout is going to equal close size.
   */
  void onPreClose();

  /**
   * Notifies the layout opened.
   */
  void onOpened();

  /**
   * Notifies the layout size equal closed size.
   */
  void onClosed();
}