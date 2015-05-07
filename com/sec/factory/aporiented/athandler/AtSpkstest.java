package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.modules.ModuleCommon;

public class AtSpkstest
  extends AtCommandHandler
{
  private final int SLEEP_TIME = 1000;
  Context mContext = this.context.getApplicationContext();
  ModuleCommon moduleCommon = ModuleCommon.instance(this.mContext);
  
  public AtSpkstest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "SPKSTEST";
    this.CLASS_NAME = "AtSpkstest";
    this.NUM_ARGS = 3;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      String str;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (mModuleAudio.isPlayingSound())
        {
          mModuleAudio.stopMedia();
          mModuleCommon.sleepThread(200);
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
        {
          if (!this.moduleCommon.isVoiceCapable())
          {
            mModuleAudio.setStreamMusicVolume(7);
            mModuleAudio.playMedia(2131034119, true);
            mModuleCommon.sleepThread(500);
            mModuleAudio.setAudioPath(0);
            mModuleAudio.setStreamMusicVolumeMax();
            str = responseStringNoArg(paramArrayOfString[0]);
            break label934;
          }
          mModuleAudio.setStreamMusicVolumeZero();
          continue;
        }
      }
      finally {}
      tmp150_147[0] = "0";
      String[] tmp155_150 = tmp150_147;
      tmp155_150[1] = "0";
      String[] tmp160_155 = tmp155_150;
      tmp160_155[2] = "1";
      if (checkArgu(paramArrayOfString, tmp160_155))
      {
        if (!this.moduleCommon.isVoiceCapable()) {
          mModuleAudio.setStreamMusicVolume(7);
        }
        for (;;)
        {
          mModuleAudio.playMedia(2131034119, true);
          mModuleCommon.sleepThread(500);
          mModuleAudio.setAudioPath(0);
          mModuleAudio.setStreamMusicVolumeMin();
          str = responseStringNoArg(paramArrayOfString[0]);
          break;
          mModuleAudio.setStreamMusicVolumeZero();
        }
      }
      if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0" }))
      {
        if (!this.moduleCommon.isVoiceCapable()) {
          mModuleAudio.setStreamMusicVolume(7);
        }
        for (;;)
        {
          mModuleAudio.playMedia(2131034120, true);
          mModuleCommon.sleepThread(500);
          mModuleAudio.setAudioPath(0);
          mModuleAudio.setStreamMusicVolumeMax();
          str = responseStringNoArg(paramArrayOfString[0]);
          break;
          mModuleAudio.setStreamMusicVolumeZero();
        }
      }
      if (checkArgu(paramArrayOfString, new String[] { "0", "1", "1" }))
      {
        if (!this.moduleCommon.isVoiceCapable()) {
          mModuleAudio.setStreamMusicVolume(7);
        }
        for (;;)
        {
          mModuleAudio.playMedia(2131034120, true);
          mModuleCommon.sleepThread(500);
          mModuleAudio.setAudioPath(0);
          mModuleAudio.setStreamMusicVolumeMin();
          str = responseStringNoArg(paramArrayOfString[0]);
          break;
          mModuleAudio.setStreamMusicVolumeZero();
        }
      }
      if (checkArgu(paramArrayOfString, new String[] { "0", "2", "0" }))
      {
        if (!this.moduleCommon.isVoiceCapable()) {
          mModuleAudio.setStreamMusicVolume(7);
        }
        for (;;)
        {
          mModuleAudio.playMedia(2131034121, true);
          mModuleCommon.sleepThread(500);
          mModuleAudio.setAudioPath(0);
          mModuleAudio.setStreamMusicVolumeMax();
          str = responseStringNoArg(paramArrayOfString[0]);
          break;
          mModuleAudio.setStreamMusicVolumeZero();
        }
      }
      if (checkArgu(paramArrayOfString, new String[] { "0", "2", "1" }))
      {
        if (!this.moduleCommon.isVoiceCapable()) {
          mModuleAudio.setStreamMusicVolume(7);
        }
        for (;;)
        {
          mModuleAudio.playMedia(2131034121, true);
          mModuleCommon.sleepThread(500);
          mModuleAudio.setAudioPath(0);
          mModuleAudio.setStreamMusicVolumeMin();
          str = responseStringNoArg(paramArrayOfString[0]);
          break;
          mModuleAudio.setStreamMusicVolumeZero();
        }
      }
      if (checkArgu(paramArrayOfString, new String[] { "0", "3", "0" }))
      {
        if (!this.moduleCommon.isVoiceCapable()) {
          mModuleAudio.setStreamMusicVolume(7);
        }
        for (;;)
        {
          mModuleAudio.playMedia(2131034119, true);
          mModuleCommon.sleepThread(500);
          mModuleAudio.setAudioPath(1);
          mModuleAudio.setStreamMusicVolumeMax();
          str = responseStringNoArg(paramArrayOfString[0]);
          break;
          mModuleAudio.setStreamMusicVolumeZero();
        }
      }
      if (checkArgu(paramArrayOfString, new String[] { "0", "3", "1" }))
      {
        if (!this.moduleCommon.isVoiceCapable()) {
          mModuleAudio.setStreamMusicVolume(7);
        }
        for (;;)
        {
          mModuleAudio.playMedia(2131034119, true);
          mModuleCommon.sleepThread(500);
          mModuleAudio.setAudioPath(1);
          mModuleAudio.setStreamMusicVolumeMin();
          str = responseStringNoArg(paramArrayOfString[0]);
          break;
          mModuleAudio.setStreamMusicVolumeZero();
        }
      }
      if (checkArgu(paramArrayOfString, new String[] { "0", "4", "0" }))
      {
        mModuleAudio.setAudioPath(4);
        str = responseStringNoArg(paramArrayOfString[0]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "5", "0" }))
      {
        mModuleAudio.setAudioPath(1);
        mModuleAudio.setStreamMusicVolumeMax();
        mModuleAudio.playMedia(2131034118, true);
        str = responseStringNoArg(paramArrayOfString[0]);
      }
      else
      {
        str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      }
      label934:
      Object localObject2 = str;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtSpkstest
 * JD-Core Version:    0.7.1
 */