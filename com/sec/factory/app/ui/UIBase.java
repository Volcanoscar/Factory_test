package com.sec.factory.app.ui;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import com.sec.factory.app.factorytest.FactoryTestDump;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.app.factorytest.FactoryTestPhone;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class UIBase
  extends Activity
{
  private final long BACK_KEY_EVENT_TIMELAG = 2000L;
  protected String CLASS_NAME;
  private int COUNT_MAX_NV_WRITE = 1;
  protected final byte FAIL = 70;
  protected final byte NOTEST = 78;
  protected int ONPASS_FINISH_DELAY = 0;
  protected final byte PASS = 80;
  protected int TEST_ID = -1;
  protected byte TEST_RESULT = 78;
  private boolean mCopyDumpResult = false;
  private int mCount_NVWrite = 0;
  private String mCurrentStage = "FactoryTestMain";
  private String mDumpTime = "";
  private FactoryTestDump mFactoryDump;
  protected FactoryTestPhone mFactoryPhone;
  protected boolean mFlag_BackKey_Twice = false;
  public Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      case 1001: 
        FtUtil.log_i(UIBase.this.CLASS_NAME, "mHandler.handleMessage", "Dismiss!");
        UIBase.this.mLogProgressDialog.dismiss();
        UIBase.this.infoLogAll();
        return;
      }
      FtUtil.log_i(UIBase.this.CLASS_NAME, "mHandler.handleMessage", "Copy Dump");
      UIBase.this.doCopyDumpOperation();
    }
  };
  protected boolean mIsFromLcdTest = false;
  private boolean mIsLongPress = false;
  private AlertDialog.Builder mLogDialogBuilder;
  private DialogInterface.OnClickListener mLogDialogOnClick = new DialogInterface.OnClickListener()
  {
    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
    {
      UIBase.this.finish();
    }
  };
  private ProgressDialog mLogProgressDialog;
  private int mLogResult = -1;
  private long mPrevBackKeyEventTime = -1L;
  Timer mTimer = new Timer();
  protected Handler mTimerHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      }
      UIBase.this.finish();
    }
  };
  
  public UIBase(String paramString)
  {
    this.CLASS_NAME = paramString;
  }
  
  public UIBase(String paramString, int paramInt)
  {
    this.CLASS_NAME = paramString;
    this.TEST_ID = paramInt;
  }
  
  private void ResultMessage(String paramString)
  {
    if (this.mLogDialogBuilder != null)
    {
      this.mLogDialogBuilder.setIcon(17301543);
      this.mLogDialogBuilder.setTitle("Dump Result");
      this.mLogDialogBuilder.setMessage(paramString);
      this.mLogDialogBuilder.setPositiveButton(17039370, null);
      this.mLogDialogBuilder.setCancelable(true);
      this.mLogDialogBuilder.show();
    }
  }
  
  private void doCopyDumpOperation()
  {
    this.mLogProgressDialog.setMessage("Copying Dump...");
    final Timer localTimer = new Timer();
    localTimer.schedule(new TimerTask()
    {
      public void run()
      {
        if (UIBase.this.mCopyDumpResult)
        {
          UIBase.this.mHandler.sendEmptyMessage(1001);
          UIBase.access$302(UIBase.this, false);
          localTimer.cancel();
        }
      }
    }, 0L, 500L);
    new Thread(new Runnable()
    {
      public void run()
      {
        UIBase.access$302(UIBase.this, UIBase.this.mFactoryDump.doCopyDump());
        FtUtil.log_i(UIBase.this.CLASS_NAME, "doCopyDumpOperation", "Finish CopyDumpThread");
      }
    }).start();
  }
  
  private void doDumpState()
  {
    showProgressDialog();
    this.mDumpTime = this.mFactoryDump.getTimestamp();
    this.mCurrentStage = getPositionData();
    this.mCurrentStage = this.mCurrentStage.trim();
    FtUtil.log_i(this.CLASS_NAME, "doDumpState", "sysdump_time : " + this.mDumpTime);
    FtUtil.log_d(this.CLASS_NAME, "onItemClick", "mCurrentStage: " + this.mCurrentStage);
    final Timer localTimer = new Timer();
    localTimer.schedule(new TimerTask()
    {
      public void run()
      {
        if (UIBase.this.mLogResult > 0)
        {
          UIBase.this.mHandler.sendEmptyMessage(1000);
          UIBase.access$502(UIBase.this, -1);
          localTimer.cancel();
        }
        FtUtil.log_i(UIBase.this.CLASS_NAME, "doDumpState", "sysdump_time : " + UIBase.this.mDumpTime);
        FtUtil.log_i(UIBase.this.CLASS_NAME, "doDumpState", "mLogResult : " + UIBase.this.mLogResult);
      }
    }, 0L, 1000L);
    new Thread(new Runnable()
    {
      public void run()
      {
        File localFile = new File("/data/log");
        if (!localFile.exists()) {
          localFile.mkdir();
        }
        UIBase.access$502(UIBase.this, UIBase.this.mFactoryDump.DoShellCmd("dumpstate > /data/log/15TEST_" + UIBase.this.mCurrentStage + "_" + UIBase.this.mDumpTime + ".log"));
        FtUtil.log_i(UIBase.this.CLASS_NAME, "doDumpState", "Finish DumpThread");
      }
    }).start();
    if (this.mFactoryPhone != null)
    {
      this.mFactoryPhone.getCPDump();
      if (!this.mFactoryPhone.getCpDumpResponse()) {}
    }
  }
  
  private void finishKeyCode()
  {
    FtUtil.log_i(this.CLASS_NAME, "finishKeyCode", "mCount_NVWrite: " + this.mCount_NVWrite + " , TEST_ID=" + this.TEST_ID);
    if ((this.mCount_NVWrite < this.COUNT_MAX_NV_WRITE) && (this.TEST_ID > -1))
    {
      byte b = FactoryTestManager.getItemResult(FactoryTestManager.getItemPosition_ID(this.TEST_ID));
      FtUtil.log_i(this.CLASS_NAME, "finishKeyCode", "nvValue: " + FactoryTestManager.convertorNVValue(b));
    }
    finish();
  }
  
  private void infoLogAll()
  {
    ResultMessage("Dump Success!");
  }
  
  private void showDumpStartDialog()
  {
    FtUtil.log_d(this.CLASS_NAME, "showDumpStartDialog", "showDumpStartDialog");
    DumpAlertDialogFragment.newInstance(10000).show(getFragmentManager(), "dialog");
  }
  
  private void showProgressDialog()
  {
    if (this.mLogProgressDialog != null)
    {
      this.mLogProgressDialog.setMessage("Getting Dump...");
      this.mLogProgressDialog.setIndeterminate(true);
      this.mLogProgressDialog.setCancelable(false);
      this.mLogProgressDialog.show();
    }
  }
  
  protected void finishOnPassWithTimer()
  {
    this.mTimerHandler.sendEmptyMessageDelayed(1, this.ONPASS_FINISH_DELAY);
  }
  
  protected String getPositionData()
  {
    return getSharedPreferences("recentfactorytest", 0).getString("itemposition", "FactoryTestMain");
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mFactoryDump = new FactoryTestDump(this);
    this.mLogDialogBuilder = new AlertDialog.Builder(this);
    this.mLogProgressDialog = new ProgressDialog(this);
    FtUtil.setSystemKeyBlock(getComponentName(), 187);
    FtUtil.setSystemKeyBlock(getComponentName(), 65);
    FtUtil.setSystemKeyBlock(getComponentName(), 64);
    FtUtil.setSystemKeyBlock(getComponentName(), 3);
    FtUtil.setSystemKeyBlock(getComponentName(), 26);
    FtUtil.setSystemKeyBlock(getComponentName(), 84);
    FtUtil.setRemoveStatusBar(getWindow());
    if (Support.Feature.getBoolean("SUPPORT_3X4_KEY")) {
      FtUtil.setSystemKeyBlock(getComponentName(), 231);
    }
    getWindow().addFlags(128);
    FtUtil.log_i(this.CLASS_NAME, "onCreate", "Create " + this.CLASS_NAME);
    if ((!FtUtil.isFactoryAppAPO()) && (this.mFactoryPhone == null))
    {
      this.mFactoryPhone = new FactoryTestPhone(this);
      this.mFactoryPhone.bindSecPhoneService();
    }
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    if (this.mFactoryPhone != null)
    {
      this.mFactoryPhone.DestroySecPhoneService();
      this.mFactoryPhone = null;
      FtUtil.log_i(this.CLASS_NAME, "onDestroy", "Unbind SecPhoneService");
    }
    FtUtil.log_i(this.CLASS_NAME, "onDestroy", "Destroy " + this.CLASS_NAME);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (this.mIsFromLcdTest) {
      finish();
    }
    if (!this.mIsLongPress) {
      this.mIsLongPress = paramKeyEvent.isLongPress();
    }
    if (!this.mIsLongPress) {}
    switch (paramInt)
    {
    default: 
    case 4: 
    case 24: 
      for (;;)
      {
        this.mPrevBackKeyEventTime = -1L;
        FtUtil.log_d("UIBase", "onKeyDown", "return true");
        return true;
        FtUtil.log_d(this.CLASS_NAME, "onKeyDown", "KEYCODE_BACK => Prev : " + this.mPrevBackKeyEventTime + ", Curr : " + paramKeyEvent.getEventTime() + " => Time Lag : " + (paramKeyEvent.getEventTime() - this.mPrevBackKeyEventTime) + " [" + 2000L + "]");
        if (this.mPrevBackKeyEventTime != -1L) {
          if (paramKeyEvent.getEventTime() - this.mPrevBackKeyEventTime < 2000L)
          {
            this.mFlag_BackKey_Twice = true;
            if (this.TEST_ID == 17) {
              finish();
            }
          }
        }
        for (;;)
        {
          FtUtil.log_d("UIBase", "onKeyDown", "KEYCODE_BACK = return true");
          return true;
          finishKeyCode();
          continue;
          this.mPrevBackKeyEventTime = paramKeyEvent.getEventTime();
          continue;
          this.mPrevBackKeyEventTime = paramKeyEvent.getEventTime();
        }
        FtUtil.log_d(this.CLASS_NAME, "onKeyDown", "KEYCODE_VOLUME_UP");
        if (this.TEST_ID == 23)
        {
          FtUtil.log_d("UIBase", "onKeyDown", "KEYCODE_VOLUME_UP = TEST_HDMI");
          finishKeyCode();
        }
      }
    case 3: 
      paramKeyEvent.startTracking();
      return true;
    }
    FtUtil.log_i(this.CLASS_NAME, "onKeyDown", "KEYCODE_HEADSETHOOK");
    startEarKey(true);
    return true;
  }
  
  public boolean onKeyLongPress(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    default: 
      return true;
    }
    showDumpStartDialog();
    return true;
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    this.mIsLongPress = false;
    FtUtil.log_d("UIBase", "onKeyUp", "return true");
    switch (paramInt)
    {
    }
    for (;;)
    {
      return true;
      FtUtil.log_i(this.CLASS_NAME, "onKeyUp", "KEYCODE_HEADSETHOOK");
      startEarKey(false);
    }
  }
  
  protected void onPause()
  {
    super.onPause();
    FtUtil.log_i(this.CLASS_NAME, "onPause", "Pause " + this.CLASS_NAME);
  }
  
  protected void onResume()
  {
    super.onResume();
    FtUtil.log_i(this.CLASS_NAME, "onResume", "Resume " + this.CLASS_NAME);
  }
  
  protected void savePositionData(String paramString)
  {
    SharedPreferences.Editor localEditor = getSharedPreferences("recentfactorytest", 0).edit();
    localEditor.putString("itemposition", paramString);
    localEditor.commit();
  }
  
  protected void setCountNVWrite(int paramInt)
  {
    this.COUNT_MAX_NV_WRITE = paramInt;
  }
  
  public void setTestId(int paramInt)
  {
    this.TEST_ID = paramInt;
  }
  
  protected void setTestResult(byte paramByte)
  {
    FtUtil.log_i(this.CLASS_NAME, "setTestResult", "result:" + paramByte);
    if ((this.TEST_ID != -1) && (this.TEST_RESULT == 78))
    {
      this.TEST_RESULT = paramByte;
      int i = FactoryTestManager.getItemPosition_ID(this.TEST_ID);
      FtUtil.log_i(this.CLASS_NAME, "setTestResult", "itemid: " + i);
      this.mCount_NVWrite = (1 + this.mCount_NVWrite);
      FactoryTestManager.setItemResult(i, paramByte);
      FactoryTestManager.notifyDataSetChanged();
      if ((!FtUtil.isFactoryAppAPO()) && (this.mFactoryPhone != null)) {
        this.mFactoryPhone.updateNVItem(FactoryTestManager.getItemNVKey(i), paramByte);
      }
    }
  }
  
  protected void setTestResult(byte paramByte1, byte paramByte2)
  {
    FtUtil.log_i(this.CLASS_NAME, "setTestResult", "id: " + paramByte1 + " , result:" + paramByte2);
    int i = FactoryTestManager.getItemPosition_ID(paramByte1);
    if (i > -1)
    {
      FtUtil.log_d(this.CLASS_NAME, "setTestResult", "itemid: " + i);
      this.mCount_NVWrite = (1 + this.mCount_NVWrite);
      FactoryTestManager.setItemResult(i, paramByte2);
      FactoryTestManager.notifyDataSetChanged();
      if ((!FtUtil.isFactoryAppAPO()) && (this.mFactoryPhone != null)) {
        this.mFactoryPhone.updateNVItem(FactoryTestManager.getItemNVKey(i), paramByte2);
      }
    }
  }
  
  protected void setTestResultFailCase(byte paramByte)
  {
    FtUtil.log_i(this.CLASS_NAME, "setTestResultFailCase", "mCount_NVWrite: " + this.mCount_NVWrite + " , TEST_ID=" + paramByte);
    if ((this.mCount_NVWrite < this.COUNT_MAX_NV_WRITE) && (this.TEST_ID > -1))
    {
      byte b = FactoryTestManager.getItemResult(FactoryTestManager.getItemPosition_ID(paramByte));
      FtUtil.log_i(this.CLASS_NAME, "setTestResultFailCase", "nvValue: " + FactoryTestManager.convertorNVValue(b));
    }
  }
  
  protected void setTestResultWithoutNV(byte paramByte1, byte paramByte2)
  {
    int i = FactoryTestManager.getItemPosition_ID(paramByte1);
    if (i > -1)
    {
      FactoryTestManager.setItemResultWithoutNVUpdate(i, paramByte2);
      FactoryTestManager.notifyDataSetChanged();
    }
  }
  
  public void startEarKey(boolean paramBoolean)
  {
    if (paramBoolean == true) {
      setTestResult((byte)13, (byte)80);
    }
  }
  
  public static class DumpAlertDialogFragment
    extends DialogFragment
  {
    public static DumpAlertDialogFragment newInstance(int paramInt)
    {
      return new DumpAlertDialogFragment();
    }
    
    public Dialog onCreateDialog(Bundle paramBundle)
    {
      new AlertDialog.Builder(getActivity()).setMessage("Start Dump").setPositiveButton("OK", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          ((UIBase)UIBase.DumpAlertDialogFragment.this.getActivity()).doDumpState();
          UIBase.DumpAlertDialogFragment.this.dismiss();
        }
      }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          UIBase.DumpAlertDialogFragment.this.dismiss();
        }
      }).setOnKeyListener(new DialogInterface.OnKeyListener()
      {
        public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          if (paramAnonymousInt == 4) {
            UIBase.DumpAlertDialogFragment.this.dismiss();
          }
          return true;
        }
      }).create();
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIBase
 * JD-Core Version:    0.7.1
 */