package com.pen.permissionform;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.permissionlib.PermissionForM;
import com.example.permissionlib.PermissionListener;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initView();

        permission();
    }

    private void initView() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragment, new MyFragment(), "MyFragment")
                .commit();
    }

    private void permission() {
       requestPermissions(new Runnable() {
           @Override
           public void run() {
               Toast.makeText(MainActivity.this, "权限已经获取", Toast.LENGTH_SHORT).show();
               //写实际调用相册的代码
           }
       });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionForM.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
