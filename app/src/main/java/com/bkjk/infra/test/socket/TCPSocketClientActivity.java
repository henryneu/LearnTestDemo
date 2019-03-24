package com.bkjk.infra.test.socket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bkjk.infra.test.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TCPSocketClientActivity extends AppCompatActivity {

    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    @BindView(R.id.test_socket_msg_tv)
    TextView mSocketMsgTv;
    @BindView(R.id.test_send_msg_et)
    EditText mSocketMsgEt;
    @BindView(R.id.test_send_msg_bt)
    Button mSendMsgBt;

    private PrintWriter mPrintWriter;
    private Socket mClientSocket;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG:
                    mSocketMsgTv.setText(mSocketMsgTv.getText() + (String) msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    mSendMsgBt.setEnabled(true);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpsocket_client);
        ButterKnife.bind(this);
        // 启动服务端
        connectSocketService();
        // Socket连接
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                connectTCPService();
            }
        }).start();
    }

    @OnClick(R.id.test_send_msg_bt)
    public void onViewClicked() {
        final String sendMsg = mSocketMsgEt.getText().toString();
        if (!TextUtils.isEmpty(sendMsg) && mPrintWriter != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mPrintWriter.println(sendMsg);
                }
            }).start();
            mSocketMsgEt.setText("");
            String time = formatDateTime(System.currentTimeMillis());
            final String showedMsg = "client " + time + ":" + sendMsg + "\n";
            mSocketMsgTv.setText(mSocketMsgTv.getText() + showedMsg);
        }
    }

    private void connectSocketService() {
        Intent intent = new Intent(TCPSocketClientActivity.this, TCPSocketClientService.class);
        startService(intent);
    }

    private void connectTCPService() {
        Socket socket = null;
        try {
            while (socket == null) {
                socket = new Socket("127.0.0.1", 8088);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                System.out.println("connected server success");
            }
        } catch (IOException e) {
            SystemClock.sleep(1000);
            System.out.println("connect tcp server failed, retry...");
            e.printStackTrace();
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!TCPSocketClientActivity.this.isFinishing()) {
                String msg = br.readLine();
                System.out.println("receive :" + msg);
                if (!TextUtils.isEmpty(msg)) {
                    String time = formatDateTime(System.currentTimeMillis());
                    final String showedMsg = "server " + time + ":" + msg + "\n";
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showedMsg).sendToTarget();
                }
            }
            System.out.println("quit...");
            mPrintWriter.flush();
            mPrintWriter.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    @SuppressLint("SimpleDateFormat")
    private String formatDateTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }
}
