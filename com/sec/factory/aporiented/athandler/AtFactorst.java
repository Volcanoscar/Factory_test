package com.sec.factory.aporiented.athandler;

import android.content.Context;
import android.content.Intent;
import com.sec.factory.support.FtUtil;

public class AtFactorst
  extends AtCommandHandler
{
  public AtFactorst(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "FACTORST";
    this.CLASS_NAME = "AtFactorst";
    this.NUM_ARGS = 2;
  }
  
  private void DoFactoryReset()
  {
    Intent localIntent = new Intent("android.intent.action.SEC_FACTORY_RESET");
    localIntent.putExtra("factory", true);
    this.context.sendBroadcast(localIntent);
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
          FtUtil.log_i(this.CLASS_NAME, "handleCommand", "run factory reset");
          DoFactoryReset();
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
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFactorst
 * JD-Core Version:    0.7.1
 */