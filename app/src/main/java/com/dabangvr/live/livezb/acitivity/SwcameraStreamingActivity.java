package com.dabangvr.live.livezb.acitivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dabangvr.live.R;
import com.dabangvr.live.activity.CreateLiveActivity;
import com.dabangvr.live.activity.LiveFinishActivity;
import com.dabangvr.live.activity.MainActivity;
import com.dabangvr.live.base.AppManager;
import com.dabangvr.live.base.BaseRecyclerHolder;
import com.dabangvr.live.base.RecyclerAdapter;
import com.dabangvr.live.livezb.ui.CameraPreviewFrameView;
import com.dabangvr.live.utils.DialogUtil;
import com.dbvr.baselibrary.model.UserMess;
import com.dbvr.baselibrary.utils.SPUtils;
import com.dbvr.baselibrary.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.AudioSourceCallback;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.StreamStatusCallback;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingSessionListener;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;

import org.apache.commons.lang.StringUtils;

import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SwcameraStreamingActivity extends Activity
        implements StreamingStateChangedListener, StreamStatusCallback, AudioSourceCallback, StreamingSessionListener {
    CameraPreviewFrameView mCameraPreviewSurfaceView;
    private MediaStreamingManager mMediaStreamingManager;
    private StreamingProfile mProfile;
    private String TAG = "StreamingByCameraActivity";

    @BindView(R.id.ll_notice)
    LinearLayout llNotice;//预览提示

    @BindView(R.id.sdvHead)
    SimpleDraweeView sdvHead;

    @BindView(R.id.tvNickName)
    TextView tvNickName;

    @BindView(R.id.tvOnLine)
    TextView tvOnLine;//在线人数

    @BindView(R.id.tvDzNum)
    TextView tvDzNum;//点赞数量

    @BindView(R.id.charCounter)
    Chronometer chronometer;//计时器

    private boolean initStatus = false;//初始化成功标志
    private boolean isStart = false;//未开始直播或正在直播标志
    @BindView(R.id.tvFinishLive)
    TextView tvFinishLive;//结束直播按钮

    @BindView(R.id.ll_goods)
    LinearLayout llGoods;//商品点击区域

    @BindView(R.id.tvGoodsNum)
    TextView tvGoodsNum;//直播商品的数量

    //评论列表
    @BindView(R.id.recycle_comment)
    RecyclerView recyclerView;
    //列表适配器
    private RecyclerAdapter commentAdapter;
    //评论数据源
    private List<String> list = new ArrayList<>();
    //消息数量，始终将最新消息显示在recycelview的底部
    private int dataSize;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 111) {
                ArrayList data = msg.getData().getParcelableArrayList("data");
                dataSize++;
                recyclerView.smoothScrollToPosition(dataSize);
                commentAdapter.addAll(data);
            }
            return false;
        }
    });

    private void setNotifyUi(){
//            CommentMo commentMo = new CommentMo();
//            commentMo.setType(type);
//            commentMo.setName(auth);
//            commentMo.setMess("赠送--"+mess);
//            commentMo.setHead(head);
//            Bundle bundle = new Bundle();
//            ArrayList arr = new ArrayList();
//            arr.add(commentMo);
//            bundle.putStringArrayList("data", arr);
//            Message message = new Message();
//            message.what = 111;
//            message.setData(bundle);
//            handler.sendMessage(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不显示程序的标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //不显示系统的标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_swcamera_streaming);
        ButterKnife.bind(this);

        //初始化用户信息
        initUserDate();

        //初始化直播控件
        init();
    }

    private void initUserDate() {
        UserMess userMess = SPUtils.instance(this).getUser();
        tvNickName.setText(userMess.getNickName());
        sdvHead.setImageURI(userMess.getHeadUrl());
        tvOnLine.setText("在线  " + 100);
        tvDzNum.setText("赞  " + userMess.getPraisedNumber());

        tvFinishLive.setText("开始直播");
    }

    /**
     * 推流成功后执行的方法
     * 1):联系环信，创建房间，
     * 2):倒计时即使，重连情况下获取上次最后断开连接的时间，继续计时
     */
    private void startFunction() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //能够走到这里
                if (mMediaStreamingManager != null) {
                    mMediaStreamingManager.startStreaming();
                }
            }
        }).start();

        llNotice.setVisibility(View.GONE);//隐藏提示
        isStart = true;//已经开始直播标志
        tvFinishLive.setText("结束直播");
    }

    /**
     * 推流结束（主播主动结束）执行的方法
     * 大邦这无敌牛逼的需求
     * 1）：结束流
     * 2）：释放计时资源
     * 3）：释放弹幕资源
     * 4）：释放静态引用资源
     * 5）：获取总直播时间（计时器）
     * 6）：获取本次获赞数
     * 7）：获取本次关注数
     * 8）：获取本次观看数
     * 9）：获取本次礼物收益
     * 10）：获取本次成交的订单数
     */
    public void stopFunction() {
        Intent intent = new Intent(this, LiveFinishActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.tvFinishLive, R.id.ll_goods})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvFinishLive:
                if (isStart){
                    showTips();
                }else {
                    startFunction();
                }
                break;
            case R.id.ll_goods:
                break;
        }
    }

    private DialogUtil dialogUtil;
    private void showTips() {
        dialogUtil = new DialogUtil(this) {
            @Override
            public void convert(BaseRecyclerHolder holder) {
                holder.getView(R.id.tvConfirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        stopFunction();
                    }
                });
                holder.getView(R.id.tvKeepLive).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogUtil.des();
                    }
                });
                holder.setText(R.id.tv_tips,String.valueOf(chronometer.getBase()));
            }
        };
        dialogUtil.show(R.layout.dialog_tip);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaStreamingManager.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // You must invoke pause here.
        mMediaStreamingManager.pause();
    }

    private void init() {
        //get form you server
        //在七牛云控制台创建推流，如下是推流地址
        String publishURLFromServer = getIntent().getStringExtra("streamUrl");
        String liveTag = getIntent().getStringExtra("liveTag");
        mCameraPreviewSurfaceView = findViewById(R.id.cameraPreview_surfaceView);
        try {
            //encoding setting
            mProfile = new StreamingProfile();
            mProfile.setVideoQuality(StreamingProfile.VIDEO_QUALITY_HIGH1)
                    .setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM2)
                    .setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_480)
                    .setEncoderRCMode(StreamingProfile.EncoderRCModes.QUALITY_PRIORITY)
                    .setPublishUrl(publishURLFromServer);
            //preview setting
            CameraStreamingSetting camerasetting = new CameraStreamingSetting();
            camerasetting.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT)
                    .setContinuousFocusModeEnabled(true)
                    .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.MEDIUM)
                    .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_16_9);
            //streaming engine init and setListener
            mMediaStreamingManager = new MediaStreamingManager(this, mCameraPreviewSurfaceView, AVCodecType.SW_VIDEO_WITH_SW_AUDIO_CODEC);  // soft codec
            mMediaStreamingManager.prepare(camerasetting, mProfile);
            mMediaStreamingManager.setStreamingStateListener(this);
            mMediaStreamingManager.setStreamingSessionListener(this);
            mMediaStreamingManager.setStreamStatusCallback(this);
            mMediaStreamingManager.setAudioSourceCallback(this);
            doTopGradualEffect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            ToastUtil.showShort(this, e.getMessage());
            finish();
        }
    }



    @Override
    public void onStateChanged(StreamingState streamingState, Object extra) {
        switch (streamingState) {
            case PREPARING:
                Log.e(TAG, "PREPARING");
                break;
            case READY:
                Log.e(TAG, "READY");
                //初始化成功
                initStatus = true;
                break;
            case CONNECTING:
                Log.e(TAG, "连接中");
                break;
            case STREAMING:
                Log.e(TAG, "推流中");
                //开始计时
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        int hour = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60);
                        chronometer.setFormat("0" + String.valueOf(hour) + ":%s");
                        chronometer.start();
                    }
                });
                break;
            case SHUTDOWN:
                Log.e(TAG, "直播中断");
                break;
            case IOERROR:
                Log.e(TAG, "网络连接失败");
                break;
            case OPEN_CAMERA_FAIL:
                Log.e(TAG, "摄像头打开失败");
                break;
            case DISCONNECTED:
                Log.e(TAG, "已经断开连接");
                break;
            case TORCH_INFO:
                Log.e(TAG, "开启闪光灯");
                break;
        }
    }

    @Override
    public void notifyStreamStatusChanged(StreamingProfile.StreamStatus status) {
        Log.e(TAG, "StreamStatus = " + status);
    }

    @Override
    public void onAudioSourceAvailable(ByteBuffer srcBuffer, int size, long tsInNanoTime, boolean isEof) {
    }

    @Override
    public boolean onRecordAudioFailedHandled(int code) {
        Log.i(TAG, "onRecordAudioFailedHandled");
        return false;
    }

    @Override
    public boolean onRestartStreamingHandled(int code) {
        Log.i(TAG, "onRestartStreamingHandled");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mMediaStreamingManager != null) {
                    mMediaStreamingManager.startStreaming();
                }
            }
        }).start();
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().finishActivity(CreateLiveActivity.class);
    }

    @Override
    public Camera.Size onPreviewSizeSelected(List<Camera.Size> list) {
        return null;
    }

    @Override
    public int onPreviewFpsSelected(List<int[]> list) {
        return -1;
    }

    //自定义评论区淡出
    private int layerId;

    public void doTopGradualEffect() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Paint mPaint = new Paint();
        // 融合器
        final Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mPaint.setXfermode(xfermode);
        // 创造一个颜色渐变，作为聊天区顶部效果
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, 100.0f, new int[]{0, Color.BLACK}, null, Shader.TileMode.CLAMP);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            // 滑动RecyclerView，渲染之后每次都会回调这个方法，就在这里进行融合
            @Override
            public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(canvas, parent, state);
                mPaint.setXfermode(xfermode);
                mPaint.setShader(linearGradient);
                canvas.drawRect(0.0f, 0.0f, parent.getRight(), 200.0f, mPaint);
                mPaint.setXfermode(null);
                canvas.restoreToCount(layerId);
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                layerId = c.saveLayer(0.0f, 0.0f, (float) parent.getWidth(), (float) parent.getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (dialogUtil!=null){
                if (dialogUtil.isShow()){
                    dialogUtil.des();
                }else {
                    showTips();
                }
            }else {
                showTips();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
