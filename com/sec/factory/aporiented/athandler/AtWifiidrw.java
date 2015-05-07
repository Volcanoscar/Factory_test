package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.modules.ModuleCommunication;

public class AtWifiidrw
  extends AtCommandHandler
{
  private static String subString = null;
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getStringExtra("S_DATA");
      if (str1.equals("OK"))
      {
        if ("boot".equals(AtWifiidrw.subString)) {}
        for (;;)
        {
          AtWifiidrw.this.stopReceiver(AtWifiidrw.this.mReceiver);
          return;
          if (AtWifiidrw.this.getCmdType() == 0) {
            AtWifiidrw.this.writerCpo.write(2, "28", "02", str1);
          } else {
            AtWifiidrw.this.writer.write(AtWifiidrw.this.responseString(AtWifiidrw.subString, str1));
          }
        }
      }
      if (str1.equals("NG"))
      {
        if ("boot".equals(AtWifiidrw.subString)) {}
        for (;;)
        {
          AtWifiidrw.this.stopReceiver(AtWifiidrw.this.mReceiver);
          return;
          if (AtWifiidrw.this.getCmdType() == 0) {
            AtWifiidrw.this.writerCpo.write(4, "28", "02", null);
          } else {
            AtWifiidrw.this.writer.write(AtWifiidrw.this.responseString(AtWifiidrw.subString, str1));
          }
        }
      }
      String str2 = "";
      String[] arrayOfString = str1.split(":");
      for (int i = 0; i < arrayOfString.length; i++) {
        str2 = str2 + arrayOfString[i];
      }
      if (AtWifiidrw.this.getCmdType() == 0) {
        AtWifiidrw.this.writerCpo.write(2, "28", "01", str2);
      }
      for (;;)
      {
        AtWifiidrw.this.stopReceiver(AtWifiidrw.this.mReceiver);
        return;
        AtWifiidrw.this.writer.write(AtWifiidrw.this.responseString(AtWifiidrw.subString, str2));
      }
    }
  };
  
  public AtWifiidrw(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "WIFIIDRW";
    this.CLASS_NAME = "AtWifiidrw";
    this.NUM_ARGS = 2;
    this.writer = paramResponseWriter;
    subString = "boot";
  }
  
  public AtWifiidrw(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "WIFIIDRW";
    this.CLASS_NAME = "AtWifiidrw";
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
        if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          startReceiver();
          mModuleCommunication.readWifiId();
          int j = getCmdType();
          str = null;
          if (j == 0) {
            str = "WAIT";
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "2" }))
        {
          if (paramArrayOfString[1].length() != 12)
          {
            if (getCmdType() == 0) {
              str = responseStringCMDNG();
            } else {
              str = responseNG(paramArrayOfString[0]);
            }
          }
          else
          {
            startReceiver();
            mModuleCommunication.writeWifiId(paramArrayOfString[1]);
            int i = getCmdType();
            str = null;
            if (i == 0) {
              str = "WAIT";
            }
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
    localIntentFilter.addAction("com.sec.android.app.wlantest.WIFI_ID_RESPONSE");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtWifiidrw
 * JD-Core Version:    0.7.1
 */