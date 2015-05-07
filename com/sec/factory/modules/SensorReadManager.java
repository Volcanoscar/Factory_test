package com.sec.factory.modules;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.sec.factory.support.FtUtil;

public class SensorReadManager
{
  private final String CLASS_NAME = "SensorReadManager";
  private int DUMMY = 0;
  private final int SENSOR_ON_ARRAY_INDEX_ACCELEROMETER;
  private final int SENSOR_ON_ARRAY_INDEX_ACCELEROMETER_N_ANGLE;
  private final int SENSOR_ON_ARRAY_INDEX_ACCELEROMETER_SELF;
  private final int SENSOR_ON_ARRAY_INDEX_BAROMETER;
  private final int SENSOR_ON_ARRAY_INDEX_GYRO;
  private final int SENSOR_ON_ARRAY_INDEX_GYRO_EXPANSION;
  private final int SENSOR_ON_ARRAY_INDEX_GYRO_POWER;
  private final int SENSOR_ON_ARRAY_INDEX_GYRO_SELF;
  private final int SENSOR_ON_ARRAY_INDEX_GYRO_TEMPERATURE;
  private final int SENSOR_ON_ARRAY_INDEX_HUMIDITY;
  private final int SENSOR_ON_ARRAY_INDEX_LIGHT;
  private final int SENSOR_ON_ARRAY_INDEX_LIGHT_CCT;
  private final int SENSOR_ON_ARRAY_INDEX_MAGNETIC;
  private final int SENSOR_ON_ARRAY_INDEX_MAGNETIC_ADC;
  private final int SENSOR_ON_ARRAY_INDEX_MAGNETIC_DAC;
  private final int SENSOR_ON_ARRAY_INDEX_MAGNETIC_OFFSETH;
  private final int SENSOR_ON_ARRAY_INDEX_MAGNETIC_POWER_OFF;
  private final int SENSOR_ON_ARRAY_INDEX_MAGNETIC_POWER_ON;
  private final int SENSOR_ON_ARRAY_INDEX_MAGNETIC_SELF;
  private final int SENSOR_ON_ARRAY_INDEX_MAGNETIC_STATUS;
  private final int SENSOR_ON_ARRAY_INDEX_MAGNETIC_TEMPERATURE;
  private final int SENSOR_ON_ARRAY_INDEX_PROXIMITY;
  private final int SENSOR_ON_ARRAY_INDEX_TEMPERATURE;
  private final int SENSOR_ON_ARRAY_LENGTH;
  private Sensor mAccelerometerSensor;
  private Sensor mBarometerSensor;
  private float[] mBuffer_SensorValue_Accelerometer;
  private float[] mBuffer_SensorValue_Barometer;
  private float[] mBuffer_SensorValue_Gyro;
  private float[] mBuffer_SensorValue_Humidity;
  private float[] mBuffer_SensorValue_Light;
  private float[] mBuffer_SensorValue_Magnetic;
  private float[] mBuffer_SensorValue_Proximity;
  private float[] mBuffer_SensorValue_Temperature;
  private Sensor mGyroscopeSensor;
  private Sensor mHumiditySensor;
  private Sensor mLightSensor;
  private Sensor mMagneticSensor;
  private Sensor mProximitySensor;
  private SensorListener mSensorListener;
  private SensorManager mSensorManager;
  private boolean[] mSensorOn;
  private Sensor mTemperatureSensor;
  
