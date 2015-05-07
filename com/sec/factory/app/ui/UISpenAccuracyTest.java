package com.sec.factory.app.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings.System;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.sec.factory.support.FtUtil;
import java.lang.reflect.Array;
import java.util.Calendar;

public class UISpenAccuracyTest
  extends UIBase
{
  private int BOTTOM_MARGIN = 0;
  private int HEIGHT_BASIS = 52;
  private int HEIGHT_BASIS_CROSS = 52;
  protected int KEY_TIMEOUT = 2;
  protected int KEY_TIMER_EXPIRED = 1;
  private int LEFT_MARGIN = 0;
  protected int MILLIS_IN_SEC = 1000;
  private int RIGHT_MARGIN = 0;
  private int SIZE_RECT = 4;
  private int TOP_MARGIN = 0;
  private int WIDTH_BASIS = 33;
  private boolean[][] click;
  private boolean[][] draw;
  private boolean[] drawCross;
  private boolean[][] isDrawArea;
  private int mBottommostOfMatrix = 0;
  private int mCenterOfHorizontalOfMatrix = 0;
  private int mCenterOfVerticalOfMatrix = 0;
  private long mCurrentTime = 0L;
  private boolean mIsPressedBackkey = false;
  private boolean mIsWacom = true;
  private int mLeftmostOfMatrix = 0;
  private int mOldHoveringSetting = 0;
  private int mRightmostOfMatrix = 0;
  protected Handler mTimerHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == UISpenAccuracyTest.this.KEY_TIMER_EXPIRED)
      {
        UISpenAccuracyTest.access$002(UISpenAccuracyTest.this, false);
        Log.e("SpenAccuracyTest", "mTimerHandler");
      }
    }
  };
  private int mTopmostOfMatrix = 0;
  private int passFlag = 0;
  private boolean remoteCall = false;
  
  public UISpenAccuracyTest()
  {
    super("UISpenAccuracyTest", 26);
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
  
  private void finishEpentest()
  {
    Settings.System.putInt(getContentResolver(), "pen_hovering", this.mOldHoveringSetting);
    setTestResult((byte)26, (byte)80);
    finish();
  }
  
  private void initGridSettings()
  {
    int[] arrayOfInt1 = { this.HEIGHT_BASIS, this.WIDTH_BASIS };
    this.click = ((boolean[][])Array.newInstance(Boolean.TYPE, arrayOfInt1));
    int[] arrayOfInt2 = { this.HEIGHT_BASIS, this.WIDTH_BASIS };
    this.draw = ((boolean[][])Array.newInstance(Boolean.TYPE, arrayOfInt2));
    int[] arrayOfInt3 = { this.HEIGHT_BASIS, this.WIDTH_BASIS };
    this.isDrawArea = ((boolean[][])Array.newInstance(Boolean.TYPE, arrayOfInt3));
    this.HEIGHT_BASIS_CROSS = (2 * (-2 + this.HEIGHT_BASIS));
    this.drawCross = new boolean[2 * this.HEIGHT_BASIS_CROSS];
    this.mTopmostOfMatrix = 0;
    this.mBottommostOfMatrix = (-1 + this.HEIGHT_BASIS);
    this.mCenterOfVerticalOfMatrix = (this.HEIGHT_BASIS / 2);
    this.mLeftmostOfMatrix = 0;
    this.mRightmostOfMatrix = (-1 + this.WIDTH_BASIS);
    this.mCenterOfHorizontalOfMatrix = (this.WIDTH_BASIS / 2);
  }
  
  private boolean isDeadZoneModels()
  {
    String str = SystemProperties.get("ro.product.model", "Unknown");
    boolean bool1;
    if ((!str.contains("N7100")) && (!str.contains("N7105")) && (!str.contains("N7102")) && (!str.contains("N7108")) && (!str.contains("E250K")) && (!str.contains("E250S")) && (!str.contains("E250L")) && (!str.contains("I605")) && (!str.contains("L900")) && (!str.contains("R950")) && (!str.contains("I317")) && (!str.contains("T889")) && (!str.contains("Sailor")) && (!str.contains("SC-02E")) && (!str.contains("N5100")) && (!str.contains("N5110")))
    {
      boolean bool2 = str.contains("N8020");
      bool1 = false;
      if (!bool2) {}
    }
    else
    {
      bool1 = true;
    }
    return bool1;
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
  
  private void setGridSizebyModel()
  {
    String str = SystemProperties.get("ro.product.model", "Unknown");
    if ((str.contains("I9220")) || (str.contains("I9220")))
    {
      this.HEIGHT_BASIS = 26;
      this.WIDTH_BASIS = 16;
      return;
    }
    if (str.contains("I9103"))
    {
      this.HEIGHT_BASIS = 18;
      this.WIDTH_BASIS = 12;
      return;
    }
    if ((str.contains("N8000")) || (str.contains("N8010")) || (str.contains("N8013")) || (str.contains("M480W")) || (str.contains("M480S")) || (str.contains("M480K")))
    {
      this.HEIGHT_BASIS = 52;
      this.WIDTH_BASIS = 33;
      return;
    }
    int i = getWindowManager().getDefaultDisplay().getWidth();
    int j = getWindowManager().getDefaultDisplay().getHeight();
    Resources localResources = getResources();
    float f = TypedValue.applyDimension(5, this.SIZE_RECT, localResources.getDisplayMetrics());
    this.WIDTH_BASIS = (1 + (int)(i / f));
    this.HEIGHT_BASIS = (1 + (int)(j / f));
  }
  
  private void turnOffRotationFix()
  {
    Log.i("SpenAccuracyTest", "Return S-Pen Rotation to normal setting");
    writeFile("/sys/class/sec/sec_epen/epen_rotation", "200");
  }
  
  private void turnOnRotationFix()
  {
    Log.i("SpenAccuracyTest", "Fix S-Pen Rotation to 0");
    writeFile("/sys/class/sec/sec_epen/epen_rotation", "100");
  }
  
  /* Error */
  private void writeFile(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 329	java/io/File
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 332	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_3
    //   9: aload_3
    //   10: invokevirtual 335	java/io/File:exists	()Z
    //   13: ifne +18 -> 31
    //   16: ldc_w 304
    //   19: ldc_w 337
    //   22: invokestatic 312	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   25: pop
    //   26: aload_3
    //   27: invokevirtual 340	java/io/File:createNewFile	()Z
    //   30: pop
    //   31: aconst_null
    //   32: astore 4
    //   34: new 342	java/io/FileWriter
    //   37: dup
    //   38: aload_1
    //   39: invokespecial 343	java/io/FileWriter:<init>	(Ljava/lang/String;)V
    //   42: astore 5
    //   44: aload 5
    //   46: aload_2
    //   47: invokevirtual 346	java/io/FileWriter:write	(Ljava/lang/String;)V
    //   50: ldc_w 304
    //   53: ldc_w 348
    //   56: invokestatic 312	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   59: pop
    //   60: aload 5
    //   62: ifnull +153 -> 215
    //   65: aload 5
    //   67: invokevirtual 351	java/io/FileWriter:close	()V
    //   70: return
    //   71: astore 18
    //   73: ldc_w 304
    //   76: ldc_w 353
    //   79: invokestatic 312	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   82: pop
    //   83: aload 18
    //   85: invokevirtual 356	java/io/IOException:printStackTrace	()V
    //   88: goto -57 -> 31
    //   91: astore 14
    //   93: ldc_w 304
    //   96: ldc_w 358
    //   99: invokestatic 361	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   102: pop
    //   103: return
    //   104: astore 16
    //   106: ldc_w 304
    //   109: new 363	java/lang/StringBuilder
    //   112: dup
    //   113: invokespecial 365	java/lang/StringBuilder:<init>	()V
    //   116: ldc_w 367
    //   119: invokevirtual 371	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: aload_1
    //   123: invokevirtual 371	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: ldc_w 373
    //   129: invokevirtual 371	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: aload_2
    //   133: invokevirtual 371	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   136: invokevirtual 377	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   139: invokestatic 361	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   142: pop
    //   143: aload 4
    //   145: ifnull -75 -> 70
    //   148: aload 4
    //   150: invokevirtual 351	java/io/FileWriter:close	()V
    //   153: return
    //   154: astore 11
    //   156: ldc_w 304
    //   159: ldc_w 358
    //   162: invokestatic 361	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   165: pop
    //   166: return
    //   167: astore 7
    //   169: aload 4
    //   171: ifnull +8 -> 179
    //   174: aload 4
    //   176: invokevirtual 351	java/io/FileWriter:close	()V
    //   179: aload 7
    //   181: athrow
    //   182: astore 8
    //   184: ldc_w 304
    //   187: ldc_w 358
    //   190: invokestatic 361	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   193: pop
    //   194: goto -15 -> 179
    //   197: astore 7
    //   199: aload 5
    //   201: astore 4
    //   203: goto -34 -> 169
    //   206: astore 6
    //   208: aload 5
    //   210: astore 4
    //   212: goto -106 -> 106
    //   215: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	216	0	this	UISpenAccuracyTest
    //   0	216	1	paramString1	String
    //   0	216	2	paramString2	String
    //   8	19	3	localFile	java.io.File
    //   32	179	4	localObject1	Object
    //   42	167	5	localFileWriter	java.io.FileWriter
    //   206	1	6	localIOException1	java.io.IOException
    //   167	13	7	localObject2	Object
    //   197	1	7	localObject3	Object
    //   182	1	8	localIOException2	java.io.IOException
    //   154	1	11	localIOException3	java.io.IOException
    //   91	1	14	localIOException4	java.io.IOException
    //   104	1	16	localIOException5	java.io.IOException
    //   71	13	18	localIOException6	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   26	31	71	java/io/IOException
    //   65	70	91	java/io/IOException
    //   34	44	104	java/io/IOException
    //   148	153	154	java/io/IOException
    //   34	44	167	finally
    //   106	143	167	finally
    //   174	179	182	java/io/IOException
    //   44	60	197	finally
    //   44	60	206	java/io/IOException
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    decideRemote();
    Log.i("SpenAccuracyTest", "TouchTest is created");
    setGridSizebyModel();
    initGridSettings();
    setContentView(new MyView(this));
    getWindow().addFlags(128);
    fillUpMatrix();
    this.mOldHoveringSetting = Settings.System.getInt(getContentResolver(), "pen_hovering", 0);
    Settings.System.putInt(getContentResolver(), "pen_hovering", 0);
  }
  
  public void onExit()
  {
    Log.e("Sensing", "finish");
    setResult(0);
    finish();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    if ((paramInt == 25) || (paramInt == 24))
    {
      finish();
      bool = super.onKeyDown(paramInt, paramKeyEvent);
    }
    Calendar localCalendar;
    do
    {
      do
      {
        return bool;
      } while (paramInt == 3);
      if (paramInt != 4) {
        break;
      }
      Log.e("SpenAccuracyTest", "This is back_key");
      if (!this.mIsPressedBackkey)
      {
        this.mCurrentTime = Calendar.getInstance().getTimeInMillis();
        this.mIsPressedBackkey = bool;
        startTimer();
        return bool;
      }
      this.mIsPressedBackkey = false;
      localCalendar = Calendar.getInstance();
      Log.e("SpenAccuracyTest", "rightNow.getTimeInMillis() = " + localCalendar.getTimeInMillis() + "mCurrentTime = " + this.mCurrentTime);
    } while (localCalendar.getTimeInMillis() > this.mCurrentTime + this.KEY_TIMEOUT * this.MILLIS_IN_SEC);
    Settings.System.putInt(getContentResolver(), "pen_hovering", this.mOldHoveringSetting);
    onExit();
    return bool;
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  protected void onPause()
  {
    turnOffRotationFix();
    super.onPause();
  }
  
  protected void onResume()
  {
    turnOnRotationFix();
    super.onResume();
    FtUtil.setRemoveSystemUI(getWindow(), true);
  }
  
  protected void onStop()
  {
    super.onStop();
    finish();
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
      this.mMatrixBitmap = Bitmap.createBitmap(this.mScreenWidth, this.mScreenHeight, Bitmap.Config.ARGB_8888);
      this.mMatrixCanvas = new Canvas(this.mMatrixBitmap);
      this.mMatrixCanvas.drawColor(-1);
      this.mLineBitmap = Bitmap.createBitmap(this.mScreenWidth, this.mScreenHeight, Bitmap.Config.ARGB_8888);
      this.mLineCanvas = new Canvas(this.mLineBitmap);
      setPaint();
      if (UISpenAccuracyTest.this.isDeadZoneModels() == true)
      {
        UISpenAccuracyTest.access$202(UISpenAccuracyTest.this, 10);
        UISpenAccuracyTest.access$302(UISpenAccuracyTest.this, 5);
        UISpenAccuracyTest.access$402(UISpenAccuracyTest.this, 5);
        UISpenAccuracyTest.access$502(UISpenAccuracyTest.this, 5);
        this.mScreenHeight -= UISpenAccuracyTest.this.TOP_MARGIN;
        this.mScreenHeight -= UISpenAccuracyTest.this.BOTTOM_MARGIN;
        this.mScreenWidth -= UISpenAccuracyTest.this.LEFT_MARGIN;
        this.mScreenWidth -= UISpenAccuracyTest.this.RIGHT_MARGIN;
        drawDeadzone();
      }
      float f1 = this.mScreenHeight / UISpenAccuracyTest.this.HEIGHT_BASIS;
      float f2 = this.mScreenWidth / UISpenAccuracyTest.this.WIDTH_BASIS;
      this.mWidthCross = ((this.mScreenWidth - 1.0F * f2) / (4 + (-1 + UISpenAccuracyTest.this.HEIGHT_BASIS_CROSS)));
      this.mHeightCross = (f1 / 2.0F);
      initRect();
      this.isTouchDown = false;
    }
    
    private void checkCrossRectRegion(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, Paint paramPaint)
    {
      float f1 = this.mScreenHeight / UISpenAccuracyTest.this.HEIGHT_BASIS;
      float f2 = this.mScreenWidth / UISpenAccuracyTest.this.WIDTH_BASIS;
      if (UISpenAccuracyTest.this.isDeadZoneModels() == true) {
        paramFloat2 -= UISpenAccuracyTest.this.TOP_MARGIN;
      }
      if ((paramInt2 > 0) && (paramInt2 < -1 + UISpenAccuracyTest.this.HEIGHT_BASIS))
      {
        int i = (int)((paramFloat2 - f1) / this.mHeightCross);
        int j = (int)(this.mWidthCross * (i + 2)) + UISpenAccuracyTest.this.LEFT_MARGIN;
        int k = (int)(f1 + this.mHeightCross * i) + UISpenAccuracyTest.this.TOP_MARGIN;
        int m = (int)(this.mScreenWidth - 1.0F * f2 - this.mWidthCross * (i + 2)) + UISpenAccuracyTest.this.LEFT_MARGIN;
        if ((paramFloat1 > j) && (paramFloat1 < f2 + j))
        {
          this.mMatrixCanvas.drawRect(j + 1, k + 1, (int)(f2 + j), (int)(k + f1 / 2.0F), paramPaint);
          invalidate(new Rect(j - 1, k - 1, (int)(1.0F + (f2 + j)), (int)(1.0F + (f1 + k))));
          if (i >= 0) {
            UISpenAccuracyTest.this.drawCross[i] = 1;
          }
        }
        if ((paramFloat1 > m) && (paramFloat1 < f2 + m))
        {
          this.mMatrixCanvas.drawRect(m + 1, k + 1, (int)(f2 + m), (int)(k + f1 / 2.0F), paramPaint);
          invalidate(new Rect(m - 1, k - 1, (int)(1.0F + (f2 + m)), (int)(1.0F + (f1 + k))));
          if (i >= 0) {
            UISpenAccuracyTest.this.drawCross[(-1 + 2 * UISpenAccuracyTest.this.HEIGHT_BASIS_CROSS - i)] = 1;
          }
        }
      }
    }
    
    private void checkPassNfinishEpenTest()
    {
      if ((UISpenAccuracyTest.this.isPass()) && (UISpenAccuracyTest.this.isPassCross()) && (UISpenAccuracyTest.this.passFlag == 0))
      {
        UISpenAccuracyTest.access$1508(UISpenAccuracyTest.this);
        if (UISpenAccuracyTest.this.mIsWacom) {
          UISpenAccuracyTest.this.finishEpentest();
        }
      }
      else
      {
        return;
      }
      if (UISpenAccuracyTest.this.remoteCall == true) {
        UISpenAccuracyTest.this.setResult(-1);
      }
      UISpenAccuracyTest.this.finish();
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
          this.isTouchDown = true;
          return;
        } while (!this.isTouchDown);
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
        this.isTouchDown = true;
        return;
      } while (!this.isTouchDown);
      this.mPreTouchedX = this.mTouchedX;
      this.mPreTouchedY = this.mTouchedY;
      this.mTouchedX = paramMotionEvent.getX();
      this.mTouchedY = paramMotionEvent.getY();
      if ((this.mPreTouchedX == this.mTouchedX) && (this.mPreTouchedY == this.mTouchedY)) {
        drawPoint(this.mTouchedX, this.mTouchedY);
      }
      this.isTouchDown = false;
    }
    
    private void drawDeadzone()
    {
      Paint localPaint = new Paint();
      localPaint.setColor(-16777216);
      localPaint.setStyle(Paint.Style.FILL);
      this.mMatrixCanvas.drawRect(0.0F, 0.0F, UISpenAccuracyTest.this.LEFT_MARGIN + this.mScreenWidth + UISpenAccuracyTest.this.RIGHT_MARGIN, UISpenAccuracyTest.this.TOP_MARGIN, localPaint);
      this.mMatrixCanvas.drawRect(0.0F, 0.0F, UISpenAccuracyTest.this.LEFT_MARGIN, UISpenAccuracyTest.this.TOP_MARGIN + this.mScreenHeight + UISpenAccuracyTest.this.BOTTOM_MARGIN, localPaint);
      this.mMatrixCanvas.drawRect(UISpenAccuracyTest.this.LEFT_MARGIN + this.mScreenWidth, 0.0F, UISpenAccuracyTest.this.LEFT_MARGIN + this.mScreenWidth + UISpenAccuracyTest.this.RIGHT_MARGIN, UISpenAccuracyTest.this.TOP_MARGIN + this.mScreenHeight + UISpenAccuracyTest.this.BOTTOM_MARGIN, localPaint);
      this.mMatrixCanvas.drawRect(0.0F, UISpenAccuracyTest.this.TOP_MARGIN + this.mScreenHeight, UISpenAccuracyTest.this.LEFT_MARGIN + this.mScreenWidth + UISpenAccuracyTest.this.RIGHT_MARGIN, UISpenAccuracyTest.this.TOP_MARGIN + this.mScreenHeight + UISpenAccuracyTest.this.BOTTOM_MARGIN, localPaint);
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
      float f1 = this.mScreenHeight / UISpenAccuracyTest.this.HEIGHT_BASIS;
      float f2 = this.mScreenWidth / UISpenAccuracyTest.this.WIDTH_BASIS;
      int i = (int)((paramFloat1 - UISpenAccuracyTest.this.LEFT_MARGIN) / f2);
      int j = (int)((paramFloat2 - UISpenAccuracyTest.this.TOP_MARGIN) / f1);
      float f3 = f2 * i + UISpenAccuracyTest.this.LEFT_MARGIN;
      float f4 = f1 * j + UISpenAccuracyTest.this.TOP_MARGIN;
      if ((j > -1 + UISpenAccuracyTest.this.HEIGHT_BASIS) || (i > -1 + UISpenAccuracyTest.this.WIDTH_BASIS))
      {
        Log.i("SpenAccuracyTest", "You are out of bounds!");
        return;
      }
      if (UISpenAccuracyTest.this.draw[j][i] == 0)
      {
        UISpenAccuracyTest.this.draw[j][i] = 1;
        if ((UISpenAccuracyTest.this.draw[j][i] == 1) && (UISpenAccuracyTest.this.isDrawArea[j][i] != 0)) {
          this.mMatrixCanvas.drawRect(1 + (int)f3, 1 + (int)f4, (int)(f3 + f2), (int)(f4 + f1), paramPaint);
        }
        invalidate(new Rect((int)(f3 - 1.0F), (int)(f4 - 1.0F), (int)(1.0F + (f3 + f2)), (int)(1.0F + (f4 + f1))));
      }
      checkCrossRectRegion(paramFloat1, paramFloat2, i, j, paramPaint);
      checkPassNfinishEpenTest();
    }
    
    private void initRect()
    {
      float f1 = this.mScreenHeight / UISpenAccuracyTest.this.HEIGHT_BASIS;
      float f2 = this.mScreenWidth / UISpenAccuracyTest.this.WIDTH_BASIS;
      Paint localPaint1 = new Paint();
      localPaint1.setColor(-16777216);
      Paint localPaint2 = new Paint();
      localPaint2.setColor(-16777216);
      localPaint2.setStyle(Paint.Style.STROKE);
      for (int i = 0;; i++)
      {
        int j = UISpenAccuracyTest.this.HEIGHT_BASIS;
        if (i >= j) {
          break;
        }
        int i6 = (int)(f1 * i) + UISpenAccuracyTest.this.TOP_MARGIN;
        for (int i7 = 0;; i7++)
        {
          int i8 = UISpenAccuracyTest.this.WIDTH_BASIS;
          if (i7 >= i8) {
            break;
          }
          int i9 = (int)(f2 * i7) + UISpenAccuracyTest.this.LEFT_MARGIN;
          this.mMatrixCanvas.drawLine(i9, i6, UISpenAccuracyTest.this.LEFT_MARGIN + this.mScreenWidth, i6, localPaint1);
          this.mMatrixCanvas.drawLine(i9, i6, i9, UISpenAccuracyTest.this.TOP_MARGIN + this.mScreenHeight, localPaint1);
          UISpenAccuracyTest.this.draw[i][i7] = 0;
        }
      }
      this.mMatrixCanvas.drawRect(1.0F + f2 + UISpenAccuracyTest.this.LEFT_MARGIN, 1.0F + f1 + UISpenAccuracyTest.this.TOP_MARGIN, UISpenAccuracyTest.this.LEFT_MARGIN + f2 * (-1 + UISpenAccuracyTest.this.WIDTH_BASIS) - 1.0F, UISpenAccuracyTest.this.TOP_MARGIN + f1 * (-1 + UISpenAccuracyTest.this.HEIGHT_BASIS) - 1.0F, this.mEmptyPaint);
      for (int k = 0;; k++)
      {
        int m = UISpenAccuracyTest.this.HEIGHT_BASIS_CROSS;
        if (k >= m) {
          break;
        }
        int i4 = (int)(this.mWidthCross * (k + 2)) + UISpenAccuracyTest.this.LEFT_MARGIN;
        int i5 = (int)(f1 + this.mHeightCross * k) + UISpenAccuracyTest.this.TOP_MARGIN;
        this.mMatrixCanvas.drawRect(i4, i5, f2 + i4, i5 + f1 / 2.0F, localPaint2);
        UISpenAccuracyTest.this.drawCross[k] = 0;
      }
      for (int n = 0;; n++)
      {
        int i1 = UISpenAccuracyTest.this.HEIGHT_BASIS_CROSS;
        if (n >= i1) {
          break;
        }
        int i2 = (int)(this.mWidthCross * (n + 2)) + UISpenAccuracyTest.this.LEFT_MARGIN;
        int i3 = (int)(this.mScreenHeight - (f1 + this.mHeightCross * (n + 1))) + UISpenAccuracyTest.this.TOP_MARGIN;
        this.mMatrixCanvas.drawRect(i2, i3, f2 + i2, i3 + f1 / 2.0F, localPaint2);
        UISpenAccuracyTest.this.drawCross[(n + UISpenAccuracyTest.this.HEIGHT_BASIS_CROSS)] = 0;
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
      this.mLinePaint.setStrokeWidth(2.0F);
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
      if (UISpenAccuracyTest.this.mIsWacom) {
        if (paramMotionEvent.getToolType(0) == 2) {
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
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UISpenAccuracyTest
 * JD-Core Version:    0.7.1
 */