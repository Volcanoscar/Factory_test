package com.sec.factory.aporiented;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NvIntentHandler
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Log.i("NvIntentHandler", "in onReceive, intent action: " + paramIntent.getAction());
    if (paramIntent.getAction().equals("com.sec.android.app.atsupport.readNV")) {
      Log.d("NvIntentHandler", "Intent incoming - readNV");
    }
    for (;;)
    {
      return;
      if (paramIntent.getAction().equals("com.sec.android.app.atsupport.writeNV"))
      {
        Log.d("NvIntentHandler", "Intent incoming - writeNV");
        return;
      }
      if (paramIntent.getAction().equals("com.android.samsungtest.RequestFactoryTestNvView"))
      {
        Log.d("NvIntentHandler", "Intent incoming - com.android.samsungtest.RequestFactoryTestNvView");
        String str6 = NvHandler.getInstance().getFullTestNv();
        String str7 = "dummy0";
        int m = 1;
        if (m < 41)
        {
          StringBuilder localStringBuilder2 = new StringBuilder().append(str7);
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = Integer.valueOf(m);
          str7 = String.format("%02X", arrayOfObject2);
          String str8 = str6.substring(2 + 3 * (m - 1), 3 + 3 * (m - 1));
          if (str8.equalsIgnoreCase("P")) {
            str7 = str7 + "50";
          }
          for (;;)
          {
            m++;
            break;
            if (str8.equalsIgnoreCase("F")) {
              str7 = str7 + "46";
            } else if (str8.equalsIgnoreCase("N")) {
              str7 = str7 + "45";
            } else if (str8.equalsIgnoreCase("E")) {
              str7 = str7 + "45";
            }
          }
        }
        Log.d("NvIntentHandler", "Return value : " + str7);
        if (str7 != null)
        {
          Intent localIntent2 = new Intent("com.android.samsungtest.RilOmissionCommand");
          localIntent2.putExtra("COMMAND", str7);
          paramContext.sendBroadcast(localIntent2);
          Log.d("NvIntentHandler", "Intent sent - com.android.samsungtest.RilOmissionCommand");
        }
      }
      else if (paramIntent.getAction().equals("com.android.samsungtest.RequestFactoryTestHistoryView"))
      {
        Log.d("NvIntentHandler", "Intent incoming - com.android.samsungtest.RequestFactoryTestHistoryView");
        String str3 = NvHandler.getInstance().getFullHistNv();
        String str4 = "dummy0";
        int k = 0;
        if (k < str3.length())
        {
          StringBuilder localStringBuilder1 = new StringBuilder().append(str4);
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = Integer.valueOf(Integer.parseInt(str3.substring(k, k + 2)));
          str4 = String.format("%02X", arrayOfObject1);
          String str5 = str3.substring(k + 2, k + 3);
          if (str5.equalsIgnoreCase("P")) {
            str4 = str4 + "50";
          }
          for (;;)
          {
            k += 3;
            break;
            if (str5.equalsIgnoreCase("F")) {
              str4 = str4 + "46";
            } else if (str5.equalsIgnoreCase("N")) {
              str4 = str4 + "45";
            } else if (str5.equalsIgnoreCase("E")) {
              str4 = str4 + "45";
            }
          }
        }
        Log.d("NvIntentHandler", "Return value : " + str4);
        if (str4 != null)
        {
          Intent localIntent1 = new Intent("com.android.samsungtest.RilOmissionCommand");
          localIntent1.putExtra("COMMAND", str4);
          paramContext.sendBroadcast(localIntent1);
          Log.d("NvIntentHandler", "Intent sent - com.android.samsungtest.RilOmissionCommand");
        }
      }
      else if (paramIntent.getAction().equals("com.android.samsungtest.FactoryTestStatus"))
      {
        Log.d("NvIntentHandler", "Intent incoming - com.android.samsungtest.FactoryTestStatus");
        int i = paramIntent.getCharExtra("ITEM_ID", '\000');
        int j = paramIntent.getCharExtra("RESULT", '\000');
        String str1 = String.valueOf(i);
        if (j == 80) {}
        for (String str2 = "P"; str1 != null; str2 = "F")
        {
          boolean bool = NvHandler.getInstance().setTestNv(str1, str2);
          Log.d("NvIntentHandler", "setTestNV(" + str1 + ", " + str2 + ") : " + bool);
          return;
        }
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.NvIntentHandler
 * JD-Core Version:    0.7.1
 */