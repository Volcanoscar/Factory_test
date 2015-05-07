package com.sec.factory.app.ui;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.modules.ModulePower;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Properties;
import com.sec.factory.support.Support.Spec;
import com.sec.factory.support.Support.TestCase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class UIHardKey
  extends UIKeyBase
{
  private final String INDI_TEST_KEY2 = "2";
  private final String TSK_MANUFACTURE_CYPRESS = "CYPRESS";
  private final String TSK_MANUFACTURE_MELFAS = "MELFAS";
  private Handler mAfKeyHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      UIHardKey.access$002(UIHardKey.this, false);
    }
  };
  private boolean mBlockedAfKey = false;
  private boolean mIsPassed = false;
  private int mLongPressKeyCode;
  private boolean mModeLCiA = false;
  private int mOldVolumeIndex;
  private View.OnClickListener mResetButtonAction = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      Iterator localIterator = UIHardKey.this.mTestList.iterator();
      while (localIterator.hasNext())
      {
        UIKeyBase.KeyTestItem localKeyTestItem = (UIKeyBase.KeyTestItem)localIterator.next();
        if (localKeyTestItem.mEnabled)
        {
          localKeyTestItem.mIsPassed = false;
          if (localKeyTestItem.mTouchKeyItem != null)
          {
            localKeyTestItem.mTouchKeyItem.mTouchKeySensitivityText.setText("");
            localKeyTestItem.mTouchKeyItem.mTouchKeyTestResult.setText("");
          }
          localKeyTestItem.mHardKeyItem.mHardKeyText.setTextColor(-16777216);
        }
      }
    }
  };
  private int mSpecMax;
  private int mSpecMin;
  private int mThreshold;
  private String mTskManufacture = Support.Feature.getString("TSK_MANUFACTURE");
  private String mTspManufacture = null;
  
  public UIHardKey()
  {
    super("UIHardKey");
  }
  
  private void initialize()
  {
    UIKeyBase.TouchKeyItemSet localTouchKeyItemSet = makeTouchKeyItemSet();
    UIKeyBase.KeyTestItem[] arrayOfKeyTestItem1 = new UIKeyBase.KeyTestItem[33];
    arrayOfKeyTestItem1[0] = new UIKeyBase.KeyTestItem(this, 24, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296423)), localTouchKeyItemSet.get(24), Support.TestCase.getEnabled("KEY_TEST_VOLUME_UP"), Support.TestCase.isTouchkey("KEY_TEST_VOLUME_UP"));
    arrayOfKeyTestItem1[1] = new UIKeyBase.KeyTestItem(this, 25, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296424)), localTouchKeyItemSet.get(25), Support.TestCase.getEnabled("KEY_TEST_VOLUME_DOWN"), Support.TestCase.isTouchkey("KEY_TEST_VOLUME_DOWN"));
    arrayOfKeyTestItem1[2] = new UIKeyBase.KeyTestItem(this, 231, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296425)), localTouchKeyItemSet.get(231), Support.TestCase.getEnabled("KEY_TEST_END"), Support.TestCase.isTouchkey("KEY_TEST_END"));
    arrayOfKeyTestItem1[3] = new UIKeyBase.KeyTestItem(this, 26, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296422)), localTouchKeyItemSet.get(26), Support.TestCase.getEnabled("KEY_TEST_POWER"), Support.TestCase.isTouchkey("KEY_TEST_POWER"));
    arrayOfKeyTestItem1[4] = new UIKeyBase.KeyTestItem(this, 82, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296428)), localTouchKeyItemSet.get(82), Support.TestCase.getEnabled("KEY_TEST_MENU"), Support.TestCase.isTouchkey("KEY_TEST_MENU"));
    arrayOfKeyTestItem1[5] = new UIKeyBase.KeyTestItem(this, 3, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296429)), localTouchKeyItemSet.get(3), Support.TestCase.getEnabled("KEY_TEST_HOME"), Support.TestCase.isTouchkey("KEY_TEST_HOME"));
    arrayOfKeyTestItem1[6] = new UIKeyBase.KeyTestItem(this, 4, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296430)), localTouchKeyItemSet.get(4), Support.TestCase.getEnabled("KEY_TEST_BACK"), Support.TestCase.isTouchkey("KEY_TEST_BACK"));
    arrayOfKeyTestItem1[7] = new UIKeyBase.KeyTestItem(this, 232, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296431)), localTouchKeyItemSet.get(232), Support.TestCase.getEnabled("KEY_TEST_NETWOR_SEL"), Support.TestCase.isTouchkey("KEY_TEST_NETWOR_SEL"));
    arrayOfKeyTestItem1[8] = new UIKeyBase.KeyTestItem(this, 5, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296432)), localTouchKeyItemSet.get(5), Support.TestCase.getEnabled("KEY_TEST_CALL"), Support.TestCase.isTouchkey("KEY_TEST_CALL"));
    arrayOfKeyTestItem1[9] = new UIKeyBase.KeyTestItem(this, 67, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296433)), localTouchKeyItemSet.get(67), Support.TestCase.getEnabled("KEY_TEST_DEL"), Support.TestCase.isTouchkey("KEY_TEST_DEL"));
    arrayOfKeyTestItem1[10] = new UIKeyBase.KeyTestItem(this, 7, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296434)), localTouchKeyItemSet.get(7), Support.TestCase.getEnabled("KEY_TEST_0"), Support.TestCase.isTouchkey("KEY_TEST_0"));
    arrayOfKeyTestItem1[11] = new UIKeyBase.KeyTestItem(this, 8, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296435)), localTouchKeyItemSet.get(8), Support.TestCase.getEnabled("KEY_TEST_1"), Support.TestCase.isTouchkey("KEY_TEST_1"));
    arrayOfKeyTestItem1[12] = new UIKeyBase.KeyTestItem(this, 9, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296436)), localTouchKeyItemSet.get(9), Support.TestCase.getEnabled("KEY_TEST_2"), Support.TestCase.isTouchkey("KEY_TEST_2"));
    arrayOfKeyTestItem1[13] = new UIKeyBase.KeyTestItem(this, 10, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296437)), localTouchKeyItemSet.get(10), Support.TestCase.getEnabled("KEY_TEST_3"), Support.TestCase.isTouchkey("KEY_TEST_3"));
    arrayOfKeyTestItem1[14] = new UIKeyBase.KeyTestItem(this, 11, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296438)), localTouchKeyItemSet.get(11), Support.TestCase.getEnabled("KEY_TEST_4"), Support.TestCase.isTouchkey("KEY_TEST_4"));
    arrayOfKeyTestItem1[15] = new UIKeyBase.KeyTestItem(this, 12, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296439)), localTouchKeyItemSet.get(12), Support.TestCase.getEnabled("KEY_TEST_5"), Support.TestCase.isTouchkey("KEY_TEST_5"));
    arrayOfKeyTestItem1[16] = new UIKeyBase.KeyTestItem(this, 13, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296440)), localTouchKeyItemSet.get(13), Support.TestCase.getEnabled("KEY_TEST_6"), Support.TestCase.isTouchkey("KEY_TEST_6"));
    arrayOfKeyTestItem1[17] = new UIKeyBase.KeyTestItem(this, 14, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296441)), localTouchKeyItemSet.get(14), Support.TestCase.getEnabled("KEY_TEST_7"), Support.TestCase.isTouchkey("KEY_TEST_7"));
    arrayOfKeyTestItem1[18] = new UIKeyBase.KeyTestItem(this, 15, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296442)), localTouchKeyItemSet.get(15), Support.TestCase.getEnabled("KEY_TEST_8"), Support.TestCase.isTouchkey("KEY_TEST_8"));
    arrayOfKeyTestItem1[19] = new UIKeyBase.KeyTestItem(this, 16, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296443)), localTouchKeyItemSet.get(16), Support.TestCase.getEnabled("KEY_TEST_9"), Support.TestCase.isTouchkey("KEY_TEST_9"));
    arrayOfKeyTestItem1[20] = new UIKeyBase.KeyTestItem(this, 17, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296444)), localTouchKeyItemSet.get(17), Support.TestCase.getEnabled("KEY_TEST_STAR"), Support.TestCase.isTouchkey("KEY_TEST_STAR"));
    arrayOfKeyTestItem1[21] = new UIKeyBase.KeyTestItem(this, 18, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296445)), localTouchKeyItemSet.get(18), Support.TestCase.getEnabled("KEY_TEST_POUND"), Support.TestCase.isTouchkey("KEY_TEST_POUND"));
    arrayOfKeyTestItem1[22] = new UIKeyBase.KeyTestItem(this, 19, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296446)), localTouchKeyItemSet.get(19), Support.TestCase.getEnabled("KEY_TEST_DPAD_UP"), Support.TestCase.isTouchkey("KEY_TEST_DPAD_UP"));
    arrayOfKeyTestItem1[23] = new UIKeyBase.KeyTestItem(this, 20, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296447)), localTouchKeyItemSet.get(20), Support.TestCase.getEnabled("KEY_TEST_DPAD_DOWN"), Support.TestCase.isTouchkey("KEY_TEST_DPAD_DOWN"));
    arrayOfKeyTestItem1[24] = new UIKeyBase.KeyTestItem(this, 21, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296448)), localTouchKeyItemSet.get(21), Support.TestCase.getEnabled("KEY_TEST_DPAD_LEFT"), Support.TestCase.isTouchkey("KEY_TEST_DPAD_LEFT"));
    arrayOfKeyTestItem1[25] = new UIKeyBase.KeyTestItem(this, 22, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296449)), localTouchKeyItemSet.get(22), Support.TestCase.getEnabled("KEY_TEST_DPAD_RIGHT"), Support.TestCase.isTouchkey("KEY_TEST_DPAD_RIGHT"));
    arrayOfKeyTestItem1[26] = new UIKeyBase.KeyTestItem(this, 23, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296450)), localTouchKeyItemSet.get(23), Support.TestCase.getEnabled("KEY_TEST_DPAD_CENTER"), Support.TestCase.isTouchkey("KEY_TEST_DPAD_CENTER"));
    arrayOfKeyTestItem1[27] = new UIKeyBase.KeyTestItem(this, 84, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296451)), localTouchKeyItemSet.get(84), Support.TestCase.getEnabled("KEY_TEST_SEARCH"), Support.TestCase.isTouchkey("KEY_TEST_SEARCH"));
    arrayOfKeyTestItem1[28] = new UIKeyBase.KeyTestItem(this, 80, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296426)), localTouchKeyItemSet.get(80), Support.TestCase.getEnabled("KEY_TEST_FOCUS"), Support.TestCase.isTouchkey("KEY_TEST_FOCUS"));
    arrayOfKeyTestItem1[29] = new UIKeyBase.KeyTestItem(this, 27, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296427)), localTouchKeyItemSet.get(27), Support.TestCase.getEnabled("KEY_TEST_CAMERA"), Support.TestCase.isTouchkey("KEY_TEST_CAMERA"));
    arrayOfKeyTestItem1[30] = new UIKeyBase.KeyTestItem(this, 237, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296452)), localTouchKeyItemSet.get(237), Support.TestCase.getEnabled("KEY_TEST_F1"), Support.TestCase.isTouchkey("KEY_TEST_F1"));
    arrayOfKeyTestItem1[31] = new UIKeyBase.KeyTestItem(this, 233, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296454)), localTouchKeyItemSet.get(233), Support.TestCase.getEnabled("KEY_TEST_3G"), Support.TestCase.isTouchkey("KEY_TEST_3G"));
    arrayOfKeyTestItem1[32] = new UIKeyBase.KeyTestItem(this, 187, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296453)), localTouchKeyItemSet.get(187), Support.TestCase.getEnabled("KEY_TEST_APP_SWITCH"), Support.TestCase.isTouchkey("KEY_TEST_APP_SWITCH"));
    UIKeyBase.KeyTestItem[] arrayOfKeyTestItem2 = new UIKeyBase.KeyTestItem[3];
    arrayOfKeyTestItem2[0] = new UIKeyBase.KeyTestItem(this, 82, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296428)), localTouchKeyItemSet.get(82), Support.TestCase.getEnabled(selectTestCase("KEY_TEST_MENU")), Support.TestCase.isTouchkey(selectTestCase("KEY_TEST_MENU")));
    arrayOfKeyTestItem2[1] = new UIKeyBase.KeyTestItem(this, 3, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296429)), localTouchKeyItemSet.get(3), Support.TestCase.getEnabled(selectTestCase("KEY_TEST_HOME")), Support.TestCase.isTouchkey(selectTestCase("KEY_TEST_HOME")));
    arrayOfKeyTestItem2[2] = new UIKeyBase.KeyTestItem(this, 4, new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296430)), localTouchKeyItemSet.get(4), Support.TestCase.getEnabled(selectTestCase("KEY_TEST_BACK")), Support.TestCase.isTouchkey(selectTestCase("KEY_TEST_BACK")));
    if (45 == this.TEST_ID) {}
    for (UIKeyBase.KeyTestItem[] arrayOfKeyTestItem3 = arrayOfKeyTestItem2;; arrayOfKeyTestItem3 = arrayOfKeyTestItem1)
    {
      int i = arrayOfKeyTestItem3.length;
      int j = 0;
      int k = 0;
      while (j < i)
      {
        UIKeyBase.KeyTestItem localKeyTestItem = arrayOfKeyTestItem3[j];
        if (localKeyTestItem.mEnabled) {
          this.mTestList.add(localKeyTestItem);
        }
        if ((localKeyTestItem.mIsTouchKey) && (localKeyTestItem.mEnabled)) {
          k = 1;
        }
        j++;
      }
      String str2;
      ViewGroup localViewGroup;
      String str1;
      if (k != 0)
      {
        if ("MELFAS".equals(this.mTskManufacture))
        {
          str2 = "THD:4.8 ";
          this.mThreshold = 0;
          this.mSpecMax = Support.Spec.getInt("TOUCH_KEY_SPEC_MAX");
          this.mSpecMin = Support.Spec.getInt("TOUCH_KEY_SPEC_MIN");
          String str3 = str2 + "MAX:" + this.mSpecMax + "    MIN:" + this.mSpecMin;
          ((TextView)findViewById(2131296458)).setText(str3);
        }
      }
      else
      {
        Collections.sort(this.mTestList, this.mComparator);
        localViewGroup = (ViewGroup)findViewById(2131296455);
        str1 = Support.TestCase.getString(selectTestCase("KEY_TEST_VIEW_TABLE"));
        if (!str1.equals("auto")) {
          break label2152;
        }
      }
      label2152:
      for (int m = getWindowManager().getDefaultDisplay().getHeight() / 2;; m = Integer.parseInt(str1))
      {
        localViewGroup.setLayoutParams(new AbsoluteLayout.LayoutParams(-1, -2, 0, m));
        ((Button)findViewById(2131296456)).setOnClickListener(this.mExitButtonAction);
        ((Button)findViewById(2131296457)).setOnClickListener(this.mResetButtonAction);
        setLayoutPosition(arrayOfKeyTestItem3);
        if (45 != this.TEST_ID) {
          break label2162;
        }
        int n = arrayOfKeyTestItem1.length;
        for (int i1 = 0; i1 < n; i1++) {
          arrayOfKeyTestItem1[i1].remobeHardKeyItem();
        }
        if (Support.Feature.getBoolean("MULTI_TSK_THD"))
        {
          str2 = "THD:" + ModuleDevice.instance(this).readTSKThresholdMulti() + "    ";
          break;
        }
        this.mThreshold = ModuleDevice.instance(this).readTSKThreshold();
        str2 = "THD:" + this.mThreshold + "    ";
        break;
      }
      label2162:
      return;
    }
  }
  
  private UIKeyBase.TouchKeyItemSet makeTouchKeyItemSet()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("MENU", Integer.valueOf(82));
    localHashMap.put("HOME", Integer.valueOf(3));
    localHashMap.put("BACK", Integer.valueOf(4));
    localHashMap.put("SEARCH", Integer.valueOf(84));
    localHashMap.put("VOLUME_UP", Integer.valueOf(24));
    localHashMap.put("VOLUME_DOWN", Integer.valueOf(25));
    localHashMap.put("END", Integer.valueOf(231));
    localHashMap.put("FOCUS", Integer.valueOf(80));
    localHashMap.put("CAMERA", Integer.valueOf(27));
    localHashMap.put("POWER", Integer.valueOf(26));
    localHashMap.put("USER", Integer.valueOf(237));
    localHashMap.put("SWITCH", Integer.valueOf(187));
    LinkedList localLinkedList = new LinkedList();
    localLinkedList.offer(new UIKeyBase.TouchKeyItem(this, (TextView)findViewById(2131296459), (TextView)findViewById(2131296463), (TextView)findViewById(2131296468)));
    localLinkedList.offer(new UIKeyBase.TouchKeyItem(this, (TextView)findViewById(2131296460), (TextView)findViewById(2131296464), (TextView)findViewById(2131296469)));
    localLinkedList.offer(new UIKeyBase.TouchKeyItem(this, (TextView)findViewById(2131296461), (TextView)findViewById(2131296465), (TextView)findViewById(2131296470)));
    localLinkedList.offer(new UIKeyBase.TouchKeyItem(this, (TextView)findViewById(2131296462), (TextView)findViewById(2131296466), (TextView)findViewById(2131296471)));
    String[] arrayOfString = Support.TestCase.getString(selectTestCase("KEY_TEST_TOUCH_KEY_ODER")).split(",");
    UIKeyBase.TouchKeyItemSet localTouchKeyItemSet = new UIKeyBase.TouchKeyItemSet(this);
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      String str = arrayOfString[j];
      localTouchKeyItemSet.put(((Integer)localHashMap.get(str)).intValue(), (UIKeyBase.TouchKeyItem)localLinkedList.poll());
      if (localTouchKeyItemSet.get(((Integer)localHashMap.get(str)).intValue()) != null) {
        localTouchKeyItemSet.get(((Integer)localHashMap.get(str)).intValue()).mTouchKeyText.setText(str);
      }
    }
    return localTouchKeyItemSet;
  }
  
  private void setLayoutPosition(UIKeyBase.KeyTestItem[] paramArrayOfKeyTestItem)
  {
    int i = paramArrayOfKeyTestItem.length;
    int j = 0;
    if (j < i)
    {
      UIKeyBase.KeyTestItem localKeyTestItem = paramArrayOfKeyTestItem[j];
      TextView localTextView = localKeyTestItem.getHardKeyTextView();
      String str;
      if (localTextView != null)
      {
        str = null;
        switch (localKeyTestItem.getKeyCode())
        {
        default: 
          FtUtil.log_e(this.CLASS_NAME, "setLayoutPosition", "Undefined case " + localKeyTestItem.getKeyCode());
        }
      }
      for (;;)
      {
        if (str != null)
        {
          Point localPoint = Support.TestCase.getViewPoint(str);
          localTextView.setTextSize(Support.TestCase.getKeyTextSize(str));
          localTextView.setLayoutParams(new AbsoluteLayout.LayoutParams(-2, -2, localPoint.x, localPoint.y));
          FtUtil.log_e(this.CLASS_NAME, "init", "id=" + str + ", point=" + localPoint.x + ", " + localPoint.y);
        }
        j++;
        break;
        str = "KEY_TEST_POWER";
        continue;
        str = "KEY_TEST_VOLUME_UP";
        continue;
        str = "KEY_TEST_VOLUME_DOWN";
        continue;
        str = "KEY_TEST_END";
        continue;
        str = "KEY_TEST_FOCUS";
        continue;
        str = "KEY_TEST_CAMERA";
        continue;
        str = selectTestCase("KEY_TEST_MENU");
        continue;
        str = selectTestCase("KEY_TEST_HOME");
        continue;
        str = selectTestCase("KEY_TEST_BACK");
        continue;
        str = "KEY_TEST_NETWOR_SEL";
        continue;
        str = "KEY_TEST_CALL";
        continue;
        str = "KEY_TEST_DEL";
        continue;
        str = "KEY_TEST_0";
        continue;
        str = "KEY_TEST_1";
        continue;
        str = "KEY_TEST_2";
        continue;
        str = "KEY_TEST_3";
        continue;
        str = "KEY_TEST_4";
        continue;
        str = "KEY_TEST_5";
        continue;
        str = "KEY_TEST_6";
        continue;
        str = "KEY_TEST_7";
        continue;
        str = "KEY_TEST_8";
        continue;
        str = "KEY_TEST_9";
        continue;
        str = "KEY_TEST_STAR";
        continue;
        str = "KEY_TEST_POUND";
        continue;
        str = "KEY_TEST_DPAD_UP";
        continue;
        str = "KEY_TEST_DPAD_DOWN";
        continue;
        str = "KEY_TEST_DPAD_LEFT";
        continue;
        str = "KEY_TEST_DPAD_RIGHT";
        continue;
        str = "KEY_TEST_DPAD_CENTER";
        continue;
        str = "KEY_TEST_SEARCH";
        continue;
        str = "KEY_TEST_F1";
        continue;
        str = "KEY_TEST_3G";
        continue;
        str = "KEY_TEST_APP_SWITCH";
      }
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setTestId(getIntent().getIntExtra("requestCode", 6));
    FtUtil.log_v(this.CLASS_NAME, "onCreate", "TEST_ID = " + this.TEST_ID);
    setContentView(2130903066);
    initialize();
    this.mModeLCiA = "LCiA".equalsIgnoreCase(Support.Properties.get("PGM_STAGE"));
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    int i = -16776961;
    super.setLongPress(false, paramKeyEvent);
    FtUtil.log_i(this.CLASS_NAME, "onKeyDown", "keyCode=" + paramInt);
    if (((!isLongPress()) && (!this.mIsPassed)) || ((!this.mIsPassed) && (this.mLongPressKeyCode != paramInt)))
    {
      this.mLongPressKeyCode = paramInt;
      if (paramInt == 80)
      {
        FtUtil.log_d(this.CLASS_NAME, "onKeyDown", "Af block=" + this.mBlockedAfKey);
        if (this.mBlockedAfKey == true) {
          return true;
        }
        this.mBlockedAfKey = true;
        this.mAfKeyHandler.sendMessageDelayed(this.mAfKeyHandler.obtainMessage(), 2000L);
      }
      int j = Collections.binarySearch(this.mTestList, new UIKeyBase.KeyTestItem(this, paramInt), this.mComparator);
      if (j >= 0)
      {
        UIKeyBase.KeyTestItem localKeyTestItem = (UIKeyBase.KeyTestItem)this.mTestList.get(j);
        if (!localKeyTestItem.mIsTouchKey) {
          if (!localKeyTestItem.mIsPassed)
          {
            bool2 = true;
            localKeyTestItem.mIsPassed = bool2;
            FtUtil.log_d(this.CLASS_NAME, "onKeyDown", "Hard key press keyCode=" + localKeyTestItem.mKeyCode + ", Passed=" + localKeyTestItem.mIsPassed);
            localTextView5 = localKeyTestItem.mHardKeyItem.mHardKeyText;
            if (!localKeyTestItem.mIsPassed) {
              break label351;
            }
            n = i;
            localTextView5.setTextColor(n);
          }
        }
        label351:
        while ((!localKeyTestItem.mIsTouchKey) || (localKeyTestItem.mIsPassed)) {
          for (;;)
          {
            TextView localTextView5;
            if (!this.mModeLCiA) {
              ModuleAudio.instance(this).playMedia(2131034115, false);
            }
            Iterator localIterator = this.mTestList.iterator();
            do
            {
              if (!localIterator.hasNext()) {
                break;
              }
            } while (((UIKeyBase.KeyTestItem)localIterator.next()).mIsPassed);
            return true;
            boolean bool2 = false;
            continue;
            int n = -65536;
          }
        }
        Integer localInteger = ModuleDevice.instance(this).readTSKSensitivity(localKeyTestItem.mKeyCode);
        boolean bool1;
        label421:
        int k;
        label449:
        String str;
        label479:
        int m;
        if (localInteger != null) {
          if ((localInteger.intValue() >= this.mSpecMin) && (localInteger.intValue() <= this.mSpecMax))
          {
            bool1 = true;
            localKeyTestItem.mIsPassed = bool1;
            TextView localTextView1 = localKeyTestItem.mTouchKeyItem.mTouchKeySensitivityText;
            if (!localKeyTestItem.mIsPassed) {
              break label622;
            }
            k = i;
            localTextView1.setTextColor(k);
            TextView localTextView2 = localKeyTestItem.mTouchKeyItem.mTouchKeyTestResult;
            if (!localKeyTestItem.mIsPassed) {
              break label630;
            }
            str = "PASS";
            localTextView2.setText(str);
            TextView localTextView3 = localKeyTestItem.mTouchKeyItem.mTouchKeyTestResult;
            if (!localKeyTestItem.mIsPassed) {
              break label638;
            }
            m = i;
            label507:
            localTextView3.setTextColor(m);
            TextView localTextView4 = localKeyTestItem.mHardKeyItem.mHardKeyText;
            if (!localKeyTestItem.mIsPassed) {
              break label646;
            }
            label532:
            localTextView4.setTextColor(i);
            FtUtil.log_d(this.CLASS_NAME, "onKeyDown", "Touch key press keyCode=" + localKeyTestItem.mKeyCode + ", Passed=" + localKeyTestItem.mIsPassed + ", Sensitivity=" + localInteger);
          }
        }
        for (;;)
        {
          localKeyTestItem.mTouchKeyItem.mTouchKeySensitivityText.setText(String.valueOf(localInteger));
          break;
          bool1 = false;
          break label421;
          label622:
          k = -65536;
          break label449;
          label630:
          str = "FAIL";
          break label479;
          label638:
          m = -65536;
          break label507;
          label646:
          i = -65536;
          break label532;
          FtUtil.log_i(this.CLASS_NAME, "onKeyDown", "sensitivity value is null");
        }
        this.mIsPassed = true;
        FtUtil.log_i(this.CLASS_NAME, "onKeyDown", "Pass the KeyTest");
        setResult(-1);
        return true;
      }
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    super.setLongPress(true, paramKeyEvent);
    FtUtil.log_d(this.CLASS_NAME, "onKeyUp", "passed=" + this.mIsPassed + ", keyCode=" + paramInt);
    this.mLongPressKeyCode = 0;
    if (this.mIsPassed == true)
    {
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          UIHardKey.this.finish();
        }
      }, 500L);
      return true;
    }
    return super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  public void onPause()
  {
    super.onPause();
    ModuleAudio.instance(this).setStreamMusicVolumeIndex(this.mOldVolumeIndex);
    ModulePower.instance(this).doWakeLock(false);
    this.mTspManufacture = Support.Feature.getString("TSP_MANUFACTURE");
    if ("CYPRESS".equals(this.mTspManufacture)) {
      ModuleDevice.instance(this).turnOffTSKSensitivity();
    }
  }
  
  public void onResume()
  {
    super.onResume();
    this.mOldVolumeIndex = ModuleAudio.instance(this).getStreamMusicVolumeIndex();
    FtUtil.log_i(this.CLASS_NAME, "initialize", "mOldVolume = " + this.mOldVolumeIndex);
    ModuleAudio.instance(this).setStreamMusicVolumeMaxIndex();
    ModuleDevice.instance(this).turnOnTSKSensitivity();
    ModulePower.instance(this).doWakeLock(true);
  }
  
  public String selectTestCase(String paramString)
  {
    if (45 == this.TEST_ID) {
      paramString = paramString + "2";
    }
    return paramString;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIHardKey
 * JD-Core Version:    0.7.1
 */