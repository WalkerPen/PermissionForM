package com.example.permissionlib;

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
     * 需要请求权限的理由
     */
    void onShowRequestPermissionRationale();
}
