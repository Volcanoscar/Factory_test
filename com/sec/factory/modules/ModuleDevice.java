package com.sec.factory.modules;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.StatFs;
import android.os.SystemProperties;
import android.os.SystemVibrator;
import android.os.storage.IMountService;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.plugin.PlugInServiceManager;
import android.plugin.dsds.PlugInDsdsService;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;
import com.sec.factory.support.Support.Properties;
import com.sec.factory.support.Support.Spec;
import com.sec.factory.support.Support.TestCase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

public class ModuleDevice
  extends ModuleObject
{
  private static final byte[] OTG_MUIC_OFF = { 49, 0 };
  private static final byte[] OTG_MUIC_ON;
  private static final byte[] OTG_TEST_OFF;
  private static final byte[] OTG_TEST_ON;
  private static final String[] STORAGE_PATH;
  public static final int TSP_NODE_X = Support.Spec.getInt("TSP_NODE_COUNT_WIDTH");
  public static final int TSP_NODE_Y = Support.Spec.getInt("TSP_NODE_COUNT_HEIGHT");
  private static ModuleDevice mInstance = null;
  private int MAGNITUDE_MAX = 10000;
  private final String[] USB_MODE = { "SUSB", "MTP", "MSTR", "AOC" };
  private boolean isMSM8260A = "MSM8260A".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isMSM8930 = "MSM8930".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isMSM8960 = "MSM8960".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isPegaPrime = "PegaPrime".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isPegasus = "Pegasus".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isPegasusPrime = "PegasusPrime".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private ActivityManager mAm;
  private boolean mIsMountedExternalStorage = false;
  private boolean mIsMountedUsbStorage = false;
  private boolean mIsMountedUsbStorageB = false;
  private boolean mIsVibrating = false;
  private BroadcastReceiver mMediaStateReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "mMediaStateReceiver.onReceive", "<ACTION_MEDIA_INTENT> action : " + str);
      if (str.equals("android.intent.action.MEDIA_MOUNTED"))
      {
        boolean bool4 = ModuleDevice.this.mIsMountedExternalStorage;
        boolean bool5 = ModuleDevice.this.mIsMountedUsbStorage;
        boolean bool6 = ModuleDevice.this.mIsMountedUsbStorageB;
        ModuleDevice.this.updateStorageMountState();
        FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "mMediaStateReceiver.onReceive", "<ACTION_MEDIA_MOUNTED> wasMountedExternalStorage : " + bool4);
        FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "mMediaStateReceiver.onReceive", "<ACTION_MEDIA_MOUNTED> mIsMountedExternalStorage : " + ModuleDevice.this.mIsMountedExternalStorage);
        FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "mMediaStateReceiver.onReceive", "<ACTION_MEDIA_MOUNTED> wasMountedUsbStorage : " + bool5);
        FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "mMediaStateReceiver.onReceive", "<ACTION_MEDIA_MOUNTED> mIsMountedUsbStorage : " + ModuleDevice.this.mIsMountedUsbStorage);
        FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "mMediaStateReceiver.onReceive", "<ACTION_MEDIA_MOUNTED> wasMountedUsbStorage_B : " + bool6);
        FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "mMediaStateReceiver. onReceive", "<ACTION_MEDIA_MOUNTED> mIsMountedUsbStorage_B : " + ModuleDevice.this.mIsMountedUsbStorageB);
        if ((!bool4) && (ModuleDevice.this.mIsMountedExternalStorage == true)) {
          ModuleDevice.this.sendBroadcast(new Intent("com.sec.factory.action.ACTION_EXTERNAL_STORAGE_MOUNTED"));
        }
        if ((!bool5) && (ModuleDevice.this.mIsMountedUsbStorage == true)) {
          ModuleDevice.this.sendBroadcast(new Intent("com.sec.factory.action.ACTION_USB_STORAGE_MOUNTED"));
        }
        if ((!bool6) && (ModuleDevice.this.mIsMountedUsbStorageB == true)) {
          ModuleDevice.this.sendBroadcast(new Intent("com.sec.factory.action.ACTION_USB_STORAGE_MOUNTED"));
        }
      }
      boolean bool3;
      do
      {
        do
        {
          return;
        } while (!str.equals("android.intent.action.MEDIA_UNMOUNTED"));
        boolean bool1 = ModuleDevice.this.mIsMountedExternalStorage;
        boolean bool2 = ModuleDevice.this.mIsMountedUsbStorage;
        bool3 = ModuleDevice.this.mIsMountedUsbStorageB;
        ModuleDevice.this.updateStorageMountState();
        FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "mMediaStateReceiver.onReceive", "<ACTION_MEDIA_UNMOUNTED> wasMountedExternalStorage : " + bool1);
        FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "mMediaStateReceiver.onReceive", "<ACTION_MEDIA_UNMOUNTED> mIsMountedExternalStorage : " + ModuleDevice.this.mIsMountedExternalStorage);
        FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "mMediaStateReceiver.onReceive", "<ACTION_MEDIA_UNMOUNTED> wasMountedUsbStorage : " + bool2);
        FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "mMediaStateReceiver.onReceive", "<ACTION_MEDIA_UNMOUNTED> mIsMountedUsbStorage : " + ModuleDevice.this.mIsMountedUsbStorage);
        FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "mMediaStateReceiver.onReceive", "<ACTION_MEDIA_MOUNTED> wasMountedUsbStorage_B : " + bool3);
        FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "mMediaStateReceiver.onReceive", "<ACTION_MEDIA_MOUNTED> mIsMountedUsbStorage_B : " + ModuleDevice.this.mIsMountedUsbStorageB);
        if ((bool1 == true) && (!ModuleDevice.this.mIsMountedExternalStorage)) {
          ModuleDevice.this.sendBroadcast(new Intent("com.sec.factory.action.ACTION_EXTERNAL_STORAGE_UNMOUNTED"));
        }
        if ((bool2 == true) && (!ModuleDevice.this.mIsMountedUsbStorage)) {
          ModuleDevice.this.sendBroadcast(new Intent("com.sec.factory.action.ACTION_USB_STORAGE_UNMOUNTED"));
        }
      } while ((bool3 != true) || (ModuleDevice.this.mIsMountedUsbStorageB));
      ModuleDevice.this.sendBroadcast(new Intent("com.sec.factory.action.ACTION_USB_STORAGE_UNMOUNTED"));
    }
  };
  private ActivityManager.MemoryInfo mMemInfo = new ActivityManager.MemoryInfo();
  private IMountService mMountService;
  private StorageManager mStorageManager = null;
  private SystemVibrator mVibrator;
  
  static
  {
    STORAGE_PATH = new String[] { "/mnt/sdcard", "/mnt/extSdCard", "/system" };
    OTG_TEST_ON = new byte[] { 79, 78, 0 };
    OTG_TEST_OFF = new byte[] { 79, 70, 70, 0 };
    OTG_MUIC_ON = new byte[] { 48, 0 };
  }
  
  private ModuleDevice(Context paramContext)
  {
    super(paramContext, "ModuleDevice");
    FtUtil.log_i(this.CLASS_NAME, "ModuleDevice", "Create ModuleDevice");
    this.mVibrator = ((SystemVibrator)getSystemService("vibrator"));
    this.mStorageManager = ((StorageManager)getSystemService("storage"));
    this.mAm = ((ActivityManager)getSystemService("activity"));
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
    localIntentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
    localIntentFilter.addDataScheme("file");
    registerReceiver(this.mMediaStateReceiver, localIntentFilter);
    FtUtil.log_i(this.CLASS_NAME, "ModuleDevice", "registerReceiver OK");
    updateStorageMountState();
  }
  
  /* Error */
  private IMountService getMountService()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 230	com/sec/factory/modules/ModuleDevice:mMountService	Landroid/os/storage/IMountService;
    //   6: ifnonnull +21 -> 27
    //   9: ldc 232
    //   11: invokestatic 238	android/os/ServiceManager:getService	(Ljava/lang/String;)Landroid/os/IBinder;
    //   14: astore_3
    //   15: aload_3
    //   16: ifnull +20 -> 36
    //   19: aload_0
    //   20: aload_3
    //   21: invokestatic 244	android/os/storage/IMountService$Stub:asInterface	(Landroid/os/IBinder;)Landroid/os/storage/IMountService;
    //   24: putfield 230	com/sec/factory/modules/ModuleDevice:mMountService	Landroid/os/storage/IMountService;
    //   27: aload_0
    //   28: getfield 230	com/sec/factory/modules/ModuleDevice:mMountService	Landroid/os/storage/IMountService;
    //   31: astore_2
    //   32: aload_0
    //   33: monitorexit
    //   34: aload_2
    //   35: areturn
    //   36: aload_0
    //   37: getfield 165	com/sec/factory/modules/ModuleDevice:CLASS_NAME	Ljava/lang/String;
    //   40: ldc 245
    //   42: ldc 247
    //   44: invokestatic 250	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   47: goto -20 -> 27
    //   50: astore_1
    //   51: aload_0
    //   52: monitorexit
    //   53: aload_1
    //   54: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	55	0	this	ModuleDevice
    //   50	4	1	localObject	Object
    //   31	4	2	localIMountService	IMountService
    //   14	7	3	localIBinder	android.os.IBinder
    // Exception table:
    //   from	to	target	type
    //   2	15	50	finally
    //   19	27	50	finally
    //   27	32	50	finally
    //   36	47	50	finally
  }
  
  public static ModuleDevice instance(Context paramContext)
  {
    if (mInstance == null) {
      mInstance = new ModuleDevice(paramContext);
    }
    return mInstance;
  }
  
  private boolean isEpenChecksumPass()
  {
    Support.Kernel.write("EPEN_CHECKSUM_CHECK", "1");
    try
    {
      Thread.sleep(700L);
      String str = Support.Kernel.read("EPEN_CHECKSUM_RESULT");
      FtUtil.log_i(this.CLASS_NAME, "isEpenChecksumPass", "result=" + str);
      if (str.equalsIgnoreCase("PASS")) {
        return true;
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;)
      {
        localInterruptedException.printStackTrace();
      }
    }
    return false;
  }
  
  private String startTSPTest(byte[] paramArrayOfByte, int paramInt)
  {
    if (Support.Kernel.write("TSP_COMMAND_CMD", paramArrayOfByte))
    {
      String str1 = Support.Kernel.read("TSP_COMMAND_STATUS");
      String str2 = Support.Kernel.read("TSP_COMMAND_RESULT");
      if (str1 == null)
      {
        FtUtil.log_e(this.CLASS_NAME, "startTSPTest", "Fail - Status Read => status == null");
        return "NG";
      }
      if (str1.equals("OK"))
      {
        if (str2 == null)
        {
          FtUtil.log_e(this.CLASS_NAME, "startTSPTest", "Fail - Result Read =>1 result == null");
          return "NG";
        }
        FtUtil.log_e(this.CLASS_NAME, "startTSPTest", "result : " + str2);
        return str2.substring(paramInt + 1, str2.length());
      }
      if (str1.equals("NOT_APPLICABLE"))
      {
        FtUtil.log_e(this.CLASS_NAME, "startTSPTest", "N/A- Status : " + str1);
        FtUtil.log_e(this.CLASS_NAME, "startTSPTest", "N/A - result : " + str2);
        if (str2 == null)
        {
          FtUtil.log_e(this.CLASS_NAME, "startTSPTest", "Fail - Result Read =>2 result == null");
          return "NG";
        }
        return str2.substring(paramInt + 1, str2.length());
      }
      FtUtil.log_e(this.CLASS_NAME, "startTSPTest", "Fail - Status : " + str1);
      FtUtil.log_e(this.CLASS_NAME, "startTSPTest", "Fail - result : " + str2);
      return "NG";
    }
    FtUtil.log_e(this.CLASS_NAME, "startTSPTest", "Fail - Command Write");
    return "NG";
  }
  
  private void updateStorageMountState()
  {
    this.mIsMountedExternalStorage = isMountedStorage(1);
    this.mIsMountedUsbStorage = isMountedStorage(2);
    this.mIsMountedUsbStorageB = isMountedStorage(3);
  }
  
  public String check_USB_Port(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "check_USB_Port", "Start USB " + paramString + " Port Check Test");
    String str;
    if (paramString.equals("1"))
    {
      str = Support.Kernel.read("USB_1ST_PORT_CHECK");
      if (str == null) {
        str = Support.Kernel.read("USB_1ST_PORT_SUPER_CHECK");
      }
    }
    for (;;)
    {
      FtUtil.log_i(this.CLASS_NAME, "check_USB_Port", "Start USB " + paramString + " Port Check : " + str);
      if (str != null) {
        break;
      }
      return "NG_NG";
      boolean bool = paramString.equals("2");
      str = null;
      if (bool)
      {
        str = Support.Kernel.read("USB_2ST_PORT_CHECK");
        if (str == null) {
          str = Support.Kernel.read("USB_2ST_PORT_SUPER_CHECK");
        }
      }
    }
    return "OK_" + str.substring(0, 3);
  }
  
  public boolean controlIRLED(String paramString)
    throws IOException
  {
    boolean bool1 = Support.TestCase.getEnabled("IS_IRLED_TEST_SPLIT_COMMAND");
    boolean bool2 = Support.Kernel.isExistFileID("IR_LED_SEND_TEST");
    boolean bool3 = Support.Kernel.isExistFileID("IR_LED_SEND");
    FtUtil.log_e(this.CLASS_NAME, "controlIRLED", "isExistONOFF : " + bool2 + " , isExistCTRL : " + bool3 + " , isSplitCmd : " + bool1);
    FtUtil.log_e(this.CLASS_NAME, "controlIRLED", "control : " + paramString);
    if (bool2) {
      Support.Kernel.write("IR_LED_SEND_TEST", "38400,10");
    }
    FileOutputStream localFileOutputStream;
    boolean bool4;
    if (bool3 == true) {
      if (bool1)
      {
        localFileOutputStream = new FileOutputStream("IR_LED_SEND");
        String[] arrayOfString = paramString.split(",");
        int i = 0;
        try
        {
          while (i < arrayOfString.length)
          {
            localFileOutputStream.write(arrayOfString[i].getBytes());
            i++;
          }
          localFileOutputStream.write("0".getBytes());
        }
        catch (Exception localException)
        {
          for (;;)
          {
            localException.printStackTrace();
            localFileOutputStream.close();
          }
        }
        finally
        {
          localFileOutputStream.close();
        }
        bool4 = false;
      }
    }
    for (;;)
    {
      if (bool2) {
        Support.Kernel.write("IR_LED_SEND_TEST", "38400,5");
      }
      FtUtil.log_e(this.CLASS_NAME, "controlIRLED", "testResult : " + bool4);
      return bool4;
      bool4 = Support.Kernel.write("IR_LED_SEND", paramString.getBytes());
      continue;
      bool4 = false;
    }
  }
  
  protected void finalize()
    throws Throwable
  {
    FtUtil.log_i(this.CLASS_NAME, "ModuleDevice", "finalize ModuleDevice");
    unregisterReceiver(this.mMediaStateReceiver);
    super.finalize();
  }
  
  public boolean firmwareDownload(int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "firmwareDownload", "module=" + paramInt);
    String str = null;
    switch (paramInt)
    {
    }
    for (;;)
    {
      return Support.Kernel.write(str, "S");
      str = "TSP_FIRMWARE_UPDATE";
      continue;
      str = "TSK_FIRMWARE_UPDATE";
    }
  }
  
  public long getAvailableSize(int paramInt1, int paramInt2)
  {
    StatFs localStatFs = new StatFs(STORAGE_PATH[paramInt1]);
    return localStatFs.getAvailableBlocks() * localStatFs.getBlockSize() / paramInt2;
  }
  
  public long getInnerMemoryDevSize(int paramInt)
  {
    return 512L * Long.parseLong(Support.Kernel.read("IMEM_DEVICE_SIZE")) / paramInt;
  }
  
  public long getInnerMemorySize()
  {
    StatFs localStatFs = new StatFs(Environment.getExternalStorageDirectory().toString());
    return localStatFs.getBlockCount() * localStatFs.getBlockSize() / 1048576L;
  }
  
  public long getSize(int paramInt1, int paramInt2)
  {
    StatFs localStatFs = new StatFs(STORAGE_PATH[paramInt1]);
    return localStatFs.getBlockCount() * localStatFs.getBlockSize() / paramInt2;
  }
  
  public int getSpeakerCount()
  {
    return Support.Feature.getInt("SPEAKER_COUNT");
  }
  
  public String getStoragePath(int paramInt)
  {
    StorageVolume[] arrayOfStorageVolume = this.mStorageManager.getVolumeList();
    if (isMountedStorage(paramInt) == true)
    {
      String str = arrayOfStorageVolume[paramInt].getPath();
      FtUtil.log_i(this.CLASS_NAME, "getStorageParh", "Storage path : " + str);
      return str;
    }
    FtUtil.log_i(this.CLASS_NAME, "getStorageParh", "Storage path : null");
    return null;
  }
  
  public String getTotalMemory()
  {
    String[] arrayOfString = { "MemTotal:", "MemFree:", "Buffers:", "Cached:", "Active:", "Inactive:", "AnonPages:", "Mapped:", "Slab:", "SReclaimable:", "SUnreclaim:", "PageTables:" };
    long[] arrayOfLong = new long[arrayOfString.length];
    Process.readProcLines("/proc/meminfo", arrayOfString, arrayOfLong);
    long l = arrayOfLong[0] / 1024L;
    FtUtil.log_i(this.CLASS_NAME, "getTotalMemory", "[Main]TotalRAM Size : " + l + "MB");
    if (l > 1024L) {
      return "2";
    }
    return "1";
  }
  
  public String getUSBMode()
  {
    return this.USB_MODE[Settings.Secure.getInt(getContentResolver(), "usb_setting_mode", 0)];
  }
  
  public String int_extMEMOSize(int paramInt)
  {
    StatFs localStatFs = new StatFs(this.mStorageManager.getVolumeList()[paramInt].getPath());
    long l1 = localStatFs.getBlockCount() * localStatFs.getBlockSize() / 1024L;
    long l2 = localStatFs.getFreeBlocks() * localStatFs.getBlockSize() / 1024L;
    long l3 = l1 - l2;
    FtUtil.log_i(this.CLASS_NAME, "externalMEMOSize", "[Main]TotalMass : " + l1 + "Bytes, FreeMass : " + l2 + "Bytes, UsedMass : " + l3 + "Bytes");
    String str = Long.toString(l1) + "," + Long.toString(l3);
    FtUtil.log_i(this.CLASS_NAME, "externalMEMOSize", "resData=" + str);
    return str;
  }
  
  public String int_extMEMOSize(int paramInt, boolean paramBoolean)
  {
    StatFs localStatFs = new StatFs(this.mStorageManager.getVolumeList()[paramInt].getPath());
    long l1 = localStatFs.getBlockCount() * localStatFs.getBlockSize();
    long l2 = localStatFs.getFreeBlocks() * localStatFs.getBlockSize();
    long l3 = l1 - l2;
    FtUtil.log_i(this.CLASS_NAME, "externalMEMOSize", "[Main]TotalMass : " + l1 + "Bytes, FreeMass : " + l2 + "Bytes, UsedMass : " + l3 + "Bytes");
    String str = Long.toString(l1) + "," + Long.toString(l3);
    FtUtil.log_i(this.CLASS_NAME, "externalMEMOSize", "resData=" + str);
    return str;
  }
  
  public boolean isCompleteFileSystemBuildingNAND()
  {
    String str = Support.Properties.get("EMMC_CHECKSUM");
    if (str == null) {
      return false;
    }
    for (;;)
    {
      try
      {
        int i = Integer.valueOf(str).intValue();
        int j = i / 2;
        int k = i % 2;
        FtUtil.log_i(this.CLASS_NAME, "isCompleteFileSystemBuildingNAND", "done=" + j + "pass=" + k);
        if ((j == 1) && (k == 1))
        {
          bool = true;
          return bool;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        FtUtil.log_e(localNumberFormatException);
        return false;
      }
      boolean bool = false;
    }
  }
  
  public boolean isDetectExternalMemory()
  {
    String str = Support.Kernel.read("EXTERNAL_MEMORY_INSERTED");
    if (str != null) {
      return str.equalsIgnoreCase("INSERT");
    }
    return false;
  }
  
  public boolean isEarSwitchPress()
  {
    int i = 5;
    for (;;)
    {
      boolean bool = false;
      if (i > 0)
      {
        i--;
        if ("1".equals(Support.Kernel.read("EARJACK_SWITCH_STATE")))
        {
          FtUtil.log_i(this.CLASS_NAME, "isEarSwitchPress", "EARJACK_SWITCH_STATE is pressed");
          bool = true;
        }
      }
      else
      {
        FtUtil.log_i(this.CLASS_NAME, "isEarSwitchPress", "EARJACK_SWITCH_STATE is released");
        return bool;
      }
      try
      {
        Thread.sleep(100L);
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
    }
  }
  
  public boolean isInnerMemoryExist()
  {
    return new StatFs(Environment.getExternalStorageDirectory().toString()).getBlockCount() > 0;
  }
  
  public boolean isKeyPress()
  {
    String[] arrayOfString = { "press", "pressed", "PRESS", "PRESSED", "1", "2", "4", "7", "8" };
    String str1 = Support.Kernel.read("KEY_PRESSED");
    String str2 = Support.Kernel.read("KEY_PRESSED_POWER");
    String str3 = Support.Kernel.read("EARJACK_SWITCH_STATE");
    FtUtil.log_i(this.CLASS_NAME, "isKeyPress", "state:" + str1 + ", state_power:" + str2 + ", state_ear:" + str3);
    for (int i = 0; i < arrayOfString.length; i++) {
      if (((str1 != null) && (arrayOfString[i].contains(str1))) || ((str2 != null) && (arrayOfString[i].contains(str2))) || ((str3 != null) && (str3.contains(arrayOfString[i]))))
      {
        FtUtil.log_i(this.CLASS_NAME, "isKeyPress", "state:" + str1 + ", state_power:" + str2 + ", state_ear:" + str3);
        return true;
      }
    }
    return false;
  }
  
  public boolean isMountedStorage(int paramInt)
  {
    StorageVolume[] arrayOfStorageVolume = this.mStorageManager.getVolumeList();
    if ((paramInt >= arrayOfStorageVolume.length) || (arrayOfStorageVolume[paramInt] == null))
    {
      FtUtil.log_i(this.CLASS_NAME, "isMountedStorage", "StorageVolumes[type] is null");
      return false;
    }
    String str1 = arrayOfStorageVolume[paramInt].getPath();
    FtUtil.log_i(this.CLASS_NAME, "isMountedStorage", "path : " + str1);
    if (str1 != null)
    {
      String str2 = this.mStorageManager.getVolumeState(str1);
      FtUtil.log_i(this.CLASS_NAME, "isMountedStorage", "Environment.MEDIA_MOUNTED : mounted");
      FtUtil.log_i(this.CLASS_NAME, "isMountedStorage", "state : " + str2);
      return str2.equals("mounted");
    }
    FtUtil.log_i(this.CLASS_NAME, "isMountedStorage", "another error");
    return false;
  }
  
  public boolean isSimCardExist()
  {
    int i = 1;
    TelephonyManager localTelephonyManager1 = (TelephonyManager)getSystemService("phone");
    if ((Support.Feature.getBoolean("SUPPORT_2ND_RIL")) && (!Support.Feature.getBoolean("SUPPORT_DUAL_STANBY")))
    {
      int j = 1;
      TelephonyManager localTelephonyManager2 = (TelephonyManager)getSystemService("phone2");
      if (((localTelephonyManager1.getSimState() == 0) || (localTelephonyManager1.getSimState() == i)) && ((localTelephonyManager2.getSimState() == 0) || (localTelephonyManager2.getSimState() == i))) {
        j = 0;
      }
      FtUtil.log_e(this.CLASS_NAME, "isSimCardExist", "2ND RIL, Res = " + j);
      i = j;
    }
    String str;
    PlugInDsdsService localPlugInDsdsService;
    do
    {
      do
      {
        return i;
        str = SystemProperties.get("ril.MSIMM");
        FtUtil.log_d(this.CLASS_NAME, "isSimCardExist", "multiSimState : " + str);
        if (!Support.Feature.getBoolean("IS_MULTI_SIM_FRAMEWORK")) {
          break label309;
        }
        FtUtil.log_d(this.CLASS_NAME, "isSimCardExist", "Multi SIM Framework");
        localPlugInDsdsService = (PlugInDsdsService)PlugInServiceManager.getService("DSDS");
        if (!str.equals("0")) {
          break;
        }
        FtUtil.log_d(this.CLASS_NAME, "isSimCardExist", "dsdsService.getSimState(0) : " + localPlugInDsdsService.getSimState(0));
      } while ((localPlugInDsdsService.getSimState(0) != 0) && (localPlugInDsdsService.getSimState(0) != i));
      return false;
      FtUtil.log_d(this.CLASS_NAME, "isSimCardExist", "dsdsService.getSimState(1) : " + localPlugInDsdsService.getSimState(i));
    } while ((localPlugInDsdsService.getSimState(i) != 0) && (localPlugInDsdsService.getSimState(i) != i));
    return false;
    label309:
    if ((localTelephonyManager1.getSimState() == 0) || (localTelephonyManager1.getSimState() == i)) {}
    for (boolean bool = false;; bool = i)
    {
      FtUtil.log_d(this.CLASS_NAME, "isSimCardExist", "bRes : " + bool);
      if (!localTelephonyManager1.isMultiSimEnabled()) {
        break label388;
      }
      if ((bool) && (str.equals("0"))) {
        break;
      }
      return false;
    }
    label388:
    return bool;
  }
  
  public boolean isSimCardExist2()
  {
    int i = 1;
    if (Support.Feature.getBoolean("SUPPORT_DUAL_STANBY"))
    {
      TelephonyManager localTelephonyManager2 = (TelephonyManager)getSystemService("phone2");
      if ((localTelephonyManager2.getSimState() == 0) || (localTelephonyManager2.getSimState() == i)) {
        i = 0;
      }
    }
    PlugInDsdsService localPlugInDsdsService;
    do
    {
      do
      {
        return i;
        if (Support.Feature.getBoolean("SUPPORT_DUALMODE"))
        {
          TelephonyManager localTelephonyManager1 = (TelephonyManager)getSystemService("phone");
          if ((localTelephonyManager1.getSimState() == 0) || (localTelephonyManager1.getSimState() == i)) {}
          for (boolean bool = false;; bool = i) {
            return bool;
          }
        }
        String str = SystemProperties.get("ril.MSIMM");
        FtUtil.log_d(this.CLASS_NAME, "isSimCardExist2", "multiSimState : " + str);
        localPlugInDsdsService = (PlugInDsdsService)PlugInServiceManager.getService("DSDS");
        if (!str.equals("1")) {
          break;
        }
        FtUtil.log_d(this.CLASS_NAME, "isSimCardExist2", "dsdsService.getSimState(0) : " + localPlugInDsdsService.getSimState(0));
      } while ((localPlugInDsdsService.getSimState(0) != 0) && (localPlugInDsdsService.getSimState(0) != i));
      return false;
      FtUtil.log_d(this.CLASS_NAME, "isSimCardExist2", "dsdsService.getSimState(1) : " + localPlugInDsdsService.getSimState(i));
    } while ((localPlugInDsdsService.getSimState(i) != 0) && (localPlugInDsdsService.getSimState(i) != i));
    return false;
  }
  
  public boolean isVibrating()
  {
    return this.mIsVibrating;
  }
  
  public String mainMEMOSize()
  {
    StatFs localStatFs = new StatFs(Environment.getDataDirectory().toString());
    long l1 = localStatFs.getBlockCount() * localStatFs.getBlockSize() / 1024L;
    long l2 = localStatFs.getFreeBlocks() * localStatFs.getBlockSize() / 1024L;
    long l3 = l1 - l2;
    FtUtil.log_i(this.CLASS_NAME, "mainMEMOSize", "[Main]TotalMass : " + l1 + "KB, FreeMass : " + l2 + "KB, UsedMass : " + l3 + "KB");
    String str = Long.toString(l1) + "," + Long.toString(l3);
    FtUtil.log_i(this.CLASS_NAME, "mainMEMOSize", "resData=" + str);
    return str;
  }
  
  public String readModuleBinVersion(int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "readModuleBinVersion", "module=" + paramInt);
    String str1 = null;
    String str2;
    switch (paramInt)
    {
    case 0: 
    default: 
      if ((paramInt == 1) && (!Support.Feature.getString("TSP_MANUFACTURE").equalsIgnoreCase("ATMEL")) && (!Support.Feature.getString("TSP_MANUFACTURE").equalsIgnoreCase("ZINITIX"))) {
        str2 = startTSPTest("get_fw_ver_bin");
      }
      break;
    }
    for (;;)
    {
      FtUtil.log_i(this.CLASS_NAME, "readModuleBinVersion", "version=" + str2);
      return str2;
      str1 = "TSP_FIRMWARE_VERSION_PHONE";
      break;
      str1 = "TSK_FIRMWARE_VERSION_PHONE";
      break;
      str1 = "EPEN_FIRMWARE_VERSION";
      break;
      if (paramInt == 3)
      {
        if (isEpenChecksumPass() == true)
        {
          String str3 = Support.Kernel.read(str1);
          FtUtil.log_i(this.CLASS_NAME, "readModuleFirmwareVersion", "version.split '\t' [0]=" + str3.split("\t")[0]);
          FtUtil.log_i(this.CLASS_NAME, "readModuleFirmwareVersion", "version.split '\t' [1]=" + str3.split("\t")[1]);
          str2 = str3.split("\t")[1];
        }
        else
        {
          str2 = "NG_CS";
        }
      }
      else {
        str2 = Support.Kernel.read(str1);
      }
    }
  }
  
  public String readModuleFirmwareVersion(int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "readModuleFirmwareVersion", "module=" + paramInt);
    String str1 = null;
    String str2;
    switch (paramInt)
    {
    case 0: 
    default: 
      if ((paramInt == 1) && (!Support.Feature.getString("TSP_MANUFACTURE").equalsIgnoreCase("ATMEL")) && (!Support.Feature.getString("TSP_MANUFACTURE").equalsIgnoreCase("ZINITIX"))) {
        str2 = startTSPTest("get_fw_ver_ic");
      }
      break;
    }
    for (;;)
    {
      FtUtil.log_i(this.CLASS_NAME, "readModuleFirmwareVersion", "version=" + str2);
      return str2;
      str1 = "TSP_FIRMWARE_VERSION_PANEL";
      break;
      str1 = "TSK_FIRMWARE_VERSION_PANEL";
      break;
      str1 = "EPEN_FIRMWARE_VERSION";
      break;
      str1 = "SENSORHUB_FIRMWARE_VERSION";
      break;
      str1 = "BARCODE_EMUL_FIRMWARE_VERSION";
      break;
      if (paramInt == 3)
      {
        if (isEpenChecksumPass() == true)
        {
          String str3 = Support.Kernel.read(str1);
          FtUtil.log_i(this.CLASS_NAME, "readModuleFirmwareVersion", "version.split '\t' [0]=" + str3.split("\t")[0]);
          FtUtil.log_i(this.CLASS_NAME, "readModuleFirmwareVersion", "version.split '\t' [1]=" + str3.split("\t")[1]);
          str2 = str3.split("\t")[0];
        }
        else
        {
          str2 = "NG_CS";
        }
      }
      else {
        str2 = Support.Kernel.read(str1);
      }
    }
  }
  
  public String readModuleUpdateStatus(int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "readModuleStatus", "module=" + paramInt);
    String str1 = null;
    switch (paramInt)
    {
    }
    for (;;)
    {
      String str2 = Support.Kernel.read(str1);
      FtUtil.log_i(this.CLASS_NAME, "readModuleStatus", "status=" + str2);
      return str2;
      str1 = "TSP_FIRMWARE_UPDATE_STATUS";
      continue;
      str1 = "TSK_FIRMWARE_UPDATE_STATUS";
    }
  }
  
  public String readSensorHubResult()
  {
    String str = Support.Kernel.read("SENSORHUB_MCU");
    FtUtil.log_i(this.CLASS_NAME, "readSensorHubResult", "result=" + str);
    return str;
  }
  
  public String readSerialNo()
  {
    return Support.Kernel.read("SERIAL_NO");
  }
  
  public Integer readTSKSensitivity(int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "readTSKSensitivity", "keyCode=" + paramInt);
    String str;
    switch (paramInt)
    {
    default: 
      str = null;
    }
    for (;;)
    {
      Integer localInteger = null;
      if (str != null) {
        localInteger = new Integer(Integer.parseInt(str));
      }
      return localInteger;
      str = Support.Kernel.read("TOUCH_KEY_SENSITIVITY_MENU");
      continue;
      if (Support.Feature.getBoolean("SUPPORT_DUAL_LCD_FOLDER"))
      {
        str = Support.Kernel.read("TOUCH_KEY_SENSITIVITY_HOME");
      }
      else
      {
        str = Support.Kernel.read("TOUCH_KEY_SENSITIVITY_OK");
        continue;
        str = Support.Kernel.read("TOUCH_KEY_SENSITIVITY_BACK");
        continue;
        str = Support.Kernel.read("TOUCH_KEY_SENSITIVITY_SEARCH");
        continue;
        str = Support.Kernel.read("TOUCH_KEY_SENSITIVITY_RECENT");
      }
    }
  }
  
  public int readTSKThreshold()
  {
    String str = Support.Kernel.read("TOUCH_KEY_THRESHOLD");
    if (str != null) {}
    for (int i = Integer.parseInt(str);; i = 0)
    {
      FtUtil.log_i(this.CLASS_NAME, "readTSKThreshold", "threshold=" + i);
      return i;
    }
  }
  
  public String readTSKThresholdMulti()
  {
    String str = Support.Kernel.read("TOUCH_KEY_THRESHOLD");
    FtUtil.log_i(this.CLASS_NAME, "readTSKThresholdMulti", "threshold=" + str);
    return str;
  }
  
  public String readiSerialNo()
  {
    return Support.Properties.get("ISERIAL_NO");
  }
  
  public void runOTGTest()
  {
    "eng".equals(Build.TYPE);
    FtUtil.log_i(this.CLASS_NAME, "runOTGTest", "Start OTG Test");
    final Intent localIntent = new Intent("com.sec.factory.intent.ACTION_OTG_RESPONSE");
    if (Support.Kernel.write("OTG_MUIC_SET", OTG_MUIC_ON)) {
      FtUtil.log_i(this.CLASS_NAME, "runOTGTest", "Set OTG MUIC success!");
    }
    while (Support.Kernel.write("OTG_TEST_MODE", OTG_TEST_ON))
    {
      FtUtil.log_i(this.CLASS_NAME, "runOTGTest", "OTG TEST ON");
      new Handler(getApplicationContext().getMainLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          String str;
          if (paramAnonymousMessage.what == 0)
          {
            FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "runOTGTest", "OTG Test Finish");
            if (!Support.Kernel.read("OTG_TEST_MODE").equals("ON")) {
              break label168;
            }
            str = "OK";
            FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "runOTGTest", "OTG Test Result=" + str);
            if (!str.equals("OK")) {
              break label180;
            }
            if (!Support.Kernel.write("OTG_TEST_MODE", ModuleDevice.OTG_TEST_OFF)) {
              break label174;
            }
            str = "OK";
          }
          for (;;)
          {
            FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "runOTGTest", "OTG TEST OFF");
            FtUtil.log_i(ModuleDevice.this.CLASS_NAME, "runOTGTest", "OTG Test Result=" + str);
            Support.Kernel.write("OTG_MUIC_SET", ModuleDevice.OTG_MUIC_OFF);
            localIntent.putExtra("result", str);
            ModuleDevice.this.sendBroadcast(localIntent);
            return;
            label168:
            str = "NG";
            break;
            label174:
            str = "NG";
            continue;
            label180:
            Support.Kernel.write("OTG_TEST_MODE", ModuleDevice.OTG_TEST_OFF);
          }
        }
      }.sendEmptyMessageDelayed(0, 2000L);
      FtUtil.log_i(this.CLASS_NAME, "runOTGTest", "Wait 2sec...");
      return;
      FtUtil.log_i(this.CLASS_NAME, "runOTGTest", "Set OTG MUIC fail!");
    }
    localIntent.putExtra("result", "NG");
    sendBroadcast(localIntent);
  }
  
  public boolean setLEDlamp(int paramInt)
  {
    String str = Support.Feature.getString("SVC_LED_TEST_BRIGHTNESS");
    switch (paramInt)
    {
    default: 
      return false;
    case 0: 
      Support.Kernel.write("LED_RED", "0");
      Support.Kernel.write("LED_GREEN", "0");
      return Support.Kernel.write("LED_BLUE", "0");
    case 1: 
      if ((this.isMSM8960) || (this.isMSM8930) || (this.isMSM8260A) || (this.isPegasus) || (this.isPegasusPrime) || (this.isPegaPrime)) {
        return Support.Kernel.write("LED_BLINK", "0xFFFFFF");
      }
      Support.Kernel.write("LED_RED", str);
      Support.Kernel.write("LED_GREEN", str);
      return Support.Kernel.write("LED_BLUE", str);
    case 2: 
      Support.Kernel.write("LED_RED", str);
      Support.Kernel.write("LED_GREEN", "0");
      return Support.Kernel.write("LED_BLUE", "0");
    case 3: 
      Support.Kernel.write("LED_RED", "0");
      Support.Kernel.write("LED_GREEN", str);
      return Support.Kernel.write("LED_BLUE", "0");
    }
    Support.Kernel.write("LED_RED", "0");
    Support.Kernel.write("LED_GREEN", "0");
    return Support.Kernel.write("LED_BLUE", str);
  }
  
  public void setLPAmode(String paramString)
  {
    boolean bool = Support.Kernel.write("LPA_MODE_SET", paramString);
    FtUtil.log_e(this.CLASS_NAME, "setLpaMode", "lpaMode : " + paramString + " Result: " + bool);
  }
  
  public boolean setUSBMode(int paramInt)
  {
    Settings.Secure.putInt(getContentResolver(), "usb_setting_mode", paramInt);
    if (paramInt == 0) {}
    for (String str = "0";; str = "1") {
      return Support.Kernel.write("USB_MENU_SEL", str);
    }
  }
  
  public void startSensorHubTest()
  {
    FtUtil.log_i(this.CLASS_NAME, "startSensorHbTest", "Start sensorhub test");
    Support.Kernel.write("SENSORHUB_MCU", "1");
  }
  
  public String startTSPConnectionTest_atmel_temporary()
  {
    String str = Support.Kernel.read("TSP_COMMAND_REFER_SET");
    if (str == null)
    {
      FtUtil.log_e(this.CLASS_NAME, "tspConnectionTest", "Fail status = null");
      return "NG";
    }
    if (str.equals("0"))
    {
      FtUtil.log_e(this.CLASS_NAME, "tspConnectionTest", "Success status = OK");
      return "OK";
    }
    FtUtil.log_e(this.CLASS_NAME, "startTSPTest", "Fail - Status : " + str);
    return "NG";
  }
  
  public String startTSPReadTest(String paramString, int paramInt)
  {
    String str1 = startTSPTest("get_x_num");
    if (str1.equals("NG"))
    {
      FtUtil.log_e(this.CLASS_NAME, "startTSPReadTest", "error - TSP_CMD_X_COUNT");
      return "NG";
    }
    int i = Integer.parseInt(str1, 10);
    String str2 = startTSPTest("get_y_num");
    if (str2.equals("NG"))
    {
      FtUtil.log_e(this.CLASS_NAME, "startTSPReadTest", "error - TSP_CMD_Y_COUNT");
      return "NG";
    }
    int j = Integer.parseInt(str2, 10);
    String str5;
    if (Support.Feature.getString("TSP_MANUFACTURE").equalsIgnoreCase("SYNAPTICS"))
    {
      str5 = "";
      for (int m = 0; m < j; m++)
      {
        String str6 = paramString + "," + paramInt + "," + m;
        str5 = str5 + startTSPTest(str6.getBytes(), str6.length()) + ",";
      }
    }
    if (paramInt < i)
    {
      String str3 = "";
      for (int k = 0; k < j; k++)
      {
        String str4 = paramString + "," + paramInt + "," + k;
        str3 = str3 + startTSPTest(str4.getBytes(), str4.length()) + ",";
      }
      return str3.substring(0, -1 + str3.length());
    }
    FtUtil.log_e(this.CLASS_NAME, "startTSPReadTest", "error - X_AXIS >= X_AXIS_MAX");
    return "NG";
    return str5.substring(0, -1 + str5.length());
  }
  
  public String startTSPTest(String paramString)
  {
    FtUtil.log_e(this.CLASS_NAME, "startTSPTest", "cmd name => " + paramString);
    return startTSPTest(paramString.getBytes(), paramString.length());
  }
  
  public void startVibrate()
  {
    FtUtil.log_i(this.CLASS_NAME, "startVibrate", "Start Vibrate pattern={0, 5000}");
    this.mVibrator.setMagnitude(this.mVibrator.getMaxMagnitude());
    this.mVibrator.vibrate(new long[] { 0L, 5000L }, 0);
    this.mIsVibrating = true;
  }
  
  public void startVibrate(long paramLong)
  {
    FtUtil.log_i(this.CLASS_NAME, "startVibrate", "Start Vibrate : " + paramLong + " sec");
    if (!this.mIsVibrating)
    {
      this.mVibrator.setMagnitude(this.mVibrator.getMaxMagnitude());
      if ((!Support.TestCase.getEnabled("IS_VIBETONZ_UNSUPPORTED")) || (paramLong != 2147483647L)) {
        break label148;
      }
      String[] arrayOfString = Support.TestCase.getString("IS_VIBETONZ_UNSUPPORTED").split(",");
      int i = Integer.parseInt(arrayOfString[0].trim());
      int j = Integer.parseInt(arrayOfString[1].trim());
      SystemVibrator localSystemVibrator = this.mVibrator;
      long[] arrayOfLong = new long[2];
      arrayOfLong[0] = i;
      arrayOfLong[1] = j;
      localSystemVibrator.vibrate(arrayOfLong, 0);
    }
    for (;;)
    {
      this.mIsVibrating = true;
      return;
      label148:
      this.mVibrator.vibrate(paramLong);
    }
  }
  
  public void stopVibrate()
  {
    FtUtil.log_i(this.CLASS_NAME, "stopVibrate", "Stop Vibrate");
    if (this.mIsVibrating)
    {
      this.mVibrator.cancel();
      this.mIsVibrating = false;
    }
  }
  
  public String tsp_module_data_read(String paramString)
  {
    String str1 = "";
    String str2 = Support.Kernel.read(paramString).trim();
    FtUtil.log_e(this.CLASS_NAME, "tspConnectionTest", "tempResult = " + str2);
    StringTokenizer localStringTokenizer = new StringTokenizer(str2, ",");
    if (localStringTokenizer.hasMoreTokens()) {
      FtUtil.log_e(this.CLASS_NAME, "tspConnectionTest", "Status = " + localStringTokenizer.nextToken().trim());
    }
    while (localStringTokenizer.hasMoreTokens())
    {
      String str3 = str1 + localStringTokenizer.nextToken().trim() + ",";
      str1 = str3 + localStringTokenizer.nextToken().trim();
    }
    FtUtil.log_e(this.CLASS_NAME, "tspConnectionTest", "result = " + str1);
    return str1;
  }
  
  public String tsp_module_data_read(String paramString1, String paramString2, String paramString3)
  {
    String[] arrayOfString = new String[TSP_NODE_X];
    if (paramString1 == null) {
      return "NG";
    }
    int i = Integer.parseInt(paramString1);
    if ((i < 0) || (i > TSP_NODE_Y)) {
      return "NG";
    }
    Support.Kernel.read(paramString2);
    String str = "";
    for (int j = 0; j < TSP_NODE_X; j++)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(i - 1 + j * TSP_NODE_X);
      Support.Kernel.write(paramString3, String.format("%d", arrayOfObject));
      arrayOfString[j] = Support.Kernel.read(paramString3);
      str = str + arrayOfString[j] + ",";
    }
    return str.substring(0, -1 + str.length());
  }
  
  public String tsp_module_mode(int paramInt)
  {
    String str;
    if (paramInt == 0)
    {
      Support.Kernel.read("TSP_COMMAND_ON");
      str = "OK";
    }
    do
    {
      return str;
      if (paramInt == 1)
      {
        Support.Kernel.read("TSP_COMMAND_OFF");
        return "OK";
      }
      if (paramInt == 2) {
        return Support.Kernel.read("TSP_COMMAND_NAME");
      }
      if (paramInt == 3) {
        return Support.Kernel.read("TSP_FIRMWARE_VERSION_PHONE");
      }
      str = null;
    } while (paramInt != 4);
    return Support.Kernel.read("TSP_FIRMWARE_VERSION_PANEL");
  }
  
  public String tsp_module_refer_diff(String paramString1, String paramString2, String paramString3)
  {
    String str = "";
    String[] arrayOfString1 = new String[TSP_NODE_X];
    String[] arrayOfString2 = new String[TSP_NODE_Y];
    int i = Integer.parseInt(paramString1);
    if ((i < 0) || (i > TSP_NODE_Y)) {
      return "NG";
    }
    Support.Kernel.read(paramString2);
    for (int j = 0; j < TSP_NODE_X; j++)
    {
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(i - 1 + j * TSP_NODE_X);
      Support.Kernel.write(paramString2, String.format("%d", arrayOfObject2));
      arrayOfString1[j] = Support.Kernel.read(paramString3);
    }
    Support.Kernel.read(paramString2);
    for (int k = 0; k < TSP_NODE_X; k++)
    {
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(i - 1 + k * TSP_NODE_X);
      Support.Kernel.write(paramString3, String.format("%d", arrayOfObject1));
      arrayOfString2[k] = Support.Kernel.read(paramString3);
      str = str + String.valueOf(Integer.parseInt(arrayOfString2[k]) - Integer.parseInt(arrayOfString1[k])) + ",";
    }
    return str.substring(0, -1 + str.length());
  }
  
  public void turnOffTSKSensitivity()
  {
    if (new File(Support.Kernel.getFilePath("TOUCH_KEY_SENSITIVITY_POWER")).exists())
    {
      FtUtil.log_i(this.CLASS_NAME, "turnOffTSKSensitivity", "Turn off TSK Sensitivity");
      Support.Kernel.write("TOUCH_KEY_SENSITIVITY_POWER", "0");
      return;
    }
    FtUtil.log_i(this.CLASS_NAME, "turnOffTSKSensitivity", "File does not exist");
  }
  
  public void turnOnTSKSensitivity()
  {
    if (new File(Support.Kernel.getFilePath("TOUCH_KEY_SENSITIVITY_POWER")).exists())
    {
      FtUtil.log_i(this.CLASS_NAME, "turnOnTSKSensitivity", "Turn on TSK Sensitivity");
      Support.Kernel.write("TOUCH_KEY_SENSITIVITY_POWER", "1");
      return;
    }
    FtUtil.log_i(this.CLASS_NAME, "turnOnTSKSensitivity", "File does not exist");
  }
  
  public void unmount(int paramInt)
  {
    StorageVolume[] arrayOfStorageVolume = this.mStorageManager.getVolumeList();
    String str;
    IMountService localIMountService;
    if (arrayOfStorageVolume[paramInt] != null)
    {
      str = arrayOfStorageVolume[paramInt].getPath();
      if ((str != null) && (isMountedStorage(paramInt)))
      {
        localIMountService = getMountService();
        if (localIMountService == null) {}
      }
    }
    try
    {
      localIMountService.unmountVolume(str, true, true);
      FtUtil.log_i(this.CLASS_NAME, "unmount", "Unmount Storage type=" + paramInt);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      FtUtil.log_e(localRemoteException);
    }
  }
  
  public void writeSerialNo(String paramString)
  {
    Support.Kernel.write("SERIAL_NO", paramString);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.ModuleDevice
 * JD-Core Version:    0.7.1
 */