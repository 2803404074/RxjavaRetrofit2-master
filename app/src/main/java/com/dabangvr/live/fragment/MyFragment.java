package com.dabangvr.live.fragment;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dabangvr.live.R;
import com.dabangvr.live.base.BaseFragment;
import com.dabangvr.live.base.BaseRecyclerHolder;
import com.dabangvr.live.base.RecyclerAdapter;
import com.dbvr.baselibrary.model.ServerMo;
import com.dbvr.baselibrary.model.UserMess;
import com.dbvr.baselibrary.utils.SPUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyFragment extends BaseFragment {
    @BindView(R.id.sdvHead)
    SimpleDraweeView sdvHead;
    @BindView(R.id.tvNickName)
    TextView tvNickName;

    @BindView(R.id.recycle_my)
    RecyclerView recyclerView;

    private RecyclerAdapter adapter;

    private List<ServerMo> mData = new ArrayList<>();

    private UserMess userMess;

    @Override
    public int layoutId() {
        return R.layout.my_fragment;
    }

    @Override
    public void initView() {
        userMess = SPUtils.instance(getContext()).getUser();
        sdvHead.setImageURI(userMess.getHeadUrl());
        tvNickName.setText(userMess.getNickName());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initData() {
        mData.add(new ServerMo("wdzb", "我的直播", R.mipmap.my_live));
        mData.add(new ServerMo("dsp", "短视频", R.mipmap.short_video));
        mData.add(new ServerMo("wdsy", "我的收益", R.mipmap.sy));
        mData.add(new ServerMo("dd", "订单", R.mipmap.order));
        mData.add(new ServerMo("zx", "咨询", R.mipmap.news));
        mData.add(new ServerMo("xx", "消息", R.mipmap.message));
        mData.add(new ServerMo("sz", "设置", R.mipmap.set));

        adapter = new RecyclerAdapter<ServerMo>(getContext(),mData,R.layout.item_server) {
            @Override
            public void convert(Context mContext, BaseRecyclerHolder holder, ServerMo o) {
                holder.setText(R.id.tvTitle,o.getName());
                holder.setImageResource(R.id.ivContent,o.getResources());
            }
        };
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Class T = null;
                switch (mData.get(position).getId()){
                    case "wdzb":break;
                    case "dsp":break;
                    case "wdsy":break;
                    case "dd":break;
                    case "zx":break;
                    case "xx":break;
                    case "sz":break;
                }
                goTActivity(T,null);
            }
        });
    }
}
