package com.sec.factory.app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.widget.TextView;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.modules.ModuleTouchScreen;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;

public class UITSPSelfTest
  extends UIBase
{
  private final String STRING_CHIP = "Chip :    ";
  private final String STRING_FW_VER_PANEL = "Panel :    ";
  private final String STRING_FW_VER_PHONE = "Phone :    ";
  private final String STRING_LOADING = "Loading...";
  private final String STRING_NODE_MAX = "Max :    ";
  private final String STRING_NODE_MIN = "Min :    ";
  private final String STRING_SPEC = "Spec ";
  private final String STRING_VENDOR = "Vendor :    ";
  private final String TSP_RESULT_FAIL = "FAIL";
  private final String TSP_RESULT_NA = "NA";
  private final int WHAT_TEST_FINISH = 2 + ModuleTouchScreen.TSP_WHAT_SCOPE_MAX;
  private final int WHAT_TEST_START = 1 + ModuleTouchScreen.TSP_WHAT_SCOPE_MAX;
  private Item[] mDisplayItem;
  private boolean mFlagTestFinish = false;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      FtUtil.log_d(UITSPSelfTest.this.CLASS_NAME, "mHandler.handleMessage", "msg.what : " + ModuleTouchScreen.convertorTSPWhat(paramAnonymousMessage.what));
      FtUtil.log_d(UITSPSelfTest.this.CLASS_NAME, "mHandler.handleMessage", "msg.arg1 : " + ModuleTouchScreen.convertorTSPID(paramAnonymousMessage.arg1));
      FtUtil.log_d(UITSPSelfTest.this.CLASS_NAME, "mHandler.handleMessage", "msg.arg2 : " + ModuleTouchScreen.convertorTSPID(paramAnonymousMessage.arg2));
      String str1 = UITSPSelfTest.this.CLASS_NAME;
      StringBuilder localStringBuilder = new StringBuilder().append("msg.obj : ");
      String str2;
      if (paramAnonymousMessage.obj != null)
      {
        str2 = paramAnonymousMessage.obj.toString();
        FtUtil.log_d(str1, "mHandler.handleMessage", str2);
        if (paramAnonymousMessage.what != ModuleTouchScreen.TSP_WHAT_STATUS_OK) {
          break label220;
        }
        FtUtil.log_d(UITSPSelfTest.this.CLASS_NAME, "mHandler.handleMessage", "TSP_WHAT_STATUS_OK");
        UITSPSelfTest.this.displayTSPItem(paramAnonymousMessage.arg1, paramAnonymousMessage.obj.toString());
        UITSPSelfTest.this.startTSPTest();
      }
      label220:
      do
      {
        return;
        str2 = "null";
        break;
        if (paramAnonymousMessage.what == ModuleTouchScreen.TSP_WHAT_STATUS_NG)
        {
          FtUtil.log_d(UITSPSelfTest.this.CLASS_NAME, "mHandler.handleMessage", "TSP_WHAT_STATUS_NG");
          UITSPSelfTest.access$202(UITSPSelfTest.this, false);
          UITSPSelfTest.this.displayTSPItem(paramAnonymousMessage.arg1, "FAIL");
          UITSPSelfTest.this.startTSPTest();
          return;
        }
        if (paramAnonymousMessage.what == ModuleTouchScreen.TSP_WHAT_STATUS_NA)
        {
          FtUtil.log_d(UITSPSelfTest.this.CLASS_NAME, "mHandler.handleMessage", "TSP_WHAT_STATUS_NA");
          UITSPSelfTest.this.displayTSPItem(paramAnonymousMessage.arg1, "NA");
          UITSPSelfTest.this.startTSPTest();
          return;
        }
        if (paramAnonymousMessage.what == UITSPSelfTest.this.WHAT_TEST_START)
        {
          FtUtil.log_d(UITSPSelfTest.this.CLASS_NAME, "mHandler.handleMessage", "WHAT_TEST_START");
          UITSPSelfTest.this.startTSPTest();
          return;
        }
      } while (paramAnonymousMessage.what != UITSPSelfTest.this.WHAT_TEST_FINISH);
      FtUtil.log_d(UITSPSelfTest.this.CLASS_NAME, "mHandler.handleMessage", "WHAT_TEST_FINISH");
    }
  };
  private boolean mIsPass = true;
  private ModuleTouchScreen mModuleTouchScreen;
  private TextView mTextViewChipName;
  private TextView mTextViewFWCheck;
  private TextView mTextViewFWVerPanel;
  private TextView mTextViewFWVerPhone;
  private TextView mTextViewName;
  private TextView mTextViewRefMax;
  private TextView mTextViewRefMin;
  private TextView mTextViewResult;
  private TextView mTextViewSpec;
  private TextView mTextViewVendorName;
  
  public UITSPSelfTest()
  {
    super("UITSPSelfTest");
  }
  
  private void displayTSPInitialized()
  {
    int i = 0;
    if (i < this.mDisplayItem.length)
    {
      if (this.mDisplayItem[i].mId == ModuleTouchScreen.TSP_ID__EXPANSION__CONNECTION)
      {
        this.mTextViewSpec.setText("Spec (Loading...~Loading...)");
        this.mTextViewSpec.setVisibility(0);
        this.mTextViewRefMin.setText("Min :    Loading...");
        this.mTextViewRefMin.setVisibility(0);
        this.mTextViewRefMax.setText("Max :    Loading...");
        this.mTextViewRefMax.setVisibility(0);
      }
      for (;;)
      {
        i++;
        break;
        if (this.mDisplayItem[i].mId == ModuleTouchScreen.TSP_ID__FW_VERSION_BINARY)
        {
          this.mTextViewFWCheck.setVisibility(0);
          this.mTextViewFWVerPhone.setText("Phone :    Loading...");
          this.mTextViewFWVerPhone.setVisibility(0);
        }
        else if (this.mDisplayItem[i].mId == ModuleTouchScreen.TSP_ID__FW_VERSION_IC)
        {
          this.mTextViewFWCheck.setVisibility(0);
          this.mTextViewFWVerPanel.setText("Panel :    Loading...");
          this.mTextViewFWVerPanel.setVisibility(0);
        }
        else if (this.mDisplayItem[i].mId == ModuleTouchScreen.TSP_ID__VENDOR_NAME)
        {
          this.mTextViewName.setVisibility(0);
          this.mTextViewVendorName.setText("Vendor :    Loading...");
          this.mTextViewVendorName.setVisibility(0);
        }
        else if (this.mDisplayItem[i].mId == ModuleTouchScreen.TSP_ID__CHIP_NAME)
        {
          this.mTextViewName.setVisibility(0);
          this.mTextViewChipName.setText("Chip :    Loading...");
          this.mTextViewChipName.setVisibility(0);
        }
      }
    }
    this.mTextViewResult.setText("Loading...");
    float f = Support.FactoryTestMenu.getUIRate(FactoryTestManager.convertorID_XML((byte)10));
    if ((f != 0.0F) && (f != 1.0F))
    {
      this.mTextViewSpec.setTextSize(0, f * this.mTextViewSpec.getTextSize());
      this.mTextViewRefMin.setTextSize(0, f * this.mTextViewRefMin.getTextSize());
      this.mTextViewRefMax.setTextSize(0, f * this.mTextViewRefMax.getTextSize());
      this.mTextViewFWCheck.setTextSize(0, f * this.mTextViewFWCheck.getTextSize());
      this.mTextViewFWVerPhone.setTextSize(0, f * this.mTextViewFWVerPhone.getTextSize());
      this.mTextViewFWVerPanel.setTextSize(0, f * this.mTextViewFWVerPanel.getTextSize());
      this.mTextViewName.setTextSize(0, f * this.mTextViewName.getTextSize());
      this.mTextViewVendorName.setTextSize(0, f * this.mTextViewVendorName.getTextSize());
      this.mTextViewChipName.setTextSize(0, f * this.mTextViewChipName.getTextSize());
      this.mTextViewResult.setTextSize(0, f * this.mTextViewResult.getTextSize());
    }
  }
  
  private void displayTSPItem(int paramInt, String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "displayTSPItem", "ID : " + ModuleTouchScreen.convertorTSPID(paramInt) + " , data : " + paramString);
    if (paramString.equals("NG"))
    {
      paramString = "FAIL";
      FtUtil.log_e(this.CLASS_NAME, "displayTSPItem", "FAIL => TSP_RESULT_VALUE_NG");
      this.mIsPass = false;
    }
    if (paramInt == ModuleTouchScreen.TSP_ID__EXPANSION__CONNECTION)
    {
      FtUtil.log_e(this.CLASS_NAME, "displayTSPItem", "Display => TSP_ID__RESULT_CONNECTION");
      if ((paramString.equals("FAIL")) || (paramString.equals("NA")))
      {
        this.mTextViewRefMin.setText("Min :    " + paramString);
        this.mTextViewRefMax.setText("Max :    " + paramString);
        int n = getItemArrayIndex(ModuleTouchScreen.TSP_ID__EXPANSION__CONNECTION);
        if (n >= 0) {
          this.mDisplayItem[n].mIsDisplay = true;
        }
      }
    }
    label352:
    label494:
    int i;
    label423:
    do
    {
      int j;
      do
      {
        int k;
        do
        {
          int m;
          do
          {
            return;
            String[] arrayOfString = paramString.split(",");
            if (arrayOfString[0].equals("NG"))
            {
              this.mIsPass = false;
              FtUtil.log_e(this.CLASS_NAME, "displayTSPItem", "FAIL => result[0] : TSP_RESULT_VALUE_NG");
            }
            this.mTextViewRefMin.setText("Min :    " + arrayOfString[1]);
            this.mTextViewRefMax.setText("Max :    " + arrayOfString[2]);
            break;
            if (paramInt != ModuleTouchScreen.TSP_ID__FW_VERSION_BINARY) {
              break label352;
            }
            FtUtil.log_e(this.CLASS_NAME, "displayTSPItem", "Display => TSP_ID__FW_VERSION_BINARY");
            this.mTextViewFWVerPhone.setText("Phone :    " + paramString);
            m = getItemArrayIndex(ModuleTouchScreen.TSP_ID__FW_VERSION_BINARY);
          } while (m < 0);
          this.mDisplayItem[m].mIsDisplay = true;
          return;
          if (paramInt != ModuleTouchScreen.TSP_ID__FW_VERSION_IC) {
            break label423;
          }
          FtUtil.log_e(this.CLASS_NAME, "displayTSPItem", "Display => TSP_ID__FW_VERSION_IC");
          this.mTextViewFWVerPanel.setText("Panel :    " + paramString);
          k = getItemArrayIndex(ModuleTouchScreen.TSP_ID__FW_VERSION_IC);
        } while (k < 0);
        this.mDisplayItem[k].mIsDisplay = true;
        return;
        if (paramInt != ModuleTouchScreen.TSP_ID__VENDOR_NAME) {
          break label494;
        }
        FtUtil.log_e(this.CLASS_NAME, "displayTSPItem", "Display => TSP_ID__VENDOR_NAME");
        this.mTextViewVendorName.setText("Vendor :    " + paramString);
        j = getItemArrayIndex(ModuleTouchScreen.TSP_ID__VENDOR_NAME);
      } while (j < 0);
      this.mDisplayItem[j].mIsDisplay = true;
      return;
      if (paramInt != ModuleTouchScreen.TSP_ID__CHIP_NAME) {
        break label562;
      }
      FtUtil.log_e(this.CLASS_NAME, "displayTSPItem", "Display => TSP_ID__CHIP_NAME");
      this.mTextViewChipName.setText("Chip :    " + paramString);
      i = getItemArrayIndex(ModuleTouchScreen.TSP_ID__CHIP_NAME);
    } while (i < 0);
    this.mDisplayItem[i].mIsDisplay = true;
    return;
    label562:
    FtUtil.log_e(this.CLASS_NAME, "displayTSPItem", "ID : Unknown");
  }
  
  private void displayTSPSpec()
  {
    int i = this.mModuleTouchScreen.getTSPConnectionSpecMin();
    int j = this.mModuleTouchScreen.getTSPConnectionSpecMax();
    this.mTextViewSpec.setText("Spec (" + i + "~" + j + ")");
  }
  
  private int getItemArrayIndex(int paramInt)
  {
    for (int i = 0; i < this.mDisplayItem.length; i++) {
      if (this.mDisplayItem[i].mId == paramInt) {
        return i;
      }
    }
    return -1;
  }
  
  private void startTSPTest()
  {
    int i = 0;
    if ((i >= this.mDisplayItem.length) || (!this.mDisplayItem[i].mIsDisplay))
    {
      if (i != this.mDisplayItem.length) {
        break label43;
      }
      testFinish();
    }
    label43:
    do
    {
      return;
      i++;
      break;
      FtUtil.log_d(this.CLASS_NAME, "startTSPTest", "====================");
      if (this.mDisplayItem[i].mId == ModuleTouchScreen.TSP_ID__EXPANSION__CONNECTION)
      {
        this.mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__EXPANSION__CONNECTION, this.mHandler);
        return;
      }
      if (this.mDisplayItem[i].mId == ModuleTouchScreen.TSP_ID__FW_VERSION_BINARY)
      {
        this.mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__FW_VERSION_BINARY, this.mHandler);
        return;
      }
      if (this.mDisplayItem[i].mId == ModuleTouchScreen.TSP_ID__FW_VERSION_IC)
      {
        this.mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__FW_VERSION_IC, this.mHandler);
        return;
      }
      if (this.mDisplayItem[i].mId == ModuleTouchScreen.TSP_ID__VENDOR_NAME)
      {
        this.mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__VENDOR_NAME, this.mHandler);
        return;
      }
    } while (this.mDisplayItem[i].mId != ModuleTouchScreen.TSP_ID__CHIP_NAME);
    this.mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__CHIP_NAME, this.mHandler);
  }
  
  private void testFinish()
  {
    if (this.mIsPass)
    {
      this.mTextViewResult.setText("PASS");
      this.mTextViewResult.setTextColor(-16776961);
      setResult(-1);
      finish();
    }
    for (;;)
    {
      this.mFlagTestFinish = true;
      return;
      this.mTextViewResult.setText("FAIL");
      this.mTextViewResult.setTextColor(-65536);
      setResult(0);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903095);
    this.mTextViewSpec = ((TextView)findViewById(2131296772));
    this.mTextViewRefMin = ((TextView)findViewById(2131296773));
    this.mTextViewRefMax = ((TextView)findViewById(2131296774));
    this.mTextViewFWCheck = ((TextView)findViewById(2131296775));
    this.mTextViewFWVerPhone = ((TextView)findViewById(2131296776));
    this.mTextViewFWVerPanel = ((TextView)findViewById(2131296777));
    this.mTextViewName = ((TextView)findViewById(2131296397));
    this.mTextViewVendorName = ((TextView)findViewById(2131296778));
    this.mTextViewChipName = ((TextView)findViewById(2131296779));
    this.mTextViewResult = ((TextView)findViewById(2131296300));
    FtUtil.setRemoveSystemUI(getWindow(), true);
    this.mModuleTouchScreen = ModuleTouchScreen.instance(this);
    Item[] arrayOfItem = new Item[5];
    arrayOfItem[0] = new Item(ModuleTouchScreen.TSP_ID__FW_VERSION_BINARY);
    arrayOfItem[1] = new Item(ModuleTouchScreen.TSP_ID__FW_VERSION_IC);
    arrayOfItem[2] = new Item(ModuleTouchScreen.TSP_ID__VENDOR_NAME);
    arrayOfItem[3] = new Item(ModuleTouchScreen.TSP_ID__CHIP_NAME);
    arrayOfItem[4] = new Item(ModuleTouchScreen.TSP_ID__EXPANSION__CONNECTION);
    this.mDisplayItem = arrayOfItem;
    displayTSPInitialized();
    displayTSPSpec();
    this.mHandler.sendEmptyMessageDelayed(this.WHAT_TEST_START, 500L);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    FtUtil.log_d(this.CLASS_NAME, "onTouchEvent", null);
    if ((this.mFlagTestFinish) && (!this.mIsPass))
    {
      FtUtil.log_d(this.CLASS_NAME, "onTouchEvent", "Test Fail - finish");
      finish();
    }
    return true;
  }
  
  private class Item
  {
    public int mId;
    public boolean mIsDisplay;
    
    Item(int paramInt)
    {
      this.mId = paramInt;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UITSPSelfTest
 * JD-Core Version:    0.7.1
 */