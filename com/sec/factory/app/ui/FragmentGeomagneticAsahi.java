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

public class FragmentGeomagneticAsahi
  extends Fragment
{
  private final String CLASS_NAME = "FragmentGeomagneticAsahi";
  private TableRow mTableRow_ADC;
  private TableRow mTableRow_DAC;
  private TableRow mTableRow_HX;
  private TableRow mTableRow_HY;
  private TableRow mTableRow_HZ;
  private TableRow mTableRow_Initialized;
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
  private TextView mText_HX;
  private TextView mText_HX_Title;
  private TextView mText_HY;
  private TextView mText_HY_Title;
  private TextView mText_HZ;
  private TextView mText_HZ_Title;
  private TextView mText_Initialized;
  private TextView mText_Initialized_Title;
  private TextView mText_Temp;
  private TextView mText_Temp_Title;
  private TextView mText_Title;
  private TextView mText_X_Title;
  private TextView mText_Y_Title;
  private TextView mText_Z_Title;
  
  private void init(View paramView)
  {
    FtUtil.log_d("FragmentGeomagneticAsahi", "init", null);
    this.mText_Title = ((TextView)paramView.findViewById(2131296285));
    this.mTableRow_Initialized = ((TableRow)paramView.findViewById(2131296286));
    this.mText_Initialized_Title = ((TextView)paramView.findViewById(2131296287));
    this.mText_Initialized = ((TextView)paramView.findViewById(2131296288));
    this.mTableRow_Temp = ((TableRow)paramView.findViewById(2131296301));
    this.mText_Temp_Title = ((TextView)paramView.findViewById(2131296302));
    this.mText_Temp = ((TextView)paramView.findViewById(2131296303));
    this.mTableRow_HX = ((TableRow)paramView.findViewById(2131296304));
    this.mText_HX_Title = ((TextView)paramView.findViewById(2131296305));
    this.mText_HX = ((TextView)paramView.findViewById(2131296306));
    this.mTableRow_HY = ((TableRow)paramView.findViewById(2131296307));
    this.mText_HY_Title = ((TextView)paramView.findViewById(2131296308));
    this.mText_HY = ((TextView)paramView.findViewById(2131296309));
    this.mTableRow_HZ = ((TableRow)paramView.findViewById(2131296310));
    this.mText_HZ_Title = ((TextView)paramView.findViewById(2131296311));
    this.mText_HZ = ((TextView)paramView.findViewById(2131296312));
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
    this.mTextResult = ((TextView)paramView.findViewById(2131296300));
  }
  
  private void setUIRate()
  {
    float f = Support.FactoryTestMenu.getUIRate(FactoryTestManager.convertorID_XML());
    FtUtil.log_d("FragmentGeomagneticAsahi", "setUIRate", "rate : " + f);
    if ((f != 0.0F) && (f != 1.0F))
    {
      this.mText_Title.setTextSize(0, f * this.mText_Title.getTextSize());
      this.mText_Initialized_Title.setTextSize(0, f * this.mText_Initialized_Title.getTextSize());
      this.mText_Initialized.setTextSize(0, f * this.mText_Initialized.getTextSize());
      this.mText_Temp_Title.setTextSize(0, f * this.mText_Temp_Title.getTextSize());
      this.mText_Temp.setTextSize(0, f * this.mText_Temp.getTextSize());
      this.mText_HX_Title.setTextSize(0, f * this.mText_HX_Title.getTextSize());
      this.mText_HX.setTextSize(0, f * this.mText_HX.getTextSize());
      this.mText_HY_Title.setTextSize(0, f * this.mText_HY_Title.getTextSize());
      this.mText_HY.setTextSize(0, f * this.mText_HY.getTextSize());
      this.mText_HZ_Title.setTextSize(0, f * this.mText_HZ_Title.getTextSize());
      this.mText_HZ.setTextSize(0, f * this.mText_HZ.getTextSize());
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
      this.mTextResult.setTextSize(0, f * this.mTextResult.getTextSize());
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    FtUtil.log_d("FragmentGeomagneticAsahi", "onCreateView", null);
    View localView = paramLayoutInflater.inflate(2130903046, null);
    init(localView);
    setUIRate();
    return localView;
  }
  
  public void update(DataGeomagneticAsahi paramDataGeomagneticAsahi, Handler paramHandler)
  {
    FtUtil.log_i("FragmentGeomagneticAsahi", "update", null);
    if (paramDataGeomagneticAsahi == null)
    {
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "Warning: data is null.");
      return;
    }
    int i = 1;
    label108:
    label636:
    String str;
    label161:
    label215:
    label738:
    label753:
    TextView localTextView2;
    if (paramDataGeomagneticAsahi.mIsDisplay_Initialized == true)
    {
      this.mTableRow_Initialized.setVisibility(0);
      if (paramDataGeomagneticAsahi.mInitialized != null) {
        if (paramDataGeomagneticAsahi.mInitialized.equals("1"))
        {
          FtUtil.log_e("FragmentGeomagneticAsahi", "update", "Initialized - Pass");
          this.mText_Initialized.setText(paramDataGeomagneticAsahi.mInitialized);
          FtUtil.log_i("FragmentGeomagneticAsahi", "update", "Initialized Return : " + paramDataGeomagneticAsahi.mInitialized);
          if (paramDataGeomagneticAsahi.mIsDisplay_Temperature != true) {
            break label889;
          }
          this.mTableRow_Temp.setVisibility(0);
          if ((paramDataGeomagneticAsahi.mTemperature == null) || (paramDataGeomagneticAsahi.mTemperature_Result == null)) {
            break label864;
          }
          if (!paramDataGeomagneticAsahi.mTemperature_Result.equals("OK")) {
            break label849;
          }
          FtUtil.log_e("FragmentGeomagneticAsahi", "update", "Temperature - Pass");
          this.mText_Temp.setText(paramDataGeomagneticAsahi.mTemperature);
          FtUtil.log_i("FragmentGeomagneticAsahi", "update", "Retuen : " + paramDataGeomagneticAsahi.mTemperature_Result + ", Temperature : " + paramDataGeomagneticAsahi.mTemperature);
          if (paramDataGeomagneticAsahi.mIsDisplay_Self != true) {
            break label962;
          }
          this.mTableRow_HX.setVisibility(0);
          this.mTableRow_HY.setVisibility(0);
          this.mTableRow_HZ.setVisibility(0);
          if ((paramDataGeomagneticAsahi.mSelf_X == null) || (paramDataGeomagneticAsahi.mSelf_Y == null) || (paramDataGeomagneticAsahi.mSelf_Z == null) || (paramDataGeomagneticAsahi.mSelf_Result == null)) {
            break label917;
          }
          if (!paramDataGeomagneticAsahi.mSelf_Result.equals("OK")) {
            break label902;
          }
          FtUtil.log_e("FragmentGeomagneticAsahi", "update", "HX/HY/HZ - Pass");
          label298:
          this.mText_HX.setText(paramDataGeomagneticAsahi.mSelf_X);
          this.mText_HY.setText(paramDataGeomagneticAsahi.mSelf_Y);
          this.mText_HZ.setText(paramDataGeomagneticAsahi.mSelf_Z);
          FtUtil.log_i("FragmentGeomagneticAsahi", "update", "Retuen : " + paramDataGeomagneticAsahi.mSelf_Result + ", HX:" + paramDataGeomagneticAsahi.mSelf_X + ", HY:" + paramDataGeomagneticAsahi.mSelf_Y + ", HZ:" + paramDataGeomagneticAsahi.mSelf_Z);
          label400:
          if (paramDataGeomagneticAsahi.mIsDisplay_DAC != true) {
            break label1035;
          }
          this.mTableRow_DAC.setVisibility(0);
          if ((paramDataGeomagneticAsahi.mDAC_X == null) || (paramDataGeomagneticAsahi.mDAC_Y == null) || (paramDataGeomagneticAsahi.mDAC_Z == null) || (paramDataGeomagneticAsahi.mDAC_Result == null)) {
            break label990;
          }
          if (!paramDataGeomagneticAsahi.mDAC_Result.equals("OK")) {
            break label975;
          }
          FtUtil.log_e("FragmentGeomagneticAsahi", "update", "DAC - Pass");
          label467:
          this.mText_DAC_X.setText(paramDataGeomagneticAsahi.mDAC_X);
          this.mText_DAC_Y.setText(paramDataGeomagneticAsahi.mDAC_Y);
          this.mText_DAC_Z.setText(paramDataGeomagneticAsahi.mDAC_Z);
          FtUtil.log_i("FragmentGeomagneticAsahi", "update", "Retuen : " + paramDataGeomagneticAsahi.mDAC_Result + ", [DAC]X:" + paramDataGeomagneticAsahi.mDAC_X + ", Y:" + paramDataGeomagneticAsahi.mDAC_Y + ", Z:" + paramDataGeomagneticAsahi.mDAC_Z);
          label569:
          if (paramDataGeomagneticAsahi.mIsDisplay_ADC != true) {
            break label1108;
          }
          this.mTableRow_ADC.setVisibility(0);
          if ((paramDataGeomagneticAsahi.mADC_X == null) || (paramDataGeomagneticAsahi.mADC_Y == null) || (paramDataGeomagneticAsahi.mADC_Z == null) || (paramDataGeomagneticAsahi.mADC_Result == null)) {
            break label1063;
          }
          if (!paramDataGeomagneticAsahi.mADC_Result.equals("OK")) {
            break label1048;
          }
          FtUtil.log_e("FragmentGeomagneticAsahi", "update", "ADC - Pass");
          this.mText_ADC_X.setText(paramDataGeomagneticAsahi.mADC_X);
          this.mText_ADC_Y.setText(paramDataGeomagneticAsahi.mADC_Y);
          this.mText_ADC_Z.setText(paramDataGeomagneticAsahi.mADC_Z);
          FtUtil.log_i("FragmentGeomagneticAsahi", "update", "Retuen : " + paramDataGeomagneticAsahi.mADC_Result + ", [ADC]X:" + paramDataGeomagneticAsahi.mADC_X + ", Y:" + paramDataGeomagneticAsahi.mADC_Y + ", Z:" + paramDataGeomagneticAsahi.mADC_Z);
          TextView localTextView1 = this.mTextResult;
          if (i == 0) {
            break label1121;
          }
          str = "PASS";
          localTextView1.setText(str);
          localTextView2 = this.mTextResult;
          if (i == 0) {
            break label1129;
          }
        }
      }
    }
    label849:
    label864:
    label1121:
    label1129:
    for (int j = -16776961;; j = -65536)
    {
      localTextView2.setTextColor(j);
      if (i != 1) {
        break label1137;
      }
      paramHandler.sendEmptyMessage(UIGeomagneticGyro.WHAT_GEOMAGNETIC_PASS);
      return;
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "Initialized - Fail");
      i = 0;
      break;
      this.mText_Initialized.setText("NONE");
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "Initialized - Fail : null");
      i = 0;
      break label108;
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "Initialized> data==null || Display==false");
      break label108;
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "Temperature - Fail");
      i = 0;
      break label161;
      this.mText_Temp.setText("NONE");
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "Temperature - Fail : null");
      i = 0;
      break label215;
      label889:
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "Temperature> data==null || Display==false");
      break label215;
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "HX/HY/HZ - Fail");
      i = 0;
      break label298;
      this.mText_HX.setText("NONE");
      this.mText_HY.setText("NONE");
      this.mText_HZ.setText("NONE");
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "HX/HY/HZ - Fail : null");
      i = 0;
      break label400;
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "Self> data==null || Display==false");
      break label400;
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "DAC - Fail");
      i = 0;
      break label467;
      this.mText_DAC_X.setText("NONE");
      this.mText_DAC_Y.setText("NONE");
      this.mText_DAC_Z.setText("NONE");
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "DAC - Fail : null");
      i = 0;
      break label569;
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "DAC> data==null || Display==false");
      break label569;
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "ADC - Fail");
      i = 0;
      break label636;
      this.mText_ADC_X.setText("NONE");
      this.mText_ADC_Y.setText("NONE");
      this.mText_ADC_Z.setText("NONE");
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "ADC - Fail : null");
      i = 0;
      break label738;
      FtUtil.log_e("FragmentGeomagneticAsahi", "update", "ADC> data==null || Display==false");
      break label738;
      str = "FAIL";
      break label753;
    }
    label902:
    label917:
    label962:
    label975:
    label990:
    label1137:
    paramHandler.sendEmptyMessage(UIGeomagneticGyro.WHAT_GEOMAGNETIC_FAIL);
    label1035:
    label1048:
    label1063:
    label1108:
    return;
  }
  
  public static class DataGeomagneticAsahi
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
    public boolean mIsDisplay_Self;
    public boolean mIsDisplay_Temperature;
    public String mSelf_Result;
    public String mSelf_X;
    public String mSelf_Y;
    public String mSelf_Z;
    public String mTemperature;
    public String mTemperature_Result;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.FragmentGeomagneticAsahi
 * JD-Core Version:    0.7.1
 */