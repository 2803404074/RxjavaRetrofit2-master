package com.dabangvr.live.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dabangvr.live.R;
import com.dabangvr.live.base.BaseActivity;
import com.dabangvr.live.fragment.HomeFragment;
import com.dabangvr.live.fragment.MyFragment;
import com.dabangvr.live.fragment.TaskFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    BottomNavigationView navView;

    @BindView(R.id.fg_content)
    FrameLayout frameLayout;

    private HomeFragment homeFragment;
    private TaskFragment taskFragment;
    private MyFragment myFragment;
    private FragmentManager fragmentManager;
    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        fragmentManager = getSupportFragmentManager();
        navView.setOnNavigationItemSelectedListener(this);
        changeFragment(0);
    }

    @Override
    public void initData() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限，CEMERA_OK是自定义的常量
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);
        }

        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO} ,101);
        };

    }

    public void changeFragment(int index) {
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        hideFragments(beginTransaction);
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    beginTransaction.add(R.id.fg_content, homeFragment);
                } else {
                    beginTransaction.show(homeFragment);
                }
                break;
            case 1:
                if (taskFragment == null) {
                    taskFragment = new TaskFragment();
                    beginTransaction.add(R.id.fg_content, taskFragment);
                } else {
                    beginTransaction.show(taskFragment);
                }
                break;
            case 2: //购物车
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    beginTransaction.add(R.id.fg_content, myFragment);
                } else {
                    beginTransaction.show(myFragment);
                }
                break;
            default:
                break;
        }
        //需要提交事务
        beginTransaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        /*****/
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }

        if (taskFragment != null) {
            transaction.hide(taskFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                changeFragment(0);
                menuItem.setChecked(true);
                break;
            case R.id.navigation_dashboard:
                changeFragment(1);
                menuItem.setChecked(true);
                break;
            case R.id.navigation_notifications:
                changeFragment(2);
                menuItem.setChecked(true);
                break;
        }
        return false;
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}