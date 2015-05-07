package com.sec.factory.app.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;

public class UILedTest
  extends UIBase
{
  private static TextView textColor;
  private final int BLACK_BLUE = 3;
  private final int BLACK_GREEN = 2;
  private final int BLACK_RED = 1;
  private final int BLACK_START = 0;
  private final int FINISH = 4;
  private boolean isLedSupport;
  private boolean mIsLongPress = false;
  private ModuleDevice mModuleDevice;
  private String mTestCase = Support.FactoryTestMenu.getTestCase(FactoryTestManager.convertorID_XML((byte)0));
  private int test_state = 0;
  
  public UILedTest()
  {
    super("UILedTest");
  }
  
  private void changeTestLevel(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      FtUtil.log_i(this.CLASS_NAME, "changeTestLevel", "default, test_state: " + this.test_state);
      return;
    case 0: 
      if (this.isLedSupport) {
        this.mModuleDevice.setLEDlamp(2);
      }
      this.test_state = 1;
      textColor.setText("RED");
      textColor.setTextColor(-65536);
      FtUtil.log_i(this.CLASS_NAME, "changeTestLevel", "START -> RED");
      return;
    case 1: 
      if (this.isLedSupport) {
        this.mModuleDevice.setLEDlamp(3);
      }
      this.test_state = 2;
      textColor.setText("GREEN");
      textColor.setTextColor(-16711936);
      FtUtil.log_i(this.CLASS_NAME, "changeTestLevel", "RED -> GREEN");
      return;
    case 2: 
      if (this.isLedSupport) {
        this.mModuleDevice.setLEDlamp(4);
      }
      this.test_state = 3;
      textColor.setText("BLUE");
      textColor.setTextColor(-16776961);
      FtUtil.log_i(this.CLASS_NAME, "changeTestLevel", "GREEN -> BLUE");
      return;
    }
    if (this.isLedSupport) {
      this.mModuleDevice.setLEDlamp(0);
    }
    this.test_state = 4;
    setResult(-1);
    finish();
    FtUtil.log_i(this.CLASS_NAME, "changeTestLevel", "BLUE -> FINISH");
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903073);
    textColor = (TextView)findViewById(2131296492);
    this.mModuleDevice = ModuleDevice.instance(this);
    FtUtil.setRemoveSystemUI(getWindow(), true);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (!this.mIsLongPress) {
      this.mIsLongPress = paramKeyEvent.isLongPress();
    }
    if (!this.mIsLongPress) {
      switch (paramInt)
      {
      }
    }
    for (;;)
    {
      return super.onKeyDown(paramInt, paramKeyEvent);
      changeTestLevel(this.test_state);
    }
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    this.mIsLongPress = false;
    return super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  protected void onPause()
  {
    super.onPause();
    if ((this.test_state != 4) && (this.isLedSupport)) {
      this.mModuleDevice.setLEDlamp(0);
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    this.isLedSupport = Support.FactoryTestMenu.getTestCase(FactoryTestManager.convertorID_XML((byte)0)).contains("LED");
    changeTestLevel(this.test_state);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UILedTest
 * JD-Core Version:    0.7.1
 */