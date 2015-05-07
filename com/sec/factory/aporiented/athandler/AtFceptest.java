package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.modules.ModuleCommon;

public class AtFceptest
  extends AtCommandHandler
{
  public AtFceptest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "FCEPTEST";
    this.CLASS_NAME = "AtFceptest";
    this.NUM_ARGS = 4;
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
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0", "0" }))
        {
          if (mModuleCommon.isVoiceCapable())
          {
            mModuleAudio.startLoopback(5, 1);
            str = responseStringNoArg(paramArrayOfString[0]);
            break label542;
          }
          if (mModuleCommon.isVoiceCapable()) {
            continue;
          }
          mModuleAudio.startLoopback(5, 3);
          continue;
        }
      }
      finally {}
      tmp110_107[0] = "0";
      String[] tmp115_110 = tmp110_107;
      tmp115_110[1] = "0";
      String[] tmp120_115 = tmp115_110;
      tmp120_115[2] = "0";
      String[] tmp125_120 = tmp120_115;
      tmp125_120[3] = "1";
      if (checkArgu(paramArrayOfString, tmp125_120))
      {
        if (mModuleCommon.isVoiceCapable()) {
          mModuleAudio.startLoopback(5, 0);
        }
        for (;;)
        {
          str = responseStringNoArg(paramArrayOfString[0]);
          break;
          if (!mModuleCommon.isVoiceCapable()) {
            mModuleAudio.startLoopback(5, 3);
          }
        }
      }
      if (checkArgu(paramArrayOfString, new String[] { "0", "0", "1", "0" }))
      {
        mModuleAudio.stopLoopback();
        str = responseStringNoArg(paramArrayOfString[0]);
      }
      else
      {
        boolean bool1 = checkArgu(paramArrayOfString, new String[] { "0", "1", "0", "0" });
        str = null;
        if (!bool1)
        {
          boolean bool2 = checkArgu(paramArrayOfString, new String[] { "0", "1", "1", "0" });
          str = null;
          if (!bool2)
          {
            boolean bool3 = checkArgu(paramArrayOfString, new String[] { "0", "1", "2", "0" });
            str = null;
            if (!bool3)
            {
              boolean bool4 = checkArgu(paramArrayOfString, new String[] { "1", "0", "0", "0" });
              str = null;
              if (!bool4)
              {
                boolean bool5 = checkArgu(paramArrayOfString, new String[] { "1", "0", "1", "0" });
                str = null;
                if (!bool5) {
                  if (checkArgu(paramArrayOfString, new String[] { "1", "0", "2", "0" }))
                  {
                    if (mModuleAudio.isEarphonePlugged()) {
                      str = responseString(paramArrayOfString[0], "FOUND");
                    } else {
                      str = responseString(paramArrayOfString[0], "NOT FOUND");
                    }
                  }
                  else
                  {
                    boolean bool6 = checkArgu(paramArrayOfString, new String[] { "0", "1", "1", "0" });
                    str = null;
                    if (!bool6) {
                      str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
                    }
                  }
                }
              }
            }
          }
        }
      }
      label542:
      Object localObject2 = str;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFceptest
 * JD-Core Version:    0.7.1
 */