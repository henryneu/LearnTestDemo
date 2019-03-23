package com.bkjk.infra.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bkjk.infra.test.widgets.CustomButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubThreadActivity extends AppCompatActivity {

    @BindView(R.id.test_sub_thread_main)
    TextView mSubThreadTv;
    @BindView(R.id.test_button_left_bt)
    CustomButton mLeftBt;
    @BindView(R.id.test_button_right_bt)
    CustomButton mRightBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_thread);
        ButterKnife.bind(this);

        /**
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mSubThreadTv.setText("子线程中修改");
            }
        }).start();
        */
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @OnClick({R.id.test_button_left_bt, R.id.test_button_right_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.test_button_left_bt:
                Toast.makeText(this, "点击了左Button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.test_button_right_bt:
                Toast.makeText(this, "点击了右Button", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
