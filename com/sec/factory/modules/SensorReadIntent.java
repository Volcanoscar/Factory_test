package com.sec.factory.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;

public class SensorReadIntent
{
  private final String ACCELERMETER_INTENT_ACTION_READ;
  private final String ACCELERMETER_INTENT_ACTION_STOP;
  private final int ACCEL_COUNT_MAX;
  private final String CLASS_NAME = "SensorReadIntent";
  private final String CP_INTENT_ACTION_READ;
  private int DUMMY = 0;
  private final int GRIP_COUNT_MAX;
  private final String GRIP_INTENT_ACTION_READ;
  private final String GRIP_INTENT_ACTION_READ_LTE;
  private final String GRIP_INTENT_ACTION_READ_OTHER;
  private final String GRIP_INTENT_ACTION_STOP;
  private final String GRIP_VALUE__INTENT_EXTRA_NAME;
  private final String GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_1;
  private final String GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_1N2;
  private final String GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_1N2N3;
  private final String GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_1N3;
  private final String GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_2;
  private final String GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_2N3;
  private final String GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_3;
  private final String GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_HC_0000;
  private final String GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_HC_0100;
  private final String GRIP_VALUE__INTENT_EXTRA_VALUE__RELEASE_ALL;
  private final int SENSOR_ON_ARRAY_INDEX_GRIP;
  private final int SENSOR_ON_ARRAY_LENGTH;
  private int count_Grip;
  private BroadcastReceiver mBroadcastReceiver;
  private int[] mBuffer_SensorValue_CPsAccelerometer;
  private int[] mBuffer_SensorValue_Grip;
  private Context mContext;
  private int[] mModuleSensorIDArray;
  private boolean[] mSensorOn;
  
