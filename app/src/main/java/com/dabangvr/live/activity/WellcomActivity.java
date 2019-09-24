package com.dabangvr.live.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.dabangvr.live.R;
import com.dbvr.baselibrary.utils.SPUtils;

public class WellcomActivity extends AppCompatActivity {
    private TextView text_version;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcom);
        text_version = this.findViewById(R.id.text_version);
        text_version.setText("V" + getVersion());

        if (null == SPUtils.instance(this).getUser()){
            goTActivity(LoginActivity.class);
        }else {
            goTActivity(MainActivity.class);
            SPUtils.instance(this).put("token",SPUtils.instance(this).getUser().getToken());
        }
    }
    private void goTActivity(final Class T){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WellcomActivity.this,T);
                startActivity(intent);
                finish();
            }
        },1500);
    }

    //获取版本号
    private String getVersion() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException eArp) {
        }
        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
