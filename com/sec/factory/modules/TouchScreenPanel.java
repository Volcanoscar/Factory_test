package com.sec.factory.modules;

import android.os.Handler;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Kernel;

public class TouchScreenPanel
{
  private static int TSP_WHAT_DUMMY = 0;
  public static final int TSP_WHAT_SCOPE_MAX = -1 + TSP_WHAT_DUMMY;
  public static final int TSP_WHAT_SCOPE_MIN = TSP_WHAT_DUMMY;
  public static final int TSP_WHAT_STATUS_NA;
  public static final int TSP_WHAT_STATUS_NG;
  public static final int TSP_WHAT_STATUS_OK;
  private final String CLASS_NAME = "TouchScreenPanel";
  private final String TSP_CMD__ATMEL__READ_DELTA_NODE = "get_delta";
  private final String TSP_CMD__ATMEL__READ_DELTA_NODE_ALL = "run_delta_read";
  private final String TSP_CMD__ATMEL__READ_REFERENCE_NODE = "get_reference";
  private final String TSP_CMD__ATMEL__READ_REFERENCE_NODE_ALL = "run_reference_read";
  private final String TSP_CMD__COMMON__FW_UPDATE = "fw_update";
  private final String TSP_CMD__COMMON__FW_UPDATE__SUB_CMD__BUILT_IN = "0";
  private final String TSP_CMD__COMMON__FW_UPDATE__SUB_CMD__UMS_OR_SDCARD = "1";
  private final String TSP_CMD__COMMON__POWER_OFF = "module_off_master";
  private final String TSP_CMD__COMMON__POWER_OFF_SECOND_CHIP = "module_off_slave";
  private final String TSP_CMD__COMMON__POWER_ON = "module_on_master";
  private final String TSP_CMD__COMMON__POWER_ON_SECOND_CHIP = "module_on_slave";
  private final String TSP_CMD__COMMON__READ_CHIP_NAME = "get_chip_name";
  private final String TSP_CMD__COMMON__READ_CONFIG_VERSION = "get_config_ver";
  private final String TSP_CMD__COMMON__READ_FW_VERSION_BINARY = "get_fw_ver_bin";
  private final String TSP_CMD__COMMON__READ_FW_VERSION_IC = "get_fw_ver_ic";
  private final String TSP_CMD__COMMON__READ_NUMBER_X_CHANNEL = "get_x_num";
  private final String TSP_CMD__COMMON__READ_NUMBER_Y_CHANNEL = "get_y_num";
  private final String TSP_CMD__COMMON__READ_THRESHOLD = "get_threshold";
  private final String TSP_CMD__COMMON__READ_VENDOR_NAME = "get_chip_vendor";
  private final String TSP_CMD__CYPRESS__READ_DIFFERENCE_NODE = "get_difference";
  private final String TSP_CMD__CYPRESS__READ_DIFFERENCE_NODE_ALL = "run_difference_read";
  private final String TSP_CMD__CYPRESS__READ_GLOBAL_IDAC_NODE = "get_global_idac";
  private final String TSP_CMD__CYPRESS__READ_GLOBAL_IDAC_NODE_ALL = "run_global_idac_read";
  private final String TSP_CMD__CYPRESS__READ_LOCAL_IDAC_NODE = "get_local_idac";
  private final String TSP_CMD__CYPRESS__READ_LOCAL_IDAC_NODE_ALL = "run_local_idac_read";
  private final String TSP_CMD__CYPRESS__READ_RAW_COUNT_NODE = "get_raw_count";
  private final String TSP_CMD__CYPRESS__READ_RAW_COUNT_NODE_ALL = "run_raw_count_read";
  private final String TSP_CMD__MELFAS__READ_CM_ABS_NODE = "get_cm_abs";
  private final String TSP_CMD__MELFAS__READ_CM_ABS_NODE_ALL = "run_cm_abs_read";
  private final String TSP_CMD__MELFAS__READ_DELTA_NODE = "get_cm_delta";
  private final String TSP_CMD__MELFAS__READ_DELTA_NODE_ALL = "run_cm_delta_read";
  private final String TSP_CMD__MELFAS__READ_INSPECTION_NODE = "get_inspection";
  private final String TSP_CMD__MELFAS__READ_INSPECTION_NODE_ALL = "run_inspection_read";
  private final String TSP_CMD__MELFAS__READ_INTENSITY_NODE = "get_intensity";
  private final String TSP_CMD__MELFAS__READ_INTENSITY_NODE_ALL = "run_intensity_read";
  private final String TSP_CMD__MELFAS__READ_REFERENCE_NODE = "get_reference";
  private final String TSP_CMD__MELFAS__READ_REFERENCE_NODE_ALL = "run_reference_read";
  private final String TSP_CMD__SYNAPTICS__READ_RAWCAP_NODE = "get_rawcap";
  private final String TSP_CMD__SYNAPTICS__READ_RAWCAP_NODE_ALL = "run_rawcap_read";
  private final String TSP_CMD__SYNAPTICS__READ_REFERENCE_NODE = "get_reference";
  private final String TSP_CMD__SYNAPTICS__READ_REFERENCE_NODE_ALL = "run_reference_read";
  private final String TSP_CMD__SYNAPTICS__READ_RX_TO_RX_NODE = "get_rx_to_rx";
  private final String TSP_CMD__SYNAPTICS__READ_RX_TO_RX_NODE_ALL = "run_rx_to_rx_read";
  private final String TSP_CMD__SYNAPTICS__READ_TX_TO_GND_NODE = "get_tx_to_gnd";
  private final String TSP_CMD__SYNAPTICS__READ_TX_TO_GND_NODE_ALL = "run_tx_to_gnd_read";
  private final String TSP_CMD__SYNAPTICS__READ_TX_TO_TX_NODE = "get_tx_to_tx";
  private final String TSP_CMD__SYNAPTICS__READ_TX_TO_TX_NODE_ALL = "run_tx_to_tx_read";
  private final String TSP_CMD__ZINITIX__READ_DELTA_NODE = "get_delta";
  private final String TSP_CMD__ZINITIX__READ_DELTA_NODE_ALL = "run_delta_read";
  private final String TSP_CMD__ZINITIX__READ_REFERENCE_NODE = "get_reference";
  private final String TSP_CMD__ZINITIX__READ_REFERENCE_NODE_ALL = "run_reference_read";
  private final String TSP_CMD__ZINITIX__READ_SCANTIME_NODE = "get_scantime";
  private final String TSP_CMD__ZINITIX__READ_SCANTIME_NODE_ALL = "run_scantime_read";
  private long TSP_STATUS_WATING_TIME_OUT = 1000L;
  private final String TSP_STATUS__COMMON__FAIL = "FAIL";
  private final String TSP_STATUS__COMMON__NOT_APPLICABLE = "NOT_APPLICABLE";
  private final String TSP_STATUS__COMMON__OK = "OK";
  private final String TSP_STATUS__COMMON__RUNNING = "RUNNING";
  private final String TSP_STATUS__COMMON__WAITING = "WAITING";
  private String mDeviceType = null;
  private String mPanelType = null;
  private String mVendor = null;
  
