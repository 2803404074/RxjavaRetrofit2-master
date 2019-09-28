package com.dabangvr.live.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.dabangvr.live.R;
import com.dabangvr.live.base.BaseActivity;

import butterknife.OnClick;

public class MyMessActivity extends BaseActivity {

    @Override
    public int setLayout() {
        return R.layout.activity_my_mess;
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
