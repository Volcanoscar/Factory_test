package com.sec.factory.aporiented;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.sec.factory.support.FtUtil;

public class WakeUpService
  extends BroadcastReceiver
{
  private PowerManager.WakeLock mWakeUp = null;
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str = paramIntent.getAction();
    boolean bool = paramIntent.getExtras().getBoolean("isAcquireWakelock");
    FtUtil.log_d("WakeUpService", "onReceive", "onReceive action=" + str);
    if ((str.equals("com.sec.factory.aporiented.athandler.AtSensorHb.WakeUp")) && (this.mWakeUp == null))
    {
      PowerManager localPowerManager = (PowerManager)paramContext.getSystemService("power");
      this.mWakeUp = localPowerManager.newWakeLock(805306394, "WakeUpService");
      if ((!localPowerManager.isScreenOn()) && (!this.mWakeUp.isHeld()))
      {
        this.mWakeUp.acquire();
        FtUtil.log_i("WakeUpService", "ACTION_WAKE_UP", "Wake up by sensorHub test cmd");
        if (!bool)
        {
          this.mWakeUp.release();
          FtUtil.log_i("WakeUpService", "ACTION_WAKE_UP", "FULL WAKELOCK OFF");
        }
        this.mWakeUp = null;
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.WakeUpService
 * JD-Core Version:    0.7.1
 */