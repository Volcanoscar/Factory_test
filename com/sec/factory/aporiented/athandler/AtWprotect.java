package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Kernel;

public class AtWprotect
  extends AtCommandHandler
{
  public AtWprotect(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "WPROTECT";
    this.CLASS_NAME = "AtWprotect";
    this.NUM_ARGS = 2;
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
        int i;
        if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          String str2 = Support.Kernel.read("MMCBLK_DEVICE_CSD");
          FtUtil.log_i(this.CLASS_NAME, "handleCommand", str2);
          if (str2 == null)
          {
            localObject2 = responseNG(paramArrayOfString[0]);
            continue;
          }
          String str3 = str2.substring(28, 29);
          FtUtil.log_i(this.CLASS_NAME, "handleCommand", str3);
          i = 0x3 & Integer.parseInt(str3);
          FtUtil.log_i(this.CLASS_NAME, "handleCommand", Integer.toString(i));
          str1 = null;
        }
        switch (i)
        {
        case 0: 
          str1 = responseString(paramArrayOfString[0], "NONE");
          break;
        case 1: 
          str1 = responseString(paramArrayOfString[0], "TEMP");
          break;
        case 2: 
        case 3: 
          str1 = responseString(paramArrayOfString[0], "PERM");
          break;
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      Object localObject2 = str1;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtWprotect
 * JD-Core Version:    0.7.1
 */