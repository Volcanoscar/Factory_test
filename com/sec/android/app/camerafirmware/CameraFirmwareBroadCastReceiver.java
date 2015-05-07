package com.sec.android.app.camerafirmware;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;

public class CameraFirmwareBroadCastReceiver
  extends BroadcastReceiver
{
  private String GenerateCameraFirmwarePackageName(String paramString)
  {
    str1 = "com.sec.android.app.camerafirmware";
    if (paramString == null) {}
    try
    {
      Log.e("CameraFirmware_broadcast", "vendor string is null!");
      return str1.concat("_unknown");
    }
    catch (Exception localException)
    {
      String str2 = str1.concat("_unknown");
      Log.e("CameraFirmware_broadcast", "[Exception]" + localException);
      localException.printStackTrace();
      return str2;
    }
    str1 = str1.concat("_" + paramString);
    Log.i("CameraFirmware_broadcast", "CameraFW PackageName : [" + str1 + "]");
    return str1;
  }
  
  private String ParsingCameraFirmwareVendor(String paramString)
  {
    String str = null;
    if (paramString == null) {
      return null;
    }
    try
    {
      str = paramString.substring(0, 2);
      Log.i("CameraFirmware_broadcast", "Camera Infomation - Vendor : " + str);
      return str;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Log.e("CameraFirmware_broadcast", "[Exception]" + localException);
        localException.printStackTrace();
      }
    }
  }
  
  private String ParsingCameraFirmwareVersion(String paramString)
  {
    String str1 = null;
    if (paramString == null) {}
    try
    {
      Log.e("CameraFirmware_broadcast", "oemcamerafirmware is NULL");
      return null;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        String str2;
        String str3;
        String str4;
        int i;
        int j;
        Object[] arrayOfObject;
        Log.e("CameraFirmware_broadcast", "[Exception]" + localException);
        localException.printStackTrace();
      }
    }
    str2 = paramString.substring(2, 3);
    str3 = paramString.substring(3, 4);
    str4 = paramString.substring(4, 6);
    Log.i("CameraFirmware_broadcast", "Camera Infomation");
    Log.i("CameraFirmware_broadcast", "Year : " + str2);
    Log.i("CameraFirmware_broadcast", "Month : " + str3);
    Log.i("CameraFirmware_broadcast", "Release : " + str4);
    i = checkCameraFWYear(str2);
    j = checkCameraFWMonth(str3);
    arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(i);
    arrayOfObject[1] = Integer.valueOf(j);
    arrayOfObject[2] = str4;
    str1 = String.format("%d.%d.%s", arrayOfObject);
    Log.v("CameraFirmware_broadcast", "ParsingCameraFW RESULT : " + str1);
    return str1;
  }
  
  private int checkCameraFWMonth(String paramString)
  {
    String str = paramString.toUpperCase();
    int i;
    if (str.equals("A")) {
      i = 1;
    }
    for (;;)
    {
      Log.d("CameraFirmware_broadcast", "checkCameraFWMonth Return :" + i);
      return i;
      if (str.equals("B")) {
        i = 2;
      } else if (str.equals("C")) {
        i = 3;
      } else if (str.equals("D")) {
        i = 4;
      } else if (str.equals("E")) {
        i = 5;
      } else if (str.equals("F")) {
        i = 6;
      } else if (str.equals("G")) {
        i = 7;
      } else if (str.equals("H")) {
        i = 8;
      } else if (str.equals("I")) {
        i = 9;
      } else if (str.equals("J")) {
        i = 10;
      } else if (str.equals("K")) {
        i = 11;
      } else if (str.equals("L")) {
        i = 12;
      } else {
        i = 0;
      }
    }
  }
  
  private int checkCameraFWYear(String paramString)
  {
    String str = paramString.toUpperCase();
    int i;
    if (str.equals("C")) {
      i = 9;
    }
    for (;;)
    {
      Log.d("CameraFirmware_broadcast", "checkCameraFWYear Return :" + i);
      return i;
      if (str.equals("D"))
      {
        i = 10;
      }
      else if (str.equals("E"))
      {
        i = 11;
      }
      else if (str.equals("F"))
      {
        i = 12;
      }
      else if (str.equals("G"))
      {
        i = 13;
      }
      else if (str.equals("H"))
      {
        i = 14;
      }
      else if (str.equals("I"))
      {
        i = 15;
      }
      else if (str.equals("J"))
      {
        i = 16;
      }
      else if (str.equals("K"))
      {
        i = 17;
      }
      else if (str.equals("L"))
      {
        i = 18;
      }
      else if (str.equals("M"))
      {
        i = 19;
      }
      else if (str.equals("N"))
      {
        i = 20;
      }
      else if (str.equals("O"))
      {
        i = 21;
      }
      else if (str.equals("P"))
      {
        i = 22;
      }
      else if (str.equals("Q"))
      {
        i = 23;
      }
      else if (str.equals("R"))
      {
        i = 24;
      }
      else if (str.equals("S"))
      {
        i = 25;
      }
      else if (str.equals("T"))
      {
        i = 26;
      }
      else if (str.equals("U"))
      {
        i = 27;
      }
      else if (str.equals("V"))
      {
        i = 28;
      }
      else if (str.equals("X"))
      {
        i = 29;
      }
      else if (str.equals("Y"))
      {
        i = 30;
      }
      else
      {
        boolean bool = str.equals("Z");
        i = 0;
        if (bool) {
          i = 31;
        }
      }
    }
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Intent localIntent1;
    String str1;
    if (paramIntent.getAction().equals("com.sec.android.app.samsungapps.una.COMPLETED_FILL_DATA_IN_DB"))
    {
      Log.i("CameraFirmware_broadcast", "Received UNA Prepared Database");
      String str2 = PhoneFactory.getDefaultPhone().getDeviceId();
      if ((str2 != null) && (str2.equals("004999010640000"))) {
        Log.i("CameraFirmware_broadcast", "The Default U1's IMEI is detected. UNASERVICE_firmware_version_check is ignored...");
      }
    }
    else if (paramIntent.getAction().equals("android.provider.Telephony.SECRET_CODE"))
    {
      localIntent1 = new Intent("android.intent.action.MAIN");
      str1 = paramIntent.getData().getHost();
      if (!str1.equals("34971539")) {
        break label335;
      }
      Log.w("CameraFirmware_broadcast", "Secret[34971539] Eng mode will be launched");
      localIntent1.setClass(paramContext, CameraFirmware.class);
    }
    for (;;)
    {
      for (;;)
      {
        localIntent1.setFlags(268435456);
        paramContext.startActivity(localIntent1);
        return;
        try
        {
          FirmwareFileMgr localFirmwareFileMgr = new FirmwareFileMgr();
          Intent localIntent2 = new Intent("com.sec.android.app.samsungapps.una.REQUEST_INSERT_PACKAGE_INFO_DATA", Uri.parse("request_for_samsungapps_una://com.sec.android.app.camerafirmware"));
          Bundle localBundle = new Bundle();
          String str3 = localFirmwareFileMgr.getCamFWVer();
          Log.i("CameraFirmware_broadcast", "Camera FW : " + str3);
          String str4 = ParsingCameraFirmwareVendor(str3);
          String str5 = ParsingCameraFirmwareVersion(str3);
          if (str5 == null)
          {
            Log.w("CameraFirmware_broadcast", "ParsingCameraFirmwareVersion returned NULL value. So, CameraFWVersion set 0.0.0");
            str5 = "0.0.0";
          }
          localBundle.putString("AppID", GenerateCameraFirmwarePackageName(str4));
          localBundle.putString("Version", str5);
          localBundle.putString("AppContentType", "3");
          localBundle.putString("AppLoadType", "0");
          localIntent2.putExtras(localBundle);
          paramContext.sendBroadcast(localIntent2);
          Log.i("CameraFirmware_broadcast", "Send data to UNA");
        }
        catch (Exception localException)
        {
          Log.e("CameraFirmware_broadcast", "Exception :" + localException);
          localException.printStackTrace();
        }
      }
      break;
      label335:
      if (str1.equals("7412365"))
      {
        Log.w("CameraFirmware_broadcast", "SecretCode[7412365] Service mode will be launched");
        localIntent1.setClass(paramContext, CameraFirmware_user.class);
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.camerafirmware.CameraFirmwareBroadCastReceiver
 * JD-Core Version:    0.7.1
 */