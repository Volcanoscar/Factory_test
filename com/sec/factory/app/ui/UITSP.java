package com.sec.factory.app.ui;

import android.content.Intent;
import android.os.Bundle;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.TestCase;

public class UITSP
  extends UIBase
{
  private final int FINISH_TSP_PATTERN_TEST = 1;
  private final int FINISH_TSP_REFERENCE_MODE_TEST = 0;
  private final int REQUEST_TSP_PATTERN_TEST = 1;
  private final int REQUEST_TSP_REFERENCE_MODE_TEST = 0;
  private boolean TSP_REFERENCE_MODE_RESULT = true;
  private String mDeviceType;
  private String mLcdType;
  private String mTestCase = Support.FactoryTestMenu.getTestCase(FactoryTestManager.convertorID_XML((byte)10));
  
  public UITSP()
  {
    super("UITSP", 10);
  }
  
  private void start(int paramInt)
  {
    switch (paramInt)
    {
    }
    do
    {
      setTestResult((byte)80);
      finish();
      return;
      if (("OCTA".equals(this.mLcdType)) || ("tablet".equals(this.mDeviceType)))
      {
        if (Support.TestCase.getEnabled("IS_TSP_STANDARD"))
        {
          Intent localIntent3 = new Intent(this, UITSPSelfTest.class);
          localIntent3.addFlags(65536);
          startActivityForResult(localIntent3, 0);
          return;
        }
        Intent localIntent4 = new Intent(this, UITspReferenceMode.class);
        localIntent4.addFlags(65536);
        startActivityForResult(localIntent4, 0);
        return;
      }
      FtUtil.log_e(this.CLASS_NAME, "start", "mLcdType:TFT , mDeviceType:phone");
      if (this.mTestCase.contains("PATTERN"))
      {
        FtUtil.log_d(this.CLASS_NAME, "onActivityResult", "resultCode = " + this.TSP_REFERENCE_MODE_RESULT);
        Intent localIntent2 = new Intent(this, UITspPattern.class);
        localIntent2.putExtra("TEST_TSP_SELF", this.TSP_REFERENCE_MODE_RESULT);
        startActivityForResult(localIntent2, 1);
        return;
      }
    } while (!this.mTestCase.contains("STYLE_X"));
    FtUtil.log_d(this.CLASS_NAME, "onActivityResult", "resultCode = " + this.TSP_REFERENCE_MODE_RESULT);
    Intent localIntent1 = new Intent(this, UITspPatternStyleX.class);
    localIntent1.putExtra("TEST_TSP_SELF", this.TSP_REFERENCE_MODE_RESULT);
    startActivityForResult(localIntent1, 1);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    switch (paramInt1)
    {
    default: 
      return;
    case 0: 
      if (paramInt2 == -1)
      {
        this.TSP_REFERENCE_MODE_RESULT = true;
        start(1);
        return;
      }
      this.TSP_REFERENCE_MODE_RESULT = false;
      if (44 != this.TEST_ID) {
        setTestResultFailCase((byte)10);
      }
      for (;;)
      {
        finish();
        return;
        setTestResultFailCase((byte)this.TEST_ID);
      }
    }
    if (paramInt2 == -1) {
      setTestResult((byte)80);
    }
    for (;;)
    {
      finish();
      return;
      if (44 != this.TEST_ID) {
        setTestResultFailCase((byte)10);
      } else {
        setTestResultFailCase((byte)this.TEST_ID);
      }
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setTestId(getIntent().getIntExtra("requestCode", 10));
    FtUtil.log_v(this.CLASS_NAME, "onCreate", "TEST_ID = " + this.TEST_ID);
    this.mLcdType = Support.Feature.getString("PANEL_TYPE");
    this.mDeviceType = Support.Feature.getString("DEVICE_TYPE");
    start(0);
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UITSP
 * JD-Core Version:    0.7.1
 */