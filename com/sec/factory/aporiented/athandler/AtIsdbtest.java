package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.app.factorytest.MTVTunerTest;
import com.sec.factory.support.FtUtil;

public class AtIsdbtest
  extends AtCommandHandler
{
  private String mCommand;
  private MTVTunerTest mMTVTunerTest;
  
  public AtIsdbtest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "ISDBTEST";
    this.CLASS_NAME = "AtIsdbtest";
    this.NUM_ARGS = 3;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      String str1;
      try
      {
        this.mMTVTunerTest = new MTVTunerTest();
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "ISDB off start");
          if (this.mMTVTunerTest.nativeoneseg_mod_deinit() == 0) {
            str1 = responseOK(paramArrayOfString[0]);
          } else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0" }))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "ISDB on start");
          if (this.mMTVTunerTest.nativeoneseg_mod_init() == 0) {
            str1 = responseOK(paramArrayOfString[0]);
          } else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2" }))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "set freq");
          this.mCommand = paramArrayOfString[2].substring(6, 8);
          if (this.mMTVTunerTest.nativeoneseg_mod_settune(Integer.parseInt(this.mCommand)) == 0) {
            str1 = responseOK(paramArrayOfString[0]);
          } else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "0", "0" }))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "BER read");
          int j = this.mMTVTunerTest.nativeoneseg_mod_getsiginfo(1);
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "ber value : " + j);
          String str3 = Integer.toString(j);
          str1 = responseString(paramArrayOfString[0], str3);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "1", "0" }))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "RSSI read");
          int i = this.mMTVTunerTest.nativeoneseg_mod_getsiginfo(0);
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "rssi value : " + i);
          if (i != 0)
          {
            String str2 = Integer.toString(i);
            str1 = responseString(paramArrayOfString[0], str2);
          }
          else
          {
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
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtIsdbtest
 * JD-Core Version:    0.7.1
 */