package com.sec.android.app.camerafirmware;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;

public class CameraDeviceController
{
  private Camera mCameraDevice;
  private Camera.Parameters mParameters;
  
  public boolean openCamera()
  {
    Log.e("CameraFirmware_broadcast", "openCameraDevice");
    if (this.mCameraDevice == null) {
      this.mCameraDevice = Camera.open(0);
    }
    Camera localCamera = this.mCameraDevice;
    boolean bool = false;
    if (localCamera != null) {
      bool = true;
    }
    return bool;
  }
  
  public void releaseCamera()
  {
    Log.e("CameraFirmware_broadcast", "releaseCameraDevice");
    if (this.mCameraDevice != null)
    {
      this.mCameraDevice.release();
      this.mCameraDevice = null;
    }
  }
  
  public void setFirmwareMode(int paramInt)
  {
    if (this.mCameraDevice == null)
    {
      Log.e("CameraFirmware_broadcast", "setFirmwareMode - mCameraDevice is null");
      return;
    }
    this.mParameters = this.mCameraDevice.getParameters();
    String str;
    switch (paramInt)
    {
    default: 
      str = "none";
    }
    for (;;)
    {
      this.mParameters.set("firmware-mode", str);
      this.mCameraDevice.setParameters(this.mParameters);
      return;
      str = "version";
      continue;
      str = "update";
      continue;
      str = "dump";
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.camerafirmware.CameraDeviceController
 * JD-Core Version:    0.7.1
 */