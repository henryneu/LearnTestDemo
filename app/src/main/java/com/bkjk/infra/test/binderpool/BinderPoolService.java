package com.bkjk.infra.test.binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Author: zhouzhenhua
 * Date: 2019/3/24
 * Version: 1.0.0
 * Description:
 */
public class BinderPoolService extends Service {

    private static final String TAG = "BinderPoolService";

    private Binder mBinderPool = new BinderPool.BinderPoolImpl();

    @Override
    public void onCreate() {
        Log.d(TAG, "BinderPoolService onCreate");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "BinderPoolService onBind");
        return mBinderPool;
    }
}
