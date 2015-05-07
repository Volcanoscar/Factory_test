package com.sec.factory.app.ui;

import android.app.Application;
import android.app.BarBeamException;
import android.app.BarBeamFactory;
import android.app.Hop;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class UIBarcodeEmulator
  extends UIBase
{
  private static BarBeamFactory barbeamFactory = null;
  private final long BACK_KEY_EVENT_TIMELAG = 2000L;
  protected int ONPASS_FINISH_DELAY = 1000;
  private byte[] barcodeByte;
  private Hop[] hopSequenceArray;
  private Button mExitButton;
  private boolean mIsLongPress = false;
  private boolean mIsPass = false;
  private long mPrevBackKeyEventTime = -1L;
  protected Handler mTimerHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      }
      UIBarcodeEmulator.this.finish();
    }
  };
  private BarcodeTestView mView = null;
  
  public UIBarcodeEmulator()
  {
    super("UIBarcodeEmulator", 50);
    Hop[] arrayOfHop = new Hop[10];
    arrayOfHop[0] = new Hop(0, 50, 50, 20, 31);
    arrayOfHop[1] = new Hop(20, 20, 50, 10, 31);
    arrayOfHop[2] = new Hop(2, 50, 50, 12, 31);
    arrayOfHop[3] = new Hop(8, 50, 50, 10, 31);
    arrayOfHop[4] = new Hop(12, 30, 50, 10, 31);
    arrayOfHop[5] = new Hop(1, 50, 50, 16, 31);
    arrayOfHop[6] = new Hop(14, 30, 50, 10, 31);
    arrayOfHop[7] = new Hop(4, 50, 50, 10, 31);
    arrayOfHop[8] = new Hop(10, 40, 50, 10, 31);
    arrayOfHop[9] = new Hop(6, 50, 50, 10, 31);
    this.hopSequenceArray = arrayOfHop;
    this.barcodeByte = new byte[] { -1, -83, -112, -79, 78, -11, -70, -83, -59, -115, 50, 77, -70, -1 };
  }
  
  public void StartBeaming()
  {
    try
    {
      barbeamFactory = new BarBeamFactory(this, this.hopSequenceArray);
      try
      {
        boolean bool2 = barbeamFactory.StartBarBeamFactory(this.barcodeByte);
        bool1 = bool2;
      }
      catch (BarBeamException localBarBeamException2)
      {
        for (;;)
        {
          localBarBeamException2.printStackTrace();
          boolean bool1 = false;
        }
        this.mView.drawResultText(false);
      }
      Log.d("UIBarcodeEmulator", "StartBarBeamFactory-- : " + bool1);
      if (bool1)
      {
        setTestResult((byte)80);
        return;
      }
    }
    catch (BarBeamException localBarBeamException1)
    {
      for (;;)
      {
        localBarBeamException1.printStackTrace();
      }
    }
  }
  
  public void StopBeaming()
  {
    Log.d("UIBarcodeEmulator", "StopBarBeamFactory ++ " + false);
    try
    {
      boolean bool2 = barbeamFactory.StopBarBeamFactory();
      bool1 = bool2;
    }
    catch (BarBeamException localBarBeamException)
    {
      for (;;)
      {
        Log.d("UIBarcodeEmulator", "exception in StopBarBeamFactory", localBarBeamException);
        boolean bool1 = false;
      }
    }
    Log.d("UIBarcodeEmulator", "StopBarBeamFactory -- " + bool1);
  }
  
  protected void finishOnPassWithTimer()
  {
    this.mTimerHandler.sendEmptyMessageDelayed(1, this.ONPASS_FINISH_DELAY);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mView = new BarcodeTestView(this);
    setContentView(this.mView);
    this.mExitButton = new Button(this);
    this.mExitButton.setTextSize(25.0F);
    this.mExitButton.setText("Exit");
    this.mExitButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UIBarcodeEmulator.this.finish();
      }
    });
    RelativeLayout localRelativeLayout = new RelativeLayout(this);
    localRelativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
    Point localPoint = new Point();
    ((WindowManager)getApplication().getSystemService("window")).getDefaultDisplay().getSize(localPoint);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(localPoint.x / 3, localPoint.y / 8);
    localLayoutParams.addRule(12);
    localLayoutParams.addRule(14);
    localLayoutParams.bottomMargin = (localPoint.y / 8);
    this.mExitButton.setLayoutParams(localLayoutParams);
    localRelativeLayout.addView(this.mExitButton);
    addContentView(localRelativeLayout, new RelativeLayout.LayoutParams(-1, -1));
    Log.i("UIBarcodeEmulator", "onCreate");
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (!this.mIsLongPress) {
      this.mIsLongPress = paramKeyEvent.isLongPress();
    }
    if (!this.mIsLongPress) {}
    switch (paramInt)
    {
    default: 
      this.mPrevBackKeyEventTime = -1L;
      Log.d("UIBarcodeEmulator", "onKeyDown return true");
      return true;
    }
    Log.d("UIBarcodeEmulator", "KEYCODE_BACK => Prev : " + this.mPrevBackKeyEventTime + ", Curr : " + paramKeyEvent.getEventTime() + " => Time Lag : " + (paramKeyEvent.getEventTime() - this.mPrevBackKeyEventTime) + " [" + 2000L + "]");
    if (this.mPrevBackKeyEventTime != -1L) {
      if (paramKeyEvent.getEventTime() - this.mPrevBackKeyEventTime < 2000L) {
        finish();
      }
    }
    for (;;)
    {
      Log.d("UIBarcodeEmulator", "KEYCODE_BACK = return true");
      return true;
      this.mPrevBackKeyEventTime = paramKeyEvent.getEventTime();
      continue;
      this.mPrevBackKeyEventTime = paramKeyEvent.getEventTime();
    }
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    this.mIsLongPress = false;
    Log.d("UIBarcodeEmulator", "onKeyUp return true");
    return true;
  }
  
  protected void onPause()
  {
    super.onPause();
    StopBeaming();
  }
  
  protected void onResume()
  {
    super.onResume();
    StartBeaming();
  }
  
  public class BarcodeTestView
    extends View
  {
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mEmptyPaint;
    private int mHeight;
    private Paint mInfoPaint;
    private final int mMargin = 15;
    private int mWidth;
    
    public BarcodeTestView(Context paramContext)
    {
      super();
      setKeepScreenOn(true);
      Display localDisplay = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
      Point localPoint = new Point();
      localDisplay.getSize(localPoint);
      this.mWidth = (localPoint.x / 8);
      this.mHeight = (localPoint.y / 8);
      Log.i("UIBarcodeEmulator", "size: " + this.mWidth + " x " + this.mHeight);
      this.mEmptyPaint = new Paint();
      this.mEmptyPaint.setAntiAlias(false);
      this.mEmptyPaint.setColor(-1);
      this.mInfoPaint = new Paint();
      this.mInfoPaint.setAntiAlias(true);
      this.mInfoPaint.setStyle(Paint.Style.FILL);
      this.mInfoPaint.setColor(-16777216);
      this.mInfoPaint.setTextSize(70.0F);
      this.mInfoPaint.setTextAlign(Paint.Align.CENTER);
      this.mBitmap = Bitmap.createBitmap(8 * this.mWidth, 8 * this.mHeight, Bitmap.Config.ARGB_8888);
      this.mCanvas = new Canvas(this.mBitmap);
      this.mCanvas.drawRect(0.0F, 0.0F, 8 * this.mWidth, 8 * this.mHeight, this.mEmptyPaint);
      this.mCanvas.drawText("1234567890128", 4 * this.mWidth, -15 + 1 * this.mHeight, this.mInfoPaint);
      this.mCanvas.drawText("BARCODE EMUL", 4 * this.mWidth, -15 + 3 * this.mHeight, this.mInfoPaint);
      Drawable localDrawable = getResources().getDrawable(2130837508);
      localDrawable.setBounds(0, 0, 8 * this.mWidth, 2 * (this.mHeight / 5));
      localDrawable.draw(this.mCanvas);
    }
    
    private void drawResultText(boolean paramBoolean)
    {
      Paint localPaint = new Paint();
      String str;
      if (paramBoolean == true)
      {
        str = getResources().getString(2131165274);
        localPaint.setColor(-16776961);
      }
      for (;;)
      {
        localPaint.setTextAlign(Paint.Align.CENTER);
        localPaint.setAntiAlias(true);
        localPaint.setTextSize(150.0F);
        this.mCanvas.drawText(str, 4 * this.mWidth, 5 * this.mHeight, localPaint);
        invalidate();
        return;
        str = getResources().getString(2131165275);
        localPaint.setColor(-65536);
      }
    }
    
    protected void onDraw(Canvas paramCanvas)
    {
      super.onDraw(paramCanvas);
      try
      {
        paramCanvas.drawBitmap(this.mBitmap, 0.0F, 0.0F, null);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIBarcodeEmulator
 * JD-Core Version:    0.7.1
 */