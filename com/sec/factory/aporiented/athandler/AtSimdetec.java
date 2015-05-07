package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDevice;

public class AtSimdetec
  extends AtCommandHandler
{
  public AtSimdetec(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "SIMDETEC";
    this.CLASS_NAME = "AtSimdetec";
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
        if (checkArgu(paramArrayOfString, new String[] { "1", "1" }))
        {
          if (mModuleDevice.isSimCardExist()) {
            str = responseString(paramArrayOfString[0], "SIM");
          } else {
            str = responseString(paramArrayOfString[0], "NOS");
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "2" }))
        {
          if (mModuleDevice.isSimCardExist2()) {
            str = responseString(paramArrayOfString[0], "SIM");
          } else {
            str = responseString(paramArrayOfString[0], "NOS");
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
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtSimdetec
 * JD-Core Version:    0.7.1
 */