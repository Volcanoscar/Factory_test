package com.sec.factory.app.factorytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import com.sec.factory.support.FtUtil;

public class MediaButtonIntentReceiver
  extends BroadcastReceiver
{
  private static int mStatusEarKey = 0;
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str = paramIntent.getAction();
    FtUtil.log_d("MediaButtonIntentReceiver", "onReceive", "onReceive action=" + str);
    KeyEvent localKeyEvent;
    if ("android.intent.action.MEDIA_BUTTON".equals(str))
    {
      FtUtil.log_d("MediaButtonIntentReceiver", "MediaButtonIntentReceiver", "ACTION_MEDIA_BUTTON event + mStatusEarKey= " + mStatusEarKey);
      localKeyEvent = (KeyEvent)paramIntent.getParcelableExtra("android.intent.extra.KEY_EVENT");
      if ((localKeyEvent.getAction() != 0) || (mStatusEarKey != 0)) {
        break label120;
      }
      FtUtil.log_d("MediaButtonIntentReceiver", "MediaButtonIntentReceiver", "ACTION_MEDIA_BUTTON clicked");
      mStatusEarKey = 1;
      paramContext.sendBroadcast(new Intent("com.sec.factory.app.factorytest.MEDIA_BUTTON_PRESSED"));
    }
    label120:
    while (1 != localKeyEvent.getAction()) {
      return;
    }
    FtUtil.log_d("MediaButtonIntentReceiver", "MediaButtonIntentReceiver", "ACTION_MEDIA_BUTTON released");
    mStatusEarKey = 0;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.MediaButtonIntentReceiver
 * JD-Core Version:    0.7.1
 */