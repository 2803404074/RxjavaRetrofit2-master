package com.dabangvr.live.fragment;


import android.content.Context;
import android.net.ParseException;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dabangvr.live.R;
import com.dabangvr.live.base.BaseFragment;
import com.dabangvr.live.base.BaseRecyclerHolder;
import com.dabangvr.live.base.RecyclerAdapter;
import com.dabangvr.live.utils.DataUtil;
import com.dbvr.baselibrary.model.WeekDay;
import com.dbvr.baselibrary.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class TaskFragment extends BaseFragment {

    @BindView(R.id.rl_toolbar)
    RelativeLayout toolBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    //日期列表
    @BindView(R.id.recycle_date)
    RecyclerView recyclerDate;

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
        recyclerDate.setLayoutManager(new GridLayoutManager(getContext(),7));
        List<WeekDay> list = DataUtil.getWeekDay();
        RecyclerAdapter adapter = new RecyclerAdapter<WeekDay>(getContext(),list,R.layout.item_date) {
            @Override
            public void convert(Context mContext, BaseRecyclerHolder holder, WeekDay o) {
                holder.setText(R.id.tvTop,o.getWeek());
                TextView tvBottom = holder.getView(R.id.tvBottom);
                tvBottom.setText(o.getDay());
                if (o.isShow()){
                    tvBottom.setBackground(getResources().getDrawable(R.drawable.shape_blue));
                    tvBottom.setTextColor(getResources().getColor(R.color.colorW));
                    holder.getView(R.id.tvRound).setVisibility(View.VISIBLE);
                }
            }
        };
        recyclerDate.setAdapter(adapter);
    }
}
