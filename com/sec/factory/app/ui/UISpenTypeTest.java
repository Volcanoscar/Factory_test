package com.sec.factory.app.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.System;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;
import java.util.Calendar;

public class UISpenTypeTest
  extends UIBase
{
  protected int KEY_TIMEOUT = 2;
  protected int KEY_TIMER_EXPIRED = 1;
  protected int MILLIS_IN_SEC = 1000;
  private int mActiveType = 0;
  private TextView mCommentText;
  private long mCurrentTime = 0L;
  private Handler mHandler = new Handler();
  private boolean mIsPressedBackkey = false;
  private boolean mIsTestDone = false;
  private LinearLayout mLinearLayout;
  private int mMASK_PEN_ERASER_BUTTON = 0;
  private int mOldHoveringSetting = 0;
  private TextView mResultText;
  private String mTestCase = Support.FactoryTestMenu.getTestCase(FactoryTestManager.convertorID_XML((byte)26));
  protected Handler mTimerHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == UISpenTypeTest.this.KEY_TIMER_EXPIRED) {
        UISpenTypeTest.access$002(UISpenTypeTest.this, false);
      }
    }
  };
  private ViewGroup mViewGroup;
  
  public UISpenTypeTest()
  {
    super("UISpenTypeTest", 26);
  }
  
  private void addButtonTestView()
  {
    if (this.mViewGroup.getChildCount() > 1)
    {
      FtUtil.log_d("UISpenTypeTest", "addButtonTestView", "already added ButtonTestView");
      this.mViewGroup.getChildAt(-1 + this.mViewGroup.getChildCount()).bringToFront();
      FtUtil.log_d("UISpenTypeTest", "addButtonTestView", "getChildCount: " + this.mViewGroup.getChildCount());
      return;
    }
    FtUtil.log_d("UISpenTypeTest", "addButtonTestView", "add ButtonTestView");
    ButtonTestView localButtonTestView = new ButtonTestView(this);
    Paint localPaint = new Paint();
    localPaint.setColor(-256);
    localPaint.setAlpha(0);
    localButtonTestView.setBackgroundColor(localPaint.getColor());
    localButtonTestView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    this.mViewGroup.addView(localButtonTestView, -1 + this.mViewGroup.getChildCount());
    this.mViewGroup.getChildAt(-1 + this.mViewGroup.getChildCount()).bringToFront();
    FtUtil.log_d("UISpenTypeTest", "addButtonTestView", "getChildCount: " + this.mViewGroup.getChildCount());
  }
  
  private void onFinish()
  {
    this.mIsTestDone = true;
    Settings.System.putInt(getContentResolver(), "pen_hovering", this.mOldHoveringSetting);
    Intent localIntent;
    if (this.mActiveType == 0) {
      if (this.mTestCase.contains("STYLE_X"))
      {
        localIntent = new Intent(this, UISpenAccuracyTestStyleX.class);
        localIntent.addFlags(33554432);
        localIntent.putExtra("RemoteCall", true);
        startActivity(localIntent);
      }
    }
    for (;;)
    {
      this.mMASK_PEN_ERASER_BUTTON = 0;
      finish();
      return;
      localIntent = new Intent(this, UISpenAccuracyTest.class);
      break;
      if (this.mActiveType == 1) {
        setTestResult((byte)28, (byte)80);
      }
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Settings.System.putInt(getContentResolver(), "disable_pen_gesture", 1);
    this.mViewGroup = ((FrameLayout)getLayoutInflater().inflate(2130903090, null));
    addButtonTestView();
    setContentView(this.mViewGroup);
    this.mLinearLayout = ((LinearLayout)findViewById(2131296751));
    this.mResultText = ((TextView)findViewById(2131296753));
    this.mCommentText = ((TextView)findViewById(2131296752));
    this.mActiveType = getIntent().getIntExtra("TYPE", -1);
    FtUtil.log_d("UISpenTypeTest", "OnCteate()", "Intent TYPE : " + this.mActiveType);
    if (this.mActiveType == 1) {
      this.mCommentText.setText(2131165362);
    }
    this.mOldHoveringSetting = Settings.System.getInt(getContentResolver(), "pen_hovering", 0);
    Settings.System.putInt(getContentResolver(), "pen_hovering", 0);
  }
  
  protected void onDestroy()
  {
    if (this.mViewGroup != null) {
      this.mViewGroup.removeAllViews();
    }
    super.onDestroy();
  }
  
  public void onExit()
  {
    setResult(0);
    finish();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      if (!this.mIsPressedBackkey)
      {
        this.mCurrentTime = Calendar.getInstance().getTimeInMillis();
        this.mIsPressedBackkey = true;
        startTimer();
      }
      do
      {
        return true;
        this.mIsPressedBackkey = false;
      } while (Calendar.getInstance().getTimeInMillis() > this.mCurrentTime + this.KEY_TIMEOUT * this.MILLIS_IN_SEC);
      Settings.System.putInt(getContentResolver(), "pen_hovering", this.mOldHoveringSetting);
      onExit();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  protected void onPause()
  {
    Settings.System.putInt(getContentResolver(), "disable_pen_gesture", 0);
    super.onPause();
  }
  
  protected void onResume()
  {
    super.onResume();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    FtUtil.log_d("UISpenTypeTest", "onTouchEvent", "getMetaState()=[" + paramMotionEvent.getMetaState() + "]");
    FtUtil.log_d("UISpenTypeTest", "onTouchEvent", "getToolType(0)=[" + paramMotionEvent.getToolType(0) + "]");
    addButtonTestView();
    return true;
  }
  
  protected void startTimer()
  {
    this.mTimerHandler.sendEmptyMessageDelayed(this.KEY_TIMER_EXPIRED, this.KEY_TIMEOUT * this.MILLIS_IN_SEC);
  }
  
  class ButtonTestView
    extends View
  {
    int mOldButtonState;
    
    public ButtonTestView(Context paramContext)
    {
      super();
    }
    
    private boolean onTouchOrHoverEvent(MotionEvent paramMotionEvent, boolean paramBoolean)
    {
      int i = paramMotionEvent.getButtonState();
      (i & (0xFFFFFFFF ^ this.mOldButtonState));
      this.mOldButtonState = i;
      if ((paramMotionEvent.getToolType(0) == 2) && ((0x2 & this.mOldButtonState) != 0) && (UISpenTypeTest.this.mActiveType == 0) && (paramBoolean))
      {
        FtUtil.log_d("UISpenTypeTest", "onTouchOrHoverEvent", "BUTTON_SECONDARY");
        FtUtil.log_d("UISpenTypeTest", "onTouchOrHoverEvent", "event.getMetaState()=[PEN_ON & SIDE_BUTTON_ON]");
        FtUtil.log_d("UISpenTypeTest", "onTouchOrHoverEvent", "PEN_BUTTON Result : PASS");
        UISpenTypeTest.this.mLinearLayout.setBackgroundColor(-16711936);
        UISpenTypeTest.this.mResultText.setText("PASS");
        UISpenTypeTest.this.mResultText.setTextColor(-16776961);
        UISpenTypeTest.this.mHandler.postDelayed(new Runnable()
        {
          public void run()
          {
            if (!UISpenTypeTest.this.mIsTestDone) {
              UISpenTypeTest.this.onFinish();
            }
          }
        }, 500L);
      }
      while ((paramMotionEvent.getToolType(0) != 4) || (UISpenTypeTest.this.mActiveType != 1) || (!paramBoolean)) {
        return true;
      }
      FtUtil.log_d("UISpenTypeTest", "onTouchOrHoverEvent", "event.getMetaState()=[AMETA_ERASER_ON]");
      FtUtil.log_d("UISpenTypeTest", "onTouchOrHoverEvent", "PEN_BUTTON Result : PASS");
      UISpenTypeTest.this.mLinearLayout.setBackgroundColor(-16711936);
      UISpenTypeTest.this.mResultText.setText("PASS");
      UISpenTypeTest.this.mResultText.setTextColor(-16776961);
      UISpenTypeTest.this.mHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          if (!UISpenTypeTest.this.mIsTestDone) {
            UISpenTypeTest.this.onFinish();
          }
        }
      }, 500L);
      return true;
    }
    
    public boolean onHoverEvent(MotionEvent paramMotionEvent)
    {
      return onTouchOrHoverEvent(paramMotionEvent, false);
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      return onTouchOrHoverEvent(paramMotionEvent, true);
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UISpenTypeTest
 * JD-Core Version:    0.7.1
 */