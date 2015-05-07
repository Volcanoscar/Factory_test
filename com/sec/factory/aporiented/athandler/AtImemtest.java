package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.Support.Feature;

public class AtImemtest
  extends AtCommandHandler
{
  public AtImemtest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "IMEMTEST";
    this.CLASS_NAME = "AtImemtest";
    this.NUM_ARGS = 2;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      Object localObject2;
      String str;
      float f2;
      float f1;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          str = null;
        }
        else
        {
          if (checkArgu(paramArrayOfString, new String[] { "1", "1" }))
          {
            if (mModuleDevice.isInnerMemoryExist())
            {
              str = responseOK(paramArrayOfString[0]);
              break label335;
            }
            str = responseNG(paramArrayOfString[0]);
            break label335;
          }
          if (checkArgu(paramArrayOfString, new String[] { "1", "2" }))
          {
            str = responseString(paramArrayOfString[0], Long.toString(mModuleDevice.getInnerMemorySize()));
          }
          else if (checkArgu(paramArrayOfString, new String[] { "1", "4" }))
          {
            long l1 = mModuleDevice.getAvailableSize(2, 1024);
            long l2 = mModuleDevice.getAvailableSize(0, 1024);
            if (Support.Feature.getBoolean("NEED_IMEM_CHECK_DEVICESIZE"))
            {
              long l3 = mModuleDevice.getInnerMemoryDevSize(1024);
              f2 = (float)(l3 / 1048576L);
              if (l3 % 1048576L != 0L) {
                f2 += 1.0F;
              }
              str = responseString(paramArrayOfString[0], Integer.toString((int)f2) + "," + l1 + "," + l2);
            }
            else
            {
              f1 = (float)mModuleDevice.getSize(0, 1024) / 1048576.0F;
              if (f1 > 4.0F) {
                break label338;
              }
              f2 = 4.0F;
              continue;
            }
          }
          else
          {
            str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          }
        }
      }
      finally {}
      label335:
      for (;;)
      {
        localObject2 = str;
        break;
      }
      label338:
      if (f1 <= 8.0F) {
        f2 = 8.0F;
      } else if (f1 <= 16.0F) {
        f2 = 16.0F;
      } else if (f1 <= 32.0F) {
        f2 = 32.0F;
      } else {
        f2 = 64.0F;
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtImemtest
 * JD-Core Version:    0.7.1
 */