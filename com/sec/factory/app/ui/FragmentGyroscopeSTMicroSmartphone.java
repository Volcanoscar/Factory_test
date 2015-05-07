package com.sec.factory.app.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import com.sec.factory.support.FtUtil;
import java.io.File;

public class FragmentGyroscopeSTMicroSmartphone
  extends Fragment
{
  private final String CLASS_NAME = "FragmentGyroscopeSTMicroSmartphone";
  private TableLayout mGyroSelfTestLayout;
  private TextView mGyroZeroRateText;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage) {}
  };
  private String[] mSelfTestResults;
  private boolean mZeroRatePass = false;
  private TextView txt_X;
  private TextView txt_Y;
  private TextView txt_Z;
  private TextView txt_diff_x;
  private TextView txt_diff_y;
  private TextView txt_diff_z;
  private TextView txt_fifo_result;
  private TextView txt_fifo_subject;
  private TextView txt_prime_x;
  private TextView txt_prime_y;
  private TextView txt_prime_z;
  private TextView txt_sub1;
  private TextView txt_sub2;
  private TextView txt_sub3;
  private TextView txt_xyz_add;
  private TextView txt_xyz_x;
  private TextView txt_xyz_y;
  private TextView txt_xyz_z;
  private TextView txt_xyzprime_add;
  private TextView txtresult;
  
  private void init(View paramView)
  {
    this.txtresult = ((TextView)paramView.findViewById(2131296300));
    this.txt_xyz_add = ((TextView)paramView.findViewById(2131296357));
    this.txt_xyzprime_add = ((TextView)paramView.findViewById(2131296362));
    this.txt_X = ((TextView)paramView.findViewById(2131296350));
    this.txt_Y = ((TextView)paramView.findViewById(2131296351));
    this.txt_Z = ((TextView)paramView.findViewById(2131296352));
    this.txt_sub1 = ((TextView)paramView.findViewById(2131296353));
    this.txt_sub2 = ((TextView)paramView.findViewById(2131296358));
    this.txt_sub3 = ((TextView)paramView.findViewById(2131296363));
    this.txt_xyz_x = ((TextView)paramView.findViewById(2131296354));
    this.txt_xyz_y = ((TextView)paramView.findViewById(2131296355));
    this.txt_xyz_z = ((TextView)paramView.findViewById(2131296356));
    this.txt_prime_x = ((TextView)paramView.findViewById(2131296359));
    this.txt_prime_y = ((TextView)paramView.findViewById(2131296360));
    this.txt_prime_z = ((TextView)paramView.findViewById(2131296361));
    this.txt_diff_x = ((TextView)paramView.findViewById(2131296364));
    this.txt_diff_y = ((TextView)paramView.findViewById(2131296365));
    this.txt_diff_z = ((TextView)paramView.findViewById(2131296366));
    this.txt_fifo_subject = ((TextView)paramView.findViewById(2131296345));
    this.txt_fifo_result = ((TextView)paramView.findViewById(2131296346));
    this.mGyroZeroRateText = ((TextView)paramView.findViewById(2131296347));
    this.mGyroSelfTestLayout = ((TableLayout)paramView.findViewById(2131296348));
  }
  
  private void showTestResults(String[] paramArrayOfString, String paramString, Handler paramHandler)
  {
    this.txt_xyz_x.setText(paramArrayOfString[0]);
    this.txt_xyz_y.setText(paramArrayOfString[1]);
    this.txt_xyz_z.setText(paramArrayOfString[2]);
    this.txt_prime_x.setText(paramArrayOfString[3]);
    this.txt_prime_y.setText(paramArrayOfString[4]);
    this.txt_prime_z.setText(paramArrayOfString[5]);
    this.txt_diff_x.setText(Integer.toString(Integer.parseInt(paramArrayOfString[3]) - Integer.parseInt(paramArrayOfString[0])));
    this.txt_diff_y.setText(Integer.toString(Integer.parseInt(paramArrayOfString[4]) - Integer.parseInt(paramArrayOfString[1])));
    this.txt_diff_z.setText(Integer.toString(Integer.parseInt(paramArrayOfString[5]) - Integer.parseInt(paramArrayOfString[2])));
    if (paramString != null)
    {
      if ((this.mZeroRatePass) && (paramString.contains("1")))
      {
        this.txtresult.setText(2131165274);
        this.txtresult.setTextColor(-16776961);
        paramHandler.sendEmptyMessage(UIGeomagneticGyro.WHAT_GYROSCOPE_PASS);
      }
    }
    else {
      return;
    }
    if (paramString.contains("0"))
    {
      this.txtresult.setText(2131165275);
      this.txtresult.setTextColor(-65536);
      return;
    }
    this.txtresult.setText(2131165276);
    this.txtresult.setTextColor(-65536);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    FtUtil.log_d("FragmentGyroscopeSTMicroSmartphone", "onCreateView", null);
    View localView = paramLayoutInflater.inflate(2130903050, null);
    init(localView);
    return localView;
  }
  
  public void update(DataGyroSTMicroSmartphone paramDataGyroSTMicroSmartphone, final Handler paramHandler)
  {
    super.onResume();
    this.mGyroZeroRateText.setVisibility(0);
    this.mGyroSelfTestLayout.setVisibility(8);
    this.txtresult.setVisibility(8);
    this.txtresult.setText("");
    try
    {
      this.mSelfTestResults = FileUtils.readTextFile(new File("/sys/class/sensors/gyro_sensor/selftest"), 8192, null).split(",");
      if (this.mSelfTestResults[7] != null)
      {
        boolean bool1 = this.mSelfTestResults[7].contains("1");
        boolean bool2 = false;
        if (bool1) {
          bool2 = true;
        }
        this.mZeroRatePass = bool2;
      }
      if (this.mZeroRatePass)
      {
        this.mGyroZeroRateText.setText("Zero Rate Level Check:  PASS");
        this.mGyroZeroRateText.setTextColor(-16776961);
        this.txtresult.setVisibility(0);
        this.mHandler.postDelayed(new Runnable()
        {
          public void run()
          {
            FragmentGyroscopeSTMicroSmartphone.this.mGyroSelfTestLayout.setVisibility(0);
            FragmentGyroscopeSTMicroSmartphone.this.showTestResults(FragmentGyroscopeSTMicroSmartphone.this.mSelfTestResults, FragmentGyroscopeSTMicroSmartphone.this.mSelfTestResults[6], paramHandler);
          }
        }, 1000L);
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
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public static class DataGyroSTMicroSmartphone {}
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.FragmentGyroscopeSTMicroSmartphone
 * JD-Core Version:    0.7.1
 */