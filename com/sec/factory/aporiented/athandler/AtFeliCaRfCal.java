package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.sec.factory.aporiented.ResponseWriter;

public class AtFeliCaRfCal
  extends AtCommandHandler
{
  private static boolean isLaunchFeliCaTest = false;
  private Handler mFeliCaTestHandler;
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      if ("com.sec.android.app.felicatest.FELICA_TEST_RESPONSE".equals(str1))
      {
        String str7 = paramAnonymousIntent.getStringExtra("S_DATA");
        AtFeliCaRfCal.this.writer.write("+" + AtFeliCaRfCal.this.CMD_NAME + ":" + "1" + ",");
        AtFeliCaRfCal.this.writer.write(str7);
        AtFeliCaRfCal.this.writer.write("\r\n\r\nOK\r\n");
        AtFeliCaRfCal.this.stopReceiver();
      }
      do
      {
        return;
        if ("com.sec.android.app.felicatest.FELICA_TEST_SETNV_RESPONSE".equals(str1))
        {
          String str6 = paramAnonymousIntent.getStringExtra("S_DATA");
          AtFeliCaRfCal.this.writer.write("+" + AtFeliCaRfCal.this.CMD_NAME + ":" + "0" + ",");
          AtFeliCaRfCal.this.writer.write(str6);
          AtFeliCaRfCal.this.writer.write("\r\n\r\nOK\r\n");
          AtFeliCaRfCal.access$002(true);
          AtFeliCaRfCal.this.stopReceiver();
          return;
        }
        if ("com.sec.android.app.felicatest.FELICA_TEST_GETNV_RESPONSE".equals(str1))
        {
          String str5 = paramAnonymousIntent.getStringExtra("S_DATA");
          AtFeliCaRfCal.this.writer.write("+" + AtFeliCaRfCal.this.CMD_NAME + ":" + "0" + ",");
          AtFeliCaRfCal.this.writer.write(str5);
          AtFeliCaRfCal.this.writer.write("\r\n\r\nOK\r\n");
          AtFeliCaRfCal.access$002(false);
          AtFeliCaRfCal.this.stopReceiver();
          return;
        }
        if ("com.sec.android.app.felicatest.FELICA_TEST_PASS_RESPONSE".equals(str1))
        {
          String str4 = paramAnonymousIntent.getStringExtra("S_DATA");
          AtFeliCaRfCal.this.writer.write("+" + AtFeliCaRfCal.this.CMD_NAME + ":" + "2" + ",");
          AtFeliCaRfCal.this.writer.write(str4);
          AtFeliCaRfCal.this.writer.write("\r\n\r\nOK\r\n");
          AtFeliCaRfCal.this.stopReceiver();
          return;
        }
        if ("com.sec.android.app.felicatest.FELICA_TEST_FAIL_RESPONSE".equals(str1))
        {
          String str3 = paramAnonymousIntent.getStringExtra("S_DATA");
          AtFeliCaRfCal.this.writer.write("+" + AtFeliCaRfCal.this.CMD_NAME + ":" + "2" + ",");
          AtFeliCaRfCal.this.writer.write(str3);
          AtFeliCaRfCal.this.writer.write("\r\n\r\nOK\r\n");
          AtFeliCaRfCal.this.stopReceiver();
          return;
        }
      } while (!"com.sec.android.app.felicatest.FELICA_GETIDM_RESPONSE".equals(str1));
      String str2 = paramAnonymousIntent.getStringExtra("S_DATA");
      AtFeliCaRfCal.this.writer.write("+" + AtFeliCaRfCal.this.CMD_NAME + ":" + "3" + ",");
      AtFeliCaRfCal.this.writer.write(str2);
      AtFeliCaRfCal.this.writer.write("\r\n\r\nOK\r\n");
      AtFeliCaRfCal.this.stopReceiver();
    }
  };
  
  public AtFeliCaRfCal(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "FLCRFCAL";
    this.CLASS_NAME = "AtFeliCaRfCal";
    this.NUM_ARGS = 2;
    this.writer = paramResponseWriter;
  }
  
  private void launchFeliCaActivity()
  {
    Intent localIntent = new Intent();
    localIntent.setClassName("com.sec.android.app.felicatest", "com.sec.android.app.felicatest.FeliCaTest");
    localIntent.setFlags(268435456);
    localIntent.putExtra("FeliCa_Start_Parameter", 222);
    this.context.startActivity(localIntent);
  }
  
  private void launchFeliCaActivityIDM()
  {
    Intent localIntent = new Intent();
    localIntent.setClassName("com.sec.android.app.felicatest", "com.sec.android.app.felicatest.FeliCaTest");
    localIntent.setFlags(268435456);
    localIntent.putExtra("FeliCa_Start_Parameter", 333);
    this.context.startActivity(localIntent);
  }
  
  private void sendFeliCaTestCommand_cal(final int paramInt1, final int paramInt2)
  {
    this.mFeliCaTestHandler = new Handler(this.context.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Intent localIntent = new Intent("com.sec.android.app.felicatest.FELICA_TEST_INDICATION");
        localIntent.putExtra("CMDID", paramInt1);
        localIntent.putExtra("I_DATA", paramInt2);
        AtFeliCaRfCal.this.context.sendBroadcast(localIntent);
      }
    };
    this.mFeliCaTestHandler.sendEmptyMessageDelayed(0, 500L);
  }
  
  private void sendFeliCaTestCommand_end(final int paramInt1, final int paramInt2)
  {
    this.mFeliCaTestHandler = new Handler(this.context.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Intent localIntent = new Intent("com.sec.android.app.felicatest.FELICA_TEST_GETNV");
        localIntent.putExtra("CMDID", paramInt1);
        localIntent.putExtra("I_DATA", paramInt2);
        AtFeliCaRfCal.this.context.sendBroadcast(localIntent);
      }
    };
    this.mFeliCaTestHandler.sendEmptyMessageDelayed(0, 500L);
  }
  
  private void sendFeliCaTestCommand_fail(final int paramInt1, final int paramInt2)
  {
    this.mFeliCaTestHandler = new Handler(this.context.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Intent localIntent = new Intent("com.sec.android.app.felicatest.FELICA_TEST_FAIL");
        localIntent.putExtra("CMDID", paramInt1);
        localIntent.putExtra("I_DATA", paramInt2);
        AtFeliCaRfCal.this.context.sendBroadcast(localIntent);
      }
    };
    this.mFeliCaTestHandler.sendEmptyMessageDelayed(0, 500L);
  }
  
  private void sendFeliCaTestCommand_pass(final int paramInt1, final int paramInt2)
  {
    this.mFeliCaTestHandler = new Handler(this.context.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Intent localIntent = new Intent("com.sec.android.app.felicatest.FELICA_TEST_PASS");
        localIntent.putExtra("CMDID", paramInt1);
        localIntent.putExtra("I_DATA", paramInt2);
        AtFeliCaRfCal.this.context.sendBroadcast(localIntent);
      }
    };
    this.mFeliCaTestHandler.sendEmptyMessageDelayed(0, 500L);
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return str;
        }
        startReceiver();
        if (!"1".equals(paramArrayOfString[0])) {
          break label88;
        }
        if (isLaunchFeliCaTest)
        {
          sendFeliCaTestCommand_cal(1, Integer.valueOf(paramArrayOfString[1]).intValue());
          str = null;
          continue;
        }
        this.writer.write("+CME Error:NG");
      }
      finally {}
      this.writer.write("\r\n\r\nOK\r\n");
      String str = null;
      continue;
      label88:
      if ("0".equals(paramArrayOfString[0]))
      {
        if ("1".equals(paramArrayOfString[1]))
        {
          if (isLaunchFeliCaTest)
          {
            this.writer.write("+" + this.CMD_NAME + ":" + "0" + ",");
            this.writer.write("OK");
            this.writer.write("\r\n\r\nOK\r\n");
            str = null;
          }
          else
          {
            launchFeliCaActivity();
            str = null;
          }
        }
        else
        {
          boolean bool4 = "0".equals(paramArrayOfString[1]);
          str = null;
          if (bool4) {
            if (isLaunchFeliCaTest)
            {
              sendFeliCaTestCommand_end(3, 0);
              str = null;
            }
            else
            {
              this.writer.write("+" + this.CMD_NAME + ":" + "0" + ",");
              this.writer.write("OK");
              this.writer.write("\r\n\r\nOK\r\n");
              str = null;
            }
          }
        }
      }
      else if ("2".equals(paramArrayOfString[0]))
      {
        if ("1".equals(paramArrayOfString[1]))
        {
          if (isLaunchFeliCaTest)
          {
            sendFeliCaTestCommand_pass(0, 0);
            str = null;
          }
          else
          {
            this.writer.write("+CME Error:NG");
            this.writer.write("\r\n\r\nOK\r\n");
            str = null;
          }
        }
        else
        {
          boolean bool3 = "0".equals(paramArrayOfString[1]);
          str = null;
          if (bool3) {
            if (isLaunchFeliCaTest)
            {
              sendFeliCaTestCommand_fail(0, 0);
              str = null;
            }
            else
            {
              this.writer.write("+CME Error:NG");
              this.writer.write("\r\n\r\nOK\r\n");
              str = null;
            }
          }
        }
      }
      else
      {
        boolean bool1 = "3".equals(paramArrayOfString[0]);
        str = null;
        if (bool1)
        {
          boolean bool2 = "0".equals(paramArrayOfString[1]);
          str = null;
          if (bool2) {
            if (isLaunchFeliCaTest)
            {
              this.writer.write("+CME Error:NG");
              this.writer.write("\r\n\r\nOK\r\n");
              str = null;
            }
            else
            {
              launchFeliCaActivityIDM();
              str = null;
            }
          }
        }
      }
    }
  }
  
  public void startReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.sec.android.app.felicatest.FELICA_TEST_SETNV_RESPONSE");
    localIntentFilter.addAction("com.sec.android.app.felicatest.FELICA_TEST_RESPONSE");
    localIntentFilter.addAction("com.sec.android.app.felicatest.FELICA_TEST_GETNV_RESPONSE");
    localIntentFilter.addAction("com.sec.android.app.felicatest.FELICA_TEST_PASS_RESPONSE");
    localIntentFilter.addAction("com.sec.android.app.felicatest.FELICA_TEST_FAIL_RESPONSE");
    localIntentFilter.addAction("com.sec.android.app.felicatest.FELICA_GETIDM_RESPONSE");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
  
  public void stopReceiver()
  {
    this.context.unregisterReceiver(this.mReceiver);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFeliCaRfCal
 * JD-Core Version:    0.7.1
 */