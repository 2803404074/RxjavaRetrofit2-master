package com.dabangvr.live.activity;

import android.view.View;
import android.widget.TextView;

import com.dabangvr.live.R;
import com.dabangvr.live.base.BaseActivity;
import com.dbvr.baselibrary.model.UserMess;
import com.dbvr.baselibrary.utils.SPUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.OnClick;

public class LiveFinishActivity extends BaseActivity {

    @BindView(R.id.sdvHead)
    SimpleDraweeView sdvHead;

    @BindView(R.id.sdvHead_finish)
    TextView tvNickName;
    @Override
    public int setLayout() {
        return R.layout.activity_live_finish;
    }

    @Override
    public void initView() {
        UserMess userMess = SPUtils.instance(this).getUser();
        sdvHead.setImageURI(userMess.getHeadUrl());
        tvNickName.setText(userMess.getNickName());
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tvConfirm})
    public void click(View view){
        switch (view.getId()){
            case R.id.tvConfirm:
                finish();
                break;
                default:break;
        }
    }
}
