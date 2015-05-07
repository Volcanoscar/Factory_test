package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.sec.android.app.sysscope.service.SysScope;
import com.sec.android.app.sysscope.service.SysScopeResultInfo;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.support.FtUtil;

public class AtSysscope
  extends AtCommandHandler
{
  private int isSysScopeStatus;
  private SysScope mSysScope;
  private BroadcastReceiver mSysScopeReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if ((paramAnonymousIntent.getAction().compareTo("com.sec.intent.action.SYSSCOPESTATUS") == 0) && (paramAnonymousIntent.getStringExtra("status").compareTo("SysScope scanning finished") == 0)) {
        AtSysscope.this.changeSysScopeStatus();
      }
    }
  };
  
  public AtSysscope(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "SYSSCOPE";
    this.CLASS_NAME = "AtSysscope";
    this.NUM_ARGS = 2;
    this.writer = paramResponseWriter;
  }
  
  public AtSysscope(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "SYSSCOPE";
    this.CLASS_NAME = "AtSysscope";
    this.NUM_ARGS = 2;
    this.writerCpo = paramResponseWriterCPO;
  }
  
  private String changeSysScopeStatus()
  {
    (SystemClock.uptimeMillis() / 1000L);
    long l = SystemClock.elapsedRealtime() / 1000L;
    if (l == 0L) {
      l = 1L;
    }
    if ((!this.mSysScope.isConnected()) && (l > 120L))
    {
      FtUtil.log_d(this.CLASS_NAME, "changeSysScopeStatus", "elapsedRealtime()=" + l);
      return "MODIFIED";
    }
    try
    {
      this.isSysScopeStatus = this.mSysScope.getLastScanResult().getResult();
      if (this.isSysScopeStatus == 2)
      {
        str = "MODIFIED";
        FtUtil.log_d(this.CLASS_NAME, "changeSysScopeStatus", "result=" + str);
        return str;
      }
    }
    catch (Exception localException)
    {
      for (;;)
      {
        String str;
        this.isSysScopeStatus = -1;
        continue;
        if (this.isSysScopeStatus == -1) {
          str = "SCANNING";
        } else {
          str = "NORMAL";
        }
      }
    }
  }
  
  private void setSysScopeStatus()
  {
    if (this.mSysScope == null) {
      this.mSysScope = new SysScope(this.context);
    }
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
        if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          setSysScopeStatus();
          str = responseString(paramArrayOfString[0], changeSysScopeStatus());
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
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtSysscope
 * JD-Core Version:    0.7.1
 */