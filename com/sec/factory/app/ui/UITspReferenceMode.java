package com.sec.factory.app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Spec;
import com.sec.factory.support.Support.TestCase;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UITspReferenceMode
  extends UIBase
{
  private static int refMaxFlag;
  private static int refMaxFlag2 = 0;
  private static int refMinFlag = 0;
  private static int refMinFlag2;
  TextView ChipName;
  private final int FAIL = 2;
  private final int PASS = 1;
  TextView PanelFirmVer;
  TextView PassFail;
  TextView PhoneFirmVer;
  private final int READ_FAIL = 3;
  TextView TspMax;
  TextView TspMax2;
  TextView TspMin;
  TextView TspMin2;
  private String UNKNOWN = "UNKNOWN";
  ArrayList<Ref_value> insp_value = new ArrayList();
  private int ispassed = -1;
  private String mDeviceType;
  public Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      case 1: 
        FtUtil.log_d(UITspReferenceMode.this.CLASS_NAME, "mHandler()", "Display Tsp Info()");
        UITspReferenceMode.this.getChipsetName();
        UITspReferenceMode.this.getPhoneFirmware();
        UITspReferenceMode.this.getPanelFirmware();
        return;
      case 2: 
        FtUtil.log_d(UITspReferenceMode.this.CLASS_NAME, "mHandler()", "Display Result Data");
        UITspReferenceMode.this.displayTSPvalue();
        return;
      }
      FtUtil.log_d(UITspReferenceMode.this.CLASS_NAME, "mHandler()", "checkPassResult()");
      UITspReferenceMode.this.checkPassResult();
    }
  };
  private String mLcdType;
  private ModuleDevice mModuleDevice;
  private int mRefMax = 0;
  private int mRefMax2 = 0;
  private int mRefMin = 0;
  private int mRefMin2 = 0;
  private String mTspManufacture;
  private TextView mTspSpecText;
  private TextView mTspSpecText2;
  ArrayList<Ref_value> ref_value = new ArrayList();
  ArrayList<Ref_value> ref_value2 = new ArrayList();
  
  static
  {
    refMaxFlag = 0;
    refMinFlag2 = 0;
  }
  
  public UITspReferenceMode()
  {
    super("UITspReferenceMode", 36);
  }
  
  private void checkPassFail()
  {
    FtUtil.log_d(this.CLASS_NAME, "checkPassFail", "refMin = " + this.mRefMin + "  refMinFlag = " + refMinFlag);
    FtUtil.log_d(this.CLASS_NAME, "checkPassFail", "refMax = " + this.mRefMax + "  refMaxFlag = " + refMaxFlag);
    FtUtil.log_d(this.CLASS_NAME, "checkPassFail", "ispassed =" + this.ispassed);
    int k;
    if (Support.TestCase.getEnabled("IS_SECOND_SELFTEST"))
    {
      int i3 = this.mRefMin;
      int i4 = refMinFlag;
      k = 0;
      if (i3 > i4)
      {
        int i5 = this.mRefMin;
        k = 0;
        if (i5 < 999999)
        {
          int i6 = this.mRefMax;
          int i7 = refMaxFlag;
          k = 0;
          if (i6 < i7)
          {
            int i8 = this.mRefMax;
            k = 0;
            if (i8 > -999999)
            {
              int i9 = this.mRefMin2;
              int i10 = refMinFlag2;
              k = 0;
              if (i9 > i10)
              {
                int i11 = this.mRefMin2;
                k = 0;
                if (i11 < 999999)
                {
                  int i12 = this.mRefMax2;
                  int i13 = refMaxFlag2;
                  k = 0;
                  if (i12 < i13)
                  {
                    int i14 = this.mRefMax2;
                    k = 0;
                    if (i14 > -999999) {
                      k = 1;
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    while ((k != 0) && (this.ispassed != 3))
    {
      this.PassFail.setText("PASS");
      this.ispassed = 1;
      return;
      int i = this.mRefMin;
      int j = refMinFlag;
      k = 0;
      if (i > j)
      {
        int m = this.mRefMin;
        k = 0;
        if (m < 999999)
        {
          int n = this.mRefMax;
          int i1 = refMaxFlag;
          k = 0;
          if (n < i1)
          {
            int i2 = this.mRefMax;
            k = 0;
            if (i2 > -999999) {
              k = 1;
            }
          }
        }
      }
    }
    this.PassFail.setText("FAIL");
    this.ispassed = 2;
  }
  
  private void checkPassResult()
  {
    if (this.ispassed == 1)
    {
      FtUtil.log_d(this.CLASS_NAME, "checkPassResult()", "pass!!!");
      setResult(-1);
      finish();
    }
    while (this.ispassed != 2) {
      return;
    }
    setResult(0);
  }
  
  private void displayTSPvalue()
  {
    this.TspMin.setText(this.TspMin.getText() + " reference : " + this.mRefMin);
    this.TspMax.setText(this.TspMax.getText() + " reference : " + this.mRefMax);
    this.TspMin2.setText(this.TspMin2.getText() + " delta : " + this.mRefMin2);
    this.TspMax2.setText(this.TspMax2.getText() + " delta : " + this.mRefMax2);
    checkPassFail();
  }
  
  private void getChipsetName()
  {
    FtUtil.log_d(this.CLASS_NAME, "getChipsetName", null);
    if ("ATMEL".equals(this.mTspManufacture)) {}
    String str1;
    for (String str2 = this.mModuleDevice.tsp_module_mode(2);; str2 = str1 + ", " + this.mModuleDevice.startTSPTest("get_chip_name"))
    {
      FtUtil.log_d(this.CLASS_NAME, "getChipsetName", "ChipsetName = " + str2);
      this.ChipName.setText("TSP : " + str2);
      return;
      str1 = this.mModuleDevice.startTSPTest("get_chip_vendor");
    }
  }
  
  private void getPanelFirmware()
  {
    FtUtil.log_d(this.CLASS_NAME, "getPanelFirmware", null);
    if ("ATMEL".equals(this.mTspManufacture)) {}
    for (String str = this.mModuleDevice.tsp_module_mode(4);; str = this.mModuleDevice.startTSPTest("get_fw_ver_ic"))
    {
      FtUtil.log_d(this.CLASS_NAME, "getChipsetName", "getPanelFirmware = " + this.PanelFirmVer);
      this.PanelFirmVer.setText("Panel Firm Version : " + str);
      return;
    }
  }
  
  private void getPhoneFirmware()
  {
    FtUtil.log_d(this.CLASS_NAME, "getPhoneFirmware", null);
    if ("ATMEL".equals(this.mTspManufacture)) {}
    for (String str = this.mModuleDevice.tsp_module_mode(3);; str = this.mModuleDevice.startTSPTest("get_fw_ver_bin"))
    {
      FtUtil.log_d(this.CLASS_NAME, "getChipsetName", "PhoneFirmware = " + str);
      this.PhoneFirmVer.setText("Phone Firm Version : " + str);
      return;
    }
  }
  
  private String getReadValue()
  {
    FtUtil.log_d(this.CLASS_NAME, "getReadValue()", null);
    String str = "";
    if ("MELFAS".equals(this.mTspManufacture)) {
      if (("TFT".equals(this.mLcdType)) && ("tablet".equals(this.mDeviceType))) {
        str = this.mModuleDevice.startTSPTest("run_cm_abs_read");
      }
    }
    for (;;)
    {
      FtUtil.log_d(this.CLASS_NAME, "getReadValue()", "retrun value = " + str);
      return str;
      if ("OCTA".equals(this.mLcdType))
      {
        str = this.mModuleDevice.startTSPTest("run_cm_delta_read");
        continue;
        if ("SYNAPTICS".equals(this.mTspManufacture))
        {
          if (("TFT".equals(this.mLcdType)) && ("tablet".equals(this.mDeviceType))) {
            str = this.mModuleDevice.startTSPTest("run_rawcap_read");
          } else if ("OCTA".equals(this.mLcdType)) {
            str = this.mModuleDevice.startTSPTest("run_rawcap_read");
          }
        }
        else if ("ATMEL".equals(this.mTspManufacture))
        {
          if (((!"TFT".equals(this.mLcdType)) || (!"tablet".equals(this.mDeviceType))) && ("OCTA".equals(this.mLcdType))) {
            str = this.mModuleDevice.tsp_module_data_read("TSP_COMMAND_REFER_SET");
          }
        }
        else if (("STM".equals(this.mTspManufacture)) && ((!"TFT".equals(this.mLcdType)) || (!"tablet".equals(this.mDeviceType))) && ("OCTA".equals(this.mLcdType))) {
          str = this.mModuleDevice.startTSPTest("run_raw_read");
        }
      }
    }
  }
  
  /* Error */
  private String getReadValue2()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 122	com/sec/factory/app/ui/UITspReferenceMode:CLASS_NAME	Ljava/lang/String;
    //   4: ldc_w 297
    //   7: aconst_null
    //   8: invokestatic 147	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   11: ldc_w 263
    //   14: astore_1
    //   15: ldc_w 265
    //   18: aload_0
    //   19: getfield 210	com/sec/factory/app/ui/UITspReferenceMode:mTspManufacture	Ljava/lang/String;
    //   22: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   25: ifeq +77 -> 102
    //   28: ldc_w 267
    //   31: aload_0
    //   32: getfield 269	com/sec/factory/app/ui/UITspReferenceMode:mLcdType	Ljava/lang/String;
    //   35: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   38: ifeq +48 -> 86
    //   41: ldc_w 271
    //   44: aload_0
    //   45: getfield 273	com/sec/factory/app/ui/UITspReferenceMode:mDeviceType	Ljava/lang/String;
    //   48: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   51: ifeq +35 -> 86
    //   54: aload_0
    //   55: getfield 122	com/sec/factory/app/ui/UITspReferenceMode:CLASS_NAME	Ljava/lang/String;
    //   58: ldc_w 297
    //   61: new 125	java/lang/StringBuilder
    //   64: dup
    //   65: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   68: ldc_w 277
    //   71: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: aload_1
    //   75: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   81: invokestatic 147	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   84: aload_1
    //   85: areturn
    //   86: ldc_w 279
    //   89: aload_0
    //   90: getfield 269	com/sec/factory/app/ui/UITspReferenceMode:mLcdType	Ljava/lang/String;
    //   93: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   96: ifeq -42 -> 54
    //   99: goto -45 -> 54
    //   102: ldc_w 283
    //   105: aload_0
    //   106: getfield 210	com/sec/factory/app/ui/UITspReferenceMode:mTspManufacture	Ljava/lang/String;
    //   109: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   112: ifeq +45 -> 157
    //   115: ldc_w 267
    //   118: aload_0
    //   119: getfield 269	com/sec/factory/app/ui/UITspReferenceMode:mLcdType	Ljava/lang/String;
    //   122: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   125: ifeq +16 -> 141
    //   128: ldc_w 271
    //   131: aload_0
    //   132: getfield 273	com/sec/factory/app/ui/UITspReferenceMode:mDeviceType	Ljava/lang/String;
    //   135: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   138: ifne -84 -> 54
    //   141: ldc_w 279
    //   144: aload_0
    //   145: getfield 269	com/sec/factory/app/ui/UITspReferenceMode:mLcdType	Ljava/lang/String;
    //   148: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   151: ifeq -97 -> 54
    //   154: goto -100 -> 54
    //   157: ldc 208
    //   159: aload_0
    //   160: getfield 210	com/sec/factory/app/ui/UITspReferenceMode:mTspManufacture	Ljava/lang/String;
    //   163: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   166: ifeq +45 -> 211
    //   169: ldc_w 267
    //   172: aload_0
    //   173: getfield 269	com/sec/factory/app/ui/UITspReferenceMode:mLcdType	Ljava/lang/String;
    //   176: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   179: ifeq +16 -> 195
    //   182: ldc_w 271
    //   185: aload_0
    //   186: getfield 273	com/sec/factory/app/ui/UITspReferenceMode:mDeviceType	Ljava/lang/String;
    //   189: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   192: ifne -138 -> 54
    //   195: ldc_w 279
    //   198: aload_0
    //   199: getfield 269	com/sec/factory/app/ui/UITspReferenceMode:mLcdType	Ljava/lang/String;
    //   202: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   205: ifeq -151 -> 54
    //   208: goto -154 -> 54
    //   211: ldc_w 292
    //   214: aload_0
    //   215: getfield 210	com/sec/factory/app/ui/UITspReferenceMode:mTspManufacture	Ljava/lang/String;
    //   218: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   221: ifeq -167 -> 54
    //   224: ldc_w 267
    //   227: aload_0
    //   228: getfield 269	com/sec/factory/app/ui/UITspReferenceMode:mLcdType	Ljava/lang/String;
    //   231: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   234: ifeq +16 -> 250
    //   237: ldc_w 271
    //   240: aload_0
    //   241: getfield 273	com/sec/factory/app/ui/UITspReferenceMode:mDeviceType	Ljava/lang/String;
    //   244: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   247: ifne -193 -> 54
    //   250: ldc_w 279
    //   253: aload_0
    //   254: getfield 269	com/sec/factory/app/ui/UITspReferenceMode:mLcdType	Ljava/lang/String;
    //   257: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   260: ifeq -206 -> 54
    //   263: aload_0
    //   264: getfield 218	com/sec/factory/app/ui/UITspReferenceMode:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   267: ldc_w 299
    //   270: invokevirtual 236	com/sec/factory/modules/ModuleDevice:startTSPTest	(Ljava/lang/String;)Ljava/lang/String;
    //   273: astore_1
    //   274: goto -220 -> 54
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	277	0	this	UITspReferenceMode
    //   14	260	1	str	String
  }
  
  private void getTspValue()
  {
    Object localObject1 = Integer.valueOf(999999);
    Object localObject2 = Integer.valueOf(-999999);
    StringTokenizer localStringTokenizer = new StringTokenizer(getReadValue(), ",");
    while (localStringTokenizer.hasMoreTokens()) {
      this.ref_value.add(new Ref_value(localStringTokenizer.nextToken()));
    }
    if ((this.ref_value != null) && (this.ref_value.size() > 0) && (this.ref_value.get(0) != null)) {}
    try
    {
      Integer localInteger2 = Integer.valueOf(Integer.parseInt(((Ref_value)this.ref_value.get(0)).get_string()));
      localObject1 = localInteger2;
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      for (;;)
      {
        FtUtil.log_e(localNumberFormatException2);
      }
    }
    if ((this.ref_value != null) && (this.ref_value.size() > 1) && (this.ref_value.get(1) != null)) {}
    try
    {
      Integer localInteger1 = Integer.valueOf(Integer.parseInt(((Ref_value)this.ref_value.get(1)).get_string()));
      localObject2 = localInteger1;
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      for (;;)
      {
        Object localObject3;
        FtUtil.log_e(localNumberFormatException1);
      }
    }
    if (((Integer)localObject1).intValue() > ((Integer)localObject2).intValue())
    {
      localObject3 = localObject1;
      localObject1 = localObject2;
      localObject2 = localObject3;
    }
    FtUtil.log_d(this.CLASS_NAME, "getTspValue()", "min = " + localObject1 + ", max = " + localObject2);
    this.mRefMin = ((Integer)localObject1).intValue();
    this.mRefMax = ((Integer)localObject2).intValue();
  }
  
  private void getTspValue2()
  {
    Object localObject1 = Integer.valueOf(999999);
    Object localObject2 = Integer.valueOf(-999999);
    StringTokenizer localStringTokenizer = new StringTokenizer(getReadValue2(), ",");
    while (localStringTokenizer.hasMoreTokens()) {
      this.ref_value2.add(new Ref_value(localStringTokenizer.nextToken()));
    }
    if ((this.ref_value2 != null) && (this.ref_value2.size() > 0) && (this.ref_value2.get(0) != null)) {}
    try
    {
      Integer localInteger2 = Integer.valueOf(Integer.parseInt(((Ref_value)this.ref_value2.get(0)).get_string()));
      localObject1 = localInteger2;
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      for (;;)
      {
        FtUtil.log_e(localNumberFormatException2);
      }
    }
    if ((this.ref_value2 != null) && (this.ref_value2.size() > 1) && (this.ref_value2.get(1) != null)) {}
    try
    {
      Integer localInteger1 = Integer.valueOf(Integer.parseInt(((Ref_value)this.ref_value2.get(1)).get_string()));
      localObject2 = localInteger1;
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      for (;;)
      {
        Object localObject3;
        FtUtil.log_e(localNumberFormatException1);
      }
    }
    if (((Integer)localObject1).intValue() > ((Integer)localObject2).intValue())
    {
      localObject3 = localObject1;
      localObject1 = localObject2;
      localObject2 = localObject3;
    }
    FtUtil.log_d(this.CLASS_NAME, "getTspValue()", "min = " + localObject1 + ", max = " + localObject2);
    this.mRefMin2 = ((Integer)localObject1).intValue();
    this.mRefMax2 = ((Integer)localObject2).intValue();
  }
  
  private void init()
  {
    FtUtil.log_d(this.CLASS_NAME, "init()", "init_start");
    this.TspMin = ((TextView)findViewById(2131296762));
    this.TspMax = ((TextView)findViewById(2131296763));
    this.PhoneFirmVer = ((TextView)findViewById(2131296767));
    this.PanelFirmVer = ((TextView)findViewById(2131296768));
    this.ChipName = ((TextView)findViewById(2131296769));
    this.PassFail = ((TextView)findViewById(2131296771));
    this.mTspSpecText = ((TextView)findViewById(2131296761));
    this.mModuleDevice = ModuleDevice.instance(this);
    this.mTspSpecText2 = ((TextView)findViewById(2131296764));
    this.TspMin2 = ((TextView)findViewById(2131296765));
    this.TspMax2 = ((TextView)findViewById(2131296766));
  }
  
  private void setModelFeature()
  {
    this.mTspManufacture = Support.Feature.getString("TSP_MANUFACTURE");
    this.mLcdType = Support.Feature.getString("PANEL_TYPE");
    this.mDeviceType = Support.Feature.getString("DEVICE_TYPE");
    refMinFlag = Support.Spec.getInt("TSP_SELFTEST_MIN");
    refMaxFlag = Support.Spec.getInt("TSP_SELFTEST_MAX");
    FtUtil.log_d(this.CLASS_NAME, "setModelFeature()", "mTspManufacture = " + this.mTspManufacture);
    FtUtil.log_d(this.CLASS_NAME, "setModelFeature()", "refMinFlag = " + refMinFlag);
    FtUtil.log_d(this.CLASS_NAME, "setModelFeature()", "refMaxFlag = " + refMaxFlag);
    if (Support.TestCase.getEnabled("IS_SECOND_SELFTEST"))
    {
      refMinFlag2 = Support.Spec.getInt("TSP_SELFTEST_MIN2");
      refMaxFlag2 = Support.Spec.getInt("TSP_SELFTEST_MAX2");
      this.mTspSpecText.setText("Raw SPEC: (" + refMinFlag + "~" + refMaxFlag + ")");
      this.mTspSpecText2.setText("Delta SPEC: (" + refMinFlag2 + "~" + refMaxFlag2 + ")");
      return;
    }
    this.mTspSpecText.setText("TSP SELFTEST SPEC: (" + refMinFlag + "~" + refMaxFlag + ")");
    this.mTspSpecText2.setVisibility(8);
    this.TspMin2.setVisibility(8);
    this.TspMax2.setVisibility(8);
  }
  
  private void startTspSelfTest()
  {
    FtUtil.log_d(this.CLASS_NAME, "startTspSelfTest", null);
    getTspValue();
    if (Support.TestCase.getEnabled("IS_SECOND_SELFTEST")) {
      getTspValue2();
    }
    this.mHandler.sendEmptyMessage(1);
    this.mHandler.sendEmptyMessage(2);
    this.mHandler.sendEmptyMessage(3);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    FtUtil.log_d(this.CLASS_NAME, "onCreate()", "startOnCreate()");
    super.onCreate(paramBundle);
    FtUtil.setRemoveSystemUI(getWindow(), true);
    setContentView(2130903094);
    init();
    setModelFeature();
    new Thread(new Runnable()
    {
      public void run()
      {
        UITspReferenceMode.this.startTspSelfTest();
      }
    }).start();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    FtUtil.log_d(this.CLASS_NAME, "onDestroy", null);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    FtUtil.log_d(this.CLASS_NAME, "onKeyDown", "KeyEent=" + paramInt);
    switch (paramInt)
    {
    default: 
      return super.onKeyDown(paramInt, paramKeyEvent);
    }
    return true;
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    FtUtil.log_d(this.CLASS_NAME, "onKeyUp", "KeyEent=" + paramInt);
    switch (paramInt)
    {
    default: 
      return super.onKeyDown(paramInt, paramKeyEvent);
    }
    return true;
  }
  
  protected void onPause()
  {
    super.onPause();
    FtUtil.log_d(this.CLASS_NAME, "onPause", null);
  }
  
  protected void onResume()
  {
    super.onResume();
    FtUtil.log_d(this.CLASS_NAME, "onResume", null);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    FtUtil.log_d(this.CLASS_NAME, "onTouchEvent()", null);
    if (this.ispassed == 1)
    {
      FtUtil.log_d(this.CLASS_NAME, "onTouchEvent()", "pass!!!");
      setResult(-1);
      finish();
    }
    while (this.ispassed != 2) {
      return true;
    }
    setResult(0);
    finish();
    return true;
  }
  
  class Ref_value
  {
    String ref_string;
    
    public Ref_value(String paramString)
    {
      this.ref_string = paramString;
    }
    
    String get_string()
    {
      return this.ref_string;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UITspReferenceMode
 * JD-Core Version:    0.7.1
 */