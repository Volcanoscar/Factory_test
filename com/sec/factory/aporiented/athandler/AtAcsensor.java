package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Kernel;

public class AtAcsensor
  extends AtCommandHandler
{
  public AtAcsensor(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "ACSENSOR";
    this.CLASS_NAME = "AtAcsensor";
    this.NUM_ARGS = 2;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
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
          int[] arrayOfInt2 = new int[4];
          arrayOfInt2[0] = ModuleSensor.ID_FILE____ACCELEROMETER;
          arrayOfInt2[1] = ModuleSensor.ID_FILE____ACCELEROMETER_SELF;
          arrayOfInt2[2] = ModuleSensor.ID_FILE____ACCELEROMETER_CAL;
          arrayOfInt2[3] = ModuleSensor.ID_FILE____ACCELEROMETER_INTPIN;
          mModuleSensor.SensorOn(arrayOfInt2);
          if (mModuleSensor.isSensorOn(ModuleSensor.ID_FILE____ACCELEROMETER)) {
            str1 = responseStringNoArg(paramArrayOfString[0]);
          } else {
            str1 = responseStringCMDNG();
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          mModuleSensor.SensorOff();
          if (mModuleSensor.isSensorOn(ModuleSensor.ID_FILE____ACCELEROMETER)) {
            str1 = responseStringCMDNG();
          } else {
            str1 = responseStringNoArg(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2" }))
        {
          if (mModuleSensor.isSensorOn(ModuleSensor.ID_FILE____ACCELEROMETER_CAL))
          {
            if (Support.Kernel.write("ACCEL_SENSOR_CAL", "1")) {
              str1 = responseStringNoArg(paramArrayOfString[0]);
            } else {
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
          else
          {
            mModuleSensor.SensorOff();
            int[] arrayOfInt1 = new int[1];
            arrayOfInt1[0] = ModuleSensor.ID_FILE____ACCELEROMETER_CAL;
            mModuleSensor.SensorOn(arrayOfInt1);
            if (mModuleSensor.isSensorOn(ModuleSensor.ID_FILE____ACCELEROMETER_CAL))
            {
              if (Support.Kernel.write("ACCEL_SENSOR_CAL", "1")) {
                str1 = responseStringNoArg(paramArrayOfString[0]);
              } else {
                str1 = responseNG(paramArrayOfString[0]);
              }
            }
            else {
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          if (mModuleSensor.isSensorOn(ModuleSensor.ID_FILE____ACCELEROMETER))
          {
            String[] arrayOfString3 = mModuleSensor.getData(ModuleSensor.ID_FILE____ACCELEROMETER);
            if (arrayOfString3 != null)
            {
              String str3 = FtUtil.addDummyValue(arrayOfString3[2], 6, ' ') + FtUtil.addDummyValue(arrayOfString3[3], 6, ' ') + FtUtil.addDummyValue(arrayOfString3[4], 6, ' ');
              FtUtil.log_d(this.CLASS_NAME, "handleCommand", "(1,0) responseData => [" + str3 + "]");
              str1 = responseString(paramArrayOfString[0], str3);
            }
            else
            {
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
          else
          {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "4" }))
        {
          if (mModuleSensor.isSensorOn(ModuleSensor.ID_FILE____ACCELEROMETER_SELF))
          {
            String[] arrayOfString2 = mModuleSensor.getData(ModuleSensor.ID_FILE____ACCELEROMETER_SELF);
            if (arrayOfString2 != null) {
              str1 = responseString(paramArrayOfString[0], arrayOfString2[2]);
            } else {
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
          else
          {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "5" }))
        {
          String str2 = Support.Kernel.read("ACCEL_SENSOR_CAL");
          if (str2 != null) {
            if (str2.charAt(0) > '0')
            {
              str1 = responseOK(paramArrayOfString[0]);
              if ((mModuleSensor.isSensorOn(ModuleSensor.ID_FILE____ACCELEROMETER)) && (mModuleSensor.isSensorOn(ModuleSensor.ID_FILE____ACCELEROMETER_SELF))) {
                break label829;
              }
              mModuleSensor.SensorOff();
            }
          }
        }
      }
      finally {}
      String str1 = responseNG(paramArrayOfString[0]);
      continue;
      str1 = responseNG(paramArrayOfString[0]);
      break label829;
      if (checkArgu(paramArrayOfString, new String[] { "1", "6" }))
      {
        if (mModuleSensor.isSensorOn(ModuleSensor.ID_FILE____ACCELEROMETER_CAL))
        {
          String[] arrayOfString1 = mModuleSensor.getData(ModuleSensor.ID_FILE____ACCELEROMETER_INTPIN);
          if (arrayOfString1 != null)
          {
            if ("1".equals(arrayOfString1[2])) {
              str1 = responseOK(paramArrayOfString[0]);
            } else {
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
          else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else
        {
          str1 = responseNG(paramArrayOfString[0]);
        }
      }
      else {
        str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      }
      label829:
      Object localObject2 = str1;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtAcsensor
 * JD-Core Version:    0.7.1
 */