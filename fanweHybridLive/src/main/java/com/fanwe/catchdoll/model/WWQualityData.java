package com.fanwe.catchdoll.model;

import com.tencent.ilivesdk.tools.quality.ILiveQualityData;

/**
 * Created by Administrator on 2018/1/29.
 */

public class WWQualityData
{
    //发包丢包率，以百分比乘以100为返回值。如丢包率为12.34%，则sendLossRate = 1234
    private int sendLossRate;
    //收包丢包率，以百分比乘以100为返回值。如丢包率为12.34%，则recvLossRate = 1234
    private int recvLossRate;
    //app占用CPU，以百分比乘以100为返回值。如占用率为12.34%，则appCPURate = 1234
    private int appCPURate;
    //系统占用CPU，以百分比乘以100为返回值。如占用率为12.34%，则sysCPURate = 1234
    private int sysCPURate;
    //发送码率
    private int sendKbps;
    //接收码率
    private int recvKbps;

    public WWQualityData(ILiveQualityData qualityData)
    {
       sendKbps = qualityData.getSendKbps();
       recvKbps = qualityData.getRecvKbps();
       appCPURate = qualityData.getAppCPURate();
       sysCPURate =  qualityData.getSysCPURate();
       sendLossRate = qualityData.getSendLossRate();
       recvLossRate = qualityData.getRecvLossRate();
    }

    @Override
    public String toString()
    {
        return "[sendLossRate= " + sendLossRate + "|" +
                "recvLossRate= " + recvLossRate + "|" +
                "appCPURate= " + appCPURate + "|" +
                "sysCPURate= " + sysCPURate + "|" +
                "sendKbps= " + sendKbps + "|" +
                "recvKbps= " + recvKbps+"]";
    }

    public int getSendLossRate()
    {
        return sendLossRate;
    }

    public void setSendLossRate(int sendLossRate)
    {
        this.sendLossRate = sendLossRate;
    }

    public int getRecvLossRate()
    {
        return recvLossRate;
    }

    public void setRecvLossRate(int recvLossRate)
    {
        this.recvLossRate = recvLossRate;
    }

    public int getAppCPURate()
    {
        return appCPURate;
    }

    public void setAppCPURate(int appCPURate)
    {
        this.appCPURate = appCPURate;
    }

    public int getSysCPURate()
    {
        return sysCPURate;
    }

    public void setSysCPURate(int sysCPURate)
    {
        this.sysCPURate = sysCPURate;
    }

    public int getSendKbps()
    {
        return sendKbps;
    }

    public void setSendKbps(int sendKbps)
    {
        this.sendKbps = sendKbps;
    }

    public int getRecvKbps()
    {
        return recvKbps;
    }

    public void setRecvKbps(int recvKbps)
    {
        this.recvKbps = recvKbps;
    }
}
