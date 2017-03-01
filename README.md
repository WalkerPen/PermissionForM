# PermissionForM
## 简介
这是一个安卓6.0请求权限的一个框架， 很方便， 完全不需要你自己判断是否请求成功，全部给你封装好。

## 依赖
在projiect的gradle文件中添加

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
在module的gradle文件中添加

```
dependencies {
	        compile 'com.github.WalkerPen:PermissionForM:v1.0.5'
	}
```



## 使用步骤
* 1.首先还是需要在AndroidManifest清单文件里面声明权限

```
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.CAMERA"/>
```
* 2.然后用下面的方法，如果你需要从相册选择照片，那么在onHave()和onGranted()中写选择照片的代码。


```
PermissionForM.requestPermissions(this, permissions, new PermissionListener() {
            @Override
            public void onHave() {
                //权限已经获取
                Toast.makeText(MainActivity.this, "你已经获取过权限，可以直接选择照片", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGranted() {
                Toast.makeText(MainActivity.this, "获取权限成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("获取权限失败，前去设置里面手动打开权限");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("前去设置", new DialogInterface.OnClickListener() {
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
                builder.setMessage("请务必同意权限，很重要!");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("去请求权限", new DialogInterface.OnClickListener() {
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
```
* 3.重写activity的onRequestPermissionsResult方法

```
@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionForM.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
```
## 介绍5个回调方法
* 1.onHave()

    如果你获取过权限，就会调用此方法，可以执行你想要执行的代码，如果从相册中选择照片。

* 2.onGranted()

    如果获取权限成功，调用此方法。一般这里面的代码与onHave()中的代码是一样的。
    
* 3.onDenied()

    如果用户拒绝了，就会回调此方法。拒绝权限后，第二次进来，会有“不再提示”的checkbox，如果用户点了，并且再次拒绝，那么下一次进来，就直接失败，也会回调此方法。
    
    一般来说，我们可以在这里显示一个dialog，提示用户这里需要权限，是否前去设置中心打开权限，这里我直接封装了一个方法PermissionForM.go2AppSetting(传activity);
    
* 4.onShowRequestPermissionRationale()

    当第一次请求权限的时候，用户拒绝了，那么第二次进来会先执行这个方法，表示先提示用户，需要获取权限，希望用户同意之类的。
    
    我们可以显示一个dialog，如果同意，我们就获取权限PermissionForM.reRequest(activity, permissions);
    
* 5.shouldShowRationale()

    这个方法是控制是否回调上面onShowRequestPermissionRationale()的一个开关，比较这个提示并不是必须的，所以在这里可以控制，默认是false，表示就算第一次用户拒绝了，第二次也不提示，而是直接请求权限。
    
    这里如果返回false，那么不管onShowRequestPermissionRationale()中怎么写，都不会执行，必须返回true才行。
    
## Fragment中请求权限

这里就不建议单独在fragment中请求权限，应为一般可能会在不同的页面请求某个权限，所以可以封装在某个baseactivity中，并且在fragment中也是可以调用activity中的方法，所以并不需要单独写。

## BaseActivity中进一步封装

我们可以在BaseActivity中写一个方法，比如叫做selectPhoto(), 我这里就叫requestPermissions()算了。添加一个参数，随便弄一个接口有一个方法就行，这里我们就不宁外创建一个接口了，直接用Runnable。

```
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

           ...
    }
}
```

然后在需要选择照片的地方

```
requestPermissions(new Runnable() {
           @Override
           public void run() {
               Toast.makeText(MainActivity.this, "权限已经获取", Toast.LENGTH_SHORT).show();
               //写实际调用相册的代码
           }
       });
```

## 尾声
是不是很简单，你只需要在回调方法中做对应的事就行，完全不需要自己在去判断什么时候应该做什么，如果你用了这个小框架，并且感觉不错，那么我就心满意足了，谢谢！
