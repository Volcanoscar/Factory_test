package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Kernel;

public class AtEwriteck
  extends AtCommandHandler
{
  BroadcastReceiver SpenDetectionReceiver;
  boolean spenInserted = false;
  
  public AtEwriteck(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "EWRITECK";
    this.CLASS_NAME = "AtEwriteck";
    this.NUM_ARGS = 2;
    this.writer = paramResponseWriter;
  }
  
  public AtEwriteck(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "EWRITECK";
    this.CLASS_NAME = "AtEwriteck";
    this.NUM_ARGS = 2;
    this.writerCpo = paramResponseWriterCPO;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      try
      {
        this.SpenDetectionReceiver = new BroadcastReceiver()
        {
          public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
          {
            AtEwriteck.this.spenInserted = paramAnonymousIntent.getBooleanExtra("penInsert", false);
            FtUtil.log_d(AtEwriteck.this.CLASS_NAME, "onReceive", "spenInserted: " + AtEwriteck.this.spenInserted);
            if (AtEwriteck.this.getCmdType() == 0)
            {
              if (!AtEwriteck.this.spenInserted) {
                break label114;
              }
              FtUtil.log_d(AtEwriteck.this.CLASS_NAME, "onReceive", "S pen detected");
              AtEwriteck.this.writerCpo.write(2, "70", "01", "OK");
            }
            for (;;)
            {
              paramAnonymousContext.unregisterReceiver(AtEwriteck.this.SpenDetectionReceiver);
              return;
              label114:
              FtUtil.log_d(AtEwriteck.this.CLASS_NAME, "onReceive", "S pen not detected");
              AtEwriteck.this.writerCpo.write(2, "70", "01", "NG");
            }
          }
        };
        IntentFilter localIntentFilter = new IntentFilter("com.samsung.pen.INSERT");
        this.context.registerReceiver(this.SpenDetectionReceiver, localIntentFilter);
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "registerReceiver");
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        String str1;
        if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          if (getCmdType() != 0) {
            this.context.unregisterReceiver(this.SpenDetectionReceiver);
          }
        }
        else
        {
          if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
          {
            String str2 = Support.Kernel.read("EPEN_DIGITIZER_CHECK");
            if ("OK".equals(str2))
            {
              str1 = responseOK(paramArrayOfString[0]);
              continue;
            }
            if ("NG".equals(str2))
            {
              str1 = responseNG(paramArrayOfString[0]);
              continue;
            }
            FtUtil.log_d(this.CLASS_NAME, "AtEwriteck", " Sysfs file's value is not 0 or 1 ");
            str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
            continue;
          }
          boolean bool = checkArgu(paramArrayOfString, new String[] { "1", "0" });
          if (bool) {}
          try
          {
            Thread.sleep(100L);
            if (this.spenInserted)
            {
              str1 = responseOK(paramArrayOfString[0]);
              if (getCmdType() != 0) {
                continue;
              }
              str1 = "WAIT";
              continue;
            }
            str1 = responseNG(paramArrayOfString[0]);
            continue;
            str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          }
          catch (InterruptedException localInterruptedException)
          {
            continue;
          }
        }
        Object localObject2 = str1;
      }
      finally {}
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtEwriteck
 * JD-Core Version:    0.7.1
 */