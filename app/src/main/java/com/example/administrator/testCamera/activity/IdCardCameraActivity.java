package com.example.administrator.testCamera.activity;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.testCamera.R;
import com.example.administrator.testCamera.utils.ScreenUtils;
import com.xinyi.xycameraview.CameraManager;
import com.xinyi.xycameraview.ui.XYCameraOverlapFragment;

/**
 * Created by Administrator on 2018/6/3 0003.
 */

public class IdCardCameraActivity extends AppCompatActivity implements Camera.PictureCallback, View.OnClickListener {

    private XYCameraOverlapFragment camera_fragment;
    public int currentCamera = CameraManager.BACK;
    private String mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_camera);
        initData();
        initView();
    }

    private void initData() {
        mTitle = getIntent().getStringExtra("tips");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera_fragment.releaseCamera();
    }

    private void initView() {
        TextView title = findViewById(R.id.tvTitle);
        ImageView takePhoto = findViewById(R.id.takePic);
        RelativeLayout changeCamera = findViewById(R.id.rl_change_camera);
        TextView tvCancel = findViewById(R.id.tvCancle);

        if (!TextUtils.isEmpty(mTitle)) title.setText(mTitle);
        takePhoto.setOnClickListener(this);
        changeCamera.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        //初始化自定义camera
        camera_fragment = new XYCameraOverlapFragment();
        float rate = ScreenUtils.getScreenWidth(this) * 1.0f / ScreenUtils.getScreenHeight(this);
        Bundle bundle = new Bundle();
        bundle.putInt("currentCamera", currentCamera);
        bundle.putFloat("rate", rate);
        camera_fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_camera_Layout, camera_fragment).commit();
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        Intent intent = getIntent();
        intent.putExtra("idCardPic", data);
        intent.putExtra("isCommit", false);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takePic:
                //拍照
                if (camera_fragment.isPreviewing()) {
                    camera_fragment.tackPicture(this);
                }
                break;
            case R.id.rl_change_camera:
                //摄像头切换
                changeCamera();
                break;
            case R.id.tvCancle:
                finish();
                break;
        }
    }

    /**
     * 切换摄像头
     */
    private void changeCamera() {
        if (currentCamera == CameraManager.BACK)
            currentCamera = CameraManager.FRONT;
        else
            currentCamera = CameraManager.BACK;
        camera_fragment.changeCamera(currentCamera);
    }
}
