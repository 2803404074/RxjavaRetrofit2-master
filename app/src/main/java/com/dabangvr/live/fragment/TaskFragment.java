package com.dabangvr.live.fragment;


import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dabangvr.live.R;
import com.dabangvr.live.base.BaseFragment;
import com.dabangvr.live.base.BaseRecyclerHolder;
import com.dabangvr.live.base.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TaskFragment extends BaseFragment {

    @BindView(R.id.rl_toolbar)
    RelativeLayout toolBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.recycle_task)
    RecyclerView recyclerView;
    private RecyclerAdapter  recyclerAdapter;
    private List<String> mData = new ArrayList<>();

    @Override
    public int layoutId() {
        return R.layout.task_fragment;
    }

    @Override
    public void initView() {
        toolBar.setBackgroundResource(R.color.green02);
        tvTitle.setText("任务中心");
        tvTitle.setTextColor(getContext().getResources().getColor(R.color.colorW));

        for (int i = 0; i < 10; i++) {
            mData.add("数据");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerAdapter = new RecyclerAdapter<String>(getContext(),mData,R.layout.item_task) {
            @Override
            public void convert(Context mContext, BaseRecyclerHolder holder, String o) {

            }
        };
        recyclerView.setAdapter(recyclerAdapter);
    }
    @Override
    public void initData() {

    }
}
