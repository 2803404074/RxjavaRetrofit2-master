package com.dabangvr.live.fragment;


import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.dabangvr.live.R;
import com.dabangvr.live.activity.CreateLiveActivity;
import com.dabangvr.live.base.BaseFragment;
import com.dabangvr.live.base.BaseRecyclerHolder;
import com.dabangvr.live.base.RecyclerAdapter;
import com.dbvr.baselibrary.model.UserMess;
import com.dbvr.baselibrary.utils.SPUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.sdvHead)
    SimpleDraweeView sdvHead;
    @BindView(R.id.tvNickName)
    TextView tvNickName;
    @BindView(R.id.recycle_notice)
    RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<String>mData = new ArrayList<>();
    @Override
    public int layoutId() {
        return R.layout.home_fragment;
    }

    @Override
    public void initView() {
        UserMess userMess = SPUtils.instance(getContext()).getUser();
        sdvHead.setImageURI(userMess.getHeadUrl());
        tvNickName.setText(userMess.getNickName());

        for (int i = 0; i < 5; i++) {
            mData.add("");
        }
        recyclerView.setFocusable(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerAdapter<String>(getContext(),mData,R.layout.item_notice) {
            @Override
            public void convert(Context mContext, BaseRecyclerHolder holder, String o) {

            }
        };
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void initData() {

    }

    @OnClick({R.id.ll_createLive,R.id.ll_createVideo})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_createLive:
                goTActivity(CreateLiveActivity.class,null);
                break;
            case R.id.ll_createVideo:
                goTActivity(CreateLiveActivity.class,null);
                break;
                default:break;
        }
    }

}
