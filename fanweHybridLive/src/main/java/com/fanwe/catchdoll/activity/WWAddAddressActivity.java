package com.fanwe.catchdoll.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.fanwe.catchdoll.common.WWCommonInterface;
import com.fanwe.catchdoll.model.WWAddressModel;
import com.fanwe.catchdoll.model.WWSubmitAddAddressModel;
import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.lib.cache.SDDisk;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.customview.ClearEditText;
import com.fanwe.library.model.SDTaskRunnable;
import com.fanwe.library.utils.SDKeyboardUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_RegionListActModel;
import com.fanwe.live.model.RegionModel;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 添加地址
 * Created by LianCP on 2017/11/13.
 */
public class WWAddAddressActivity extends BaseActivity
{
    //进入添加地址页面业务类型（int） 0:添加收货地址；1:修改收货地址
    public static final String EXTRA_JUMP_TYPE = "jumpType";
    //当是修改地址页面的时候必须把当前地址数据传进来（WWAddressModel）
    public static final String EXTRA_JUMP_DATA = "jumpData";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ww_act_add_address);
        findView();
        initValue();
    }

    private FrameLayout mFlTitleLeft;
    private TextView mTvTitleName;
    private ClearEditText mEtName;
    private ClearEditText mEtphone;
    private LinearLayout mLlAddress;
    private TextView mTvAddress;
    private ClearEditText mEtAddressDetail;
    private TextView mTvsure;

    private OptionsPickerView mPickerCity;//城市选择器
    private ArrayList<RegionModel> mListProvince;//省份集合
    private ArrayList<ArrayList<RegionModel>> mListCity;//城市集合
    private ArrayList<ArrayList<ArrayList<RegionModel>>> mListRegion; //区域集合

    private int mType = 0;//0、表示添加地址，1、表示修改地址
    private WWAddressModel mAddressModel;
    private WWSubmitAddAddressModel mSubmitAddAddressModel = new WWSubmitAddAddressModel();

    private void findView()
    {
        mFlTitleLeft = (FrameLayout) findViewById(R.id.fl_title_left);
        mTvTitleName = (TextView) findViewById(R.id.tv_title_name);
        mEtName = (ClearEditText) findViewById(R.id.cet_name);
        mEtphone = (ClearEditText) findViewById(R.id.cet_phone);
        mLlAddress = (LinearLayout) findViewById(R.id.ll_address);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mEtAddressDetail = (ClearEditText) findViewById(R.id.cet_address_detail);
        mTvsure = (TextView) findViewById(R.id.tv_sure);
        setBtnOnClick();
    }

    private void setBtnOnClick()
    {
        mFlTitleLeft.setOnClickListener(this);
        mLlAddress.setOnClickListener(this);
        mTvsure.setOnClickListener(this);
    }

    private void initValue()
    {
        Intent intent = getIntent();
        mType = intent.getIntExtra(EXTRA_JUMP_TYPE, 0);
        mAddressModel = (WWAddressModel) intent.getSerializableExtra(EXTRA_JUMP_DATA);
        switch (mType)
        {
            case 0:
                mTvTitleName.setText("添加收货地址");
                break;
            case 1:
                mTvTitleName.setText("修改收货地址");
                break;
            default:
        }

        if (null != mAddressModel)
        {
            mSubmitAddAddressModel.setId(mAddressModel.getId());
            mSubmitAddAddressModel.setProvince(mAddressModel.getProvince());
            mSubmitAddAddressModel.setCity(mAddressModel.getCity());
            mSubmitAddAddressModel.setCounty(mAddressModel.getCounty());
            mEtName.setText(mAddressModel.getConsignee());
            mEtName.setSelection(mAddressModel.getConsignee().length());
            mEtphone.setText(mAddressModel.getMobile());
            mTvAddress.setText(mAddressModel.getProvince() + " - " + mAddressModel.getCity()
                    + " - " + mAddressModel.getCounty());
            mEtAddressDetail.setText(mAddressModel.getAddress());
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.fl_title_left:
                finish();
                break;
            case R.id.ll_address:
                SDKeyboardUtil.hideKeyboard(v);
                if (mPickerCity == null)
                {
                    showProgressDialog("加载中...");
                    initCityPicker();
                } else
                {
                    mPickerCity.show();
                }
                break;
            case R.id.tv_sure:
                saveAddress();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化城市选择器
     */
    private void initCityPicker()
    {
        mPickerCity = new OptionsPickerView(this);
        mListProvince = new ArrayList<>();
        mListCity = new ArrayList<>();
        mListRegion = new ArrayList<>();
        mPickerCity.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener()
        {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3)
            {
                mSubmitAddAddressModel.setCounty(mListRegion.get(options1).get(option2).get(options3).getName());
                mSubmitAddAddressModel.setCity(mListCity.get(options1).get(option2).getName());
                mSubmitAddAddressModel.setProvince(mListProvince.get(options1).getName());
                mTvAddress.setText(mListProvince.get(options1).getName() + " - "
                        +  mListCity.get(options1).get(option2).getName() + " - "
                        + mListRegion.get(options1).get(option2).get(options3).getName());
            }
        });
        mPickerCity.setCancelable(true);
        checkRegionVersion();
    }

    /**
     * 检查地区版本、更新保存数据
     */
    private void checkRegionVersion()
    {
        App_RegionListActModel regionActModel = AppRuntimeWorker.getRegionListActModel();
        if (regionActModel == null)
        {
            CommonInterface.requestRegionList(new AppRequestCallback<App_RegionListActModel>()
            {
                @Override
                protected void onSuccess(SDResponse resp)
                {
                    if (actModel.isOk())
                    {
                        SDDisk.openInternal().putSerializable(actModel);
                        handleCityData(actModel.getRegion_list());
                    }
                }

                @Override
                protected void onError(SDResponse resp)
                {
                    super.onError(resp);
                }
            });
        } else
        {
            handleCityData(regionActModel.getRegion_list());
        }
    }

    /**
     * 分类省份及其对应的城市集合
     *
     * @param regionModelArrayList
     */
    private void handleCityData(final ArrayList<RegionModel> regionModelArrayList)
    {
        SDHandlerManager.getBackgroundHandler().post(new SDTaskRunnable<String>()
        {

            @Override
            public String onBackground()
            {
                initCityData(regionModelArrayList);
                return null;
            }

            @Override
            public void onMainThread(String result)
            {
                dismissProgressDialog();
                mPickerCity.setPicker(mListProvince, mListCity, mListRegion, true);
                mPickerCity.setCyclic(false);
                mPickerCity.setSelectOptions(getProvincePosition(), getCityPosition(getProvincePosition()),
                        getRegionPosition(getProvincePosition(), getCityPosition(getProvincePosition())));//默认选中
                mPickerCity.show();
            }
        });
    }

    /**
     * 初始化城市数据
     *
     * @param listModel
     */
    private void initCityData(ArrayList<RegionModel> listModel)
    {
        Iterator<RegionModel> it = listModel.iterator();
        while (it.hasNext())
        {
            RegionModel item = it.next();
            if (item.getRegion_level() == 2)
            {
                mListProvince.add(item);
                it.remove();
            }
        }

        for (RegionModel itemProvince : mListProvince)
        {
            ArrayList<RegionModel> listCity = new ArrayList<>();
            for (RegionModel itemCity : listModel)
            {
                if (itemCity.getPid() == itemProvince.getId())
                {
                    listCity.add(itemCity);
                }
            }
            mListCity.add(listCity);
        }

        for (ArrayList<RegionModel> itemCityGroup : mListCity)
        {
            ArrayList<ArrayList<RegionModel>> listRegionGroup = new ArrayList<>();
            for (RegionModel city : itemCityGroup)
            {
                ArrayList<RegionModel> listRegion = new ArrayList<>();
                for (RegionModel itemRegion : listModel)
                {
                    if (itemRegion.getPid() == city.getId())
                    {
                        listRegion.add(itemRegion);
                    }
                }
                listRegionGroup.add(listRegion);
            }
            mListRegion.add(listRegionGroup);
        }
    }

    /**
     * 遍历获取集合内省份的position
     *
     * @return
     */
    private int getProvincePosition()
    {
        if (TextUtils.isEmpty(mSubmitAddAddressModel.getProvince()))
        {
            return 0;
        } else
        {
            for (RegionModel model : mListProvince)
            {
                if (TextUtils.equals(mSubmitAddAddressModel.getProvince(), model.getName()))
                {
                    return mListProvince.indexOf(model);
                }
            }
            return 0;
        }
    }

    /**
     * 遍历获取集合内省份对应集合的城市的position
     *
     * @param province_position 省份所在集合对应的position
     * @return
     */
    private int getCityPosition(int province_position)
    {
        if (TextUtils.isEmpty(mSubmitAddAddressModel.getCity()))
        {
            return 0;
        } else
        {
            for (RegionModel model : mListCity.get(province_position))
            {
                if (TextUtils.equals(mSubmitAddAddressModel.getCity(), model.getName()))
                {
                    return mListCity.get(province_position).indexOf(model);
                }
            }
            return 0;
        }
    }

    private int getRegionPosition(int province_position, int city_position)
    {
        if (TextUtils.isEmpty(mSubmitAddAddressModel.getCounty()))
        {
            return 0;
        } else
        {
            for (RegionModel model : mListRegion.get(province_position).get(city_position))
            {
                if (TextUtils.equals(mSubmitAddAddressModel.getCounty(), model.getName()))
                {
                    return mListRegion.get(province_position).get(city_position).indexOf(model);
                }
            }
            return 0;
        }
    }

    private void saveAddress()
    {
        if (isNotData())
        {
            SDToast.showToast("您要保存的地址信息不完整，请先完善！");
            return;
        }
        WWCommonInterface.requestSaveAddress(mSubmitAddAddressModel, new AppRequestCallback<BaseActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    finish();
                }
            }
        });
    }

    private boolean isNotData()
    {
        String nameStr = mEtName.getText().toString().trim();
        mSubmitAddAddressModel.setConsignee(nameStr);
        String phoneStr = mEtphone.getText().toString().trim();
        mSubmitAddAddressModel.setMobile(phoneStr);
        String addressStr = mTvAddress.getText().toString().trim();
        String detailAddressStr = mEtAddressDetail.getText().toString().trim();
        mSubmitAddAddressModel.setAddress(detailAddressStr);
        if (TextUtils.isEmpty(nameStr))
        {
            return true;
        }
        if (TextUtils.isEmpty(phoneStr))
        {
            return true;
        }
        if (TextUtils.isEmpty(addressStr))
        {
            return true;
        }
        if (TextUtils.isEmpty(detailAddressStr))
        {
            return true;
        }
        return false;
    }
}
