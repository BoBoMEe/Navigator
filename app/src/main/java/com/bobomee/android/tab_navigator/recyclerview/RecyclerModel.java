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

package com.bobomee.android.tab_navigator.recyclerview;

import com.bobomee.android.common.util.StringUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/1/26.下午8:12.
 *
 * @author bobomee.
 */

public class RecyclerModel {

  private CharSequence text;
  private boolean checked;

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean pChecked) {
    checked = pChecked;
  }

  public CharSequence getText() {
    return text;
  }

  public void setText(CharSequence pText) {
    text = pText;
  }

  public static List<RecyclerModel> getModels(int modelCount) {
    List<RecyclerModel> result = new ArrayList<>();

    for (int lI = 0; lI < modelCount; lI++) {
      RecyclerModel lRecyclerModel = new RecyclerModel();

      lRecyclerModel.setText(StringUtil.getRandomString());
      lRecyclerModel.setChecked(false);

      result.add(lRecyclerModel);
    }
    return result;
  }
}
