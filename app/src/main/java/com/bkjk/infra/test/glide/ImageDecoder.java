package com.bkjk.infra.test.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Author: zhouzhenhua
 * Date: 2018/10/25
 * Version: 1.0.0
 * Description:
 */
public class ImageDecoder {

    private volatile static ImageDecoder sInstance;

    /**
     * 单例返回
     *
     * @param context
     * @return
     */
    public static ImageDecoder getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ImageDecoder.class) {
                if (sInstance == null) {
                    sInstance = new ImageDecoder();
                }
            }
        }
        return sInstance;
    }

    private ImageDecoder() {
    }

    /**
     * 根据资源和资源 ID 解析出 Bitmap
     *
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return Bitmap
     */
    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用下面定义的方法计算 inSampleSize 值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        // 使用获取到的inSampleSize值再次解析图片
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 计算得出符合要求的 inSampleSize
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return inSampleSize
     */
    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int width = options.outWidth;
        final int height = options.outHeight;

        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            // 计算出实际宽高和目标宽高的比率
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            // 选择宽和高中最小的比率作为 inSampleSize 的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize =  widthRatio > heightRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
