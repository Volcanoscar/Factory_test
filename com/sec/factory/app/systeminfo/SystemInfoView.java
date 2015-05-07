package com.sec.factory.app.systeminfo;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.hardware.Camera;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.view.View;
import com.sec.factory.app.factorytest.FactoryTestPhone;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.modules.ModuleTouchScreen;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.NVAccessor;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;
import com.sec.factory.support.Support.SystemInfo;
import com.sec.factory.support.Support.TestCase;
import java.io.File;

public class SystemInfoView
  extends View
{
  private final String CLASS_NAME = "SystemInfoView";
  private String DEFAULT_STRING;
  private final int ID_BAND_N_CHANNEL;
  private final int ID_BATTERY_LEVEL;
  private final int ID_CAMERA_FIRMWARE_VERSION;
  private final int ID_CSC_VERSION;
  private int ID_DUMMY = 0;
  private final int ID_HW_VERSION;
  private final int ID_LTE_VERSION;
  private final int ID_MEMORY_SIZE;
  private final int ID_PDA_VERSION;
  private final int ID_PHONE_VERSION;
  private final int ID_RF_CAL_DATE;
  private final int ID_SMD_N_PBA;
  private final int ID_TSK_FIRMWARE_VERSION;
  private final int ID_TSP_FIRMWARE_VERSION;
  private final int ID_UART_N_USB;
  private final int ID_UN;
  private String NA_STRING;
  private final byte NV_KEY_PBA;
  private final byte NV_KEY_SMD;
  private final byte NV_VALUE_P;
  private int mBgColor;
  private Camera mCameraDevice;
  private String mCommunicationMode;
  private Context mContext;
  private int mCoordinate_Land_x;
  private int mCoordinate_Land_y;
  private int mCoordinate_Port_x;
  private int mCoordinate_Port_y;
  private int mHeight;
  private Info[] mInfo;
  ModuleCommon mModuleCommon;
  ModuleDevice mModuleDevice;
  ModuleTouchScreen mModuleTouchScreen;
  private int mTextColor;
  private int mTextSize;
  private int mWidth;
  private String operator;
  
  public SystemInfoView(Context paramContext)
  {
    super(paramContext);
    int i = this.ID_DUMMY;
    this.ID_DUMMY = (i + 1);
    this.ID_PDA_VERSION = i;
    int j = this.ID_DUMMY;
    this.ID_DUMMY = (j + 1);
    this.ID_PHONE_VERSION = j;
    int k = this.ID_DUMMY;
    this.ID_DUMMY = (k + 1);
    this.ID_LTE_VERSION = k;
    int m = this.ID_DUMMY;
    this.ID_DUMMY = (m + 1);
    this.ID_CSC_VERSION = m;
    int n = this.ID_DUMMY;
    this.ID_DUMMY = (n + 1);
    this.ID_HW_VERSION = n;
    int i1 = this.ID_DUMMY;
    this.ID_DUMMY = (i1 + 1);
    this.ID_RF_CAL_DATE = i1;
    int i2 = this.ID_DUMMY;
    this.ID_DUMMY = (i2 + 1);
    this.ID_SMD_N_PBA = i2;
    int i3 = this.ID_DUMMY;
    this.ID_DUMMY = (i3 + 1);
    this.ID_CAMERA_FIRMWARE_VERSION = i3;
    int i4 = this.ID_DUMMY;
    this.ID_DUMMY = (i4 + 1);
    this.ID_TSP_FIRMWARE_VERSION = i4;
    int i5 = this.ID_DUMMY;
    this.ID_DUMMY = (i5 + 1);
    this.ID_TSK_FIRMWARE_VERSION = i5;
    int i6 = this.ID_DUMMY;
    this.ID_DUMMY = (i6 + 1);
    this.ID_BAND_N_CHANNEL = i6;
    int i7 = this.ID_DUMMY;
    this.ID_DUMMY = (i7 + 1);
    this.ID_UART_N_USB = i7;
    int i8 = this.ID_DUMMY;
    this.ID_DUMMY = (i8 + 1);
    this.ID_UN = i8;
    int i9 = this.ID_DUMMY;
    this.ID_DUMMY = (i9 + 1);
    this.ID_BATTERY_LEVEL = i9;
    int i10 = this.ID_DUMMY;
    this.ID_DUMMY = (i10 + 1);
    this.ID_MEMORY_SIZE = i10;
    this.mInfo = new Info[this.ID_DUMMY];
    this.mWidth = -1;
    this.mHeight = -1;
    this.NV_KEY_SMD = 1;
    this.NV_KEY_PBA = 4;
    this.NV_VALUE_P = 80;
    this.DEFAULT_STRING = "Unknown";
    this.NA_STRING = "N/A";
    this.operator = SystemProperties.get("ro.csc.sales_code", "Unknown").trim().toLowerCase();
    this.mContext = paramContext;
    this.mModuleCommon = ModuleCommon.instance(paramContext);
    this.mModuleDevice = ModuleDevice.instance(paramContext);
    this.mModuleTouchScreen = ModuleTouchScreen.instance(paramContext);
    init();
  }
  
  public SystemInfoView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    int i = this.ID_DUMMY;
    this.ID_DUMMY = (i + 1);
    this.ID_PDA_VERSION = i;
    int j = this.ID_DUMMY;
    this.ID_DUMMY = (j + 1);
    this.ID_PHONE_VERSION = j;
    int k = this.ID_DUMMY;
    this.ID_DUMMY = (k + 1);
    this.ID_LTE_VERSION = k;
    int m = this.ID_DUMMY;
    this.ID_DUMMY = (m + 1);
    this.ID_CSC_VERSION = m;
    int n = this.ID_DUMMY;
    this.ID_DUMMY = (n + 1);
    this.ID_HW_VERSION = n;
    int i1 = this.ID_DUMMY;
    this.ID_DUMMY = (i1 + 1);
    this.ID_RF_CAL_DATE = i1;
    int i2 = this.ID_DUMMY;
    this.ID_DUMMY = (i2 + 1);
    this.ID_SMD_N_PBA = i2;
    int i3 = this.ID_DUMMY;
    this.ID_DUMMY = (i3 + 1);
    this.ID_CAMERA_FIRMWARE_VERSION = i3;
    int i4 = this.ID_DUMMY;
    this.ID_DUMMY = (i4 + 1);
    this.ID_TSP_FIRMWARE_VERSION = i4;
    int i5 = this.ID_DUMMY;
    this.ID_DUMMY = (i5 + 1);
    this.ID_TSK_FIRMWARE_VERSION = i5;
    int i6 = this.ID_DUMMY;
    this.ID_DUMMY = (i6 + 1);
    this.ID_BAND_N_CHANNEL = i6;
    int i7 = this.ID_DUMMY;
    this.ID_DUMMY = (i7 + 1);
    this.ID_UART_N_USB = i7;
    int i8 = this.ID_DUMMY;
    this.ID_DUMMY = (i8 + 1);
    this.ID_UN = i8;
    int i9 = this.ID_DUMMY;
    this.ID_DUMMY = (i9 + 1);
    this.ID_BATTERY_LEVEL = i9;
    int i10 = this.ID_DUMMY;
    this.ID_DUMMY = (i10 + 1);
    this.ID_MEMORY_SIZE = i10;
    this.mInfo = new Info[this.ID_DUMMY];
    this.mWidth = -1;
    this.mHeight = -1;
    this.NV_KEY_SMD = 1;
    this.NV_KEY_PBA = 4;
    this.NV_VALUE_P = 80;
    this.DEFAULT_STRING = "Unknown";
    this.NA_STRING = "N/A";
    this.operator = SystemProperties.get("ro.csc.sales_code", "Unknown").trim().toLowerCase();
    init();
  }
  
  public SystemInfoView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    int i = this.ID_DUMMY;
    this.ID_DUMMY = (i + 1);
    this.ID_PDA_VERSION = i;
    int j = this.ID_DUMMY;
    this.ID_DUMMY = (j + 1);
    this.ID_PHONE_VERSION = j;
    int k = this.ID_DUMMY;
    this.ID_DUMMY = (k + 1);
    this.ID_LTE_VERSION = k;
    int m = this.ID_DUMMY;
    this.ID_DUMMY = (m + 1);
    this.ID_CSC_VERSION = m;
    int n = this.ID_DUMMY;
    this.ID_DUMMY = (n + 1);
    this.ID_HW_VERSION = n;
    int i1 = this.ID_DUMMY;
    this.ID_DUMMY = (i1 + 1);
    this.ID_RF_CAL_DATE = i1;
    int i2 = this.ID_DUMMY;
    this.ID_DUMMY = (i2 + 1);
    this.ID_SMD_N_PBA = i2;
    int i3 = this.ID_DUMMY;
    this.ID_DUMMY = (i3 + 1);
    this.ID_CAMERA_FIRMWARE_VERSION = i3;
    int i4 = this.ID_DUMMY;
    this.ID_DUMMY = (i4 + 1);
    this.ID_TSP_FIRMWARE_VERSION = i4;
    int i5 = this.ID_DUMMY;
    this.ID_DUMMY = (i5 + 1);
    this.ID_TSK_FIRMWARE_VERSION = i5;
    int i6 = this.ID_DUMMY;
    this.ID_DUMMY = (i6 + 1);
    this.ID_BAND_N_CHANNEL = i6;
    int i7 = this.ID_DUMMY;
    this.ID_DUMMY = (i7 + 1);
    this.ID_UART_N_USB = i7;
    int i8 = this.ID_DUMMY;
    this.ID_DUMMY = (i8 + 1);
    this.ID_UN = i8;
    int i9 = this.ID_DUMMY;
    this.ID_DUMMY = (i9 + 1);
    this.ID_BATTERY_LEVEL = i9;
    int i10 = this.ID_DUMMY;
    this.ID_DUMMY = (i10 + 1);
    this.ID_MEMORY_SIZE = i10;
    this.mInfo = new Info[this.ID_DUMMY];
    this.mWidth = -1;
    this.mHeight = -1;
    this.NV_KEY_SMD = 1;
    this.NV_KEY_PBA = 4;
    this.NV_VALUE_P = 80;
    this.DEFAULT_STRING = "Unknown";
    this.NA_STRING = "N/A";
    this.operator = SystemProperties.get("ro.csc.sales_code", "Unknown").trim().toLowerCase();
    init();
  }
  
  /* Error */
  public static String GetTextFromFile(String paramString)
  {
    // Byte code:
    //   0: ldc 177
    //   2: astore_1
    //   3: aconst_null
    //   4: astore_2
    //   5: new 179	java/io/BufferedReader
    //   8: dup
    //   9: new 181	java/io/FileReader
    //   12: dup
    //   13: aload_0
    //   14: invokespecial 184	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   17: sipush 8096
    //   20: invokespecial 187	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   23: astore_3
    //   24: aload_3
    //   25: ifnull +15 -> 40
    //   28: aload_3
    //   29: invokevirtual 190	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   32: astore_1
    //   33: aload_1
    //   34: ifnonnull +23 -> 57
    //   37: ldc 177
    //   39: astore_1
    //   40: aload_3
    //   41: ifnull +173 -> 214
    //   44: aload_3
    //   45: invokevirtual 193	java/io/BufferedReader:close	()V
    //   48: aload_1
    //   49: ifnonnull +6 -> 55
    //   52: ldc 177
    //   54: astore_1
    //   55: aload_1
    //   56: areturn
    //   57: aload_1
    //   58: invokevirtual 128	java/lang/String:trim	()Ljava/lang/String;
    //   61: astore 13
    //   63: aload 13
    //   65: astore_1
    //   66: goto -26 -> 40
    //   69: astore 4
    //   71: aload 4
    //   73: invokevirtual 196	java/io/IOException:printStackTrace	()V
    //   76: goto -28 -> 48
    //   79: astore 5
    //   81: aload 5
    //   83: invokevirtual 197	java/io/FileNotFoundException:printStackTrace	()V
    //   86: aload_2
    //   87: ifnull -39 -> 48
    //   90: aload_2
    //   91: invokevirtual 193	java/io/BufferedReader:close	()V
    //   94: goto -46 -> 48
    //   97: astore 8
    //   99: aload 8
    //   101: invokevirtual 196	java/io/IOException:printStackTrace	()V
    //   104: goto -56 -> 48
    //   107: astore 9
    //   109: aload 9
    //   111: invokevirtual 196	java/io/IOException:printStackTrace	()V
    //   114: aload_2
    //   115: ifnull -67 -> 48
    //   118: aload_2
    //   119: invokevirtual 193	java/io/BufferedReader:close	()V
    //   122: goto -74 -> 48
    //   125: astore 10
    //   127: aload 10
    //   129: invokevirtual 196	java/io/IOException:printStackTrace	()V
    //   132: goto -84 -> 48
    //   135: astore 11
    //   137: aload 11
    //   139: invokevirtual 198	java/lang/NullPointerException:printStackTrace	()V
    //   142: aload_2
    //   143: ifnull -95 -> 48
    //   146: aload_2
    //   147: invokevirtual 193	java/io/BufferedReader:close	()V
    //   150: goto -102 -> 48
    //   153: astore 12
    //   155: aload 12
    //   157: invokevirtual 196	java/io/IOException:printStackTrace	()V
    //   160: goto -112 -> 48
    //   163: astore 6
    //   165: aload_2
    //   166: ifnull +7 -> 173
    //   169: aload_2
    //   170: invokevirtual 193	java/io/BufferedReader:close	()V
    //   173: aload 6
    //   175: athrow
    //   176: astore 7
    //   178: aload 7
    //   180: invokevirtual 196	java/io/IOException:printStackTrace	()V
    //   183: goto -10 -> 173
    //   186: astore 6
    //   188: aload_3
    //   189: astore_2
    //   190: goto -25 -> 165
    //   193: astore 11
    //   195: aload_3
    //   196: astore_2
    //   197: goto -60 -> 137
    //   200: astore 9
    //   202: aload_3
    //   203: astore_2
    //   204: goto -95 -> 109
    //   207: astore 5
    //   209: aload_3
    //   210: astore_2
    //   211: goto -130 -> 81
    //   214: goto -166 -> 48
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	217	0	paramString	String
    //   2	64	1	localObject1	Object
    //   4	207	2	localObject2	Object
    //   23	187	3	localBufferedReader	java.io.BufferedReader
    //   69	3	4	localIOException1	java.io.IOException
    //   79	3	5	localFileNotFoundException1	java.io.FileNotFoundException
    //   207	1	5	localFileNotFoundException2	java.io.FileNotFoundException
    //   163	11	6	localObject3	Object
    //   186	1	6	localObject4	Object
    //   176	3	7	localIOException2	java.io.IOException
    //   97	3	8	localIOException3	java.io.IOException
    //   107	3	9	localIOException4	java.io.IOException
    //   200	1	9	localIOException5	java.io.IOException
    //   125	3	10	localIOException6	java.io.IOException
    //   135	3	11	localNullPointerException1	NullPointerException
    //   193	1	11	localNullPointerException2	NullPointerException
    //   153	3	12	localIOException7	java.io.IOException
    //   61	3	13	str	String
    // Exception table:
    //   from	to	target	type
    //   44	48	69	java/io/IOException
    //   5	24	79	java/io/FileNotFoundException
    //   90	94	97	java/io/IOException
    //   5	24	107	java/io/IOException
    //   118	122	125	java/io/IOException
    //   5	24	135	java/lang/NullPointerException
    //   146	150	153	java/io/IOException
    //   5	24	163	finally
    //   81	86	163	finally
    //   109	114	163	finally
    //   137	142	163	finally
    //   169	173	176	java/io/IOException
    //   28	33	186	finally
    //   57	63	186	finally
    //   28	33	193	java/lang/NullPointerException
    //   57	63	193	java/lang/NullPointerException
    //   28	33	200	java/io/IOException
    //   57	63	200	java/io/IOException
    //   28	33	207	java/io/FileNotFoundException
    //   57	63	207	java/io/FileNotFoundException
  }
  
  private String ReadUniqueNumber()
  {
    FtUtil.log_i("SystemInfoView", "ReadUniqueNumber", "");
    if (new File("/sys/block/mmcblk0/device/cid").exists())
    {
      String str1 = GetTextFromFile("/sys/block/mmcblk0/device/cid");
      String str2 = GetTextFromFile("/sys/block/mmcblk0/device/name");
      String str3 = "";
      FtUtil.log_i("SystemInfoView", "ReadUniqueNumber", "cid : " + str1 + ", memory_name : " + str2);
      String str4 = "" + "c";
      String str5 = str1.substring(0, 2);
      if (str5.equalsIgnoreCase("15")) {
        str3 = str2.substring(0, 2);
      }
      for (;;)
      {
        String str6 = str4 + str3;
        String str7 = str6 + str1.substring(18, 20);
        String str8 = str7 + str1.substring(20, 28);
        String str9 = (str8 + str1.substring(28, 30)).toUpperCase();
        FtUtil.log_i("SystemInfoView", "ReadUniqueNumber", "Unique Number : " + str9);
        return str9;
        if ((str5.equalsIgnoreCase("02")) || (str5.equalsIgnoreCase("45"))) {
          str3 = str2.substring(3, 5);
        } else if (str5.equalsIgnoreCase("11")) {
          str3 = str2.substring(4, 6);
        }
      }
    }
    return this.DEFAULT_STRING;
  }
  
  private boolean check_over_length(int paramInt, String paramString)
  {
    int i = 2 * (paramInt / this.mTextSize);
    return paramString.length() >= i;
  }
  
  private String getBand()
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)this.mContext.getSystemService("phone");
    FtUtil.log_d("SystemInfoView", "getBand", "NetworkType : " + localTelephonyManager.getNetworkType());
    String str;
    switch (localTelephonyManager.getNetworkType())
    {
    default: 
      str = this.DEFAULT_STRING;
    }
    for (;;)
    {
      if ("none".equals(this.mCommunicationMode)) {
        str = this.NA_STRING;
      }
      FtUtil.log_i("SystemInfoView", "getBand", str);
      return str;
      str = "GPRS";
      continue;
      str = "EDGE";
      continue;
      str = "UMTS";
      continue;
      str = "CDMA";
      continue;
      str = "EVDO_0";
      continue;
      str = "EVDOA_A";
      continue;
      str = "1xRTT";
      continue;
      str = "HSDPA";
      continue;
      str = "HSUPA";
      continue;
      str = "HSPA";
      continue;
      str = "IDEN";
      continue;
      str = "EVDO_B";
      continue;
      str = "LTE";
      continue;
      str = "EHRPD";
      continue;
      str = "HSPAP";
    }
  }
  
  private String getBatteryLevel()
  {
    String str1 = Support.Feature.getString("DEVICE_TYPE");
    FtUtil.log_i("SystemInfoView", "getBatteryLevel", "typeDevice : " + str1);
    if (str1.equals("tablet"))
    {
      str2 = Support.Kernel.read("BATTERY_CAPACITY");
      if (str2 != null) {}
    }
    for (String str2 = this.NA_STRING;; str2 = this.NA_STRING)
    {
      FtUtil.log_i("SystemInfoView", "getBatteryLevel", "getBatteryLevel : " + str2);
      return str2;
    }
  }
  
  private String getCPChipName()
  {
    String str = this.mModuleCommon.getCPName();
    FtUtil.log_i("SystemInfoView", "getCPChipName", str);
    return str;
  }
  
  private String getCSCVersion()
  {
    String str = this.mModuleCommon.getCSCVer();
    FtUtil.log_i("SystemInfoView", "getCSCVersion", str);
    return str;
  }
  
  private String getCameraFirmwareVersion()
  {
    if ("SOC".equals(Support.Feature.getString("REAR_CAMERA_TYPE")))
    {
      if (Support.Feature.getBoolean("SUPPORT_FRONT_CAMERA")) {}
      for (String str5 = "CAMERA : " + this.NA_STRING + ", FrontCAM : " + this.NA_STRING;; str5 = "CAMERA : " + this.NA_STRING) {
        return str5;
      }
    }
    startCameraforFwRead();
    String str3;
    String str4;
    if (Support.Feature.getBoolean("SUPPORT_FRONT_CAMERA"))
    {
      str3 = this.mModuleCommon.readCameraRearFWver();
      str4 = this.mModuleCommon.readCameraFrontFWver();
      if (str3 == null) {
        str3 = this.NA_STRING;
      }
      if (str4 == null) {
        str4 = this.NA_STRING;
      }
    }
    String str1;
    for (String str2 = "RearCAM : " + str3 + ", FrontCAM : " + str4;; str2 = "CAMERA : " + str1)
    {
      stopCameraforFwRead();
      FtUtil.log_i("SystemInfoView", "getCameraFirmwareVersion", str2);
      return str2;
      str1 = this.mModuleCommon.readCameraRearFWver();
      if (str1 == null) {
        str1 = this.DEFAULT_STRING;
      }
    }
  }
  
  private String getChannel()
  {
    String str = SystemProperties.get("gsm.default.channel", this.DEFAULT_STRING);
    if (("none".equals(this.mCommunicationMode)) || ("gsm".equals(this.mCommunicationMode))) {
      str = this.NA_STRING;
    }
    FtUtil.log_i("SystemInfoView", "getChannel", str);
    return str;
  }
  
  private String getHWVersion()
  {
    String str = this.mModuleCommon.getHWVer();
    FtUtil.log_i("SystemInfoView", "getHWVersion", str);
    return str;
  }
  
  private String getLTEVersion()
  {
    String str = this.mModuleCommon.getLTEVer();
    boolean bool = Support.Feature.getBoolean("SUPPORT_LTE");
    if (!bool) {
      str = this.NA_STRING;
    }
    FtUtil.log_i("SystemInfoView", "getLTEVersion", "isLTE : " + bool);
    FtUtil.log_i("SystemInfoView", "getLTEVersion", "getLTEVersion :" + str);
    return str;
  }
  
  private String getMemorySize()
  {
    int i = Integer.parseInt(GetTextFromFile("/sys/block/mmcblk0/size"));
    String str;
    if (i < 10000000) {
      str = "Error";
    }
    for (;;)
    {
      FtUtil.log_i("SystemInfoView", "DisplyVerInFactoryMde", "memorySize : " + str);
      return str;
      if (i <= 20000000) {
        str = "8GB";
      } else if (i <= 40000000) {
        str = "16GB";
      } else if (i <= 70000000) {
        str = "32GB";
      } else {
        str = "64GB";
      }
    }
  }
  
  private String getPBA()
  {
    FactoryTestPhone localFactoryTestPhone;
    if (!FtUtil.isFactoryAppAPO()) {
      if (!this.operator.contains("ctc"))
      {
        localFactoryTestPhone = new FactoryTestPhone(this.mContext);
        localFactoryTestPhone.bindSecPhoneService();
        FtUtil.log_i("SystemInfoView", "getPBA", "Request for TestNV!");
        localFactoryTestPhone.requestTestNvViewToRil();
      }
    }
    for (int i = localFactoryTestPhone.getNVResult(Byte.toString((byte)4));; i = NVAccessor.getNV((byte)4))
    {
      String str = "04" + (char)i;
      FtUtil.log_i("SystemInfoView", "getPBA", "return value : " + str);
      return str;
      return SystemInfoService.pbaValue;
    }
  }
  
  private String getPDAVersion()
  {
    String str = this.mModuleCommon.getPDAVer();
    FtUtil.log_i("SystemInfoView", "getPDAVersion", str);
    return str;
  }
  
  private String getPhone2Version()
  {
    String str = this.mModuleCommon.getPhone2Ver();
    if ("none".equals(this.mCommunicationMode)) {
      str = this.NA_STRING;
    }
    FtUtil.log_i("SystemInfoView", "getPhoneVersion", str);
    return str;
  }
  
  private String getPhoneVersion()
  {
    String str = this.mModuleCommon.getMainSWVer();
    if ("none".equals(this.mCommunicationMode)) {
      str = this.NA_STRING;
    }
    FtUtil.log_i("SystemInfoView", "getPhoneVersion", str);
    return str;
  }
  
  private String getRFCalDate()
  {
    String str = SystemProperties.get("ril.rfcal_date", this.DEFAULT_STRING);
    if ("none".equals(this.mCommunicationMode)) {
      str = this.NA_STRING;
    }
    FtUtil.log_i("SystemInfoView", "getRFCalDate", str);
    return str;
  }
  
  private String getSMD()
  {
    FactoryTestPhone localFactoryTestPhone;
    if (!FtUtil.isFactoryAppAPO()) {
      if (!this.operator.contains("ctc"))
      {
        localFactoryTestPhone = new FactoryTestPhone(this.mContext);
        localFactoryTestPhone.bindSecPhoneService();
        FtUtil.log_i("SystemInfoView", "getSMD", "Request for TestNV!");
        localFactoryTestPhone.requestTestNvViewToRil();
      }
    }
    for (int i = localFactoryTestPhone.getNVResult(Byte.toString((byte)1));; i = NVAccessor.getNV((byte)1))
    {
      String str = "01" + (char)i;
      FtUtil.log_i("SystemInfoView", "getSMD", "return value : " + str);
      return str;
      return SystemInfoService.smdValue;
    }
  }
  
  private void getSystemInfo()
  {
    FtUtil.log_i("SystemInfoView", "getSystemInfo", null);
    if (this.mInfo[this.ID_PDA_VERSION].mIsShow) {
      this.mInfo[this.ID_PDA_VERSION].mData = ("PDA : " + getPDAVersion());
    }
    String str = "";
    if (Support.SystemInfo.getVisibility("SYS_INFO_PHONE2_VERSION")) {
      str = " / " + getPhone2Version();
    }
    if (Support.Feature.getBoolean("SUPPORT_DUALMODE")) {
      str = " / Chip Name " + getCPChipName();
    }
    if (this.mInfo[this.ID_PHONE_VERSION].mIsShow) {
      this.mInfo[this.ID_PHONE_VERSION].mData = ("Phone : " + getPhoneVersion() + str);
    }
    if (this.mInfo[this.ID_LTE_VERSION].mIsShow) {
      this.mInfo[this.ID_LTE_VERSION].mData = ("LTE : " + getLTEVersion());
    }
    if (this.mInfo[this.ID_CSC_VERSION].mIsShow) {
      this.mInfo[this.ID_CSC_VERSION].mData = ("CSC : " + getCSCVersion());
    }
    if (this.mInfo[this.ID_HW_VERSION].mIsShow) {
      this.mInfo[this.ID_HW_VERSION].mData = ("H/W : " + getHWVersion());
    }
    if (this.mInfo[this.ID_RF_CAL_DATE].mIsShow) {
      this.mInfo[this.ID_RF_CAL_DATE].mData = ("RF Cal Date : " + getRFCalDate());
    }
    if (this.mInfo[this.ID_SMD_N_PBA].mIsShow) {
      this.mInfo[this.ID_SMD_N_PBA].mData = ("SMD : " + getSMD() + ", PBA : " + getPBA());
    }
    if (this.mInfo[this.ID_CAMERA_FIRMWARE_VERSION].mIsShow) {
      this.mInfo[this.ID_CAMERA_FIRMWARE_VERSION].mData = getCameraFirmwareVersion();
    }
    if (this.mInfo[this.ID_TSP_FIRMWARE_VERSION].mIsShow) {
      this.mInfo[this.ID_TSP_FIRMWARE_VERSION].mData = ("TSP : " + getTSPFirmwareVersion());
    }
    if (this.mInfo[this.ID_TSK_FIRMWARE_VERSION].mIsShow) {
      this.mInfo[this.ID_TSK_FIRMWARE_VERSION].mData = ("TSK : " + getTSKFirmwareVersion());
    }
    if (this.mInfo[this.ID_BAND_N_CHANNEL].mIsShow) {
      this.mInfo[this.ID_BAND_N_CHANNEL].mData = ("Band : " + getBand() + ", CH : " + getChannel());
    }
    if (this.mInfo[this.ID_UART_N_USB].mIsShow) {
      this.mInfo[this.ID_UART_N_USB].mData = ("UART : " + getUART() + ", USB : " + getUSB());
    }
    if (this.mInfo[this.ID_UN].mIsShow) {
      this.mInfo[this.ID_UN].mData = ("UN : " + getUN());
    }
    if (this.mInfo[this.ID_BATTERY_LEVEL].mIsShow) {
      this.mInfo[this.ID_BATTERY_LEVEL].mData = ("BATT_LEVEL : " + getBatteryLevel());
    }
    if (this.mInfo[this.ID_MEMORY_SIZE].mIsShow) {
      this.mInfo[this.ID_MEMORY_SIZE].mData = ("Memory : " + getMemorySize());
    }
  }
  
  private String getTSKFirmwareVersion()
  {
    try
    {
      Thread.sleep(1000L);
      if (Support.TestCase.getEnabled("IS_TSK_SAME_AS_TSP")) {
        if (Support.TestCase.getEnabled("IS_TSP_STANDARD"))
        {
          str = this.mModuleTouchScreen.getTSPFirmwareVersionIC();
          FtUtil.log_i("SystemInfoView", "getTSKFirmwareVersion", str);
          if (str == null) {
            str = "N/A";
          }
          return str;
        }
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;)
      {
        localInterruptedException.printStackTrace();
        continue;
        String str = this.mModuleDevice.readModuleFirmwareVersion(1);
        continue;
        str = this.mModuleDevice.readModuleFirmwareVersion(2);
      }
    }
  }
  
  private String getTSPFirmwareVersion()
  {
    try
    {
      Thread.sleep(1000L);
      if (Support.TestCase.getEnabled("IS_TSP_STANDARD"))
      {
        str = this.mModuleTouchScreen.getTSPFirmwareVersionIC();
        FtUtil.log_i("SystemInfoView", "getTSPFirmwareVersion", str);
        if (str == null) {
          str = "N/A";
        }
        return str;
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;)
      {
        localInterruptedException.printStackTrace();
        continue;
        String str = this.mModuleDevice.readModuleFirmwareVersion(1);
      }
    }
  }
  
  private String getUART()
  {
    String str = SystemProperties.get("gsm.default.siomode", this.DEFAULT_STRING);
    if (("none".equals(this.mCommunicationMode)) || ("gsm".equals(this.mCommunicationMode))) {
      str = this.NA_STRING;
    }
    if ("NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL")))
    {
      if (!"0".equals(str)) {
        break label82;
      }
      str = "DM";
    }
    for (;;)
    {
      FtUtil.log_i("SystemInfoView", "getUART", str);
      return str;
      label82:
      if ("2".equals(str)) {
        str = "HFK";
      }
    }
  }
  
  private String getUN()
  {
    FactoryTestPhone localFactoryTestPhone;
    if (!FtUtil.isFactoryAppAPO())
    {
      localFactoryTestPhone = new FactoryTestPhone(this.mContext);
      localFactoryTestPhone.bindSecPhoneService();
      FtUtil.log_i("SystemInfoView", "getPBA", "Request for TestNV!");
      localFactoryTestPhone.requestForUniqueNumber();
    }
    for (String str = localFactoryTestPhone.getUniqueNumber();; str = SystemProperties.get("ril.unique_number", this.DEFAULT_STRING))
    {
      if ((this.DEFAULT_STRING.equals(str)) || (str == null)) {
        str = ReadUniqueNumber();
      }
      FtUtil.log_i("SystemInfoView", "getUN", str);
      return str;
    }
  }
  
  private String getUSB()
  {
    String str = SystemProperties.get("gsm.default.siomode", this.DEFAULT_STRING);
    if (("none".equals(this.mCommunicationMode)) || ("gsm".equals(this.mCommunicationMode))) {
      str = this.NA_STRING;
    }
    if ("NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL")))
    {
      if (!"0".equals(str)) {
        break label82;
      }
      str = "NULL";
    }
    for (;;)
    {
      FtUtil.log_i("SystemInfoView", "getUSB", str);
      return str;
      label82:
      if ("2".equals(str)) {
        str = "DM";
      }
    }
  }
  
  private void init()
  {
    FtUtil.log_i("SystemInfoView", "init", null);
    this.mInfo[this.ID_PDA_VERSION] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_PDA_VERSION"));
    this.mInfo[this.ID_PHONE_VERSION] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_PHONE_VERSION"));
    this.mInfo[this.ID_LTE_VERSION] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_LTE_VERSION"));
    this.mInfo[this.ID_CSC_VERSION] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_CSC_VERSION"));
    this.mInfo[this.ID_HW_VERSION] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_HW_VERSION"));
    this.mInfo[this.ID_RF_CAL_DATE] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_RF_CAL_DATA"));
    this.mInfo[this.ID_SMD_N_PBA] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_SMD_PDA"));
    this.mInfo[this.ID_CAMERA_FIRMWARE_VERSION] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_CAMERA_FIRM_VERSION"));
    this.mInfo[this.ID_TSP_FIRMWARE_VERSION] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_TSP_FIRM_VERSION"));
    this.mInfo[this.ID_TSK_FIRMWARE_VERSION] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_TSK_FIRM_VERSION"));
    this.mInfo[this.ID_BAND_N_CHANNEL] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_BAND_CHANNEL"));
    this.mInfo[this.ID_UART_N_USB] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_SIO_MODE"));
    this.mInfo[this.ID_UN] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_UNIQUE_NUMBER"));
    this.mInfo[this.ID_BATTERY_LEVEL] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_BATTERY_LEVEL"));
    this.mInfo[this.ID_MEMORY_SIZE] = new Info(Support.SystemInfo.getVisibility("SYS_INFO_MEMORY_SIZE"));
    if (Support.Feature.getBoolean("TSP_TSK_ALL_IN_ONE_TYPE")) {
      this.mInfo[this.ID_TSK_FIRMWARE_VERSION].mIsShow = false;
    }
    String[] arrayOfString = Support.SystemInfo.getPosition();
    if (arrayOfString.length == 6)
    {
      this.mCoordinate_Port_x = Integer.parseInt(arrayOfString[0].trim());
      this.mCoordinate_Port_y = Integer.parseInt(arrayOfString[1].trim());
      this.mCoordinate_Land_x = Integer.parseInt(arrayOfString[2].trim());
      this.mCoordinate_Land_y = Integer.parseInt(arrayOfString[3].trim());
      this.mWidth = Integer.parseInt(arrayOfString[4].trim());
      this.mHeight = Integer.parseInt(arrayOfString[5].trim());
    }
    for (;;)
    {
      this.mTextColor = Support.SystemInfo.getFontColor();
      this.mTextSize = Support.SystemInfo.getFontSize();
      this.mBgColor = Support.SystemInfo.getBGColor();
      this.mCommunicationMode = Support.Feature.getString("MODEL_COMMUNICATION_MODE");
      return;
      this.mCoordinate_Port_x = Integer.parseInt(arrayOfString[0].trim());
      this.mCoordinate_Port_y = Integer.parseInt(arrayOfString[1].trim());
      this.mCoordinate_Land_x = 5;
      this.mCoordinate_Land_y = 165;
    }
  }
  
  private boolean startCameraforFwRead()
  {
    FtUtil.log_d("SystemInfoView", "startCameraforFwRead", "openCameraDevice");
    if (this.mCameraDevice == null) {}
    try
    {
      this.mCameraDevice = Camera.open(0);
      Camera localCamera = this.mCameraDevice;
      boolean bool = false;
      if (localCamera != null) {
        bool = true;
      }
      return bool;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        FtUtil.log_e(localException);
      }
    }
  }
  
  private boolean stopCameraforFwRead()
  {
    FtUtil.log_d("SystemInfoView", "stopCameraforFwRead", "releaseCameraDevice");
    if (this.mCameraDevice != null)
    {
      this.mCameraDevice.release();
      this.mCameraDevice = null;
    }
    return true;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    FtUtil.log_i("SystemInfoView", "onDraw", null);
    getSystemInfo();
    Paint localPaint1 = new Paint(33);
    localPaint1.setStyle(Paint.Style.FILL);
    localPaint1.setColor(this.mTextColor);
    localPaint1.setTextSize(this.mTextSize);
    Paint localPaint2 = new Paint(1);
    localPaint2.setStyle(Paint.Style.FILL_AND_STROKE);
    localPaint2.setColor(this.mBgColor);
    int i;
    int j;
    if (this.mContext.getResources().getConfiguration().orientation == 1)
    {
      i = this.mCoordinate_Port_x;
      j = this.mCoordinate_Port_y;
    }
    while ((this.mWidth < 0) && (this.mHeight < 0))
    {
      int i5 = i - 10;
      int i6 = j - 2 * this.mTextSize;
      int i7 = getWidth() - i5;
      int i8 = j;
      int i9 = 0;
      for (;;)
      {
        if (i9 < this.mInfo.length)
        {
          if (this.mInfo[i9].mIsShow)
          {
            if ((i9 == this.ID_CAMERA_FIRMWARE_VERSION) && (check_over_length(i7 - i5, this.mInfo[i9].mData))) {
              i8 += this.mTextSize;
            }
            i8 += this.mTextSize;
          }
          i9++;
          continue;
          i = this.mCoordinate_Land_x;
          j = this.mCoordinate_Land_y;
          break;
        }
      }
      paramCanvas.drawRect(new RectF(i5, i6, i7, i8), localPaint2);
      int i10 = 0;
      if (i10 >= this.mInfo.length) {
        break label588;
      }
      if (this.mInfo[i10].mIsShow)
      {
        if (i10 != this.ID_CAMERA_FIRMWARE_VERSION) {
          break label434;
        }
        if (!check_over_length(i7 - i5, this.mInfo[i10].mData)) {
          break label410;
        }
        String[] arrayOfString = this.mInfo[i10].mData.split(",");
        arrayOfString[1] = arrayOfString[1].replaceFirst(" ", "");
        paramCanvas.drawText(arrayOfString[0], i, j, localPaint1);
        j += this.mTextSize;
        paramCanvas.drawText(arrayOfString[1], i, j, localPaint1);
      }
      for (;;)
      {
        j += this.mTextSize;
        i10++;
        break;
        label410:
        paramCanvas.drawText(this.mInfo[i10].mData, i, j, localPaint1);
        continue;
        label434:
        paramCanvas.drawText(this.mInfo[i10].mData, i, j, localPaint1);
      }
    }
    int k = i;
    int m = j;
    int n = i + this.mWidth;
    int i1 = j + this.mHeight;
    paramCanvas.drawRect(new RectF(k, m, n, i1), localPaint2);
    int i2 = k + this.mTextSize;
    int i3 = m + this.mTextSize;
    for (int i4 = 0; i4 < this.mInfo.length; i4++) {
      if (this.mInfo[i4].mIsShow)
      {
        i3 += this.mTextSize;
        paramCanvas.drawText(this.mInfo[i4].mData, i2, i3, localPaint1);
      }
    }
    label588:
    super.onDraw(paramCanvas);
  }
  
  private class Info
  {
    public String mData;
    public boolean mIsShow;
    
    public Info(boolean paramBoolean)
    {
      this.mIsShow = paramBoolean;
      this.mData = "";
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.systeminfo.SystemInfoView
 * JD-Core Version:    0.7.1
 */