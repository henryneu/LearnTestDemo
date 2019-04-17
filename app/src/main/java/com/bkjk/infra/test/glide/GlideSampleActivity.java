package com.bkjk.infra.test.glide;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bkjk.infra.test.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.LruCache;
//import com.bumptech.glide.request.RequestOptions;

public class GlideSampleActivity extends AppCompatActivity {

    private ImageView mLoadImageIv;

    private LruCache<String, Bitmap> mMemoryCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_sample);
        mLoadImageIv = (ImageView) findViewById(R.id.test_glide_img_iv);
        // mLoadImageIv.setImageBitmap(ImageDecoder.getInstance(this).decodeSampledBitmapFromResource(getResources(), R.drawable.error_icon, 300, 400));

        // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
        // LruCache通过构造函数传入缓存值，以KB为单位。
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 使用最大可用内存值的1/8作为缓存的大小。
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public void loadBitmap(int resId, ImageView imageView) {
        final String imageKey = String.valueOf(resId);
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.loading_icon);
            BitmapWorkerTask task = new BitmapWorkerTask();
            task.execute(resId);
        }
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        // 在后台加载图片。
        @Override
        protected Bitmap doInBackground(Integer... params) {
            final Bitmap bitmap = ImageDecoder.getInstance(GlideSampleActivity.this).decodeSampledBitmapFromResource(
                    getResources(), params[0], 100, 100);
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }
    }

    /**
     * 3.x 用法
     * @param view
     */
    public void loadImage(View view) {
        String url = "https://ww1.sinaimg.cn/large/0065oQSqgy1fu39hosiwoj30j60qyq96.jpg";
        String gifUrl = "http://p1.pstatp.com/large/166200019850062839d3";
        Glide.with(this)
                .load(gifUrl)
//                .asBitmap()
                .asGif()
                .placeholder(R.drawable.loading_icon)
                .error(R.drawable.error_icon)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(200, 300)
                .into(mLoadImageIv);
    }

//    /**
//     * 4.x 用法
//     * @param view
//     */
//    public void loadImage(View view) {
//        String url = "https://ww1.sinaimg.cn/large/0065oQSqgy1fu39hosiwoj30j60qyq96.jpg";
//        String gifUrl = "http://p1.pstatp.com/large/166200019850062839d3";
//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.placeholder(R.drawable.loading_icon);
//        requestOptions.error(R.drawable.error_icon);
//        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
//        Glide.with(this)
//                .load(gifUrl)
//                .apply(requestOptions)
//                .into(mLoadImageIv);
//    }
}