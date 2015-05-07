package com.sec.factory.aporiented.athandler;

import android.content.Context;
import android.os.SystemProperties;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.modules.ModulePower;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;

public class AtFactolog
  extends AtCommandHandler
{
  public AtFactolog(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "FACTOLOG";
    this.CLASS_NAME = "AtFactolog";
    this.NUM_ARGS = 4;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (paramArrayOfString[2].equals("1"))
        {
          str1 = responseOK(paramArrayOfString[0]);
          mModulePower.doWakeLock(true);
          mModulePower.sendDvfsLockIntent();
          mModulePower.sendAlarmManagerOnOff(false);
          mModulePower.setFactoryModeAtBatteryNode(true);
          mModuleCommon.setSwitchFactoryState();
          if (Support.Feature.getBoolean("NEED_NOTI_AUDIO_MANAGER", true))
          {
            mModuleAudio.sendToAudioManagerFTAOnOff(true);
            String str2 = SystemProperties.get("ril.factory_mode", "none");
            FtUtil.log_i(this.CLASS_NAME, "handleCommand()", "FT TYPE: " + str2);
            break label211;
          }
          FtUtil.log_i(this.CLASS_NAME, "handleCommand()", "sendToAudioManagerFTAOnOff : M0_EUR case");
          continue;
        }
        if (!paramArrayOfString[2].equals("0")) {
          break label207;
        }
      }
      finally {}
      String str1 = responseOK(paramArrayOfString[0]);
      mModulePower.setFactoryModeAtBatteryNode(false);
      if (Support.Feature.getBoolean("NEED_NOTI_AUDIO_MANAGER", true))
      {
        mModuleAudio.sendToAudioManagerFTAOnOff(false);
      }
      else
      {
        FtUtil.log_i(this.CLASS_NAME, "handleCommand()", "sendToAudioManagerFTAOnOff : M0_EUR case");
        break label211;
        label207:
        str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      }
      label211:
      Object localObject2 = str1;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFactolog
 * JD-Core Version:    0.7.1
 */