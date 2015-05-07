package com.sec.factory.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.modules.ModulePower;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;

public class UIUSB
  extends UIBase
{
  public static int WHAT_CHECK_LCD_ON = 0;
  private final int DELAY = 200;
  private int DUMMY = 0;
  private final int TESTSTATE_TA_CONNECT;
  private final int TESTSTATE_TA_DISCONNECT;
  private final int TESTSTATE_USB_CONNECT;
  private final int TESTSTATE_USB_DISCONNECT;
  private boolean isPlugged = false;
  private BroadcastReceiver mBroadcastReceiver;
  private int mCount = 0;
  private Handler mHandler;
  private boolean mIsTestPass;
  private LinearLayout mLinearBackground;
  private ModuleDevice mModuleDevice;
  private ModulePower mModulePower;
  private TableLayout mTableTA;
  private TableLayout mTableUSB;
  private int mTestState;
  private TextView mTextTAStep1Result;
  private TextView mTextTAStep2;
  private TextView mTextTAStep2Result;
  private TextView mTextUSBStep1Result;
  private TextView mTextUSBStep2;
  private TextView mTextUSBStep2Result;
  
  public UIUSB()
  {
    super("UIUSB", 18);
    int i = this.DUMMY;
    this.DUMMY = (i + 1);
    this.TESTSTATE_TA_CONNECT = i;
    int j = this.DUMMY;
    this.DUMMY = (j + 1);
    this.TESTSTATE_TA_DISCONNECT = j;
    int k = this.DUMMY;
    this.DUMMY = (k + 1);
    this.TESTSTATE_USB_CONNECT = k;
    int m = this.DUMMY;
    this.DUMMY = (m + 1);
    this.TESTSTATE_USB_DISCONNECT = m;
    this.mTestState = 0;
    this.mIsTestPass = false;
    this.mBroadcastReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        String str = paramAnonymousIntent.getAction();
        FtUtil.log_d(UIUSB.this.CLASS_NAME, "mBroadcastReceiver.onReceive", "action=" + str);
        FtUtil.log_d(UIUSB.this.CLASS_NAME, "mBroadcastReceiver.onReceive", "mTestState=" + UIUSB.this.mTestState);
        if ("android.intent.action.BATTERY_CHANGED".equals(str))
        {
          int i = paramAnonymousIntent.getIntExtra("plugged", 0);
          FtUtil.log_d(UIUSB.this.CLASS_NAME, "Battery State: " + i);
          if ((UIUSB.this.mTestState >= 0) && (UIUSB.this.mTestState <= 1)) {
            UIUSB.this.TACheck(i);
          }
        }
        boolean bool;
        do
        {
          do
          {
            return;
          } while (!"android.hardware.usb.action.USB_STATE".equals(str));
          bool = paramAnonymousIntent.getExtras().getBoolean("connected");
          FtUtil.log_d(UIUSB.this.CLASS_NAME, "USB State: " + bool);
        } while ((UIUSB.this.mTestState < 2) || (UIUSB.this.mTestState > 3));
        UIUSB.this.USBCheck(bool);
      }
    };
    this.mHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what == UIUSB.WHAT_CHECK_LCD_ON)
        {
          if (UIUSB.access$308(UIUSB.this) == 5)
          {
            FtUtil.log_d(UIUSB.this.CLASS_NAME, "handleMessage : LCD is not on state during 1 second.");
            UIUSB.this.mTextTAStep1Result.setVisibility(0);
            UIUSB.this.mTextTAStep1Result.setText("Connected");
            UIUSB.this.mTextTAStep1Result.setTextColor(-16776961);
            UIUSB.this.mTextTAStep2.setVisibility(0);
            UIUSB.access$008(UIUSB.this);
            UIUSB.access$302(UIUSB.this, 0);
          }
        }
        else {
          return;
        }
        if (UIUSB.this.mModulePower.isScreenOn() == true)
        {
          UIUSB.this.mModulePower.doPartialWakeLock(true);
          UIUSB.this.mModulePower.doWakeLock(false);
          if (Support.Feature.getBoolean("NEED_LPA_MODE_SET", true)) {
            UIUSB.this.mModuleDevice.setLPAmode("0");
          }
          UIUSB.this.mModulePower.gotosleep();
          UIUSB.this.mTextTAStep1Result.setVisibility(0);
          UIUSB.this.mTextTAStep1Result.setText("Connected");
          UIUSB.this.mTextTAStep1Result.setTextColor(-16776961);
          UIUSB.this.mTextTAStep2.setVisibility(0);
          UIUSB.access$008(UIUSB.this);
          UIUSB.access$302(UIUSB.this, 0);
          return;
        }
        UIUSB.this.mHandler.sendEmptyMessageDelayed(UIUSB.WHAT_CHECK_LCD_ON, 200L);
      }
    };
  }
  
  private void TACheck(int paramInt)
  {
    if ((this.mTestState == this.TESTSTATE_TA_CONNECT) && (paramInt == 1) && (!this.isPlugged))
    {
      this.isPlugged = true;
      FtUtil.log_d(this.CLASS_NAME, "ACTION_BATTERY_CHANGED : Plugged");
      this.mHandler.sendEmptyMessage(WHAT_CHECK_LCD_ON);
    }
    while ((this.mTestState != this.TESTSTATE_TA_DISCONNECT) || (paramInt != 0)) {
      return;
    }
    this.isPlugged = false;
    FtUtil.log_d(this.CLASS_NAME, "ACTION_BATTERY_CHANGED : Unplugged");
    this.mTextTAStep2Result.setVisibility(0);
    this.mTextTAStep2Result.setText("Disconnected");
    this.mTextTAStep2Result.setTextColor(-16776961);
    this.mTestState = (1 + this.mTestState);
    this.mTableUSB.setVisibility(0);
  }
  
  private void USBCheck(boolean paramBoolean)
  {
    if ((this.mTestState == this.TESTSTATE_USB_CONNECT) && (paramBoolean))
    {
      this.mTextUSBStep1Result.setVisibility(0);
      this.mTextUSBStep1Result.setText("Connected");
      this.mTextUSBStep1Result.setTextColor(-16776961);
      this.mTextUSBStep2.setVisibility(0);
      this.mTestState = (1 + this.mTestState);
    }
    while ((this.mTestState != this.TESTSTATE_USB_DISCONNECT) || (paramBoolean)) {
      return;
    }
    this.mTextUSBStep2Result.setVisibility(0);
    this.mTextUSBStep2Result.setText("Disconnected");
    this.mTextUSBStep2Result.setTextColor(-16776961);
    this.mTestState = 0;
    this.mIsTestPass = true;
    setTestResult();
  }
  
  private void initialize()
  {
    FtUtil.log_d(this.CLASS_NAME, "initialize", null);
    this.mLinearBackground = ((LinearLayout)findViewById(2131296398));
    this.mTestState = 0;
    this.mTableTA = ((TableLayout)findViewById(2131296780));
    this.mTextTAStep1Result = ((TextView)findViewById(2131296781));
    this.mTextTAStep2 = ((TextView)findViewById(2131296782));
    this.mTextTAStep2Result = ((TextView)findViewById(2131296783));
    this.mTableTA.setVisibility(0);
    this.mTableUSB = ((TableLayout)findViewById(2131296784));
    this.mTextUSBStep1Result = ((TextView)findViewById(2131296785));
    this.mTextUSBStep2 = ((TextView)findViewById(2131296786));
    this.mTextUSBStep2Result = ((TextView)findViewById(2131296787));
  }
  
  private void setTestResult()
  {
    FtUtil.log_e(this.CLASS_NAME, "setTestResult", null);
    if (this.mIsTestPass) {
      setTestResult((byte)80);
    }
    for (;;)
    {
      finish();
      return;
      setTestResultFailCase((byte)18);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903096);
    this.mModuleDevice = ModuleDevice.instance(this);
    this.mModulePower = ModulePower.instance(this);
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.BATTERY_CHANGED");
    localIntentFilter.addAction("android.hardware.usb.action.USB_STATE");
    registerReceiver(this.mBroadcastReceiver, localIntentFilter);
    initialize();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
  }
  
  protected void onPause()
  {
    super.onPause();
  }
  
  protected void onResume()
  {
    super.onResume();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIUSB
 * JD-Core Version:    0.7.1
 */