package com.sec.factory.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class UIFMIntenna
  extends UIBase
{
  private static int FMTestResult = -1;
  private static boolean IsFMOn = false;
  public static Context context;
  private static TextView mFMIntennaResult;
  private final int FM_FINISH_TEST_DELAY = 2000;
  private final int FM_ON_STATUS_TIMEOUT_DELAY = 5000;
  private final int FM_STOP_DELAY = 3000;
  private final String KEY_FACTORY = "factoryrssi";
  private final byte MSG_FINISH_TEST = 3;
  private final byte MSG_READ_STATUS_TIMEOUT = 1;
  private final byte MSG_STOP_RADIO = 2;
  private final String PREF_FILE = "SettingsPreference";
  private int factory_rssi = -70;
  public Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      case 1: 
        Log.i("UIFMIntenna", "Read Test Fail because of timeout");
        UIFMIntenna.access$002(0);
        UIFMIntenna.this.updateDisplay(2131296400, "FAIL", -65536);
        UIFMIntenna.this.setTestResultFailCase((byte)51);
        UIFMIntenna.this.mHandler.sendEmptyMessageDelayed(3, 2000L);
        return;
      case 2: 
        Log.i("UIFMIntenna", "MSG_STOP_RADIO");
        UIFMIntenna.this.StopFMRadio();
        UIFMIntenna.this.mHandler.sendEmptyMessageDelayed(3, 2000L);
        return;
      }
      Log.i("UIFMIntenna", "MSG_FINISH_TEST");
      UIFMIntenna.this.finish();
    }
  };
  private SharedPreferences mPreferences;
  private final BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (paramAnonymousIntent.getAction().equals("test.mode.radio.freq.response"))
      {
        Log.i("UIFMIntenna", "Received FM_RADIO_FREQUENCY_RESPONSE");
        UIFMIntenna.this.mHandler.removeMessages(1);
        int i = Integer.parseInt(paramAnonymousIntent.getStringExtra("signal_strength"), 10);
        Log.i("UIFMIntenna", "FM_RADIO_FREQUENCY_RESPONSE::rssi=" + i);
        if (i < UIFMIntenna.this.factory_rssi) {
          break label146;
        }
        Log.i("UIFMIntenna", "FM Intenna result :: PASS");
        UIFMIntenna.access$002(1);
        UIFMIntenna.this.updateDisplay(2131296400, "PASS", -16776961);
        UIFMIntenna.this.setTestResult((byte)51, (byte)80);
      }
      for (;;)
      {
        if (!UIFMIntenna.IsFMOn)
        {
          UIFMIntenna.access$402(true);
          UIFMIntenna.this.mHandler.sendEmptyMessageDelayed(2, 3000L);
        }
        return;
        label146:
        Log.i("UIFMIntenna", "FM Intenna result :: FAIL");
        UIFMIntenna.access$002(0);
        UIFMIntenna.this.updateDisplay(2131296400, "FAIL", -65536);
        UIFMIntenna.this.setTestResultFailCase((byte)51);
      }
    }
  };
  private final String test_freq = "103000";
  
  public UIFMIntenna()
  {
    super("UIFMIntenna", 51);
  }
  
  private void StartFMRadio()
  {
    Log.i("UIFMIntenna", "*** StartFMRadio ***");
    Intent localIntent = new Intent("test.mode.radio.freq");
    localIntent.putExtra("frequency", "103000");
    localIntent.putExtra("output", "speaker");
    localIntent.setFlags(268435456);
    startActivity(localIntent);
  }
  
  private void StopFMRadio()
  {
    Log.i("UIFMIntenna", "*** StopFMRadio ***");
    IsFMOn = false;
    Intent localIntent = new Intent("test.mode.radio.off");
    localIntent.setFlags(268435456);
    startActivity(localIntent);
  }
  
  private void init()
  {
    FMTestResult = -1;
    IsFMOn = false;
    try
    {
      context = createPackageContext("com.sec.android.app.fm", 2);
      if (context != null)
      {
        this.mPreferences = context.getSharedPreferences("SettingsPreference", 0);
        this.factory_rssi = this.mPreferences.getInt("factoryrssi", this.factory_rssi);
        Log.i("UIFMIntenna", "factory_rssi 1=" + this.factory_rssi);
      }
      Log.i("UIFMIntenna", "factory_rssi 2 =" + this.factory_rssi);
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        localException.printStackTrace();
      }
    }
  }
  
  private void updateDisplay(int paramInt1, String paramString, int paramInt2)
  {
    if (paramInt1 == 2131296400)
    {
      mFMIntennaResult.setText(paramString);
      mFMIntennaResult.setBackgroundColor(paramInt2);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903060);
    mFMIntennaResult = (TextView)findViewById(2131296400);
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("test.mode.radio.freq.response");
    registerReceiver(this.mReceiver, localIntentFilter);
    init();
    StartFMRadio();
  }
  
  protected void onDestroy()
  {
    Log.i("UIFMIntenna", "onDestroy");
    super.onDestroy();
    unregisterReceiver(this.mReceiver);
  }
  
  protected void onPause()
  {
    Log.i("UIFMIntenna", "onPause");
    super.onPause();
    if (IsFMOn == true) {
      StopFMRadio();
    }
  }
  
  protected void onResume()
  {
    Log.i("UIFMIntenna", "onResume");
    super.onResume();
    if (FMTestResult == -1) {
      updateDisplay(2131296400, "Ready", -1);
    }
    do
    {
      return;
      if (FMTestResult == 0)
      {
        updateDisplay(2131296400, "FAIL", -65536);
        return;
      }
    } while (FMTestResult != 1);
    updateDisplay(2131296400, "PASS", -16776961);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIFMIntenna
 * JD-Core Version:    0.7.1
 */