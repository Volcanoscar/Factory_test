package com.sec.factory.app.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;

public class UIVibrate
  extends UIBase
{
  private static View rgb_view;
  private final int STATE_BLUE = 3;
  private final int STATE_FINISH = 5;
  private final int STATE_GREEN = 2;
  private final int STATE_RED = 1;
  private final int STATE_START = 0;
  private final int STATE_WHITE_BLACK = 4;
  private boolean isLedSupport;
  private boolean mIsLongPress = false;
  private ModuleDevice mModuleDevice;
  private String mTestCase = Support.FactoryTestMenu.getTestCase(FactoryTestManager.convertorID_XML((byte)0));
  private int test_state = 0;
  
  public UIVibrate()
  {
    super("UIVibrate");
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
      rgb_view.setBackgroundColor(-65536);
      FtUtil.log_i(this.CLASS_NAME, "changeTestLevel", "START -> RED");
      return;
    case 1: 
      if (this.isLedSupport) {
        this.mModuleDevice.setLEDlamp(3);
      }
      this.test_state = 2;
      rgb_view.setBackgroundColor(-16711936);
      FtUtil.log_i(this.CLASS_NAME, "changeTestLevel", "RED -> GREEN");
      return;
    case 2: 
      if (this.isLedSupport) {
        this.mModuleDevice.setLEDlamp(4);
      }
      this.test_state = 3;
      rgb_view.setBackgroundColor(-16776961);
      FtUtil.log_i(this.CLASS_NAME, "changeTestLevel", "GREEN -> BLUE");
      return;
    case 3: 
      if (this.isLedSupport) {
        this.mModuleDevice.setLEDlamp(1);
      }
      this.test_state = 4;
      rgb_view.setVisibility(8);
      FtUtil.log_i(this.CLASS_NAME, "changeTestLevel", "BLUE -> WHITE_BLACK");
      return;
    }
    this.test_state = 5;
    if (this.isLedSupport) {
      this.mModuleDevice.setLEDlamp(0);
    }
    FtUtil.log_i(this.CLASS_NAME, "changeTestLevel", "WHITE_BLACK -> FINISH");
    setResult(-1);
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903097);
    rgb_view = findViewById(2131296788);
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
    if ((this.test_state != 5) && (this.isLedSupport)) {
      this.mModuleDevice.setLEDlamp(0);
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    if ((this.mTestCase != null) && (this.mTestCase.contains("LED"))) {}
    for (boolean bool = true;; bool = false)
    {
      this.isLedSupport = bool;
      changeTestLevel(this.test_state);
      return;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIVibrate
 * JD-Core Version:    0.7.1
 */