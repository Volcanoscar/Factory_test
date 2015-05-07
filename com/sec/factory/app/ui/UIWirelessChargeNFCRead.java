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
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Kernel;
import java.util.Timer;
import java.util.TimerTask;

public class UIWirelessChargeNFCRead
  extends UIBase
{
  private final byte MSG_DISABLE_DISCOVERY = 3;
  private final byte MSG_ENABLE_DISCOVERY = 2;
  private final byte MSG_FINISH_TEST = 4;
  private final byte MSG_READ_STATUS_TIMEOUT = 1;
  private final int NFC_DISABLE_DELAY = 200;
  private final int NFC_ENABLE_DELAY_OFFSET = 700;
  private final int NFC_FINISH_STATUS_TIMEOUT_DELAY = 550;
  private final int NFC_READ_STATUS_TIMEOUT_DELAY = 10000;
  private boolean NRStatus = false;
  private String TAG = "UIWirelessChargeNFCRead";
  private boolean WCStatus = false;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      case 1: 
        FtUtil.log_i(UIWirelessChargeNFCRead.this.CLASS_NAME, "mHandler.handleMessage(msg)", "msg : MSG_READ_STATUS_TIMEOUT");
        UIWirelessChargeNFCRead.this.mNFCResultText.setText("FAIL");
        UIWirelessChargeNFCRead.this.mNFCResultText.setTextColor(-65536);
        return;
      case 4: 
        FtUtil.log_i(UIWirelessChargeNFCRead.this.CLASS_NAME, "mHandler.handleMessage(msg)", "msg : MSG_FINISH_TEST");
        UIWirelessChargeNFCRead.this.finishNFCRead();
        return;
      case 2: 
        Log.i(UIWirelessChargeNFCRead.this.TAG, "send Intent ENABLE_SEC_NFC_DISCOVERY");
        UIWirelessChargeNFCRead.this.sendBroadcast(new Intent("com.sec.android.app.nfctest.NFC_DISCOVERY_ENABLE"));
        return;
      }
      Log.i(UIWirelessChargeNFCRead.this.TAG, "send Intent DISABLE_SEC_NFC_DISCOVERY");
      UIWirelessChargeNFCRead.this.sendBroadcast(new Intent("com.sec.android.app.nfctest.NFC_DISCOVERY_DISABLE"));
    }
  };
  private TextView mNFCResultText;
  private final BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if ("android.nfc.action.TAG_DISCOVERED".equals(paramAnonymousIntent.getAction()))
      {
        Log.i(UIWirelessChargeNFCRead.this.TAG, "Received ACTION_TAG_DISCOVERED");
        UIWirelessChargeNFCRead.access$802(UIWirelessChargeNFCRead.this, true);
      }
      try
      {
        FtUtil.log_i(UIWirelessChargeNFCRead.this.CLASS_NAME, "mhandler", "  WCStatus : " + UIWirelessChargeNFCRead.this.WCStatus + "  NRStatus : " + UIWirelessChargeNFCRead.this.NRStatus);
        if (UIWirelessChargeNFCRead.this.NRStatus)
        {
          UIWirelessChargeNFCRead.this.mNFCResultText.setText("PASS");
          UIWirelessChargeNFCRead.this.mNFCResultText.setTextColor(-16776961);
        }
        if ((UIWirelessChargeNFCRead.this.WCStatus) && (UIWirelessChargeNFCRead.this.NRStatus))
        {
          UIWirelessChargeNFCRead.this.setTestResult((byte)80);
          UIWirelessChargeNFCRead.this.mHandler.sendEmptyMessageDelayed(4, 550L);
        }
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  };
  private Runnable mRunnable = null;
  private TextView mSpeakResultText;
  private Timer mTimer = null;
  private String mstatus;
  private int prev_nvupdate = 0;
  
  public UIWirelessChargeNFCRead()
  {
    super("UIWirelessChargeNFCRead", 39);
  }
  
  private void startDiscovery()
  {
    this.mHandler.sendEmptyMessageDelayed(3, 200L);
    this.mHandler.sendEmptyMessageDelayed(2, 900L);
  }
  
  private void startNFCRead()
  {
    startDiscovery();
    this.mHandler.sendEmptyMessageDelayed(1, 10000L);
    this.mHandler.sendEmptyMessageDelayed(4, 10550L);
  }
  
  public void finishNFCRead()
  {
    finish();
  }
  
  public void finishWirelessCharge()
  {
    terminateWirelessCharge();
    startNFCRead();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903099);
    this.mSpeakResultText = ((TextView)findViewById(2131296792));
    this.mNFCResultText = ((TextView)findViewById(2131296793));
    FtUtil.log_i(this.CLASS_NAME, "onCreate", Support.Kernel.getFilePath("WIRELESS_BATTERY") + ": " + this.mstatus);
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.nfc.action.TAG_DISCOVERED");
    registerReceiver(this.mReceiver, localIntentFilter);
    this.mTimer = new Timer();
    this.mTimer.schedule(new TimerTask()
    {
      public void run()
      {
        UIWirelessChargeNFCRead.this.mHandler.post(UIWirelessChargeNFCRead.access$202(UIWirelessChargeNFCRead.this, new Runnable()
        {
          public void run()
          {
            try
            {
              UIWirelessChargeNFCRead.access$302(UIWirelessChargeNFCRead.this, Support.Kernel.read("WIRELESS_BATTERY"));
              FtUtil.log_i(UIWirelessChargeNFCRead.this.CLASS_NAME, "mhandler", Support.Kernel.getFilePath("WIRELESS_BATTERY") + ": " + UIWirelessChargeNFCRead.this.mstatus);
              if (UIWirelessChargeNFCRead.this.mstatus.contains("1"))
              {
                UIWirelessChargeNFCRead.this.mSpeakResultText.setText(2131165274);
                UIWirelessChargeNFCRead.this.mSpeakResultText.setTextColor(-16776961);
                if (UIWirelessChargeNFCRead.this.prev_nvupdate != 1)
                {
                  UIWirelessChargeNFCRead.access$602(UIWirelessChargeNFCRead.this, true);
                  UIWirelessChargeNFCRead.access$502(UIWirelessChargeNFCRead.this, 1);
                  UIWirelessChargeNFCRead.this.finishWirelessCharge();
                }
              }
              else
              {
                UIWirelessChargeNFCRead.this.mSpeakResultText.setText(2131165392);
                UIWirelessChargeNFCRead.this.mSpeakResultText.setTextColor(-16777216);
                return;
              }
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
            }
          }
        }));
      }
    }, 0L, 500L);
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    terminateWirelessCharge();
    terminateNFC();
  }
  
  protected void onPause()
  {
    super.onPause();
    sendBroadcast(new Intent("com.sec.android.app.nfctest.NFC_TEST_END"));
    sendBroadcast(new Intent("com.sec.android.app.nfctest.NFC_DISCOVERY_DISABLE"));
  }
  
  protected void onResume()
  {
    super.onResume();
    sendBroadcast(new Intent("com.sec.android.app.nfctest.NFC_TEST_START"));
  }
  
  public void terminateNFC()
  {
    if (this.mHandler != null)
    {
      if (this.mRunnable != null)
      {
        this.mHandler.removeCallbacks(this.mRunnable);
        this.mRunnable = null;
      }
      if (this.mHandler.hasMessages(3)) {
        this.mHandler.removeMessages(3);
      }
      if (this.mHandler.hasMessages(1)) {
        this.mHandler.removeMessages(1);
      }
      if (this.mHandler.hasMessages(2)) {
        this.mHandler.removeMessages(2);
      }
      if (this.mHandler.hasMessages(4)) {
        this.mHandler.removeMessages(4);
      }
    }
    if (this.mTimer != null) {
      this.mTimer.cancel();
    }
    unregisterReceiver(this.mReceiver);
  }
  
  public void terminateWirelessCharge()
  {
    if ((this.mRunnable != null) && (this.mHandler != null))
    {
      this.mHandler.removeCallbacks(this.mRunnable);
      this.mRunnable = null;
    }
    if (this.mTimer != null) {
      this.mTimer.cancel();
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIWirelessChargeNFCRead
 * JD-Core Version:    0.7.1
 */