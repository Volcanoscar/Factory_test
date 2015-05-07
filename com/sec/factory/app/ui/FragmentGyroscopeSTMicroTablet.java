package com.sec.factory.app.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import com.sec.factory.support.FtUtil;
import java.io.File;

public class FragmentGyroscopeSTMicroTablet
  extends Fragment
{
  private final String CLASS_NAME = "FragmentGyroscopeSTMicroTablet";
  private boolean mCalibrationPass = false;
  private TableLayout mGyroSelfTestLayout;
  private TextView mGyroZeroRateText;
  private Handler mHandler = new Handler();
  private String[] mSelfTestResults;
  private boolean mZeroRatePass = false;
  private TextView txt_calibration;
  private TextView txt_diff_x;
  private TextView txt_diff_y;
  private TextView txt_diff_z;
  private TextView txt_self_x;
  private TextView txt_self_y;
  private TextView txt_self_z;
  private TextView txt_x;
  private TextView txt_xyz_x;
  private TextView txt_xyz_y;
  private TextView txt_xyz_z;
  private TextView txt_y;
  private TextView txt_z;
  private TextView txt_zerorate_x;
  private TextView txt_zerorate_y;
  private TextView txt_zerorate_z;
  private TextView txtresult;
  
  private void init(View paramView)
  {
    this.txtresult = ((TextView)paramView.findViewById(2131296300));
    this.txt_x = ((TextView)paramView.findViewById(2131296350));
    this.txt_y = ((TextView)paramView.findViewById(2131296351));
    this.txt_z = ((TextView)paramView.findViewById(2131296352));
    this.txt_xyz_x = ((TextView)paramView.findViewById(2131296354));
    this.txt_xyz_y = ((TextView)paramView.findViewById(2131296355));
    this.txt_xyz_z = ((TextView)paramView.findViewById(2131296356));
    this.txt_self_x = ((TextView)paramView.findViewById(2131296359));
    this.txt_self_y = ((TextView)paramView.findViewById(2131296360));
    this.txt_self_z = ((TextView)paramView.findViewById(2131296361));
    this.txt_diff_x = ((TextView)paramView.findViewById(2131296364));
    this.txt_diff_y = ((TextView)paramView.findViewById(2131296365));
    this.txt_diff_z = ((TextView)paramView.findViewById(2131296366));
    this.txt_zerorate_x = ((TextView)paramView.findViewById(2131296368));
    this.txt_zerorate_y = ((TextView)paramView.findViewById(2131296369));
    this.txt_zerorate_z = ((TextView)paramView.findViewById(2131296370));
    this.txt_calibration = ((TextView)paramView.findViewById(2131296372));
    this.mGyroZeroRateText = ((TextView)paramView.findViewById(2131296347));
    this.mGyroSelfTestLayout = ((TableLayout)paramView.findViewById(2131296348));
  }
  
  private void showTestResults(String[] paramArrayOfString, String paramString, Handler paramHandler)
  {
    this.txt_xyz_x.setText(paramArrayOfString[0]);
    this.txt_xyz_y.setText(paramArrayOfString[1]);
    this.txt_xyz_z.setText(paramArrayOfString[2]);
    this.txt_self_x.setText(paramArrayOfString[3]);
    this.txt_self_y.setText(paramArrayOfString[4]);
    this.txt_self_z.setText(paramArrayOfString[5]);
    this.txt_zerorate_x.setText(paramArrayOfString[6]);
    this.txt_zerorate_y.setText(paramArrayOfString[7]);
    this.txt_zerorate_z.setText(paramArrayOfString[8]);
    this.txt_diff_x.setText(Integer.toString(Integer.parseInt(paramArrayOfString[3]) - Integer.parseInt(paramArrayOfString[0])));
    this.txt_diff_y.setText(Integer.toString(Integer.parseInt(paramArrayOfString[4]) - Integer.parseInt(paramArrayOfString[1])));
    this.txt_diff_z.setText(Integer.toString(Integer.parseInt(paramArrayOfString[5]) - Integer.parseInt(paramArrayOfString[2])));
    if (this.mCalibrationPass)
    {
      this.txt_calibration.setText(2131165274);
      this.txt_calibration.setTextColor(-16776961);
    }
    for (;;)
    {
      if (paramString != null)
      {
        if ((!this.mZeroRatePass) || (!paramString.contains("1"))) {
          break;
        }
        FtUtil.log_d("FragmentGyroscopeSTMicroTablet", "showTestResults", "resultValue catains TEST_SUCCESS");
        this.txtresult.setText(2131165274);
        this.txtresult.setTextColor(-16776961);
        paramHandler.sendEmptyMessage(UIGeomagneticGyro.WHAT_GYROSCOPE_PASS);
      }
      return;
      this.txt_calibration.setText(2131165275);
      this.txt_calibration.setTextColor(-65536);
    }
    if (paramString.contains("0"))
    {
      FtUtil.log_d("FragmentGyroscopeSTMicroTablet", "showTestResults", "resultValue catains TEST_FAIL");
      this.txtresult.setText(2131165275);
      this.txtresult.setTextColor(-65536);
      paramHandler.sendEmptyMessage(UIGeomagneticGyro.WHAT_GYROSCOPE_FAIL);
      return;
    }
    FtUtil.log_d("FragmentGyroscopeSTMicroTablet", "showTestResults", "error: RETRY");
    this.txtresult.setText(2131165276);
    this.txtresult.setTextColor(-65536);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    FtUtil.log_d("FragmentGyroscopeSTMicroTablet", "onCreateView", null);
    View localView = paramLayoutInflater.inflate(2130903051, null);
    init(localView);
    return localView;
  }
  
  public void update(DataGyroSTMicroTablet paramDataGyroSTMicroTablet, final Handler paramHandler)
  {
    boolean bool1 = true;
    super.onResume();
    this.mGyroZeroRateText.setVisibility(0);
    this.mGyroSelfTestLayout.setVisibility(8);
    this.txtresult.setVisibility(8);
    this.txtresult.setText("");
    for (;;)
    {
      try
      {
        String str = FileUtils.readTextFile(new File("/sys/class/sensors/gyro_sensor/selftest"), 8192, null);
        this.mSelfTestResults = str.split(",");
        if (this.mSelfTestResults[11] != null)
        {
          if (!this.mSelfTestResults[10].contains("1")) {
            break label311;
          }
          bool2 = bool1;
          this.mZeroRatePass = bool2;
          if (!this.mSelfTestResults[11].contains("1")) {
            break label317;
          }
          this.mCalibrationPass = bool1;
          FtUtil.log_d("FragmentGyroscopeSTMicroTablet", "update", "mZeroRatePass: " + this.mZeroRatePass);
          if (this.mZeroRatePass)
          {
            this.mGyroZeroRateText.setText("Zero Rate Level Check:  PASS");
            this.mGyroZeroRateText.setTextColor(-16776961);
            this.txtresult.setVisibility(0);
            this.mHandler.postDelayed(new Runnable()
            {
              public void run()
              {
                FragmentGyroscopeSTMicroTablet.this.mGyroSelfTestLayout.setVisibility(0);
                FragmentGyroscopeSTMicroTablet.this.showTestResults(FragmentGyroscopeSTMicroTablet.this.mSelfTestResults, FragmentGyroscopeSTMicroTablet.this.mSelfTestResults[9], paramHandler);
              }
            }, 1000L);
          }
        }
        else
        {
          FtUtil.log_d("FragmentGyroscopeSTMicroTablet", "parse error: Gyro self-test ", "ret selftest: " + str);
          return;
        }
      }
      catch (Exception localException)
      {
        FtUtil.log_d("FragmentGyroscopeSTMicroTablet", "update", "Exception:" + localException.getCause());
        return;
      }
      this.mGyroZeroRateText.setText("Zero Rate Level Check:  FAIL");
      this.mGyroZeroRateText.setTextColor(-65536);
      this.mHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          paramHandler.sendEmptyMessage(UIGeomagneticGyro.WHAT_GYROSCOPE_FAIL);
        }
      }, 1000L);
      return;
      label311:
      boolean bool2 = false;
      continue;
      label317:
      bool1 = false;
    }
  }
  
  public static class DataGyroSTMicroTablet {}
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.FragmentGyroscopeSTMicroTablet
 * JD-Core Version:    0.7.1
 */