package com.bkjk.infra.test;

import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.bkjk.infra.test.remote.RemoteActivity;

public class CircleViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;
    private Button mValueBt;
    private Button mValueListenerBt;

    Handler mHandler1 = new MyHandler();

    Handler mHandler2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    Handler mHandler3 = new Handler(Looper.getMainLooper());

    Handler mHandler4 = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    Handler mHandler5 = new Handler();

    //AsyncTask
    //HandlerThread
    //StringBuffer
    //LocalBroadcastManager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_view);
        mButton = (Button) findViewById(R.id.test_notification_btn);
        mValueBt = (Button) findViewById(R.id.test_notification_custom_btn);
        mValueListenerBt = (Button) findViewById(R.id.test_notification_custom_listener_btn);
        mButton.setOnClickListener(this);
        mValueBt.setOnClickListener(this);
        mValueListenerBt.setOnClickListener(this);

        initAnim(mButton);
        initAnim(mValueBt);
        initAnim(mValueListenerBt);
    }

    private void initAnim(View targetView) {
        ValueAnimator animator = ObjectAnimator.ofInt(targetView, "backgroundColor", /*Red*/0xFFFF8080, /*Blue*/0xFF8080FF);
        animator.setDuration(3000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.test_notification_btn) {
            // 开启通知
            startOpenNotification();
        } else if (viewId == R.id.test_notification_custom_btn) {
            // 缩放按钮
            performAnim();
        } else if (viewId == R.id.test_notification_custom_listener_btn) {
            // Listener 监听
            performAnimListener(mValueListenerBt, mValueListenerBt.getWidth(), 600);
        }
    }

    private void performAnim() {
        ViewWrap viewWrap = new ViewWrap(mValueBt);
        ObjectAnimator.ofInt(viewWrap, "width", 600).setDuration(5000).start();
    }

    private void performAnimListener(final View targetView, final int startValue, final int endValue) {
        ValueAnimator animator = ValueAnimator.ofInt(1, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator mEvaluator = new IntEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                float fraction = animation.getAnimatedFraction();
                targetView.getLayoutParams().width = mEvaluator.evaluate(fraction, startValue, endValue);
                targetView.requestLayout();
            }
        });

        animator.setDuration(5000).start();
    }

    private static class ViewWrap {
        private View targetView;

        public ViewWrap(View targetView) {
            this.targetView = targetView;
        }

        public int getWidth() {
            return targetView.getLayoutParams().width;
        }

        public void setWidth(int width) {
            targetView.getLayoutParams().width = width;
            targetView.requestLayout();
        }
    }

    static class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    private void startOpenNotification() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.loading_icon);
        builder.setTicker("hello henry");
        builder.setContentTitle("Notification");
        builder.setContentText("点击查看详细内容");
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setAutoCancel(true);
        Intent intent = new Intent(CircleViewActivity.this, RemoteActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification_layout);
        remoteViews.setTextViewText(R.id.test_custom_remote_tv, "custom henry");
        remoteViews.setImageViewResource(R.id.test_custom_remote_iv, R.drawable.loading_icon);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.test_custom_remote_iv, pendingIntent1);

        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notification.contentView = remoteViews;
        notification.contentIntent = pendingIntent;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(12, notification);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
