package com.sec.factory.app.ui.oldconcept;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TableRow;
import android.widget.TextView;
import com.sec.factory.app.ui.UIBase;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.modules.SensorDeviceInfo;
import com.sec.factory.support.FtUtil;

public class UIMagneticAlps
  extends UIBase
{
  private int WHAT_UPDATE = 1;
  private String mFeature;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == UIMagneticAlps.this.WHAT_UPDATE) {
        UIMagneticAlps.this.update();
      }
    }
  };
  private ModuleSensor mModuleSensor;
  private int[] mSenserID = null;
  private int mSensorID_ADC = this.mSensorID_None;
  private int mSensorID_DAC = this.mSensorID_None;
  private int mSensorID_Initialized = this.mSensorID_None;
  private int mSensorID_None = -1 + ModuleSensor.ID_SCOPE_MIN;
  private int mSensorID_Offset_H = this.mSensorID_None;
  private int mSensorID_Released = this.mSensorID_None;
  private int mSensorID_Self = this.mSensorID_None;
  private int mSensorID_Status = this.mSensorID_None;
  private TableRow mTableRow_ADC;
  private TableRow mTableRow_DAC;
  private TableRow mTableRow_Initialized;
  private TableRow mTableRow_Offset_H;
  private TableRow mTableRow_SX;
  private TableRow mTableRow_SY;
  private TableRow mTableRow_Status;
  private TextView mTextResult;
  private TextView mText_ADC_X;
  private TextView mText_ADC_Y;
  private TextView mText_ADC_Z;
  private TextView mText_DAC_X;
  private TextView mText_DAC_Y;
  private TextView mText_DAC_Z;
  private TextView mText_Initialized;
  private TextView mText_Offset_H_X;
  private TextView mText_Offset_H_Y;
  private TextView mText_Offset_H_Z;
  private TextView mText_SX;
  private TextView mText_SY;
  private TextView mText_Status;
  
  public UIMagneticAlps()
  {
    super("UIMagneticAlps", 20);
  }
  
  private void init()
  {
    this.mTableRow_Initialized = ((TableRow)findViewById(2131296286));
    this.mText_Initialized = ((TextView)findViewById(2131296288));
    this.mTableRow_Status = ((TableRow)findViewById(2131296289));
    this.mText_Status = ((TextView)findViewById(2131296291));
    this.mTableRow_SX = ((TableRow)findViewById(2131296321));
    this.mText_SX = ((TextView)findViewById(2131296323));
    this.mTableRow_SY = ((TableRow)findViewById(2131296324));
    this.mText_SY = ((TextView)findViewById(2131296326));
    this.mTableRow_DAC = ((TableRow)findViewById(2131296313));
    this.mText_DAC_X = ((TextView)findViewById(2131296315));
    this.mText_DAC_Y = ((TextView)findViewById(2131296316));
    this.mText_DAC_Z = ((TextView)findViewById(2131296317));
    this.mTableRow_ADC = ((TableRow)findViewById(2131296295));
    this.mText_ADC_X = ((TextView)findViewById(2131296297));
    this.mText_ADC_Y = ((TextView)findViewById(2131296298));
    this.mText_ADC_Z = ((TextView)findViewById(2131296299));
    this.mTableRow_Offset_H = ((TableRow)findViewById(2131296327));
    this.mText_Offset_H_X = ((TextView)findViewById(2131296329));
    this.mText_Offset_H_Y = ((TextView)findViewById(2131296330));
    this.mText_Offset_H_Z = ((TextView)findViewById(2131296331));
    this.mTextResult = ((TextView)findViewById(2131296300));
  }
  
  private void update()
  {
    FtUtil.log_e(this.CLASS_NAME, "update", null);
    int i = 1;
    label114:
    label636:
    String str1;
    label179:
    label233:
    int j;
    label298:
    label463:
    String str2;
    label398:
    label563:
    label713:
    StringBuilder localStringBuilder;
    if (this.mSensorID_None < this.mSensorID_Initialized)
    {
      this.mTableRow_Initialized.setVisibility(0);
      String[] arrayOfString6 = this.mModuleSensor.getData(this.mSensorID_Initialized);
      if (arrayOfString6 == null) {
        break label935;
      }
      if (arrayOfString6[2].equals("1"))
      {
        FtUtil.log_e(this.CLASS_NAME, "update", "Initialized - Pass");
        this.mText_Initialized.setText(arrayOfString6[2]);
        FtUtil.log_i(this.CLASS_NAME, "update", "Initialized Return : " + arrayOfString6[2]);
      }
    }
    else
    {
      if ((i == 1) && (this.mSensorID_None < this.mSensorID_Status))
      {
        this.mTableRow_Status.setVisibility(0);
        String[] arrayOfString5 = this.mModuleSensor.getData(this.mSensorID_Status);
        if (arrayOfString5 == null) {
          break label979;
        }
        if (!arrayOfString5[1].equals("OK")) {
          break label962;
        }
        FtUtil.log_e(this.CLASS_NAME, "update", "Status - Pass");
        this.mText_Status.setText(arrayOfString5[2]);
        FtUtil.log_i(this.CLASS_NAME, "update", "Retuen : " + arrayOfString5[1] + ", Status : " + arrayOfString5[2]);
      }
      if ((i == 1) && (this.mSensorID_None < this.mSensorID_DAC))
      {
        this.mTableRow_DAC.setVisibility(0);
        String[] arrayOfString4 = this.mModuleSensor.getData(this.mSensorID_DAC);
        if (arrayOfString4 == null) {
          break label1023;
        }
        if (!arrayOfString4[1].equals("OK")) {
          break label1006;
        }
        FtUtil.log_e(this.CLASS_NAME, "update", "DAC - Pass");
        this.mText_DAC_X.setText(arrayOfString4[2]);
        this.mText_DAC_Y.setText(arrayOfString4[3]);
        this.mText_DAC_Z.setText(arrayOfString4[4]);
        FtUtil.log_i(this.CLASS_NAME, "update", "Retuen : " + arrayOfString4[1] + ", [DAC]X:" + arrayOfString4[2] + ", Y:" + arrayOfString4[3] + ", Z:" + arrayOfString4[4]);
      }
      if ((i == 1) && (this.mSensorID_None < this.mSensorID_ADC))
      {
        this.mTableRow_ADC.setVisibility(0);
        String[] arrayOfString3 = this.mModuleSensor.getData(this.mSensorID_ADC);
        if (arrayOfString3 == null) {
          break label1087;
        }
        if (!arrayOfString3[1].equals("OK")) {
          break label1070;
        }
        FtUtil.log_e(this.CLASS_NAME, "update", "ADC - Pass");
        this.mText_ADC_X.setText(arrayOfString3[2]);
        this.mText_ADC_Y.setText(arrayOfString3[3]);
        this.mText_ADC_Z.setText(arrayOfString3[4]);
        FtUtil.log_i(this.CLASS_NAME, "update", "Retuen : " + arrayOfString3[1] + ", [ADC]X:" + arrayOfString3[2] + ", Y:" + arrayOfString3[3] + ", Z:" + arrayOfString3[4]);
      }
      if ((i == 1) && (this.mSensorID_None < this.mSensorID_Self))
      {
        this.mTableRow_SX.setVisibility(0);
        this.mTableRow_SY.setVisibility(0);
        String[] arrayOfString2 = this.mModuleSensor.getData(this.mSensorID_Self);
        if (arrayOfString2 == null) {
          break label1151;
        }
        if (!arrayOfString2[1].equals("OK")) {
          break label1134;
        }
        FtUtil.log_e(this.CLASS_NAME, "update", "Self - Pass");
        this.mText_SX.setText(arrayOfString2[2]);
        this.mText_SY.setText(arrayOfString2[3]);
        FtUtil.log_i(this.CLASS_NAME, "update", "Retuen : " + arrayOfString2[1] + ", SX:" + arrayOfString2[2] + ", SY:" + arrayOfString2[3]);
      }
      if ((i == 1) && (this.mSensorID_None < this.mSensorID_Released))
      {
        String[] arrayOfString1 = this.mModuleSensor.getData(this.mSensorID_Released);
        if (arrayOfString1 == null) {
          break label1205;
        }
        if (!arrayOfString1[2].equals("1")) {
          break label1188;
        }
        FtUtil.log_e(this.CLASS_NAME, "update", "Released - Pass");
        label770:
        FtUtil.log_i(this.CLASS_NAME, "update", "Released Return : " + arrayOfString1[2]);
      }
      label801:
      TextView localTextView1 = this.mTextResult;
      if (i == 0) {
        break label1222;
      }
      str1 = "PASS";
      label813:
      localTextView1.setText(str1);
      TextView localTextView2 = this.mTextResult;
      if (i == 0) {
        break label1229;
      }
      j = -16776961;
      label832:
      localTextView2.setTextColor(j);
      if ((!this.mIsFromLcdTest) && (i != 0)) {
        setTestResult((byte)80);
      }
      str2 = this.CLASS_NAME;
      localStringBuilder = new StringBuilder().append("Result:");
      if (i == 0) {
        break label1237;
      }
    }
    label935:
    label962:
    label979:
    label1237:
    for (String str3 = "PASS";; str3 = "FAIL")
    {
      FtUtil.log_i(str2, "update", str3);
      if ((i != 0) && (!this.mIsFromLcdTest)) {
        finishOnPassWithTimer();
      }
      return;
      FtUtil.log_e(this.CLASS_NAME, "update", "Initialized - Fail");
      i = 0;
      break;
      this.mText_Initialized.setText("NONE");
      FtUtil.log_e(this.CLASS_NAME, "update", "Initialized - Fail : null");
      i = 0;
      break label114;
      FtUtil.log_e(this.CLASS_NAME, "update", "Status - Fail");
      i = 0;
      break label179;
      this.mText_Status.setText("NONE");
      FtUtil.log_e(this.CLASS_NAME, "update", "Status - Fail : null");
      i = 0;
      break label233;
      label1006:
      FtUtil.log_e(this.CLASS_NAME, "update", "DAC - Fail");
      i = 0;
      break label298;
      label1023:
      this.mText_DAC_X.setText("NONE");
      this.mText_DAC_Y.setText("NONE");
      this.mText_DAC_Z.setText("NONE");
      FtUtil.log_e(this.CLASS_NAME, "update", "DAC - Fail : null");
      i = 0;
      break label398;
      FtUtil.log_e(this.CLASS_NAME, "update", "ADC - Fail");
      i = 0;
      break label463;
      this.mText_ADC_X.setText("NONE");
      this.mText_ADC_Y.setText("NONE");
      this.mText_ADC_Z.setText("NONE");
      FtUtil.log_e(this.CLASS_NAME, "update", "ADC - Fail : null");
      i = 0;
      break label563;
      label1134:
      FtUtil.log_e(this.CLASS_NAME, "update", "Self - Fail");
      i = 0;
      break label636;
      label1151:
      this.mText_SX.setText("NONE");
      this.mText_SY.setText("NONE");
      FtUtil.log_e(this.CLASS_NAME, "update", "Self - Fail : null");
      i = 0;
      break label713;
      FtUtil.log_e(this.CLASS_NAME, "update", "Released - Fail");
      i = 0;
      break label770;
      FtUtil.log_e(this.CLASS_NAME, "update", "Released - Fail : null");
      i = 0;
      break label801;
      str1 = "FAIL";
      break label813;
      j = -65536;
      break label832;
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903074);
    this.mIsFromLcdTest = getIntent().getBooleanExtra("IS_FROM_LCDTEST", false);
    this.mFeature = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_GEOMAGNETIC, SensorDeviceInfo.TARGET_XML);
    FtUtil.log_i(this.CLASS_NAME, "onCreate", "mFeature : " + this.mFeature);
    init();
  }
  
  protected void onPause()
  {
    super.onPause();
    this.mModuleSensor.SensorOff();
  }
  
  protected void onResume()
  {
    super.onResume();
    if ((this.mFeature.equals("HSCDTD004")) || (this.mFeature.equals("HSCDTD004A")) || (this.mFeature.equals("HSCDTD006A")) || (this.mFeature.equals("HSCDTD008A")))
    {
      this.mSensorID_Initialized = ModuleSensor.ID_MANAGER_MAGNETIC_POWER_ON;
      this.mSensorID_Status = ModuleSensor.ID_FILE____MAGNETIC_STATUS;
      this.mSensorID_ADC = ModuleSensor.ID_FILE____MAGNETIC_ADC;
    }
    int[] arrayOfInt = new int[3];
    arrayOfInt[0] = this.mSensorID_Initialized;
    arrayOfInt[1] = this.mSensorID_Status;
    arrayOfInt[2] = this.mSensorID_ADC;
    this.mSenserID = arrayOfInt;
    this.mModuleSensor = ModuleSensor.instance(this);
    this.mModuleSensor.SensorOn(this.mSenserID);
    this.mHandler.sendEmptyMessageDelayed(this.WHAT_UPDATE, 500L);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.oldconcept.UIMagneticAlps
 * JD-Core Version:    0.7.1
 */