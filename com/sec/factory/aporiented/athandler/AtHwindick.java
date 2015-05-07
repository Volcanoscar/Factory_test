package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleCommon;

public class AtHwindick
  extends AtCommandHandler
{
  public AtHwindick(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "HWINDICK";
    this.CLASS_NAME = "AtHwindick";
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
        if (checkArgu(paramArrayOfString, new String[] { "1", "0" })) {
          str = responseString(paramArrayOfString[0], mModuleCommon.getHWRevision());
        } else if (checkArgu(paramArrayOfString, new String[] { "1", "1" })) {
          str = responseString(paramArrayOfString[0], mModuleCommon.readLcdType());
        } else if (checkArgu(paramArrayOfString, new String[] { "1", "2" })) {
          str = responseString(paramArrayOfString[0], mModuleCommon.readBatteryType() + "_" + mModuleCommon.readBatteryVF());
        } else if (checkArgu(paramArrayOfString, new String[] { "1", "3" })) {
          str = responseString(paramArrayOfString[0], mModuleCommon.readCameraRearType());
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
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtHwindick
 * JD-Core Version:    0.7.1
 */