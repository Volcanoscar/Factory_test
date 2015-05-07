package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.FtUtil;

public class AtFceptest_dual
  extends AtCommandHandler
{
  public AtFceptest_dual(Context paramContext)
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
          if (!checkArgu(paramArrayOfString, new String[] { "0", "0", "0", "0" })) {
            break label163;
          }
          if (mModuleCommon.isVoiceCapable())
          {
            mModuleAudio.startLoopback(5, 1);
            str2 = responseStringNoArg(paramArrayOfString[0]);
            break label601;
          }
        }
        else
        {
          FtUtil.log_d(this.CLASS_NAME, "AtLooptest_dual", " is Cp1");
          mModuleAudio.setPhoneType("cp1");
          continue;
        }
        if (mModuleCommon.isVoiceCapable()) {
          continue;
        }
      }
      finally {}
      mModuleAudio.startLoopback(5, 3);
      continue;
      label163:
      if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0", "1" }))
      {
        if (mModuleCommon.isVoiceCapable()) {
          mModuleAudio.startLoopback(5, 0);
        }
        for (;;)
        {
          str2 = responseStringNoArg(paramArrayOfString[0]);
          break;
          if (!mModuleCommon.isVoiceCapable()) {
            mModuleAudio.startLoopback(5, 3);
          }
        }
      }
      if (checkArgu(paramArrayOfString, new String[] { "0", "0", "1", "0" }))
      {
        mModuleAudio.stopLoopback();
        str2 = responseStringNoArg(paramArrayOfString[0]);
      }
      else
      {
        boolean bool1 = checkArgu(paramArrayOfString, new String[] { "0", "1", "0", "0" });
        str2 = null;
        if (!bool1)
        {
          boolean bool2 = checkArgu(paramArrayOfString, new String[] { "0", "1", "1", "0" });
          str2 = null;
          if (!bool2)
          {
            boolean bool3 = checkArgu(paramArrayOfString, new String[] { "0", "1", "2", "0" });
            str2 = null;
            if (!bool3)
            {
              boolean bool4 = checkArgu(paramArrayOfString, new String[] { "1", "0", "0", "0" });
              str2 = null;
              if (!bool4)
              {
                boolean bool5 = checkArgu(paramArrayOfString, new String[] { "1", "0", "1", "0" });
                str2 = null;
                if (!bool5) {
                  if (checkArgu(paramArrayOfString, new String[] { "1", "0", "2", "0" }))
                  {
                    if (mModuleAudio.isEarphonePlugged()) {
                      str2 = responseString(paramArrayOfString[0], "FOUND");
                    } else {
                      str2 = responseString(paramArrayOfString[0], "NOT FOUND");
                    }
                  }
                  else
                  {
                    boolean bool6 = checkArgu(paramArrayOfString, new String[] { "0", "1", "1", "0" });
                    str2 = null;
                    if (!bool6) {
                      str2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
                    }
                  }
                }
              }
            }
          }
        }
      }
      label601:
      Object localObject2 = str2;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFceptest_dual
 * JD-Core Version:    0.7.1
 */