  public SensorReadManager(int[] paramArrayOfInt, SensorManager paramSensorManager)
  {
    int i = this.DUMMY;
    this.DUMMY = (i + 1);
    this.SENSOR_ON_ARRAY_INDEX_ACCELEROMETER = i;
    int j = this.DUMMY;
    this.DUMMY = (j + 1);
    this.SENSOR_ON_ARRAY_INDEX_ACCELEROMETER_N_ANGLE = j;
    int k = this.DUMMY;
    this.DUMMY = (k + 1);
    this.SENSOR_ON_ARRAY_INDEX_ACCELEROMETER_SELF = k;
    int m = this.DUMMY;
    this.DUMMY = (m + 1);
    this.SENSOR_ON_ARRAY_INDEX_BAROMETER = m;
    int n = this.DUMMY;
    this.DUMMY = (n + 1);
    this.SENSOR_ON_ARRAY_INDEX_GYRO = n;
    int i1 = this.DUMMY;
    this.DUMMY = (i1 + 1);
    this.SENSOR_ON_ARRAY_INDEX_GYRO_EXPANSION = i1;
    int i2 = this.DUMMY;
    this.DUMMY = (i2 + 1);
    this.SENSOR_ON_ARRAY_INDEX_GYRO_POWER = i2;
    int i3 = this.DUMMY;
    this.DUMMY = (i3 + 1);
    this.SENSOR_ON_ARRAY_INDEX_GYRO_SELF = i3;
    int i4 = this.DUMMY;
    this.DUMMY = (i4 + 1);
    this.SENSOR_ON_ARRAY_INDEX_GYRO_TEMPERATURE = i4;
    int i5 = this.DUMMY;
    this.DUMMY = (i5 + 1);
    this.SENSOR_ON_ARRAY_INDEX_LIGHT = i5;
    int i6 = this.DUMMY;
    this.DUMMY = (i6 + 1);
    this.SENSOR_ON_ARRAY_INDEX_LIGHT_CCT = i6;
    int i7 = this.DUMMY;
    this.DUMMY = (i7 + 1);
    this.SENSOR_ON_ARRAY_INDEX_MAGNETIC = i7;
    int i8 = this.DUMMY;
    this.DUMMY = (i8 + 1);
    this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_POWER_ON = i8;
    int i9 = this.DUMMY;
    this.DUMMY = (i9 + 1);
    this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_STATUS = i9;
    int i10 = this.DUMMY;
    this.DUMMY = (i10 + 1);
    this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_TEMPERATURE = i10;
    int i11 = this.DUMMY;
    this.DUMMY = (i11 + 1);
    this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_DAC = i11;
    int i12 = this.DUMMY;
    this.DUMMY = (i12 + 1);
    this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_ADC = i12;
    int i13 = this.DUMMY;
    this.DUMMY = (i13 + 1);
    this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_SELF = i13;
    int i14 = this.DUMMY;
    this.DUMMY = (i14 + 1);
    this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_OFFSETH = i14;
    int i15 = this.DUMMY;
    this.DUMMY = (i15 + 1);
    this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_POWER_OFF = i15;
    int i16 = this.DUMMY;
    this.DUMMY = (i16 + 1);
    this.SENSOR_ON_ARRAY_INDEX_PROXIMITY = i16;
    int i17 = this.DUMMY;
    this.DUMMY = (i17 + 1);
    this.SENSOR_ON_ARRAY_INDEX_TEMPERATURE = i17;
    int i18 = this.DUMMY;
    this.DUMMY = (i18 + 1);
    this.SENSOR_ON_ARRAY_INDEX_HUMIDITY = i18;
    this.SENSOR_ON_ARRAY_LENGTH = this.DUMMY;
    this.mSensorOn = new boolean[this.SENSOR_ON_ARRAY_LENGTH];
    this.mSensorManager = null;
    this.mSensorListener = null;
    this.mAccelerometerSensor = null;
    this.mBarometerSensor = null;
    this.mGyroscopeSensor = null;
    this.mLightSensor = null;
    this.mMagneticSensor = null;
    this.mProximitySensor = null;
    this.mTemperatureSensor = null;
    this.mHumiditySensor = null;
    this.mBuffer_SensorValue_Accelerometer = null;
    this.mBuffer_SensorValue_Barometer = null;
    this.mBuffer_SensorValue_Gyro = null;
    this.mBuffer_SensorValue_Light = null;
    this.mBuffer_SensorValue_Magnetic = null;
    this.mBuffer_SensorValue_Proximity = null;
    this.mBuffer_SensorValue_Temperature = null;
    this.mBuffer_SensorValue_Humidity = null;
    FtUtil.log_e("SensorReadManager", "SensorReadManager", "Sensor On");
    for (int i19 = 0; i19 < this.SENSOR_ON_ARRAY_LENGTH; i19++) {
      this.mSensorOn[i19] = false;
    }
    this.mSensorManager = paramSensorManager;
    if (this.mSensorManager != null)
    {
      int i20 = 0;
      if (i20 < paramArrayOfInt.length)
      {
        if (this.mSensorListener == null) {
          this.mSensorListener = new SensorListener(null);
        }
        if ((paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_ACCELEROMETER) || (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_ACCELEROMETER_N_ANGLE) || (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_ACCELEROMETER_SELF))
        {
          if (this.mAccelerometerSensor == null)
          {
            this.mAccelerometerSensor = this.mSensorManager.getDefaultSensor(1);
            this.mSensorManager.registerListener(this.mSensorListener, this.mAccelerometerSensor, 2);
            FtUtil.log_d("SensorReadManager", "SensorReadManager", "register-AccelerometerSensor");
          }
          if (this.mAccelerometerSensor != null)
          {
            if (paramArrayOfInt[i20] != ModuleSensor.ID_MANAGER_ACCELEROMETER) {
              break label772;
            }
            int i23 = ConverterID(ModuleSensor.ID_MANAGER_ACCELEROMETER);
            this.mSensorOn[i23] = true;
          }
        }
        for (;;)
        {
          i20++;
          break;
          label772:
          if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_ACCELEROMETER_N_ANGLE)
          {
            int i22 = ConverterID(ModuleSensor.ID_MANAGER_ACCELEROMETER_N_ANGLE);
            this.mSensorOn[i22] = true;
          }
          else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_ACCELEROMETER_SELF)
          {
            int i21 = ConverterID(ModuleSensor.ID_MANAGER_ACCELEROMETER_SELF);
            this.mSensorOn[i21] = true;
            continue;
            if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_BAROMETER)
            {
              if (this.mBarometerSensor == null)
              {
                this.mBarometerSensor = this.mSensorManager.getDefaultSensor(6);
                this.mSensorManager.registerListener(this.mSensorListener, this.mBarometerSensor, 2);
                FtUtil.log_d("SensorReadManager", "SensorReadManager", "register-BarometerSensor");
              }
              if (this.mBarometerSensor != null)
              {
                int i43 = ConverterID(ModuleSensor.ID_MANAGER_BAROMETER);
                this.mSensorOn[i43] = true;
              }
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_GYRO)
            {
              if (this.mGyroscopeSensor == null)
              {
                this.mGyroscopeSensor = this.mSensorManager.getDefaultSensor(4);
                this.mSensorManager.registerListener(this.mSensorListener, this.mGyroscopeSensor, 2);
                FtUtil.log_d("SensorReadManager", "SensorReadManager", "register-GyroscopeSensor");
              }
              if (this.mGyroscopeSensor != null)
              {
                int i42 = ConverterID(ModuleSensor.ID_MANAGER_GYRO);
                this.mSensorOn[i42] = true;
              }
            }
            else if ((paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_LIGHT) || (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_LIGHT_CCT))
            {
              if (this.mLightSensor == null)
              {
                this.mLightSensor = this.mSensorManager.getDefaultSensor(5);
                this.mSensorManager.registerListener(this.mSensorListener, this.mLightSensor, 2);
                FtUtil.log_d("SensorReadManager", "SensorReadManager", "register-LightSensor");
              }
              if (this.mLightSensor != null)
              {
                int i24 = ConverterID(ModuleSensor.ID_MANAGER_LIGHT);
                this.mSensorOn[i24] = true;
                int i25 = ConverterID(ModuleSensor.ID_MANAGER_LIGHT_CCT);
                this.mSensorOn[i25] = true;
              }
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_MAGNETIC)
            {
              if (this.mMagneticSensor == null)
              {
                this.mMagneticSensor = this.mSensorManager.getDefaultSensor(2);
                this.mSensorManager.registerListener(this.mSensorListener, this.mMagneticSensor, 2);
                FtUtil.log_d("SensorReadManager", "SensorReadManager", "register-MagneticSensor");
              }
              if (this.mMagneticSensor != null)
              {
                int i41 = ConverterID(ModuleSensor.ID_MANAGER_MAGNETIC);
                this.mSensorOn[i41] = true;
              }
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_PROXIMITY)
            {
              if (this.mProximitySensor == null)
              {
                this.mProximitySensor = this.mSensorManager.getDefaultSensor(8);
                this.mSensorManager.registerListener(this.mSensorListener, this.mProximitySensor, 2);
                FtUtil.log_d("SensorReadManager", "SensorReadManager", "register-ProximitySensor");
              }
              if (this.mProximitySensor != null)
              {
                int i40 = ConverterID(ModuleSensor.ID_MANAGER_PROXIMITY);
                this.mSensorOn[i40] = true;
              }
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_TEMPERATURE)
            {
              if (this.mTemperatureSensor == null)
              {
                this.mTemperatureSensor = this.mSensorManager.getDefaultSensor(13);
                this.mSensorManager.registerListener(this.mSensorListener, this.mTemperatureSensor, 2);
                FtUtil.log_d("SensorReadManager", "SensorReadManager", "register-Temperature");
              }
              if (this.mTemperatureSensor != null)
              {
                int i39 = ConverterID(ModuleSensor.ID_MANAGER_TEMPERATURE);
                this.mSensorOn[i39] = true;
              }
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_HUMIDITY)
            {
              if (this.mHumiditySensor == null)
              {
                this.mHumiditySensor = this.mSensorManager.getDefaultSensor(12);
                this.mSensorManager.registerListener(this.mSensorListener, this.mHumiditySensor, 2);
                FtUtil.log_d("SensorReadManager", "SensorReadManager", "register-Humidity");
              }
              if (this.mHumiditySensor != null)
              {
                int i38 = ConverterID(ModuleSensor.ID_MANAGER_HUMIDITY);
                this.mSensorOn[i38] = true;
              }
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_GYRO_EXPANSION)
            {
              int i37 = ConverterID(ModuleSensor.ID_MANAGER_GYRO_EXPANSION);
              this.mSensorOn[i37] = true;
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_GYRO_POWER)
            {
              int i36 = ConverterID(ModuleSensor.ID_MANAGER_GYRO_POWER);
              this.mSensorOn[i36] = true;
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_GYRO_SELF)
            {
              int i35 = ConverterID(ModuleSensor.ID_MANAGER_GYRO_SELF);
              this.mSensorOn[i35] = true;
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_GYRO_TEMPERATURE)
            {
              int i34 = ConverterID(ModuleSensor.ID_MANAGER_GYRO_TEMPERATURE);
              this.mSensorOn[i34] = true;
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_MAGNETIC_POWER_ON)
            {
              int i33 = ConverterID(ModuleSensor.ID_MANAGER_MAGNETIC_POWER_ON);
              this.mSensorOn[i33] = true;
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_MAGNETIC_POWER_OFF)
            {
              int i32 = ConverterID(ModuleSensor.ID_MANAGER_MAGNETIC_POWER_OFF);
              this.mSensorOn[i32] = true;
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_MAGNETIC_STATUS)
            {
              int i31 = ConverterID(ModuleSensor.ID_MANAGER_MAGNETIC_STATUS);
              this.mSensorOn[i31] = true;
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_MAGNETIC_TEMPERATURE)
            {
              int i30 = ConverterID(ModuleSensor.ID_MANAGER_MAGNETIC_TEMPERATURE);
              this.mSensorOn[i30] = true;
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_MAGNETIC_DAC)
            {
              int i29 = ConverterID(ModuleSensor.ID_MANAGER_MAGNETIC_DAC);
              this.mSensorOn[i29] = true;
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_MAGNETIC_ADC)
            {
              int i28 = ConverterID(ModuleSensor.ID_MANAGER_MAGNETIC_ADC);
              this.mSensorOn[i28] = true;
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_MAGNETIC_SELF)
            {
              int i27 = ConverterID(ModuleSensor.ID_MANAGER_MAGNETIC_SELF);
              this.mSensorOn[i27] = true;
            }
            else if (paramArrayOfInt[i20] == ModuleSensor.ID_MANAGER_MAGNETIC_OFFSETH)
            {
              int i26 = ConverterID(ModuleSensor.ID_MANAGER_MAGNETIC_OFFSETH);
              this.mSensorOn[i26] = true;
            }
            else
            {
              FtUtil.log_d("SensorReadManager", "SensorReadManager", "unregistered-ETC(ModuleSensor ID) : " + paramArrayOfInt[i20]);
            }
          }
        }
      }
    }
    else
    {
      FtUtil.log_e("SensorReadManager", "SensorReadManager", "SensorManager null !!!");
    }
  }
  
  private int ConverterID(int paramInt)
  {
    int i = -1;
    if (paramInt == ModuleSensor.ID_MANAGER_ACCELEROMETER) {
      i = this.SENSOR_ON_ARRAY_INDEX_ACCELEROMETER;
    }
    do
    {
      return i;
      if (paramInt == ModuleSensor.ID_MANAGER_ACCELEROMETER_N_ANGLE) {
        return this.SENSOR_ON_ARRAY_INDEX_ACCELEROMETER_N_ANGLE;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_ACCELEROMETER_SELF) {
        return this.SENSOR_ON_ARRAY_INDEX_ACCELEROMETER_SELF;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_BAROMETER) {
        return this.SENSOR_ON_ARRAY_INDEX_BAROMETER;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_GYRO) {
        return this.SENSOR_ON_ARRAY_INDEX_GYRO;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_GYRO_POWER) {
        return this.SENSOR_ON_ARRAY_INDEX_GYRO_POWER;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_GYRO_EXPANSION) {
        return this.SENSOR_ON_ARRAY_INDEX_GYRO_EXPANSION;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_GYRO_SELF) {
        return this.SENSOR_ON_ARRAY_INDEX_GYRO_SELF;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_GYRO_TEMPERATURE) {
        return this.SENSOR_ON_ARRAY_INDEX_GYRO_TEMPERATURE;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_LIGHT) {
        return this.SENSOR_ON_ARRAY_INDEX_LIGHT;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_LIGHT_CCT) {
        return this.SENSOR_ON_ARRAY_INDEX_LIGHT_CCT;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_MAGNETIC) {
        return this.SENSOR_ON_ARRAY_INDEX_MAGNETIC;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_MAGNETIC_POWER_ON) {
        return this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_POWER_ON;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_MAGNETIC_POWER_OFF) {
        return this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_POWER_OFF;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_MAGNETIC_STATUS) {
        return this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_STATUS;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_MAGNETIC_TEMPERATURE) {
        return this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_TEMPERATURE;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_MAGNETIC_DAC) {
        return this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_DAC;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_MAGNETIC_ADC) {
        return this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_ADC;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_MAGNETIC_SELF) {
        return this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_SELF;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_MAGNETIC_OFFSETH) {
        return this.SENSOR_ON_ARRAY_INDEX_MAGNETIC_OFFSETH;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_PROXIMITY) {
        return this.SENSOR_ON_ARRAY_INDEX_PROXIMITY;
      }
      if (paramInt == ModuleSensor.ID_MANAGER_TEMPERATURE) {
        return this.SENSOR_ON_ARRAY_INDEX_TEMPERATURE;
      }
    } while (paramInt != ModuleSensor.ID_MANAGER_HUMIDITY);
    return this.SENSOR_ON_ARRAY_INDEX_HUMIDITY;
  }
  
  private String dataCheck(float[] paramArrayOfFloat)
  {
    String str = "";
    if (paramArrayOfFloat != null) {
      for (int i = 0; i < paramArrayOfFloat.length; i++)
      {
        str = str + paramArrayOfFloat[i];
        if (i < -1 + paramArrayOfFloat.length) {
          str = str + " , ";
        }
      }
    }
    str = "Data : null";
    return str;
  }
  
  public boolean isSensorOn(int paramInt)
  {
    int i = ConverterID(paramInt);
    return this.mSensorOn[i];
  }
  
  public float[] returnAccelermeter()
  {
    if (ModuleSensor.LOG_LEVEL <= 3) {
      FtUtil.log_e("SensorReadManager", "returnAccelermeter", dataCheck(this.mBuffer_SensorValue_Accelerometer));
    }
    return this.mBuffer_SensorValue_Accelerometer;
  }
  
  public float[] returnBarometer()
  {
    if (ModuleSensor.LOG_LEVEL <= 3) {
      FtUtil.log_e("SensorReadManager", "returnBarometer", dataCheck(this.mBuffer_SensorValue_Barometer));
    }
    return this.mBuffer_SensorValue_Barometer;
  }
  
  public float[] returnGyro()
  {
    if (ModuleSensor.LOG_LEVEL <= 3) {
      FtUtil.log_e("SensorReadManager", "returnGyro", dataCheck(this.mBuffer_SensorValue_Gyro));
    }
    return this.mBuffer_SensorValue_Gyro;
  }
  
  public GyroExpansionData returnGyroExpansion()
  {
    int[] arrayOfInt = new int[3];
    GyroExpansionData localGyroExpansionData = new GyroExpansionData();
    localGyroExpansionData.mReturnValue = this.mSensorManager.runGyroFactoryTest(arrayOfInt, localGyroExpansionData.mData, localGyroExpansionData.mRMSValue);
    localGyroExpansionData.mNoiseBias[0] = (arrayOfInt[0] / 1000.0F);
    localGyroExpansionData.mNoiseBias[1] = (arrayOfInt[1] / 1000.0F);
    localGyroExpansionData.mNoiseBias[2] = (arrayOfInt[2] / 1000.0F);
    localGyroExpansionData.mRMSValue[0] = ((int)(1000.0F * localGyroExpansionData.mRMSValue[0]) / 1000.0F);
    localGyroExpansionData.mRMSValue[1] = ((int)(1000.0F * localGyroExpansionData.mRMSValue[1]) / 1000.0F);
    localGyroExpansionData.mRMSValue[2] = ((int)(1000.0F * localGyroExpansionData.mRMSValue[2]) / 1000.0F);
    if (ModuleSensor.LOG_LEVEL <= 3) {
      FtUtil.log_e("SensorReadManager", "returnGyroExpansion", " (1)MethodReturnValue / (2)Bias[3] / (3)PacketCount[3] / (4)RMS[3] => (1) " + localGyroExpansionData.mReturnValue + " / (2) " + localGyroExpansionData.mNoiseBias[0] + " , " + localGyroExpansionData.mNoiseBias[1] + " , " + localGyroExpansionData.mNoiseBias[2] + " / (3) " + localGyroExpansionData.mData[0] + " , " + localGyroExpansionData.mData[1] + " , " + localGyroExpansionData.mData[2] + " / (4) " + localGyroExpansionData.mRMSValue[0] + " , " + localGyroExpansionData.mRMSValue[1] + " , " + localGyroExpansionData.mRMSValue[2]);
    }
    return localGyroExpansionData;
  }
  
  public float[] returnHumidity()
  {
    if (ModuleSensor.LOG_LEVEL <= 3) {
      FtUtil.log_e("SensorReadManager", "returnHumidity", dataCheck(this.mBuffer_SensorValue_Humidity));
    }
    return this.mBuffer_SensorValue_Humidity;
  }
  
  public float[] returnLight()
  {
    if (ModuleSensor.LOG_LEVEL <= 3) {
      FtUtil.log_e("SensorReadManager", "returnLight", dataCheck(this.mBuffer_SensorValue_Light));
    }
    return this.mBuffer_SensorValue_Light;
  }
  
  public float[] returnMagnetic()
  {
    if (ModuleSensor.LOG_LEVEL <= 3) {
      FtUtil.log_e("SensorReadManager", "returnMagnetic", dataCheck(this.mBuffer_SensorValue_Magnetic));
    }
    return this.mBuffer_SensorValue_Magnetic;
  }
  
  public MagneticExpansionData returnMagneticExpansion_Alps(int paramInt)
  {
    int[] arrayOfInt1 = new int[3];
    int[] arrayOfInt2 = new int[3];
    new int[3];
    MagneticExpansionData localMagneticExpansionData = new MagneticExpansionData();
    if (paramInt == 1)
    {
      arrayOfInt1[0] = 1;
      if (arrayOfInt2 != null)
      {
        localMagneticExpansionData.mPowerOn = new String[2];
        if (localMagneticExpansionData.mReturnValue < 0) {
          break label125;
        }
        localMagneticExpansionData.mPowerOn[0] = "OK";
        localMagneticExpansionData.mPowerOn[1] = "1";
      }
    }
    label125:
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
              do
              {
                do
                {
                  for (;;)
                  {
                    if (paramInt == 2)
                    {
                      arrayOfInt1[0] = 2;
                      if (arrayOfInt2 != null)
                      {
                        localMagneticExpansionData.mPowerOff = new String[2];
                        if (localMagneticExpansionData.mReturnValue < 0) {
                          break;
                        }
                        localMagneticExpansionData.mPowerOff[0] = "OK";
                        localMagneticExpansionData.mPowerOff[1] = "1";
                      }
                    }
                    return localMagneticExpansionData;
                    localMagneticExpansionData.mPowerOn[0] = "NG";
                    localMagneticExpansionData.mPowerOn[1] = "0";
                  }
                  if (paramInt != 3) {
                    break;
                  }
                  arrayOfInt1[0] = 1;
                  arrayOfInt1[0] = 3;
                } while (arrayOfInt2 == null);
                localMagneticExpansionData.mStatus = new String[2];
                if (localMagneticExpansionData.mReturnValue >= 0) {
                  localMagneticExpansionData.mStatus[0] = "OK";
                }
                for (;;)
                {
                  localMagneticExpansionData.mStatus[1] = String.valueOf(arrayOfInt2[0]);
                  break;
                  localMagneticExpansionData.mStatus[0] = "NG";
                }
              } while (paramInt == 2);
              if (paramInt != 4) {
                break;
              }
              arrayOfInt1[0] = 1;
              arrayOfInt1[0] = 3;
              arrayOfInt1[0] = 4;
            } while (arrayOfInt2 == null);
            localMagneticExpansionData.mDAC = new String[4];
            if (localMagneticExpansionData.mReturnValue >= 0) {
              localMagneticExpansionData.mDAC[0] = "OK";
            }
            for (;;)
            {
              localMagneticExpansionData.mDAC[1] = String.valueOf(arrayOfInt2[0]);
              localMagneticExpansionData.mDAC[2] = String.valueOf(arrayOfInt2[1]);
              localMagneticExpansionData.mDAC[3] = String.valueOf(arrayOfInt2[2]);
              break;
              localMagneticExpansionData.mDAC[0] = "NG";
            }
            if (paramInt != 5) {
              break;
            }
            arrayOfInt1[0] = 1;
            arrayOfInt1[0] = 3;
            arrayOfInt1[0] = 4;
            arrayOfInt1[0] = 5;
          } while (arrayOfInt2 == null);
          localMagneticExpansionData.mADC = new String[4];
          if (localMagneticExpansionData.mReturnValue >= 0) {
            localMagneticExpansionData.mADC[0] = "OK";
          }
          for (;;)
          {
            localMagneticExpansionData.mADC[1] = String.valueOf(arrayOfInt2[0]);
            localMagneticExpansionData.mADC[2] = "0";
            localMagneticExpansionData.mADC[3] = "0";
            break;
            localMagneticExpansionData.mADC[0] = "NG";
          }
          if (paramInt != 6) {
            break;
          }
          arrayOfInt1[0] = 1;
          arrayOfInt1[0] = 3;
          arrayOfInt1[0] = 4;
          arrayOfInt1[0] = 5;
          arrayOfInt1[0] = 6;
        } while (arrayOfInt2 == null);
        localMagneticExpansionData.mSelf = new String[4];
        if (localMagneticExpansionData.mReturnValue >= 0) {
          localMagneticExpansionData.mSelf[0] = "OK";
        }
        for (;;)
        {
          localMagneticExpansionData.mSelf[1] = String.valueOf(arrayOfInt2[0]);
          localMagneticExpansionData.mSelf[2] = String.valueOf(arrayOfInt2[1]);
          localMagneticExpansionData.mSelf[3] = String.valueOf(arrayOfInt2[2]);
          break;
          localMagneticExpansionData.mSelf[0] = "NG";
        }
      } while (paramInt != 7);
      arrayOfInt1[0] = 1;
      arrayOfInt1[0] = 3;
      arrayOfInt1[0] = 4;
      arrayOfInt1[0] = 5;
      arrayOfInt1[0] = 6;
      arrayOfInt1[0] = 7;
    } while (arrayOfInt2 == null);
    localMagneticExpansionData.mOffset_H = new String[4];
    if (localMagneticExpansionData.mReturnValue >= 0) {
      localMagneticExpansionData.mOffset_H[0] = "OK";
    }
    for (;;)
    {
      localMagneticExpansionData.mOffset_H[1] = String.valueOf(arrayOfInt2[0]);
      localMagneticExpansionData.mOffset_H[2] = String.valueOf(arrayOfInt2[1]);
      localMagneticExpansionData.mOffset_H[3] = String.valueOf(arrayOfInt2[2]);
      break;
      localMagneticExpansionData.mOffset_H[0] = "NG";
    }
    localMagneticExpansionData.mPowerOff[0] = "NG";
    localMagneticExpansionData.mPowerOff[1] = "0";
    return localMagneticExpansionData;
  }
  
  public MagneticExpansionData returnMagneticExpansion_Asahi(int paramInt)
  {
    String str1 = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_GEOMAGNETIC, SensorDeviceInfo.TARGET_XML);
    int[] arrayOfInt1 = new int[22];
    int[] arrayOfInt2 = new int[23];
    int[] arrayOfInt3 = new int[2];
    MagneticExpansionData localMagneticExpansionData = new MagneticExpansionData();
    localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
    if (ModuleSensor.LOG_LEVEL <= 3)
    {
      FtUtil.log_e("SensorReadManager", "returnMagneticExpansion", "feature : " + str1);
      FtUtil.log_e("SensorReadManager", "returnMagneticExpansion", "MethodReturnValue : " + localMagneticExpansionData.mReturnValue);
      FtUtil.log_e("SensorReadManager", "returnMagneticExpansion", "Parameter1[22] : " + arrayOfInt1[0] + " , " + arrayOfInt1[1] + " , " + arrayOfInt1[2] + " , " + arrayOfInt1[3] + " , " + arrayOfInt1[4] + " , " + arrayOfInt1[5] + " , " + arrayOfInt1[6] + " , " + arrayOfInt1[7] + " , " + arrayOfInt1[8] + " , " + arrayOfInt1[9] + " , " + arrayOfInt1[10] + " , " + arrayOfInt1[11] + " , " + arrayOfInt1[12] + " , " + arrayOfInt1[13] + " , " + arrayOfInt1[14] + " , " + arrayOfInt1[15] + " , " + arrayOfInt1[16] + " , " + arrayOfInt1[17] + " , " + arrayOfInt1[18] + " , " + arrayOfInt1[19] + " , " + arrayOfInt1[20] + " , " + arrayOfInt1[21]);
      FtUtil.log_e("SensorReadManager", "returnMagneticExpansion", "Parameter2[23] : " + arrayOfInt2[0] + " , " + arrayOfInt2[1] + " , " + arrayOfInt2[2] + " , " + arrayOfInt2[3] + " , " + arrayOfInt2[4] + " , " + arrayOfInt2[5] + " , " + arrayOfInt2[6] + " , " + arrayOfInt2[7] + " , " + arrayOfInt2[8] + " , " + arrayOfInt2[9] + " , " + arrayOfInt2[10] + " , " + arrayOfInt2[11] + " , " + arrayOfInt2[12] + " , " + arrayOfInt2[13] + " , " + arrayOfInt2[14] + " , " + arrayOfInt2[15] + " , " + arrayOfInt2[16] + " , " + arrayOfInt2[17] + " , " + arrayOfInt2[18] + " , " + arrayOfInt2[19] + " , " + arrayOfInt2[20] + " , " + arrayOfInt2[21] + " , " + arrayOfInt2[22]);
    }
    int n;
    label815:
    int m;
    label853:
    label887:
    int j;
    label919:
    String str3;
    int k;
    label989:
    label1013:
    int i;
    String str2;
    if (((paramInt == 1) || (paramInt == 8)) && (arrayOfInt1 != null))
    {
      n = 1;
      if (arrayOfInt1[3] != 72)
      {
        n = 0;
        localMagneticExpansionData.mPowerOn = new String[2];
        if (n == 0) {
          break label1387;
        }
        localMagneticExpansionData.mPowerOn[0] = "OK";
        localMagneticExpansionData.mPowerOn[1] = "1";
      }
    }
    else
    {
      if (((paramInt == 3) || (paramInt == 8)) && (arrayOfInt1 != null))
      {
        m = 1;
        if ((arrayOfInt1[17] != 0) && (arrayOfInt1[17] != 255)) {
          break label1410;
        }
        m = 0;
        localMagneticExpansionData.mStatus = new String[2];
        if (m == 0) {
          break label1456;
        }
        localMagneticExpansionData.mStatus[0] = "OK";
        localMagneticExpansionData.mStatus[1] = "1";
      }
      if (((paramInt != 2) && (paramInt == 8)) && (((paramInt == 4) || (paramInt == 8)) && (!str1.equals("AK8973")))) {
        break label1479;
      }
      if (((paramInt == 5) || (paramInt == 8)) && (arrayOfInt2 != null))
      {
        j = 1;
        str3 = SensorCalculator.checkSpecMagneticADC(arrayOfInt2[4], arrayOfInt2[6], arrayOfInt2[8]);
        boolean bool = str1.equals("AK8963C_MANAGER");
        k = 0;
        if (bool) {
          k = 16;
        }
        if (arrayOfInt2[3] == 1) {
          break label1585;
        }
        j = 0;
        localMagneticExpansionData.mADC = new String[4];
        if (j == 0) {
          break label1629;
        }
        localMagneticExpansionData.mADC[0] = "OK";
        localMagneticExpansionData.mADC[1] = ("" + arrayOfInt2[4]);
        localMagneticExpansionData.mADC[2] = ("" + arrayOfInt2[6]);
        localMagneticExpansionData.mADC[3] = ("" + arrayOfInt2[8]);
      }
      if (((paramInt == 6) || (paramInt == 8)) && (arrayOfInt2 != null))
      {
        i = 1;
        str2 = SensorCalculator.checkSpecMagneticSelf(arrayOfInt2[15], arrayOfInt2[17], arrayOfInt2[19]);
        if (arrayOfInt2[14] == 1) {
          break label1642;
        }
        i = 0;
        label1157:
        localMagneticExpansionData.mSelf = new String[4];
        if (i == 0) {
          break label1670;
        }
        localMagneticExpansionData.mSelf[0] = "OK";
      }
    }
    for (;;)
    {
      localMagneticExpansionData.mSelf[1] = ("" + arrayOfInt2[15]);
      localMagneticExpansionData.mSelf[2] = ("" + arrayOfInt2[17]);
      localMagneticExpansionData.mSelf[3] = ("" + arrayOfInt2[19]);
      return localMagneticExpansionData;
      if ((arrayOfInt1[4] < 0) || (arrayOfInt1[4] > 255))
      {
        n = 0;
        break;
      }
      if ((arrayOfInt1[5] != 0) || (arrayOfInt1[6] != 0) || (arrayOfInt1[7] != 0) || (arrayOfInt1[8] != 0) || (arrayOfInt1[9] != 0) || (arrayOfInt1[10] != 0) || (arrayOfInt1[11] != 0) || (arrayOfInt1[12] != 0) || (arrayOfInt1[13] != 0))
      {
        n = 0;
        break;
      }
      if ((arrayOfInt1[14] >= 0) || (arrayOfInt1[14] <= 1)) {
        break;
      }
      n = 0;
      break;
      label1387:
      localMagneticExpansionData.mPowerOn[0] = "NG";
      localMagneticExpansionData.mPowerOn[1] = "0";
      break label815;
      label1410:
      if ((arrayOfInt1[18] == 0) || (arrayOfInt1[18] == 255))
      {
        m = 0;
        break label853;
      }
      if ((arrayOfInt1[19] != 0) && (arrayOfInt1[19] != 255)) {
        break label853;
      }
      m = 0;
      break label853;
      label1456:
      localMagneticExpansionData.mStatus[0] = "NG";
      localMagneticExpansionData.mStatus[1] = "0";
      break label887;
      label1479:
      if (((!str1.equals("AK8975")) && (!str1.equals("AK8963C_MANAGER"))) || (arrayOfInt1 == null)) {
        break label919;
      }
      localMagneticExpansionData.mDAC = new String[4];
      if (arrayOfInt1[21] == 0)
      {
        localMagneticExpansionData.mDAC[0] = "OK";
        localMagneticExpansionData.mDAC[1] = "1";
      }
      for (;;)
      {
        localMagneticExpansionData.mDAC[2] = "0";
        localMagneticExpansionData.mDAC[3] = "0";
        break;
        localMagneticExpansionData.mDAC[0] = "NG";
        localMagneticExpansionData.mDAC[1] = "0";
      }
      label1585:
      if (arrayOfInt2[10] != k)
      {
        j = 0;
        break label989;
      }
      if (str3.equals("OK")) {
        break label989;
      }
      FtUtil.log_e("SensorReadManager", "returnMagneticExpansion_Asahi", "ADC specResult : false");
      j = 0;
      break label989;
      label1629:
      localMagneticExpansionData.mADC[0] = "NG";
      break label1013;
      label1642:
      if (str2.equals("OK")) {
        break label1157;
      }
      FtUtil.log_e("SensorReadManager", "returnMagneticExpansion_Asahi", "Self specResult : false");
      i = 0;
      break label1157;
      label1670:
      localMagneticExpansionData.mSelf[0] = "NG";
    }
  }
  
  public MagneticExpansionData returnMagneticExpansion_Bosch(int paramInt)
  {
    int[] arrayOfInt1 = new int[3];
    int[] arrayOfInt2 = new int[3];
    int[] arrayOfInt3 = new int[3];
    MagneticExpansionData localMagneticExpansionData = new MagneticExpansionData();
    if (paramInt == 1)
    {
      arrayOfInt1[0] = 1;
      localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
      localMagneticExpansionData.mPowerOn = new String[2];
      if (localMagneticExpansionData.mReturnValue == 0)
      {
        localMagneticExpansionData.mPowerOn[0] = "OK";
        localMagneticExpansionData.mPowerOn[1] = "1";
      }
    }
    else
    {
      if (paramInt != 3) {
        break label222;
      }
      arrayOfInt1[0] = 3;
      localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
      FtUtil.log_d("SensorReadManager", "returnMagneticExpansion_Bosch", "parameter2[0] : " + arrayOfInt2[0]);
      if (arrayOfInt2 != null)
      {
        localMagneticExpansionData.mStatus = new String[2];
        if (arrayOfInt2[0] != 1) {
          break label209;
        }
        localMagneticExpansionData.mStatus[0] = "OK";
        label170:
        localMagneticExpansionData.mStatus[1] = String.valueOf(arrayOfInt2[0]);
      }
    }
    label209:
    label222:
    label501:
    do
    {
      do
      {
        do
        {
          return localMagneticExpansionData;
          localMagneticExpansionData.mPowerOn[0] = "NG";
          localMagneticExpansionData.mPowerOn[1] = "0";
          break;
          localMagneticExpansionData.mStatus[0] = "NG";
          break label170;
          if (paramInt != 5) {
            break label501;
          }
          arrayOfInt1[0] = 5;
          localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
        } while (arrayOfInt2 == null);
        String str2 = SensorCalculator.checkSpecMagneticADC(arrayOfInt2[0], arrayOfInt2[1], arrayOfInt2[2]);
        FtUtil.log_d("SensorReadManager", "update", "specResult : " + str2);
        localMagneticExpansionData.mADC = new String[4];
        FtUtil.log_d("SensorReadManager", "update", "parameter2[0] : " + arrayOfInt2[0]);
        FtUtil.log_d("SensorReadManager", "update", "parameter2[1] : " + arrayOfInt2[1]);
        FtUtil.log_d("SensorReadManager", "update", "parameter2[2] : " + arrayOfInt2[2]);
        FtUtil.log_d("SensorReadManager", "update", "returnData.mReturnValue : " + localMagneticExpansionData.mReturnValue);
        if (!str2.equals("OK")) {
          localMagneticExpansionData.mADC[0] = "NG";
        }
        for (;;)
        {
          localMagneticExpansionData.mADC[1] = String.valueOf(arrayOfInt2[0]);
          localMagneticExpansionData.mADC[2] = String.valueOf(arrayOfInt2[1]);
          localMagneticExpansionData.mADC[3] = String.valueOf(arrayOfInt2[2]);
          return localMagneticExpansionData;
          localMagneticExpansionData.mADC[0] = "OK";
        }
      } while (paramInt != 6);
      arrayOfInt1[0] = 6;
      localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
    } while (arrayOfInt2 == null);
    String str1 = SensorCalculator.checkSpecMagneticSelf(0, 0, arrayOfInt2[0]);
    FtUtil.log_d("SensorReadManager", "update", "specResult : " + str1);
    localMagneticExpansionData.mSelf = new String[2];
    FtUtil.log_d("SensorReadManager", "update", "parameter2[0] : " + arrayOfInt2[0]);
    if (!str1.equals("OK")) {
      localMagneticExpansionData.mSelf[0] = "NG";
    }
    for (;;)
    {
      localMagneticExpansionData.mSelf[1] = String.valueOf(arrayOfInt2[0]);
      return localMagneticExpansionData;
      localMagneticExpansionData.mSelf[0] = "OK";
    }
  }
  
  public MagneticExpansionData returnMagneticExpansion_Yamaha(int paramInt)
  {
    String str = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_GEOMAGNETIC, SensorDeviceInfo.TARGET_XML);
    int[] arrayOfInt1 = new int[3];
    int[] arrayOfInt2 = new int[3];
    int[] arrayOfInt3 = new int[3];
    MagneticExpansionData localMagneticExpansionData = new MagneticExpansionData();
    if (paramInt == 1)
    {
      arrayOfInt1[0] = 1;
      localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
      if (arrayOfInt2 != null)
      {
        localMagneticExpansionData.mPowerOn = new String[2];
        if (localMagneticExpansionData.mReturnValue < 0) {
          break label173;
        }
        localMagneticExpansionData.mPowerOn[0] = "OK";
        localMagneticExpansionData.mPowerOn[1] = "1";
      }
    }
    label173:
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
              for (;;)
              {
                if (paramInt == 2)
                {
                  arrayOfInt1[0] = 2;
                  localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
                  if (arrayOfInt2 != null)
                  {
                    localMagneticExpansionData.mPowerOff = new String[2];
                    if (localMagneticExpansionData.mReturnValue < 0) {
                      break label1127;
                    }
                    localMagneticExpansionData.mPowerOff[0] = "OK";
                    localMagneticExpansionData.mPowerOff[1] = "1";
                  }
                }
                return localMagneticExpansionData;
                localMagneticExpansionData.mPowerOn[0] = "NG";
                localMagneticExpansionData.mPowerOn[1] = "0";
                continue;
                if (paramInt == 3)
                {
                  arrayOfInt1[0] = 1;
                  localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
                  arrayOfInt1[0] = 3;
                  localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
                  if (arrayOfInt2 != null)
                  {
                    localMagneticExpansionData.mStatus = new String[2];
                    if (localMagneticExpansionData.mReturnValue >= 0) {
                      localMagneticExpansionData.mStatus[0] = "OK";
                    }
                    for (;;)
                    {
                      localMagneticExpansionData.mStatus[1] = String.valueOf(arrayOfInt2[0]);
                      break;
                      localMagneticExpansionData.mStatus[0] = "NG";
                    }
                  }
                }
                else
                {
                  if (paramInt != 2) {
                    break;
                  }
                  if ((str.equals("YAS530C")) || (str.equals("YAS532B")) || (str.equals("YAS532")))
                  {
                    localMagneticExpansionData.mTemperature = new String[2];
                    localMagneticExpansionData.mTemperature[0] = "None";
                    localMagneticExpansionData.mTemperature[1] = "0";
                  }
                }
              }
              if (paramInt != 4) {
                break;
              }
              arrayOfInt1[0] = 1;
              localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
              arrayOfInt1[0] = 3;
              localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
              arrayOfInt1[0] = 4;
              localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
            } while (arrayOfInt2 == null);
            localMagneticExpansionData.mDAC = new String[4];
            if (localMagneticExpansionData.mReturnValue >= 0) {
              localMagneticExpansionData.mDAC[0] = "OK";
            }
            for (;;)
            {
              localMagneticExpansionData.mDAC[1] = String.valueOf(arrayOfInt2[0]);
              localMagneticExpansionData.mDAC[2] = String.valueOf(arrayOfInt2[1]);
              localMagneticExpansionData.mDAC[3] = String.valueOf(arrayOfInt2[2]);
              break;
              localMagneticExpansionData.mDAC[0] = "NG";
            }
            if (paramInt != 5) {
              break;
            }
            arrayOfInt1[0] = 1;
            localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
            arrayOfInt1[0] = 3;
            localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
            arrayOfInt1[0] = 4;
            localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
            arrayOfInt1[0] = 5;
            localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
          } while (arrayOfInt2 == null);
          localMagneticExpansionData.mADC = new String[4];
          if (localMagneticExpansionData.mReturnValue >= 0) {
            localMagneticExpansionData.mADC[0] = "OK";
          }
          for (;;)
          {
            localMagneticExpansionData.mADC[1] = String.valueOf(arrayOfInt2[0]);
            localMagneticExpansionData.mADC[2] = "0";
            localMagneticExpansionData.mADC[3] = "0";
            break;
            localMagneticExpansionData.mADC[0] = "NG";
          }
          if (paramInt != 6) {
            break;
          }
          arrayOfInt1[0] = 1;
          localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
          arrayOfInt1[0] = 3;
          localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
          arrayOfInt1[0] = 4;
          localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
          arrayOfInt1[0] = 5;
          localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
          arrayOfInt1[0] = 6;
          localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
        } while (arrayOfInt2 == null);
        localMagneticExpansionData.mSelf = new String[4];
        if (localMagneticExpansionData.mReturnValue >= 0) {
          localMagneticExpansionData.mSelf[0] = "OK";
        }
        for (;;)
        {
          localMagneticExpansionData.mSelf[1] = String.valueOf(arrayOfInt2[0]);
          localMagneticExpansionData.mSelf[2] = String.valueOf(arrayOfInt2[1]);
          localMagneticExpansionData.mSelf[3] = String.valueOf(arrayOfInt2[2]);
          break;
          localMagneticExpansionData.mSelf[0] = "NG";
        }
      } while (paramInt != 7);
      arrayOfInt1[0] = 1;
      localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
      arrayOfInt1[0] = 3;
      localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
      arrayOfInt1[0] = 4;
      localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
      arrayOfInt1[0] = 5;
      localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
      arrayOfInt1[0] = 6;
      localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
      arrayOfInt1[0] = 7;
      localMagneticExpansionData.mReturnValue = this.mSensorManager.runMagFactoryTest(arrayOfInt1, arrayOfInt2, arrayOfInt3);
    } while (arrayOfInt2 == null);
    localMagneticExpansionData.mOffset_H = new String[4];
    if (localMagneticExpansionData.mReturnValue >= 0) {
      localMagneticExpansionData.mOffset_H[0] = "OK";
    }
    for (;;)
    {
      localMagneticExpansionData.mOffset_H[1] = String.valueOf(arrayOfInt2[0]);
      localMagneticExpansionData.mOffset_H[2] = String.valueOf(arrayOfInt2[1]);
      localMagneticExpansionData.mOffset_H[3] = String.valueOf(arrayOfInt2[2]);
      break;
      localMagneticExpansionData.mOffset_H[0] = "NG";
    }
    label1127:
    localMagneticExpansionData.mPowerOff[0] = "NG";
    localMagneticExpansionData.mPowerOff[1] = "0";
    return localMagneticExpansionData;
  }
  
  public float[] returnProximity()
  {
    if (ModuleSensor.LOG_LEVEL <= 3) {
      FtUtil.log_e("SensorReadManager", "returnProximity", dataCheck(this.mBuffer_SensorValue_Proximity));
    }
    return this.mBuffer_SensorValue_Proximity;
  }
  
  public float[] returnTemperature()
  {
    if (ModuleSensor.LOG_LEVEL <= 3) {
      FtUtil.log_e("SensorReadManager", "returnTemperature", dataCheck(this.mBuffer_SensorValue_Temperature));
    }
    return this.mBuffer_SensorValue_Temperature;
  }
  
  public void sensorOff()
  {
    FtUtil.log_e("SensorReadManager", "sensorOff", "Sensor Off");
    if (this.mSensorManager != null) {
      this.mSensorManager.unregisterListener(this.mSensorListener);
    }
    this.mSensorManager = null;
    this.mSensorListener = null;
    this.mAccelerometerSensor = null;
    this.mBarometerSensor = null;
    this.mGyroscopeSensor = null;
    this.mLightSensor = null;
    this.mMagneticSensor = null;
    this.mProximitySensor = null;
    this.mTemperatureSensor = null;
    this.mHumiditySensor = null;
    this.mBuffer_SensorValue_Accelerometer = null;
    this.mBuffer_SensorValue_Barometer = null;
    this.mBuffer_SensorValue_Gyro = null;
    this.mBuffer_SensorValue_Light = null;
    this.mBuffer_SensorValue_Magnetic = null;
    this.mBuffer_SensorValue_Proximity = null;
    this.mBuffer_SensorValue_Temperature = null;
    this.mBuffer_SensorValue_Humidity = null;
    for (int i = 0; i < this.SENSOR_ON_ARRAY_LENGTH; i++) {
      this.mSensorOn[i] = false;
    }
  }
  
  public class GyroExpansionData
  {
    public short[] mData = new short[3];
    public float[] mNoiseBias = new float[3];
    public float[] mRMSValue = new float[3];
    public int mReturnValue;
    
    public GyroExpansionData() {}
  }
  
  public class MagneticExpansionData
  {
    public String[] mADC = null;
    public String[] mADC2 = null;
    public String[] mDAC = null;
    public String[] mOffset_H = null;
    public String[] mPowerOff = null;
    public String[] mPowerOn = null;
    public int mReturnValue;
    public String[] mSelf = null;
    public String[] mStatus = null;
    public String[] mTemperature = null;
    
    public MagneticExpansionData() {}
  }
  
  private class SensorListener
    implements SensorEventListener
  {
    private SensorListener() {}
    
    public void onAccuracyChanged(Sensor paramSensor, int paramInt) {}
    
    public void onSensorChanged(SensorEvent paramSensorEvent)
    {
      switch (paramSensorEvent.sensor.getType())
      {
      case 3: 
      case 7: 
      case 9: 
      case 10: 
      case 11: 
      default: 
        return;
      case 1: 
        SensorReadManager.access$102(SensorReadManager.this, (float[])paramSensorEvent.values.clone());
        return;
      case 6: 
        SensorReadManager.access$202(SensorReadManager.this, (float[])paramSensorEvent.values.clone());
        return;
      case 4: 
        SensorReadManager.access$302(SensorReadManager.this, (float[])paramSensorEvent.values.clone());
        return;
      case 5: 
        SensorReadManager.access$402(SensorReadManager.this, (float[])paramSensorEvent.values.clone());
        return;
      case 2: 
        SensorReadManager.access$502(SensorReadManager.this, (float[])paramSensorEvent.values.clone());
        return;
      case 8: 
        SensorReadManager.access$602(SensorReadManager.this, (float[])paramSensorEvent.values.clone());
        return;
      case 13: 
        SensorReadManager.access$702(SensorReadManager.this, (float[])paramSensorEvent.values.clone());
        return;
      }
      SensorReadManager.access$802(SensorReadManager.this, (float[])paramSensorEvent.values.clone());
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.SensorReadManager
 * JD-Core Version:    0.7.1
 */