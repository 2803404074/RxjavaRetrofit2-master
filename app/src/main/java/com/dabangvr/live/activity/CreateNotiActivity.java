package com.dabangvr.live.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dabangvr.live.R;
import com.dabangvr.live.base.BaseActivity;
import com.dabangvr.live.base.BaseRecyclerHolder;
import com.dabangvr.live.base.RecyclerAdapter;
import com.dabangvr.live.livezb.acitivity.SwcameraStreamingActivity;
import com.dabangvr.live.utils.BottomDialogUtil;
import com.dabangvr.live.utils.qiniu.listener.OnUploadListener;
import com.dabangvr.live.utils.qiniu.manager.QiniuUploadManager;
import com.dabangvr.live.view.MyImageView;
import com.dbvr.baselibrary.model.QiniuUploadFile;
import com.dbvr.baselibrary.model.StreamMo;
import com.dbvr.baselibrary.model.TagMo;
import com.dbvr.baselibrary.utils.ToastUtil;
import com.dbvr.retrofitlibrary.observer.MyObserver;
import com.dbvr.retrofitlibrary.utils.RequestUtils;
import com.google.gson.Gson;
import com.wildma.pictureselector.PictureSelector;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建预告
 */
public class CreateNotiActivity extends BaseActivity {
    @BindView(R.id.tvAdd)
    TextView tvAdd;
    @BindView(R.id.iv_content)
    MyImageView imageView;
    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.etContent)
    EditText etContent;
    @BindView(R.id.rb_live)
    RadioButton liveRadioButton;
    @BindView(R.id.ll_TagContent)
    LinearLayout llTagContent;

    private String picturePath;//封面地址

    @Override
    public int setLayout() {
        return R.layout.activity_create_live;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        RequestUtils.getLiveTag(this, new MyObserver<List<TagMo>>(getContext()) {
            @Override
            public void onSuccess(List<TagMo> result) {
                mData = result;
            }
            @Override
            public void onFailure(Throwable e, String errorMsg) {
                ToastUtil.showShort(getContext(),errorMsg);
            }
        });
    }

    public void onClickImage(View view){
        /**
         * create()方法参数一是上下文，在activity中传activity.this，在fragment中传fragment.this。参数二为请求码，用于结果回调onActivityResult中判断
         * selectPicture()方法参数分别为 是否裁剪、裁剪后图片的宽(单位px)、裁剪后图片的高、宽比例、高比例。都不传则默认为裁剪，宽200，高200，宽高比例为1：1。
         */
        PictureSelector
                .create(CreateNotiActivity.this, PictureSelector.SELECT_REQUEST_CODE)
                .selectPicture(true, 130, 156, 5, 6);

    }

    @OnClick({R.id.tvAdd,R.id.tvAddTag,R.id.tvCreate})
    public void onTuch(View view){
        switch (view.getId()){
            case R.id.tvAdd:
                break;
            case R.id.tvAddTag:
                showBottomDialog();
                break;
            case R.id.tvCreate:
                judge();
                break;
                default:break;
        }
    }

    /**
     * 判断输入信息
     * 获取七牛云token
     */
    private void judge(){
        if (StringUtils.isEmpty(etTitle.getText().toString())){
            ToastUtil.showShort(getContext(),"标题不能留空");
            return;
        }
        if (StringUtils.isEmpty(etContent.getText().toString())){
            ToastUtil.showShort(getContext(),"内容不能留空");
            return;
        }
        if (StringUtils.isEmpty(picturePath)){
            ToastUtil.showShort(getContext(),"请添加封面");
            return;
        }
        if (null == checkItemData || checkItemData.size() == 0){
            ToastUtil.showShort(getContext(),"请添加标签");
            return;
        }

        RequestUtils.getQinNiu(this,new MyObserver<QiniuUploadFile>(getContext()) {
            @Override
            public void onSuccess(QiniuUploadFile result) {
                upCover(result);//这里是七牛的token
            }
            @Override
            public void onFailure(Throwable e, String errorMsg) {

            }
        });
    }

    private ProgressDialog pd;
    /**
     * 上传封面
     * 成功后再请求后端创建推流
     */
    private void upCover(QiniuUploadFile qiniuUploadFile){
        qiniuUploadFile.setFilePath(picturePath);
        qiniuUploadFile.setKey("live-cover"+ UUID.randomUUID());
        QiniuUploadManager.getInstance(this).upload(qiniuUploadFile, new OnUploadListener() {
            @Override
            public void onStartUpload() {
                pd = new ProgressDialog(getContext());
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.setTitle("正在初始化直播间");
                pd.setMessage("校检个人信息...");
                pd.show();
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
            }
            @Override
            public void onUploadProgress(String key, double percent) {
                int percentInt = (int)percent;
                pd.setProgress(percentInt);
            }

            @Override
            public void onUploadFailed(String key, String err) {
                pd.dismiss();
                ToastUtil.showShort(getContext(),err);
            }

            @Override
            public void onUploadBlockComplete(String key) {
                pd.dismiss();
                createLive(key);
            }

            @Override
            public void onUploadCompleted() {
                pd.dismiss();
            }

            @Override
            public void onUploadCancel() {
                pd.dismiss();
            }
        });
    }

    private void createLive(String imgKey) {
        Gson gson = new Gson();
        List<String>tagIds = new ArrayList<>();
        for (int i = 0; i < checkItemData.size(); i++) {
            tagIds.add(checkItemData.get(i).getId());
        }
        String tagListStr = gson.toJson(tagIds);
        Map<String,Object>map = new HashMap<>();
        map.put("liveTitle",etTitle.getText().toString());
        map.put("liveContent",etContent.getText().toString());
        map.put("liveTag", tagListStr);
        map.put("liveGImg",imgKey);
        RequestUtils.createSteam(this,map, new MyObserver<StreamMo>(getContext()) {
            @Override
            public void onSuccess(StreamMo result) {
                Map<String,Object>map1 = new HashMap<>();
                map1.put("streamUrl",result.getPublishURL());
                map1.put("streamTag",result.getTag());
                goTActivity(SwcameraStreamingActivity.class,map1);
            }
            @Override
            public void onFailure(Throwable e, String errorMsg) {
                ToastUtil.showShort(getContext(),errorMsg);
            }
        });
    }

    private RecyclerAdapter adapter;
    private int checkNum=1;
    private List<TagMo>mData = new ArrayList<>();
    private BottomDialogUtil dialogUtil;
    private List<TagMo>checkItemData = new ArrayList<>();
    private void showBottomDialog() {
        if (mData.size()>0){
            dialogUtil = new BottomDialogUtil(getContext(),R.layout.recycle_view,3) {
                @Override
                public void convert(View holder) {
                    RecyclerView recyclerView = holder.findViewById(R.id.recycle_tag);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),5));
                    adapter = new RecyclerAdapter<TagMo>(getContext(),mData,R.layout.item_txt) {
                        @Override
                        public void convert(Context mContext, BaseRecyclerHolder holder, TagMo o) {
                            CheckBox checkBox = holder.getView(R.id.cb_txt);
                            checkBox.setText(o.getName());
                            checkBox.setChecked(o.isCheck());
                            if (o.isCheck()){
                                checkBox.setTextColor(getResources().getColor(R.color.colorW));
                            }else {
                                checkBox.setTextColor(getResources().getColor(R.color.green01));
                            }
                        }
                    };

                    recyclerView.setAdapter(adapter);
                }
            };
            dialogUtil.show();
            adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    boolean isCheck = mData.get(position).isCheck();
                    if (isCheck){
                        mData.get(position).setCheck(false);
                        checkItemData.remove(mData.get(position));
                        checkNum--;
                    }else {
                        if (checkNum>5)return;
                        mData.get(position).setCheck(true);
                        checkItemData.add(mData.get(position));
                        checkNum++;
                    }
                    adapter.updateDataa(mData);
                }
            });

            dialogUtil.setOnDismissCallBack(new BottomDialogUtil.OnDismissCallBack() {
                @Override
                public void onDismiss() {
                    System.out.println(checkItemData.toArray());
                    addViewByJava();
                }
            });
        }

    }

    private void addViewByJava() {
        for (int i = 0; i < checkItemData.size(); i++) {
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            vlp.setMargins(5,0,5,0);
            tv.setLayoutParams(vlp);// 设置TextView的布局
            tv.setText(checkItemData.get(i).getName());
            tv.setTag(i);
            tv.setBackground(getResources().getDrawable(R.drawable.shape_gray));
            tv.setPadding(10,3,10,3);
            llTagContent.addView(tv);// 将TextView 添加到container中
            Log.e("aaaa","循环"+i);
        }
        Log.e("aaaa","完成");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
    }
}
