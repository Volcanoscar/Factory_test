package com.sec.factory.app.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;

public class FragmentGeomagneticYamaha
  extends Fragment
{
  private final String CLASS_NAME = "FragmentGeomagneticYamaha";
  private TableRow mTableRow_ADC;
  private TableRow mTableRow_DAC;
  private TableRow mTableRow_Initialized;
  private TableRow mTableRow_Offset_H;
  private TableRow mTableRow_SX;
  private TableRow mTableRow_SY;
  private TableRow mTableRow_Temp;
  private TextView mTextResult;
  private TextView mText_ADC_Title;
  private TextView mText_ADC_X;
  private TextView mText_ADC_Y;
  private TextView mText_ADC_Z;
  private TextView mText_DAC_Title;
  private TextView mText_DAC_X;
  private TextView mText_DAC_Y;
  private TextView mText_DAC_Z;
  private TextView mText_Initialized;
  private TextView mText_Initialized_Title;
  private TextView mText_Offset_H_Title;
  private TextView mText_Offset_H_X;
  private TextView mText_Offset_H_Y;
  private TextView mText_Offset_H_Z;
  private TextView mText_SX;
  private TextView mText_SX_Title;
  private TextView mText_SY;
  private TextView mText_SY_Title;
  private TextView mText_Temp;
  private TextView mText_Temp_Title;
  private TextView mText_Title;
  private TextView mText_X_Title;
  private TextView mText_Y_Title;
  private TextView mText_Z_Title;
  
  private void init(View paramView)
  {
    FtUtil.log_d("FragmentGeomagneticYamaha", "init", null);
    this.mText_Title = ((TextView)paramView.findViewById(2131296285));
    this.mTableRow_Initialized = ((TableRow)paramView.findViewById(2131296286));
    this.mText_Initialized_Title = ((TextView)paramView.findViewById(2131296287));
    this.mText_Initialized = ((TextView)paramView.findViewById(2131296288));
    this.mTableRow_Temp = ((TableRow)paramView.findViewById(2131296301));
    this.mText_Temp_Title = ((TextView)paramView.findViewById(2131296302));
    this.mText_Temp = ((TextView)paramView.findViewById(2131296303));
    this.mTableRow_SX = ((TableRow)paramView.findViewById(2131296321));
    this.mText_SX_Title = ((TextView)paramView.findViewById(2131296322));
    this.mText_SX = ((TextView)paramView.findViewById(2131296323));
    this.mTableRow_SY = ((TableRow)paramView.findViewById(2131296324));
    this.mText_SY_Title = ((TextView)paramView.findViewById(2131296325));
    this.mText_SY = ((TextView)paramView.findViewById(2131296326));
    this.mText_X_Title = ((TextView)paramView.findViewById(2131296292));
    this.mText_Y_Title = ((TextView)paramView.findViewById(2131296293));
    this.mText_Z_Title = ((TextView)paramView.findViewById(2131296294));
    this.mTableRow_DAC = ((TableRow)paramView.findViewById(2131296313));
    this.mText_DAC_Title = ((TextView)paramView.findViewById(2131296314));
    this.mText_DAC_X = ((TextView)paramView.findViewById(2131296315));
    this.mText_DAC_Y = ((TextView)paramView.findViewById(2131296316));
    this.mText_DAC_Z = ((TextView)paramView.findViewById(2131296317));
    this.mTableRow_ADC = ((TableRow)paramView.findViewById(2131296295));
    this.mText_ADC_Title = ((TextView)paramView.findViewById(2131296296));
    this.mText_ADC_X = ((TextView)paramView.findViewById(2131296297));
    this.mText_ADC_Y = ((TextView)paramView.findViewById(2131296298));
    this.mText_ADC_Z = ((TextView)paramView.findViewById(2131296299));
    this.mTableRow_Offset_H = ((TableRow)paramView.findViewById(2131296327));
    this.mText_Offset_H_Title = ((TextView)paramView.findViewById(2131296328));
    this.mText_Offset_H_X = ((TextView)paramView.findViewById(2131296329));
    this.mText_Offset_H_Y = ((TextView)paramView.findViewById(2131296330));
    this.mText_Offset_H_Z = ((TextView)paramView.findViewById(2131296331));
    this.mTextResult = ((TextView)paramView.findViewById(2131296300));
  }
  
  private void setUIRate()
  {
    float f = Support.FactoryTestMenu.getUIRate(FactoryTestManager.convertorID_XML());
    FtUtil.log_d("FragmentGeomagneticYamaha", "setUIRate", "rate : " + f);
    if ((f != 0.0F) && (f != 1.0F))
    {
      this.mText_Title.setTextSize(0, f * this.mText_Title.getTextSize());
      this.mText_Initialized_Title.setTextSize(0, f * this.mText_Initialized_Title.getTextSize());
      this.mText_Initialized.setTextSize(0, f * this.mText_Initialized.getTextSize());
      this.mText_Temp_Title.setTextSize(0, f * this.mText_Temp_Title.getTextSize());
      this.mText_Temp.setTextSize(0, f * this.mText_Temp.getTextSize());
      this.mText_SX_Title.setTextSize(0, f * this.mText_SX_Title.getTextSize());
      this.mText_SX.setTextSize(0, f * this.mText_SX.getTextSize());
      this.mText_SY_Title.setTextSize(0, f * this.mText_SY_Title.getTextSize());
      this.mText_SY.setTextSize(0, f * this.mText_SY.getTextSize());
      this.mText_X_Title.setTextSize(0, f * this.mText_X_Title.getTextSize());
      this.mText_Y_Title.setTextSize(0, f * this.mText_Y_Title.getTextSize());
      this.mText_Z_Title.setTextSize(0, f * this.mText_Z_Title.getTextSize());
      this.mText_DAC_Title.setTextSize(0, f * this.mText_DAC_Title.getTextSize());
      this.mText_DAC_X.setTextSize(0, f * this.mText_DAC_X.getTextSize());
      this.mText_DAC_Y.setTextSize(0, f * this.mText_DAC_Y.getTextSize());
      this.mText_DAC_Z.setTextSize(0, f * this.mText_DAC_Z.getTextSize());
      this.mText_ADC_Title.setTextSize(0, f * this.mText_ADC_Title.getTextSize());
      this.mText_ADC_X.setTextSize(0, f * this.mText_ADC_X.getTextSize());
      this.mText_ADC_Y.setTextSize(0, f * this.mText_ADC_Y.getTextSize());
      this.mText_ADC_Z.setTextSize(0, f * this.mText_ADC_Z.getTextSize());
      this.mText_Offset_H_Title.setTextSize(0, f * this.mText_Offset_H_Title.getTextSize());
      this.mText_Offset_H_X.setTextSize(0, f * this.mText_Offset_H_X.getTextSize());
      this.mText_Offset_H_Y.setTextSize(0, f * this.mText_Offset_H_Y.getTextSize());
      this.mText_Offset_H_Z.setTextSize(0, f * this.mText_Offset_H_Z.getTextSize());
      this.mTextResult.setTextSize(0, f * this.mTextResult.getTextSize());
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    FtUtil.log_d("FragmentGeomagneticYamaha", "onCreateView", null);
    View localView = paramLayoutInflater.inflate(2130903048, null);
    init(localView);
    setUIRate();
    return localView;
  }
  
  public void update(DataGeomagneticYamaha paramDataGeomagneticYamaha, Handler paramHandler)
  {
    FtUtil.log_e("FragmentGeomagneticYamaha", "update", null);
    if (paramDataGeomagneticYamaha == null)
    {
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Warning: data is null.");
      return;
    }
    int i = 1;
    label108:
    label255:
    String str;
    label145:
    label188:
    label357:
    label870:
    TextView localTextView2;
    if (paramDataGeomagneticYamaha.mIsDisplay_Initialized == true)
    {
      this.mTableRow_Initialized.setVisibility(0);
      if (paramDataGeomagneticYamaha.mInitialized != null) {
        if (paramDataGeomagneticYamaha.mInitialized.equals("1"))
        {
          FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Initialized - Pass");
          this.mText_Initialized.setText(paramDataGeomagneticYamaha.mInitialized);
          FtUtil.log_i("FragmentGeomagneticYamaha", "update", "Initialized Return : " + paramDataGeomagneticYamaha.mInitialized);
          if ((paramDataGeomagneticYamaha.mStatus == null) || (paramDataGeomagneticYamaha.mStatus_Result == null)) {
            break label1025;
          }
          if (!paramDataGeomagneticYamaha.mStatus_Result.equals("OK")) {
            break label1010;
          }
          FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Status - Pass");
          FtUtil.log_i("FragmentGeomagneticYamaha", "update", "Retuen : " + paramDataGeomagneticYamaha.mStatus_Result + ", Status : " + paramDataGeomagneticYamaha.mStatus);
          if (paramDataGeomagneticYamaha.mIsDisplay_DAC != true) {
            break label1100;
          }
          this.mTableRow_DAC.setVisibility(0);
          if ((paramDataGeomagneticYamaha.mDAC_X == null) || (paramDataGeomagneticYamaha.mDAC_Y == null) || (paramDataGeomagneticYamaha.mDAC_Z == null) || (paramDataGeomagneticYamaha.mDAC_Result == null)) {
            break label1055;
          }
          if (!paramDataGeomagneticYamaha.mDAC_Result.equals("OK")) {
            break label1040;
          }
          FtUtil.log_e("FragmentGeomagneticYamaha", "update", "DAC - Pass");
          this.mText_DAC_X.setText(paramDataGeomagneticYamaha.mDAC_X);
          this.mText_DAC_Y.setText(paramDataGeomagneticYamaha.mDAC_Y);
          this.mText_DAC_Z.setText(paramDataGeomagneticYamaha.mDAC_Z);
          FtUtil.log_i("FragmentGeomagneticYamaha", "update", "Retuen : " + paramDataGeomagneticYamaha.mDAC_Result + ", [DAC]X:" + paramDataGeomagneticYamaha.mDAC_X + ", Y:" + paramDataGeomagneticYamaha.mDAC_Y + ", Z:" + paramDataGeomagneticYamaha.mDAC_Z);
          if (paramDataGeomagneticYamaha.mIsDisplay_ADC != true) {
            break label1173;
          }
          this.mTableRow_ADC.setVisibility(0);
          if ((paramDataGeomagneticYamaha.mADC_X == null) || (paramDataGeomagneticYamaha.mADC_Y == null) || (paramDataGeomagneticYamaha.mADC_Z == null) || (paramDataGeomagneticYamaha.mADC_Result == null)) {
            break label1128;
          }
          if (!paramDataGeomagneticYamaha.mADC_Result.equals("OK")) {
            break label1113;
          }
          FtUtil.log_e("FragmentGeomagneticYamaha", "update", "ADC - Pass");
          label424:
          this.mText_ADC_X.setText(paramDataGeomagneticYamaha.mADC_X);
          this.mText_ADC_Y.setText(paramDataGeomagneticYamaha.mADC_Y);
          this.mText_ADC_Z.setText(paramDataGeomagneticYamaha.mADC_Z);
          FtUtil.log_i("FragmentGeomagneticYamaha", "update", "Retuen : " + paramDataGeomagneticYamaha.mADC_Result + ", [ADC]X:" + paramDataGeomagneticYamaha.mADC_X + ", Y:" + paramDataGeomagneticYamaha.mADC_Y + ", Z:" + paramDataGeomagneticYamaha.mADC_Z);
          label526:
          if (paramDataGeomagneticYamaha.mIsDisplay_Self != true) {
            break label1236;
          }
          this.mTableRow_SX.setVisibility(0);
          this.mTableRow_SY.setVisibility(0);
          if ((paramDataGeomagneticYamaha.mSelf_X == null) || (paramDataGeomagneticYamaha.mSelf_Y == null) || (paramDataGeomagneticYamaha.mSelf_Result == null)) {
            break label1201;
          }
          if (!paramDataGeomagneticYamaha.mSelf_Result.equals("OK")) {
            break label1186;
          }
          FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Self - Pass");
          label594:
          this.mText_SX.setText(paramDataGeomagneticYamaha.mSelf_X);
          this.mText_SY.setText(paramDataGeomagneticYamaha.mSelf_Y);
          FtUtil.log_i("FragmentGeomagneticYamaha", "update", "Retuen : " + paramDataGeomagneticYamaha.mSelf_Result + ", SX:" + paramDataGeomagneticYamaha.mSelf_X + ", SY:" + paramDataGeomagneticYamaha.mSelf_Y);
          label672:
          if (paramDataGeomagneticYamaha.mIsDisplay_OffsetH != true) {
            break label1309;
          }
          this.mTableRow_Offset_H.setVisibility(0);
          if ((paramDataGeomagneticYamaha.mOffsetH_X == null) || (paramDataGeomagneticYamaha.mOffsetH_Y == null) || (paramDataGeomagneticYamaha.mOffsetH_Z == null) || (paramDataGeomagneticYamaha.mOffsetH_Result == null)) {
            break label1264;
          }
          if (!paramDataGeomagneticYamaha.mOffsetH_Result.equals("OK")) {
            break label1249;
          }
          FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Offset - Pass");
          label739:
          this.mText_Offset_H_X.setText(paramDataGeomagneticYamaha.mOffsetH_X);
          this.mText_Offset_H_Y.setText(paramDataGeomagneticYamaha.mOffsetH_Y);
          this.mText_Offset_H_Z.setText(paramDataGeomagneticYamaha.mOffsetH_Z);
          FtUtil.log_i("FragmentGeomagneticYamaha", "update", "Retuen : " + paramDataGeomagneticYamaha.mOffsetH_Result + ", [Offset H]X:" + paramDataGeomagneticYamaha.mOffsetH_X + ", Y:" + paramDataGeomagneticYamaha.mOffsetH_Y + ", Z:" + paramDataGeomagneticYamaha.mOffsetH_Z);
          label841:
          if (paramDataGeomagneticYamaha.mPowerOff_Result == null) {
            break label1337;
          }
          if (!paramDataGeomagneticYamaha.mPowerOff_Result.equals("1")) {
            break label1322;
          }
          FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Released - Pass");
          FtUtil.log_i("FragmentGeomagneticYamaha", "update", "Released Return : " + paramDataGeomagneticYamaha.mPowerOff_Result);
          label900:
          TextView localTextView1 = this.mTextResult;
          if (i == 0) {
            break label1352;
          }
          str = "PASS";
          label915:
          localTextView1.setText(str);
          localTextView2 = this.mTextResult;
          if (i == 0) {
            break label1360;
          }
        }
      }
    }
    label1025:
    label1040:
    label1055:
    label1100:
    label1360:
    for (int j = -16776961;; j = -65536)
    {
      localTextView2.setTextColor(j);
      if (i == 0) {
        break label1368;
      }
      paramHandler.sendEmptyMessage(UIGeomagneticGyro.WHAT_GEOMAGNETIC_PASS);
      return;
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Initialized - Fail");
      i = 0;
      break;
      this.mText_Initialized.setText("NONE");
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Initialized - Fail : null");
      i = 0;
      break label108;
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Initialized> data==null || Display==false");
      break label108;
      label1010:
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Status - Fail");
      i = 0;
      break label145;
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Status - Fail : null");
      i = 0;
      break label188;
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "DAC - Fail");
      i = 0;
      break label255;
      this.mText_DAC_X.setText("NONE");
      this.mText_DAC_Y.setText("NONE");
      this.mText_DAC_Z.setText("NONE");
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "DAC - Fail : null");
      i = 0;
      break label357;
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "DAC> data==null || Display==false");
      break label357;
      label1113:
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "ADC - Fail");
      i = 0;
      break label424;
      label1128:
      this.mText_ADC_X.setText("NONE");
      this.mText_ADC_Y.setText("NONE");
      this.mText_ADC_Z.setText("NONE");
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "ADC - Fail : null");
      i = 0;
      break label526;
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "ADC> data==null || Display==false");
      break label526;
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Self - Fail");
      i = 0;
      break label594;
      this.mText_SX.setText("NONE");
      this.mText_SY.setText("NONE");
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Self - Fail : null");
      i = 0;
      break label672;
      label1236:
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Self> data==null || Display==false");
      break label672;
      label1249:
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Offset - Fail");
      i = 0;
      break label739;
      label1264:
      this.mText_Offset_H_X.setText("NONE");
      this.mText_Offset_H_Y.setText("NONE");
      this.mText_Offset_H_Z.setText("NONE");
      FtUtil.log_i("FragmentGeomagneticYamaha", "update", "Offset - Fail : null");
      i = 0;
      break label841;
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "OffsetH> data==null || Display==false");
      break label841;
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Released - Fail");
      i = 0;
      break label870;
      FtUtil.log_e("FragmentGeomagneticYamaha", "update", "Released - Fail : null");
      i = 0;
      break label900;
      str = "FAIL";
      break label915;
    }
    label1173:
    label1186:
    label1201:
    label1337:
    label1352:
    label1368:
    paramHandler.sendEmptyMessage(UIGeomagneticGyro.WHAT_GEOMAGNETIC_FAIL);
    label1309:
    label1322:
    return;
  }
  
  public static class DataGeomagneticYamaha
  {
    public String mADC_Result;
    public String mADC_X;
    public String mADC_Y;
    public String mADC_Z;
    public String mDAC_Result;
    public String mDAC_X;
    public String mDAC_Y;
    public String mDAC_Z;
    public String mInitialized;
    public boolean mIsDisplay_ADC;
    public boolean mIsDisplay_DAC;
    public boolean mIsDisplay_Initialized;
    public boolean mIsDisplay_OffsetH;
    public boolean mIsDisplay_Self;
    public String mOffsetH_Result;
    public String mOffsetH_X;
    public String mOffsetH_Y;
    public String mOffsetH_Z;
    public String mPowerOff_Result;
    public String mSelf_Result;
    public String mSelf_X;
    public String mSelf_Y;
    public String mStatus;
    public String mStatus_Result;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.FragmentGeomagneticYamaha
 * JD-Core Version:    0.7.1
 */