package com.sec.factory.aporiented.athandler;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.modules.ModuleTouchScreen;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.TestCase;

public class AtFirmvers
  extends AtCommandHandler
{
  private static String mTSPUpdateStatus = "NG";
  Handler mTspFirmwareUpdateHandler;
  
  public AtFirmvers(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "FIRMVERS";
    this.CLASS_NAME = "AtFirmvers";
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
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" })) {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        } else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "1" })) {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        } else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0" })) {
          if (Support.TestCase.getEnabled("IS_TSP_STANDARD"))
          {
            mTSPUpdateStatus = "DOWNLOADING";
            this.mTspFirmwareUpdateHandler = null;
            this.mTspFirmwareUpdateHandler = new Handler(this.context.getMainLooper())
            {
              public void handleMessage(Message paramAnonymousMessage)
              {
                FtUtil.log_d(AtFirmvers.this.CLASS_NAME, "tspFirmwareUpdateHandler", "msg.what = " + paramAnonymousMessage.what);
                if (paramAnonymousMessage.what == ModuleTouchScreen.TSP_WHAT_STATUS_OK)
                {
                  FtUtil.log_d(AtFirmvers.this.CLASS_NAME, "tspFirmwareUpdateHandler", "TSP_WHAT_STATUS_OK");
                  if (paramAnonymousMessage.arg1 == ModuleTouchScreen.TSP_ID__FW_UPDATE)
                  {
                    if (!"PASS".equalsIgnoreCase(paramAnonymousMessage.obj.toString())) {
                      break label105;
                    }
                    AtFirmvers.access$002("PASS");
                  }
                }
                for (;;)
                {
                  FtUtil.log_d(AtFirmvers.this.CLASS_NAME, "tspFirmwareUpdateHandler", AtFirmvers.mTSPUpdateStatus);
                  return;
                  label105:
                  AtFirmvers.access$002("FAIL");
                  continue;
                  if (paramAnonymousMessage.what == ModuleTouchScreen.TSP_WHAT_STATUS_NG)
                  {
                    AtFirmvers.access$002("FAIL");
                    FtUtil.log_d(AtFirmvers.this.CLASS_NAME, "tspFirmwareUpdateHandler", "TSP_WHAT_STATUS_NG");
                  }
                  else if (paramAnonymousMessage.what == ModuleTouchScreen.TSP_WHAT_STATUS_NA)
                  {
                    FtUtil.log_d(AtFirmvers.this.CLASS_NAME, "tspFirmwareUpdateHandler", AtFirmvers.mTSPUpdateStatus);
                    AtFirmvers.access$002("FAIL");
                  }
                }
              }
            };
            if (mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__FW_UPDATE, "0", this.mTspFirmwareUpdateHandler))
            {
              str = responseOK(paramArrayOfString[0]);
            }
            else
            {
              str = responseNG(paramArrayOfString[0]);
              mTSPUpdateStatus = "NG";
            }
          }
        }
      }
      finally {}
      if (mModuleDevice.firmwareDownload(1))
      {
        str = responseOK(paramArrayOfString[0]);
      }
      else
      {
        str = responseNG(paramArrayOfString[0]);
        break label880;
        if (checkArgu(paramArrayOfString, new String[] { "0", "1", "1" }))
        {
          if (Support.TestCase.getEnabled("IS_TSP_STANDARD")) {
            str = responseString(paramArrayOfString[0], mTSPUpdateStatus);
          } else {
            str = responseString(paramArrayOfString[0], mModuleDevice.readModuleUpdateStatus(1));
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2", "0" }))
        {
          if (mModuleDevice.firmwareDownload(2)) {
            str = responseOK(paramArrayOfString[0]);
          } else {
            str = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2", "1" }))
        {
          str = responseString(paramArrayOfString[0], mModuleDevice.readModuleUpdateStatus(2));
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "0", "0" }))
        {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "0", "1" }))
        {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "1", "0" }))
        {
          if (Support.TestCase.getEnabled("IS_TSP_STANDARD")) {
            str = responseString(paramArrayOfString[0], mModuleTouchScreen.getTSPFirmwareVersionIC());
          } else {
            str = responseString(paramArrayOfString[0], mModuleDevice.readModuleFirmwareVersion(1));
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "1", "1" }))
        {
          if (Support.TestCase.getEnabled("IS_TSP_STANDARD")) {
            str = responseString(paramArrayOfString[0], mModuleTouchScreen.getTSPFirmwareVersionBinary());
          } else {
            str = responseString(paramArrayOfString[0], mModuleDevice.readModuleBinVersion(1));
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "2", "0" }))
        {
          str = responseString(paramArrayOfString[0], mModuleDevice.readModuleFirmwareVersion(2));
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "2", "1" }))
        {
          str = responseString(paramArrayOfString[0], mModuleDevice.readModuleBinVersion(2));
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "3", "0" }))
        {
          str = responseString(paramArrayOfString[0], mModuleDevice.readModuleFirmwareVersion(3));
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "4", "0" }))
        {
          String[] arrayOfString2 = mModuleDevice.readModuleFirmwareVersion(4).split(",");
          str = responseString(paramArrayOfString[0], arrayOfString2[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "4", "1" }))
        {
          String[] arrayOfString1 = mModuleDevice.readModuleFirmwareVersion(4).split(",");
          str = responseString(paramArrayOfString[0], arrayOfString1[1]);
        }
        else
        {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      label880:
      Object localObject2 = str;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtFirmvers
 * JD-Core Version:    0.7.1
 */