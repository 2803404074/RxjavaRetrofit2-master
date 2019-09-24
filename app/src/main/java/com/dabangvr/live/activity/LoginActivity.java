package com.dabangvr.live.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dabangvr.live.R;
import com.dabangvr.live.base.BaseActivity;
import com.dbvr.baselibrary.base.Contents;
import com.dbvr.baselibrary.model.UserMess;
import com.dbvr.baselibrary.utils.SPUtils;
import com.dbvr.baselibrary.utils.ToastUtil;
import com.dbvr.retrofitlibrary.observer.MyObserver;
import com.dbvr.retrofitlibrary.utils.RequestUtils;

import org.apache.commons.lang.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etPass)
    EditText etPass;

    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tvCodeLogin)
    TextView tvCodeLogin;

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tvLogin,R.id.tvCodeLogin})
    public void touchEvent(View view){
        switch (view.getId()){
            case R.id.tvLogin:
                checkInput();
                break;
            case R.id.tvCodeLogin:
                goTActivity(LoginCodeActivity.class,null);
                break;
                default:break;
        }
    }

    private void checkInput(){
        if (StringUtils.isEmpty(etPhone.getText().toString())){
            ToastUtil.showShort(this,"手机号不能留空");
            return;
        }
        if (StringUtils.isEmpty(etPass.getText().toString())){
            ToastUtil.showShort(this,"密码不能留空");
            return;
        }
        RequestUtils.loginByPhone(this, etPhone.getText().toString(), etPass.getText().toString(), new MyObserver<UserMess>(getContext()) {
            @Override
            public void onSuccess(UserMess result) {
                SPUtils.instance(getContext()).putObj(Contents.USER,result);
                SPUtils.instance(getContext()).put("token",result.getToken());
                startActivity(new Intent(getContext(),MainActivity.class));
                finish();
            }
            @Override
            public void onFailure(Throwable e, String errorMsg) {
                ToastUtil.showShort(getContext(),errorMsg);
            }
        });
    }
}
