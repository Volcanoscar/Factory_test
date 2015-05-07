package com.sec.factory.aporiented;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.os.IBinder;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.XMLDataStorage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FtClient
  extends Service
{
  LocalSocket clientSocket_recv;
  LocalSocket clientSocket_send;
  private ConnectionThread connectionThread;
  private boolean isMarvell = "Marvell".equalsIgnoreCase(Support.Feature.getString("CHIPSET_MANUFACTURE"));
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    /* Error */
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_2
      //   3: invokevirtual 25	android/content/Intent:getAction	()Ljava/lang/String;
      //   6: astore 4
      //   8: ldc 27
      //   10: ldc 29
      //   12: new 31	java/lang/StringBuilder
      //   15: dup
      //   16: invokespecial 32	java/lang/StringBuilder:<init>	()V
      //   19: ldc 34
      //   21: invokevirtual 38	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   24: aload 4
      //   26: invokevirtual 38	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   29: invokevirtual 41	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   32: invokestatic 47	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   35: aload 4
      //   37: ldc 49
      //   39: invokevirtual 55	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   42: ifeq +25 -> 67
      //   45: aload_0
      //   46: getfield 12	com/sec/factory/aporiented/FtClient$1:this$0	Lcom/sec/factory/aporiented/FtClient;
      //   49: invokestatic 59	com/sec/factory/aporiented/FtClient:access$000	(Lcom/sec/factory/aporiented/FtClient;)Landroid/os/Handler;
      //   52: new 61	com/sec/factory/aporiented/FtClient$1$1
      //   55: dup
      //   56: aload_0
      //   57: invokespecial 64	com/sec/factory/aporiented/FtClient$1$1:<init>	(Lcom/sec/factory/aporiented/FtClient$1;)V
      //   60: invokevirtual 70	android/os/Handler:post	(Ljava/lang/Runnable;)Z
      //   63: pop
      //   64: aload_0
      //   65: monitorexit
      //   66: return
      //   67: aload 4
      //   69: ldc 72
      //   71: invokevirtual 55	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   74: ifeq +131 -> 205
      //   77: aload_1
      //   78: invokestatic 78	com/sec/factory/modules/ModuleCommon:instance	(Landroid/content/Context;)Lcom/sec/factory/modules/ModuleCommon;
      //   81: invokevirtual 82	com/sec/factory/modules/ModuleCommon:isConnectionModeNone	()Z
      //   84: ifne +99 -> 183
      //   87: aload_0
      //   88: getfield 12	com/sec/factory/aporiented/FtClient$1:this$0	Lcom/sec/factory/aporiented/FtClient;
      //   91: invokestatic 86	com/sec/factory/aporiented/FtClient:access$100	(Lcom/sec/factory/aporiented/FtClient;)Z
      //   94: ifne +89 -> 183
      //   97: iconst_5
      //   98: istore 8
      //   100: aload_0
      //   101: getfield 12	com/sec/factory/aporiented/FtClient$1:this$0	Lcom/sec/factory/aporiented/FtClient;
      //   104: getfield 90	com/sec/factory/aporiented/FtClient:clientSocket_recv	Landroid/net/LocalSocket;
      //   107: invokevirtual 95	android/net/LocalSocket:isConnected	()Z
      //   110: ifeq +20 -> 130
      //   113: aload_0
      //   114: getfield 12	com/sec/factory/aporiented/FtClient$1:this$0	Lcom/sec/factory/aporiented/FtClient;
      //   117: getfield 98	com/sec/factory/aporiented/FtClient:clientSocket_send	Landroid/net/LocalSocket;
      //   120: invokevirtual 95	android/net/LocalSocket:isConnected	()Z
      //   123: istore 10
      //   125: iload 10
      //   127: ifne +44 -> 171
      //   130: ldc2_w 99
      //   133: invokestatic 106	java/lang/Thread:sleep	(J)V
      //   136: iinc 8 255
      //   139: iload 8
      //   141: ifge -41 -> 100
      //   144: ldc 27
      //   146: ldc 29
      //   148: ldc 108
      //   150: invokestatic 111	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   153: goto -89 -> 64
      //   156: astore_3
      //   157: aload_0
      //   158: monitorexit
      //   159: aload_3
      //   160: athrow
      //   161: astore 9
      //   163: aload 9
      //   165: invokevirtual 114	java/lang/InterruptedException:printStackTrace	()V
      //   168: goto -32 -> 136
      //   171: new 116	com/sec/factory/aporiented/IPCWriterToSecPhoneService
      //   174: dup
      //   175: aload_1
      //   176: invokespecial 119	com/sec/factory/aporiented/IPCWriterToSecPhoneService:<init>	(Landroid/content/Context;)V
      //   179: pop
      //   180: goto -116 -> 64
      //   183: aload_0
      //   184: getfield 12	com/sec/factory/aporiented/FtClient$1:this$0	Lcom/sec/factory/aporiented/FtClient;
      //   187: invokestatic 59	com/sec/factory/aporiented/FtClient:access$000	(Lcom/sec/factory/aporiented/FtClient;)Landroid/os/Handler;
      //   190: new 121	com/sec/factory/aporiented/FtClient$1$2
      //   193: dup
      //   194: aload_0
      //   195: invokespecial 122	com/sec/factory/aporiented/FtClient$1$2:<init>	(Lcom/sec/factory/aporiented/FtClient$1;)V
      //   198: invokevirtual 70	android/os/Handler:post	(Ljava/lang/Runnable;)Z
      //   201: pop
      //   202: goto -138 -> 64
      //   205: aload 4
      //   207: ldc 124
      //   209: invokevirtual 55	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   212: ifeq -148 -> 64
      //   215: aload_2
      //   216: ldc 126
      //   218: invokevirtual 130	android/content/Intent:getStringExtra	(Ljava/lang/String;)Ljava/lang/String;
      //   221: astore 5
      //   223: aload 5
      //   225: ifnonnull +15 -> 240
      //   228: ldc 27
      //   230: ldc 132
      //   232: ldc 134
      //   234: invokestatic 47	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   237: goto -173 -> 64
      //   240: aload_0
      //   241: getfield 12	com/sec/factory/aporiented/FtClient$1:this$0	Lcom/sec/factory/aporiented/FtClient;
      //   244: invokestatic 59	com/sec/factory/aporiented/FtClient:access$000	(Lcom/sec/factory/aporiented/FtClient;)Landroid/os/Handler;
      //   247: new 136	com/sec/factory/aporiented/FtClient$1$3
      //   250: dup
      //   251: aload_0
      //   252: aload 5
      //   254: invokespecial 139	com/sec/factory/aporiented/FtClient$1$3:<init>	(Lcom/sec/factory/aporiented/FtClient$1;Ljava/lang/String;)V
      //   257: invokevirtual 70	android/os/Handler:post	(Ljava/lang/Runnable;)Z
      //   260: pop
      //   261: goto -197 -> 64
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	264	0	this	1
      //   0	264	1	paramAnonymousContext	Context
      //   0	264	2	paramAnonymousIntent	Intent
      //   156	4	3	localObject	Object
      //   6	200	4	str1	String
      //   221	32	5	str2	String
      //   98	42	8	i	int
      //   161	3	9	localInterruptedException	InterruptedException
      //   123	3	10	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   2	64	156	finally
      //   67	97	156	finally
      //   100	125	156	finally
      //   130	136	156	finally
      //   144	153	156	finally
      //   163	168	156	finally
      //   171	180	156	finally
      //   183	202	156	finally
      //   205	223	156	finally
      //   228	237	156	finally
      //   240	261	156	finally
      //   130	136	161	java/lang/InterruptedException
    }
  };
  private Context mContext;
  private Handler mHandler = new Handler();
  private boolean mIsFirstCmd = false;
  public AtParser parser;
  public ResponseWriter writer;
  
  private void setPendingIntent()
  {
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(this.mContext, 0, new Intent("factory"), 0);
    Notification.Builder localBuilder = new Notification.Builder(this.mContext);
    NotificationManager localNotificationManager = (NotificationManager)this.mContext.getSystemService("notification");
    localBuilder.setSmallIcon(2130837522).setWhen(1000000L + System.currentTimeMillis()).setContentTitle("FT Service").setTicker("on Factory command").setContentText("on Factory mode").setContentIntent(localPendingIntent);
    Notification localNotification = localBuilder.getNotification();
    localNotificationManager.notify(5555, localNotification);
    startForeground(5555, localNotification);
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    this.mContext = getApplicationContext();
    FtUtil.log_d("FtClient", "onCreate", "Create FtClient service");
    if (!XMLDataStorage.instance().parseXML(getApplicationContext())) {
      FtUtil.log_i("FtClient", "onCreate", "FtClient => XML data parsing was failed.");
    }
    this.clientSocket_recv = new LocalSocket();
    this.clientSocket_send = new LocalSocket();
    this.connectionThread = new ConnectionThread(this);
    this.connectionThread.start();
    FtUtil.log_d("FtClient", "onCreate", "registBroadCastReceiver");
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.sec.factory.entry.SEND_RESET_COMPLETED");
    localIntentFilter.addAction("com.sec.factory.entry.SEND_BOOT_COMPLETED");
    localIntentFilter.addAction("com.sec.factory.SEND_TO_RIL");
    registerReceiver(this.mBroadcastReceiver, localIntentFilter);
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    FtUtil.log_d("FtClient", "onDestroy", "Destroy FtClient service");
    this.connectionThread.kill();
    unregisterReceiver(this.mBroadcastReceiver);
    if (this.clientSocket_recv != null) {}
    try
    {
      this.clientSocket_recv.close();
      FtUtil.log_i("FtClient", "onDestroy", "Close client socket(receiver)");
      if (this.clientSocket_send == null) {}
    }
    catch (IOException localIOException2)
    {
      for (;;)
      {
        try
        {
          this.clientSocket_send.close();
          FtUtil.log_i("FtClient", "onDestroy", "Close client socket(sender)");
          return;
        }
        catch (IOException localIOException1)
        {
          FtUtil.log_e(localIOException1);
        }
        localIOException2 = localIOException2;
        FtUtil.log_e(localIOException2);
      }
    }
  }
  
  private class ConnectionThread
    extends Thread
  {
    private boolean active;
    private final Context context;
    private BufferedReader in;
    
    public ConnectionThread(Context paramContext)
    {
      this.context = paramContext;
      this.active = true;
    }
    
    public void kill()
    {
      this.active = false;
      FtClient.this.connectionThread.interrupt();
      FtUtil.log_d("FtClient", "run", "Kill ConnectionThread");
    }
    
    public void run()
    {
      FtUtil.log_d("FtClient", "run", "ConnectionThread start");
      boolean bool = false;
      int i = 0;
      LocalSocketAddress localLocalSocketAddress1 = new LocalSocketAddress("FactoryClientRecv");
      LocalSocketAddress localLocalSocketAddress2 = new LocalSocketAddress("FactoryClientSend");
      for (;;)
      {
        if (!bool)
        {
          try
          {
            if (!FtClient.this.clientSocket_recv.isConnected())
            {
              FtClient.this.clientSocket_recv.connect(localLocalSocketAddress1);
              FtUtil.log_i("FtClient", "run", "Connect client socket(receiver)");
            }
            if (!FtClient.this.clientSocket_send.isConnected())
            {
              FtClient.this.clientSocket_send.connect(localLocalSocketAddress2);
              FtUtil.log_i("FtClient", "run", "Connect client socket(sender)");
            }
          }
          catch (IOException localIOException1)
          {
            try
            {
              for (;;)
              {
                this.in = new BufferedReader(new InputStreamReader(FtClient.this.clientSocket_recv.getInputStream()));
                for (;;)
                {
                  if (!this.active) {
                    break label486;
                  }
                  try
                  {
                    String str = this.in.readLine();
                    if (str == null) {
                      break;
                    }
                    if ((FtClient.this.parser.process(str, FtClient.this.writer) == true) && (!FtClient.this.mIsFirstCmd))
                    {
                      FtClient.this.setPendingIntent();
                      ModuleCommon.instance(FtClient.this.mContext).enableFtClient();
                      FtClient.access$202(FtClient.this, true);
                      FtUtil.log_i("FtClient", "handleMessage", "FirstCMDReceived: Noti ON");
                    }
                  }
                  catch (IOException localIOException3)
                  {
                    FtUtil.log_e(localIOException3);
                    this.active = false;
                    bool = false;
                  }
                }
                localIOException1 = localIOException1;
                FtUtil.log_e(localIOException1);
                continue;
                try
                {
                  Thread.sleep(3000L);
                  bool = false;
                }
                catch (InterruptedException localInterruptedException)
                {
                  FtUtil.log_e(localInterruptedException);
                  bool = false;
                }
              }
            }
            catch (IOException localIOException2)
            {
              for (;;)
              {
                FtUtil.log_e(localIOException2);
                this.active = false;
                bool = false;
                i = 0;
                continue;
                i++;
                FtUtil.log_i("FtClient", "run", "Garbage data incoming...");
                if (i > 10)
                {
                  FtUtil.log_i("FtClient", "run", "Stop FtClient(Garbage data count=" + i + ")");
                  this.active = false;
                }
              }
              FtUtil.log_d("FtClient", "killed thread", "connected value : " + bool);
            }
          }
          if ((FtClient.this.clientSocket_recv.isConnected()) && (FtClient.this.clientSocket_send.isConnected()))
          {
            bool = true;
            FtUtil.log_i("FtClient", "run", "Client socket connect success");
            if (!bool) {
              continue;
            }
            FtUtil.log_i("FtClient", "run", "Connected to AT Core");
            this.active = true;
            if (FtClient.this.writer == null) {
              FtClient.this.writer = new ResponseWriter(FtClient.this.clientSocket_send);
            }
            if (FtClient.this.parser == null)
            {
              FtClient.this.parser = new AtParser();
              FtClient.this.parser.registerAllHandler(this.context, FtClient.this.writer);
            }
          }
        }
      }
      label486:
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.FtClient
 * JD-Core Version:    0.7.1
 */