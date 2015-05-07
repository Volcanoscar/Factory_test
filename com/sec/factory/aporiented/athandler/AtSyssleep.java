package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModulePower;
import com.sec.factory.support.Support.Feature;

public class AtSyssleep
  extends AtCommandHandler
{
  public AtSyssleep(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "SYSSLEEP";
    this.CLASS_NAME = "AtSyssleep";
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
          ModulePower.instance(this.context).sendAlarmManagerOnOff(false);
          reqestSleep();
          if (getCmdType() == 0) {
            str = responseStringNoArg(paramArrayOfString[0]);
          } else {
            str = responseStringNoArg(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          mModulePower.doWakeLock(true);
          if (getCmdType() == 0) {
            str = responseStringNoArg(paramArrayOfString[0]);
          } else {
            str = responseStringNoArg(paramArrayOfString[0]);
          }
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
  
  public void reqestSleep()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        if (Support.Feature.getBoolean("SUPPORT_DUAL_STANBY")) {
          AtCommandHandler.mModulePower.sendSleepCmd2Ril();
        }
        try
        {
          Thread.sleep(200L);
          AtCommandHandler.mModulePower.sleep();
          return;
        }
        catch (InterruptedException localInterruptedException)
        {
          for (;;)
          {
            localInterruptedException.printStackTrace();
          }
        }
      }
    }).start();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtSyssleep
 * JD-Core Version:    0.7.1
 */