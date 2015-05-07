package com.sec.factory.app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.TextView;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.support.FtUtil;

public class UIRgb
  extends UIBase
{
  private int WHAT_EXIT = 2;
  private int WHAT_UPDATE = 1;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == UIRgb.this.WHAT_UPDATE) {
        UIRgb.this.update();
      }
      while (paramAnonymousMessage.what != UIRgb.this.WHAT_EXIT) {
        return;
      }
      UIRgb.this.finish();
    }
  };
  private ModuleSensor mModuleSensor = null;
  private int[] mSensorID = null;
  private TextView mTextResult;
  private TextView mText_Temperature;
  
  public UIRgb()
  {
    super("UIRgb", 35);
  }
  
  private void update()
  {
    String[] arrayOfString = this.mModuleSensor.getData(ModuleSensor.ID_MANAGER_LIGHT_CCT);
    int i;
    int j;
    String str;
    label101:
    int k;
    if (arrayOfString != null)
    {
      FtUtil.log_i(this.CLASS_NAME, "update", "Temperature : " + arrayOfString[2]);
      this.mText_Temperature.setText(arrayOfString[2]);
      i = (int)Float.parseFloat(arrayOfString[2]);
      j = 0;
      if (i < 10300)
      {
        j = 0;
        if (i > 6300) {
          j = 1;
        }
      }
      if (arrayOfString == null) {
        break label176;
      }
      TextView localTextView1 = this.mTextResult;
      if (j == 0) {
        break label162;
      }
      str = "PASS";
      localTextView1.setText(str);
      TextView localTextView2 = this.mTextResult;
      if (j == 0) {
        break label169;
      }
      k = -16776961;
      label122:
      localTextView2.setTextColor(k);
      if (j != 0) {
        setTestResult((byte)80);
      }
    }
    for (;;)
    {
      if (j != 0) {
        finishOnPassWithTimer();
      }
      return;
      this.mText_Temperature.setText("NONE");
      i = 0;
      break;
      label162:
      str = "FAIL";
      break label101;
      label169:
      k = -65536;
      break label122;
      label176:
      this.mTextResult.setText("FAIL");
      this.mTextResult.setTextColor(-65536);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903085);
    if (this.mModuleSensor == null) {}
    for (ModuleSensor localModuleSensor = ModuleSensor.instance(this);; localModuleSensor = this.mModuleSensor)
    {
      this.mModuleSensor = localModuleSensor;
      this.mModuleSensor.SensorOff();
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = ModuleSensor.ID_MANAGER_LIGHT_CCT;
      this.mSensorID = arrayOfInt;
      this.mModuleSensor.SensorOn(this.mSensorID);
      this.mText_Temperature = ((TextView)findViewById(2131296333));
      this.mTextResult = ((TextView)findViewById(2131296300));
      return;
    }
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      return super.onKeyDown(paramInt, paramKeyEvent);
      FtUtil.log_d(this.CLASS_NAME, "onKeyDown", "KEYCODE_VOLUME_DOWN");
      this.mHandler.sendEmptyMessageDelayed(this.WHAT_UPDATE, 500L);
    }
  }
  
  protected void onPause()
  {
    super.onPause();
    this.mModuleSensor.SensorOff();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIRgb
 * JD-Core Version:    0.7.1
 */