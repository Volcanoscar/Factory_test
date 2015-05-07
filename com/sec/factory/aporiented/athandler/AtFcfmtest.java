package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.modules.ModuleCommunication;
import com.sec.factory.support.Support.Feature;

public class AtFcfmtest
  extends AtCommandHandler
{
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      String str2 = paramAnonymousIntent.getStringExtra("signal_strength");
      if (str1.equals("test.mode.radio.on.response")) {
        AtFcfmtest.this.writer.write(AtFcfmtest.this.responseStringNoArg("0"));
      }
      if (str1.equals("test.mode.radio.off.response")) {
        AtFcfmtest.this.writer.write(AtFcfmtest.this.responseStringNoArg("0"));
      }
      if ("NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))) {
        while (str2.length() < 4) {
          str2 = str2 + " ";
        }
      }
      if (str1.equals("test.mode.radio.freq.response"))
      {
        if (AtFcfmtest.this.getCmdType() != 0) {
          break label204;
        }
        AtFcfmtest.this.writerCpo.write(2, "08", "01", str2);
      }
      for (;;)
      {
        if (str1.equals("test.mode.radio.factoryrssi.response")) {
          AtFcfmtest.this.writer.write(AtFcfmtest.this.responseString("1", paramAnonymousIntent.getStringExtra("signal_strength")));
        }
        AtFcfmtest.this.stopReceiver(AtFcfmtest.this.mReceiver);
        return;
        label204:
        AtFcfmtest.this.writer.write(AtFcfmtest.this.responseString("1", paramAnonymousIntent.getStringExtra("signal_strength")));
      }
    }
  };
  
  public AtFcfmtest(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "FCFMTEST";
    this.CLASS_NAME = "AtFcfmtest";
    this.NUM_ARGS = 4;
    this.writer = paramResponseWriter;
  }
  
  public AtFcfmtest(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "FCFMTEST";
    this.CLASS_NAME = "AtFcfmtest";
    this.NUM_ARGS = 4;
    this.writerCpo = paramResponseWriterCPO;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    Object localObject2;
    String str4;
    for (;;)
    {
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if ((paramArrayOfString[3] != null) && (paramArrayOfString[3].contains("."))) {
          paramArrayOfString[3] = paramArrayOfString[3].replace(".", "");
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
        {
          StringBuilder localStringBuilder3 = new StringBuilder().append(paramArrayOfString[3]);
          if ((paramArrayOfString[3].startsWith("1")) || (!paramArrayOfString[3].endsWith("30"))) {
            break label754;
          }
          str4 = "0";
          label116:
          paramArrayOfString[3] = str4;
          mModuleCommunication.fmRadioOn(paramArrayOfString[3], 0);
          if (Support.Feature.getBoolean("FM_RADIO_RESPONSE"))
          {
            this.context.registerReceiver(this.mReceiver, new IntentFilter("test.mode.radio.on.response"));
            str1 = null;
          }
          else
          {
            str1 = responseStringNoArg(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "1" }))
        {
          StringBuilder localStringBuilder2 = new StringBuilder().append(paramArrayOfString[3]);
          if ((!paramArrayOfString[3].startsWith("1")) && (paramArrayOfString[3].endsWith("30")))
          {
            str3 = "0";
            paramArrayOfString[3] = str3;
            mModuleCommunication.fmRadioOn(paramArrayOfString[3], 1);
            if (!Support.Feature.getBoolean("FM_RADIO_RESPONSE")) {
              break;
            }
            this.context.registerReceiver(this.mReceiver, new IntentFilter("test.mode.radio.on.response"));
            str1 = null;
          }
        }
      }
      finally {}
      String str3 = "00";
    }
    String str1 = responseStringNoArg(paramArrayOfString[0]);
    break label748;
    if (!checkArgu(paramArrayOfString, new String[] { "0", "1", "0", "0" }))
    {
      if (!checkArgu(paramArrayOfString, new String[] { "0", "1", "0" })) {}
    }
    else
    {
      mModuleCommunication.fmRadioOff();
      if (Support.Feature.getBoolean("FM_RADIO_RESPONSE"))
      {
        this.context.registerReceiver(this.mReceiver, new IntentFilter("test.mode.radio.off.response"));
        str1 = null;
        break label748;
      }
      str1 = responseStringNoArg(paramArrayOfString[0]);
      break label748;
    }
    StringBuilder localStringBuilder1;
    if (checkArgu(paramArrayOfString, new String[] { "1", "0", "0" }))
    {
      this.context.registerReceiver(this.mReceiver, new IntentFilter("test.mode.radio.freq.response"));
      localStringBuilder1 = new StringBuilder().append(paramArrayOfString[3]);
      if ((paramArrayOfString[3].startsWith("1")) || (!paramArrayOfString[3].endsWith("30"))) {
        break label761;
      }
    }
    label748:
    label754:
    label761:
    for (String str2 = "0";; str2 = "00")
    {
      paramArrayOfString[3] = str2;
      mModuleCommunication.fmRadioReadFrequencyIntensity(paramArrayOfString[3]);
      int j = getCmdType();
      str1 = null;
      if (j == 0)
      {
        str1 = "WAIT";
        break label748;
        if (checkArgu(paramArrayOfString, new String[] { "1", "0", "1" }))
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "1", "0", "0" }))
        {
          this.context.registerReceiver(this.mReceiver, new IntentFilter("test.mode.radio.factoryrssi.response"));
          mModuleCommunication.fmRadioReadFactoryRssi();
          int i = getCmdType();
          str1 = null;
          if (i == 0) {
            str1 = "WAIT";
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "2", "0", "0" }))
        {
          mModuleCommunication.fmRadioWriteFactoryRssi(paramArrayOfString[3]);
          str1 = responseString(paramArrayOfString[0], "OK");
        }
        else
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      localObject2 = str1;
      break;
      str4 = "00";
      break label116;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFcfmtest
 * JD-Core Version:    0.7.1
 */