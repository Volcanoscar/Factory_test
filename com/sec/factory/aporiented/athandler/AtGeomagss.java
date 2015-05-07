package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.modules.SensorDeviceInfo;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;

public class AtGeomagss
  extends AtCommandHandler
{
  private final char dummyData = ' ';
  private String mFeature = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_GEOMAGNETIC, SensorDeviceInfo.TARGET_XML);
  private int[] mSensorID = null;
  private String[] temp;
  private String tempdata = null;
  
  public AtGeomagss(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "GEOMAGSS";
    this.CLASS_NAME = "AtGeomagss";
    this.NUM_ARGS = 2;
  }
  
  private String commandFormat_Temperature(String paramString)
  {
    FtUtil.log_d(this.CLASS_NAME, "commandFormat_Temperature", "str : " + paramString);
    String str = paramString;
    if (paramString.indexOf(".") > 0) {
      str = paramString.substring(0, paramString.indexOf("."));
    }
    if (("NEW_ETS".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))) || ("NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))))
    {
      FtUtil.log_d(this.CLASS_NAME, "commandFormat_Temperature", "returnValue : " + str);
      if (paramString.length() <= 3) {
        break label146;
      }
      str = "NG";
    }
    for (;;)
    {
      FtUtil.log_d(this.CLASS_NAME, "commandFormat_Temperature", "returnValue : " + str);
      return str;
      label146:
      int i = Integer.valueOf(paramString).intValue();
      if ((i >= 0) && (i <= 9)) {
        str = "0" + i + " ";
      } else {
        str = i + " ";
      }
    }
  }
  
  private String commandFormat_XYZ(String paramString)
  {
    FtUtil.log_d(this.CLASS_NAME, "commandFormat_XYZ", "str : " + paramString);
    if (("NEW_ETS".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))) || ("NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL")))) {
      paramString = FtUtil.addDummyValue(paramString, 8, ' ');
    }
    return paramString;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    Object localObject2;
    label406:
    label429:
    label452:
    label713:
    label987:
    for (;;)
    {
      try
      {
        FtUtil.log_i(this.CLASS_NAME, "handleCommand", "mFeature : " + this.mFeature);
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (!checkArgu(paramArrayOfString, new String[] { "0", "0" })) {
          break label475;
        }
        if ((this.mFeature.equals("AK8963")) || (this.mFeature.equals("AK8963C")))
        {
          str1 = responseOK(paramArrayOfString[0]);
        }
        else if ((Support.Feature.getBoolean("SUPPORT_SYSFS_GEO_GYRO")) && (Support.Feature.getString("SENSOR_NAME_MAGNETIC").contains("YAS")))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,0 YAMAHA ( using SYSFS )");
          this.tempdata = Support.Kernel.read("GEOMAGNETIC_SENSOR_SELFTEST");
          if (this.tempdata != null)
          {
            this.temp = this.tempdata.split(",");
            if (Integer.parseInt(this.temp[0]) == 0)
            {
              FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,0 - Power On : OK");
              str1 = responseOK(paramArrayOfString[0]);
            }
            else
            {
              FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,0 - Power On : NG");
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
          else
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,0 - Power On : NG (null)");
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if ((this.mFeature.equals("HSCDTD004")) || (this.mFeature.equals("HSCDTD004A")) || (this.mFeature.equals("HSCDTD006A")) || (this.mFeature.equals("HSCDTD008A")))
        {
          i2 = ModuleSensor.ID_FILE____MAGNETIC_SELF;
          int[] arrayOfInt5 = { i2 };
          mModuleSensor.SensorOff();
          mModuleSensor.SensorOn(arrayOfInt5);
          String[] arrayOfString7 = mModuleSensor.getData(i2);
          if (arrayOfString7 == null) {
            break label452;
          }
          if (!mModuleSensor.isSensorOn(i2)) {
            break label429;
          }
          if (!arrayOfString7[2].equals("1")) {
            break label406;
          }
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,0 - Power On : OK");
          str1 = responseOK(paramArrayOfString[0]);
          mModuleSensor.SensorOff();
        }
      }
      finally {}
      int i2 = ModuleSensor.ID_MANAGER_MAGNETIC_POWER_ON;
      continue;
      FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,0 - Power On : NG");
      str1 = responseNG(paramArrayOfString[0]);
      continue;
      FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,0 - Power On : NG (Sensor Off)");
      str1 = responseNG(paramArrayOfString[0]);
      continue;
      FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,0 - Power On : NG (null)");
      str1 = responseNG(paramArrayOfString[0]);
      continue;
      label475:
      if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
      {
        if ((Support.Feature.getBoolean("SUPPORT_SYSFS_GEO_GYRO")) && (Support.Feature.getString("SENSOR_NAME_MAGNETIC").contains("YAS")))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 YAMAHA ( using SYSFS )");
          if (Integer.parseInt(this.temp[2]) == 0)
          {
            String str10 = "1" + ",0";
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Temperature : responseData => " + str10);
            str1 = responseString(paramArrayOfString[0], str10);
            break label2754;
          }
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Temperature : NG (null)");
          localObject2 = responseNG(paramArrayOfString[0]);
        }
        else
        {
          mModuleSensor.SensorOff();
          int n;
          String str8;
          if ((this.mFeature.equals("AK8975")) || (this.mFeature.equals("YAS530C")) || (this.mFeature.equals("YAS532")) || (this.mFeature.equals("YAS532B")) || (this.mFeature.equals("AK8963C_MANAGER")) || (this.mFeature.equals("BMC150")))
          {
            n = ModuleSensor.ID_MANAGER_MAGNETIC_STATUS;
            this.mSensorID = new int[] { n };
            mModuleSensor.SensorOn(this.mSensorID);
            if (!mModuleSensor.isSensorOn(n)) {
              break label964;
            }
            String[] arrayOfString5 = mModuleSensor.getData(n);
            if (arrayOfString5 == null) {
              break label941;
            }
            str8 = arrayOfString5[2];
            mModuleSensor.SensorOff();
            if ((!this.mFeature.equals("YAS530C")) && (!this.mFeature.equals("YAS532")) && (!this.mFeature.equals("YAS532B"))) {
              break label987;
            }
          }
          for (int i1 = ModuleSensor.ID_MANAGER_MAGNETIC_TEMPERATURE;; i1 = ModuleSensor.ID_FILE____MAGNETIC_TEMPERATURE)
          {
            this.mSensorID = new int[] { i1 };
            mModuleSensor.SensorOn(this.mSensorID);
            String[] arrayOfString6 = mModuleSensor.getData(i1);
            if (arrayOfString6 == null) {
              break label995;
            }
            String str9 = str8 + "," + commandFormat_Temperature(arrayOfString6[2]);
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Temperature : responseData => " + str9);
            mModuleSensor.SensorOff();
            str1 = responseString(paramArrayOfString[0], str9);
            break label2754;
            n = ModuleSensor.ID_FILE____MAGNETIC_STATUS;
            break label713;
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Status : NG (null)");
            localObject2 = responseNG(paramArrayOfString[0]);
            break;
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Status : NG (Sensor Off)");
            localObject2 = responseNG(paramArrayOfString[0]);
            break;
          }
          label995:
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Temperature : NG (null)");
          localObject2 = responseNG(paramArrayOfString[0]);
        }
      }
      else if (checkArgu(paramArrayOfString, new String[] { "1", "1" }))
      {
        if ((Support.Feature.getBoolean("SUPPORT_SYSFS_GEO_GYRO")) && (Support.Feature.getString("SENSOR_NAME_MAGNETIC").contains("YAS")))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,1 YAMAHA ( using SYSFS )");
          if (Integer.parseInt(this.temp[3]) == 0)
          {
            String str7 = this.temp[4] + "," + this.temp[5] + "," + this.temp[6];
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "(1,1) responseData => [" + str7 + "]");
            str1 = responseString(paramArrayOfString[0], str7);
            break label2754;
          }
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,1 - Temperature : NG (null)");
          localObject2 = responseNG(paramArrayOfString[0]);
        }
        else
        {
          int m;
          if ((this.mFeature.equals("AK8963")) || (this.mFeature.equals("AK8963C")))
          {
            m = ModuleSensor.ID_FILE____MAGNETIC_DAC;
            int[] arrayOfInt4 = { m };
            mModuleSensor.SensorOff();
            mModuleSensor.SensorOn(arrayOfInt4);
            String[] arrayOfString4 = mModuleSensor.getData(m);
            if (arrayOfString4 == null) {
              break label1429;
            }
            if (!mModuleSensor.isSensorOn(m)) {
              break label1405;
            }
            String str6 = commandFormat_XYZ(arrayOfString4[2]) + "," + commandFormat_XYZ(arrayOfString4[3]) + "," + commandFormat_XYZ(arrayOfString4[4]);
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "(1,1) responseData => [" + str6 + "]");
            str1 = responseString(paramArrayOfString[0], str6);
          }
          for (;;)
          {
            mModuleSensor.SensorOff();
            break label2754;
            m = ModuleSensor.ID_MANAGER_MAGNETIC_DAC;
            break;
            label1405:
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,1 - DAC X,Y,Z : NG (Sensor Off)");
            str1 = responseNG(paramArrayOfString[0]);
            continue;
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,1 - DAC X,Y,Z : NG (null)");
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
      }
      else
      {
        if (!checkArgu(paramArrayOfString, new String[] { "1", "2" })) {
          break label1931;
        }
        if ((!Support.Feature.getBoolean("SUPPORT_SYSFS_GEO_GYRO")) || (!Support.Feature.getString("SENSOR_NAME_MAGNETIC").contains("YAS"))) {
          break;
        }
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,2 YAMAHA ( using SYSFS )");
        if (Integer.parseInt(this.temp[7]) == 0)
        {
          String str5 = this.temp[8] + "," + "0" + "," + "0";
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "(1,2) responseData => [" + str5 + "]");
          str1 = responseString(paramArrayOfString[0], str5);
          break label2754;
        }
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,2 - Temperature : NG (null)");
        localObject2 = responseNG(paramArrayOfString[0]);
      }
    }
    label941:
    label964:
    int k;
    label1429:
    if ((this.mFeature.equals("AK8963")) || (this.mFeature.equals("AK8963C")) || (this.mFeature.equals("HSCDTD004")) || (this.mFeature.equals("HSCDTD004A")) || (this.mFeature.equals("HSCDTD006A")) || (this.mFeature.equals("HSCDTD008A")))
    {
      k = ModuleSensor.ID_FILE____MAGNETIC_ADC;
      int[] arrayOfInt3 = { k };
      mModuleSensor.SensorOff();
      mModuleSensor.SensorOn(arrayOfInt3);
      String[] arrayOfString3 = mModuleSensor.getData(k);
      if (arrayOfString3 == null) {
        break label1907;
      }
      if (!mModuleSensor.isSensorOn(k)) {
        break label1883;
      }
      String str4 = commandFormat_XYZ(arrayOfString3[2]) + "," + commandFormat_XYZ(arrayOfString3[3]) + "," + commandFormat_XYZ(arrayOfString3[4]);
      FtUtil.log_d(this.CLASS_NAME, "handleCommand", "(1,2) responseData => [" + str4 + "]");
      str1 = responseString(paramArrayOfString[0], str4);
    }
    for (;;)
    {
      mModuleSensor.SensorOff();
      break label2754;
      k = ModuleSensor.ID_MANAGER_MAGNETIC_ADC;
      break;
      label1883:
      FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,2 - ADC X,Y,Z : NG (Sensor Off)");
      str1 = responseNG(paramArrayOfString[0]);
      continue;
      label1907:
      FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,2 - ADC X,Y,Z : NG (null)");
      str1 = responseNG(paramArrayOfString[0]);
    }
    label1931:
    if (checkArgu(paramArrayOfString, new String[] { "1", "3" }))
    {
      if ((Support.Feature.getBoolean("SUPPORT_SYSFS_GEO_GYRO")) && (Support.Feature.getString("SENSOR_NAME_MAGNETIC").contains("YAS")))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,3 YAMAHA ( using SYSFS )");
        if (this.tempdata != null)
        {
          this.temp = this.tempdata.split(",");
          if (Integer.parseInt(this.temp[9]) == 0)
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,3 - Self Test : ");
            str1 = responseOK(paramArrayOfString[0]);
          }
          else
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,3 - Self Test : NG");
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,3 - Self Test : NG (null)");
          str1 = responseNG(paramArrayOfString[0]);
        }
      }
      else
      {
        int j;
        if ((this.mFeature.equals("AK8963")) || (this.mFeature.equals("AK8963C")))
        {
          j = ModuleSensor.ID_FILE____MAGNETIC_SELF;
          mModuleSensor.SensorOff();
          int[] arrayOfInt2 = { j };
          mModuleSensor.SensorOn(arrayOfInt2);
          String[] arrayOfString2 = mModuleSensor.getData(j);
          if (arrayOfString2 == null) {
            break label2284;
          }
          if (!mModuleSensor.isSensorOn(j)) {
            break label2263;
          }
          if (!arrayOfString2[1].equals("OK")) {
            break label2242;
          }
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,3 - Self Test : " + arrayOfString2[1]);
          str1 = responseOK(paramArrayOfString[0]);
        }
        for (;;)
        {
          mModuleSensor.SensorOff();
          break label2754;
          j = ModuleSensor.ID_MANAGER_MAGNETIC_SELF;
          break;
          label2242:
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,3 - Self Test : NG");
          str1 = responseStringCMDNG();
          continue;
          label2263:
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,3 - Self Test : NG (Sensor Off)");
          str1 = responseStringCMDNG();
          continue;
          label2284:
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,3 - Self Test : NG (null)");
          str1 = responseStringCMDNG();
        }
      }
    }
    else if (checkArgu(paramArrayOfString, new String[] { "1", "4" }))
    {
      if ((Support.Feature.getBoolean("SUPPORT_SYSFS_GEO_GYRO")) && (Support.Feature.getString("SENSOR_NAME_MAGNETIC").contains("YAS")))
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,4 YAMAHA ( using SYSFS )");
        if (Integer.parseInt(this.temp[12]) == 0)
        {
          String str3 = this.temp[13] + "," + this.temp[14] + "," + this.temp[15];
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "(1,4) responseData => [" + str3 + "]");
          str1 = responseString(paramArrayOfString[0], str3);
          break label2754;
        }
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,4 - OffsetH X,Y,Z : NG (Sensor Off)");
        str1 = responseNG(paramArrayOfString[0]);
        break label2754;
      }
      if ((!this.mFeature.equals("YAS530C")) && (!this.mFeature.equals("YAS532")) && (!this.mFeature.equals("YAS532B"))) {
        break label2760;
      }
      int i = ModuleSensor.ID_MANAGER_MAGNETIC_OFFSETH;
      int[] arrayOfInt1 = { i };
      mModuleSensor.SensorOff();
      mModuleSensor.SensorOn(arrayOfInt1);
      String[] arrayOfString1 = mModuleSensor.getData(i);
      if (arrayOfString1 != null) {
        if (mModuleSensor.isSensorOn(i))
        {
          String str2 = commandFormat_XYZ(arrayOfString1[2]) + "," + commandFormat_XYZ(arrayOfString1[3]) + "," + commandFormat_XYZ(arrayOfString1[4]);
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "(1,4) responseData => [" + str2 + "]");
          str1 = responseString(paramArrayOfString[0], str2);
        }
      }
      for (;;)
      {
        mModuleSensor.SensorOff();
        break;
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,4 - OffsetH X,Y,Z : NG (Sensor Off)");
        str1 = responseNG(paramArrayOfString[0]);
        continue;
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,4 - OffsetH X,Y,Z : NG (null)");
        str1 = responseNG(paramArrayOfString[0]);
      }
    }
    label2754:
    label2760:
    for (String str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";; str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n")
    {
      localObject2 = str1;
      break;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtGeomagss
 * JD-Core Version:    0.7.1
 */