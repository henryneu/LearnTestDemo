package com.bkjk.infra.test.timeline;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bkjk.infra.test.R;

/**
 * Author: zhouzhenhua
 * Date: 2019/3/26
 * Version: 1.0.0
 * Description:
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    // 内容的画笔
    private Paint mPaint;
    // 时间、日期的画笔
    private Paint mTimePaint;
    private Paint mDatePaint;

    // 左 上偏移长度
    private int itemView_leftinterval;
    private int itemView_topinterval;

    // 轴点半径
    private int mCircleRadius;

    // 图标
    private Bitmap mIcon;

    public DividerItemDecoration(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);        // 轴点画笔
        mTimePaint = new Paint();
        mTimePaint.setColor(Color.BLUE);
        mTimePaint.setTextSize(30);        // 时间画笔
        mDatePaint = new Paint();
        mDatePaint.setColor(Color.BLUE);   // 日期画笔

        // 赋值ItemView的左偏移长度为200
        itemView_leftinterval = 240;

        // 赋值ItemView的上偏移长度为50
        itemView_topinterval = 50;

        // 赋值轴点圆的半径为10
        mCircleRadius = 10;

        mIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.truck);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // 设置ItemView的左&上偏移长度分别为200px & 50px，即此时onDraw()可绘制的区域
        outRect.set(itemView_leftinterval, itemView_topinterval, 0, 0);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        // 获取RecyclerView的Child view的个数
        int childCount = parent.getChildCount();

        // 遍历每个Item，分别获取它们的位置信息，然后再绘制对应的分割线
        for (int i = 0; i < childCount; i++) {

            // 获取每个Item对象
            View child = parent.getChildAt(i);

            /**
             * 绘制轴点
             */
            // 轴点 = 圆 = 圆心(x,y)
            float centerx = child.getLeft() - (itemView_leftinterval * 3) / 8;
            float centery = child.getTop() - itemView_topinterval + (itemView_topinterval + child.getHeight()) / 2;
            // 绘制轴点圆
            // c.drawCircle(centerx, centery, mCircleRadius, mPaint);
            // 通过Canvas绘制角标
            c.drawBitmap(mIcon,centerx - mCircleRadius ,centery - mCircleRadius,mPaint);

            /**
             * 绘制上半轴线
             */
            // 上端点坐标(x,y)
            float upLine_up_x = centerx;
            float upLine_up_y = child.getTop() - itemView_topinterval;

            // 下端点坐标(x,y)
            float upLine_bottom_x = centerx;
            float upLine_bottom_y = centery - mCircleRadius;

            //绘制上半部轴线
            c.drawLine(upLine_up_x, upLine_up_y, upLine_bottom_x, upLine_bottom_y, mPaint);

            /**
             * 绘制下半轴线
             */
            // 上端点坐标(x,y)
            float bottomLine_up_x = centerx;
            float bottom_up_y = centery + mCircleRadius;

            // 下端点坐标(x,y)
            float bottomLine_bottom_x = centerx;
            float bottomLine_bottom_y = child.getBottom();

            //绘制下半部轴线
            c.drawLine(bottomLine_up_x, bottom_up_y, bottomLine_bottom_x, bottomLine_bottom_y, mPaint);


            /**
             * 绘制左边时间
             */
            // 获取每个Item的位置
            int index = parent.getChildAdapterPosition(child);
            // 设置文本起始坐标
            float Text_x = child.getLeft() - itemView_leftinterval * 5 / 6;
            float Text_y = upLine_bottom_y;

            // 根据Item位置设置时间文本
            switch (index) {
                case (0):
                    // 设置日期绘制位置
                    c.drawText("13:40", Text_x, Text_y, mTimePaint);
                    c.drawText("2019.3.03", Text_x + 5, Text_y + 20, mDatePaint);
                    break;
                case (1):
                    // 设置日期绘制位置
                    c.drawText("17:33", Text_x, Text_y, mTimePaint);
                    c.drawText("2019.3.03", Text_x + 5, Text_y + 20, mDatePaint);
                    break;
                case (2):
                    // 设置日期绘制位置
                    c.drawText("20:13", Text_x, Text_y, mTimePaint);
                    c.drawText("2019.3.03", Text_x + 5, Text_y + 20, mDatePaint);
                    break;
                case (3):
                    // 设置日期绘制位置
                    c.drawText("11:40", Text_x, Text_y, mTimePaint);
                    c.drawText("2019.3.04", Text_x + 5, Text_y + 20, mDatePaint);
                    break;
                case (4):
                    // 设置日期绘制位置
                    c.drawText("13:20", Text_x, Text_y, mTimePaint);
                    c.drawText("2019.3.04", Text_x + 5, Text_y + 20, mDatePaint);
                    break;
                case (5):
                    // 设置日期绘制位置
                    c.drawText("22:40", Text_x, Text_y, mTimePaint);
                    c.drawText("2019.3.04", Text_x + 5, Text_y + 20, mDatePaint);
                    break;
                default:
                    c.drawText("已签收", Text_x, Text_y, mTimePaint);
            }
        }
    }
}
