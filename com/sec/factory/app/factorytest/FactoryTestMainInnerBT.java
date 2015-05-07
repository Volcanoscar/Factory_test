package com.sec.factory.app.factorytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.widget.ListView;
import com.sec.factory.modules.ModuleCommunication;
import com.sec.factory.support.FtUtil;

public class FactoryTestMainInnerBT
{
  private static int mBtTurnningCount = 0;
  private static final String mSalesCode = SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase();
  private final String CLASS_NAME = "FactoryTestMainInnerBT";
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      FtUtil.log_d("FactoryTestMainInnerBT", "mBroadcastReceiver.onReceive", "action=" + str);
      if (str.equals("com.sec.factory.intent.ACTION_BT_RESPONSE"))
      {
        FtUtil.log_i("FactoryTestMainInnerBT", "mBroadcastReceiver.onReceive", "ACTION_BT_RESPONSE");
        FactoryTestMainInnerBT.this.changeBluetoothState(paramAnonymousIntent.getStringExtra("result"));
      }
      if (FactoryTestManager.getAllitemPass()) {
        FactoryTestMainInnerBT.this.mListView.setBackgroundColor(-16776961);
      }
      for (FactoryTestMainAdapter.ALL_PASS_STATE = true;; FactoryTestMainAdapter.ALL_PASS_STATE = false)
      {
        FactoryTestManager.notifyDataSetChanged();
        return;
        FactoryTestMainInnerBT.this.mListView.setBackgroundColor(-1);
      }
    }
  };
  private Context mContext;
  boolean mEnable = false;
  private Handler mHandlerFeedback;
  private Handler mHandlerResult;
  private ListView mListView;
  
  public FactoryTestMainInnerBT(Context paramContext, Handler paramHandler1, Handler paramHandler2, ListView paramListView)
  {
    this.mContext = paramContext;
    this.mHandlerResult = paramHandler1;
    this.mHandlerFeedback = paramHandler2;
    this.mListView = paramListView;
  }
  
  private void changeBluetoothState(String paramString)
  {
    FtUtil.log_d("FactoryTestMainInnerBT", "changeBluetoothState", "result : " + paramString);
    this.mHandlerFeedback.sendEmptyMessage(4);
    if (paramString.equals("ON"))
    {
      sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)2, (byte)80);
      setEnable(false);
      return;
    }
    sendMessageTestResultFailCase(FactoryTestMain.WHAT_RESULT_FAIL_CASE, (byte)2);
  }
  
  private void registerTestReceiver()
  {
    FtUtil.log_d("FactoryTestMainInnerBT", "registerTestReceiver", null);
    IntentFilter localIntentFilter = new IntentFilter();
    if (FactoryTestManager.getEnable(2)) {
      localIntentFilter.addAction("com.sec.factory.intent.ACTION_BT_RESPONSE");
    }
    this.mContext.registerReceiver(this.mBroadcastReceiver, localIntentFilter);
  }
  
  private void sendMessageTestResult(int paramInt, byte paramByte1, byte paramByte2)
  {
    FtUtil.log_e("FactoryTestMainInnerBT", "sendTestResultMessage", "what=" + paramInt + " , id=" + paramByte1 + " , result=" + paramByte2);
    Message localMessage = this.mHandlerResult.obtainMessage(paramInt, paramByte1, paramByte2);
    this.mHandlerResult.sendMessage(localMessage);
  }
  
  private void sendMessageTestResultFailCase(int paramInt, byte paramByte)
  {
    FtUtil.log_e("FactoryTestMainInnerBT", "sendTestResultMessage", "what=" + paramInt + " , id=" + paramByte);
    Message localMessage = this.mHandlerResult.obtainMessage(paramInt, Byte.valueOf(paramByte));
    this.mHandlerResult.sendMessage(localMessage);
  }
  
  private void unregisterTestReceiver()
  {
    FtUtil.log_d("FactoryTestMainInnerBT", "unregisterTestReceiver", null);
    this.mContext.unregisterReceiver(this.mBroadcastReceiver);
  }
  
  public boolean getEnable()
  {
    FtUtil.log_d("FactoryTestMainInnerBT", "getEnable", "mEnable=" + this.mEnable);
    return this.mEnable;
  }
  
  public void setEnable(boolean paramBoolean)
  {
    this.mEnable = paramBoolean;
    FtUtil.log_d("FactoryTestMainInnerBT", "setEnable", "mEnable=" + this.mEnable);
  }
  
  public void startBluetooth()
  {
    FtUtil.log_d("FactoryTestMainInnerBT", "startBluetooth", null);
    mBtTurnningCount = 1 + mBtTurnningCount;
    setEnable(true);
    registerTestReceiver();
    ModuleCommunication localModuleCommunication = ModuleCommunication.instance(this.mContext);
    if (!localModuleCommunication.isEnabledBtDevice())
    {
      FtUtil.log_d("FactoryTestMainInnerBT", "startBluetooth", "BT Enable : FALSE => BT Activation");
      localModuleCommunication.btActivation();
      this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(3, "BT Activating"));
      this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(2, "Bluetooth state is changed to On!!"));
      return;
    }
    FtUtil.log_d("FactoryTestMainInnerBT", "startBluetooth", "BT Enable : TRUE => BT Discoverable");
    localModuleCommunication.btSetDiscoverable();
    changeBluetoothState("ON");
  }
  
  public void stopBluetooth()
  {
    mBtTurnningCount = -1 + mBtTurnningCount;
    if (mBtTurnningCount == 0)
    {
      FtUtil.log_d("FactoryTestMainInnerBT", "stopBluetooth", null);
      ModuleCommunication localModuleCommunication = ModuleCommunication.instance(this.mContext);
      if (localModuleCommunication.isEnabledBtDevice())
      {
        localModuleCommunication.btDeactivation();
        this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(3, "BT Deactivating"));
        this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(2, "Bluetooth state is changed to Off!!"));
      }
      if ("CTC".equals(mSalesCode)) {
        unregisterTestReceiver();
      }
    }
    if (!"CTC".equals(mSalesCode)) {
      unregisterTestReceiver();
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestMainInnerBT
 * JD-Core Version:    0.7.1
 */