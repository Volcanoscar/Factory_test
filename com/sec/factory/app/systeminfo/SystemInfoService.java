package com.sec.factory.app.systeminfo;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemProperties;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.sec.factory.app.factorytest.FactoryTestPhone;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.XMLDataStorage;
import java.util.List;

public class SystemInfoService
  extends Service
{
  private static String TAG = "SystemInfoService";
  public static String pbaValue = "04N";
  public static String smdValue = "01N";
  private final String GET_HISTORY_VIEW_ITEM_ACTION = "com.android.samsungtest.RilOmissionCommand";
  private ActivityManager mActivityManager;
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (paramAnonymousIntent.getAction().equals("com.android.samsungtest.RilOmissionCommand"))
      {
        SystemInfoService.this.mSystemInfoView.setVisibility(8);
        SystemInfoService.this.mWindowManager.removeView(SystemInfoService.this.mSystemInfoView);
        String str = paramAnonymousIntent.getStringExtra("COMMAND").substring(6);
        SystemInfoService.this.parseNVHistoryCPO(str);
        SystemInfoService.this.stopReceiver();
        SystemInfoService.access$202(SystemInfoService.this, new SystemInfoView(SystemInfoService.this.getApplicationContext()));
        SystemInfoService.access$602(SystemInfoService.this, (ActivityManager)SystemInfoService.this.getSystemService("activity"));
        SystemInfoService.access$302(SystemInfoService.this, (WindowManager)SystemInfoService.this.getSystemService("window"));
        SystemInfoService.access$702(SystemInfoService.this, new WindowManager.LayoutParams(-2, -2, 2005, 24, -3));
        SystemInfoService.this.mWindowManager.addView(SystemInfoService.this.mSystemInfoView, SystemInfoService.this.mLayoutParams);
        SystemInfoService.this.DisplaySystemInfo();
      }
    }
  };
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      }
      SystemInfoService.this.mHandler.removeMessages(3);
      SystemInfoService.this.DisplaySystemInfo();
    }
  };
  Handler mHandlerForTestNV = new Handler();
  private WindowManager.LayoutParams mLayoutParams;
  private View mSystemInfoView;
  private WindowManager mWindowManager;
  private String operator = SystemProperties.get("ro.csc.sales_code", "Unknown").trim().toLowerCase();
  
  private void DisplaySystemInfo()
  {
    try
    {
      String str2 = ((ActivityManager.RunningTaskInfo)this.mActivityManager.getRunningTasks(1).get(0)).topActivity.getClassName();
      str1 = str2;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        FtUtil.log_e(TAG, "Exception : " + localException);
        String str1 = null;
        continue;
        this.mSystemInfoView.setVisibility(4);
      }
    }
    if ((str1 != null) && ("com.android.launcher2.Launcher".contains(str1)))
    {
      this.mSystemInfoView.setVisibility(0);
      this.mHandler.sendEmptyMessageDelayed(3, 1000L);
      return;
    }
  }
  
  private String convertResult(String paramString)
  {
    String str = "N";
    if ("50".equals(paramString)) {
      str = "P";
    }
    do
    {
      return str;
      if ("45".equals(paramString)) {
        return "E";
      }
      if ("46".equals(paramString)) {
        return "F";
      }
    } while (!"4E".equals(paramString));
    return "N";
  }
  
  private void parseNVHistoryCPO(String paramString)
  {
    if (paramString == null) {}
    for (;;)
    {
      return;
      for (int i = 0; i < paramString.length(); i += 4)
      {
        String str1 = paramString.substring(i, i + 2);
        String str2 = paramString.substring(i + 2, i + 4);
        if ("01".equals(str1)) {
          smdValue = "01" + convertResult(str2);
        }
        FtUtil.log_i(TAG, "parseNVHistory", str1);
        if ("04".equals(str1)) {
          pbaValue = "04" + convertResult(str2);
        }
      }
    }
  }
  
  private void setupReceiver()
  {
    registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.android.samsungtest.RilOmissionCommand"));
  }
  
  private void stopReceiver()
  {
    unregisterReceiver(this.mBroadcastReceiver);
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    FtUtil.log_i(TAG, "onCreate", null);
    if (!XMLDataStorage.instance().parseXML(getApplicationContext())) {
      FtUtil.log_i(TAG, "onReceive", "SystemInfoService => XML data parsing was failed.");
    }
    this.mSystemInfoView = new SystemInfoView(this);
    this.mActivityManager = ((ActivityManager)getSystemService("activity"));
    this.mWindowManager = ((WindowManager)getSystemService("window"));
    this.mLayoutParams = new WindowManager.LayoutParams(-2, -1, 2005, 24, -3);
    this.mWindowManager.addView(this.mSystemInfoView, this.mLayoutParams);
    DisplaySystemInfo();
    if (this.operator.contains("ctc"))
    {
      if (Support.Feature.getBoolean("SUPPORT_SWITCH_KERNEL_FTM")) {
        ModuleCommon.instance(getApplicationContext()).notifyFactoryMode2Kernel();
      }
      this.mHandlerForTestNV.postDelayed(new Runnable()
      {
        public void run()
        {
          SystemInfoService.this.setupReceiver();
          FactoryTestPhone localFactoryTestPhone = new FactoryTestPhone(SystemInfoService.this.getApplicationContext());
          localFactoryTestPhone.bindSecPhoneService();
          FtUtil.log_i(SystemInfoService.TAG, "bindSecPhoneService()", null);
          localFactoryTestPhone.requestTestNvViewToRil();
        }
      }, 10000L);
    }
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    FtUtil.log_i(TAG, "onDestroy", null);
    this.mSystemInfoView.setVisibility(8);
    this.mWindowManager.removeView(this.mSystemInfoView);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.systeminfo.SystemInfoService
 * JD-Core Version:    0.7.1
 */