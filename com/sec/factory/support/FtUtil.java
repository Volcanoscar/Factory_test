package com.sec.factory.support;

import android.content.ComponentName;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.IWindowManager;
import android.view.IWindowManager.Stub;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import java.lang.reflect.Field;

public class FtUtil
{
  public static String addDummyValue(String paramString, int paramInt, char paramChar)
  {
    log_d("FtUtil", "addDummyValue", "value : " + paramString);
    String str = paramString.trim();
    log_d("FtUtil", "addDummyValue", "value : " + str);
    StringBuilder localStringBuilder = new StringBuilder(str);
    for (int i = 0; i < paramInt - str.length(); i++) {
      localStringBuilder.append(paramChar);
    }
    return localStringBuilder.toString();
  }
  
  public static int charToNum(char paramChar)
  {
    if (('0' <= paramChar) && (paramChar <= '9')) {
      return paramChar - '0';
    }
    if (('a' <= paramChar) && (paramChar <= 'f')) {
      return 10 + (paramChar - 'a');
    }
    if (('A' <= paramChar) && (paramChar <= 'F')) {
      return 10 + (paramChar - 'A');
    }
    log_e("FtUtil", "charToNum", "can not convert to Heximal : " + paramChar);
    return -1;
  }
  
  private static String getLogMessage(String paramString1, String paramString2, String paramString3)
  {
    String str1;
    String str2;
    if ((paramString1 == null) || (paramString1.equals("")))
    {
      str1 = "";
      if ((paramString2 != null) && (!paramString2.equals(""))) {
        break label190;
      }
      str2 = "";
      label33:
      if ((!str1.equals("")) && (!str2.equals(""))) {
        str2 = " , " + str2;
      }
      if ((paramString3 != null) && (!paramString3.equals(""))) {
        break label214;
      }
    }
    label190:
    label214:
    for (String str3 = "";; str3 = "[Message] " + paramString3)
    {
      if ((!str3.equals("")) && ((!str2.equals("")) || (!str1.equals("")))) {
        str3 = " , " + str3;
      }
      return str1 + str2 + str3;
      str1 = "[Class] " + paramString1;
      break;
      str2 = "[Method] " + paramString2;
      break label33;
    }
  }
  
  public static boolean isFactoryAppAPO()
  {
    return Support.Feature.getBoolean("FACTORY_TEST_APO");
  }
  
  public static boolean isFolderOpen()
  {
    boolean bool = true;
    String str = Support.Kernel.read("PATH_HALLIC_STATE");
    log_v("FtUtil", "isFolderOpen", str);
    if ((str != null) && (str.equals("0"))) {
      bool = false;
    }
    return bool;
  }
  
  public static void log_d(String paramString1, String paramString2)
  {
    Log.d("FactoryTestApp", getLogMessage(paramString1, paramString2, null));
  }
  
  public static void log_d(String paramString1, String paramString2, String paramString3)
  {
    Log.d("FactoryTestApp", getLogMessage(paramString1, paramString2, paramString3));
  }
  
  public static void log_e(Exception paramException)
  {
    StackTraceElement[] arrayOfStackTraceElement = paramException.getStackTrace();
    Log.w("FactoryTestApp", "WARNNING: " + paramException.toString());
    for (int i = 0; i < arrayOfStackTraceElement.length; i++) {
      Log.w("FactoryTestApp", "WARNNING:     " + arrayOfStackTraceElement[i]);
    }
  }
  
  public static void log_e(String paramString1, String paramString2)
  {
    Log.e("FactoryTestApp", getLogMessage(paramString1, paramString2, null));
  }
  
  public static void log_e(String paramString1, String paramString2, String paramString3)
  {
    Log.e("FactoryTestApp", getLogMessage(paramString1, paramString2, paramString3));
  }
  
  public static void log_i(String paramString1, String paramString2)
  {
    Log.i("FactoryTestApp", getLogMessage(paramString1, paramString2, null));
  }
  
  public static void log_i(String paramString1, String paramString2, String paramString3)
  {
    Log.i("FactoryTestApp", getLogMessage(paramString1, paramString2, paramString3));
  }
  
  public static void log_v(String paramString1, String paramString2, String paramString3)
  {
    Log.v("FactoryTestApp", getLogMessage(paramString1, paramString2, paramString3));
  }
  
  public static void log_w(String paramString1, String paramString2, String paramString3)
  {
    Log.w("FactoryTestApp", getLogMessage(paramString1, paramString2, paramString3));
  }
  
  public static void setRemoveStatusBar(Window paramWindow)
  {
    WindowManager.LayoutParams localLayoutParams = paramWindow.getAttributes();
    localLayoutParams.flags = (0x400 | localLayoutParams.flags);
    localLayoutParams.privateFlags = (0x10 | localLayoutParams.privateFlags);
    paramWindow.setAttributes(localLayoutParams);
  }
  
  public static void setRemoveSystemUI(Window paramWindow, boolean paramBoolean)
  {
    localLayoutParams = paramWindow.getAttributes();
    for (;;)
    {
      try
      {
        Field localField = View.class.getField("SYSTEM_UI_FLAG_REMOVE_NAVIGATION");
        if (localField == null) {
          continue;
        }
        int j = localField.getInt(localField);
        i = j;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        int i = 0;
        continue;
        if ((i & localLayoutParams.systemUiVisibility) == 0) {
          continue;
        }
        localLayoutParams.systemUiVisibility = (i ^ localLayoutParams.systemUiVisibility);
        continue;
      }
      if (!paramBoolean) {
        continue;
      }
      localLayoutParams.systemUiVisibility = (i | localLayoutParams.systemUiVisibility);
      paramWindow.setAttributes(localLayoutParams);
      return;
      i = 2;
    }
  }
  
  public static void setSystemKeyBlock(ComponentName paramComponentName, int paramInt)
  {
    IWindowManager localIWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
    try
    {
      localIWindowManager.requestSystemKeyEvent(paramInt, paramComponentName, true);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      log_e(localRemoteException);
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.support.FtUtil
 * JD-Core Version:    0.7.1
 */