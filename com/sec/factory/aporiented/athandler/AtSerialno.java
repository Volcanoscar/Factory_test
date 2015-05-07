package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.Support.Kernel;

public class AtSerialno
  extends AtCommandHandler
{
  public AtSerialno(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "SERIALNO";
    this.CLASS_NAME = "AtSerialno";
    this.NUM_ARGS = 2;
    String str = Support.Kernel.getFilePath("SERIAL_NO");
    if (!Support.Kernel.isExistFile(str))
    {
      mModuleDevice.writeSerialNo("00000000000");
      Support.Kernel.setPermission(str, true, true, true, true, true, false);
    }
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      String str;
      try
      {
        if (paramArrayOfString.length < this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          str = responseString(paramArrayOfString[0], mModuleDevice.readSerialNo());
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "1" }))
        {
          str = responseString(paramArrayOfString[0], mModuleDevice.readiSerialNo());
        }
        else if (checkArgu(paramArrayOfString, new String[] { "2" }))
        {
          StringBuilder localStringBuilder = new StringBuilder();
          int i = 1;
          if (i < paramArrayOfString.length)
          {
            localStringBuilder.append(paramArrayOfString[i]);
            localStringBuilder.append(",");
            i++;
            continue;
          }
          localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
          mModuleDevice.writeSerialNo(localStringBuilder.toString());
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
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtSerialno
 * JD-Core Version:    0.7.1
 */