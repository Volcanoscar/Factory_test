package com.sec.factory.app.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class UIIrLedFailPop
  extends Activity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(2130903069);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    finish();
    return true;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIIrLedFailPop
 * JD-Core Version:    0.7.1
 */