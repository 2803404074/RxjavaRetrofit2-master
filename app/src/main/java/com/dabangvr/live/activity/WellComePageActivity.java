package com.dabangvr.live.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.dabangvr.live.R;
import com.dabangvr.live.view.GuideButtomLineView;

import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;


public class WellComePageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private TextView text_time;
    private ViewPager mViewPager;
    private int[] ds = {R.mipmap.wellcom_one, R.mipmap.wellcom_tow, R.mipmap.wellcom_three};
    private GuideButtomLineView mGuideButtomLineView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_come_page);
        mViewPager = this.findViewById(R.id.view_pager);
        text_time = this.findViewById(R.id.text_time);
        mGuideButtomLineView = this.findViewById(R.id.guideButtomLineView);
        mViewPager.setOffscreenPageLimit(ds.length);
        mGuideButtomLineView.setSelectIndex(0);
        //顺便申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return ds.length;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView = new ImageView(WellComePageActivity.this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(WellComePageActivity.this).load(ds[position]).into(imageView);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
                super.destroyItem(container, position, object);
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        });
        mViewPager.addOnPageChangeListener(this);
        text_time.setOnClickListener(this);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        text_time.setVisibility(position == ds.length - 1 ? View.VISIBLE : View.GONE);
        mGuideButtomLineView.setSelectIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text_time:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
                default:break;
        }
    }
}
