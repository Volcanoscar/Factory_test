package com.sec.factory.app.ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.app.factorytest.FactoryTestManager.FactoryTestItemInfo;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.modules.SensorDeviceInfo;
import com.sec.factory.modules.SensorNotification;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;

public class UIGeomagneticGyro
  extends UIBase
{
  public static final int WHAT_GEOMAGNETIC_FAIL = 4 + SensorNotification.WHAT_NOTI_SENSOR_MAX;
  public static final int WHAT_GEOMAGNETIC_PASS;
  public static final int WHAT_GYROSCOPE_FAIL;
  public static final int WHAT_GYROSCOPE_PASS = 1 + SensorNotification.WHAT_NOTI_SENSOR_MAX;
  private final int TESTINFO_ARRAY_INDEX_GEOMAGNETIC;
  private final int TESTINFO_ARRAY_INDEX_GYROSCOPE;
  private final String TESTINFO_IC_ALPS = "ALPS";
  private final String TESTINFO_IC_ASAHI = "ASAHI";
  private final String TESTINFO_IC_BOSCH = "BOSCH";
  private final String TESTINFO_IC_INVENSENSE = "INVENSENSE";
  private final String TESTINFO_IC_STMICRO_SMARTPHONE = "STMICRO_SMARTPHONE";
  private final String TESTINFO_IC_STMICRO_TABLET = "STMICRO_TABLET";
  private final String TESTINFO_IC_YAMAHA = "YAMAHA";
  private int dummy = 0;
  private String mFeature_Geomagnetic;
  private String mFeature_Gyroscope;
  FragmentGeomagneticAlps mFragment_geomagnetic_alps;
  FragmentGeomagneticAsahi mFragment_geomagnetic_asahi;
  FragmentGeomagneticBosch mFragment_geomagnetic_bosch;
  FragmentGeomagneticYamaha mFragment_geomagnetic_yamaha;
  FragmentGyroscopeInvensense mFragment_gyroscope_invensense;
  FragmentGyroscopeSTMicroSmartphone mFragment_gyroscope_stmicro_smartphone;
  FragmentGyroscopeSTMicroTablet mFragment_gyroscope_stmicro_tablet;
  private Handler mHandler;
  private LinearLayout mLinear_Bottom;
  private LinearLayout mLinear_Top;
  private FactoryTestManager.FactoryTestItemInfo[] mList_TestItem;
  private ModuleSensor mModuleSensor;
  private int[] mSenserID;
  private int mSensorID_Geomagnetic_ADC;
  private int mSensorID_Geomagnetic_ADC2;
  private int mSensorID_Geomagnetic_DAC;
  private int mSensorID_Geomagnetic_Initialized;
  private int mSensorID_Geomagnetic_Released;
  private int mSensorID_Geomagnetic_Self;
  private int mSensorID_Geomagnetic_Status;
  private int mSensorID_Geomagnetic_Temperature;
  private int mSensorID_Gyrocope_Power;
  private int mSensorID_Gyrocope_Self;
  private int mSensorID_Gyrocope_Temperature;
  private int mSensorID_None;
  private TestInfo[] mTestInfoArray;
  private View mView_Separator;
  
  static
  {
    WHAT_GYROSCOPE_FAIL = 2 + SensorNotification.WHAT_NOTI_SENSOR_MAX;
    WHAT_GEOMAGNETIC_PASS = 3 + SensorNotification.WHAT_NOTI_SENSOR_MAX;
  }
  
  public UIGeomagneticGyro()
  {
    super("UIGeomagneticGyro", 37);
    int i = this.dummy;
    this.dummy = (i + 1);
    this.TESTINFO_ARRAY_INDEX_GYROSCOPE = i;
    int j = this.dummy;
    this.dummy = (j + 1);
    this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC = j;
    this.mTestInfoArray = new TestInfo[this.dummy];
    this.mSenserID = null;
    this.mSensorID_None = (-1 + ModuleSensor.ID_SCOPE_MIN);
    this.mSensorID_Gyrocope_Power = this.mSensorID_None;
    this.mSensorID_Gyrocope_Temperature = this.mSensorID_None;
    this.mSensorID_Gyrocope_Self = this.mSensorID_None;
    this.mSensorID_Geomagnetic_Initialized = this.mSensorID_None;
    this.mSensorID_Geomagnetic_Status = this.mSensorID_None;
    this.mSensorID_Geomagnetic_Temperature = this.mSensorID_None;
    this.mSensorID_Geomagnetic_DAC = this.mSensorID_None;
    this.mSensorID_Geomagnetic_ADC = this.mSensorID_None;
    this.mSensorID_Geomagnetic_ADC2 = this.mSensorID_None;
    this.mSensorID_Geomagnetic_Self = this.mSensorID_None;
    this.mSensorID_Geomagnetic_Released = this.mSensorID_None;
    this.mHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what == SensorNotification.WHAT_NOTI_SENSOR_READY)
        {
          FtUtil.log_d(UIGeomagneticGyro.this.CLASS_NAME, "mHandler.handleMessage", "WHAT_NOTI_SENSOR_READY");
          UIGeomagneticGyro.this.update();
          return;
        }
        UIGeomagneticGyro.this.checkResult(paramAnonymousMessage.what);
      }
    };
  }
  
  private void GyroTestFail()
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle("Gyro Test Fail");
    localBuilder.setMessage("Do Not Move & Re-Test");
    EmptyListener localEmptyListener = new EmptyListener();
    AlertDialog localAlertDialog = localBuilder.create();
    localAlertDialog.setOnDismissListener(localEmptyListener);
    localAlertDialog.show();
  }
  
  private void checkResult(int paramInt)
  {
    if (paramInt == WHAT_GYROSCOPE_PASS)
    {
      FtUtil.log_e(this.CLASS_NAME, "checkResult", "WHAT_GYROSCOPE_PASS");
      this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mIsTestComplete = true;
      this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mIsTestPass = true;
      writeNV(this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mID, true);
    }
    int i;
    for (;;)
    {
      i = 1;
      for (int j = 0; j < this.mTestInfoArray.length; j++) {
        if ((this.mTestInfoArray[j] != null) && (!this.mTestInfoArray[j].mIsTestComplete)) {
          i = 0;
        }
      }
      if (paramInt == WHAT_GYROSCOPE_FAIL)
      {
        FtUtil.log_e(this.CLASS_NAME, "checkResult", "WHAT_GYROSCOPE_FAIL");
        this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mIsTestComplete = true;
        this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mIsTestPass = false;
        writeNV(this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mID, false);
        GyroTestFail();
      }
      else if (paramInt == WHAT_GEOMAGNETIC_PASS)
      {
        FtUtil.log_e(this.CLASS_NAME, "checkResult", "WHAT_GEOMAGNETIC_PASS");
        this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIsTestComplete = true;
        this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIsTestPass = true;
        writeNV(this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mID, true);
      }
      else if (paramInt == WHAT_GEOMAGNETIC_FAIL)
      {
        FtUtil.log_e(this.CLASS_NAME, "checkResult", "WHAT_GEOMAGNETIC_FAIL");
        this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIsTestComplete = true;
        this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIsTestPass = false;
        writeNV(this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mID, false);
      }
    }
    if (i != 0)
    {
      int k = 1;
      FtUtil.log_e(this.CLASS_NAME, "checkResult", "finish");
      for (int m = 0; m < this.mTestInfoArray.length; m++) {
        if ((this.mTestInfoArray[m] != null) && (!this.mTestInfoArray[m].mIsTestPass)) {
          k = 0;
        }
      }
      if (k != 0)
      {
        setTestResultWithoutNV((byte)37, (byte)80);
        finishOnPassWithTimer();
      }
    }
  }
  
  private FragmentGeomagneticAlps.DataGeomagneticAlps getDataGeomagneticAlps()
  {
    FtUtil.log_e(this.CLASS_NAME, "getDataGeomagneticAlps", null);
    FragmentGeomagneticAlps.DataGeomagneticAlps localDataGeomagneticAlps = new FragmentGeomagneticAlps.DataGeomagneticAlps();
    String[] arrayOfString2;
    if (this.mSensorID_None < this.mSensorID_Geomagnetic_Initialized)
    {
      String[] arrayOfString3 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_Initialized);
      localDataGeomagneticAlps.mIsDisplay_Initialized = true;
      if ((arrayOfString3 != null) && (arrayOfString3.length > 2)) {
        localDataGeomagneticAlps.mInitialized = arrayOfString3[2];
      }
    }
    else if (this.mSensorID_None < this.mSensorID_Geomagnetic_Status)
    {
      arrayOfString2 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_Status);
      localDataGeomagneticAlps.mIsDisplay_Status = true;
      if ((arrayOfString2 == null) || (arrayOfString2.length <= 2)) {
        break label197;
      }
      localDataGeomagneticAlps.mStatus_Result = arrayOfString2[1];
    }
    for (localDataGeomagneticAlps.mStatus = arrayOfString2[2];; localDataGeomagneticAlps.mStatus = "-1")
    {
      if (this.mSensorID_None < this.mSensorID_Geomagnetic_ADC)
      {
        String[] arrayOfString1 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_ADC);
        localDataGeomagneticAlps.mIsDisplay_ADC = true;
        if ((arrayOfString1 == null) || (arrayOfString1.length <= 4)) {
          break label214;
        }
        localDataGeomagneticAlps.mADC_Result = arrayOfString1[1];
        localDataGeomagneticAlps.mADC_X = arrayOfString1[2];
        localDataGeomagneticAlps.mADC_Y = arrayOfString1[3];
        localDataGeomagneticAlps.mADC_Z = arrayOfString1[4];
      }
      return localDataGeomagneticAlps;
      localDataGeomagneticAlps.mInitialized = "-1";
      break;
      label197:
      localDataGeomagneticAlps.mStatus_Result = "-1";
    }
    label214:
    localDataGeomagneticAlps.mADC_Result = "-1";
    localDataGeomagneticAlps.mADC_X = "-1";
    localDataGeomagneticAlps.mADC_Y = "-1";
    localDataGeomagneticAlps.mADC_Z = "-1";
    return localDataGeomagneticAlps;
  }
  
  private FragmentGeomagneticAsahi.DataGeomagneticAsahi getDataGeomagneticAsahi()
  {
    FtUtil.log_e(this.CLASS_NAME, "getDataGeomagneticAsahi", null);
    FragmentGeomagneticAsahi.DataGeomagneticAsahi localDataGeomagneticAsahi = new FragmentGeomagneticAsahi.DataGeomagneticAsahi();
    label158:
    label297:
    label495:
    String[] arrayOfString1;
    if (this.mSensorID_None < this.mSensorID_Geomagnetic_Initialized)
    {
      String[] arrayOfString5 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_Initialized);
      localDataGeomagneticAsahi.mIsDisplay_Initialized = true;
      if ((arrayOfString5 != null) && (arrayOfString5.length > 2))
      {
        localDataGeomagneticAsahi.mInitialized = arrayOfString5[2];
        FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mInitialized : " + localDataGeomagneticAsahi.mInitialized);
      }
    }
    else
    {
      if (this.mSensorID_None < this.mSensorID_Geomagnetic_Temperature)
      {
        String[] arrayOfString4 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_Temperature);
        localDataGeomagneticAsahi.mIsDisplay_Temperature = true;
        if ((arrayOfString4 == null) || (arrayOfString4.length <= 2)) {
          break label837;
        }
        localDataGeomagneticAsahi.mTemperature_Result = arrayOfString4[1];
        localDataGeomagneticAsahi.mTemperature = arrayOfString4[2];
        FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mTemperature_Result : " + localDataGeomagneticAsahi.mTemperature_Result);
        FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mTemperature : " + localDataGeomagneticAsahi.mTemperature);
      }
      if (this.mSensorID_None < this.mSensorID_Geomagnetic_Self)
      {
        String[] arrayOfString3 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_Self);
        localDataGeomagneticAsahi.mIsDisplay_Self = true;
        if ((arrayOfString3 == null) || (arrayOfString3.length <= 4)) {
          break label854;
        }
        localDataGeomagneticAsahi.mSelf_Result = arrayOfString3[1];
        localDataGeomagneticAsahi.mSelf_X = arrayOfString3[2];
        localDataGeomagneticAsahi.mSelf_Y = arrayOfString3[3];
        localDataGeomagneticAsahi.mSelf_Z = arrayOfString3[4];
        FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mSelf_Result : " + localDataGeomagneticAsahi.mSelf_Result);
        FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mSelf_X : " + localDataGeomagneticAsahi.mSelf_X);
        FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mSelf_Y : " + localDataGeomagneticAsahi.mSelf_Y);
        FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mSelf_Z : " + localDataGeomagneticAsahi.mSelf_Z);
      }
      if (this.mSensorID_None < this.mSensorID_Geomagnetic_DAC)
      {
        String[] arrayOfString2 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_DAC);
        localDataGeomagneticAsahi.mIsDisplay_DAC = true;
        if ((arrayOfString2 == null) || (arrayOfString2.length <= 4)) {
          break label885;
        }
        localDataGeomagneticAsahi.mDAC_Result = arrayOfString2[1];
        localDataGeomagneticAsahi.mDAC_X = arrayOfString2[2];
        localDataGeomagneticAsahi.mDAC_Y = arrayOfString2[3];
        localDataGeomagneticAsahi.mDAC_Z = arrayOfString2[4];
        FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mDAC_Result : " + localDataGeomagneticAsahi.mDAC_Result);
        FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mDAC_X : " + localDataGeomagneticAsahi.mDAC_X);
        FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mDAC_Y : " + localDataGeomagneticAsahi.mDAC_Y);
        FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mDAC_Z : " + localDataGeomagneticAsahi.mDAC_Z);
      }
      if (this.mSensorID_None < this.mSensorID_Geomagnetic_ADC)
      {
        arrayOfString1 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_ADC);
        localDataGeomagneticAsahi.mIsDisplay_ADC = true;
        if ((arrayOfString1 == null) || (arrayOfString1.length <= 4)) {
          break label916;
        }
        localDataGeomagneticAsahi.mADC_Result = arrayOfString1[1];
        localDataGeomagneticAsahi.mADC_X = arrayOfString1[2];
        localDataGeomagneticAsahi.mADC_Y = arrayOfString1[3];
      }
    }
    for (localDataGeomagneticAsahi.mADC_Z = arrayOfString1[4];; localDataGeomagneticAsahi.mADC_Z = "-1")
    {
      FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mADC_Result : " + localDataGeomagneticAsahi.mADC_Result);
      FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mADC_X : " + localDataGeomagneticAsahi.mADC_X);
      FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mADC_Y : " + localDataGeomagneticAsahi.mADC_Y);
      FtUtil.log_d(this.CLASS_NAME, "getDataGeomagneticAsahi", "data.mADC_Z : " + localDataGeomagneticAsahi.mADC_Z);
      return localDataGeomagneticAsahi;
      localDataGeomagneticAsahi.mInitialized = "-1";
      break;
      label837:
      localDataGeomagneticAsahi.mTemperature_Result = "-1";
      localDataGeomagneticAsahi.mTemperature = "-1";
      break label158;
      label854:
      localDataGeomagneticAsahi.mSelf_Result = "-1";
      localDataGeomagneticAsahi.mSelf_X = "-1";
      localDataGeomagneticAsahi.mSelf_Y = "-1";
      localDataGeomagneticAsahi.mSelf_Z = "-1";
      break label297;
      label885:
      localDataGeomagneticAsahi.mDAC_Result = "-1";
      localDataGeomagneticAsahi.mDAC_X = "-1";
      localDataGeomagneticAsahi.mDAC_Y = "-1";
      localDataGeomagneticAsahi.mDAC_Z = "-1";
      break label495;
      label916:
      localDataGeomagneticAsahi.mADC_Result = "-1";
      localDataGeomagneticAsahi.mADC_X = "-1";
      localDataGeomagneticAsahi.mADC_Y = "-1";
    }
  }
  
  private FragmentGeomagneticBosch.DataGeomagneticBosch getDataGeomagneticBosch()
  {
    FtUtil.log_e(this.CLASS_NAME, "getDataGeomagneticBosch", null);
    FragmentGeomagneticBosch.DataGeomagneticBosch localDataGeomagneticBosch = new FragmentGeomagneticBosch.DataGeomagneticBosch();
    if (this.mSensorID_None < this.mSensorID_Geomagnetic_Initialized)
    {
      String[] arrayOfString3 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_Initialized);
      localDataGeomagneticBosch.mIsDisplay_Initialized = true;
      localDataGeomagneticBosch.mInitialized = arrayOfString3[2];
    }
    if (this.mSensorID_None < this.mSensorID_Geomagnetic_Status)
    {
      String[] arrayOfString2 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_Status);
      localDataGeomagneticBosch.mIsDisplay_Self = true;
      localDataGeomagneticBosch.mSelf_Result = arrayOfString2[1];
      localDataGeomagneticBosch.mBmZ = arrayOfString2[2];
    }
    if (this.mSensorID_None < this.mSensorID_Geomagnetic_ADC)
    {
      String[] arrayOfString1 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_ADC);
      localDataGeomagneticBosch.mIsDisplay_ADC = true;
      localDataGeomagneticBosch.mADC_Result = arrayOfString1[1];
      localDataGeomagneticBosch.mADC_X = arrayOfString1[2];
      localDataGeomagneticBosch.mADC_Y = arrayOfString1[3];
      localDataGeomagneticBosch.mADC_Z = arrayOfString1[4];
    }
    return localDataGeomagneticBosch;
  }
  
  private FragmentGeomagneticYamaha.DataGeomagneticYamaha getDataGeomagneticYamaha()
  {
    FtUtil.log_e(this.CLASS_NAME, "getDataGeomagneticYamaha", null);
    FragmentGeomagneticYamaha.DataGeomagneticYamaha localDataGeomagneticYamaha = new FragmentGeomagneticYamaha.DataGeomagneticYamaha();
    label120:
    String[] arrayOfString2;
    if (this.mSensorID_None < this.mSensorID_Geomagnetic_Initialized)
    {
      String[] arrayOfString7 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_Initialized);
      localDataGeomagneticYamaha.mIsDisplay_Initialized = true;
      if ((arrayOfString7 != null) && (arrayOfString7.length > 2)) {
        localDataGeomagneticYamaha.mInitialized = arrayOfString7[2];
      }
    }
    else
    {
      if (this.mSensorID_None < this.mSensorID_Geomagnetic_Status)
      {
        String[] arrayOfString6 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_Status);
        if ((arrayOfString6 == null) || (arrayOfString6.length <= 2)) {
          break label449;
        }
        localDataGeomagneticYamaha.mStatus_Result = arrayOfString6[1];
        localDataGeomagneticYamaha.mStatus = arrayOfString6[2];
      }
      if (this.mSensorID_None < this.mSensorID_Geomagnetic_DAC)
      {
        String[] arrayOfString5 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_DAC);
        localDataGeomagneticYamaha.mIsDisplay_DAC = true;
        if ((arrayOfString5 == null) || (arrayOfString5.length <= 4)) {
          break label466;
        }
        localDataGeomagneticYamaha.mDAC_Result = arrayOfString5[1];
        localDataGeomagneticYamaha.mDAC_X = arrayOfString5[2];
        localDataGeomagneticYamaha.mDAC_Y = arrayOfString5[3];
        localDataGeomagneticYamaha.mDAC_Z = arrayOfString5[4];
      }
      label193:
      if (this.mSensorID_None < this.mSensorID_Geomagnetic_ADC)
      {
        String[] arrayOfString4 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_ADC);
        localDataGeomagneticYamaha.mIsDisplay_ADC = true;
        if ((arrayOfString4 == null) || (arrayOfString4.length <= 4)) {
          break label497;
        }
        localDataGeomagneticYamaha.mADC_Result = arrayOfString4[1];
        localDataGeomagneticYamaha.mADC_X = arrayOfString4[2];
        localDataGeomagneticYamaha.mADC_Y = arrayOfString4[3];
        localDataGeomagneticYamaha.mADC_Z = arrayOfString4[4];
      }
      label266:
      if (this.mSensorID_None < this.mSensorID_Geomagnetic_Self)
      {
        String[] arrayOfString3 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_Self);
        localDataGeomagneticYamaha.mIsDisplay_Self = true;
        if ((arrayOfString3 == null) || (arrayOfString3.length <= 3)) {
          break label528;
        }
        localDataGeomagneticYamaha.mSelf_Result = arrayOfString3[1];
        localDataGeomagneticYamaha.mSelf_X = arrayOfString3[2];
        localDataGeomagneticYamaha.mSelf_Y = arrayOfString3[3];
      }
      label331:
      if (this.mSensorID_None < this.mSensorID_Geomagnetic_ADC2)
      {
        arrayOfString2 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_ADC2);
        localDataGeomagneticYamaha.mIsDisplay_OffsetH = true;
        if ((arrayOfString2 == null) || (arrayOfString2.length <= 4)) {
          break label552;
        }
        localDataGeomagneticYamaha.mOffsetH_Result = arrayOfString2[1];
        localDataGeomagneticYamaha.mOffsetH_X = arrayOfString2[2];
        localDataGeomagneticYamaha.mOffsetH_Y = arrayOfString2[3];
      }
    }
    for (localDataGeomagneticYamaha.mOffsetH_Z = arrayOfString2[4];; localDataGeomagneticYamaha.mOffsetH_Z = "-1")
    {
      if (this.mSensorID_None < this.mSensorID_Geomagnetic_Released)
      {
        String[] arrayOfString1 = this.mModuleSensor.getData(this.mSensorID_Geomagnetic_Released);
        if ((arrayOfString1 == null) || (arrayOfString1.length <= 2)) {
          break label583;
        }
        localDataGeomagneticYamaha.mPowerOff_Result = arrayOfString1[2];
      }
      return localDataGeomagneticYamaha;
      localDataGeomagneticYamaha.mInitialized = "-1";
      break;
      label449:
      localDataGeomagneticYamaha.mStatus_Result = "-1";
      localDataGeomagneticYamaha.mStatus = "-1";
      break label120;
      label466:
      localDataGeomagneticYamaha.mDAC_Result = "-1";
      localDataGeomagneticYamaha.mDAC_X = "-1";
      localDataGeomagneticYamaha.mDAC_Y = "-1";
      localDataGeomagneticYamaha.mDAC_Z = "-1";
      break label193;
      label497:
      localDataGeomagneticYamaha.mADC_Result = "-1";
      localDataGeomagneticYamaha.mADC_X = "-1";
      localDataGeomagneticYamaha.mADC_Y = "-1";
      localDataGeomagneticYamaha.mADC_Z = "-1";
      break label266;
      label528:
      localDataGeomagneticYamaha.mSelf_Result = "-1";
      localDataGeomagneticYamaha.mSelf_X = "-1";
      localDataGeomagneticYamaha.mSelf_Y = "-1";
      break label331;
      label552:
      localDataGeomagneticYamaha.mOffsetH_Result = "-1";
      localDataGeomagneticYamaha.mOffsetH_X = "-1";
      localDataGeomagneticYamaha.mOffsetH_Y = "-1";
    }
    label583:
    localDataGeomagneticYamaha.mPowerOff_Result = "-1";
    return localDataGeomagneticYamaha;
  }
  
  private FragmentGeomagneticYamaha.DataGeomagneticYamaha getDataGeomagneticYamahaSysfs()
  {
    FtUtil.log_e(this.CLASS_NAME, "getDataGeomagneticYamaha", null);
    FragmentGeomagneticYamaha.DataGeomagneticYamaha localDataGeomagneticYamaha = new FragmentGeomagneticYamaha.DataGeomagneticYamaha();
    String str = Support.Kernel.read("GEOMAGNETIC_SENSOR_SELFTEST");
    String[] arrayOfString = null;
    if (str != null) {
      arrayOfString = str.split(",");
    }
    if (arrayOfString != null)
    {
      localDataGeomagneticYamaha.mIsDisplay_Initialized = true;
      if (!arrayOfString[0].equals("0")) {
        break label299;
      }
      localDataGeomagneticYamaha.mInitialized = "1";
      if (!arrayOfString[2].equals("0")) {
        break label309;
      }
      localDataGeomagneticYamaha.mStatus_Result = "OK";
      localDataGeomagneticYamaha.mStatus = arrayOfString[2];
      label94:
      localDataGeomagneticYamaha.mIsDisplay_DAC = true;
      if (!arrayOfString[3].equals("0")) {
        break label326;
      }
      localDataGeomagneticYamaha.mDAC_Result = "OK";
      localDataGeomagneticYamaha.mDAC_X = arrayOfString[4];
      localDataGeomagneticYamaha.mDAC_Y = arrayOfString[5];
      localDataGeomagneticYamaha.mDAC_Z = arrayOfString[6];
      label140:
      localDataGeomagneticYamaha.mIsDisplay_ADC = true;
      if (!arrayOfString[7].equals("0")) {
        break label357;
      }
      localDataGeomagneticYamaha.mADC_Result = "OK";
      localDataGeomagneticYamaha.mADC_X = arrayOfString[8];
      localDataGeomagneticYamaha.mADC_Y = "0";
      localDataGeomagneticYamaha.mADC_Z = "0";
      label187:
      localDataGeomagneticYamaha.mIsDisplay_Self = true;
      if (!arrayOfString[9].equals("0")) {
        break label388;
      }
      localDataGeomagneticYamaha.mSelf_Result = "OK";
      localDataGeomagneticYamaha.mSelf_X = arrayOfString[10];
      localDataGeomagneticYamaha.mSelf_Y = arrayOfString[11];
      label228:
      localDataGeomagneticYamaha.mIsDisplay_OffsetH = true;
      if (!arrayOfString[12].equals("0")) {
        break label412;
      }
      localDataGeomagneticYamaha.mOffsetH_Result = "OK";
      localDataGeomagneticYamaha.mOffsetH_X = arrayOfString[13];
      localDataGeomagneticYamaha.mOffsetH_Y = arrayOfString[14];
    }
    for (localDataGeomagneticYamaha.mOffsetH_Z = arrayOfString[15];; localDataGeomagneticYamaha.mOffsetH_Z = "-1")
    {
      if (!arrayOfString[16].equals("0")) {
        break label443;
      }
      localDataGeomagneticYamaha.mPowerOff_Result = "1";
      return localDataGeomagneticYamaha;
      label299:
      localDataGeomagneticYamaha.mInitialized = "-1";
      break;
      label309:
      localDataGeomagneticYamaha.mStatus_Result = "-1";
      localDataGeomagneticYamaha.mStatus = "-1";
      break label94;
      label326:
      localDataGeomagneticYamaha.mDAC_Result = "-1";
      localDataGeomagneticYamaha.mDAC_X = "-1";
      localDataGeomagneticYamaha.mDAC_Y = "-1";
      localDataGeomagneticYamaha.mDAC_Z = "-1";
      break label140;
      label357:
      localDataGeomagneticYamaha.mADC_Result = "-1";
      localDataGeomagneticYamaha.mADC_X = "-1";
      localDataGeomagneticYamaha.mADC_Y = "-1";
      localDataGeomagneticYamaha.mADC_Z = "-1";
      break label187;
      label388:
      localDataGeomagneticYamaha.mSelf_Result = "-1";
      localDataGeomagneticYamaha.mSelf_X = "-1";
      localDataGeomagneticYamaha.mSelf_Y = "-1";
      break label228;
      label412:
      localDataGeomagneticYamaha.mOffsetH_Result = "-1";
      localDataGeomagneticYamaha.mOffsetH_X = "-1";
      localDataGeomagneticYamaha.mOffsetH_Y = "-1";
    }
    label443:
    localDataGeomagneticYamaha.mPowerOff_Result = "-1";
    return localDataGeomagneticYamaha;
  }
  
  private FragmentGyroscopeInvensense.DataGyroInvensense getDataGyroInvensense()
  {
    FtUtil.log_e(this.CLASS_NAME, "getDataGyroInvensense", null);
    FragmentGyroscopeInvensense.DataGyroInvensense localDataGyroInvensense = new FragmentGyroscopeInvensense.DataGyroInvensense();
    if ((this.mFeature_Gyroscope.equals("INVENSENSE_MPU6050")) || (this.mFeature_Gyroscope.equals("INVENSENSE_MPU6051"))) {
      localDataGyroInvensense.mInitialized = "1";
    }
    if (this.mSensorID_None < this.mSensorID_Gyrocope_Temperature) {
      localDataGyroInvensense.mTemperature = this.mModuleSensor.getData(this.mSensorID_Gyrocope_Temperature)[2];
    }
    if (this.mSensorID_None < this.mSensorID_Gyrocope_Self)
    {
      String[] arrayOfString = this.mModuleSensor.getData(this.mSensorID_Gyrocope_Self);
      if ((arrayOfString != null) && (arrayOfString.length > 7))
      {
        localDataGyroInvensense.mBias_Result = arrayOfString[1];
        localDataGyroInvensense.mBias_X = arrayOfString[2];
        localDataGyroInvensense.mBias_Y = arrayOfString[3];
        localDataGyroInvensense.mBias_Z = arrayOfString[4];
        localDataGyroInvensense.mRMS_X = arrayOfString[5];
        localDataGyroInvensense.mRMS_Y = arrayOfString[6];
        localDataGyroInvensense.mRMS_Z = arrayOfString[7];
      }
    }
    else
    {
      return localDataGyroInvensense;
    }
    localDataGyroInvensense.mBias_Result = "-1";
    localDataGyroInvensense.mBias_X = "-1";
    localDataGyroInvensense.mBias_Y = "-1";
    localDataGyroInvensense.mBias_Z = "-1";
    localDataGyroInvensense.mRMS_X = "-1";
    localDataGyroInvensense.mRMS_Y = "-1";
    localDataGyroInvensense.mRMS_Z = "-1";
    return localDataGyroInvensense;
  }
  
  private FragmentGyroscopeInvensense.DataGyroInvensense getDataGyroInvensenseSysfs()
  {
    FtUtil.log_e(this.CLASS_NAME, "getDataGyroInvensenseSysfs", null);
    FragmentGyroscopeInvensense.DataGyroInvensense localDataGyroInvensense = new FragmentGyroscopeInvensense.DataGyroInvensense();
    if ((this.mFeature_Gyroscope.equals("INVENSENSE_MPU6050")) || (this.mFeature_Gyroscope.equals("INVENSENSE_MPU6051"))) {
      localDataGyroInvensense.mInitialized = "1";
    }
    String[] arrayOfString2;
    if (this.mSensorID_None < this.mSensorID_Gyrocope_Temperature)
    {
      arrayOfString2 = this.mModuleSensor.getData(this.mSensorID_Gyrocope_Temperature);
      if ((arrayOfString2 == null) || (arrayOfString2.length <= 2)) {
        break label189;
      }
    }
    label189:
    for (localDataGyroInvensense.mTemperature = arrayOfString2[2];; localDataGyroInvensense.mTemperature = "-1")
    {
      if (this.mSensorID_None < this.mSensorID_Gyrocope_Self)
      {
        String str = Support.Kernel.read("GYRO_SENSOR_SELFTEST");
        if (str != null)
        {
          String[] arrayOfString1 = str.split(",");
          if ((arrayOfString1 == null) || (arrayOfString1.length <= 6)) {
            break;
          }
          localDataGyroInvensense.mBias_Result = arrayOfString1[0];
          localDataGyroInvensense.mBias_X = arrayOfString1[1];
          localDataGyroInvensense.mBias_Y = arrayOfString1[2];
          localDataGyroInvensense.mBias_Z = arrayOfString1[3];
          localDataGyroInvensense.mRMS_X = arrayOfString1[4];
          localDataGyroInvensense.mRMS_Y = arrayOfString1[5];
          localDataGyroInvensense.mRMS_Z = arrayOfString1[6];
        }
      }
      return localDataGyroInvensense;
    }
    localDataGyroInvensense.mBias_Result = "-1";
    localDataGyroInvensense.mBias_X = "-1";
    localDataGyroInvensense.mBias_Y = "-1";
    localDataGyroInvensense.mBias_Z = "-1";
    localDataGyroInvensense.mRMS_X = "-1";
    localDataGyroInvensense.mRMS_Y = "-1";
    localDataGyroInvensense.mRMS_Z = "-1";
    return localDataGyroInvensense;
  }
  
  private FragmentGyroscopeSTMicroSmartphone.DataGyroSTMicroSmartphone getDataGyroSTMicroSmartphone()
  {
    FtUtil.log_e(this.CLASS_NAME, "getDataGyroSTMicroSmartphone", null);
    return new FragmentGyroscopeSTMicroSmartphone.DataGyroSTMicroSmartphone();
  }
  
  private FragmentGyroscopeSTMicroTablet.DataGyroSTMicroTablet getDataGyroSTMicroTablet()
  {
    FtUtil.log_e(this.CLASS_NAME, "getDataGyroSTMicroTablet", null);
    return new FragmentGyroscopeSTMicroTablet.DataGyroSTMicroTablet();
  }
  
  private void init()
  {
    FtUtil.log_d(this.CLASS_NAME, "init", null);
    this.mList_TestItem = FactoryTestManager.getChildItems(this.TEST_ID);
    FtUtil.log_e(this.CLASS_NAME, "init", "TestItemCount : " + this.mList_TestItem.length);
    if (FactoryTestManager.isTest(21))
    {
      this.mLinear_Top = ((LinearLayout)findViewById(2131296401));
      this.mLinear_Top.setVisibility(0);
    }
    if (FactoryTestManager.isTest(20))
    {
      this.mLinear_Bottom = ((LinearLayout)findViewById(2131296403));
      this.mLinear_Bottom.setVisibility(0);
    }
    if (this.mList_TestItem.length == 2)
    {
      this.mView_Separator = findViewById(2131296402);
      this.mView_Separator.setVisibility(0);
    }
    setCountNVWrite(this.mList_TestItem.length);
  }
  
  private void setFragment()
  {
    FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
    if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE] != null)
    {
      if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mIC == "INVENSENSE")
      {
        FtUtil.log_d(this.CLASS_NAME, "setFragment", "TESTINFO_IC_INVENSENSE");
        this.mFragment_gyroscope_invensense = new FragmentGyroscopeInvensense();
        localFragmentTransaction.add(2131296401, this.mFragment_gyroscope_invensense);
      }
    }
    else if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC] != null)
    {
      if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIC != "ASAHI") {
        break label256;
      }
      FtUtil.log_d(this.CLASS_NAME, "setFragment", "TESTINFO_IC_ASAHI");
      this.mFragment_geomagnetic_asahi = new FragmentGeomagneticAsahi();
      localFragmentTransaction.add(2131296403, this.mFragment_geomagnetic_asahi);
    }
    for (;;)
    {
      localFragmentTransaction.commit();
      return;
      if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mIC == "STMICRO_SMARTPHONE")
      {
        FtUtil.log_d(this.CLASS_NAME, "setFragment", "TESTINFO_IC_STMICRO_SMARTPHONE");
        this.mFragment_gyroscope_stmicro_smartphone = new FragmentGyroscopeSTMicroSmartphone();
        localFragmentTransaction.add(2131296401, this.mFragment_gyroscope_stmicro_smartphone);
        break;
      }
      if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mIC != "STMICRO_TABLET") {
        break;
      }
      FtUtil.log_d(this.CLASS_NAME, "setFragment", "TESTINFO_IC_STMICRO_TABLET");
      this.mFragment_gyroscope_stmicro_tablet = new FragmentGyroscopeSTMicroTablet();
      localFragmentTransaction.add(2131296401, this.mFragment_gyroscope_stmicro_tablet);
      break;
      label256:
      if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIC == "YAMAHA")
      {
        FtUtil.log_d(this.CLASS_NAME, "setFragment", "TESTINFO_IC_YAMAHA");
        this.mFragment_geomagnetic_yamaha = new FragmentGeomagneticYamaha();
        localFragmentTransaction.add(2131296403, this.mFragment_geomagnetic_yamaha);
      }
      else if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIC == "ALPS")
      {
        FtUtil.log_d(this.CLASS_NAME, "setFragment", "TESTINFO_IC_ALPS");
        this.mFragment_geomagnetic_alps = new FragmentGeomagneticAlps();
        localFragmentTransaction.add(2131296403, this.mFragment_geomagnetic_alps);
      }
      else if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIC == "BOSCH")
      {
        FtUtil.log_d(this.CLASS_NAME, "setFragment", "TESTINFO_IC_BOSCH");
        this.mFragment_geomagnetic_bosch = new FragmentGeomagneticBosch();
        localFragmentTransaction.add(2131296403, this.mFragment_geomagnetic_bosch);
      }
    }
  }
  
  private void setSensorID()
  {
    FtUtil.log_d(this.CLASS_NAME, "setSensorID", null);
    if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE] != null)
    {
      if ((this.mFeature_Gyroscope.equals("INVENSENSE")) || (this.mFeature_Gyroscope.equals("INVENSENSE_MPU6050")) || (this.mFeature_Gyroscope.equals("INVENSENSE_MPU6051")))
      {
        FtUtil.log_d(this.CLASS_NAME, "setSensorID", "INVENSENSE or INVENSENSE_MPU6050 or INVENSENSE_MPU6051");
        this.mSensorID_Gyrocope_Temperature = ModuleSensor.ID_FILE____GYRO_TEMPERATURE;
        this.mSensorID_Gyrocope_Self = ModuleSensor.ID_MANAGER_GYRO_SELF;
      }
    }
    else if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC] != null)
    {
      if ((!this.mFeature_Geomagnetic.equals("AK8963")) && (!this.mFeature_Geomagnetic.equals("AK8963C"))) {
        break label354;
      }
      FtUtil.log_d(this.CLASS_NAME, "setSensorID", "AK8963 or AK8963C");
      this.mSensorID_Geomagnetic_Initialized = ModuleSensor.ID_FILE____MAGNETIC_STATUS;
      this.mSensorID_Geomagnetic_DAC = ModuleSensor.ID_FILE____MAGNETIC_DAC;
      this.mSensorID_Geomagnetic_ADC = ModuleSensor.ID_FILE____MAGNETIC_ADC;
      this.mSensorID_Geomagnetic_Self = ModuleSensor.ID_FILE____MAGNETIC_SELF;
    }
    for (;;)
    {
      int[] arrayOfInt = new int[11];
      arrayOfInt[0] = this.mSensorID_Gyrocope_Power;
      arrayOfInt[1] = this.mSensorID_Gyrocope_Temperature;
      arrayOfInt[2] = this.mSensorID_Gyrocope_Self;
      arrayOfInt[3] = this.mSensorID_Geomagnetic_Initialized;
      arrayOfInt[4] = this.mSensorID_Geomagnetic_Status;
      arrayOfInt[5] = this.mSensorID_Geomagnetic_Temperature;
      arrayOfInt[6] = this.mSensorID_Geomagnetic_DAC;
      arrayOfInt[7] = this.mSensorID_Geomagnetic_ADC;
      arrayOfInt[8] = this.mSensorID_Geomagnetic_ADC2;
      arrayOfInt[9] = this.mSensorID_Geomagnetic_Self;
      arrayOfInt[10] = this.mSensorID_Geomagnetic_Released;
      this.mSenserID = arrayOfInt;
      this.mModuleSensor = ModuleSensor.instance(this);
      this.mModuleSensor.SensorOn(this.mSenserID, this.mHandler, -1);
      return;
      if (this.mFeature_Gyroscope.equals("INVENSENSE_MPU6500"))
      {
        FtUtil.log_d(this.CLASS_NAME, "setSensorID", "INVENSENSE_MPU6500");
        this.mSensorID_Gyrocope_Temperature = ModuleSensor.ID_FILE____GYRO_TEMPERATURE;
        this.mSensorID_Gyrocope_Self = ModuleSensor.ID_FILE____GYRO_SELFTEST;
        break;
      }
      if ((this.mFeature_Gyroscope.equals("STMICRO_SMARTPHONE")) || (!this.mFeature_Gyroscope.equals("STMICRO_TABLET"))) {
        break;
      }
      break;
      label354:
      if ((this.mFeature_Geomagnetic.equals("AK8975")) || (this.mFeature_Geomagnetic.equals("AK8963C_MANAGER")))
      {
        FtUtil.log_d(this.CLASS_NAME, "setSensorID", "AK8975 or AK8963C_MANAGER");
        this.mSensorID_Geomagnetic_Initialized = ModuleSensor.ID_MANAGER_MAGNETIC_POWER_ON;
        this.mSensorID_Geomagnetic_DAC = ModuleSensor.ID_MANAGER_MAGNETIC_DAC;
        this.mSensorID_Geomagnetic_ADC = ModuleSensor.ID_MANAGER_MAGNETIC_ADC;
        this.mSensorID_Geomagnetic_Self = ModuleSensor.ID_MANAGER_MAGNETIC_SELF;
      }
      else if (this.mFeature_Geomagnetic.equals("AK8973"))
      {
        FtUtil.log_d(this.CLASS_NAME, "setSensorID", "AK8973");
      }
      else if ((this.mFeature_Geomagnetic.equals("YAS530C")) || (this.mFeature_Geomagnetic.equals("YAS532B")) || (this.mFeature_Geomagnetic.equals("YAS532")))
      {
        FtUtil.log_d(this.CLASS_NAME, "setSensorID", "YAS530C or YAS532, YAS532B");
        this.mSensorID_Geomagnetic_Initialized = ModuleSensor.ID_MANAGER_MAGNETIC_POWER_ON;
        this.mSensorID_Geomagnetic_Status = ModuleSensor.ID_MANAGER_MAGNETIC_STATUS;
        this.mSensorID_Geomagnetic_DAC = ModuleSensor.ID_MANAGER_MAGNETIC_DAC;
        this.mSensorID_Geomagnetic_ADC = ModuleSensor.ID_MANAGER_MAGNETIC_ADC;
        this.mSensorID_Geomagnetic_Self = ModuleSensor.ID_MANAGER_MAGNETIC_SELF;
        this.mSensorID_Geomagnetic_ADC2 = ModuleSensor.ID_MANAGER_MAGNETIC_OFFSETH;
        this.mSensorID_Geomagnetic_Released = ModuleSensor.ID_MANAGER_MAGNETIC_POWER_OFF;
      }
      else if ((this.mFeature_Geomagnetic.equals("HSCDTD004")) || (this.mFeature_Geomagnetic.equals("HSCDTD004A")) || (this.mFeature_Geomagnetic.equals("HSCDTD006A")) || (this.mFeature_Geomagnetic.equals("HSCDTD008A")))
      {
        FtUtil.log_d(this.CLASS_NAME, "setSensorID", "HSCDTD004 or HSCDTD004A or HSCDTD006A");
        this.mSensorID_Geomagnetic_Initialized = ModuleSensor.ID_MANAGER_MAGNETIC_POWER_ON;
        this.mSensorID_Geomagnetic_Status = ModuleSensor.ID_FILE____MAGNETIC_STATUS;
        this.mSensorID_Geomagnetic_ADC = ModuleSensor.ID_FILE____MAGNETIC_ADC;
      }
      else if (this.mFeature_Geomagnetic.equals("BMC150"))
      {
        FtUtil.log_d(this.CLASS_NAME, "setSensorID", "BMC150");
        this.mSensorID_Geomagnetic_Initialized = ModuleSensor.ID_MANAGER_MAGNETIC_POWER_ON;
        this.mSensorID_Geomagnetic_Status = ModuleSensor.ID_MANAGER_MAGNETIC_SELF;
        this.mSensorID_Geomagnetic_ADC = ModuleSensor.ID_MANAGER_MAGNETIC_ADC;
      }
    }
  }
  
  private void setTestInfo()
  {
    FtUtil.log_d(this.CLASS_NAME, "setTestInfo", null);
    this.mFeature_Gyroscope = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_GYRO, SensorDeviceInfo.TARGET_XML);
    this.mFeature_Geomagnetic = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_GEOMAGNETIC, SensorDeviceInfo.TARGET_XML);
    FtUtil.log_d(this.CLASS_NAME, "setTestInfo", "mFeature_Gyroscope : " + this.mFeature_Gyroscope);
    FtUtil.log_d(this.CLASS_NAME, "setTestInfo", "mFeature_Geomagnetic : " + this.mFeature_Geomagnetic);
    if (FactoryTestManager.isTest(21))
    {
      this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE] = new TestInfo(null);
      this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mID = 21;
      if (this.mFeature_Gyroscope.contains("INVENSENSE"))
      {
        FtUtil.log_d(this.CLASS_NAME, "setTestInfo", "TESTINFO_IC_INVENSENSE");
        this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mIC = "INVENSENSE";
      }
    }
    else if (FactoryTestManager.isTest(20))
    {
      this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC] = new TestInfo(null);
      this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mID = 20;
      if (!this.mFeature_Geomagnetic.contains("AK")) {
        break label347;
      }
      FtUtil.log_d(this.CLASS_NAME, "setTestInfo", "TESTINFO_IC_ASAHI");
      this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIC = "ASAHI";
    }
    label347:
    do
    {
      return;
      if (this.mFeature_Gyroscope.contains("STMICRO_SMARTPHONE"))
      {
        FtUtil.log_d(this.CLASS_NAME, "setTestInfo", "TESTINFO_IC_STMICRO_SMARTPHONE");
        this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mIC = "STMICRO_SMARTPHONE";
        break;
      }
      if (!this.mFeature_Gyroscope.contains("STMICRO_TABLET")) {
        break;
      }
      FtUtil.log_d(this.CLASS_NAME, "setTestInfo", "TESTINFO_IC_STMICRO_TABLET");
      this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mIC = "STMICRO_TABLET";
      break;
      if (this.mFeature_Geomagnetic.contains("YAS"))
      {
        FtUtil.log_d(this.CLASS_NAME, "setTestInfo", "TESTINFO_IC_YAMAHA");
        this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIC = "YAMAHA";
        return;
      }
      if (this.mFeature_Geomagnetic.contains("HSCDTD"))
      {
        FtUtil.log_d(this.CLASS_NAME, "setTestInfo", "TESTINFO_IC_ALPS");
        this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIC = "ALPS";
        return;
      }
    } while (!this.mFeature_Geomagnetic.contains("BMC"));
    FtUtil.log_d(this.CLASS_NAME, "setTestInfo", "TESTINFO_IC_BOSCH");
    this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIC = "BOSCH";
  }
  
  private void update()
  {
    FtUtil.log_d(this.CLASS_NAME, "update", null);
    if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE] != null)
    {
      if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mIC != "INVENSENSE") {
        break label127;
      }
      if (Support.Feature.getBoolean("SUPPORT_SYSFS_GEO_GYRO")) {
        this.mFragment_gyroscope_invensense.update(getDataGyroInvensenseSysfs(), this.mHandler);
      }
    }
    else if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC] != null)
    {
      if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIC != "ASAHI") {
        break label197;
      }
      this.mFragment_geomagnetic_asahi.update(getDataGeomagneticAsahi(), this.mHandler);
    }
    label127:
    do
    {
      return;
      this.mFragment_gyroscope_invensense.update(getDataGyroInvensense(), this.mHandler);
      break;
      if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mIC == "STMICRO_SMARTPHONE")
      {
        this.mFragment_gyroscope_stmicro_smartphone.update(getDataGyroSTMicroSmartphone(), this.mHandler);
        break;
      }
      if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GYROSCOPE].mIC != "STMICRO_TABLET") {
        break;
      }
      this.mFragment_gyroscope_stmicro_tablet.update(getDataGyroSTMicroTablet(), this.mHandler);
      break;
      if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIC == "YAMAHA")
      {
        if (Support.Feature.getBoolean("SUPPORT_SYSFS_GEO_GYRO"))
        {
          this.mFragment_geomagnetic_yamaha.update(getDataGeomagneticYamahaSysfs(), this.mHandler);
          return;
        }
        this.mFragment_geomagnetic_yamaha.update(getDataGeomagneticYamaha(), this.mHandler);
        return;
      }
      if (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIC == "ALPS")
      {
        this.mFragment_geomagnetic_alps.update(getDataGeomagneticAlps(), this.mHandler);
        return;
      }
    } while (this.mTestInfoArray[this.TESTINFO_ARRAY_INDEX_GEOMAGNETIC].mIC != "BOSCH");
    label197:
    this.mFragment_geomagnetic_bosch.update(getDataGeomagneticBosch(), this.mHandler);
  }
  
  private void writeNV(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean == true)
    {
      FtUtil.log_e(this.CLASS_NAME, "writeNV", "PASS");
      setTestResult((byte)paramInt, (byte)80);
      return;
    }
    FtUtil.log_e(this.CLASS_NAME, "writeNV", "FAIL");
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903061);
    setTestInfo();
    setFragment();
    setSensorID();
    init();
  }
  
  protected void onPause()
  {
    super.onPause();
    this.mModuleSensor.SensorOff();
  }
  
  public class EmptyListener
    implements DialogInterface.OnDismissListener
  {
    public EmptyListener() {}
    
    public void onDismiss(DialogInterface paramDialogInterface)
    {
      FtUtil.log_e(UIGeomagneticGyro.this.CLASS_NAME, "onDismiss", "bye bye~~ !");
      UIGeomagneticGyro.this.finish();
    }
  }
  
  private class TestInfo
  {
    public String mIC = null;
    public int mID = -1;
    public boolean mIsTestComplete = false;
    public boolean mIsTestPass = false;
    
    private TestInfo() {}
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIGeomagneticGyro
 * JD-Core Version:    0.7.1
 */