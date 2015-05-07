package com.sec.factory.aporiented.athandler;

import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.DVFSLock;
import com.sec.factory.support.FtUtil;

public class AtMaxpower
  extends AtCommandHandler
{
  private PowerManager.DVFSLock mDVFSLock;
  private String mMaxPowerCount = "-1";
  private PowerManager mPowerManager;
  private int[] mSupportedFreq = null;
  
  public AtMaxpower(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "MAXPOWER";
    this.CLASS_NAME = "AtMaxpower";
    this.NUM_ARGS = 3;
    this.mPowerManager = ((PowerManager)paramContext.getSystemService("power"));
    this.mSupportedFreq = this.mPowerManager.getSupportedFrequency();
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      try
      {
        if (checkArgu(paramArrayOfString, new String[] { "0", "9", "9" }))
        {
          if (("8".equalsIgnoreCase(this.mMaxPowerCount)) && (this.mSupportedFreq != null))
          {
            PowerManager.DVFSLock localDVFSLock2 = this.mDVFSLock;
            if (localDVFSLock2 == null) {}
          }
          try
          {
            if (this.mDVFSLock.isHeld())
            {
              FtUtil.log_i(this.CLASS_NAME, "handleCommand()", "Release DVFS Lock");
              this.mDVFSLock.release();
            }
            return null;
          }
          catch (Exception localException3)
          {
            localException3.printStackTrace();
            continue;
          }
        }
        if (!paramArrayOfString[2].equalsIgnoreCase("1")) {
          continue;
        }
      }
      finally {}
      this.mMaxPowerCount = paramArrayOfString[1];
      if (paramArrayOfString[1].equalsIgnoreCase("0"))
      {
        FtUtil.log_i(this.CLASS_NAME, "handleCommand()", "DVFS Lock Routine");
        try
        {
          this.mDVFSLock = this.mPowerManager.newDVFSLock(2, this.mSupportedFreq[4], this.CLASS_NAME);
          if (this.mSupportedFreq != null)
          {
            PowerManager.DVFSLock localDVFSLock1 = this.mDVFSLock;
            if (localDVFSLock1 != null) {
              try
              {
                if (!this.mDVFSLock.isHeld())
                {
                  FtUtil.log_i(this.CLASS_NAME, "handleCommand()", "Acquire DVFS Lock");
                  this.mDVFSLock.acquire();
                }
              }
              catch (Exception localException2)
              {
                localException2.printStackTrace();
              }
            }
          }
        }
        catch (Exception localException1)
        {
          for (;;)
          {
            localException1.printStackTrace();
          }
          this.mDVFSLock.release();
        }
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtMaxpower
 * JD-Core Version:    0.7.1
 */