package com.fanwe.catchdoll.common;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.fanwe.catchdoll.model.WWAccountLogActModel;
import com.fanwe.catchdoll.model.WWAddressListModel;
import com.fanwe.catchdoll.model.WWAppGameEndModel;
import com.fanwe.catchdoll.model.WWAppGameStartModel;
import com.fanwe.catchdoll.model.WWAppJoinQueueModel;
import com.fanwe.catchdoll.model.WWAppMyReserveModel;
import com.fanwe.catchdoll.model.WWDollExchangeDiamondActModel;
import com.fanwe.catchdoll.model.WWDollQuestionActModel;
import com.fanwe.catchdoll.model.WWExchangeRecordActModel;
import com.fanwe.catchdoll.model.WWExchangeThingDetailActModel;
import com.fanwe.catchdoll.model.WWFirstRegisterSendGiftActModel;
import com.fanwe.catchdoll.model.WWGrabDollMasterActModel;
import com.fanwe.catchdoll.model.WWGrabDollRecordActModel;
import com.fanwe.catchdoll.model.WWInviteHistoryActModel;
import com.fanwe.catchdoll.model.WWMainDiscoveryListModel;
import com.fanwe.catchdoll.model.WWMainHomeListModel;
import com.fanwe.catchdoll.model.WWMainIntegralMallActModel;
import com.fanwe.catchdoll.model.WWMainMyDollListActModel;
import com.fanwe.catchdoll.model.WWMyThingsActModel;
import com.fanwe.catchdoll.model.WWOrderDetailActModel;
import com.fanwe.catchdoll.model.WWPayGetFreeModel;
import com.fanwe.catchdoll.model.WWPayPayModel;
import com.fanwe.catchdoll.model.WWPaymentDetailActModel;
import com.fanwe.catchdoll.model.WWQualityData;
import com.fanwe.catchdoll.model.WWRechargeLogActModel;
import com.fanwe.catchdoll.model.WWSignInActModel;
import com.fanwe.catchdoll.model.WWSignInfoActModel;
import com.fanwe.catchdoll.model.WWSubmitAddAddressModel;
import com.fanwe.catchdoll.model.WWTotalFreightModel;
import com.fanwe.catchdoll.model.WWTreasureBoxActModel;
import com.fanwe.catchdoll.model.WWUserDollDetailModel;
import com.fanwe.catchdoll.model.WWValidateInviteCodeActModel;
import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.handler.SDRequestHandler;
import com.fanwe.library.utils.MD5Util;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.App_get_videoActModel;
import com.tencent.ilivesdk.tools.quality.ILiveQualityData;

import java.util.List;

import static com.fanwe.live.common.AppRuntimeWorker.getLoginUserID;


/**
 * 抓娃娃接口请求参数类
 * Created by LianCP on 2017/11/14.
 */
public class WWCommonInterface
{
    public static AppRequestParams getWaWaParams()
    {
        AppRequestParams params = new AppRequestParams();
        params.put("itype", "wawa_server");
        return params;
    }

