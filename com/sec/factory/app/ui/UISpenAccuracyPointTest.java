package com.sec.factory.app.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemProperties;
import android.provider.Settings.System;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.sec.factory.support.FtUtil;

public class UISpenAccuracyPointTest
  extends UIBase
{
  private int EDGE_NUM_X = 3;
  private int EDGE_NUM_Y = 3;
  private int NUMBER_OF_CIRCLES = 9;
  private int SIZE_CENTER_CIRCLE = 3;
  private int SIZE_MARGIN = 5;
  private int SIZE_OUT_CIRCLE = 5;
  private float SIZE_X;
  private float SIZE_Y;
  private CircleView circleView;
  private float closerEdgeX;
  private float closerEdgeY;
  float delta_Distance = 0.0F;
  float delta_Distance_MAX = 0.0F;
  float delta_subtractionX = 0.0F;
  float delta_subtractionX_MAX = 0.0F;
  float delta_subtractionY = 0.0F;
  float delta_subtractionY_MAX = 0.0F;
  private float[] edgeArray_X;
  private float[] edgeArray_Y;
  private float edgeX;
  private float edgeY;
  private Paint mCloserEdgePaint;
  private Handler mHandler = new Handler();
  private boolean[] mIsClicked;
  private boolean mIsPassFlag = false;
  private boolean mIsTablet = false;
  private boolean mIsWacom = true;
  private int mOldHoveringSetting = 0;
  
  public UISpenAccuracyPointTest()
  {
    super("UISpenAccuracyPointTest", 26);
  }
  
  private void initPoints()
  {
    this.mIsClicked = new boolean[this.NUMBER_OF_CIRCLES];
    if (this.circleView != null) {
      this.circleView.invalidate();
    }
  }
  
  private void initValues()
  {
    String str = SystemProperties.get("ro.build.characteristics");
    if (str != null) {
      this.mIsTablet = str.contains("tablet");
    }
    if (this.mIsTablet)
    {
      this.NUMBER_OF_CIRCLES = 9;
      this.EDGE_NUM_X = 3;
    }
    for (this.EDGE_NUM_Y = 3;; this.EDGE_NUM_Y = 2)
    {
      this.edgeArray_X = new float[this.EDGE_NUM_X];
      this.edgeArray_Y = new float[this.EDGE_NUM_Y];
      return;
      this.NUMBER_OF_CIRCLES = 5;
      this.EDGE_NUM_X = 2;
    }
  }
  
  private void turnOffRotationFix()
  {
    Log.i("UISpenAccuracyPointTest", "Return S-Pen Rotation to normal setting");
    writeFile("/sys/class/sec/sec_epen/epen_rotation", "200");
  }
  
  private void turnOnRotationFix()
  {
    Log.i("UISpenAccuracyPointTest", "Fix S-Pen Rotation to 0");
    writeFile("/sys/class/sec/sec_epen/epen_rotation", "100");
  }
  
  /* Error */
  private void writeFile(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 200	java/io/File
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 203	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_3
    //   9: aload_3
    //   10: invokevirtual 207	java/io/File:exists	()Z
    //   13: ifne +16 -> 29
    //   16: ldc 44
    //   18: ldc 209
    //   20: invokestatic 183	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   23: pop
    //   24: aload_3
    //   25: invokevirtual 212	java/io/File:createNewFile	()Z
    //   28: pop
    //   29: aconst_null
    //   30: astore 4
    //   32: new 214	java/io/FileWriter
    //   35: dup
    //   36: aload_1
    //   37: invokespecial 215	java/io/FileWriter:<init>	(Ljava/lang/String;)V
    //   40: astore 5
    //   42: aload 5
    //   44: aload_2
    //   45: invokevirtual 218	java/io/FileWriter:write	(Ljava/lang/String;)V
    //   48: ldc 44
    //   50: ldc 220
    //   52: invokestatic 183	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   55: pop
    //   56: aload 5
    //   58: ifnull +142 -> 200
    //   61: aload 5
    //   63: invokevirtual 223	java/io/FileWriter:close	()V
    //   66: return
    //   67: astore 18
    //   69: ldc 44
    //   71: ldc 225
    //   73: invokestatic 183	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   76: pop
    //   77: aload 18
    //   79: invokevirtual 228	java/io/IOException:printStackTrace	()V
    //   82: goto -53 -> 29
    //   85: astore 14
    //   87: ldc 44
    //   89: ldc 230
    //   91: invokestatic 233	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   94: pop
    //   95: return
    //   96: astore 16
    //   98: ldc 44
    //   100: new 235	java/lang/StringBuilder
    //   103: dup
    //   104: invokespecial 236	java/lang/StringBuilder:<init>	()V
    //   107: ldc 238
    //   109: invokevirtual 242	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: aload_1
    //   113: invokevirtual 242	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: ldc 244
    //   118: invokevirtual 242	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: aload_2
    //   122: invokevirtual 242	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   125: invokevirtual 248	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   128: invokestatic 233	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   131: pop
    //   132: aload 4
    //   134: ifnull -68 -> 66
    //   137: aload 4
    //   139: invokevirtual 223	java/io/FileWriter:close	()V
    //   142: return
    //   143: astore 11
    //   145: ldc 44
    //   147: ldc 230
    //   149: invokestatic 233	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   152: pop
    //   153: return
    //   154: astore 7
    //   156: aload 4
    //   158: ifnull +8 -> 166
    //   161: aload 4
    //   163: invokevirtual 223	java/io/FileWriter:close	()V
    //   166: aload 7
    //   168: athrow
    //   169: astore 8
    //   171: ldc 44
    //   173: ldc 230
    //   175: invokestatic 233	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   178: pop
    //   179: goto -13 -> 166
    //   182: astore 7
    //   184: aload 5
    //   186: astore 4
    //   188: goto -32 -> 156
    //   191: astore 6
    //   193: aload 5
    //   195: astore 4
    //   197: goto -99 -> 98
    //   200: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	201	0	this	UISpenAccuracyPointTest
    //   0	201	1	paramString1	String
    //   0	201	2	paramString2	String
    //   8	17	3	localFile	java.io.File
    //   30	166	4	localObject1	Object
    //   40	154	5	localFileWriter	java.io.FileWriter
    //   191	1	6	localIOException1	java.io.IOException
    //   154	13	7	localObject2	Object
    //   182	1	7	localObject3	Object
    //   169	1	8	localIOException2	java.io.IOException
    //   143	1	11	localIOException3	java.io.IOException
    //   85	1	14	localIOException4	java.io.IOException
    //   96	1	16	localIOException5	java.io.IOException
    //   67	11	18	localIOException6	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   24	29	67	java/io/IOException
    //   61	66	85	java/io/IOException
    //   32	42	96	java/io/IOException
    //   137	142	143	java/io/IOException
    //   32	42	154	finally
    //   98	132	154	finally
    //   161	166	169	java/io/IOException
    //   42	56	182	finally
    //   42	56	191	java/io/IOException
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    initValues();
    initPoints();
    this.circleView = new CircleView(this);
    setContentView(this.circleView);
    Display localDisplay = ((WindowManager)getSystemService("window")).getDefaultDisplay();
    Point localPoint = new Point();
    localDisplay.getRealSize(localPoint);
    int i = localPoint.x;
    int j = localPoint.y;
    Button localButton = new Button(this);
    localButton.setText("Reset");
    new LinearLayout(this);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    localButton.setClickable(true);
    localButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UISpenAccuracyPointTest.this.initPoints();
      }
    });
    localButton.setX(-35 + i / 2);
    localButton.setY(j * 2 / 3);
    addContentView(localButton, localLayoutParams);
    this.mOldHoveringSetting = Settings.System.getInt(getContentResolver(), "pen_hovering", 0);
    Settings.System.putInt(getContentResolver(), "pen_hovering", 0);
  }
  
  protected void onDestroy()
  {
    Settings.System.putInt(getContentResolver(), "pen_hovering", this.mOldHoveringSetting);
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 25) || (paramInt == 24)) {
      finish();
    }
    return true;
  }
  
  protected void onPause()
  {
    turnOffRotationFix();
    super.onPause();
  }
  
  protected void onResume()
  {
    super.onResume();
    turnOnRotationFix();
    FtUtil.setRemoveSystemUI(getWindow(), true);
  }
  
  public class CircleView
    extends View
  {
    private float mCenterRadius;
    private Bitmap mCircleBitmap;
    private Canvas mCircleCanvas;
    private Paint mClickPaint;
    private float mLargeRadius;
    private Bitmap mLineBitmap;
    private Canvas mLineCanvas;
    private Paint mLinePaint;
    private Paint mNonClickPaint;
    private String mPassText = "";
    private float mPointX;
    private float mPointY;
    private float mRadiusLength;
    private int mScreenHeight;
    private int mScreenWidth;
    private Paint mTextResultPaint;
    private Paint[] mUsePaint;
    private float viewMarginHeight;
    private float viewMarginWidth;
    
    public CircleView(Context paramContext)
    {
      super();
      Display localDisplay = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
      Point localPoint = new Point();
      localDisplay.getRealSize(localPoint);
      this.mScreenWidth = localPoint.x;
      this.mScreenHeight = localPoint.y;
      initCircleView();
    }
    
    private void calculateCloserEdge(float paramFloat1, float paramFloat2)
    {
      UISpenAccuracyPointTest.access$902(UISpenAccuracyPointTest.this, 10000.0F);
      UISpenAccuracyPointTest.access$1002(UISpenAccuracyPointTest.this, 10000.0F);
      for (int i = 0; i < UISpenAccuracyPointTest.this.EDGE_NUM_Y; i++) {
        for (int j = 0; j < UISpenAccuracyPointTest.this.EDGE_NUM_X; j++) {
          if (squareValue(makeAbsoluteValue(paramFloat1 - UISpenAccuracyPointTest.this.edgeArray_X[j])) + squareValue(makeAbsoluteValue(paramFloat2 - UISpenAccuracyPointTest.this.edgeArray_Y[i])) < squareValue(makeAbsoluteValue(paramFloat1 - UISpenAccuracyPointTest.this.closerEdgeX)) + squareValue(makeAbsoluteValue(paramFloat2 - UISpenAccuracyPointTest.this.closerEdgeY)))
          {
            UISpenAccuracyPointTest.access$902(UISpenAccuracyPointTest.this, UISpenAccuracyPointTest.this.edgeArray_X[j]);
            UISpenAccuracyPointTest.access$1002(UISpenAccuracyPointTest.this, UISpenAccuracyPointTest.this.edgeArray_Y[i]);
          }
        }
      }
      Log.i("UISpenAccuracyPointTest", "closerX : " + UISpenAccuracyPointTest.this.closerEdgeX + "closerY : " + UISpenAccuracyPointTest.this.closerEdgeY);
      this.mLineCanvas.drawLine(UISpenAccuracyPointTest.this.closerEdgeX - this.mLargeRadius, UISpenAccuracyPointTest.this.closerEdgeY, UISpenAccuracyPointTest.this.closerEdgeX + this.mLargeRadius, UISpenAccuracyPointTest.this.closerEdgeY, UISpenAccuracyPointTest.this.mCloserEdgePaint);
      this.mLineCanvas.drawLine(UISpenAccuracyPointTest.this.closerEdgeX, UISpenAccuracyPointTest.this.closerEdgeY - this.mLargeRadius, UISpenAccuracyPointTest.this.closerEdgeX, UISpenAccuracyPointTest.this.closerEdgeY + this.mLargeRadius, UISpenAccuracyPointTest.this.mCloserEdgePaint);
    }
    
    private void checkRange(MotionEvent paramMotionEvent)
    {
      Log.i("UISpenAccuracyPointTest", "pointX: " + this.mPointX);
      Log.i("UISpenAccuracyPointTest", "pointY: " + this.mPointY);
      switch (paramMotionEvent.getAction())
      {
      default: 
        return;
      }
      if (isInCircleArea(this.viewMarginWidth, this.viewMarginHeight, this.mPointX, this.mPointY, this.mRadiusLength))
      {
        Log.i("UISpenAccuracyPointTest", "TopLeftCircle: true");
        UISpenAccuracyPointTest.this.mIsClicked[0] = 1;
        return;
      }
      if (isInCircleArea(this.mScreenWidth - this.viewMarginWidth, this.viewMarginHeight, this.mPointX, this.mPointY, this.mRadiusLength))
      {
        Log.i("UISpenAccuracyPointTest", "TopRightCircle: true");
        UISpenAccuracyPointTest.this.mIsClicked[1] = 1;
        return;
      }
      if (isInCircleArea(this.mScreenWidth / 2, this.mScreenHeight / 2, this.mPointX, this.mPointY, this.mCenterRadius))
      {
        Log.i("UISpenAccuracyPointTest", "CenterCircle: true");
        UISpenAccuracyPointTest.this.mIsClicked[2] = 1;
        return;
      }
      if (isInCircleArea(this.viewMarginWidth, this.mScreenHeight - this.viewMarginHeight, this.mPointX, this.mPointY, this.mRadiusLength))
      {
        Log.i("UISpenAccuracyPointTest", "BottomLeftCircle: true");
        UISpenAccuracyPointTest.this.mIsClicked[3] = 1;
        return;
      }
      if (isInCircleArea(this.mScreenWidth - this.viewMarginWidth, this.mScreenHeight - this.viewMarginHeight, this.mPointX, this.mPointY, this.mRadiusLength))
      {
        Log.i("UISpenAccuracyPointTest", "BottomRightCircle: true");
        UISpenAccuracyPointTest.this.mIsClicked[4] = 1;
        return;
      }
      if (UISpenAccuracyPointTest.this.mIsTablet)
      {
        if (isInCircleArea(this.mScreenWidth / 2, this.viewMarginHeight, this.mPointX, this.mPointY, this.mRadiusLength))
        {
          Log.i("UISpenAccuracyPointTest", "TopCenterCircle: true");
          UISpenAccuracyPointTest.this.mIsClicked[5] = 1;
          return;
        }
        if (isInCircleArea(this.mScreenWidth / 2, this.mScreenHeight - this.viewMarginHeight, this.mPointX, this.mPointY, this.mRadiusLength))
        {
          Log.i("UISpenAccuracyPointTest", "BottomCenterCircle: true");
          UISpenAccuracyPointTest.this.mIsClicked[6] = 1;
          return;
        }
        if (isInCircleArea(this.viewMarginWidth, this.mScreenHeight / 2, this.mPointX, this.mPointY, this.mRadiusLength))
        {
          Log.i("UISpenAccuracyPointTest", "LeftCenterCircle: true");
          UISpenAccuracyPointTest.this.mIsClicked[7] = 1;
          return;
        }
        if (isInCircleArea(this.mScreenWidth - this.viewMarginWidth, this.mScreenHeight / 2, this.mPointX, this.mPointY, this.mRadiusLength))
        {
          Log.i("UISpenAccuracyPointTest", "RightCenterCircle: true");
          UISpenAccuracyPointTest.this.mIsClicked[8] = 1;
          return;
        }
        Log.i("UISpenAccuracyPointTest", "FALSE");
        return;
      }
      Log.i("UISpenAccuracyPointTest", "FALSE");
    }
    
    private void drawCircles(Canvas paramCanvas)
    {
      int i = 0;
      if (i < UISpenAccuracyPointTest.this.NUMBER_OF_CIRCLES)
      {
        if (UISpenAccuracyPointTest.this.mIsClicked[i] != 0) {
          this.mUsePaint[i] = this.mClickPaint;
        }
        for (;;)
        {
          i++;
          break;
          this.mUsePaint[i] = this.mNonClickPaint;
        }
      }
      paramCanvas.drawCircle(this.viewMarginWidth, this.viewMarginHeight, this.mLargeRadius, this.mUsePaint[0]);
      paramCanvas.drawCircle(this.mScreenWidth - this.viewMarginWidth, this.viewMarginHeight, this.mLargeRadius, this.mUsePaint[1]);
      paramCanvas.drawCircle(this.mScreenWidth / 2, this.mScreenHeight / 2, this.mCenterRadius, this.mUsePaint[2]);
      paramCanvas.drawCircle(this.viewMarginWidth, this.mScreenHeight - this.viewMarginHeight, this.mLargeRadius, this.mUsePaint[3]);
      paramCanvas.drawCircle(this.mScreenWidth - this.viewMarginWidth, this.mScreenHeight - this.viewMarginHeight, this.mLargeRadius, this.mUsePaint[4]);
      if (UISpenAccuracyPointTest.this.mIsTablet)
      {
        paramCanvas.drawCircle(this.mScreenWidth / 2, this.viewMarginHeight, this.mLargeRadius, this.mUsePaint[5]);
        paramCanvas.drawCircle(this.mScreenWidth / 2, this.mScreenHeight - this.viewMarginHeight, this.mLargeRadius, this.mUsePaint[6]);
        paramCanvas.drawCircle(this.viewMarginWidth, this.mScreenHeight / 2, this.mLargeRadius, this.mUsePaint[7]);
        paramCanvas.drawCircle(this.mScreenWidth - this.viewMarginWidth, this.mScreenHeight / 2, this.mLargeRadius, this.mUsePaint[8]);
      }
    }
    
    private void drawLines(Canvas paramCanvas)
    {
      paramCanvas.drawLine(this.viewMarginWidth - this.mRadiusLength, this.viewMarginHeight, this.viewMarginWidth + this.mRadiusLength, this.viewMarginHeight, this.mLinePaint);
      paramCanvas.drawLine(this.viewMarginWidth, this.viewMarginHeight - this.mRadiusLength, this.viewMarginWidth, this.viewMarginHeight + this.mRadiusLength, this.mLinePaint);
      paramCanvas.drawLine(this.mScreenWidth - this.viewMarginWidth - this.mRadiusLength, this.viewMarginHeight, this.mScreenWidth - this.viewMarginWidth + this.mRadiusLength, this.viewMarginHeight, this.mLinePaint);
      paramCanvas.drawLine(this.mScreenWidth - this.viewMarginWidth, this.viewMarginHeight - this.mRadiusLength, this.mScreenWidth - this.viewMarginWidth, this.viewMarginHeight + this.mRadiusLength, this.mLinePaint);
      paramCanvas.drawLine(this.mScreenWidth / 2 - this.mCenterRadius, this.mScreenHeight / 2, this.mScreenWidth / 2 + this.mCenterRadius, this.mScreenHeight / 2, this.mLinePaint);
      paramCanvas.drawLine(this.mScreenWidth / 2, this.mScreenHeight / 2 - this.mCenterRadius, this.mScreenWidth / 2, this.mScreenHeight / 2 + this.mCenterRadius, this.mLinePaint);
      paramCanvas.drawLine(this.viewMarginWidth - this.mRadiusLength, this.mScreenHeight - this.viewMarginHeight, this.viewMarginWidth + this.mRadiusLength, this.mScreenHeight - this.viewMarginHeight, this.mLinePaint);
      paramCanvas.drawLine(this.viewMarginWidth, this.mScreenHeight - this.viewMarginHeight - this.mRadiusLength, this.viewMarginWidth, this.mScreenHeight - this.viewMarginHeight + this.mRadiusLength, this.mLinePaint);
      paramCanvas.drawLine(this.mScreenWidth - this.viewMarginWidth - this.mRadiusLength, this.mScreenHeight - this.viewMarginHeight, this.mScreenWidth - this.viewMarginWidth + this.mRadiusLength, this.mScreenHeight - this.viewMarginHeight, this.mLinePaint);
      paramCanvas.drawLine(this.mScreenWidth - this.viewMarginWidth, this.mScreenHeight - this.viewMarginHeight - this.mRadiusLength, this.mScreenWidth - this.viewMarginWidth, this.mScreenHeight - this.viewMarginHeight + this.mRadiusLength, this.mLinePaint);
      if (UISpenAccuracyPointTest.this.mIsTablet)
      {
        paramCanvas.drawLine(this.mScreenWidth / 2 - this.mRadiusLength, this.viewMarginHeight, this.mScreenWidth / 2 + this.mRadiusLength, this.viewMarginHeight, this.mLinePaint);
        paramCanvas.drawLine(this.mScreenWidth / 2, this.viewMarginHeight - this.mRadiusLength, this.mScreenWidth / 2, this.viewMarginHeight + this.mRadiusLength, this.mLinePaint);
        paramCanvas.drawLine(this.mScreenWidth / 2 - this.mRadiusLength, this.mScreenHeight - this.viewMarginHeight, this.mScreenWidth / 2 + this.mRadiusLength, this.mScreenHeight - this.viewMarginHeight, this.mLinePaint);
        paramCanvas.drawLine(this.mScreenWidth / 2, this.mScreenHeight - this.viewMarginHeight - this.mRadiusLength, this.mScreenWidth / 2, this.mScreenHeight - this.viewMarginHeight + this.mRadiusLength, this.mLinePaint);
        paramCanvas.drawLine(this.viewMarginWidth - this.mRadiusLength, this.mScreenHeight / 2, this.viewMarginWidth + this.mRadiusLength, this.mScreenHeight / 2, this.mLinePaint);
        paramCanvas.drawLine(this.viewMarginWidth, this.mScreenHeight / 2 - this.mRadiusLength, this.viewMarginWidth, this.mScreenHeight / 2 + this.mRadiusLength, this.mLinePaint);
        paramCanvas.drawLine(this.mScreenWidth - this.viewMarginWidth - this.mRadiusLength, this.mScreenHeight / 2, this.mScreenWidth - this.viewMarginWidth + this.mRadiusLength, this.mScreenHeight / 2, this.mLinePaint);
        paramCanvas.drawLine(this.mScreenWidth - this.viewMarginWidth, this.mScreenHeight / 2 - this.mRadiusLength, this.mScreenWidth - this.viewMarginWidth, this.mScreenHeight / 2 + this.mRadiusLength, this.mLinePaint);
      }
    }
    
    private void drawPoint(float paramFloat1, float paramFloat2)
    {
      this.mLineCanvas.drawPoint(paramFloat1, paramFloat2, this.mLinePaint);
    }
    
    private void initCircleView()
    {
      Resources localResources = getResources();
      this.mLargeRadius = (TypedValue.applyDimension(5, UISpenAccuracyPointTest.this.SIZE_OUT_CIRCLE, localResources.getDisplayMetrics()) / 2.0F);
      this.mCenterRadius = (TypedValue.applyDimension(5, UISpenAccuracyPointTest.this.SIZE_CENTER_CIRCLE, localResources.getDisplayMetrics()) / 2.0F);
      this.mRadiusLength = this.mLargeRadius;
      this.viewMarginWidth = TypedValue.applyDimension(5, UISpenAccuracyPointTest.this.SIZE_MARGIN, localResources.getDisplayMetrics());
      this.viewMarginHeight = TypedValue.applyDimension(5, UISpenAccuracyPointTest.this.SIZE_MARGIN, localResources.getDisplayMetrics());
      this.mClickPaint = new Paint();
      this.mClickPaint.setStrokeWidth(4.0F);
      this.mClickPaint.setAntiAlias(false);
      this.mClickPaint.setColor(-16711936);
      this.mNonClickPaint = new Paint();
      this.mNonClickPaint.setAntiAlias(false);
      this.mNonClickPaint.setColor(-65536);
      this.mTextResultPaint = new Paint();
      this.mTextResultPaint.setColor(-16776961);
      this.mTextResultPaint.setTextAlign(Paint.Align.CENTER);
      this.mTextResultPaint.setAntiAlias(false);
      this.mTextResultPaint.setTextSize(100.0F);
      this.mLinePaint = new Paint();
      this.mLinePaint.setStrokeWidth(4.0F);
      this.mLinePaint.setColor(-16777216);
      UISpenAccuracyPointTest.access$402(UISpenAccuracyPointTest.this, new Paint());
      UISpenAccuracyPointTest.this.mCloserEdgePaint.setStrokeWidth(4.0F);
      UISpenAccuracyPointTest.this.mCloserEdgePaint.setColor(-16776961);
      this.mCircleBitmap = Bitmap.createBitmap(this.mScreenWidth, this.mScreenHeight, Bitmap.Config.ARGB_8888);
      this.mCircleCanvas = new Canvas(this.mCircleBitmap);
      this.mLineBitmap = Bitmap.createBitmap(this.mScreenWidth, this.mScreenHeight, Bitmap.Config.ARGB_8888);
      this.mLineCanvas = new Canvas(this.mLineBitmap);
      this.mUsePaint = new Paint[UISpenAccuracyPointTest.this.NUMBER_OF_CIRCLES];
      this.mPassText = getResources().getString(2131165274);
      invalidate();
    }
    
    private boolean isInCircleArea(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
    {
      return Math.pow(paramFloat1 - paramFloat3, 2.0D) + Math.pow(paramFloat2 - paramFloat4, 2.0D) <= Math.pow(paramFloat5, 2.0D);
    }
    
    private boolean isPass()
    {
      int i = 1;
      for (int j = 0; j < UISpenAccuracyPointTest.this.NUMBER_OF_CIRCLES; j++) {
        i &= UISpenAccuracyPointTest.this.mIsClicked[j];
      }
      return i;
    }
    
    private float makeAbsoluteValue(float paramFloat)
    {
      if (paramFloat < 0.0F) {
        paramFloat *= -1.0F;
      }
      return paramFloat;
    }
    
    private void saveEdgeLocations()
    {
      UISpenAccuracyPointTest.access$1102(UISpenAccuracyPointTest.this, this.viewMarginWidth);
      UISpenAccuracyPointTest.access$1202(UISpenAccuracyPointTest.this, this.viewMarginHeight);
      if (UISpenAccuracyPointTest.this.mIsTablet)
      {
        UISpenAccuracyPointTest.access$1402(UISpenAccuracyPointTest.this, this.mScreenWidth / 2 - this.viewMarginWidth);
        UISpenAccuracyPointTest.access$1502(UISpenAccuracyPointTest.this, this.mScreenHeight / 2 - this.viewMarginHeight);
      }
      for (;;)
      {
        for (int i = 0; i < UISpenAccuracyPointTest.this.EDGE_NUM_X; i++)
        {
          UISpenAccuracyPointTest.this.edgeArray_X[i] = UISpenAccuracyPointTest.this.edgeX;
          UISpenAccuracyPointTest.access$1116(UISpenAccuracyPointTest.this, UISpenAccuracyPointTest.this.SIZE_X);
        }
        UISpenAccuracyPointTest.access$1402(UISpenAccuracyPointTest.this, this.mScreenWidth - 2.0F * this.viewMarginWidth);
        UISpenAccuracyPointTest.access$1502(UISpenAccuracyPointTest.this, this.mScreenHeight - 2.0F * this.viewMarginHeight);
      }
      for (int j = 0; j < UISpenAccuracyPointTest.this.EDGE_NUM_Y; j++)
      {
        UISpenAccuracyPointTest.this.edgeArray_Y[j] = UISpenAccuracyPointTest.this.edgeY;
        UISpenAccuracyPointTest.access$1216(UISpenAccuracyPointTest.this, UISpenAccuracyPointTest.this.SIZE_Y);
      }
    }
    
    private float squareValue(float paramFloat)
    {
      return paramFloat * paramFloat;
    }
    
    protected void onDraw(Canvas paramCanvas)
    {
      paramCanvas.drawBitmap(this.mCircleBitmap, 0.0F, 0.0F, null);
      drawCircles(paramCanvas);
      drawLines(paramCanvas);
      paramCanvas.drawBitmap(this.mLineBitmap, 0.0F, 0.0F, null);
    }
    
    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
    {
      if ((paramInt == 25) || (paramInt == 24)) {
        UISpenAccuracyPointTest.this.finish();
      }
      return super.onKeyDown(paramInt, paramKeyEvent);
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      this.mPointX = paramMotionEvent.getX();
      this.mPointY = paramMotionEvent.getY();
      int i = paramMotionEvent.getAction();
      if (UISpenAccuracyPointTest.this.mIsWacom) {
        if (paramMotionEvent.getToolType(0) == 2)
        {
          Log.i("UISpenAccuracyPointTest", "Touched!");
          checkRange(paramMotionEvent);
          drawCircles(this.mCircleCanvas);
          drawPoint(this.mPointX, this.mPointY);
          invalidate();
          if ((isPass()) && (!UISpenAccuracyPointTest.this.mIsPassFlag))
          {
            UISpenAccuracyPointTest.access$702(UISpenAccuracyPointTest.this, true);
            this.mCircleCanvas.drawText(this.mPassText, this.mScreenWidth / 2, 300.0F, this.mTextResultPaint);
            invalidate();
            UISpenAccuracyPointTest.this.mHandler.postDelayed(new Runnable()
            {
              public void run()
              {
                UISpenAccuracyPointTest.this.setTestResult((byte)26, (byte)80);
                UISpenAccuracyPointTest.this.finish();
              }
            }, 500L);
          }
        }
      }
      switch (i)
      {
      default: 
        return true;
      }
      initCircleView();
      saveEdgeLocations();
      calculateCloserEdge(this.mPointX, this.mPointY);
      this.mLinePaint.setTextSize(20.0F);
      this.mLineCanvas.drawText("Point T(X " + UISpenAccuracyPointTest.this.closerEdgeX + ", Y " + UISpenAccuracyPointTest.this.closerEdgeY + ")" + " / Point A(X " + this.mPointX + ", Y " + this.mPointY + ")", 30.0F, 300.0F, this.mLinePaint);
      UISpenAccuracyPointTest.this.delta_Distance = ((float)(Math.sqrt(squareValue(makeAbsoluteValue(this.mPointX - UISpenAccuracyPointTest.this.closerEdgeX)) + squareValue(makeAbsoluteValue(this.mPointY - UISpenAccuracyPointTest.this.closerEdgeY))) / 5.6D));
      UISpenAccuracyPointTest.this.delta_subtractionX = makeAbsoluteValue(this.mPointX - UISpenAccuracyPointTest.this.closerEdgeX);
      UISpenAccuracyPointTest.this.delta_subtractionY = makeAbsoluteValue(this.mPointY - UISpenAccuracyPointTest.this.closerEdgeY);
      if (UISpenAccuracyPointTest.this.delta_Distance > UISpenAccuracyPointTest.this.delta_Distance_MAX)
      {
        UISpenAccuracyPointTest.this.delta_Distance_MAX = UISpenAccuracyPointTest.this.delta_Distance;
        UISpenAccuracyPointTest.this.delta_subtractionX_MAX = UISpenAccuracyPointTest.this.delta_subtractionX;
        UISpenAccuracyPointTest.this.delta_subtractionY_MAX = UISpenAccuracyPointTest.this.delta_subtractionY;
      }
      float f = paramMotionEvent.getPressure();
      this.mLineCanvas.drawText("Deta(X " + UISpenAccuracyPointTest.this.delta_subtractionX + ", Y " + UISpenAccuracyPointTest.this.delta_subtractionY + ", " + UISpenAccuracyPointTest.this.delta_Distance + "mm)", 30.0F, 320.0F, this.mLinePaint);
      this.mLineCanvas.drawText("Deta MAX(X " + UISpenAccuracyPointTest.this.delta_subtractionX_MAX + ", Y " + UISpenAccuracyPointTest.this.delta_subtractionY_MAX + ", " + UISpenAccuracyPointTest.this.delta_Distance_MAX + "mm)", 30.0F, 340.0F, this.mLinePaint);
      this.mLineCanvas.drawText("Pressure : " + f, 30.0F, 360.0F, this.mLinePaint);
      return true;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UISpenAccuracyPointTest
 * JD-Core Version:    0.7.1
 */