package com.sec.factory.app.factorytest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.SystemProperties;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.NVAccessor;
import com.sec.factory.support.Support.Feature;

public class FactoryTestSupportService
  extends Service
{
  private AudioManager mAudioManager = null;
  FactoryTestPhone mFactoryPhone = null;
  private PhoneStateListener mPhoneStateListener = new PhoneStateListener()
  {
    public void onCallStateChanged(int paramAnonymousInt, String paramAnonymousString)
    {
      switch (paramAnonymousInt)
      {
      default: 
        return;
      case 2: 
        FtUtil.log_i("FactoryTestSupportService", "mPhoneStateListener.onCallStateChanged", "call state offhook");
        FactoryTestSupportService.this.checkfordualcp('\035');
        return;
      case 0: 
        FtUtil.log_i("FactoryTestSupportService", "mPhoneStateListener.onCallStateChanged", "call state idle");
        return;
      }
      FtUtil.log_i("FactoryTestSupportService", "mPhoneStateListener.onCallStateChanged", "call state ringing");
    }
  };
  private PhoneStateListener mPhoneStateListener2 = new PhoneStateListener()
  {
    public void onCallStateChanged(int paramAnonymousInt, String paramAnonymousString)
    {
      switch (paramAnonymousInt)
      {
      default: 
        return;
      case 2: 
        FtUtil.log_i("FactoryTestSupportService", "mPhoneStateListener2.onCallStateChanged", "call2 state offhook");
        FactoryTestSupportService.this.updateDetectedItem('7');
        return;
      case 0: 
        FtUtil.log_i("FactoryTestSupportService", "mPhoneStateListener2.onCallStateChanged", "call2 state idle");
        return;
      }
      FtUtil.log_i("FactoryTestSupportService", "mPhoneStateListener2.onCallStateChanged", "call2 state ringing");
    }
  };
  private TelephonyManager mTelePhony;
  private TelephonyManager mTelePhony2;
  
  private void checkfordualcp(char paramChar)
  {
    if (Support.Feature.getBoolean("SUPPORT_DUALMODE"))
    {
      if ("XMM6262".equals(SystemProperties.get("ril.modem.board", "Unknown")))
      {
        FtUtil.log_d("FactoryTestSupportService", "checkfordualcp", " is Cp2");
        updateDetectedItem('7');
        return;
      }
      FtUtil.log_d("FactoryTestSupportService", "checkfordualcp", " is Cp1");
      updateDetectedItem('\035');
      return;
    }
    updateDetectedItem(paramChar);
  }
  
  private void upDateNVAPO(char paramChar)
  {
    FtUtil.log_i("FactoryTestSupportService", "upDateNVAPO", String.valueOf(paramChar));
    NVAccessor.setNV((byte)paramChar, (byte)80);
  }
  
  private void upDateNVCPO(char paramChar)
  {
    FtUtil.log_i("FactoryTestSupportService", "upDateNVCPO", String.valueOf(paramChar));
    if (this.mFactoryPhone == null)
    {
      this.mFactoryPhone = new FactoryTestPhone(this);
      this.mFactoryPhone.bindSecPhoneService();
    }
    this.mFactoryPhone.updateNVItem((byte)paramChar, (byte)80);
  }
  
  private void updateDetectedItem(char paramChar)
  {
    switch (paramChar)
    {
    default: 
      return;
    }
    updateNV(paramChar);
    sendBroadcast(new Intent("com.sec.factory.app.factorytest.CALL_CONNECTION_DETECTED"));
  }
  
  private void updateNV(char paramChar)
  {
    if (FtUtil.isFactoryAppAPO() == true)
    {
      upDateNVAPO(paramChar);
      return;
    }
    upDateNVCPO(paramChar);
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    FtUtil.log_i("FactoryTestSupportService", "onCreate", null);
    this.mTelePhony = ((TelephonyManager)getSystemService("phone"));
    this.mTelePhony.listen(this.mPhoneStateListener, 32);
    if ((Support.Feature.getBoolean("SUPPORT_2ND_RIL")) && (Support.Feature.getBoolean("SUPPORT_DUAL_STANBY")))
    {
      this.mTelePhony2 = ((TelephonyManager)getSystemService("phone2"));
      this.mTelePhony2.listen(this.mPhoneStateListener2, 32);
    }
    this.mAudioManager = ((AudioManager)getSystemService("audio"));
    this.mAudioManager.registerMediaButtonEventReceiver(new ComponentName(getPackageName(), MediaButtonIntentReceiver.class.getName()));
  }
  
  public void onDestroy()
  {
    FtUtil.log_i("FactoryTestSupportService", "onDestroy", null);
    this.mTelePhony.listen(this.mPhoneStateListener, 0);
    this.mTelePhony.listen(this.mPhoneStateListener2, 0);
    if (this.mFactoryPhone != null)
    {
      this.mFactoryPhone.unbindSecPhoneService();
      this.mFactoryPhone = null;
    }
    this.mAudioManager.unregisterMediaButtonEventReceiver(new ComponentName(getPackageName(), MediaButtonIntentReceiver.class.getName()));
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    FtUtil.log_i("FactoryTestSupportService", "onStartCommand", null);
    return 0;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestSupportService
 * JD-Core Version:    0.7.1
 */