package com.bkjk.infra.test.webank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bkjk.infra.test.R;
import com.webank.mdl_sdk.MDLIndexActivity;
import com.webank.mdl_sdk.constants.MDLContants;

public class WeBankActivity extends AppCompatActivity implements View.OnClickListener {

    // 微众银行开户和转账请求码
    private static final int REQUEST_CODE_OPEN_ACCOUNT = 0x07;
    private static final int REQUEST_CODE_TRANSFER = 0x08;

    private TextView mWeBankOpenTv;
    private TextView mWeBankTransTv;
    private TextView mWeBankPreviewTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_bank);
        initView();
    }

    private void initView() {
        mWeBankOpenTv = (TextView) findViewById(R.id.test_open_count_tv);
        mWeBankTransTv = (TextView) findViewById(R.id.test_transfer_tv);
        mWeBankPreviewTv = (TextView) findViewById(R.id.test_preview_tv);
        mWeBankOpenTv.setOnClickListener(this);
        mWeBankTransTv.setOnClickListener(this);
        mWeBankPreviewTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.test_open_count_tv) {
            Intent intent = new Intent(WeBankActivity.this, MDLIndexActivity.class);
            // 场景，必填，开户场景为 "open_account"
            intent.putExtra(MDLContants.SCENE, "open_account");
            // 渠道，必填，⻉壳为 "BK"
            intent.putExtra(MDLContants.CHANNEL_TYPE, "BK");
            // 应用 ID，必填，⻉壳为 "wb8sds2maggnnvtbtk"
            intent.putExtra(MDLContants.APP_ID, "wb8sds2maggnnvtbtk");
            // 用户 ID，必填-注册手机号
            // String phone = BZConfigStore.getInstance(AppWebViewActivity.this).mUserBean.phone;
            intent.putExtra(MDLContants.OPEN_ID, "13940378390");
            // 凭证(用于换取token)，必填
            intent.putExtra(MDLContants.AUTH_VALUE, "fasdf");
            // 微众订单号，必填
            intent.putExtra(MDLContants.MER_ORDER_NO, "3123");
            // 平台订单号，必填
            intent.putExtra(MDLContants.NBS_ORDER_NO, "423");
            // 产品结构编号，必填
            intent.putExtra(MDLContants.PS_CODE, "2342");
            // 身份证姓名，必填
            intent.putExtra(MDLContants.NAME, "周振华");
            // 身份证号码，必填
            intent.putExtra(MDLContants.ID_NO, "412827199204253572");
            startActivityForResult(intent, REQUEST_CODE_OPEN_ACCOUNT);
        } else if (viewId == R.id.test_transfer_tv) {
            Intent intent = new Intent(WeBankActivity.this, MDLIndexActivity.class);
            // 场景，必填，转账场景为 "transfer"
            intent.putExtra(MDLContants.SCENE, "transfer");
            // 渠道，必填，⻉壳为 "BK"
            intent.putExtra(MDLContants.CHANNEL_TYPE, "BK");
            // 应用 ID，必填，⻉壳为 "wb8sds2maggnnvtbtk"
            intent.putExtra(MDLContants.APP_ID, "wb8sds2maggnnvtbtk");
            // 用户 ID，必填-注册手机号
            // String phone = BZConfigStore.getInstance(AppWebViewActivity.this).mUserBean.phone;
            intent.putExtra(MDLContants.OPEN_ID, "13940378390");
            // 凭证(用于换取token)，必填
            intent.putExtra(MDLContants.AUTH_VALUE, "fasdf");
            // 微众订单号，必填
            intent.putExtra(MDLContants.MER_ORDER_NO, "3123");
            // 平台订单号，必填
            intent.putExtra(MDLContants.NBS_ORDER_NO, "423");
            // 产品结构编号，必填
            intent.putExtra(MDLContants.PS_CODE, "2342");
            // 身份证姓名，必填
            intent.putExtra(MDLContants.NAME, "周振华");
            // 身份证号码，必填
            intent.putExtra(MDLContants.ID_NO, "412827199204253572");
            // 借据号，必填
            intent.putExtra(MDLContants.LOAN_RECEIPT_NBR, "31242");
            // 合作方交易流水号，可选，如有则填写
            intent.putExtra(MDLContants.MER_BIZ_NO, "43241");
            startActivityForResult(intent, REQUEST_CODE_TRANSFER);
        } else if (viewId == R.id.test_preview_tv) {
            Intent intent = new Intent(WeBankActivity.this, WeBankPreviewActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_ACCOUNT) {
            // 处理微众开户返回结果
            if (resultCode == RESULT_OK) {
                // 获取 SDK 返回结果
                String errCode = data.getStringExtra("errCode");
                String errMsg = data.getStringExtra("errMsg");
                String jsonData = data.getStringExtra("data");

            }
        } else if (requestCode == REQUEST_CODE_TRANSFER) {
            // 处理微众转账返回结果

        }
    }
}
