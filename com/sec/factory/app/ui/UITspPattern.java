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

public class UITspPattern
  extends UIBase
{
  private int HEIGHT_BASIS = 19;
  protected int KEY_TIMEOUT;
  protected int KEY_TIMER_EXPIRED;
  protected int MILLIS_IN_SEC;
  private final int REQUEST_GHOST_TEST = 0;
  private int WIDTH_BASIS = 11;
  private boolean[][] click;
  private boolean dialog_showing = false;
  private boolean[][] draw;
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
      if (paramAnonymousMessage.what == UITspPattern.this.KEY_TIMER_EXPIRED)
      {
        UITspPattern.access$002(UITspPattern.this, false);
        FtUtil.log_e(UITspPattern.this.CLASS_NAME, "mTimerHandler.handleMessage", null);
      }
    }
  };
  private int mTopmostOfMatrix;
  private boolean needFailPopupDispaly = false;
  private boolean remoteCall = false;
  private boolean successTest = true;
  
  public UITspPattern()
  {
    super("UITspPattern", 10);
  }
  
  private void ResultLog()
  {
    for (int i = 0; i < this.HEIGHT_BASIS; i++) {
      for (int j = 0; j < this.WIDTH_BASIS; j++) {
        if (this.isDrawArea[i][j] == 1) {
          FtUtil.log_i(this.CLASS_NAME, "ResultLog", "isDrawArea[" + i + "][" + j + "] = " + this.isDrawArea[i][j]);
        }
      }
    }
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
    return (paramInt1 == this.mTopmostOfMatrix) || (paramInt1 == this.mBottommostOfMatrix) || (paramInt1 == this.mCenterOfVerticalOfMatrix) || (paramInt2 == this.mLeftmostOfMatrix) || (paramInt2 == this.mRightmostOfMatrix) || (paramInt2 == this.mCenterOfHorizontalOfMatrix);
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
          break label333;
        }
        FtUtil.log_d(this.CLASS_NAME, "setTSP", "Read> ModuleTouchScreen");
        ModuleTouchScreen.instance(this).getTSPResult(ModuleTouchScreen.TSP_ID__POWER_ON);
      }
    }
    for (this.HEIGHT_BASIS = Integer.valueOf(ModuleTouchScreen.instance(this).getTSPChannelCountY()).intValue();; this.HEIGHT_BASIS = Integer.valueOf(ModuleDevice.instance(this).startTSPTest("get_y_num")).intValue())
    {
      int[] arrayOfInt1 = { this.HEIGHT_BASIS, this.WIDTH_BASIS };
      this.click = ((boolean[][])Array.newInstance(Boolean.TYPE, arrayOfInt1));
      int[] arrayOfInt2 = { this.HEIGHT_BASIS, this.WIDTH_BASIS };
      this.draw = ((boolean[][])Array.newInstance(Boolean.TYPE, arrayOfInt2));
      int[] arrayOfInt3 = { this.HEIGHT_BASIS, this.WIDTH_BASIS };
      this.isDrawArea = ((boolean[][])Array.newInstance(Boolean.TYPE, arrayOfInt3));
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
      label333:
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
    if (paramInt == 24) {
      if (!isPass())
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
  
  protected void onPause()
  {
    super.onPause();
    ResultLog();
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
    private boolean isTouchDown;
    private Paint mClickPaint;
    private Paint mEmptyPaint;
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
    
    public MyView(Context paramContext)
    {
      super();
      setKeepScreenOn(true);
      Display localDisplay = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
      Point localPoint = new Point();
      localDisplay.getRealSize(localPoint);
      this.mScreenWidth = localPoint.x;
      this.mScreenHeight = localPoint.y;
      FtUtil.log_i(UITspPattern.this.CLASS_NAME, "MyView", "Screen size: " + this.mScreenWidth + " x " + this.mScreenHeight);
      this.mMatrixBitmap = Bitmap.createBitmap(this.mScreenWidth, this.mScreenHeight, Bitmap.Config.ARGB_8888);
      this.mMatrixCanvas = new Canvas(this.mMatrixBitmap);
      this.mMatrixCanvas.drawColor(-1);
      this.mLineBitmap = Bitmap.createBitmap(this.mScreenWidth, this.mScreenHeight, Bitmap.Config.ARGB_8888);
      this.mLineCanvas = new Canvas(this.mLineBitmap);
      setPaint();
      initRect();
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
      invalidate(new Rect(-6 + (int)paramFloat1, -6 + (int)paramFloat2, 6 + (int)paramFloat1, 6 + (int)paramFloat2));
    }
    
    private void drawRect(float paramFloat1, float paramFloat2, Paint paramPaint)
    {
      float f1 = this.mScreenHeight / UITspPattern.this.HEIGHT_BASIS;
      float f2 = this.mScreenWidth / UITspPattern.this.WIDTH_BASIS;
      int i = (int)(paramFloat1 / f2);
      int j = (int)(paramFloat2 / f1);
      float f3 = f2 * i;
      float f4 = f1 * j;
      if ((j < 0) || (i < 0) || (j > -1 + UITspPattern.this.HEIGHT_BASIS) || (i > -1 + UITspPattern.this.WIDTH_BASIS))
      {
        FtUtil.log_e(UITspPattern.this.CLASS_NAME, "drawRect", "You are out of bounds!");
        return;
      }
      if (UITspPattern.this.draw[j][i] == 0)
      {
        UITspPattern.this.draw[j][i] = 1;
        FtUtil.log_d(UITspPattern.this.CLASS_NAME, "drawRect", "draw[" + j + "][" + i + "] = true");
        if ((UITspPattern.this.draw[j][i] != 1) || (UITspPattern.this.isDrawArea[j][i] == 0)) {
          break label360;
        }
        this.mMatrixCanvas.drawRect(1 + (int)f3, 1 + (int)f4, (int)(f3 + f2), (int)(f4 + f1), paramPaint);
      }
      for (;;)
      {
        invalidate(new Rect((int)(f3 - 1.0F), (int)(f4 - 1.0F), (int)(1.0F + (f3 + f2)), (int)(1.0F + (f4 + f1))));
        if (!UITspPattern.this.isPass()) {
          break;
        }
        if (!UITspPattern.this.successTest) {
          break label400;
        }
        UITspPattern.access$802(UITspPattern.this, false);
        if (UITspPattern.this.remoteCall == true) {
          UITspPattern.this.setResult(-1);
        }
        UITspPattern.this.setResult(-1);
        UITspPattern.this.finish();
        return;
        label360:
        this.mMatrixCanvas.drawRect(1 + (int)f3, 1 + (int)f4, (int)(f3 + f2), (int)(f4 + f1), this.mNonClickPaint);
      }
      label400:
      UITspPattern.this.setResult(0);
      UITspPattern.this.finish();
    }
    
    private void initRect()
    {
      float f1 = this.mScreenHeight / UITspPattern.this.HEIGHT_BASIS;
      float f2 = this.mScreenWidth / UITspPattern.this.WIDTH_BASIS;
      Paint localPaint = new Paint();
      localPaint.setColor(-16777216);
      for (int i = 0;; i++)
      {
        int j = UITspPattern.this.HEIGHT_BASIS;
        if (i >= j) {
          break;
        }
        int k = (int)(f1 * i);
        for (int m = 0;; m++)
        {
          int n = UITspPattern.this.WIDTH_BASIS;
          if (m >= n) {
            break;
          }
          int i1 = (int)(f2 * m);
          this.mMatrixCanvas.drawLine(i1, k, this.mScreenWidth, k, localPaint);
          this.mMatrixCanvas.drawLine(i1, k, i1, this.mScreenHeight, localPaint);
          UITspPattern.this.draw[i][m] = 0;
          UITspPattern.this.click[i][m] = 0;
        }
      }
      this.mMatrixCanvas.drawRect(f2 + 1.0F, f1 + 1.0F, f2 * (UITspPattern.this.WIDTH_BASIS / 2) - 1.0F, f1 * (UITspPattern.this.HEIGHT_BASIS / 2) - 1.0F, this.mEmptyPaint);
      this.mMatrixCanvas.drawRect(1.0F + f2 * (1 + UITspPattern.this.WIDTH_BASIS / 2), f1 + 1.0F, f2 * (-1 + UITspPattern.this.WIDTH_BASIS) - 1.0F, f1 * (UITspPattern.this.HEIGHT_BASIS / 2) - 1.0F, this.mEmptyPaint);
      this.mMatrixCanvas.drawRect(f2 + 1.0F, 1.0F + f1 * (1 + UITspPattern.this.HEIGHT_BASIS / 2), f2 * (UITspPattern.this.WIDTH_BASIS / 2) - 1.0F, f1 * (-1 + UITspPattern.this.HEIGHT_BASIS) - 1.0F, this.mEmptyPaint);
      this.mMatrixCanvas.drawRect(1.0F + f2 * (1 + UITspPattern.this.WIDTH_BASIS / 2), 1.0F + f1 * (1 + UITspPattern.this.HEIGHT_BASIS / 2), f2 * (-1 + UITspPattern.this.WIDTH_BASIS) - 1.0F, f1 * (-1 + UITspPattern.this.HEIGHT_BASIS) - 1.0F, this.mEmptyPaint);
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
      FtUtil.log_e(UITspPattern.this.CLASS_NAME, "onTouchEvent", "Touch is working !");
      if ((!UITspPattern.this.dialog_showing) && (j > 1))
      {
        UITspPattern.access$102(UITspPattern.this, true);
        UITspPattern.this.startActivityForResult(new Intent(UITspPattern.this, UITouchTestGhost.class), 0);
      }
      if (paramMotionEvent.getToolType(0) == 2) {}
      do
      {
        boolean bool;
        int k;
        do
        {
          return true;
          switch (i)
          {
          default: 
            return true;
          case 0: 
            this.mTouchedX = paramMotionEvent.getX();
            this.mTouchedY = paramMotionEvent.getY();
            FtUtil.log_d(UITspPattern.this.CLASS_NAME, "onTouchEvent", "ACTION_DOWN mTouchedX :" + this.mTouchedX);
            FtUtil.log_d(UITspPattern.this.CLASS_NAME, "onTouchEvent", "ACTION_DOWN mTouchedY :" + this.mTouchedY);
            drawRect(this.mTouchedX, this.mTouchedY, this.mClickPaint);
            this.isTouchDown = true;
            return true;
          case 2: 
            bool = this.isTouchDown;
            k = 0;
          }
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
        FtUtil.log_d(UITspPattern.this.CLASS_NAME, "onTouchEvent", "ACTION_MOVE mTouchedX :" + this.mTouchedX);
        FtUtil.log_d(UITspPattern.this.CLASS_NAME, "onTouchEvent", "ACTION_MOVE mTouchedY :" + this.mTouchedY);
        drawRect(this.mTouchedX, this.mTouchedY, this.mClickPaint);
        drawLine(this.mPreTouchedX, this.mPreTouchedY, this.mTouchedX, this.mTouchedY);
        this.isTouchDown = true;
        return true;
      } while (!this.isTouchDown);
      this.mPreTouchedX = this.mTouchedX;
      this.mPreTouchedY = this.mTouchedY;
      this.mTouchedX = paramMotionEvent.getX();
      this.mTouchedY = paramMotionEvent.getY();
      FtUtil.log_d(UITspPattern.this.CLASS_NAME, "onTouchEvent", "ACTION_UP mTouchedX :" + this.mTouchedX);
      FtUtil.log_d(UITspPattern.this.CLASS_NAME, "onTouchEvent", "ACTION_UP mTouchedY :" + this.mTouchedY);
      if ((this.mPreTouchedX == this.mTouchedX) && (this.mPreTouchedY == this.mTouchedY)) {
        drawPoint(this.mTouchedX, this.mTouchedY);
      }
      this.isTouchDown = false;
      return true;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UITspPattern
 * JD-Core Version:    0.7.1
 */