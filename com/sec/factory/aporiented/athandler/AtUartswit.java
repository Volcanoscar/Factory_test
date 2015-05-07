package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.FtUtil;

public class AtUartswit
  extends AtCommandHandler
{
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (paramAnonymousIntent.getAction().equals("com.sec.factory.aporiented.athandler.AtUartswit.SetUartPath"))
      {
        String str = paramAnonymousIntent.getStringExtra("PATH");
        FtUtil.log_d(AtUartswit.this.CLASS_NAME, "onReceive", "com.sec.factory.aporiented.athandler.AtUartswit.SetUartPath : Set : " + str);
        AtUartswit.this.writer.write(str);
      }
    }
  };
  
  public AtUartswit(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "UARTSWIT";
    this.CLASS_NAME = "AtUartswit";
    this.NUM_ARGS = 2;
    this.writer = paramResponseWriter;
    startReceiver();
  }
  
  public AtUartswit(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "UARTSWIT";
    this.CLASS_NAME = "AtUartswit";
    this.NUM_ARGS = 2;
    this.writerCpo = paramResponseWriterCPO;
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
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "MODEM");
          if (mModuleCommon.setUartPath("CP")) {
            str1 = responseOK(paramArrayOfString[0]);
          } else {
            str1 = responseStringCMDNG();
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "PDA");
          if (mModuleCommon.setUartPath("AP")) {
            str1 = responseOK(paramArrayOfString[0]);
          } else {
            str1 = responseStringCMDNG();
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2" }))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "LTE");
          if (mModuleCommon.setUartPath("LTE")) {
            str1 = responseOK(paramArrayOfString[0]);
          } else {
            str1 = responseStringCMDNG();
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "3" }))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "ESC");
          if (mModuleCommon.setUartPath("ESC")) {
            str1 = responseOK(paramArrayOfString[0]);
          } else {
            str1 = responseStringCMDNG();
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "Read UART");
          String str2 = mModuleCommon.getUartPath();
          if (str2 == null) {
            break label428;
          }
          if (str2.contains("CP"))
          {
            str3 = "UART1";
            str1 = responseString(paramArrayOfString[0], str3);
          }
          else
          {
            if (str2.contains("AP"))
            {
              str3 = "UART2";
              continue;
            }
            if (str2.contains("LTE"))
            {
              str3 = "UART3";
              continue;
            }
            if (!str2.contains("ESC")) {
              break label421;
            }
            str3 = "UART4";
            continue;
          }
        }
        else
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      Object localObject2 = str1;
      continue;
      label421:
      String str3 = "NG";
      continue;
      label428:
      str3 = "NG";
    }
  }
  
  public void startReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.sec.factory.aporiented.athandler.AtUartswit.SetUartPath");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtUartswit
 * JD-Core Version:    0.7.1
 */