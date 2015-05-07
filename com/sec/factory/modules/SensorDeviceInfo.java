package com.sec.factory.modules;

import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;

public class SensorDeviceInfo
{
  private static int DUMMY = 0;
  public static final int TARGET_FILE;
  public static final int TARGET_XML;
  public static final int TYPE_ACCELEROMETER;
  public static final int TYPE_BAROMETER;
  public static final int TYPE_GEOMAGNETIC;
  public static final int TYPE_GRIP;
  public static final int TYPE_GYRO;
  public static final int TYPE_HUMIDITY;
  public static final int TYPE_LIGHT;
  public static final int TYPE_PROXIMITY;
  public static final int TYPE_TEMPERATURE;
  private static String mSensorNameFileAccelerometer;
  private static String mSensorNameFileBarometer;
  private static String mSensorNameFileGeomagnetic;
  private static String mSensorNameFileGrip;
  private static String mSensorNameFileGyro;
  private static String mSensorNameFileHumidity = null;
  private static String mSensorNameFileLight;
  private static String mSensorNameFileProximity;
  private static String mSensorNameFileTemperature;
  private static String mSensorNameXMLAccelerometer;
  private static String mSensorNameXMLBarometer;
  private static String mSensorNameXMLGeomagnetic;
  private static String mSensorNameXMLGrip;
  private static String mSensorNameXMLGyro;
  private static String mSensorNameXMLHumidity;
  private static String mSensorNameXMLLight;
  private static String mSensorNameXMLProximity;
  private static String mSensorNameXMLTemperature;
  
  static
  {
    int i = DUMMY;
    DUMMY = i + 1;
    TARGET_XML = i;
    int j = DUMMY;
    DUMMY = j + 1;
    TARGET_FILE = j;
    int k = DUMMY;
    DUMMY = k + 1;
    TYPE_ACCELEROMETER = k;
    int m = DUMMY;
    DUMMY = m + 1;
    TYPE_BAROMETER = m;
    int n = DUMMY;
    DUMMY = n + 1;
    TYPE_GEOMAGNETIC = n;
    int i1 = DUMMY;
    DUMMY = i1 + 1;
    TYPE_GRIP = i1;
    int i2 = DUMMY;
    DUMMY = i2 + 1;
    TYPE_GYRO = i2;
    int i3 = DUMMY;
    DUMMY = i3 + 1;
    TYPE_LIGHT = i3;
    int i4 = DUMMY;
    DUMMY = i4 + 1;
    TYPE_PROXIMITY = i4;
    int i5 = DUMMY;
    DUMMY = i5 + 1;
    TYPE_TEMPERATURE = i5;
    int i6 = DUMMY;
    DUMMY = i6 + 1;
    TYPE_HUMIDITY = i6;
    mSensorNameXMLAccelerometer = null;
    mSensorNameXMLBarometer = null;
    mSensorNameXMLGeomagnetic = null;
    mSensorNameXMLGrip = null;
    mSensorNameXMLGyro = null;
    mSensorNameXMLLight = null;
    mSensorNameXMLProximity = null;
    mSensorNameXMLTemperature = null;
    mSensorNameXMLHumidity = null;
    mSensorNameFileAccelerometer = null;
    mSensorNameFileBarometer = null;
    mSensorNameFileGeomagnetic = null;
    mSensorNameFileGrip = null;
    mSensorNameFileGyro = null;
    mSensorNameFileLight = null;
    mSensorNameFileProximity = null;
    mSensorNameFileTemperature = null;
  }
  
