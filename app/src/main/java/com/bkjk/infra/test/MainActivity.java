package com.bkjk.infra.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bkjk.infra.test.bankcard.EditTextWatcher;
import com.bkjk.infra.test.binder.BookManagerActivity;
import com.bkjk.infra.test.glide.GlideSampleActivity;
import com.bkjk.infra.test.hotfix.SampleHotFixActivity;
import com.bkjk.infra.test.okhttp.OkHttpActivity;
import com.bkjk.infra.test.phonecall.PhoneCallActivity;
import com.bkjk.infra.test.service_8.TestServiceActivity;
import com.bkjk.infra.test.spannable.SpannableActivity;
import com.bkjk.infra.test.webank.WeBankActivity;
import com.squareup.haha.perflib.Main;

import java.util.LinkedList;

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
    private TextView mSubThreadTv;
    private TextView mCustomViewTv;
    private TextView mRequestNetTv;
    private TextView mService8Tv;
    private TextView mBinderTv;

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
        Looper.getMainLooper().setMessageLogging(new Printer() {
            @Override
            public void println(String x) {
                Log.e(TAG, "Printer:" + x);
            }
        });

        // System.out.println("count：" + count(mOutLinearLayout));
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
        mSubThreadTv = (TextView) findViewById(R.id.test_sub_thread_tv);
        mCustomViewTv = (TextView) findViewById(R.id.test_circle_view);
        mRequestNetTv = (TextView) findViewById(R.id.test_request_net_view);
        mService8Tv = (TextView) findViewById(R.id.test_service_8_view);
        mBinderTv = (TextView) findViewById(R.id.test_binder_view);
        EditTextWatcher.bind(mBankInputEt);
        mSpanTv.setOnClickListener(this);
        mGlideTv.setOnClickListener(this);
        mHotFixTv.setOnClickListener(this);
        mScrollTv.setOnClickListener(this);
        mPhoneCallTv.setOnClickListener(this);
        mWeBankTv.setOnClickListener(this);
        mSubThreadTv.setOnClickListener(this);
        mCustomViewTv.setOnClickListener(this);
        mRequestNetTv.setOnClickListener(this);
        mService8Tv.setOnClickListener(this);
        mBinderTv.setOnClickListener(this);
    }

    /**
     * 非递归统计一个View的子View数(包含自身)
     *
     * @param root
     * @return
     */
    public int count(View root) {
        int viewCount = 0;

        if (null == root) {
            return 0;
        }

        if (root instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) root;
            LinkedList<ViewGroup> queue = new LinkedList<ViewGroup>();
            queue.add(viewGroup);
            while (!queue.isEmpty()) {
                ViewGroup current = queue.removeFirst();
                viewCount++;
                for (int i = 0; i < current.getChildCount(); i++) {
                    if (current.getChildAt(i) instanceof ViewGroup) {
                        queue.addLast((ViewGroup) current.getChildAt(i));
                    } else {
                        viewCount++;
                    }
                }
            }
        } else {
            viewCount++;
        }

        return viewCount;
    }

    /**
     * 递归统计一个View的子View数(包含自身)
     *
     * @param root
     * @return
     */
    public int count1(View root) {
        int viewCount = 0;

        if (null == root) {
            return 0;
        }

        if (root instanceof ViewGroup) {
            viewCount++;
            for (int i = 0; i < ((ViewGroup) root).getChildCount(); i++) {
                View view = ((ViewGroup) root).getChildAt(i);
                if (view instanceof ViewGroup) {
                    viewCount += count1(view);
                } else {
                    viewCount++;
                }
            }
        }

        return viewCount;
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
        } else if (viewId == R.id.test_sub_thread_tv) {
            Intent intent = new Intent(MainActivity.this, SubThreadActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.test_circle_view) {
            Intent intent = new Intent(MainActivity.this, CircleViewActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.test_request_net_view) {
            Intent intent = new Intent(MainActivity.this, OkHttpActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.test_service_8_view) {
            Intent intent = new Intent(MainActivity.this, TestServiceActivity.class);
            startActivity(intent);
        } else if (viewId == R.id.test_binder_view) {
            Intent intent = new Intent(MainActivity.this, BookManagerActivity.class);
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
