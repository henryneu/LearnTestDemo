package com.bkjk.infra.test.phonecall;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bkjk.infra.test.R;

public class PhoneCallActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mCallPhoneBtn;

    // 申请拨打电话权限
    String[] permissionCallPhone = new String[]{Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_call_activity_layout);
        // 启动监听服务
        openService();
        // 初始化视图
        initView();
    }

    private void initView() {
        mCallPhoneBtn = (Button) findViewById(R.id.call_up_bt);
        mCallPhoneBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.call_up_bt) {
            checkPermission(this);
        }
    }

    /**
     * 启动监听服务
     */
    private void openService() {
        Intent intent = new Intent(PhoneCallActivity.this, PhoneListenService.class);
        startService(intent);
    }

    /**
     * 拨打电话
     *
     * @param phoneNum
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }

    /**
     * 检测权限
     *
     * @param context
     */
    private void checkPermission(Activity context) {
        PermissionUtils.getInstance().checkPermission(context, permissionCallPhone, new PermissionUtils.IPermissionsResult() {
            @Override
            public void grantPermission() {
                Toast.makeText(PhoneCallActivity.this, "权限已授予", Toast.LENGTH_SHORT).show();
                callPhone("10086");
            }

            @Override
            public void forbidPermission() {
                Toast.makeText(PhoneCallActivity.this, "权限未授予", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