  public SensorReadIntent(Context paramContext, int[] paramArrayOfInt)
  {
    int i = this.DUMMY;
    this.DUMMY = (i + 1);
    this.SENSOR_ON_ARRAY_INDEX_GRIP = i;
    this.SENSOR_ON_ARRAY_LENGTH = this.DUMMY;
    this.mSensorOn = new boolean[this.SENSOR_ON_ARRAY_LENGTH];
    this.CP_INTENT_ACTION_READ = "com.sec.android.app.factorytest";
    this.GRIP_INTENT_ACTION_READ = "030005";
    this.GRIP_INTENT_ACTION_READ_LTE = "03000b";
    this.GRIP_INTENT_ACTION_READ_OTHER = "07000b";
    this.ACCELERMETER_INTENT_ACTION_READ = "07000c";
    this.GRIP_INTENT_ACTION_STOP = "com.android.samsungtest.GripTestStop";
    this.GRIP_VALUE__INTENT_EXTRA_NAME = "COMMAND";
    this.GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_1 = "010000000000";
    this.GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_2 = "000001000000";
    this.GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_3 = "";
    this.GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_1N2 = "010001000000";
    this.GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_1N3 = "";
    this.GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_2N3 = "";
    this.GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_1N2N3 = "";
    this.GRIP_VALUE__INTENT_EXTRA_VALUE__RELEASE_ALL = "000000000000";
    this.GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_HC_0100 = "0100";
    this.GRIP_VALUE__INTENT_EXTRA_VALUE__DETECT_SENSOR_HC_0000 = "0000";
    this.ACCELERMETER_INTENT_ACTION_STOP = "com.android.samsungtest.CpAccelermeterOff";
    this.ACCEL_COUNT_MAX = 3;
    this.GRIP_COUNT_MAX = 3;
    this.mBuffer_SensorValue_Grip = null;
    this.mBuffer_SensorValue_CPsAccelerometer = null;
    this.mBroadcastReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        String str1 = paramAnonymousIntent.getAction();
        FtUtil.log_i("SensorReadIntent", "mBroadcastReceiver.onReceive()", "action : " + str1);
        String str2;
        String str3;
        if (str1.equals("com.sec.android.app.factorytest"))
        {
          str2 = paramAnonymousIntent.getStringExtra("COMMAND");
          str3 = str2.substring(6);
          FtUtil.log_i("SensorReadIntent", "mBroadcastReceiver.onReceive()", "cmdData : " + str2 + " , sensorData : " + str3);
          if ((str2.startsWith("030005") != true) && (str2.startsWith("07000b") != true) && (str2.startsWith("03000b") != true)) {
            break label146;
          }
          FtUtil.log_d("SensorReadIntent", "onReceive", "Grip");
          SensorReadIntent.this.setValueGrip(str3);
        }
        label146:
        while (str2.startsWith("07000c") != true) {
          return;
        }
        FtUtil.log_d("SensorReadIntent", "onReceive", "Accelerometer");
        SensorReadIntent.this.setValueCPsAccelerometerData(str3);
      }
    };
    FtUtil.log_e("SensorReadIntent", "SensorReadIntent", "Sensor On");
    this.mContext = paramContext;
    this.mModuleSensorIDArray = paramArrayOfInt;
    this.mBuffer_SensorValue_Grip = new int[3];
    this.mBuffer_SensorValue_CPsAccelerometer = new int[3];
    for (int j = 0; j < this.SENSOR_ON_ARRAY_LENGTH; j++) {
      this.mSensorOn[j] = false;
    }
    int k = 0;
    if (k < this.mModuleSensorIDArray.length)
    {
      if (this.mModuleSensorIDArray[k] == ModuleSensor.ID_INTENT__GRIP) {
        if (this.mSensorOn[this.SENSOR_ON_ARRAY_INDEX_GRIP] == 0)
        {
          FtUtil.log_d("SensorReadIntent", "SensorReadIntent", "Grip Sensor");
          int m = ConverterID(ModuleSensor.ID_INTENT__GRIP);
          this.mSensorOn[m] = true;
          registerReceiver_Grip();
        }
      }
      for (;;)
      {
        k++;
        break;
        FtUtil.log_d("SensorReadIntent", "SensorReadIntent", "Grip Sensor - On");
        continue;
        if (this.mModuleSensorIDArray[k] == ModuleSensor.ID_INTENT__CP_ACCELEROMETER)
        {
          FtUtil.log_d("SensorReadIntent", "SensorReadIntent", "CP Accelerometer Sensor - On");
          registerReceiver_CPAccelerometer();
        }
      }
    }
  }
  
  private int ConverterID(int paramInt)
  {
    int i = -1;
    if (paramInt == ModuleSensor.ID_INTENT__GRIP) {
      i = this.SENSOR_ON_ARRAY_INDEX_GRIP;
    }
    return i;
  }
  
  private String dataCheck(int[] paramArrayOfInt)
  {
    String str = "";
    if (paramArrayOfInt != null) {
      for (int i = 0; i < paramArrayOfInt.length; i++)
      {
        str = str + paramArrayOfInt[i];
        if (i < -1 + paramArrayOfInt.length) {
          str = str + " , ";
        }
      }
    }
    str = "Data : null";
    return str;
  }
  
  private void registerReceiver_CPAccelerometer()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.sec.android.app.factorytest");
    localIntentFilter.addAction("com.android.samsungtest.CpAccelermeterOff");
    this.mContext.registerReceiver(this.mBroadcastReceiver, localIntentFilter);
  }
  
  private void registerReceiver_Grip()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.sec.android.app.factorytest");
    localIntentFilter.addAction("com.android.samsungtest.GripTestStop");
    this.mContext.registerReceiver(this.mBroadcastReceiver, localIntentFilter);
  }
  
  private void setValueCPsAccelerometerData(String paramString)
  {
    String str1 = paramString.substring(2, 4) + paramString.substring(0, 2);
    String str2 = paramString.substring(6, 8) + paramString.substring(4, 6);
    String str3 = paramString.substring(10, 12) + paramString.substring(8, 10);
    FtUtil.log_d("SensorReadIntent", "setValueCPsAccelerometerData", "xData=[" + str1 + "],yData=[" + str2 + "],zData=[" + str3 + "]");
    this.mBuffer_SensorValue_CPsAccelerometer[0] = (-1 * (short)Integer.parseInt(str2, 16));
    this.mBuffer_SensorValue_CPsAccelerometer[1] = (-1 * (short)Integer.parseInt(str1, 16));
    this.mBuffer_SensorValue_CPsAccelerometer[2] = ((short)Integer.parseInt(str3, 16));
    FtUtil.log_d("SensorReadIntent", "setValueCPsAccelerometerData", "x=[" + this.mBuffer_SensorValue_CPsAccelerometer[0] + "],y=[" + this.mBuffer_SensorValue_CPsAccelerometer[1] + "],z=[" + this.mBuffer_SensorValue_CPsAccelerometer[2] + "]");
  }
  
  private void setValueGrip(String paramString)
  {
    FtUtil.log_d("SensorReadIntent", "setValueGrip", "value : " + paramString);
    this.count_Grip = Integer.valueOf(Support.FactoryTestMenu.getTestCase(FactoryTestManager.convertorID_XML((byte)34))).intValue();
    if (paramString.equals("010000000000"))
    {
      this.mBuffer_SensorValue_Grip[0] = 1;
      this.mBuffer_SensorValue_Grip[1] = 0;
      this.mBuffer_SensorValue_Grip[2] = 0;
    }
    for (;;)
    {
      if (this.count_Grip == 1)
      {
        if ((paramString.equals("010000000000")) || (paramString.equals("000001000000"))) {
          this.mBuffer_SensorValue_Grip[0] = 1;
        }
        this.mBuffer_SensorValue_Grip[1] = 1;
        this.mBuffer_SensorValue_Grip[2] = 0;
      }
      return;
      if (paramString.equals("000001000000"))
      {
        this.mBuffer_SensorValue_Grip[0] = 0;
        this.mBuffer_SensorValue_Grip[1] = 1;
        this.mBuffer_SensorValue_Grip[2] = 0;
      }
      else if (paramString.equals(""))
      {
        this.mBuffer_SensorValue_Grip[0] = 0;
        this.mBuffer_SensorValue_Grip[1] = 0;
        this.mBuffer_SensorValue_Grip[2] = 1;
      }
      else if (paramString.equals("010001000000"))
      {
        this.mBuffer_SensorValue_Grip[0] = 1;
        this.mBuffer_SensorValue_Grip[1] = 1;
        this.mBuffer_SensorValue_Grip[2] = 0;
      }
      else if (paramString.equals(""))
      {
        this.mBuffer_SensorValue_Grip[0] = 1;
        this.mBuffer_SensorValue_Grip[1] = 0;
        this.mBuffer_SensorValue_Grip[2] = 1;
      }
      else if (paramString.equals(""))
      {
        this.mBuffer_SensorValue_Grip[0] = 0;
        this.mBuffer_SensorValue_Grip[1] = 1;
        this.mBuffer_SensorValue_Grip[2] = 1;
      }
      else if (paramString.equals(""))
      {
        this.mBuffer_SensorValue_Grip[0] = 1;
        this.mBuffer_SensorValue_Grip[1] = 1;
        this.mBuffer_SensorValue_Grip[2] = 1;
      }
      else if (paramString.equals("000000000000"))
      {
        this.mBuffer_SensorValue_Grip[0] = 0;
        this.mBuffer_SensorValue_Grip[1] = 0;
        this.mBuffer_SensorValue_Grip[2] = 0;
      }
      else if (paramString.equals("0100"))
      {
        this.mBuffer_SensorValue_Grip[0] = 0;
        this.mBuffer_SensorValue_Grip[1] = 0;
        this.mBuffer_SensorValue_Grip[2] = 0;
      }
      else if (paramString.equals("0000"))
      {
        this.mBuffer_SensorValue_Grip[0] = 1;
        this.mBuffer_SensorValue_Grip[1] = 0;
        this.mBuffer_SensorValue_Grip[2] = 0;
      }
      else
      {
        FtUtil.log_e("SensorReadIntent", "setValueGrip", "Unknown => " + paramString);
      }
    }
  }
  
  private void unregisterReceiver_CPAccelerometer()
  {
    if (this.mBroadcastReceiver != null) {
      this.mContext.unregisterReceiver(this.mBroadcastReceiver);
    }
  }
  
  private void unregisterReceiver_Grip()
  {
    FtUtil.log_d("SensorReadIntent", "unregisterReceiver_Grip", "unregisterReceiver_Grip");
    if (this.mBroadcastReceiver != null) {
      this.mContext.unregisterReceiver(this.mBroadcastReceiver);
    }
  }
  
  public void disableReceiver_CPsAccelerometer()
  {
    FtUtil.log_e("SensorReadIntent", "disableBroadcastReceiverCPsAccelerometer", "CP's Acceler... OFF");
    this.mBuffer_SensorValue_CPsAccelerometer = null;
    unregisterReceiver_CPAccelerometer();
  }
  
  public boolean isSensorOn(int paramInt)
  {
    int i = ConverterID(paramInt);
    return this.mSensorOn[i];
  }
  
  public int[] returnCPsAccelerometerData()
  {
    if (ModuleSensor.LOG_LEVEL <= 3) {
      FtUtil.log_e("SensorReadIntent", "returnCPsAccelerometerData", dataCheck(this.mBuffer_SensorValue_CPsAccelerometer));
    }
    return this.mBuffer_SensorValue_CPsAccelerometer;
  }
  
  public int[] returnGrip()
  {
    if (ModuleSensor.LOG_LEVEL <= 3) {
      FtUtil.log_e("SensorReadIntent", "returnGrip", dataCheck(this.mBuffer_SensorValue_Grip));
    }
    return this.mBuffer_SensorValue_Grip;
  }
  
  public void sensorOff()
  {
    FtUtil.log_e("SensorReadIntent", "sensorOff", "Grip Sensor Off");
    for (int i = 0; i < this.mModuleSensorIDArray.length; i++) {
      if (this.mModuleSensorIDArray[i] == ModuleSensor.ID_INTENT__GRIP)
      {
        FtUtil.log_d("SensorReadIntent", "sensorOff", "Grip Sensor");
        this.mBuffer_SensorValue_Grip = null;
        unregisterReceiver_Grip();
      }
    }
    for (int j = 0; j < this.SENSOR_ON_ARRAY_LENGTH; j++) {
      this.mSensorOn[j] = false;
    }
    this.mModuleSensorIDArray = null;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.SensorReadIntent
 * JD-Core Version:    0.7.1
 */