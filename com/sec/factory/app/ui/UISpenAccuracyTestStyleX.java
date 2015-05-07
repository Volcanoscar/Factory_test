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

public class UISpenAccuracyTestStyleX
  extends UIBase
{
  private int BOTTOM_MARGIN = 15;
  private int HEIGHT_BASIS = 52;
  protected int KEY_TIMEOUT = 2;
  protected int KEY_TIMER_EXPIRED = 1;
  private int LEFT_MARGIN = 5;
  protected int MILLIS_IN_SEC = 1000;
  private int RIGHT_MARGIN = 5;
  private int SIZE_RECT = 4;
  private int TOP_MARGIN = 5;
  private int WIDTH_BASIS = 33;
  private boolean[][] click;
  private Rect[] crossA_Array;
  private Rect[] crossB_Array;
  private boolean[][] draw;
  private boolean[] draw_CrossA;
  private boolean[] draw_CrossB;
  private boolean[][] isDrawArea;
  private int mBottommostOfMatrix = 0;
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
      if (paramAnonymousMessage.what == UISpenAccuracyTestStyleX.this.KEY_TIMER_EXPIRED)
      {
        UISpenAccuracyTestStyleX.access$002(UISpenAccuracyTestStyleX.this, false);
        Log.e("UISpenAccuracyTestStyleX", "mTimerHandler");
      }
    }
  };
  private int mTopmostOfMatrix = 0;
  private int passFlag = 0;
  private boolean remoteCall = false;
  
  public UISpenAccuracyTestStyleX()
  {
    super("UISpenAccuracyTestStyleX", 26);
  }
  
  private void decideRemote()
  {
    this.remoteCall = getIntent().getBooleanExtra("RemoteCall", false);
  }
  
  private void fillUpMatrix()
  {
    for (int i = 0; i < this.HEIGHT_BASIS; i++)
    {
      int k = 0;
      if (k < this.WIDTH_BASIS)
      {
        if (isNeededCheck(i, k)) {
          this.isDrawArea[i][k] = 1;
        }
        for (;;)
        {
          k++;
          break;
          this.isDrawArea[i][k] = 0;
        }
      }
    }
    for (int j = 0; j < 2 * (-2 + this.HEIGHT_BASIS); j++)
    {
      this.draw_CrossA[j] = false;
      this.draw_CrossB[j] = false;
    }
  }
  
  private void finishEpentest()
  {
    Settings.System.putInt(getContentResolver(), "pen_hovering", this.mOldHoveringSetting);
    Intent localIntent = new Intent(this, UISpenAccuracyPointTest.class);
    localIntent.addFlags(33554432);
    startActivity(localIntent);
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
    this.mTopmostOfMatrix = 0;
    this.mBottommostOfMatrix = (-1 + this.HEIGHT_BASIS);
    this.mLeftmostOfMatrix = 0;
    this.mRightmostOfMatrix = (-1 + this.WIDTH_BASIS);
    this.crossA_Array = new Rect[2 * (-2 + this.HEIGHT_BASIS)];
    this.crossB_Array = new Rect[2 * (-2 + this.HEIGHT_BASIS)];
    this.draw_CrossA = new boolean[2 * (-2 + this.HEIGHT_BASIS)];
    this.draw_CrossB = new boolean[2 * (-2 + this.HEIGHT_BASIS)];
  }
  
  private boolean isDeadZoneModels()
  {
    String str = SystemProperties.get("ro.product.model", "Unknown");
    boolean bool1;
    if ((!str.contains("N7100")) && (!str.contains("N7105")) && (!str.contains("N7102")) && (!str.contains("N7108")) && (!str.contains("E250K")) && (!str.contains("E250S")) && (!str.contains("E250L")) && (!str.contains("I605")) && (!str.contains("L900")) && (!str.contains("R950")) && (!str.contains("I317")) && (!str.contains("T889")) && (!str.contains("Sailor")))
    {
      boolean bool2 = str.contains("SC-02E");
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
      int m = 0;
      if (m < this.WIDTH_BASIS)
      {
        if (this.isDrawArea[i][m] == 1) {
          if ((!bool) || (this.draw[i][m] == 0)) {
            break label61;
          }
        }
        label61:
        for (bool = true;; bool = false)
        {
          m++;
          break;
        }
      }
    }
    int j = 0;
    if (j < 2 * (-2 + this.HEIGHT_BASIS))
    {
      int k;
      if ((bool) && (this.draw_CrossA[j] != 0))
      {
        k = 1;
        label103:
        if ((k == 0) || (this.draw_CrossB[j] == 0)) {
          break label131;
        }
      }
      label131:
      for (bool = true;; bool = false)
      {
        j++;
        break;
        k = 0;
        break label103;
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
    Log.i("UISpenAccuracyTestStyleX", "Return S-Pen Rotation to normal setting");
    writeFile("/sys/class/sec/sec_epen/epen_rotation", "200");
  }
  
  private void turnOnRotationFix()
  {
    Log.i("UISpenAccuracyTestStyleX", "Fix S-Pen Rotation to 0");
    writeFile("/sys/class/sec/sec_epen/epen_rotation", "100");
  }
  
  /* Error */
  private void writeFile(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 333	java/io/File
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 336	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_3
    //   9: aload_3
    //   10: invokevirtual 339	java/io/File:exists	()Z
    //   13: ifne +17 -> 30
    //   16: ldc 43
    //   18: ldc_w 341
    //   21: invokestatic 316	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   24: pop
    //   25: aload_3
    //   26: invokevirtual 344	java/io/File:createNewFile	()Z
    //   29: pop
    //   30: aconst_null
    //   31: astore 4
    //   33: new 346	java/io/FileWriter
    //   36: dup
    //   37: aload_1
    //   38: invokespecial 347	java/io/FileWriter:<init>	(Ljava/lang/String;)V
    //   41: astore 5
    //   43: aload 5
    //   45: aload_2
    //   46: invokevirtual 350	java/io/FileWriter:write	(Ljava/lang/String;)V
    //   49: ldc 43
    //   51: ldc_w 352
    //   54: invokestatic 316	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   57: pop
    //   58: aload 5
    //   60: ifnull +148 -> 208
    //   63: aload 5
    //   65: invokevirtual 355	java/io/FileWriter:close	()V
    //   68: return
    //   69: astore 18
    //   71: ldc 43
    //   73: ldc_w 357
    //   76: invokestatic 316	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   79: pop
    //   80: aload 18
    //   82: invokevirtual 360	java/io/IOException:printStackTrace	()V
    //   85: goto -55 -> 30
    //   88: astore 14
    //   90: ldc 43
    //   92: ldc_w 362
    //   95: invokestatic 365	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   98: pop
    //   99: return
    //   100: astore 16
    //   102: ldc 43
    //   104: new 367	java/lang/StringBuilder
    //   107: dup
    //   108: invokespecial 369	java/lang/StringBuilder:<init>	()V
    //   111: ldc_w 371
    //   114: invokevirtual 375	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: aload_1
    //   118: invokevirtual 375	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: ldc_w 377
    //   124: invokevirtual 375	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: aload_2
    //   128: invokevirtual 375	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: invokevirtual 381	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   134: invokestatic 365	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   137: pop
    //   138: aload 4
    //   140: ifnull -72 -> 68
    //   143: aload 4
    //   145: invokevirtual 355	java/io/FileWriter:close	()V
    //   148: return
    //   149: astore 11
    //   151: ldc 43
    //   153: ldc_w 362
    //   156: invokestatic 365	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   159: pop
    //   160: return
    //   161: astore 7
    //   163: aload 4
    //   165: ifnull +8 -> 173
    //   168: aload 4
    //   170: invokevirtual 355	java/io/FileWriter:close	()V
    //   173: aload 7
    //   175: athrow
    //   176: astore 8
    //   178: ldc 43
    //   180: ldc_w 362
    //   183: invokestatic 365	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   186: pop
    //   187: goto -14 -> 173
    //   190: astore 7
    //   192: aload 5
    //   194: astore 4
    //   196: goto -33 -> 163
    //   199: astore 6
    //   201: aload 5
    //   203: astore 4
    //   205: goto -103 -> 102
    //   208: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	209	0	this	UISpenAccuracyTestStyleX
    //   0	209	1	paramString1	String
    //   0	209	2	paramString2	String
    //   8	18	3	localFile	java.io.File
    //   31	173	4	localObject1	Object
    //   41	161	5	localFileWriter	java.io.FileWriter
    //   199	1	6	localIOException1	java.io.IOException
    //   161	13	7	localObject2	Object
    //   190	1	7	localObject3	Object
    //   176	1	8	localIOException2	java.io.IOException
    //   149	1	11	localIOException3	java.io.IOException
    //   88	1	14	localIOException4	java.io.IOException
    //   100	1	16	localIOException5	java.io.IOException
    //   69	12	18	localIOException6	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   25	30	69	java/io/IOException
    //   63	68	88	java/io/IOException
    //   33	43	100	java/io/IOException
    //   143	148	149	java/io/IOException
    //   33	43	161	finally
    //   102	138	161	finally
    //   168	173	176	java/io/IOException
    //   43	58	190	finally
    //   43	58	199	java/io/IOException
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    decideRemote();
    Log.i("UISpenAccuracyTestStyleX", "TouchTest is created");
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
      Log.e("UISpenAccuracyTestStyleX", "This is back_key");
      if (!this.mIsPressedBackkey)
      {
        this.mCurrentTime = Calendar.getInstance().getTimeInMillis();
        this.mIsPressedBackkey = bool;
        startTimer();
        return bool;
      }
      this.mIsPressedBackkey = false;
      localCalendar = Calendar.getInstance();
      Log.e("UISpenAccuracyTestStyleX", "rightNow.getTimeInMillis() = " + localCalendar.getTimeInMillis() + "mCurrentTime = " + this.mCurrentTime);
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
      this.mMatrixBitmap = Bitmap.createBitmap(this.mScreenWidth, this.mScreenHeight, Bitmap.Config.ARGB_8888);
      this.mMatrixCanvas = new Canvas(this.mMatrixBitmap);
      this.mMatrixCanvas.drawColor(-1);
      this.mLineBitmap = Bitmap.createBitmap(this.mScreenWidth, this.mScreenHeight, Bitmap.Config.ARGB_8888);
      this.mLineCanvas = new Canvas(this.mLineBitmap);
      setPaint();
      if (UISpenAccuracyTestStyleX.this.isDeadZoneModels() == true)
      {
        UISpenAccuracyTestStyleX.access$202(UISpenAccuracyTestStyleX.this, 10);
        UISpenAccuracyTestStyleX.access$302(UISpenAccuracyTestStyleX.this, 5);
        UISpenAccuracyTestStyleX.access$402(UISpenAccuracyTestStyleX.this, 5);
        UISpenAccuracyTestStyleX.access$502(UISpenAccuracyTestStyleX.this, 5);
        this.mScreenHeight -= UISpenAccuracyTestStyleX.this.TOP_MARGIN;
        this.mScreenHeight -= UISpenAccuracyTestStyleX.this.BOTTOM_MARGIN;
        this.mScreenWidth -= UISpenAccuracyTestStyleX.this.LEFT_MARGIN;
        this.mScreenWidth -= UISpenAccuracyTestStyleX.this.RIGHT_MARGIN;
        drawDeadzone();
      }
      initRect();
      this.isTouchDown = false;
    }
    
    private void checkPassNfinishEpenTest()
    {
      if ((UISpenAccuracyTestStyleX.this.isPass()) && (UISpenAccuracyTestStyleX.this.passFlag == 0))
      {
        UISpenAccuracyTestStyleX.access$1608(UISpenAccuracyTestStyleX.this);
        if (UISpenAccuracyTestStyleX.this.mIsWacom) {
          UISpenAccuracyTestStyleX.this.finishEpentest();
        }
      }
      else
      {
        return;
      }
      if (UISpenAccuracyTestStyleX.this.remoteCall == true) {
        UISpenAccuracyTestStyleX.this.setResult(-1);
      }
      UISpenAccuracyTestStyleX.this.finish();
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
      this.mMatrixCanvas.drawRect(0.0F, 0.0F, UISpenAccuracyTestStyleX.this.LEFT_MARGIN + this.mScreenWidth + UISpenAccuracyTestStyleX.this.RIGHT_MARGIN, UISpenAccuracyTestStyleX.this.TOP_MARGIN, localPaint);
      this.mMatrixCanvas.drawRect(0.0F, 0.0F, UISpenAccuracyTestStyleX.this.LEFT_MARGIN, UISpenAccuracyTestStyleX.this.TOP_MARGIN + this.mScreenHeight + UISpenAccuracyTestStyleX.this.BOTTOM_MARGIN, localPaint);
      this.mMatrixCanvas.drawRect(UISpenAccuracyTestStyleX.this.LEFT_MARGIN + this.mScreenWidth, 0.0F, UISpenAccuracyTestStyleX.this.LEFT_MARGIN + this.mScreenWidth + UISpenAccuracyTestStyleX.this.RIGHT_MARGIN, UISpenAccuracyTestStyleX.this.TOP_MARGIN + this.mScreenHeight + UISpenAccuracyTestStyleX.this.BOTTOM_MARGIN, localPaint);
      this.mMatrixCanvas.drawRect(0.0F, UISpenAccuracyTestStyleX.this.TOP_MARGIN + this.mScreenHeight, UISpenAccuracyTestStyleX.this.LEFT_MARGIN + this.mScreenWidth + UISpenAccuracyTestStyleX.this.RIGHT_MARGIN, UISpenAccuracyTestStyleX.this.TOP_MARGIN + this.mScreenHeight + UISpenAccuracyTestStyleX.this.BOTTOM_MARGIN, localPaint);
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
      float f1 = this.mScreenHeight / UISpenAccuracyTestStyleX.this.HEIGHT_BASIS;
      float f2 = this.mScreenWidth / UISpenAccuracyTestStyleX.this.WIDTH_BASIS;
      int i = (int)((paramFloat1 - UISpenAccuracyTestStyleX.this.LEFT_MARGIN) / f2);
      int j = (int)((paramFloat2 - UISpenAccuracyTestStyleX.this.TOP_MARGIN) / f1);
      float f3 = f2 * i + UISpenAccuracyTestStyleX.this.LEFT_MARGIN;
      float f4 = f1 * j + UISpenAccuracyTestStyleX.this.TOP_MARGIN;
      if ((j > -1 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS) || (i > -1 + UISpenAccuracyTestStyleX.this.WIDTH_BASIS))
      {
        Log.i("UISpenAccuracyTestStyleX", "You are out of bounds!");
        return;
      }
      if (UISpenAccuracyTestStyleX.this.draw[j][i] == 0)
      {
        UISpenAccuracyTestStyleX.this.draw[j][i] = 1;
        if ((UISpenAccuracyTestStyleX.this.draw[j][i] == 1) && (UISpenAccuracyTestStyleX.this.isDrawArea[j][i] != 0)) {
          this.mMatrixCanvas.drawRect(1 + (int)f3, 1 + (int)f4, (int)(f3 + f2), (int)(f4 + f1), paramPaint);
        }
        if ((j == 1) || (j == -2 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))
        {
          if (UISpenAccuracyTestStyleX.this.draw_CrossA[0] == 0) {
            this.mMatrixCanvas.drawRect(new Rect(1 + UISpenAccuracyTestStyleX.this.crossA_Array[0].left, 1 + UISpenAccuracyTestStyleX.this.crossA_Array[0].top, -1 + UISpenAccuracyTestStyleX.this.crossA_Array[0].right, UISpenAccuracyTestStyleX.this.crossA_Array[0].bottom), this.mEmptyPaint);
          }
          if (UISpenAccuracyTestStyleX.this.draw_CrossA[1] == 0) {
            this.mMatrixCanvas.drawRect(new Rect(1 + UISpenAccuracyTestStyleX.this.crossA_Array[1].left, 1 + UISpenAccuracyTestStyleX.this.crossA_Array[1].top, -1 + UISpenAccuracyTestStyleX.this.crossA_Array[1].right, UISpenAccuracyTestStyleX.this.crossA_Array[1].bottom), this.mEmptyPaint);
          }
          if (UISpenAccuracyTestStyleX.this.draw_CrossA[(2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))] == 0) {
            this.mMatrixCanvas.drawRect(new Rect(1 + UISpenAccuracyTestStyleX.this.crossA_Array[(2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].left, 1 + UISpenAccuracyTestStyleX.this.crossA_Array[(2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].top, -1 + UISpenAccuracyTestStyleX.this.crossA_Array[(2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].right, UISpenAccuracyTestStyleX.this.crossA_Array[(2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].bottom), this.mEmptyPaint);
          }
          if (UISpenAccuracyTestStyleX.this.draw_CrossA[(1 + 2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))] == 0) {
            this.mMatrixCanvas.drawRect(new Rect(1 + UISpenAccuracyTestStyleX.this.crossA_Array[(1 + 2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].left, 1 + UISpenAccuracyTestStyleX.this.crossA_Array[(1 + 2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].top, -1 + UISpenAccuracyTestStyleX.this.crossA_Array[(1 + 2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].right, UISpenAccuracyTestStyleX.this.crossA_Array[(1 + 2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].bottom), this.mEmptyPaint);
          }
          if (UISpenAccuracyTestStyleX.this.draw_CrossB[0] == 0) {
            this.mMatrixCanvas.drawRect(new Rect(1 + UISpenAccuracyTestStyleX.this.crossB_Array[0].left, 1 + UISpenAccuracyTestStyleX.this.crossB_Array[0].top, -1 + UISpenAccuracyTestStyleX.this.crossB_Array[0].right, UISpenAccuracyTestStyleX.this.crossB_Array[0].bottom), this.mEmptyPaint);
          }
          if (UISpenAccuracyTestStyleX.this.draw_CrossB[1] == 0) {
            this.mMatrixCanvas.drawRect(new Rect(1 + UISpenAccuracyTestStyleX.this.crossB_Array[1].left, 1 + UISpenAccuracyTestStyleX.this.crossB_Array[1].top, -1 + UISpenAccuracyTestStyleX.this.crossB_Array[1].right, UISpenAccuracyTestStyleX.this.crossB_Array[1].bottom), this.mEmptyPaint);
          }
          if (UISpenAccuracyTestStyleX.this.draw_CrossB[(2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))] == 0) {
            this.mMatrixCanvas.drawRect(new Rect(1 + UISpenAccuracyTestStyleX.this.crossB_Array[(2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].left, 1 + UISpenAccuracyTestStyleX.this.crossB_Array[(2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].top, -1 + UISpenAccuracyTestStyleX.this.crossB_Array[(2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].right, UISpenAccuracyTestStyleX.this.crossB_Array[(2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].bottom), this.mEmptyPaint);
          }
          if (UISpenAccuracyTestStyleX.this.draw_CrossB[(1 + 2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))] == 0) {
            this.mMatrixCanvas.drawRect(new Rect(1 + UISpenAccuracyTestStyleX.this.crossB_Array[(1 + 2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].left, 1 + UISpenAccuracyTestStyleX.this.crossB_Array[(1 + 2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].top, -1 + UISpenAccuracyTestStyleX.this.crossB_Array[(1 + 2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].right, UISpenAccuracyTestStyleX.this.crossB_Array[(1 + 2 * (-3 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS))].bottom), this.mEmptyPaint);
          }
        }
        invalidate(new Rect((int)(f3 - 1.0F), (int)(f4 - 1.0F), (int)(1.0F + (f3 + f2)), (int)(1.0F + (f4 + f1))));
      }
      for (int k = 0; k < 2 * (-2 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS); k++)
      {
        if ((UISpenAccuracyTestStyleX.this.crossA_Array[k].contains((int)paramFloat1, (int)paramFloat2)) && (UISpenAccuracyTestStyleX.this.draw_CrossA[k] == 0))
        {
          UISpenAccuracyTestStyleX.this.draw_CrossA[k] = 1;
          this.mMatrixCanvas.drawRect(new Rect(1 + UISpenAccuracyTestStyleX.this.crossA_Array[k].left, 1 + UISpenAccuracyTestStyleX.this.crossA_Array[k].top, -1 + UISpenAccuracyTestStyleX.this.crossA_Array[k].right, UISpenAccuracyTestStyleX.this.crossA_Array[k].bottom), paramPaint);
          invalidate(UISpenAccuracyTestStyleX.this.crossA_Array[k]);
        }
        if ((UISpenAccuracyTestStyleX.this.crossB_Array[k].contains((int)paramFloat1, (int)paramFloat2)) && (UISpenAccuracyTestStyleX.this.draw_CrossB[k] == 0))
        {
          UISpenAccuracyTestStyleX.this.draw_CrossB[k] = 1;
          this.mMatrixCanvas.drawRect(new Rect(1 + UISpenAccuracyTestStyleX.this.crossB_Array[k].left, 1 + UISpenAccuracyTestStyleX.this.crossB_Array[k].top, -1 + UISpenAccuracyTestStyleX.this.crossB_Array[k].right, UISpenAccuracyTestStyleX.this.crossB_Array[k].bottom), paramPaint);
          invalidate(UISpenAccuracyTestStyleX.this.crossB_Array[k]);
        }
      }
      checkPassNfinishEpenTest();
    }
    
    private void initRect()
    {
      float f1 = this.mScreenHeight / UISpenAccuracyTestStyleX.this.HEIGHT_BASIS;
      float f2 = this.mScreenWidth / UISpenAccuracyTestStyleX.this.WIDTH_BASIS;
      Paint localPaint = new Paint();
      localPaint.setColor(-16777216);
      for (int i = 0;; i++)
      {
        int j = UISpenAccuracyTestStyleX.this.HEIGHT_BASIS;
        if (i >= j) {
          break;
        }
        int i8 = (int)(f1 * i) + UISpenAccuracyTestStyleX.this.TOP_MARGIN;
        for (int i9 = 0;; i9++)
        {
          int i10 = UISpenAccuracyTestStyleX.this.WIDTH_BASIS;
          if (i9 >= i10) {
            break;
          }
          int i11 = (int)(f2 * i9) + UISpenAccuracyTestStyleX.this.LEFT_MARGIN;
          this.mMatrixCanvas.drawLine(i11, i8, UISpenAccuracyTestStyleX.this.LEFT_MARGIN + this.mScreenWidth, i8, localPaint);
          this.mMatrixCanvas.drawLine(i11, i8, i11, UISpenAccuracyTestStyleX.this.TOP_MARGIN + this.mScreenHeight, localPaint);
          UISpenAccuracyTestStyleX.this.draw[i][i9] = 0;
        }
      }
      this.mMatrixCanvas.drawRect(1.0F + f2 + UISpenAccuracyTestStyleX.this.LEFT_MARGIN, 1.0F + f1 + UISpenAccuracyTestStyleX.this.TOP_MARGIN, UISpenAccuracyTestStyleX.this.LEFT_MARGIN + f2 * (-1 + UISpenAccuracyTestStyleX.this.WIDTH_BASIS) - 1.0F, UISpenAccuracyTestStyleX.this.TOP_MARGIN + f1 * (-1 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS) - 1.0F, this.mEmptyPaint);
      int k = (int)(f2 * (-1 + UISpenAccuracyTestStyleX.this.WIDTH_BASIS));
      int m = -1 + 2 * UISpenAccuracyTestStyleX.this.HEIGHT_BASIS;
      for (int n = 2;; n++)
      {
        int i1 = 2 * (-1 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS);
        if (n >= i1) {
          break;
        }
        int i6 = (int)(f1 * (n / 2)) + UISpenAccuracyTestStyleX.this.TOP_MARGIN;
        if (n % 2 == 1) {
          i6 = (int)(i6 + f1 / 2.0F);
        }
        int i7 = (int)(k / m * n) + UISpenAccuracyTestStyleX.this.LEFT_MARGIN;
        this.mMatrixCanvas.drawLine(i7, i6, f2 + i7, i6, localPaint);
        this.mMatrixCanvas.drawLine(f2 + i7, i6, f2 + i7, i6 + f1 / 2.0F, localPaint);
        this.mMatrixCanvas.drawLine(f2 + i7, i6 + f1 / 2.0F, i7, i6 + f1 / 2.0F, localPaint);
        this.mMatrixCanvas.drawLine(i7, i6 + f1 / 2.0F, i7, i6, localPaint);
        UISpenAccuracyTestStyleX.this.crossA_Array[(n - 2)] = new Rect();
        UISpenAccuracyTestStyleX.this.crossA_Array[(n - 2)].left = i7;
        UISpenAccuracyTestStyleX.this.crossA_Array[(n - 2)].right = ((int)(f2 + i7));
        UISpenAccuracyTestStyleX.this.crossA_Array[(n - 2)].top = i6;
        UISpenAccuracyTestStyleX.this.crossA_Array[(n - 2)].bottom = ((int)(i6 + f1 / 2.0F));
      }
      for (int i2 = 2;; i2++)
      {
        int i3 = 2 * (-1 + UISpenAccuracyTestStyleX.this.HEIGHT_BASIS);
        if (i2 >= i3) {
          break;
        }
        int i4 = (int)(f1 * (i2 / 2)) + UISpenAccuracyTestStyleX.this.TOP_MARGIN;
        if (i2 % 2 == 1) {
          i4 = (int)(i4 + f1 / 2.0F);
        }
        int i5 = k - (int)(k / m * i2) + UISpenAccuracyTestStyleX.this.LEFT_MARGIN;
        this.mMatrixCanvas.drawLine(i5, i4, f2 + i5, i4, localPaint);
        this.mMatrixCanvas.drawLine(f2 + i5, i4, f2 + i5, i4 + f1 / 2.0F, localPaint);
        this.mMatrixCanvas.drawLine(f2 + i5, i4 + f1 / 2.0F, i5, i4 + f1 / 2.0F, localPaint);
        this.mMatrixCanvas.drawLine(i5, i4 + f1 / 2.0F, i5, i4, localPaint);
        UISpenAccuracyTestStyleX.this.crossB_Array[(i2 - 2)] = new Rect();
        UISpenAccuracyTestStyleX.this.crossB_Array[(i2 - 2)].left = i5;
        UISpenAccuracyTestStyleX.this.crossB_Array[(i2 - 2)].right = ((int)(f2 + i5));
        UISpenAccuracyTestStyleX.this.crossB_Array[(i2 - 2)].top = i4;
        UISpenAccuracyTestStyleX.this.crossB_Array[(i2 - 2)].bottom = ((int)(i4 + f1 / 2.0F));
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
      if (UISpenAccuracyTestStyleX.this.mIsWacom) {
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
 * Qualified Name:     com.sec.factory.app.ui.UISpenAccuracyTestStyleX
 * JD-Core Version:    0.7.1
 */