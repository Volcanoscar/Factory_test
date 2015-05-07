package com.sec.factory.support;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface INVService
  extends IInterface
{
  public abstract void setTestNV(int paramInt1, int paramInt2)
    throws RemoteException;
  
  public abstract void turnoffSupportService()
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements INVService
  {
    public Stub()
    {
      attachInterface(this, "com.sec.factory.support.INVService");
    }
    
    public IBinder asBinder()
    {
      return this;
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.sec.factory.support.INVService");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.sec.factory.support.INVService");
        setTestNV(paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      }
      paramParcel1.enforceInterface("com.sec.factory.support.INVService");
      turnoffSupportService();
      paramParcel2.writeNoException();
      return true;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.support.INVService
 * JD-Core Version:    0.7.1
 */