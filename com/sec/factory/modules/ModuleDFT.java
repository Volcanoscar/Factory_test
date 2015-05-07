package com.sec.factory.modules;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Point;
import android.net.Uri;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.ITelephony.Stub;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import java.util.List;

public class ModuleDFT
  extends ModuleObject
{
  private static ModuleDFT mInstance = null;
  private boolean mIsSlateCommand = false;
  private long mTouchLongEventTime;
  private float mTouchLongX1;
  private float mTouchLongY1;
  private int mTouchMoveStatus = 0;
  private String x1;
  private String y1;
  
  private ModuleDFT(Context paramContext)
  {
    super(paramContext, "ModuleDFT");
    FtUtil.log_i(this.CLASS_NAME, "ModuleDFT", "Create ModuleDFT");
  }
  
  private void ActionDFTTouch(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    FtUtil.log_i(this.CLASS_NAME, "ActionDFTTouch", "type=" + paramInt + ", x1=" + paramString1 + ", y1=" + paramString2 + ", x2=" + paramString3 + ", y2=" + paramString4);
    Display localDisplay = ((WindowManager)getSystemService("window")).getDefaultDisplay();
    Point localPoint = new Point();
    localPoint.x = localDisplay.getWidth();
    localPoint.y = localDisplay.getHeight();
    FtUtil.log_i(this.CLASS_NAME, "ActionDFTTouch", "Display w=" + localPoint.x + ", h=" + localPoint.y);
    float f1;
    float f2;
    float f3;
    float f4;
    long l1;
    MotionEvent localMotionEvent;
    try
    {
      f1 = Float.parseFloat(paramString1);
      f2 = Float.parseFloat(paramString2);
      f3 = Float.parseFloat(paramString3);
      f4 = Float.parseFloat(paramString4);
      FtUtil.log_i(this.CLASS_NAME, "ActionDFTTouch", "x1" + f1 + "y1" + f2 + "x2" + f3 + "y2" + f4);
      l1 = SystemClock.uptimeMillis();
      localMotionEvent = MotionEvent.obtain(l1, l1, 0, f1, f2, 0);
      if (paramInt == 1)
      {
        injectTouchEvent(localMotionEvent, MotionEvent.obtain(l1, l1, 1, f1, f2, 0));
        return;
      }
      if (paramInt == 3)
      {
        injectTouchEvent(localMotionEvent, null);
        FtUtil.log_d(this.CLASS_NAME, "ActionDFTTouch", "Touch Event = down = " + l1 + " = " + f1 + " , " + f2);
        this.mTouchLongEventTime = l1;
        this.mTouchLongX1 = f1;
        this.mTouchLongY1 = f2;
        Thread local3 = new Thread()
        {
          public void run()
          {
            try
            {
              sleep(2000L);
              MotionEvent localMotionEvent = MotionEvent.obtain(ModuleDFT.this.mTouchLongEventTime, 2000L + ModuleDFT.this.mTouchLongEventTime, 1, ModuleDFT.this.mTouchLongX1, ModuleDFT.this.mTouchLongY1, 0);
              ModuleDFT.this.injectTouchEvent(localMotionEvent, null);
              FtUtil.log_d(ModuleDFT.this.CLASS_NAME, "ActionDFTTouch", "Touch Event (Thread) = up = " + ModuleDFT.this.mTouchLongEventTime + "(+2000) = " + ModuleDFT.this.mTouchLongX1 + " , " + ModuleDFT.this.mTouchLongY1);
              return;
            }
            catch (InterruptedException localInterruptedException)
            {
              localInterruptedException.printStackTrace();
            }
          }
        };
        local3.start();
        return;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return;
    }
    if (paramInt == 2)
    {
      float f5 = (f3 - f1) / 10.0F;
      float f6 = (f4 - f2) / 10.0F;
      injectTouchEvent(localMotionEvent, null);
      long l2 = l1;
      for (int i = 1; i <= 10; i++)
      {
        float f7 = f1 + f5 * i;
        float f8 = f2 + f6 * i;
        injectTouchEvent(MotionEvent.obtain(l2, l1, 2, f7, f8, 0), null);
        l1 += 27L;
      }
      injectTouchEvent(MotionEvent.obtain(l2, l1, 1, f3, f4, 0), null);
    }
  }
  
  private void ActionDFTkey(int paramInt, boolean paramBoolean)
  {
    long l = SystemClock.uptimeMillis();
    if (((paramInt == 6) && (paramBoolean == true)) || ((paramInt == 26) && (paramBoolean == true)))
    {
      FtUtil.log_i(this.CLASS_NAME, "ActionDFTKey", "KEYCODE_ENDCALL, KEYCODE_POWER hold=true");
      Intent localIntent4 = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
      localIntent4.putExtra("android.intent.extra.KEY_CONFIRM", false);
      localIntent4.setFlags(268435456);
      startActivity(localIntent4);
    }
    do
    {
      ITelephony localITelephony;
      do
      {
        return;
        if ((paramInt != 6) || (paramBoolean)) {
          break;
        }
        FtUtil.log_i(this.CLASS_NAME, "ActionDFTKey", "KEYCODE_ENDCALL hold=false");
        localITelephony = ITelephony.Stub.asInterface(ServiceManager.checkService("phone"));
      } while (localITelephony == null);
      try
      {
        localITelephony.endCall();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        FtUtil.log_e(localRemoteException);
        return;
      }
      if ((paramInt == 26) && (!paramBoolean))
      {
        isSleep();
        return;
      }
      if ((paramInt == 65) && (!paramBoolean))
      {
        FtUtil.log_i(this.CLASS_NAME, "ActionDFTKey", "KEYCODE_ENVELOPE hold=false");
        Intent localIntent3 = new Intent("android.intent.action.SEND");
        localIntent3.addFlags(268435456);
        localIntent3.setClassName("com.android.mms", "com.android.mms.ui.ConversationComposer");
        startActivity(localIntent3);
        return;
      }
      if (paramInt == 27)
      {
        FtUtil.log_i(this.CLASS_NAME, "ActionDFTKey", "KEYCODE_CAMERA");
        if (((ActivityManager.RunningTaskInfo)((ActivityManager)getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getClassName().equals("com.sec.android.app.camera.Camera"))
        {
          injectKeyEvent(new KeyEvent(l, l, 0, paramInt, 0), new KeyEvent(l, l, 1, paramInt, 0));
          return;
        }
        Intent localIntent2 = new Intent("android.media.action.IMAGE_CAPTURE");
        localIntent2.setFlags(268435456);
        startActivity(localIntent2);
        return;
      }
      FtUtil.log_i(this.CLASS_NAME, "ActionDFTKey", "Another key keyCode=" + paramInt + ", hold=" + paramBoolean);
      String str1 = ((ActivityManager.RunningTaskInfo)((ActivityManager)getSystemService("activity")).getRunningTasks(1).get(0)).topActivity.getClassName();
      FtUtil.log_d(this.CLASS_NAME, "ActionDFTKey", "topactivity call = " + str1);
      if ((paramInt == 66) && (!str1.equals("com.android.contacts.DialtactsActivity")) && (!str1.equals("com.android.contacts.activities.DialtactsActivity")) && (!str1.equals(Support.Feature.getString("CONTACT_DIALTACTS_CLASS"))) && (!this.mIsSlateCommand))
      {
        Intent localIntent1 = new Intent("android.intent.action.DIAL");
        localIntent1.addFlags(268435456);
        String str2 = Support.Feature.getString("CONTACT_DIALTACTS_PACKAGE");
        String str3 = Support.Feature.getString("CONTACT_DIALTACTS_CLASS");
        FtUtil.log_d(this.CLASS_NAME, "ActionDFTKey", "CONTACT_DIALTACTS_PACKAGE = " + str2);
        FtUtil.log_d(this.CLASS_NAME, "ActionDFTKey", "CONTACT_DIALTACTS_CLASS = " + str3);
        localIntent1.setClassName(str2, str3);
        startActivity(localIntent1);
        return;
      }
    } while (paramBoolean);
    KeyEvent localKeyEvent1 = new KeyEvent(l, l, 0, paramInt, 0);
    KeyEvent localKeyEvent2 = new KeyEvent(l, l, 1, paramInt, 0);
    FtUtil.log_d(this.CLASS_NAME, "ActionDFTKey", "Inject keyevent: keycode" + paramInt);
    injectKeyEvent(localKeyEvent1, localKeyEvent2);
  }
  
  private String hexToDec(String paramString)
  {
    return Float.toString(4096.0F * FtUtil.charToNum(paramString.charAt(0)) + 256.0F * FtUtil.charToNum(paramString.charAt(1)) + 16.0F * FtUtil.charToNum(paramString.charAt(2)) + FtUtil.charToNum(paramString.charAt(3)));
  }
  
  private void injectKeyEvent(final KeyEvent paramKeyEvent1, final KeyEvent paramKeyEvent2)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        new Instrumentation().sendKeySync(paramKeyEvent1);
        new Instrumentation().sendKeySync(paramKeyEvent2);
      }
    }).start();
  }
  
  private void injectTouchEvent(final MotionEvent paramMotionEvent1, final MotionEvent paramMotionEvent2)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        new Instrumentation().sendPointerSync(paramMotionEvent1);
        if (paramMotionEvent2 != null) {
          new Instrumentation().sendPointerSync(paramMotionEvent2);
        }
      }
    }).start();
  }
  
  public static ModuleDFT instance(Context paramContext)
  {
    if (mInstance == null) {
      mInstance = new ModuleDFT(paramContext);
    }
    return mInstance;
  }
  
  private void isSleep()
  {
    PowerManager localPowerManager = (PowerManager)getSystemService("power");
    if (localPowerManager.isScreenOn())
    {
      localPowerManager.goToSleep(SystemClock.uptimeMillis());
      return;
    }
    localPowerManager.newWakeLock(805306394, "wake_up").acquire();
  }
  
  private int keymapping(String paramString, boolean paramBoolean)
  {
    int i = Integer.valueOf(paramString).intValue();
    FtUtil.log_i(this.CLASS_NAME, "keymapping: ", String.valueOf(i));
    switch (i)
    {
    case 16: 
    case 17: 
    case 19: 
    case 21: 
    case 34: 
    case 35: 
    case 36: 
    case 37: 
    case 38: 
    case 39: 
    case 40: 
    case 41: 
    case 42: 
    case 43: 
    case 44: 
    case 45: 
    case 46: 
    case 47: 
    case 48: 
    case 49: 
    case 50: 
    case 51: 
    case 52: 
    case 53: 
    case 54: 
    case 55: 
    case 56: 
    case 57: 
    case 58: 
    case 59: 
    case 60: 
    case 61: 
    case 62: 
    case 63: 
    case 64: 
    case 91: 
    case 92: 
    case 93: 
    case 94: 
    case 95: 
    case 96: 
    case 98: 
    case 104: 
    default: 
      return 0;
    case 32: 
      return 3;
    case 15: 
      return 4;
    case 11: 
      int j = ((TelephonyManager)mContext.getSystemService("phone")).getCallState();
      FtUtil.log_d(this.CLASS_NAME, "ActionDFTKey", "callState = " + j);
      if (j != 1) {
        return 66;
      }
      return 5;
    case 12: 
      return 6;
    case 0: 
    case 10: 
      if (paramBoolean) {
        return 81;
      }
      return 7;
    case 1: 
      return 8;
    case 2: 
      return 9;
    case 3: 
      return 10;
    case 4: 
      return 11;
    case 5: 
      return 12;
    case 6: 
      return 13;
    case 7: 
      return 14;
    case 8: 
      return 15;
    case 9: 
      return 16;
    case 13: 
      return 18;
    case 14: 
      return 17;
    case 18: 
      return 19;
    case 20: 
      return 20;
    case 24: 
      return 21;
    case 25: 
      return 22;
    case 27: 
      return 66;
    case 100: 
      return 66;
    case 22: 
      return 24;
    case 23: 
      return 25;
    case 30: 
    case 33: 
      return 26;
    case 26: 
      return 27;
    case 102: 
      return 67;
    case 28: 
      return 82;
    case 29: 
      return 4;
    case 65: 
      return 29;
    case 66: 
      return 30;
    case 67: 
      return 31;
    case 68: 
      return 32;
    case 69: 
      return 33;
    case 70: 
      return 34;
    case 71: 
      return 35;
    case 72: 
      return 36;
    case 73: 
      return 37;
    case 74: 
      return 38;
    case 75: 
      return 39;
    case 76: 
      return 40;
    case 77: 
      return 41;
    case 78: 
      return 42;
    case 79: 
      return 43;
    case 80: 
      return 44;
    case 81: 
      return 45;
    case 82: 
      return 46;
    case 83: 
      return 47;
    case 84: 
      return 48;
    case 85: 
      return 49;
    case 86: 
      return 50;
    case 87: 
      return 51;
    case 88: 
      return 52;
    case 89: 
      return 53;
    case 90: 
      return 54;
    case 99: 
      return 57;
    case 109: 
      return 60;
    case 105: 
      return 63;
    case 108: 
      return 84;
    case 103: 
      return 55;
    case 97: 
      return 62;
    case 101: 
      return 56;
    case 106: 
      return 94;
    case 107: 
      return 65;
    case 110: 
      return 187;
    }
    return 302;
  }
  
  private int keymappingCPO(String paramString, boolean paramBoolean)
  {
    int i = Integer.parseInt(paramString, 16);
    FtUtil.log_i(this.CLASS_NAME, "keymapping: ", String.valueOf(i));
    FtUtil.log_i(this.CLASS_NAME, "keymapping: ", String.valueOf(66));
    FtUtil.log_i(this.CLASS_NAME, "keymapping: ", String.valueOf(5));
    this.mIsSlateCommand = false;
    switch (i)
    {
    default: 
      return 0;
    case 152: 
      return 3;
    case 21: 
      return 4;
    case 80: 
      int j = ((TelephonyManager)mContext.getSystemService("phone")).getCallState();
      FtUtil.log_d(this.CLASS_NAME, "ActionDFTKey", "callState = " + j);
      if (j != 1) {
        return 66;
      }
      return 5;
    case 81: 
      return 6;
    case 16: 
    case 48: 
      if (paramBoolean) {
        return 81;
      }
      return 7;
    case 49: 
      return 8;
    case 50: 
      return 9;
    case 51: 
      return 10;
    case 52: 
      return 11;
    case 53: 
      return 12;
    case 54: 
      return 13;
    case 55: 
      return 14;
    case 56: 
      return 15;
    case 57: 
      return 16;
    case 35: 
      return 18;
    case 42: 
      return 17;
    case 99: 
      return 19;
    case 100: 
      return 20;
    case 101: 
      return 21;
    case 102: 
      return 22;
    case 140: 
    case 244: 
      this.mIsSlateCommand = true;
      return 66;
    case 84: 
    case 115: 
      return 24;
    case 85: 
    case 114: 
      return 25;
    case 149: 
    case 222: 
      return 26;
    case 141: 
      return 27;
    case 91: 
    case 151: 
      return 82;
    case 82: 
    case 92: 
    case 246: 
      return 4;
    case 227: 
      return 29;
    case 241: 
      return 30;
    case 239: 
      return 31;
    case 229: 
      return 32;
    case 248: 
      return 33;
    case 230: 
      return 34;
    case 231: 
      return 35;
    case 232: 
      return 36;
    case 196: 
      return 37;
    case 233: 
      return 38;
    case 234: 
      return 39;
    case 235: 
      return 40;
    case 243: 
      return 41;
    case 242: 
      return 42;
    case 197: 
      return 43;
    case 198: 
      return 44;
    case 192: 
      return 45;
    case 236: 
      return 46;
    case 228: 
      return 47;
    case 193: 
      return 48;
    case 195: 
      return 49;
    case 240: 
      return 50;
    case 199: 
      return 51;
    case 238: 
      return 52;
    case 194: 
      return 53;
    case 237: 
      return 54;
    case 225: 
      return 57;
    case 182: 
      return 60;
    case 202: 
      return 63;
    case 181: 
      return 84;
    case 200: 
      return 55;
    case 247: 
      return 62;
    case 245: 
      return 56;
    case 254: 
      return 76;
    case 11: 
      return 65;
    case 157: 
      return 187;
    }
    return 232;
  }
  
  public void DftKey(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "DftKey", "key=" + paramString);
    if (FtUtil.isFactoryAppAPO())
    {
      ActionDFTkey(keymapping(paramString, false), false);
      return;
    }
    ActionDFTkey(keymappingCPO(paramString, false), false);
  }
  
  public void DftKeyHold(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "DftKeyHold", "key=" + paramString);
    if (FtUtil.isFactoryAppAPO())
    {
      ActionDFTkey(keymapping(paramString, true), true);
      return;
    }
    ActionDFTkey(keymappingCPO(paramString, true), true);
  }
  
  public String DftMicsd()
  {
    if (ModuleDevice.instance(getApplicationContext()).isDetectExternalMemory())
    {
      if (ModuleDevice.instance(getApplicationContext()).isMountedStorage(1))
      {
        if (!FtUtil.isFactoryAppAPO())
        {
          FtUtil.log_i(this.CLASS_NAME, "DftMicsd", "SDdetect<swmount> = ok:" + "\002");
          return "\002";
        }
        return "AT+MICSD:SON\r\nOK";
      }
      if (!FtUtil.isFactoryAppAPO())
      {
        FtUtil.log_i(this.CLASS_NAME, "DftMicsd", "SDdetect<swunmount> = ok:" + "\001");
        return "\001";
      }
      return "AT+MICSD:OFF\r\nOK";
    }
    if (!FtUtil.isFactoryAppAPO())
    {
      FtUtil.log_i(this.CLASS_NAME, "DftMicsd", "SDdetect = nok:" + "");
      return "";
    }
    return "AT+MICSD:OFF\r\nOK";
  }
  
  public void DftTouchDown(String paramString1, String paramString2)
  {
    FtUtil.log_i(this.CLASS_NAME, "DftTouchDown", "x=" + paramString1 + ", y=" + paramString2);
    if (!FtUtil.isFactoryAppAPO())
    {
      paramString1 = hexToDec(paramString1);
      paramString2 = hexToDec(paramString2);
    }
    FtUtil.log_i(this.CLASS_NAME, "DftTouchDown", "x=" + paramString1 + ", y=" + paramString2);
    this.x1 = paramString1;
    this.y1 = paramString2;
    this.mTouchMoveStatus = 1;
  }
  
  public void DftTouchLong()
  {
    FtUtil.log_i(this.CLASS_NAME, "DftTouchLong", "Touch Long");
    if (this.mTouchMoveStatus == 1) {
      this.mTouchMoveStatus = 3;
    }
  }
  
  public void DftTouchMove()
  {
    FtUtil.log_i(this.CLASS_NAME, "DftTouchMode", "Touch Move");
    if (this.mTouchMoveStatus == 1) {
      this.mTouchMoveStatus = 2;
    }
  }
  
  public void DftTouchUp(String paramString1, String paramString2)
  {
    if (!FtUtil.isFactoryAppAPO())
    {
      paramString1 = hexToDec(paramString1);
      paramString2 = hexToDec(paramString2);
    }
    FtUtil.log_i(this.CLASS_NAME, "DftTouchUp", "x=" + paramString1 + ", y=" + paramString2);
    if (this.mTouchMoveStatus == 1) {
      ActionDFTTouch(this.mTouchMoveStatus, this.x1, this.y1, paramString1, paramString2);
    }
    for (;;)
    {
      this.mTouchMoveStatus = 0;
      return;
      if (this.mTouchMoveStatus == 2) {
        ActionDFTTouch(this.mTouchMoveStatus, this.x1, this.y1, paramString1, paramString2);
      } else if (this.mTouchMoveStatus == 3) {
        ActionDFTTouch(this.mTouchMoveStatus, this.x1, this.y1, paramString1, paramString2);
      }
    }
  }
  
  public boolean makeSMS(Uri paramUri, String paramString1, String paramString2)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("read", Integer.valueOf(1));
    localContentValues.put("date", new Long(System.currentTimeMillis()));
    localContentValues.put("address", paramString1);
    localContentValues.put("body", paramString2.substring(2, -2 + paramString2.length()));
    try
    {
      getContentResolver().insert(paramUri, localContentValues);
      return true;
    }
    catch (SQLiteException localSQLiteException)
    {
      FtUtil.log_e(localSQLiteException);
    }
    return false;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.ModuleDFT
 * JD-Core Version:    0.7.1
 */