package com.sec.factory.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.TextView;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.FtUtil;
import java.util.Calendar;

public class UISpenDetectionTest
  extends UIBase
{
  protected int KEY_TIMEOUT = 2;
  protected int KEY_TIMER_EXPIRED = 1;
  protected int MILLIS_IN_SEC = 1000;
  BroadcastReceiver SpenDetectionReceiver;
  private long mCurrentTime = 0L;
  private boolean mIsPressedBackkey = false;
  private ModuleDevice mModuleDevice;
  private State mState = this.stateStart;
  private TextView mTextViewPreRelease;
  private TextView mTextViewRelease;
  private TextView mTextViewReleaseFail;
  private TextView mTextViewReleasePass;
  private TextView mTextViewWorking;
  private TextView mTextViewWorkingFail;
  private TextView mTextViewWorkingPass;
  private State statePreRelase = new PreRelaseStatus();
  private State stateRelase = new ReleaseStatus();
  private State stateStart = new StartState();
  private State stateWorking = new WorkingStatus();
  
  public UISpenDetectionTest()
  {
    super("UISpenDetectionTest", 41);
  }
  
  private boolean isSpenReleased()
  {
    FtUtil.log_d(this.CLASS_NAME, "isSpenReleased", "");
    String str = this.CLASS_NAME;
    if (0 > 0) {}
    for (boolean bool = true;; bool = false)
    {
      FtUtil.log_d(str, "isSpenReleased", Boolean.toString(bool));
      if (0 <= 0) {
        break;
      }
      return true;
    }
    return false;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    FtUtil.log_d(this.CLASS_NAME, "onCreate", "");
    super.onCreate(paramBundle);
    setContentView(2130903088);
    this.mTextViewPreRelease = ((TextView)findViewById(2131296742));
    this.mTextViewWorking = ((TextView)findViewById(2131296743));
    this.mTextViewWorkingPass = ((TextView)findViewById(2131296744));
    this.mTextViewWorkingFail = ((TextView)findViewById(2131296745));
    this.mTextViewRelease = ((TextView)findViewById(2131296746));
    this.mTextViewReleasePass = ((TextView)findViewById(2131296747));
    this.mTextViewReleaseFail = ((TextView)findViewById(2131296748));
    this.mModuleDevice = ModuleDevice.instance(this);
    this.mState = this.stateStart;
    this.SpenDetectionReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        boolean bool = paramAnonymousIntent.getBooleanExtra("penInsert", false);
        FtUtil.log_d(UISpenDetectionTest.this.CLASS_NAME, "onReceive", "isPenInsert: " + bool);
        if (UISpenDetectionTest.this.mState == null) {
          return;
        }
        if (bool)
        {
          UISpenDetectionTest.this.mState.detected();
          return;
        }
        UISpenDetectionTest.this.mState.released();
      }
    };
    IntentFilter localIntentFilter = new IntentFilter("com.samsung.pen.INSERT");
    registerReceiver(this.SpenDetectionReceiver, localIntentFilter);
    FtUtil.log_d(this.CLASS_NAME, "onCreate", "registerReceiver");
  }
  
  protected void onDestroy()
  {
    FtUtil.log_d(this.CLASS_NAME, "onDestroy", "");
    unregisterReceiver(this.SpenDetectionReceiver);
    super.onDestroy();
  }
  
  public void onExit()
  {
    FtUtil.log_e(this.CLASS_NAME, "onExit", "");
    finish();
  }
  
  public void onFinish()
  {
    FtUtil.log_e(this.CLASS_NAME, "onFinish", "");
    setTestResult((byte)41, (byte)80);
    finish();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    switch (paramInt)
    {
    default: 
      bool = super.onKeyDown(paramInt, paramKeyEvent);
    }
    Calendar localCalendar;
    do
    {
      return bool;
      FtUtil.log_e(this.CLASS_NAME, "onKeyDown", "KEYCODE_HOME");
      return bool;
      FtUtil.log_e(this.CLASS_NAME, "onKeyDown", "KEYCODE_BACK");
      if (!this.mIsPressedBackkey)
      {
        this.mCurrentTime = Calendar.getInstance().getTimeInMillis();
        this.mIsPressedBackkey = bool;
        startTimer();
        return bool;
      }
      this.mIsPressedBackkey = false;
      localCalendar = Calendar.getInstance();
      FtUtil.log_e(this.CLASS_NAME, "UISpenDetectionTest", "rightNow.getTimeInMillis() = " + localCalendar.getTimeInMillis() + "mCurrentTime = " + this.mCurrentTime);
    } while (localCalendar.getTimeInMillis() > this.mCurrentTime + this.KEY_TIMEOUT * this.MILLIS_IN_SEC);
    this.mModuleDevice.stopVibrate();
    onExit();
    return bool;
  }
  
  protected void onPause()
  {
    FtUtil.log_d(this.CLASS_NAME, "onPause", "");
    super.onPause();
  }
  
  protected void onResume()
  {
    FtUtil.log_d(this.CLASS_NAME, "onResume", "");
    super.onResume();
    if (isSpenReleased()) {
      this.mState.released();
    }
  }
  
  protected void startTimer()
  {
    this.mTimerHandler.sendEmptyMessageDelayed(this.KEY_TIMER_EXPIRED, this.KEY_TIMEOUT * this.MILLIS_IN_SEC);
  }
  
  class PreRelaseStatus
    implements UISpenDetectionTest.State
  {
    PreRelaseStatus() {}
    
    public void detected()
    {
      FtUtil.log_e(UISpenDetectionTest.this.CLASS_NAME, "PreRelaseStatus detected", "");
      UISpenDetectionTest.this.mTextViewWorking.setVisibility(0);
      UISpenDetectionTest.this.mTextViewWorkingPass.setVisibility(0);
      UISpenDetectionTest.this.mModuleDevice.stopVibrate();
      UISpenDetectionTest.this.mModuleDevice.startVibrate(1000L);
      UISpenDetectionTest.access$002(UISpenDetectionTest.this, UISpenDetectionTest.this.stateWorking);
    }
    
    public void released()
    {
      FtUtil.log_e(UISpenDetectionTest.this.CLASS_NAME, "PreRelaseStatus released", "");
    }
  }
  
  class ReleaseStatus
    implements UISpenDetectionTest.State
  {
    ReleaseStatus() {}
    
    public void detected()
    {
      FtUtil.log_e(UISpenDetectionTest.this.CLASS_NAME, "ReleaseStatus detected", "");
    }
    
    public void released()
    {
      FtUtil.log_e(UISpenDetectionTest.this.CLASS_NAME, "ReleaseStatus released", "");
    }
  }
  
  class StartState
    implements UISpenDetectionTest.State
  {
    StartState() {}
    
    public void detected()
    {
      FtUtil.log_e(UISpenDetectionTest.this.CLASS_NAME, "StartState detected", "");
    }
    
    public void released()
    {
      FtUtil.log_e(UISpenDetectionTest.this.CLASS_NAME, "StartState released", "");
      UISpenDetectionTest.this.mTextViewPreRelease.setVisibility(0);
      UISpenDetectionTest.access$002(UISpenDetectionTest.this, UISpenDetectionTest.this.statePreRelase);
    }
  }
  
  static abstract interface State
  {
    public abstract void detected();
    
    public abstract void released();
  }
  
  class WorkingStatus
    implements UISpenDetectionTest.State
  {
    WorkingStatus() {}
    
    public void detected()
    {
      FtUtil.log_e(UISpenDetectionTest.this.CLASS_NAME, "WorkingStatus detected", "");
    }
    
    public void released()
    {
      FtUtil.log_e(UISpenDetectionTest.this.CLASS_NAME, "WorkingStatus released", "");
      UISpenDetectionTest.this.mTextViewRelease.setVisibility(0);
      UISpenDetectionTest.this.mTextViewReleasePass.setVisibility(0);
      UISpenDetectionTest.this.mModuleDevice.stopVibrate();
      UISpenDetectionTest.this.mModuleDevice.startVibrate(1000L);
      UISpenDetectionTest.access$002(UISpenDetectionTest.this, UISpenDetectionTest.this.stateRelase);
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          UISpenDetectionTest.this.mModuleDevice.stopVibrate();
          UISpenDetectionTest.this.onFinish();
        }
      }, 1200L);
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UISpenDetectionTest
 * JD-Core Version:    0.7.1
 */