package com.sec.factory.modules;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;
import java.io.IOException;

public class ModuleAudio
  extends ModuleObject
{
  private static ModuleAudio mInstance = null;
  private final String[] AUDIO_PATH = { "spk", "rcv", "ear", "hdmi", "off" };
  private final String[] LOOPBACK_PATH = { "mic_rcv;", "mic_spk;", "mic3_spk;", "mic_ear;", "mic2_spk;", "ear_ear;", "dualmic_rcv;" };
  private final String[] LOOPBACK_TYPE = { "packet;", "pcm;", "realtime", "codec" };
  private int earKeyState = 0;
  private AudioManager mAudioManager;
  private String mConnectionMode;
  private boolean mEarphonePlugged = false;
  private boolean mIsDoingLoopback = false;
  private int mLoopbackPreviousPath = -1;
  private int mLoopbackTestPath = -1;
  private MediaPlayer mMediaPlayer;
  private boolean mPacketLoop = false;
  ModuleCommon moduleCommon = ModuleCommon.instance(mContext);
  
  private ModuleAudio(Context paramContext)
  {
    super(paramContext, "ModuleAudio");
    FtUtil.log_i(this.CLASS_NAME, "ModuleAudio", "Create ModuleAudio");
    this.mAudioManager = ((AudioManager)getSystemService("audio"));
    this.mConnectionMode = Support.Feature.getString("MODEL_COMMUNICATION_MODE");
    this.mPacketLoop = Support.Feature.getBoolean("SUPPORT_PACKET_LOOPBACK");
  }
  
  public static ModuleAudio instance(Context paramContext)
  {
    if (mInstance == null) {
      mInstance = new ModuleAudio(paramContext);
    }
    return mInstance;
  }
  
  public static boolean isSupportSecondMicTest()
  {
    return Support.Feature.getBoolean("SUPPORT_SECOND_MIC_TEST", false);
  }
  
  private void release()
  {
    FtUtil.log_i(this.CLASS_NAME, "release", "release ModuleAudio");
    if (this.mMediaPlayer != null)
    {
      this.mMediaPlayer.release();
      this.mMediaPlayer = null;
    }
  }
  
  protected void finalize()
    throws Throwable
  {
    release();
    super.finalize();
  }
  
  public int getCurrentLoopbackPath()
  {
    return this.mLoopbackTestPath;
  }
  
  public int getEarKeyState()
  {
    return this.earKeyState;
  }
  
  public int getMicCount()
  {
    return Support.Feature.getInt("MIC_COUNT");
  }
  
  public int getPreviousLoopbackPath()
  {
    return this.mLoopbackPreviousPath;
  }
  
  public int getStreamMusicVolume()
  {
    return this.mAudioManager.getStreamVolume(3);
  }
  
  public int getStreamMusicVolumeIndex()
  {
    return this.mAudioManager.getStreamVolume(3);
  }
  
  public boolean isConnectionModeNone()
  {
    FtUtil.log_i(this.CLASS_NAME, "isConnectionModeNone", "mConnectionMode = " + this.mConnectionMode);
    if ("none".equals(this.mConnectionMode)) {}
    for (boolean bool = true;; bool = false)
    {
      if ((bool == true) && (this.mPacketLoop)) {
        bool = false;
      }
      return bool;
    }
  }
  
  public boolean isDoingLoopback()
  {
    return this.mIsDoingLoopback;
  }
  
  public boolean isEarphonePlugged()
  {
    String str = Support.Kernel.read("EARJACK_PLUGGED");
    if (str != null) {
      if (!"1".equals(str)) {
        break label31;
      }
    }
    label31:
    for (boolean bool = true;; bool = false)
    {
      this.mEarphonePlugged = bool;
      return this.mEarphonePlugged;
    }
  }
  
  public boolean isPlayingSound()
  {
    try
    {
      if (this.mMediaPlayer != null)
      {
        boolean bool = this.mMediaPlayer.isPlaying();
        return bool;
      }
      return false;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      FtUtil.log_e(localIllegalStateException);
    }
    return false;
  }
  
  public void playMedia(int paramInt, boolean paramBoolean)
  {
    playMedia(paramInt, paramBoolean, 2);
  }
  
  public void playMedia(int paramInt1, boolean paramBoolean, int paramInt2)
  {
    FtUtil.log_i(this.CLASS_NAME, "playMedia", "resId=" + paramInt1 + ", isLoop=" + paramBoolean);
    release();
    this.mMediaPlayer = MediaPlayer.create(getApplicationContext(), paramInt1);
    this.mMediaPlayer.setLooping(paramBoolean);
    this.mMediaPlayer.start();
  }
  
  public void playMedia(String paramString, boolean paramBoolean)
    throws IOException
  {
    playMedia(paramString, paramBoolean, 2);
  }
  
  public void playMedia(String paramString, boolean paramBoolean, int paramInt)
    throws IOException
  {
    FtUtil.log_i(this.CLASS_NAME, "playMedia", "dataSource=" + paramString + ", isLoop=" + paramBoolean);
    release();
    this.mAudioManager.setStreamVolume(3, this.mAudioManager.getStreamMaxVolume(3), 0);
    this.mMediaPlayer = new MediaPlayer();
    this.mMediaPlayer.reset();
    this.mMediaPlayer.setLooping(paramBoolean);
    this.mMediaPlayer.setDataSource(paramString);
    this.mMediaPlayer.prepare();
    this.mMediaPlayer.start();
  }
  
  public void sendToAudioManagerFTAOnOff(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      FtUtil.log_i(this.CLASS_NAME, "sendToAudioManagerFTAOnOff", " com.sec.factory.app.factorytest.FTA_OFF");
      sendBroadcast(new Intent("com.sec.factory.app.factorytest.FTA_OFF"));
      return;
    }
    if (paramBoolean == true)
    {
      FtUtil.log_i(this.CLASS_NAME, "sendToAudioManagerFTAOnOff", "com.sec.factory.app.factorytest.FTA_ON");
      sendBroadcast(new Intent("com.sec.factory.app.factorytest.FTA_ON"));
      return;
    }
    FtUtil.log_i(this.CLASS_NAME, "sendToAudioManagerFTAOnOff", "Invalid parameter");
  }
  
  public void setAudioPath(int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "setMode", "setAudioPath : factory_test_route=" + this.AUDIO_PATH[paramInt]);
    this.mAudioManager.setParameters("factory_test_route=" + this.AUDIO_PATH[paramInt]);
  }
  
  public void setEarKeyState(int paramInt)
  {
    this.earKeyState = paramInt;
  }
  
  public void setLoopbackPath(int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "setLoopbackPath", "Change Loopback path=" + paramInt);
    setStreamMusicVolumeMax();
    this.mAudioManager.setParameters("factory_test_path=" + this.LOOPBACK_PATH[paramInt]);
    this.mLoopbackPreviousPath = this.mLoopbackTestPath;
    this.mLoopbackTestPath = paramInt;
  }
  
  public void setMode(int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "setMode", "mode=" + paramInt);
    this.mAudioManager.setMode(paramInt);
  }
  
  public void setPhoneType(String paramString)
  {
    FtUtil.log_i(this.CLASS_NAME, "setPhoneType", "phone type=" + paramString);
    this.mAudioManager.setParameters("phone_type=" + paramString);
  }
  
  public void setStreamMusicVolume(int paramInt)
  {
    if (paramInt > 100) {
      paramInt = 100;
    }
    if (paramInt < 0) {
      paramInt = 0;
    }
    FtUtil.log_i(this.CLASS_NAME, "setStreamMusicVolume", "volme=" + paramInt);
    if (!this.moduleCommon.isVoiceCapable()) {
      this.mAudioManager.setRingerMode(2);
    }
    if (paramInt == 0)
    {
      this.mAudioManager.setStreamVolume(3, 0, 0);
      return;
    }
    this.mAudioManager.setStreamVolume(3, paramInt * this.mAudioManager.getStreamMaxVolume(3) / 100, 0);
  }
  
  public void setStreamMusicVolumeIndex(int paramInt)
  {
    if (paramInt > 15) {
      paramInt = 15;
    }
    if (paramInt < 0) {
      paramInt = 0;
    }
    FtUtil.log_i(this.CLASS_NAME, "setStreamMusicVolumeIndex", "volme Index =" + paramInt);
    if (!this.moduleCommon.isVoiceCapable()) {
      this.mAudioManager.setRingerMode(2);
    }
    if (paramInt == 0)
    {
      this.mAudioManager.setStreamVolume(3, 0, 0);
      return;
    }
    this.mAudioManager.setStreamVolume(3, paramInt, 0);
  }
  
  public void setStreamMusicVolumeMax()
  {
    setStreamMusicVolume(100);
  }
  
  public void setStreamMusicVolumeMaxIndex()
  {
    setStreamMusicVolumeIndex(15);
  }
  
  public void setStreamMusicVolumeMin()
  {
    setStreamMusicVolume(30);
  }
  
  public void setStreamMusicVolumeZero()
  {
    setStreamMusicVolume(0);
  }
  
  public void setStreamVoiceVolume(int paramInt)
  {
    if (paramInt > 100) {
      paramInt = 100;
    }
    if (paramInt < 0) {
      paramInt = 0;
    }
    FtUtil.log_i(this.CLASS_NAME, "setStreamVoiceVolume", "volme=" + paramInt);
    if (paramInt == 0)
    {
      this.mAudioManager.setStreamVolume(0, 0, 0);
      return;
    }
    this.mAudioManager.setStreamVolume(0, paramInt * this.mAudioManager.getStreamMaxVolume(0) / 100, 0);
  }
  
  public void setStreamVoiceVolumeMax()
  {
    setStreamVoiceVolume(100);
  }
  
  public void startLoopback(int paramInt1, int paramInt2)
  {
    FtUtil.log_i(this.CLASS_NAME, "startLoopback", "Loopback start path=" + paramInt1 + ", type" + paramInt2);
    setStreamMusicVolumeMax();
    this.mAudioManager.setParameters("factory_test_loopback=on;factory_test_path=" + this.LOOPBACK_PATH[paramInt1] + "factory_test_type=" + this.LOOPBACK_TYPE[paramInt2]);
    this.mIsDoingLoopback = true;
    this.mLoopbackTestPath = paramInt1;
    if ("MSM7227A".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"))) {
      setStreamVoiceVolumeMax();
    }
    if ((!this.moduleCommon.isVoiceCapable()) && (paramInt2 != 3))
    {
      FtUtil.log_i(this.CLASS_NAME, "startLoopback", "none");
      new Thread(new Runnable()
      {
        public void run()
        {
          Intent localIntent = new Intent();
          localIntent.setClass(ModuleAudio.this.getApplicationContext(), ModuleAudioService.class);
          ModuleAudio.this.startService(localIntent);
        }
      }).start();
    }
  }
  
  public void stopLoopback()
  {
    FtUtil.log_i(this.CLASS_NAME, "startLoopback", "Loopback stop");
    if (isSupportSecondMicTest()) {
      this.mAudioManager.setParameters("dualmic_enabled=false");
    }
    this.mAudioManager.setParameters("factory_test_loopback=off");
    this.mIsDoingLoopback = false;
    this.mLoopbackTestPath = -1;
    this.mLoopbackPreviousPath = -1;
    if (!this.moduleCommon.isVoiceCapable())
    {
      FtUtil.log_i(this.CLASS_NAME, "stopLoopback", "none");
      Intent localIntent = new Intent();
      localIntent.setClass(getApplicationContext(), ModuleAudioService.class);
      stopService(localIntent);
    }
  }
  
  public void stopMedia()
  {
    FtUtil.log_i(this.CLASS_NAME, "stopMedia", "Stop media");
    if (this.mMediaPlayer != null) {
      this.mMediaPlayer.stop();
    }
    try
    {
      FtUtil.log_d(this.CLASS_NAME, "stopMedia : delay", " : 10ms");
      Thread.sleep(10L);
      release();
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
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.ModuleAudio
 * JD-Core Version:    0.7.1
 */