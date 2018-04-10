package com.fanwe.catchdoll.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.event.WWERechargeResult;
import com.fanwe.catchdoll.model.WWPaymentDetailActModel;
import com.fanwe.catchdoll.model.WWPaymentDetailModel;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;

import de.greenrobot.event.EventBus;

/**
 * 充值结果界面
 */
public class WWRechargeResultActivity extends BaseTitleActivity
{
    /**
     * 充值订单id（String）
     */
    public static final String EXTRA_ID = "extra_id";
    private String pay_id;

    private ImageView iv_payment_logo;
    private TextView tv_payment_status;
    private TextView tv_payment_money;
    private TextView tv_goods_name;
    private TextView tv_payment_type;
    private TextView tv_payment_time;
    private View tv_complete;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_recharge_result);
        mTitle.setMiddleTextTop("交易详情");
        initView();
        initIntent();
        requestData();
    }

    private void initView()
    {
        iv_payment_logo = (ImageView) findViewById(R.id.iv_payment_logo);
        tv_payment_status = (TextView) findViewById(R.id.tv_payment_status);
        tv_payment_money = (TextView) findViewById(R.id.tv_payment_money);
        tv_goods_name = (TextView) findViewById(R.id.tv_goods_name);
        tv_payment_type = (TextView) findViewById(R.id.tv_payment_type);
        tv_payment_time = (TextView) findViewById(R.id.tv_payment_time);
        tv_complete = findViewById(R.id.tv_complete);
        tv_complete.setOnClickListener(this);
    }

    private void initIntent()
    {
        pay_id = getIntent().getStringExtra(EXTRA_ID);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == tv_complete)
        {
            getActivity().finish();
        }
    }

    private void requestData()
    {
        WWCommonInterface.requestPayResult(pay_id, new AppRequestCallback<WWPaymentDetailActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    setDetailData(actModel.getData());
                }
            }
        });
    }

    private void setDetailData(WWPaymentDetailModel model)
    {
        if (model.getIs_paid() == 0)
        {
            SDViewBinder.setTextView(tv_payment_time, "---");
            SDViewBinder.setTextView(tv_payment_status, "支付失败");
            iv_payment_logo.setImageResource(R.drawable.ww_ic_recharge_fail);
        } else
        {
            SDViewBinder.setTextView(tv_payment_time, model.getPay_time());
            SDViewBinder.setTextView(tv_payment_status, "支付成功");
            iv_payment_logo.setImageResource(R.drawable.ww_ic_recharge_success);
        }
        SDViewBinder.setTextView(tv_payment_money, model.getMoney());
        SDViewBinder.setTextView(tv_payment_type, model.getPayment_type());
        SDViewBinder.setTextView(tv_goods_name, model.getRecharge_name());
        EventBus.getDefault().post(new WWERechargeResult(model.getBalance()));
    }
}
