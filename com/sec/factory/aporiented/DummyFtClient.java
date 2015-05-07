package com.sec.factory.aporiented;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.XMLDataStorage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DummyFtClient
  extends Service
{
  LocalSocket clientSocket_recv = new LocalSocket();
  LocalSocket clientSocket_send = new LocalSocket();
  private ConnectionThread connectionThread;
  public int mCounter = 0;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      case 102: 
        DummyFtClient.this.stopService();
        return;
      }
      DummyFtClient.this.writer.write("BOOTING COMPLETED\r\n");
    }
  };
  public AtParser parser;
  public ResponseWriter writer;
  
  private void sendBootCompletedAndFinish()
  {
    FtUtil.log_d("DummyFtClient", "sendBootCompletedAndFinish", "...");
    if (!ModuleCommon.instance(this).isConnectionModeNone()) {
      new IPCWriterToSecPhoneService(this);
    }
    for (;;)
    {
      this.mHandler.post(new Runnable()
      {
        public void run()
        {
          DummyFtClient.this.mHandler.sendEmptyMessageDelayed(102, 5000L);
        }
      });
      return;
      this.mHandler.post(new Runnable()
      {
        public void run()
        {
          int i = 0;
          for (;;)
          {
            if (i < 10)
            {
              if ((DummyFtClient.this.clientSocket_recv.isConnected()) && (DummyFtClient.this.clientSocket_send.isConnected()) && (DummyFtClient.this.writer != null))
              {
                FtUtil.log_d("DummyFtClient", "sendBootCompletedAndFinish", "BOOTING COMPLETED!!");
                DummyFtClient.this.mHandler.sendEmptyMessage(1002);
              }
            }
            else {
              return;
            }
            FtUtil.log_d("DummyFtClient", "sendBootCompletedAndFinish", "Retry couter : " + i);
            try
            {
              Thread.sleep(1000L);
              i++;
            }
            catch (InterruptedException localInterruptedException)
            {
              for (;;)
              {
                localInterruptedException.printStackTrace();
              }
            }
          }
        }
      });
    }
  }
  
  private void stopService()
  {
    stopSelf();
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    FtUtil.log_d("DummyFtClient", "onCreate", "Create DummyFtClient service");
    if (!XMLDataStorage.instance().parseXML(this)) {
      FtUtil.log_d("DummyFtClient", "onReceive", "ACTION_SIM_STATE_CHANGED => XML data parsing was failed.");
    }
    if (ModuleCommon.instance(this).isConnectionModeNone())
    {
      this.connectionThread = new ConnectionThread(this);
      this.connectionThread.start();
    }
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    FtUtil.log_d("DummyFtClient", "onDestroy", "Destroy DummyFtClient service");
    if (ModuleCommon.instance(this).isConnectionModeNone())
    {
      this.connectionThread.kill();
      if (this.clientSocket_recv == null) {}
    }
    try
    {
      this.clientSocket_recv.close();
      FtUtil.log_i("DummyFtClient", "onDestroy", "Close client socket(receiver)");
      if (this.clientSocket_send == null) {}
    }
    catch (IOException localIOException2)
    {
      for (;;)
      {
        try
        {
          this.clientSocket_send.close();
          FtUtil.log_i("DummyFtClient", "onDestroy", "Close client socket(sender)");
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
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    FtUtil.log_d("DummyFtClient", "onStartCommand", "...");
    sendBootCompletedAndFinish();
    return 2;
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
      FtUtil.log_d("DummyFtClient", "run", "Kill ConnectionThread");
    }
    
    public void run()
    {
      FtUtil.log_d("DummyFtClient", "run", "ConnectionThread start");
      int i = 0;
      int j = 0;
      LocalSocketAddress localLocalSocketAddress1 = new LocalSocketAddress("FactoryClientRecv");
      LocalSocketAddress localLocalSocketAddress2 = new LocalSocketAddress("FactoryClientSend");
      for (;;)
      {
        if (i == 0) {
          try
          {
            if (!DummyFtClient.this.clientSocket_recv.isConnected())
            {
              DummyFtClient.this.clientSocket_recv.connect(localLocalSocketAddress1);
              FtUtil.log_i("DummyFtClient", "run", "Connect client socket(receiver)");
            }
            if (!DummyFtClient.this.clientSocket_send.isConnected())
            {
              DummyFtClient.this.clientSocket_send.connect(localLocalSocketAddress2);
              FtUtil.log_i("DummyFtClient", "run", "Connect client socket(sender)");
            }
            if ((DummyFtClient.this.clientSocket_recv.isConnected()) && (DummyFtClient.this.clientSocket_send.isConnected()))
            {
              i = 1;
              FtUtil.log_i("DummyFtClient", "run", "Client socket connect success");
              if (i == 0) {
                continue;
              }
              FtUtil.log_i("DummyFtClient", "run", "Connected to AT Core");
              this.active = true;
              DummyFtClient.this.writer = new ResponseWriter(DummyFtClient.this.clientSocket_send);
            }
          }
          catch (IOException localIOException1)
          {
            try
            {
              for (;;)
              {
                this.in = new BufferedReader(new InputStreamReader(DummyFtClient.this.clientSocket_recv.getInputStream()));
                while (this.active) {
                  try
                  {
                    String str = this.in.readLine();
                    if (str == null) {
                      break label320;
                    }
                    DummyFtClient.this.parser.process(str, DummyFtClient.this.writer);
                  }
                  catch (IOException localIOException3)
                  {
                    FtUtil.log_e(localIOException3);
                    this.active = false;
                    i = 0;
                  }
                }
                localIOException1 = localIOException1;
                FtUtil.log_e(localIOException1);
                continue;
                try
                {
                  Thread.sleep(3000L);
                  i = 0;
                }
                catch (InterruptedException localInterruptedException)
                {
                  FtUtil.log_e(localInterruptedException);
                  i = 0;
                }
              }
            }
            catch (IOException localIOException2)
            {
              for (;;)
              {
                FtUtil.log_e(localIOException2);
                this.active = false;
                i = 0;
                continue;
                label320:
                j++;
                FtUtil.log_i("DummyFtClient", "run", "Garbage data incoming...");
                if (j > 10)
                {
                  FtUtil.log_i("DummyFtClient", "run", "Stop DummyFtClient(Garbage data count=" + j + ")");
                  this.active = false;
                }
              }
            }
          }
        }
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.DummyFtClient
 * JD-Core Version:    0.7.1
 */