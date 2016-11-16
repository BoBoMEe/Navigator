package com.bobomee.android.tab_navigator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bobomee.android.navigator.adapter.TabAdapter;
import com.bobomee.android.navigator.view.TabContainer;
import com.bobomee.android.tab_navigator.tabview.DropdownButton;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016/11/16.下午3:46.
 *
 * @author bobomee.
 * @description
 */

public class SecondActivity extends AppCompatActivity {

  @BindView(R.id.tab_container) TabContainer mTabContainer;
  private List<String> mMTitles;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.dropdown_btn_layout);
    ButterKnife.bind(this);

    initTitles();

    initTaBContainer();
  }

  private void initTitles() {
    mMTitles = new ArrayList<>();
    for (int i = 0; i < 4; ++i) {
      mMTitles.add("第" + i + "个");
    }
  }

  private void initTaBContainer() {
    mTabContainer.setTabAdapter(new TabAdapter<String>(mMTitles) {
      @Override public View getView(int position, ViewGroup parent, String object) {
        DropdownButton dropdownButton = new DropdownButton(getApplicationContext());

        dropdownButton.setText(object);
        dropdownButton.setId(position);

        return dropdownButton;
      }
    });
  }
}
