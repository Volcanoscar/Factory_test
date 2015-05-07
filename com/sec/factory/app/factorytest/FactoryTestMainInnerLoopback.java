package com.sec.factory.app.factorytest;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;

public class FactoryTestMainInnerLoopback
{
  private final String CLASS_NAME = "FactoryTestMainInnerLoopback";
  boolean isSupportRCV = true;
  private Context mContext;
  private Handler mHandlerFeedback;
  private Handler mHandlerResult;
  private ListView mListView;
  
  public FactoryTestMainInnerLoopback(Context paramContext, Handler paramHandler1, Handler paramHandler2, ListView paramListView)
  {
    this.mContext = paramContext;
    this.mHandlerResult = paramHandler1;
    this.mHandlerFeedback = paramHandler2;
    this.mListView = paramListView;
    this.isSupportRCV = Support.Feature.getBoolean("SUPPORT_RCV", true);
  }
  
  private void sendMessageTestResult(int paramInt, byte paramByte1, byte paramByte2)
  {
    FtUtil.log_e("FactoryTestMainInnerLoopback", "sendTestResultMessage", "what=" + paramInt + " , id=" + paramByte1 + " , result=" + paramByte2);
    Message localMessage = this.mHandlerResult.obtainMessage(paramInt, paramByte1, paramByte2);
    this.mHandlerResult.sendMessage(localMessage);
  }
  
  private void setPhoneType()
  {
    FtUtil.log_d("FactoryTestMainInnerLoopback", "setPhoneType testid=", FactoryTestMain.CURRENT_TEST_ID + "loopback ID = " + 42);
    if (Support.Feature.getBoolean("SUPPORT_DUALMODE", false)) {
      if (FactoryTestMain.CURRENT_TEST_ID == 42)
      {
        FtUtil.log_d("FactoryTestMainInnerLoopback", "setPhoneType", "CP2");
        ModuleAudio.instance(this.mContext).setPhoneType("cp2");
        FactoryTestMain.IsLoopBack2 = true;
      }
    }
    do
    {
      do
      {
        return;
      } while (FactoryTestMain.CURRENT_TEST_ID != 1);
      FtUtil.log_d("FactoryTestMainInnerLoopback", "setPhoneType", "CP1");
      ModuleAudio.instance(this.mContext).setPhoneType("cp1");
      FactoryTestMain.IsLoopBack2 = false;
      return;
      if (FactoryTestMain.CURRENT_TEST_ID == 42)
      {
        FtUtil.log_d("FactoryTestMainInnerLoopback", "setPhoneType", "CP2");
        ModuleAudio.instance(this.mContext).setPhoneType("cp2");
        FactoryTestMain.IsLoopBack2 = true;
        return;
      }
    } while (FactoryTestMain.CURRENT_TEST_ID != 1);
    FtUtil.log_d("FactoryTestMainInnerLoopback", "setPhoneType", "CP1");
    ModuleAudio.instance(this.mContext).setPhoneType("cp1");
    FactoryTestMain.IsLoopBack2 = false;
  }
  
  public void changeLoopbackRoute(boolean paramBoolean)
  {
    ModuleAudio localModuleAudio = ModuleAudio.instance(this.mContext);
    localModuleAudio.getMicCount();
    boolean bool = ModuleAudio.isSupportSecondMicTest();
    int i = localModuleAudio.getCurrentLoopbackPath();
    if (paramBoolean)
    {
      FtUtil.log_d("FactoryTestMainInnerLoopback", "changeLoopbackRoute", "KeyEvent");
      if (bool) {
        if (i == -1)
        {
          localModuleAudio.setLoopbackPath(6);
          if ((localModuleAudio.getCurrentLoopbackPath() == 1) && (this.isSupportRCV))
          {
            FtUtil.log_d("FactoryTestMainInnerLoopback", "changeLoopbackRoute", "Update NV: Pass");
            if (FactoryTestMain.CURRENT_TEST_ID != 42) {
              break label216;
            }
            sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)42, (byte)80);
          }
        }
      }
    }
    for (;;)
    {
      FactoryTestManager.notifyDataSetChanged();
      return;
      if (i != 6) {
        break;
      }
      localModuleAudio.setLoopbackPath(1);
      break;
      localModuleAudio.setLoopbackPath(i + 1);
      break;
      FtUtil.log_d("FactoryTestMainInnerLoopback", "changeLoopbackRoute", "Earphone Plugged Event");
      if (localModuleAudio.isEarphonePlugged())
      {
        localModuleAudio.setLoopbackPath(5);
        break;
      }
      if ((localModuleAudio.getPreviousLoopbackPath() == -1) || (localModuleAudio.getPreviousLoopbackPath() == 5))
      {
        if (this.isSupportRCV == true)
        {
          if (bool)
          {
            localModuleAudio.setLoopbackPath(6);
            break;
          }
          localModuleAudio.setLoopbackPath(0);
          break;
        }
        localModuleAudio.setLoopbackPath(1);
        break;
      }
      localModuleAudio.setLoopbackPath(localModuleAudio.getPreviousLoopbackPath());
      break;
      label216:
      sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)1, (byte)80);
    }
  }
  
  public void startLoopback()
  {
    FtUtil.log_d("FactoryTestMainInnerLoopback", "startLoopback", "Start Loopback");
    ModuleAudio localModuleAudio = ModuleAudio.instance(this.mContext);
    ModuleCommon localModuleCommon = ModuleCommon.instance(this.mContext);
    if (!localModuleAudio.isDoingLoopback())
    {
      if ((Support.Feature.getBoolean("SUPPORT_2ND_RIL")) || (Support.Feature.getBoolean("SUPPORT_DUALMODE", false))) {
        setPhoneType();
      }
      this.mListView.setSoundEffectsEnabled(false);
      if (localModuleAudio.isEarphonePlugged()) {
        if (localModuleCommon.isVoiceCapable()) {
          localModuleAudio.startLoopback(5, 0);
        }
      }
      for (;;)
      {
        this.mHandlerFeedback.sendMessage(this.mHandlerFeedback.obtainMessage(3, "Loopback On"));
        FactoryTestManager.notifyDataSetChanged();
        return;
        localModuleAudio.startLoopback(5, 2);
        continue;
        if ((localModuleCommon.isVoiceCapable()) && (this.isSupportRCV))
        {
          if (ModuleAudio.isSupportSecondMicTest()) {
            localModuleAudio.startLoopback(6, 0);
          } else {
            localModuleAudio.startLoopback(0, 0);
          }
        }
        else if ((localModuleCommon.isVoiceCapable()) && (!this.isSupportRCV))
        {
          localModuleAudio.startLoopback(1, 0);
          if (FactoryTestMain.CURRENT_TEST_ID == 42) {
            sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)42, (byte)80);
          } else {
            sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)1, (byte)80);
          }
        }
        else
        {
          localModuleAudio.startLoopback(1, 2);
          if (FactoryTestMain.CURRENT_TEST_ID == 42) {
            sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)42, (byte)80);
          } else {
            sendMessageTestResult(FactoryTestMain.WHAT_RESULT, (byte)1, (byte)80);
          }
        }
      }
    }
    this.mListView.setSoundEffectsEnabled(true);
    localModuleAudio.stopLoopback();
    this.mHandlerFeedback.sendEmptyMessage(4);
    FactoryTestManager.notifyDataSetChanged();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestMainInnerLoopback
 * JD-Core Version:    0.7.1
 */