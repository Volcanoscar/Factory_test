package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.modules.ModuleDevice;

public class AtOtggtest
  extends AtCommandHandler
{
  private static String subString = null;
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (paramAnonymousIntent.getAction().equals("com.sec.factory.intent.ACTION_OTG_RESPONSE"))
      {
        if (AtOtggtest.this.getCmdType() != 0) {
          break label58;
        }
        AtOtggtest.this.writerCpo.write(2, "61", "00", paramAnonymousIntent.getStringExtra("result"));
      }
      for (;;)
      {
        AtOtggtest.this.stopReceiver(AtOtggtest.this.mReceiver);
        return;
        label58:
        AtOtggtest.this.writer.write(AtOtggtest.this.responseString(AtOtggtest.subString, paramAnonymousIntent.getStringExtra("result")));
      }
    }
  };
  
  public AtOtggtest(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "OTGGTEST";
    this.CLASS_NAME = "AtOtggtest";
    this.NUM_ARGS = 2;
    this.writer = paramResponseWriter;
  }
  
  public AtOtggtest(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "OTGGTEST";
    this.CLASS_NAME = "AtOtggtest";
    this.NUM_ARGS = 2;
    this.writerCpo = paramResponseWriterCPO;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      String str;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        subString = paramArrayOfString[0];
        if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          startReceiver();
          mModuleDevice.runOTGTest();
          int i = getCmdType();
          str = null;
          if (i == 0) {
            str = "WAIT";
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1" }))
        {
          str = responseString(paramArrayOfString[0], mModuleDevice.check_USB_Port(paramArrayOfString[1]));
        }
        else
        {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      Object localObject2 = str;
    }
  }
  
  public void startReceiver()
  {
    this.context.registerReceiver(this.mReceiver, new IntentFilter("com.sec.factory.intent.ACTION_OTG_RESPONSE"));
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtOtggtest
 * JD-Core Version:    0.7.1
 */