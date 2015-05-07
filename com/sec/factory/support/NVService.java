package com.sec.factory.support;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.sec.factory.aporiented.FtClient;
import com.sec.factory.app.factorytest.FactoryTestPhone;
import com.sec.factory.app.factorytest.FactoryTestSupportService;

public class NVService
  extends Service
{
  INVService.Stub mBinder = new INVService.Stub()
  {
    public void setTestNV(int paramAnonymousInt1, int paramAnonymousInt2)
      throws RemoteException
    {
      Intent localIntent = new Intent(NVService.this, FtClient.class);
      NVService.this.startService(localIntent);
      if (!FtUtil.isFactoryAppAPO())
      {
        NVAccessor.setNV((byte)paramAnonymousInt1, (byte)paramAnonymousInt2);
        FactoryTestPhone localFactoryTestPhone = new FactoryTestPhone(NVService.this);
        localFactoryTestPhone.bindSecPhoneService();
        localFactoryTestPhone.updateNVItem((byte)paramAnonymousInt1, (byte)paramAnonymousInt2);
        localFactoryTestPhone.unbindSecPhoneService();
        return;
      }
      NVAccessor.setNV((byte)paramAnonymousInt1, (byte)paramAnonymousInt2);
      FtUtil.log_d("NVService", "setTestNV", "itemid=" + paramAnonymousInt1 + ", result=" + paramAnonymousInt2);
    }
    
    public void turnoffSupportService()
      throws RemoteException
    {
      Intent localIntent = new Intent(NVService.this, FactoryTestSupportService.class);
      NVService.this.stopService(localIntent);
    }
  };
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
  
  public void onCreate()
  {
    super.onCreate();
  }
  
  public void onDestroy()
  {
    super.onDestroy();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.support.NVService
 * JD-Core Version:    0.7.1
 */