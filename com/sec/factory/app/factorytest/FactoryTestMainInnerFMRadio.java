package com.sec.factory.app.factorytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import com.sec.factory.modules.ModuleCommunication;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Kernel;

public class FactoryTestMainInnerFMRadio
{
  public static ModuleCommunication mModuleCommunication = null;
  private final String CLASS_NAME = "FactoryTestMainInnerFMRADIO";
  private Context mContext;
  private boolean mEarphonePlugged = false;
  private BroadcastReceiver mFMRADIOReceiver;
  private Handler mHandlerFeedback;
  private Handler mHandlerResult;
  
  public FactoryTestMainInnerFMRadio(Context paramContext, Handler paramHandler1, Handler paramHandler2)
  {
    this.mContext = paramContext;
    this.mHandlerResult = paramHandler1;
    this.mHandlerFeedback = paramHandler2;
  }
  
  private void changeFMRADIOstate()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("test.mode.radio.on.freq");
    localIntentFilter.addAction("test.mode.radio.on.response");
    this.mFMRADIOReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent) {}
    };
    this.mContext.registerReceiver(this.mFMRADIOReceiver, localIntentFilter);
    this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(3, "FMRADIO : 98.5Mhz"));
    sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)53, (byte)80);
  }
  
  private void sendMessageTestResult(int paramInt, byte paramByte1, byte paramByte2)
  {
    FtUtil.log_e("FactoryTestMainInnerFMRADIO", "sendTestResultMessage", "what=" + paramInt + " , id=" + paramByte1 + " , result=" + paramByte2);
    Message localMessage = this.mHandlerResult.obtainMessage(paramInt, paramByte1, paramByte2);
    this.mHandlerResult.sendMessage(localMessage);
  }
  
  public boolean isEarphonePlugged()
  {
    String str = Support.Kernel.read("EARJACK_PLUGGED");
    if (str != null) {
      if (!"1".equals(str)) {
        break label31;
      }
    }
    label31:
    for (boolean bool = true;; bool = false)
    {
      this.mEarphonePlugged = bool;
      return this.mEarphonePlugged;
    }
  }
  
  public void startFMRADIO()
  {
    FtUtil.log_d("FactoryTestMainInnerFMRADIO", "startFMRADIO", "Start FMRADIO");
    if (this.mFMRADIOReceiver == null)
    {
      Intent localIntent1 = new Intent("test.mode.radio.on.freq");
      localIntent1.putExtra("frequency", "98500");
      if (isEarphonePlugged()) {
        localIntent1.putExtra("output", "earphone");
      }
      for (;;)
      {
        localIntent1.setFlags(268435456);
        this.mContext.startActivity(localIntent1);
        FtUtil.log_i("FactoryTestMainInnerFMRADIO", "send Frequency", "Frequency : 98500");
        changeFMRADIOstate();
        return;
        localIntent1.putExtra("output", "speaker");
      }
    }
    this.mContext.unregisterReceiver(this.mFMRADIOReceiver);
    this.mFMRADIOReceiver = null;
    this.mHandlerFeedback.sendEmptyMessage(4);
    Intent localIntent2 = new Intent("test.mode.radio.off");
    localIntent2.setFlags(268435456);
    this.mContext.startActivity(localIntent2);
  }
  
  public void unregisterFMRADIOReceiver()
  {
    if (this.mFMRADIOReceiver != null)
    {
      this.mContext.unregisterReceiver(this.mFMRADIOReceiver);
      this.mFMRADIOReceiver = null;
      FtUtil.log_i("FactoryTestMainInnerFMRADIO", "unregisterFMRADIOReceiver", null);
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestMainInnerFMRadio
 * JD-Core Version:    0.7.1
 */