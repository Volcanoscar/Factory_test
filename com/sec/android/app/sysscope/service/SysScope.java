package com.sec.android.app.sysscope.service;

import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

public final class SysScope
{
  private ServiceConnection mConnection = null;
  private final Context mContext;
  private SysScopeListener mLocalListener;
  private final ISysScopeListener mRemoteListener;
  private ISysScopeService mService;
  
  public SysScope(Context paramContext)
  {
    this.mContext = paramContext;
    this.mLocalListener = null;
    this.mRemoteListener = getDefaultListener();
    connectToService();
  }
  
  private boolean confirmServiceVersion()
  {
    Bundle localBundle = getInfo();
    if (localBundle == null)
    {
      if (this.mLocalListener != null) {
        this.mLocalListener.onError(19);
      }
      return false;
    }
    int i = localBundle.getInt("versionCode");
    if ((i < 0) && (this.mLocalListener != null)) {
      this.mLocalListener.onError(255);
    }
    if (4 >= i) {
      return true;
    }
    Log.e("SysScope", "Version missmatched, change the library with the valid version. The version of server is " + i);
    return false;
  }
  
  private void connectToService()
  {
    this.mService = ISysScopeService.Stub.asInterface(ServiceManager.getService("SYSSCOPE"));
    if (!verifyServiceIntegrity()) {
      this.mService = null;
    }
  }
  
  private ISysScopeListener getDefaultListener()
  {
    new ISysScopeListener.Stub()
    {
      public void onComplete(boolean paramAnonymousBoolean, String paramAnonymousString)
        throws RemoteException
      {
        if (SysScope.this.mLocalListener != null) {
          SysScope.this.mLocalListener.onComplete(paramAnonymousBoolean, paramAnonymousString);
        }
      }
      
      public void onError(int paramAnonymousInt)
      {
        if (SysScope.this.mLocalListener != null) {
          SysScope.this.mLocalListener.onError(paramAnonymousInt);
        }
      }
      
      public void onProgress(int paramAnonymousInt, String paramAnonymousString)
        throws RemoteException
      {
        if (SysScope.this.mLocalListener != null) {
          SysScope.this.mLocalListener.onProgress(paramAnonymousInt, paramAnonymousString);
        }
      }
      
      public void onReady()
      {
        if (SysScope.this.mLocalListener != null) {
          SysScope.this.mLocalListener.onReady();
        }
      }
      
      public void onStart(int paramAnonymousInt)
      {
        if (SysScope.this.mLocalListener != null) {
          SysScope.this.mLocalListener.onStart(paramAnonymousInt);
        }
      }
    };
  }
  
  private boolean verifyServiceIntegrity()
  {
    if (!verifySysScopeService())
    {
      if (this.mLocalListener != null) {
        this.mLocalListener.onError(21);
      }
      Log.e("SysScope", "SysScope service has invalid signature");
    }
    do
    {
      return false;
      if (confirmServiceVersion()) {
        break;
      }
    } while (this.mLocalListener == null);
    this.mLocalListener.onError(23);
    return false;
    return true;
  }
  
  private boolean verifySysScopeService()
  {
    if (!new SysScopeVerifier(this.mContext).verifySysScopeService())
    {
      if (this.mLocalListener != null) {
        this.mLocalListener.onError(21);
      }
      return false;
    }
    return true;
  }
  
  public Bundle getInfo()
  {
    Bundle localBundle = null;
    try
    {
      ISysScopeService localISysScopeService = this.mService;
      localBundle = null;
      if (localISysScopeService == null)
      {
        SysScopeListener localSysScopeListener = this.mLocalListener;
        localBundle = null;
        if (localSysScopeListener == null) {
          break label82;
        }
        this.mLocalListener.onError(19);
        break label82;
      }
      localBundle = this.mService.getInfo();
      if ((localBundle == null) && (this.mLocalListener != null)) {
        this.mLocalListener.onError(19);
      }
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        localRemoteException.printStackTrace();
      }
    }
    return localBundle;
    label82:
    return null;
  }
  
  public SysScopeResultInfo getLastScanResult()
  {
    if (this.mService == null)
    {
      if (this.mLocalListener != null) {
        this.mLocalListener.onError(19);
      }
      return null;
    }
    try
    {
      SysScopeResultInfo localSysScopeResultInfo = this.mService.getLastScanResult();
      return localSysScopeResultInfo;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return null;
  }
  
  public boolean isConnected()
  {
    return this.mService != null;
  }
  
  public static abstract interface SysScopeListener
  {
    public abstract void onComplete(boolean paramBoolean, String paramString);
    
    public abstract void onError(int paramInt);
    
    public abstract void onProgress(int paramInt, String paramString);
    
    public abstract void onReady();
    
    public abstract void onStart(int paramInt);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.sysscope.service.SysScope
 * JD-Core Version:    0.7.1
 */