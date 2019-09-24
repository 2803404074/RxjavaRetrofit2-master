package com.dabangvr.live.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.dbvr.retrofitlibrary.utils.LoadingUtils;
import java.util.Map;
import butterknife.ButterKnife;

/**
 * Created by 黄仕豪 on 2019/7/03
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private LoadingUtils mLoaddingUtils;

    public void setLoaddingView(boolean isLoadding) {
        if (mLoaddingUtils == null) {
            mLoaddingUtils = new LoadingUtils(this);
        }
        if (isLoadding) {
            mLoaddingUtils.show();
        } else {
            mLoaddingUtils.dismiss();
        }
    }

    public void goTActivity(final Class T, Map<String,Object>map){
        Intent intent = new Intent(this,T);
        if (map!=null){
            for (String key : map.keySet()) {
                if (map.get(key) instanceof Boolean){
                    intent.putExtra(key,(boolean)map.get(key));
                }
                if (map.get(key) instanceof String){
                    intent.putExtra(key,(String)map.get(key));
                }
                if (map.get(key) instanceof Integer){
                    intent.putExtra(key,(Integer)map.get(key));
                }
            }
        }
        startActivity(intent);
    }

    // 设置布局
    public abstract int setLayout();

    public abstract void initView();

    public abstract void initData();

    @Override
    protected void onDestroy() {
        setLoaddingView(false);
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    public Context getContext() {
        return this;
    }
}
