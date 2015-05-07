package com.sec.factory.cporiented;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.sec.factory.support.FtUtil;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseWriterCPO
{
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      case 100: 
        FtUtil.log_d("ResponseWriterCPO", "handleMessage", "DFMS Confirm received");
        return;
      case 101: 
        FtUtil.log_d("ResponseWriterCPO", "handleMessage", "DFMS Event received");
        return;
      case 102: 
        FtUtil.log_d("ResponseWriterCPO", "handleMessage", "DFT Confirm received");
        return;
      case 103: 
        FtUtil.log_d("ResponseWriterCPO", "handleMessage", "DFT Event received");
        return;
      case 104: 
        FtUtil.log_d("ResponseWriterCPO", "handleMessage", "CP Sysdump done");
        ResponseWriterCPO.this.write(2, "5A", "00", "OK");
        return;
      case 1000: 
        FtUtil.log_d("ResponseWriterCPO", "handleMessage", "Send BOOT COMPLETED done");
        return;
      }
      FtUtil.log_d("ResponseWriterCPO", "handleMessage", "Send POWER CMD done");
    }
  };
  public Messenger mMessenger = null;
  public Messenger mResoponseMessenger = null;
  private Message mResponse;
  
  public ResponseWriterCPO()
  {
    FtUtil.log_d("ResponseWriterCPO", "ResponseWriter", "Create ResponseWriterCPO Without Messenger");
  }
  
  public ResponseWriterCPO(Messenger paramMessenger)
  {
    FtUtil.log_d("ResponseWriterCPO", "ResponseWriter", "Create ResponseWriterCPO");
    this.mResoponseMessenger = new Messenger(this.mHandler);
    if (paramMessenger != null)
    {
      this.mMessenger = paramMessenger;
      return;
    }
    FtUtil.log_e("ResponseWriterCPO", "ResponseWriterCPO", "messenger is not available");
  }
  
  private byte[] setResultEndModeData(String paramString1, String paramString2, String paramString3)
  {
    int i = 2;
    int j = 1;
    boolean bool = "F7".equals(paramString1.substring(0, 2));
    int k = 0;
    if (bool)
    {
      FtUtil.log_d("ResponseWriterCPO", "setResultEndModeData", "Set Event type to DFT");
      j = 5;
      i = 1;
      paramString1 = paramString1.substring(2, 4);
      k = 1;
    }
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    int m = -1;
    int n = 16 * FtUtil.charToNum(paramString1.charAt(0)) + FtUtil.charToNum(paramString1.charAt(1));
    if (paramString2 != null)
    {
      FtUtil.log_d("ResponseWriterCPO", "setResultEndModeData", "attr.charAt(0) : " + paramString2.charAt(0));
      FtUtil.log_d("ResponseWriterCPO", "setResultEndModeData", "attr.charAt(1) : " + paramString2.charAt(1));
      m = 16 * FtUtil.charToNum(paramString2.charAt(0)) + FtUtil.charToNum(paramString2.charAt(1));
    }
    FtUtil.log_d("ResponseWriterCPO", "setResultEndModeData", "nAttr : " + paramString2);
    if (paramString3 != null)
    {
      i += paramString3.length();
      FtUtil.log_d("ResponseWriterCPO", "setResultEndModeData", "nLength : " + i + "data.length(): " + paramString3.length());
    }
    byte b1 = (byte)i;
    byte b2 = (byte)(i >> 8);
    int i1 = i + 6;
    try
    {
      localDataOutputStream.writeByte(18);
      localDataOutputStream.writeByte(j);
      localDataOutputStream.writeShort(i1);
      localDataOutputStream.writeByte(b1);
      localDataOutputStream.writeByte(b2);
      localDataOutputStream.writeByte(n);
      if ((m != -1) && (k == 0)) {
        localDataOutputStream.writeByte(m);
      }
      if (paramString3 != null)
      {
        localDataOutputStream.writeBytes(paramString3);
        FtUtil.log_d("ResponseWriterCPO", "setResultEndModeData", "mData : " + paramString3);
      }
      StringBuilder localStringBuilder = new StringBuilder().append("send : ");
      Object[] arrayOfObject = new Object[8];
      arrayOfObject[0] = Integer.valueOf(18);
      arrayOfObject[1] = Integer.valueOf(j);
      arrayOfObject[2] = Integer.valueOf(i1);
      arrayOfObject[3] = Byte.valueOf(b1);
      arrayOfObject[4] = Byte.valueOf(b2);
      arrayOfObject[5] = Integer.valueOf(n);
      arrayOfObject[6] = Integer.valueOf(m);
      arrayOfObject[7] = paramString3;
      FtUtil.log_d("ResponseWriterCPO", "setResultEndModeData", String.format("%x %x %x %x %x %x %x %s", arrayOfObject));
      this.mResponse = this.mHandler.obtainMessage(101);
      return localByteArrayOutputStream.toByteArray();
    }
    catch (IOException localIOException) {}
    return null;
  }
  
  private byte[] setResultEndModeData_DFT(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    FtUtil.log_d("ResponseWriterCPO", "setResultEndModeData", "Event Response type: DFT");
    int i;
    if (paramBoolean) {
      i = 3;
    }
    for (;;)
    {
      String str = paramString1.substring(2, 4);
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      int j = 16 * FtUtil.charToNum(str.charAt(0)) + FtUtil.charToNum(str.charAt(1));
      if (paramString2 != null)
      {
        FtUtil.log_d("ResponseWriterCPO", "setResultEndModeData", "attr.charAt(0) : " + paramString2.charAt(0));
        FtUtil.log_d("ResponseWriterCPO", "setResultEndModeData", "attr.charAt(1) : " + paramString2.charAt(1));
        (16 * FtUtil.charToNum(paramString2.charAt(0)) + FtUtil.charToNum(paramString2.charAt(1)));
      }
      FtUtil.log_d("ResponseWriterCPO", "setResultEndModeData", "nAttr : " + paramString2);
      if (paramString3 != null)
      {
        if (!paramBoolean) {
          i += paramString3.length();
        }
        FtUtil.log_d("ResponseWriterCPO", "setResultEndModeData", "nLength : " + i + " data.length(): " + paramString3.length());
      }
      byte b1 = (byte)i;
      byte b2 = (byte)(i >> 8);
      int k = i + 6;
      try
      {
        localDataOutputStream.writeByte(18);
        localDataOutputStream.writeByte(5);
        localDataOutputStream.writeShort(k);
        localDataOutputStream.writeByte(b1);
        localDataOutputStream.writeByte(b2);
        localDataOutputStream.writeByte(j);
        if (paramString3 != null)
        {
          if (!paramBoolean) {
            break label472;
          }
          localDataOutputStream.writeShort(Integer.valueOf(paramString3).intValue());
        }
        for (;;)
        {
          FtUtil.log_d("ResponseWriterCPO", "setResultEndModeData", "mData : " + paramString3);
          StringBuilder localStringBuilder = new StringBuilder().append("send : ");
          Object[] arrayOfObject = new Object[7];
          arrayOfObject[0] = Integer.valueOf(18);
          arrayOfObject[1] = Integer.valueOf(5);
          arrayOfObject[2] = Integer.valueOf(k);
          arrayOfObject[3] = Byte.valueOf(b1);
          arrayOfObject[4] = Byte.valueOf(b2);
          arrayOfObject[5] = Integer.valueOf(j);
          arrayOfObject[6] = paramString3;
          FtUtil.log_d("ResponseWriterCPO", "setResultEndModeData", String.format("%x %x %x %x %x %x %s", arrayOfObject));
          this.mResponse = this.mHandler.obtainMessage(103);
          return localByteArrayOutputStream.toByteArray();
          i = 1;
          break;
          label472:
          localDataOutputStream.writeBytes(paramString3);
        }
        return null;
      }
      catch (IOException localIOException) {}
    }
  }
  
  private byte[] setResultEndModeData_cpdump()
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    for (;;)
    {
      try
      {
        localDataOutputStream.writeByte(7);
        localDataOutputStream.writeByte(18);
        localDataOutputStream.writeShort(5);
        localDataOutputStream.writeByte(0);
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        if (localDataOutputStream == null) {
          continue;
        }
        try
        {
          localDataOutputStream.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        continue;
      }
      finally
      {
        if (localDataOutputStream == null) {
          break label112;
        }
      }
      try
      {
        localDataOutputStream.close();
        this.mResponse = this.mHandler.obtainMessage(104);
        return localByteArrayOutputStream.toByteArray();
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
      }
    }
    try
    {
      localDataOutputStream.close();
      label112:
      throw ((Throwable)localObject);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        localException1.printStackTrace();
      }
    }
  }
  
  /* Error */
  public boolean sendRILBootMsg()
  {
    // Byte code:
    //   0: new 72	java/io/ByteArrayOutputStream
    //   3: dup
    //   4: invokespecial 73	java/io/ByteArrayOutputStream:<init>	()V
    //   7: astore_1
    //   8: new 75	java/io/DataOutputStream
    //   11: dup
    //   12: aload_1
    //   13: invokespecial 78	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
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
    //   42: invokevirtual 191	java/io/DataOutputStream:write	([BII)V
    //   45: aload_2
    //   46: iload 4
    //   48: invokevirtual 124	java/io/DataOutputStream:writeShort	(I)V
    //   51: aload_2
    //   52: invokevirtual 181	java/io/DataOutputStream:close	()V
    //   55: aload_1
    //   56: invokevirtual 192	java/io/ByteArrayOutputStream:close	()V
    //   59: aload_0
    //   60: aload_0
    //   61: getfield 26	com/sec/factory/cporiented/ResponseWriterCPO:mHandler	Landroid/os/Handler;
    //   64: sipush 1000
    //   67: invokevirtual 155	android/os/Handler:obtainMessage	(I)Landroid/os/Message;
    //   70: putfield 157	com/sec/factory/cporiented/ResponseWriterCPO:mResponse	Landroid/os/Message;
    //   73: aload_0
    //   74: aload_1
    //   75: invokevirtual 161	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   78: invokevirtual 195	com/sec/factory/cporiented/ResponseWriterCPO:write	([B)V
    //   81: iconst_1
    //   82: ireturn
    //   83: astore 9
    //   85: aload 9
    //   87: invokevirtual 185	java/io/IOException:printStackTrace	()V
    //   90: goto -31 -> 59
    //   93: astore 7
    //   95: aload_2
    //   96: invokevirtual 181	java/io/DataOutputStream:close	()V
    //   99: aload_1
    //   100: invokevirtual 192	java/io/ByteArrayOutputStream:close	()V
    //   103: iconst_0
    //   104: ireturn
    //   105: astore 8
    //   107: aload 8
    //   109: invokevirtual 185	java/io/IOException:printStackTrace	()V
    //   112: goto -9 -> 103
    //   115: astore 5
    //   117: aload_2
    //   118: invokevirtual 181	java/io/DataOutputStream:close	()V
    //   121: aload_1
    //   122: invokevirtual 192	java/io/ByteArrayOutputStream:close	()V
    //   125: aload 5
    //   127: athrow
    //   128: astore 6
    //   130: aload 6
    //   132: invokevirtual 185	java/io/IOException:printStackTrace	()V
    //   135: goto -10 -> 125
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	138	0	this	ResponseWriterCPO
    //   7	115	1	localByteArrayOutputStream	ByteArrayOutputStream
    //   16	102	2	localDataOutputStream	DataOutputStream
    //   29	12	3	arrayOfByte	byte[]
    //   35	12	4	i	int
    //   115	11	5	localObject	Object
    //   128	3	6	localIOException1	IOException
    //   93	1	7	localIOException2	IOException
    //   105	3	8	localIOException3	IOException
    //   83	3	9	localIOException4	IOException
    // Exception table:
    //   from	to	target	type
    //   51	59	83	java/io/IOException
    //   37	51	93	java/io/IOException
    //   95	103	105	java/io/IOException
    //   37	51	115	finally
    //   117	125	128	java/io/IOException
  }
  
  public boolean sendSleepCmd()
  {
    localByteArrayOutputStream = new ByteArrayOutputStream();
    localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    try
    {
      localDataOutputStream.writeByte(5);
      localDataOutputStream.writeByte(5);
      localDataOutputStream.writeShort(4);
      try
      {
        localDataOutputStream.close();
        localByteArrayOutputStream.close();
        this.mResponse = this.mHandler.obtainMessage(200);
        write(localByteArrayOutputStream.toByteArray());
        return true;
      }
      catch (IOException localIOException4)
      {
        for (;;)
        {
          FtUtil.log_e("ResponseWriterCPO", "sendSleepCmd", "IO Exception on close(): " + localIOException4.toString());
        }
      }
      try
      {
        localDataOutputStream.close();
        localByteArrayOutputStream.close();
        throw ((Throwable)localObject);
      }
      catch (IOException localIOException1)
      {
        for (;;)
        {
          FtUtil.log_e("ResponseWriterCPO", "sendSleepCmd", "IO Exception on close(): " + localIOException1.toString());
        }
      }
    }
    catch (IOException localIOException2)
    {
      localIOException2 = localIOException2;
      FtUtil.log_e("ResponseWriterCPO", "sendSleepCmd", "IO Exception : " + localIOException2.toString());
      try
      {
        localDataOutputStream.close();
        localByteArrayOutputStream.close();
        return false;
      }
      catch (IOException localIOException3)
      {
        FtUtil.log_e("ResponseWriterCPO", "sendSleepCmd", "IO Exception on close(): " + localIOException3.toString());
        return false;
      }
    }
    finally {}
  }
  
  /* Error */
  public byte[] setAckEndModeData(int paramInt)
  {
    // Byte code:
    //   0: new 72	java/io/ByteArrayOutputStream
    //   3: dup
    //   4: invokespecial 73	java/io/ByteArrayOutputStream:<init>	()V
    //   7: astore_2
    //   8: new 75	java/io/DataOutputStream
    //   11: dup
    //   12: aload_2
    //   13: invokespecial 78	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   16: astore_3
    //   17: aload_3
    //   18: bipush 18
    //   20: invokevirtual 121	java/io/DataOutputStream:writeByte	(I)V
    //   23: aload_3
    //   24: iconst_2
    //   25: invokevirtual 121	java/io/DataOutputStream:writeByte	(I)V
    //   28: aload_3
    //   29: iconst_5
    //   30: invokevirtual 124	java/io/DataOutputStream:writeShort	(I)V
    //   33: iload_1
    //   34: ifne +34 -> 68
    //   37: aload_3
    //   38: iconst_1
    //   39: invokevirtual 121	java/io/DataOutputStream:writeByte	(I)V
    //   42: aload_3
    //   43: ifnull +7 -> 50
    //   46: aload_3
    //   47: invokevirtual 181	java/io/DataOutputStream:close	()V
    //   50: aload_0
    //   51: aload_0
    //   52: getfield 26	com/sec/factory/cporiented/ResponseWriterCPO:mHandler	Landroid/os/Handler;
    //   55: bipush 100
    //   57: invokevirtual 155	android/os/Handler:obtainMessage	(I)Landroid/os/Message;
    //   60: putfield 157	com/sec/factory/cporiented/ResponseWriterCPO:mResponse	Landroid/os/Message;
    //   63: aload_2
    //   64: invokevirtual 161	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   67: areturn
    //   68: aload_3
    //   69: sipush 255
    //   72: invokevirtual 121	java/io/DataOutputStream:writeByte	(I)V
    //   75: goto -33 -> 42
    //   78: astore 6
    //   80: aload 6
    //   82: invokevirtual 185	java/io/IOException:printStackTrace	()V
    //   85: aload_3
    //   86: ifnull -36 -> 50
    //   89: aload_3
    //   90: invokevirtual 181	java/io/DataOutputStream:close	()V
    //   93: goto -43 -> 50
    //   96: astore 7
    //   98: aload 7
    //   100: invokevirtual 184	java/lang/Exception:printStackTrace	()V
    //   103: goto -53 -> 50
    //   106: astore 8
    //   108: aload 8
    //   110: invokevirtual 184	java/lang/Exception:printStackTrace	()V
    //   113: goto -63 -> 50
    //   116: astore 4
    //   118: aload_3
    //   119: ifnull +7 -> 126
    //   122: aload_3
    //   123: invokevirtual 181	java/io/DataOutputStream:close	()V
    //   126: aload 4
    //   128: athrow
    //   129: astore 5
    //   131: aload 5
    //   133: invokevirtual 184	java/lang/Exception:printStackTrace	()V
    //   136: goto -10 -> 126
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	139	0	this	ResponseWriterCPO
    //   0	139	1	paramInt	int
    //   7	57	2	localByteArrayOutputStream	ByteArrayOutputStream
    //   16	107	3	localDataOutputStream	DataOutputStream
    //   116	11	4	localObject	Object
    //   129	3	5	localException1	Exception
    //   78	3	6	localIOException	IOException
    //   96	3	7	localException2	Exception
    //   106	3	8	localException3	Exception
    // Exception table:
    //   from	to	target	type
    //   17	33	78	java/io/IOException
    //   37	42	78	java/io/IOException
    //   68	75	78	java/io/IOException
    //   89	93	96	java/lang/Exception
    //   46	50	106	java/lang/Exception
    //   17	33	116	finally
    //   37	42	116	finally
    //   68	75	116	finally
    //   80	85	116	finally
    //   122	126	129	java/lang/Exception
  }
  
  public byte[] setAckEndModeData_DFT(int paramInt)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    try
    {
      localDataOutputStream.writeByte(18);
      localDataOutputStream.writeByte(6);
      localDataOutputStream.writeShort(5);
      if (paramInt == 0) {
        localDataOutputStream.writeByte(1);
      }
      for (;;)
      {
        this.mResponse = this.mHandler.obtainMessage(102);
        return localByteArrayOutputStream.toByteArray();
        localDataOutputStream.writeByte(255);
      }
      return null;
    }
    catch (IOException localIOException) {}
  }
  
  public void write(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    byte[] arrayOfByte;
    switch (paramInt)
    {
    default: 
      FtUtil.log_e("ResponseWriterCPO", "write", "Cannot accept result!!!");
      arrayOfByte = setResultEndModeData(paramString1, paramString2, "\r\n+CME Error:NA\r\n\r\nOK\r\n");
    }
    for (;;)
    {
      write(arrayOfByte);
      FtUtil.log_d("ResponseWriterCPO", "write", "bData: " + arrayOfByte);
      return;
      arrayOfByte = setAckEndModeData(paramInt);
      continue;
      arrayOfByte = setAckEndModeData_DFT(paramInt);
      continue;
      arrayOfByte = setResultEndModeData(paramString1, "fe", paramString3);
      continue;
      arrayOfByte = setResultEndModeData(paramString1, "ff", paramString3);
      continue;
      FtUtil.log_d("ResponseWriterCPO", "write", "bData: " + paramString3);
      arrayOfByte = setResultEndModeData(paramString1, paramString2, paramString3);
      continue;
      arrayOfByte = setResultEndModeData_DFT(paramString1, paramString2, paramString3, true);
      continue;
      arrayOfByte = setResultEndModeData_DFT(paramString1, paramString2, paramString3, false);
      continue;
      arrayOfByte = setResultEndModeData_cpdump();
    }
  }
  
  public void write(byte[] paramArrayOfByte)
  {
    FtUtil.log_d("ResponseWriterCPO", "write", "Send Response Message to SecPhone");
    FtUtil.log_d("ResponseWriterCPO", "write", "Response " + paramArrayOfByte.toString());
    Bundle localBundle = new Bundle();
    localBundle.putByteArray("request", paramArrayOfByte);
    Message localMessage = this.mResponse;
    this.mResponse = null;
    if (localMessage == null) {
      localMessage = this.mHandler.obtainMessage(101);
    }
    localMessage.setData(localBundle);
    localMessage.replyTo = this.mResoponseMessenger;
    try
    {
      if (this.mMessenger != null)
      {
        this.mMessenger.send(localMessage);
        return;
      }
      FtUtil.log_e("ResponseWriterCPO", "write", "send failed!!!");
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.cporiented.ResponseWriterCPO
 * JD-Core Version:    0.7.1
 */