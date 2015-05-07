package com.sec.factory.app.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;
import com.sec.factory.support.Support.Spec;

public class FragmentGyroscopeInvensense
  extends Fragment
{
  private final String CLASS_NAME = "FragmentGyroscopeInvensense";
  private int mGyroSpec_Max;
  private int mGyroSpec_Min;
  private TextView mTextResult;
  private TextView mText_Initialized;
  private TextView mText_Initialized_Title;
  private TextView mText_Noise_Bias_Title;
  private TextView mText_Noise_Bias_X;
  private TextView mText_Noise_Bias_Y;
  private TextView mText_Noise_Bias_Z;
  private TextView mText_Noise_Power_Title;
  private TextView mText_Noise_Power_X;
  private TextView mText_Noise_Power_Y;
  private TextView mText_Noise_Power_Z;
  private TextView mText_Result_Title;
  private TextView mText_Sensor_Result;
  private TextView mText_Temperature;
  private TextView mText_Temperature_Title;
  private TextView mText_Title;
  private TextView mText_X_Title;
  private TextView mText_Y_Title;
  private TextView mText_Z_Title;
  
  private void inti(View paramView)
  {
    this.mText_Title = ((TextView)paramView.findViewById(2131296285));
    this.mText_Initialized_Title = ((TextView)paramView.findViewById(2131296287));
    this.mText_Initialized = ((TextView)paramView.findViewById(2131296288));
    this.mText_Temperature_Title = ((TextView)paramView.findViewById(2131296332));
    this.mText_Temperature = ((TextView)paramView.findViewById(2131296333));
    this.mText_X_Title = ((TextView)paramView.findViewById(2131296292));
    this.mText_Y_Title = ((TextView)paramView.findViewById(2131296293));
    this.mText_Z_Title = ((TextView)paramView.findViewById(2131296294));
    this.mText_Noise_Bias_Title = ((TextView)paramView.findViewById(2131296334));
    this.mText_Noise_Bias_X = ((TextView)paramView.findViewById(2131296335));
    this.mText_Noise_Bias_Y = ((TextView)paramView.findViewById(2131296336));
    this.mText_Noise_Bias_Z = ((TextView)paramView.findViewById(2131296337));
    this.mText_Noise_Power_Title = ((TextView)paramView.findViewById(2131296338));
    this.mText_Noise_Power_X = ((TextView)paramView.findViewById(2131296339));
    this.mText_Noise_Power_Y = ((TextView)paramView.findViewById(2131296340));
    this.mText_Noise_Power_Z = ((TextView)paramView.findViewById(2131296341));
    this.mText_Result_Title = ((TextView)paramView.findViewById(2131296342));
    this.mTextResult = ((TextView)paramView.findViewById(2131296300));
  }
  
  private void setUIRate()
  {
    float f = Support.FactoryTestMenu.getUIRate(FactoryTestManager.convertorID_XML());
    FtUtil.log_d("FragmentGyroscopeInvensense", "setUIRate", "rate : " + f);
    if ((f != 0.0F) && (f != 1.0F))
    {
      this.mText_Title.setTextSize(0, f * this.mText_Title.getTextSize());
      this.mText_Initialized_Title.setTextSize(0, f * this.mText_Initialized_Title.getTextSize());
      this.mText_Initialized.setTextSize(0, f * this.mText_Initialized.getTextSize());
      this.mText_Temperature_Title.setTextSize(0, f * this.mText_Temperature_Title.getTextSize());
      this.mText_Temperature.setTextSize(0, f * this.mText_Temperature.getTextSize());
      this.mText_X_Title.setTextSize(0, f * this.mText_X_Title.getTextSize());
      this.mText_Y_Title.setTextSize(0, f * this.mText_Y_Title.getTextSize());
      this.mText_Z_Title.setTextSize(0, f * this.mText_Z_Title.getTextSize());
      this.mText_Noise_Bias_Title.setTextSize(0, f * this.mText_Noise_Bias_Title.getTextSize());
      this.mText_Noise_Bias_X.setTextSize(0, f * this.mText_Noise_Bias_X.getTextSize());
      this.mText_Noise_Bias_Y.setTextSize(0, f * this.mText_Noise_Bias_Y.getTextSize());
      this.mText_Noise_Bias_Z.setTextSize(0, f * this.mText_Noise_Bias_Z.getTextSize());
      this.mText_Noise_Power_Title.setTextSize(0, f * this.mText_Noise_Power_Title.getTextSize());
      this.mText_Noise_Power_X.setTextSize(0, f * this.mText_Noise_Power_X.getTextSize());
      this.mText_Noise_Power_Y.setTextSize(0, f * this.mText_Noise_Power_Y.getTextSize());
      this.mText_Noise_Power_Z.setTextSize(0, f * this.mText_Noise_Power_Z.getTextSize());
      this.mText_Result_Title.setTextSize(0, f * this.mText_Result_Title.getTextSize());
      this.mTextResult.setTextSize(0, f * this.mTextResult.getTextSize());
    }
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    FtUtil.log_d("FragmentGyroscopeInvensense", "onCreateView", null);
    View localView = paramLayoutInflater.inflate(2130903049, null);
    inti(localView);
    setUIRate();
    return localView;
  }
  
  public void update(DataGyroInvensense paramDataGyroInvensense, Handler paramHandler)
  {
    FtUtil.log_i("FragmentGyroscopeInvensense", "update", null);
    if (paramDataGyroInvensense == null)
    {
      FtUtil.log_e("FragmentGyroscopeInvensense", "update", "Warning: data is null.");
      return;
    }
    int i = 1;
    if (paramDataGyroInvensense.mInitialized != null) {
      this.mText_Initialized.setText(paramDataGyroInvensense.mInitialized);
    }
    String str;
    label573:
    int j;
    if (paramDataGyroInvensense.mTemperature != null)
    {
      FtUtil.log_i("FragmentGyroscopeInvensense", "update", "Temperature : " + paramDataGyroInvensense.mTemperature);
      this.mText_Temperature.setText(paramDataGyroInvensense.mTemperature);
      if ((paramDataGyroInvensense.mBias_Result == null) || (paramDataGyroInvensense.mBias_X == null) || (paramDataGyroInvensense.mBias_Y == null) || (paramDataGyroInvensense.mBias_Z == null) || (paramDataGyroInvensense.mRMS_X == null) || (paramDataGyroInvensense.mRMS_Y == null) || (paramDataGyroInvensense.mRMS_Z == null)) {
        break label643;
      }
      FtUtil.log_i("FragmentGyroscopeInvensense", "update", "Noise Bias : " + paramDataGyroInvensense.mBias_X + " , " + paramDataGyroInvensense.mBias_Y + " , " + paramDataGyroInvensense.mBias_Z);
      FtUtil.log_i("FragmentGyroscopeInvensense", "update", "Noise Power : " + paramDataGyroInvensense.mRMS_X + " , " + paramDataGyroInvensense.mRMS_Y + " , " + paramDataGyroInvensense.mRMS_Z);
      this.mText_Noise_Bias_X.setText(paramDataGyroInvensense.mBias_X);
      this.mText_Noise_Bias_Y.setText(paramDataGyroInvensense.mBias_Y);
      this.mText_Noise_Bias_Z.setText(paramDataGyroInvensense.mBias_Z);
      this.mText_Noise_Power_X.setText(paramDataGyroInvensense.mRMS_X);
      this.mText_Noise_Power_Y.setText(paramDataGyroInvensense.mRMS_Y);
      this.mText_Noise_Power_Z.setText(paramDataGyroInvensense.mRMS_Z);
      FtUtil.log_i("FragmentGyroscopeInvensense", "update", "ReturnValue : " + paramDataGyroInvensense.mBias_Result);
      if (!paramDataGyroInvensense.mBias_Result.equals("0")) {
        i = 0;
      }
      this.mGyroSpec_Min = Support.Spec.getInt("GYROSCOPE_SELFTEST_MIN");
      this.mGyroSpec_Max = Support.Spec.getInt("GYROSCOPE_SELFTEST_MAX");
      if ((i != 1) || (Float.parseFloat(paramDataGyroInvensense.mBias_X) > this.mGyroSpec_Max) || (Float.parseFloat(paramDataGyroInvensense.mBias_Y) > this.mGyroSpec_Max) || (Float.parseFloat(paramDataGyroInvensense.mBias_Z) > this.mGyroSpec_Max) || (Float.parseFloat(paramDataGyroInvensense.mBias_X) < this.mGyroSpec_Min) || (Float.parseFloat(paramDataGyroInvensense.mBias_Y) < this.mGyroSpec_Min) || (Float.parseFloat(paramDataGyroInvensense.mBias_Z) < this.mGyroSpec_Min)) {
        i = 0;
      }
      if ((i != 1) || (Float.parseFloat(paramDataGyroInvensense.mRMS_X) > 5.0F) || (Float.parseFloat(paramDataGyroInvensense.mRMS_Y) > 5.0F) || (Float.parseFloat(paramDataGyroInvensense.mRMS_Z) > 5.0F) || (Float.parseFloat(paramDataGyroInvensense.mRMS_X) < -5.0F) || (Float.parseFloat(paramDataGyroInvensense.mRMS_Y) < -5.0F) || (Float.parseFloat(paramDataGyroInvensense.mRMS_Z) < -5.0F)) {
        i = 0;
      }
      TextView localTextView1 = this.mTextResult;
      if (i == 0) {
        break label627;
      }
      str = "PASS";
      localTextView1.setText(str);
      TextView localTextView2 = this.mTextResult;
      if (i == 0) {
        break label635;
      }
      j = -16776961;
      label594:
      localTextView2.setTextColor(j);
    }
    for (;;)
    {
      if (i == 0) {
        break label746;
      }
      paramHandler.sendEmptyMessage(UIGeomagneticGyro.WHAT_GYROSCOPE_PASS);
      return;
      this.mText_Temperature.setText("NONE");
      break;
      label627:
      str = "FAIL";
      break label573;
      label635:
      j = -65536;
      break label594;
      label643:
      this.mText_Noise_Bias_X.setText("NONE");
      this.mText_Noise_Bias_Y.setText("NONE");
      this.mText_Noise_Bias_Z.setText("NONE");
      this.mText_Noise_Power_X.setText("NONE");
      this.mText_Noise_Power_Y.setText("NONE");
      this.mText_Noise_Power_Z.setText("NONE");
      this.mText_Sensor_Result.setText("FAIL");
      this.mText_Sensor_Result.setTextColor(-65536);
      this.mTextResult.setText("FAIL");
      this.mTextResult.setTextColor(-65536);
    }
    label746:
    paramHandler.sendEmptyMessage(UIGeomagneticGyro.WHAT_GYROSCOPE_FAIL);
  }
  
  public static class DataGyroInvensense
  {
    public String mBias_Result;
    public String mBias_X;
    public String mBias_Y;
    public String mBias_Z;
    public String mInitialized;
    public String mRMS_X;
    public String mRMS_Y;
    public String mRMS_Z;
    public String mTemperature;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.FragmentGyroscopeInvensense
 * JD-Core Version:    0.7.1
 */