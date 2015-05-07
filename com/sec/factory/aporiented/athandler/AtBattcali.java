package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModulePower;

public class AtBattcali
  extends AtCommandHandler
{
  public AtBattcali(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "BATTCALI";
    this.CLASS_NAME = "AtBattcali";
    this.NUM_ARGS = 3;
    setCalibrationOnBoot();
  }
  
  private void setCalibrationOnBoot()
  {
    String str = mModulePower.readBatteryCal();
    if ((str != null) && (str.length() > 0)) {
      mModulePower.writeBatteryAdcCal(str);
    }
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
        if (checkArgu(paramArrayOfString, new String[] { "1", "0", "0" }))
        {
          String str2 = mModulePower.readBatteryAdcCal();
          mModulePower.writeBatteryAdcCal(str2);
          str1 = responseString(paramArrayOfString[0], str2);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "2" }))
        {
          mModulePower.writeBatteryCal(paramArrayOfString[2]);
          str1 = responseString(paramArrayOfString[0], paramArrayOfString[2]);
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
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtBattcali
 * JD-Core Version:    0.7.1
 */