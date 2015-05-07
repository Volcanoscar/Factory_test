package com.sec.factory.app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;
import java.io.FileOutputStream;
import java.io.IOException;

public class UIIrLedTest
  extends UIBase
  implements View.OnClickListener
{
  private TextView IRLedResultView;
  private TextView IRLedSelfTest;
  private boolean IsIrLedPass = false;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == 1) {}
      try
      {
        UIIrLedTest.this.controlIrED("38400,173,171,24,62,24,61,24,62,24,17,24,17,24,18,24,17,24,18,23,62,24,61,24,62,24,17,24,17,25,17,24,17,24,17,24,19,23,61,24,18,24,17,24,61,24,19,23,17,24,17,24,62,24,17,24,62,24,61,24,19,23,61,24,62,24,61,24,1880,0");
        UIIrLedTest.this.startIRLed();
        return;
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          localIOException.printStackTrace();
        }
      }
    }
  };
  private int maxcount = 0;
  
  public UIIrLedTest()
  {
    super("UIIrLedTest");
  }
  
  private void controlIrED(String paramString)
    throws IOException
  {
    localFileOutputStream = new FileOutputStream(Support.Kernel.getFilePath("IR_LED_SEND"));
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
  
  private void startIRLed()
  {
    boolean bool = Support.Feature.getBoolean("SUPPORT_IRLED_FEEDBACK_IC", false);
    String str = Support.Kernel.read("IR_LED_RESULT_TX");
    if (bool)
    {
      FtUtil.log_i(this.CLASS_NAME, "startIRLed", "Feed Back IC Supported");
      this.mHandler.sendEmptyMessageDelayed(1, 1000L);
      if ("1".equals(str))
      {
        this.IRLedResultView.setTextColor(-16776961);
        this.IRLedResultView.setText("PASS");
        this.IsIrLedPass = true;
      }
      while (!"0".equals(str)) {
        return;
      }
      int i = this.maxcount;
      this.maxcount = (i + 1);
      if (i < 5)
      {
        this.IRLedResultView.setText("Waiting...");
        FtUtil.log_i(this.CLASS_NAME, "startIRLed", "IR LED Tx on Check Wait");
        return;
      }
      this.IRLedResultView.setTextColor(-65536);
      this.IRLedResultView.setText("FAIL");
      FtUtil.log_i(this.CLASS_NAME, "startIRLed", "IR LED Tx on Check Fail");
      return;
    }
    setResult(-1);
    this.IRLedResultView.setTextColor(-16776961);
    this.IRLedResultView.setText("PASS");
    finish();
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    }
    do
    {
      return;
    } while (!this.IsIrLedPass);
    FtUtil.log_i(this.CLASS_NAME, "onClick", "exit_button Click");
    setResult(-1);
    finish();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903070);
    FtUtil.log_i(this.CLASS_NAME, "onCreate", "UIIrLedTest onCreate");
    this.IRLedResultView = ((TextView)findViewById(2131296475));
    this.IRLedSelfTest = ((TextView)findViewById(2131296474));
    this.IRLedSelfTest.setText("Self Test : ");
    View localView = findViewById(2131296477);
    localView.setOnClickListener(this);
    localView.setVisibility(0);
    this.mHandler.sendEmptyMessageDelayed(1, 1000L);
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    this.mHandler.removeMessages(1);
  }
  
  protected void onPause()
  {
    super.onPause();
    this.mHandler.removeMessages(1);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIIrLedTest
 * JD-Core Version:    0.7.1
 */