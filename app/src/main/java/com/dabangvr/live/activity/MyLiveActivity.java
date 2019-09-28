package com.dabangvr.live.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dabangvr.live.R;
import com.dabangvr.live.base.BaseActivity;
import com.dabangvr.live.base.BaseRecyclerHolder;
import com.dabangvr.live.base.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyLiveActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recycle_live)
    RecyclerView recyclerLive;
    private RecyclerAdapter adapter;
    private List<String>mData = new ArrayList<>();

    @Override
    public int setLayout() {
        return R.layout.activity_my_live;
    }

    @Override
    public void initView() {
        boolean isShort = getIntent().getBooleanExtra("isShort",false);
        if (isShort){
            tvTitle.setText("短视频");
        }

        for (int i = 0; i < 20; i++) {
            mData.add("数据"+i);
        }
        recyclerLive.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new RecyclerAdapter<String>(getContext(),mData,R.layout.item_my_live) {
            @Override
            public void convert(Context mContext, BaseRecyclerHolder holder, String o) {
                if (isShort){
                    holder.setImageResource(R.id.ivContent,R.mipmap.test2);
                }
            }
        };
        recyclerLive.setAdapter(adapter);

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.ivBack})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }

}
