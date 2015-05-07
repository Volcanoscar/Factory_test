package com.sec.factory.app.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;

public class UiHallICTest
  extends UIBase
{
  private final int KEYCODE_FOLDER_CLOSE = 235;
  private final int KEYCODE_FOLDER_OPEN = 234;
  private CountDownTimer _timer;
  private long mCurrentTime = 0L;
  private boolean mIsHallICTestAllPass = false;
  private boolean mIsPressedBackkey = false;
  private boolean mIsReleasePass = false;
  private boolean mIsWorkingPass = false;
  private TextView mReleaseTextView;
  protected Handler mTimerHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      }
      UiHallICTest.access$002(UiHallICTest.this, false);
      Log.e("HallICTest", "mTimerHandler");
    }
  };
  private Vibrator mVibrator;
  private TextView mWorkingTextView;
  private int nHallICTestState = 0;
  
  public UiHallICTest()
  {
    super("UiHallICTest", 19);
  }
  
  private void DisplayResult(int paramInt)
  {
    Log.v("HallICTest", "DisplayResult - mIsWorkingPass = " + this.mIsWorkingPass + ", mIsReleasePass : " + this.mIsReleasePass);
    Log.v("HallICTest", "DisplayResult - nCurrentTest = " + paramInt);
    if (this.mIsHallICTestAllPass == true) {}
    label216:
    label235:
    for (;;)
    {
      return;
      if (paramInt == 1)
      {
        if (this.mIsWorkingPass)
        {
          this.mWorkingTextView.setText("PASS");
          this.mWorkingTextView.setTextColor(-16776961);
          this.mVibrator.vibrate(1000L);
        }
      }
      else if (paramInt == 2)
      {
        if (!this.mIsReleasePass) {
          break label216;
        }
        this.mReleaseTextView.setText("PASS");
        this.mReleaseTextView.setTextColor(-16776961);
        this.mVibrator.vibrate(1000L);
      }
      for (;;)
      {
        if ((this.mIsWorkingPass != true) || (this.mIsReleasePass != true)) {
          break label235;
        }
        this.mIsHallICTestAllPass = true;
        setResult(-1);
        setTestResult((byte)80);
        this._timer.start();
        return;
        this.mWorkingTextView.setText("FAIL");
        this.mWorkingTextView.setTextColor(-65536);
        break;
        this.mReleaseTextView.setText("FAIL");
        this.mReleaseTextView.setTextColor(-65536);
      }
    }
  }
  
  private void HallICTestInit()
  {
    if (Support.Feature.getBoolean("SUPPORT_DUAL_LCD_FOLDER"))
    {
      FtUtil.log_v(this.CLASS_NAME, "HallICTestInit", "Folder open/close check mode");
      ((TextView)findViewById(2131296417)).setVisibility(8);
      ((TextView)findViewById(2131296418)).setText(getString(2131165330));
      ((TextView)findViewById(2131296420)).setText(getString(2131165329));
    }
    this.mWorkingTextView = ((TextView)findViewById(2131296419));
    this.mReleaseTextView = ((TextView)findViewById(2131296421));
    this.mVibrator = ((Vibrator)getSystemService("vibrator"));
    this.nHallICTestState = 0;
    if (Support.Feature.getBoolean("SUPPORT_DUAL_LCD_FOLDER")) {
      this.nHallICTestState = 3;
    }
    this._timer = new CountDownTimer(1000L, 1000L)
    {
      public void onFinish()
      {
        Log.v("HallICTest", "onFinish Countdown Timer End");
        UiHallICTest.access$002(UiHallICTest.this, false);
        UiHallICTest.this.mVibrator.cancel();
        UiHallICTest.this.finish();
      }
      
      public void onTick(long paramAnonymousLong) {}
    };
  }
  
  private int hallIc_by_sysfs()
  {
    Log.e("HallICTest", "hallIc_by_sysfs()");
    try
    {
      String str = readOneLine(Support.Kernel.getFilePath("PATH_HALLIC_STATE"));
      Log.e("HallICTest", "hallIc_by_sysfs : state value ( " + str + " )");
      if (str.equals("0")) {
        return 1;
      }
      boolean bool = str.equals("1");
      if (bool) {
        return 0;
      }
    }
    catch (Exception localException)
    {
      Log.e("HallICTest", "hallIc_by_sysfs Exception ->" + localException);
    }
    return 2;
  }
  
  /* Error */
  private String readOneLine(String paramString)
  {
    // Byte code:
    //   0: ldc 230
    //   2: astore_2
    //   3: aconst_null
    //   4: astore_3
    //   5: ldc 58
    //   7: ldc 232
    //   9: invokestatic 196	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   12: pop
    //   13: new 234	java/io/BufferedReader
    //   16: dup
    //   17: new 236	java/io/FileReader
    //   20: dup
    //   21: aload_1
    //   22: invokespecial 239	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   25: sipush 8096
    //   28: invokespecial 242	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   31: astore 5
    //   33: aload 5
    //   35: ifnull +16 -> 51
    //   38: aload 5
    //   40: invokevirtual 245	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   43: invokevirtual 248	java/lang/String:trim	()Ljava/lang/String;
    //   46: astore 11
    //   48: aload 11
    //   50: astore_2
    //   51: aload 5
    //   53: ifnull +94 -> 147
    //   56: aload 5
    //   58: invokevirtual 251	java/io/BufferedReader:close	()V
    //   61: aload_2
    //   62: ifnonnull +6 -> 68
    //   65: ldc 230
    //   67: astore_2
    //   68: aload_2
    //   69: areturn
    //   70: astore 6
    //   72: aload 6
    //   74: invokevirtual 254	java/io/IOException:printStackTrace	()V
    //   77: goto -16 -> 61
    //   80: astore 7
    //   82: aload 7
    //   84: invokevirtual 254	java/io/IOException:printStackTrace	()V
    //   87: aload_3
    //   88: ifnull -27 -> 61
    //   91: aload_3
    //   92: invokevirtual 251	java/io/BufferedReader:close	()V
    //   95: goto -34 -> 61
    //   98: astore 10
    //   100: aload 10
    //   102: invokevirtual 254	java/io/IOException:printStackTrace	()V
    //   105: goto -44 -> 61
    //   108: astore 8
    //   110: aload_3
    //   111: ifnull +7 -> 118
    //   114: aload_3
    //   115: invokevirtual 251	java/io/BufferedReader:close	()V
    //   118: aload 8
    //   120: athrow
    //   121: astore 9
    //   123: aload 9
    //   125: invokevirtual 254	java/io/IOException:printStackTrace	()V
    //   128: goto -10 -> 118
    //   131: astore 8
    //   133: aload 5
    //   135: astore_3
    //   136: goto -26 -> 110
    //   139: astore 7
    //   141: aload 5
    //   143: astore_3
    //   144: goto -62 -> 82
    //   147: goto -86 -> 61
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	150	0	this	UiHallICTest
    //   0	150	1	paramString	String
    //   2	67	2	localObject1	Object
    //   4	140	3	localObject2	Object
    //   31	111	5	localBufferedReader	java.io.BufferedReader
    //   70	3	6	localIOException1	java.io.IOException
    //   80	3	7	localIOException2	java.io.IOException
    //   139	1	7	localIOException3	java.io.IOException
    //   108	11	8	localObject3	Object
    //   131	1	8	localObject4	Object
    //   121	3	9	localIOException4	java.io.IOException
    //   98	3	10	localIOException5	java.io.IOException
    //   46	3	11	str	String
    // Exception table:
    //   from	to	target	type
    //   56	61	70	java/io/IOException
    //   13	33	80	java/io/IOException
    //   91	95	98	java/io/IOException
    //   13	33	108	finally
    //   82	87	108	finally
    //   114	118	121	java/io/IOException
    //   38	48	131	finally
    //   38	48	139	java/io/IOException
  }
  
  public void CheckingFolderState()
  {
    Log.v("HallICTest", "CheckingFolderState ()");
    int i = hallIc_by_sysfs();
    switch (this.nHallICTestState)
    {
    }
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              return;
              Log.v("HallICTest", "CheckingFolderState - TESTCASE_ONCREATE_RELEASE_CHECK");
            } while (i == 0);
            this.nHallICTestState = 1;
            return;
            Log.v("HallICTest", "CheckingFolderState - TESTCASE_WORKING_CHECK");
          } while (i != 0);
          this.mIsWorkingPass = true;
          DisplayResult(1);
          this.nHallICTestState = 2;
          return;
          Log.v("HallICTest", "CheckingFolderState - TESTCASE_RELEASE_CHECK");
        } while (i != 1);
        this.mIsReleasePass = true;
        DisplayResult(2);
        return;
        FtUtil.log_v(this.CLASS_NAME, "CheckingFolderState", "TESTCASE_CLOSE_CHECK");
      } while (i != 1);
      this.mIsWorkingPass = true;
      DisplayResult(1);
      this.nHallICTestState = 4;
      return;
      FtUtil.log_v(this.CLASS_NAME, "CheckingFolderState", "TESTCASE_OPEN_CHECK");
    } while (i != 0);
    this.mIsReleasePass = true;
    DisplayResult(2);
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    Log.v("HallICTest", "onConfigurationChanged");
    CheckingFolderState();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903065);
    Log.v("HallICTest", "OnCreate");
    HallICTestInit();
    CheckingFolderState();
  }
  
  public void onDestroy()
  {
    Log.v("HallICTest", "onDestroy");
    super.onDestroy();
    this._timer.cancel();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    Log.v("HallICTest", "onKeyDown, keycode: " + String.valueOf(paramInt));
    if (paramInt == 3) {
      return true;
    }
    if ((Support.Feature.getBoolean("SUPPORT_DUAL_LCD_FOLDER")) && ((paramInt == 0) || (paramInt == 234) || (paramInt == 235)))
    {
      FtUtil.log_d(this.CLASS_NAME, "SUPPORT_DUAL_LCD_FOLDER", "Temporary checking flip status");
      CheckingFolderState();
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public void onPause()
  {
    Log.v("HallICTest", "onPause");
    super.onPause();
  }
  
  protected void onResume()
  {
    Log.v("HallICTest", "OnResume");
    super.onResume();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UiHallICTest
 * JD-Core Version:    0.7.1
 */