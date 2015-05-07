package com.sec.factory.modules;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.sec.factory.support.FtUtil;

public class SensorNotification
  extends Thread
{
  private static int DUMMY = 0;
  public static int WHAT_NOTI_SENSOR_MAX = DUMMY;
  public static int WHAT_NOTI_SENSOR_MIN = DUMMY;
  public static int WHAT_NOTI_SENSOR_READY;
  public static int WHAT_NOTI_SENSOR_UPDATAE;
  private final String CLASS_NAME = "SensorNotification";
  private boolean FLAG_LOOP = false;
  private int NOTI_LOOP_DELAY = 100;
  private int TIMEOUT = 500;
  private int WHAT_LOCAL_TIMEOUT = 0;
  private int[] mFile_ID = null;
  private boolean[] mFile_Ready = null;
  private boolean mInterrupted = false;
  private boolean mIsReady_File = false;
  private boolean mIsReady_Manager = false;
  private boolean mIsTimeout = false;
  private Handler mLocalHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == SensorNotification.this.WHAT_LOCAL_TIMEOUT)
      {
        FtUtil.log_e("SensorNotification", "mLocalHandler.handleMessage", "WHAT_LOCAL_TIMEOUT [Message Received]");
        SensorNotification.access$102(SensorNotification.this, true);
      }
    }
  };
  private int[] mManager_ID = null;
  private boolean[] mManager_Ready = null;
  private ModuleSensor mModuleSensor;
  private Handler mNotiHandler = null;
  
  static
  {
    int i = DUMMY;
    DUMMY = i + 1;
    WHAT_NOTI_SENSOR_READY = i;
    int j = DUMMY;
    DUMMY = j + 1;
    WHAT_NOTI_SENSOR_UPDATAE = j;
  }
  
  public SensorNotification(Context paramContext, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    this.mManager_ID = paramArrayOfInt1;
    this.mFile_ID = paramArrayOfInt2;
    this.mIsTimeout = false;
    this.mInterrupted = false;
    this.mModuleSensor = ModuleSensor.instance(paramContext);
    if (this.mManager_ID != null) {
      this.mManager_Ready = new boolean[this.mManager_ID.length];
    }
    while (this.mFile_ID != null)
    {
      this.mFile_Ready = new boolean[this.mFile_ID.length];
      return;
      this.mIsReady_Manager = true;
    }
    this.mIsReady_File = true;
  }
  
  private boolean checkExceptionID(int paramInt)
  {
    if (paramInt == ModuleSensor.ID_MANAGER_GYRO_SELF)
    {
      FtUtil.log_e("SensorNotification", "checkExceptionID", " [" + paramInt + "] => " + ModuleSensor.getString_ID(paramInt));
      return true;
    }
    return false;
  }
  
  private String dataCheck(String[] paramArrayOfString)
  {
    str = "";
    if (paramArrayOfString != null) {
      try
      {
        int i = Integer.parseInt(paramArrayOfString[0]);
        for (int j = 0; j < i + 1; j++)
        {
          str = str + paramArrayOfString[j];
          if (j < i) {
            str = str + " , ";
          }
        }
        return str;
      }
      catch (Exception localException)
      {
        FtUtil.log_i("SensorNotification", "dataCheck", "Exception: " + Log.getStackTraceString(localException));
        return "NULL";
      }
    }
  }
  
  private void sendMessage_SensorReady()
  {
    FtUtil.log_e("SensorNotification", "sendMessage_SensorReady", " Timeout : " + this.TIMEOUT);
    if (this.mManager_ID != null) {
      FtUtil.log_d("SensorNotification", "sendMessage_SensorReady", " check - Manager ID");
    }
    if (this.mFile_ID != null) {
      FtUtil.log_d("SensorNotification", "sendMessage_SensorReady", " check - File ID");
    }
    long l1 = System.currentTimeMillis();
    FtUtil.log_i("SensorNotification", "sendMessage_SensorReady", " START!!");
    FtUtil.log_i("SensorNotification", "sendMessage_SensorReady", " Time : " + l1);
    this.mLocalHandler.sendEmptyMessageDelayed(this.WHAT_LOCAL_TIMEOUT, this.TIMEOUT);
    int i = 0;
    int j = 0;
    if (!this.mInterrupted)
    {
      if (this.mManager_ID != null)
      {
        int m = 0;
        if (m < this.mManager_ID.length)
        {
          if (this.mInterrupted) {
            return;
          }
          if (this.mManager_Ready[m] == 0)
          {
            if (!checkExceptionID(this.mManager_ID[m])) {
              break label199;
            }
            this.mManager_Ready[m] = true;
            i++;
          }
          for (;;)
          {
            m++;
            break;
            label199:
            String[] arrayOfString2 = this.mModuleSensor.getData(this.mManager_ID[m]);
            if (arrayOfString2 != null)
            {
              this.mManager_Ready[m] = true;
              i++;
              FtUtil.log_i("SensorNotification", "sendMessage_SensorReady", " Manager check [" + m + "] " + dataCheck(arrayOfString2));
            }
          }
        }
        if (i == this.mManager_ID.length) {
          this.mIsReady_Manager = true;
        }
      }
      if (this.mFile_ID != null)
      {
        for (int k = 0;; k++)
        {
          if (k >= this.mFile_ID.length) {
            break label406;
          }
          if (this.mInterrupted) {
            break;
          }
          if (this.mFile_Ready[k] == 0)
          {
            String[] arrayOfString1 = this.mModuleSensor.getData(this.mFile_ID[k]);
            if (arrayOfString1 != null)
            {
              this.mFile_Ready[k] = true;
              j++;
              FtUtil.log_i("SensorNotification", "sendMessage_SensorReady", " File check [" + k + "] " + dataCheck(arrayOfString1));
            }
          }
        }
        label406:
        if (j == this.mFile_ID.length) {
          this.mIsReady_File = true;
        }
      }
      if ((!this.mIsReady_Manager) || (!this.mIsReady_File)) {
        break label544;
      }
      FtUtil.log_e("SensorNotification", "sendMessage_SensorReady", " All ready");
    }
    for (;;)
    {
      long l2 = System.currentTimeMillis();
      long l3 = l2 - l1;
      FtUtil.log_i("SensorNotification", "sendMessage_SensorReady", " FINISH!!");
      FtUtil.log_i("SensorNotification", "sendMessage_SensorReady", " Time : " + l2);
      FtUtil.log_e("SensorNotification", "sendMessage_SensorReady", " Time Lag => " + l3 + " millisecond");
      FtUtil.log_e("SensorNotification", "sendMessage_SensorReady", " Send Message => WHAT_NOTI_SENSOR_READY");
      this.mNotiHandler.sendEmptyMessage(WHAT_NOTI_SENSOR_READY);
      return;
      label544:
      if (!this.mIsTimeout) {
        break;
      }
      FtUtil.log_e("SensorNotification", "sendMessage_SensorReady", " ==========> Timeout~!~!~!~!~!~!");
      showID_NotReady();
    }
  }
  
  private void sendMessage_SensorUpdate()
  {
    FtUtil.log_i("SensorNotification", "sendMessage_SensorUpdate", " START!! ");
    FtUtil.log_e("SensorNotification", "sendMessage_SensorUpdate", " Send Message => WHAT_NOTI_SENSOR_UPDATAE");
    while (!this.mInterrupted)
    {
      this.mNotiHandler.sendEmptyMessage(WHAT_NOTI_SENSOR_UPDATAE);
      try
      {
        sleep(this.NOTI_LOOP_DELAY);
      }
      catch (InterruptedException localInterruptedException)
      {
        FtUtil.log_e(localInterruptedException);
      }
    }
    FtUtil.log_i("SensorNotification", "sendMessage_SensorUpdate", " FINISH!! ");
  }
  
  private void showID_NotReady()
  {
    String str1 = "";
    if (this.mManager_Ready != null)
    {
      for (int j = 0; j < this.mManager_Ready.length; j++) {
        if (this.mManager_Ready[j] == 0) {
          str1 = str1 + " [" + j + "] " + ModuleSensor.getString_ID(this.mManager_ID[j]) + ", ";
        }
      }
      FtUtil.log_d("SensorNotification", "showID_NotReady", " Not Ready ID (Manager) => " + str1);
    }
    String str2 = "";
    if (this.mFile_Ready != null)
    {
      for (int i = 0; i < this.mFile_Ready.length; i++) {
        if (this.mFile_Ready[i] == 0) {
          str2 = str2 + " [" + i + "] " + ModuleSensor.getString_ID(this.mFile_ID[i]) + ", ";
        }
      }
      FtUtil.log_d("SensorNotification", "showID_NotReady", " Not Ready ID (File) => " + str2);
    }
  }
  
  public void interrup()
  {
    this.mInterrupted = true;
  }
  
  public void run()
  {
    FtUtil.log_i("SensorNotification", "run", " ========== ");
    sendMessage_SensorReady();
    FtUtil.log_i("SensorNotification", "run", " ========== ");
    if (this.FLAG_LOOP)
    {
      sendMessage_SensorUpdate();
      FtUtil.log_i("SensorNotification", "run", " ========== ");
    }
  }
  
  public void setHandler(Handler paramHandler)
  {
    this.mNotiHandler = paramHandler;
  }
  
  public void setLoopDelay(int paramInt)
  {
    if (paramInt > 0)
    {
      this.FLAG_LOOP = true;
      this.NOTI_LOOP_DELAY = paramInt;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.SensorNotification
 * JD-Core Version:    0.7.1
 */