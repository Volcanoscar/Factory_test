package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDevice;

public class AtMemoSize
  extends AtCommandHandler
{
  public AtMemoSize(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "MEMOSIZE";
    this.CLASS_NAME = "AtMemoSize";
    this.NUM_ARGS = 2;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    Object localObject2;
    String str;
    try
    {
      if (paramArrayOfString.length != this.NUM_ARGS)
      {
        localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        return (String)localObject2;
      }
      if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
      {
        str = responseString(paramArrayOfString[0], mModuleDevice.mainMEMOSize());
      }
      else
      {
        if (checkArgu(paramArrayOfString, new String[] { "1", "1" }))
        {
          if (mModuleDevice.isInnerMemoryExist())
          {
            str = responseString(paramArrayOfString[0], mModuleDevice.int_extMEMOSize(0));
            break label217;
          }
          str = responseNG(paramArrayOfString[0]);
          break label217;
        }
        if (checkArgu(paramArrayOfString, new String[] { "1", "3" }))
        {
          if (mModuleDevice.isMountedStorage(1))
          {
            str = responseString(paramArrayOfString[0], mModuleDevice.int_extMEMOSize(1));
            break label220;
          }
          str = responseNG(paramArrayOfString[0]);
          break label220;
        }
        if (getCmdType() == 0) {
          str = responseNA();
        } else {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
    }
    finally {}
    label217:
    label220:
    for (;;)
    {
      localObject2 = str;
      break;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtMemoSize
 * JD-Core Version:    0.7.1
 */