  static
  {
    int i = TSP_WHAT_DUMMY;
    TSP_WHAT_DUMMY = i + 1;
    TSP_WHAT_STATUS_OK = i;
    int j = TSP_WHAT_DUMMY;
    TSP_WHAT_DUMMY = j + 1;
    TSP_WHAT_STATUS_NG = j;
    int k = TSP_WHAT_DUMMY;
    TSP_WHAT_DUMMY = k + 1;
    TSP_WHAT_STATUS_NA = k;
  }
  
  public TouchScreenPanel(String paramString1, String paramString2, String paramString3)
  {
    FtUtil.log_i("TouchScreenPanel", "TouchScreenPanel", "" + paramString1 + " , " + paramString2 + " , " + paramString3);
    this.mVendor = paramString1;
    this.mPanelType = paramString2;
    this.mDeviceType = paramString3;
  }
  
  private String getTSPCommandResult(String paramString1, String paramString2)
  {
    if (paramString2 != null) {
      paramString1 = paramString1 + "," + paramString2;
    }
    FtUtil.log_e("TouchScreenPanel", "getTSPCommandResult", "2. get Result => " + paramString1);
    String str1 = Support.Kernel.read("TSP_COMMAND_RESULT");
    if (str1 == null)
    {
      FtUtil.log_e("TouchScreenPanel", "getTSPCommandResult", "Fail - Result Read =>1 result == null");
      return "NG";
    }
    if (str1.contains(paramString1))
    {
      String str2 = str1.substring(1 + paramString1.length(), str1.length());
      FtUtil.log_e("TouchScreenPanel", "getTSPCommandResult", "result : " + str2);
      return str2;
    }
    FtUtil.log_e("TouchScreenPanel", "getTSPCommandResult", "(Write Command) " + paramString1 + " != (Result Command) " + str1);
    return "NG";
  }
  
