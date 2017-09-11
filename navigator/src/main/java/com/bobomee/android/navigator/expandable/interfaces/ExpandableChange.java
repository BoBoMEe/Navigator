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

import android.support.annotation.IntDef;
import com.bobomee.android.navigator.listener.ListenerImpl;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created on 2017/1/24.下午1:37.
 *
 * @author bobomee.
 */

public class ExpandableChange extends ListenerImpl<ExpandableLayoutListener> {

  /** @hide */
  @IntDef({ START, END, PRE_OPEN, PRE_CLOSE, OPENED, CLOSEED }) @Retention(RetentionPolicy.SOURCE)
  @interface State {
  }

  static final int START = 0;
  static final int END = 1;
  static final int PRE_OPEN = 2;
  static final int PRE_CLOSE = 3;
  static final int OPENED = 4;
  static final int CLOSEED = 5;

  private void listening(@State int state) {
    List<ExpandableLayoutListener> lFrom = from();
    if (null != lFrom && !lFrom.isEmpty()) {
      for (ExpandableLayoutListener lExpandableLayoutListener : lFrom) {
        if (null != lExpandableLayoutListener) {
          switch (state) {
            case START:
              lExpandableLayoutListener.onAnimationStart();
              break;
            case END:
              lExpandableLayoutListener.onAnimationEnd();
              break;
            case PRE_OPEN:
              lExpandableLayoutListener.onPreOpen();
              break;
            case PRE_CLOSE:
              lExpandableLayoutListener.onPreClose();
              break;
            case OPENED:
              lExpandableLayoutListener.onOpened();
              break;
            case CLOSEED:
              lExpandableLayoutListener.onClosed();
              break;
            default:
              break;
          }
        }
      }
    }
  }

  /**
   * Notifies the start of the animation.
   * Sync from android.animation.Animator.AnimatorListener.onAnimationStart(Animator animation)
   */
  public void onAnimationStart() {
    listening(START);
  }

  /**
   * Notifies the end of the animation.
   * Sync from android.animation.Animator.AnimatorListener.onAnimationEnd(Animator animation)
   */
  public void onAnimationEnd() {
    listening(END);
  }

  /**
   * Notifies the layout is going to open.
   */
  public void onPreOpen() {
    listening(PRE_OPEN);
  }

  /**
   * Notifies the layout is going to equal close size.
   */
  public void onPreClose() {
    listening(PRE_CLOSE);
  }

  /**
   * Notifies the layout opened.
   */
  public void onOpened() {
    listening(OPENED);
  }

  /**
   * Notifies the layout size equal closed size.
   */
  public void onClosed() {
    listening(CLOSEED);
  }

  public void clearExpandableLayoutListener() {
    List<ExpandableLayoutListener> from = from();
    from.clear();
  }
}
