package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.modules.ModuleCommon;

public class AtLooptest
  extends AtCommandHandler
{
  public AtLooptest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "LOOPTEST";
    this.CLASS_NAME = "AtLoopback";
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
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
        {
          mModuleAudio.startLoopback(0, 1);
          str = responseStringNoArg(paramArrayOfString[0]);
          break label540;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "1" }))
        {
          mModuleAudio.startLoopback(1, 1);
          str = responseStringNoArg(paramArrayOfString[0]);
          break label540;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "2" }))
        {
          mModuleAudio.startLoopback(0, 0);
          str = responseStringNoArg(paramArrayOfString[0]);
          break label540;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "3" }))
        {
          if (mModuleCommon.isVoiceCapable())
          {
            mModuleAudio.startLoopback(1, 0);
            str = responseStringNoArg(paramArrayOfString[0]);
            break label540;
          }
          if (mModuleCommon.isVoiceCapable()) {
            continue;
          }
          mModuleAudio.startLoopback(1, 3);
          continue;
        }
      }
      finally {}
      tmp246_243[0] = "0";
      String[] tmp251_246 = tmp246_243;
      tmp251_246[1] = "0";
      String[] tmp256_251 = tmp251_246;
      tmp256_251[2] = "4";
      if (checkArgu(paramArrayOfString, tmp256_251))
      {
        mModuleAudio.startLoopback(4, 1);
        str = responseStringNoArg(paramArrayOfString[0]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "5" }))
      {
        mModuleAudio.startLoopback(4, 0);
        str = responseStringNoArg(paramArrayOfString[0]);
      }
      else
      {
        boolean bool = checkArgu(paramArrayOfString, new String[] { "0", "0", "6" });
        str = null;
        if (!bool)
        {
          if (checkArgu(paramArrayOfString, new String[] { "0", "0", "7" }))
          {
            if (mModuleCommon.isVoiceCapable()) {
              mModuleAudio.startLoopback(3, 1);
            }
            for (;;)
            {
              str = responseStringNoArg(paramArrayOfString[0]);
              break;
              if (!mModuleCommon.isVoiceCapable()) {
                mModuleAudio.startLoopback(3, 3);
              }
            }
          }
          if (checkArgu(paramArrayOfString, new String[] { "0", "0", "8" }))
          {
            mModuleAudio.startLoopback(2, 0);
            str = responseStringNoArg(paramArrayOfString[0]);
          }
          else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0" }))
          {
            mModuleAudio.stopLoopback();
            str = responseStringNoArg(paramArrayOfString[0]);
          }
          else
          {
            str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          }
        }
      }
      label540:
      Object localObject2 = str;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtLooptest
 * JD-Core Version:    0.7.1
 */