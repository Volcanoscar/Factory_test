package com.sec.factory.app.factorytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.FtUtil;

public class FactoryTestMainInnerOTG
{
  private final String CLASS_NAME = "FactoryTestMainInnerOTG";
  private Context mContext;
  private Handler mHandlerFeedback;
  private Handler mHandlerResult;
  private BroadcastReceiver mOTGReceiver = null;
  
  public FactoryTestMainInnerOTG(Context paramContext, Handler paramHandler1, Handler paramHandler2)
  {
    this.mContext = paramContext;
    this.mHandlerResult = paramHandler1;
    this.mHandlerFeedback = paramHandler2;
  }
  
  private void changeOTGState()
  {
    if (this.mOTGReceiver == null)
    {
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction("com.sec.factory.action.ACTION_USB_STORAGE_MOUNTED");
      localIntentFilter.addAction("com.sec.factory.action.ACTION_USB_STORAGE_UNMOUNTED");
      this.mOTGReceiver = new BroadcastReceiver()
      {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
        {
          String str = paramAnonymousIntent.getAction();
          if (str.equals("com.sec.factory.action.ACTION_USB_STORAGE_MOUNTED"))
          {
            FtUtil.log_i("FactoryTestMainInnerOTG", "mOTGReceiver.onReceive", "ACTION_USB_STORAGE_MOUNTED");
            if (ModuleDevice.instance(paramAnonymousContext).isMountedStorage(2))
            {
              FactoryTestMainInnerOTG.this.sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)24, (byte)80);
              FactoryTestMainInnerOTG.this.mHandlerFeedback.sendMessage(FactoryTestMainInnerOTG.this.mHandlerFeedback.obtainMessage(3, "Unmounting"));
              ModuleDevice.instance(paramAnonymousContext).unmount(2);
            }
          }
          while (!str.equals("com.sec.factory.action.ACTION_USB_STORAGE_UNMOUNTED"))
          {
            do
            {
              return;
            } while (!ModuleDevice.instance(paramAnonymousContext).isMountedStorage(3));
            FactoryTestMainInnerOTG.this.sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)24, (byte)80);
            FactoryTestMainInnerOTG.this.mHandlerFeedback.sendMessage(FactoryTestMainInnerOTG.this.mHandlerFeedback.obtainMessage(3, "Unmounting"));
            ModuleDevice.instance(paramAnonymousContext).unmount(3);
            return;
          }
          FtUtil.log_i("FactoryTestMainInnerOTG", "mOTGReceiver.onReceive", "ACTION_USB_STORAGE_UNMOUNTED");
          if (!ModuleDevice.instance(paramAnonymousContext).isMountedStorage(2)) {}
          for (;;)
          {
            FactoryTestMainInnerOTG.this.changeOTGState();
            return;
            FactoryTestMainInnerOTG.this.sendMessageTestResultFailCase(FactoryTestMain.WHAT_RESULT_FAIL_CASE, (byte)24);
          }
        }
      };
      this.mContext.registerReceiver(this.mOTGReceiver, localIntentFilter);
      this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(3, "Waiting OTG"));
      return;
    }
    this.mContext.unregisterReceiver(this.mOTGReceiver);
    this.mOTGReceiver = null;
    this.mHandlerFeedback.sendEmptyMessage(4);
  }
  
  private void sendMessageTestResult(int paramInt, byte paramByte1, byte paramByte2)
  {
    FtUtil.log_e("FactoryTestMainInnerOTG", "sendTestResultMessage", "what=" + paramInt + " , id=" + paramByte1 + " , result=" + paramByte2);
    Message localMessage = this.mHandlerResult.obtainMessage(paramInt, paramByte1, paramByte2);
    this.mHandlerResult.sendMessage(localMessage);
  }
  
  private void sendMessageTestResultFailCase(int paramInt, byte paramByte)
  {
    FtUtil.log_e("FactoryTestMainInnerOTG", "sendTestResultMessage", "what=" + paramInt + " , id=" + paramByte);
    Message localMessage = this.mHandlerResult.obtainMessage(paramInt, Byte.valueOf(paramByte));
    this.mHandlerResult.sendMessage(localMessage);
  }
  
  public void startOTG()
  {
    FtUtil.log_d("FactoryTestMainInnerOTG", "startOTG", "Start OTG");
    changeOTGState();
  }
  
  public void unregisterOTGReceiver()
  {
    if (this.mOTGReceiver != null)
    {
      this.mContext.unregisterReceiver(this.mOTGReceiver);
      this.mOTGReceiver = null;
      FtUtil.log_i("FactoryTestMainInnerOTG", "unregisterOTGReceiver", null);
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestMainInnerOTG
 * JD-Core Version:    0.7.1
 */