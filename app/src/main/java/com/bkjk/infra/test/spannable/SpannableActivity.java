package com.bkjk.infra.test.spannable;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bkjk.infra.test.R;

public class SpannableActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable);
        mTextView = (TextView) findViewById(R.id.spannable_test_string_tv);

        String str = new String("获取更多帮助，请拨打客服电话400-888-8888");

        // SpannableString 的用法、和 SpannableStringBuilder 很相似、下面主要以 SpannableStringBuilder 来介绍
        // SpannableString spannableString = new SpannableString(str);
        // ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#3072F6"));
        // spannableString.setSpan(colorSpan, 14, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // mTextView.setText(spannableString);

        // SpannableStringBuilder 用法
        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder(str);

        // 设置字体大小
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(40);
        // 相对于默认字体大小的倍数,这里是1.3倍
        // RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan((float) 1.3);
        spannableBuilder.setSpan(sizeSpan, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 单独设置字体颜色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#3072F6"));
        spannableBuilder.setSpan(colorSpan, 14, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 单独设置点击事件
        ClickableSpan clickableSpanOne = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SpannableActivity.this, "拨打电话", Toast.LENGTH_SHORT).show();
            }
        };
        spannableBuilder.setSpan(clickableSpanOne, 14, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置删除线
        StrikethroughSpan strikeSpan = new StrikethroughSpan();
        // 设置下划线
        UnderlineSpan underlineSpan = new UnderlineSpan();

        // 在设置点击事件、同时设置字体颜色
        ClickableSpan clickableSpanTwo = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SpannableActivity.this, "拨打电话", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint paint) {
                paint.setColor(Color.parseColor("#3072F6"));
                // 设置下划线 true显示、false不显示
                paint.setUnderlineText(false);
                // paint.setStrikeThruText(true);
            }
        };
        spannableBuilder.setSpan(clickableSpanTwo, 14, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 不设置点击不生效
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mTextView.setText(spannableBuilder);
        // 去掉点击后文字的背景色
        // mTextView.setHighlightColor(Color.parseColor("#00000000"));
    }
}