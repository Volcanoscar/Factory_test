package com.sec.factory.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import com.sec.factory.support.NVAccessor;

public class UIFeliCa
  extends UIBase
{
  private static TextView mReaderModeResult;
  private static int polling_flag = 0;
  private final int FELICA_FINISH_DELAY = 1000;
  private final byte MSG_FINISH_TEST = 4;
  public Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      Log.i("UIFeliCa", "handleMessage(Message msg)");
      switch (paramAnonymousMessage.what)
      {
      }
      do
      {
        return;
        Log.i("UIFeliCa", "MSG_FINISH_TEST");
      } while (!UIFeliCa.this.mIsPassed_reader);
      UIFeliCa.this.setTestResultWithoutNV((byte)40, (byte)80);
      UIFeliCa.this.finish();
    }
  };
  private boolean mIsPassed_reader = false;
  private final BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      Log.i("UIFeliCa", "onReceive(Context context, Intent intent)");
      int i = NVAccessor.getNV((byte)30);
      if ("com.sec.android.app.felicatest.FELICA_TEST_POLLING".equals(paramAnonymousIntent.getAction()))
      {
        if (!paramAnonymousIntent.getStringExtra("S_DATA").equals("OK")) {
          break label93;
        }
        UIFeliCa.access$002(UIFeliCa.this, true);
        if (i != 80) {
          NVAccessor.setNV((byte)30, (byte)80);
        }
        UIFeliCa.this.updateDisplay(2131296399, "PASS", -16776961);
      }
      for (;;)
      {
        UIFeliCa.this.mHandler.sendEmptyMessageDelayed(4, 1000L);
        return;
        label93:
        UIFeliCa.access$002(UIFeliCa.this, false);
        if (i != 80) {
          NVAccessor.setNV((byte)30, (byte)70);
        }
        UIFeliCa.this.updateDisplay(2131296399, "FAIL", -65536);
      }
    }
  };
  
  public UIFeliCa()
  {
    super("UIFeliCa()", 40);
  }
  
  private void updateDisplay(int paramInt1, String paramString, int paramInt2)
  {
    Log.i("UIFeliCa", "updateDisplay(int id, String result, int textColor)");
    if (paramInt1 == 2131296399)
    {
      mReaderModeResult.setText(paramString);
      mReaderModeResult.setTextColor(paramInt2);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    Log.i("UIFeliCa", "onCreate(Bundle savedInstanceState)");
    super.onCreate(paramBundle);
    setContentView(2130903059);
    mReaderModeResult = (TextView)findViewById(2131296399);
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.sec.android.app.felicatest.FELICA_TEST_POLLING");
    registerReceiver(this.mReceiver, localIntentFilter);
  }
  
  protected void onDestroy()
  {
    Log.i("UIFeliCa", "onDestroy()");
    super.onDestroy();
    unregisterReceiver(this.mReceiver);
  }
  
  protected void onPause()
  {
    Log.i("UIFeliCa", "onPause()");
    super.onPause();
  }
  
  protected void onResume()
  {
    Log.i("UIFeliCa", "onResume()");
    super.onResume();
    int i = NVAccessor.getNV((byte)30);
    if (polling_flag == 0)
    {
      Log.i("UIFeliCa", "Start Polling");
      polling_flag = 1;
      if (i != 80) {
        NVAccessor.setNV((byte)30, (byte)69);
      }
      Intent localIntent = new Intent();
      localIntent.setClassName("com.sec.android.app.felicatest", "com.sec.android.app.felicatest.FeliCaTest");
      localIntent.setFlags(268435456);
      localIntent.putExtra("FeliCa_Start_Parameter", 111);
      startActivity(localIntent);
      return;
    }
    polling_flag = 0;
  }
  
  protected void setTestResult(byte paramByte)
  {
    Log.i("UIFeliCa", "setTestResult(final byte result)");
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIFeliCa
 * JD-Core Version:    0.7.1
 */