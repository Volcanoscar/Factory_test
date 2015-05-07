package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModulePower;

public class AtFuelgaic
  extends AtCommandHandler
{
  public AtFuelgaic(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "FUELGAIC";
    this.CLASS_NAME = "AtFuelgaic";
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
          if (mModulePower.resetFuelGaugeIC()) {
            str = responseString(paramArrayOfString[0], paramArrayOfString[0]);
          } else {
            str = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1" })) {
          str = responseString(paramArrayOfString[0], mModulePower.readFuelGaugeSOC());
        } else {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      Object localObject2 = str;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFuelgaic
 * JD-Core Version:    0.7.1
 */