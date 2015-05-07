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
import com.sec.factory.modules.ModulePower;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;

public class FactoryTestMainInner
{
  private static boolean mPluggedChargeUSB = false;
  private final String CLASS_NAME = "FactoryTestMainInner";
  private final int PLAYING_LEFT = 1;
  private final int PLAYING_RIGHT = 2;
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      FtUtil.log_d("FactoryTestMainInner", "mBroadcastReceiver.onReceive", "action=" + str1);
      if (str1.equals("com.sec.factory.app.factorytest.CALL_CONNECTION_DETECTED"))
      {
        FtUtil.log_i("FactoryTestMainInner", "mBroadcastReceiver.onReceive", "INTENT_CALL");
        FactoryTestMainInner.this.sendMessageTestResult(FactoryTestMain.WHAT_RESULT_WITHOUT_NV, (byte)16, (byte)80);
        if (!FactoryTestManager.getAllitemPass()) {
          break label248;
        }
        FactoryTestMainInner.this.mListView.setBackgroundColor(-16776961);
      }
      for (FactoryTestMainAdapter.ALL_PASS_STATE = true;; FactoryTestMainAdapter.ALL_PASS_STATE = false)
      {
        FactoryTestManager.notifyDataSetChanged();
        return;
        if (!"android.intent.action.BATTERY_CHANGED".equals(paramAnonymousIntent.getAction())) {
          break;
        }
        int i = paramAnonymousIntent.getIntExtra("plugged", 0);
        FtUtil.log_d("FactoryTestMainInner", "Battery State : " + i);
        boolean bool;
        if (Support.Kernel.isExistFile(Support.Kernel.getFilePath("WIRELESS_CHARGE_ONLINE")))
        {
          FtUtil.log_d("FactoryTestMainInner", "Check wireless online value");
          String str2 = Support.Kernel.read("WIRELESS_CHARGE_ONLINE");
          FtUtil.log_d("FactoryTestMainInner", "online: " + str2);
          if ("10".equals(str2)) {
            bool = true;
          }
        }
        for (;;)
        {
          FtUtil.log_d("FactoryTestMainInner", "Wireless Charge: " + bool);
          if (!bool) {
            break;
          }
          return;
          bool = false;
          continue;
          FtUtil.log_d("FactoryTestMainInner", "File does not exist");
          bool = false;
        }
        label248:
        FactoryTestMainInner.this.mListView.setBackgroundColor(-1);
      }
    }
  };
  private Context mContext;
  private Handler mHandlerFeedback;
  private Handler mHandlerResult;
  private ListView mListView;
  private int mPlayingSpeaker = 0;
  
  public FactoryTestMainInner(Context paramContext, Handler paramHandler1, Handler paramHandler2, ListView paramListView)
  {
    this.mContext = paramContext;
    this.mHandlerResult = paramHandler1;
    this.mHandlerFeedback = paramHandler2;
    this.mListView = paramListView;
    registerTestReceiver();
  }
  
  private void playSound(int paramInt, boolean paramBoolean)
  {
    ModuleAudio localModuleAudio = ModuleAudio.instance(this.mContext);
    if (!localModuleAudio.isPlayingSound()) {
      localModuleAudio.stopMedia();
    }
    localModuleAudio.playMedia(paramInt, true);
    if (paramBoolean) {
      this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(3, "Media playing"));
    }
  }
  
  private void registerTestReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    if (FactoryTestManager.getEnable(18)) {
      localIntentFilter.addAction("android.intent.action.BATTERY_CHANGED");
    }
    if (FactoryTestManager.getEnable(16)) {
      localIntentFilter.addAction("com.sec.factory.app.factorytest.CALL_CONNECTION_DETECTED");
    }
    this.mContext.registerReceiver(this.mBroadcastReceiver, localIntentFilter);
  }
  
  private void sendMessageTestResult(int paramInt, byte paramByte1, byte paramByte2)
  {
    FtUtil.log_e("FactoryTestMainInner", "sendTestResultMessage", "what=" + paramInt + " , id=" + paramByte1 + " , result=" + paramByte2);
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
  
  public void startBattery()
  {
    FtUtil.log_d("FactoryTestMainInner", "startBattery", "Start Battery");
    byte b;
    if (ModulePower.instance(this.mContext).isBatteryAuthPass()) {
      b = 80;
    }
    for (String str = "Battery auth is PASS!";; str = "Battery auth is FAIL!")
    {
      sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)14, b);
      this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(2, str));
      return;
      b = 70;
    }
  }
  
  public void startLowFrequency()
  {
    FtUtil.log_d("FactoryTestMainInner", "startLowFrequency", null);
    if (ModuleAudio.instance(this.mContext).isPlayingSound())
    {
      FtUtil.log_e("FactoryTestMainInner", "start low frequency", "Stop sound");
      stopSound();
      try
      {
        FtUtil.log_d("FactoryTestMainInner", "startLowFrequency : delay", " : 500ms");
        Thread.sleep(500L);
        ModuleAudio.instance(this.mContext).setAudioPath(4);
        this.mListView.setSoundEffectsEnabled(true);
        return;
      }
      catch (InterruptedException localInterruptedException2)
      {
        for (;;)
        {
          localInterruptedException2.printStackTrace();
        }
      }
    }
    try
    {
      FtUtil.log_d("FactoryTestMainInner", "startLowFrequency : delay", " : 20ms");
      Thread.sleep(20L);
      this.mListView.setSoundEffectsEnabled(false);
      ModuleAudio.instance(this.mContext).setAudioPath(1);
      ModuleAudio.instance(this.mContext).setStreamMusicVolumeMax();
      playSound(2131034118, true);
      sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)33, (byte)80);
      return;
    }
    catch (InterruptedException localInterruptedException1)
    {
      for (;;)
      {
        localInterruptedException1.printStackTrace();
      }
    }
  }
  
  public void startSDCard()
  {
    boolean bool = true;
    FtUtil.log_e("FactoryTestMainInner", "startSDCard", null);
    ModuleDevice localModuleDevice = ModuleDevice.instance(this.mContext);
    if (!"MSM8625".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"))) {
      bool = localModuleDevice.isDetectExternalMemory();
    }
    String str;
    if ((bool) && (localModuleDevice.isMountedStorage(1)))
    {
      str = "SD card test is PASS!";
      sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)3, (byte)80);
    }
    for (;;)
    {
      this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(2, str));
      return;
      str = "SD card test is FAIL!";
    }
  }
  
  public void startSIMCard()
  {
    FtUtil.log_d("FactoryTestMainInner", "startSIMCard", null);
    String str;
    if (ModuleDevice.instance(this.mContext).isSimCardExist())
    {
      str = "SIM card test is PASS!";
      sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)25, (byte)80);
    }
    for (;;)
    {
      this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(2, str));
      return;
      str = "SIM card test is FAIL!";
    }
  }
  
  public void startSIMCard2()
  {
    FtUtil.log_d("FactoryTestMainInner", "startSIMCard2", null);
    String str;
    if (ModuleDevice.instance(this.mContext).isSimCardExist2())
    {
      str = "SIM card test 2 is PASS!";
      sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)47, (byte)80);
    }
    for (;;)
    {
      this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(2, str));
      return;
      str = "SIM card test 2 is FAIL!";
    }
  }
  
  public void startSpeaker()
  {
    FtUtil.log_d("FactoryTestMainInner", "startSpeaker", null);
    if (ModuleAudio.instance(this.mContext).isPlayingSound())
    {
      stopSound();
      ModuleAudio.instance(this.mContext).setAudioPath(4);
      if (this.mPlayingSpeaker == 2)
      {
        if (!ModuleAudio.instance(this.mContext).isEarphonePlugged()) {
          break label93;
        }
        ModuleAudio.instance(this.mContext).setAudioPath(2);
      }
      for (;;)
      {
        playSound(2131034122, true);
        this.mPlayingSpeaker = 1;
        sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)4, (byte)80);
        return;
        label93:
        ModuleAudio.instance(this.mContext).setAudioPath(0);
      }
    }
    if (ModuleAudio.instance(this.mContext).isEarphonePlugged())
    {
      ModuleAudio.instance(this.mContext).setAudioPath(2);
      ModuleAudio.instance(this.mContext).setStreamMusicVolumeMax();
      if (ModuleDevice.instance(this.mContext).getSpeakerCount() <= 1) {
        break label193;
      }
      playSound(2131034122, true);
      this.mPlayingSpeaker = 1;
    }
    for (;;)
    {
      sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)4, (byte)80);
      return;
      ModuleAudio.instance(this.mContext).setAudioPath(0);
      break;
      label193:
      playSound(2131034113, true);
    }
  }
  
  public void startSpeaker_r()
  {
    FtUtil.log_d("FactoryTestMainInner", "startSpeaker_r", null);
    if (ModuleAudio.instance(this.mContext).isPlayingSound())
    {
      stopSound();
      ModuleAudio.instance(this.mContext).setAudioPath(4);
      if (this.mPlayingSpeaker == 1)
      {
        if (!ModuleAudio.instance(this.mContext).isEarphonePlugged()) {
          break label93;
        }
        ModuleAudio.instance(this.mContext).setAudioPath(2);
      }
      for (;;)
      {
        playSound(2131034123, true);
        this.mPlayingSpeaker = 2;
        sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)5, (byte)80);
        return;
        label93:
        ModuleAudio.instance(this.mContext).setAudioPath(0);
      }
    }
    if (ModuleAudio.instance(this.mContext).isEarphonePlugged()) {
      ModuleAudio.instance(this.mContext).setAudioPath(2);
    }
    for (;;)
    {
      ModuleAudio.instance(this.mContext).setStreamMusicVolumeMax();
      playSound(2131034123, true);
      sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)5, (byte)80);
      this.mPlayingSpeaker = 2;
      return;
      ModuleAudio.instance(this.mContext).setAudioPath(0);
    }
  }
  
  public void unregisterTestReceiver()
  {
    if (this.mBroadcastReceiver != null)
    {
      this.mContext.unregisterReceiver(this.mBroadcastReceiver);
      this.mBroadcastReceiver = null;
      FtUtil.log_i("FactoryTestMainInner", "unregisterTestReceiver", null);
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestMainInner
 * JD-Core Version:    0.7.1
 */