package com.bkjk.infra.test.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Author: zhouzhenhua
 * Date: 2019/3/21
 * Version: 1.0.0
 * Description:
 */
public class CustomRelativeLayout extends RelativeLayout {
    public CustomRelativeLayout(Context context) {
        super(context);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("--->dispatch中收到--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("--->dispatch中收到--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("--->dispatch中收到--->ACTION_CANCEL");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("--->dispatch中收到--->ACTION_UP");
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("--->onInterceptTouchEvent中收到--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("--->onInterceptTouchEvent中收到--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("--->onInterceptTouchEvent中收到--->ACTION_CANCEL");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("--->onInterceptTouchEvent中收到--->ACTION_UP");
                break;
        }

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("--->onTouchEvent中收到--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("--->onTouchEvent中收到--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("--->onTouchEvent中收到--->ACTION_CANCEL");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("--->onTouchEvent中收到--->ACTION_UP");
                break;
        }

        return super.onTouchEvent(event);
    }
}
