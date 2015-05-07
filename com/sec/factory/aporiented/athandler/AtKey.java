package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDFT;
import com.sec.factory.support.FtUtil;

public class AtKey
  extends AtCommandHandler
{
  public AtKey(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "KEY";
    this.CLASS_NAME = "AtKey";
    if (FtUtil.isFactoryAppAPO())
    {
      this.NUM_ARGS = 1;
      return;
    }
    this.NUM_ARGS = 2;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "wrong";
          return (String)localObject2;
        }
        if (getCmdType() == 0)
        {
          if (paramArrayOfString[0].equals("00"))
          {
            mModuleDFT.DftKey(paramArrayOfString[1]);
            int i = Short.parseShort(paramArrayOfString[0], 16);
            str = String.valueOf((short)(Short.parseShort(paramArrayOfString[1], 16) + i * 256));
            setResultType(9);
            break label126;
          }
          if (!paramArrayOfString[0].equals("01")) {
            continue;
          }
          mModuleDFT.DftKeyHold(paramArrayOfString[1]);
          continue;
        }
        mModuleDFT.DftKey(paramArrayOfString[0]);
      }
      finally {}
      String str = "OK\r\n";
      label126:
      Object localObject2 = str;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtKey
 * JD-Core Version:    0.7.1
 */