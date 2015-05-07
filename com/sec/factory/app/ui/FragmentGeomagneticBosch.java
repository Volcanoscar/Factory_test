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

public class FragmentGeomagneticBosch
  extends Fragment
{
  private final String CLASS_NAME = "FragmentGeomagneticBosch";
  private TableRow mTableRow_ADC;
  private TableRow mTableRow_BmZ;
  private TableRow mTableRow_Initialized;
  private TextView mTextResult;
  private TextView mText_ADC_Title;
  private TextView mText_ADC_X;
  private TextView mText_ADC_Y;
  private TextView mText_ADC_Z;
  private TextView mText_BmZ;
  private TextView mText_BmZ_Title;
  private TextView mText_Initialized;
  private TextView mText_Initialized_Title;
  private TextView mText_Title;
  private TextView mText_X_Title;
  private TextView mText_Y_Title;
  private TextView mText_Z_Title;
  
  private void init(View paramView)
  {
    FtUtil.log_d("FragmentGeomagneticBosch", "init", null);
    this.mText_Title = ((TextView)paramView.findViewById(2131296285));
    this.mTableRow_Initialized = ((TableRow)paramView.findViewById(2131296286));
    this.mText_Initialized_Title = ((TextView)paramView.findViewById(2131296287));
    this.mText_Initialized = ((TextView)paramView.findViewById(2131296288));
    this.mTableRow_BmZ = ((TableRow)paramView.findViewById(2131296318));
    this.mText_BmZ_Title = ((TextView)paramView.findViewById(2131296319));
    this.mText_BmZ = ((TextView)paramView.findViewById(2131296320));
    this.mText_X_Title = ((TextView)paramView.findViewById(2131296292));
    this.mText_Y_Title = ((TextView)paramView.findViewById(2131296293));
    this.mText_Z_Title = ((TextView)paramView.findViewById(2131296294));
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
    FtUtil.log_d("FragmentGeomagneticBosch", "setUIRate", "rate : " + f);
    if ((f != 0.0F) && (f != 1.0F))
    {
      this.mText_Title.setTextSize(0, f * this.mText_Title.getTextSize());
      this.mText_Initialized_Title.setTextSize(0, f * this.mText_Initialized_Title.getTextSize());
      this.mText_Initialized.setTextSize(0, f * this.mText_Initialized.getTextSize());
      this.mText_BmZ_Title.setTextSize(0, f * this.mText_BmZ_Title.getTextSize());
      this.mText_BmZ.setTextSize(0, f * this.mText_BmZ.getTextSize());
      this.mText_X_Title.setTextSize(0, f * this.mText_X_Title.getTextSize());
      this.mText_Y_Title.setTextSize(0, f * this.mText_Y_Title.getTextSize());
      this.mText_Z_Title.setTextSize(0, f * this.mText_Z_Title.getTextSize());
      this.mText_ADC_Title.setTextSize(0, f * this.mText_ADC_Title.getTextSize());
      this.mText_ADC_X.setTextSize(0, f * this.mText_ADC_X.getTextSize());
      this.mText_ADC_Y.setTextSize(0, f * this.mText_ADC_Y.getTextSize());
      this.mText_ADC_Z.setTextSize(0, f * this.mText_ADC_Z.getTextSize());
      this.mTextResult.setTextSize(0, f * this.mTextResult.getTextSize());
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    FtUtil.log_d("FragmentGeomagneticBosch", "onCreateView", null);
    View localView = paramLayoutInflater.inflate(2130903047, null);
    init(localView);
    setUIRate();
    return localView;
  }
  
  public void update(DataGeomagneticBosch paramDataGeomagneticBosch, Handler paramHandler)
  {
    FtUtil.log_e("FragmentGeomagneticBosch", "update", null);
    if (paramDataGeomagneticBosch == null)
    {
      FtUtil.log_e("FragmentGeomagneticBosch", "update", "Warning: data is null.");
      return;
    }
    int i = 1;
    label108:
    label249:
    String str;
    label144:
    label184:
    label347:
    label361:
    TextView localTextView2;
    if (paramDataGeomagneticBosch.mIsDisplay_Initialized == true)
    {
      this.mTableRow_Initialized.setVisibility(0);
      if (paramDataGeomagneticBosch.mInitialized != null) {
        if (paramDataGeomagneticBosch.mInitialized.equals("1"))
        {
          FtUtil.log_e("FragmentGeomagneticBosch", "update", "Initialized - Pass");
          this.mText_Initialized.setText(paramDataGeomagneticBosch.mInitialized);
          FtUtil.log_i("FragmentGeomagneticBosch", "update", "Initialized Return : " + paramDataGeomagneticBosch.mInitialized);
          this.mTableRow_BmZ.setVisibility(0);
          if (paramDataGeomagneticBosch.mBmZ == null) {
            break label469;
          }
          if (!paramDataGeomagneticBosch.mSelf_Result.equals("OK")) {
            break label454;
          }
          FtUtil.log_e("FragmentGeomagneticBosch", "update", "Self - Pass");
          this.mText_BmZ.setText(paramDataGeomagneticBosch.mBmZ);
          FtUtil.log_i("FragmentGeomagneticBosch", "update", "Return : " + paramDataGeomagneticBosch.mIsDisplay_Self);
          if (paramDataGeomagneticBosch.mIsDisplay_ADC != true) {
            break label544;
          }
          this.mTableRow_ADC.setVisibility(0);
          if ((paramDataGeomagneticBosch.mADC_X == null) || (paramDataGeomagneticBosch.mADC_Y == null) || (paramDataGeomagneticBosch.mADC_Z == null) || (paramDataGeomagneticBosch.mADC_Result == null)) {
            break label499;
          }
          if (!paramDataGeomagneticBosch.mADC_Result.equals("OK")) {
            break label484;
          }
          FtUtil.log_e("FragmentGeomagneticBosch", "update", "ADC - Pass");
          this.mText_ADC_X.setText(paramDataGeomagneticBosch.mADC_X);
          this.mText_ADC_Y.setText(paramDataGeomagneticBosch.mADC_Y);
          this.mText_ADC_Z.setText(paramDataGeomagneticBosch.mADC_Z);
          FtUtil.log_i("FragmentGeomagneticBosch", "update", "Retuen : " + paramDataGeomagneticBosch.mADC_Result + ", [ADC]X:" + paramDataGeomagneticBosch.mADC_X + ", Y:" + paramDataGeomagneticBosch.mADC_Y + ", Z:" + paramDataGeomagneticBosch.mADC_Z);
          TextView localTextView1 = this.mTextResult;
          if (i == 0) {
            break label557;
          }
          str = "PASS";
          localTextView1.setText(str);
          localTextView2 = this.mTextResult;
          if (i == 0) {
            break label565;
          }
        }
      }
    }
    label544:
    label557:
    label565:
    for (int j = -16776961;; j = -65536)
    {
      localTextView2.setTextColor(j);
      if (i == 0) {
        break label573;
      }
      paramHandler.sendEmptyMessage(UIGeomagneticGyro.WHAT_GEOMAGNETIC_PASS);
      return;
      FtUtil.log_e("FragmentGeomagneticBosch", "update", "Initialized - Fail");
      i = 0;
      break;
      this.mText_Initialized.setText("NONE");
      FtUtil.log_e("FragmentGeomagneticBosch", "update", "Initialized - Fail : null");
      i = 0;
      break label108;
      FtUtil.log_e("FragmentGeomagneticBosch", "update", "Initialized> data==null || Display==false");
      break label108;
      label454:
      FtUtil.log_e("FragmentGeomagneticBosch", "update", "Self - Fail");
      i = 0;
      break label144;
      label469:
      FtUtil.log_e("FragmentGeomagneticBosch", "update", "Self - Fail : null");
      i = 0;
      break label184;
      label484:
      FtUtil.log_e("FragmentGeomagneticBosch", "update", "ADC - Fail");
      i = 0;
      break label249;
      label499:
      this.mText_ADC_X.setText("NONE");
      this.mText_ADC_Y.setText("NONE");
      this.mText_ADC_Z.setText("NONE");
      FtUtil.log_e("FragmentGeomagneticBosch", "update", "ADC - Fail : null");
      i = 0;
      break label347;
      FtUtil.log_e("FragmentGeomagneticBosch", "update", "ADC> data==null || Display==false");
      break label347;
      str = "FAIL";
      break label361;
    }
    label573:
    paramHandler.sendEmptyMessage(UIGeomagneticGyro.WHAT_GEOMAGNETIC_FAIL);
  }
  
  public static class DataGeomagneticBosch
  {
    public String mADC_Result;
    public String mADC_X;
    public String mADC_Y;
    public String mADC_Z;
    public String mBmZ;
    public String mInitialized;
    public boolean mIsDisplay_ADC;
    public boolean mIsDisplay_Initialized;
    public boolean mIsDisplay_Self;
    public String mSelf_Result;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.FragmentGeomagneticBosch
 * JD-Core Version:    0.7.1
 */