    /**
     * 首页接口
     *
     * @param p        分页
     * @param listener
     */
    public static void requestHome(int cate_id, int p, AppRequestCallback<WWMainHomeListModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("index");
        params.putAct("index");
        params.put("classified_id", cate_id);
        params.put("p", p);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 娃娃领取详情页
     *
     * @param id
     * @param listener
     */
    public static void requestUserDollDetail(String id, AppRequestCallback<WWUserDollDetailModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("doll_detail");
        params.put("id", id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 收货地址列表接口
     *
     * @param p        分页
     * @param listener
     */
    public static void requestAddressList(int p, AppRequestCallback<WWAddressListModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("consignee");
        params.put("p", p);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 修改和添加收货地址
     *
     * @param wwSubmitAddAddressModel
     * @param listener
     */
    public static void requestSaveAddress(WWSubmitAddAddressModel wwSubmitAddAddressModel, AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("save_consignee");
        if (!TextUtils.isEmpty(wwSubmitAddAddressModel.getId()))
        {
            params.put("id", wwSubmitAddAddressModel.getId());//收货地址id(编辑要传，新增没有)
        }
        params.put("consignee", wwSubmitAddAddressModel.getConsignee());//姓名
        params.put("mobile", wwSubmitAddAddressModel.getMobile());//手机
        params.put("province", wwSubmitAddAddressModel.getProvince());//省
        params.put("city", wwSubmitAddAddressModel.getCity());//市
        params.put("county", wwSubmitAddAddressModel.getCounty());//县/区
        params.put("address", wwSubmitAddAddressModel.getAddress());//详细地址
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 删除收货地址
     *
     * @param ids
     * @param listener
     */
    public static void requestDeleteAddress(String ids, AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("del_consignee");
        params.put("ids", ids);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 删除收货地址
     *
     * @param id
     * @param listener
     */
    public static void requestSetDefaultAddress(String id, AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("set_default_consignee");
        params.put("id", id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 我的娃列表获取
     * status ：0全部，1未领取，2已领取
     */
    public static void requestMainMyDollList(int page, int status, AppRequestCallback<WWMainMyDollListActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("my_doll_list");
        params.put("p", page);
        params.put("status", status);
        params.setNeedShowActInfo(false);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * TA的娃列表获取
     * uid ：用户id
     * room_id：如果没传则显示所有娃娃，有传就显示当前房间
     */
    public static void requestHisDollList(int page, int uid, int room_id, AppRequestCallback<WWMainMyDollListActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("his_doll_list");
        params.put("p", page);
        params.put("uid", uid);
        if (room_id > 0)
        {
            params.put("room_id", room_id);
        }
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 发现列表获取
     */
    public static void requestMainDiscoveryList(int page, AppRequestCallback<WWMainDiscoveryListModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("index");
        params.putAct("discovery");
        params.put("p", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 交易详情
     */
    public static void requestPayResult(String pay_id, AppRequestCallback<WWPaymentDetailActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("pay");
        params.putAct("pay_result");
        params.put("pay_id", pay_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 账单明细
     *
     * @param page
     * @param listener
     */
    public static void requestAccountLog(int page, AppRequestCallback<WWAccountLogActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("pay");
        params.putAct("bill_list");
        params.put("p", page);
        params.setNeedShowActInfo(false);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 充值明细
     *
     * @param page
     * @param listener
     */
    public static void requestRechargeLog(int page, AppRequestCallback<WWRechargeLogActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("pay");
        params.putAct("payment_list");
        params.put("p", page);
        params.setNeedShowActInfo(false);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 免费获取娃娃
     */
    public static void requestPayGetFree(String id, String address_id, AppRequestCallback<WWPayGetFreeModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("pay");
        params.putAct("get_free");
        params.put("id", id);
        params.put("address_id", address_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 领取娃娃支付接口
     */
    public static void requestPayPay(String id, int pay_id, String address_id, String total_freight, AppRequestCallback<WWPayPayModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("pay");
        params.putAct("get_free");
        params.put("id", id);
        params.put("pay_id", pay_id);
        params.put("address_id", address_id);
        params.put("total_freight", total_freight);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 领取娃娃总运费接口
     */
    public static void requestTotalFreight(int num, String freight, AppRequestCallback<WWTotalFreightModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("total_freight");
        params.put("num", num);
        params.put("freight", freight);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 请求直播间信息
     *
     * @param room_id  直播间id
     * @param listener
     */
    public static SDRequestHandler requestRoomInfo(int room_id, AppRequestCallback<App_get_videoActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("video");
        params.putAct("get_video2");
        params.put("room_id", room_id);
        params.put("itype", "wawa_server");

        String tencent_app_id = AppRuntimeWorker.getSdkappid();
        String user_id = getLoginUserID();
        String sign = tencent_app_id + user_id + room_id;
        String sign_md5 = MD5Util.MD5(sign);
        params.put("sign", sign_md5);
        return AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 游戏开始
     *
     * @param room_id 直播间id
     */
    public static SDRequestHandler requestGameStart(int room_id, AppRequestCallback<WWAppGameStartModel> callback)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("game");
        params.putAct("start");
        params.put("room_id", room_id);
        params.put("itype", "wawa_server");
        params.setNeedShowActInfo(false);

        return AppHttpUtil.getInstance().post(params, callback);
    }

    public static SDRequestHandler requestGameEnd(int gameId, int roomId, boolean is_grab,boolean isEnd, List<WWQualityData> qualityData, AppRequestCallback<WWAppGameEndModel> callback)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("game");
        params.putAct("end");
        params.put("game_id", gameId);
        params.put("room_id", roomId);
        params.put("is_grab", is_grab ? 1 : 0);
        params.put("is_end",isEnd ? 1 :0);
        params.put("live_quality", JSON.toJSONString(qualityData));
        params.put("itype", "wawa_server");
        params.put("toy_id", "unknown");
        params.put("result_no", "unknown");

        return AppHttpUtil.getInstance().post(params, callback);
    }

    public static SDRequestHandler requestGameFinish(int roomId, AppRequestCallback<WWAppGameEndModel> callback)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("game");
        params.putAct("finish");
        params.put("room_id", roomId);

        return AppHttpUtil.getInstance().post(params, callback);
    }

    public static SDRequestHandler requestJoinQueue(int roomId, AppRequestCallback<WWAppJoinQueueModel> callback)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("game");
        params.putAct("reserve");
        params.put("room_id", roomId);

        return AppHttpUtil.getInstance().post(params, callback);
    }

    public static SDRequestHandler requestQuitQueue(int roomId, AppRequestCallback<WWAppJoinQueueModel> callback)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("game");
        params.putAct("cancel_reserve");
        params.put("room_id", roomId);

        return AppHttpUtil.getInstance().post(params, callback);
    }

    public static SDRequestHandler requestMyReserve(int roomId, AppRequestCallback<WWAppMyReserveModel> callback)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("game");
        params.putAct("my_reserve");
        params.put("room_id", roomId);

        return AppHttpUtil.getInstance().post(params, callback);
    }

    public static SDRequestHandler requestUploadLog(String log)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("logs");
        params.putAct("set_log");
        params.put("content",log);

        return AppHttpUtil.getInstance().post(params,null);
    }

    /**
     * 订单详情
     *
     * @param id
     * @param listener
     */
    public static void requestOrderDetail(String id, AppRequestCallback<WWOrderDetailActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("doll_detail");
        params.put("id", id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 抓娃娃达人
     *
     * @param room_id
     * @param page
     * @param listener
     */
    public static void requestGrabDollMaster(int room_id, int page, AppRequestCallback<WWGrabDollMasterActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("dolls");
        params.putAct("game_leaderboard");
        params.put("room_id", room_id);
        params.put("p", page);
        params.setNeedShowActInfo(false);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 最近抓中记录
     *
     * @param room_id
     * @param page
     * @param listener
     */
    public static void requestGrabDollRecord(int room_id, int page, AppRequestCallback<WWGrabDollRecordActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("dolls");
        params.putAct("game_succeed_record");
        params.put("room_id", room_id);
        params.put("p", page);
        params.setNeedShowActInfo(false);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 报修数据获取
     */
    public static void requestDollQuestion(AppRequestCallback<WWDollQuestionActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("game");
        params.putAct("question");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 报修数据提交
     */
    public static void requestSaveQuestion(int roomId, int gameId, int question_id, int dollId, String log, AppRequestCallback<BaseActModel> listener)
    {

        AppRequestParams params = getWaWaParams();
        params.putCtl("game");
        params.putAct("save_question");
        params.put("room_id", roomId);
        params.put("doll_id", dollId);
        params.put("game_record_id", gameId);
        params.put("question_id", question_id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 积分商城列表(虚拟)
     */

    public static void requestMainIntegralMallList(int page, AppRequestCallback<WWMainIntegralMallActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("exchange_score");
        params.put("p", page);
        params.setNeedShowActInfo(false);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 积分商城列表(实物)
     */

    public static void requestExchangeThingList(int page, int id, AppRequestCallback<WWMainIntegralMallActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("exchange_thing_list");
        params.put("p", page);
        params.put("id", id);
        params.setNeedShowActInfo(false);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 兑换卷兑换钻石
     */
    public static void requestSaveExchangeLog(int id, int score, int diamonds, AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("save_exchange_score_log");
        params.put("id", id);
        params.put("score", score);
        params.put("diamonds", diamonds);
        AppHttpUtil.getInstance().post(params, listener);
    }


    /**
     * 兑换记录
     */
    public static void requestExchangeRecordList(int page, AppRequestCallback<WWExchangeRecordActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("exchange_score_log");
        params.put("p", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 提交邀请码
     */
    public static void requestInviteCode(String inviteCode, AppRequestCallback<WWValidateInviteCodeActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("invite");
        params.putAct("invite_validate");
        params.put("invite_code", inviteCode);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 邀请好友记录
     */
    public static void requestInviteHistory(int page, AppRequestCallback<WWInviteHistoryActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("invite");
        params.putAct("invite_history");
        params.put("p", page);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 积分宝箱
     */
    public static void requestTreasureBox(AppRequestCallback<WWTreasureBoxActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("score_box");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 打开积分宝箱
     */
    public static void requestOpenTreasureBox(long score, AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("save_score_box_log");
        params.put("score", score);
        params.setNeedShowActInfo(false);
        AppHttpUtil.getInstance().post(params, listener);
    }


    /**
     * 签到数据初始化
     */
    public static void requestSignInfo(AppRequestCallback<WWSignInfoActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("get_sign_in");
        AppHttpUtil.getInstance().post(params, listener);
    }


    /**
     * 签到
     */
    public static void requestSignIn(AppRequestCallback<WWSignInActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("sign_in");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 娃娃列表兑换钻石
     */
    public static void requestExchangeDiamond(String id, AppRequestCallback<WWDollExchangeDiamondActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("pay");
        params.putAct("exchange_diamonds");
        params.put("id", id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 首次注册送礼物
     */
    public static void requestFirstRegisterSendGift(AppRequestCallback<WWFirstRegisterSendGiftActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("get_first_register");
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 实物详情
     */
    public static void requestExchangeThingDetail(int id, AppRequestCallback<WWExchangeThingDetailActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("exchange_thing");
        params.put("id", id);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 兑换实物
     */
    public static void requestSaveExchangeThing(int id, long score, AppRequestCallback<BaseActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("save_exchange_thing");
        params.put("id", id);
        params.put("score", score);
        AppHttpUtil.getInstance().post(params, listener);
    }

    /**
     * 我的实物列表获取
     * status ：0全部，1未领取，2已领取
     */
    public static void requestMyThingsList(int page, int status, AppRequestCallback<WWMyThingsActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("my_thing_list");
        params.put("p", page);
        params.put("status", status);
        params.setNeedShowActInfo(false);
        AppHttpUtil.getInstance().post(params, listener);
    }


    /**
     * 我的实物领取详情页
     *
     */
    public static void requestMyThingsDetail(String id, AppRequestCallback<WWUserDollDetailModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("thing_detail");
        params.put("id", id);
        AppHttpUtil.getInstance().post(params, listener);
    }


    /**
     * 领取我的实物支付接口
     */
    public static void requestMyThingsPay(String id, int pay_id, String address_id, String total_freight, AppRequestCallback<WWPayPayModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("pay");
        params.putAct("get_thing_free");
        params.put("id", id);
        params.put("pay_id", pay_id);
        params.put("address_id", address_id);
        params.put("total_freight", total_freight);
        AppHttpUtil.getInstance().post(params, listener);
    }


    /**
     * 我的实物订单详情
     *
     * @param id
     * @param listener
     */
    public static void requestMyThingsOrderDetail(String id, AppRequestCallback<WWOrderDetailActModel> listener)
    {
        AppRequestParams params = getWaWaParams();
        params.putCtl("user");
        params.putAct("thing_detail");
        params.put("id", id);
        AppHttpUtil.getInstance().post(params, listener);
    }

}
