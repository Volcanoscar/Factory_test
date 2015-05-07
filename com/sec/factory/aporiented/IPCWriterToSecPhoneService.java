package com.sec.factory.aporiented;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.sec.factory.support.FtUtil;
import java.util.Timer;
import java.util.TimerTask;

public class IPCWriterToSecPhoneService
{
  private Context mContext = null;
  private ServiceConnection mFactoryTestServiceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      FtUtil.log_i("IPCWriterToSecPhoneService", "onServiceConnected", "connected done");
      IPCWriterToSecPhoneService.access$002(IPCWriterToSecPhoneService.this, new Messenger(paramAnonymousIBinder));
      IPCWriterToSecPhoneService.this.sendRILBootMsg();
      TimerTask local1 = new TimerTask()
      {
        public void run()
        {
          IPCWriterToSecPhoneService.this.disConnectSecPhoneService();
        }
      };
      new Timer().schedule(local1, 2000L);
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      FtUtil.log_i("IPCWriterToSecPhoneService", "onServiceDisconnected", "disconnected done");
      IPCWriterToSecPhoneService.access$002(IPCWriterToSecPhoneService.this, null);
    }
  };
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      case 100: 
        FtUtil.log_d("IPCWriterToSecPhoneService", "handleMessage", "DFMS Confirm received");
        return;
      case 101: 
        FtUtil.log_d("IPCWriterToSecPhoneService", "handleMessage", "DFMS Event received");
        return;
      case 102: 
        FtUtil.log_d("IPCWriterToSecPhoneService", "handleMessage", "DFT Confirm received");
        return;
      case 103: 
        FtUtil.log_d("IPCWriterToSecPhoneService", "handleMessage", "DFT Event received");
        return;
      case 104: 
        FtUtil.log_d("IPCWriterToSecPhoneService", "handleMessage", "CP Sysdump done");
        return;
      }
      FtUtil.log_d("IPCWriterToSecPhoneService", "handleMessage", "Send BOOT COMPLETED done");
    }
  };
  public Messenger mResoponseMessenger = null;
  private Message mResponse = null;
  private Messenger mSecPhoneServiceMessenger = null;
  
  public IPCWriterToSecPhoneService()
  {
    FtUtil.log_d("IPCWriterToSecPhoneService", "ResponseWriter", "Create IPCWriterToSecPhoneService Without Messenger");
  }
  
  public IPCWriterToSecPhoneService(Context paramContext)
  {
    FtUtil.log_d("IPCWriterToSecPhoneService", "ResponseWriter", "Create IPCWriterToSecPhoneService");
    this.mResoponseMessenger = new Messenger(this.mHandler);
    if (paramContext != null) {
      this.mContext = paramContext;
    }
    for (;;)
    {
      connectToSecPhoneService();
      return;
      FtUtil.log_e("IPCWriterToSecPhoneService", "IPCWriterToSecPhoneService", "messenger is not available");
    }
  }
  
  public void connectToSecPhoneService()
  {
    FtUtil.log_i("IPCWriterToSecPhoneService", "connectToSecPhoneService", " ");
    Intent localIntent = new Intent();
    localIntent.setClassName("com.sec.phone", "com.sec.phone.SecPhoneService");
    this.mContext.bindService(localIntent, this.mFactoryTestServiceConnection, 1);
  }
  
  public void disConnectSecPhoneService()
  {
    FtUtil.log_i("IPCWriterToSecPhoneService", "disConnectSecPhoneService", " ");
    try
    {
      this.mContext.unbindService(this.mFactoryTestServiceConnection);
      return;
    }
    catch (Exception localException) {}
  }
  
  /* Error */
  public boolean sendRILBootMsg()
  {
    // Byte code:
    //   0: new 106	java/io/ByteArrayOutputStream
    //   3: dup
    //   4: invokespecial 107	java/io/ByteArrayOutputStream:<init>	()V
    //   7: astore_1
    //   8: new 109	java/io/DataOutputStream
    //   11: dup
    //   12: aload_1
    //   13: invokespecial 112	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   16: astore_2
    //   17: iconst_2
    //   18: newarray 慢⁤污潬慣楴湯
    //   21: iconst_0
    //   22: bipush 6
    //   24: bastore
    //   25: dup
    //   26: iconst_1
    //   27: iconst_5
    //   28: bastore
    //   29: astore_3
    //   30: iconst_2
    //   31: aload_3
    //   32: arraylength
    //   33: iadd
    //   34: i2s
    //   35: istore 4
    //   37: aload_2
    //   38: aload_3
    //   39: iconst_0
    //   40: aload_3
    //   41: arraylength
    //   42: invokevirtual 116	java/io/DataOutputStream:write	([BII)V
    //   45: aload_2
    //   46: iload 4
    //   48: invokevirtual 120	java/io/DataOutputStream:writeShort	(I)V
    //   51: aload_2
    //   52: invokevirtual 123	java/io/DataOutputStream:close	()V
    //   55: aload_1
    //   56: invokevirtual 124	java/io/ByteArrayOutputStream:close	()V
    //   59: aload_0
    //   60: aload_0
    //   61: getfield 39	com/sec/factory/aporiented/IPCWriterToSecPhoneService:mHandler	Landroid/os/Handler;
    //   64: sipush 1000
    //   67: invokevirtual 130	android/os/Handler:obtainMessage	(I)Landroid/os/Message;
    //   70: putfield 23	com/sec/factory/aporiented/IPCWriterToSecPhoneService:mResponse	Landroid/os/Message;
    //   73: aload_0
    //   74: aload_1
    //   75: invokevirtual 134	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   78: invokevirtual 137	com/sec/factory/aporiented/IPCWriterToSecPhoneService:write	([B)V
    //   81: iconst_1
    //   82: ireturn
    //   83: astore 9
    //   85: aload 9
    //   87: invokevirtual 140	java/io/IOException:printStackTrace	()V
    //   90: goto -31 -> 59
    //   93: astore 7
    //   95: aload_2
    //   96: invokevirtual 123	java/io/DataOutputStream:close	()V
    //   99: aload_1
    //   100: invokevirtual 124	java/io/ByteArrayOutputStream:close	()V
    //   103: iconst_0
    //   104: ireturn
    //   105: astore 8
    //   107: aload 8
    //   109: invokevirtual 140	java/io/IOException:printStackTrace	()V
    //   112: goto -9 -> 103
    //   115: astore 5
    //   117: aload_2
    //   118: invokevirtual 123	java/io/DataOutputStream:close	()V
    //   121: aload_1
    //   122: invokevirtual 124	java/io/ByteArrayOutputStream:close	()V
    //   125: aload 5
    //   127: athrow
    //   128: astore 6
    //   130: aload 6
    //   132: invokevirtual 140	java/io/IOException:printStackTrace	()V
    //   135: goto -10 -> 125
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	138	0	this	IPCWriterToSecPhoneService
    //   7	115	1	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    //   16	102	2	localDataOutputStream	java.io.DataOutputStream
    //   29	12	3	arrayOfByte	byte[]
    //   35	12	4	i	int
    //   115	11	5	localObject	Object
    //   128	3	6	localIOException1	java.io.IOException
    //   93	1	7	localIOException2	java.io.IOException
    //   105	3	8	localIOException3	java.io.IOException
    //   83	3	9	localIOException4	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   51	59	83	java/io/IOException
    //   37	51	93	java/io/IOException
    //   95	103	105	java/io/IOException
    //   37	51	115	finally
    //   117	125	128	java/io/IOException
  }
  
  public void write(byte[] paramArrayOfByte)
  {
    FtUtil.log_d("IPCWriterToSecPhoneService", "write", "Send Response Message to SecPhone");
    FtUtil.log_d("IPCWriterToSecPhoneService", "write", "Response " + paramArrayOfByte.toString());
    Bundle localBundle = new Bundle();
    localBundle.putByteArray("request", paramArrayOfByte);
    Message localMessage = this.mResponse;
    this.mResponse = null;
    localMessage.setData(localBundle);
    localMessage.replyTo = this.mResoponseMessenger;
    try
    {
      if (this.mSecPhoneServiceMessenger != null)
      {
        this.mSecPhoneServiceMessenger.send(localMessage);
        return;
      }
      FtUtil.log_e("IPCWriterToSecPhoneService", "write", "send failed!!!");
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.IPCWriterToSecPhoneService
 * JD-Core Version:    0.7.1
 */