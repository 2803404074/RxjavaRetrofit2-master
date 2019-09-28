package com.dabangvr.live.activity;

import android.view.View;

import com.dabangvr.live.R;
import com.dabangvr.live.base.BaseActivity;

import butterknife.OnClick;

public class MyNewsActivity extends BaseActivity {

    @Override
    public int setLayout() {
        return R.layout.activity_my_news;
    }

    @Override
    public void initView() {

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
