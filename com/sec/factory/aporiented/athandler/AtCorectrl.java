package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.Support.Kernel;

public class AtCorectrl
  extends AtCommandHandler
{
  public AtCorectrl(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "CORECTRL";
    this.CLASS_NAME = "AtCorectrl";
    this.NUM_ARGS = 3;
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
        if (checkArgu(paramArrayOfString, new String[] { "1", "1", "0" }))
        {
          if (mModuleCommon.readCpuOnline().equals("0-3"))
          {
            if (Support.Kernel.write("HOT_PLUG_LOCK", "0")) {
              localObject3 = responseString(paramArrayOfString[0], "4");
            } else {
              localObject3 = responseNG(paramArrayOfString[0]);
            }
          }
          else if ((mModuleCommon.readCpuOnline().equals("0-2")) || (mModuleCommon.readCpuOnline().equals("0-1,3")) || (mModuleCommon.readCpuOnline().equals("0,2-3")) || (mModuleCommon.readCpuOnline().equals("1-3")))
          {
            if (Support.Kernel.write("HOT_PLUG_LOCK", "0")) {
              localObject3 = responseString(paramArrayOfString[0], "3");
            } else {
              localObject3 = responseNG(paramArrayOfString[0]);
            }
          }
          else if ((mModuleCommon.readCpuOnline().equals("0-1")) || (mModuleCommon.readCpuOnline().equals("0,2")) || (mModuleCommon.readCpuOnline().equals("0,3")))
          {
            if (Support.Kernel.write("HOT_PLUG_LOCK", "0")) {
              localObject3 = responseString(paramArrayOfString[0], "2");
            } else {
              localObject3 = responseNG(paramArrayOfString[0]);
            }
          }
          else if (mModuleCommon.readCpuOnline().equals("0")) {
            localObject3 = responseString(paramArrayOfString[0], "0");
          } else {
            localObject3 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0" }))
        {
          if (Support.Kernel.write("HOT_PLUG_LOCK", "0")) {
            localObject3 = responseOK(paramArrayOfString[0]);
          } else {
            localObject3 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "1" }))
        {
          if (Support.Kernel.write("HOT_PLUG_LOCK", "1")) {
            localObject3 = responseOK(paramArrayOfString[0]);
          } else {
            localObject3 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "2" }))
        {
          if (Support.Kernel.write("HOT_PLUG_LOCK", "2")) {
            localObject3 = responseOK(paramArrayOfString[0]);
          } else {
            localObject3 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "3" }))
        {
          if (Support.Kernel.write("HOT_PLUG_LOCK", "3")) {
            localObject3 = responseOK(paramArrayOfString[0]);
          } else {
            localObject3 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "4" }))
        {
          if (Support.Kernel.write("HOT_PLUG_LOCK", "4")) {
            localObject3 = responseOK(paramArrayOfString[0]);
          } else {
            localObject3 = responseNG(paramArrayOfString[0]);
          }
        }
        else
        {
          String str = responseNA();
          localObject3 = str;
        }
      }
      finally {}
      Object localObject2 = localObject3;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtCorectrl
 * JD-Core Version:    0.7.1
 */