package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.support.FtUtil;

public class AtNfcmtest
  extends AtCommandHandler
{
  private final String ACTION_NFC_RESPONSE = "com.sec.android.app.nfcntest.NFC_TEST_RESPONSE";
  private boolean mEndReceiver = true;
  private Handler mNfcTestHandler;
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1;
      int i;
      if ("com.sec.android.app.nfcntest.NFC_TEST_RESPONSE".equals(paramAnonymousIntent.getAction()))
      {
        str1 = paramAnonymousIntent.getStringExtra("S_DATA");
        if (str1 == null) {
          str1 = paramAnonymousIntent.getStringExtra("I_DATA");
        }
        i = paramAnonymousIntent.getIntExtra("CMDID", 0);
        FtUtil.log_d(AtNfcmtest.this.CLASS_NAME, "onReceive", "resCmd: " + i);
        if (AtNfcmtest.this.getCmdType() != 0) {
          break label208;
        }
        String str2 = AtNfcmtest.this.CLASS_NAME;
        StringBuilder localStringBuilder = new StringBuilder().append("resData: ").append(str1).append(" ");
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(i);
        FtUtil.log_d(str2, "onReceive", String.format("%02d", arrayOfObject2));
        ResponseWriterCPO localResponseWriterCPO = AtNfcmtest.this.writerCpo;
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Integer.valueOf(i);
        localResponseWriterCPO.write(2, "5D", String.format("%02d", arrayOfObject3), str1);
      }
      while (AtNfcmtest.this.mEndReceiver)
      {
        AtNfcmtest.this.stopReceiver();
        return;
        label208:
        ResponseWriter localResponseWriter = AtNfcmtest.this.writer;
        AtNfcmtest localAtNfcmtest = AtNfcmtest.this;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(i);
        localResponseWriter.write(localAtNfcmtest.responseString(String.format("%d", arrayOfObject1), str1));
      }
      AtNfcmtest.access$002(AtNfcmtest.this, true);
    }
  };
  
  public AtNfcmtest(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "NFCMTEST";
    this.CLASS_NAME = "AtNfcmtest";
    this.NUM_ARGS = 2;
    this.writer = paramResponseWriter;
  }
  
  public AtNfcmtest(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "NFCMTEST";
    this.CLASS_NAME = "AtNfcmtest";
    this.NUM_ARGS = 2;
    this.writerCpo = paramResponseWriterCPO;
  }
  
  private void launchNfcActivity()
  {
    Intent localIntent = new Intent();
    localIntent.setClassName("com.sec.android.app.nfctest", "com.sec.android.app.nfctest.NfcTest");
    localIntent.setFlags(268435456);
    this.context.startActivity(localIntent);
  }
  
  private void sendNfcTestCommand(final int paramInt1, final int paramInt2)
  {
    this.mNfcTestHandler = new Handler(this.context.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Intent localIntent = new Intent("com.sec.android.app.nfctest.NFC_TEST_INDICATION");
        localIntent.putExtra("CMDID", paramInt1);
        localIntent.putExtra("I_DATA", paramInt2);
        AtNfcmtest.this.context.sendBroadcast(localIntent);
      }
    };
    this.mNfcTestHandler.sendEmptyMessageDelayed(0, 500L);
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    String str1 = null;
    for (;;)
    {
      String str2;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          str2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return str2;
        }
        launchNfcActivity();
        if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          startReceiver();
          sendNfcTestCommand(0, 0);
          if (getCmdType() != 0) {
            break label235;
          }
          str1 = "WAIT";
          break label235;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          this.mEndReceiver = false;
          startReceiver();
          sendNfcTestCommand(0, 1);
          str1 = null;
          continue;
        }
      }
      finally {}
      tmp124_121[0] = "0";
      String[] tmp129_124 = tmp124_121;
      tmp129_124[1] = "2";
      if (checkArgu(paramArrayOfString, tmp129_124))
      {
        startReceiver();
        sendNfcTestCommand(0, 2);
        str1 = null;
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "3" }))
      {
        startReceiver();
        sendNfcTestCommand(0, 3);
        str1 = null;
      }
      else if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
      {
        startReceiver();
        sendNfcTestCommand(1, 0);
        str1 = null;
      }
      else
      {
        str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        continue;
        label235:
        str2 = str1;
      }
    }
  }
  
  public void startReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.sec.android.app.nfcntest.NFC_TEST_RESPONSE");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
  
  public void stopReceiver()
  {
    this.context.unregisterReceiver(this.mReceiver);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtNfcmtest
 * JD-Core Version:    0.7.1
 */