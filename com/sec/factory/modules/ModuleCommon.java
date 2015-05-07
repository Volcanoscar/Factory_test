package com.sec.factory.modules;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemProperties;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;
import com.sec.factory.support.Support.Properties;
import java.io.File;
import java.util.List;
import java.util.Locale;

public class ModuleCommon
  extends ModuleObject
{
  private static boolean IS_FACTORY_MODE = false;
  private static ModuleCommon mInstance = null;
  private static PackageManager mPackageManager = null;
  private static final String mSalesCode = SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase();
  final Uri CONTENT_URI_NO_NOTIFICATION = Uri.parse("content://com.sec.android.app.launcher.settings/favorites?notify=false");
  private final long HDCP_KEBOX_SIZE = 640L;
  private final long WIDEVINE_KEBOX_SIZE = 144L;
  private boolean isCPName_IMC = "IMC".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME_CP"));
  private boolean isCPName_SPRD = "SPRD".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME_CP"));
  private boolean isMSM8260A = "MSM8260A".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isMSM8930 = "MSM8930".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isMSM8960 = "MSM8960".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isPegaPrime = "PegaPrime".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isPegasus = "Pegasus".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isSLSI = "S.LSI".equalsIgnoreCase(Support.Feature.getString("CHIPSET_MANUFACTURE"));
  private boolean isU8420 = "U8420".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  
  private ModuleCommon(Context paramContext)
  {
    super(paramContext, "ModuleCommon");
    FtUtil.log_i(this.CLASS_NAME, "ModuleCommon", "Create ModuleCommon");
    if (!new File(Support.Kernel.getFilePath("EFS_FACTORYAPP_ROOT_PATH")).exists())
    {
      FtUtil.log_i(this.CLASS_NAME, "ModuleCommon", "Create EFS_FACTORYAPP_ROOT_PATH directory...");
      if (Support.Kernel.mkDir(Support.Kernel.getFilePath("EFS_FACTORYAPP_ROOT_PATH"))) {
        Support.Kernel.setPermission(Support.Kernel.getFilePath("EFS_FACTORYAPP_ROOT_PATH"), true, false, true, true, true, false);
      }
    }
    if (!new File(Support.Kernel.getFilePath("TA_COUNT")).exists())
    {
      writeTACount("0");
      Support.Kernel.setPermission(Support.Kernel.getFilePath("TA_COUNT"), false, false, true, false, true, false);
      FtUtil.log_d(this.CLASS_NAME, "ModuleCommon", "batt_cable_count is created...");
    }
    if (!new File(Support.Kernel.getFilePath("EARJACK_COUNT")).exists())
    {
      writeEarjackCount("0");
      Support.Kernel.setPermission(Support.Kernel.getFilePath("EARJACK_COUNT"), false, false, true, false, true, false);
      FtUtil.log_d(this.CLASS_NAME, "ModuleCommon", "earjack_count is created...");
    }
  }
  
  private boolean checkTopActivity()
  {
    String str = ((ActivityManager.RunningTaskInfo)((ActivityManager)getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getPackageName();
    FtUtil.log_i(this.CLASS_NAME, "topActivity: ", str);
    if (str.contains("com.sec.factory"))
    {
      FtUtil.log_i(this.CLASS_NAME, "checkTopActivity(): ", "sendBroadcast");
      return true;
    }
    return false;
  }
  
  public static ModuleCommon instance(Context paramContext)
  {
    if (mInstance == null) {
      mInstance = new ModuleCommon(paramContext);
    }
    return mInstance;
  }
  
  private boolean isEnabledApplication(String paramString)
  {
    switch (mPackageManager.getApplicationEnabledSetting(paramString))
    {
    case 2: 
    case 3: 
    default: 
      return false;
    }
    return true;
  }
  
  private boolean isExistApplication(String paramString)
  {
    try
    {
      mPackageManager.getApplicationEnabledSetting(paramString);
      return true;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      FtUtil.log_e(this.CLASS_NAME, "isExistApplication", "Can't find " + paramString);
    }
    return false;
  }
  
  private void setApplicationState(String paramString, int paramInt)
  {
    mPackageManager.setApplicationEnabledSetting(paramString, paramInt, 0);
  }
  
  public void CreateFactoyShortCut()
  {
    FtUtil.log_i(this.CLASS_NAME, "CreateFactoyShortCut", "send intent");
    Intent localIntent = new Intent();
    localIntent.setAction("com.sec.android.app.factorymode.shortcuts");
    localIntent.setClassName("com.sec.android.app.factorymode", "com.sec.android.app.factorymode.FactoryCTRL");
    localIntent.setFlags(268435456);
    localIntent.putExtra("SUBACTION", "CREATE");
    mContext.startActivity(localIntent);
  }
  
  public boolean FactoryShortcutExists()
  {
    ContentResolver localContentResolver = mContext.getContentResolver();
    if ((isVoiceCapable()) && ("CTC".equals(SystemProperties.get("ro.csc.sales_code")))) {
      return false;
    }
    Cursor localCursor = localContentResolver.query(this.CONTENT_URI_NO_NOTIFICATION, null, null, null, null);
    boolean bool;
    if (localCursor != null) {
      while (localCursor.moveToNext())
      {
        String str = localCursor.getString(localCursor.getColumnIndex("title"));
        FtUtil.log_i(this.CLASS_NAME, "FactoryShortcutExists", "title : " + str);
        if ((str != null) && (str.equals("Factory Mode")))
        {
          FtUtil.log_i(this.CLASS_NAME, "FactoryShortcutExists", "Factory Mode Exist");
          bool = true;
          localCursor.close();
        }
      }
    }
    for (;;)
    {
      FtUtil.log_i(this.CLASS_NAME, "FactoryShortcutExists", "returnvalue :" + bool);
      return bool;
      bool = false;
      break;
      bool = false;
    }
  }
  
  public boolean connectedJIG()
  {
    FtUtil.log_i(this.CLASS_NAME, "connectedJIG", "...");
    String str1 = Support.Feature.getString("ANYWAY_JIG_CABLE_TYPE");
    FtUtil.log_i(this.CLASS_NAME, "connectedJIG", "cable_type = " + str1);
    if ("ANYWAY_JIG".equals(str1)) {
      if (Support.Kernel.isExistFileID("ANYWAY_JIG")) {}
    }
    while ((!"ANYWAY_JIG_30PIN".equals(str1)) || (!Support.Kernel.isExistFileID("ANYWAY_JIG_30PIN")))
    {
      return false;
      String str3 = Support.Kernel.read("ANYWAY_JIG");
      FtUtil.log_i(this.CLASS_NAME, "connectedJIG", "value = " + str3 + ", JIG_ON = " + "1C");
      if (str3 != null) {}
      for (String str4 = str3.toUpperCase();; str4 = " ") {
        return String.valueOf("1C").equals(str4);
      }
    }
    String str2 = Support.Kernel.read("ANYWAY_JIG_30PIN");
    FtUtil.log_i(this.CLASS_NAME, "connectedJIG", "value = " + str2 + ", JIG_ON = " + "1");
    return String.valueOf("1").equals(str2);
  }
  
  public boolean disable15Test()
  {
    FtUtil.log_i(this.CLASS_NAME, "15 Test Ready flag", "blocking");
    SharedPreferences.Editor localEditor = mContext.getSharedPreferences("com.sec.android.app.factorymode.15_test_ready_flag", 0).edit();
    localEditor.putBoolean("com.sec.android.app.factorymode.15_test_ready_flag", false);
    localEditor.commit();
    return true;
  }
  
  public void disableApplication(String paramString)
  {
    if (mPackageManager == null) {
      mPackageManager = mContext.getPackageManager();
    }
    if ((isExistApplication(paramString)) && (isEnabledApplication(paramString)))
    {
      FtUtil.log_d(this.CLASS_NAME, "disableApplication", "Package: " + paramString);
      setApplicationState(paramString, 2);
    }
  }
  
  public boolean disableFtClient()
  {
    FtUtil.log_i(this.CLASS_NAME, "disableFtClient", "...");
    SharedPreferences.Editor localEditor = mContext.getSharedPreferences("com.sec.android.app.factorymode.ftclient", 0).edit();
    localEditor.putBoolean("com.sec.android.app.factorymode.ftclient.key", false);
    localEditor.commit();
    return true;
  }
  
  public boolean enable15Test()
  {
    FtUtil.log_i(this.CLASS_NAME, "15 Test Ready flag", "set");
    SharedPreferences.Editor localEditor = mContext.getSharedPreferences("com.sec.android.app.factorymode.15_test_ready_flag", 0).edit();
    localEditor.putBoolean("com.sec.android.app.factorymode.15_test_ready_flag", true);
    localEditor.commit();
    return true;
  }
  
  public void enableApplication(String paramString)
  {
    if (mPackageManager == null) {
      mPackageManager = mContext.getPackageManager();
    }
    if ((isExistApplication(paramString)) && (!isEnabledApplication(paramString)))
    {
      FtUtil.log_d(this.CLASS_NAME, "enableApplication", "Package: " + paramString);
      setApplicationState(paramString, 0);
    }
  }
  
  public boolean enableFtClient()
  {
    FtUtil.log_i(this.CLASS_NAME, "enableFtClient", "...");
    SharedPreferences.Editor localEditor = mContext.getSharedPreferences("com.sec.android.app.factorymode.ftclient", 0).edit();
    localEditor.putBoolean("com.sec.android.app.factorymode.ftclient.key", true);
    localEditor.commit();
    return true;
  }
  
  public int getAutoRotateState()
  {
    int i = Settings.System.getInt(getContentResolver(), "accelerometer_rotation", 2);
    FtUtil.log_i(this.CLASS_NAME, "getAutoRotate", "state=" + i);
    return i;
  }
  
  public String getBinaryType()
  {
    String str = SystemProperties.get("ro.build.type", "Unknown");
    FtUtil.log_i(this.CLASS_NAME, "getBinaryType", "type=" + str);
    return str;
  }
  
  public String getBootVer()
  {
    String str = Support.Properties.get("BOOT_VERSION");
    FtUtil.log_i(this.CLASS_NAME, "getBootVer", "version=" + str);
    return str;
  }
  
  public String getBootloaderVer()
  {
    String str = Support.Properties.get("BOOTLOADER_VERSION");
    FtUtil.log_i(this.CLASS_NAME, "getBootVer", "version=" + str);
    return str;
  }
  
  public String getCPName()
  {
    String str = Support.Properties.get("MAIN_CHIP_NAME_CP");
    FtUtil.log_i(this.CLASS_NAME, "getCPName", "type=" + str);
    return str;
  }
  
  public String getCSCVer()
  {
    String str1 = SystemProperties.get("ro.product.model", "Unknown").toLowerCase(Locale.ENGLISH);
    String str2 = Support.Properties.get("CSC_VERSION");
    if (str1.contains("m3")) {
      str2 = str2.substring(5, str2.length());
    }
    FtUtil.log_i(this.CLASS_NAME, "getCSCVer", "version=" + str2);
    return str2;
  }
  
  public String getCalDate()
  {
    String str = Support.Kernel.read("EFS_CALDATE_PATH");
    FtUtil.log_i(this.CLASS_NAME, "getCalDate", "getCalDate=" + str);
    return str;
  }
  
  public String getContentsVer()
  {
    String str = Support.Properties.get("CONTENTS_VERSION");
    FtUtil.log_i(this.CLASS_NAME, "getContentsVer", "version=" + str);
    return str;
  }
  
  public boolean getFactoryMode()
  {
    return IS_FACTORY_MODE;
  }
  
  public boolean getFtClientEnableState()
  {
    boolean bool = true;
    SharedPreferences localSharedPreferences = mContext.getSharedPreferences("com.sec.android.app.factorymode.ftclient", 0);
    if (localSharedPreferences != null) {
      bool = localSharedPreferences.getBoolean("com.sec.android.app.factorymode.ftclient.key", bool);
    }
    FtUtil.log_i(this.CLASS_NAME, "isFactoryModeCheck", "result : " + bool);
    return bool;
  }
  
  public String getHWRevision()
  {
    String str = Support.Properties.get("HW_REVISION");
    if (str.equalsIgnoreCase("Unknown")) {
      str = SystemProperties.get("ro.revision", "Unknown");
    }
    FtUtil.log_i(this.CLASS_NAME, "getHWRevision", "rev=" + str);
    return str;
  }
  
  public String getHWVer()
  {
    if ((FtUtil.isFactoryAppAPO()) && (!this.isMSM8960) && (!this.isMSM8930) && (!this.isMSM8260A) && (!this.isPegasus) && (!this.isPegaPrime) && (!this.isCPName_IMC) && (!this.isU8420) && (!this.isCPName_SPRD) && (Support.Feature.getBoolean("HW_VER_EFS"))) {}
    for (String str = Support.Kernel.read("EFS_HW_PATH");; str = Support.Properties.get("HW_VERSION"))
    {
      FtUtil.log_i(this.CLASS_NAME, "getHWver", "version=" + str);
      return str;
    }
  }
  
  public int getKeyLockState()
  {
    return Settings.System.getInt(getContentResolver(), "SHOULD_SHUT_DOWN", -1);
  }
  
  public String getLTEVer()
  {
    String str = Support.Properties.get("LTE_VERSION");
    FtUtil.log_i(this.CLASS_NAME, "getLTEVer", "version=" + str);
    return str;
  }
  
  public String getMainChipName()
  {
    if (Support.Feature.getBoolean("STANDARD_JB_SYSFS", false)) {}
    String str2;
    for (String str1 = Support.Properties.get("MAIN_CHIP_NAME_AP_REAL");; str1 = Support.Properties.get("MAIN_CHIP_NAME_AP"))
    {
      str2 = "";
      if (FtUtil.isFactoryAppAPO()) {
        str2 = Support.Properties.get("MAIN_CHIP_NAME_CP");
      }
      if (!str1.equals(str2)) {
        break;
      }
      String str6 = str1;
      FtUtil.log_i(this.CLASS_NAME, "getMainChipName", "name=" + str6);
      return str6;
    }
    if (isConnectionModeNone())
    {
      String str5 = str1;
      FtUtil.log_i(this.CLASS_NAME, "getMainChipName", "name=" + str5);
      return str5;
    }
    String str3 = SystemProperties.get("ro.product.model", "Unknown");
    if ((str3.equals("GT-P5100")) || (str3.equals("GT-P3100"))) {}
    for (String str4 = str1;; str4 = str1 + "," + str2)
    {
      FtUtil.log_i(this.CLASS_NAME, "getMainChipName", "name=" + str4);
      return str4;
    }
  }
  
  public String getMainSWVer()
  {
    String str1 = SystemProperties.get("ril.sw_ver", "Unknown");
    if (SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase().equals("CTC"))
    {
      String str2 = SystemProperties.get("gsm.version.baseband", "Unknown");
      FtUtil.log_i(this.CLASS_NAME, "getMainSWVer", "CTCversion=" + str2);
      return str2;
    }
    FtUtil.log_i(this.CLASS_NAME, "getMainSWVer", "version=" + str1);
    return str1;
  }
  
  public String getModelName()
  {
    String str = Support.Properties.get("PRODUCT_NAME");
    FtUtil.log_i(this.CLASS_NAME, "getModelName", "name=" + str);
    if ((str != null) && (str.contains("SAMSUNG-"))) {
      str = str.replaceAll("SAMSUNG-", "");
    }
    return str;
  }
  
  public String getPDAVer()
  {
    String str1 = Support.Properties.get("PDA_VERSION");
    String str2 = Support.Properties.get("CONTENTS_VERSION");
    if ((!str1.equals(str2)) && (mSalesCode.equals("VZW"))) {}
    for (String str3 = str2;; str3 = str1)
    {
      String str4 = str3;
      FtUtil.log_i(this.CLASS_NAME, "getPDAVer", "version=" + str4);
      return str4;
    }
  }
  
  public String getPhone2Ver()
  {
    String str1 = SystemProperties.get("ro.product.model", "Unknown").toLowerCase(Locale.ENGLISH);
    String str2 = SystemProperties.get("ril.sw_ver", "Unknown");
    if (str1.contains("m3")) {
      str2 = str2.substring(5, str2.length());
    }
    if ("CTC".equals(SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase()))
    {
      String str3 = SystemProperties.get("ril.sw_ver", "Unknown");
      FtUtil.log_i(this.CLASS_NAME, "getPhone2Ver", "CTCversion=" + str3);
      return str3;
    }
    FtUtil.log_i(this.CLASS_NAME, "getPhone2Ver", "version=" + str2);
    return str2;
  }
  
  public String getPhoneVer()
  {
    String str1 = SystemProperties.get("ro.product.model", "Unknown").toLowerCase(Locale.ENGLISH);
    String str2 = SystemProperties.get("ril.sw_ver", "Unknown");
    if (str1.contains("m3")) {
      str2 = str2.substring(5, str2.length());
    }
    if (SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase().equals("CTC"))
    {
      String str3 = SystemProperties.get("gsm.version.baseband", "Unknown");
      FtUtil.log_i(this.CLASS_NAME, "getPhoneVer", "CTCversion=" + str3);
      return str3;
    }
    FtUtil.log_i(this.CLASS_NAME, "getPhoneVer", "version=" + str2);
    return str2;
  }
  
  public String getStandardVer()
  {
    String str = Support.Feature.getString("FACTORY_TEST_COMMAND");
    FtUtil.log_i(this.CLASS_NAME, "getStandardVer", "version=" + str);
    return str;
  }
  
  public String getUartPath()
  {
    String str = Support.Kernel.read("UART_SELECT");
    FtUtil.log_i(this.CLASS_NAME, "getUartPath", "path=" + str);
    return str;
  }
  
  public void go15mode()
  {
    FtUtil.log_i(this.CLASS_NAME, "go15mode", "Launch 15 test Activity");
    Intent localIntent = new Intent();
    localIntent.setClassName("com.sec.factory", "com.sec.factory.app.factorytest.FactoryTestMain");
    localIntent.setFlags(872415232);
    startActivity(localIntent);
  }
  
  public void goIdle()
  {
    FtUtil.log_i(this.CLASS_NAME, "goIdle", "Launch Home Activity");
    if (checkTopActivity())
    {
      Intent localIntent1 = new Intent();
      localIntent1.setAction("com.sec.samsung.STOP_FACTORY_TEST");
      sendBroadcast(localIntent1);
    }
    Intent localIntent2 = new Intent();
    localIntent2.setAction("android.intent.action.MAIN");
    localIntent2.addCategory("android.intent.category.HOME");
    localIntent2.addFlags(32768);
    localIntent2.setFlags(335544320);
    startActivity(localIntent2);
  }
  
  public void goLcdtest()
  {
    FtUtil.log_i(this.CLASS_NAME, "goLcdtest", "Launch Lcd test Activity");
    Intent localIntent = new Intent();
    localIntent.setClassName("com.sec.android.app.lcdtest", "com.sec.android.app.lcdtest.Lcdtest");
    localIntent.setFlags(335544320);
    startActivity(localIntent);
  }
  
  public String hdcpCheck(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "NG";
    case 1: 
      return Support.Kernel.read("HDCP_CHECK_2_0");
    case 2: 
    case 3: 
      long l2 = Support.Kernel.getFileLength("HDCP_CHECK_1_3_KEYBOX");
      FtUtil.log_d(this.CLASS_NAME, "checkHDCPKey", "length : " + l2);
      if (l2 == 640L) {
        return "OK";
      }
      return "NG";
    }
    long l1 = Support.Kernel.getFileLength("HDCP_CHECK_WIDEVINE_KEYBOX");
    FtUtil.log_d(this.CLASS_NAME, "checkWidevineKey", "length : " + l1);
    if (l1 == 144L) {
      return "OK";
    }
    return "NG";
  }
  
  public String hdcpReadSN()
  {
    return Support.Properties.get("HDCP_SN");
  }
  
  public boolean is15TestEnable()
  {
    boolean bool = true;
    SharedPreferences localSharedPreferences = mContext.getSharedPreferences("com.sec.android.app.factorymode.15_test_ready_flag", 0);
    if (localSharedPreferences != null) {
      bool = localSharedPreferences.getBoolean("com.sec.android.app.factorymode.15_test_ready_flag", bool);
    }
    FtUtil.log_i(this.CLASS_NAME, "is15TestEnable", "TEST_READY_FLAG : " + bool);
    return bool;
  }
  
  public boolean isConnectionModeNone()
  {
    String str = Support.Feature.getString("MODEL_COMMUNICATION_MODE");
    FtUtil.log_i(this.CLASS_NAME, "isConnectionModeNone", "mConnectionMode = " + str);
    return "none".equals(str);
  }
  
  public boolean isFactoryMode()
  {
    String str = SystemProperties.get("ro.product.model", "NONE").trim().toUpperCase();
    FtUtil.log_i(this.CLASS_NAME, "isFactoryMode", "mProductModel:" + str);
    if (str.contains("I9070")) {
      FtUtil.log_i(this.CLASS_NAME, "isFactoryMode", "isFactoryMode GT-I9070 =false:mode");
    }
    while ((readFactoryMode() != null) && (readFactoryMode().contains("ON"))) {
      return false;
    }
    FtUtil.log_i(this.CLASS_NAME, "isFactoryMode", "isFactoryMode=true:mode");
    return true;
  }
  
  public boolean isFactorySim()
  {
    String str = ((TelephonyManager)getApplicationContext().getSystemService("phone")).getSubscriberId();
    if ((str != null) && (str.equals("999999999999999")))
    {
      FtUtil.log_i(this.CLASS_NAME, "isFactorySim", "isFactorySim == true");
      return true;
    }
    FtUtil.log_i(this.CLASS_NAME, "isFactorySim", "isFactorySim == false");
    FtUtil.log_i(this.CLASS_NAME, "isFactorySim", "isFactorySim=" + getFactoryMode() + "by Keystring");
    return getFactoryMode();
  }
  
  public boolean isFirstBoot()
  {
    boolean bool = true;
    SharedPreferences localSharedPreferences = mContext.getSharedPreferences("com.sec.android.app.factorymode.first_boot_flag", 0);
    if (localSharedPreferences != null) {
      bool = localSharedPreferences.getBoolean("factorymode.shortcut.skipped", bool);
    }
    FtUtil.log_i(this.CLASS_NAME, "isFirstBoot", "before : " + bool);
    return bool;
  }
  
  public boolean isVoiceCapable()
  {
    boolean bool = true;
    if (mContext != null) {
      bool = mContext.getResources().getBoolean(17891370);
    }
    FtUtil.log_i(this.CLASS_NAME, "isVoiceCapable", "=" + bool);
    return bool;
  }
  
  public void notifyFactoryMode2Kernel()
  {
    if ((isFactoryMode()) || (isFactorySim())) {}
    for (String str = "1";; str = "0")
    {
      boolean bool = Support.Kernel.write("SWITCH_KERNEL_FTM", str);
      FtUtil.log_d(this.CLASS_NAME, "notifyFactoryMode2Kernel", "mode=" + str + ", result=" + bool);
      return;
    }
  }
  
  public String readBatteryType()
  {
    String str = Support.Kernel.read("BATTERY_TYPE");
    FtUtil.log_i(this.CLASS_NAME, "readBatteryType", "type=" + str);
    return str;
  }
  
  public String readBatteryVF()
  {
    String str1 = Support.Kernel.read("BATTERY_ADC_NOTSUPPORT");
    String str2 = Support.Kernel.read("BATTERY_TEMP_ADC");
    if (Support.Feature.getBoolean("SUPPORT_BATTERY_ADC", false))
    {
      FtUtil.log_i(this.CLASS_NAME, "readBatteryVF", "type=" + str1);
      return str1;
    }
    FtUtil.log_i(this.CLASS_NAME, "readBatteryVF", "type=" + str2);
    return str2;
  }
  
  public String readCameraFrontFWver()
  {
    String str = Support.Kernel.read("CAMERA_FRONT_FW_VER");
    FtUtil.log_i(this.CLASS_NAME, "readCameraFrontFWver", "type=" + str);
    return str;
  }
  
  public String readCameraFrontType()
  {
    String str = Support.Kernel.read("CAMERA_FRONT_TYPE");
    FtUtil.log_i(this.CLASS_NAME, "readCameraFrontType", "type=" + str);
    return str;
  }
  
  public String readCameraRearFWver()
  {
    String str = Support.Kernel.read("CAMERA_REAR_FW_VER");
    FtUtil.log_i(this.CLASS_NAME, "readCameraRearFWver", "type=" + str);
    return str;
  }
  
  public String readCameraRearType()
  {
    String str = Support.Kernel.read("CAMERA_REAR_TYPE");
    FtUtil.log_i(this.CLASS_NAME, "readCameraRearType", "type=" + str);
    return str;
  }
  
  public String readCpuOnline()
  {
    String str = Support.Kernel.read("CPU_ONLINE");
    FtUtil.log_i(this.CLASS_NAME, "readCpuOnline", "type=" + str);
    return str;
  }
  
  public String readFactoryMode()
  {
    String str = Support.Kernel.read("FACTORY_MODE");
    FtUtil.log_i(this.CLASS_NAME, "readFactoryMode", "mode: " + str);
    return str;
  }
  
  public String readHDMITestResult()
  {
    String str = Support.Kernel.read("HDMI_TEST_RESULT");
    FtUtil.log_i(this.CLASS_NAME, "readHDMICheck", "result" + str);
    return str;
  }
  
  public String readKeyStringBlock()
  {
    String str = Support.Kernel.read("KEYSTRING_BLOCK");
    FtUtil.log_i(this.CLASS_NAME, "readKeyStringBlock", "block=" + str);
    return str;
  }
  
  public String readLcdType()
  {
    String str = Support.Kernel.read("LCD_TYPE");
    FtUtil.log_i(this.CLASS_NAME, "readLcdType", "type=" + str);
    return str;
  }
  
  public String readPrePay()
  {
    String str = Support.Kernel.read("PRE_PAY");
    FtUtil.log_i(this.CLASS_NAME, "readPrePay", "mode: " + str);
    return str;
  }
  
  public String readProxiADC()
  {
    String str = Support.Kernel.read("PROXI_SENSOR_ADC");
    FtUtil.log_i(this.CLASS_NAME, "readProxiADC", "type=" + str);
    return str;
  }
  
  public String readProxiOffset()
  {
    String str = Support.Kernel.read("PROXI_SENSOR_OFFSET").split(",")[0];
    FtUtil.log_i(this.CLASS_NAME, "readProxiOffset", "type=" + str);
    return str;
  }
  
  public String readmBaroDelta()
  {
    String str = Support.Kernel.read("BAROMETE_DELTA");
    FtUtil.log_i(this.CLASS_NAME, "readmBaroDelta", "delta=" + str);
    return str;
  }
  
  public void setAutoRotate(int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "setAutoRotate", "state=" + paramInt);
    Settings.System.putInt(getContentResolver(), "accelerometer_rotation", paramInt);
  }
  
  public boolean setCalDate(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "setCalDate", "setCalDate=" + paramString);
    return Support.Kernel.write("EFS_CALDATE_PATH", paramString);
  }
  
  public void setFactoryMode()
  {
    IS_FACTORY_MODE = true;
  }
  
  public boolean setFirstBoot()
  {
    FtUtil.log_i(this.CLASS_NAME, "setFirstBoot", "set");
    SharedPreferences.Editor localEditor = mContext.getSharedPreferences("com.sec.android.app.factorymode.first_boot_flag", 0).edit();
    localEditor.putBoolean("factorymode.shortcut.skipped", false);
    localEditor.commit();
    return true;
  }
  
  public boolean setHDMIPatternSwitch(String paramString)
  {
    boolean bool = Support.Kernel.write("HDMI_PATTERN_SWITCH", paramString);
    FtUtil.log_i(this.CLASS_NAME, "setHDMIPatternSwitch", "result=" + bool);
    return bool;
  }
  
  public boolean setHWVer(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "setHWVer", "version=" + paramString);
    String str = paramString.replaceAll("\"", "").replaceAll("~", "");
    FtUtil.log_i(this.CLASS_NAME, "setHWVer", "write version=" + str);
    return Support.Kernel.write("EFS_HW_PATH", str);
  }
  
  public void setKeyLock(int paramInt)
  {
    Settings.System.putInt(getContentResolver(), "SHOULD_SHUT_DOWN", paramInt);
  }
  
  public void setSwitchFactoryState()
  {
    if ((FtUtil.isFactoryAppAPO()) && (this.isSLSI))
    {
      FtUtil.log_i(this.CLASS_NAME, "setSwitchFactoryState", "SwitchFactory= + FACTORY_START");
      Support.Kernel.write("SWITCH_FACTORY", "FACTORY_START");
      return;
    }
    FtUtil.log_i(this.CLASS_NAME, "setSwitchFactoryState", "Not SLSI model");
  }
  
  public boolean setUartPath(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "setUartPath", "path=" + paramString);
    return Support.Kernel.write("UART_SELECT", paramString);
  }
  
  public void sleepThread(int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "sleepThread", "Thread sleep during : " + paramInt + "ms...");
    long l = paramInt;
    try
    {
      Thread.sleep(l);
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      FtUtil.log_e(localInterruptedException);
    }
  }
  
  public void writeEarjackCount(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "writeEarjackCount", "count=" + paramString);
    Support.Kernel.write("EARJACK_COUNT", paramString);
  }
  
  public void writeFactoryMode(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "writeFactoryMode", "mode=" + paramString);
    Support.Kernel.write("FACTORY_MODE", paramString);
  }
  
  public void writeKeyStringBlock(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "writeKeyStringBlock", "KeyStringBlock=" + paramString);
    Support.Kernel.write("KEYSTRING_BLOCK", paramString);
  }
  
  public void writePrePay(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "writePrePay", "mode=" + paramString);
    Support.Kernel.write("PRE_PAY", paramString);
  }
  
  public void writeTACount(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "writeTACount", "count=" + paramString);
    Support.Kernel.write("TA_COUNT", paramString);
  }
  
  public void writemBaroDelta(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "writemBaroDelta", "BaroDelta=" + paramString);
    Support.Kernel.write("BAROMETE_DELTA", paramString);
    Support.Kernel.write("BAROMETE_CALIBRATION", paramString);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.ModuleCommon
 * JD-Core Version:    0.7.1
 */