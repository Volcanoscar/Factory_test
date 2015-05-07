package com.sec.factory.cporiented;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemProperties;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.XMLDataStorage;

public class FtClientCPO
  extends Service
{
  public String BIND_SVC_NAME = "com.sec.phone.SecPhoneService";
  public String CLASS_NAME = "FtClientCPO";
  private BroadcastReceiver mAmBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      FtUtil.log_i(FtClientCPO.this.CLASS_NAME, "onReceive", "Received intent from am");
      String str1 = paramAnonymousIntent.getAction();
      String str2 = paramAnonymousIntent.getStringExtra("COMMAND");
      FtUtil.log_i(FtClientCPO.this.CLASS_NAME, "onReceive", "rawCMD: " + str2);
      if ("com.android.samsungtest.RilDFTCommand".equals(str1)) {}
      for (boolean bool = true; FtClientCPO.this.commandOk(str2) == true; bool = false)
      {
        FtClientCPO.this.sendAckCommand(0, bool);
        FtClientCPO.this.parser.process(str2, FtClientCPO.this.writer, bool);
        return;
      }
      FtClientCPO.this.sendAckCommand(1, bool);
    }
  };
  private final Handler mBindHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      FtUtil.log_d(FtClientCPO.this.CLASS_NAME, "handleMessage", "get message from SecPhoneService");
      Message localMessage = Message.obtain();
      localMessage.copyFrom(paramAnonymousMessage);
      Bundle localBundle = localMessage.getData();
      String str1 = localBundle.getString("COMMAND");
      String str2 = localBundle.getString("action");
      FtUtil.log_i(FtClientCPO.this.CLASS_NAME, "handleMessage", "ACTION: " + str2);
      FtUtil.log_i(FtClientCPO.this.CLASS_NAME, "handleMessage", "rawCMD: " + str1);
      if (((str2.equals("com.android.samsungtest.RilCommand")) || (str2.equals("com.android.samsungtest.RilDFTCommand"))) && (!FtClientCPO.this.mIsFirstCmd))
      {
        FtClientCPO.this.setPendingIntent();
        FtClientCPO.access$202(FtClientCPO.this, true);
        FtUtil.log_i(FtClientCPO.this.CLASS_NAME, "handleMessage", "FirstCMDReceived: Noti ON");
      }
      if ("com.android.samsungtest.RilDFTCommand".equals(str2)) {}
      for (boolean bool = true; FtClientCPO.this.commandOk(str1) == true; bool = false)
      {
        FtClientCPO.this.sendAckCommand(0, bool);
        FtClientCPO.this.parser.process(str1, FtClientCPO.this.writer, bool);
        return;
      }
      FtClientCPO.this.sendAckCommand(1, bool);
    }
  };
  private final Messenger mBindMessager = new Messenger(this.mBindHandler);
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      for (;;)
      {
        String str;
        try
        {
          str = paramAnonymousIntent.getAction();
          FtUtil.log_d(FtClientCPO.this.CLASS_NAME, "mBroadcastReceiver", "action= = " + str);
          if (str.equals("com.sec.factory.entry.SEND_BOOT_COMPLETED"))
          {
            FtClientCPO.this.mHandler.post(new Runnable()
            {
              public void run()
              {
                if ((FtClientCPO.this.mSecPhoneServiceMessenger != null) && (FtClientCPO.this.writer != null))
                {
                  FtUtil.log_d(FtClientCPO.this.CLASS_NAME, "mBroadcastReceiver", "BOOTING COMPLETED!!");
                  FtClientCPO.this.writer.sendRILBootMsg();
                  FtClientCPO.this.stopForeground(true);
                }
                for (;;)
                {
                  if ((SystemProperties.get("ro.csc.sales_code", "Unknown").toUpperCase().contains("CTC")) && (ModuleCommon.instance(FtClientCPO.this.mContext).connectedJIG())) {
                    FtClientCPO.this.setPendingIntent();
                  }
                  return;
                  FtUtil.log_d(FtClientCPO.this.CLASS_NAME, "mBroadcastReceiver", "clientSocket is not Ready");
                  FtClientCPO.this.mHandler.postDelayed(this, 1000L);
                }
              }
            });
            return;
          }
          if (str.equals("com.sec.factory.entry.SEND_SET_FOREGROUND"))
          {
            FtClientCPO.this.startForeground(138, new Notification());
            continue;
          }
          if (!str.equals("com.sec.factory.SEND_TO_RIL")) {
            break label171;
          }
        }
        finally {}
        final byte[] arrayOfByte = paramAnonymousIntent.getByteArrayExtra("message");
        if (arrayOfByte == null)
        {
          FtUtil.log_d(FtClientCPO.this.CLASS_NAME, "mBroadcastReceiver ACTION_SEND_TO_RIL", "Empty Message!");
        }
        else
        {
          FtClientCPO.this.mHandler.post(new Runnable()
          {
            public void run()
            {
              if ((FtClientCPO.this.mSecPhoneServiceMessenger != null) && (FtClientCPO.this.writer != null))
              {
                FtUtil.log_d(FtClientCPO.this.CLASS_NAME, "mBroadcastReceiver ACTION_SEND_TO_RIL", arrayOfByte.toString());
                FtClientCPO.this.writer.write(arrayOfByte);
                return;
              }
              FtUtil.log_d(FtClientCPO.this.CLASS_NAME, "mBroadcastReceiver ACTION_SEND_TO_RIL", "clientSocket is not Ready");
              FtClientCPO.this.mHandler.postDelayed(this, 1000L);
            }
          });
          continue;
          label171:
          if (str.equals("com.sec.factory.cporiented.SEND_CMD2RIL"))
          {
            if ("FtClientCPO2".equals(FtClientCPO.this.CLASS_NAME)) {
              if ((FtClientCPO.this.mSecPhoneServiceMessenger != null) && (FtClientCPO.this.writer != null))
              {
                FtClientCPO.this.writer.sendSleepCmd();
                FtUtil.log_d(FtClientCPO.this.CLASS_NAME, "mBroadcastReceiver", "ACTION_SEND_CMD2RIL");
              }
              else
              {
                FtUtil.log_d(FtClientCPO.this.CLASS_NAME, "mBroadcastReceiver", "ACTION_SEND_CMD2RIL clientSocket is not Ready");
              }
            }
          }
          else if ((FtClientCPO.this.model.contains("n7102")) && (str.equals("com.sec.factory.entry.SEND_BOOT_COMPLETED_RIL2")) && ("FtClientCPO2".equals(FtClientCPO.this.CLASS_NAME))) {
            FtClientCPO.this.mHandler.post(new Runnable()
            {
              public void run()
              {
                if ((FtClientCPO.this.mSecPhoneServiceMessenger != null) && (FtClientCPO.this.writer != null))
                {
                  FtUtil.log_d(FtClientCPO.this.CLASS_NAME, "mBroadcastReceiver", "BOOTING COMPLETED!!");
                  FtClientCPO.this.writer.sendRILBootMsg();
                  FtClientCPO.this.stopForeground(true);
                  return;
                }
                FtUtil.log_d(FtClientCPO.this.CLASS_NAME, "mBroadcastReceiver", "clientSocket is not Ready");
                FtClientCPO.this.mHandler.postDelayed(this, 1000L);
              }
            });
          }
        }
      }
    }
  };
  private Context mContext;
  private ServiceConnection mFactoryTestServiceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      FtUtil.log_i(FtClientCPO.this.CLASS_NAME, "onServiceConnected", "connected done");
      FtClientCPO.access$002(FtClientCPO.this, new Messenger(paramAnonymousIBinder));
      FtClientCPO.this.createResponseWriter();
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      FtUtil.log_i(FtClientCPO.this.CLASS_NAME, "onServiceDisconnected", "disconnected done");
      FtClientCPO.access$002(FtClientCPO.this, null);
      FtClientCPO.this.removeResponseWriter();
      FtClientCPO.this.rebindToSecPhoneService();
    }
  };
  private Handler mHandler = new Handler();
  private boolean mIsFirstCmd = false;
  private Messenger mSecPhoneServiceMessenger = null;
  public String model = SystemProperties.get("ro.product.model", "Unknown").trim().toLowerCase();
  public AtParserCPO parser = null;
  public ResponseWriterCPO writer = null;
  
  private boolean commandOk(String paramString)
  {
    if (4096 * FtUtil.charToNum(paramString.charAt(2)) + 256 * FtUtil.charToNum(paramString.charAt(3)) + 16 * FtUtil.charToNum(paramString.charAt(0)) + FtUtil.charToNum(paramString.charAt(1)) == (-4 + paramString.length()) / 2) {
      return true;
    }
    FtUtil.log_e(this.CLASS_NAME, "commandOk", "Invalid IPC CMD received");
    return false;
  }
  
  private void rebindToSecPhoneService()
  {
    if (this.mSecPhoneServiceMessenger != null) {
      return;
    }
    new Thread()
    {
      public void run()
      {
        while (FtClientCPO.this.mSecPhoneServiceMessenger == null)
        {
          FtClientCPO.this.connectToSecPhoneService();
          try
          {
            Thread.sleep(1000L);
          }
          catch (InterruptedException localInterruptedException)
          {
            FtUtil.log_i(FtClientCPO.this.CLASS_NAME, "rebindToSecPhoneService", "sleep interrupted.");
          }
        }
      }
    }.start();
  }
  
  private void sendAckCommand(int paramInt, boolean paramBoolean)
  {
    String str1 = this.CLASS_NAME;
    if (paramInt == 0) {}
    for (String str2 = "N_ACK_RESPONSE";; str2 = "N_NACK_RESPONSE")
    {
      FtUtil.log_d(str1, "sendAckCommand", str2);
      if (paramBoolean) {
        paramInt += 7;
      }
      if (this.writer != null) {
        this.writer.write(paramInt, null, null, null);
      }
      return;
    }
  }
  
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
  
  public void connectToSecPhoneService()
  {
    FtUtil.log_i(this.CLASS_NAME, "connectToSecPhoneService", "Create sendMessenger - " + this.BIND_SVC_NAME);
    Intent localIntent = new Intent();
    localIntent.setClassName("com.sec.phone", this.BIND_SVC_NAME);
    bindService(localIntent, this.mFactoryTestServiceConnection, 1);
  }
  
  public void createResponseWriter()
  {
    this.writer = new ResponseWriterCPO(this.mSecPhoneServiceMessenger);
    this.parser = new AtParserCPO();
    this.parser.registerAllHandler(getApplicationContext(), this.writer);
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    FtUtil.log_i(this.CLASS_NAME, "onBind", "start");
    return this.mBindMessager.getBinder();
  }
  
  public void onCreate()
  {
    this.mContext = getApplicationContext();
    FtUtil.log_i(this.CLASS_NAME, "onCreate", "Create FtClientCPO service");
    if (!XMLDataStorage.instance().parseXML(getApplicationContext())) {
      FtUtil.log_i(this.CLASS_NAME, "onCreate", "FtClientCPO => XML data parsing was failed.");
    }
    connectToSecPhoneService();
    IntentFilter localIntentFilter1 = new IntentFilter();
    if ("FtClientCPO".equals(this.CLASS_NAME))
    {
      localIntentFilter1.addAction("com.sec.factory.entry.SEND_BOOT_COMPLETED");
      localIntentFilter1.addAction("com.sec.factory.entry.SEND_SET_FOREGROUND");
      localIntentFilter1.addAction("com.sec.factory.SEND_TO_RIL");
    }
    for (;;)
    {
      registerReceiver(this.mBroadcastReceiver, localIntentFilter1);
      FtUtil.log_i(this.CLASS_NAME, "onCreate", "mBroadcastReceiver registered");
      IntentFilter localIntentFilter2 = new IntentFilter();
      localIntentFilter2.addAction("com.android.samsungtest.RilCommand");
      localIntentFilter2.addAction("com.android.samsungtest.RilDFTCommand");
      return;
      if ("FtClientCPO2".equals(this.CLASS_NAME))
      {
        localIntentFilter1.addAction("com.sec.factory.cporiented.SEND_CMD2RIL");
        if (this.model.contains("n7102")) {
          localIntentFilter1.addAction("com.sec.factory.entry.SEND_BOOT_COMPLETED_RIL2");
        }
      }
      else
      {
        FtUtil.log_e(this.CLASS_NAME, "onCreate", "Cannot classify CLASS_NAME for mBroadcastReceiver register");
      }
    }
  }
  
  public void onDestroy()
  {
    if (("FtClientCPO".equals(this.CLASS_NAME)) && (this.mBroadcastReceiver != null))
    {
      unregisterReceiver(this.mBroadcastReceiver);
      this.mBroadcastReceiver = null;
    }
    super.onDestroy();
    FtUtil.log_d(this.CLASS_NAME, "onDestroy", "Destroy FtClientCPO service");
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    return 0;
  }
  
  public void removeResponseWriter()
  {
    this.writer = null;
  }
  
  protected void setBindSvcName(String paramString)
  {
    this.BIND_SVC_NAME = paramString;
  }
  
  protected void setClassName(String paramString)
  {
    this.CLASS_NAME = paramString;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.cporiented.FtClientCPO
 * JD-Core Version:    0.7.1
 */