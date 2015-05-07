package com.sec.android.app.camera.framework;

import android.content.Context;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import java.util.Iterator;
import java.util.List;

public class CameraSettings
{
  public Context mContext;
  private String mFocusMode;
  
  public CameraSettings(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public static boolean getSupportedAutofocus()
  {
    return true;
  }
  
  public static boolean hasFlash()
  {
    return true;
  }
  
  public static boolean hasZoom(int paramInt)
  {
    if (paramInt == 0) {
      return true;
    }
    return 1 != paramInt;
  }
  
  public static boolean needToCheckCamcorderPreviewTest()
  {
    return true;
  }
  
  public static boolean needToCheckDTP(int paramInt)
  {
    int i = 1;
    int j = 1;
    if (paramInt == 0) {
      j = 1;
    }
    for (;;)
    {
      if (j == i) {
        i = 0;
      }
      return i;
      if (paramInt == i) {
        j = 1;
      }
    }
  }
  
  public String getFocusMode()
  {
    return this.mFocusMode;
  }
  
  public boolean isSupportedFocusModes(String paramString, List<String> paramList)
  {
    return false;
  }
  
  public boolean isSupportedFocusModes(List<String> paramList)
  {
    return false;
  }
  
  public void setPictureSize(int paramInt, Camera.Parameters paramParameters)
  {
    List localList = paramParameters.getSupportedPictureSizes();
    if (localList == null)
    {
      if (paramInt == 1)
      {
        paramParameters.set("picture-size", "640x480");
        return;
      }
      paramParameters.set("picture-size", "640x480");
      return;
    }
    int i = 0;
    int j = 0;
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      Camera.Size localSize = (Camera.Size)localIterator.next();
      if (localSize.width * localSize.height > i * j)
      {
        i = localSize.width;
        j = localSize.height;
      }
    }
    Log.i("CameraSettings", "setPictureSize - " + i + "x" + j);
    paramParameters.setPictureSize(i, j);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.camera.framework.CameraSettings
 * JD-Core Version:    0.7.1
 */