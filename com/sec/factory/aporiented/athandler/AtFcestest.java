package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.FtUtil;

public class AtFcestest
  extends AtCommandHandler
{
  public AtFcestest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "FCESTEST";
    this.CLASS_NAME = "AtFcestest";
    this.NUM_ARGS = 1;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        boolean bool = checkArgu(paramArrayOfString, new String[] { "1" });
        if (!bool) {
          break label108;
        }
        try
        {
          FtUtil.log_d(this.CLASS_NAME, "delay", " : 1s");
          Thread.sleep(1000L);
          if (mModuleDevice.isEarSwitchPress()) {
            str = responseString(paramArrayOfString[0], "ON");
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
          continue;
        }
        str = responseString(paramArrayOfString[0], "OFF");
      }
      finally {}
      break label112;
      label108:
      String str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      label112:
      Object localObject2 = str;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFcestest
 * JD-Core Version:    0.7.1
 */