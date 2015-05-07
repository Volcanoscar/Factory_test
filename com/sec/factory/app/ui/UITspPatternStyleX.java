package com.sec.factory.app.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.modules.ModuleTouchScreen;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Spec;
import com.sec.factory.support.Support.TestCase;
import java.lang.reflect.Array;
import java.util.Calendar;

public class UITspPatternStyleX
  extends UIBase
{
  private int HEIGHT_BASIS = 19;
  private int HEIGHT_BASIS_CROSS = 19;
  protected int KEY_TIMEOUT;
  protected int KEY_TIMER_EXPIRED;
  protected int MILLIS_IN_SEC;
  private final int REQUEST_GHOST_TEST = 0;
  private int WIDTH_BASIS = 11;
  private int WIDTH_BASIS_CROSS = 11;
  private boolean[][] click;
  private boolean dialog_showing = false;
  private boolean[][] draw;
  private boolean[] drawCross;
  private boolean[][] isDrawArea;
  private int mBottommostOfMatrix;
  private int mCenterOfHorizontalOfMatrix;
  private int mCenterOfVerticalOfMatrix;
  private long mCurrentTime = 0L;
  private boolean mIsPressedBackkey = false;
  private int mLeftmostOfMatrix;
  private int mRightmostOfMatrix;
  protected Handler mTimerHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == UITspPatternStyleX.this.KEY_TIMER_EXPIRED)
      {
        UITspPatternStyleX.access$002(UITspPatternStyleX.this, false);
        FtUtil.log_e(UITspPatternStyleX.this.CLASS_NAME, "mTimerHandler.handleMessage", null);
      }
    }
  };
  private int mTopmostOfMatrix;
  private boolean needFailPopupDispaly = false;
  private boolean remoteCall = false;
  private boolean successTest = true;
  
  public UITspPatternStyleX()
  {
    super("UITspPatternStyleX", 10);
  }
  
  private void decideRemote()
  {
    this.remoteCall = getIntent().getBooleanExtra("RemoteCall", false);
  }
  
  private void fillUpMatrix()
  {
    for (int i = 0; i < this.HEIGHT_BASIS; i++)
    {
      int j = 0;
      if (j < this.WIDTH_BASIS)
      {
        if (isNeededCheck(i, j)) {
          this.isDrawArea[i][j] = 1;
        }
        for (;;)
        {
          j++;
          break;
          this.isDrawArea[i][j] = 0;
        }
      }
    }
  }
  
  private boolean isNeededCheck(int paramInt1, int paramInt2)
  {
    return (paramInt1 == this.mTopmostOfMatrix) || (paramInt1 == this.mBottommostOfMatrix) || (paramInt2 == this.mLeftmostOfMatrix) || (paramInt2 == this.mRightmostOfMatrix);
  }
  
  private boolean isPass()
  {
    boolean bool = true;
    for (int i = 0; i < this.HEIGHT_BASIS; i++)
    {
      int j = 0;
      if (j < this.WIDTH_BASIS)
      {
        if (this.isDrawArea[i][j] == 1) {
          if ((!bool) || (this.draw[i][j] == 0)) {
            break label57;
          }
        }
        label57:
        for (bool = true;; bool = false)
        {
          j++;
          break;
        }
      }
    }
    return bool;
  }
  
  private boolean isPassCross()
  {
    boolean bool = true;
    int i = 0;
    if (i < 2 * this.HEIGHT_BASIS_CROSS)
    {
      if ((bool) && (this.drawCross[i] != 0)) {}
      for (bool = true;; bool = false)
      {
        i++;
        break;
      }
    }
    return bool;
  }
  
  private void setTSP()
  {
    this.WIDTH_BASIS = Support.Spec.getInt("TSP_NODE_COUNT_WIDTH");
    if (this.WIDTH_BASIS < 0)
    {
      if (Support.TestCase.getEnabled("IS_TSP_STANDARD"))
      {
        FtUtil.log_d(this.CLASS_NAME, "setTSP", "Read> ModuleTouchScreen");
        ModuleTouchScreen.instance(this).getTSPResult(ModuleTouchScreen.TSP_ID__POWER_ON);
        this.WIDTH_BASIS = Integer.valueOf(ModuleTouchScreen.instance(this).getTSPChannelCountX()).intValue();
      }
    }
    else
    {
      this.HEIGHT_BASIS = Support.Spec.getInt("TSP_NODE_COUNT_HEIGHT");
      if (this.HEIGHT_BASIS < 0)
      {
        if (!Support.TestCase.getEnabled("IS_TSP_STANDARD")) {
          break label366;
        }
        FtUtil.log_d(this.CLASS_NAME, "setTSP", "Read> ModuleTouchScreen");
        ModuleTouchScreen.instance(this).getTSPResult(ModuleTouchScreen.TSP_ID__POWER_ON);
      }
    }
    for (this.HEIGHT_BASIS = Integer.valueOf(ModuleTouchScreen.instance(this).getTSPChannelCountY()).intValue();; this.HEIGHT_BASIS = Integer.valueOf(ModuleDevice.instance(this).startTSPTest("get_y_num")).intValue())
    {
      this.HEIGHT_BASIS_CROSS = (2 * (-2 + this.HEIGHT_BASIS));
      this.WIDTH_BASIS_CROSS = this.HEIGHT_BASIS_CROSS;
      int[] arrayOfInt1 = { this.HEIGHT_BASIS, this.WIDTH_BASIS };
      this.click = ((boolean[][])Array.newInstance(Boolean.TYPE, arrayOfInt1));
      int[] arrayOfInt2 = { this.HEIGHT_BASIS, this.WIDTH_BASIS };
      this.draw = ((boolean[][])Array.newInstance(Boolean.TYPE, arrayOfInt2));
      int[] arrayOfInt3 = { this.HEIGHT_BASIS, this.WIDTH_BASIS };
      this.isDrawArea = ((boolean[][])Array.newInstance(Boolean.TYPE, arrayOfInt3));
      this.drawCross = new boolean[2 * this.HEIGHT_BASIS_CROSS];
      this.mTopmostOfMatrix = 0;
      this.mBottommostOfMatrix = (-1 + this.HEIGHT_BASIS);
      this.mCenterOfVerticalOfMatrix = (this.HEIGHT_BASIS / 2);
      this.mLeftmostOfMatrix = 0;
      this.mRightmostOfMatrix = (-1 + this.WIDTH_BASIS);
      this.mCenterOfHorizontalOfMatrix = (this.WIDTH_BASIS / 2);
      this.KEY_TIMER_EXPIRED = 1;
      this.MILLIS_IN_SEC = 1000;
      this.KEY_TIMEOUT = 2;
      return;
      FtUtil.log_d(this.CLASS_NAME, "setTSP", "Read> ModuleDevice");
      ModuleDevice.instance(this).startTSPTest("module_on_master");
      this.WIDTH_BASIS = Integer.valueOf(ModuleDevice.instance(this).startTSPTest("get_x_num")).intValue();
      break;
      label366:
      FtUtil.log_d(this.CLASS_NAME, "setTSP", "Read> ModuleDevice");
      ModuleDevice.instance(this).startTSPTest("module_on_master");
    }
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    FtUtil.log_d(this.CLASS_NAME, "onActivityResult", "resultCode = " + paramInt1);
    if (paramInt1 == 10)
    {
      FtUtil.log_d(this.CLASS_NAME, "onActivityResult", "resultCode = " + paramInt2);
      if (paramInt2 == 0)
      {
        setResult(0);
        finish();
      }
    }
    for (;;)
    {
      FactoryTestManager.notifyDataSetChanged();
      return;
      if (paramInt1 == 0)
      {
        FtUtil.log_d(this.CLASS_NAME, "onActivityResult", "resultCode = " + paramInt2);
        if (paramInt2 == 0)
        {
          setResult(0);
          finish();
        }
      }
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    FtUtil.setRemoveSystemUI(getWindow(), true);
    try
    {
      FtUtil.log_i(this.CLASS_NAME, "onCreate", "TEST_TSP_SELF = " + getIntent().getBooleanExtra("TEST_TSP_SELF", false));
      if (!getIntent().getBooleanExtra("TEST_TSP_SELF", true))
      {
        this.successTest = false;
        this.needFailPopupDispaly = true;
      }
      setTSP();
      decideRemote();
      FtUtil.log_i(this.CLASS_NAME, "onCreate", "TouchTest is created");
      setContentView(new MyView(this));
      fillUpMatrix();
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        FtUtil.log_i(this.CLASS_NAME, "onCreate", "Exception");
      }
    }
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 24)
    {
      FtUtil.log_e(this.CLASS_NAME, "onKeyDown", "This is Vol up_key");
      if ((!isPass()) || (!isPassCross()))
      {
        setResult(0);
        finish();
      }
    }
    for (;;)
    {
      return super.onKeyDown(paramInt, paramKeyEvent);
      if (paramInt == 4)
      {
        FtUtil.log_e(this.CLASS_NAME, "onKeyDown", "This is back_key");
        if (!this.mIsPressedBackkey)
        {
          this.mCurrentTime = Calendar.getInstance().getTimeInMillis();
          this.mIsPressedBackkey = true;
          startTimer();
        }
        else
        {
          this.mIsPressedBackkey = false;
          Calendar localCalendar = Calendar.getInstance();
          FtUtil.log_e(this.CLASS_NAME, "onKeyDown", "KEYCODE_BACK => rightNow.getTimeInMillis() = " + localCalendar.getTimeInMillis() + "mCurrentTime = " + this.mCurrentTime);
          if ((localCalendar.getTimeInMillis() <= this.mCurrentTime + this.KEY_TIMEOUT * this.MILLIS_IN_SEC) && (!isPass()))
          {
            setResult(0);
            finish();
          }
        }
      }
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    FtUtil.log_d(this.CLASS_NAME, "onResume", null);
    if ((!this.successTest) && (this.needFailPopupDispaly == true))
    {
      startActivityForResult(new Intent(this, UITspFail.class), 10);
      this.needFailPopupDispaly = false;
    }
  }
  
  protected void startTimer()
  {
    this.mTimerHandler.sendEmptyMessageDelayed(this.KEY_TIMER_EXPIRED, this.KEY_TIMEOUT * this.MILLIS_IN_SEC);
  }
  
  public class MyView
    extends View
  {
    private float col_height = 0.0F;
    private float col_width = 0.0F;
    private boolean isTouchDown;
    private Paint mClickPaint;
    private Paint mEmptyPaint;
    private float mHeightCross = 0.0F;
    private Bitmap mLineBitmap;
    private Canvas mLineCanvas;
    private Paint mLinePaint;
    private Bitmap mMatrixBitmap;
    private Canvas mMatrixCanvas;
    private Paint mNonClickPaint;
    private float mPreTouchedX = 0.0F;
    private float mPreTouchedY = 0.0F;
    private int mScreenHeight;
    private int mScreenWidth;
    private float mTouchedX = 0.0F;
    private float mTouchedY = 0.0F;
    private float mWidthCross = 0.0F;
    
    public MyView(Context paramContext)
    {
      super();
      setKeepScreenOn(true);
      Display localDisplay = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
      Point localPoint = new Point();
      localDisplay.getRealSize(localPoint);
      this.mScreenWidth = localPoint.x;
      this.mScreenHeight = localPoint.y;
      FtUtil.log_i(UITspPatternStyleX.this.CLASS_NAME, "MyView", "Screen size: " + this.mScreenWidth + " x " + this.mScreenHeight);
      this.mMatrixBitmap = Bitmap.createBitmap(this.mScreenWidth, this.mScreenHeight, Bitmap.Config.ARGB_8888);
      this.mMatrixCanvas = new Canvas(this.mMatrixBitmap);
      this.mMatrixCanvas.drawColor(-1);
      this.mLineBitmap = Bitmap.createBitmap(this.mScreenWidth, this.mScreenHeight, Bitmap.Config.ARGB_8888);
      this.mLineCanvas = new Canvas(this.mLineBitmap);
      this.col_height = (this.mScreenHeight / UITspPatternStyleX.this.HEIGHT_BASIS);
      this.col_width = (this.mScreenWidth / UITspPatternStyleX.this.WIDTH_BASIS);
      this.mWidthCross = ((this.mScreenWidth - 1.0F * this.col_width) / (4 + (-1 + UITspPatternStyleX.this.HEIGHT_BASIS_CROSS)));
      this.mHeightCross = (this.col_height / 2.0F);
      setPaint();
      initRect();
      this.isTouchDown = false;
    }
    
    private void checkCrossRectRegion(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, Paint paramPaint)
    {
      if ((paramInt2 > 0) && (paramInt2 < -1 + UITspPatternStyleX.this.HEIGHT_BASIS))
      {
        int i = (int)((paramFloat2 - this.col_height) / this.mHeightCross);
        int j = (int)(this.mWidthCross * (i + 2));
        int k = (int)(this.col_height + this.mHeightCross * i);
        int m = (int)(this.mScreenWidth - 1.0F * this.col_width - this.mWidthCross * (i + 2));
        if ((paramFloat1 > j) && (paramFloat1 < j + this.col_width))
        {
          this.mMatrixCanvas.drawRect(j + 1, k + 1, (int)(j + this.col_width), (int)(k + this.col_height / 2.0F), paramPaint);
          invalidate(new Rect(j - 1, k - 1, (int)(1.0F + (j + this.col_width)), (int)(1.0F + (k + this.col_height))));
          if (i >= 0) {
            UITspPatternStyleX.this.drawCross[i] = 1;
          }
        }
        if ((paramFloat1 > m) && (paramFloat1 < m + this.col_width))
        {
          this.mMatrixCanvas.drawRect(m + 1, k + 1, (int)(m + this.col_width), (int)(k + this.col_height / 2.0F), paramPaint);
          invalidate(new Rect(m - 1, k - 1, (int)(1.0F + (m + this.col_width)), (int)(1.0F + (k + this.col_height))));
          if (i >= 0) {
            UITspPatternStyleX.this.drawCross[(-1 + 2 * UITspPatternStyleX.this.HEIGHT_BASIS_CROSS - i)] = 1;
          }
        }
      }
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
      invalidate(new Rect(-6 + (int)paramFloat1, -6 + (int)paramFloat2, 6 + (int)paramFloat1, 6 + (int)paramFloat2));
    }
    
    private void drawRect(float paramFloat1, float paramFloat2, Paint paramPaint)
    {
      float f1 = this.mScreenHeight / UITspPatternStyleX.this.HEIGHT_BASIS;
      float f2 = this.mScreenWidth / UITspPatternStyleX.this.WIDTH_BASIS;
      int i = (int)(paramFloat1 / f2);
      int j = (int)(paramFloat2 / f1);
      float f3 = f2 * i;
      float f4 = f1 * j;
      if ((j > -1 + UITspPatternStyleX.this.HEIGHT_BASIS) || (i > -1 + UITspPatternStyleX.this.WIDTH_BASIS)) {
        FtUtil.log_e(UITspPatternStyleX.this.CLASS_NAME, "drawRect", "You are out of bounds!");
      }
      do
      {
        return;
        if (UITspPatternStyleX.this.draw[j][i] == 0)
        {
          UITspPatternStyleX.this.draw[j][i] = 1;
          if ((UITspPatternStyleX.this.draw[j][i] == 1) && (UITspPatternStyleX.this.isDrawArea[j][i] != 0))
          {
            this.mMatrixCanvas.drawRect(1 + (int)f3, 1 + (int)f4, (int)(f3 + f2), (int)(f4 + f1), paramPaint);
            invalidate(new Rect((int)(f3 - 1.0F), (int)(f4 - 1.0F), (int)(1.0F + (f3 + f2)), (int)(1.0F + (f4 + f1))));
          }
        }
        checkCrossRectRegion(paramFloat1, paramFloat2, i, j, paramPaint);
      } while ((!UITspPatternStyleX.this.isPass()) || (!UITspPatternStyleX.this.isPassCross()));
      if (UITspPatternStyleX.this.successTest)
      {
        UITspPatternStyleX.access$1102(UITspPatternStyleX.this, false);
        if (UITspPatternStyleX.this.remoteCall == true) {
          UITspPatternStyleX.this.setResult(-1);
        }
        UITspPatternStyleX.this.setResult(-1);
        UITspPatternStyleX.this.finish();
        return;
      }
      UITspPatternStyleX.this.setResult(0);
      UITspPatternStyleX.this.finish();
    }
    
    private void initRect()
    {
      Paint localPaint1 = new Paint();
      Paint localPaint2 = new Paint();
      localPaint1.setColor(-16777216);
      localPaint2.setColor(-16777216);
      localPaint2.setStyle(Paint.Style.STROKE);
      for (int i = 0; i < UITspPatternStyleX.this.HEIGHT_BASIS; i++)
      {
        int i3 = (int)(this.col_height * i);
        for (int i4 = 0;; i4++)
        {
          int i5 = UITspPatternStyleX.this.WIDTH_BASIS;
          if (i4 >= i5) {
            break;
          }
          int i6 = (int)(this.col_width * i4);
          this.mMatrixCanvas.drawLine(i6, i3, this.mScreenWidth, i3, localPaint1);
          this.mMatrixCanvas.drawLine(i6, i3, i6, this.mScreenHeight, localPaint1);
          UITspPatternStyleX.this.draw[i][i4] = 0;
          UITspPatternStyleX.this.click[i][i4] = 0;
        }
      }
      this.mMatrixCanvas.drawRect(1.0F + this.col_width, 1.0F + this.col_height, this.col_width * (-1 + UITspPatternStyleX.this.WIDTH_BASIS) - 1.0F, this.col_height * (-1 + UITspPatternStyleX.this.HEIGHT_BASIS) - 1.0F, this.mEmptyPaint);
      for (int j = 0; j < UITspPatternStyleX.this.HEIGHT_BASIS_CROSS; j++)
      {
        int i1 = (int)(this.mWidthCross * (j + 2));
        int i2 = (int)(this.col_height + this.mHeightCross * j);
        this.mMatrixCanvas.drawRect(i1, i2, i1 + this.col_width, i2 + this.col_height / 2.0F, localPaint2);
        UITspPatternStyleX.this.drawCross[j] = 0;
      }
      for (int k = 0; k < UITspPatternStyleX.this.HEIGHT_BASIS_CROSS; k++)
      {
        int m = (int)(this.mWidthCross * (k + 2));
        int n = (int)(this.mScreenHeight - (this.col_height + this.mHeightCross * (k + 1)));
        this.mMatrixCanvas.drawRect(m, n, m + this.col_width, n + this.col_height / 2.0F, localPaint2);
        UITspPatternStyleX.this.drawCross[(k + UITspPatternStyleX.this.HEIGHT_BASIS_CROSS)] = 0;
      }
    }
    
    private void setPaint()
    {
      this.mLinePaint = new Paint();
      this.mLinePaint.setAntiAlias(true);
      this.mLinePaint.setDither(true);
      this.mLinePaint.setColor(-16777216);
      this.mLinePaint.setStyle(Paint.Style.STROKE);
      this.mLinePaint.setStrokeJoin(Paint.Join.ROUND);
      this.mLinePaint.setStrokeCap(Paint.Cap.SQUARE);
      this.mLinePaint.setStrokeWidth(5.0F);
      DashPathEffect localDashPathEffect = new DashPathEffect(new float[] { 5.0F, 5.0F }, 1.0F);
      this.mLinePaint.setPathEffect(localDashPathEffect);
      this.mLinePaint.setColor(-16777216);
      this.mClickPaint = new Paint();
      this.mClickPaint.setAntiAlias(false);
      this.mClickPaint.setStyle(Paint.Style.FILL);
      this.mClickPaint.setColor(-16711936);
      this.mNonClickPaint = new Paint();
      this.mNonClickPaint.setAntiAlias(false);
      this.mNonClickPaint.setColor(-1);
      this.mEmptyPaint = new Paint();
      this.mEmptyPaint.setAntiAlias(false);
      this.mEmptyPaint.setColor(-1);
    }
    
    protected void onDraw(Canvas paramCanvas)
    {
      paramCanvas.drawBitmap(this.mMatrixBitmap, 0.0F, 0.0F, null);
      paramCanvas.drawBitmap(this.mLineBitmap, 0.0F, 0.0F, null);
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      int i = paramMotionEvent.getAction();
      int j = paramMotionEvent.getPointerCount();
      FtUtil.log_e(UITspPatternStyleX.this.CLASS_NAME, "onTouchEvent", "Touch is working !");
      if ((!UITspPatternStyleX.this.dialog_showing) && (j > 1))
      {
        UITspPatternStyleX.access$402(UITspPatternStyleX.this, true);
        UITspPatternStyleX.this.startActivityForResult(new Intent(UITspPatternStyleX.this, UITouchTestGhost.class), 0);
      }
      switch (i)
      {
      }
      do
      {
        boolean bool;
        int k;
        do
        {
          return true;
          this.mTouchedX = paramMotionEvent.getX();
          this.mTouchedY = paramMotionEvent.getY();
          drawRect(this.mTouchedX, this.mTouchedY, this.mClickPaint);
          this.isTouchDown = true;
          return true;
          bool = this.isTouchDown;
          k = 0;
        } while (!bool);
        while (k < paramMotionEvent.getHistorySize())
        {
          this.mPreTouchedX = this.mTouchedX;
          this.mPreTouchedY = this.mTouchedY;
          this.mTouchedX = paramMotionEvent.getHistoricalX(k);
          this.mTouchedY = paramMotionEvent.getHistoricalY(k);
          drawRect(this.mTouchedX, this.mTouchedY, this.mClickPaint);
          drawLine(this.mPreTouchedX, this.mPreTouchedY, this.mTouchedX, this.mTouchedY);
          k++;
        }
        this.mPreTouchedX = this.mTouchedX;
        this.mPreTouchedY = this.mTouchedY;
        this.mTouchedX = paramMotionEvent.getX();
        this.mTouchedY = paramMotionEvent.getY();
        drawRect(this.mTouchedX, this.mTouchedY, this.mClickPaint);
        drawLine(this.mPreTouchedX, this.mPreTouchedY, this.mTouchedX, this.mTouchedY);
        this.isTouchDown = true;
        return true;
      } while (!this.isTouchDown);
      this.mPreTouchedX = this.mTouchedX;
      this.mPreTouchedY = this.mTouchedY;
      this.mTouchedX = paramMotionEvent.getX();
      this.mTouchedY = paramMotionEvent.getY();
      if ((this.mPreTouchedX == this.mTouchedX) && (this.mPreTouchedY == this.mTouchedY)) {
        drawPoint(this.mTouchedX, this.mTouchedY);
      }
      this.isTouchDown = false;
      return true;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UITspPatternStyleX
 * JD-Core Version:    0.7.1
 */