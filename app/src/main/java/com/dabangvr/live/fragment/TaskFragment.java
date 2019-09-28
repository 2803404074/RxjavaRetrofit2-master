package com.dabangvr.live.fragment;


import android.content.Context;
import android.net.ParseException;
import android.util.Log;
import android.view.Gravity;
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
import com.dabangvr.live.livezb.acitivity.SwcameraStreamingActivity;
import com.dabangvr.live.utils.DataUtil;
import com.dabangvr.live.utils.DialogUtil;
import com.dabangvr.live.utils.PopupWindowUtil;
import com.dbvr.baselibrary.model.TaskMo;
import com.dbvr.baselibrary.model.WeekDay;
import com.dbvr.baselibrary.utils.DateUtils;
import com.dbvr.baselibrary.utils.SPUtils;
import com.dbvr.baselibrary.utils.ToastUtil;
import com.dbvr.retrofitlibrary.observer.MyObserver;
import com.dbvr.retrofitlibrary.utils.RequestUtils;
import com.dbvr.retrofitlibrary.utils.RetrofitUtils;

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

    @BindView(R.id.tvTaskList)
    TextView tvTaskList;

    private RecyclerAdapter recyclerAdapter;
    private List<TaskMo> mData = new ArrayList<>();

    @Override
    public int layoutId() {
        return R.layout.task_fragment;
    }

    @Override
    public void initView() {
        toolBar.setBackgroundResource(R.color.green02);
        tvTitle.setText("任务中心");
        tvTitle.setTextColor(getContext().getResources().getColor(R.color.colorW));

        //日期
        recyclerDate.setLayoutManager(new GridLayoutManager(getContext(), 7));
        List<WeekDay> list = DataUtil.getWeekDay();
        RecyclerAdapter adapter = new RecyclerAdapter<WeekDay>(getContext(), list, R.layout.item_date) {
            @Override
            public void convert(Context mContext, BaseRecyclerHolder holder, WeekDay o) {
                holder.setText(R.id.tvTop, o.getWeek());
                TextView tvBottom = holder.getView(R.id.tvBottom);
                tvBottom.setText(o.getDay());
                if (o.isShow()) {
                    tvBottom.setBackground(getResources().getDrawable(R.drawable.shape_blue));
                    tvBottom.setTextColor(getResources().getColor(R.color.colorW));
                    holder.getView(R.id.tvRound).setVisibility(View.VISIBLE);
                }
            }
        };
        recyclerDate.setAdapter(adapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerAdapter = new RecyclerAdapter<TaskMo>(getContext(), mData, R.layout.item_task) {
            @Override
            public void convert(Context mContext, BaseRecyclerHolder holder, TaskMo o) {

                holder.setText(R.id.tvContent,o.getTaskContent());
                holder.setText(R.id.tvDate,o.getTaskTime());

                RecyclerView recyclerGoods = holder.getView(R.id.recycle_goods);
                recyclerGoods.setLayoutManager(new LinearLayoutManager(getContext()));
                RecyclerAdapter adapter1 = new RecyclerAdapter<TaskMo.GoodsList>(getContext(),o.getGoodsList(),R.layout.item_task_item) {
                    @Override
                    public void convert(Context mContext, BaseRecyclerHolder holder, TaskMo.GoodsList o) {
                        holder.setImageByUrl(R.id.iv_content,o.getListUrl());
                        holder.setText(R.id.tvTitle,o.getName());
                        holder.setText(R.id.tvPrice,"¥"+o.getSellingPrice());

                        TextView tvSee = holder.getView(R.id.tvSee);
                        tvSee.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogUtil dialogUtil = new DialogUtil(getContext()) {
                                    @Override
                                    public void convert(BaseRecyclerHolder holder) {
                                        holder.setText(R.id.tvGoodsTx,"本产品由xxxx，从您直播间所卖出的订单，可获得百分之十的提成，十单起步");
                                        holder.setText(R.id.tvJq,"技巧技");
                                    }
                                };
                                dialogUtil.show(R.layout.popuview_text);
                            }
                        });
                    }
                };
                recyclerGoods.setAdapter(adapter1);
            }
        };
        recyclerView.setAdapter(recyclerAdapter);

    }

    @Override
    public void initData() {
        RequestUtils.getTaskList(getContext(), String.valueOf(System.currentTimeMillis()), new MyObserver<List<TaskMo>>(getContext()) {
            @Override
            public void onSuccess(List<TaskMo> result) {
                if (result != null && result.size() > 0) {
                    mData = result;
                    tvTaskList.setText("任务总汇列表("+mData.size()+")");
                    recyclerAdapter.updateData(result);
                }
            }

            @Override
            public void onFailure(Throwable e, String errorMsg) {
                ToastUtil.showShort(getContext(),errorMsg);
            }
        });
    }
}
