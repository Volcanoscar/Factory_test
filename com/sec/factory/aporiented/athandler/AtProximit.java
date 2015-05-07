package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.modules.SensorDeviceInfo;
import com.sec.factory.support.Support.Kernel;

public class AtProximit
  extends AtCommandHandler
{
  public AtProximit(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "PROXIMIT";
    this.CLASS_NAME = "AtProximit";
    this.NUM_ARGS = 2;
  }
  
  private boolean isSharpNonADCChipset()
  {
    String str = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_PROXIMITY, SensorDeviceInfo.TARGET_FILE);
    boolean bool1 = false;
    if (str != null)
    {
      boolean bool2 = "GP2AP002".equals(str);
      bool1 = false;
      if (bool2) {
        bool1 = true;
      }
    }
    return bool1;
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
        if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          mModuleSensor.SensorOff();
          if (mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_PROXIMITY)) {
            str1 = responseStringCMDNG();
          } else {
            str1 = responseStringNoArg(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          int[] arrayOfInt = new int[1];
          arrayOfInt[0] = ModuleSensor.ID_MANAGER_PROXIMITY;
          if (!mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_PROXIMITY)) {
            mModuleSensor.SensorOn(arrayOfInt);
          }
          if (mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_PROXIMITY)) {
            str1 = responseStringNoArg(paramArrayOfString[0]);
          } else {
            str1 = responseStringCMDNG();
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2" }))
        {
          if (isSharpNonADCChipset())
          {
            if (mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_PROXIMITY))
            {
              if (Support.Kernel.write("PROXI_SENSOR_OFFSET", "0")) {
                str1 = responseStringNoArg(paramArrayOfString[0]);
              } else {
                str1 = responseNG(paramArrayOfString[0]);
              }
            }
            else {
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
          else if (Support.Kernel.write("PROXI_SENSOR_OFFSET", "0"))
          {
            if (Support.Kernel.write("PROXI_SENSOR_OFFSET", "1")) {
              str1 = responseStringNoArg(paramArrayOfString[0]);
            } else {
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
          else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if ((checkArgu(paramArrayOfString, new String[] { "0", "3" })) && (isSharpNonADCChipset()))
        {
          if (mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_PROXIMITY))
          {
            if (Support.Kernel.write("PROXI_SENSOR_OFFSET", "1")) {
              str1 = responseStringNoArg(paramArrayOfString[0]);
            } else {
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
          else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if ((checkArgu(paramArrayOfString, new String[] { "0", "4" })) && (isSharpNonADCChipset()))
        {
          if (mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_PROXIMITY))
          {
            if (Support.Kernel.write("PROXI_SENSOR_OFFSET", "2")) {
              str1 = responseStringNoArg(paramArrayOfString[0]);
            } else {
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
          else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          if (mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_PROXIMITY))
          {
            String[] arrayOfString = mModuleSensor.getData(ModuleSensor.ID_MANAGER_PROXIMITY);
            if (arrayOfString != null)
            {
              if (arrayOfString[2].equals("0.0")) {
                str1 = responseString(paramArrayOfString[0], "FOUND");
              } else {
                str1 = responseString(paramArrayOfString[0], "NOT FOUND");
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
        else if (checkArgu(paramArrayOfString, new String[] { "1", "1" }))
        {
          str1 = responseStringCMDNG();
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "2" }))
        {
          str1 = responseString(paramArrayOfString[0], mModuleCommon.readProxiOffset());
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "3" }))
        {
          str1 = responseString(paramArrayOfString[0], mModuleCommon.readProxiADC());
        }
        else if ((checkArgu(paramArrayOfString, new String[] { "1", "4" })) && (isSharpNonADCChipset()))
        {
          String str2 = Support.Kernel.read("PROXI_SENSOR_OFFSET");
          if (str2 != null) {
            str1 = responseString(paramArrayOfString[0], "MODE" + str2);
          } else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      Object localObject2 = str1;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtProximit
 * JD-Core Version:    0.7.1
 */