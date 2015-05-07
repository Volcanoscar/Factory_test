package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModulePower;

public class AtSwdlmode
  extends AtCommandHandler
{
  public AtSwdlmode(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "SWDLMODE";
    this.CLASS_NAME = "AtSwdlmode";
    this.NUM_ARGS = 1;
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
        if (checkArgu(paramArrayOfString, new String[] { "0" }))
        {
          str = responseOK(paramArrayOfString[0]);
          mModulePower.reboot((byte)1);
        }
        else
        {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      Object localObject2 = str;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtSwdlmode
 * JD-Core Version:    0.7.1
 */