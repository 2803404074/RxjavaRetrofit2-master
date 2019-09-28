package com.dabangvr.live.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dabangvr.live.R;
import com.dabangvr.live.base.BaseActivity;
import com.dabangvr.live.base.BaseRecyclerHolder;
import com.dabangvr.live.base.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyOrderActivity extends BaseActivity {

    @BindView(R.id.recycle_order)
    RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<String>mData = new ArrayList<>();
    @Override
    public int setLayout() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initView() {
        for (int i = 0; i < 5; i++) {
            mData.add("数据");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerAdapter<String>(getContext(),mData,R.layout.item_order) {
            @Override
            public void convert(Context mContext, BaseRecyclerHolder holder, String o) {

            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.ivBack})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.ivBack:
                finish();
                break;
        }
    }
}
