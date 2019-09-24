package com.dabangvr.live.fragment;


import android.widget.TextView;

import com.dabangvr.live.R;
import com.dabangvr.live.base.BaseFragment;
import com.dbvr.baselibrary.model.UserMess;
import com.dbvr.baselibrary.utils.SPUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

public class MyFragment extends BaseFragment {
    @BindView(R.id.sdvHead)
    SimpleDraweeView sdvHead;
    @BindView(R.id.tvNickName)
    TextView tvNickName;

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
    }

    @Override
    public void initData() {

    }
}
