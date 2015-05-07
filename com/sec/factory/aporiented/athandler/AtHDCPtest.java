package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleCommon;

public class AtHDCPtest
  extends AtCommandHandler
{
  public AtHDCPtest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "HDCPTEST";
    this.CLASS_NAME = "AtHDCPtest";
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
        if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          String str6 = mModuleCommon.hdcpCheck(1);
          if (str6 != null)
          {
            if (str6.equals("OK")) {
              str1 = responseOK(paramArrayOfString[0]);
            } else {
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
          else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          String str5 = mModuleCommon.hdcpCheck(2);
          if (str5 != null)
          {
            if (str5.equals("OK")) {
              str1 = responseOK(paramArrayOfString[0]);
            } else {
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
          else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2" }))
        {
          String str4 = mModuleCommon.hdcpCheck(3);
          if (str4 != null)
          {
            if (str4.equals("OK")) {
              str1 = responseOK(paramArrayOfString[0]);
            } else {
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
          else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "3" }))
        {
          String str3 = mModuleCommon.hdcpCheck(4);
          if (str3 != null)
          {
            if (str3.equals("OK")) {
              str1 = responseOK(paramArrayOfString[0]);
            } else {
              str1 = responseNG(paramArrayOfString[0]);
            }
          }
          else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          String str2 = mModuleCommon.hdcpReadSN();
          if (str2.equals("Unknown"))
          {
            str1 = responseNG(paramArrayOfString[0]);
          }
          else
          {
            if (str2.length() < "0000000000000000".length()) {
              str2 = str2 + "0000000000000000".substring(0, "0000000000000000".length() - str2.length());
            }
            str1 = responseString(paramArrayOfString[0], str2);
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
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtHDCPtest
 * JD-Core Version:    0.7.1
 */