package com.sec.factory.app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class UIIrLedTestOld
  extends UIBase
  implements View.OnClickListener
{
  private int mCounter = 0;
  private TextView mCounterView = null;
  private Handler mHandler = new Handler();
  private Runnable mRunnable = null;
  private Timer mTimer = null;
  
  public UIIrLedTestOld()
  {
    super("UIIrLedTestOld");
  }
  
  private void controlIrED(String paramString)
    throws IOException
  {
    localFileOutputStream = new FileOutputStream("/sys/class/sec/sec_ir/ir_send");
    byte[] arrayOfByte = paramString.getBytes();
    try
    {
      localFileOutputStream.write(arrayOfByte);
    }
    catch (Exception localException)
    {
      for (;;)
      {
        localException.printStackTrace();
        localFileOutputStream.close();
      }
    }
    finally
    {
      localFileOutputStream.close();
    }
    Log.i("UIIrLedTest", "controlIrED - write bytes : " + paramString);
  }
  
  private void onoffIrED(String paramString)
    throws IOException
  {
    byte[] arrayOfByte = paramString.getBytes();
    Log.i("UIIrLedTest", "onoffIrED - write bytes : " + paramString);
    FileOutputStream localFileOutputStream = new FileOutputStream("/sys/class/sec/sec_ir_test/ir_send_test");
    try
    {
      localFileOutputStream.write(arrayOfByte);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    finally
    {
      localFileOutputStream.close();
    }
  }
  
  public void finishOperation()
  {
    if ((this.mRunnable != null) && (this.mHandler != null))
    {
      this.mHandler.removeCallbacks(this.mRunnable);
      this.mRunnable = null;
    }
    if (this.mTimer != null) {
      this.mTimer.cancel();
    }
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    case 2131296480: 
    case 2131296481: 
    case 2131296484: 
    case 2131296487: 
    default: 
      return;
    case 2131296482: 
      Log.i("UIIrLedTest", "channel_up");
      try
      {
        controlIrED("38400,173,171,24,62,24,61,24,62,24,17,24,17,24,18,24,17,24,18,23,62,24,61,24,62,24,17,24,17,25,17,24,17,24,17,24,19,23,61,24,18,24,17,24,61,24,19,23,17,24,17,24,62,24,17,24,62,24,61,24,19,23,61,24,62,24,61,24,1880,0");
        return;
      }
      catch (IOException localIOException7)
      {
        localIOException7.printStackTrace();
        return;
      }
    case 2131296483: 
      Log.i("UIIrLedTest", "channel_down");
      try
      {
        controlIrED("38400,173,171,24,62,24,61,24,62,24,17,24,17,24,18,24,17,24,18,23,62,24,61,24,62,24,18,23,17,25,17,24,17,24,17,24,19,23,17,24,17,24,18,24,61,24,17,25,17,24,17,24,62,24,61,24,62,24,61,24,19,23,61,24,62,24,61,24,1880,0");
        return;
      }
      catch (IOException localIOException6)
      {
        localIOException6.printStackTrace();
        return;
      }
    case 2131296485: 
      Log.i("UIIrLedTest", "volume_up");
      try
      {
        controlIrED("38400,173,171,24,62,24,61,24,62,24,17,24,17,24,18,24,17,24,19,22,62,24,61,24,62,24,19,22,17,25,17,24,17,24,17,24,62,24,61,25,61,24,17,24,19,23,17,24,17,24,20,22,17,24,17,24,17,25,61,24,62,24,61,24,62,24,61,24,1880,0");
        return;
      }
      catch (IOException localIOException5)
      {
        localIOException5.printStackTrace();
        return;
      }
    case 2131296486: 
      Log.i("UIIrLedTest", "volume_down");
      try
      {
        controlIrED("38400,173,171,24,62,24,61,24,62,24,17,24,17,24,18,24,17,24,18,23,62,24,61,24,62,24,18,23,17,25,17,24,17,24,17,24,62,24,61,25,17,24,61,24,18,24,17,24,17,24,18,24,17,24,17,24,62,24,17,24,62,24,61,24,62,24,61,24,1880,0");
        return;
      }
      catch (IOException localIOException4)
      {
        localIOException4.printStackTrace();
        return;
      }
    case 2131296488: 
      Log.i("UIIrLedTest", "button_on");
      try
      {
        onoffIrED("38400,10");
        return;
      }
      catch (IOException localIOException3)
      {
        localIOException3.printStackTrace();
        return;
      }
    case 2131296489: 
      Log.i("UIIrLedTest", "button_off");
      try
      {
        onoffIrED("38400,5");
        return;
      }
      catch (IOException localIOException2)
      {
        localIOException2.printStackTrace();
        return;
      }
    case 2131296490: 
      Log.i("UIIrLedTest", "on_off_repeat");
      try
      {
        onoffIrED("38400,10,5,10,5,10,5,10,5,10,5,10,5,10,5,10,5,10,5,10,5");
        return;
      }
      catch (IOException localIOException1)
      {
        localIOException1.printStackTrace();
        return;
      }
    }
    Log.i("UIIrLedTest", "Finished IR LED Test in 15 mode");
    setResult(-1);
    finish();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903071);
    Log.i("UIIrLedTest", "UIIrLedTest onCreate");
    View localView1 = findViewById(2131296482);
    localView1.setOnClickListener(this);
    View localView2 = findViewById(2131296483);
    localView2.setOnClickListener(this);
    View localView3 = findViewById(2131296485);
    localView3.setOnClickListener(this);
    View localView4 = findViewById(2131296486);
    localView4.setOnClickListener(this);
    View localView5 = findViewById(2131296488);
    localView5.setOnClickListener(this);
    View localView6 = findViewById(2131296489);
    localView6.setOnClickListener(this);
    View localView7 = findViewById(2131296490);
    localView7.setOnClickListener(this);
    findViewById(2131296473).setVisibility(0);
    View localView8 = findViewById(2131296479);
    localView8.setOnClickListener(this);
    localView8.setVisibility(0);
    this.mCounterView = ((TextView)findViewById(2131296480));
    this.mCounterView.setVisibility(0);
    localView1.setVisibility(8);
    localView2.setVisibility(8);
    localView3.setVisibility(8);
    localView4.setVisibility(8);
    localView5.setVisibility(8);
    localView6.setVisibility(8);
    localView7.setVisibility(8);
    findViewById(2131296481).setVisibility(8);
    findViewById(2131296484).setVisibility(8);
    findViewById(2131296487).setVisibility(8);
    this.mTimer = new Timer();
    this.mTimer.schedule(new TimerTask()
    {
      public void run()
      {
        UIIrLedTestOld.this.mHandler.post(UIIrLedTestOld.access$002(UIIrLedTestOld.this, new Runnable()
        {
          public void run()
          {
            try
            {
              UIIrLedTestOld.this.controlIrED("38400,173,171,24,62,24,61,24,62,24,17,24,17,24,18,24,17,24,18,23,62,24,61,24,62,24,17,24,17,25,17,24,17,24,17,24,19,23,61,24,18,24,17,24,61,24,19,23,17,24,17,24,62,24,17,24,62,24,61,24,19,23,61,24,62,24,61,24,1880,0");
              UIIrLedTestOld.access$208(UIIrLedTestOld.this);
              UIIrLedTestOld.this.mCounterView.setText(Integer.toString(UIIrLedTestOld.this.mCounter));
              return;
            }
            catch (IOException localIOException)
            {
              localIOException.printStackTrace();
            }
          }
        }));
      }
    }, 0L, 800L);
  }
  
  public void onDestroy()
  {
    finishOperation();
    super.onDestroy();
  }
  
  protected void onPause()
  {
    super.onPause();
    finishOperation();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIIrLedTestOld
 * JD-Core Version:    0.7.1
 */