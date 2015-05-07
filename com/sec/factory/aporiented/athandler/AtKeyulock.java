package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleCommon;

public class AtKeyulock
  extends AtCommandHandler
{
  public AtKeyulock(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "KEYULOCK";
    this.CLASS_NAME = "AtKeyulock";
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
          mModuleCommon.setKeyLock(0);
          if (mModuleCommon.getKeyLockState() == 0) {
            str = responseOK(paramArrayOfString[0]);
          } else {
            str = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          mModuleCommon.setKeyLock(1);
          if (mModuleCommon.getKeyLockState() == 1) {
            str = responseOK(paramArrayOfString[0]);
          } else {
            str = responseNG(paramArrayOfString[0]);
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
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtKeyulock
 * JD-Core Version:    0.7.1
 */