package com.sec.factory.entry;

import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.sec.factory.support.FtUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DisplayNotiBar
{
  public static void clearNotification(Context paramContext)
  {
    ((NotificationManager)paramContext.getSystemService("notification"));
    ((NotificationManager)paramContext.getSystemService("notification")).cancel(8);
  }
  
  public static void createNotification(Context paramContext)
  {
    NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
    FtUtil.log_i("DisplayNotiBar", "createNotification", null);
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, new Intent("com.sec.android.app.factoryTest.NOTI_CLEAR"), 134217728);
    Notification.Builder localBuilder = new Notification.Builder(paramContext);
    localBuilder.setOngoing(false);
    localBuilder.setSmallIcon(17301658);
    localBuilder.setAutoCancel(true);
    localBuilder.setTicker("Reset completed" + "  (" + parseDate() + ")");
    localBuilder.setContentTitle("Reset completed");
    localBuilder.setContentIntent(localPendingIntent);
    FtUtil.log_i("DisplayNotiBar", "createNotification", "-3");
    localNotificationManager.notify(8, localBuilder.getNotification());
  }
  
  public static String parseDate()
  {
    long l = System.currentTimeMillis();
    return new SimpleDateFormat("HH:mm:ss").format(new Date(l));
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.entry.DisplayNotiBar
 * JD-Core Version:    0.7.1
 */