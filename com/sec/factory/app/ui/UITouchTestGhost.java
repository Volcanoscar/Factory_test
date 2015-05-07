package com.sec.factory.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import java.util.Calendar;

public class UITouchTestGhost
  extends Activity
{
  private final String CLASS_NAME = "UITouchTestGhost";
  protected int KEY_TIMEOUT;
  protected int KEY_TIMER_EXPIRED;
  protected int MILLIS_IN_SEC;
  private long mCurrentTime = 0L;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      UITouchTestGhost.this.finish();
    }
  };
  private boolean mIsPressedBackkey = false;
  protected Handler mTimerHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == UITouchTestGhost.this.KEY_TIMER_EXPIRED)
      {
        UITouchTestGhost.access$002(UITouchTestGhost.this, false);
        FtUtil.log_e("UITouchTestGhost", "mTimerHandler.handleMessage", null);
      }
    }
  };
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    FtUtil.log_e("UITouchTestGhost", "onCreate", null);
    requestWindowFeature(1);
    setContentView(2130903092);
    this.MILLIS_IN_SEC = 1000;
    this.KEY_TIMEOUT = 2;
    FtUtil.setRemoveSystemUI(getWindow(), true);
    FtUtil.setSystemKeyBlock(getComponentName(), 3);
    FtUtil.setSystemKeyBlock(getComponentName(), 26);
    FtUtil.setSystemKeyBlock(getComponentName(), 187);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      FtUtil.log_i("UITouchTestGhost", "onKeyDown", "This is back key");
      if (!this.mIsPressedBackkey)
      {
        this.mCurrentTime = Calendar.getInstance().getTimeInMillis();
        this.mIsPressedBackkey = true;
        startTimer();
      }
    }
    while ((paramInt != 25) || (!"tablet".equalsIgnoreCase(Support.Feature.getString("DEVICE_TYPE"))))
    {
      Calendar localCalendar;
      do
      {
        return true;
        this.mIsPressedBackkey = false;
        localCalendar = Calendar.getInstance();
        FtUtil.log_e("UITouchTestGhost", "onKeyDown", "KEYCODE_BACK => rightNow.getTimeInMillis() = " + localCalendar.getTimeInMillis() + "mCurrentTime = " + this.mCurrentTime);
      } while (localCalendar.getTimeInMillis() > this.mCurrentTime + this.KEY_TIMEOUT * this.MILLIS_IN_SEC);
      setResult(0);
      finish();
      return true;
    }
    FtUtil.log_i("UITouchTestGhost", "onKeyDown", "This is volume down_key");
    finish();
    return true;
  }
  
  protected void startTimer()
  {
    this.mTimerHandler.sendEmptyMessageDelayed(this.KEY_TIMER_EXPIRED, this.KEY_TIMEOUT * this.MILLIS_IN_SEC);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UITouchTestGhost
 * JD-Core Version:    0.7.1
 */