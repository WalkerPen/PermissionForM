package com.example.permissionlib;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Pen on 2017/2/25.
 * <p>
 * 安卓6.0以上请求权限工具
 */

public class PermissionForM {
    /**
     * 联系人相关权限<br>
     * WRITE_CONTACTS<br>
     * GET_ACCOUNTS<br>
     * READ_CONTACTS
     */
    public static final String CONTACTS = Manifest.permission.READ_CONTACTS;

    /**
     * 电话相关权限<br>
     * READ_CALL_LOG<br>
     * READ_PHONE_STATE<br>
     * CALL_PHONE<br>
     * WRITE_CALL_LOG<br>
     * USE_SIP<br>
     * PROCESS_OUTGOING_CALLS<br>
     * ADD_VOICEMAIL
     */
    public static final String PHONE = Manifest.permission.CALL_PHONE;

    /**
     * 日历相关权限<br>
     * READ_CALENDAR<br>
     * WRITE_CALENDAR
     */
    public static final String CALENDAR = Manifest.permission.READ_CALENDAR;

    /**
     * 相机相关权限<br>
     * CAMERA
     */
    public static final String CAMERA = Manifest.permission.CAMERA;

    /**
     * 传感器关权限<br>
     * BODY_SENSORS
     */
    public static final String SENSORS = Manifest.permission.BODY_SENSORS;

    /**
     * 位置相关权限<br>
     * ACCESS_FINE_LOCATION<br>
     * ACCESS_COARSE_LOCATION
     */
    public static final String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    /**
     * 外部储存相关权限<br>
     * READ_EXTERNAL_STORAGE<br>
     * WRITE_EXTERNAL_STORAGE
     */
    public static final String STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;

    /**
     * 麦克风相关权限<br>
     * RECORD_AUDIO<br>
     */
    public static final String MICROPHONE = Manifest.permission.RECORD_AUDIO;

    /**
     * 短信相关权限<br>
     * READ_SMS<br>
     * RECEIVE_WAP_PUSH<br>
     * RECEIVE_MMS<br>
     * RECEIVE_SMS<br>
     * SEND_SMS<br>
     * READ_CELL_BROADCASTS<br>
     */
    public static final String SMS = Manifest.permission.SEND_SMS;


    /**
     * 单个权限请求请求码
     */
    private static final int REQUEST_CODE_SINGLE_PERMISSIONS = 100;
    private static PermissionListener sPermissionListener;


    /**
     * 核心方法
     * @param activity
     * @param permissions
     * @param permissionListener
     */
    public static void requestPermissions(Activity activity, String[] permissions, PermissionListener permissionListener) {
        sPermissionListener = permissionListener;
        if (checkSelfPermission(activity, permissions)) {
            //获取过权限
            if(sPermissionListener != null) {
                sPermissionListener.onHave();
            }

        } else {
            //没有权限，请求
            if(shouldShowRequestPermissionRationale(activity, permissions) && sPermissionListener.shouldShowRationale()) {
                if(sPermissionListener != null) {
                    sPermissionListener.onShowRequestPermissionRationale(activity, permissions);
                }
            }else {
                reRequest(activity, permissions);
            }
        }
    }

    /**
     * 在第一次请求权限拒绝后，再次请求调用此方法
     * @param activity
     * @param permissions
     */
    public static void reRequest(Activity activity, String[] permissions) {
        ActivityCompat.requestPermissions(activity,
                permissions,
                REQUEST_CODE_SINGLE_PERMISSIONS);
    }

    /**
     * 检查是否获取权限
     * @param activity
     * @param permissions
     * @return
     */
    private static boolean checkSelfPermission(Activity activity, String[] permissions) {
        for(int i = 0; i < permissions.length; i ++) {
            if(ContextCompat.checkSelfPermission(activity, permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否需要向用户展示请求权限的理由
     * @param activity
     * @param permissions
     * @return
     */
    private static boolean shouldShowRequestPermissionRationale(Activity activity, String[] permissions) {
        for(int i = 0; i < permissions.length; i ++) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 在activity中的onRequestPermissionsResult方法调用此方法
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == REQUEST_CODE_SINGLE_PERMISSIONS) {
            if(isSucceed(grantResults)) {
                //成功
                if(sPermissionListener != null) {
                    sPermissionListener.onGranted();
                }
            }else {
                //失败
                if(sPermissionListener != null) {
                    sPermissionListener.onDenied();
                }
            }
        }
    }

    /**
     * 判断权限请求是否成功
     * @param grantResults
     * @return
     */
    private static boolean isSucceed(int[] grantResults) {
        for(int i = 0; i < grantResults.length; i ++) {
            if(grantResults[i] == PackageManager.PERMISSION_DENIED) {
                //失败
                return false;
            }
        }
        return true;
    }

    /**
     * 如果拒绝了并且不再提示，那么再次执行会提示失败，执行此方法可已前去设置中app信息页面，有用户手动开启权限
     * @param activity
     */
    public static void go2AppSetting(Activity activity) {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }
}
