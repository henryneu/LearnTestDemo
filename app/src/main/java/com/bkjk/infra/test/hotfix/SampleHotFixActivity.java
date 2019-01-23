package com.bkjk.infra.test.hotfix;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bkjk.infra.test.R;

public class SampleHotFixActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SampleHotFixActivity";

    private TextView mFixedBugTv;
    private Button mFixedBugBtn;
    private Button mAddBugBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_hot_fix);
        mFixedBugTv = (TextView) findViewById(R.id.fixed_bug_tv);
        mFixedBugBtn = (Button) findViewById(R.id.fixed_bug_btn);
        mAddBugBtn = (Button) findViewById(R.id.add_bug_btn);

        mFixedBugBtn.setOnClickListener(this);
        mAddBugBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fixed_bug_btn) {
            fixedBug(v);
        } else if (v.getId() == R.id.add_bug_btn) {
            addBug(v);
        }
    }

    private void addBug(View v) {
        SampleHotFixedBugTest test = new SampleHotFixedBugTest();
        test.addBug(this);
    }

    private void fixedBug(View v) {
        Log.e(TAG, "DataDirectory: " + Environment.getDataDirectory());
        FixedDexUtils.loadFixedDex(this, Environment.getDataDirectory());
    }
}
