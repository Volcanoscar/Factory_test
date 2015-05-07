package com.sec.android.app.camerafirmware;

import android.os.Environment;
import android.util.Log;
import java.io.File;

public class FirmwareFileMgrFront
  extends FirmwareFileMgr
{
  protected static final String CAMERA_FIRMWARE_INFO_USER_FILE = Environment.getExternalStorageDirectory().toString() + "/";
  
  FirmwareFileMgrFront()
  {
    Log.e("FirmwareFileMgrFront", "FirmwareFileMgrFront");
    this.CAMERA_FIRMWARE_INFO_FILE = "/sys/class/camera/front/front_camfw";
    this.CAMERA_FIRMWARE_TYPE_FILE = "/sys/class/camera/front/front_camtype";
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.camerafirmware.FirmwareFileMgrFront
 * JD-Core Version:    0.7.1
 */