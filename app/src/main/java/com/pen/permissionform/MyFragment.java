package com.pen.permissionform;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.permissionlib.PermissionForM;
import com.example.permissionlib.PermissionListener;

/**
 * Created by : PMan<br>
 * data : 2017/2/28<br>
 * time : 17:44<br>
 * description :
 */

public class MyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("MyFragment");

        permission();
        return textView;
    }

    private void permission() {
        String[] permissions = new String[] {PermissionForM.LOCATION, PermissionForM.STORAGE, PermissionForM.CAMERA};
        PermissionForM.requestPermissions(getActivity(), permissions, new PermissionListener() {
            @Override
            public void onHave() {
                Toast.makeText(getActivity(), "已拥有权限", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGranted() {
                Toast.makeText(getActivity(), "获取成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                        PermissionForM.go2AppSetting(getActivity());
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            @Override
            public void onShowRequestPermissionRationale(final Activity activity, final String[] permissions) {
//                PermissionForM.reRequest(activity, permissions);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
}
