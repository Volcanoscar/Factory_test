package com.sec.factory.modules;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.XMLDataStorage;
import java.util.HashMap;

public class ModuleObject
{
  protected static Context mContext = null;
  private static HashMap<BroadcastReceiver, Context> mRegistedReceiverMap = new HashMap();
  protected String CLASS_NAME = "ModuleObject";
  
  protected ModuleObject(Context paramContext, String paramString)
  {
    mContext = paramContext.getApplicationContext();
    this.CLASS_NAME = paramString;
    XMLDataStorage.instance().parseXML(paramContext);
  }
  
  protected Context getApplicationContext()
  {
    return mContext;
  }
  
  protected ContentResolver getContentResolver()
  {
    return mContext.getContentResolver();
  }
  
  protected Object getSystemService(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "getSystemService", "service=" + paramString);
    return mContext.getSystemService(paramString);
  }
  
  protected void registerReceiver(BroadcastReceiver paramBroadcastReceiver, IntentFilter paramIntentFilter)
  {
    FtUtil.log_d(this.CLASS_NAME, "registerReceiver", "mContext=" + mContext);
    mContext.registerReceiver(paramBroadcastReceiver, paramIntentFilter);
    mRegistedReceiverMap.put(paramBroadcastReceiver, mContext);
  }
  
  protected void sendBroadcast(Intent paramIntent)
  {
    FtUtil.log_i(this.CLASS_NAME, "sendBroadcast", paramIntent.toString());
    mContext.sendBroadcast(paramIntent);
  }
  
  protected void startActivity(Intent paramIntent)
  {
    FtUtil.log_i(this.CLASS_NAME, "startActivity", "activity=" + paramIntent);
    mContext.startActivity(paramIntent);
  }
  
  protected void startService(Intent paramIntent)
  {
    FtUtil.log_i(this.CLASS_NAME, "startService", "service=" + paramIntent);
    mContext.startService(paramIntent);
  }
  
  protected void stopService(Intent paramIntent)
  {
    FtUtil.log_i(this.CLASS_NAME, "stopService", "service=" + paramIntent);
    mContext.stopService(paramIntent);
  }
  
  protected void unregisterReceiver(BroadcastReceiver paramBroadcastReceiver)
  {
    if (mRegistedReceiverMap.containsKey(paramBroadcastReceiver))
    {
      Context localContext = (Context)mRegistedReceiverMap.get(paramBroadcastReceiver);
      FtUtil.log_d(this.CLASS_NAME, "unregisterReceiver", "mContext=" + localContext);
      localContext.unregisterReceiver(paramBroadcastReceiver);
      mRegistedReceiverMap.remove(paramBroadcastReceiver);
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.ModuleObject
 * JD-Core Version:    0.7.1
 */