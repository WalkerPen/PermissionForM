package com.pen.permissionform;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("是否前去设置中开启权限?");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("设置中心", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        PermissionForM.go2AppSetting(MainActivity.this);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            @Override
            public void onShowRequestPermissionRationale(final Activity activity, final String[] permissions) {
//                PermissionForM.reRequest(activity, permissions);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("此功能需要权限，请务必同意!");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("请求权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        PermissionForM.reRequest(activity, permissions);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            @Override
            public boolean shouldShowRationale() {
                return true;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionForM.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
