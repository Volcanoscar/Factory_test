package com.sec.factory.app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Kernel;
import java.util.Timer;
import java.util.TimerTask;

public class UIWirelessCharge
  extends UIBase
{
  private String TAG = "UIWirelessCharge";
  private Handler mHandler = new Handler();
  private Runnable mRunnable = null;
  private TextView mSpeakResultText;
  private Timer mTimer = null;
  private String mstatus;
  private int prev_nvupdate = 0;
  
  public UIWirelessCharge()
  {
    super("UIWirelessCharge", 32);
  }
  
  public void finishOperation()
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
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903098);
    this.mSpeakResultText = ((TextView)findViewById(2131296790));
    FtUtil.log_i(this.CLASS_NAME, "onCreate", Support.Kernel.getFilePath("WIRELESS_BATTERY") + ": " + this.mstatus);
    this.mTimer = new Timer();
    this.mTimer.schedule(new TimerTask()
    {
      public void run()
      {
        UIWirelessCharge.this.mHandler.post(UIWirelessCharge.access$002(UIWirelessCharge.this, new Runnable()
        {
          public void run()
          {
            try
            {
              UIWirelessCharge.access$102(UIWirelessCharge.this, Support.Kernel.read("WIRELESS_BATTERY"));
              FtUtil.log_i(UIWirelessCharge.this.CLASS_NAME, "mHandler", Support.Kernel.getFilePath("WIRELESS_BATTERY") + ": " + UIWirelessCharge.this.mstatus);
              if (UIWirelessCharge.this.mstatus.contains("1"))
              {
                UIWirelessCharge.this.mSpeakResultText.setText(2131165274);
                UIWirelessCharge.this.mSpeakResultText.setTextColor(-16776961);
                if (UIWirelessCharge.this.prev_nvupdate != 1)
                {
                  UIWirelessCharge.this.setTestResult((byte)80);
                  UIWirelessCharge.access$302(UIWirelessCharge.this, 1);
                  UIWirelessCharge.this.finishOnPassWithTimer();
                }
              }
              else
              {
                UIWirelessCharge.this.mSpeakResultText.setText(2131165392);
                UIWirelessCharge.this.mSpeakResultText.setTextColor(-16777216);
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
    finishOperation();
    super.onDestroy();
  }
  
  protected void onPause()
  {
    super.onPause();
    finishOperation();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIWirelessCharge
 * JD-Core Version:    0.7.1
 */