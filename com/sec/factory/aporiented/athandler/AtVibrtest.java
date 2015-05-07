package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.Support.Feature;

public class AtVibrtest
  extends AtCommandHandler
{
  public AtVibrtest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "VIBRTEST";
    this.CLASS_NAME = "AtVibrtest";
    this.NUM_ARGS = 2;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      String str;
      try
      {
        if ((paramArrayOfString.length != this.NUM_ARGS) || (!Support.Feature.getBoolean("SUPPORT_VIBRATOR", true)))
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          mModuleDevice.startVibrate();
          str = responseStringNoArg(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          mModuleDevice.stopVibrate();
          str = responseStringNoArg(paramArrayOfString[0]);
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
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtVibrtest
 * JD-Core Version:    0.7.1
 */