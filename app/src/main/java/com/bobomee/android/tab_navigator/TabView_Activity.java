package com.bobomee.android.tab_navigator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.navigator.adapter.TabAdapter;
import com.bobomee.android.navigator.dropdown.TabContainer;
import com.bobomee.android.navigator.tab.TabView;
import com.bobomee.android.navigator.tab.interfaces.ITabView;
import com.bobomee.android.tab_navigator.animator.ObjectAnimatorUtils;
import com.bobomee.android.tab_navigator.vp.ContentFragmentAdapter;
import com.bobomee.android.tab_navigator.vp.MainTabFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * 主界面<br>
 * Copyright (c) 2016 isanwenyu@163.com. All rights reserved.
 * <p>
 * modify by BoBoMEe(wbwjx115@gmail.com)
 */
public class TabView_Activity extends AppCompatActivity {
  public static final int TAB_CHAT = 0x00;
  public static final int TAB_APP = 0x01;
  public static final int TAB_PIC = 0x02;
  public static final int TAB_USER = 0x03;
  private static final String TAG = TabView_Activity.class.getSimpleName();
  @BindView(R.id.vp_main) ViewPager mViewPager;
  @BindView(R.id.tg_tab) TabContainer mTabGroup;
  @BindView(R.id.tab_container) TabContainer mTabContainer;

  private TabAdapter<String> mTabAdapter;
  private List<String> mTitles;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tab_view_sample);
    //绑定当前界面
    ButterKnife.bind(this);

    initData();

    initView();
  }

  private void initData() {
    mTitles = new ArrayList<>();
    for (int i = 0; i < 4; ++i) {
      mTitles.add("第" + i + "个");
    }
  }

  /**
   * 初始化布局
   */
  private void initView() {
    initViewPager();
    setTabContainerListener(mTabGroup);
    initTabContainer();
  }

  private void initTabContainer() {

    mTabContainer.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
      @Override public void onChildViewAdded(View parent, View child) {
        Log.d(TAG, "onChildViewAdded ");
      }

      @Override public void onChildViewRemoved(View parent, View child) {
        Log.d(TAG, "onChildViewRemoved");
      }
    });

    mTabContainer.setTabAdapter(mTabAdapter = new TabAdapter<String>(mTitles) {

      @Override public View getView(int position, ViewGroup parent, String object) {

        TabView commonTabView = new TabView(parent.getContext());

        View inflate = View.inflate(TabView_Activity.this, R.layout.layout_tab_text, null);
        TextView textView = (TextView) inflate.findViewById(R.id.tv_tab_text);
        textView.setText(object);

        commonTabView.setContentView(inflate);
        commonTabView.setId(position);
        commonTabView.setShouldKeep(true);

        commonTabView.addOnCheckedChangeListener((tabView, isChecked) -> {
          if (isChecked) {
            View vtabView = (View) tabView;
            ObjectAnimatorUtils.object_animator(vtabView);
          }
        });

        return commonTabView;
      }
    });

    setTabContainerListener(mTabContainer);

    mTabContainer.setCheckedStateForView(0, true);
  }

  private void setTabContainerListener(TabContainer mTabGroup) {
    mTabGroup.addOnCheckedChangeListener((group, checkedId) -> {
      switch (checkedId) {
        case R.id.tab_chat:
          setCurrentFragment(TAB_CHAT);
          break;
        case R.id.tb_pic:
          setCurrentFragment(TAB_PIC);
          break;
        case R.id.tb_app:
          setCurrentFragment(TAB_APP);
          break;
        case R.id.tb_user:
          setCurrentFragment(TAB_USER);
          break;
      }
    });
  }

  @OnClick(R.id.add) public void setAddEvent() {

    int count = mTabAdapter.getCount();
    count++;

    mTabAdapter.addItem("第" + count + "个");
  }

  @OnClick(R.id.remove) public void remove() {
    mTabAdapter.remove(mTabAdapter.getCount() - 1);
  }

  /**
   * 初始化viewpager
   */
  private void initViewPager() {

    //缓存3页避免切换时出现空指针
    mViewPager.setOffscreenPageLimit(3);
    mViewPager.setAdapter(new ContentFragmentAdapter.Holder(getSupportFragmentManager()).add(
        MainTabFragment.newInstance(android.R.color.holo_blue_dark))
        .add(MainTabFragment.newInstance(android.R.color.holo_red_dark))
        .add(MainTabFragment.newInstance(android.R.color.holo_green_dark))
        .add(MainTabFragment.newInstance(android.R.color.holo_orange_dark))
        .set());
    mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

      @Override public void onPageSelected(int position) {
        ITabView childAt = (ITabView) mTabGroup.getChildAt(position);
        if (null != childAt) childAt.setChecked(true);

        ITabView childAt1 = (ITabView) mTabContainer.getChildAt(position);
        if (null != childAt1) childAt1.setChecked(true);
      }
    });
  }

  /**
   * 改变fragment状态
   */
  private void setCurrentFragment(final int position) {
    Log.i(TAG, "position:" + position);
    //不使用切换动画 避免与自定义动画冲突
    mViewPager.setCurrentItem(position, false);
  }
}
