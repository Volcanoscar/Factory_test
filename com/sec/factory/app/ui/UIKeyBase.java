package com.sec.factory.app.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.sec.factory.support.FtUtil;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class UIKeyBase
  extends UIBase
{
  protected String CLASS_NAME = "UIKeyBase";
  protected Comparator<KeyTestItem> mComparator = new Comparator()
  {
    public int compare(UIKeyBase.KeyTestItem paramAnonymousKeyTestItem1, UIKeyBase.KeyTestItem paramAnonymousKeyTestItem2)
    {
      if (paramAnonymousKeyTestItem1.mKeyCode > paramAnonymousKeyTestItem2.mKeyCode) {
        return 1;
      }
      if (paramAnonymousKeyTestItem1.mKeyCode < paramAnonymousKeyTestItem2.mKeyCode) {
        return -1;
      }
      return 0;
    }
  };
  protected View.OnClickListener mExitButtonAction = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      UIKeyBase.this.setResult(0);
      UIKeyBase.this.finish();
    }
  };
  private boolean mIsLongPress;
  protected ArrayList<KeyTestItem> mTestList = new ArrayList();
  
  public UIKeyBase(String paramString)
  {
    super(paramString);
  }
  
  protected boolean isLongPress()
  {
    return this.mIsLongPress;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (!isLongPress()) {
      setLongPress(false, paramKeyEvent);
    }
    switch (paramInt)
    {
    default: 
      return true;
    }
    FtUtil.log_i(this.CLASS_NAME, "onKeyDown", "KEYCODE_HEADSETHOOK");
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    setLongPress(true, paramKeyEvent);
    switch (paramInt)
    {
    default: 
      return true;
    }
    FtUtil.log_i(this.CLASS_NAME, "onKeyUp", "KEYCODE_HEADSETHOOK");
    return super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  protected boolean setLongPress(boolean paramBoolean, KeyEvent paramKeyEvent)
  {
    if (!paramBoolean) {
      if (this.mIsLongPress) {}
    }
    for (this.mIsLongPress = paramKeyEvent.isLongPress();; this.mIsLongPress = false) {
      return true;
    }
  }
  
  protected class HardKeyItem
  {
    protected TextView mHardKeyText = null;
    
    protected HardKeyItem(TextView paramTextView)
    {
      this.mHardKeyText = paramTextView;
    }
    
    protected void setAttribute_TextView(int paramInt, String paramString, float paramFloat1, float paramFloat2)
    {
      if (this.mHardKeyText != null)
      {
        this.mHardKeyText.setVisibility(paramInt);
        if (!paramString.equals("DUMMY")) {
          break label71;
        }
        this.mHardKeyText.setText(" ");
        this.mHardKeyText.setBackgroundColor(UIKeyBase.this.getResources().getColor(2131296398));
      }
      for (;;)
      {
        this.mHardKeyText.setTextSize(paramFloat1);
        this.mHardKeyText.setHeight((int)paramFloat2);
        return;
        label71:
        this.mHardKeyText.setText(paramString);
      }
    }
  }
  
  protected class KeyTestItem
  {
    protected boolean mEnabled = false;
    protected UIKeyBase.HardKeyItem mHardKeyItem = null;
    protected boolean mIsPassed = false;
    protected boolean mIsTouchKey = false;
    protected int mKeyCode;
    protected String mKeyCode_String;
    protected UIKeyBase.TouchKeyItem mTouchKeyItem = null;
    
    protected KeyTestItem(int paramInt)
    {
      this.mKeyCode = paramInt;
    }
    
    protected KeyTestItem(int paramInt, UIKeyBase.HardKeyItem paramHardKeyItem, UIKeyBase.TouchKeyItem paramTouchKeyItem, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.mKeyCode = paramInt;
      this.mHardKeyItem = paramHardKeyItem;
      this.mTouchKeyItem = paramTouchKeyItem;
      this.mEnabled = paramBoolean1;
      this.mIsTouchKey = paramBoolean2;
      if (!paramBoolean1) {
        removeHardKeyItem(this.mHardKeyItem);
      }
      while (!paramBoolean2) {
        return;
      }
      enableTouchKeyItem(this.mTouchKeyItem);
    }
    
    protected KeyTestItem(int paramInt, UIKeyBase.HardKeyItem paramHardKeyItem, String paramString)
    {
      this.mKeyCode = paramInt;
      this.mHardKeyItem = paramHardKeyItem;
      this.mKeyCode_String = paramString;
      if (paramString.equals("DUMMY")) {
        this.mIsPassed = true;
      }
    }
    
    private void enableTouchKeyItem(UIKeyBase.TouchKeyItem paramTouchKeyItem)
    {
      if (paramTouchKeyItem != null)
      {
        paramTouchKeyItem.mTouchKeyText.setVisibility(0);
        paramTouchKeyItem.mTouchKeySensitivityText.setVisibility(0);
        paramTouchKeyItem.mTouchKeyTestResult.setVisibility(0);
      }
    }
    
    private void removeHardKeyItem(UIKeyBase.HardKeyItem paramHardKeyItem)
    {
      paramHardKeyItem.mHardKeyText.setVisibility(8);
    }
    
    public TextView getHardKeyTextView()
    {
      return this.mHardKeyItem.mHardKeyText;
    }
    
    public int getKeyCode()
    {
      return this.mKeyCode;
    }
    
    public String getKeyCode_String()
    {
      return this.mKeyCode_String;
    }
    
    public void remobeHardKeyItem()
    {
      removeHardKeyItem(this.mHardKeyItem);
    }
  }
  
  protected class TouchKeyItem
  {
    protected TextView mTouchKeySensitivityText;
    protected TextView mTouchKeyTestResult;
    protected TextView mTouchKeyText;
    
    protected TouchKeyItem(TextView paramTextView1, TextView paramTextView2, TextView paramTextView3)
    {
      this.mTouchKeyText = paramTextView1;
      this.mTouchKeySensitivityText = paramTextView2;
      this.mTouchKeyTestResult = paramTextView3;
    }
  }
  
  protected class TouchKeyItemSet
  {
    private HashMap<Integer, UIKeyBase.TouchKeyItem> mTouchKeyMap = new HashMap();
    
    protected TouchKeyItemSet() {}
    
    protected UIKeyBase.TouchKeyItem get(int paramInt)
    {
      return (UIKeyBase.TouchKeyItem)this.mTouchKeyMap.get(Integer.valueOf(paramInt));
    }
    
    protected void put(int paramInt, UIKeyBase.TouchKeyItem paramTouchKeyItem)
    {
      this.mTouchKeyMap.put(Integer.valueOf(paramInt), paramTouchKeyItem);
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIKeyBase
 * JD-Core Version:    0.7.1
 */