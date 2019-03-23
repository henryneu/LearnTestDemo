package com.bkjk.infra.test.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Author: zhouzhenhua
 * Date: 2019/3/20
 * Version: 1.0.0
 * Description:
 */
public class CustomButton extends Button {

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println(this.getText() + "--->dispatch中收到--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println(this.getText() + "--->dispatch中收到--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println(this.getText() + "--->dispatch中收到--->ACTION_CANCEL");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println(this.getText() + "--->dispatch中收到--->ACTION_UP");
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println(this.getText() + "--->onTouchEvent收到--->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println(this.getText() + "--->onTouchEvent中收到--->ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println(this.getText() + "--->onTouchEvent中收到--->ACTION_CANCEL");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println(this.getText() + "--->onTouchEvent中收到--->ACTION_UP");
                break;
        }

        return super.onTouchEvent(event);
    }
}
