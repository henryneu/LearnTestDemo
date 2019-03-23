package com.bkjk.infra.test.binder;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bkjk.infra.test.Book;
import com.bkjk.infra.test.IBookManager;
import com.bkjk.infra.test.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author: zhouzhenhua
 * Date: 2019/3/23
 * Version: 1.0.0
 * Description:
 */
public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mBookArrivedListeners = new RemoteCallbackList<IOnNewBookArrivedListener>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            SystemClock.sleep(5000);
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mBookArrivedListeners.register(listener);

            final int N = mBookArrivedListeners.beginBroadcast();
            mBookArrivedListeners.finishBroadcast();
            Log.d(TAG, "registerListener, current size:" + N);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            boolean success = mBookArrivedListeners.unregister(listener);

            if (success) {
                Log.d(TAG, "unregister success.");
            } else {
                Log.d(TAG, "not found, can not unregister.");
            }
            final int N = mBookArrivedListeners.beginBroadcast();
            mBookArrivedListeners.finishBroadcast();
            Log.d(TAG, "unregisterListener, current size:" + N);
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {

            int check = checkCallingOrSelfPermission("com.bkjk.infra.test.permission.ACCESS_BOOK_MANAGER");
            if (check == PackageManager.PERMISSION_DENIED) {
                Log.d(TAG, "permission denied!");
                return false;
            }

            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            if (!packageName.startsWith("com.bkjk")) {
                Log.d(TAG, "permission denied!");
                return false;
            }

            Log.d(TAG, "permission granted!");

            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android开发艺术探索"));
        mBookList.add(new Book(2, "第一行代码"));
        new Thread(new ServiceWorker()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("com.bkjk.infra.test.permission.ACCESS_BOOK_MANAGER");
        if (check == PackageManager.PERMISSION_DENIED) {
            Log.d(TAG, "permission denied!");
            return null;
        }
        Log.d(TAG, "permission granted!");
        return mBinder;
    }

    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size();
                Book newBook = new Book(bookId, "newBook#" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        final int N = mBookArrivedListeners.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener listener = mBookArrivedListeners.getBroadcastItem(i);
            Log.d(TAG, "onNewBookArrived, notify listener:" + listener);
            if (listener != null) {
                listener.onNewBookArrived(book);
            }
        }
        mBookArrivedListeners.finishBroadcast();
    }
}
