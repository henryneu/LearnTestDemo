package com.bkjk.infra.test.webank.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author: zhouzhenhua
 * Date: 2019/2/20
 * Version: 1.0.0
 * Description:
 */
//public class OkHttpUtils {
//    public static OkHttpClient mOkHttpClient;
//    public static OkHttpUtils mOkHttpUtils;
//    private OkHttpCallback mCallback;
//
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 1://异常
//                    IOException e = (IOException) msg.obj;
//                    mCallback.onError(e);
//                    break;
//                case 2://成功
//                    String result = (String) msg.obj;
//                    mCallback.onResponse(result);
//                    break;
//            }
//        }
//    };
//
//    /**
//     * http 请求工具
//     *
//     * @return
//     */
//    public static OkHttpUtils build() {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        mOkHttpClient = builder.build();
//        mOkHttpUtils = new OkHttpUtils();
//        return mOkHttpUtils;
//    }
//
//    /**
//     * 设置回调方法
//     *
//     * @param callback
//     * @return
//     */
//    public OkHttpUtils setCallback(OkHttpCallback callback) {
//        mCallback = callback;
//        return mOkHttpUtils;
//    }
//
//    /**
//     * post 请求
//     *
//     * @param url
//     * @param params
//     * @return
//     */
//    public OkHttpUtils postOkHttp(String url, Map<String, Object> params) {
//        FormBody.Builder builder = new FormBody.Builder();
//        String temp = "";
//        for (String key : params.keySet()) {
//            builder.add(key, String.valueOf(params.get(key)));
//            temp += (key + "=" + String.valueOf(params.get(key)) + "&");
//        }
//        FormBody formBody = builder.build();
//        final Request request = new Request.Builder()
//                .url(url)
//                .post(formBody)
//                .build();
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Message msg = Message.obtain();
//                msg.what = 1;
//                msg.obj = e;
//                handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String result = response.body().string();
//                Message msg = Message.obtain();
//                msg.what = 2;
//                msg.obj = result;
//                handler.sendMessage(msg);
//            }
//        });
//        return mOkHttpUtils;
//    }
//
//    /**
//     * post 异步请求
//     *
//     * @param url
//     * @param params
//     * @return
//     */
//    public OkHttpUtils postAsync(String url, Map<String, Object> params) {
//        RequestBody requestBody;
//        if (params == null) {
//            params = new HashMap<>();
//        }
//        /**
//         * OKhttp3.0之后版本
//         */
//        FormBody.Builder builder = new FormBody.Builder();
//        /**
//         * 在这对添加的参数进行遍历，map遍历有四种方式，如果想要了解的可以网上查找
//         */
//        String temp = "";
//        for (Map.Entry<String, Object> map : params.entrySet()) {
//            String key = map.getKey();
//            Object value;
//            value = map.getValue() == null ? "" : map.getValue();
//            builder.add(key, String.valueOf(value));
//            temp += (key + "=" + String.valueOf(value));
//        }
//        requestBody = builder.build();
//        final Request request = new Request.Builder().url(url).post(requestBody).build();
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Message msg = Message.obtain();
//                msg.what = 1;
//                msg.obj = e;
//                handler.sendMessage(msg);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String result = response.body().string();
//                Message msg = Message.obtain();
//                msg.what = 2;
//                msg.obj = result;
//                handler.sendMessage(msg);
//            }
//        });
//        return mOkHttpUtils;
//    }
//
//    /**
//     * 判断是否有网络连接
//     */
//    private boolean isNetworkConnected(@NonNull Context context) {
//        ConnectivityManager mConnectivityManager =
//                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
//        return mNetworkInfo != null && mNetworkInfo.isAvailable();
//    }
//
//    /**
//     * 下载
//     *
//     * @param url
//     * @param saveDir
//     * @param listener
//     */
//    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        mOkHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                // 下载失败
//                listener.onDownloadFailed(e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                InputStream is = null;
//                byte[] buf = new byte[2048];
//                int len = 0;
//                FileOutputStream fos = null;
//                // 储存下载文件的目录
//                String savePath = isExitDir(saveDir);
//                try {
//                    is = response.body().byteStream();
//                    long total = response.body().contentLength();
//                    File file = new File(savePath, getNameFromUrl(url));
//                    fos = new FileOutputStream(file);
//                    long sum = 0;
//                    while ((len = is.read(buf)) != -1) {
//                        fos.write(buf, 0, len);
//                        sum += len;
//                        int progress = (int) (sum * 1.0f / total * 100);
//                        listener.onDownloading(progress);
//                    }
//                    fos.flush();
//                    listener.onDownloadSuccess(file);
//                } catch (Exception e) {
//                    listener.onDownloadFailed(e);
//                } finally {
//                    try {
//                        if (is != null) {
//                            is.close();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        if (fos != null) {
//                            fos.close();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
//
//    /**
//     * 判断下载目录是否存在
//     *
//     * @param saveDir
//     * @return
//     */
//    private String isExitDir(String saveDir) throws IOException {
//        // 下载位置
//        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
//        if (!downloadFile.mkdir()) {
//            downloadFile.createNewFile();
//        }
//        return downloadFile.getAbsolutePath();
//    }
//
//    /**
//     * 从下载链接中解析出文件名
//     *
//     * @param url
//     * @return
//     */
//    private String getNameFromUrl(String url) {
//        return url.substring(url.lastIndexOf("/") + 1);
//    }
//
//    /**
//     * 请求回调接口
//     */
//    public interface OkHttpCallback {
//        void onError(Exception e);
//
//        void onResponse(String result);
//    }
//
//    /**
//     * 下载监听接口
//     */
//    public interface OnDownloadListener {
//        /**
//         * 下载成功
//         */
//        void onDownloadSuccess(File file);
//
//        /**
//         * @param progress 下载进度
//         */
//        void onDownloading(int progress);
//
//        /**
//         * 下载失败
//         */
//        void onDownloadFailed(Exception e);
//    }
//}

