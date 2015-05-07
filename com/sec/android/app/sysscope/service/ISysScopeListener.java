package com.sec.android.app.sysscope.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ISysScopeListener
  extends IInterface
{
  public abstract void onComplete(boolean paramBoolean, String paramString)
    throws RemoteException;
  
  public abstract void onError(int paramInt)
    throws RemoteException;
  
  public abstract void onProgress(int paramInt, String paramString)
    throws RemoteException;
  
  public abstract void onReady()
    throws RemoteException;
  
  public abstract void onStart(int paramInt)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements ISysScopeListener
  {
    public Stub()
    {
      attachInterface(this, "com.sec.android.app.sysscope.service.ISysScopeListener");
    }
    
    public static ISysScopeListener asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.sec.android.app.sysscope.service.ISysScopeListener");
      if ((localIInterface != null) && ((localIInterface instanceof ISysScopeListener))) {
        return (ISysScopeListener)localIInterface;
      }
      return new Proxy(paramIBinder);
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
        paramParcel2.writeString("com.sec.android.app.sysscope.service.ISysScopeListener");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.sec.android.app.sysscope.service.ISysScopeListener");
        onReady();
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.sec.android.app.sysscope.service.ISysScopeListener");
        onStart(paramParcel1.readInt());
        return true;
      case 3: 
        paramParcel1.enforceInterface("com.sec.android.app.sysscope.service.ISysScopeListener");
        onProgress(paramParcel1.readInt(), paramParcel1.readString());
        return true;
      case 4: 
        paramParcel1.enforceInterface("com.sec.android.app.sysscope.service.ISysScopeListener");
        if (paramParcel1.readInt() != 0) {}
        for (boolean bool = true;; bool = false)
        {
          onComplete(bool, paramParcel1.readString());
          return true;
        }
      }
      paramParcel1.enforceInterface("com.sec.android.app.sysscope.service.ISysScopeListener");
      onError(paramParcel1.readInt());
      return true;
    }
    
    private static class Proxy
      implements ISysScopeListener
    {
      private IBinder mRemote;
      
      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }
      
      public IBinder asBinder()
      {
        return this.mRemote;
      }
      
      /* Error */
      public void onComplete(boolean paramBoolean, String paramString)
        throws RemoteException
      {
        // Byte code:
        //   0: iconst_1
        //   1: istore_3
        //   2: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   5: astore 4
        //   7: aload 4
        //   9: ldc 29
        //   11: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: iload_1
        //   15: ifeq +36 -> 51
        //   18: aload 4
        //   20: iload_3
        //   21: invokevirtual 37	android/os/Parcel:writeInt	(I)V
        //   24: aload 4
        //   26: aload_2
        //   27: invokevirtual 40	android/os/Parcel:writeString	(Ljava/lang/String;)V
        //   30: aload_0
        //   31: getfield 15	com/sec/android/app/sysscope/service/ISysScopeListener$Stub$Proxy:mRemote	Landroid/os/IBinder;
        //   34: iconst_4
        //   35: aload 4
        //   37: aconst_null
        //   38: iconst_1
        //   39: invokeinterface 46 5 0
        //   44: pop
        //   45: aload 4
        //   47: invokevirtual 49	android/os/Parcel:recycle	()V
        //   50: return
        //   51: iconst_0
        //   52: istore_3
        //   53: goto -35 -> 18
        //   56: astore 5
        //   58: aload 4
        //   60: invokevirtual 49	android/os/Parcel:recycle	()V
        //   63: aload 5
        //   65: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	66	0	this	Proxy
        //   0	66	1	paramBoolean	boolean
        //   0	66	2	paramString	String
        //   1	52	3	i	int
        //   5	54	4	localParcel	Parcel
        //   56	8	5	localObject	Object
        // Exception table:
        //   from	to	target	type
        //   7	14	56	finally
        //   18	45	56	finally
      }
      
      public void onError(int paramInt)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.sec.android.app.sysscope.service.ISysScopeListener");
          localParcel.writeInt(paramInt);
          this.mRemote.transact(5, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void onProgress(int paramInt, String paramString)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.sec.android.app.sysscope.service.ISysScopeListener");
          localParcel.writeInt(paramInt);
          localParcel.writeString(paramString);
          this.mRemote.transact(3, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void onReady()
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.sec.android.app.sysscope.service.ISysScopeListener");
          this.mRemote.transact(1, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
      
      public void onStart(int paramInt)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.sec.android.app.sysscope.service.ISysScopeListener");
          localParcel.writeInt(paramInt);
          this.mRemote.transact(2, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.sysscope.service.ISysScopeListener
 * JD-Core Version:    0.7.1
 */