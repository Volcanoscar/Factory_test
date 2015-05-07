package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.support.FtUtil;

public class AtWifitest
  extends AtCommandHandler
{
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      FtUtil.log_d(AtWifitest.this.CLASS_NAME, "handleCommand", "Action: " + str);
      if ("com.sec.android.app.wlantest.WIFI_TEST_RESPONSE".equals(str)) {
        if (AtWifitest.this.getCmdType() == 0)
        {
          AtWifitest.this.writerCpo.write(2, "18", "00", paramAnonymousIntent.getStringExtra("S_DATA"));
          AtWifitest.this.stopReceiver(AtWifitest.this.mReceiver);
        }
      }
      while (!"android.intent.action.WIFI_DRIVER_INDICATION".equals(str)) {
        for (;;)
        {
          return;
          AtWifitest.this.writer.write(AtWifitest.this.responseString("0", paramAnonymousIntent.getStringExtra("S_DATA")));
        }
      }
      FtUtil.log_d(AtWifitest.this.CLASS_NAME, "handleCommand", "Status: " + paramAnonymousIntent.getStringExtra("STATUS"));
      if (paramAnonymousIntent.getStringExtra("STATUS").equals("ready"))
      {
        AtWifitest.access$002(AtWifitest.this, 1);
        AtWifitest.this.handleCommand(AtWifitest.this.savedArgu);
        return;
      }
      AtWifitest.access$002(AtWifitest.this, 0);
    }
  };
  private int mWlanTest = 0;
  private String[] savedArgu;
  
  public AtWifitest(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "WIFITEST";
    this.CLASS_NAME = "AtWifitest";
    this.NUM_ARGS = 4;
    this.writer = paramResponseWriter;
  }
  
  public AtWifitest(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "WIFITEST";
    this.CLASS_NAME = "AtWifitest";
    this.NUM_ARGS = 4;
    this.writerCpo = paramResponseWriterCPO;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    String str1 = "";
    int i = 0;
    for (;;)
    {
      String str2;
      try
      {
        if (i < paramArrayOfString.length)
        {
          str1 = str1 + paramArrayOfString[i];
          i++;
          continue;
        }
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : " + str1);
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "mWlanTest: " + this.mWlanTest);
        if (this.mWlanTest == 0)
        {
          if (!checkArgu(paramArrayOfString, new String[] { "0", "1", "0" }))
          {
            if (!checkArgu(paramArrayOfString, new String[] { "0", "1", "2" })) {}
          }
          else
          {
            str2 = responseOK(paramArrayOfString[0]);
            break label1299;
          }
          startReceiver();
          this.savedArgu = paramArrayOfString;
          Intent localIntent = new Intent();
          localIntent.setClassName("com.sec.android.app.wlantest", "com.sec.android.app.wlantest.WlanTest");
          localIntent.setFlags(268435456);
          localIntent.putExtra("MODE", "auto");
          this.context.startActivity(localIntent);
          int k = getCmdType();
          str2 = null;
          if (k == 0) {
            str2 = "WAIT";
          }
        }
        else
        {
          startReceiver();
          int j = getCmdType();
          str2 = null;
          if (j == 0) {
            str2 = "WAIT";
          }
          if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0" }))
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : closeDut");
            sendIntent(1, paramArrayOfString[3]);
            this.mWlanTest = 0;
          }
        }
      }
      finally {}
      if (checkArgu(paramArrayOfString, new String[] { "0", "1", "1" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : openDut");
        sendIntent(0, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "2" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : unloadNormalFW");
        sendIntent(19, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "3" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : loadNormalFW");
        sendIntent(18, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : channelSetting");
        sendIntent(6, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "1" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : dataRateSetting");
        sendIntent(2, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "2" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : preambleSetting");
        sendIntent(5, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "3" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : txGainSetting");
        sendIntent(7, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "4" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : payloadLengthSetting");
        sendIntent(9, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "5" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : burstIntervalSetting");
        sendIntent(8, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "6" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : BandSetting");
        sendIntent(16, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "7" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : BandwidthSetting");
        sendIntent(17, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "2", "0" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : stopTx");
        sendIntent(11, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "2", "1" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : startTx");
        sendIntent(10, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "3", "0" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : stopRx");
        sendIntent(13, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "3", "1" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : startRx");
        sendIntent(12, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "4", "0" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : rxFrameReceiveError");
        sendIntent(14, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "4", "1" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : rxFrameReceiveGood");
        sendIntent(15, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "6", "0" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : joinAP");
        sendIntent(20, paramArrayOfString[3]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "7", "0" }))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "command : Ant Select");
        sendIntent(4, paramArrayOfString[3]);
      }
      else
      {
        str2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      }
      label1299:
      Object localObject2 = str2;
    }
  }
  
  protected void sendIntent(int paramInt, String paramString)
  {
    if (this.mWlanTest == 1)
    {
      Intent localIntent = new Intent("com.sec.android.app.wlantest.WIFI_TEST_INDICATION");
      localIntent.putExtra("CMDID", paramInt);
      localIntent.putExtra("S_DATA", paramString);
      this.context.sendBroadcast(localIntent);
    }
  }
  
  public void startReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.sec.android.app.wlantest.WIFI_TEST_RESPONSE");
    localIntentFilter.addAction("android.intent.action.WIFI_DRIVER_INDICATION");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtWifitest
 * JD-Core Version:    0.7.1
 */