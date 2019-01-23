package com.bkjk.infra.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bkjk.infra.test.bankcard.EditTextWatcher;
import com.bkjk.infra.test.glide.GlideSampleActivity;
import com.bkjk.infra.test.hotfix.SampleHotFixActivity;
import com.bkjk.infra.test.phonecall.PhoneCallActivity;
import com.bkjk.infra.test.spannable.SpannableActivity;
import com.bkjk.infra.test.webank.WeBankActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private LinearLayout mOutLinearLayout;
    private LinearLayout mLinearLayout;
    private EditText mBankInputEt;
    private TextView mSpanTv;
    private TextView mGlideTv;
    private TextView mHotFixTv;
    private TextView mScrollTv;
    private TextView mPhoneCallTv;
    private TextView mWeBankTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 继承自 AppCompatActivity 时,这种方法是无效的,继承自 Activity 时有效
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 去掉Activity上面的状态栏
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        // 继承自 AppCompatActivity 时有效
        // getSupportActionBar().hide();
        // 继承自 Activity 时有效
        // getActionBar().hide();
        // 初始化视图
        initView();
    }

    private void initView() {
        mOutLinearLayout = (LinearLayout) findViewById(R.id.test_input_out_ll);
        mLinearLayout = (LinearLayout) findViewById(R.id.test_input_ll);
        mBankInputEt = (EditText) findViewById(R.id.test_input_et);
        mSpanTv = (TextView) findViewById(R.id.test_spannable_string_tv);
        mGlideTv = (TextView) findViewById(R.id.test_glide_tv);
        mHotFixTv = (TextView) findViewById(R.id.test_hot_fix_tv);
        mScrollTv = (TextView) findViewById(R.id.test_scroll_fill_tv);
        mPhoneCallTv = (TextView) findViewById(R.id.test_phone_call_tv);
        mWeBankTv = (TextView) findViewById(R.id.test_we_bank_tv);
        EditTextWatcher.bind(mBankInputEt);
        mSpanTv.setOnClickListener(this);
        mGlideTv.setOnClickListener(this);
        mHotFixTv.setOnClickListener(this);
        mScrollTv.setOnClickListener(this);
        mPhoneCallTv.setOnClickListener(this);
        mWeBankTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.test_spannable_string_tv) {
            Intent intent = new Intent(MainActivity.this, SpannableActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.test_glide_tv) {
            Intent intent = new Intent(MainActivity.this, GlideSampleActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.test_hot_fix_tv) {
            Intent intent = new Intent(MainActivity.this, SampleHotFixActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.test_scroll_fill_tv) {
            Intent intent = new Intent(MainActivity.this, MainScrollActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.test_phone_call_tv) {
            Intent intent = new Intent(MainActivity.this, PhoneCallActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.test_we_bank_tv) {
            Intent intent = new Intent(MainActivity.this, WeBankActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isEditTextFocused(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    mOutLinearLayout.setClickable(true);
                    mOutLinearLayout.setFocusable(true);
                    mOutLinearLayout.setFocusableInTouchMode(true);
                    mOutLinearLayout.requestFocusFromTouch();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isEditTextFocused(View v, MotionEvent event) {
        if (v != null && v instanceof EditText) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的 location 位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
