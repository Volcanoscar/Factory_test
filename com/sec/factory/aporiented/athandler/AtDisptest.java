package com.sec.factory.aporiented.athandler;

import android.content.Context;
import android.content.Intent;
import com.sec.factory.app.ui.UIDisptestActivity;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.modules.ModulePower;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;

public class AtDisptest
  extends AtCommandHandler
{
  private Context mContext;
  
  public AtDisptest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "DISPTEST";
    this.CLASS_NAME = "AtDisptest";
    this.NUM_ARGS = 2;
    this.mContext = paramContext;
  }
  
  private void startDisptest(int paramInt1, int paramInt2)
  {
    Intent localIntent = new Intent(this.mContext, UIDisptestActivity.class);
    localIntent.putExtra("TEST_TYPE", paramInt1);
    localIntent.putExtra("VALUE", paramInt2);
    localIntent.addFlags(335544320);
    this.mContext.startActivity(localIntent);
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      Object localObject2;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          String str3 = responseNA();
          localObject3 = str3;
          return (String)localObject3;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          startDisptest(0, -1);
          localObject2 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          startDisptest(1, -1);
          localObject2 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2" }))
        {
          startDisptest(2, -1);
          localObject2 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "3" }))
        {
          mModulePower.setScreenState(true);
          mModulePower.doWakeLock(true);
          mModulePower.doPartialWakeLock(false);
          localObject2 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "4" }))
        {
          mModulePower.doPartialWakeLock(true);
          mModulePower.doWakeLock(false);
          if (Support.Feature.getBoolean("NEED_LPA_MODE_SET", true)) {
            mModuleDevice.setLPAmode("0");
          }
          mModulePower.setScreenState(false);
          localObject2 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "5" }))
        {
          mModulePower.setBrightness(20);
          localObject2 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "6" }))
        {
          startDisptest(3, -1);
          localObject2 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "7" }))
        {
          localObject2 = responseNA();
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "8" }))
        {
          localObject2 = responseNA();
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "9" }))
        {
          startDisptest(4, -1);
          localObject2 = responseOK(paramArrayOfString[0]);
        }
        else if ("1".equals(paramArrayOfString[0]))
        {
          String str1 = "/";
          if (mModuleDevice.isMountedStorage(1))
          {
            str1 = mModuleDevice.getStoragePath(1) + "/" + paramArrayOfString[1] + ".bmp";
            if (Support.Kernel.isExistFile(str1))
            {
              startDisptest(5, Integer.valueOf(paramArrayOfString[1]).intValue());
              localObject2 = responseOK(paramArrayOfString[0]);
              break label718;
            }
          }
          else
          {
            if (!mModuleDevice.isMountedStorage(0)) {
              continue;
            }
            str1 = mModuleDevice.getStoragePath(0) + "/" + paramArrayOfString[1] + ".bmp";
            continue;
          }
          localObject2 = responseNG(paramArrayOfString[0]);
        }
        else if ("3".equals(paramArrayOfString[0]))
        {
          int i = -1;
          if ("0".equals(paramArrayOfString[1]))
          {
            i = 255;
            mModulePower.setBrightness(i);
            localObject2 = responseOK(paramArrayOfString[0]);
          }
          else
          {
            if ("1".equals(paramArrayOfString[1]))
            {
              i = 140;
              continue;
            }
            if (!"2".equals(paramArrayOfString[1])) {
              continue;
            }
            i = 30;
            continue;
          }
        }
        else
        {
          String str2 = responseNA();
          localObject2 = str2;
        }
      }
      finally {}
      label718:
      Object localObject3 = localObject2;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtDisptest
 * JD-Core Version:    0.7.1
 */