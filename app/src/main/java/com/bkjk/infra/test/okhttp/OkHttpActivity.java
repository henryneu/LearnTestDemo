package com.bkjk.infra.test.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bkjk.infra.test.R;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    @BindView(R.id.test_ok_http_sync_bt)
    Button mOkHttpSyncBt;
    @BindView(R.id.test_ok_http_async_bt)
    Button mOkHttpAsyncBt;
    @BindView(R.id.test_retrofit_sync_bt)
    Button mRetrofitSyncBt;
    @BindView(R.id.test_retrofit_async_bt)
    Button mRetrofitAsyncBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.test_ok_http_sync_bt, R.id.test_ok_http_async_bt,
            R.id.test_retrofit_sync_bt, R.id.test_retrofit_async_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.test_ok_http_sync_bt:
                // OkHttp同步请求
                requestOkHttpSync();
                break;
            case R.id.test_ok_http_async_bt:
                // OkHttp异步请求
                requestOkHttpAsync();
                break;
            case R.id.test_retrofit_sync_bt:
                // Retrofit同步请求
                requestRetrofitSync();
                break;
            case R.id.test_retrofit_async_bt:
                // Retrofit异步请求
                requestRetrofitAsync();
                break;
        }
    }

    OkHttpClient client = new OkHttpClient();
    OkHttpClient mClient = new OkHttpClient.Builder()
            .cache(new Cache(new File("test"), 24*1024*1024))
            .build();
    private void requestOkHttpSync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url("https://www.baidu.com/")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        System.out.println("OkHttp同步请求成功");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void requestOkHttpAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url("https://www.baidu.com/")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    // 异步请求的失败和成功回调都是运行在工作线程中的（也就是子线程中）

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            System.out.println("OkHttp异步请求成功");
                        }
                    }
                });
            }
        }).start();
    }

    private void requestRetrofitSync() {

    }

    private void requestRetrofitAsync() {

    }
}
