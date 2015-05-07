package com.sec.factory.app.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.modules.SensorNotification;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;
import com.sec.factory.support.Support.TestCase;
import com.sec.factory.support.Support.Version;

public class UIProximityLight
  extends UIBase
{
  private final int DELAY_EXIT;
  private int DUMMY = 0;
  private int FACTORY_TEST_APP;
  private boolean FLAG_EXIT;
  private int RANGE_LIGHT_BRIGHT;
  private int RANGE_LIGHT_DARK;
  private int RANGE_PROXIMITY_DETECT;
  private int RANGE_PROXIMITY_RELEASE;
  int SENSOR_ID_LIGHT_ADC;
  int SENSOR_ID_LIGHT_LUX;
  int SENSOR_ID_PROXIMITY;
  int SENSOR_ID_PROXIMITY_ADC;
  int SENSOR_ID_PROXIMITY_OFFSET;
  private final int TESTITEM_ID_LIGHT;
  private final int TESTITEM_ID_PROXIMITY;
  private final int TESTITEM_STATUS_FINISH;
  private final int TESTITEM_STATUS_NONE;
  private final int TESTITEM_STATUS_OFFSET;
  private final int TESTITEM_STATUS_PENDING;
  private final int TESTITEM_STATUS_RELEASE;
  private final int TESTITEM_STATUS_RELEASE_SECOND;
  private final int TESTITEM_STATUS_WORKING;
  private final int WHAT_EXIT;
  private Button mButton_Proximity_Offset;
  private float mCurrValue;
  private Handler mHandler;
  private final boolean mIsProximityOffsetTest;
  private final boolean mIsTestLight;
  private final boolean mIsTestProximity;
  private final boolean mLightLuxOnly;
  private LinearLayout mLinearBackground;
  private LinearLayout mLinear_Proximity_Button;
  private final int mLogLevel;
  private ModuleDevice mModuleDevice;
  private ModuleSensor mModuleSensor;
  private float mPrevValue;
  private View.OnClickListener mProximityOffsetButton;
  private String mProximityOffsetValue;
  private String mProximityThresholdValue;
  int[] mSenserID;
  private TableRow mTableRow1_Dummy1;
  private TableRow mTableRow1_Light;
  private TableRow mTableRow1_Proximity;
  private TableRow mTableRow2_Light;
  private TableRow mTableRow2_Proximity;
  private TableRow mTableRow3_Light;
  private TableRow mTableRow3_Proximity;
  private TableRow mTableRow4_Light;
  private TableRow mTableRow4_Proximity;
  private TableRow mTableRow5_Proximity;
  private TableRow mTableRow6_Proximity;
  private final String mTestCase;
  private TestItem[] mTestList;
  private int mTestListIndex;
  private TextView mText_Dummy_Row1_Col1;
  private TextView mText_Light_Row1_Col1;
  private TextView mText_Light_Row1_Col2;
  private TextView mText_Light_Row1_Col3;
  private TextView mText_Light_Row2_Col1;
  private TextView mText_Light_Row2_Col2;
  private TextView mText_Light_Row2_Col3;
  private TextView mText_Light_Row3_Col1;
  private TextView mText_Light_Row3_Col2;
  private TextView mText_Light_Row3_Col3;
  private TextView mText_Light_Row4_Col1;
  private TextView mText_Light_Row4_Col2;
  private TextView mText_Light_Row4_Col3;
  private TextView mText_Proximity_Row1_Col1;
  private TextView mText_Proximity_Row1_Col2;
  private TextView mText_Proximity_Row1_Col3;
  private TextView mText_Proximity_Row2_Col1;
  private TextView mText_Proximity_Row2_Col2;
  private TextView mText_Proximity_Row2_Col3;
  private TextView mText_Proximity_Row3_Col1;
  private TextView mText_Proximity_Row3_Col2;
  private TextView mText_Proximity_Row3_Col3;
  private TextView mText_Proximity_Row4_Col1;
  private TextView mText_Proximity_Row4_Col2;
  private TextView mText_Proximity_Row4_Col3;
  private TextView mText_Proximity_Row5_Col1;
  private TextView mText_Proximity_Row5_Col2;
  private TextView mText_Proximity_Row5_Col3;
  private TextView mText_Proximity_Row6_Col1;
  private TextView mText_Proximity_Row6_Col2;
  private TextView mText_Proximity_Row6_Col3;
  private TextView mText_Title;
  private final float mUIRate;
  
  public UIProximityLight()
  {
    super("UIProximityLight", 17);
    int i = this.DUMMY;
    this.DUMMY = (i + 1);
    this.TESTITEM_ID_PROXIMITY = i;
    int j = this.DUMMY;
    this.DUMMY = (j + 1);
    this.TESTITEM_ID_LIGHT = j;
    int k = this.DUMMY;
    this.DUMMY = (k + 1);
    this.TESTITEM_STATUS_NONE = k;
    int m = this.DUMMY;
    this.DUMMY = (m + 1);
    this.TESTITEM_STATUS_OFFSET = m;
    int n = this.DUMMY;
    this.DUMMY = (n + 1);
    this.TESTITEM_STATUS_PENDING = n;
    int i1 = this.DUMMY;
    this.DUMMY = (i1 + 1);
    this.TESTITEM_STATUS_RELEASE = i1;
    int i2 = this.DUMMY;
    this.DUMMY = (i2 + 1);
    this.TESTITEM_STATUS_RELEASE_SECOND = i2;
    int i3 = this.DUMMY;
    this.DUMMY = (i3 + 1);
    this.TESTITEM_STATUS_WORKING = i3;
    int i4 = this.DUMMY;
    this.DUMMY = (i4 + 1);
    this.TESTITEM_STATUS_FINISH = i4;
    this.mTestList = null;
    this.mTestListIndex = 0;
    this.mSenserID = null;
    this.SENSOR_ID_PROXIMITY = (-1 + ModuleSensor.ID_SCOPE_MIN);
    this.SENSOR_ID_PROXIMITY_OFFSET = (-1 + ModuleSensor.ID_SCOPE_MIN);
    this.SENSOR_ID_PROXIMITY_ADC = (-1 + ModuleSensor.ID_SCOPE_MIN);
    this.SENSOR_ID_LIGHT_ADC = (-1 + ModuleSensor.ID_SCOPE_MIN);
    this.SENSOR_ID_LIGHT_LUX = (-1 + ModuleSensor.ID_SCOPE_MIN);
    this.mUIRate = Support.FactoryTestMenu.getUIRate(FactoryTestManager.convertorID_XML((byte)17));
    this.mTestCase = Support.FactoryTestMenu.getTestCase(FactoryTestManager.convertorID_XML((byte)17));
    this.mLogLevel = Support.FactoryTestMenu.getLogLevel(FactoryTestManager.convertorID_XML((byte)17));
    this.mLightLuxOnly = Support.Feature.getBoolean("LIGHT_SENSOR_LUX_ONLY", false);
    this.mIsTestProximity = this.mTestCase.contains("PROX");
    this.mIsProximityOffsetTest = this.mTestCase.contains("PROX_N_OFFSET");
    this.mIsTestLight = this.mTestCase.contains("LIGHT");
    this.RANGE_PROXIMITY_DETECT = 0;
    this.RANGE_PROXIMITY_RELEASE = 5;
    this.RANGE_LIGHT_DARK = 10;
    this.RANGE_LIGHT_BRIGHT = 150;
    this.mPrevValue = -1.0F;
    this.mCurrValue = -1.0F;
    this.mProximityOffsetValue = null;
    this.WHAT_EXIT = (1 + SensorNotification.WHAT_NOTI_SENSOR_MAX);
    this.DELAY_EXIT = 1000;
    this.FACTORY_TEST_APP = 17;
    this.FLAG_EXIT = true;
    this.mHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what == SensorNotification.WHAT_NOTI_SENSOR_READY)
        {
          FtUtil.log_d(UIProximityLight.this.CLASS_NAME, "mHandler.handleMessage", "WHAT_NOTI_SENSOR_READY");
          if ((UIProximityLight.this.mTestList != null) && (UIProximityLight.this.mTestList.length > 0)) {
            UIProximityLight.this.sensorReady();
          }
        }
        do
        {
          do
          {
            return;
            if (paramAnonymousMessage.what != SensorNotification.WHAT_NOTI_SENSOR_UPDATAE) {
              break;
            }
          } while ((UIProximityLight.this.mTestList == null) || (UIProximityLight.this.mTestList.length <= 0));
          UIProximityLight.this.startTest();
          return;
        } while (paramAnonymousMessage.what != UIProximityLight.this.WHAT_EXIT);
        UIProximityLight.this.exit();
      }
    };
    this.mProximityOffsetButton = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if ((UIProximityLight.this.mTestList[UIProximityLight.this.mTestListIndex].mID == UIProximityLight.this.TESTITEM_ID_PROXIMITY) && (UIProximityLight.this.mTestList[UIProximityLight.this.mTestListIndex].mStatus == UIProximityLight.this.TESTITEM_STATUS_OFFSET))
        {
          FtUtil.log_i(UIProximityLight.this.CLASS_NAME, "mProximityOffsetButton.onClick", null);
          if (UIProximityLight.this.mProximityOffsetValue == null)
          {
            UIProximityLight.this.mTestList[UIProximityLight.this.mTestListIndex].mIsPass = false;
            FtUtil.log_i(UIProximityLight.this.CLASS_NAME, "mProximityOffsetButton.onClick", "=== FAIL === (mProximityOffsetValue==null)");
            UIProximityLight.this.mText_Proximity_Row1_Col3.setText("Fail");
            UIProximityLight.this.mText_Proximity_Row1_Col3.setTextColor(-65536);
          }
        }
        else
        {
          return;
        }
        Support.Kernel.write("PROXI_SENSOR_OFFSET", "0");
        Support.Kernel.write("PROXI_SENSOR_OFFSET", "1");
        FtUtil.log_i(UIProximityLight.this.CLASS_NAME, "mProximityOffsetButton.onClick", "<Befor> offset : " + UIProximityLight.this.mProximityOffsetValue + " , threshold : " + UIProximityLight.this.mProximityThresholdValue);
        int i = Integer.parseInt(UIProximityLight.this.mProximityOffsetValue.replace(" ", ""));
        double d = 1.5D * Integer.parseInt(UIProximityLight.this.mProximityThresholdValue.replace(" ", ""));
        FtUtil.log_i(UIProximityLight.this.CLASS_NAME, "mProximityOffsetButton.onClick", "<After> offset : " + i + " , threshold : " + (int)d);
        if (i > (int)d)
        {
          UIProximityLight.this.mTestList[UIProximityLight.this.mTestListIndex].mIsPass = true;
          FtUtil.log_i(UIProximityLight.this.CLASS_NAME, "mProximityOffsetButton.onClick", "=== PASS ===");
          return;
        }
        UIProximityLight.this.mTestList[UIProximityLight.this.mTestListIndex].mIsPass = false;
        FtUtil.log_i(UIProximityLight.this.CLASS_NAME, "mProximityOffsetButton.onClick", "=== FAIL ===");
        UIProximityLight.this.mText_Proximity_Row1_Col3.setText("Fail");
        UIProximityLight.this.mText_Proximity_Row1_Col3.setTextColor(-65536);
      }
    };
  }
  
  private float converterProximityValue(float paramFloat)
  {
    if (paramFloat == 0.0F) {
      return this.RANGE_PROXIMITY_DETECT;
    }
    return this.RANGE_PROXIMITY_RELEASE;
  }
  
  private void exit()
  {
    FtUtil.log_e(this.CLASS_NAME, "exit", null);
    this.mLinearBackground.setBackgroundColor(-1);
    TestItem[] arrayOfTestItem = this.mTestList;
    int i = 0;
    int k;
    if (arrayOfTestItem != null)
    {
      int j = this.mTestList.length;
      i = 0;
      if (j > 0)
      {
        k = 0;
        if (k >= this.mTestList.length) {
          break label104;
        }
        boolean bool = this.mTestList[k].mIsPass;
        i = 0;
        if (bool) {
          break label89;
        }
      }
    }
    for (;;)
    {
      if (i != 0) {
        setTestResult((byte)80);
      }
      for (;;)
      {
        finish();
        return;
        label89:
        k++;
        break;
        setTestResultFailCase((byte)17);
      }
      label104:
      i = 1;
    }
  }
  
  private void initialize()
  {
    FtUtil.log_d(this.CLASS_NAME, "initialize", "mLogLevel : " + this.mLogLevel);
    FtUtil.log_d(this.CLASS_NAME, "initialize", "mIsTestProximity : " + this.mIsTestProximity);
    FtUtil.log_d(this.CLASS_NAME, "initialize", "mIsProximityOffsetTest : " + this.mIsProximityOffsetTest);
    FtUtil.log_d(this.CLASS_NAME, "initialize", "mIsTestLight : " + this.mIsTestLight);
    this.mModuleSensor = ModuleSensor.instance(this);
    this.mModuleDevice = ModuleDevice.instance(this);
    this.SENSOR_ID_PROXIMITY = ModuleSensor.ID_MANAGER_PROXIMITY;
    this.SENSOR_ID_PROXIMITY_OFFSET = ModuleSensor.ID_FILE____PROXIMITY_OFFSET;
    this.SENSOR_ID_PROXIMITY_ADC = ModuleSensor.ID_FILE____PROXIMITY_ADC;
    this.SENSOR_ID_LIGHT_ADC = ModuleSensor.ID_FILE____LIGHT_ADC;
    this.SENSOR_ID_LIGHT_LUX = ModuleSensor.ID_MANAGER_LIGHT;
    String str = Support.Version.getString("FACTORY_TEST_APP");
    FtUtil.log_d(this.CLASS_NAME, "initialize", "FACTORY_TEST_APP : " + str);
    this.FACTORY_TEST_APP = Integer.parseInt(str.substring(2));
    boolean bool1 = this.mIsTestProximity;
    int i = 0;
    int j = 0;
    if (bool1)
    {
      j = 0 + 1;
      i = 0 + 1;
      if (this.mIsProximityOffsetTest) {
        i += 2;
      }
    }
    if (this.mIsTestLight)
    {
      j++;
      i += 2;
    }
    FtUtil.log_d(this.CLASS_NAME, "initialize", "countTestItem : " + j);
    FtUtil.log_d(this.CLASS_NAME, "initialize", "countID : " + i);
    if (j > 0)
    {
      this.mTestList = new TestItem[j];
      this.mSenserID = new int[i];
      this.mLinearBackground = ((LinearLayout)findViewById(2131296398));
      boolean bool2 = this.mIsTestProximity;
      int k = 0;
      int m = 0;
      if (bool2)
      {
        TestItem[] arrayOfTestItem2 = this.mTestList;
        int i1 = 0 + 1;
        arrayOfTestItem2[0] = new TestItem(this.TESTITEM_ID_PROXIMITY);
        int[] arrayOfInt3 = this.mSenserID;
        int i2 = 0 + 1;
        arrayOfInt3[0] = this.SENSOR_ID_PROXIMITY;
        this.mTableRow2_Proximity = ((TableRow)findViewById(2131296517));
        this.mTableRow2_Proximity.setVisibility(0);
        this.mText_Proximity_Row2_Col1 = ((TextView)findViewById(2131296518));
        this.mText_Proximity_Row2_Col2 = ((TextView)findViewById(2131296519));
        this.mText_Proximity_Row2_Col3 = ((TextView)findViewById(2131296520));
        this.mTableRow3_Proximity = ((TableRow)findViewById(2131296521));
        this.mTableRow3_Proximity.setVisibility(0);
        this.mText_Proximity_Row3_Col1 = ((TextView)findViewById(2131296522));
        this.mText_Proximity_Row3_Col2 = ((TextView)findViewById(2131296523));
        this.mText_Proximity_Row3_Col3 = ((TextView)findViewById(2131296524));
        this.mTableRow4_Proximity = ((TableRow)findViewById(2131296525));
        this.mTableRow4_Proximity.setVisibility(0);
        this.mText_Proximity_Row4_Col1 = ((TextView)findViewById(2131296526));
        this.mText_Proximity_Row4_Col2 = ((TextView)findViewById(2131296527));
        this.mText_Proximity_Row4_Col3 = ((TextView)findViewById(2131296528));
        this.mTableRow5_Proximity = ((TableRow)findViewById(2131296529));
        this.mTableRow5_Proximity.setVisibility(0);
        this.mText_Proximity_Row5_Col1 = ((TextView)findViewById(2131296530));
        this.mText_Proximity_Row5_Col2 = ((TextView)findViewById(2131296531));
        this.mText_Proximity_Row5_Col3 = ((TextView)findViewById(2131296532));
        this.mTableRow6_Proximity = ((TableRow)findViewById(2131296533));
        if (this.FACTORY_TEST_APP < 18) {
          break label1338;
        }
        this.mTableRow6_Proximity.setVisibility(0);
        this.mText_Proximity_Row6_Col1 = ((TextView)findViewById(2131296534));
        this.mText_Proximity_Row6_Col2 = ((TextView)findViewById(2131296535));
        this.mText_Proximity_Row6_Col3 = ((TextView)findViewById(2131296536));
        if (this.mIsProximityOffsetTest)
        {
          int[] arrayOfInt4 = this.mSenserID;
          int i3 = i2 + 1;
          arrayOfInt4[i2] = this.SENSOR_ID_PROXIMITY_OFFSET;
          int[] arrayOfInt5 = this.mSenserID;
          i2 = i3 + 1;
          arrayOfInt5[i3] = this.SENSOR_ID_PROXIMITY_ADC;
          this.mTableRow1_Proximity = ((TableRow)findViewById(2131296513));
          this.mTableRow1_Proximity.setVisibility(0);
          this.mText_Proximity_Row1_Col1 = ((TextView)findViewById(2131296514));
          this.mText_Proximity_Row1_Col2 = ((TextView)findViewById(2131296515));
          this.mText_Proximity_Row1_Col3 = ((TextView)findViewById(2131296516));
          this.mLinear_Proximity_Button = ((LinearLayout)findViewById(2131296555));
          this.mLinear_Proximity_Button.setVisibility(0);
          this.mButton_Proximity_Offset = ((Button)findViewById(2131296556));
          this.mButton_Proximity_Offset.setOnClickListener(this.mProximityOffsetButton);
        }
        k = i2;
        m = i1;
      }
      if (this.mIsTestLight)
      {
        TestItem[] arrayOfTestItem1 = this.mTestList;
        (m + 1);
        arrayOfTestItem1[m] = new TestItem(this.TESTITEM_ID_LIGHT);
        int[] arrayOfInt1 = this.mSenserID;
        int n = k + 1;
        arrayOfInt1[k] = this.SENSOR_ID_LIGHT_ADC;
        int[] arrayOfInt2 = this.mSenserID;
        (n + 1);
        arrayOfInt2[n] = this.SENSOR_ID_LIGHT_LUX;
        this.mTableRow1_Dummy1 = ((TableRow)findViewById(2131296537));
        this.mTableRow1_Dummy1.setVisibility(4);
        this.mTableRow1_Light = ((TableRow)findViewById(2131296539));
        this.mTableRow1_Light.setVisibility(0);
        this.mText_Light_Row1_Col1 = ((TextView)findViewById(2131296540));
        this.mText_Light_Row1_Col2 = ((TextView)findViewById(2131296541));
        this.mText_Light_Row1_Col3 = ((TextView)findViewById(2131296542));
        this.mTableRow2_Light = ((TableRow)findViewById(2131296543));
        this.mTableRow2_Light.setVisibility(0);
        this.mText_Light_Row2_Col1 = ((TextView)findViewById(2131296544));
        this.mText_Light_Row2_Col2 = ((TextView)findViewById(2131296545));
        this.mText_Light_Row2_Col3 = ((TextView)findViewById(2131296546));
        this.mTableRow3_Light = ((TableRow)findViewById(2131296547));
        this.mTableRow3_Light.setVisibility(0);
        this.mText_Light_Row3_Col1 = ((TextView)findViewById(2131296548));
        this.mText_Light_Row3_Col2 = ((TextView)findViewById(2131296549));
        this.mText_Light_Row3_Col3 = ((TextView)findViewById(2131296550));
        this.mTableRow4_Light = ((TableRow)findViewById(2131296551));
        this.mTableRow4_Light.setVisibility(0);
        this.mText_Light_Row4_Col1 = ((TextView)findViewById(2131296552));
        this.mText_Light_Row4_Col2 = ((TextView)findViewById(2131296553));
        this.mText_Light_Row4_Col3 = ((TextView)findViewById(2131296554));
      }
      if (this.mTestList[0].mID != this.TESTITEM_ID_PROXIMITY) {
        break label1361;
      }
      if (!this.mIsProximityOffsetTest) {
        break label1350;
      }
      this.mText_Proximity_Row1_Col1.setText("PROXIMIT");
    }
    label1338:
    label1350:
    label1361:
    while (this.mTestList[0].mID != this.TESTITEM_ID_LIGHT)
    {
      return;
      this.mHandler.sendEmptyMessageDelayed(this.WHAT_EXIT, 1500L);
      return;
      this.mTableRow6_Proximity.setVisibility(8);
      break;
      this.mText_Proximity_Row2_Col1.setText("PROXIMIT");
      return;
    }
    this.mText_Light_Row1_Col1.setText("LIGHTEST");
  }
  
  private void sensorReady()
  {
    FtUtil.log_d(this.CLASS_NAME, "sensorReady", null);
    if (this.mTestList[0].mID == this.TESTITEM_ID_PROXIMITY) {
      if (this.mIsProximityOffsetTest)
      {
        this.mTestList[0].mStatus = this.TESTITEM_STATUS_OFFSET;
        this.mText_Proximity_Row1_Col2.setText("Offset");
      }
    }
    while (this.mTestList[0].mID != this.TESTITEM_ID_LIGHT)
    {
      return;
      this.mTestList[0].mStatus = this.TESTITEM_STATUS_PENDING;
      this.mText_Proximity_Row2_Col2.setText("Pending");
      return;
    }
    this.mTestList[0].mStatus = this.TESTITEM_STATUS_PENDING;
    this.mText_Light_Row1_Col2.setText("Pending");
  }
  
  private void setRelease()
  {
    if (Support.TestCase.getEnabled("IS_PROXIMITY_TEST_MOTOR_FEEDBACK")) {
      this.mModuleDevice.stopVibrate();
    }
    this.mLinearBackground.setBackgroundColor(-1);
  }
  
  private void setUIRate()
  {
    FtUtil.log_d(this.CLASS_NAME, "setUIRate", "rate : " + this.mUIRate);
    if ((this.mUIRate != 0.0F) && (this.mUIRate != 1.0F))
    {
      this.mText_Title = ((TextView)findViewById(2131296512));
      this.mText_Title.setTextSize(this.mText_Title.getTextSize() * this.mUIRate);
      if (this.mIsTestProximity)
      {
        if (this.mIsProximityOffsetTest)
        {
          this.mText_Proximity_Row1_Col1.setTextSize(0, this.mText_Proximity_Row1_Col1.getTextSize() * this.mUIRate);
          this.mText_Proximity_Row1_Col2.setTextSize(0, this.mText_Proximity_Row1_Col2.getTextSize() * this.mUIRate);
          this.mText_Proximity_Row1_Col3.setTextSize(0, this.mText_Proximity_Row1_Col3.getTextSize() * this.mUIRate);
          this.mButton_Proximity_Offset.setTextSize(0, this.mButton_Proximity_Offset.getTextSize() * this.mUIRate);
        }
        this.mText_Proximity_Row2_Col1.setTextSize(0, this.mText_Proximity_Row2_Col1.getTextSize() * this.mUIRate);
        this.mText_Proximity_Row2_Col2.setTextSize(0, this.mText_Proximity_Row2_Col2.getTextSize() * this.mUIRate);
        this.mText_Proximity_Row2_Col3.setTextSize(0, this.mText_Proximity_Row2_Col3.getTextSize() * this.mUIRate);
        this.mText_Proximity_Row3_Col1.setTextSize(0, this.mText_Proximity_Row3_Col1.getTextSize() * this.mUIRate);
        this.mText_Proximity_Row3_Col2.setTextSize(0, this.mText_Proximity_Row3_Col2.getTextSize() * this.mUIRate);
        this.mText_Proximity_Row3_Col3.setTextSize(0, this.mText_Proximity_Row3_Col3.getTextSize() * this.mUIRate);
        this.mText_Proximity_Row4_Col1.setTextSize(0, this.mText_Proximity_Row4_Col1.getTextSize() * this.mUIRate);
        this.mText_Proximity_Row4_Col2.setTextSize(0, this.mText_Proximity_Row4_Col2.getTextSize() * this.mUIRate);
        this.mText_Proximity_Row4_Col3.setTextSize(0, this.mText_Proximity_Row4_Col3.getTextSize() * this.mUIRate);
        this.mText_Proximity_Row5_Col1.setTextSize(0, this.mText_Proximity_Row5_Col1.getTextSize() * this.mUIRate);
        this.mText_Proximity_Row5_Col2.setTextSize(0, this.mText_Proximity_Row5_Col2.getTextSize() * this.mUIRate);
        this.mText_Proximity_Row5_Col3.setTextSize(0, this.mText_Proximity_Row5_Col3.getTextSize() * this.mUIRate);
        if (this.FACTORY_TEST_APP >= 18)
        {
          this.mText_Proximity_Row6_Col1.setTextSize(0, this.mText_Proximity_Row6_Col1.getTextSize() * this.mUIRate);
          this.mText_Proximity_Row6_Col2.setTextSize(0, this.mText_Proximity_Row6_Col2.getTextSize() * this.mUIRate);
          this.mText_Proximity_Row6_Col3.setTextSize(0, this.mText_Proximity_Row6_Col3.getTextSize() * this.mUIRate);
        }
      }
      if (this.mIsTestLight)
      {
        this.mText_Dummy_Row1_Col1 = ((TextView)findViewById(2131296538));
        this.mText_Dummy_Row1_Col1.setTextSize(0, this.mText_Dummy_Row1_Col1.getTextSize() * this.mUIRate);
        this.mText_Light_Row1_Col1.setTextSize(0, this.mText_Light_Row1_Col1.getTextSize() * this.mUIRate);
        this.mText_Light_Row1_Col2.setTextSize(0, this.mText_Light_Row1_Col2.getTextSize() * this.mUIRate);
        this.mText_Light_Row1_Col3.setTextSize(0, this.mText_Light_Row1_Col3.getTextSize() * this.mUIRate);
        this.mText_Light_Row2_Col1.setTextSize(0, this.mText_Light_Row2_Col1.getTextSize() * this.mUIRate);
        this.mText_Light_Row2_Col2.setTextSize(0, this.mText_Light_Row2_Col2.getTextSize() * this.mUIRate);
        this.mText_Light_Row2_Col3.setTextSize(0, this.mText_Light_Row2_Col3.getTextSize() * this.mUIRate);
        this.mText_Light_Row3_Col1.setTextSize(0, this.mText_Light_Row3_Col1.getTextSize() * this.mUIRate);
        this.mText_Light_Row3_Col2.setTextSize(0, this.mText_Light_Row3_Col2.getTextSize() * this.mUIRate);
        this.mText_Light_Row3_Col3.setTextSize(0, this.mText_Light_Row3_Col3.getTextSize() * this.mUIRate);
        this.mText_Light_Row4_Col1.setTextSize(0, this.mText_Light_Row4_Col1.getTextSize() * this.mUIRate);
        this.mText_Light_Row4_Col2.setTextSize(0, this.mText_Light_Row4_Col2.getTextSize() * this.mUIRate);
        this.mText_Light_Row4_Col3.setTextSize(0, this.mText_Light_Row4_Col3.getTextSize() * this.mUIRate);
      }
    }
  }
  
  private void setWidthCol3()
  {
    if (this.mIsTestProximity)
    {
      this.mText_Proximity_Row4_Col3.setText("pass");
      this.mText_Proximity_Row4_Col3.setTextColor(getResources().getColor(2131099678));
    }
    if ((this.mIsTestLight) && (!this.mIsTestProximity))
    {
      this.mText_Light_Row3_Col3.setText("pass");
      this.mText_Light_Row3_Col3.setTextColor(getResources().getColor(2131099678));
    }
  }
  
  private void setWorking()
  {
    if (Support.TestCase.getEnabled("IS_PROXIMITY_TEST_MOTOR_FEEDBACK")) {
      this.mModuleDevice.startVibrate();
    }
    this.mLinearBackground.setBackgroundColor(-16711936);
  }
  
  private void startTest()
  {
    String[] arrayOfString4;
    if (this.mTestList[this.mTestListIndex].mID == this.TESTITEM_ID_PROXIMITY)
    {
      if (this.mIsProximityOffsetTest)
      {
        String[] arrayOfString5 = this.mModuleSensor.getData(this.SENSOR_ID_PROXIMITY_ADC);
        String[] arrayOfString6 = this.mModuleSensor.getData(this.SENSOR_ID_PROXIMITY_OFFSET);
        if ((arrayOfString5 == null) || (arrayOfString6 == null)) {
          break label560;
        }
        this.mProximityOffsetValue = arrayOfString6[2];
        this.mProximityThresholdValue = arrayOfString6[3];
        this.mText_Proximity_Row3_Col1.setText("ADC : " + arrayOfString5[2]);
        this.mText_Proximity_Row4_Col1.setText("Offset : " + this.mProximityOffsetValue + "," + this.mProximityThresholdValue);
        FtUtil.log_e(this.CLASS_NAME, "startTest", "Offset : " + this.mProximityOffsetValue + "," + this.mProximityThresholdValue);
        if ((this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_OFFSET) && (this.mTestList[this.mTestListIndex].mIsPass))
        {
          this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_PENDING;
          this.mText_Proximity_Row1_Col3.setText("Pass");
          this.mText_Proximity_Row1_Col3.setTextColor(-16776961);
          this.mLinear_Proximity_Button.setVisibility(8);
          this.mText_Proximity_Row2_Col2.setText("Pending");
        }
      }
      arrayOfString4 = this.mModuleSensor.getData(this.SENSOR_ID_PROXIMITY);
      if (arrayOfString4 != null) {
        if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_RELEASE)
        {
          this.mCurrValue = converterProximityValue(Float.valueOf(arrayOfString4[2]).floatValue());
          if ((this.mPrevValue == this.RANGE_PROXIMITY_RELEASE) && (this.mCurrValue == this.RANGE_PROXIMITY_DETECT))
          {
            this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_WORKING;
            setWorking();
            this.mText_Proximity_Row4_Col2.setText("Working");
            this.mText_Proximity_Row4_Col3.setText("Pass");
            this.mText_Proximity_Row4_Col3.setTextColor(-16776961);
            this.mTestList[this.mTestListIndex].mIsPass = true;
          }
          this.mPrevValue = this.mCurrValue;
          label438:
          if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_FINISH)
          {
            this.mPrevValue = -1.0F;
            this.mCurrValue = -1.0F;
            if (this.mIsTestLight) {
              setRelease();
            }
            if (this.mTestListIndex >= -1 + this.mTestList.length) {
              break label2051;
            }
            this.mTestListIndex = (1 + this.mTestListIndex);
            if (this.mTestList[this.mTestListIndex].mID == this.TESTITEM_ID_LIGHT)
            {
              this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_PENDING;
              this.mText_Light_Row1_Col1.setText("LIGHTEST");
              this.mText_Light_Row1_Col2.setText("Pending");
            }
          }
        }
      }
    }
    label560:
    label729:
    while ((this.mTestListIndex != -1 + this.mTestList.length) || (!this.FLAG_EXIT))
    {
      return;
      this.mText_Proximity_Row3_Col1.setText("ADC : NONE");
      this.mText_Proximity_Row4_Col1.setText("Offset : NONE");
      break;
      if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_WORKING)
      {
        this.mCurrValue = converterProximityValue(Float.valueOf(arrayOfString4[2]).floatValue());
        if ((this.mPrevValue == this.RANGE_PROXIMITY_DETECT) && (this.mCurrValue == this.RANGE_PROXIMITY_RELEASE))
        {
          if (this.FACTORY_TEST_APP < 18) {
            break label729;
          }
          this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_RELEASE_SECOND;
          setRelease();
        }
        for (;;)
        {
          this.mText_Proximity_Row5_Col2.setText("Release");
          this.mText_Proximity_Row5_Col3.setText("Pass");
          this.mText_Proximity_Row5_Col3.setTextColor(-16776961);
          this.mTestList[this.mTestListIndex].mIsPass = true;
          this.mPrevValue = this.mCurrValue;
          break;
          this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_FINISH;
        }
      }
      if ((this.mTestList[this.mTestListIndex].mStatus != this.TESTITEM_STATUS_RELEASE_SECOND) || (this.FACTORY_TEST_APP < 18)) {
        break label438;
      }
      this.mCurrValue = converterProximityValue(Float.valueOf(arrayOfString4[2]).floatValue());
      if ((this.mPrevValue == this.RANGE_PROXIMITY_RELEASE) && (this.mCurrValue == this.RANGE_PROXIMITY_DETECT))
      {
        this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_FINISH;
        setWorking();
        this.mText_Proximity_Row6_Col2.setText("Working");
        this.mText_Proximity_Row6_Col3.setText("Pass");
        this.mText_Proximity_Row6_Col3.setTextColor(-16776961);
        this.mTestList[this.mTestListIndex].mIsPass = true;
      }
      this.mPrevValue = this.mCurrValue;
      break label438;
      if (this.mTestList[this.mTestListIndex].mStatus != this.TESTITEM_STATUS_RELEASE) {
        break label438;
      }
      this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_FINISH;
      this.mText_Proximity_Row4_Col2.setText("Working");
      this.mText_Proximity_Row4_Col3.setText("Fail");
      this.mText_Proximity_Row4_Col3.setTextColor(-65536);
      this.mText_Proximity_Row5_Col2.setText("Release");
      this.mText_Proximity_Row5_Col3.setText("Fail");
      this.mText_Proximity_Row5_Col3.setTextColor(-65536);
      if (this.FACTORY_TEST_APP >= 18)
      {
        this.mText_Proximity_Row6_Col2.setText("Release");
        this.mText_Proximity_Row6_Col3.setText("Fail");
        this.mText_Proximity_Row6_Col3.setTextColor(-65536);
      }
      this.mTestList[this.mTestListIndex].mIsPass = false;
      break label438;
      if (this.mTestList[this.mTestListIndex].mID != this.TESTITEM_ID_LIGHT) {
        break label438;
      }
      SystemProperties.get("ro.product.model", "Unknown");
      String[] arrayOfString1 = this.mModuleSensor.getData(this.SENSOR_ID_LIGHT_ADC);
      String[] arrayOfString2 = this.mModuleSensor.getData(this.SENSOR_ID_LIGHT_LUX);
      if (arrayOfString1 != null) {
        this.mText_Light_Row3_Col1.setText("ADC : " + arrayOfString1[2]);
      }
      for (;;)
      {
        if (this.mLightLuxOnly)
        {
          FtUtil.log_e(this.CLASS_NAME, "startTest", "grande source");
          this.mText_Light_Row3_Col1.setVisibility(4);
          String[] arrayOfString3 = this.mModuleSensor.getData(this.SENSOR_ID_LIGHT_ADC);
          if (arrayOfString3 != null)
          {
            this.mText_Light_Row4_Col1.setText("LUX : " + arrayOfString3[2]);
            if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_PENDING)
            {
              this.mCurrValue = Float.valueOf(arrayOfString3[2]).floatValue();
              if (this.mCurrValue < this.RANGE_LIGHT_BRIGHT) {
                break;
              }
              FtUtil.log_e(this.CLASS_NAME, "startTest", "TESTITEM_STATUS_PENDING => TESTITEM_STATUS_RELEASE");
              this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_RELEASE;
              this.mText_Light_Row2_Col2.setText("Release");
              break;
              this.mText_Light_Row3_Col1.setText("ADC : NONE");
              continue;
            }
            if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_RELEASE)
            {
              this.mCurrValue = Float.valueOf(arrayOfString3[2]).floatValue();
              if (this.mCurrValue > this.RANGE_LIGHT_DARK) {
                break;
              }
              FtUtil.log_e(this.CLASS_NAME, "startTest", "TESTITEM_STATUS_RELEASE => TESTITEM_STATUS_WORKING");
              this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_WORKING;
              setWorking();
              this.mText_Light_Row3_Col2.setText("Working");
              this.mText_Light_Row3_Col3.setText("Pass");
              this.mText_Light_Row3_Col3.setTextColor(-16776961);
              this.mTestList[this.mTestListIndex].mIsPass = true;
              break;
            }
            if (this.mTestList[this.mTestListIndex].mStatus != this.TESTITEM_STATUS_WORKING) {
              break;
            }
            this.mCurrValue = Float.valueOf(arrayOfString3[2]).floatValue();
            if (this.mCurrValue < this.RANGE_LIGHT_BRIGHT) {
              break;
            }
            FtUtil.log_e(this.CLASS_NAME, "startTest", "TESTITEM_STATUS_WORKING => TESTITEM_STATUS_FINISH");
            this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_FINISH;
            this.mText_Light_Row4_Col2.setText("Release");
            this.mText_Light_Row4_Col3.setText("Pass");
            this.mText_Light_Row4_Col3.setTextColor(-16776961);
            this.mTestList[this.mTestListIndex].mIsPass = true;
            break;
          }
          this.mText_Light_Row4_Col1.setText("LUX : NONE");
          break;
        }
      }
      if (arrayOfString2 != null)
      {
        this.mText_Light_Row4_Col1.setText("LUX : " + arrayOfString2[2]);
        if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_PENDING)
        {
          this.mCurrValue = Float.valueOf(arrayOfString2[2]).floatValue();
          if (this.mCurrValue < this.RANGE_LIGHT_BRIGHT) {
            break label438;
          }
          FtUtil.log_e(this.CLASS_NAME, "startTest", "TESTITEM_STATUS_PENDING => TESTITEM_STATUS_RELEASE");
          this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_RELEASE;
          this.mText_Light_Row2_Col2.setText("Release");
          break label438;
        }
        if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_RELEASE)
        {
          this.mCurrValue = Float.valueOf(arrayOfString2[2]).floatValue();
          if (this.mCurrValue > this.RANGE_LIGHT_DARK) {
            break label438;
          }
          FtUtil.log_e(this.CLASS_NAME, "startTest", "TESTITEM_STATUS_RELEASE => TESTITEM_STATUS_WORKING");
          this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_WORKING;
          setWorking();
          this.mText_Light_Row3_Col2.setText("Working");
          this.mText_Light_Row3_Col3.setText("Pass");
          this.mText_Light_Row3_Col3.setTextColor(-16776961);
          this.mTestList[this.mTestListIndex].mIsPass = true;
          break label438;
        }
        if (this.mTestList[this.mTestListIndex].mStatus != this.TESTITEM_STATUS_WORKING) {
          break label438;
        }
        this.mCurrValue = Float.valueOf(arrayOfString2[2]).floatValue();
        if (this.mCurrValue < this.RANGE_LIGHT_BRIGHT) {
          break label438;
        }
        FtUtil.log_e(this.CLASS_NAME, "startTest", "TESTITEM_STATUS_WORKING => TESTITEM_STATUS_FINISH");
        this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_FINISH;
        this.mText_Light_Row4_Col2.setText("Release");
        this.mText_Light_Row4_Col3.setText("Pass");
        this.mText_Light_Row4_Col3.setTextColor(-16776961);
        this.mTestList[this.mTestListIndex].mIsPass = true;
        break label438;
      }
      this.mText_Light_Row4_Col1.setText("LUX : NONE");
      if (this.mTestList[this.mTestListIndex].mStatus != this.TESTITEM_STATUS_RELEASE) {
        break label438;
      }
      this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_FINISH;
      this.mText_Light_Row3_Col2.setText("Working");
      this.mText_Light_Row3_Col3.setText("Fail");
      this.mText_Light_Row3_Col3.setTextColor(-65536);
      this.mText_Light_Row4_Col2.setText("Release");
      this.mText_Light_Row4_Col3.setText("Fail");
      this.mText_Light_Row4_Col3.setTextColor(-65536);
      this.mTestList[this.mTestListIndex].mIsPass = false;
      break label438;
    }
    label2051:
    FtUtil.log_e(this.CLASS_NAME, "startTest", "mHandler.sendEmptyMessageDelayed(WHAT_EXIT, DELAY_EXIT)");
    this.mHandler.sendEmptyMessageDelayed(this.WHAT_EXIT, 1000L);
    this.FLAG_EXIT = false;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903083);
    initialize();
    setWidthCol3();
    setUIRate();
    this.mModuleSensor.SensorOn(this.mSenserID, this.mHandler, 100, this.mLogLevel);
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
      if ((this.mIsTestProximity) && (this.mTestList != null) && (this.mTestList.length > 0)) {
        if (this.mTestList[0].mStatus == this.TESTITEM_STATUS_PENDING)
        {
          this.mTestList[0].mStatus = this.TESTITEM_STATUS_RELEASE;
          this.mText_Proximity_Row3_Col2.setText("Release");
        }
        else if (this.mTestList[0].mStatus == this.TESTITEM_STATUS_OFFSET)
        {
          Toast.makeText(this, "Please set the Offset", 0).show();
        }
      }
    }
  }
  
  protected void onPause()
  {
    super.onPause();
    this.mModuleSensor.SensorOff();
    this.mModuleDevice.stopVibrate();
  }
  
  private class TestItem
  {
    public int mID;
    public boolean mIsPass;
    public int mStatus;
    
    public TestItem(int paramInt)
    {
      this.mID = paramInt;
      this.mStatus = UIProximityLight.this.TESTITEM_STATUS_NONE;
      this.mIsPass = false;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIProximityLight
 * JD-Core Version:    0.7.1
 */