package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.support.FtUtil;

public class AtHumitemp
  extends AtCommandHandler
{
  private int[] mSensorID = null;
  
  public AtHumitemp(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "HUMITEMP";
    this.CLASS_NAME = "AtHumitemp";
    this.NUM_ARGS = 3;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      String str1;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
        {
          mModuleSensor.SensorOff();
          if ((mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_HUMIDITY)) || (mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_TEMPERATURE)))
          {
            str1 = responseNG(paramArrayOfString[0]);
            FtUtil.log_e(this.CLASS_NAME, "Sensor OFF (0,0,0)", " Sensor Off : NG");
          }
          else
          {
            str1 = responseOK(paramArrayOfString[0]);
            FtUtil.log_d(this.CLASS_NAME, "Sensor OFF (0,0,0)", " Sensor Off : OK");
          }
        }
      }
      finally {}
      if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0" }))
      {
        mModuleSensor.SensorOff();
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = ModuleSensor.ID_MANAGER_HUMIDITY;
        arrayOfInt[1] = ModuleSensor.ID_MANAGER_TEMPERATURE;
        this.mSensorID = arrayOfInt;
        mModuleSensor.SensorOn(this.mSensorID);
        if ((mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_HUMIDITY)) && (mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_TEMPERATURE)))
        {
          str1 = responseOK(paramArrayOfString[0]);
          FtUtil.log_d(this.CLASS_NAME, "Sensor ON (0,1,0)", " Sensor On : OK");
        }
        else
        {
          str1 = responseNG(paramArrayOfString[0]);
          FtUtil.log_e(this.CLASS_NAME, "Sensor ON (0,1,0)", " Sensor On : NG");
        }
      }
      else
      {
        if (checkArgu(paramArrayOfString, new String[] { "1", "0", "0" }))
        {
          String str2;
          String str3;
          if (mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_HUMIDITY))
          {
            String[] arrayOfString2 = mModuleSensor.getData(ModuleSensor.ID_MANAGER_HUMIDITY);
            if (arrayOfString2 != null)
            {
              str2 = arrayOfString2[2];
              FtUtil.log_d(this.CLASS_NAME, "Sensor read (1,0,0)", "Humidity: " + str2);
              if (!mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_TEMPERATURE)) {
                break label508;
              }
              String[] arrayOfString1 = mModuleSensor.getData(ModuleSensor.ID_MANAGER_TEMPERATURE);
              if (arrayOfString1 == null) {
                break label490;
              }
              str3 = arrayOfString1[2];
              FtUtil.log_d(this.CLASS_NAME, "Sensor read (1,0,0)", "Temperature: " + str3);
            }
          }
          for (;;)
          {
            str1 = responseString(paramArrayOfString[0], str2 + "," + str3);
            break label530;
            str2 = "NG";
            FtUtil.log_d(this.CLASS_NAME, "Sensor read (1,0,0)", "Humidity: null");
            break;
            str2 = "NG";
            FtUtil.log_d(this.CLASS_NAME, "Sensor read (1,0,0)", "Humidity Sensor On Fail");
            break;
            label490:
            str3 = "NG";
            FtUtil.log_d(this.CLASS_NAME, "Sensor read (1,0,0)", "Temperature: null");
            continue;
            label508:
            str3 = "NG";
            FtUtil.log_d(this.CLASS_NAME, "Sensor read (1,0,0)", "Temperature Sensor On Fail");
          }
        }
        str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      }
      label530:
      Object localObject2 = str1;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtHumitemp
 * JD-Core Version:    0.7.1
 */