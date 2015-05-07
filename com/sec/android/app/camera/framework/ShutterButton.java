package com.sec.android.app.camera.framework;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ShutterButton
  extends ImageView
{
  private OnShutterButtonListener mListener;
  private boolean mOldPressed;
  
  public ShutterButton(Context paramContext)
  {
    super(paramContext);
  }
  
  public ShutterButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ShutterButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void callShutterButtonFocus(boolean paramBoolean)
  {
    if (this.mListener != null) {
      this.mListener.onShutterButtonFocus(this, paramBoolean);
    }
  }
  
  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    final boolean bool = isPressed();
    if (bool != this.mOldPressed)
    {
      if (bool) {
        break label41;
      }
      post(new Runnable()
      {
        public void run()
        {
          ShutterButton.this.callShutterButtonFocus(bool);
        }
      });
    }
    for (;;)
    {
      this.mOldPressed = bool;
      return;
      label41:
      callShutterButtonFocus(bool);
    }
  }
  
  public boolean performClick()
  {
    boolean bool = super.performClick();
    if (this.mListener != null) {
      this.mListener.onShutterButtonClick(this);
    }
    return bool;
  }
  
  public void setOnShutterButtonListener(OnShutterButtonListener paramOnShutterButtonListener)
  {
    this.mListener = paramOnShutterButtonListener;
  }
  
  public static abstract interface OnShutterButtonListener
  {
    public abstract void onShutterButtonClick(ShutterButton paramShutterButton);
    
    public abstract void onShutterButtonFocus(ShutterButton paramShutterButton, boolean paramBoolean);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.camera.framework.ShutterButton
 * JD-Core Version:    0.7.1
 */