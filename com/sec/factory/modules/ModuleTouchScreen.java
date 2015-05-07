package com.sec.factory.modules;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Spec;
import com.sec.factory.support.Support.TestCase;

public class ModuleTouchScreen
  extends ModuleObject
{
  private static int TSP_ID_DUMMY;
  public static final int TSP_ID_SCOPE_MAX;
  public static final int TSP_ID_SCOPE_MIN;
  public static final int TSP_ID__CHIP_NAME;
  public static final int TSP_ID__CM_ABS__ALL_NODE__RETURN_MIN_MAX;
  public static final int TSP_ID__CM_ABS__NODE;
  public static final int TSP_ID__CONFIG_VERSION;
  public static final int TSP_ID__DELTA__ALL_NODE__RETURN_MIN_MAX;
  public static final int TSP_ID__DELTA__ALL_NODE__RETURN_NONE;
  public static final int TSP_ID__DELTA__NODE;
  public static final int TSP_ID__DIFFERENCE__ALL_NODE__RETURN_NONE;
  public static final int TSP_ID__DIFFERENCE__NODE;
  public static final int TSP_ID__EXPANSION__CONNECTION;
  public static final int TSP_ID__FW_UPDATE;
  public static final int TSP_ID__FW_VERSION_BINARY;
  public static final int TSP_ID__FW_VERSION_IC;
  public static final int TSP_ID__GLOBAL_IDAC__ALL_NODE__RETURN_NONE;
  public static final int TSP_ID__GLOBAL_IDAC__NODE;
  public static final int TSP_ID__INSPECTION__ALL_NODE__RETURN_MIN_MAX;
  public static final int TSP_ID__INSPECTION__NODE;
  public static final int TSP_ID__INTENSITY__ALL_NODE__RETURN_MIN_MAX;
  public static final int TSP_ID__INTENSITY__NODE;
  public static final int TSP_ID__LOCAL_IDAC__ALL_NODE__RETURN_NONE;
  public static final int TSP_ID__LOCAL_IDAC__NODE;
  public static final int TSP_ID__NUMBER_X_CHANNEL;
  public static final int TSP_ID__NUMBER_Y_CHANNEL;
  public static final int TSP_ID__POWER_OFF;
  public static final int TSP_ID__POWER_OFF_SECOND_CHIP;
  public static final int TSP_ID__POWER_ON;
  public static final int TSP_ID__POWER_ON_SECOND_CHIP;
  public static final int TSP_ID__RAWCAP__ALL_NODE__RETURN_MIN_MAX;
  public static final int TSP_ID__RAWCAP__NODE;
  public static final int TSP_ID__RAW_COUNT__ALL_NODE__RETURN_MIN_MAX;
  public static final int TSP_ID__RAW_COUNT__NODE;
  public static final int TSP_ID__REFERENCE__ALL_NODE__RETURN_MIN_MAX;
  public static final int TSP_ID__REFERENCE__ALL_NODE__RETURN_NONE;
  public static final int TSP_ID__REFERENCE__NODE;
  public static final int TSP_ID__RX_TO_RX__ALL_NODE__RETURN_NONE;
  public static final int TSP_ID__RX_TO_RX__NODE;
  public static final int TSP_ID__SCANTIME__ALL_NODE__RETURN_NONE;
  public static final int TSP_ID__SCANTIME__NODE;
  public static final int TSP_ID__THRESHOLD;
  public static final int TSP_ID__TX_TO_GND__ALL_NODE__RETURN_NONE;
  public static final int TSP_ID__TX_TO_GND__NODE;
  public static final int TSP_ID__TX_TO_TX__ALL_NODE__RETURN_NONE;
  public static final int TSP_ID__TX_TO_TX__NODE;
  public static final int TSP_ID__VENDOR_NAME;
  public static final int TSP_WHAT_SCOPE_MAX = TouchScreenPanel.TSP_WHAT_SCOPE_MAX;
  public static final int TSP_WHAT_SCOPE_MIN;
  public static final int TSP_WHAT_STATUS_NA;
  public static final int TSP_WHAT_STATUS_NG;
  public static final int TSP_WHAT_STATUS_OK;
  private static ModuleTouchScreen mInstance = null;
  private boolean mIsStandardChannel;
  private TouchScreenPanel mTSP;
  private String mTSPChannelCountX = null;
  private String mTSPChannelCountY = null;
  private int mTSPConnectionSpec_Max;
  private int mTSPConnectionSpec_Min;
  private String mTSPDeviceType;
  Handler mTSPHandler = new Handler(getApplicationContext().getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      label241:
      do
      {
        try
        {
          FtUtil.log_d(ModuleTouchScreen.this.CLASS_NAME, "mTSPHandler.handleMessage", "msg.arg1 (TSP ID Main) : " + ModuleTouchScreen.convertorTSPID(paramAnonymousMessage.arg1));
          FtUtil.log_d(ModuleTouchScreen.this.CLASS_NAME, "mTSPHandler.handleMessage", "msg.obj : " + paramAnonymousMessage.obj.toString());
          if (paramAnonymousMessage.arg1 == ModuleTouchScreen.TSP_ID__EXPANSION__CONNECTION)
          {
            FtUtil.log_d(ModuleTouchScreen.this.CLASS_NAME, "mTSPHandler.handleMessage", "Connection");
            if (ModuleTouchScreen.this.mTSPNotiHandler != null)
            {
              if (paramAnonymousMessage.what != ModuleTouchScreen.TSP_WHAT_STATUS_OK) {
                break label272;
              }
              FtUtil.log_d(ModuleTouchScreen.this.CLASS_NAME, "mTSPHandler.handleMessage", "TSP_WHAT_STATUS_OK");
              String str1 = paramAnonymousMessage.obj.toString();
              FtUtil.log_d(ModuleTouchScreen.this.CLASS_NAME, "mTSPHandler.handleMessage", "result : " + str1);
              str2 = ModuleTouchScreen.this.checkTSPConnectionSpec(str1);
              if (!str2.equals("NG")) {
                break label241;
              }
              ModuleTouchScreen.this.mTSPNotiHandler.sendMessage(ModuleTouchScreen.this.mTSPNotiHandler.obtainMessage(ModuleTouchScreen.TSP_WHAT_STATUS_NG, ModuleTouchScreen.TSP_ID__EXPANSION__CONNECTION, -1));
            }
          }
          return;
        }
        catch (NullPointerException localNullPointerException)
        {
          String str2;
          for (;;)
          {
            FtUtil.log_e(ModuleTouchScreen.this.CLASS_NAME, "mTSPHandler.handleMessage", "msg.obj is NULL ");
          }
          ModuleTouchScreen.this.mTSPNotiHandler.sendMessage(ModuleTouchScreen.this.mTSPNotiHandler.obtainMessage(ModuleTouchScreen.TSP_WHAT_STATUS_OK, ModuleTouchScreen.TSP_ID__EXPANSION__CONNECTION, -1, str2));
          return;
        }
        if (paramAnonymousMessage.what == ModuleTouchScreen.TSP_WHAT_STATUS_NG)
        {
          FtUtil.log_d(ModuleTouchScreen.this.CLASS_NAME, "mTSPHandler.handleMessage", "TSP_WHAT_STATUS_NG");
          ModuleTouchScreen.this.mTSPNotiHandler.sendMessage(ModuleTouchScreen.this.mTSPNotiHandler.obtainMessage(ModuleTouchScreen.TSP_WHAT_STATUS_NG, ModuleTouchScreen.TSP_ID__EXPANSION__CONNECTION, -1));
          return;
        }
      } while (paramAnonymousMessage.what != ModuleTouchScreen.TSP_WHAT_STATUS_NA);
      label272:
      FtUtil.log_d(ModuleTouchScreen.this.CLASS_NAME, "mTSPHandler.handleMessage", "TSP_WHAT_STATUS_NA");
      ModuleTouchScreen.this.mTSPNotiHandler.sendMessage(ModuleTouchScreen.this.mTSPNotiHandler.obtainMessage(ModuleTouchScreen.TSP_WHAT_STATUS_NA, ModuleTouchScreen.TSP_ID__EXPANSION__CONNECTION, -1));
    }
  };
  private Handler mTSPNotiHandler;
  private String mTSPPanelType;
  private String mTSPVendorName;
  
  static
  {
    TSP_ID_DUMMY = 1;
    TSP_ID_SCOPE_MIN = TSP_ID_DUMMY;
    int i = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i + 1;
    TSP_ID__FW_UPDATE = i;
    int j = TSP_ID_DUMMY;
    TSP_ID_DUMMY = j + 1;
    TSP_ID__FW_VERSION_BINARY = j;
    int k = TSP_ID_DUMMY;
    TSP_ID_DUMMY = k + 1;
    TSP_ID__FW_VERSION_IC = k;
    int m = TSP_ID_DUMMY;
    TSP_ID_DUMMY = m + 1;
    TSP_ID__CONFIG_VERSION = m;
    int n = TSP_ID_DUMMY;
    TSP_ID_DUMMY = n + 1;
    TSP_ID__THRESHOLD = n;
    int i1 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i1 + 1;
    TSP_ID__POWER_OFF = i1;
    int i2 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i2 + 1;
    TSP_ID__POWER_ON = i2;
    int i3 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i3 + 1;
    TSP_ID__POWER_OFF_SECOND_CHIP = i3;
    int i4 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i4 + 1;
    TSP_ID__POWER_ON_SECOND_CHIP = i4;
    int i5 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i5 + 1;
    TSP_ID__VENDOR_NAME = i5;
    int i6 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i6 + 1;
    TSP_ID__CHIP_NAME = i6;
    int i7 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i7 + 1;
    TSP_ID__NUMBER_X_CHANNEL = i7;
    int i8 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i8 + 1;
    TSP_ID__NUMBER_Y_CHANNEL = i8;
    int i9 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i9 + 1;
    TSP_ID__REFERENCE__ALL_NODE__RETURN_MIN_MAX = i9;
    int i10 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i10 + 1;
    TSP_ID__REFERENCE__ALL_NODE__RETURN_NONE = i10;
    int i11 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i11 + 1;
    TSP_ID__REFERENCE__NODE = i11;
    int i12 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i12 + 1;
    TSP_ID__DELTA__ALL_NODE__RETURN_MIN_MAX = i12;
    int i13 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i13 + 1;
    TSP_ID__DELTA__ALL_NODE__RETURN_NONE = i13;
    int i14 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i14 + 1;
    TSP_ID__DELTA__NODE = i14;
    int i15 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i15 + 1;
    TSP_ID__CM_ABS__ALL_NODE__RETURN_MIN_MAX = i15;
    int i16 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i16 + 1;
    TSP_ID__CM_ABS__NODE = i16;
    int i17 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i17 + 1;
    TSP_ID__INSPECTION__ALL_NODE__RETURN_MIN_MAX = i17;
    int i18 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i18 + 1;
    TSP_ID__INSPECTION__NODE = i18;
    int i19 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i19 + 1;
    TSP_ID__INTENSITY__ALL_NODE__RETURN_MIN_MAX = i19;
    int i20 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i20 + 1;
    TSP_ID__INTENSITY__NODE = i20;
    int i21 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i21 + 1;
    TSP_ID__SCANTIME__ALL_NODE__RETURN_NONE = i21;
    int i22 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i22 + 1;
    TSP_ID__SCANTIME__NODE = i22;
    int i23 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i23 + 1;
    TSP_ID__RAWCAP__ALL_NODE__RETURN_MIN_MAX = i23;
    int i24 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i24 + 1;
    TSP_ID__RAWCAP__NODE = i24;
    int i25 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i25 + 1;
    TSP_ID__RX_TO_RX__ALL_NODE__RETURN_NONE = i25;
    int i26 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i26 + 1;
    TSP_ID__RX_TO_RX__NODE = i26;
    int i27 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i27 + 1;
    TSP_ID__TX_TO_TX__ALL_NODE__RETURN_NONE = i27;
    int i28 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i28 + 1;
    TSP_ID__TX_TO_TX__NODE = i28;
    int i29 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i29 + 1;
    TSP_ID__TX_TO_GND__ALL_NODE__RETURN_NONE = i29;
    int i30 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i30 + 1;
    TSP_ID__TX_TO_GND__NODE = i30;
    int i31 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i31 + 1;
    TSP_ID__RAW_COUNT__ALL_NODE__RETURN_MIN_MAX = i31;
    int i32 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i32 + 1;
    TSP_ID__RAW_COUNT__NODE = i32;
    int i33 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i33 + 1;
    TSP_ID__DIFFERENCE__ALL_NODE__RETURN_NONE = i33;
    int i34 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i34 + 1;
    TSP_ID__DIFFERENCE__NODE = i34;
    int i35 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i35 + 1;
    TSP_ID__LOCAL_IDAC__ALL_NODE__RETURN_NONE = i35;
    int i36 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i36 + 1;
    TSP_ID__LOCAL_IDAC__NODE = i36;
    int i37 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i37 + 1;
    TSP_ID__GLOBAL_IDAC__ALL_NODE__RETURN_NONE = i37;
    int i38 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i38 + 1;
    TSP_ID__GLOBAL_IDAC__NODE = i38;
    int i39 = TSP_ID_DUMMY;
    TSP_ID_DUMMY = i39 + 1;
    TSP_ID__EXPANSION__CONNECTION = i39;
    TSP_ID_SCOPE_MAX = -1 + TSP_ID_DUMMY;
    TSP_WHAT_SCOPE_MIN = TouchScreenPanel.TSP_WHAT_SCOPE_MIN;
    TSP_WHAT_STATUS_OK = TouchScreenPanel.TSP_WHAT_STATUS_OK;
    TSP_WHAT_STATUS_NG = TouchScreenPanel.TSP_WHAT_STATUS_NG;
    TSP_WHAT_STATUS_NA = TouchScreenPanel.TSP_WHAT_STATUS_NA;
  }
  
  private ModuleTouchScreen(Context paramContext)
  {
    super(paramContext, "ModuleTouchScreen");
    FtUtil.log_i(this.CLASS_NAME, "ModuleTouchScreen");
    setTSPInfo();
  }
  
  private String checkNodeHeight(int paramInt)
  {
    if (this.mIsStandardChannel) {}
    for (String str = getTSPChannelCountX(); str.equals("NG"); str = getTSPChannelCountY()) {
      return "NG";
    }
    if (str.equals("NA")) {
      return "NA";
    }
    if ((paramInt >= 0) && (paramInt < Integer.valueOf(str).intValue())) {
      return "OK";
    }
    return "NA";
  }
  
  private String checkTSPConnectionSpec(String paramString)
  {
    String[] arrayOfString = paramString.split(",");
    if (arrayOfString.length == 2)
    {
      int i = Integer.valueOf(arrayOfString[0].trim()).intValue();
      int j = Integer.valueOf(arrayOfString[1].trim()).intValue();
      FtUtil.log_i(this.CLASS_NAME, "checkTSPConnectionSpec", "min : " + i + " , max : " + j);
      int k;
      StringBuilder localStringBuilder;
      if ((this.mTSPConnectionSpec_Min < i) && (i < this.mTSPConnectionSpec_Max)) {
        if ((this.mTSPConnectionSpec_Min < j) && (j < this.mTSPConnectionSpec_Max))
        {
          k = 1;
          localStringBuilder = new StringBuilder();
          if (k == 0) {
            break label211;
          }
        }
      }
      label211:
      for (String str = "OK";; str = "NG")
      {
        return str + "," + arrayOfString[0] + "," + arrayOfString[1];
        FtUtil.log_e(this.CLASS_NAME, "checkTSPConnectionSpec", "Spec Out => MAX");
        k = 0;
        break;
        FtUtil.log_e(this.CLASS_NAME, "checkTSPConnectionSpec", "Spec Out => MIN");
        k = 0;
        break;
      }
    }
    FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Connection", "data.length!=2 => length:" + arrayOfString.length);
    return "NG";
  }
  
  public static String convertorTSPID(int paramInt)
  {
    if (paramInt == TSP_ID__FW_UPDATE) {
      return "TSP_ID__FW_UPDATE";
    }
    if (paramInt == TSP_ID__FW_VERSION_BINARY) {
      return "TSP_ID__FW_VERSION_BINARY";
    }
    if (paramInt == TSP_ID__FW_VERSION_IC) {
      return "TSP_ID__FW_VERSION_IC";
    }
    if (paramInt == TSP_ID__CONFIG_VERSION) {
      return "TSP_ID__CONFIG_VERSION";
    }
    if (paramInt == TSP_ID__THRESHOLD) {
      return "TSP_ID__THRESHOLD";
    }
    if (paramInt == TSP_ID__POWER_OFF) {
      return "TSP_ID__POWER_OFF";
    }
    if (paramInt == TSP_ID__POWER_ON) {
      return "TSP_ID__POWER_ON";
    }
    if (paramInt == TSP_ID__POWER_OFF_SECOND_CHIP) {
      return "TSP_ID__POWER_OFF_SECOND_CHIP";
    }
    if (paramInt == TSP_ID__POWER_ON_SECOND_CHIP) {
      return "TSP_ID__POWER_ON_SECOND_CHIP";
    }
    if (paramInt == TSP_ID__VENDOR_NAME) {
      return "TSP_ID__VENDOR_NAME";
    }
    if (paramInt == TSP_ID__CHIP_NAME) {
      return "TSP_ID__CHIP_NAME";
    }
    if (paramInt == TSP_ID__NUMBER_X_CHANNEL) {
      return "TSP_ID__NUMBER_X_CHANNEL";
    }
    if (paramInt == TSP_ID__NUMBER_Y_CHANNEL) {
      return "TSP_ID__NUMBER_Y_CHANNEL";
    }
    if (paramInt == TSP_ID__REFERENCE__ALL_NODE__RETURN_MIN_MAX) {
      return "TSP_ID__REFERENCE__ALL_NODE__RETURN_MIN_MAX";
    }
    if (paramInt == TSP_ID__REFERENCE__ALL_NODE__RETURN_NONE) {
      return "TSP_ID__REFERENCE__ALL_NODE__RETURN_NONE";
    }
    if (paramInt == TSP_ID__REFERENCE__NODE) {
      return "TSP_ID__REFERENCE__NODE";
    }
    if (paramInt == TSP_ID__DELTA__ALL_NODE__RETURN_MIN_MAX) {
      return "TSP_ID__DELTA__ALL_NODE__RETURN_MIN_MAX";
    }
    if (paramInt == TSP_ID__DELTA__ALL_NODE__RETURN_NONE) {
      return "TSP_ID__DELTA__ALL_NODE__RETURN_NONE";
    }
    if (paramInt == TSP_ID__DELTA__NODE) {
      return "TSP_ID__DELTA__NODE";
    }
    if (paramInt == TSP_ID__CM_ABS__ALL_NODE__RETURN_MIN_MAX) {
      return "TSP_ID__CM_ABS__ALL_NODE__RETURN_MIN_MAX";
    }
    if (paramInt == TSP_ID__CM_ABS__NODE) {
      return "TSP_ID__CM_ABS__NODE";
    }
    if (paramInt == TSP_ID__INSPECTION__ALL_NODE__RETURN_MIN_MAX) {
      return "TSP_ID__INSPECTION__ALL_NODE__RETURN_MIN_MAX";
    }
    if (paramInt == TSP_ID__INSPECTION__NODE) {
      return "TSP_ID__INSPECTION__NODE";
    }
    if (paramInt == TSP_ID__INTENSITY__ALL_NODE__RETURN_MIN_MAX) {
      return "TSP_ID__INTENSITY__ALL_NODE__RETURN_MIN_MAX";
    }
    if (paramInt == TSP_ID__INTENSITY__NODE) {
      return "TSP_ID__INTENSITY__NODE";
    }
    if (paramInt == TSP_ID__SCANTIME__ALL_NODE__RETURN_NONE) {
      return "TSP_ID__SCANTIME__ALL_NODE__RETURN_NONE";
    }
    if (paramInt == TSP_ID__SCANTIME__NODE) {
      return "TSP_ID__SCANTIME__NODE";
    }
    if (paramInt == TSP_ID__RAWCAP__ALL_NODE__RETURN_MIN_MAX) {
      return "TSP_ID__RAWCAP__ALL_NODE__RETURN_MIN_MAX";
    }
    if (paramInt == TSP_ID__RAWCAP__NODE) {
      return "TSP_ID__RAWCAP__NODE";
    }
    if (paramInt == TSP_ID__RX_TO_RX__ALL_NODE__RETURN_NONE) {
      return "TSP_ID__RX_TO_RX__ALL_NODE__RETURN_NONE";
    }
    if (paramInt == TSP_ID__RX_TO_RX__NODE) {
      return "TSP_ID__RX_TO_RX__NODE";
    }
    if (paramInt == TSP_ID__TX_TO_TX__ALL_NODE__RETURN_NONE) {
      return "TSP_ID__TX_TO_TX__ALL_NODE__RETURN_NONE";
    }
    if (paramInt == TSP_ID__TX_TO_TX__NODE) {
      return "TSP_ID__TX_TO_TX__NODE";
    }
    if (paramInt == TSP_ID__TX_TO_GND__ALL_NODE__RETURN_NONE) {
      return "TSP_ID__TX_TO_GND__ALL_NODE__RETURN_NONE";
    }
    if (paramInt == TSP_ID__TX_TO_GND__NODE) {
      return "TSP_ID__TX_TO_GND__NODE";
    }
    if (paramInt == TSP_ID__RAW_COUNT__ALL_NODE__RETURN_MIN_MAX) {
      return "TSP_ID__RAW_COUNT__ALL_NODE__RETURN_MIN_MAX";
    }
    if (paramInt == TSP_ID__RAW_COUNT__NODE) {
      return "TSP_ID__RAW_COUNT__NODE";
    }
    if (paramInt == TSP_ID__DIFFERENCE__ALL_NODE__RETURN_NONE) {
      return "TSP_ID__DIFFERENCE__ALL_NODE__RETURN_NONE";
    }
    if (paramInt == TSP_ID__DIFFERENCE__NODE) {
      return "TSP_ID__DIFFERENCE__NODE";
    }
    if (paramInt == TSP_ID__LOCAL_IDAC__ALL_NODE__RETURN_NONE) {
      return "TSP_ID__LOCAL_IDAC__ALL_NODE__RETURN_NONE";
    }
    if (paramInt == TSP_ID__LOCAL_IDAC__NODE) {
      return "TSP_ID__LOCAL_IDAC__NODE";
    }
    if (paramInt == TSP_ID__GLOBAL_IDAC__ALL_NODE__RETURN_NONE) {
      return "TSP_ID__GLOBAL_IDAC__ALL_NODE__RETURN_NONE";
    }
    if (paramInt == TSP_ID__GLOBAL_IDAC__NODE) {
      return "TSP_ID__GLOBAL_IDAC__NODE";
    }
    if (paramInt == TSP_ID__EXPANSION__CONNECTION) {
      return "TSP_ID__EXPANSION__CONNECTION";
    }
    return "Unknown";
  }
  
  public static String convertorTSPWhat(int paramInt)
  {
    if (paramInt == TSP_WHAT_STATUS_OK) {
      return "TSP_WHAT_STATUS_OK";
    }
    if (paramInt == TSP_WHAT_STATUS_NG) {
      return "TSP_WHAT_STATUS_NG";
    }
    if (paramInt == TSP_WHAT_STATUS_NA) {
      return "TSP_WHAT_STATUS_NA";
    }
    return "Unknown";
  }
  
  private String getTSPNodeAllWidth(int paramInt1, int paramInt2)
  {
    FtUtil.log_i(this.CLASS_NAME, "getTSPNodeAllWidth", "TSP ID : " + convertorTSPID(paramInt1));
    String str1 = "";
    if (this.mIsStandardChannel) {}
    for (String str2 = getTSPChannelCountY(); str2.equals("NG"); str2 = getTSPChannelCountX()) {
      return "NG";
    }
    if (str2.equals("NA")) {
      return "NA";
    }
    int i = Integer.valueOf(str2).intValue();
    for (int j = 0; j < i; j++)
    {
      if (this.mIsStandardChannel) {}
      for (String str3 = this.mTSP.getTSPResult(paramInt1, "" + paramInt2 + "," + j); str3.equals("NG"); str3 = this.mTSP.getTSPResult(paramInt1, "" + j + "," + paramInt2)) {
        return "NG";
      }
      if (str3.equals("NA")) {
        return "NA";
      }
      str1 = str1 + str3;
      if (j < i - 1) {
        str1 = str1 + ",";
      }
    }
    FtUtil.log_i(this.CLASS_NAME, "getTSPNodeAllWidth", "result : " + str1);
    return str1;
  }
  
  private String getTSPResult_Connection(Handler paramHandler)
  {
    if (paramHandler == null)
    {
      String str = this.mTSP.getTSPResult(TSP_ID__EXPANSION__CONNECTION, null);
      FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Connection", "result : [" + str + "]");
      if ((str.equals("NA")) || (str.equals("NG"))) {}
      do
      {
        return str;
        str = checkTSPConnectionSpec(str);
      } while (!str.equals("NG"));
      return "NG";
    }
    this.mTSP.getTSPResult(TSP_ID__EXPANSION__CONNECTION, null, this.mTSPHandler);
    return null;
  }
  
  public static ModuleTouchScreen instance(Context paramContext)
  {
    if (mInstance == null) {
      mInstance = new ModuleTouchScreen(paramContext);
    }
    return mInstance;
  }
  
  private void setTSPInfo()
  {
    FtUtil.log_i(this.CLASS_NAME, "setTSPInfo", null);
    this.mTSPNotiHandler = null;
    this.mTSPVendorName = Support.Feature.getString("TSP_MANUFACTURE");
    this.mTSPPanelType = Support.Feature.getString("PANEL_TYPE");
    this.mTSPDeviceType = Support.Feature.getString("DEVICE_TYPE");
    FtUtil.log_d(this.CLASS_NAME, "setTSPInfo", "mTSPVendorName : " + this.mTSPVendorName);
    FtUtil.log_d(this.CLASS_NAME, "setTSPInfo", "mTSPPanelType : " + this.mTSPPanelType);
    FtUtil.log_d(this.CLASS_NAME, "setTSPInfo", "mTSPDeviceType : " + this.mTSPDeviceType);
    this.mTSP = new TouchScreenPanel(this.mTSPVendorName, this.mTSPPanelType, this.mTSPDeviceType);
    this.mTSPConnectionSpec_Min = Support.Spec.getInt("TSP_SELFTEST_MIN");
    this.mTSPConnectionSpec_Max = Support.Spec.getInt("TSP_SELFTEST_MAX");
    this.mIsStandardChannel = Support.TestCase.getEnabled("IS_TSP_STANDARD_CHANNEL");
    FtUtil.log_d(this.CLASS_NAME, "setTSPInfo", "mIsStandardChannel : " + this.mIsStandardChannel);
  }
  
  public String getTSPChannelCountX()
  {
    if (this.mTSPChannelCountX == null)
    {
      String str = this.mTSP.getTSPResult(TSP_ID__NUMBER_X_CHANNEL, null);
      if ((str.equals("NG")) || (str.equals("NA"))) {
        return str;
      }
      this.mTSPChannelCountX = str;
      FtUtil.log_d(this.CLASS_NAME, "getTSPChannelCountX", "set X : " + this.mTSPChannelCountX);
    }
    FtUtil.log_d(this.CLASS_NAME, "getTSPChannelCountX", "X : " + this.mTSPChannelCountX);
    return this.mTSPChannelCountX;
  }
  
  public String getTSPChannelCountY()
  {
    if (this.mTSPChannelCountY == null)
    {
      String str = this.mTSP.getTSPResult(TSP_ID__NUMBER_Y_CHANNEL, null);
      if ((str.equals("NG")) || (str.equals("NA"))) {
        return str;
      }
      this.mTSPChannelCountY = str;
      FtUtil.log_d(this.CLASS_NAME, "getTSPChannelCountY", "set Y : " + this.mTSPChannelCountY);
    }
    FtUtil.log_d(this.CLASS_NAME, "getTSPChannelCountY", "Y : " + this.mTSPChannelCountY);
    return this.mTSPChannelCountY;
  }
  
  public String getTSPChipName()
  {
    String str = this.mTSP.getTSPResult(TSP_ID__CHIP_NAME, null);
    FtUtil.log_d(this.CLASS_NAME, "getTSPChipName", str);
    return str;
  }
  
  public int getTSPConnectionSpecMax()
  {
    return this.mTSPConnectionSpec_Max;
  }
  
  public int getTSPConnectionSpecMin()
  {
    return this.mTSPConnectionSpec_Min;
  }
  
  public String getTSPFirmwareVersionBinary()
  {
    String str = this.mTSP.getTSPResult(TSP_ID__FW_VERSION_BINARY, null);
    FtUtil.log_d(this.CLASS_NAME, "getTSPFirmwareVersionBinary", str);
    return str;
  }
  
  public String getTSPFirmwareVersionIC()
  {
    String str = this.mTSP.getTSPResult(TSP_ID__FW_VERSION_IC, null);
    FtUtil.log_d(this.CLASS_NAME, "getTSPFirmwareVersionIC", str);
    return str;
  }
  
  public String getTSPResult(int paramInt)
  {
    if (paramInt == TSP_ID__EXPANSION__CONNECTION) {
      return getTSPResult_Connection(null);
    }
    return this.mTSP.getTSPResult(paramInt, null);
  }
  
  public boolean getTSPResult(int paramInt, Handler paramHandler)
  {
    this.mTSPNotiHandler = paramHandler;
    if (paramInt == TSP_ID__EXPANSION__CONNECTION) {
      getTSPResult_Connection(paramHandler);
    }
    for (;;)
    {
      return true;
      this.mTSP.getTSPResult(paramInt, null, paramHandler);
    }
  }
  
  public boolean getTSPResult(int paramInt, String paramString, Handler paramHandler)
  {
    this.mTSPNotiHandler = paramHandler;
    if (paramInt == TSP_ID__EXPANSION__CONNECTION) {
      getTSPResult_Connection(paramHandler);
    }
    for (;;)
    {
      return true;
      this.mTSP.getTSPResult(paramInt, paramString, paramHandler);
    }
  }
  
  public String getTSPResult_Read(int paramInt1, int paramInt2)
  {
    FtUtil.log_i(this.CLASS_NAME, "getTSPResult_Read", "readNumber : " + paramInt1 + " , lineNumber : " + paramInt2);
    int i = paramInt2 - 1;
    FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "checkNodeHeight");
    String str1 = checkNodeHeight(i);
    if (!str1.equals("OK")) {
      return str1;
    }
    if (this.mTSPVendorName.equalsIgnoreCase("ATMEL"))
    {
      if (paramInt1 == 1)
      {
        String str15 = this.mTSP.getTSPResult(TSP_ID__REFERENCE__ALL_NODE__RETURN_MIN_MAX, null);
        FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "Read1 == result : " + str15);
        if ((str15.equals("NG")) || (str15.equals("NA"))) {
          return str15;
        }
        return getTSPNodeAllWidth(TSP_ID__REFERENCE__NODE, i);
      }
      if (paramInt1 == 2)
      {
        String str14 = this.mTSP.getTSPResult(TSP_ID__DELTA__ALL_NODE__RETURN_NONE, null);
        FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "Read2 == result : " + str14);
        if ((str14.equals("NG")) || (str14.equals("NA"))) {
          return str14;
        }
        return getTSPNodeAllWidth(TSP_ID__DELTA__NODE, i);
      }
    }
    else if (this.mTSPVendorName.equalsIgnoreCase("CYPRESS"))
    {
      if (paramInt1 == 1)
      {
        String str13 = this.mTSP.getTSPResult(TSP_ID__RAW_COUNT__ALL_NODE__RETURN_MIN_MAX, null);
        FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "Read1 == result : " + str13);
        if ((str13.equals("NG")) || (str13.equals("NA"))) {
          return str13;
        }
        return getTSPNodeAllWidth(TSP_ID__RAW_COUNT__NODE, i);
      }
      if (paramInt1 == 2)
      {
        String str12 = this.mTSP.getTSPResult(TSP_ID__DIFFERENCE__ALL_NODE__RETURN_NONE, null);
        FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "Read2 == result : " + str12);
        if ((str12.equals("NG")) || (str12.equals("NA"))) {
          return str12;
        }
        return getTSPNodeAllWidth(TSP_ID__DIFFERENCE__NODE, i);
      }
      if (paramInt1 == 3)
      {
        String str11 = this.mTSP.getTSPResult(TSP_ID__LOCAL_IDAC__ALL_NODE__RETURN_NONE, null);
        FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "Read3 == result : " + str11);
        if ((str11.equals("NG")) || (str11.equals("NA"))) {
          return str11;
        }
        return getTSPNodeAllWidth(TSP_ID__LOCAL_IDAC__NODE, i);
      }
      if (paramInt1 == 4)
      {
        String str10 = this.mTSP.getTSPResult(TSP_ID__GLOBAL_IDAC__ALL_NODE__RETURN_NONE, null);
        FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "Read4 == result : " + str10);
        if ((str10.equals("NG")) || (str10.equals("NA"))) {
          return str10;
        }
        return getTSPNodeAllWidth(TSP_ID__GLOBAL_IDAC__NODE, i);
      }
    }
    else if (this.mTSPVendorName.equalsIgnoreCase("MELFAS"))
    {
      if (paramInt1 == 1)
      {
        String str9 = this.mTSP.getTSPResult(TSP_ID__CM_ABS__ALL_NODE__RETURN_MIN_MAX, null);
        FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "Read1 == result : " + str9);
        if ((str9.equals("NG")) || (str9.equals("NA"))) {
          return str9;
        }
        return getTSPNodeAllWidth(TSP_ID__CM_ABS__NODE, i);
      }
      if (paramInt1 == 2)
      {
        String str8 = this.mTSP.getTSPResult(TSP_ID__DELTA__ALL_NODE__RETURN_MIN_MAX, null);
        FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "Read2 == result : " + str8);
        if ((str8.equals("NG")) || (str8.equals("NA"))) {
          return str8;
        }
        return getTSPNodeAllWidth(TSP_ID__DELTA__NODE, i);
      }
      if (paramInt1 == 3)
      {
        String str7 = this.mTSP.getTSPResult(TSP_ID__INTENSITY__ALL_NODE__RETURN_MIN_MAX, null);
        FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "Read3 == result : " + str7);
        if ((str7.equals("NG")) || (str7.equals("NA"))) {
          return str7;
        }
        return getTSPNodeAllWidth(TSP_ID__INTENSITY__NODE, i);
      }
    }
    else if (this.mTSPVendorName.equalsIgnoreCase("SILAB"))
    {
      if (paramInt1 == 1) {
        return "NG";
      }
      if (paramInt1 == 2) {
        return "NG";
      }
      if (paramInt1 == 3) {
        return "NG";
      }
    }
    else if (this.mTSPVendorName.equalsIgnoreCase("SYNAPTICS"))
    {
      if (paramInt1 == 1)
      {
        String str6 = this.mTSP.getTSPResult(TSP_ID__RAWCAP__ALL_NODE__RETURN_MIN_MAX, null);
        FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "Read1 == result : " + str6);
        if ((str6.equals("NG")) || (str6.equals("NA"))) {
          return str6;
        }
        return getTSPNodeAllWidth(TSP_ID__RAWCAP__NODE, i);
      }
      if (paramInt1 == 2)
      {
        String str5 = this.mTSP.getTSPResult(TSP_ID__RX_TO_RX__ALL_NODE__RETURN_NONE, null);
        FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "Read2 == result : " + str5);
        if ((str5.equals("NG")) || (str5.equals("NA"))) {
          return str5;
        }
        return getTSPNodeAllWidth(TSP_ID__RX_TO_RX__NODE, i);
      }
      if (paramInt1 == 3) {
        return "NG";
      }
      if (paramInt1 == 4) {
        return "NG";
      }
    }
    else if (this.mTSPVendorName.equalsIgnoreCase("ZINITIX"))
    {
      if (paramInt1 == 1)
      {
        String str4 = this.mTSP.getTSPResult(TSP_ID__REFERENCE__ALL_NODE__RETURN_NONE, null);
        FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "Read1 == result : " + str4);
        if (("NG".equals(str4)) || ("NA".equals(str4))) {
          return str4;
        }
        return getTSPNodeAllWidth(TSP_ID__REFERENCE__NODE, i);
      }
      if (paramInt1 == 2)
      {
        String str3 = this.mTSP.getTSPResult(TSP_ID__SCANTIME__ALL_NODE__RETURN_NONE, null);
        FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "Read2 == result : " + str3);
        if (("NG".equals(str3)) || ("NA".equals(str3))) {
          return str3;
        }
        return getTSPNodeAllWidth(TSP_ID__SCANTIME__NODE, i);
      }
      if (paramInt1 == 3)
      {
        String str2 = this.mTSP.getTSPResult(TSP_ID__DELTA__ALL_NODE__RETURN_NONE, null);
        FtUtil.log_d(this.CLASS_NAME, "getTSPResult_Read", "Read3 == result : " + str2);
        if (("NG".equals(str2)) || ("NA".equals(str2))) {
          return str2;
        }
        return getTSPNodeAllWidth(TSP_ID__DELTA__NODE, i);
      }
    }
    else
    {
      FtUtil.log_e(this.CLASS_NAME, "getTSPResult_Read", "Vendor Name : Unknown");
      return "NG";
    }
    FtUtil.log_e(this.CLASS_NAME, "getTSPResult_Read", "Read Number : " + paramInt1 + " (N/A)");
    return "NA";
  }
  
  public boolean isTSPSupport()
  {
    return (this.mTSPVendorName.equalsIgnoreCase("ATMEL")) || (this.mTSPVendorName.equalsIgnoreCase("CYPRESS")) || (this.mTSPVendorName.equalsIgnoreCase("MELFAS")) || (this.mTSPVendorName.equalsIgnoreCase("SILAB")) || (this.mTSPVendorName.equalsIgnoreCase("SYNAPTICS")) || (this.mTSPVendorName.equalsIgnoreCase("ZINITIX"));
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.ModuleTouchScreen
 * JD-Core Version:    0.7.1
 */