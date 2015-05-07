package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.modules.ModuleCommunication;
import com.sec.factory.support.FtUtil;

public class AtFcbttest
  extends AtCommandHandler
{
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str;
      if (paramAnonymousIntent.getAction().equals("com.sec.factory.intent.ACTION_BT_RESPONSE"))
      {
        str = paramAnonymousIntent.getStringExtra("result");
        if (AtFcbttest.this.getCmdType() != 0) {
          break label91;
        }
        FtUtil.log_d(AtFcbttest.this.CLASS_NAME, "onReceive", "resData: " + str);
        AtFcbttest.this.writerCpo.write(2, "06", "00", str);
      }
      for (;;)
      {
        AtFcbttest.this.stopReceiver(AtFcbttest.this.mReceiver);
        return;
        label91:
        AtFcbttest.this.writer.write(AtFcbttest.this.responseString("0", str));
      }
    }
  };
  
  public AtFcbttest(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "FCBTTEST";
    this.CLASS_NAME = "AtFcbttest";
    this.NUM_ARGS = 3;
    this.writer = paramResponseWriter;
  }
  
  public AtFcbttest(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "FCBTTEST";
    this.CLASS_NAME = "AtFcbttest";
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
          startReceiver();
          mModuleCommunication.btActivationWhereAtcmd();
          if (getCmdType() == 0) {
            str = "WAIT";
          } else {
            str = responseStringNoArgAndNoNewLine(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "1" }))
        {
          startReceiver();
          mModuleCommunication.btSearchStart();
          if (getCmdType() == 0) {
            str = "WAIT";
          } else {
            str = responseStringNoArgAndNoNewLine(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "1" }))
        {
          startReceiver();
          mModuleCommunication.btSearchStartWithAck();
          if (getCmdType() == 0) {
            str = "WAIT";
          } else {
            str = responseStringNoArgAndNoNewLine(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "2" }))
        {
          startReceiver();
          mModuleCommunication.btSearchStop();
          if (getCmdType() == 0) {
            str = "WAIT";
          } else {
            str = responseStringNoArgAndNoNewLine(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "3" }))
        {
          startReceiver();
          mModuleCommunication.bleSearchStartWithAckOn10s();
          if (getCmdType() == 0) {
            str = "WAIT";
          } else {
            str = responseStringNoArgAndNoNewLine(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2", "0" }))
        {
          startReceiver();
          mModuleCommunication.btDeactivation();
          if (getCmdType() == 0) {
            str = "WAIT";
          } else {
            str = responseStringNoArgAndNoNewLine(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "3", "0" }))
        {
          mModuleCommunication.btRfTestStart();
          str = responseStringNoArgAndNoNewLine(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2", "1" }))
        {
          mModuleCommunication.btRfTestStop();
          str = responseStringNoArgAndNoNewLine(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "4", "0" }))
        {
          startReceiver();
          mModuleCommunication.btAudioTestStart();
          str = responseStringNoArgAndNoNewLine(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2", "2" }))
        {
          startReceiver();
          mModuleCommunication.btAudioTestStop();
          str = responseStringNoArgAndNoNewLine(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "5", "0" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxOutputChLow();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "5", "1" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxOutputChMid();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "5", "2" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxOutputChHigh();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "5", "3" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxModChLow();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "5", "4" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxModChMid();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "5", "5" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxModChHigh();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "5", "6" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxModChLowAA();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "5", "7" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxModChMidAA();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "5", "8" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxModChHighAA();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "6", "0" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxCarrierChLow();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "6", "1" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxCarrierChMid();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "6", "2" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxCarrierChHigh();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "6", "3" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxInBandChLow();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "6", "4" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxInBandChMid();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "6", "5" }))
        {
          startReceiver();
          mModuleCommunication.btLeTxInBandChHigh();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "6", "6" }))
        {
          startReceiver();
          mModuleCommunication.btLeRxLow();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "6", "7" }))
        {
          startReceiver();
          mModuleCommunication.btLeRxMid();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "6", "8" }))
        {
          startReceiver();
          mModuleCommunication.btLeRxHigh();
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "6", "9" }))
        {
          startReceiver();
          mModuleCommunication.btLeTestEnd();
          if (getCmdType() == 0) {
            str = "WAIT";
          } else {
            str = responseOK(paramArrayOfString[0]);
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
    localIntentFilter.addAction("com.sec.factory.intent.ACTION_BT_RESPONSE");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFcbttest
 * JD-Core Version:    0.7.1
 */