package com.sec.factory.app.factorytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.SystemProperties;
import android.widget.ListView;
import android.widget.TextView;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.NVAccessor;
import com.sec.factory.support.Support.Feature;

public class FactoryTestMainNV
{
  private final String CLASS_NAME = "FactoryTestMainNV";
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      FtUtil.log_d("FactoryTestMainNV", "mBroadcastReceiver.onReceive", "action=" + str);
      if (str.equals("com.android.samsungtest.RilOmissionCommand"))
      {
        FtUtil.log_i("FactoryTestMainNV", "mBroadcastReceiver.onReceive", "ACTION_RECEIVE_TESTNV");
        FactoryTestMainNV.this.getFactoryTestHistoryValue(paramAnonymousIntent.getStringExtra("COMMAND"));
        FactoryTestMainNV.this.mTestResultText.setText(FactoryTestMainNV.this.formatTestResultCPO());
        if (FactoryTestMainAdapter.FONT_SIZE != 0.0F) {
          FactoryTestMainNV.this.mTestResultText.setTextSize(FactoryTestMainAdapter.FONT_SIZE);
        }
      }
      if (FactoryTestManager.getAllitemPass()) {
        FactoryTestMainNV.this.mListView.setBackgroundColor(-16776961);
      }
      for (FactoryTestMainAdapter.ALL_PASS_STATE = true;; FactoryTestMainAdapter.ALL_PASS_STATE = false)
      {
        FactoryTestManager.notifyDataSetChanged();
        return;
        FactoryTestMainNV.this.mListView.setBackgroundColor(-1);
      }
    }
  };
  private Context mContext;
  FactoryTestPhone mFactoryPhone;
  private Handler mHandlerFeedback;
  private boolean mIsFirstRegisterReceiverNRequest = false;
  private ListView mListView;
  private TextView mTestResultText;
  
  public FactoryTestMainNV(Context paramContext, Handler paramHandler, TextView paramTextView, ListView paramListView, FactoryTestPhone paramFactoryTestPhone)
  {
    this.mContext = paramContext;
    this.mHandlerFeedback = paramHandler;
    this.mTestResultText = paramTextView;
    this.mListView = paramListView;
    this.mFactoryPhone = paramFactoryTestPhone;
    this.mIsFirstRegisterReceiverNRequest = false;
  }
  
  private String formatTestResultCPO()
  {
    String str1 = "";
    String str7;
    String str2;
    label105:
    String str3;
    label134:
    String str4;
    label165:
    StringBuilder localStringBuilder4;
    if ((SystemProperties.get("ro.csc.sales_code", "NONE").contains("CTC")) && (Support.Feature.getBoolean("SUPPORT_DUAL_STANBY")))
    {
      FtUtil.log_d("FactoryTestMainNV", "formatTestResultAPO", "support DUAL STANDBY");
      StringBuilder localStringBuilder5 = new StringBuilder().append(" C2: ");
      if (this.mFactoryPhone.getResultForPGMItems("08"))
      {
        str7 = "O";
        str1 = str7;
      }
    }
    else
    {
      StringBuilder localStringBuilder1 = new StringBuilder().append("S: ");
      if (this.mFactoryPhone.getResultForPGMItems("01") != true) {
        break label249;
      }
      str2 = "O";
      StringBuilder localStringBuilder2 = localStringBuilder1.append(str2).append(" P: ");
      if (this.mFactoryPhone.getResultForPGMItems("04") != true) {
        break label255;
      }
      str3 = "O";
      StringBuilder localStringBuilder3 = localStringBuilder2.append(str3).append(" C: ");
      if (this.mFactoryPhone.getResultForPGMItems("07") != true) {
        break label262;
      }
      str4 = "O";
      localStringBuilder4 = localStringBuilder3.append(str4).append(str1).append(" F: ");
      if (this.mFactoryPhone.getResultForPGMItems("0B") != true) {
        break label269;
      }
    }
    label262:
    label269:
    for (String str5 = "O";; str5 = "X")
    {
      String str6 = str5;
      FtUtil.log_d("FactoryTestMainNV", "formatTestResultCPO", "result=" + str6);
      return str6;
      str7 = "X";
      break;
      label249:
      str2 = "X";
      break label105;
      label255:
      str3 = "X";
      break label134;
      str4 = "X";
      break label165;
    }
  }
  
  private void getFactoryTestHistoryValue(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    FtUtil.log_i("FactoryTestMainNV", "getFactoryTestHistoryValue", "nvString = " + paramString);
    int i = 6;
    if (i < paramString.length())
    {
      String str1 = paramString.substring(i, i + 2);
      String str2 = paramString.substring(i + 2, i + 4);
      int j = this.mFactoryPhone.getItemID(str1);
      if ("50".equals(str2))
      {
        FtUtil.log_i("FactoryTestMainNV", "getFactoryTestHistoryValue", "PASS = " + str1);
        if ((str1.equals("01")) || (str1.equals("04")) || (str1.equals("07")) || ("08".equals(str1)) || (str1.equalsIgnoreCase("0A")) || (str1.equals("0B")) || (str1.equalsIgnoreCase("0C"))) {
          this.mFactoryPhone.setResultForPGMItems(str1, true);
        }
      }
      while (j <= -1)
      {
        i += 4;
        break;
      }
      FtUtil.log_i("FactoryTestMainNV", "getFactoryTestHistoryValue", "Position = " + j);
      byte b = 78;
      if ("50".equals(str2)) {
        b = 80;
      }
      for (;;)
      {
        FactoryTestManager.setItemResult(FactoryTestManager.getItemPosition_ID(j), b);
        break;
        if ("45".equals(str2)) {
          b = 69;
        } else if ("46".equals(str2)) {
          b = 70;
        }
      }
    }
    FactoryTestManager.setResultParent();
    if (!this.mIsFirstRegisterReceiverNRequest)
    {
      this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(5));
      this.mIsFirstRegisterReceiverNRequest = true;
    }
  }
  
  public String formatTestResultAPO()
  {
    StringBuilder localStringBuilder1 = new StringBuilder().append("S: ");
    String str1;
    String str2;
    label49:
    String str3;
    label76:
    String str4;
    label104:
    String str5;
    StringBuilder localStringBuilder5;
    if (NVAccessor.getNV((byte)1) == 80)
    {
      str1 = "O";
      StringBuilder localStringBuilder2 = localStringBuilder1.append(str1).append(" P: ");
      if (NVAccessor.getNV((byte)4) != 80) {
        break label205;
      }
      str2 = "O";
      StringBuilder localStringBuilder3 = localStringBuilder2.append(str2).append(" C: ");
      if (NVAccessor.getNV((byte)7) != 80) {
        break label212;
      }
      str3 = "O";
      StringBuilder localStringBuilder4 = localStringBuilder3.append(str3).append(" F: ");
      if (NVAccessor.getNV((byte)11) != 80) {
        break label219;
      }
      str4 = "O";
      str5 = str4;
      if (Support.Feature.getBoolean("SUPPORT_DUALMODE"))
      {
        localStringBuilder5 = new StringBuilder().append(str5).append(" C2: ");
        if (NVAccessor.getNV((byte)8) != 80) {
          break label226;
        }
      }
    }
    label205:
    label212:
    label219:
    label226:
    for (String str6 = "O";; str6 = "X")
    {
      str5 = str6;
      FtUtil.log_d("FactoryTestMainNV", "formatTestResultAPO", "result=" + str5);
      return str5;
      str1 = "X";
      break;
      str2 = "X";
      break label49;
      str3 = "X";
      break label76;
      str4 = "X";
      break label104;
    }
  }
  
  public void registerReceiverNRequestCPO()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.android.samsungtest.RilOmissionCommand");
    this.mContext.registerReceiver(this.mBroadcastReceiver, localIntentFilter);
    FtUtil.log_i("FactoryTestMainNV", "registerReceiverNRequestCPO", "Request for TestNV!");
    this.mFactoryPhone.requestTestNvViewToRil();
  }
  
  public void unregisterReceiverNRequestCPO()
  {
    if (this.mBroadcastReceiver != null)
    {
      this.mContext.unregisterReceiver(this.mBroadcastReceiver);
      this.mBroadcastReceiver = null;
      FtUtil.log_i("FactoryTestMainNV", "unregisterTestReceiver", null);
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestMainNV
 * JD-Core Version:    0.7.1
 */