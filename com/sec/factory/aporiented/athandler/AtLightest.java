package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;

public class AtLightest
  extends AtCommandHandler
{
  private final int NEW_DM_DATA_LEN = 10;
  private int[] mSensorID = null;
  private int mSensorID_LIGHT_CCT = -1;
  private int mSensorID_LIGHT_LUX = -1;
  
  public AtLightest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "LIGHTEST";
    this.CLASS_NAME = "AtLightest";
    this.NUM_ARGS = 2;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      Object localObject3;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          mModuleSensor.SensorOff();
          this.mSensorID_LIGHT_LUX = ModuleSensor.ID_MANAGER_LIGHT;
          this.mSensorID_LIGHT_CCT = ModuleSensor.ID_MANAGER_LIGHT_CCT;
          int[] arrayOfInt = new int[2];
          arrayOfInt[0] = this.mSensorID_LIGHT_LUX;
          arrayOfInt[1] = this.mSensorID_LIGHT_CCT;
          this.mSensorID = arrayOfInt;
          mModuleSensor.SensorOn(this.mSensorID);
          if (mModuleSensor.isSensorOn(this.mSensorID_LIGHT_LUX))
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,0 - Sensor On : OK");
            localObject3 = responseStringNoArg(paramArrayOfString[0]);
          }
          else
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,0 - Sensor On : NG");
            localObject3 = responseStringCMDNG();
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          mModuleSensor.SensorOff();
          if (mModuleSensor.isSensorOn(this.mSensorID_LIGHT_LUX))
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,1 - Sensor Off : NG");
            localObject3 = responseStringCMDNG();
          }
          else
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,1 - Sensor Off : OK");
            localObject3 = responseStringNoArg(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          if (mModuleSensor.isSensorOn(this.mSensorID_LIGHT_LUX))
          {
            String[] arrayOfString2 = mModuleSensor.getData(this.mSensorID_LIGHT_LUX);
            if (arrayOfString2 != null)
            {
              FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Total Read : " + arrayOfString2[2]);
              try
              {
                float f2 = Float.parseFloat(arrayOfString2[2]);
                j = (int)f2;
              }
              catch (Exception localException2)
              {
                localException2.printStackTrace();
                int j = 0;
                continue;
              }
              FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Totaldata: " + j);
              localObject3 = responseString(paramArrayOfString[0], "" + j);
              if ((!"NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))) && (!"NEW_ETS".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL")))) {
                break label751;
              }
              localObject3 = FtUtil.addDummyValue((String)localObject3, 10, ' ');
              break label751;
            }
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Total Read : NG (null)");
            localObject3 = responseStringCMDNG();
          }
          else
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Total Read : NG (Sensor Off)");
            localObject3 = responseStringCMDNG();
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "1" }))
        {
          if (mModuleSensor.isSensorOn(this.mSensorID_LIGHT_CCT))
          {
            String[] arrayOfString1 = mModuleSensor.getData(this.mSensorID_LIGHT_CCT);
            if (arrayOfString1 != null)
            {
              FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,1 - Total Read : " + arrayOfString1[2]);
              try
              {
                float f1 = Float.parseFloat(arrayOfString1[2]);
                i = (int)f1;
              }
              catch (Exception localException1)
              {
                localException1.printStackTrace();
                int i = 0;
                continue;
              }
              FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,1 - Totaldata: " + i);
              localObject3 = responseString(paramArrayOfString[0], "" + i);
              if ((!"NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))) && (!"NEW_ETS".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL")))) {
                break label751;
              }
              localObject3 = FtUtil.addDummyValue((String)localObject3, 10, ' ');
              break label751;
            }
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,1 - Total Read : NG (null)");
            localObject3 = responseStringCMDNG();
          }
          else
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,1 - Total Read : NG (Sensor Off)");
            localObject3 = responseStringCMDNG();
          }
        }
        else
        {
          String str = responseNA();
          localObject3 = str;
        }
      }
      finally {}
      label751:
      Object localObject2 = localObject3;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtLightest
 * JD-Core Version:    0.7.1
 */