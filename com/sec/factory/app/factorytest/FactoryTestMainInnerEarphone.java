package com.sec.factory.app.factorytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.FtUtil;

public class FactoryTestMainInnerEarphone
{
  private static int mStatusEarJack = 0;
  private final String CLASS_NAME = "FactoryTestMainInnerEarphone";
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      FtUtil.log_d("FactoryTestMainInnerEarphone", "mBroadcastReceiver.onReceive", "action=" + str);
      boolean bool;
      if ("android.intent.action.HEADSET_PLUG".equals(paramAnonymousIntent.getAction()))
      {
        if (paramAnonymousIntent.getIntExtra("state", 0) != 1) {
          break label161;
        }
        bool = true;
        FtUtil.log_d("FactoryTestMainInnerEarphone", "ACTION_HEADSET_PLUG state : " + bool);
        if ((bool != true) || (FactoryTestMainInnerEarphone.mStatusEarJack != 0)) {
          break label167;
        }
        FactoryTestMainInnerEarphone.access$002(1);
        label99:
        FactoryTestMainInnerEarphone.this.startEarphone(bool);
        if (ModuleAudio.instance(FactoryTestMainInnerEarphone.this.mContext).isDoingLoopback()) {
          FactoryTestMainInnerEarphone.this.mTestInnerLoopback.changeLoopbackRoute(false);
        }
      }
      if (FactoryTestManager.getAllitemPass()) {
        FactoryTestMainInnerEarphone.this.mListView.setBackgroundColor(-16776961);
      }
      for (FactoryTestMainAdapter.ALL_PASS_STATE = true;; FactoryTestMainAdapter.ALL_PASS_STATE = false)
      {
        FactoryTestManager.notifyDataSetChanged();
        return;
        label161:
        bool = false;
        break;
        label167:
        if ((!bool) && (FactoryTestMainInnerEarphone.mStatusEarJack == 1))
        {
          FactoryTestMainInnerEarphone.access$002(0);
          break label99;
        }
        FtUtil.log_d("FactoryTestMainInnerEarphone", "This is duplicated called, ACTION_HEADSET_PLUG state : " + bool);
        return;
        FactoryTestMainInnerEarphone.this.mListView.setBackgroundColor(-1);
      }
    }
  };
  private Context mContext;
  private Handler mHandlerFeedback;
  private Handler mHandlerResult;
  private ListView mListView;
  private FactoryTestMainInnerLoopback mTestInnerLoopback;
  
  public FactoryTestMainInnerEarphone(Context paramContext, Handler paramHandler1, Handler paramHandler2, ListView paramListView, FactoryTestMainInnerLoopback paramFactoryTestMainInnerLoopback)
  {
    this.mContext = paramContext;
    this.mHandlerResult = paramHandler1;
    this.mHandlerFeedback = paramHandler2;
    this.mListView = paramListView;
    this.mTestInnerLoopback = paramFactoryTestMainInnerLoopback;
    registerTestReceiver();
  }
  
  private void registerTestReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    if (FactoryTestManager.getEnable(12)) {
      localIntentFilter.addAction("android.intent.action.HEADSET_PLUG");
    }
    this.mContext.registerReceiver(this.mBroadcastReceiver, localIntentFilter);
  }
  
  private void sendMessageTestResult(int paramInt, byte paramByte1, byte paramByte2)
  {
    FtUtil.log_e("FactoryTestMainInnerEarphone", "sendTestResultMessage", "what=" + paramInt + " , id=" + paramByte1 + " , result=" + paramByte2);
    Message localMessage = this.mHandlerResult.obtainMessage(paramInt, paramByte1, paramByte2);
    this.mHandlerResult.sendMessage(localMessage);
  }
  
  private void stopSound()
  {
    ModuleAudio localModuleAudio = ModuleAudio.instance(this.mContext);
    if (localModuleAudio.isPlayingSound())
    {
      localModuleAudio.stopMedia();
      this.mHandlerFeedback.sendEmptyMessage(4);
    }
  }
  
  public void startEarkey(boolean paramBoolean)
  {
    if ((FactoryTestManager.getEnable(13)) && (ModuleAudio.instance(this.mContext).isEarphonePlugged()))
    {
      if (paramBoolean)
      {
        this.mHandlerFeedback.removeMessages(1);
        this.mHandlerFeedback.sendEmptyMessage(0);
      }
    }
    else {
      return;
    }
    this.mHandlerFeedback.sendEmptyMessageDelayed(1, 500L);
    sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)13, (byte)80);
  }
  
  public void startEarphone(boolean paramBoolean)
  {
    ModuleAudio localModuleAudio = ModuleAudio.instance(this.mContext);
    FtUtil.log_d("FactoryTestMainInnerEarphone", "startEarphone", "Start Earphonestate: " + paramBoolean + "IsEarplugged:  " + localModuleAudio.isEarphonePlugged());
    if ((paramBoolean == true) && (localModuleAudio.isEarphonePlugged()))
    {
      FtUtil.log_i("FactoryTestMainInnerEarphone", "startEarphone", "Earphone Plugged=true");
      if (ModuleAudio.instance(this.mContext).isPlayingSound()) {
        ModuleAudio.instance(this.mContext).setAudioPath(2);
      }
      this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(2, "Earphone Detected!"));
      sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)12, (byte)80);
      return;
    }
    FtUtil.log_i("FactoryTestMainInnerEarphone", "stopEarphone", "Earphone Plugged=false");
    if (ModuleDevice.instance(this.mContext).isVibrating())
    {
      stopSound();
      ModuleDevice.instance(this.mContext).stopVibrate();
    }
    if (ModuleAudio.instance(this.mContext).isPlayingSound()) {
      ModuleAudio.instance(this.mContext).setAudioPath(0);
    }
    this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(2, "Earphone Removed!"));
  }
  
  public void unregisterTestReceiver()
  {
    if (this.mBroadcastReceiver != null)
    {
      this.mContext.unregisterReceiver(this.mBroadcastReceiver);
      this.mBroadcastReceiver = null;
      FtUtil.log_i("FactoryTestMainInnerEarphone", "unregisterTestReceiver", null);
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestMainInnerEarphone
 * JD-Core Version:    0.7.1
 */