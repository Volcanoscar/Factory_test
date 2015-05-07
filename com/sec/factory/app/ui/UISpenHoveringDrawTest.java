package com.sec.factory.app.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemProperties;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnHoverListener;
import android.view.WindowManager;
import com.sec.factory.support.FtUtil;

public class UISpenHoveringDrawTest
  extends UIBase
{
  protected int KEY_TIMEOUT = 2;
  protected int KEY_TIMER_EXPIRED = 1;
  protected int MILLIS_IN_SEC = 1000;
  private int WIDTH_BASIS = 21;
  private MyRect[] areaRect;
  private boolean isTouch = false;
  private boolean isTouchDown;
  private float mCellBlockHeight = 0.0F;
  private float mCellBlockWidth = 0.0F;
  private long mCurrentTime = 0L;
  private View mHoverView;
  private boolean mIsFail = false;
  private boolean mIsPass = false;
  private boolean mIsPressedBackkey = false;
  private boolean mIsTestStart = false;
  private boolean mIsWacom = true;
  private int nRemainCount = 1;
  
  public UISpenHoveringDrawTest()
  {
    super("UISpenHoveringDrawTest", 27);
  }
  
  private boolean isDeadZoneModels()
  {
    String str = SystemProperties.get("ro.product.model", "Unknown");
    boolean bool1;
    if ((!str.contains("N7100")) && (!str.contains("N7105")) && (!str.contains("N7102")) && (!str.contains("N7108")) && (!str.contains("E250K")) && (!str.contains("E250S")) && (!str.contains("E250L")) && (!str.contains("I317")))
    {
      boolean bool2 = str.contains("T889");
      bool1 = false;
      if (!bool2) {}
    }
    else
    {
      bool1 = true;
    }
    return bool1;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    FtUtil.setRemoveSystemUI(getWindow(), true);
    this.mHoverView = new HoverView(this);
    setContentView(this.mHoverView);
    this.mHoverView.setOnHoverListener((View.OnHoverListener)this.mHoverView);
    Log.i("UISpenHoveringDrawTest", "onCreate");
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 25) || (paramInt == 24))
    {
      Log.e("UISpenHoveringDrawTest", "This is back_key KeyEvent.KEYCODE_VOLUME_DOWN or KEYCODE_VOLUME_UP");
      finish();
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  protected void onPause()
  {
    super.onPause();
  }
  
  protected void onResume()
  {
    super.onResume();
  }
  
  public class HoverView
    extends View
    implements View.OnHoverListener
  {
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
    private String mResultText;
    private int mScreenHeight;
    private int mScreenWidth;
    private float mTouchedX = 0.0F;
    private float mTouchedY = 0.0F;
    
    public HoverView(Context paramContext)
    {
      super();
      setKeepScreenOn(true);
      Display localDisplay = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
      Point localPoint = new Point();
      localDisplay.getRealSize(localPoint);
      this.mScreenWidth = localPoint.x;
      this.mScreenHeight = localPoint.y;
      this.mMatrixBitmap = Bitmap.createBitmap(this.mScreenWidth, this.mScreenHeight, Bitmap.Config.ARGB_8888);
      this.mMatrixCanvas = new Canvas(this.mMatrixBitmap);
      this.mMatrixCanvas.drawColor(-1);
      this.mLineBitmap = Bitmap.createBitmap(this.mScreenWidth, this.mScreenHeight, Bitmap.Config.ARGB_8888);
      this.mLineCanvas = new Canvas(this.mLineBitmap);
      setPaint();
      if (UISpenHoveringDrawTest.this.isDeadZoneModels() == true)
      {
        this.mScreenHeight = (-15 + this.mScreenHeight);
        drawDeadzone();
      }
      Resources localResources = getResources();
      float f = TypedValue.applyDimension(5, 6.0F, localResources.getDisplayMetrics());
      UISpenHoveringDrawTest.access$102(UISpenHoveringDrawTest.this, 1 + (int)(this.mScreenHeight / f));
      UISpenHoveringDrawTest.access$202(UISpenHoveringDrawTest.this, TypedValue.applyDimension(5, 6.0F, localResources.getDisplayMetrics()));
      UISpenHoveringDrawTest.access$302(UISpenHoveringDrawTest.this, TypedValue.applyDimension(5, 2.0F, localResources.getDisplayMetrics()));
      UISpenHoveringDrawTest.access$102(UISpenHoveringDrawTest.this, 1 + (int)(this.mScreenHeight / UISpenHoveringDrawTest.this.mCellBlockHeight));
      UISpenHoveringDrawTest.access$402(UISpenHoveringDrawTest.this, -1 + UISpenHoveringDrawTest.this.WIDTH_BASIS);
      UISpenHoveringDrawTest.access$502(UISpenHoveringDrawTest.this, new UISpenHoveringDrawTest.MyRect[-1 + UISpenHoveringDrawTest.this.WIDTH_BASIS]);
      initRect();
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
          this.mTouchedX = paramMotionEvent.getX();
          this.mTouchedY = paramMotionEvent.getY();
          drawRect(this.mTouchedX, this.mTouchedY, this.mClickPaint);
          UISpenHoveringDrawTest.access$1002(UISpenHoveringDrawTest.this, true);
          return;
        } while (!UISpenHoveringDrawTest.this.isTouchDown);
        for (int i = 0; i < paramMotionEvent.getHistorySize(); i++)
        {
          this.mPreTouchedX = this.mTouchedX;
          this.mPreTouchedY = this.mTouchedY;
          this.mTouchedX = paramMotionEvent.getHistoricalX(i);
          this.mTouchedY = paramMotionEvent.getHistoricalY(i);
          drawRect(this.mTouchedX, this.mTouchedY, this.mClickPaint);
          drawLine(this.mPreTouchedX, this.mPreTouchedY, this.mTouchedX, this.mTouchedY);
        }
        this.mPreTouchedX = this.mTouchedX;
        this.mPreTouchedY = this.mTouchedY;
        this.mTouchedX = paramMotionEvent.getX();
        this.mTouchedY = paramMotionEvent.getY();
        drawRect(this.mTouchedX, this.mTouchedY, this.mClickPaint);
        drawLine(this.mPreTouchedX, this.mPreTouchedY, this.mTouchedX, this.mTouchedY);
        UISpenHoveringDrawTest.access$1002(UISpenHoveringDrawTest.this, true);
        return;
      } while (!UISpenHoveringDrawTest.this.isTouchDown);
      this.mPreTouchedX = this.mTouchedX;
      this.mPreTouchedY = this.mTouchedY;
      this.mTouchedX = paramMotionEvent.getX();
      this.mTouchedY = paramMotionEvent.getY();
      if ((this.mPreTouchedX == this.mTouchedX) && (this.mPreTouchedY == this.mTouchedY)) {
        drawPoint(this.mTouchedX, this.mTouchedY);
      }
      UISpenHoveringDrawTest.access$1002(UISpenHoveringDrawTest.this, false);
    }
    
    private void drawDeadzone()
    {
      Paint localPaint = new Paint();
      localPaint.setColor(-16777216);
      localPaint.setStyle(Paint.Style.FILL);
      this.mMatrixCanvas.drawRect(0.0F, this.mScreenHeight, this.mScreenWidth, 15 + this.mScreenHeight, localPaint);
    }
    
    private void drawHoverText(boolean paramBoolean)
    {
      Paint localPaint = new Paint();
      localPaint.setColor(-1);
      localPaint.setStyle(Paint.Style.FILL);
      localPaint.setTextAlign(Paint.Align.CENTER);
      this.mMatrixCanvas.drawRect(2 * (this.mScreenWidth / 8) + this.mScreenWidth / 8 / 2, 10.0F, 8 * (this.mScreenWidth / 8), 2 * (this.mScreenHeight / 8), localPaint);
      if (paramBoolean == true)
      {
        this.mResultText = getResources().getString(2131165365);
        localPaint.setColor(-16777216);
        localPaint.setAntiAlias(true);
        localPaint.setTextSize(80.0F);
        this.mMatrixCanvas.drawText(this.mResultText, 5 * (this.mScreenWidth / 8), 1 * (this.mScreenHeight / 8), localPaint);
      }
      invalidate();
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
      float f1 = this.mScreenHeight / (1 + UISpenHoveringDrawTest.this.WIDTH_BASIS);
      float f2 = this.mScreenWidth / (1 + UISpenHoveringDrawTest.this.WIDTH_BASIS);
      int i = 0;
      int j = -1 + UISpenHoveringDrawTest.this.WIDTH_BASIS;
      int k = 0;
      if (i < j)
      {
        if ((UISpenHoveringDrawTest.this.areaRect[i].contains((int)paramFloat1, (int)paramFloat2) != true) && (UISpenHoveringDrawTest.this.areaRect[i].contains(1 + (int)paramFloat1, 1 + (int)paramFloat2) != true) && (UISpenHoveringDrawTest.this.areaRect[i].contains(1 + (int)paramFloat1, -1 + (int)paramFloat2) != true) && (UISpenHoveringDrawTest.this.areaRect[i].contains(-1 + (int)paramFloat1, 1 + (int)paramFloat2) != true) && (UISpenHoveringDrawTest.this.areaRect[i].contains(-1 + (int)paramFloat1, -1 + (int)paramFloat2) != true)) {
          break label639;
        }
        k = 1;
        if (!UISpenHoveringDrawTest.this.areaRect[i].click)
        {
          Path localPath = new Path();
          localPath.moveTo(UISpenHoveringDrawTest.this.areaRect[i].x1, UISpenHoveringDrawTest.this.areaRect[i].y1);
          localPath.lineTo(UISpenHoveringDrawTest.this.areaRect[i].x2, 1 + UISpenHoveringDrawTest.this.areaRect[i].y1);
          localPath.lineTo(UISpenHoveringDrawTest.this.areaRect[i].x2, UISpenHoveringDrawTest.this.areaRect[i].y2);
          localPath.lineTo(1 + UISpenHoveringDrawTest.this.areaRect[i].x1, UISpenHoveringDrawTest.this.areaRect[i].y2);
          localPath.lineTo(1 + UISpenHoveringDrawTest.this.areaRect[i].x1, 1 + UISpenHoveringDrawTest.this.areaRect[i].y1);
          Paint localPaint = new Paint();
          localPaint.setColor(-16711936);
          this.mMatrixCanvas.drawPath(localPath, localPaint);
          Log.i("UISpenHoveringDrawTest", "PtIsInside !!!");
          UISpenHoveringDrawTest.this.areaRect[i].click = true;
          UISpenHoveringDrawTest.access$410(UISpenHoveringDrawTest.this);
          if ((i == UISpenHoveringDrawTest.this.WIDTH_BASIS) && (UISpenHoveringDrawTest.this.isDeadZoneModels() == true)) {
            drawDeadzone();
          }
          invalidate(new Rect(UISpenHoveringDrawTest.this.areaRect[i].x1, UISpenHoveringDrawTest.this.areaRect[i].y1, UISpenHoveringDrawTest.this.areaRect[i].x2, UISpenHoveringDrawTest.this.areaRect[i].y2));
          if (UISpenHoveringDrawTest.this.nRemainCount == 0)
          {
            drawHoverText(false);
            drawResultText(true);
            UISpenHoveringDrawTest.this.setTestResult((byte)80);
            new Handler().postDelayed(new Runnable()
            {
              public void run()
              {
                UISpenHoveringDrawTest.HoverView.this.onFinish();
              }
            }, 500L);
          }
        }
      }
      if (k == 0)
      {
        if (((paramFloat1 >= f2) || (paramFloat2 >= f1)) && ((paramFloat1 <= this.mScreenWidth - f2) || (paramFloat2 <= this.mScreenHeight - f1))) {
          break label645;
        }
        Log.e("UISpenHoveringDrawTest", "Ignore first and last triangle");
      }
      label639:
      do
      {
        return;
        i++;
        break;
        Log.e("UISpenHoveringDrawTest", "PtOutside");
      } while (!UISpenHoveringDrawTest.this.mIsTestStart);
      label645:
      UISpenHoveringDrawTest.access$902(UISpenHoveringDrawTest.this, false);
    }
    
    private void drawResultText(boolean paramBoolean)
    {
      Paint localPaint = new Paint();
      if ((paramBoolean == true) && (!UISpenHoveringDrawTest.this.isTouch))
      {
        this.mResultText = getResources().getString(2131165274);
        localPaint.setColor(-16776961);
      }
      for (;;)
      {
        localPaint.setTextAlign(Paint.Align.CENTER);
        localPaint.setAntiAlias(true);
        localPaint.setTextSize(150.0F);
        this.mMatrixCanvas.drawText(this.mResultText, 2 * (this.mScreenWidth / 8), 6 * (this.mScreenHeight / 8), localPaint);
        invalidate();
        return;
        this.mResultText = getResources().getString(2131165275);
        localPaint.setColor(-65536);
      }
    }
    
    private void initRect()
    {
      (this.mScreenHeight / (1 + UISpenHoveringDrawTest.this.WIDTH_BASIS));
      (this.mScreenWidth / (1 + UISpenHoveringDrawTest.this.WIDTH_BASIS));
      Paint localPaint = new Paint();
      localPaint.setColor(-16777216);
      float f = (this.mScreenWidth - UISpenHoveringDrawTest.this.mCellBlockWidth) / ((this.mScreenHeight - UISpenHoveringDrawTest.this.mCellBlockHeight) / UISpenHoveringDrawTest.this.mCellBlockHeight);
      for (int i = 0; i < -1 + UISpenHoveringDrawTest.this.WIDTH_BASIS; i++)
      {
        int j = (int)(UISpenHoveringDrawTest.this.mCellBlockHeight * i);
        int k = (int)(f * i);
        UISpenHoveringDrawTest.this.areaRect[i] = new UISpenHoveringDrawTest.MyRect(UISpenHoveringDrawTest.this, null);
        UISpenHoveringDrawTest.this.areaRect[i].x1 = k;
        UISpenHoveringDrawTest.this.areaRect[i].y1 = j;
        UISpenHoveringDrawTest.this.areaRect[i].x2 = (k + (int)UISpenHoveringDrawTest.this.mCellBlockWidth);
        UISpenHoveringDrawTest.this.areaRect[i].y2 = (j + (int)UISpenHoveringDrawTest.this.mCellBlockHeight);
        UISpenHoveringDrawTest.this.areaRect[i].click = false;
        this.mMatrixCanvas.drawLine(UISpenHoveringDrawTest.this.areaRect[i].x1, UISpenHoveringDrawTest.this.areaRect[i].y1, UISpenHoveringDrawTest.this.areaRect[i].x2, UISpenHoveringDrawTest.this.areaRect[i].y1, localPaint);
        this.mMatrixCanvas.drawLine(UISpenHoveringDrawTest.this.areaRect[i].x1, UISpenHoveringDrawTest.this.areaRect[i].y1, UISpenHoveringDrawTest.this.areaRect[i].x1, UISpenHoveringDrawTest.this.areaRect[i].y2, localPaint);
        this.mMatrixCanvas.drawLine(UISpenHoveringDrawTest.this.areaRect[i].x2, UISpenHoveringDrawTest.this.areaRect[i].y1, UISpenHoveringDrawTest.this.areaRect[i].x2, UISpenHoveringDrawTest.this.areaRect[i].y2, localPaint);
        this.mMatrixCanvas.drawLine(UISpenHoveringDrawTest.this.areaRect[i].x2, UISpenHoveringDrawTest.this.areaRect[i].y2, UISpenHoveringDrawTest.this.areaRect[i].x1, UISpenHoveringDrawTest.this.areaRect[i].y2, localPaint);
        Path localPath = new Path();
        localPath.moveTo(UISpenHoveringDrawTest.this.areaRect[i].x1, UISpenHoveringDrawTest.this.areaRect[i].y1);
        localPath.lineTo(UISpenHoveringDrawTest.this.areaRect[i].x2, UISpenHoveringDrawTest.this.areaRect[i].y1);
        localPath.lineTo(UISpenHoveringDrawTest.this.areaRect[i].x2, UISpenHoveringDrawTest.this.areaRect[i].y2);
        localPath.lineTo(UISpenHoveringDrawTest.this.areaRect[i].x1, UISpenHoveringDrawTest.this.areaRect[i].y2);
        localPath.lineTo(UISpenHoveringDrawTest.this.areaRect[i].x1, UISpenHoveringDrawTest.this.areaRect[i].y1);
        UISpenHoveringDrawTest.this.areaRect[i].setPath(localPath, new Region(UISpenHoveringDrawTest.this.areaRect[i].x1, UISpenHoveringDrawTest.this.areaRect[i].y1, UISpenHoveringDrawTest.this.areaRect[i].x2, UISpenHoveringDrawTest.this.areaRect[i].y2));
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
    
    public void onFinish()
    {
      Log.e("UISpenHoveringDrawTest", "Result Pass");
      if (!UISpenHoveringDrawTest.this.isTouch)
      {
        UISpenHoveringDrawTest.this.setResult(-1);
        UISpenHoveringDrawTest.this.finish();
      }
    }
    
    public boolean onHover(View paramView, MotionEvent paramMotionEvent)
    {
      int i = paramMotionEvent.getAction();
      paramMotionEvent.getPointerCount();
      switch (i)
      {
      case 8: 
      default: 
      case 9: 
      case 7: 
        do
        {
          do
          {
            do
            {
              do
              {
                return false;
                Log.i("UISpenHoveringDrawTest", "ACTION_HOVER_ENTER ");
                StringBuilder localStringBuilder3 = new StringBuilder().append("X,Y : ");
                Object[] arrayOfObject3 = new Object[1];
                arrayOfObject3[0] = Float.valueOf(paramMotionEvent.getX());
                StringBuilder localStringBuilder4 = localStringBuilder3.append(String.format("%.1f", arrayOfObject3)).append(",");
                Object[] arrayOfObject4 = new Object[1];
                arrayOfObject4[0] = Float.valueOf(paramMotionEvent.getY());
                Log.i("UISpenHoveringDrawTest", String.format("%.1f", arrayOfObject4));
              } while ((!UISpenHoveringDrawTest.this.mIsWacom) || (paramMotionEvent.getX() <= 0.0F) || (paramMotionEvent.getY() <= 0.0F));
              if ((!UISpenHoveringDrawTest.this.mIsFail) && (!UISpenHoveringDrawTest.this.mIsPass))
              {
                this.mTouchedX = paramMotionEvent.getX();
                this.mTouchedY = paramMotionEvent.getY();
                drawHoverText(true);
                Log.i("UISpenHoveringDrawTest", "ACTION_HOVER_ENTER test start!");
              }
              UISpenHoveringDrawTest.access$902(UISpenHoveringDrawTest.this, true);
              return false;
            } while ((UISpenHoveringDrawTest.this.mIsFail) || (UISpenHoveringDrawTest.this.mIsPass));
            if (UISpenHoveringDrawTest.this.mIsTestStart) {
              break;
            }
            StringBuilder localStringBuilder1 = new StringBuilder().append("X,Y : ");
            Object[] arrayOfObject1 = new Object[1];
            arrayOfObject1[0] = Float.valueOf(paramMotionEvent.getX());
            StringBuilder localStringBuilder2 = localStringBuilder1.append(String.format("%.1f", arrayOfObject1)).append(",");
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = Float.valueOf(paramMotionEvent.getY());
            Log.i("UISpenHoveringDrawTest", String.format("%.1f", arrayOfObject2));
          } while ((paramMotionEvent.getX() <= 0.0F) || (paramMotionEvent.getY() <= 0.0F));
          this.mTouchedX = paramMotionEvent.getX();
          this.mTouchedY = paramMotionEvent.getY();
          drawHoverText(true);
          UISpenHoveringDrawTest.access$902(UISpenHoveringDrawTest.this, true);
          Log.i("UISpenHoveringDrawTest", "ACTION_HOVER_ENTER test start! at MOVE");
          return false;
        } while ((paramMotionEvent.getX() <= 0.0F) || (paramMotionEvent.getY() <= 0.0F));
        for (int j = 0; j < paramMotionEvent.getHistorySize(); j++)
        {
          this.mPreTouchedX = this.mTouchedX;
          this.mPreTouchedY = this.mTouchedY;
          this.mTouchedX = paramMotionEvent.getHistoricalX(j);
          this.mTouchedY = paramMotionEvent.getHistoricalY(j);
          drawRect(this.mTouchedX, this.mTouchedY, this.mClickPaint);
          drawLine(this.mPreTouchedX, this.mPreTouchedY, this.mTouchedX, this.mTouchedY);
        }
        this.mPreTouchedX = this.mTouchedX;
        this.mPreTouchedY = this.mTouchedY;
        this.mTouchedX = paramMotionEvent.getX();
        this.mTouchedY = paramMotionEvent.getY();
        drawRect(this.mTouchedX, this.mTouchedY, this.mClickPaint);
        drawLine(this.mPreTouchedX, this.mPreTouchedY, this.mTouchedX, this.mTouchedY);
        return false;
      }
      Log.i("UISpenHoveringDrawTest", "ACTION_HOVER_EXIT");
      UISpenHoveringDrawTest.access$902(UISpenHoveringDrawTest.this, false);
      drawHoverText(false);
      return false;
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      if (paramMotionEvent.getToolType(0) == 2)
      {
        UISpenHoveringDrawTest.access$1102(UISpenHoveringDrawTest.this, true);
        drawResultText(false);
      }
      this.mLinePaint.setColor(-65536);
      if (UISpenHoveringDrawTest.this.mIsWacom) {
        if (paramMotionEvent.getToolType(0) == 2) {
          drawByEvent(paramMotionEvent);
        }
      }
      for (;;)
      {
        this.mLinePaint.setColor(-16777216);
        return true;
        if (paramMotionEvent.getToolType(0) != 2) {
          drawByEvent(paramMotionEvent);
        }
      }
    }
  }
  
  private class MyRect
    extends Region
  {
    boolean click;
    int x1;
    int x2;
    int y1;
    int y2;
    
    private MyRect() {}
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UISpenHoveringDrawTest
 * JD-Core Version:    0.7.1
 */