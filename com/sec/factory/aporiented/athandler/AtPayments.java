package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Kernel;
import java.io.File;

public class AtPayments
  extends AtCommandHandler
{
  public AtPayments(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "PAYMENTS";
    this.CLASS_NAME = "AtPayments";
    this.NUM_ARGS = 2;
    if (!new File(Support.Kernel.getFilePath("PRE_PAY")).exists()) {
      try
      {
        mModuleCommon.writePrePay("false");
        Support.Kernel.setPermission(Support.Kernel.getFilePath("PRE_PAY"), true, true, true, true, true, false);
        FtUtil.log_d(this.CLASS_NAME, "AtPayments", "PRE_PAY is created...");
        return;
      }
      finally {}
    }
    FtUtil.log_d(this.CLASS_NAME, "AtPayments", "PRE_PAYis already existed...");
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
        if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          mModuleCommon.writePrePay("false");
          if (mModuleCommon.readPrePay().equals("false")) {
            str1 = responseOK(paramArrayOfString[0]);
          } else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          mModuleCommon.writePrePay("true");
          if (mModuleCommon.readPrePay().equals("true")) {
            str1 = responseOK(paramArrayOfString[0]);
          } else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          String str2 = mModuleCommon.readPrePay();
          if (str2.equals("false")) {
            str1 = responseString(paramArrayOfString[0], "POSTPAY");
          } else if (str2.equals("true")) {
            str1 = responseString(paramArrayOfString[0], "PREPAY");
          } else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      Object localObject2 = str1;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtPayments
 * JD-Core Version:    0.7.1
 */