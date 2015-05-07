package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDevice;

public class AtRamsizec
  extends AtCommandHandler
{
  public AtRamsizec(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "RAMSIZEC";
    this.CLASS_NAME = "AtRamSizec";
    this.NUM_ARGS = 3;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      try
      {
        Object localObject2;
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        boolean bool = checkArgu(paramArrayOfString, new String[] { "1", "1", "0" });
        if (bool)
        {
          try
          {
            String str2 = mModuleDevice.getTotalMemory();
            String str3 = responseString(paramArrayOfString[0], str2);
            str1 = str3;
          }
          catch (NullPointerException localNullPointerException)
          {
            responseNG(paramArrayOfString[0]);
            str1 = null;
            continue;
          }
          localObject2 = str1;
          continue;
        }
        String str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      }
      finally {}
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtRamsizec
 * JD-Core Version:    0.7.1
 */