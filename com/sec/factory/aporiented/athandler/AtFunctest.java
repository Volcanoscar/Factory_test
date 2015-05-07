package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.modules.ModulePower;

public class AtFunctest
  extends AtCommandHandler
{
  public AtFunctest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "FUNCTEST";
    this.CLASS_NAME = "AtFunctest";
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
          if (!mModulePower.isScreenOn()) {
            mModulePower.setScreenState(true);
          }
          mModuleCommon.goIdle();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          if (!mModulePower.isScreenOn()) {
            mModulePower.setScreenState(true);
          }
          mModuleCommon.goLcdtest();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2" }))
        {
          if (!mModulePower.isScreenOn()) {
            mModulePower.setScreenState(true);
          }
          mModuleCommon.go15mode();
          str = responseOK(paramArrayOfString[0]);
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
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFunctest
 * JD-Core Version:    0.7.1
 */