package com.sec.factory.aporiented.athandler;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.provider.Settings.Secure;
import android.util.Log;

public class AtDebugLvC
  extends AtCommandHandler
{
  public AtDebugLvC(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "DEBUGLVC";
    this.CLASS_NAME = "AtDebuglvc";
    this.NUM_ARGS = 2;
  }
  
  private void doRebootNSave(String paramString)
  {
    String str = "debug" + paramString;
    Log.i(this.CMD_NAME, "Set debug: " + str);
    ((PowerManager)this.context.getSystemService("power")).reboot(str);
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      String str1;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0" }))
        {
          reqestChangeLevel("0x4f4c");
          str1 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1" }))
        {
          reqestChangeLevel("0x494d");
          str1 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2" }))
        {
          reqestChangeLevel("0x4948");
          str1 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "0" }))
        {
          String str3 = SystemProperties.get("ro.debug_level", "Unknown");
          if ("0x4f4c".equals(str3))
          {
            str3 = "LOW";
            str1 = responseString(paramArrayOfString[0], str3);
          }
          else
          {
            if ("0x494d".equals(str3))
            {
              str3 = "MID";
              continue;
            }
            if (!"0x4948".equals(str3)) {
              continue;
            }
            str3 = "HIGH";
            continue;
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "1" }))
        {
          String str2 = Build.TYPE;
          if (str2 != null) {
            str1 = responseString(paramArrayOfString[0], str2);
          } else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "3" }))
        {
          Settings.Secure.putInt(this.context.getContentResolver(), "development_settings_enabled", 1);
          str1 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "4" }))
        {
          Settings.Secure.putInt(this.context.getContentResolver(), "development_settings_enabled", 0);
          Settings.Secure.putInt(this.context.getContentResolver(), "adb_enabled", 0);
          str1 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "5" }))
        {
          Settings.Secure.putInt(this.context.getContentResolver(), "development_settings_enabled", 1);
          Settings.Secure.putInt(this.context.getContentResolver(), "adb_enabled", 1);
          str1 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "6" }))
        {
          Settings.Secure.putInt(this.context.getContentResolver(), "development_settings_enabled", 1);
          Settings.Secure.putInt(this.context.getContentResolver(), "adb_enabled", 0);
          str1 = responseOK(paramArrayOfString[0]);
        }
        else
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      Object localObject2 = str1;
    }
  }
  
  public void reqestChangeLevel(final String paramString)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          Thread.sleep(500L);
          AtDebugLvC.this.doRebootNSave(paramString);
          return;
        }
        catch (InterruptedException localInterruptedException)
        {
          for (;;)
          {
            localInterruptedException.printStackTrace();
          }
        }
      }
    }).start();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtDebugLvC
 * JD-Core Version:    0.7.1
 */