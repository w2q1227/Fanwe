package com.fanwe.catchdoll.activity;

import android.text.TextUtils;

import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.event.WWEReceiveDoll;
import com.fanwe.catchdoll.model.WWDollDetailModel;
import com.fanwe.catchdoll.model.WWPayPayModel;
import com.fanwe.catchdoll.model.WWUserDollDetailModel;
import com.fanwe.catchdoll.model.WWUserDollDispatchingDefaultModel;
import com.fanwe.hybrid.common.CommonOpenSDK;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.event.EWxPayResultCodeComplete;
import com.fanwe.live.model.PayItemModel;
import com.fanwe.live.view.pulltorefresh.IPullToRefreshViewWrapper;
//import com.fanwe.live.wxapi.WXPayEntryActivity;
import com.wawakuailai.www.wxapi.WXPayEntryActivity;
import com.fanwei.jubaosdk.shell.OnPayResultListener;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 提取详情
 * Created by LianCP on 2017/12/5.
 */
public class WWReceiveDollActivity extends WWBaseReceiveActivity
{

    @Override
    protected void requestDetail()
    {
        super.requestDetail();
        requestUserDollDetail();
    }


    private void requestUserDollDetail()
    {
        WWCommonInterface.requestUserDollDetail(mId, new AppRequestCallback<WWUserDollDetailModel>()
        {

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                onRefreshComplete();
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    WWDollDetailModel detailModel = actModel.getDetail();
                    if (detailModel == null)
                    {
                        return;
                    }

                    if (null != actModel.getDetail_similar() && actModel.getDetail_similar().size() > 0)
                    {
                        ww_select_doll_list.setListViewVisible(true);
                    } else
                    {
                        ww_select_doll_list.setListViewVisible(false);
                    }
                    ww_select_doll_list.setDatas(actModel.getDetail_similar(), String.valueOf(detailModel.getFreight_number()));

                    ww_doll_card.setData(detailModel.getImg(), detailModel.getDoll_name(), detailModel.getGrab_time());

                    int recharge_btn_show = detailModel.getRecharge_btn_show();
                    mRechargeBtnShow = recharge_btn_show;
                    List<PayItemModel> paylist = actModel.getPay_list();
                    if (recharge_btn_show == 1 && paylist != null && paylist.size() > 0)
                    {
                        ww_recharge_way.setData(actModel.getPay_list());
                        SDViewUtil.setVisible(ww_recharge_way);
                    } else
                    {
                        SDViewUtil.setGone(ww_recharge_way);
                    }

                    if (detailModel.getStatus() == 0)
                    {
                        WWUserDollDispatchingDefaultModel model = detailModel.getDispatching_default();
                        if (model != null)
                        {
                            if (TextUtils.isEmpty(model.getAddress_id()))
                            {
                                ww_address_card.setNoGetAddressVisible(false);
                            } else
                            {
                                mAddressID = model.getAddress_id();
                                ww_address_card.setNoGetAddressVisible(true);
                                ww_address_card.setDefaultAddress(model.getConsignee(), model.getMobile(), model.getAddress());
                            }
                        } else
                        {
                            ww_address_card.setNoGetAddressVisible(false);
                        }
                    }
                }
            }
        });
    }


    protected void requestPay(String ids, String totalFreight)
    {
        WWCommonInterface.requestPayPay(ids, mPayId, mAddressID, totalFreight, new AppRequestCallback<WWPayPayModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    if (actModel.getNeed_pay() == 1)
                    {
                        CommonOpenSDK.dealPayRequestSuccess(actModel.getPay(), getActivity(), payResultListner, jbfPayResultListener);
                    } else
                    {
                        finish();
                        refreshMainMyDollList();
                    }
                }
            }
        });
    }

    protected void onPaySuccess()
    {
        finish();
        refreshMainMyDollList();
    }


    /*微信支付回调返回信息*/
    public void onEventMainThread(final EWxPayResultCodeComplete event)
    {
        switch (event.WxPayResultCode)
        {
            case WXPayEntryActivity.RespErrCode.CODE_CANCEL:
                payResultListner.onCancel();
                break;
            case WXPayEntryActivity.RespErrCode.CODE_FAIL:
                payResultListner.onFail();
                break;
            case WXPayEntryActivity.RespErrCode.CODE_SUCCESS:
                payResultListner.onSuccess();
                break;
        }
    }

    /**
     * 通过EventBus来通知我的娃娃页面刷新数据
     */
    private void refreshMainMyDollList()
    {
        WWEReceiveDoll event = new WWEReceiveDoll();
        EventBus.getDefault().post(event);
    }

}
