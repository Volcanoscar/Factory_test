package com.sec.factory.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import com.sec.factory.support.FtUtil;

public class UIOTG
  extends UIBase
{
  private final int COLOR_FAIL = -65536;
  private final int COLOR_PASS = -16742721;
  private final String DESCRIPTION_MEDIA_MOUNTED = "USB Memory Inserted";
  private final String DESCRIPTION_MEDIA_UNMOUNTED = "Please Insert USB Memory";
  private final byte MSG_MEDIA_MOUNTED = 2;
  private final byte MSG_MEDIA_UNMOUNTED = 3;
  private final byte MSG_OTG_CHECK_TEST_START = 0;
  private TextView mDescriptionText;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      case 1: 
      default: 
        return;
      case 0: 
        FtUtil.log_i(UIOTG.this.CLASS_NAME, "mHandler-handleMessage", "msg.what-MSG_OTG_CHECK_TEST_START");
        UIOTG.this.mDescriptionText.setText("Please Insert USB Memory");
        return;
      case 2: 
        FtUtil.log_i(UIOTG.this.CLASS_NAME, "mHandler-handleMessage", "msg.what-MSG_MEDIA_MOUNTED");
        UIOTG.this.mDescriptionText.setText("USB Memory Inserted");
        UIOTG.access$102(UIOTG.this, true);
        UIOTG.this.setTestResult();
        return;
      }
      FtUtil.log_i(UIOTG.this.CLASS_NAME, "mHandler-handleMessage", "msg.what-MSG_MEDIA_UNMOUNTED");
      UIOTG.this.mDescriptionText.setText("Please Insert USB Memory");
      UIOTG.this.setTestResult();
    }
  };
  private boolean mIsTestPass = false;
  private TextView mResultText;
  private BroadcastReceiver mStorageReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      String str2 = paramAnonymousIntent.getData().getPath();
      FtUtil.log_i(UIOTG.this.CLASS_NAME, "mStorageReceiver-onReceive", "action=" + str1);
      FtUtil.log_i(UIOTG.this.CLASS_NAME, "mStorageReceiver-onReceive", "path=" + str2);
      if ("android.intent.action.MEDIA_MOUNTED".equals(str1)) {
        UIOTG.this.mHandler.sendEmptyMessage(2);
      }
      while (!"android.intent.action.MEDIA_UNMOUNTED".equals(str1)) {
        return;
      }
      UIOTG.this.mHandler.sendEmptyMessage(3);
    }
  };
  
  public UIOTG()
  {
    super("UIOTG", 24);
  }
  
  private void registerBroadcastReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    FtUtil.log_i(this.CLASS_NAME, "registerBroadcastReceiver", "registerBroadcastReceiver");
    localIntentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
    localIntentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
    localIntentFilter.addDataScheme("file");
    registerReceiver(this.mStorageReceiver, localIntentFilter);
  }
  
  private void setTestResult()
  {
    TextView localTextView1 = this.mResultText;
    String str;
    TextView localTextView2;
    if (this.mIsTestPass)
    {
      str = "PASS";
      localTextView1.setText(str);
      localTextView2 = this.mResultText;
      if (!this.mIsTestPass) {
        break label62;
      }
    }
    label62:
    for (int i = -16742721;; i = -65536)
    {
      localTextView2.setTextColor(i);
      if (!this.mIsTestPass) {
        break label69;
      }
      setTestResult((byte)80);
      return;
      str = "FAIL";
      break;
    }
    label69:
    setTestResultFailCase((byte)24);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    FtUtil.log_i(this.CLASS_NAME, "onCreate", null);
    setContentView(2130903082);
    this.mResultText = ((TextView)findViewById(2131296300));
    this.mDescriptionText = ((TextView)findViewById(2131296511));
  }
  
  public void onPause()
  {
    super.onPause();
    FtUtil.log_i(this.CLASS_NAME, "onPause", null);
    unregisterReceiver(this.mStorageReceiver);
  }
  
  protected void onResume()
  {
    super.onResume();
    FtUtil.log_i(this.CLASS_NAME, "onResume", null);
    registerBroadcastReceiver();
    this.mHandler.sendEmptyMessage(0);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIOTG
 * JD-Core Version:    0.7.1
 */