package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.support.FtUtil;
import java.io.File;

public class AtPreconfg
  extends AtCommandHandler
{
  private String code = null;
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      FtUtil.log_i(AtPreconfg.this.CLASS_NAME, "onReceive", "Get intent : " + str1);
      if ("com.sec.factory.aporiented.athandler.atpreconfg".equals(str1))
      {
        String str2 = paramAnonymousIntent.getStringExtra("code");
        FtUtil.log_i(AtPreconfg.this.CLASS_NAME, "onReceive", "code value : " + str2);
        AtPreconfg.this.writer.write(AtPreconfg.this.responseString("0", str2));
        AtPreconfg.this.stopReceiver(AtPreconfg.this.mReceiver);
      }
    }
  };
  
  public AtPreconfg(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "PRECONFG";
    this.CLASS_NAME = "AtPreconfg";
    this.NUM_ARGS = 2;
    this.writer = paramResponseWriter;
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
          FtUtil.log_i(this.CLASS_NAME, "handleCommand", "AtPreconfg=0,0");
          startReceiver();
          Intent localIntent = new Intent("android.intent.action.CSC_COMPARE");
          this.context.sendBroadcast(localIntent);
          str1 = null;
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          FtUtil.log_i(this.CLASS_NAME, "handleCommand", "AtPreconfg=1,0");
          if (!isFileExist("efs/imei/mps_code.dat"))
          {
            str1 = responseNG(paramArrayOfString[0]);
          }
          else
          {
            String str2 = readOneLine("efs/imei/mps_code.dat");
            str1 = responseString(paramArrayOfString[0], str2);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "2" }))
        {
          FtUtil.log_i(this.CLASS_NAME, "handleCommand", "AtPreconfg=2,STRING");
          if (paramArrayOfString[1].length() != 3)
          {
            str1 = responseNG(paramArrayOfString[0]);
          }
          else if (!isFileExist("efs/imei/mps_code.dat"))
          {
            str1 = responseNG(paramArrayOfString[0]);
          }
          else
          {
            writeFile("efs/imei/mps_code.dat", paramArrayOfString[1]);
            str1 = responseOK(paramArrayOfString[0]);
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
  
  public boolean isFileExist(String paramString)
  {
    return new File(paramString).exists();
  }
  
  public void startReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.sec.factory.aporiented.athandler.atpreconfg");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtPreconfg
 * JD-Core Version:    0.7.1
 */