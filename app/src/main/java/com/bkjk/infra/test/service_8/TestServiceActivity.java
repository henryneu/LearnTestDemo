package com.bkjk.infra.test.service_8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bkjk.infra.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestServiceActivity extends AppCompatActivity {

    @BindView(R.id.test_service_8_tv)
    TextView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.test_service_8_tv)
    public void onViewClicked() {
        Intent intent = new Intent(TestServiceActivity.this, Mine2Service.class);
        //startForegroundService(intent);
        startService(intent);
    }
}
