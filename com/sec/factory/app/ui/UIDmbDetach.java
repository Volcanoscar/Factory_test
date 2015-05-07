package com.sec.factory.app.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.FtUtil;

public class UIDmbDetach
  extends UIBase
{
  private int Flag = 0;
  private TextView ant_close;
  private TextView ant_open;
  private TextView dmb_detach_passfail;
  private int ispassed = -1;
  private ModuleDevice mModuleDevice;
  
  public UIDmbDetach()
  {
    super("UIDmbDetach", 52);
  }
  
  private void checkPassFail()
  {
    if (this.Flag == 2) {
      writeNV(52, true);
    }
    for (int i = 1; i != 0; i = 0)
    {
      this.dmb_detach_passfail.setText("PASS");
      this.dmb_detach_passfail.setTextColor(-16776961);
      this.ispassed = 80;
      return;
      writeNV(52, false);
    }
    this.dmb_detach_passfail.setText("FAIL");
    this.dmb_detach_passfail.setTextColor(-65536);
    this.ispassed = 70;
  }
  
  private void checkPassResult()
  {
    if (this.ispassed == 80)
    {
      FtUtil.log_d(this.CLASS_NAME, "checkPassResult()", "TEST_DMB_DETACH pass!!!");
      setTestResultWithoutNV((byte)52, (byte)80);
      finishOnPassWithTimer();
    }
    for (;;)
    {
      this.mModuleDevice.stopVibrate();
      this.Flag = 0;
      return;
      if (this.ispassed == 70) {
        setResult(0);
      }
    }
  }
  
  private void init()
  {
    FtUtil.log_d(this.CLASS_NAME, "init()", "init_start");
    this.ant_open = ((TextView)findViewById(2131296388));
    this.ant_close = ((TextView)findViewById(2131296389));
    this.dmb_detach_passfail = ((TextView)findViewById(2131296391));
  }
  
  private void writeNV(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean == true)
    {
      FtUtil.log_e(this.CLASS_NAME, "writeNV", "PASS");
      setTestResult((byte)paramInt, (byte)80);
      return;
    }
    FtUtil.log_e(this.CLASS_NAME, "writeNV", "FAIL");
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    FtUtil.log_d(this.CLASS_NAME, "onCreate()", "startOnCreate()");
    super.onCreate(paramBundle);
    this.mModuleDevice = ModuleDevice.instance(this);
    FtUtil.setRemoveSystemUI(getWindow(), true);
    setContentView(2130903056);
    init();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    FtUtil.log_d(this.CLASS_NAME, "onDestroy", null);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    int i = 1;
    FtUtil.log_d(this.CLASS_NAME, "onKeyDown", "KeyEent=" + paramInt);
    switch (paramInt)
    {
    default: 
      i = super.onKeyDown(paramInt, paramKeyEvent);
    }
    do
    {
      return i;
      this.ant_open.setText("ANT OPEN : PASS");
      this.ant_open.setTextColor(-16776961);
      this.mModuleDevice.stopVibrate();
      this.mModuleDevice.startVibrate(1000L);
      this.Flag = i;
      return i;
      this.ant_close.setText("ANT CLOSE : PASS");
      this.ant_close.setTextColor(-16776961);
      this.mModuleDevice.stopVibrate();
      this.mModuleDevice.startVibrate(1000L);
    } while (this.Flag != i);
    this.Flag = 2;
    checkPassFail();
    checkPassResult();
    return i;
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
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIDmbDetach
 * JD-Core Version:    0.7.1
 */