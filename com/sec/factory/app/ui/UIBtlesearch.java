package com.sec.factory.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.sec.factory.modules.ModuleCommunication;
import com.sec.factory.support.FtUtil;
import java.util.Timer;
import java.util.TimerTask;

public class UIBtlesearch
  extends UIBase
  implements View.OnClickListener
{
  private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      String str2 = paramAnonymousIntent.getStringExtra("result");
      FtUtil.log_d(UIBtlesearch.this.CLASS_NAME, "mBroadcastReceiver.onReceive", "action= " + str1 + "result= " + str2);
      UIBtlesearch localUIBtlesearch;
      if ((!UIBtlesearch.this.mIsTestDone) && (str1.equals("com.sec.factory.intent.ACTION_BT_RESPONSE")))
      {
        FtUtil.log_i(UIBtlesearch.this.CLASS_NAME, "mBroadcastReceiver.onReceive", "ACTION_BT_RESPONSE");
        localUIBtlesearch = UIBtlesearch.this;
        if (!"FOUND".equals(str2)) {
          break label142;
        }
      }
      label142:
      for (boolean bool = true;; bool = false)
      {
        UIBtlesearch.access$902(localUIBtlesearch, bool);
        FtUtil.log_i(UIBtlesearch.this.CLASS_NAME, "mBroadcastReceiver.onReceive", "BT Response Received..Stop Timer");
        UIBtlesearch.this.mHandler.sendEmptyMessage(102);
        return;
      }
    }
  };
  private TimerTask mBtLeSearchtask;
  private TextView mBtleResult;
  private Button mBtleRetryButton;
  private TextView mBtleTimeCount;
  private int mCount = 90;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      case 100: 
        UIBtlesearch.access$002(UIBtlesearch.this, false);
        UIBtlesearch.this.mBtleTimeCount.setText("Time: " + String.valueOf(UIBtlesearch.this.mCount));
        UIBtlesearch.this.mBtleRetryButton.setClickable(false);
        UIBtlesearch.this.startBtSearch();
        UIBtlesearch.this.mBtleResult.setText("SEARCHING..");
        UIBtlesearch.this.mBtleResult.setTextColor(-16777216);
        try
        {
          if (UIBtlesearch.this.mTimer != null)
          {
            UIBtlesearch.this.mTimer.schedule(UIBtlesearch.this.mBtLeSearchtask, 1000L, 1000L);
            return;
          }
        }
        catch (IllegalStateException localIllegalStateException)
        {
          FtUtil.log_e(localIllegalStateException);
          return;
        }
        UIBtlesearch.access$602(UIBtlesearch.this, new Timer());
        UIBtlesearch.this.mTimer.schedule(UIBtlesearch.this.mBtLeSearchtask, 1000L, 1000L);
        return;
      case 101: 
        UIBtlesearch.this.mBtleTimeCount.setText("Time: " + String.valueOf(UIBtlesearch.this.mCount));
        return;
      case 102: 
        UIBtlesearch.this.mBtLeSearchtask.cancel();
        UIBtlesearch.this.mHandler.sendEmptyMessage(103);
        ModuleCommunication.instance(UIBtlesearch.this).btSearchStop();
        return;
      }
      if (UIBtlesearch.this.mIsTestPassed)
      {
        FtUtil.log_i(UIBtlesearch.this.CLASS_NAME, "mHandler.handleMessge", "BT LE SEARCH PASS");
        UIBtlesearch.this.mBtleResult.setText("PASS");
        UIBtlesearch.this.mBtleResult.setTextColor(-16776961);
        UIBtlesearch.access$902(UIBtlesearch.this, false);
        UIBtlesearch.this.setTestResult((byte)38, (byte)80);
        UIBtlesearch.this.finishOnPassWithTimer();
      }
      for (;;)
      {
        UIBtlesearch.access$102(UIBtlesearch.this, 90);
        UIBtlesearch.access$002(UIBtlesearch.this, true);
        UIBtlesearch.this.mBtleRetryButton.setClickable(true);
        return;
        FtUtil.log_i(UIBtlesearch.this.CLASS_NAME, "mHandler.handleMessge", "BT LE SEARCH FAIL");
        UIBtlesearch.this.mBtleResult.setText("FAIL");
        UIBtlesearch.this.mBtleResult.setTextColor(-65536);
      }
    }
  };
  private boolean mIsTestDone = false;
  private boolean mIsTestPassed = false;
  private Timer mTimer;
  
  public UIBtlesearch()
  {
    super("UIBtlesearch", 38);
  }
  
  private TimerTask createNewTask()
  {
    new TimerTask()
    {
      public void run()
      {
        if (UIBtlesearch.this.mCount > 0)
        {
          UIBtlesearch.access$120(UIBtlesearch.this, 1);
          UIBtlesearch.this.mHandler.sendEmptyMessage(101);
          return;
        }
        UIBtlesearch.this.mHandler.sendEmptyMessage(102);
      }
    };
  }
  
  private void init()
  {
    this.mBtleTimeCount = ((TextView)findViewById(2131296382));
    this.mBtleResult = ((TextView)findViewById(2131296384));
    this.mBtleRetryButton = ((Button)findViewById(2131296383));
    this.mBtleRetryButton.setOnClickListener(this);
    this.mTimer = new Timer();
    this.mBtLeSearchtask = createNewTask();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.sec.factory.intent.ACTION_BT_RESPONSE");
    registerReceiver(this.mBroadcastReceiver, localIntentFilter);
  }
  
  private void startBtSearch()
  {
    FtUtil.log_i(this.CLASS_NAME, "startBtSearch()", "BT Search Start!");
    ModuleCommunication.instance(this).bleSearchStartWithAck();
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    }
    this.mBtLeSearchtask = createNewTask();
    this.mHandler.sendEmptyMessage(100);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903054);
    init();
  }
  
  protected void onDestroy()
  {
    if (this.mBtLeSearchtask != null)
    {
      this.mBtLeSearchtask.cancel();
      this.mBtLeSearchtask = null;
    }
    if (this.mTimer != null)
    {
      this.mTimer.cancel();
      this.mTimer = null;
    }
    super.onDestroy();
  }
  
  protected void onPause()
  {
    super.onPause();
    unregisterReceiver(this.mBroadcastReceiver);
    ModuleCommunication.instance(this).btSearchStop();
  }
  
  protected void onResume()
  {
    super.onResume();
    this.mHandler.sendEmptyMessage(100);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIBtlesearch
 * JD-Core Version:    0.7.1
 */