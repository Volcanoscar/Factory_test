package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleCommon;

public class AtMainmenu
  extends AtCommandHandler
{
  public AtMainmenu(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "MAINMENU";
    this.CLASS_NAME = "AtMainmenu";
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
          mModuleCommon.goIdle();
          str = null;
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
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtMainmenu
 * JD-Core Version:    0.7.1
 */