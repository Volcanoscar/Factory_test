package com.sec.factory.support;

public class NVAccessor
{
  private static NVAccessor mNVAccessor;
  private static boolean mSuccessLibLoad = true;
  
  static
  {
    mNVAccessor = new NVAccessor();
    try
    {
      System.loadLibrary("nvaccessor");
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      FtUtil.log_e("NVAccessor", "WARNING: Could not load libnvaccessor");
      mSuccessLibLoad = false;
    }
  }
  
  public static byte getNV(byte paramByte)
  {
    if ((FtUtil.isFactoryAppAPO()) && (paramByte > 0) && (mSuccessLibLoad == true)) {
      try
      {
        int i = mNVAccessor.nativeGetNV(paramByte).charAt(0);
        return (byte)i;
      }
      catch (Exception localException)
      {
        FtUtil.log_e(localException);
      }
    }
    return 78;
  }
  
  public static String getNVHistory()
  {
    if ((FtUtil.isFactoryAppAPO()) && (mSuccessLibLoad == true)) {
      return mNVAccessor.nativeGetNVHistory();
    }
    return null;
  }
  
  public static void setNV(byte paramByte1, byte paramByte2)
  {
    if ((FtUtil.isFactoryAppAPO()) && (mSuccessLibLoad == true)) {
      mNVAccessor.nativeSetNV(paramByte1, Character.toString((char)paramByte2));
    }
  }
  
  native String nativeGetNV(int paramInt);
  
  native String nativeGetNVHistory();
  
  native int nativeSetNV(int paramInt, String paramString);
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.support.NVAccessor
 * JD-Core Version:    0.7.1
 */