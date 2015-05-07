package com.sec.android.app.sysscope.service;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ISysScopeService
  extends IInterface
{
  public abstract Bundle getInfo()
    throws RemoteException;
  
  public abstract SysScopeResultInfo getLastScanResult()
    throws RemoteException;
  
  public abstract int getScanPeriod()
    throws RemoteException;
  
  public abstract void registerListener(ISysScopeListener paramISysScopeListener)
    throws RemoteException;
  
  public abstract int run(int paramInt, ISysScopeListener paramISysScopeListener)
    throws RemoteException;
  
  public abstract int scope()
    throws RemoteException;
  
  public abstract int setScanPeriod(int paramInt)
    throws RemoteException;
  
  public abstract void unregisterListener(ISysScopeListener paramISysScopeListener)
    throws RemoteException;
  
  public static abstract class Stub
    extends Binder
    implements ISysScopeService
  {
    public Stub()
    {
      attachInterface(this, "com.sec.android.app.sysscope.service.ISysScopeService");
    }
    
    public static ISysScopeService asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null) {
        return null;
      }
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.sec.android.app.sysscope.service.ISysScopeService");
      if ((localIInterface != null) && ((localIInterface instanceof ISysScopeService))) {
        return (ISysScopeService)localIInterface;
      }
      return new Proxy(paramIBinder);
    }
    
    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default: 
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902: 
        paramParcel2.writeString("com.sec.android.app.sysscope.service.ISysScopeService");
        return true;
      case 1: 
        paramParcel1.enforceInterface("com.sec.android.app.sysscope.service.ISysScopeService");
        int m = run(paramParcel1.readInt(), ISysScopeListener.Stub.asInterface(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        paramParcel2.writeInt(m);
        return true;
      case 2: 
        paramParcel1.enforceInterface("com.sec.android.app.sysscope.service.ISysScopeService");
        int k = scope();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(k);
        return true;
      case 3: 
        paramParcel1.enforceInterface("com.sec.android.app.sysscope.service.ISysScopeService");
        Bundle localBundle = getInfo();
        paramParcel2.writeNoException();
        if (localBundle != null)
        {
          paramParcel2.writeInt(1);
          localBundle.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 4: 
        paramParcel1.enforceInterface("com.sec.android.app.sysscope.service.ISysScopeService");
        registerListener(ISysScopeListener.Stub.asInterface(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 5: 
        paramParcel1.enforceInterface("com.sec.android.app.sysscope.service.ISysScopeService");
        unregisterListener(ISysScopeListener.Stub.asInterface(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 6: 
        paramParcel1.enforceInterface("com.sec.android.app.sysscope.service.ISysScopeService");
        int j = setScanPeriod(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(j);
        return true;
      case 7: 
        paramParcel1.enforceInterface("com.sec.android.app.sysscope.service.ISysScopeService");
        int i = getScanPeriod();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i);
        return true;
      }
      paramParcel1.enforceInterface("com.sec.android.app.sysscope.service.ISysScopeService");
      SysScopeResultInfo localSysScopeResultInfo = getLastScanResult();
      paramParcel2.writeNoException();
      if (localSysScopeResultInfo != null)
      {
        paramParcel2.writeInt(1);
        localSysScopeResultInfo.writeToParcel(paramParcel2, 1);
        return true;
      }
      paramParcel2.writeInt(0);
      return true;
    }
    
    private static class Proxy
      implements ISysScopeService
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
      public Bundle getInfo()
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_1
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_2
        //   8: aload_1
        //   9: ldc 29
        //   11: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_0
        //   15: getfield 15	com/sec/android/app/sysscope/service/ISysScopeService$Stub$Proxy:mRemote	Landroid/os/IBinder;
        //   18: iconst_3
        //   19: aload_1
        //   20: aload_2
        //   21: iconst_0
        //   22: invokeinterface 39 5 0
        //   27: pop
        //   28: aload_2
        //   29: invokevirtual 42	android/os/Parcel:readException	()V
        //   32: aload_2
        //   33: invokevirtual 46	android/os/Parcel:readInt	()I
        //   36: ifeq +28 -> 64
        //   39: getstatic 52	android/os/Bundle:CREATOR	Landroid/os/Parcelable$Creator;
        //   42: aload_2
        //   43: invokeinterface 58 2 0
        //   48: checkcast 48	android/os/Bundle
        //   51: astore 5
        //   53: aload_2
        //   54: invokevirtual 61	android/os/Parcel:recycle	()V
        //   57: aload_1
        //   58: invokevirtual 61	android/os/Parcel:recycle	()V
        //   61: aload 5
        //   63: areturn
        //   64: aconst_null
        //   65: astore 5
        //   67: goto -14 -> 53
        //   70: astore_3
        //   71: aload_2
        //   72: invokevirtual 61	android/os/Parcel:recycle	()V
        //   75: aload_1
        //   76: invokevirtual 61	android/os/Parcel:recycle	()V
        //   79: aload_3
        //   80: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	81	0	this	Proxy
        //   3	73	1	localParcel1	Parcel
        //   7	65	2	localParcel2	Parcel
        //   70	10	3	localObject	Object
        //   51	15	5	localBundle	Bundle
        // Exception table:
        //   from	to	target	type
        //   8	53	70	finally
      }
      
      /* Error */
      public SysScopeResultInfo getLastScanResult()
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_1
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_2
        //   8: aload_1
        //   9: ldc 29
        //   11: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_0
        //   15: getfield 15	com/sec/android/app/sysscope/service/ISysScopeService$Stub$Proxy:mRemote	Landroid/os/IBinder;
        //   18: bipush 8
        //   20: aload_1
        //   21: aload_2
        //   22: iconst_0
        //   23: invokeinterface 39 5 0
        //   28: pop
        //   29: aload_2
        //   30: invokevirtual 42	android/os/Parcel:readException	()V
        //   33: aload_2
        //   34: invokevirtual 46	android/os/Parcel:readInt	()I
        //   37: ifeq +28 -> 65
        //   40: getstatic 66	com/sec/android/app/sysscope/service/SysScopeResultInfo:CREATOR	Landroid/os/Parcelable$Creator;
        //   43: aload_2
        //   44: invokeinterface 58 2 0
        //   49: checkcast 65	com/sec/android/app/sysscope/service/SysScopeResultInfo
        //   52: astore 5
        //   54: aload_2
        //   55: invokevirtual 61	android/os/Parcel:recycle	()V
        //   58: aload_1
        //   59: invokevirtual 61	android/os/Parcel:recycle	()V
        //   62: aload 5
        //   64: areturn
        //   65: aconst_null
        //   66: astore 5
        //   68: goto -14 -> 54
        //   71: astore_3
        //   72: aload_2
        //   73: invokevirtual 61	android/os/Parcel:recycle	()V
        //   76: aload_1
        //   77: invokevirtual 61	android/os/Parcel:recycle	()V
        //   80: aload_3
        //   81: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	82	0	this	Proxy
        //   3	74	1	localParcel1	Parcel
        //   7	66	2	localParcel2	Parcel
        //   71	10	3	localObject	Object
        //   52	15	5	localSysScopeResultInfo	SysScopeResultInfo
        // Exception table:
        //   from	to	target	type
        //   8	54	71	finally
      }
      
      public int getScanPeriod()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.sec.android.app.sysscope.service.ISysScopeService");
          this.mRemote.transact(7, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      /* Error */
      public void registerListener(ISysScopeListener paramISysScopeListener)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 29
        //   11: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +44 -> 59
        //   18: aload_1
        //   19: invokeinterface 73 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 76	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/sec/android/app/sysscope/service/ISysScopeService$Stub$Proxy:mRemote	Landroid/os/IBinder;
        //   36: iconst_4
        //   37: aload_2
        //   38: aload_3
        //   39: iconst_0
        //   40: invokeinterface 39 5 0
        //   45: pop
        //   46: aload_3
        //   47: invokevirtual 42	android/os/Parcel:readException	()V
        //   50: aload_3
        //   51: invokevirtual 61	android/os/Parcel:recycle	()V
        //   54: aload_2
        //   55: invokevirtual 61	android/os/Parcel:recycle	()V
        //   58: return
        //   59: aconst_null
        //   60: astore 5
        //   62: goto -36 -> 26
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 61	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 61	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	Proxy
        //   0	78	1	paramISysScopeListener	ISysScopeListener
        //   3	69	2	localParcel1	Parcel
        //   7	61	3	localParcel2	Parcel
        //   65	11	4	localObject	Object
        //   24	37	5	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   8	14	65	finally
        //   18	26	65	finally
        //   26	50	65	finally
      }
      
      /* Error */
      public int run(int paramInt, ISysScopeListener paramISysScopeListener)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_3
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore 4
        //   9: aload_3
        //   10: ldc 29
        //   12: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   15: aload_3
        //   16: iload_1
        //   17: invokevirtual 82	android/os/Parcel:writeInt	(I)V
        //   20: aload_2
        //   21: ifnull +56 -> 77
        //   24: aload_2
        //   25: invokeinterface 73 1 0
        //   30: astore 6
        //   32: aload_3
        //   33: aload 6
        //   35: invokevirtual 76	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   38: aload_0
        //   39: getfield 15	com/sec/android/app/sysscope/service/ISysScopeService$Stub$Proxy:mRemote	Landroid/os/IBinder;
        //   42: iconst_1
        //   43: aload_3
        //   44: aload 4
        //   46: iconst_0
        //   47: invokeinterface 39 5 0
        //   52: pop
        //   53: aload 4
        //   55: invokevirtual 42	android/os/Parcel:readException	()V
        //   58: aload 4
        //   60: invokevirtual 46	android/os/Parcel:readInt	()I
        //   63: istore 8
        //   65: aload 4
        //   67: invokevirtual 61	android/os/Parcel:recycle	()V
        //   70: aload_3
        //   71: invokevirtual 61	android/os/Parcel:recycle	()V
        //   74: iload 8
        //   76: ireturn
        //   77: aconst_null
        //   78: astore 6
        //   80: goto -48 -> 32
        //   83: astore 5
        //   85: aload 4
        //   87: invokevirtual 61	android/os/Parcel:recycle	()V
        //   90: aload_3
        //   91: invokevirtual 61	android/os/Parcel:recycle	()V
        //   94: aload 5
        //   96: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	97	0	this	Proxy
        //   0	97	1	paramInt	int
        //   0	97	2	paramISysScopeListener	ISysScopeListener
        //   3	88	3	localParcel1	Parcel
        //   7	79	4	localParcel2	Parcel
        //   83	12	5	localObject	Object
        //   30	49	6	localIBinder	IBinder
        //   63	12	8	i	int
        // Exception table:
        //   from	to	target	type
        //   9	20	83	finally
        //   24	32	83	finally
        //   32	65	83	finally
      }
      
      public int scope()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.sec.android.app.sysscope.service.ISysScopeService");
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      public int setScanPeriod(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.sec.android.app.sysscope.service.ISysScopeService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(6, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
      
      /* Error */
      public void unregisterListener(ISysScopeListener paramISysScopeListener)
        throws RemoteException
      {
        // Byte code:
        //   0: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore_2
        //   4: invokestatic 27	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   7: astore_3
        //   8: aload_2
        //   9: ldc 29
        //   11: invokevirtual 33	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
        //   14: aload_1
        //   15: ifnull +44 -> 59
        //   18: aload_1
        //   19: invokeinterface 73 1 0
        //   24: astore 5
        //   26: aload_2
        //   27: aload 5
        //   29: invokevirtual 76	android/os/Parcel:writeStrongBinder	(Landroid/os/IBinder;)V
        //   32: aload_0
        //   33: getfield 15	com/sec/android/app/sysscope/service/ISysScopeService$Stub$Proxy:mRemote	Landroid/os/IBinder;
        //   36: iconst_5
        //   37: aload_2
        //   38: aload_3
        //   39: iconst_0
        //   40: invokeinterface 39 5 0
        //   45: pop
        //   46: aload_3
        //   47: invokevirtual 42	android/os/Parcel:readException	()V
        //   50: aload_3
        //   51: invokevirtual 61	android/os/Parcel:recycle	()V
        //   54: aload_2
        //   55: invokevirtual 61	android/os/Parcel:recycle	()V
        //   58: return
        //   59: aconst_null
        //   60: astore 5
        //   62: goto -36 -> 26
        //   65: astore 4
        //   67: aload_3
        //   68: invokevirtual 61	android/os/Parcel:recycle	()V
        //   71: aload_2
        //   72: invokevirtual 61	android/os/Parcel:recycle	()V
        //   75: aload 4
        //   77: athrow
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	78	0	this	Proxy
        //   0	78	1	paramISysScopeListener	ISysScopeListener
        //   3	69	2	localParcel1	Parcel
        //   7	61	3	localParcel2	Parcel
        //   65	11	4	localObject	Object
        //   24	37	5	localIBinder	IBinder
        // Exception table:
        //   from	to	target	type
        //   8	14	65	finally
        //   18	26	65	finally
        //   26	50	65	finally
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.sysscope.service.ISysScopeService
 * JD-Core Version:    0.7.1
 */