  public static String getSensorName(int paramInt1, int paramInt2)
  {
    String str;
    if (paramInt2 == TARGET_XML) {
      if (paramInt1 == TYPE_ACCELEROMETER)
      {
        if (mSensorNameXMLAccelerometer == null) {
          mSensorNameXMLAccelerometer = Support.Feature.getString("SENSOR_NAME_ACCELEROMETER");
        }
        str = mSensorNameXMLAccelerometer;
        if (paramInt2 != TARGET_XML) {
          break label494;
        }
        FtUtil.log_d("SensorDeviceInfo", "getSensorName", "XML => return : " + str);
      }
    }
    label494:
    while (paramInt2 != TARGET_FILE)
    {
      return str;
      if (paramInt1 == TYPE_BAROMETER)
      {
        str = mSensorNameXMLBarometer;
        break;
      }
      if (paramInt1 == TYPE_GEOMAGNETIC)
      {
        if (mSensorNameXMLGeomagnetic == null) {
          mSensorNameXMLGeomagnetic = Support.Feature.getString("SENSOR_NAME_MAGNETIC");
        }
        str = mSensorNameXMLGeomagnetic;
        break;
      }
      if (paramInt1 == TYPE_GRIP)
      {
        str = mSensorNameXMLGrip;
        break;
      }
      if (paramInt1 == TYPE_GYRO)
      {
        if (mSensorNameXMLGyro == null) {
          mSensorNameXMLGyro = Support.Feature.getString("SENSOR_NAME_GYROSCOPE");
        }
        str = mSensorNameXMLGyro;
        break;
      }
      if (paramInt1 == TYPE_LIGHT)
      {
        if (mSensorNameXMLLight == null) {
          mSensorNameXMLLight = Support.Feature.getString("SENSOR_NAME_LIGHT");
        }
        str = mSensorNameXMLLight;
        break;
      }
      if (paramInt1 == TYPE_PROXIMITY)
      {
        if (mSensorNameXMLProximity == null) {
          mSensorNameXMLProximity = Support.Feature.getString("SENSOR_NAME_PROXIMITY");
        }
        str = mSensorNameXMLProximity;
        break;
      }
      if (paramInt1 == TYPE_TEMPERATURE)
      {
        str = mSensorNameXMLTemperature;
        break;
      }
      if (paramInt1 == TYPE_HUMIDITY)
      {
        str = mSensorNameXMLHumidity;
        break;
      }
      FtUtil.log_e("SensorDeviceInfo", "getSensorName", "TARGET_XML - sensorType : Unknown");
      str = null;
      break;
      if (paramInt2 == TARGET_FILE)
      {
        if (paramInt1 == TYPE_ACCELEROMETER)
        {
          if (mSensorNameFileAccelerometer == null) {
            mSensorNameFileAccelerometer = Support.Kernel.read("ACCEL_SENSOR_NAME");
          }
          str = mSensorNameFileAccelerometer;
          break;
        }
        if (paramInt1 == TYPE_BAROMETER)
        {
          if (mSensorNameFileBarometer == null) {
            mSensorNameFileBarometer = Support.Kernel.read("BAROMETER_SENSOR_NAME");
          }
          str = mSensorNameFileBarometer;
          break;
        }
        if (paramInt1 == TYPE_GEOMAGNETIC)
        {
          if (mSensorNameFileGeomagnetic == null) {
            mSensorNameFileGeomagnetic = Support.Kernel.read("GEOMAGNETIC_SENSOR_NAME");
          }
          str = mSensorNameFileGeomagnetic;
          break;
        }
        if (paramInt1 == TYPE_GRIP)
        {
          str = mSensorNameFileGrip;
          break;
        }
        if (paramInt1 == TYPE_GYRO)
        {
          if (mSensorNameFileGyro == null) {
            mSensorNameFileGyro = Support.Kernel.read("GYRO_SENSOR_NAME");
          }
          str = mSensorNameFileGyro;
          break;
        }
        if (paramInt1 == TYPE_LIGHT)
        {
          if (mSensorNameFileLight == null) {
            mSensorNameFileLight = Support.Kernel.read("LIGHT_SENSOR_NAME");
          }
          str = mSensorNameFileLight;
          break;
        }
        if (paramInt1 == TYPE_PROXIMITY)
        {
          if (mSensorNameFileProximity == null) {
            mSensorNameFileProximity = Support.Kernel.read("PROXI_SENSOR_NAME");
          }
          str = mSensorNameFileProximity;
          break;
        }
        if (paramInt1 == TYPE_TEMPERATURE)
        {
          str = mSensorNameFileTemperature;
          break;
        }
        if (paramInt1 == TYPE_HUMIDITY)
        {
          str = mSensorNameFileHumidity;
          break;
        }
        FtUtil.log_e("SensorDeviceInfo", "getSensorName", "TARGET_FILE - sensorType : Unknown");
        str = null;
        break;
      }
      FtUtil.log_e("SensorDeviceInfo", "getSensorName", "readTarget : Unknown");
      str = null;
      break;
    }
    FtUtil.log_d("SensorDeviceInfo", "getSensorName", "File => return : " + str);
    return str;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.SensorDeviceInfo
 * JD-Core Version:    0.7.1
 */