  private String mapping_IDnCommand(int paramInt)
  {
    FtUtil.log_i("TouchScreenPanel", "mapping_IDnCommand", "TSP ID : " + ModuleTouchScreen.convertorTSPID(paramInt));
    if (paramInt == ModuleTouchScreen.TSP_ID__FW_UPDATE) {
      return "fw_update";
    }
    if (paramInt == ModuleTouchScreen.TSP_ID__FW_VERSION_BINARY) {
      return "get_fw_ver_bin";
    }
    if (paramInt == ModuleTouchScreen.TSP_ID__FW_VERSION_IC) {
      return "get_fw_ver_ic";
    }
    if (paramInt == ModuleTouchScreen.TSP_ID__CONFIG_VERSION) {
      return "get_config_ver";
    }
    if (paramInt == ModuleTouchScreen.TSP_ID__THRESHOLD) {
      return "get_threshold";
    }
    if (paramInt == ModuleTouchScreen.TSP_ID__POWER_OFF) {
      return "module_off_master";
    }
    if (paramInt == ModuleTouchScreen.TSP_ID__POWER_ON) {
      return "module_on_master";
    }
    if (paramInt == ModuleTouchScreen.TSP_ID__POWER_OFF_SECOND_CHIP) {
      return "module_off_slave";
    }
    if (paramInt == ModuleTouchScreen.TSP_ID__POWER_ON_SECOND_CHIP) {
      return "module_on_slave";
    }
    if (paramInt == ModuleTouchScreen.TSP_ID__VENDOR_NAME) {
      return "get_chip_vendor";
    }
    if (paramInt == ModuleTouchScreen.TSP_ID__CHIP_NAME) {
      return "get_chip_name";
    }
    if (paramInt == ModuleTouchScreen.TSP_ID__NUMBER_X_CHANNEL) {
      return "get_x_num";
    }
    if (paramInt == ModuleTouchScreen.TSP_ID__NUMBER_Y_CHANNEL) {
      return "get_y_num";
    }
    if (paramInt == ModuleTouchScreen.TSP_ID__REFERENCE__ALL_NODE__RETURN_MIN_MAX)
    {
      if (this.mVendor.equalsIgnoreCase("ATMEL")) {
        return "run_reference_read";
      }
      if (this.mVendor.equalsIgnoreCase("MELFAS")) {
        return "run_reference_read";
      }
      if (this.mVendor.equalsIgnoreCase("SYNAPTICS")) {
        return "run_reference_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__REFERENCE__ALL_NODE__RETURN_NONE)
    {
      if (this.mVendor.equalsIgnoreCase("ZINITIX")) {
        return "run_reference_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__REFERENCE__NODE)
    {
      if (this.mVendor.equalsIgnoreCase("ATMEL")) {
        return "get_reference";
      }
      if (this.mVendor.equalsIgnoreCase("MELFAS")) {
        return "get_reference";
      }
      if (this.mVendor.equalsIgnoreCase("ZINITIX")) {
        return "get_reference";
      }
      if (this.mVendor.equalsIgnoreCase("SYNAPTICS")) {
        return "get_reference";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__DELTA__ALL_NODE__RETURN_MIN_MAX)
    {
      if (this.mVendor.equalsIgnoreCase("MELFAS")) {
        return "run_cm_delta_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__DELTA__ALL_NODE__RETURN_NONE)
    {
      if (this.mVendor.equalsIgnoreCase("ATMEL")) {
        return "run_delta_read";
      }
      if (this.mVendor.equalsIgnoreCase("ZINITIX")) {
        return "run_delta_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__DELTA__NODE)
    {
      if (this.mVendor.equalsIgnoreCase("ATMEL")) {
        return "get_delta";
      }
      if (this.mVendor.equalsIgnoreCase("MELFAS")) {
        return "get_cm_delta";
      }
      if (this.mVendor.equalsIgnoreCase("ZINITIX")) {
        return "get_delta";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__CM_ABS__ALL_NODE__RETURN_MIN_MAX)
    {
      if (this.mVendor.equalsIgnoreCase("MELFAS")) {
        return "run_cm_abs_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__CM_ABS__NODE)
    {
      if (this.mVendor.equalsIgnoreCase("MELFAS")) {
        return "get_cm_abs";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__INSPECTION__ALL_NODE__RETURN_MIN_MAX)
    {
      if (this.mVendor.equalsIgnoreCase("MELFAS")) {
        return "run_inspection_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__INSPECTION__NODE)
    {
      if (this.mVendor.equalsIgnoreCase("MELFAS")) {
        return "get_inspection";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__INTENSITY__ALL_NODE__RETURN_MIN_MAX)
    {
      if (this.mVendor.equalsIgnoreCase("MELFAS")) {
        return "run_intensity_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__INTENSITY__NODE)
    {
      if (this.mVendor.equalsIgnoreCase("MELFAS")) {
        return "get_intensity";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__SCANTIME__ALL_NODE__RETURN_NONE)
    {
      if (this.mVendor.equalsIgnoreCase("ZINITIX")) {
        return "run_scantime_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__SCANTIME__NODE)
    {
      if (this.mVendor.equalsIgnoreCase("ZINITIX")) {
        return "get_scantime";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__RAWCAP__ALL_NODE__RETURN_MIN_MAX)
    {
      if (this.mVendor.equalsIgnoreCase("SYNAPTICS")) {
        return "run_rawcap_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__RAWCAP__NODE)
    {
      if (this.mVendor.equalsIgnoreCase("SYNAPTICS")) {
        return "get_rawcap";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__RX_TO_RX__ALL_NODE__RETURN_NONE)
    {
      if (this.mVendor.equalsIgnoreCase("SYNAPTICS")) {
        return "run_rx_to_rx_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__RX_TO_RX__NODE)
    {
      if (this.mVendor.equalsIgnoreCase("SYNAPTICS")) {
        return "get_rx_to_rx";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__TX_TO_TX__ALL_NODE__RETURN_NONE)
    {
      if (this.mVendor.equalsIgnoreCase("SYNAPTICS")) {
        return "run_tx_to_tx_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__TX_TO_TX__NODE)
    {
      if (this.mVendor.equalsIgnoreCase("SYNAPTICS")) {
        return "get_tx_to_tx";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__TX_TO_GND__ALL_NODE__RETURN_NONE)
    {
      if (this.mVendor.equalsIgnoreCase("SYNAPTICS")) {
        return "run_tx_to_gnd_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__TX_TO_GND__NODE)
    {
      if (this.mVendor.equalsIgnoreCase("SYNAPTICS")) {
        return "get_tx_to_gnd";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__RAW_COUNT__ALL_NODE__RETURN_MIN_MAX)
    {
      if (this.mVendor.equalsIgnoreCase("CYPRESS")) {
        return "run_raw_count_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__RAW_COUNT__NODE)
    {
      if (this.mVendor.equalsIgnoreCase("CYPRESS")) {
        return "get_raw_count";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__DIFFERENCE__ALL_NODE__RETURN_NONE)
    {
      if (this.mVendor.equalsIgnoreCase("CYPRESS")) {
        return "run_difference_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__DIFFERENCE__NODE)
    {
      if (this.mVendor.equalsIgnoreCase("CYPRESS")) {
        return "get_difference";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__LOCAL_IDAC__ALL_NODE__RETURN_NONE)
    {
      if (this.mVendor.equalsIgnoreCase("CYPRESS")) {
        return "run_local_idac_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__LOCAL_IDAC__NODE)
    {
      if (this.mVendor.equalsIgnoreCase("CYPRESS")) {
        return "get_local_idac";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__GLOBAL_IDAC__ALL_NODE__RETURN_NONE)
    {
      if (this.mVendor.equalsIgnoreCase("CYPRESS")) {
        return "run_global_idac_read";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__GLOBAL_IDAC__NODE)
    {
      if (this.mVendor.equalsIgnoreCase("CYPRESS")) {
        return "get_global_idac";
      }
    }
    else if (paramInt == ModuleTouchScreen.TSP_ID__EXPANSION__CONNECTION)
    {
      if (this.mVendor.equalsIgnoreCase("ATMEL")) {
        return "run_reference_read";
      }
      if (this.mVendor.equalsIgnoreCase("CYPRESS")) {
        return "run_raw_count_read";
      }
      if (this.mVendor.equalsIgnoreCase("MELFAS"))
      {
        if (this.mPanelType.equals("TFT"))
        {
          if (this.mDeviceType.equals("tablet")) {
            return "run_cm_abs_read";
          }
        }
        else if (this.mPanelType.equals("OCTA")) {
          return "run_cm_delta_read";
        }
      }
      else {
        if (!this.mVendor.equalsIgnoreCase("SILAB")) {
          break label1103;
        }
      }
    }
    for (;;)
    {
      return "NA";
      label1103:
      if (this.mVendor.equalsIgnoreCase("SYNAPTICS")) {
        return "run_rawcap_read";
      }
      if (!this.mVendor.equalsIgnoreCase("ZINITIX")) {}
    }
  }
  
  private String setTSPCommand(String paramString1, String paramString2)
  {
    FtUtil.log_e("TouchScreenPanel", "setTSPCommand", "command => " + paramString1);
    FtUtil.log_e("TouchScreenPanel", "setTSPCommand", "subCommand => " + paramString2);
    if (paramString2 != null) {
      paramString1 = paramString1 + "," + paramString2;
    }
    FtUtil.log_e("TouchScreenPanel", "setTSPCommand", "1. set Command => " + paramString1);
    if (Support.Kernel.write("TSP_COMMAND_CMD", paramString1.getBytes()))
    {
      String str1 = Support.Kernel.read("TSP_COMMAND_STATUS");
      if (str1 == null)
      {
        FtUtil.log_e("TouchScreenPanel", "setTSPCommand", "status == null");
        return "FAIL";
      }
      if (str1.equals("FAIL"))
      {
        FtUtil.log_e("TouchScreenPanel", "setTSPCommand", "status == fail");
        return "FAIL";
      }
      if (str1.equals("NOT_APPLICABLE"))
      {
        FtUtil.log_e("TouchScreenPanel", "setTSPCommand", "status == not applicable");
        return "NOT_APPLICABLE";
      }
      if (str1.equals("OK"))
      {
        FtUtil.log_e("TouchScreenPanel", "setTSPCommand", "status == ok");
        return "OK";
      }
      if (str1.equals("WAITING"))
      {
        FtUtil.log_e("TouchScreenPanel", "setTSPCommand", "status == waiting");
        FtUtil.log_i("TouchScreenPanel", "checkTSPStatus", null);
        long l = System.currentTimeMillis();
        String str2 = Support.Kernel.read("TSP_COMMAND_STATUS");
        if (str2.equals("OK")) {
          FtUtil.log_e("TouchScreenPanel", "checkTSPStatus", "finish => " + System.currentTimeMillis());
        }
        for (;;)
        {
          return str2;
          if (this.TSP_STATUS_WATING_TIME_OUT >= System.currentTimeMillis() - l) {
            break;
          }
          FtUtil.log_e("TouchScreenPanel", "checkTSPStatus", "finish => Time Out(" + this.TSP_STATUS_WATING_TIME_OUT + ")");
          str2 = "FAIL";
        }
      }
      if (str1.equals("RUNNING"))
      {
        FtUtil.log_e("TouchScreenPanel", "setTSPCommand", "status == other command is running");
        return "FAIL";
      }
      FtUtil.log_e("TouchScreenPanel", "setTSPCommand", "status == Unknown : " + str1);
      return "FAIL";
    }
    FtUtil.log_e("TouchScreenPanel", "setTSPCommand", "Fail - Command Write");
    return "FAIL";
  }
  
  public String getTSPResult(int paramInt, String paramString)
  {
    FtUtil.log_i("TouchScreenPanel", "getTSPResult", "TSP ID (Main) : " + ModuleTouchScreen.convertorTSPID(paramInt));
    String str1 = mapping_IDnCommand(paramInt);
    FtUtil.log_i("TouchScreenPanel", "getTSPResult", "Main Command : " + str1);
    FtUtil.log_i("TouchScreenPanel", "getTSPResult", "Sub Command : " + paramString);
    String str3;
    String str2;
    if (str1 != "NA")
    {
      str3 = setTSPCommand(str1, paramString);
      String str4 = getTSPCommandResult(str1, paramString);
      FtUtil.log_d("TouchScreenPanel", "getTSPResult", "status : " + str3);
      FtUtil.log_d("TouchScreenPanel", "getTSPResult", "result : " + str4);
      if (str3.equals("OK")) {
        str2 = str4;
      }
    }
    for (;;)
    {
      FtUtil.log_d("TouchScreenPanel", "getTSPResult", "returnValue : [" + str2 + "]");
      return str2;
      if (str3.equals("NOT_APPLICABLE"))
      {
        str2 = "NA";
      }
      else
      {
        str2 = "NG";
        continue;
        str2 = "NA";
      }
    }
  }
  
  public void getTSPResult(int paramInt, String paramString, Handler paramHandler)
  {
    TSPThread localTSPThread = new TSPThread(null);
    localTSPThread.setHandler(paramHandler);
    localTSPThread.run(paramInt, paramString);
  }
  
  private class TSPThread
    extends Thread
  {
    Handler mNotiHandler;
    
    private TSPThread() {}
    
    private void setHandler(Handler paramHandler)
    {
      this.mNotiHandler = paramHandler;
    }
    
    public void run(int paramInt, String paramString)
    {
      FtUtil.log_i("TouchScreenPanel", "TSPStatus.run", "TSP ID (Main) : " + ModuleTouchScreen.convertorTSPID(paramInt));
      String str1 = TouchScreenPanel.this.mapping_IDnCommand(paramInt);
      FtUtil.log_i("TouchScreenPanel", "TSPStatus.run", "Main Command : " + str1);
      FtUtil.log_i("TouchScreenPanel", "TSPStatus.run", "Sub Command : " + paramString);
      if (str1 != "NA")
      {
        String str2 = TouchScreenPanel.this.setTSPCommand(str1, paramString);
        String str3 = TouchScreenPanel.this.getTSPCommandResult(str1, paramString);
        FtUtil.log_d("TouchScreenPanel", "TSPStatus.run", "status : " + str2);
        FtUtil.log_d("TouchScreenPanel", "TSPStatus.run", "result : " + str3);
        if (str2.equals("OK"))
        {
          FtUtil.log_e("TouchScreenPanel", "TSPStatus.run", "sendMessage == TSP_WHAT_STATUS_OK");
          this.mNotiHandler.sendMessage(this.mNotiHandler.obtainMessage(TouchScreenPanel.TSP_WHAT_STATUS_OK, paramInt, -1, str3));
          return;
        }
        if (str2.equals("NOT_APPLICABLE"))
        {
          FtUtil.log_e("TouchScreenPanel", "TSPStatus.run", "sendMessage == TSP_WHAT_STATUS_NA");
          this.mNotiHandler.sendMessage(this.mNotiHandler.obtainMessage(TouchScreenPanel.TSP_WHAT_STATUS_NA, paramInt, -1));
          return;
        }
        FtUtil.log_e("TouchScreenPanel", "TSPStatus.run", "sendMessage == TSP_WHAT_STATUS_NG");
        this.mNotiHandler.sendMessage(this.mNotiHandler.obtainMessage(TouchScreenPanel.TSP_WHAT_STATUS_NG, paramInt, -1));
        return;
      }
      FtUtil.log_e("TouchScreenPanel", "TSPStatus.run", "command==null");
      FtUtil.log_e("TouchScreenPanel", "TSPStatus.run", "sendMessage == TSP_WHAT_STATUS_NA");
      this.mNotiHandler.sendMessage(this.mNotiHandler.obtainMessage(TouchScreenPanel.TSP_WHAT_STATUS_NA, paramInt, -1));
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.TouchScreenPanel
 * JD-Core Version:    0.7.1
 */