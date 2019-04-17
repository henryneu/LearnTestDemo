package com.bkjk.infra.test.binderpool;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;

import com.bkjk.infra.test.IComputer;
import com.bkjk.infra.test.IEncodeDecode;
import com.bkjk.infra.test.R;

import androidx.appcompat.app.AppCompatActivity;

public class BinderPoolActivity extends AppCompatActivity {

    private static final String TAG = "BinderPoolActivity";

    private IComputer mComputer;
    private IEncodeDecode mEncodeDecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);
        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
    }

    private void doWork() {
        BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);
        IBinder encodeDecode = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mEncodeDecode = EncodeDecodeImpl.asInterface(encodeDecode);
        Log.d(TAG, "visit IEncodeDecode");
        String msg = "helloworld-安卓";
        System.out.println("content:" + msg);
        try {
            String password = mEncodeDecode.encode(msg);
            System.out.println("BinderPoolActivity-> encrypt:" + password);
            System.out.println("BinderPoolActivity-> decrypt:" + mEncodeDecode.decode(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "visit IComputer");
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        mComputer = ComputerImpl.asInterface(computeBinder);
        try {
            System.out.println("BinderPoolActivity-> 3+5=" + mComputer.calculate(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
