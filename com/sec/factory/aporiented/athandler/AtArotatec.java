package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleCommon;

public class AtArotatec
  extends AtCommandHandler
{
  public AtArotatec(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "AROTATEC";
    this.CLASS_NAME = "AtArotatec";
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
      if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
      {
        mModuleCommon.setAutoRotate(0);
        if (mModuleCommon.getAutoRotateState() == 0) {
          str = responseOK(paramArrayOfString[0]);
        } else {
          str = responseNG(paramArrayOfString[0]);
        }
      }
      else
      {
        if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          mModuleCommon.setAutoRotate(1);
          if (mModuleCommon.getAutoRotateState() == 1)
          {
            str = responseOK(paramArrayOfString[0]);
            break label240;
          }
          str = responseNG(paramArrayOfString[0]);
          break label240;
        }
        if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          int i = mModuleCommon.getAutoRotateState();
          if (i == 1) {
            str = responseString("1", "ON");
          } else if (i == 0) {
            str = responseString("1", "OFF");
          } else {
            str = responseNG(paramArrayOfString[0]);
          }
        }
        else
        {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
    }
    finally {}
    label240:
    for (;;)
    {
      localObject2 = str;
      break;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtArotatec
 * JD-Core Version:    0.7.1
 */