package com.sec.factory.modules;

import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Kernel;
import java.util.Timer;
import java.util.TimerTask;

public class SensorReadFile
{
  private final String CLASS_NAME = "SensorReadFile";
  private final int INFO_ARRAY_INDEX_ACCELEROMETER;
  private final int INFO_ARRAY_INDEX_ACCELEROMETER_CAL;
  private final int INFO_ARRAY_INDEX_ACCELEROMETER_INTPIN;
  private final int INFO_ARRAY_INDEX_ACCELEROMETER_N_ANGLE;
  private final int INFO_ARRAY_INDEX_ACCELEROMETER_SELF;
  private final int INFO_ARRAY_INDEX_BAROMETER_EEPROM;
  private final int INFO_ARRAY_INDEX_GYRO_POWER;
  private final int INFO_ARRAY_INDEX_GYRO_SELFTEST;
  private final int INFO_ARRAY_INDEX_GYRO_TEMPERATURE;
  private final int INFO_ARRAY_INDEX_LIGHT_ADC;
  private final int INFO_ARRAY_INDEX_LIGHT_RGBW;
  private final int INFO_ARRAY_INDEX_MAGNETIC_ADC;
  private final int INFO_ARRAY_INDEX_MAGNETIC_DAC;
  private final int INFO_ARRAY_INDEX_MAGNETIC_POWER_OFF;
  private final int INFO_ARRAY_INDEX_MAGNETIC_POWER_ON;
  private final int INFO_ARRAY_INDEX_MAGNETIC_SELF;
  private final int INFO_ARRAY_INDEX_MAGNETIC_STATUS;
  private final int INFO_ARRAY_INDEX_MAGNETIC_TEMP;
  private final int INFO_ARRAY_INDEX_PROXIMITY_ADC;
  private final int INFO_ARRAY_INDEX_PROXIMITY_AVG;
  private final int INFO_ARRAY_INDEX_PROXIMITY_OFFSET;
  private final int INFO_ARRAY_LENGTH;
  private int SENSOR_READ_INTERVAL;
  private int dummy = 0;
  private String mFeature_Magnetic;
  private Info[] mInfo;
  private boolean mIsLoop;
  private int[] mModuleSensorIDArray;
  private String[] mTemp_String;
  private Timer mTimer;
  
