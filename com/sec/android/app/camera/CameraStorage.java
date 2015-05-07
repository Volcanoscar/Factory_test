package com.sec.android.app.camera;

public class CameraStorage
{
  private static CameraStorage mInstance = null;
  private String mFrontFile = null;
  private String mRearFile = null;
  
  public static CameraStorage getInstance()
  {
    if (mInstance == null) {
      mInstance = new CameraStorage();
    }
    return mInstance;
  }
  
  void clearFilePath()
  {
    this.mRearFile = null;
    this.mFrontFile = null;
  }
  
  String getFilePath(int paramInt)
  {
    if (paramInt == 0) {
      return this.mRearFile;
    }
    return this.mFrontFile;
  }
  
  void setFilePath(int paramInt, String paramString)
  {
    if (paramInt == 0)
    {
      this.mRearFile = paramString;
      return;
    }
    this.mFrontFile = paramString;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.camera.CameraStorage
 * JD-Core Version:    0.7.1
 */