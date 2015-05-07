package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModulePower;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Kernel;
import com.sec.factory.support.Support.TestCase;

public class AtBatttest
  extends AtCommandHandler
{
  public int mBatteryHealth = -1;
  public int mBatteryStatus = -1;
  
  public AtBatttest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "BATTTEST";
    this.CLASS_NAME = "AtBatttest";
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
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          break label205;
        }
        if (checkArgu(paramArrayOfString, new String[] { "1", "2" }))
        {
          boolean bool = Support.TestCase.getEnabled("AT_BATTEST_RESET_WHEN_READ");
          FtUtil.log_i(this.CLASS_NAME, "handleCommand", "isresetneeded: " + bool);
          if (bool)
          {
            mModulePower.resetFuelGaugeIC();
            str = responseString(paramArrayOfString[0], mModulePower.readBatteryVoltage());
            break label205;
          }
          Support.Kernel.write("BATTERY_UPDATE_BEFORE_READ", "1");
          continue;
        }
      }
      finally {}
      tmp157_154[0] = "1";
      String[] tmp162_157 = tmp157_154;
      tmp162_157[1] = "4";
      if (checkArgu(paramArrayOfString, tmp162_157))
      {
        FtUtil.log_i(this.CLASS_NAME, "handleCommand", "read capacity");
        str = responseString(paramArrayOfString[0], Support.Kernel.read("BATTERY_CAPACITY"));
      }
      else
      {
        str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      }
      label205:
      Object localObject2 = str;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtBatttest
 * JD-Core Version:    0.7.1
 */