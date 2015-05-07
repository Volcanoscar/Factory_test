package com.sec.factory.modules;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;

public class ModuleAudioService
  extends Service
{
  private int audioSource = 5;
  byte[] buffer = null;
  private AudioManager mAudioManager;
  private AudioTrack mAudioTrack = null;
  private final IBinder mBinder = new LocalBinder();
  private int mBufferSize = 0;
  private String mConnectionMode = null;
  private int mCurrentState = 0;
  public Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      }
      do
      {
        return;
        if (ModuleAudioService.this.mAudioTrack != null)
        {
          if (ModuleAudioService.this.mAudioTrack.getState() == 3)
          {
            ModuleAudioService.this.mAudioTrack.flush();
            ModuleAudioService.this.mAudioTrack.stop();
          }
          ModuleAudioService.this.mAudioTrack.release();
          ModuleAudioService.access$002(ModuleAudioService.this, null);
        }
      } while (ModuleAudioService.this.mRecorder == null);
      if (ModuleAudioService.this.mRecorder.getState() == 3) {
        ModuleAudioService.this.mRecorder.stop();
      }
      ModuleAudioService.this.mRecorder.release();
      ModuleAudioService.access$102(ModuleAudioService.this, null);
    }
  };
  private boolean mIsHeadsetPlugged = false;
  private boolean mIsPlaying = false;
  private boolean mIsRecording = false;
  private int mReadBytes = 0;
  private AudioRecord mRecorder = null;
  private Thread mThread = null;
  private int mWrittenBytes = 0;
  
  private void setStreamMusicVolume(int paramInt)
  {
    if (paramInt > 100) {
      paramInt = 100;
    }
    if (paramInt < 0) {
      paramInt = 0;
    }
    FtUtil.log_i("ModuleAudioService", "setStreamMusicVolume", "volme=" + paramInt);
    if (paramInt == 0)
    {
      this.mAudioManager.setStreamVolume(3, 0, 0);
      return;
    }
    this.mAudioManager.setStreamVolume(3, paramInt * this.mAudioManager.getStreamMaxVolume(3) / 100, 0);
  }
  
  private void setStreamMusicVolumeMax()
  {
    setStreamMusicVolume(100);
  }
  
  private void setStreamVolumeMax()
  {
    FtUtil.log_i("ModuleAudioService", "ModuleAudioService", "setVolumeMax");
    setStreamMusicVolumeMax();
  }
  
  public void InitLoopBack()
  {
    FtUtil.log_i("ModuleAudioService", "ModuleAudioService", "InitLoopBack");
    this.mAudioManager = ((AudioManager)getSystemService("audio"));
    this.mConnectionMode = Support.Feature.getString("MODEL_COMMUNICATION_MODE");
    this.mBufferSize = AudioTrack.getMinBufferSize(44100, 3, 2);
    this.mBufferSize = (5 * this.mBufferSize);
    this.buffer = new byte[this.mBufferSize];
    this.mRecorder = new AudioRecord(this.audioSource, 44100, 3, 2, this.mBufferSize);
    this.mAudioTrack = new AudioTrack(3, 44100, 3, 2, this.mBufferSize, 1);
  }
  
  public void StartLoopBack()
  {
    FtUtil.log_i("ModuleAudioService", "ModuleAudioService", "StartLoopBack");
    if ((this.mAudioTrack != null) && (this.mRecorder != null))
    {
      setStreamVolumeMax();
      this.mRecorder.startRecording();
      this.mIsRecording = true;
    }
    label216:
    label226:
    for (;;)
    {
      if (this.mRecorder == null) {}
      do
      {
        return;
        this.mReadBytes = this.mRecorder.read(this.buffer, 0, this.mBufferSize);
        if (-3 == this.mReadBytes) {
          break;
        }
      } while (this.mAudioTrack == null);
      this.mWrittenBytes += this.mAudioTrack.write(this.buffer, 0, this.mReadBytes);
      FtUtil.log_i("ModuleAudioService", "ModuleAudioService", "Audio recorder written bytes = " + this.mWrittenBytes);
      if ((!this.mIsPlaying) && (this.mWrittenBytes > this.mBufferSize / 2))
      {
        if (this.mAudioTrack == null) {
          break label216;
        }
        if (this.mAudioTrack.getPlayState() != 3)
        {
          FtUtil.log_i("ModuleAudioService", "ModuleAudioService", "AudioTrack start playing...");
          this.mAudioTrack.play();
          this.mIsPlaying = true;
        }
      }
      for (;;)
      {
        if (this.mIsRecording) {
          break label226;
        }
        return;
        FtUtil.log_i("ModuleAudioService", "ModuleAudioService", "Audio recording failed!!!");
        break;
        FtUtil.log_i("ModuleAudioService", "ModuleAudioService", "AudioTrack create fail");
      }
    }
  }
  
  public void StopLoopBack()
  {
    FtUtil.log_i("ModuleAudioService", "ModuleAudioService", "StopLoopBack");
    this.mIsRecording = false;
    this.mHandler.sendEmptyMessageDelayed(1, 200L);
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    FtUtil.log_i("ModuleAudioService", "ModuleAudioService", "onBind");
    return this.mBinder;
  }
  
  public void onCreate()
  {
    FtUtil.log_i("ModuleAudioService", "ModuleAudioService", "onDestroy");
  }
  
  public void onDestroy()
  {
    StopLoopBack();
    super.onDestroy();
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    FtUtil.log_i("ModuleAudioService", "ModuleAudioService", "onStartCommand");
    this.mCurrentState = paramIntent.getIntExtra("STATE", 1);
    FtUtil.log_i("ModuleAudioService", "ModuleAudioService", "getState=" + this.mCurrentState);
    InitLoopBack();
    this.mThread = new Thread(new Runnable()
    {
      public void run()
      {
        ModuleAudioService.this.StartLoopBack();
      }
    });
    this.mThread.start();
    return 1;
  }
  
  public boolean onUnbind(Intent paramIntent)
  {
    return super.onUnbind(paramIntent);
  }
  
  public class LocalBinder
    extends Binder
  {
    public LocalBinder() {}
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.ModuleAudioService
 * JD-Core Version:    0.7.1
 */