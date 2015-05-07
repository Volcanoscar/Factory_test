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

public class FragmentGeomagneticAlps
  extends Fragment
{
  private final String CLASS_NAME = "FragmentGeomagneticAlps";
  private TableRow mTableRow_ADC;
  private TableRow mTableRow_Initialized;
  private TableRow mTableRow_Status;
  private TextView mTextResult;
  private TextView mText_ADC_Title;
  private TextView mText_ADC_X;
  private TextView mText_ADC_Y;
  private TextView mText_ADC_Z;
  private TextView mText_Initialized;
  private TextView mText_Initialized_Title;
  private TextView mText_Status;
  private TextView mText_Status_Title;
  private TextView mText_Title;
  private TextView mText_X_Title;
  private TextView mText_Y_Title;
  private TextView mText_Z_Title;
  
  private void init(View paramView)
  {
    FtUtil.log_d("FragmentGeomagneticAlps", "init", null);
    this.mText_Title = ((TextView)paramView.findViewById(2131296285));
    this.mTableRow_Initialized = ((TableRow)paramView.findViewById(2131296286));
    this.mText_Initialized_Title = ((TextView)paramView.findViewById(2131296287));
    this.mText_Initialized = ((TextView)paramView.findViewById(2131296288));
    this.mTableRow_Status = ((TableRow)paramView.findViewById(2131296289));
    this.mText_Status_Title = ((TextView)paramView.findViewById(2131296290));
    this.mText_Status = ((TextView)paramView.findViewById(2131296291));
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
    FtUtil.log_d("FragmentGeomagneticAlps", "setUIRate", "rate : " + f);
    if ((f != 0.0F) && (f != 1.0F))
    {
      this.mText_Title.setTextSize(0, f * this.mText_Title.getTextSize());
      this.mText_Initialized_Title.setTextSize(0, f * this.mText_Initialized_Title.getTextSize());
      this.mText_Initialized.setTextSize(0, f * this.mText_Initialized.getTextSize());
      this.mText_Status_Title.setTextSize(0, f * this.mText_Status_Title.getTextSize());
      this.mText_Status.setTextSize(0, f * this.mText_Status.getTextSize());
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
    FtUtil.log_d("FragmentGeomagneticAlps", "onCreateView", null);
    View localView = paramLayoutInflater.inflate(2130903045, null);
    init(localView);
    setUIRate();
    return localView;
  }
  
  public void update(DataGeomagneticAlps paramDataGeomagneticAlps, Handler paramHandler)
  {
    FtUtil.log_i("FragmentGeomagneticAlps", "update", null);
    if (paramDataGeomagneticAlps == null)
    {
      FtUtil.log_e("FragmentGeomagneticAlps", "update", "Warning: data is null.");
      return;
    }
    int i = 1;
    label108:
    label374:
    String str;
    label159:
    label211:
    TextView localTextView2;
    if (paramDataGeomagneticAlps.mIsDisplay_Initialized == true)
    {
      this.mTableRow_Initialized.setVisibility(0);
      if (paramDataGeomagneticAlps.mInitialized != null) {
        if (paramDataGeomagneticAlps.mInitialized.equals("1"))
        {
          FtUtil.log_e("FragmentGeomagneticAlps", "update", "Initialized - Pass");
          this.mText_Initialized.setText(paramDataGeomagneticAlps.mInitialized);
          FtUtil.log_i("FragmentGeomagneticAlps", "update", "Initialized Return : " + paramDataGeomagneticAlps.mInitialized);
          if (paramDataGeomagneticAlps.mIsDisplay_Status != true) {
            break label519;
          }
          this.mTableRow_Status.setVisibility(0);
          if ((paramDataGeomagneticAlps.mStatus == null) || (paramDataGeomagneticAlps.mStatus_Result == null)) {
            break label495;
          }
          if (!paramDataGeomagneticAlps.mStatus_Result.equals("OK")) {
            break label480;
          }
          FtUtil.log_e("FragmentGeomagneticAlps", "update", "Status - Pass");
          this.mText_Status.setText(paramDataGeomagneticAlps.mStatus);
          FtUtil.log_i("FragmentGeomagneticAlps", "update", "Retuen : " + paramDataGeomagneticAlps.mStatus_Result + ", Status : " + paramDataGeomagneticAlps.mStatus);
          if (paramDataGeomagneticAlps.mIsDisplay_ADC != true) {
            break label589;
          }
          this.mTableRow_ADC.setVisibility(0);
          if ((paramDataGeomagneticAlps.mADC_X == null) || (paramDataGeomagneticAlps.mADC_Y == null) || (paramDataGeomagneticAlps.mADC_Z == null) || (paramDataGeomagneticAlps.mADC_Result == null)) {
            break label547;
          }
          if (!paramDataGeomagneticAlps.mADC_Result.equals("OK")) {
            break label532;
          }
          FtUtil.log_e("FragmentGeomagneticAlps", "update", "ADC - Pass");
          label276:
          this.mText_ADC_X.setText(paramDataGeomagneticAlps.mADC_X);
          this.mText_ADC_Y.setText(paramDataGeomagneticAlps.mADC_Y);
          this.mText_ADC_Z.setText(paramDataGeomagneticAlps.mADC_Z);
          FtUtil.log_i("FragmentGeomagneticAlps", "update", "Retuen : " + paramDataGeomagneticAlps.mADC_Result + ", [ADC]X:" + paramDataGeomagneticAlps.mADC_X + ", Y:" + paramDataGeomagneticAlps.mADC_Y + ", Z:" + paramDataGeomagneticAlps.mADC_Z);
          TextView localTextView1 = this.mTextResult;
          if (i == 0) {
            break label602;
          }
          str = "PASS";
          label388:
          localTextView1.setText(str);
          localTextView2 = this.mTextResult;
          if (i == 0) {
            break label610;
          }
        }
      }
    }
    label480:
    label610:
    for (int j = -16776961;; j = -65536)
    {
      localTextView2.setTextColor(j);
      if (i != 1) {
        break label618;
      }
      paramHandler.sendEmptyMessage(UIGeomagneticGyro.WHAT_GEOMAGNETIC_PASS);
      return;
      FtUtil.log_e("FragmentGeomagneticAlps", "update", "Initialized - Fail");
      i = 0;
      break;
      this.mText_Initialized.setText("NONE");
      FtUtil.log_e("FragmentGeomagneticAlps", "update", "Initialized - Fail : null");
      i = 0;
      break label108;
      FtUtil.log_e("FragmentGeomagneticAlps", "update", "Initialized> data==null || Display==false");
      break label108;
      FtUtil.log_e("FragmentGeomagneticAlps", "update", "Status - Fail");
      i = 0;
      break label159;
      label495:
      this.mText_Status.setText("NONE");
      FtUtil.log_e("FragmentGeomagneticAlps", "update", "Status - Fail : null");
      i = 0;
      break label211;
      FtUtil.log_e("FragmentGeomagneticAlps", "update", "Status> data==null || Display==false");
      break label211;
      FtUtil.log_e("FragmentGeomagneticAlps", "update", "ADC - Fail");
      i = 0;
      break label276;
      this.mText_ADC_X.setText("NONE");
      this.mText_ADC_Y.setText("NONE");
      this.mText_ADC_Z.setText("NONE");
      FtUtil.log_e("FragmentGeomagneticAlps", "update", "ADC - Fail : null");
      i = 0;
      break label374;
      FtUtil.log_e("FragmentGeomagneticAlps", "update", "ADC> data==null || Display==false");
      break label374;
      str = "FAIL";
      break label388;
    }
    label519:
    label532:
    label547:
    label589:
    label602:
    label618:
    paramHandler.sendEmptyMessage(UIGeomagneticGyro.WHAT_GEOMAGNETIC_FAIL);
  }
  
  public static class DataGeomagneticAlps
  {
    public String mADC_Result;
    public String mADC_X;
    public String mADC_Y;
    public String mADC_Z;
    public String mInitialized;
    public boolean mIsDisplay_ADC;
    public boolean mIsDisplay_Initialized;
    public boolean mIsDisplay_Status;
    public String mStatus;
    public String mStatus_Result;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.FragmentGeomagneticAlps
 * JD-Core Version:    0.7.1
 */