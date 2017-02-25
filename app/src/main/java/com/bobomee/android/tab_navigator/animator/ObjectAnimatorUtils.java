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

package com.bobomee.android.tab_navigator.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import com.bobomee.android.navigator.expandable.Utils;

/**
 * Created on 2017/1/26.下午9:04.
 *
 * @author bobomee.
 * 
 * happy new year ,hahaha
 * 
 * 临睡前 撸一发
 */

public class ObjectAnimatorUtils {

  public static void object_animator(View vtabView) {
    ObjectAnimator scaleX = ObjectAnimator.ofFloat(vtabView, View.SCALE_X, 1f, .5f, 1f);
    ObjectAnimator scaleY = ObjectAnimator.ofFloat(vtabView, View.SCALE_Y, 1f, .5f, 1f);
    ObjectAnimator alpha = ObjectAnimator.ofFloat(vtabView, View.ALPHA, 1f, .5f, 1f);

    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playTogether(scaleX, scaleY, alpha);
    animatorSet.setInterpolator(Utils.createInterpolator(Utils.BOUNCE_INTERPOLATOR));
    animatorSet.start();
  }

  public static void object_rotate(View vtabView, boolean checked) {
    ObjectAnimator alpha = ObjectAnimator.ofFloat(vtabView, View.ALPHA, 1f, .5f, 1f);
    ObjectAnimator rotate =
        ObjectAnimator.ofFloat(vtabView, View.ROTATION, checked ? 0f : 180f, checked ? 180f : 0f);

    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.playTogether(alpha, rotate);
    animatorSet.start();
  }

  public static void object_left_right(View pView) {
    ObjectAnimator lObjectAnimator =
        ObjectAnimator.ofFloat(pView, View.X, 0, 300f, 0, 150f, 0, 75f, 0);
    lObjectAnimator.start();
  }
}
