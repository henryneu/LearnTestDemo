package com.bkjk.infra.test.webank;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.bkjk.infra.test.R;
import com.bkjk.infra.test.webank.utils.OkHttpUtils;
import com.bkjk.infra.test.webank.utils.ProtocolDownloadUtil;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class WeBankPreviewActivity extends AppCompatActivity {

    private static final String url = "http://bkjk-private-dev-1256212241.cos.ap-beijing.myqcloud.com/lego-storage/infra/lego-esign/6UPcYfJP.pdf?sign=q-sign-algorithm%3Dsha1%26q-ak%3DAKID96oFSPHVeXAWnsJJgneEYgY9UZOwBG4c%26q-sign-time%3D1550811504%3B1550812104%26q-key-time%3D1550811504%3B1550812104%26q-header-list%3D%26q-url-param-list%3Dresponse-cache-control%3Bresponse-content-disposition%3Bresponse-content-language%3Bresponse-content-type%3Bresponse-expires%26q-signature%3De0eb5fb920de408e0dd636721e845cc00cda54c8&response-cache-control=no-cache&response-content-disposition=inline%3Bfilename%2A%3Dutf-8%27zh_cn%275735745d-ad07-41cb-b591-9642c0d0a9be.pdf&response-content-language=zh-CN&response-expires=Sat%2C%2023%20Feb%202019%2004%3A58%3A24%20GMT&response-content-type=application%2Fpdf";
    //private static final String url = "https://web.stanford.edu/~xgzhou/zhou_book2017.pdf";
    private String mSaveDir;

    private PDFView mPDFView;

    private List<String> mPDFPathList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_bank_preview);
        mPDFView = (PDFView) findViewById(R.id.test_protocol_preview_pdfview);

//        mPDFView.fromAsset("test.pdf")
//                .swipeHorizontal(false)
//                .spacing(0)
//                .enableAnnotationRendering(true)
//                .load();

//        mPDFView.fromUri(Uri.parse(url))
//                .swipeHorizontal(false)
//                .spacing(0)
//                .enableAnnotationRendering(true)
//                .load();

        mSaveDir = getExternalCacheDir().getAbsolutePath() + "/pdf/";

        OkHttpUtils.build().download(url, "download", new OkHttpUtils.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                showProtocol(file.getPath());
                mPDFPathList.add(file.getPath());
            }

            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadFailed() {

            }
        });

//        ProtocolDownloadUtil.get().download(url, "download", new ProtocolDownloadUtil.OnDownloadListener() {
//            @Override
//            public void onDownloadSuccess(File file) {
//                mPDFView.fromFile(file)
//                        .swipeHorizontal(false)
//                        .spacing(0)
//                        .enableAnnotationRendering(true)
//                        .load();
//            }
//
//            @Override
//            public void onDownloading(int progress) {
//
//            }
//
//            @Override
//            public void onDownloadFailed(Exception e) {
//
//            }
//        });
    }

    /**
     * 展示下载协议
     *
     * @param fileName
     */
    private void showProtocol(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        } else {
            mPDFView.fromFile(new File(fileName))
                    .defaultPage(0)
                    .enableAnnotationRendering(true)
                    .enableSwipe(true)
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            float pageWidth = mPDFView.getOptimalPageWidth();
                            float viewWidth = mPDFView.getWidth();
                            mPDFView.zoomTo(viewWidth/pageWidth);
                            mPDFView.loadPages();
                        }
                    })
                    .load();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        OkHttpUtils.deletePDFFile(this, mPDFPathList);
    }
}