public class OkHttpUtils {

    private static final String TAG = "OkHttpUtils";

    private static OkHttpClient client;
    private static OkHttpUtils okHttpUtils;
    private OkHttpCallback callback;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://异常
                    IOException e = (IOException) msg.obj;
                    callback.onError(e);
                    break;
                case 2://成功
                    String result = (String) msg.obj;
                    callback.onResponse(result);
                    break;
            }
        }
    };

    /**
     * http请求
     */
    public static OkHttpUtils build() {
        OkHttpClient.Builder sBuilder = new OkHttpClient.Builder();
        client = sBuilder.build();
        okHttpUtils = new OkHttpUtils();
        return okHttpUtils;
    }

    //设置回调方法
    public OkHttpUtils setCallback(OkHttpCallback callback) {
        this.callback = callback;
        return okHttpUtils;
    }

    //post请求
    public OkHttpUtils postOkHttp(String url, Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        String temp = "";
        for (String key : params.keySet()) {
            builder.add(key, String.valueOf(params.get(key)));
            temp += (key + "=" + String.valueOf(params.get(key)) + "&");
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = e;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Message msg = Message.obtain();
                msg.what = 2;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        });
        return okHttpUtils;
    }

    public OkHttpUtils postAsync(String url, Map<String, Object> params) {
        RequestBody requestBody;
        if (params == null) {
            params = new HashMap<>();
        }
        /**
         * OKhttp3.0之后版本
         */
        FormBody.Builder builder = new FormBody.Builder();
        /**
         * 在这对添加的参数进行遍历，map遍历有四种方式，如果想要了解的可以网上查找
         */
        String temp = "";
        for (Map.Entry<String, Object> map : params.entrySet()) {
            String key = map.getKey();
            Object value;
            value = map.getValue() == null ? "" : map.getValue();
            builder.add(key, String.valueOf(value));
            temp += (key + "=" + String.valueOf(value));
        }
        requestBody = builder.build();
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = e;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Message msg = Message.obtain();
                msg.what = 2;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        });
        return okHttpUtils;
    }

    //请求回调接口
    public interface OkHttpCallback {
        void onError(Exception e);
        void onResponse(String result);
    }

    /**
     * 判断是否有网络连接
     */
    private boolean isNetworkConnected(@NonNull Context context) {
        ConnectivityManager mConnectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    /**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = isExistDir(saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath, getNameFromUrl(url));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100); // 下载中
                        listener.onDownloading(progress);
                    }
                    fos.flush(); // 下载完成
                    listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    listener.onDownloadFailed();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    /**
     * 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        Log.e(TAG,"savePath:" + savePath);
        return savePath;
    }

    /**
     * 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url) {
        String tempName = url.substring(url.lastIndexOf("/") + 1);
        String fileName = tempName.substring(0, 12);
        return fileName;
        //return url.substring(url.lastIndexOf("/") + 1);
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess(File file);
        /**
         * 下载进度
         */
        void onDownloading(int progress);
        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

    /**
     * 删除存储的PDF文件
     *
     * @param context
     * @param pathList
     */
    public static void deletePDFFile(Context context, List<String> pathList) {

        ContentResolver resolver = context.getContentResolver();

        for (String imgPath : pathList) {
            Cursor cursor = MediaStore.Images.Media.query(resolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=?",
                    new String[]{imgPath}, null);
            if (cursor.moveToFirst()) {
                long id = cursor.getLong(0);
                Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                Uri uri = ContentUris.withAppendedId(contentUri, id);
                context.getContentResolver().delete(uri, null, null);
            } else {
                File file = new File(imgPath);
                file.delete();
            }
        }
    }
}
