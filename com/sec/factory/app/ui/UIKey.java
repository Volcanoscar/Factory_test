package com.sec.factory.app.ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;

public class UIKey
  extends UIBase
{
  private final int FINISH_HARD_KEY_TEST = 0;
  private final int FINISH_QWERTY_KEY_TEST = 1;
  private final int REQUEST_HARD_KEY_TEST = 0;
  private final int REQUEST_QWERTY_KEY_TEST = 1;
  private AlertDialogEx mAlertDialogEx;
  private boolean mIsQwertyKeyTestStarted = false;
  private boolean mIsStandByQwertyTest = false;
  
  public UIKey()
  {
    super("UIKey", 6);
  }
  
  private void start(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return;
    case 0: 
      Intent localIntent = new Intent(this, UIHardKey.class);
      localIntent.putExtra("requestCode", this.TEST_ID);
      startActivityForResult(localIntent, 0);
      return;
    }
    startActivityForResult(new Intent(this, UIQwertyKey.class), 1);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    switch (paramInt1)
    {
    default: 
    case 0: 
      do
      {
        return;
        if (!Support.Feature.getBoolean("SUPPORT_QWERTY_KEY")) {
          break label96;
        }
        if (paramInt2 == 0)
        {
          finish();
          return;
        }
        this.mIsStandByQwertyTest = true;
        if (!Support.Feature.getBoolean("SUPPORT_NONE_SLIDE_QWERTY_PHONE")) {
          break;
        }
      } while ((!this.mIsStandByQwertyTest) || (this.mIsQwertyKeyTestStarted));
      this.mIsQwertyKeyTestStarted = true;
      start(1);
      return;
      this.mAlertDialogEx.show();
      return;
      label96:
      if (paramInt2 == -1) {
        setTestResult((byte)80);
      }
      for (;;)
      {
        this.mIsQwertyKeyTestStarted = false;
        finish();
        return;
        if (45 != this.TEST_ID) {
          setTestResultFailCase((byte)6);
        } else {
          setTestResultFailCase((byte)this.TEST_ID);
        }
      }
    }
    if (paramInt2 == -1) {
      setTestResult((byte)80);
    }
    for (;;)
    {
      this.mIsQwertyKeyTestStarted = false;
      finish();
      return;
      setTestResultFailCase((byte)6);
    }
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if ((this.mIsStandByQwertyTest) && (paramConfiguration.hardKeyboardHidden != 2))
    {
      this.mAlertDialogEx.dismiss();
      if (!this.mIsQwertyKeyTestStarted)
      {
        this.mIsQwertyKeyTestStarted = true;
        start(1);
      }
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mAlertDialogEx = new AlertDialogEx(this, 2130903072, 2131296491);
    this.mAlertDialogEx.setTitle("QWERTY Key Test Start...");
    this.mAlertDialogEx.setMessage("Slide Up Please...");
    setTestId(getIntent().getIntExtra("requestCode", 6));
    FtUtil.log_v(this.CLASS_NAME, "onCreate", "TEST_ID = " + this.TEST_ID);
    start(0);
  }
  
  protected void onResume()
  {
    super.onResume();
  }
  
  private class AlertDialogEx
  {
    AlertDialog mAlertDialog;
    TextView mMessageView;
    View mView;
    
    public AlertDialogEx(Context paramContext, int paramInt1, int paramInt2)
    {
      this.mView = View.inflate(paramContext, paramInt1, null);
      this.mMessageView = ((TextView)this.mView.findViewById(paramInt2));
      this.mAlertDialog = new AlertDialog.Builder(paramContext).create();
      this.mAlertDialog.setView(this.mView);
      this.mAlertDialog.setCancelable(false);
    }
    
    public void dismiss()
    {
      this.mAlertDialog.dismiss();
    }
    
    public void setMessage(String paramString)
    {
      this.mMessageView.setText(paramString);
    }
    
    public void setTitle(String paramString)
    {
      this.mAlertDialog.setTitle(paramString);
    }
    
    public void show()
    {
      this.mAlertDialog.show();
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIKey
 * JD-Core Version:    0.7.1
 */