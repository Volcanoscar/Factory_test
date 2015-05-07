package com.sec.factory.app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.app.factorytest.FactoryTestPhone;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.modules.SensorNotification;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;

public class UIGrip
  extends UIBase
{
  private final int DELAY_EXIT;
  private int DUMMY = 0;
  private boolean FLAG_EXIT;
  private final int TESTITEM_ID_GRIP1;
  private final int TESTITEM_ID_GRIP2;
  private final int TESTITEM_ID_GRIP3;
  private final int TESTITEM_STATUS_FINISH;
  private final int TESTITEM_STATUS_RELEASE;
  private final int TESTITEM_STATUS_WORKING;
  private final int WHAT_EXIT;
  private float mCurrValue;
  private Handler mHandler;
  private LinearLayout mLinearBackground;
  private ModuleDevice mModuleDevice;
  private ModuleSensor mModuleSensor;
  private float mPrevValue;
  private TableLayout mTableGrip1;
  private TableLayout mTableGrip2;
  private TableLayout mTableGrip3;
  private TestItem[] mTestList;
  private int mTestListIndex;
  private TextView mTextGrip1Step1Result;
  private TextView mTextGrip1Step2;
  private TextView mTextGrip1Step2Result;
  private TextView mTextGrip2Step1Result;
  private TextView mTextGrip2Step2;
  private TextView mTextGrip2Step2Result;
  private TextView mTextGrip3Step1Result;
  private TextView mTextGrip3Step2;
  private TextView mTextGrip3Step2Result;
  
  public UIGrip()
  {
    super("UIGrip", 34);
    int i = this.DUMMY;
    this.DUMMY = (i + 1);
    this.TESTITEM_ID_GRIP1 = i;
    int j = this.DUMMY;
    this.DUMMY = (j + 1);
    this.TESTITEM_ID_GRIP2 = j;
    int k = this.DUMMY;
    this.DUMMY = (k + 1);
    this.TESTITEM_ID_GRIP3 = k;
    int m = this.DUMMY;
    this.DUMMY = (m + 1);
    this.TESTITEM_STATUS_RELEASE = m;
    int n = this.DUMMY;
    this.DUMMY = (n + 1);
    this.TESTITEM_STATUS_WORKING = n;
    int i1 = this.DUMMY;
    this.DUMMY = (i1 + 1);
    this.TESTITEM_STATUS_FINISH = i1;
    this.mTestList = null;
    this.mTestListIndex = 0;
    this.mPrevValue = -1.0F;
    this.mCurrValue = -1.0F;
    this.WHAT_EXIT = (1 + SensorNotification.WHAT_NOTI_SENSOR_MAX);
    this.DELAY_EXIT = 1000;
    this.FLAG_EXIT = true;
    this.mHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what == SensorNotification.WHAT_NOTI_SENSOR_UPDATAE) {
          if ((UIGrip.this.mTestList != null) && (UIGrip.this.mTestList.length > 0)) {
            UIGrip.this.startTest();
          }
        }
        while (paramAnonymousMessage.what != UIGrip.this.WHAT_EXIT) {
          return;
        }
        UIGrip.this.exit();
      }
    };
  }
  
  private void exit()
  {
    int i = 1;
    int j;
    if ((this.mTestList != null) && (this.mTestList.length > 0))
    {
      j = 0;
      if (j < this.mTestList.length)
      {
        if (!this.mTestList[j].mIsPass) {
          i = 0;
        }
      }
      else
      {
        label42:
        if (i == 0) {
          break label68;
        }
        setTestResult((byte)80);
      }
    }
    for (;;)
    {
      finish();
      return;
      j++;
      break;
      i = 0;
      break label42;
      label68:
      setTestResultFailCase((byte)34);
    }
  }
  
  private void initialize()
  {
    FtUtil.log_d(this.CLASS_NAME, "initialize", null);
    int i = Integer.valueOf(Support.FactoryTestMenu.getTestCase(FactoryTestManager.convertorID_XML((byte)34))).intValue();
    FtUtil.log_d(this.CLASS_NAME, "initialize", "count_Grip : " + i);
    int j;
    if (i > 0)
    {
      this.mTestList = new TestItem[i];
      ModuleSensor localModuleSensor = this.mModuleSensor;
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = ModuleSensor.ID_INTENT__GRIP;
      localModuleSensor.SensorOn(arrayOfInt, this.mHandler, 100);
      this.mLinearBackground = ((LinearLayout)findViewById(2131296398));
      if (1 > i) {
        break label395;
      }
      TestItem[] arrayOfTestItem3 = this.mTestList;
      j = 0 + 1;
      arrayOfTestItem3[0] = new TestItem(this.TESTITEM_ID_GRIP1);
      this.mTableGrip1 = ((TableLayout)findViewById(2131296404));
      this.mTextGrip1Step1Result = ((TextView)findViewById(2131296405));
      this.mTextGrip1Step2 = ((TextView)findViewById(2131296406));
      this.mTextGrip1Step2Result = ((TextView)findViewById(2131296407));
      this.mTableGrip1.setVisibility(0);
    }
    for (;;)
    {
      if (2 <= i)
      {
        TestItem[] arrayOfTestItem2 = this.mTestList;
        int k = j + 1;
        arrayOfTestItem2[j] = new TestItem(this.TESTITEM_ID_GRIP2);
        this.mTableGrip2 = ((TableLayout)findViewById(2131296408));
        this.mTextGrip2Step1Result = ((TextView)findViewById(2131296409));
        this.mTextGrip2Step2 = ((TextView)findViewById(2131296410));
        this.mTextGrip2Step2Result = ((TextView)findViewById(2131296411));
        j = k;
      }
      if (3 <= i)
      {
        TestItem[] arrayOfTestItem1 = this.mTestList;
        (j + 1);
        arrayOfTestItem1[j] = new TestItem(this.TESTITEM_ID_GRIP3);
        this.mTableGrip3 = ((TableLayout)findViewById(2131296412));
        this.mTextGrip3Step1Result = ((TextView)findViewById(2131296413));
        this.mTextGrip3Step2 = ((TextView)findViewById(2131296414));
        this.mTextGrip3Step2Result = ((TextView)findViewById(2131296415));
        return;
        this.mHandler.sendEmptyMessageDelayed(this.WHAT_EXIT, 1500L);
        return;
      }
      return;
      label395:
      j = 0;
    }
  }
  
  private void setRelease()
  {
    this.mModuleDevice.stopVibrate();
  }
  
  private void setWorking()
  {
    this.mModuleDevice.startVibrate();
  }
  
  private void startTest()
  {
    if (this.mTestList[this.mTestListIndex].mID == this.TESTITEM_ID_GRIP1)
    {
      String[] arrayOfString3 = this.mModuleSensor.getData(ModuleSensor.ID_INTENT__GRIP);
      if (arrayOfString3 != null)
      {
        this.mTestList[this.mTestListIndex].mValue = arrayOfString3;
        if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_RELEASE)
        {
          this.mCurrValue = Integer.valueOf(this.mTestList[this.mTestListIndex].mValue[1]).intValue();
          if (this.mCurrValue == 1.0F)
          {
            setWorking();
            this.mTextGrip1Step1Result.setVisibility(0);
            this.mTextGrip1Step1Result.setText("Pass");
            this.mTextGrip1Step1Result.setTextColor(-16776961);
            this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_WORKING;
          }
          this.mPrevValue = this.mCurrValue;
        }
      }
    }
    label1327:
    do
    {
      do
      {
        for (;;)
        {
          if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_FINISH)
          {
            this.mPrevValue = -1.0F;
            this.mCurrValue = -1.0F;
            setRelease();
            if (this.mTestListIndex >= -1 + this.mTestList.length) {
              break label1327;
            }
            this.mTestListIndex = (1 + this.mTestListIndex);
            if (this.mTestList[this.mTestListIndex].mID != this.TESTITEM_ID_GRIP2) {
              break;
            }
            this.mTableGrip2.setVisibility(0);
          }
          return;
          if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_WORKING)
          {
            this.mCurrValue = Integer.valueOf(this.mTestList[this.mTestListIndex].mValue[1]).intValue();
            if ((this.mPrevValue == 1.0F) && (this.mCurrValue == 0.0F))
            {
              this.mTextGrip1Step2.setVisibility(0);
              this.mTextGrip1Step2Result.setVisibility(0);
              this.mTextGrip1Step2Result.setText("Pass");
              this.mTextGrip1Step2Result.setTextColor(-16776961);
              this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_FINISH;
              this.mTestList[this.mTestListIndex].mIsPass = true;
            }
            this.mPrevValue = this.mCurrValue;
            continue;
            if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_RELEASE)
            {
              this.mTextGrip1Step1Result.setVisibility(0);
              this.mTextGrip1Step1Result.setText("Fail");
              this.mTextGrip1Step1Result.setTextColor(-65536);
              this.mTextGrip1Step2.setVisibility(0);
              this.mTextGrip1Step2Result.setText("Fail");
              this.mTextGrip1Step2Result.setTextColor(-65536);
              this.mTestList[this.mTestListIndex].mIsPass = false;
              this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_FINISH;
              continue;
              if (this.mTestList[this.mTestListIndex].mID == this.TESTITEM_ID_GRIP2)
              {
                String[] arrayOfString2 = this.mModuleSensor.getData(ModuleSensor.ID_INTENT__GRIP);
                if (arrayOfString2 != null)
                {
                  this.mTestList[this.mTestListIndex].mValue = arrayOfString2;
                  if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_RELEASE)
                  {
                    this.mCurrValue = Integer.valueOf(this.mTestList[this.mTestListIndex].mValue[2]).intValue();
                    if (this.mCurrValue == 1.0F)
                    {
                      setWorking();
                      this.mTextGrip2Step1Result.setVisibility(0);
                      this.mTextGrip2Step1Result.setText("Pass");
                      this.mTextGrip2Step1Result.setTextColor(-16776961);
                      this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_WORKING;
                    }
                    this.mPrevValue = this.mCurrValue;
                  }
                  else if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_WORKING)
                  {
                    this.mCurrValue = Integer.valueOf(this.mTestList[this.mTestListIndex].mValue[2]).intValue();
                    if ((this.mPrevValue == 1.0F) && (this.mCurrValue == 0.0F))
                    {
                      this.mTextGrip2Step2.setVisibility(0);
                      this.mTextGrip2Step2Result.setVisibility(0);
                      this.mTextGrip2Step2Result.setText("Pass");
                      this.mTextGrip2Step2Result.setTextColor(-16776961);
                      this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_FINISH;
                      this.mTestList[this.mTestListIndex].mIsPass = true;
                    }
                    this.mPrevValue = this.mCurrValue;
                  }
                }
                else if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_RELEASE)
                {
                  this.mTextGrip2Step1Result.setVisibility(0);
                  this.mTextGrip2Step1Result.setText("Fail");
                  this.mTextGrip2Step1Result.setTextColor(-65536);
                  this.mTextGrip2Step2.setVisibility(0);
                  this.mTextGrip2Step2Result.setText("Fail");
                  this.mTextGrip2Step2Result.setTextColor(-65536);
                  this.mTestList[this.mTestListIndex].mIsPass = false;
                  this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_FINISH;
                }
              }
              else if (this.mTestList[this.mTestListIndex].mID == this.TESTITEM_ID_GRIP3)
              {
                String[] arrayOfString1 = this.mModuleSensor.getData(ModuleSensor.ID_INTENT__GRIP);
                if (arrayOfString1 != null)
                {
                  this.mTestList[this.mTestListIndex].mValue = arrayOfString1;
                  if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_RELEASE)
                  {
                    this.mCurrValue = Integer.valueOf(this.mTestList[this.mTestListIndex].mValue[3]).intValue();
                    if (this.mCurrValue == 1.0F)
                    {
                      setWorking();
                      this.mTextGrip3Step1Result.setVisibility(0);
                      this.mTextGrip3Step1Result.setText("Pass");
                      this.mTextGrip3Step1Result.setTextColor(-16776961);
                      this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_WORKING;
                    }
                    this.mPrevValue = this.mCurrValue;
                  }
                  else if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_WORKING)
                  {
                    this.mCurrValue = Integer.valueOf(this.mTestList[this.mTestListIndex].mValue[3]).intValue();
                    if ((this.mPrevValue == 1.0F) && (this.mCurrValue == 0.0F))
                    {
                      this.mTextGrip3Step2.setVisibility(0);
                      this.mTextGrip3Step2Result.setVisibility(0);
                      this.mTextGrip3Step2Result.setText("Pass");
                      this.mTextGrip3Step2Result.setTextColor(-16776961);
                      this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_FINISH;
                      this.mTestList[this.mTestListIndex].mIsPass = true;
                    }
                    this.mPrevValue = this.mCurrValue;
                  }
                }
                else if (this.mTestList[this.mTestListIndex].mStatus == this.TESTITEM_STATUS_RELEASE)
                {
                  this.mTextGrip3Step1Result.setVisibility(0);
                  this.mTextGrip3Step1Result.setText("Fail");
                  this.mTextGrip3Step1Result.setTextColor(-65536);
                  this.mTextGrip3Step2.setVisibility(0);
                  this.mTextGrip3Step2Result.setText("Fail");
                  this.mTextGrip3Step2Result.setTextColor(-65536);
                  this.mTestList[this.mTestListIndex].mIsPass = false;
                  this.mTestList[this.mTestListIndex].mStatus = this.TESTITEM_STATUS_FINISH;
                }
              }
            }
          }
        }
      } while (this.mTestList[this.mTestListIndex].mID != this.TESTITEM_ID_GRIP3);
      this.mTableGrip3.setVisibility(0);
      return;
    } while ((this.mTestListIndex != -1 + this.mTestList.length) || (!this.FLAG_EXIT));
    this.mHandler.sendEmptyMessageDelayed(this.WHAT_EXIT, 1000L);
    this.FLAG_EXIT = false;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903062);
    this.mModuleSensor = ModuleSensor.instance(this);
    this.mModuleDevice = ModuleDevice.instance(this);
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
  }
  
  protected void onPause()
  {
    super.onPause();
    if (this.mFactoryPhone == null)
    {
      this.mFactoryPhone = new FactoryTestPhone(getApplicationContext());
      this.mFactoryPhone.bindSecPhoneService();
    }
    this.mFactoryPhone.requestGripSensorOn(false);
    ModuleSensor localModuleSensor = this.mModuleSensor;
    localModuleSensor.SensorOff_Intent(ModuleSensor.ID_INTENT__GRIP);
    this.mModuleDevice.stopVibrate();
  }
  
  protected void onResume()
  {
    super.onResume();
    initialize();
    if (this.mFactoryPhone == null)
    {
      this.mFactoryPhone = new FactoryTestPhone(getApplicationContext());
      this.mFactoryPhone.bindSecPhoneService();
    }
    this.mFactoryPhone.requestGripSensorOn(true);
  }
  
  private class TestItem
  {
    public int mID;
    public boolean mIsPass;
    public int mStatus;
    public String[] mValue;
    
    public TestItem(int paramInt)
    {
      this.mID = paramInt;
      this.mStatus = UIGrip.this.TESTITEM_STATUS_RELEASE;
      this.mValue = null;
      this.mIsPass = false;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIGrip
 * JD-Core Version:    0.7.1
 */