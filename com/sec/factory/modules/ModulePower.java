package com.sec.factory.modules;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IPowerManager;
import android.os.IPowerManager.Stub;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.Settings.System;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Kernel;

public class ModulePower
  extends ModuleObject
{
  private static ModulePower mInstance = null;
  private PowerManager.WakeLock mMediaScanWakeLock = null;
  private PowerManager.WakeLock mPartialWakeLock = null;
  private PowerManager.WakeLock mWakeLock = null;
  
  private ModulePower(Context paramContext)
  {
    super(paramContext, "ModulePower");
    FtUtil.log_i(this.CLASS_NAME, "ModulePower", "Create ModulePower");
  }
  
  public static ModulePower instance(Context paramContext)
  {
    if (mInstance == null) {
      mInstance = new ModulePower(paramContext);
    }
    return mInstance;
  }
  
  public void doMediaScanWakeLock(boolean paramBoolean)
  {
    FtUtil.log_i(this.CLASS_NAME, "doMediaScanWakeLock", "wake=" + paramBoolean);
    if (paramBoolean == true)
    {
      if (this.mMediaScanWakeLock == null) {
        this.mMediaScanWakeLock = ((PowerManager)getSystemService("power")).newWakeLock(805306394, this.CLASS_NAME + "Media");
      }
      if (!this.mMediaScanWakeLock.isHeld())
      {
        this.mMediaScanWakeLock.acquire();
        FtUtil.log_i(this.CLASS_NAME, "doMediaScanWakeLock", "FULL WAKELOCK ON");
      }
    }
    while (this.mMediaScanWakeLock == null) {
      return;
    }
    if (this.mMediaScanWakeLock.isHeld())
    {
      this.mMediaScanWakeLock.release();
      FtUtil.log_i(this.CLASS_NAME, "doMediaScanWakeLock", "FULL WAKELOCK OFF");
    }
    this.mMediaScanWakeLock = null;
  }
  
  public void doPartialWakeLock(boolean paramBoolean)
  {
    FtUtil.log_i(this.CLASS_NAME, "doPartialWakeLock", "wake=" + paramBoolean);
    if (paramBoolean == true)
    {
      if (this.mPartialWakeLock == null) {
        this.mPartialWakeLock = ((PowerManager)getSystemService("power")).newWakeLock(1, this.CLASS_NAME + "Partial");
      }
      if (!this.mPartialWakeLock.isHeld())
      {
        this.mPartialWakeLock.acquire();
        FtUtil.log_i(this.CLASS_NAME, "doPartialWakeLock", "PARTIAL WAKELOCK ON");
      }
    }
    while (this.mPartialWakeLock == null) {
      return;
    }
    if (this.mPartialWakeLock.isHeld())
    {
      this.mPartialWakeLock.release();
      FtUtil.log_i(this.CLASS_NAME, "doPartialWakeLock", "PARTIAL WAKELOCK OFF");
    }
    this.mPartialWakeLock = null;
  }
  
  public void doWakeLock(boolean paramBoolean)
  {
    FtUtil.log_i(this.CLASS_NAME, "doWakeLock", "wake=" + paramBoolean);
    if (paramBoolean == true)
    {
      if (this.mWakeLock == null) {
        this.mWakeLock = ((PowerManager)getSystemService("power")).newWakeLock(805306394, this.CLASS_NAME);
      }
      if (!this.mWakeLock.isHeld())
      {
        this.mWakeLock.acquire();
        FtUtil.log_i(this.CLASS_NAME, "doWakeLock", "FULL WAKELOCK ON");
      }
    }
    while (this.mWakeLock == null) {
      return;
    }
    if (this.mWakeLock.isHeld())
    {
      this.mWakeLock.release();
      FtUtil.log_i(this.CLASS_NAME, "doWakeLock", "FULL WAKELOCK OFF");
    }
    this.mWakeLock = null;
  }
  
  public int getBrightness()
  {
    int i = Settings.System.getInt(getContentResolver(), "screen_brightness", 0);
    FtUtil.log_i(this.CLASS_NAME, "getBrightness", "brightness=" + i);
    return i;
  }
  
  public int getScreenBrightnessMode()
  {
    int i = Settings.System.getInt(getContentResolver(), "screen_brightness_mode", -1);
    FtUtil.log_i(this.CLASS_NAME, "getScreenBrightnessMode", "mode=" + i);
    return i;
  }
  
  public int getTouchLedTime()
  {
    return Settings.System.getInt(getContentResolver(), "button_key_light", 1500);
  }
  
  public void gotosleep()
  {
    FtUtil.log_i(this.CLASS_NAME, "goToSleep", "Power Sleep");
    ((PowerManager)getSystemService("power")).goToSleep(SystemClock.uptimeMillis());
  }
  
  public boolean isBatteryAuthPass()
  {
    return Integer.parseInt(Support.Kernel.read("BATTERY_AUTH")) == 1;
  }
  
  public boolean isHeldWakeLock()
  {
    if (this.mWakeLock == null) {
      return false;
    }
    return this.mWakeLock.isHeld();
  }
  
  public boolean isScreenOn()
  {
    return ((PowerManager)getSystemService("power")).isScreenOn();
  }
  
  public String readApChipTemp()
  {
    String str = Support.Kernel.read("APCHIP_TEMP_ADC");
    FtUtil.log_i(this.CLASS_NAME, "readApChipTemp", "adc=" + str);
    return str;
  }
  
  public String readBatteryAdcCal()
  {
    String str = Support.Kernel.read("BATTERY_VOLT_ADC_CAL");
    FtUtil.log_i(this.CLASS_NAME, "readBatteryAdcCal", "adc_cal=" + str);
    return str;
  }
  
  public String readBatteryCal()
  {
    if (Support.Kernel.isExistFileID("BATTERY_VOLT_ADC_CAL"))
    {
      String str = Support.Kernel.read("BATTERY_VOLT_ADC_CAL");
      FtUtil.log_i(this.CLASS_NAME, "readBatteryCal", "cal=" + str);
      return str;
    }
    return null;
  }
  
  public String readBatteryTemp()
  {
    String str1 = Support.Kernel.read("BATTERY_TEMP");
    FtUtil.log_i(this.CLASS_NAME, "readBatteryTemp", "sysfs temp=" + str1);
    int i;
    if (str1.length() >= 3)
    {
      int j = Integer.parseInt(str1.substring(2));
      i = Integer.parseInt(str1.substring(0, 2));
      if (j >= 5) {
        i++;
      }
    }
    for (;;)
    {
      String str2 = String.valueOf(i);
      FtUtil.log_i(this.CLASS_NAME, "readBatteryTemp", "return temp=" + str2);
      return str2;
      i = Integer.parseInt(str1.substring(0, 2));
    }
  }
  
  public String readBatteryTempAdc()
  {
    String str = Support.Kernel.read("BATTERY_TEMP_ADC");
    FtUtil.log_i(this.CLASS_NAME, "readBatteryTempAdc", "adc=" + str);
    return str;
  }
  
  public String readBatteryVoltage()
  {
    String str1 = Support.Kernel.read("BATTERY_VOLT");
    if (str1.length() < 3) {}
    for (String str2 = str1.trim();; str2 = str1.substring(0, 3))
    {
      FtUtil.log_i(this.CLASS_NAME, "readBatteryVoltate", "voltate=" + str2);
      return String.valueOf(Float.valueOf(str2).floatValue() / 100.0F);
    }
  }
  
  public String readExternalApChipTemp()
  {
    String str1 = Support.Kernel.read("SEC_EXT_THERMISTER_ADC");
    FtUtil.log_i(this.CLASS_NAME, "readExternalApChipTemp", "sysfs ext_temp=" + str1);
    int i;
    if (str1.length() >= 3)
    {
      int j = Integer.parseInt(str1.substring(2));
      i = Integer.parseInt(str1.substring(0, 2));
      if (j >= 5) {
        i++;
      }
    }
    for (;;)
    {
      String str2 = String.valueOf(i);
      FtUtil.log_i(this.CLASS_NAME, "readExternalApChipTemp", "return ext_temp=" + str2);
      return str2;
      i = Integer.parseInt(str1.substring(0, 2));
    }
  }
  
  public String readFuelGaugeSOC()
  {
    String str = Support.Kernel.read("BATTERY_CAPACITY");
    if (str != null) {
      str = String.valueOf(100 * Integer.parseInt(str));
    }
    FtUtil.log_i(this.CLASS_NAME, "readFuelGaugeSOC", "soc=" + str);
    return str;
  }
  
  public void reboot(byte paramByte)
  {
    FtUtil.log_i(this.CLASS_NAME, "reboot", "mode=" + paramByte);
    PowerManager localPowerManager = (PowerManager)getSystemService("power");
    Handler local1 = new Handler(getApplicationContext().getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what == 0) {
          ((PowerManager)paramAnonymousMessage.obj).reboot(null);
        }
        while (paramAnonymousMessage.what != 1) {
          return;
        }
        ((PowerManager)paramAnonymousMessage.obj).reboot("download");
      }
    };
    local1.sendMessageDelayed(local1.obtainMessage(paramByte, localPowerManager), 1000L);
  }
  
  public boolean resetFuelGaugeIC()
  {
    FtUtil.log_i(this.CLASS_NAME, "resetFuelGaugeIC", "Fuel Gauge IC reset");
    return Support.Kernel.write("FUEL_GAUGE_RESET", "1");
  }
  
  public void sendAlarmManagerOnOff(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      FtUtil.log_i(this.CLASS_NAME, "sendAlarmOnOffIntent", "sendAlarmOnOffIntentandroid.intent.action.START_FACTORY_TEST");
      sendBroadcast(new Intent("android.intent.action.START_FACTORY_TEST"));
      return;
    }
    if (paramBoolean == true)
    {
      FtUtil.log_i(this.CLASS_NAME, "sendAlarmOnOffIntent", "sendAlarmOnOffIntentandroid.intent.action.STOP_FACTORY_TEST");
      sendBroadcast(new Intent("android.intent.action.STOP_FACTORY_TEST"));
      return;
    }
    FtUtil.log_i(this.CLASS_NAME, "sendAlarmOnOffIntent", "Invalid parameter");
  }
  
  public void sendDvfsLockIntent()
  {
    FtUtil.log_i(this.CLASS_NAME, "sendDvfsLockIntent", "...");
    sendBroadcast(new Intent("com.sec.android.intent.action.DVFS_FACTORY_CPU_LOCK"));
  }
  
  public void sendSleepCmd2Ril()
  {
    sendBroadcast(new Intent("com.sec.factory.cporiented.SEND_CMD2RIL"));
    FtUtil.log_i(this.CLASS_NAME, "sendSleepCmd2Ril", "Power Sleep for 2nd modem - com.sec.factory.cporiented.SEND_CMD2RIL");
  }
  
  public void setBrightness(int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "setBrightness", "brightness=" + paramInt);
    try
    {
      IPowerManager localIPowerManager = IPowerManager.Stub.asInterface(ServiceManager.getService("power"));
      if (localIPowerManager != null) {
        localIPowerManager.setBacklightBrightness(paramInt);
      }
      return;
    }
    catch (RemoteException localRemoteException)
    {
      FtUtil.log_e(localRemoteException);
    }
  }
  
  public void setFactoryModeAtBatteryNode(boolean paramBoolean)
  {
    if (paramBoolean == true)
    {
      if (Support.Kernel.write("BATTRY_FACTORYMODE", "1"))
      {
        FtUtil.log_i(this.CLASS_NAME, "setFactoryModeAtBatteryNode", "set : 1");
        return;
      }
      FtUtil.log_i(this.CLASS_NAME, "setFactoryModeAtBatteryNode", "set : on Failed");
      return;
    }
    if (Support.Kernel.write("BATTRY_FACTORYMODE", "0"))
    {
      FtUtil.log_i(this.CLASS_NAME, "setFactoryModeAtBatteryNode", "set : 0");
      return;
    }
    FtUtil.log_i(this.CLASS_NAME, "setFactoryModeAtBatteryNode", "set off : Failed");
  }
  
  public void setScreenBrightnessMode(int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "setScreenBrightnessMode", "mode=" + paramInt);
    Settings.System.putInt(getContentResolver(), "screen_brightness_mode", paramInt);
  }
  
  public void setScreenState(boolean paramBoolean)
  {
    FtUtil.log_i(this.CLASS_NAME, "setScreenState", "state=" + paramBoolean);
    PowerManager localPowerManager = (PowerManager)getSystemService("power");
    if (paramBoolean)
    {
      localPowerManager.userActivity(SystemClock.uptimeMillis(), false);
      doPartialWakeLock(false);
      return;
    }
    doPartialWakeLock(true);
    localPowerManager.goToSleep(SystemClock.uptimeMillis());
  }
  
  public void setTouchLedTime(int paramInt)
  {
    Settings.System.putInt(getContentResolver(), "button_key_light", paramInt);
  }
  
  public void sleep()
  {
    FtUtil.log_i(this.CLASS_NAME, "sleep", "Power Sleep");
    doWakeLock(false);
    doPartialWakeLock(false);
    doMediaScanWakeLock(false);
    ((PowerManager)getSystemService("power")).goToSleep(SystemClock.uptimeMillis());
  }
  
  public void writeBatteryAdcCal(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "writeBatteryAdcCal", "value=" + paramString);
    Support.Kernel.write("BATTERY_VOLT_ADC_CAL", paramString);
  }
  
  public void writeBatteryCal(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "writeBatteryCal", "value=" + paramString);
    Support.Kernel.write("BATTERY_VOLT_ADC_CAL", paramString);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.ModulePower
 * JD-Core Version:    0.7.1
 */