  public SensorReadFile(int[] paramArrayOfInt)
  {
    int i = this.dummy;
    this.dummy = (i + 1);
    this.INFO_ARRAY_INDEX_ACCELEROMETER = i;
    int j = this.dummy;
    this.dummy = (j + 1);
    this.INFO_ARRAY_INDEX_ACCELEROMETER_N_ANGLE = j;
    int k = this.dummy;
    this.dummy = (k + 1);
    this.INFO_ARRAY_INDEX_ACCELEROMETER_SELF = k;
    int m = this.dummy;
    this.dummy = (m + 1);
    this.INFO_ARRAY_INDEX_ACCELEROMETER_CAL = m;
    int n = this.dummy;
    this.dummy = (n + 1);
    this.INFO_ARRAY_INDEX_ACCELEROMETER_INTPIN = n;
    int i1 = this.dummy;
    this.dummy = (i1 + 1);
    this.INFO_ARRAY_INDEX_BAROMETER_EEPROM = i1;
    int i2 = this.dummy;
    this.dummy = (i2 + 1);
    this.INFO_ARRAY_INDEX_GYRO_POWER = i2;
    int i3 = this.dummy;
    this.dummy = (i3 + 1);
    this.INFO_ARRAY_INDEX_GYRO_TEMPERATURE = i3;
    int i4 = this.dummy;
    this.dummy = (i4 + 1);
    this.INFO_ARRAY_INDEX_GYRO_SELFTEST = i4;
    int i5 = this.dummy;
    this.dummy = (i5 + 1);
    this.INFO_ARRAY_INDEX_LIGHT_ADC = i5;
    int i6 = this.dummy;
    this.dummy = (i6 + 1);
    this.INFO_ARRAY_INDEX_LIGHT_RGBW = i6;
    int i7 = this.dummy;
    this.dummy = (i7 + 1);
    this.INFO_ARRAY_INDEX_MAGNETIC_POWER_ON = i7;
    int i8 = this.dummy;
    this.dummy = (i8 + 1);
    this.INFO_ARRAY_INDEX_MAGNETIC_POWER_OFF = i8;
    int i9 = this.dummy;
    this.dummy = (i9 + 1);
    this.INFO_ARRAY_INDEX_MAGNETIC_STATUS = i9;
    int i10 = this.dummy;
    this.dummy = (i10 + 1);
    this.INFO_ARRAY_INDEX_MAGNETIC_TEMP = i10;
    int i11 = this.dummy;
    this.dummy = (i11 + 1);
    this.INFO_ARRAY_INDEX_MAGNETIC_DAC = i11;
    int i12 = this.dummy;
    this.dummy = (i12 + 1);
    this.INFO_ARRAY_INDEX_MAGNETIC_ADC = i12;
    int i13 = this.dummy;
    this.dummy = (i13 + 1);
    this.INFO_ARRAY_INDEX_MAGNETIC_SELF = i13;
    int i14 = this.dummy;
    this.dummy = (i14 + 1);
    this.INFO_ARRAY_INDEX_PROXIMITY_ADC = i14;
    int i15 = this.dummy;
    this.dummy = (i15 + 1);
    this.INFO_ARRAY_INDEX_PROXIMITY_AVG = i15;
    int i16 = this.dummy;
    this.dummy = (i16 + 1);
    this.INFO_ARRAY_INDEX_PROXIMITY_OFFSET = i16;
    this.INFO_ARRAY_LENGTH = this.dummy;
    this.mInfo = new Info[this.INFO_ARRAY_LENGTH];
    this.mIsLoop = false;
    this.mTimer = null;
    this.SENSOR_READ_INTERVAL = 50;
    this.mTemp_String = null;
    this.mFeature_Magnetic = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_GEOMAGNETIC, SensorDeviceInfo.TARGET_XML);
    FtUtil.log_e("SensorReadFile", "SensorReadFile", "Sensor On");
    this.mModuleSensorIDArray = paramArrayOfInt;
    init();
  }
  
  private int ConverterID(int paramInt)
  {
    int i = -1;
    if (paramInt == ModuleSensor.ID_FILE____ACCELEROMETER) {
      i = this.INFO_ARRAY_INDEX_ACCELEROMETER;
    }
    do
    {
      return i;
      if (paramInt == ModuleSensor.ID_FILE____ACCELEROMETER_N_ANGLE) {
        return this.INFO_ARRAY_INDEX_ACCELEROMETER_N_ANGLE;
      }
      if (paramInt == ModuleSensor.ID_FILE____ACCELEROMETER_SELF) {
        return this.INFO_ARRAY_INDEX_ACCELEROMETER_SELF;
      }
      if (paramInt == ModuleSensor.ID_FILE____ACCELEROMETER_CAL) {
        return this.INFO_ARRAY_INDEX_ACCELEROMETER_CAL;
      }
      if (paramInt == ModuleSensor.ID_FILE____ACCELEROMETER_INTPIN) {
        return this.INFO_ARRAY_INDEX_ACCELEROMETER_INTPIN;
      }
      if (paramInt == ModuleSensor.ID_FILE____BAROMETER_EEPROM) {
        return this.INFO_ARRAY_INDEX_BAROMETER_EEPROM;
      }
      if (paramInt == ModuleSensor.ID_FILE____GYRO_POWER) {
        return this.INFO_ARRAY_INDEX_GYRO_POWER;
      }
      if (paramInt == ModuleSensor.ID_FILE____GYRO_TEMPERATURE) {
        return this.INFO_ARRAY_INDEX_GYRO_TEMPERATURE;
      }
      if (paramInt == ModuleSensor.ID_FILE____GYRO_SELFTEST) {
        return this.INFO_ARRAY_INDEX_GYRO_SELFTEST;
      }
      if (paramInt == ModuleSensor.ID_FILE____LIGHT_ADC) {
        return this.INFO_ARRAY_INDEX_LIGHT_ADC;
      }
      if (paramInt == ModuleSensor.ID_FILE____LIGHT_RGBW) {
        return this.INFO_ARRAY_INDEX_LIGHT_RGBW;
      }
      if (paramInt == ModuleSensor.ID_FILE____MAGNETIC_POWER_ON) {
        return this.INFO_ARRAY_INDEX_MAGNETIC_POWER_ON;
      }
      if (paramInt == ModuleSensor.ID_FILE____MAGNETIC_POWER_OFF) {
        return this.INFO_ARRAY_INDEX_MAGNETIC_POWER_OFF;
      }
      if (paramInt == ModuleSensor.ID_FILE____MAGNETIC_STATUS) {
        return this.INFO_ARRAY_INDEX_MAGNETIC_STATUS;
      }
      if (paramInt == ModuleSensor.ID_FILE____MAGNETIC_TEMPERATURE) {
        return this.INFO_ARRAY_INDEX_MAGNETIC_TEMP;
      }
      if (paramInt == ModuleSensor.ID_FILE____MAGNETIC_DAC) {
        return this.INFO_ARRAY_INDEX_MAGNETIC_DAC;
      }
      if (paramInt == ModuleSensor.ID_FILE____MAGNETIC_ADC) {
        return this.INFO_ARRAY_INDEX_MAGNETIC_ADC;
      }
      if (paramInt == ModuleSensor.ID_FILE____MAGNETIC_SELF) {
        return this.INFO_ARRAY_INDEX_MAGNETIC_SELF;
      }
      if (paramInt == ModuleSensor.ID_FILE____PROXIMITY_ADC) {
        return this.INFO_ARRAY_INDEX_PROXIMITY_ADC;
      }
      if (paramInt == ModuleSensor.ID_FILE____PROXIMITY_AVG) {
        return this.INFO_ARRAY_INDEX_PROXIMITY_AVG;
      }
    } while (paramInt != ModuleSensor.ID_FILE____PROXIMITY_OFFSET);
    return this.INFO_ARRAY_INDEX_PROXIMITY_OFFSET;
  }
  
  private void checkSpec(int paramInt1, int paramInt2)
  {
    if (this.mInfo[paramInt2].mBuffer_SensorValue == null) {}
    do
    {
      do
      {
        do
        {
          for (;;)
          {
            return;
            if (paramInt1 == ModuleSensor.ID_FILE____MAGNETIC_DAC)
            {
              FtUtil.log_d("SensorReadFile", "checkSpec", "ID_FILE____MAGNETIC_DAC");
              if (this.mInfo[paramInt2].mBuffer_SensorValue.length == 4) {
                this.mInfo[paramInt2].mBuffer_SensorValue[0] = SensorCalculator.checkSpecMagneticDAC(Integer.valueOf(this.mInfo[paramInt2].mBuffer_SensorValue[1]).intValue(), Integer.valueOf(this.mInfo[paramInt2].mBuffer_SensorValue[2]).intValue(), Integer.valueOf(this.mInfo[paramInt2].mBuffer_SensorValue[3]).intValue());
              }
              if (!this.mInfo[paramInt2].mBuffer_SensorValue[0].equals("OK"))
              {
                FtUtil.log_d("SensorReadFile", "checkSpec", "DAC [0] : " + this.mInfo[paramInt2].mBuffer_SensorValue[0]);
                this.mInfo[paramInt2].mBuffer_SensorValue[0] = "NG";
              }
            }
            else
            {
              if (paramInt1 != ModuleSensor.ID_FILE____MAGNETIC_ADC) {
                break;
              }
              FtUtil.log_d("SensorReadFile", "checkSpec", "ID_FILE____MAGNETIC_ADC");
              if (this.mInfo[paramInt2].mBuffer_SensorValue.length == 4) {
                this.mInfo[paramInt2].mBuffer_SensorValue[0] = SensorCalculator.checkSpecMagneticADC(Integer.valueOf(this.mInfo[paramInt2].mBuffer_SensorValue[1]).intValue(), Integer.valueOf(this.mInfo[paramInt2].mBuffer_SensorValue[2]).intValue(), Integer.valueOf(this.mInfo[paramInt2].mBuffer_SensorValue[3]).intValue());
              }
              while (!this.mInfo[paramInt2].mBuffer_SensorValue[0].equals("OK"))
              {
                FtUtil.log_d("SensorReadFile", "checkSpec", "ADC [0] : " + this.mInfo[paramInt2].mBuffer_SensorValue[0]);
                this.mInfo[paramInt2].mBuffer_SensorValue[0] = "NG";
                return;
                if (this.mInfo[paramInt2].mBuffer_SensorValue.length == 3)
                {
                  this.mTemp_String = this.mInfo[paramInt2].mBuffer_SensorValue;
                  this.mInfo[paramInt2].mBuffer_SensorValue = new String[4];
                  this.mInfo[paramInt2].mBuffer_SensorValue[1] = this.mTemp_String[0];
                  this.mInfo[paramInt2].mBuffer_SensorValue[2] = this.mTemp_String[1];
                  this.mInfo[paramInt2].mBuffer_SensorValue[3] = this.mTemp_String[2];
                  this.mInfo[paramInt2].mBuffer_SensorValue[0] = SensorCalculator.checkSpecMagneticADC(Integer.valueOf(this.mInfo[paramInt2].mBuffer_SensorValue[1]).intValue(), Integer.valueOf(this.mInfo[paramInt2].mBuffer_SensorValue[2]).intValue(), Integer.valueOf(this.mInfo[paramInt2].mBuffer_SensorValue[3]).intValue());
                }
              }
            }
          }
        } while (paramInt1 != ModuleSensor.ID_FILE____MAGNETIC_SELF);
        FtUtil.log_d("SensorReadFile", "checkSpec", "ID_FILE____MAGNETIC_SELF");
        if ((!this.mFeature_Magnetic.equals("HSCDTD004")) && (!this.mFeature_Magnetic.equals("HSCDTD004A")) && (!this.mFeature_Magnetic.equals("HSCDTD006A")) && (!this.mFeature_Magnetic.equals("HSCDTD008A"))) {
          break;
        }
        this.mTemp_String = this.mInfo[paramInt2].mBuffer_SensorValue;
      } while (this.mInfo[paramInt2].mBuffer_SensorValue.length != 2);
      this.mInfo[paramInt2].mBuffer_SensorValue = new String[2];
      this.mInfo[paramInt2].mBuffer_SensorValue[0] = this.mTemp_String[0];
      return;
      if (this.mInfo[paramInt2].mBuffer_SensorValue.length == 4) {
        this.mInfo[paramInt2].mBuffer_SensorValue[0] = SensorCalculator.checkSpecMagneticSelf(Integer.valueOf(this.mInfo[paramInt2].mBuffer_SensorValue[1]).intValue(), Integer.valueOf(this.mInfo[paramInt2].mBuffer_SensorValue[2]).intValue(), Integer.valueOf(this.mInfo[paramInt2].mBuffer_SensorValue[3]).intValue());
      }
    } while (this.mInfo[paramInt2].mBuffer_SensorValue[0].equals("OK"));
    FtUtil.log_d("SensorReadFile", "checkSpec", "Self [0] : " + this.mInfo[paramInt2].mBuffer_SensorValue[0]);
    this.mInfo[paramInt2].mBuffer_SensorValue[0] = "NG";
  }
  
  private String dataCheck(String[] paramArrayOfString)
  {
    String str = "";
    if (paramArrayOfString != null) {
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        str = str + paramArrayOfString[i];
        if (i < -1 + paramArrayOfString.length) {
          str = str + " , ";
        }
      }
    }
    str = "Data : null";
    return str;
  }
  
  private void init()
  {
    FtUtil.log_d("SensorReadFile", "init", "INFO_ARRAY_LENGTH : " + this.INFO_ARRAY_LENGTH);
    int i = -1;
    if (this.mModuleSensorIDArray != null)
    {
      int j = 0;
      if (j < this.mModuleSensorIDArray.length)
      {
        if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____ACCELEROMETER)
        {
          i = ConverterID(ModuleSensor.ID_FILE____ACCELEROMETER);
          if (-1 < i) {
            this.mInfo[i] = new Info("ID_FILE____ACCELEROMETER", "ACCEL_SENSOR_RAW");
          }
          label96:
          if ((-1 >= i) || (i >= this.INFO_ARRAY_LENGTH)) {
            break label1133;
          }
          FtUtil.log_e("SensorReadFile", "init", "mInfo - mName : " + this.mInfo[i].mName);
          FtUtil.log_e("SensorReadFile", "init", "mInfo - mFilePath : " + this.mInfo[i].mFilePath);
          FtUtil.log_e("SensorReadFile", "init", "mInfo - mIsExistFile : " + this.mInfo[i].mIsExistFile);
        }
        for (;;)
        {
          j++;
          break;
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____ACCELEROMETER_N_ANGLE)
          {
            i = ConverterID(ModuleSensor.ID_FILE____ACCELEROMETER_N_ANGLE);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____ACCELEROMETER_N_ANGLE", "ACCEL_SENSOR_RAW");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____ACCELEROMETER_SELF)
          {
            i = ConverterID(ModuleSensor.ID_FILE____ACCELEROMETER_SELF);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____ACCELEROMETER_SELF", "ACCEL_SENSOR_RAW");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____ACCELEROMETER_CAL)
          {
            i = ConverterID(ModuleSensor.ID_FILE____ACCELEROMETER_CAL);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____ACCELEROMETER_CAL", "ACCEL_SENSOR_CAL");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____ACCELEROMETER_INTPIN)
          {
            i = ConverterID(ModuleSensor.ID_FILE____ACCELEROMETER_INTPIN);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____ACCELEROMETER_INTPIN", "ACCEL_SENSOR_INTPIN");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____BAROMETER_EEPROM)
          {
            i = ConverterID(ModuleSensor.ID_FILE____BAROMETER_EEPROM);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____BAROMETER_EEPROM", "BAROME_EEPROM");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____GYRO_POWER)
          {
            i = ConverterID(ModuleSensor.ID_FILE____GYRO_POWER);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____GYRO_POWER", "GYRO_SENSOR_POWER_ON");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____GYRO_TEMPERATURE)
          {
            i = ConverterID(ModuleSensor.ID_FILE____GYRO_TEMPERATURE);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____GYRO_TEMPERATURE", "GYRO_SENSOR_TEMP");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____GYRO_SELFTEST)
          {
            i = ConverterID(ModuleSensor.ID_FILE____GYRO_SELFTEST);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____GYRO_SELF", "GYRO_SENSOR_SELFTEST");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____LIGHT_ADC)
          {
            i = ConverterID(ModuleSensor.ID_FILE____LIGHT_ADC);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____LIGHT_ADC", "LIGHT_SENSOR_RAW");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____LIGHT_RGBW)
          {
            i = ConverterID(ModuleSensor.ID_FILE____LIGHT_RGBW);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____LIGHT_ADC", "LIGHT_SENSOR_RAW");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____MAGNETIC_POWER_ON)
          {
            i = ConverterID(ModuleSensor.ID_FILE____MAGNETIC_POWER_ON);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____MAGNETIC_POWER_ON", "GEOMAGNETIC_SENSOR_POWER");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____MAGNETIC_STATUS)
          {
            i = ConverterID(ModuleSensor.ID_FILE____MAGNETIC_STATUS);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____MAGNETIC_STATUS", "GEOMAGNETIC_SENSOR_STATUS");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____MAGNETIC_TEMPERATURE)
          {
            i = ConverterID(ModuleSensor.ID_FILE____MAGNETIC_TEMPERATURE);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____MAGNETIC_TEMPERATURE", "GEOMAGNETIC_SENSOR_TEMP");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____MAGNETIC_DAC)
          {
            i = ConverterID(ModuleSensor.ID_FILE____MAGNETIC_DAC);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____MAGNETIC_DAC", "GEOMAGNETIC_SENSOR_DAC");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____MAGNETIC_ADC)
          {
            i = ConverterID(ModuleSensor.ID_FILE____MAGNETIC_ADC);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____MAGNETIC_ADC", "GEOMAGNETIC_SENSOR_ADC");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____MAGNETIC_SELF)
          {
            i = ConverterID(ModuleSensor.ID_FILE____MAGNETIC_SELF);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____MAGNETIC_SELF", "GEOMAGNETIC_SENSOR_SELFTEST");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____PROXIMITY_ADC)
          {
            i = ConverterID(ModuleSensor.ID_FILE____PROXIMITY_ADC);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____PROXIMITY_ADC", "PROXI_SENSOR_ADC");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] == ModuleSensor.ID_FILE____PROXIMITY_AVG)
          {
            i = ConverterID(ModuleSensor.ID_FILE____PROXIMITY_AVG);
            if (-1 >= i) {
              break label96;
            }
            this.mInfo[i] = new Info("ID_FILE____PROXIMITY_AVG", "PROXI_SENSOR_ADC_AVG");
            break label96;
          }
          if (this.mModuleSensorIDArray[j] != ModuleSensor.ID_FILE____PROXIMITY_OFFSET) {
            break label96;
          }
          i = ConverterID(ModuleSensor.ID_FILE____PROXIMITY_OFFSET);
          if (-1 >= i) {
            break label96;
          }
          this.mInfo[i] = new Info("ID_FILE____PROXIMITY_OFFSET", "PROXI_SENSOR_OFFSET");
          break label96;
          label1133:
          FtUtil.log_e("SensorReadFile", "init", "ID : Unknown");
        }
      }
    }
    else
    {
      FtUtil.log_e("SensorReadFile", "init", "mModuleSensorIDArray null");
    }
  }
  
  private void removeWhiteSpace(int paramInt)
  {
    if (this.mInfo[paramInt].mBuffer_SensorValue != null) {
      for (int i = 0; i < this.mInfo[paramInt].mBuffer_SensorValue.length; i++) {
        this.mInfo[paramInt].mBuffer_SensorValue[i] = this.mInfo[paramInt].mBuffer_SensorValue[i].replace(" ", "");
      }
    }
  }
  
  public void getSensorReadData()
  {
    int i = -1;
    int j = 0;
    for (;;)
    {
      if (j < this.mModuleSensorIDArray.length)
      {
        if (this.mModuleSensorIDArray != null) {
          i = ConverterID(this.mModuleSensorIDArray[j]);
        }
        String str;
        if ((-1 < i) && (this.mInfo != null) && (this.mInfo[i] != null) && (this.mInfo[i].mIsExistFile) && (this.mInfo != null))
        {
          if (ModuleSensor.LOG_LEVEL > 1) {
            break label124;
          }
          str = Support.Kernel.read(this.mInfo[i].mFilePath);
          if (str == null) {}
        }
        try
        {
          if (this.mInfo != null) {
            this.mInfo[i].mBuffer_SensorValue = str.split(",");
          }
          j++;
          continue;
          label124:
          str = Support.Kernel.read(this.mInfo[i].mFilePath, false);
        }
        catch (Exception localException)
        {
          for (;;)
          {
            FtUtil.log_d("SensorReadFile", "SensorReadThread-run", "execption : " + localException);
          }
        }
      }
    }
  }
  
  public boolean isSensorOn(int paramInt)
  {
    int i = ConverterID(paramInt);
    if ((this.mInfo != null) && (this.mInfo[i] != null)) {
      return this.mInfo[i].mIsExistFile;
    }
    return false;
  }
  
  public String[] returnData(int paramInt)
  {
    int i = ConverterID(paramInt);
    if (this.mIsLoop) {}
    for (;;)
    {
      if (ModuleSensor.LOG_LEVEL <= 3) {
        FtUtil.log_e("SensorReadFile", "returnData", this.mInfo[i].mName + " => " + dataCheck(this.mInfo[i].mBuffer_SensorValue));
      }
      removeWhiteSpace(i);
      checkSpec(paramInt, i);
      return this.mInfo[i].mBuffer_SensorValue;
      if ((-1 < i) && (this.mInfo[i].mIsExistFile))
      {
        FtUtil.log_d("SensorReadFile", "returnData", this.mInfo[i].mName);
        String str = Support.Kernel.read(this.mInfo[i].mFilePath);
        if (str != null) {
          this.mInfo[i].mBuffer_SensorValue = str.split(",");
        }
      }
    }
  }
  
  public void sensorOff()
  {
    FtUtil.log_e("SensorReadFile", "sensorOff", "Sensor Off");
    if (this.mTimer != null)
    {
      this.mTimer.cancel();
      this.mTimer = null;
      FtUtil.log_d("SensorReadFile", "mTimer canceled", "...");
    }
  }
  
  public void startLoop()
  {
    this.mIsLoop = true;
    this.mTimer = new Timer();
    this.mTimer.schedule(new TimerTask()
    {
      public void run()
      {
        SensorReadFile.this.getSensorReadData();
      }
    }, 0L, this.SENSOR_READ_INTERVAL);
  }
  
  private class Info
  {
    public String[] mBuffer_SensorValue;
    public String mFilePath;
    public boolean mIsExistFile;
    public String mName;
    
    public Info(String paramString1, String paramString2)
    {
      this.mName = paramString1;
      this.mFilePath = paramString2;
      this.mIsExistFile = Support.Kernel.isExistFile(Support.Kernel.getFilePath(paramString2));
      this.mBuffer_SensorValue = null;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.SensorReadFile
 * JD-Core Version:    0.7.1
 */