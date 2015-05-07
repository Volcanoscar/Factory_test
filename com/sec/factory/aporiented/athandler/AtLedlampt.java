package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDevice;

public class AtLedlampt
  extends AtCommandHandler
{
  public AtLedlampt(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "LEDLAMPT";
    this.CLASS_NAME = "AtLedlampt";
    this.NUM_ARGS = 2;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      String str;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          if (mModuleDevice.setLEDlamp(0)) {
            str = responseOK(paramArrayOfString[0]);
          } else {
            str = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          if (mModuleDevice.setLEDlamp(1)) {
            str = responseOK(paramArrayOfString[0]);
          } else {
            str = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2" }))
        {
          if (mModuleDevice.setLEDlamp(2)) {
            str = responseOK(paramArrayOfString[0]);
          } else {
            str = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "3" }))
        {
          if (mModuleDevice.setLEDlamp(3)) {
            str = responseOK(paramArrayOfString[0]);
          } else {
            str = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "4" }))
        {
          if (mModuleDevice.setLEDlamp(4)) {
            str = responseOK(paramArrayOfString[0]);
          } else {
            str = responseNG(paramArrayOfString[0]);
          }
        }
        else {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      Object localObject2 = str;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtLedlampt
 * JD-Core Version:    0.7.1
 */