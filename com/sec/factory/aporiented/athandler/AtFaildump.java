package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemProperties;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.support.FtUtil;
import java.text.DecimalFormat;
import java.util.Calendar;

public class AtFaildump
  extends AtCommandHandler
{
  private String PastMainCMD = "00";
  private String currentStage;
  private String mDestFilePath = "";
  private String mDumpFileName = null;
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      FtUtil.log_d(AtFaildump.this.CLASS_NAME, "onReceive", str);
      if ("com.android.sec.FAILDUMP.DONE".equals(str))
      {
        if (AtFaildump.this.getCmdType() != 0) {
          break label70;
        }
        AtFaildump.this.writerCpo.write(11, " ", " ", " ");
      }
      for (;;)
      {
        AtFaildump.this.stopReceiver(AtFaildump.this.mReceiver);
        return;
        label70:
        AtFaildump.this.writer.write(AtFaildump.this.responseOK("0"));
      }
    }
  };
  public int nFailDumpResult = -1;
  private String sysdump_time;
  
  public AtFaildump(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "FAILDUMP";
    this.CLASS_NAME = "AtFaildump";
    this.NUM_ARGS = 2;
    this.writer = paramResponseWriter;
  }
  
  public AtFaildump(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "FAILDUMP";
    this.CLASS_NAME = "AtFaildump";
    this.NUM_ARGS = 2;
    this.writerCpo = paramResponseWriterCPO;
  }
  
  private void generateAPDumpFileName()
  {
    Calendar localCalendar = Calendar.getInstance();
    this.currentStage = SystemProperties.get("ril.factory_mode", "none");
    this.PastMainCMD = SystemProperties.get("ril.factory_cmd", "00");
    FtUtil.log_d(this.CLASS_NAME, "generateAPDumpFileName", "getFailDump() : " + this.currentStage);
    String str1 = new DecimalFormat("00").format(1 + localCalendar.get(2));
    String str2 = new DecimalFormat("00").format(localCalendar.get(5));
    String str3 = new DecimalFormat("00").format(localCalendar.get(11));
    String str4 = new DecimalFormat("00").format(localCalendar.get(12));
    String str5 = new DecimalFormat("00").format(localCalendar.get(13));
    this.sysdump_time = (localCalendar.get(1) + str1 + str2 + str3 + str4 + str5);
    this.mDestFilePath = "/data/log/";
    this.mDumpFileName = ("factory_" + this.currentStage + "_" + this.PastMainCMD + "_dumpState_" + this.sysdump_time);
    FtUtil.log_d(this.CLASS_NAME, "generateAPDumpFileName", this.mDumpFileName);
  }
  
  private void getAPCPDump()
  {
    FtUtil.log_d(this.CLASS_NAME, "getAPCPDump", "...");
    startReceiver();
    Intent localIntent = new Intent("com.android.sec.FAILDUMP");
    localIntent.putExtra("FILENAME", this.mDumpFileName);
    this.context.sendBroadcast(localIntent);
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          generateAPDumpFileName();
          if (getCmdType() == 0)
          {
            getAPCPDump();
            str = "WAIT";
          }
          else
          {
            getAPCPDump();
            str = null;
          }
        }
      }
      finally {}
      boolean bool = checkArgu(paramArrayOfString, new String[] { "0", "1" });
      String str = null;
      if (!bool) {
        str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      }
      Object localObject2 = str;
    }
  }
  
  public void startReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.android.sec.FAILDUMP.DONE");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFaildump
 * JD-Core Version:    0.7.1
 */