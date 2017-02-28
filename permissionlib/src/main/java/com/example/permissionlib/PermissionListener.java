package com.example.permissionlib;

import android.app.Activity;

/**
 * Created by Pen on 2017/2/25.
 */

public interface PermissionListener {
    /**
     * 已经拥有权限
     */
    void onHave();

    /**
     * 获取成功
     */
    void onGranted();

    /**
     * 获取失败
     */
    void onDenied();

    /**
     *展示请求权限的理由,
     */
    void onShowRequestPermissionRationale(Activity activity, String[] permissions);

    /**
     * 是否需要展示请求权限理由
     * @return
     */
    boolean shouldShowRationale();
}
