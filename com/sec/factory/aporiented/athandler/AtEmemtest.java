package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDevice;

public class AtEmemtest
  extends AtCommandHandler
{
  public AtEmemtest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "EMEMTEST";
    this.CLASS_NAME = "AtEmemtest";
    this.NUM_ARGS = 2;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    Object localObject2;
    String str;
    try
    {
      if (paramArrayOfString.length != this.NUM_ARGS)
      {
        localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        return (String)localObject2;
      }
      if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
      {
        if (mModuleDevice.isDetectExternalMemory()) {
          str = responseOK(paramArrayOfString[0]);
        } else {
          str = responseNG(paramArrayOfString[0]);
        }
      }
      else if (checkArgu(paramArrayOfString, new String[] { "1", "1" }))
      {
        if (mModuleDevice.isMountedStorage(1)) {
          str = responseOK(paramArrayOfString[0]);
        } else {
          str = responseNG(paramArrayOfString[0]);
        }
      }
      else
      {
        if (checkArgu(paramArrayOfString, new String[] { "1", "2" }))
        {
          if (mModuleDevice.isMountedStorage(1))
          {
            str = responseString(paramArrayOfString[0], mModuleDevice.int_extMEMOSize(1, true));
            break label280;
          }
          str = responseNG(paramArrayOfString[0]);
          break label280;
        }
        if (checkArgu(paramArrayOfString, new String[] { "1", "4" }))
        {
          if (mModuleDevice.isMountedStorage(0))
          {
            str = responseString(paramArrayOfString[0], String.valueOf(mModuleDevice.getAvailableSize(0, 1024)));
            break label283;
          }
          str = responseNG(paramArrayOfString[0]);
          break label283;
        }
        str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      }
    }
    finally {}
    label280:
    label283:
    for (;;)
    {
      localObject2 = str;
      break;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtEmemtest
 * JD-Core Version:    0.7.1
 */