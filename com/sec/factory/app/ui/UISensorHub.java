package com.sec.factory.app.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;
import com.sec.factory.aporiented.WakeUpService;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.modules.ModulePower;
import com.sec.factory.support.FtUtil;

public class UISensorHub
  extends UIBase
{
  protected static ModulePower mModulePower = null;
  private final String ACTION_WAKE_UP = "com.sec.factory.aporiented.athandler.AtSensorHb.WakeUp";
  private TextView BinFwVersion;
  private final int FAIL = 2;
  private TextView IntCheckResult;
  private TextView McuChipName;
  private TextView McuFwVersion;
  private final int PASS = 1;
  private TextView PassFail;
  private final int READ_FAIL = 3;
  boolean isFactoryMode = false;
  private int ispassed = -1;
  public Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      case 1: 
        FtUtil.log_d(UISensorHub.this.CLASS_NAME, "mHandler()", "Display SensorHub Info()");
        UISensorHub.this.getMcuFirmware();
        UISensorHub.this.getBinFirmware();
        FtUtil.log_d(UISensorHub.this.CLASS_NAME, "mHandler()", "Start SensorHub SelfTest");
        UISensorHub.mModulePower.doPartialWakeLock(true);
        if (UISensorHub.this.isFactoryMode)
        {
          Intent localIntent2 = new Intent("android.intent.action.STOP_FACTORY_TEST");
          UISensorHub.this.getApplicationContext().sendBroadcast(localIntent2);
          FtUtil.log_i(UISensorHub.this.CLASS_NAME, "handleCommand", "AlarmManager Enable");
        }
        UISensorHub.this.mModuleDevice.startSensorHubTest();
        Intent localIntent3 = new Intent(UISensorHub.this.getApplicationContext(), WakeUpService.class);
        localIntent3.setAction("com.sec.factory.aporiented.athandler.AtSensorHb.WakeUp");
        localIntent3.putExtra("isAcquireWakelock", false);
        PendingIntent localPendingIntent = PendingIntent.getBroadcast(UISensorHub.this.getApplicationContext(), 0, localIntent3, 0);
        ((AlarmManager)UISensorHub.this.getApplicationContext().getSystemService("alarm")).set(0, 5000L + System.currentTimeMillis(), localPendingIntent);
        UISensorHub.this.mPM.goToSleep(SystemClock.uptimeMillis());
        return;
      case 2: 
        FtUtil.log_d(UISensorHub.this.CLASS_NAME, "mHandler()", "WakeUp device");
        if (UISensorHub.this.isFactoryMode)
        {
          Intent localIntent1 = new Intent("android.intent.action.START_FACTORY_TEST");
          UISensorHub.this.getApplicationContext().sendBroadcast(localIntent1);
          FtUtil.log_i(UISensorHub.this.CLASS_NAME, "handleCommand", "AlarmManager disable");
        }
        UISensorHub.access$402(UISensorHub.this, UISensorHub.this.mModuleDevice.readSensorHubResult().split(","));
        FtUtil.log_i(UISensorHub.this.CLASS_NAME, "Result - ", "MCU name: " + UISensorHub.this.result[0] + "  /  Selftest: " + UISensorHub.this.result[1] + "  /  INT pin: " + UISensorHub.this.result[2]);
        UISensorHub.this.IntCheckResult.setText("INT Check : " + UISensorHub.this.result[2]);
        UISensorHub.this.McuChipName.setText("MCU name : " + UISensorHub.this.result[0]);
        while (!UISensorHub.this.mPM.isScreenOn()) {}
        try
        {
          Thread.sleep(200L);
          ModulePower.instance(UISensorHub.this.getApplicationContext()).setBrightness(255);
          FtUtil.log_d(UISensorHub.this.CLASS_NAME, "mHandler()", "checkPassResult()");
          UISensorHub.this.checkPassFail();
          UISensorHub.this.checkPassResult();
          return;
        }
        catch (InterruptedException localInterruptedException)
        {
          for (;;)
          {
            localInterruptedException.printStackTrace();
          }
        }
      }
      UISensorHub.this.finish();
    }
  };
  private ModuleDevice mModuleDevice;
  private PowerManager mPM;
  private String[] result;
  
  public UISensorHub()
  {
    super("UISensorHub", 49);
  }
  
  private void checkPassFail()
  {
    if (("OK".equals(this.result[1])) && ("OK".equals(this.result[2]))) {
      writeNV(49, true);
    }
    for (int i = 1; (i != 0) && (this.ispassed != 3); i = 0)
    {
      this.PassFail.setText("PASS");
      this.PassFail.setTextColor(-16776961);
      this.ispassed = 1;
      return;
      writeNV(49, false);
    }
    this.PassFail.setText("FAIL");
    this.PassFail.setTextColor(-65536);
    this.ispassed = 2;
  }
  
  private void checkPassResult()
  {
    if (this.ispassed == 1)
    {
      FtUtil.log_d(this.CLASS_NAME, "checkPassResult()", "pass!!!");
      setTestResultWithoutNV((byte)49, (byte)80);
      this.mHandler.sendEmptyMessageDelayed(3, 500L);
    }
    while (this.ispassed != 2) {
      return;
    }
    setResult(0);
  }
  
  private void getBinFirmware()
  {
    FtUtil.log_d(this.CLASS_NAME, "getBinFirmware", null);
    String[] arrayOfString = this.mModuleDevice.readModuleFirmwareVersion(4).split(",");
    FtUtil.log_d(this.CLASS_NAME, "getBinFirmware", "BinFirmware = " + arrayOfString[1]);
    this.BinFwVersion.setText("Bin Firm Version : " + arrayOfString[1]);
  }
  
  private void getMcuFirmware()
  {
    FtUtil.log_d(this.CLASS_NAME, "getMcuFirmware", null);
    String[] arrayOfString = this.mModuleDevice.readModuleFirmwareVersion(4).split(",");
    FtUtil.log_d(this.CLASS_NAME, "getMcuFirmware", "McuFirmware = " + arrayOfString[0]);
    this.McuFwVersion.setText("MCU Firm Version : " + arrayOfString[0]);
  }
  
  private void init()
  {
    FtUtil.log_d(this.CLASS_NAME, "init()", "init_start");
    this.IntCheckResult = ((TextView)findViewById(2131296733));
    this.McuFwVersion = ((TextView)findViewById(2131296734));
    this.BinFwVersion = ((TextView)findViewById(2131296735));
    this.McuChipName = ((TextView)findViewById(2131296736));
    this.PassFail = ((TextView)findViewById(2131296737));
    this.mModuleDevice = ModuleDevice.instance(this);
    this.mPM = ((PowerManager)getApplicationContext().getSystemService("power"));
    ModulePower localModulePower;
    if (mModulePower == null)
    {
      localModulePower = ModulePower.instance(this);
      mModulePower = localModulePower;
      if ((!ModuleCommon.instance(this).isFactorySim()) && (!ModuleCommon.instance(this).isFactoryMode())) {
        break label181;
      }
    }
    label181:
    for (boolean bool = true;; bool = false)
    {
      this.isFactoryMode = bool;
      FtUtil.log_i(this.CLASS_NAME, "handleCommand", "isFactoryMode: " + this.isFactoryMode);
      return;
      localModulePower = mModulePower;
      break;
    }
  }
  
  private void startSensorHubTest()
  {
    FtUtil.log_d(this.CLASS_NAME, "startSensorHubTest", null);
    this.mHandler.sendEmptyMessage(1);
    this.mHandler.sendEmptyMessageDelayed(2, 5000L);
  }
  
  private void writeNV(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean == true)
    {
      FtUtil.log_e(this.CLASS_NAME, "writeNV", "PASS");
      setTestResult((byte)paramInt, (byte)80);
      return;
    }
    FtUtil.log_e(this.CLASS_NAME, "writeNV", "FAIL");
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    FtUtil.log_d(this.CLASS_NAME, "onCreate()", "startOnCreate()");
    super.onCreate(paramBundle);
    FtUtil.setRemoveSystemUI(getWindow(), true);
    setContentView(2130903086);
    init();
    new Thread(new Runnable()
    {
      public void run()
      {
        UISensorHub.this.startSensorHubTest();
      }
    }).start();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    FtUtil.log_d(this.CLASS_NAME, "onDestroy", null);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    FtUtil.log_d(this.CLASS_NAME, "onKeyDown", "KeyEent=" + paramInt);
    switch (paramInt)
    {
    default: 
      return super.onKeyDown(paramInt, paramKeyEvent);
    }
    return true;
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    FtUtil.log_d(this.CLASS_NAME, "onKeyUp", "KeyEent=" + paramInt);
    switch (paramInt)
    {
    default: 
      return super.onKeyDown(paramInt, paramKeyEvent);
    }
    return true;
  }
  
  protected void onPause()
  {
    super.onPause();
    FtUtil.log_d(this.CLASS_NAME, "onPause", null);
  }
  
  protected void onResume()
  {
    super.onResume();
    FtUtil.log_d(this.CLASS_NAME, "onResume", null);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    FtUtil.log_d(this.CLASS_NAME, "onTouchEvent()", null);
    if (this.ispassed == 1)
    {
      FtUtil.log_d(this.CLASS_NAME, "onTouchEvent()", "pass!!!");
      setResult(-1);
      finish();
    }
    while (this.ispassed != 2) {
      return true;
    }
    setResult(0);
    finish();
    return true;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UISensorHub
 * JD-Core Version:    0.7.1
 */