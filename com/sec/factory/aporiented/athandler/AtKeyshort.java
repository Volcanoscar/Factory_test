package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDevice;

public class AtKeyshort
  extends AtCommandHandler
{
  public AtKeyshort(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "KEYSHORT";
    this.CLASS_NAME = "AtKeyshort";
    this.NUM_ARGS = 2;
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
        if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          String str2 = paramArrayOfString[0];
          if (!mModuleDevice.isKeyPress()) {
            break label89;
          }
          str3 = "PRESS";
          str1 = responseString(str2, str3);
        }
        else
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      Object localObject2 = str1;
      continue;
      label89:
      String str3 = "RELEASE";
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtKeyshort
 * JD-Core Version:    0.7.1
 */