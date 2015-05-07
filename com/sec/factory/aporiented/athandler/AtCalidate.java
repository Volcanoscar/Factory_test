package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleCommon;

public class AtCalidate
  extends AtCommandHandler
{
  public AtCalidate(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "CALIDATE";
    this.CLASS_NAME = "AtCalidate";
    this.NUM_ARGS = 4;
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
        if (checkArgu(paramArrayOfString, new String[] { "1", "0", "0", "0" }))
        {
          str1 = responseString(paramArrayOfString[0], mModuleCommon.getCalDate());
        }
        else if (checkArgu(paramArrayOfString, new String[] { "2" }))
        {
          String str2 = paramArrayOfString[1] + paramArrayOfString[2] + paramArrayOfString[3];
          mModuleCommon.setCalDate(str2);
          str1 = responseStringNoArg(paramArrayOfString[0]);
        }
        else
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      Object localObject2 = str1;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtCalidate
 * JD-Core Version:    0.7.1
 */