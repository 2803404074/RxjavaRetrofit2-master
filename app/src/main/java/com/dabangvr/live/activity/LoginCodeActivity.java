package com.dabangvr.live.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dabangvr.live.R;
import com.dabangvr.live.base.BaseActivity;
import com.dbvr.baselibrary.utils.ToastUtil;

import org.apache.commons.lang.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginCodeActivity extends BaseActivity {

    //手机号
    @BindView(R.id.etPhone)
    EditText etPhone;
    //验证码
    @BindView(R.id.etCode)
    EditText etCode;
    //获取验证码
    @BindView(R.id.tvGetCode)
    TextView tvGetCode;
    //登陆
    @BindView(R.id.tvLogin)
    TextView tvLogin;

    @Override
    public int setLayout() {
        return R.layout.activity_login_code;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tvLogin,R.id.tvGetCode})
    public void touchEvent(View view){
        switch (view.getId()){
            case R.id.tvLogin:

                break;
            case R.id.tvGetCode:
                getCode();
                break;
                default:break;
        }
    }

    private void getCode() {
        if (StringUtils.isEmpty(etPhone.getText().toString())){
            ToastUtil.showShort(getContext(),"手机号不能留空");
            return;
        }
        downDate();

        //获取验证码
    }

    private void downDate(){
        new CountDownTimer(60*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                tvGetCode.setText(millisUntilFinished/1000+"秒");
            }

            @Override
            public void onFinish() {
                tvGetCode.setText("重新获取");
            }
        }.start();
    }

    private void checkCode(){
        if (StringUtils.isEmpty(etPhone.getText().toString())){
            ToastUtil.showShort(getContext(),"手机号不能留空");
            return;
        }
        if (StringUtils.isEmpty(etCode.getText().toString())){
            ToastUtil.showShort(getContext(),"验证码不能留空");
            return;
        }

        //请求验证验证码
    }

}
