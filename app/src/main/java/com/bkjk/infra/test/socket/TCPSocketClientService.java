package com.bkjk.infra.test.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import androidx.annotation.Nullable;

/**
 * Author: zhouzhenhua
 * Date: 2019/3/24
 * Version: 1.0.0
 * Description:
 */
public class TCPSocketClientService extends Service {

    private boolean mIsServiceDestoryed = false;
    private String[] mDefinedMessages = new String[] {
            "你好啊，哈哈",
            "请问你叫什么名字呀？",
            "今天北京天气不错啊，shy",
            "你知道吗？我可是可以和多个人同时聊天的哦",
            "给你讲个笑话吧：据说爱笑的人运气不会太差，不知道真假。"
    };

    @Override
    public void onCreate() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();
        super.onCreate();
    }

    private void doWork() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8088);
            while (!mIsServiceDestoryed) {
                Socket socket = serverSocket.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                printWriter.println("欢迎来到聊天室！");

                while (!mIsServiceDestoryed) {
                    String str = br.readLine();
                    System.out.println("msg from client:" + str);
                    if (str == null) {
                        break;
                    }
                    int index = new Random().nextInt(mDefinedMessages.length);
                    String msg = mDefinedMessages[index];
                    printWriter.println(msg);
                    System.out.println("send :" + msg);
                }
                printWriter.flush();
                printWriter.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed = true;
        super.onDestroy();
    }
}
