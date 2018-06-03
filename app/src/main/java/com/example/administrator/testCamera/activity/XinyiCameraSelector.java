package com.example.administrator.testCamera.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;


/**
 * Created by Administrator on 2018/6/3 0003.
 * 该类用做跳转链接，以后可以给据需求在该类进行扩展
 */

public class XinyiCameraSelector {

    //弱引用封装，防止内存溢出泄露
    private final WeakReference<Activity> mActivity;
    private final WeakReference<Fragment> mFragment;

    private XinyiCameraSelector(Activity activity) {
        this(activity, (Fragment) null);
    }

    private XinyiCameraSelector(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private XinyiCameraSelector(Activity activity, Fragment fragment) {
        this.mActivity = new WeakReference(activity);
        this.mFragment = new WeakReference(fragment);
    }

    public static XinyiCameraSelector create(Activity activity) {
        return new XinyiCameraSelector(activity);
    }

    public static XinyiCameraSelector create(Fragment fragment) {
        return new XinyiCameraSelector(fragment);
    }


    public void intentFaceView(int requestCode) {
        if (!isFastDoubleClick()) {
            Intent intent = new Intent(this.getActivity(), CameraActivity.class);
            this.getActivity().startActivityForResult(intent, requestCode);

        }

    }

    /**
     * 带返回图片的身份证采集
     *
     * @param requestCode
     * @param isCommit    true：先跳转IdCardActivity(主要用作提交按钮);  false:直接拍照
     */
    public void intentIdCardView(int requestCode, boolean isCommit) {
        if (!isFastDoubleClick()) {
            Class idCardClass = isCommit ? IdCardActivity.class : IdCardCameraActivity.class;
            Intent intent = new Intent(this.getActivity(), idCardClass);
            this.getActivity().startActivityForResult(intent, requestCode);
        }
    }


    @Nullable
    Activity getActivity() {
        return (Activity) this.mActivity.get();
    }

    @Nullable
    Fragment getFragment() {
        return this.mFragment != null ? (Fragment) this.mFragment.get() : null;
    }

    //是否快速点击
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 800L) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }
}
