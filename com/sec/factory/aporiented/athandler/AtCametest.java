package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.android.app.camera.Camera;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;
import com.sec.factory.support.Support.TestCase;

public class AtCametest
  extends AtCommandHandler
{
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      if ("com.android.samsungtest.CAMERA_GOOD".equals(str))
      {
        FtUtil.log_d(AtCametest.this.CLASS_NAME, "onReceive", "CAMERA_GOOD");
        if (AtCametest.this.getCmdType() == 0)
        {
          FtUtil.log_d(AtCametest.this.CLASS_NAME, "handleCommand", "CAMERA_GOOD_CPO");
          AtCametest.this.writerCpo.write(3, "10", "00", null);
        }
      }
      for (;;)
      {
        AtCametest.this.stopReceiver(AtCametest.this.mReceiver);
        return;
        FtUtil.log_d(AtCametest.this.CLASS_NAME, "handleCommand", "CAMERA_GOOD_APO");
        AtCametest.this.writer.write(AtCametest.this.responseStringNoArg("0"));
        continue;
        if ("com.android.samsungtest.CAMERA_BAD".equals(str))
        {
          FtUtil.log_d(AtCametest.this.CLASS_NAME, "onReceive", "CAMERA_BAD");
          if (AtCametest.this.getCmdType() == 0)
          {
            FtUtil.log_d(AtCametest.this.CLASS_NAME, "onReceive", "CAMERA_BAD_CPO");
            AtCametest.this.writerCpo.write(4, "10", "00", null);
          }
          else
          {
            FtUtil.log_d(AtCametest.this.CLASS_NAME, "onReceive", "CAMERA_BAD_APO");
            AtCametest.this.writer.write(AtCametest.this.responseStringCMDNG());
          }
        }
        else if ("com.android.samsungtest.CAMERA_STOP_ACK".equals(str))
        {
          FtUtil.log_d(AtCametest.this.CLASS_NAME, "onReceive", "CAMERA_STOP_ACK");
          if (Support.TestCase.getEnabled("NEED_ACK_FOR_CAMERA_STOP")) {
            if (AtCametest.this.getCmdType() == 0)
            {
              FtUtil.log_d(AtCametest.this.CLASS_NAME, "handleCommand", "CAMERA_STOP_ACK_CPO");
              AtCametest.this.writerCpo.write(3, "10", "00", null);
            }
            else
            {
              FtUtil.log_d(AtCametest.this.CLASS_NAME, "handleCommand", "CAMERA_STOP_ACK_APO");
              AtCametest.this.writer.write(AtCametest.this.responseStringNoArg("0"));
            }
          }
        }
      }
    }
  };
  
  public AtCametest(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "CAMETEST";
    this.CLASS_NAME = "AtCametest";
    this.NUM_ARGS = 4;
    this.writer = paramResponseWriter;
  }
  
  public AtCametest(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "CAMETEST";
    this.CLASS_NAME = "AtCametest";
    this.NUM_ARGS = 4;
    this.writerCpo = paramResponseWriterCPO;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      Boolean localBoolean1;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return str1;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0", "1" }))
        {
          str1 = responseStringNoArg(paramArrayOfString[0]);
          continue;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0", "0" }))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "Front Preview");
          startReceiver();
          Intent localIntent6 = new Intent(this.context, Camera.class);
          localIntent6.putExtra("camera_id", 1);
          localIntent6.setFlags(335544320);
          this.context.startActivity(localIntent6);
          int n = getCmdType();
          str1 = null;
          if (n != 0) {
            continue;
          }
          str1 = "WAIT";
          continue;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0", "1" }))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "Rear Preview");
          startReceiver();
          Intent localIntent5 = new Intent(this.context, Camera.class);
          localIntent5.putExtra("camera_id", 0);
          localIntent5.putExtra("camcorder_preview_test", true);
          localIntent5.setFlags(335544320);
          this.context.startActivity(localIntent5);
          int m = getCmdType();
          str1 = null;
          if (m != 0) {
            continue;
          }
          str1 = "WAIT";
          continue;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "3", "2", "0" }))
        {
          String str3 = Support.Feature.getString("REAR_CAMERA_TYPE");
          Boolean localBoolean2 = Boolean.valueOf(Support.Feature.getBoolean("ISP_FLASH_TEST_SMD", false));
          String str4 = Support.Feature.getString("TORCH_MODE_FLASH_ON_CURRENT");
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "Rear cam type : " + str3);
          if (str3.equals("SOC"))
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "flash on for SOC");
            if (Support.Kernel.write("TORCH_MODE_FLASH", str4))
            {
              str1 = responseStringNoArg(paramArrayOfString[0]);
              continue;
            }
            boolean bool5 = Support.Kernel.write("TORCH_MODE_FLASH_OLD", str4);
            str1 = null;
            if (!bool5) {
              continue;
            }
            str1 = responseStringNoArg(paramArrayOfString[0]);
            continue;
          }
          boolean bool3 = str3.equals("ISP");
          str1 = null;
          if (!bool3) {
            continue;
          }
          if (!localBoolean2.booleanValue())
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "flash off for ISP");
            startReceiver();
            Intent localIntent4 = new Intent(this.context, Camera.class);
            localIntent4.putExtra("camera_id", 0);
            localIntent4.putExtra("torch_on", 1);
            localIntent4.setFlags(268435456);
            this.context.startActivity(localIntent4);
            int k = getCmdType();
            str1 = null;
            if (k != 0) {
              continue;
            }
            str1 = "WAIT";
            continue;
          }
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "ISP_SMD");
          if (Support.Kernel.write("TORCH_MODE_FLASH", str4))
          {
            str1 = responseStringNoArg(paramArrayOfString[0]);
            continue;
          }
          boolean bool4 = Support.Kernel.write("TORCH_MODE_FLASH_OLD", str4);
          str1 = null;
          if (!bool4) {
            continue;
          }
          str1 = responseStringNoArg(paramArrayOfString[0]);
          continue;
        }
        if (!checkArgu(paramArrayOfString, new String[] { "0", "3", "3", "0" })) {
          break label886;
        }
        String str2 = Support.Feature.getString("REAR_CAMERA_TYPE");
        localBoolean1 = Boolean.valueOf(Support.Feature.getBoolean("ISP_FLASH_TEST_SMD", false));
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "Rear cam type : " + str2);
        if (str2.equals("SOC"))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "flash off for SOC");
          if (Support.Kernel.write("TORCH_MODE_FLASH", "0"))
          {
            responseStringNoArg(paramArrayOfString[0]);
            str1 = responseStringNoArg(paramArrayOfString[0]);
            continue;
          }
          if (!Support.Kernel.write("TORCH_MODE_FLASH_OLD", "0")) {
            continue;
          }
          responseStringNoArg(paramArrayOfString[0]);
          continue;
        }
        bool1 = str2.equals("ISP");
      }
      finally {}
      boolean bool1;
      String str1 = null;
      if (bool1) {
        if (!localBoolean1.booleanValue())
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "flash off for ISP");
          Intent localIntent3 = new Intent("com.android.samsungtest.CameraStop");
          this.context.sendBroadcast(localIntent3);
          str1 = responseStringNoArg(paramArrayOfString[0]);
        }
        else
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "flash off for ISP_SMD");
          if (Support.Kernel.write("TORCH_MODE_FLASH", "0"))
          {
            str1 = responseStringNoArg(paramArrayOfString[0]);
          }
          else
          {
            boolean bool2 = Support.Kernel.write("TORCH_MODE_FLASH_OLD", "0");
            str1 = null;
            if (bool2)
            {
              str1 = responseStringNoArg(paramArrayOfString[0]);
              continue;
              label886:
              if (checkArgu(paramArrayOfString, new String[] { "0", "9", "0", "0" }))
              {
                FtUtil.log_d(this.CLASS_NAME, "handleCommand", "Front off");
                Intent localIntent2 = new Intent("com.android.samsungtest.CameraStop");
                this.context.sendBroadcast(localIntent2);
                if (Support.TestCase.getEnabled("NEED_ACK_FOR_CAMERA_STOP"))
                {
                  FtUtil.log_d(this.CLASS_NAME, "handleCommand", "Camera Stop from mReceiver");
                  startReceiver();
                  int j = getCmdType();
                  str1 = null;
                  if (j == 0) {
                    str1 = "WAIT";
                  }
                }
                else
                {
                  FtUtil.log_d(this.CLASS_NAME, "handleCommand", "Camera Stop!");
                  str1 = responseStringNoArg(paramArrayOfString[0]);
                }
              }
              else if (checkArgu(paramArrayOfString, new String[] { "0", "9", "1", "0" }))
              {
                FtUtil.log_d(this.CLASS_NAME, "handleCommand", "Rear off");
                Intent localIntent1 = new Intent("com.android.samsungtest.CameraStop");
                this.context.sendBroadcast(localIntent1);
                if (Support.TestCase.getEnabled("NEED_ACK_FOR_CAMERA_STOP"))
                {
                  FtUtil.log_d(this.CLASS_NAME, "handleCommand", "Camera Stop from mReceiver");
                  startReceiver();
                  int i = getCmdType();
                  str1 = null;
                  if (i == 0) {
                    str1 = "WAIT";
                  }
                }
                else
                {
                  FtUtil.log_d(this.CLASS_NAME, "handleCommand", "Camera Stop!");
                  str1 = responseStringNoArg(paramArrayOfString[0]);
                }
              }
              else
              {
                str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
              }
            }
          }
        }
      }
    }
  }
  
  public void startReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.android.samsungtest.CAMERA_GOOD");
    localIntentFilter.addAction("com.android.samsungtest.CAMERA_BAD");
    localIntentFilter.addAction("com.android.samsungtest.CAMERA_STOP_ACK");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtCametest
 * JD-Core Version:    0.7.1
 */