package com.sec.factory.app.factorytest;

public class MTVTunerTest
{
  static
  {
    System.loadLibrary("OneSegfactorytest_jni");
  }
  
  public native int nativeoneseg_mod_deinit();
  
  public native int nativeoneseg_mod_getsiginfo(int paramInt);
  
  public native int nativeoneseg_mod_init();
  
  public native int nativeoneseg_mod_settune(int paramInt);
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.MTVTunerTest
 * JD-Core Version:    0.7.1
 */