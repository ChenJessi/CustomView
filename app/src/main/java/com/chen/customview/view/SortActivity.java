package com.chen.customview.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chen.customview.R;
import com.chen.customview.adapter.SortAdapter;
import com.chen.customview.bean.SortBean;
import com.chen.customview.util.PinyinUtils;
import com.chen.customview.widget.LetterComparator;
import com.chen.customview.widget.SideBar;
import com.chen.customview.widget.TitleItemDecoration;
import com.chen.customview.widget.TopLinearSmoothScroller;
import com.chen.customview.widget.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SideBar sideBar;
    EditText editText;

    private SortAdapter adapter;
    private List<SortBean> mList = new ArrayList<>();
    private List<SortBean> allList = new ArrayList<>();

    private LetterComparator mComparator = new LetterComparator();
    private TitleItemDecoration titleItemDecoration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        recyclerView = findViewById(R.id.recyclerView);
        sideBar = findViewById(R.id.sideBar);
        editText = findViewById(R.id.editText);


        mList.add(new SortBean("阿黄"));
        mList.add(new SortBean("啊大"));
        mList.add(new SortBean("测试"));
        mList.add(new SortBean("王二"));
        mList.add(new SortBean("*哈哈哈"));
        mList.add(new SortBean("测试"));
        mList.add(new SortBean("张三,"));
        mList.add(new SortBean("我是谁"));
        mList.add(new SortBean("124"));
        mList.add(new SortBean("张三啊"));
        mList.add(new SortBean("刘禹锡"));
        mList.add(new SortBean("u,李白"));
        mList.add(new SortBean("李白"));
        mList.add(new SortBean("花花"));
        mList.add(new SortBean("》阿狗"));
        mList.add(new SortBean("哦哦哦"));
        mList.add(new SortBean("1"));
        mList.add(new SortBean("chen188669"));
        mList.add(new SortBean("辰辰辰"));
        mList.add(new SortBean("辰188669"));
        mList.add(new SortBean("chen188"));
        mList.add(new SortBean("android"));
        mList.add(new SortBean("java"));
        mList.add(new SortBean("名字"));
        mList.add(new SortBean("祝"));
        mList.add(new SortBean("oppo"));
        mList.add(new SortBean("江江"));
        mList.add(new SortBean("@阿狗"));

        mList = getInitial();
        allList.addAll(mList);
        Collections.sort(mList, mComparator);


        WrapContentLinearLayoutManager manager = new WrapContentLinearLayoutManager(SortActivity.this) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                TopLinearSmoothScroller scroller = new TopLinearSmoothScroller(recyclerView.getContext());
                scroller.setTargetPosition(position);
                startSmoothScroll(scroller);
            }
        };
        adapter = new SortAdapter(SortActivity.this, mList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        titleItemDecoration = new TitleItemDecoration(SortActivity.this, mList);
        recyclerView.addItemDecoration(titleItemDecoration);


        sideBar.setOnStrSelectCallBack(new SideBar.ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                int position = adapter.getPositionForSection(selectStr.charAt(0));
                if (position != -1) {
                    recyclerView.smoothScrollToPosition(position);
                }
            }
        });

        initListener();
    }



    private void initListener() {
        //根据输入框输入值的改变来过滤搜索
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString().trim());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }



    /**
     * 提取首字母
     *
     * @param
     * @return
     */
    private List<SortBean> getInitial() {
        for (int i = 0; i < mList.size(); i++) {
            SortBean sortBean = mList.get(i);
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(sortBean.getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

//             正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortBean.setInitial(sortString.toUpperCase());
            } else {
                sortBean.setInitial("#");
            }
            sortBean.setPinyin(pinyin);
            mList.set(i, sortBean);
        }
        return mList;
    }

    /**
     * 根据输入框中的值来过滤数据并更新RecyclerView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {

        int size = mList.size();
        mList.clear();
        adapter.notifyItemRangeChanged(0, size);
        if (TextUtils.isEmpty(filterStr)) {
            mList.addAll(allList) ;
        } else {
            for (SortBean sortModel : allList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 ||
                        PinyinUtils.getFirstSpell(name).startsWith(filterStr.toString())
                        //不区分大小写
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr.toString())
                        || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())
                ) {
                    mList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(mList, mComparator);
        adapter.notifyDataSetChanged();
    }
}
