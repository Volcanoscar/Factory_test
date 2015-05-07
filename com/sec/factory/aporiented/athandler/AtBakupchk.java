package com.sec.factory.aporiented.athandler;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Properties;

public class AtBakupchk
  extends AtCommandHandler
{
  private int mNResult;
  
  public AtBakupchk(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "BAKUPCHK";
    this.CLASS_NAME = "AtBakupchk";
    this.NUM_ARGS = 2;
  }
  
  private void doSystemCall(int paramInt)
  {
    new Handler(this.context.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        PowerManager localPowerManager = (PowerManager)AtBakupchk.this.context.getSystemService("power");
        switch (paramAnonymousMessage.what)
        {
        default: 
          return;
        case 0: 
          FtUtil.log_d(AtBakupchk.this.CLASS_NAME, "nvBackupHandler", "I_NVBACKUP ");
          localPowerManager.reboot("nvbackup");
          FtUtil.log_d(AtBakupchk.this.CLASS_NAME, "nvBackupHandler", "NVBACKUP done, mNResult=" + AtBakupchk.this.mNResult);
          return;
        case 2: 
          FtUtil.log_d(AtBakupchk.this.CLASS_NAME, "nvBackupHandler", "I_NVBACKUP ");
          localPowerManager.reboot("nverase");
          FtUtil.log_d(AtBakupchk.this.CLASS_NAME, "nvBackupHandler", "NVERASE done, mNResult=" + AtBakupchk.this.mNResult);
          return;
        }
        FtUtil.log_d(AtBakupchk.this.CLASS_NAME, "nvBackupHandler", "I_NVRESTORE ");
        localPowerManager.reboot("nvrestore");
        FtUtil.log_d(AtBakupchk.this.CLASS_NAME, "nvBackupHandler", "NVRESTORE done, mNResult=" + AtBakupchk.this.mNResult);
      }
    }.sendEmptyMessage(paramInt);
  }
  
  private boolean setProperties(String paramString)
  {
    FtUtil.log_d(this.CLASS_NAME, "setProperties", "cmd = " + paramString);
    Support.Properties.set("CPNV_BACKUP_CMD", paramString);
    return true;
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
        if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          setProperties("nvrecovery");
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          doSystemCall(2);
          str = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2" }))
        {
          doSystemCall(1);
          str = responseOK(paramArrayOfString[0]);
        }
        else
        {
          boolean bool1 = checkArgu(paramArrayOfString, new String[] { "1", "0" });
          str = null;
          if (!bool1)
          {
            boolean bool2 = checkArgu(paramArrayOfString, new String[] { "1", "1" });
            str = null;
            if (!bool2)
            {
              boolean bool3 = checkArgu(paramArrayOfString, new String[] { "1", "2" });
              str = null;
              if (!bool3) {
                str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
              }
            }
          }
        }
      }
      finally {}
      Object localObject2 = str;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtBakupchk
 * JD-Core Version:    0.7.1
 */