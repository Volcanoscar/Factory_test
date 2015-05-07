package com.sec.factory.app.factorytest;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import com.sec.factory.support.FtUtil;

public class FactoryTestSlaveService
  extends Service
{
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      FtUtil.log_d("FactoryTestSlaveService", "mBroadcastReceiver.onReceive", "action=" + str);
    }
  };
  FactoryTestPhone mFactoryPhone = null;
  
  private void ChangeDualmode()
  {
    FtUtil.log_i("FactoryTestSlaveService", "ChangeDualmode", null);
    if (this.mFactoryPhone == null)
    {
      this.mFactoryPhone = new FactoryTestPhone(this);
      this.mFactoryPhone.bindSecPhoneService();
    }
    this.mFactoryPhone.changeDualModeState();
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    FtUtil.log_i("FactoryTestSlaveService", "onCreate", null);
    new IntentFilter();
  }
  
  public void onDestroy()
  {
    FtUtil.log_i("FactoryTestSlaveService", "onDestroy", null);
    if (this.mFactoryPhone != null)
    {
      this.mFactoryPhone.unbindSecPhoneService();
      this.mFactoryPhone = null;
    }
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    FtUtil.log_i("FactoryTestSlaveService", "onStartCommand", null);
    if (paramIntent == null) {}
    while (!paramIntent.getBooleanExtra("DUALMODE", false)) {
      return 0;
    }
    ChangeDualmode();
    return 0;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestSlaveService
 * JD-Core Version:    0.7.1
 */