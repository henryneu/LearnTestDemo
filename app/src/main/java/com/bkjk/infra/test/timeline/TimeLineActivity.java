package com.bkjk.infra.test.timeline;

import android.os.Bundle;

import com.bkjk.infra.test.R;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeLineActivity extends AppCompatActivity {

    @BindView(R.id.test_time_line_rv)
    RecyclerView mTimeLineRv;

    private ArrayList<HashMap<String, Object>> mItemLists;
    private TimeLineAdapter mTimeLineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mTimeLineRv.setLayoutManager(linearLayoutManager);
        mTimeLineRv.setHasFixedSize(true);
        // 用自定义分割线类设置分割线
        mTimeLineRv.addItemDecoration(new DividerItemDecoration(this));
        mTimeLineAdapter = new TimeLineAdapter(this, mItemLists);
        mTimeLineRv.setAdapter(mTimeLineAdapter);
    }

    private void initData() {
        mItemLists = new ArrayList<HashMap<String, Object>>();/*在数组中存放数据*/

        HashMap<String, Object> map1 = new HashMap<String, Object>();
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        HashMap<String, Object> map4 = new HashMap<String, Object>();
        HashMap<String, Object> map5 = new HashMap<String, Object>();
        HashMap<String, Object> map6 = new HashMap<String, Object>();

        map1.put("ItemTitle", "美国谷歌公司已发出");
        map1.put("ItemContent", "发件人:谷歌 CEO Sundar Pichai");
        mItemLists.add(map1);

        map2.put("ItemTitle", "国际顺丰已收入");
        map2.put("ItemContent", "等待中转");
        mItemLists.add(map2);

        map3.put("ItemTitle", "国际顺丰转件中");
        map3.put("ItemContent", "下一站中国");
        mItemLists.add(map3);

        map4.put("ItemTitle", "中国顺丰已收入");
        map4.put("ItemContent", "下一站北京市朝阳区");
        mItemLists.add(map4);

        map5.put("ItemTitle", "中国顺丰派件中");
        map5.put("ItemContent", "等待派件");
        mItemLists.add(map5);

        map6.put("ItemTitle", "荟万鸿社区已签收");
        map6.put("ItemContent", "收件人:Henry");
        mItemLists.add(map6);
    }
}
