package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDevice;

public class AtFsbuildc
  extends AtCommandHandler
{
  public AtFsbuildc(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "FSBUILDC";
    this.CLASS_NAME = "AtFsbuildc";
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
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        } else if (checkArgu(paramArrayOfString, new String[] { "1", "1" })) {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        } else if (checkArgu(paramArrayOfString, new String[] { "1", "2" }))
        {
          if (mModuleDevice.isCompleteFileSystemBuildingNAND()) {
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
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFsbuildc
 * JD-Core Version:    0.7.1
 */