package com.sec.factory.app.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import com.sec.factory.app.factorytest.FactoryTestMainAdapter;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.support.FtUtil;

public class UISPDIF
  extends Activity
{
  private static AudioManager mAudioManager;
  private static FactoryTestMainAdapter mFacAdap;
  private static MediaPlayer melody_test;
  private final long BACK_KEY_EVENT_TIMELAG = 2000L;
  private final String TAG = "SPDIF";
  private boolean isCharge = false;
  private boolean isDock = false;
  private int mBatteryStatus = 1;
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      StringBuilder localStringBuilder = new StringBuilder().append("Action :").append(str1).append("  State: ");
      String str2;
      if ("android.intent.action.DOCK_EVENT".equals(str1))
      {
        str2 = "android.intent.extra.DOCK_STATE";
        Log.e("SPDIF", paramAnonymousIntent.getIntExtra(str2, 0));
        Log.i("SPDIF", "isDock: " + UISPDIF.this.isDock + " mIsDigitalHeadsetPlugged: " + UISPDIF.this.mIsDigitalHeadsetPlugged + " isCharge: " + UISPDIF.this.isCharge);
        if (!str1.equals("android.intent.action.BATTERY_CHANGED")) {
          break label249;
        }
        UISPDIF.access$302(UISPDIF.this, paramAnonymousIntent.getIntExtra("status", 1));
        Log.i("SPDIF", "ACTION_BATTERY_CHANGED - mBatteryStatus " + UISPDIF.this.mBatteryStatus);
        if (UISPDIF.this.mBatteryStatus == 2)
        {
          UISPDIF.access$202(UISPDIF.this, true);
          if ((UISPDIF.this.isDock) && (UISPDIF.this.mIsDigitalHeadsetPlugged)) {
            UISPDIF.this.Charge2Pass();
          }
        }
      }
      label249:
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                return;
                if ("android.intent.action.BATTERY_CHANGED".equals(str1))
                {
                  str2 = "status";
                  break;
                }
                str2 = "state";
                break;
                if (!str1.equals("android.intent.action.DOCK_EVENT")) {
                  break label341;
                }
                Log.i("SPDIF", "ACTION_DOCK_EVENT");
                if (paramAnonymousIntent.getIntExtra("android.intent.extra.DOCK_STATE", 0) != 1) {
                  break label322;
                }
                Log.i("SPDIF", "ACTION_DOCK_EVENT: DOCK ON");
                UISPDIF.access$002(UISPDIF.this, true);
              } while ((!UISPDIF.this.isCharge) || (!UISPDIF.this.mIsDigitalHeadsetPlugged));
              UISPDIF.this.Charge2Pass();
              return;
            } while (paramAnonymousIntent.getIntExtra("android.intent.extra.DOCK_STATE", 0) != 0);
            Log.i("SPDIF", "ACTION_DOCK_EVENT: DOCK OFF");
            return;
          } while (!"".equals(str1));
          if (paramAnonymousIntent.getIntExtra("state", 0) != 1) {
            break label406;
          }
          Log.i("SPDIF", "ACTION_DIGITAL_HEADSET_PLUG: switch audio path to SPDIF");
          UISPDIF.access$102(UISPDIF.this, true);
        } while ((!UISPDIF.this.isCharge) || (!UISPDIF.this.isDock));
        UISPDIF.this.Charge2Pass();
        return;
      } while (paramAnonymousIntent.getIntExtra("state", 0) != 0);
      label322:
      label341:
      Log.i("SPDIF", "mIsStart: " + UISPDIF.this.mIsStart);
      label406:
      Log.i("SPDIF", "ACTION_DIGITAL_HEADSET_PLUG: switch audio path to SPK");
    }
  };
  private boolean mIsDigitalHeadsetPlugged = false;
  private boolean mIsStart = false;
  private long mPrevBackKeyEventTime = -1L;
  private TextView mSpeakResultText;
  
  private void Charge2Pass()
  {
    Log.i("SPDIF", "Charge2Pass ");
    this.mSpeakResultText.setText(2131165274);
    this.mSpeakResultText.setTextColor(-16776961);
    FactoryTestManager.notifyDataSetChanged();
  }
  
  private void registerBroadcastReceiver()
  {
    IntentFilter localIntentFilter1 = new IntentFilter();
    localIntentFilter1.addAction("android.intent.action.BATTERY_CHANGED");
    registerReceiver(this.mBroadcastReceiver, localIntentFilter1);
    IntentFilter localIntentFilter2 = new IntentFilter();
    localIntentFilter2.addAction("android.intent.action.DOCK_EVENT");
    registerReceiver(this.mBroadcastReceiver, localIntentFilter2);
    IntentFilter localIntentFilter3 = new IntentFilter();
    localIntentFilter3.addAction("");
    registerReceiver(this.mBroadcastReceiver, localIntentFilter3);
  }
  
  private void speak2testStart()
  {
    Log.i("SPDIF", "speakerTest Start!!");
    if (melody_test == null)
    {
      melody_test = MediaPlayer.create(this, 2131034113);
      mAudioManager.setStreamVolume(3, mAudioManager.getStreamMaxVolume(3), 0);
      melody_test.start();
    }
  }
  
  private void speak2testStop()
  {
    Log.i("SPDIF", "speakerTest Stop");
    if (melody_test != null)
    {
      melody_test.stop();
      melody_test.release();
      melody_test = null;
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    Log.d("SPDIF", "onCreate");
    super.onCreate(paramBundle);
    setContentView(2130903087);
    registerBroadcastReceiver();
    Log.d("SPDIF", " onCreate - mBroadcastReceiver : " + this.mBroadcastReceiver);
    mFacAdap = (FactoryTestMainAdapter)getLastNonConfigurationInstance();
    FtUtil.setSystemKeyBlock(getComponentName(), 3);
    if (mFacAdap == null) {
      mFacAdap = new FactoryTestMainAdapter(this);
    }
    this.mSpeakResultText = ((TextView)findViewById(2131296739));
    mAudioManager = (AudioManager)getSystemService("audio");
    this.isCharge = false;
    this.isDock = false;
    this.mIsDigitalHeadsetPlugged = false;
    this.mIsStart = getIntent().getBooleanExtra("IsStart", false);
    speak2testStart();
  }
  
  protected void onDestroy()
  {
    Log.e("SPDIF", "onDestroy");
    if (this.mBroadcastReceiver != null) {
      unregisterReceiver(this.mBroadcastReceiver);
    }
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    default: 
    case 24: 
    case 25: 
      for (;;)
      {
        this.mPrevBackKeyEventTime = -1L;
        return super.onKeyDown(paramInt, paramKeyEvent);
        Log.i("SPDIF", "KEYCODE_VOLUME_UP");
        continue;
        Log.i("SPDIF", "KEYCODE_VOLUME_DOWN");
      }
    }
    FtUtil.log_d("UIBase", "onKeyDown", "KEYCODE_BACK => Prev : " + this.mPrevBackKeyEventTime + ", Curr : " + paramKeyEvent.getEventTime() + " => Time Lag : " + (paramKeyEvent.getEventTime() - this.mPrevBackKeyEventTime) + " [" + 2000L + "]");
    if (this.mPrevBackKeyEventTime != -1L) {
      if (paramKeyEvent.getEventTime() - this.mPrevBackKeyEventTime < 2000L) {
        finish();
      }
    }
    for (;;)
    {
      return true;
      this.mPrevBackKeyEventTime = paramKeyEvent.getEventTime();
      continue;
      this.mPrevBackKeyEventTime = paramKeyEvent.getEventTime();
    }
  }
  
  public void onPause()
  {
    Log.d("SPDIF", "onPause");
    super.onPause();
    speak2testStop();
    this.isCharge = false;
    this.isDock = false;
    this.mIsDigitalHeadsetPlugged = false;
    this.mIsStart = false;
  }
  
  protected void onResume()
  {
    Log.d("SPDIF", "onResume");
    super.onResume();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UISPDIF
 * JD-Core Version:    0.7.1
 */