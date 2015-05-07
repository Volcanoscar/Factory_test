package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.FtUtil;

public class AtLooptest_dual
  extends AtCommandHandler
{
  public AtLooptest_dual(Context paramContext)
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
      String str2;
      try
      {
        String str1 = mModuleCommon.getCPName();
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (str1.equals("XMM6262"))
        {
          FtUtil.log_d(this.CLASS_NAME, "AtLooptest_dual", " is Cp2");
          mModuleAudio.setPhoneType("cp2");
          if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
          {
            mModuleAudio.startLoopback(0, 1);
            str2 = responseStringNoArg(paramArrayOfString[0]);
            break label599;
          }
        }
        else
        {
          FtUtil.log_d(this.CLASS_NAME, "AtLooptest_dual", " is Cp1");
          mModuleAudio.setPhoneType("cp1");
          continue;
        }
      }
      finally {}
      tmp135_132[0] = "0";
      String[] tmp140_135 = tmp135_132;
      tmp140_135[1] = "0";
      String[] tmp145_140 = tmp140_135;
      tmp145_140[2] = "1";
      if (checkArgu(paramArrayOfString, tmp145_140))
      {
        mModuleAudio.startLoopback(1, 1);
        str2 = responseStringNoArg(paramArrayOfString[0]);
      }
      else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "2" }))
      {
        mModuleAudio.startLoopback(0, 0);
        str2 = responseStringNoArg(paramArrayOfString[0]);
      }
      else
      {
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "3" }))
        {
          if (mModuleCommon.isVoiceCapable()) {
            mModuleAudio.startLoopback(1, 0);
          }
          for (;;)
          {
            str2 = responseStringNoArg(paramArrayOfString[0]);
            break;
            if (!mModuleCommon.isVoiceCapable()) {
              mModuleAudio.startLoopback(1, 3);
            }
          }
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "4" }))
        {
          mModuleAudio.startLoopback(4, 1);
          str2 = responseStringNoArg(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "5" }))
        {
          mModuleAudio.startLoopback(4, 0);
          str2 = responseStringNoArg(paramArrayOfString[0]);
        }
        else
        {
          boolean bool = checkArgu(paramArrayOfString, new String[] { "0", "0", "6" });
          str2 = null;
          if (!bool)
          {
            if (checkArgu(paramArrayOfString, new String[] { "0", "0", "7" }))
            {
              if (mModuleCommon.isVoiceCapable()) {
                mModuleAudio.startLoopback(3, 1);
              }
              for (;;)
              {
                str2 = responseStringNoArg(paramArrayOfString[0]);
                break;
                if (!mModuleCommon.isVoiceCapable()) {
                  mModuleAudio.startLoopback(3, 3);
                }
              }
            }
            if (checkArgu(paramArrayOfString, new String[] { "0", "0", "8" }))
            {
              mModuleAudio.startLoopback(2, 0);
              str2 = responseStringNoArg(paramArrayOfString[0]);
            }
            else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0" }))
            {
              mModuleAudio.stopLoopback();
              str2 = responseStringNoArg(paramArrayOfString[0]);
            }
            else
            {
              str2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
            }
          }
        }
      }
      label599:
      Object localObject2 = str2;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtLooptest_dual
 * JD-Core Version:    0.7.1
 */