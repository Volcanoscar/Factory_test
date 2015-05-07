package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.FtUtil;
import java.io.IOException;

public class AtFcmptest
  extends AtCommandHandler
{
  Context mContext = this.context.getApplicationContext();
  ModuleCommon moduleCommon = ModuleCommon.instance(this.mContext);
  
  public AtFcmptest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "FCMPTEST";
    this.CLASS_NAME = "AtFcmptest";
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
        mModuleAudio.setMode(0);
        mModuleAudio.stopMedia();
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
        {
          if (mModuleDevice.isMountedStorage(1))
          {
            responseStringNoArg(paramArrayOfString[0]);
            try
            {
              if (!this.moduleCommon.isVoiceCapable())
              {
                mModuleAudio.setStreamMusicVolume(7);
                mModuleAudio.playMedia(mModuleDevice.getStoragePath(1) + "/1kHz.mp3", true);
                mModuleAudio.setStreamMusicVolumeMax();
                str = responseStringNoArg(paramArrayOfString[0]);
              }
              else
              {
                mModuleAudio.setStreamMusicVolumeZero();
                continue;
                str = responseStringCMDNG();
              }
            }
            catch (IOException localIOException)
            {
              FtUtil.log_e(this.CLASS_NAME, "handleCommand", mModuleDevice.getStoragePath(1) + " not found.");
              str = responseStringCMDNG();
            }
          }
          break label394;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "1" }))
        {
          if (mModuleDevice.isMountedStorage(1))
          {
            str = responseStringNoArg(paramArrayOfString[0]);
            break label394;
          }
          str = responseStringCMDNG();
          break label394;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0" }))
        {
          if (!this.moduleCommon.isVoiceCapable())
          {
            mModuleAudio.setStreamMusicVolume(7);
            mModuleAudio.playMedia(2131034119, true);
            mModuleAudio.setStreamMusicVolumeMax();
            str = responseStringNoArg(paramArrayOfString[0]);
            break label394;
          }
          mModuleAudio.setStreamMusicVolumeZero();
          continue;
        }
      }
      finally {}
      tmp357_354[0] = "0";
      String[] tmp362_357 = tmp357_354;
      tmp362_357[1] = "1";
      String[] tmp367_362 = tmp362_357;
      tmp367_362[2] = "1";
      if (checkArgu(paramArrayOfString, tmp367_362)) {
        str = responseStringNoArg(paramArrayOfString[0]);
      } else {
        str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      }
      label394:
      Object localObject2 = str;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFcmptest
 * JD-Core Version:    0.7.1
 */