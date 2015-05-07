package com.sec.factory.app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;
import com.sec.factory.support.Support.Feature;

public class UIBarometer
  extends UIBase
{
  private int WHAT_GET_DATA = 2;
  private int WHAT_GET_DATA2 = 3;
  private int WHAT_UPDATE = 1;
  public int mCurrentIndex = 0;
  String[] mData = null;
  private Handler mHandler;
  private boolean mIsTestAltitude;
  private boolean mIsTestPressure;
  private boolean mIsTestTemp;
  private LinearLayout mLinearAltitude;
  private LinearLayout mLinearPressure;
  private LinearLayout mLinearTemp;
  private ModuleSensor mModuleSensor;
  float[] mPressureValue = new float[5];
  private TextView mResultText;
  int[] mSenserID = null;
  private String mTestCase = Support.FactoryTestMenu.getTestCase(FactoryTestManager.convertorID_XML((byte)22));
  private TextView mTextAltitude;
  private TextView mTextPressure;
  private TextView mTextTemp;
  
  public UIBarometer()
  {
    super("UIBarometer", 22);
    boolean bool1;
    boolean bool2;
    label80:
    boolean bool3;
    if (this.mTestCase == null)
    {
      bool1 = false;
      this.mIsTestTemp = bool1;
      if (this.mTestCase != null) {
        break label129;
      }
      bool2 = false;
      this.mIsTestPressure = bool2;
      String str = this.mTestCase;
      bool3 = false;
      if (str != null) {
        break label142;
      }
    }
    for (;;)
    {
      this.mIsTestAltitude = bool3;
      this.mHandler = new Handler()
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          if (paramAnonymousMessage.what == UIBarometer.this.WHAT_GET_DATA) {
            UIBarometer.this.getDataBaro();
          }
          do
          {
            return;
            if (paramAnonymousMessage.what == UIBarometer.this.WHAT_GET_DATA2)
            {
              UIBarometer.this.getBaroDataDelay();
              return;
            }
          } while (paramAnonymousMessage.what != UIBarometer.this.WHAT_UPDATE);
          FtUtil.log_e(UIBarometer.this.CLASS_NAME, "mHandler.handleMessage", "WHAT_NOTI_SENSOR_READY");
          UIBarometer.this.update();
        }
      };
      return;
      bool1 = this.mTestCase.contains("TEMP");
      break;
      label129:
      bool2 = this.mTestCase.contains("PRESSURE");
      break label80;
      label142:
      bool3 = this.mTestCase.contains("ALTITUDE");
    }
  }
  
  private void getBaroDataDelay()
  {
    this.mHandler.sendEmptyMessageDelayed(this.WHAT_GET_DATA, 600L);
    this.mHandler.sendEmptyMessageDelayed(this.WHAT_GET_DATA, 700L);
    this.mHandler.sendEmptyMessageDelayed(this.WHAT_GET_DATA, 800L);
    this.mHandler.sendEmptyMessageDelayed(this.WHAT_GET_DATA, 900L);
    this.mHandler.sendEmptyMessageDelayed(this.WHAT_GET_DATA, 1000L);
    this.mHandler.sendEmptyMessageDelayed(this.WHAT_UPDATE, 1150L);
  }
  
  private void getDataBaro()
  {
    this.mData = this.mModuleSensor.getData(ModuleSensor.ID_MANAGER_BAROMETER);
    if (this.mData != null)
    {
      FtUtil.log_i(this.CLASS_NAME, "getDataBaro", "mData:" + this.mData[2]);
      this.mPressureValue[this.mCurrentIndex] = Float.valueOf(this.mData[2]).floatValue();
      FtUtil.log_i(this.CLASS_NAME, "getDataBaro : " + this.mCurrentIndex, "mPressureValue:" + this.mPressureValue[this.mCurrentIndex]);
      this.mCurrentIndex = (1 + this.mCurrentIndex);
    }
  }
  
  private void update()
  {
    FtUtil.log_i(this.CLASS_NAME, "update", null);
    int i = 1;
    String[] arrayOfString = this.mModuleSensor.getData(ModuleSensor.ID_MANAGER_BAROMETER);
    int i1;
    if (this.mIsTestTemp)
    {
      if (arrayOfString == null) {
        break label273;
      }
      float f3 = Float.valueOf(arrayOfString[4]).floatValue();
      FtUtil.log_i(this.CLASS_NAME, "update", "Temperature:" + f3);
      if ((-45.0F > f3) || (f3 > 85.0F)) {
        break label220;
      }
      FtUtil.log_i(this.CLASS_NAME, "update", "Temperature : Pass");
      this.mTextTemp.setText(" : Pass ");
      TextView localTextView4 = this.mTextTemp;
      if (i == 0) {
        break label213;
      }
      i1 = -16776961;
      localTextView4.setTextColor(i1);
    }
    for (;;)
    {
      label131:
      if (this.mIsTestPressure)
      {
        if (arrayOfString == null) {
          break label579;
        }
        this.mTextPressure.setText(" : " + arrayOfString[2] + " hPa");
        float f1 = 0.0F;
        int k = this.mPressureValue.length;
        int m = 0;
        for (;;)
        {
          if (m < k)
          {
            f1 += this.mPressureValue[m];
            m++;
            continue;
            label213:
            i1 = -65536;
            break;
            label220:
            FtUtil.log_i(this.CLASS_NAME, "update", "Temperature : Fail");
            this.mTextTemp.setText(" : Fail ");
            TextView localTextView3 = this.mTextTemp;
            if (0 != 0) {}
            for (int n = -16776961;; n = -65536)
            {
              localTextView3.setTextColor(n);
              i = 0;
              break;
            }
            label273:
            FtUtil.log_i(this.CLASS_NAME, "update", "TEMPERATURE - data == null");
            this.mTextTemp.setText(" : NONE");
            i = 0;
            break label131;
          }
        }
        float f2 = f1 / k;
        FtUtil.log_i(this.CLASS_NAME, "update", "result:" + f2);
        if ((300.0F > f2) || (f2 > 1100.0F)) {
          break label562;
        }
        FtUtil.log_i(this.CLASS_NAME, "update", "Pressure : Pass");
      }
    }
    label425:
    String str1;
    label438:
    int j;
    label458:
    String str2;
    StringBuilder localStringBuilder;
    if (this.mIsTestAltitude)
    {
      FtUtil.log_i(this.CLASS_NAME, "update", "ALTITUDE");
      this.mLinearAltitude.setVisibility(0);
      if (arrayOfString != null) {
        this.mTextAltitude.setText(" : " + arrayOfString[3] + " m");
      }
    }
    else
    {
      TextView localTextView1 = this.mResultText;
      if (i == 0) {
        break label629;
      }
      str1 = "PASS";
      localTextView1.setText(str1);
      TextView localTextView2 = this.mResultText;
      if (i == 0) {
        break label637;
      }
      j = -16776961;
      localTextView2.setTextColor(j);
      if (i != 0)
      {
        setTestResult((byte)80);
        finishOnPassWithTimer();
      }
      if (arrayOfString != null)
      {
        str2 = this.CLASS_NAME;
        localStringBuilder = new StringBuilder().append("Temperature:").append(arrayOfString[4]).append(", Pressure:").append(arrayOfString[2]).append(", Altitude:").append(arrayOfString[3]).append(", Result:");
        if (i == 0) {
          break label644;
        }
      }
    }
    label644:
    for (String str3 = "PASS";; str3 = "FAIL")
    {
      FtUtil.log_i(str2, "update", str3);
      return;
      label562:
      FtUtil.log_i(this.CLASS_NAME, "update", "Pressure : Fail");
      i = 0;
      break;
      label579:
      FtUtil.log_i(this.CLASS_NAME, "update", "TEMPERATURE - data == null");
      this.mTextPressure.setText(" : NONE");
      i = 0;
      break;
      FtUtil.log_i(this.CLASS_NAME, "update", "TEMPERATURE - data == null");
      this.mTextAltitude.setText(" : NONE");
      i = 0;
      break label425;
      label629:
      str1 = "FAIL";
      break label438;
      label637:
      j = -65536;
      break label458;
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903053);
    this.mLinearTemp = ((LinearLayout)findViewById(2131296375));
    this.mTextTemp = ((TextView)findViewById(2131296303));
    this.mLinearPressure = ((LinearLayout)findViewById(2131296376));
    this.mTextPressure = ((TextView)findViewById(2131296377));
    this.mLinearAltitude = ((LinearLayout)findViewById(2131296378));
    this.mTextAltitude = ((TextView)findViewById(2131296379));
    this.mResultText = ((TextView)findViewById(2131296300));
    this.mModuleSensor = ModuleSensor.instance(this);
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = ModuleSensor.ID_MANAGER_BAROMETER;
    this.mSenserID = arrayOfInt;
  }
  
  protected void onPause()
  {
    super.onPause();
    if (this.mHandler.hasMessages(this.WHAT_GET_DATA)) {
      this.mHandler.removeMessages(this.WHAT_GET_DATA);
    }
    if (this.mHandler.hasMessages(this.WHAT_UPDATE)) {
      this.mHandler.removeMessages(this.WHAT_UPDATE);
    }
    this.mModuleSensor.SensorOff();
  }
  
  protected void onResume()
  {
    super.onResume();
    this.mModuleSensor.SensorOn(this.mSenserID);
    if (this.mIsTestTemp)
    {
      FtUtil.log_i(this.CLASS_NAME, "onResume", "TEMPERATURE");
      this.mLinearTemp.setVisibility(0);
    }
    if (this.mIsTestPressure)
    {
      FtUtil.log_i(this.CLASS_NAME, "onResume", "PRESSURE");
      this.mLinearPressure.setVisibility(0);
    }
    if (this.mIsTestAltitude)
    {
      FtUtil.log_i(this.CLASS_NAME, "update", "ALTITUDE");
      this.mLinearAltitude.setVisibility(0);
    }
    if (Support.Feature.getBoolean("SUPPORT_SENSORHUB", true))
    {
      this.mHandler.sendEmptyMessageDelayed(this.WHAT_GET_DATA2, 600L);
      return;
    }
    getBaroDataDelay();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIBarometer
 * JD-Core Version:    0.7.1
 */