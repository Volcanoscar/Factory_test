package com.sec.factory.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.System;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnHoverListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sec.factory.support.Support.Version;
import java.util.Calendar;

public class UISpenHoveringTest
  extends UIBase
{
  protected int KEY_TIMEOUT = 2;
  protected int KEY_TIMER_EXPIRED = 1;
  protected int MILLIS_IN_SEC = 1000;
  private long mCurrentTime = 0L;
  private View mHoverView;
  private boolean mIsCounting = false;
  private boolean mIsPass = true;
  private boolean mIsPressedBackkey = false;
  private boolean mIsWacom = true;
  private LinearLayout mLinearLayout;
  private int mOldHoveringSetting = 0;
  private TextView mResultText;
  protected Handler mTimerHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == UISpenHoveringTest.this.KEY_TIMER_EXPIRED)
      {
        UISpenHoveringTest.access$302(UISpenHoveringTest.this, false);
        Log.e("SpenHoveringTest", "mTimerHandler");
      }
    }
  };
  
  public UISpenHoveringTest()
  {
    super("UISpenHoveringTest", 27);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    Log.d("SpenHoveringTest", "onActivityResult :: requestCode : " + paramInt1 + ", resultCode : " + paramInt2);
    if (paramInt2 == -1)
    {
      onFinish();
      return;
    }
    onExit();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mHoverView = new HoverView(this);
    setContentView(this.mHoverView);
    this.mHoverView.setOnHoverListener((View.OnHoverListener)this.mHoverView);
    this.mLinearLayout = ((LinearLayout)findViewById(2131296749));
    this.mResultText = ((TextView)findViewById(2131296750));
    this.mOldHoveringSetting = Settings.System.getInt(getContentResolver(), "pen_hovering", 0);
    Settings.System.putInt(getContentResolver(), "pen_hovering", 1);
  }
  
  protected void onDestroy()
  {
    Settings.System.putInt(getContentResolver(), "pen_hovering", this.mOldHoveringSetting);
    super.onDestroy();
  }
  
  public void onExit()
  {
    Log.e("SpenHoveringTest", "Result Fail");
    finish();
  }
  
  public void onFinish()
  {
    Log.e("SpenHoveringTest", "Result Pass");
    setTestResult((byte)27, (byte)80);
    finish();
  }
  
  protected void onPause()
  {
    super.onPause();
  }
  
  protected void onResume()
  {
    super.onResume();
  }
  
  protected void startTimer()
  {
    this.mTimerHandler.sendEmptyMessageDelayed(this.KEY_TIMER_EXPIRED, this.KEY_TIMEOUT * this.MILLIS_IN_SEC);
  }
  
  public class HoverView
    extends View
    implements View.OnHoverListener
  {
    private ExitRunnable exitRunnable = new ExitRunnable();
    private boolean isTouchDown = false;
    private Bitmap mHoverBitmap;
    private Canvas mHoverCanvas;
    private Paint mHoverPaint;
    private boolean mIsCheckRange = false;
    private Bitmap mLineBitmap;
    private Canvas mLineCanvas;
    private Paint mLinePaint;
    private String mMessageText;
    private float mPointX = 0.0F;
    private float mPointY = 0.0F;
    private float mPreTouchedX = 0.0F;
    private float mPreTouchedY = 0.0F;
    private String mResultFailText;
    private String mResultHoveringText;
    private String mResultText;
    private int mScreenHeight;
    private int mScreenWidth;
    private Paint mTextPaint;
    private Paint mTextResultPaint;
    private String mTitle;
    private Paint mTouchPaint;
    
    public HoverView(Context paramContext)
    {
      super();
      setFocusableInTouchMode(true);
      initHoverView();
    }
    
    private void changeView(Paint paramPaint, String paramString)
    {
      this.mHoverCanvas.drawRect(0.0F, 0.0F, this.mScreenWidth, this.mScreenHeight, paramPaint);
      this.mHoverCanvas.drawText(this.mTitle, this.mScreenWidth / 2, 200.0F, this.mTextPaint);
      this.mHoverCanvas.drawText(this.mMessageText, this.mScreenWidth / 2, 500.0F, this.mTextPaint);
      this.mHoverCanvas.drawText(paramString, this.mScreenWidth / 2, 700.0F, this.mTextResultPaint);
    }
    
    private void drawByEvent(MotionEvent paramMotionEvent)
    {
      switch (paramMotionEvent.getAction())
      {
      }
      do
      {
        do
        {
          return;
          this.mPointX = paramMotionEvent.getX();
          this.mPointY = paramMotionEvent.getY();
          this.isTouchDown = true;
          return;
        } while (!this.isTouchDown);
        for (int i = 0; i < paramMotionEvent.getHistorySize(); i++)
        {
          this.mPreTouchedX = this.mPointX;
          this.mPreTouchedY = this.mPointY;
          this.mPointX = paramMotionEvent.getHistoricalX(i);
          this.mPointY = paramMotionEvent.getHistoricalY(i);
          drawLine(this.mPreTouchedX, this.mPreTouchedY, this.mPointX, this.mPointY);
        }
        this.mPreTouchedX = this.mPointX;
        this.mPreTouchedY = this.mPointY;
        this.mPointX = paramMotionEvent.getX();
        this.mPointY = paramMotionEvent.getY();
        drawLine(this.mPreTouchedX, this.mPreTouchedY, this.mPointX, this.mPointY);
        this.isTouchDown = true;
        return;
      } while (!this.isTouchDown);
      this.mPreTouchedX = this.mPointX;
      this.mPreTouchedY = this.mPointY;
      this.mPointX = paramMotionEvent.getX();
      this.mPointY = paramMotionEvent.getY();
      if ((this.mPreTouchedX == this.mPointX) && (this.mPreTouchedY == this.mPointY)) {
        drawPoint(this.mPointX, this.mPointY);
      }
      this.isTouchDown = false;
    }
    
    private void drawLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      this.mLineCanvas.drawLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.mLinePaint);
      int i;
      int j;
      int k;
      if (paramFloat1 >= paramFloat3)
      {
        i = (int)paramFloat1;
        j = (int)paramFloat3;
        if (paramFloat2 < paramFloat4) {
          break label89;
        }
        k = (int)paramFloat2;
      }
      for (int m = (int)paramFloat4;; m = (int)paramFloat2)
      {
        invalidate(new Rect(j - 6, m - 6, i + 6, k + 6));
        return;
        i = (int)paramFloat3;
        j = (int)paramFloat1;
        break;
        label89:
        k = (int)paramFloat4;
      }
    }
    
    private void drawPoint(float paramFloat1, float paramFloat2)
    {
      this.mLineCanvas.drawPoint(paramFloat1, paramFloat2, this.mLinePaint);
    }
    
    private void initHoverView()
    {
      this.mScreenWidth = UISpenHoveringTest.this.getWindowManager().getDefaultDisplay().getWidth();
      this.mScreenHeight = UISpenHoveringTest.this.getWindowManager().getDefaultDisplay().getHeight();
      this.mHoverPaint = new Paint();
      this.mHoverPaint.setStrokeWidth(4.0F);
      this.mHoverPaint.setAntiAlias(false);
      this.mHoverPaint.setColor(-1);
      this.mTouchPaint = new Paint();
      this.mTouchPaint.setAntiAlias(false);
      this.mTouchPaint.setColor(-65536);
      this.mLinePaint = new Paint();
      this.mLinePaint.setStrokeWidth(4.0F);
      this.mLinePaint.setColor(-16777216);
      this.mTextPaint = new Paint();
      this.mTextPaint.setColor(-16777216);
      this.mTextPaint.setTextAlign(Paint.Align.CENTER);
      this.mTextPaint.setAntiAlias(false);
      this.mTextPaint.setTextSize(55.0F);
      this.mTextResultPaint = new Paint();
      this.mTextResultPaint.setColor(-16776961);
      this.mTextResultPaint.setTextAlign(Paint.Align.CENTER);
      this.mTextResultPaint.setAntiAlias(false);
      this.mTextResultPaint.setTextSize(70.0F);
      this.mHoverBitmap = Bitmap.createBitmap(this.mScreenWidth, this.mScreenHeight, Bitmap.Config.ARGB_8888);
      this.mHoverCanvas = new Canvas(this.mHoverBitmap);
      this.mLineBitmap = Bitmap.createBitmap(this.mScreenWidth, this.mScreenHeight, Bitmap.Config.ARGB_8888);
      this.mLineCanvas = new Canvas(this.mLineBitmap);
      this.mTitle = getResources().getString(2131165363);
      this.mMessageText = getResources().getString(2131165364);
      this.mResultText = getResources().getString(2131165366);
      this.mResultHoveringText = getResources().getString(2131165365);
      this.mResultFailText = getResources().getString(2131165275);
      initText();
      invalidate();
    }
    
    private void initText()
    {
      this.mHoverCanvas.drawRect(0.0F, 0.0F, this.mScreenWidth, this.mScreenHeight, this.mHoverPaint);
      this.mHoverCanvas.drawText(this.mTitle, this.mScreenWidth / 2, 200.0F, this.mTextPaint);
      this.mHoverCanvas.drawText(this.mMessageText, this.mScreenWidth / 2, 500.0F, this.mTextPaint);
      this.mHoverCanvas.drawText(this.mResultText, this.mScreenWidth / 2, 700.0F, this.mTextResultPaint);
    }
    
    protected void onDraw(Canvas paramCanvas)
    {
      paramCanvas.drawBitmap(this.mHoverBitmap, 0.0F, 0.0F, null);
      paramCanvas.drawBitmap(this.mLineBitmap, 0.0F, 0.0F, null);
    }
    
    public boolean onHover(View paramView, MotionEvent paramMotionEvent)
    {
      switch (paramMotionEvent.getAction())
      {
      }
      do
      {
        do
        {
          return false;
        } while ((!UISpenHoveringTest.this.mIsWacom) || (UISpenHoveringTest.this.mIsCounting) || (!UISpenHoveringTest.this.mIsPass));
        UISpenHoveringTest.access$202(UISpenHoveringTest.this, true);
        this.mHoverPaint.setColor(-16711936);
        changeView(this.mHoverPaint, this.mResultHoveringText);
        invalidate();
        UISpenHoveringTest.this.mTimerHandler.postDelayed(this.exitRunnable, 500L);
        return false;
      } while (!UISpenHoveringTest.this.mIsPass);
      UISpenHoveringTest.access$202(UISpenHoveringTest.this, false);
      this.mHoverPaint.setColor(-1);
      changeView(this.mHoverPaint, this.mResultText);
      invalidate();
      UISpenHoveringTest.this.mTimerHandler.removeCallbacks(this.exitRunnable);
      return false;
    }
    
    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
    {
      if (paramInt == 3) {
        Log.i("SpenHoveringTest", "Keycode Home!");
      }
      Calendar localCalendar1;
      do
      {
        return true;
        if (paramInt != 4) {
          break;
        }
        Log.e("SpenHoveringTest", "This is back_key");
        if (!UISpenHoveringTest.this.mIsPressedBackkey)
        {
          Calendar localCalendar2 = Calendar.getInstance();
          UISpenHoveringTest.access$402(UISpenHoveringTest.this, localCalendar2.getTimeInMillis());
          UISpenHoveringTest.access$302(UISpenHoveringTest.this, true);
          UISpenHoveringTest.this.startTimer();
          return true;
        }
        UISpenHoveringTest.access$302(UISpenHoveringTest.this, false);
        localCalendar1 = Calendar.getInstance();
        Log.e("SpenHoveringTest", "rightNow.getTimeInMillis() = " + localCalendar1.getTimeInMillis() + "mCurrentTime = " + UISpenHoveringTest.this.mCurrentTime);
      } while (localCalendar1.getTimeInMillis() > UISpenHoveringTest.this.mCurrentTime + UISpenHoveringTest.this.KEY_TIMEOUT * UISpenHoveringTest.this.MILLIS_IN_SEC);
      UISpenHoveringTest.this.onExit();
      return true;
      return super.onKeyDown(paramInt, paramKeyEvent);
    }
    
    public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
    {
      return true;
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      if (UISpenHoveringTest.this.mIsWacom) {
        if (paramMotionEvent.getToolType(0) == 2)
        {
          Log.i("SpenHoveringTest", "Draw!");
          UISpenHoveringTest.access$102(UISpenHoveringTest.this, false);
          UISpenHoveringTest.access$202(UISpenHoveringTest.this, false);
          UISpenHoveringTest.this.mTimerHandler.removeCallbacks(this.exitRunnable);
          changeView(this.mTouchPaint, this.mResultFailText);
          invalidate();
          drawByEvent(paramMotionEvent);
        }
      }
      for (;;)
      {
        return true;
        if (paramMotionEvent.getToolType(0) != 2) {
          drawByEvent(paramMotionEvent);
        }
      }
    }
    
    class ExitRunnable
      implements Runnable
    {
      ExitRunnable() {}
      
      public void run()
      {
        if (UISpenHoveringTest.this.mIsPass)
        {
          UISpenHoveringTest.access$102(UISpenHoveringTest.this, false);
          UISpenHoveringTest.HoverView.this.mTextResultPaint.setTextSize(100.0F);
          UISpenHoveringTest.HoverView.this.mHoverCanvas.drawText("PASS", UISpenHoveringTest.HoverView.this.mScreenWidth / 2, 1000.0F, UISpenHoveringTest.HoverView.this.mTextResultPaint);
          UISpenHoveringTest.HoverView.this.invalidate();
          UISpenHoveringTest.this.mTimerHandler.postDelayed(new Runnable()
          {
            public void run()
            {
              String str = Support.Version.getString("FACTORY_TEST_APP");
              Log.d("SpenHoveringTest", "FACTORY_TEST_APP : " + str);
              if (Integer.parseInt(str.substring(2)) >= 18)
              {
                Log.d("SpenHoveringTest", "startSPenHoveringDraw test");
                Intent localIntent = new Intent(UISpenHoveringTest.HoverView.this.mContext, UISpenHoveringDrawTest.class);
                ((Activity)UISpenHoveringTest.HoverView.this.mContext).startActivityForResult(localIntent, 27);
                return;
              }
              UISpenHoveringTest.this.onFinish();
            }
          }, 500L);
        }
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UISpenHoveringTest
 * JD-Core Version:    0.7.1
 */