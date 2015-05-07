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

public class UINFC
  extends UIBase
{
  private static TextView mCardModeResult;
  private static TextView mReaderModeResult;
  private final byte MSG_DISABLE_DISCOVERY = 3;
  private final byte MSG_ENABLE_DISCOVERY = 2;
  private final byte MSG_FINISH_TEST = 4;
  private final byte MSG_READ_STATUS_TIMEOUT = 1;
  private final byte MSG_SIM_TEST = 5;
  private final int NFC_DISABLE_DELAY = 400;
  private final int NFC_ENABLE_DELAY_OFFSET = 700;
  private final int NFC_FINISH_DELAY = 1000;
  private final int NFC_READ_STATUS_TIMEOUT_DELAY = 10000;
  public Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      case 1: 
        Log.i("UINFC", "Read Test Fail because of timeout");
        UINFC.access$002(UINFC.this, false);
        UINFC.this.updateDisplay(2131296494, "FAIL", -65536);
        return;
      case 2: 
        Log.i("UINFC", "send Intent ENABLE_SEC_NFC_DISCOVERY");
        UINFC.this.sendBroadcast(new Intent("com.sec.android.app.nfctest.NFC_DISCOVERY_ENABLE"));
        return;
      case 3: 
        Log.i("UINFC", "send Intent DISABLE_SEC_NFC_DISCOVERY");
        UINFC.this.sendBroadcast(new Intent("com.sec.android.app.nfctest.NFC_DISCOVERY_DISABLE"));
        return;
      case 5: 
        Log.i("UINFC", "send Intent CHECK_SEC_NFC_SIM");
        UINFC.this.sendBroadcast(new Intent("com.sec.android.app.nfctest.NFC_CHECK_SIM"));
        return;
      }
      Log.i("UINFC", "MSG_FINISH_TEST");
      if ((UINFC.this.mIsPassed_card) && (UINFC.this.mIsPassed_reader))
      {
        Log.i("UINFC", "SWP & Read Test all pass");
        UINFC.this.setTestResult((byte)80);
        UINFC.this.finish();
        return;
      }
      Log.i("UINFC", "SWP or Read Test Fail");
    }
  };
  private boolean mIsPassed_card = false;
  private boolean mIsPassed_reader = false;
  private final BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      if ("android.nfc.action.TAG_DISCOVERED".equals(str))
      {
        Log.i("UINFC", "Received ACTION_TAG_DISCOVERED");
        UINFC.access$002(UINFC.this, true);
        UINFC.this.mHandler.removeMessages(1);
        UINFC.this.updateDisplay(2131296494, "PASS", -16776961);
        UINFC.this.mHandler.sendEmptyMessageDelayed(4, 1000L);
      }
      while (!"com.sec.android.app.nfctest.NFC_CHECK_SIM_RESPONSE".equals(str)) {
        return;
      }
      Log.i("UINFC", "Received CHECK_SEC_NFC_SIM_RESP");
      if (paramAnonymousIntent.getIntExtra("SIM_DATA", -1) == 1)
      {
        Log.i("UINFC", "SWP Test Pass");
        UINFC.access$202(UINFC.this, true);
        UINFC.this.updateDisplay(2131296493, "PASS", -16776961);
        return;
      }
      Log.i("UINFC", "SWP Test Fail");
      UINFC.access$202(UINFC.this, false);
      UINFC.this.updateDisplay(2131296493, "FAIL", -65536);
    }
  };
  
  public UINFC()
  {
    super("UINFC", 9);
  }
  
  private void nfctest()
  {
    Log.i("UINFC", "*** Start nfctest ***");
    this.mHandler.sendEmptyMessageDelayed(1, 10000L);
    this.mHandler.sendEmptyMessageDelayed(3, 0L);
    Log.i("UINFC", "SWP Test Not supported");
    this.mIsPassed_card = true;
    updateDisplay(2131296493, "Not supported", -16776961);
    long l = 0L + 400L;
    this.mHandler.sendEmptyMessageDelayed(2, l);
  }
  
  private void updateDisplay(int paramInt1, String paramString, int paramInt2)
  {
    if (paramInt1 == 2131296493)
    {
      mCardModeResult.setText(paramString);
      mCardModeResult.setTextColor(paramInt2);
    }
    while (paramInt1 != 2131296494) {
      return;
    }
    mReaderModeResult.setText(paramString);
    mReaderModeResult.setTextColor(paramInt2);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903077);
    mCardModeResult = (TextView)findViewById(2131296493);
    mReaderModeResult = (TextView)findViewById(2131296494);
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.nfc.action.TAG_DISCOVERED");
    localIntentFilter.addAction("com.sec.android.app.nfctest.NFC_CHECK_SIM_RESPONSE");
    registerReceiver(this.mReceiver, localIntentFilter);
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    unregisterReceiver(this.mReceiver);
  }
  
  protected void onPause()
  {
    Log.i("UINFC", "onPause");
    this.mHandler.removeMessages(1);
    this.mHandler.removeMessages(3);
    this.mHandler.removeMessages(5);
    this.mHandler.removeMessages(2);
    super.onPause();
    sendBroadcast(new Intent("com.sec.android.app.nfctest.NFC_TEST_END"));
    try
    {
      Thread.sleep(100L);
      label63:
      sendBroadcast(new Intent("com.sec.android.app.nfctest.NFC_DISCOVERY_DISABLE"));
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label63;
    }
  }
  
  protected void onResume()
  {
    Log.i("UINFC", "onResume");
    super.onResume();
    sendBroadcast(new Intent("com.sec.android.app.nfctest.NFC_TEST_START"));
    Log.d("DFMS_LIST", "onCreate---");
    nfctest();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UINFC
 * JD-Core Version:    0.7.1
 */