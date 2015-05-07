package com.sec.factory.aporiented.athandler;

import android.content.Context;
import android.os.SystemClock;
import java.util.Calendar;

public class AtRtcctest
  extends AtCommandHandler
{
  public AtRtcctest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "RTCCTEST";
    this.CLASS_NAME = "AtRtcctest";
    this.NUM_ARGS = 8;
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
        if (checkArgu(paramArrayOfString, new String[] { "1" }))
        {
          Calendar localCalendar2 = Calendar.getInstance();
          String str2 = "" + localCalendar2.get(1);
          String str3;
          if (localCalendar2.get(2) < 9)
          {
            str3 = "0" + (1 + localCalendar2.get(2));
            if (localCalendar2.get(5) < 10)
            {
              str4 = "0" + localCalendar2.get(5);
              if (localCalendar2.get(9) != 0) {
                continue;
              }
              str5 = "" + localCalendar2.get(10);
              if (1 == str5.length()) {
                str5 = "0" + str5;
              }
              if (localCalendar2.get(12) >= 10) {
                continue;
              }
              str6 = "0" + localCalendar2.get(12);
              if (localCalendar2.get(13) >= 10) {
                continue;
              }
              str7 = "0" + localCalendar2.get(13);
              if (-2 + localCalendar2.get(7) >= 0) {
                continue;
              }
              str8 = "6";
              str1 = responseString(paramArrayOfString[0], str2 + str3 + str4 + str5 + str6 + str7 + str8);
              break label777;
            }
          }
          else
          {
            str3 = "" + (1 + localCalendar2.get(2));
            continue;
          }
          String str4 = "" + localCalendar2.get(5);
          continue;
          String str5 = "" + (12 + localCalendar2.get(10));
          continue;
          String str6 = "" + localCalendar2.get(12);
          continue;
          String str7 = "" + localCalendar2.get(13);
          continue;
          String str8 = "" + (-2 + localCalendar2.get(7));
          continue;
        }
        else if (checkArgu(paramArrayOfString, new String[] { "2" }))
        {
          Calendar localCalendar1 = Calendar.getInstance();
          int i = Integer.valueOf(paramArrayOfString[1]).intValue();
          int j = -1 + Integer.valueOf(paramArrayOfString[2]).intValue();
          int k = Integer.valueOf(paramArrayOfString[3]).intValue();
          int m = Integer.valueOf(paramArrayOfString[4]).intValue();
          int n = 0;
          if (m > 12)
          {
            m -= 12;
            n = 1;
          }
          int i1 = Integer.valueOf(paramArrayOfString[5]).intValue();
          int i2 = Integer.valueOf(paramArrayOfString[6]).intValue();
          if (2 + Integer.valueOf(paramArrayOfString[7]).intValue() == 8) {}
          localCalendar1.set(1, i);
          localCalendar1.set(2, j);
          localCalendar1.set(5, k);
          localCalendar1.set(10, m);
          localCalendar1.set(12, i1);
          localCalendar1.set(13, i2);
          if (n == 0)
          {
            localCalendar1.set(9, 0);
            SystemClock.setCurrentTimeMillis(localCalendar1.getTimeInMillis());
            str1 = responseOK(paramArrayOfString[0]);
          }
          else
          {
            localCalendar1.set(9, 1);
            continue;
          }
        }
        else
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      label777:
      Object localObject2 = str1;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtRtcctest
 * JD-Core Version:    0.7.1
 */