package com.sec.factory.aporiented.athandler;

import android.content.Context;
import android.hardware.SensorManager;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.modules.SensorDeviceInfo;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;

public class AtGyroscop
  extends AtCommandHandler
{
  private final int RETURN_DATA_ARRAY_SIZE_MAX = 16;
  private String mFeature = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_GYRO, SensorDeviceInfo.TARGET_XML);
  private int[] mSensorID = null;
  protected SensorManager mSensorManager;
  
  public AtGyroscop(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "GYROSCOP";
    this.CLASS_NAME = "AtGyroscop";
    this.NUM_ARGS = 2;
    this.mSensorManager = ((SensorManager)paramContext.getSystemService("sensor"));
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    Object localObject2;
    String str1;
    int k;
    for (;;)
    {
      try
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "mFeature : " + this.mFeature);
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          mModuleSensor.SensorOff();
          str1 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          if ((this.mFeature.equals("INVENSENSE_MPU6050")) || (this.mFeature.equals("INVENSENSE_MPU6051")) || (this.mFeature.equals("INVENSENSE_MPU6500")))
          {
            str1 = responseOK(paramArrayOfString[0]);
          }
          else
          {
            int[] arrayOfInt5 = new int[1];
            arrayOfInt5[0] = ModuleSensor.ID_FILE____GYRO_POWER;
            this.mSensorID = arrayOfInt5;
            mModuleSensor.SensorOn(this.mSensorID);
            if (!Support.Kernel.read("GYRO_SENSOR_POWER_ON").equals("1")) {
              break label891;
            }
            k = 1;
            label203:
            if (k != 0) {
              str1 = responseOK(paramArrayOfString[0]);
            } else {
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          mModuleSensor.SensorOff();
          int[] arrayOfInt4 = new int[1];
          arrayOfInt4[0] = ModuleSensor.ID_FILE____GYRO_TEMPERATURE;
          mModuleSensor.SensorOn(arrayOfInt4);
          if (mModuleSensor.isSensorOn(ModuleSensor.ID_FILE____GYRO_TEMPERATURE))
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Sensor On");
            String[] arrayOfString3 = mModuleSensor.getData(ModuleSensor.ID_FILE____GYRO_TEMPERATURE);
            if (arrayOfString3 != null)
            {
              FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Temperature : OK");
              str1 = responseString(paramArrayOfString[0], arrayOfString3[2]);
              mModuleSensor.SensorOff();
            }
          }
        }
      }
      finally {}
      FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Temperature : NG (null)");
      str1 = responseNG(paramArrayOfString[0]);
      continue;
      FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Temperature : NG (Sensor Off)");
      str1 = responseNG(paramArrayOfString[0]);
    }
    String[] arrayOfString1;
    if (checkArgu(paramArrayOfString, new String[] { "1", "1" }))
    {
      mModuleSensor.SensorOff();
      arrayOfString1 = new String[16];
      if ((Support.Feature.getBoolean("SUPPORT_SYSFS_GEO_GYRO")) && (this.mFeature.contains("INVENSENSE")))
      {
        FtUtil.log_d(this.CLASS_NAME, "SUPPORT_SYSFS_GEO_GYRO", "1,1 - Test");
        String str5 = Support.Kernel.read("GYRO_SENSOR_SELFTEST");
        if (str5 != null)
        {
          String[] arrayOfString2 = str5.split(",");
          if ((arrayOfString2 != null) && (arrayOfString2.length > 6))
          {
            arrayOfString1[0] = "7";
            arrayOfString1[1] = arrayOfString2[0];
            arrayOfString1[2] = arrayOfString2[1];
            arrayOfString1[3] = arrayOfString2[2];
            arrayOfString1[4] = arrayOfString2[3];
            arrayOfString1[5] = arrayOfString2[4];
            arrayOfString1[6] = arrayOfString2[5];
            arrayOfString1[7] = arrayOfString2[6];
          }
        }
        if (arrayOfString1 == null) {
          break label858;
        }
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,1 - Test result : OK");
        if (!arrayOfString1[1].equals("0")) {
          break label897;
        }
      }
    }
    label897:
    for (String str2 = "OK";; str2 = "NG")
    {
      int i = Integer.parseInt(arrayOfString1[0]);
      String str3 = str2;
      int j = 2;
      while (j <= i)
      {
        String str4 = str3 + ",";
        str3 = str4 + arrayOfString1[j];
        j++;
        continue;
        if ((this.mFeature.equals("INVENSENSE_MPU6050")) || (this.mFeature.equals("INVENSENSE_MPU6051")))
        {
          int[] arrayOfInt1 = new int[1];
          arrayOfInt1[0] = ModuleSensor.ID_MANAGER_GYRO_SELF;
          this.mSensorID = arrayOfInt1;
        }
        for (;;)
        {
          mModuleSensor.SensorOn(this.mSensorID);
          if (!mModuleSensor.isSensorOn(this.mSensorID[0])) {
            break;
          }
          arrayOfString1 = mModuleSensor.getData(this.mSensorID[0]);
          break;
          if (this.mFeature.equals("INVENSENSE_MPU6500"))
          {
            int[] arrayOfInt3 = new int[1];
            arrayOfInt3[0] = ModuleSensor.ID_FILE____GYRO_SELFTEST;
            this.mSensorID = arrayOfInt3;
          }
          else if ((this.mFeature.equals("STMICRO_SMARTPHONE")) || (this.mFeature.equals("STMICRO_TABLET")))
          {
            int[] arrayOfInt2 = new int[1];
            arrayOfInt2[0] = ModuleSensor.ID_FILE____GYRO_SELFTEST;
            this.mSensorID = arrayOfInt2;
          }
        }
      }
      for (str1 = responseString(paramArrayOfString[0], str3);; str1 = responseNG(paramArrayOfString[0]))
      {
        mModuleSensor.SensorOff();
        break;
        label858:
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,1 - Test result : NG (null)");
      }
      str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      localObject2 = str1;
      break;
      label891:
      k = 0;
      break label203;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtGyroscop
 * JD-Core Version:    0.7.1
 */