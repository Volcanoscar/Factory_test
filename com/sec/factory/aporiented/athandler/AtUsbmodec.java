package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDevice;

public class AtUsbmodec
  extends AtCommandHandler
{
  public AtUsbmodec(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "USBMODEC";
    this.CLASS_NAME = "AtUsbmodec";
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
        if (checkArgu(paramArrayOfString, new String[] { "1", "0" })) {
          str = responseString(paramArrayOfString[0], mModuleDevice.getUSBMode());
        } else if (checkArgu(paramArrayOfString, new String[] { "2", "0" }))
        {
          if (mModuleDevice.setUSBMode(0)) {
            str = responseOK(paramArrayOfString[0]);
          } else {
            str = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "2", "1" })) {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        } else if (checkArgu(paramArrayOfString, new String[] { "2", "2" }))
        {
          if (mModuleDevice.setUSBMode(1)) {
            str = responseOK(paramArrayOfString[0]);
          } else {
            str = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "2", "3" }))
        {
          if (mModuleDevice.setUSBMode(2)) {
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
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtUsbmodec
 * JD-Core Version:    0.7.1
 */