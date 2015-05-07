package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleTouchScreen;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.TestCase;

public class AtTspptestStandard
  extends AtCommandHandler
{
  public AtTspptestStandard(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "TSPPTEST";
    this.CLASS_NAME = "AtTspptestStandard";
    this.NUM_ARGS = 3;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    label1164:
    for (;;)
    {
      Object localObject2;
      String str1;
      String str11;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
        {
          String str15 = mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__POWER_OFF);
          if (str15.equals("OK")) {
            str1 = responseOK(paramArrayOfString[0]);
          } else if (str15.equals("NA")) {
            str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          } else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0" }))
        {
          String str14 = mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__POWER_ON);
          if (str14.equals("OK")) {
            str1 = responseOK(paramArrayOfString[0]);
          } else if (str14.equals("NA")) {
            str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          } else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2", "0" }))
        {
          String str13 = mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__POWER_OFF_SECOND_CHIP);
          if (str13.equals("OK")) {
            str1 = responseOK(paramArrayOfString[0]);
          } else if (str13.equals("NA")) {
            str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          } else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "3", "0" }))
        {
          String str12 = mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__POWER_ON_SECOND_CHIP);
          if (str12.equals("OK")) {
            str1 = responseOK(paramArrayOfString[0]);
          } else if (str12.equals("NA")) {
            str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          } else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "4", "0" }))
        {
          str11 = mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__EXPANSION__CONNECTION);
          if (str11.equals("NG"))
          {
            FtUtil.log_e(this.CLASS_NAME, "handleCommand", "TSP_RESULT_VALUE_NG");
            str1 = responseNG(paramArrayOfString[0]);
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "resData : " + str1);
          }
        }
      }
      finally {}
      String[] arrayOfString = str11.split(",");
      FtUtil.log_d(this.CLASS_NAME, "handleCommand", "[0]" + arrayOfString[0] + " , [1]" + arrayOfString[1] + " , [2]" + arrayOfString[2]);
      if (arrayOfString[0].equals("OK"))
      {
        str1 = responseOK(paramArrayOfString[0]);
      }
      else
      {
        str1 = responseNG(paramArrayOfString[0]);
        break label1164;
        if (checkArgu(paramArrayOfString, new String[] { "1", "0", "0" }))
        {
          String str9 = mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__VENDOR_NAME);
          String str10 = mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__CHIP_NAME);
          str1 = responseString(paramArrayOfString[0], str9 + "," + str10);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "1" }))
        {
          String str8 = mModuleTouchScreen.getTSPResult_Read(1, Integer.parseInt(paramArrayOfString[2]));
          str1 = responseString(paramArrayOfString[0], str8);
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "resData : " + str1);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "2" }))
        {
          String str7 = mModuleTouchScreen.getTSPResult_Read(2, Integer.parseInt(paramArrayOfString[2]));
          str1 = responseString(paramArrayOfString[0], str7);
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "resData : " + str1);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "3" }))
        {
          String str6 = mModuleTouchScreen.getTSPResult_Read(3, Integer.parseInt(paramArrayOfString[2]));
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "resData : " + str6);
          str1 = responseString(paramArrayOfString[0], str6);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "4" }))
        {
          String str5 = mModuleTouchScreen.getTSPResult_Read(4, Integer.parseInt(paramArrayOfString[2]));
          str1 = responseString(paramArrayOfString[0], str5);
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "resData : " + str1);
        }
        else
        {
          if (checkArgu(paramArrayOfString, new String[] { "1", "5", "0" }))
          {
            String str2;
            if (Support.TestCase.getEnabled("IS_TSP_STANDARD_CHANNEL")) {
              str2 = mModuleTouchScreen.getTSPChannelCountY();
            }
            for (String str3 = mModuleTouchScreen.getTSPChannelCountX();; str3 = mModuleTouchScreen.getTSPChannelCountY())
            {
              String str4 = str2 + "," + str3;
              FtUtil.log_d(this.CLASS_NAME, "handleCommand", "result : " + str4);
              str1 = responseString(paramArrayOfString[0], str4);
              FtUtil.log_d(this.CLASS_NAME, "handleCommand", "resData : " + str1);
              break;
              str2 = mModuleTouchScreen.getTSPChannelCountX();
            }
          }
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
        localObject2 = str1;
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtTspptestStandard
 * JD-Core Version:    0.7.1
 */