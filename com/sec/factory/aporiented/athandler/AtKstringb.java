package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Kernel;
import java.io.File;

public class AtKstringb
  extends AtCommandHandler
{
  public AtKstringb(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "KSTRINGB";
    this.CLASS_NAME = "AtKstringb";
    this.NUM_ARGS = 2;
    if (!new File(Support.Kernel.getFilePath("FACTORY_MODE")).exists()) {}
    for (;;)
    {
      try
      {
        mModuleCommon.writeFactoryMode("OFF");
        Support.Kernel.setPermission(Support.Kernel.getFilePath("FACTORY_MODE"), true, true, true, true, true, false);
        FtUtil.log_d(this.CLASS_NAME, "AtKstringb", "FACTORY_MODE is created...");
        if (new File(Support.Kernel.getFilePath("KEYSTRING_BLOCK")).exists()) {
          break;
        }
        FtUtil.log_d(this.CLASS_NAME, "AtKstringb", "FACTORY_MODEis already existed...");
      }
      finally
      {
        try
        {
          mModuleCommon.writeKeyStringBlock("OFF");
          Support.Kernel.setPermission(Support.Kernel.getFilePath("KEYSTRING_BLOCK"), true, true, true, true, true, false);
          FtUtil.log_d(this.CLASS_NAME, "AtKstringb", "KEYSTRING_BLOCK is created...");
          return;
        }
        finally {}
        localObject2 = finally;
      }
    }
    FtUtil.log_d(this.CLASS_NAME, "AtKstringb", "KEYSTRING_BLOCKis already existed...");
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      Object localObject3;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          String str3 = mModuleCommon.readKeyStringBlock();
          localObject3 = responseString(paramArrayOfString[0], str3);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          mModuleCommon.writeKeyStringBlock("ON");
          localObject3 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          mModuleCommon.writeKeyStringBlock("OFF");
          localObject3 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "4" }))
        {
          mModuleCommon.writeFactoryMode("OFF");
          localObject3 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "5" }))
        {
          mModuleCommon.writeFactoryMode("ON");
          localObject3 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "2" }))
        {
          String str2 = mModuleCommon.readFactoryMode();
          localObject3 = responseString(paramArrayOfString[0], str2);
        }
        else
        {
          String str1 = responseNA();
          localObject3 = str1;
        }
      }
      finally {}
      Object localObject2 = localObject3;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtKstringb
 * JD-Core Version:    0.7.1
 */