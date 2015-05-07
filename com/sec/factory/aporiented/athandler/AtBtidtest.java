package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.modules.ModuleCommunication;
import com.sec.factory.support.FtUtil;

public class AtBtidtest
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
        FtUtil.log_i(AtBtidtest.this.CLASS_NAME, "mReceiver.onReceive", "BT Write Response Good");
        if ("boot".equals(AtBtidtest.subString)) {
          FtUtil.log_i(AtBtidtest.this.CLASS_NAME, "mReceiver.onReceive", "BT Read Boot Setting");
        }
        for (;;)
        {
          AtBtidtest.this.stopReceiver(AtBtidtest.this.mReceiver);
          return;
          if (AtBtidtest.this.getCmdType() == 0) {
            AtBtidtest.this.writerCpo.write(2, "27", "02", str1);
          } else {
            AtBtidtest.this.writer.write(AtBtidtest.this.responseString(AtBtidtest.subString, str1));
          }
        }
      }
      if (str1.equals("NG"))
      {
        FtUtil.log_i(AtBtidtest.this.CLASS_NAME, "mReceiver.onReceive", "BT Write/Read Response Bad");
        if ("boot".equals(AtBtidtest.subString)) {
          FtUtil.log_i(AtBtidtest.this.CLASS_NAME, "mReceiver.onReceive", "BT Write Boot Setting");
        }
        for (;;)
        {
          AtBtidtest.this.stopReceiver(AtBtidtest.this.mReceiver);
          return;
          if (AtBtidtest.this.getCmdType() == 0) {
            AtBtidtest.this.writerCpo.write(4, "27", "00", null);
          } else {
            AtBtidtest.this.writer.write(AtBtidtest.this.responseString(AtBtidtest.subString, str1));
          }
        }
      }
      String str2 = "";
      FtUtil.log_i(AtBtidtest.this.CLASS_NAME, "mReceiver.onReceive", "resData: " + str1);
      String[] arrayOfString = str1.split(":");
      for (int i = 0; i < arrayOfString.length; i++) {
        str2 = str2 + arrayOfString[i];
      }
      FtUtil.log_i(AtBtidtest.this.CLASS_NAME, "mReceiver.onReceive", "BT Read Response Good");
      if (AtBtidtest.this.getCmdType() == 0) {
        AtBtidtest.this.writerCpo.write(2, "27", "01", str2);
      }
      for (;;)
      {
        AtBtidtest.this.stopReceiver(AtBtidtest.this.mReceiver);
        return;
        AtBtidtest.this.writer.write(AtBtidtest.this.responseString(AtBtidtest.subString, str2));
      }
    }
  };
  
  public AtBtidtest(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "BTIDTEST";
    this.CLASS_NAME = "AtBtidtest";
    this.NUM_ARGS = 2;
    this.writer = paramResponseWriter;
    subString = "boot";
  }
  
  public AtBtidtest(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "BTIDTEST";
    this.CLASS_NAME = "AtBtidtest";
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
        if ((checkArgu(paramArrayOfString, new String[] { "1", "0" })) || ((getCmdType() == 0) && ("1".equals(paramArrayOfString[0]))))
        {
          startReceiver();
          mModuleCommunication.readBtId();
          int i = getCmdType();
          str = null;
          if (i == 0) {
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
            mModuleCommunication.writeBtId(paramArrayOfString[1]);
            int j = getCmdType();
            str = null;
            if (j == 0) {
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
    localIntentFilter.addAction("com.sec.android.app.bluetoothtest.BT_ID_RESPONSE");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtBtidtest
 * JD-Core Version:    0.7.1
 */