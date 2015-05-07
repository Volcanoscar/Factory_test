package com.sec.android.app.camera.framework;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

public class FocusRectangle
  extends View
{
  public FocusRectangle(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void setDrawable(int paramInt)
  {
    setBackgroundDrawable(getContext().getResources().getDrawable(paramInt));
  }
  
  public void clear()
  {
    setBackgroundDrawable(null);
  }
  
  public void showFail()
  {
    setDrawable(2130837509);
  }
  
  public void showStart()
  {
    setDrawable(2130837511);
  }
  
  public void showSuccess()
  {
    setDrawable(2130837510);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.camera.framework.FocusRectangle
 * JD-Core Version:    0.7.1
 */