# Android Tab_Navigator

Android首页底部常用tab切换控件,借鉴了`Adapter`和`AdapterView`的写法,可动态增减tab

## Features
- TabViews in TabContainer only one can be checked

> `TabGroup` 参考 `RadioGroup` api基本一致 <br>
> `TabGroup` 继承自 `LinearLayoutCompat`,可配置 `divider` <br>
> `TabView` 参考 `CompoundButton` 实现 `checkable` <br>
> `TabContainer` 添加方式采用了`Adapter`方式,可动态配置`TabView`和个数

## Quick Overview

![image](gif/demo.gif)

## Usage
- xml

```
<com.bobomee.android.navigator.view.TabContainer
        android:id="@+id/tab_container"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@android:color/white"
        android:elevation="10dp"
        />
        
```

- code

```

       List<String> mTitles = new ArrayList<>();
           for (int i = 0; i < 4; ++i) {
             mTitles.add("第" + i + "个");
           }
       
           mTabContainer.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
             //...
           });
       
           mTabContainer.setTabAdapter(mTabAdapter = new TabAdapter<String>(mTitles) {
       
             @Override public View getView(int position, ViewGroup parent, String object) {
       
               TabView commonTabView = new TabView(parent.getContext());
       
               View inflate = View.inflate(MainActivity.this, R.layout.layout_tab_text, null);
               TextView textView = (TextView) inflate.findViewById(R.id.tv_tab_text);
               textView.setText(object);
       
               commonTabView.setContentView(inflate);
               commonTabView.setId(position);
               if (position == 0) commonTabView.setChecked(true);
       
               commonTabView.addOnCheckedChangeListener(new ITabView.OnCheckedChangeListener() {
                 @Override public void onCheckedChanged(ITabView tabView, boolean isChecked) {
                   Log.d(TAG, "onCheckedChanged");
                 }
               });
       
               return commonTabView;
             }
           });
       
           mTabContainer.addOnCheckedChangeListener(new ITabGroup.OnCheckedChangeListener() {
             @Override public void onCheckedChanged(TabGroup group, int checkedId) {
              //...
             }
           });
       
```

## Thanks

[isanwenyu/TabView](https://github.com/isanwenyu/TabView)

 
## License

    Copyright 2016 BoBoMEe(wbwjx115@gmail.com)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
