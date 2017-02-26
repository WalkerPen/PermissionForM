package com.pen.permissionform;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.permissionlib.PermissionForM;
import com.example.permissionlib.PermissionListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = new String[] {PermissionForM.LOCATION, PermissionForM.STORAGE, PermissionForM.CAMERA};
        PermissionForM.requestPermissions(this, permissions, new PermissionListener() {
            @Override
            public void onHave() {
                Toast.makeText(MainActivity.this, "已拥有权限", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied() {
                PermissionForM.go2AppSetting(MainActivity.this);
            }

            @Override
            public void onShowRequestPermissionRationale() {
                Toast.makeText(MainActivity.this, "请务必同意权限请求", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionForM.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
