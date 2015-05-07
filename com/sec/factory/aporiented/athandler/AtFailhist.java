package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Properties;

public class AtFailhist
  extends AtCommandHandler
{
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      FtUtil.log_d(AtFailhist.this.CLASS_NAME, "onReceive", str);
      if ("com.android.sec.FAILHIST.DONE".equals(str)) {}
      AtFailhist.this.stopReceiver(AtFailhist.this.mReceiver);
    }
  };
  
  public AtFailhist(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "FAILHIST";
    this.CLASS_NAME = "AtFailhist";
    this.NUM_ARGS = 3;
    this.writer = paramResponseWriter;
  }
  
  public AtFailhist(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "FAILHIST";
    this.CLASS_NAME = "AtFailhist";
    this.NUM_ARGS = 3;
    this.writerCpo = paramResponseWriterCPO;
  }
  
  private boolean setProperties(String paramString)
  {
    FtUtil.log_d(this.CLASS_NAME, "setProperties", "cmd = " + paramString);
    Support.Properties.set("FACTORYAPP_FAILHIST", paramString);
    return true;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      String str1;
      try
      {
        if (paramArrayOfString.length < this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "1", "0", "0" }))
        {
          if (paramArrayOfString.length != this.NUM_ARGS)
          {
            localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
            continue;
          }
          String str2 = Support.Properties.get("FACTORYAPP_FAILHIST");
          if (str2 == null)
          {
            str1 = responseNG(paramArrayOfString[0]);
          }
          else if (str2 == "Unknown")
          {
            str1 = responseString(paramArrayOfString[0], "none");
          }
          else
          {
            String str3 = str2.replaceAll("\"", "");
            str1 = responseString(paramArrayOfString[0], str3);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "2", "0" }))
        {
          if (paramArrayOfString.length != 3)
          {
            localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
            continue;
          }
          if (!setProperties(paramArrayOfString[2].replaceAll("\"", ""))) {
            str1 = responseNG(paramArrayOfString[0]);
          } else {
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
  
  public void startReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.android.sec.FAILHIST.DONE");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFailhist
 * JD-Core Version:    0.7.1
 */