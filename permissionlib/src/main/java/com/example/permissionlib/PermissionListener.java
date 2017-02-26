package com.example.permissionlib;

/**
 * Created by Pen on 2017/2/25.
 */

public interface PermissionListener {
    void onHave();
    void onGranted();
    void onDenied();
}
