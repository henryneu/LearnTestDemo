package com.bkjk.infra.test.hotfix;

import android.content.Context;
import android.widget.Toast;

/**
 * Author: zhouzhenhua
 * Date: 2018/11/9
 * Version: 1.0.0
 * Description:
 */
public class SampleHotFixedBugTest {
    public void addBug(Context context) {
        int i = 10;
        int j = 0;
        Toast.makeText(context, "Hello, The calculation results is: " + (i / j), Toast.LENGTH_SHORT).show();
    }
}
