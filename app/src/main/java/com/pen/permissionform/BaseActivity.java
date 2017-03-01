package com.pen.permissionform;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.permissionlib.PermissionForM;
import com.example.permissionlib.PermissionListener;

/**
 * Created by : PMan<br>
 * data : 2017/2/28<br>
 * time : 18:28<br>
 * description :
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void requestPermissions(final Runnable runnable) {
        String[] permissions = new String[] {PermissionForM.LOCATION, PermissionForM.STORAGE, PermissionForM.CAMERA};
        PermissionForM.requestPermissions(this, permissions, new PermissionListener() {
            @Override
            public void onHave() {
                //权限已经获取
//                Toast.makeText(BaseActivity.this, "Permission has been obtained", Toast.LENGTH_SHORT).show();
                runnable.run();
            }

            @Override
            public void onGranted() {
//                Toast.makeText(BaseActivity.this, "succeed", Toast.LENGTH_SHORT).show();
                runnable.run();
            }

            @Override
            public void onDenied() {
                AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
                builder.setMessage("go to setting to get perssion");
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("go to setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        PermissionForM.go2AppSetting(BaseActivity.this);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            @Override
            public void onShowRequestPermissionRationale(final Activity activity, final String[] permissions) {
//                PermissionForM.reRequest(activity, permissions);
                AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
                builder.setMessage("we need permission, agreed the request please!");
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("request", new DialogInterface.OnClickListener() {
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
}
