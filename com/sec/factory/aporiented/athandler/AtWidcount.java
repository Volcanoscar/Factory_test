package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.support.FtUtil;

public class AtWidcount
  extends AtCommandHandler
{
  private final String ACTION_HOME_WIDCOUNT_REQUEST = "android.intent.action.REQUEST_HOME_COUNT";
  private final String ACTION_HOME_WIDCOUNT_RESPONSE = "android.intent.action.RESPONSE_HOME_COUNT";
  private final String ACTION_WIDCOUNT_REQUEST = "android.intent.action.REQUEST_WIDGET_COUNT";
  private final String ACTION_WIDCOUNT_RESPONSE = "android.intent.action.RESPONSE_WIDGET_COUNT";
  private int homeWidgetCount = -1;
  private boolean mIsReceived_HOME_COUNT = false;
  private boolean mIsReceived_WID_COUNT = false;
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      String str2;
      if ("android.intent.action.RESPONSE_HOME_COUNT".equals(str1))
      {
        AtWidcount.access$002(AtWidcount.this, true);
        AtWidcount.access$102(AtWidcount.this, paramAnonymousIntent.getIntExtra("HOME_ALL_COUNT", -99));
        FtUtil.log_d(AtWidcount.this.CLASS_NAME, "onReceive", "homeWidgetCount: " + AtWidcount.this.homeWidgetCount);
        if ((AtWidcount.this.mIsReceived_WID_COUNT) && (AtWidcount.this.mIsReceived_HOME_COUNT))
        {
          str2 = AtWidcount.this.menuWidgetCount + "," + AtWidcount.this.homeWidgetCount;
          FtUtil.log_d(AtWidcount.this.CLASS_NAME, "onReceive", "result: " + str2);
          if (AtWidcount.this.getCmdType() != 0) {
            break label281;
          }
          AtWidcount.this.writerCpo.write(2, "81", "01", str2);
        }
      }
      for (;;)
      {
        AtWidcount.this.stopReceiver(AtWidcount.this.mReceiver);
        return;
        if (!"android.intent.action.RESPONSE_WIDGET_COUNT".equals(str1)) {
          break;
        }
        AtWidcount.access$202(AtWidcount.this, true);
        AtWidcount.access$302(AtWidcount.this, paramAnonymousIntent.getIntExtra("WIDGET_ALL_COUNT", -99));
        FtUtil.log_d(AtWidcount.this.CLASS_NAME, "onReceive", "menuWidgetCount: " + AtWidcount.this.menuWidgetCount);
        break;
        label281:
        AtWidcount.this.writer.write(AtWidcount.this.responseString("1", str2));
      }
    }
  };
  private int menuWidgetCount = -1;
  
  public AtWidcount(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "WIDCOUNT";
    this.CLASS_NAME = "AtWidcount";
    this.NUM_ARGS = 3;
    this.writer = paramResponseWriter;
  }
  
  public AtWidcount(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "WIDCOUNT";
    this.CLASS_NAME = "AtWidcount";
    this.NUM_ARGS = 3;
    this.writerCpo = paramResponseWriterCPO;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    String str1 = null;
    for (;;)
    {
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          str2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return str2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "1", "0", "0" }))
        {
          startReceiver();
          this.context.sendBroadcast(new Intent("android.intent.action.REQUEST_WIDGET_COUNT"));
          this.context.sendBroadcast(new Intent("android.intent.action.REQUEST_HOME_COUNT"));
          if (getCmdType() == 0) {
            str1 = "WAIT";
          }
        }
        else
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          continue;
        }
        String str2 = str1;
      }
      finally {}
    }
  }
  
  public void startReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.RESPONSE_WIDGET_COUNT");
    localIntentFilter.addAction("android.intent.action.RESPONSE_HOME_COUNT");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtWidcount
 * JD-Core Version:    0.7.1
 */