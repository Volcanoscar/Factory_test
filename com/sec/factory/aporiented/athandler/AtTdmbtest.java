package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.support.FtUtil;

public class AtTdmbtest
  extends AtCommandHandler
{
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      FtUtil.log_d(AtTdmbtest.this.CLASS_NAME, "onReceive", str);
      if ("com.android.samsungtest.DMB_GOOD".equals(str))
      {
        FtUtil.log_d(AtTdmbtest.this.CLASS_NAME, "onReceive", "DMB_GOOD");
        if (AtTdmbtest.this.getCmdType() == 0)
        {
          FtUtil.log_d(AtTdmbtest.this.CLASS_NAME, "handleCommand", "DMB_GOOD_CPO");
          AtTdmbtest.this.writerCpo.write(3, "41", "00", null);
        }
      }
      for (;;)
      {
        AtTdmbtest.this.stopReceiver(AtTdmbtest.this.mReceiver);
        return;
        FtUtil.log_d(AtTdmbtest.this.CLASS_NAME, "handleCommand", "DMB_GOOD_APO");
        AtTdmbtest.this.writer.write(AtTdmbtest.this.responseStringNoArg("0"));
        continue;
        if ("com.android.samsungtest.DMB_BAD".equals(str))
        {
          FtUtil.log_d(AtTdmbtest.this.CLASS_NAME, "onReceive", "DMB_BAD");
          if (AtTdmbtest.this.getCmdType() == 0)
          {
            FtUtil.log_d(AtTdmbtest.this.CLASS_NAME, "onReceive", "DMB_BAD_CPO");
            AtTdmbtest.this.writerCpo.write(4, "41", "00", null);
          }
          else
          {
            FtUtil.log_d(AtTdmbtest.this.CLASS_NAME, "onReceive", "DMB_BAD_APO");
            AtTdmbtest.this.writer.write(AtTdmbtest.this.responseNG("0"));
          }
        }
      }
    }
  };
  
  public AtTdmbtest(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "TDMBTEST";
    this.CLASS_NAME = "AtTdmbtest";
    this.NUM_ARGS = 3;
    this.writer = paramResponseWriter;
  }
  
  public AtTdmbtest(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "TDMBTEST";
    this.CLASS_NAME = "AtTdmbtest";
    this.NUM_ARGS = 3;
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
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "TDMB off start");
          startReceiver();
          Intent localIntent2 = new Intent();
          localIntent2.setClassName("com.sec.android.app.dmb", "com.sec.android.app.dmb.DMBFactoryService");
          localIntent2.setFlags(268435456);
          localIntent2.putExtra("COMMAND", "com.android.samsungtest.DMBPowerOff");
          this.context.startService(localIntent2);
          int j = getCmdType();
          str = null;
          if (j == 0) {
            str = "WAIT";
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0" }))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "TDMB on start");
          startReceiver();
          Intent localIntent1 = new Intent();
          localIntent1.setClassName("com.sec.android.app.dmb", "com.sec.android.app.dmb.DMBFactoryService");
          localIntent1.setFlags(268435456);
          localIntent1.putExtra("COMMAND", "com.android.samsungtest.DMBPowerOn");
          this.context.startService(localIntent1);
          int i = getCmdType();
          str = null;
          if (i == 0) {
            str = "WAIT";
          }
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
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.android.samsungtest.DMB_GOOD");
    localIntentFilter.addAction("com.android.samsungtest.DMB_BAD");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtTdmbtest
 * JD-Core Version:    0.7.1
 */