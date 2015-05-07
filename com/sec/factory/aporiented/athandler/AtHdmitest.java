package com.sec.factory.aporiented.athandler;

import android.content.Context;
import android.content.Intent;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.app.ui.UIHDMI_Landscape;
import com.sec.factory.app.ui.UIHDMI_Portrait;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;

public class AtHdmitest
  extends AtCommandHandler
{
  public AtHdmitest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "HDMITEST";
    this.CLASS_NAME = "AtHdmitest";
    this.NUM_ARGS = 2;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      String str1;
      Intent localIntent;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return str1;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          String str3 = mModuleCommon.readHDMITestResult();
          FtUtil.log_d(this.CLASS_NAME, "AtHdmitest", "value = " + str3);
          if (str3 == null)
          {
            str1 = responseNG(paramArrayOfString[0]);
            continue;
          }
          if (str3.equals("1"))
          {
            str1 = responseOK(paramArrayOfString[0]);
            continue;
          }
          str1 = responseNG(paramArrayOfString[0]);
          continue;
        }
        if (!checkArgu(paramArrayOfString, new String[] { "0", "2" })) {
          break label285;
        }
        str1 = responseOK(paramArrayOfString[0]);
        localIntent = new Intent();
        String str2 = Support.FactoryTestMenu.getTestCase(FactoryTestManager.convertorID_XML((byte)23));
        FtUtil.log_d(this.CLASS_NAME, "AtHdmitest", "testCase=" + str2);
        if (str2 == null) {
          break label270;
        }
        if (str2.contains("LAND"))
        {
          localIntent.setClass(this.context, UIHDMI_Landscape.class);
          localIntent.setFlags(335544320);
          this.context.startActivity(localIntent);
          continue;
        }
        localIntent.setClass(this.context, UIHDMI_Portrait.class);
      }
      finally {}
      continue;
      label270:
      localIntent.setClass(this.context, UIHDMI_Portrait.class);
      continue;
      label285:
      if (checkArgu(paramArrayOfString, new String[] { "0", "3" }))
      {
        str1 = responseOK(paramArrayOfString[0]);
        this.context.sendBroadcast(new Intent("com.android.samsungtest.HDMITEST_STOP"));
      }
      else
      {
        str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtHdmitest
 * JD-Core Version:    0.7.1
 */