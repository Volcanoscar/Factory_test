package com.sec.factory.app.ui;

import android.app.Instrumentation;
import android.os.Bundle;
import android.provider.Settings.System;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Properties;
import com.sec.factory.support.Support.TestCase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UIQwertyKey
  extends UIKeyBase
{
  private final int COL_MAX = 15;
  private final float DEFAULT_BUTTON_SIZE;
  private final int DEFAULT_COUNT_OF_SHIFT = 0;
  private final float DEFAULT_TEXT_SIZE;
  private final float DEFAULT_TEXT_VIEW_HEIGHT;
  private final int KEYCODE_DUMMY;
  private final int KEYCODE_ENTER_2;
  private final int KEYCODE_SPACE_2;
  private final int KEYCODE_SPACE_3;
  private final int KEYCODE_SPACE_4;
  private final int RESET_TO_DEFAULT_COUNT_SHIFT = 2;
  private final String ROW_END = "END";
  private final int ROW_MAX = 11;
  private final String SEPARATOR = " ";
  private int ShiftStatus;
  private String[] buffer = new String['¥'];
  private int dummy = 1000;
  private String mDisplay_String;
  private int mELLightTimePreQwertKeyTest;
  private boolean mELLightTimePreQwertKeyTestReadPass;
  private int mIndex_Enter;
  private int mIndex_Space;
  private int mKeyCount_Enter;
  private int mKeyCount_Space;
  private boolean mModeLCiA;
  private int mOldVolume;
  protected View.OnClickListener mResetButtonAction;
  private float mTextSize;
  
  public UIQwertyKey()
  {
    super("UIQwertyKey");
    int i = this.dummy;
    this.dummy = (i + 1);
    this.KEYCODE_DUMMY = i;
    int j = this.dummy;
    this.dummy = (j + 1);
    this.KEYCODE_ENTER_2 = j;
    int k = this.dummy;
    this.dummy = (k + 1);
    this.KEYCODE_SPACE_2 = k;
    int m = this.dummy;
    this.dummy = (m + 1);
    this.KEYCODE_SPACE_3 = m;
    int n = this.dummy;
    this.dummy = (n + 1);
    this.KEYCODE_SPACE_4 = n;
    this.DEFAULT_TEXT_SIZE = Support.TestCase.getSize("QWERTY_KEY_TEXT_SIZE");
    this.DEFAULT_TEXT_VIEW_HEIGHT = Support.TestCase.getSize("QWERTY_KEY_TEXT_VIEW_HEIGHT");
    this.DEFAULT_BUTTON_SIZE = Support.TestCase.getSize("QWERTY_EXIT_BUTTON_SIZE");
    this.mDisplay_String = null;
    this.mTextSize = this.DEFAULT_TEXT_SIZE;
    this.mKeyCount_Enter = 0;
    this.mKeyCount_Space = 0;
    this.mIndex_Enter = 0;
    this.mIndex_Space = 0;
    this.mModeLCiA = false;
    this.mELLightTimePreQwertKeyTest = 0;
    this.mELLightTimePreQwertKeyTestReadPass = false;
    this.ShiftStatus = 0;
    this.mResetButtonAction = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Iterator localIterator = UIQwertyKey.this.mTestList.iterator();
        while (localIterator.hasNext())
        {
          UIKeyBase.KeyTestItem localKeyTestItem = (UIKeyBase.KeyTestItem)localIterator.next();
          localKeyTestItem.mIsPassed = false;
          UIQwertyKey.access$002(UIQwertyKey.this, 0);
          localKeyTestItem.mHardKeyItem.mHardKeyText.setTextColor(-16777216);
        }
      }
    };
  }
  
  private void PressShiftKey()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        new Instrumentation().sendKeyDownUpSync(59);
      }
    }).start();
  }
  
  private int converterKeyCode(String paramString)
  {
    if (paramString.equals("1")) {
      return 8;
    }
    if (paramString.equals("2")) {
      return 9;
    }
    if (paramString.equals("3")) {
      return 10;
    }
    if (paramString.equals("4")) {
      return 11;
    }
    if (paramString.equals("5")) {
      return 12;
    }
    if (paramString.equals("6")) {
      return 13;
    }
    if (paramString.equals("7")) {
      return 14;
    }
    if (paramString.equals("8")) {
      return 15;
    }
    if (paramString.equals("9")) {
      return 16;
    }
    if (paramString.equals("0")) {
      return 7;
    }
    if (paramString.equals("A")) {
      return 29;
    }
    if (paramString.equals("B")) {
      return 30;
    }
    if (paramString.equals("C")) {
      return 31;
    }
    if (paramString.equals("D")) {
      return 32;
    }
    if (paramString.equals("E")) {
      return 33;
    }
    if (paramString.equals("F")) {
      return 34;
    }
    if (paramString.equals("G")) {
      return 35;
    }
    if (paramString.equals("H")) {
      return 36;
    }
    if (paramString.equals("I")) {
      return 37;
    }
    if (paramString.equals("J")) {
      return 38;
    }
    if (paramString.equals("K")) {
      return 39;
    }
    if (paramString.equals("L")) {
      return 40;
    }
    if (paramString.equals("M")) {
      return 41;
    }
    if (paramString.equals("N")) {
      return 42;
    }
    if (paramString.equals("O")) {
      return 43;
    }
    if (paramString.equals("P")) {
      return 44;
    }
    if (paramString.equals("Q")) {
      return 45;
    }
    if (paramString.equals("R")) {
      return 46;
    }
    if (paramString.equals("S")) {
      return 47;
    }
    if (paramString.equals("T")) {
      return 48;
    }
    if (paramString.equals("U")) {
      return 49;
    }
    if (paramString.equals("V")) {
      return 50;
    }
    if (paramString.equals("W")) {
      return 51;
    }
    if (paramString.equals("X")) {
      return 52;
    }
    if (paramString.equals("Y")) {
      return 53;
    }
    if (paramString.equals("Z")) {
      return 54;
    }
    if (paramString.equals("ALT-L")) {
      return 57;
    }
    if (paramString.equals("BACK")) {
      return 4;
    }
    if (paramString.equals("COMMA")) {
      return 55;
    }
    if (paramString.equals("DELETE")) {
      return 67;
    }
    if (paramString.equals("CENTER")) {
      return 23;
    }
    if (paramString.equals("DOWN")) {
      return 20;
    }
    if (paramString.equals("LEFT")) {
      return 21;
    }
    if (paramString.equals("RIGHT")) {
      return 22;
    }
    if (paramString.equals("UP")) {
      return 19;
    }
    if (paramString.equals("EMAIL")) {
      return 220;
    }
    if (paramString.equals("ENTER1")) {
      return 66;
    }
    if (paramString.equals("ENTER2")) {
      return this.KEYCODE_ENTER_2;
    }
    if (paramString.equals("ENVELOPE")) {
      return 65;
    }
    if (paramString.equals("EXPLORER")) {
      return 64;
    }
    if (paramString.equals("HOME")) {
      return 3;
    }
    if (paramString.equals("MENU")) {
      return 82;
    }
    if (paramString.equals("PERIOD")) {
      return 56;
    }
    if (paramString.equals("QUESTION")) {
      return 236;
    }
    if (paramString.equals("SEARCH")) {
      return 84;
    }
    if (paramString.equals("SHIFT-L")) {
      return 59;
    }
    if (paramString.equals("SHIFT-R")) {
      return 60;
    }
    if (paramString.equals("SPACE1")) {
      return 62;
    }
    if (paramString.equals("SPACE2")) {
      return this.KEYCODE_SPACE_2;
    }
    if (paramString.equals("SPACE3")) {
      return this.KEYCODE_SPACE_3;
    }
    if (paramString.equals("SPACE4")) {
      return this.KEYCODE_SPACE_4;
    }
    if (paramString.equals("TEXT")) {
      return 235;
    }
    if (paramString.equals("VOICESEARCH")) {
      return 229;
    }
    if (paramString.equals("DUMMY")) {
      return this.KEYCODE_DUMMY;
    }
    if (paramString.equals("CHATON")) {
      return 303;
    }
    if (paramString.equals("CUST")) {
      return 301;
    }
    if (paramString.equals("SYM")) {
      return 63;
    }
    FtUtil.log_e(this.CLASS_NAME, "converterKeyCode", "Unknown => qwertyKey : [" + paramString + "]");
    return -1;
  }
  
  private UIKeyBase.HardKeyItem getObjectHardKeyItem(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 1)
    {
      if (paramInt2 == 1) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296558));
      }
      if (paramInt2 == 2) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296559));
      }
      if (paramInt2 == 3) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296560));
      }
      if (paramInt2 == 4) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296561));
      }
      if (paramInt2 == 5) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296562));
      }
      if (paramInt2 == 6) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296563));
      }
      if (paramInt2 == 7) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296564));
      }
      if (paramInt2 == 8) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296565));
      }
      if (paramInt2 == 9) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296566));
      }
      if (paramInt2 == 10) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296567));
      }
      if (paramInt2 == 11) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296568));
      }
      if (paramInt2 == 12) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296569));
      }
      if (paramInt2 == 13) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296570));
      }
      if (paramInt2 == 14) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296571));
      }
      if (paramInt2 == 15) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296572));
      }
      FtUtil.log_e(this.CLASS_NAME, "getObjectHardKeyItem", "Unknown - col : " + paramInt2);
      return null;
    }
    if (paramInt1 == 2)
    {
      if (paramInt2 == 1) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296574));
      }
      if (paramInt2 == 2) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296575));
      }
      if (paramInt2 == 3) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296576));
      }
      if (paramInt2 == 4) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296577));
      }
      if (paramInt2 == 5) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296578));
      }
      if (paramInt2 == 6) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296579));
      }
      if (paramInt2 == 7) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296580));
      }
      if (paramInt2 == 8) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296581));
      }
      if (paramInt2 == 9) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296582));
      }
      if (paramInt2 == 10) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296583));
      }
      if (paramInt2 == 11) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296584));
      }
      if (paramInt2 == 12) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296585));
      }
      if (paramInt2 == 13) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296586));
      }
      if (paramInt2 == 14) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296587));
      }
      if (paramInt2 == 15) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296588));
      }
      FtUtil.log_e(this.CLASS_NAME, "getObjectHardKeyItem", "Unknown - col : " + paramInt2);
      return null;
    }
    if (paramInt1 == 3)
    {
      if (paramInt2 == 1) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296590));
      }
      if (paramInt2 == 2) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296591));
      }
      if (paramInt2 == 3) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296592));
      }
      if (paramInt2 == 4) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296593));
      }
      if (paramInt2 == 5) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296594));
      }
      if (paramInt2 == 6) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296595));
      }
      if (paramInt2 == 7) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296596));
      }
      if (paramInt2 == 8) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296597));
      }
      if (paramInt2 == 9) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296598));
      }
      if (paramInt2 == 10) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296599));
      }
      if (paramInt2 == 11) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296600));
      }
      if (paramInt2 == 12) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296601));
      }
      if (paramInt2 == 13) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296602));
      }
      if (paramInt2 == 14) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296603));
      }
      if (paramInt2 == 15) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296604));
      }
      FtUtil.log_e(this.CLASS_NAME, "getObjectHardKeyItem", "Unknown - col : " + paramInt2);
      return null;
    }
    if (paramInt1 == 4)
    {
      if (paramInt2 == 1) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296606));
      }
      if (paramInt2 == 2) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296607));
      }
      if (paramInt2 == 3) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296608));
      }
      if (paramInt2 == 4) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296609));
      }
      if (paramInt2 == 5) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296610));
      }
      if (paramInt2 == 6) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296611));
      }
      if (paramInt2 == 7) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296612));
      }
      if (paramInt2 == 8) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296613));
      }
      if (paramInt2 == 9) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296614));
      }
      if (paramInt2 == 10) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296615));
      }
      if (paramInt2 == 11) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296616));
      }
      if (paramInt2 == 12) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296617));
      }
      if (paramInt2 == 13) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296618));
      }
      if (paramInt2 == 14) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296619));
      }
      if (paramInt2 == 15) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296620));
      }
      FtUtil.log_e(this.CLASS_NAME, "getObjectHardKeyItem", "Unknown - col : " + paramInt2);
      return null;
    }
    if (paramInt1 == 5)
    {
      if (paramInt2 == 1) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296622));
      }
      if (paramInt2 == 2) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296623));
      }
      if (paramInt2 == 3) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296624));
      }
      if (paramInt2 == 4) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296625));
      }
      if (paramInt2 == 5) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296626));
      }
      if (paramInt2 == 6) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296627));
      }
      if (paramInt2 == 7) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296628));
      }
      if (paramInt2 == 8) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296629));
      }
      if (paramInt2 == 9) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296630));
      }
      if (paramInt2 == 10) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296631));
      }
      if (paramInt2 == 11) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296632));
      }
      if (paramInt2 == 12) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296633));
      }
      if (paramInt2 == 13) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296634));
      }
      if (paramInt2 == 14) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296635));
      }
      if (paramInt2 == 15) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296636));
      }
      FtUtil.log_e(this.CLASS_NAME, "getObjectHardKeyItem", "Unknown - col : " + paramInt2);
      return null;
    }
    if (paramInt1 == 6)
    {
      if (paramInt2 == 1) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296638));
      }
      if (paramInt2 == 2) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296639));
      }
      if (paramInt2 == 3) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296640));
      }
      if (paramInt2 == 4) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296641));
      }
      if (paramInt2 == 5) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296642));
      }
      if (paramInt2 == 6) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296643));
      }
      if (paramInt2 == 7) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296644));
      }
      if (paramInt2 == 8) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296645));
      }
      if (paramInt2 == 9) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296646));
      }
      if (paramInt2 == 10) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296647));
      }
      if (paramInt2 == 11) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296648));
      }
      if (paramInt2 == 12) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296649));
      }
      if (paramInt2 == 13) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296650));
      }
      if (paramInt2 == 14) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296651));
      }
      if (paramInt2 == 15) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296652));
      }
      FtUtil.log_e(this.CLASS_NAME, "getObjectHardKeyItem", "Unknown - col : " + paramInt2);
      return null;
    }
    if (paramInt1 == 7)
    {
      if (paramInt2 == 1) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296654));
      }
      if (paramInt2 == 2) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296655));
      }
      if (paramInt2 == 3) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296656));
      }
      if (paramInt2 == 4) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296657));
      }
      if (paramInt2 == 5) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296658));
      }
      if (paramInt2 == 6) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296659));
      }
      if (paramInt2 == 7) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296660));
      }
      if (paramInt2 == 8) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296661));
      }
      if (paramInt2 == 9) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296662));
      }
      if (paramInt2 == 10) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296663));
      }
      if (paramInt2 == 11) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296664));
      }
      if (paramInt2 == 12) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296665));
      }
      if (paramInt2 == 13) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296666));
      }
      if (paramInt2 == 14) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296667));
      }
      if (paramInt2 == 15) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296668));
      }
      FtUtil.log_e(this.CLASS_NAME, "getObjectHardKeyItem", "Unknown - col : " + paramInt2);
      return null;
    }
    if (paramInt1 == 8)
    {
      if (paramInt2 == 1) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296670));
      }
      if (paramInt2 == 2) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296671));
      }
      if (paramInt2 == 3) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296672));
      }
      if (paramInt2 == 4) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296673));
      }
      if (paramInt2 == 5) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296674));
      }
      if (paramInt2 == 6) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296675));
      }
      if (paramInt2 == 7) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296676));
      }
      if (paramInt2 == 8) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296677));
      }
      if (paramInt2 == 9) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296678));
      }
      if (paramInt2 == 10) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296679));
      }
      if (paramInt2 == 11) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296680));
      }
      if (paramInt2 == 12) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296681));
      }
      if (paramInt2 == 13) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296682));
      }
      if (paramInt2 == 14) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296683));
      }
      if (paramInt2 == 15) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296684));
      }
      FtUtil.log_e(this.CLASS_NAME, "getObjectHardKeyItem", "Unknown - col : " + paramInt2);
      return null;
    }
    if (paramInt1 == 9)
    {
      if (paramInt2 == 1) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296686));
      }
      if (paramInt2 == 2) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296687));
      }
      if (paramInt2 == 3) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296688));
      }
      if (paramInt2 == 4) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296689));
      }
      if (paramInt2 == 5) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296690));
      }
      if (paramInt2 == 6) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296691));
      }
      if (paramInt2 == 7) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296692));
      }
      if (paramInt2 == 8) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296693));
      }
      if (paramInt2 == 9) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296694));
      }
      if (paramInt2 == 10) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296695));
      }
      if (paramInt2 == 11) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296696));
      }
      if (paramInt2 == 12) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296697));
      }
      if (paramInt2 == 13) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296698));
      }
      if (paramInt2 == 14) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296699));
      }
      if (paramInt2 == 15) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296700));
      }
      FtUtil.log_e(this.CLASS_NAME, "getObjectHardKeyItem", "Unknown - col : " + paramInt2);
      return null;
    }
    if (paramInt1 == 10)
    {
      if (paramInt2 == 1) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296702));
      }
      if (paramInt2 == 2) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296703));
      }
      if (paramInt2 == 3) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296704));
      }
      if (paramInt2 == 4) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296705));
      }
      if (paramInt2 == 5) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296706));
      }
      if (paramInt2 == 6) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296707));
      }
      if (paramInt2 == 7) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296708));
      }
      if (paramInt2 == 8) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296709));
      }
      if (paramInt2 == 9) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296710));
      }
      if (paramInt2 == 10) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296711));
      }
      if (paramInt2 == 11) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296712));
      }
      if (paramInt2 == 12) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296713));
      }
      if (paramInt2 == 13) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296714));
      }
      if (paramInt2 == 14) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296715));
      }
      if (paramInt2 == 15) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296716));
      }
      FtUtil.log_e(this.CLASS_NAME, "getObjectHardKeyItem", "Unknown - col : " + paramInt2);
      return null;
    }
    if (paramInt1 == 11)
    {
      if (paramInt2 == 1) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296718));
      }
      if (paramInt2 == 2) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296719));
      }
      if (paramInt2 == 3) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296720));
      }
      if (paramInt2 == 4) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296721));
      }
      if (paramInt2 == 5) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296722));
      }
      if (paramInt2 == 6) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296723));
      }
      if (paramInt2 == 7) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296724));
      }
      if (paramInt2 == 8) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296725));
      }
      if (paramInt2 == 9) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296726));
      }
      if (paramInt2 == 10) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296727));
      }
      if (paramInt2 == 11) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296728));
      }
      if (paramInt2 == 12) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296729));
      }
      if (paramInt2 == 13) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296730));
      }
      if (paramInt2 == 14) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296731));
      }
      if (paramInt2 == 15) {
        return new UIKeyBase.HardKeyItem(this, (TextView)findViewById(2131296732));
      }
      FtUtil.log_e(this.CLASS_NAME, "getObjectHardKeyItem", "Unknown - col : " + paramInt2);
      return null;
    }
    FtUtil.log_e(this.CLASS_NAME, "getObjectHardKeyItem", "Unknown - row : " + paramInt1);
    return null;
  }
  
  private UIKeyBase.KeyTestItem getObjectKeyTestItem(int paramInt1, int paramInt2, String paramString1, String paramString2, float paramFloat1, float paramFloat2)
  {
    int i = converterKeyCode(paramString1);
    UIKeyBase.HardKeyItem localHardKeyItem = getObjectHardKeyItem(paramInt1, paramInt2);
    if ((i > -1) && (localHardKeyItem != null))
    {
      if ((paramString1.equals("DUMMY")) || (paramString2 == null)) {
        localHardKeyItem.setAttribute_TextView(0, paramString1, paramFloat1, paramFloat2);
      }
      for (;;)
      {
        return new UIKeyBase.KeyTestItem(this, i, localHardKeyItem, paramString1);
        localHardKeyItem.setAttribute_TextView(0, paramString2, paramFloat1, paramFloat2);
      }
    }
    FtUtil.log_e(this.CLASS_NAME, "getObjectKeyTestItem", "keyCode=Unknown or hardKeyItem=null");
    return null;
  }
  
  private void initialize()
  {
    int i = 0;
    String[] arrayOfString1 = readTestItem();
    int j = arrayOfString1.length;
    if (j > 0)
    {
      UIKeyBase.KeyTestItem[] arrayOfKeyTestItem1 = new UIKeyBase.KeyTestItem[j];
      int k = 1;
      int m = 0;
      int n = 0;
      int i1 = 0;
      while (k <= 11)
      {
        int i2 = 1;
        int i3;
        String[] arrayOfString2;
        if (i2 <= 15)
        {
          i3 = m + 1;
          arrayOfString2 = arrayOfString1[m].split("_");
          if (arrayOfString2[0].equals("END")) {
            m = i3;
          }
        }
        else
        {
          if (i1 > 0)
          {
            showRow(k);
            i1 = 0;
          }
          k++;
          continue;
        }
        setKeyCount(arrayOfString2[0]);
        int i4;
        int i5;
        if (!parsingItem(arrayOfString2))
        {
          i4 = n;
          i5 = i1;
        }
        for (;;)
        {
          i2++;
          n = i4;
          i1 = i5;
          m = i3;
          break;
          UIKeyBase.KeyTestItem localKeyTestItem = getObjectKeyTestItem(k, i2, arrayOfString2[0].toUpperCase(), this.mDisplay_String, this.mTextSize, this.DEFAULT_TEXT_VIEW_HEIGHT);
          if (localKeyTestItem != null)
          {
            i4 = n + 1;
            arrayOfKeyTestItem1[n] = localKeyTestItem;
            i5 = i1 + 1;
            FtUtil.log_d(this.CLASS_NAME, "initialize", "Row[" + k + "] item : " + arrayOfKeyTestItem1[(i4 - 1)].getKeyCode_String());
          }
          else
          {
            FtUtil.log_d(this.CLASS_NAME, "initialize", "Row[" + k + "] item : NONE");
            i4 = n;
            i5 = i1;
          }
        }
      }
      if (j != n)
      {
        arrayOfKeyTestItem2 = new UIKeyBase.KeyTestItem[n];
        while (i < n)
        {
          arrayOfKeyTestItem2[i] = arrayOfKeyTestItem1[i];
          i++;
        }
      }
      UIKeyBase.KeyTestItem[] arrayOfKeyTestItem2 = arrayOfKeyTestItem1;
      this.mTestList.addAll(Arrays.asList(arrayOfKeyTestItem2));
      Collections.sort(this.mTestList, this.mComparator);
      Button localButton1 = (Button)findViewById(2131296456);
      localButton1.setTextSize(this.DEFAULT_BUTTON_SIZE);
      localButton1.setOnClickListener(this.mExitButtonAction);
      Button localButton2 = (Button)findViewById(2131296457);
      localButton2.setTextSize(this.DEFAULT_BUTTON_SIZE);
      localButton2.setOnClickListener(this.mResetButtonAction);
    }
  }
  
  private boolean parsingItem(String[] paramArrayOfString)
  {
    if (paramArrayOfString.length == 1)
    {
      this.mDisplay_String = null;
      this.mTextSize = this.DEFAULT_TEXT_SIZE;
      return true;
    }
    if (paramArrayOfString.length == 2)
    {
      this.mDisplay_String = null;
      if (Float.valueOf(paramArrayOfString[1]).floatValue() > 0.0F)
      {
        this.mTextSize = Float.valueOf(paramArrayOfString[1]).floatValue();
        return true;
      }
      this.mTextSize = this.DEFAULT_TEXT_SIZE;
      return true;
    }
    if (paramArrayOfString.length == 3)
    {
      this.mDisplay_String = paramArrayOfString[2];
      if (Float.valueOf(paramArrayOfString[1]).floatValue() > 0.0F)
      {
        this.mTextSize = Float.valueOf(paramArrayOfString[1]).floatValue();
        return true;
      }
      this.mTextSize = this.DEFAULT_TEXT_SIZE;
      return true;
    }
    FtUtil.log_e(this.CLASS_NAME, "parsingItem", "Error => item.length > 3");
    return false;
  }
  
  private String[] readTestItem()
  {
    String str1 = Support.TestCase.getString("QWERTY_KEY_TEST_ROW_1");
    String str2;
    String str4;
    label93:
    String str6;
    label143:
    String str8;
    label193:
    String str10;
    label243:
    String str12;
    label293:
    String str14;
    label343:
    String str16;
    label393:
    String str18;
    label443:
    String str20;
    label493:
    String str21;
    if (str1 != null)
    {
      str2 = "" + str1 + " " + "END" + " ";
      String str3 = Support.TestCase.getString("QWERTY_KEY_TEST_ROW_2");
      if (str3 == null) {
        break label648;
      }
      str4 = str2 + str3 + " " + "END" + " ";
      String str5 = Support.TestCase.getString("QWERTY_KEY_TEST_ROW_3");
      if (str5 == null) {
        break label673;
      }
      str6 = str4 + str5 + " " + "END" + " ";
      String str7 = Support.TestCase.getString("QWERTY_KEY_TEST_ROW_4");
      if (str7 == null) {
        break label699;
      }
      str8 = str6 + str7 + " " + "END" + " ";
      String str9 = Support.TestCase.getString("QWERTY_KEY_TEST_ROW_5");
      if (str9 == null) {
        break label725;
      }
      str10 = str8 + str9 + " " + "END" + " ";
      String str11 = Support.TestCase.getString("QWERTY_KEY_TEST_ROW_6");
      if (str11 == null) {
        break label751;
      }
      str12 = str10 + str11 + " " + "END" + " ";
      String str13 = Support.TestCase.getString("QWERTY_KEY_TEST_ROW_7");
      if (str13 == null) {
        break label777;
      }
      str14 = str12 + str13 + " " + "END" + " ";
      String str15 = Support.TestCase.getString("QWERTY_KEY_TEST_ROW_8");
      if (str15 == null) {
        break label803;
      }
      str16 = str14 + str15 + " " + "END" + " ";
      String str17 = Support.TestCase.getString("QWERTY_KEY_TEST_ROW_9");
      if (str17 == null) {
        break label829;
      }
      str18 = str16 + str17 + " " + "END" + " ";
      String str19 = Support.TestCase.getString("QWERTY_KEY_TEST_ROW_10");
      if (str19 == null) {
        break label855;
      }
      str20 = str18 + str19 + " " + "END" + " ";
      str21 = Support.TestCase.getString("QWERTY_KEY_TEST_ROW_11");
      if (str21 == null) {
        break label881;
      }
    }
    label648:
    label673:
    label699:
    label725:
    label751:
    label881:
    for (String str22 = str20 + str21 + " " + "END";; str22 = str20 + "END")
    {
      FtUtil.log_d(this.CLASS_NAME, "readTestItem", "testItem : " + str22);
      Matcher localMatcher = Pattern.compile("[^ \t]+").matcher(str22);
      int j;
      for (int i = 0; localMatcher.find(); i = j)
      {
        String[] arrayOfString = this.buffer;
        j = i + 1;
        arrayOfString[i] = localMatcher.group();
      }
      str2 = "" + "END ";
      break;
      str4 = str2 + "END ";
      break label93;
      str6 = str4 + "END ";
      break label143;
      str8 = str6 + "END ";
      break label193;
      str10 = str8 + "END ";
      break label243;
      str12 = str10 + "END ";
      break label293;
      str14 = str12 + "END ";
      break label343;
      str16 = str14 + "END ";
      break label393;
      str18 = str16 + "END ";
      break label443;
      str20 = str18 + "END ";
      break label493;
    }
    label777:
    label803:
    label829:
    label855:
    return this.buffer;
  }
  
  private void setKeyCount(String paramString)
  {
    if (paramString.equals("ENTER1")) {
      this.mKeyCount_Enter = (1 + this.mKeyCount_Enter);
    }
    do
    {
      return;
      if (paramString.equals("ENTER2"))
      {
        this.mKeyCount_Enter = (1 + this.mKeyCount_Enter);
        return;
      }
      if (paramString.equals("SPACE1"))
      {
        this.mKeyCount_Space = (1 + this.mKeyCount_Space);
        return;
      }
      if (paramString.equals("SPACE2"))
      {
        this.mKeyCount_Space = (1 + this.mKeyCount_Space);
        return;
      }
      if (paramString.equals("SPACE3"))
      {
        this.mKeyCount_Space = (1 + this.mKeyCount_Space);
        return;
      }
    } while (!paramString.equals("SPACE4"));
    this.mKeyCount_Space = (1 + this.mKeyCount_Space);
  }
  
  private void setResult_MultiKey(int paramInt)
  {
    int i = 66;
    int j = -1;
    if (paramInt == i) {
      if (this.mIndex_Enter != 0) {}
    }
    for (;;)
    {
      this.mIndex_Enter = (1 + this.mIndex_Enter);
      if (this.mIndex_Enter == this.mKeyCount_Enter)
      {
        this.mIndex_Enter = 0;
        j = i;
      }
      for (;;)
      {
        label45:
        int k = Collections.binarySearch(this.mTestList, new UIKeyBase.KeyTestItem(this, j), this.mComparator);
        UIKeyBase.KeyTestItem localKeyTestItem = (UIKeyBase.KeyTestItem)this.mTestList.get(k);
        boolean bool;
        label92:
        TextView localTextView;
        if (!localKeyTestItem.mIsPassed)
        {
          bool = true;
          localKeyTestItem.mIsPassed = bool;
          localTextView = localKeyTestItem.mHardKeyItem.mHardKeyText;
          if (!localKeyTestItem.mIsPassed) {
            break label279;
          }
        }
        label279:
        for (int m = -16776961;; m = -65536)
        {
          localTextView.setTextColor(m);
          FtUtil.log_d(this.CLASS_NAME, "setResult_MultiKey", "keyCode : " + localKeyTestItem.getKeyCode_String());
          return;
          if (this.mIndex_Enter != 1) {
            break label292;
          }
          i = this.KEYCODE_ENTER_2;
          break;
          if (paramInt != 62) {
            break label45;
          }
          if (this.mIndex_Space == 0) {
            j = 62;
          }
          for (;;)
          {
            this.mIndex_Space = (1 + this.mIndex_Space);
            if (this.mIndex_Space != this.mKeyCount_Space) {
              break;
            }
            this.mIndex_Space = 0;
            break;
            if (this.mIndex_Space == 1) {
              j = this.KEYCODE_SPACE_2;
            } else if (this.mIndex_Space == 2) {
              j = this.KEYCODE_SPACE_3;
            } else if (this.mIndex_Space == 3) {
              j = this.KEYCODE_SPACE_4;
            }
          }
          bool = false;
          break label92;
        }
        j = i;
      }
      label292:
      i = j;
    }
  }
  
  private void showRow(int paramInt)
  {
    if (paramInt == 1) {
      ((LinearLayout)findViewById(2131296557)).setVisibility(0);
    }
    do
    {
      return;
      if (paramInt == 2)
      {
        ((LinearLayout)findViewById(2131296573)).setVisibility(0);
        return;
      }
      if (paramInt == 3)
      {
        ((LinearLayout)findViewById(2131296589)).setVisibility(0);
        return;
      }
      if (paramInt == 4)
      {
        ((LinearLayout)findViewById(2131296605)).setVisibility(0);
        return;
      }
      if (paramInt == 5)
      {
        ((LinearLayout)findViewById(2131296621)).setVisibility(0);
        return;
      }
      if (paramInt == 6)
      {
        ((LinearLayout)findViewById(2131296637)).setVisibility(0);
        return;
      }
      if (paramInt == 7)
      {
        ((LinearLayout)findViewById(2131296653)).setVisibility(0);
        return;
      }
      if (paramInt == 8)
      {
        ((LinearLayout)findViewById(2131296669)).setVisibility(0);
        return;
      }
      if (paramInt == 9)
      {
        ((LinearLayout)findViewById(2131296685)).setVisibility(0);
        return;
      }
    } while (paramInt != 10);
    ((LinearLayout)findViewById(2131296701)).setVisibility(0);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903084);
    FtUtil.log_d(this.CLASS_NAME, "onCreate", "<Default> DEFAULT_TEXT_SIZE : " + this.DEFAULT_TEXT_SIZE);
    FtUtil.log_d(this.CLASS_NAME, "onCreate", "<Default> DEFAULT_TEXT_VIEW_HEIGHT : " + this.DEFAULT_TEXT_VIEW_HEIGHT);
    FtUtil.log_d(this.CLASS_NAME, "onCreate", "<Default> DEFAULT_BUTTON_SIZE : " + this.DEFAULT_BUTTON_SIZE);
    initialize();
    this.mModeLCiA = "LCiA".equalsIgnoreCase(Support.Properties.get("PGM_STAGE"));
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    super.setLongPress(false, paramKeyEvent);
    if (!isLongPress())
    {
      int i;
      if (paramInt == 59)
      {
        FtUtil.log_e(this.CLASS_NAME, "onKeyDown", "keyCode : KEYCODE_SHIFT_LEFT [" + this.ShiftStatus + "]");
        if (this.ShiftStatus >= 2) {
          this.ShiftStatus = 0;
        }
      }
      else
      {
        i = Collections.binarySearch(this.mTestList, new UIKeyBase.KeyTestItem(this, paramInt), this.mComparator);
        if (i < 0) {
          break label363;
        }
        if ((paramInt != 66) && (paramInt != 62)) {
          break label187;
        }
        setResult_MultiKey(paramInt);
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
      }
      this.ShiftStatus = (1 + this.ShiftStatus);
      PressShiftKey();
      return true;
      label187:
      UIKeyBase.KeyTestItem localKeyTestItem = (UIKeyBase.KeyTestItem)this.mTestList.get(i);
      boolean bool;
      label212:
      TextView localTextView;
      if (!localKeyTestItem.mIsPassed)
      {
        bool = true;
        localKeyTestItem.mIsPassed = bool;
        localTextView = localKeyTestItem.mHardKeyItem.mHardKeyText;
        if (!localKeyTestItem.mIsPassed) {
          break label292;
        }
      }
      label292:
      for (int j = -16776961;; j = -65536)
      {
        localTextView.setTextColor(j);
        FtUtil.log_d(this.CLASS_NAME, "onKeyDown", "keyCode : " + localKeyTestItem.getKeyCode_String());
        break;
        bool = false;
        break label212;
      }
      setResult(-1);
      if (this.mELLightTimePreQwertKeyTestReadPass == true) {
        Settings.System.putInt(getContentResolver(), "button_key_light", this.mELLightTimePreQwertKeyTest);
      }
      try
      {
        FtUtil.log_i(this.CLASS_NAME, "delay", " : 1000ms");
        Thread.sleep(200L);
        finish();
        return true;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;)
        {
          localInterruptedException.printStackTrace();
        }
      }
      label363:
      FtUtil.log_e(this.CLASS_NAME, "onKeyDown", "keyCode : Unknown [" + paramInt + "]");
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public void onPause()
  {
    super.onPause();
    ModuleAudio.instance(this).setStreamMusicVolume(this.mOldVolume);
    if (this.mELLightTimePreQwertKeyTestReadPass == true) {
      Settings.System.putInt(getContentResolver(), "button_key_light", this.mELLightTimePreQwertKeyTest);
    }
  }
  
  public void onResume()
  {
    super.onResume();
    this.mOldVolume = ModuleAudio.instance(this).getStreamMusicVolume();
    FtUtil.log_i(this.CLASS_NAME, "initialize", "mOldVolume = " + this.mOldVolume);
    ModuleAudio.instance(this).setStreamMusicVolumeMax();
    try
    {
      this.mELLightTimePreQwertKeyTest = Settings.System.getInt(getContentResolver(), "button_key_light");
      Settings.System.putInt(getContentResolver(), "button_key_light", -1);
      this.mELLightTimePreQwertKeyTestReadPass = true;
      FtUtil.log_i(this.CLASS_NAME, "initialize", "mELLightTimePreQwertKeyTest = " + this.mELLightTimePreQwertKeyTest);
      if (Support.Feature.getBoolean("SUPPORT_NONE_SLIDE_QWERTY_PHONE")) {
        setRequestedOrientation(1);
      }
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        FtUtil.log_i(this.CLASS_NAME, "initialize", "exception = " + localException);
        this.mELLightTimePreQwertKeyTestReadPass = false;
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIQwertyKey
 * JD-Core Version:    0.7.1
 */