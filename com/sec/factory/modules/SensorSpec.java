package com.sec.factory.modules;

import com.sec.factory.support.FtUtil;

public class SensorSpec
{
  private final String CLASS_NAME = "SensorSpec";
  private int RANGE_MAX_INTEGER = Integer.MAX_VALUE;
  private int RANGE_MIN_INTEGER = Integer.MIN_VALUE;
  private Accelerometer mAccelerometer = null;
  private String mFeature_Accelerometer = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_ACCELEROMETER, SensorDeviceInfo.TARGET_XML);
  private String mFeature_Light;
  private String mFeature_Magnetic;
  private String mFeature_Proximity;
  private Light mLight = null;
  private Range mMagneticRangeADC = null;
  private Range mMagneticRangeADC2 = null;
  private Range mMagneticRangeDAC = null;
  private Range mMagneticRangeSelf = null;
  private Proximity mProximity = null;
  
  public SensorSpec()
  {
    FtUtil.log_d("SensorSpec", "SensorSpec", "mFeature_Accelerometer : " + this.mFeature_Accelerometer);
    setSpecAccel();
    this.mFeature_Magnetic = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_GEOMAGNETIC, SensorDeviceInfo.TARGET_XML);
    FtUtil.log_d("SensorSpec", "SensorSpec", "mFeature_Magnetic : " + this.mFeature_Magnetic);
    setSpecMagnetic();
    this.mFeature_Proximity = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_PROXIMITY, SensorDeviceInfo.TARGET_XML);
    FtUtil.log_d("SensorSpec", "SensorSpec", "mFeature_Proximity : " + this.mFeature_Proximity);
    setSpecProximity();
    this.mFeature_Proximity = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_LIGHT, SensorDeviceInfo.TARGET_XML);
    FtUtil.log_d("SensorSpec", "SensorSpec", "mFeature_Light : " + this.mFeature_Light);
    setSpecLight();
  }
  
  private void setSpecAccel()
  {
    if (this.mFeature_Accelerometer == null)
    {
      this.mAccelerometer = null;
      return;
    }
    if (this.mFeature_Accelerometer.equals("BOSCH_SMB380"))
    {
      this.mAccelerometer = new Accelerometer(10, -52, 52, -52, 52, 192, 320, 0, 24, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("BOSCH_BMA250"))
    {
      this.mAccelerometer = new Accelerometer(10, -52, 52, -52, 52, 190, 322, 0, 24, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("BOSCH_BMA022"))
    {
      this.mAccelerometer = new Accelerometer(10, -52, 52, -52, 52, 185, 327, 0, 24, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("BOSCH_BMA023"))
    {
      this.mAccelerometer = new Accelerometer(10, -56, 56, -56, 56, 181, 331, 0, 24, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("BOSCH_BMA222"))
    {
      this.mAccelerometer = new Accelerometer(8, -15, 15, -15, 15, 44, 84, 0, 6, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("BOSCH_BMA220"))
    {
      this.mAccelerometer = new Accelerometer(6, -6, 6, -6, 6, 9, 23, 0, 0, false);
      return;
    }
    if (this.mFeature_Accelerometer.equals("BOSCH_BMA254"))
    {
      this.mAccelerometer = new Accelerometer(12, -181, 181, -181, 181, 786, 1262, 0, 100, false);
      return;
    }
    if (this.mFeature_Accelerometer.equals("BOSCH_BMC150"))
    {
      this.mAccelerometer = new Accelerometer(12, -181, 181, -181, 181, 786, 1262, 0, 100, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("STMICRO_KR3DH"))
    {
      this.mAccelerometer = new Accelerometer(12, -102, 102, -102, 102, 849, 1199, 0, 100, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("STMICRO_K2DH"))
    {
      this.mAccelerometer = new Accelerometer(12, -154, 154, -154, 154, 798, 1250, 0, 100, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("STMICRO_K2DH_REV"))
    {
      this.mAccelerometer = new Accelerometer(12, -154, 154, -154, 154, -1250, -798, 0, 100, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("STMICRO_K3DH"))
    {
      this.mAccelerometer = new Accelerometer(12, -154, 154, -154, 154, 798, 1250, 0, 100, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("STMICRO_K2DM"))
    {
      this.mAccelerometer = new Accelerometer(8, -12, 12, -12, 12, 48, 80, 0, 6, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("STMICRO_KR3DM"))
    {
      this.mAccelerometer = new Accelerometer(8, -12, 12, -12, 12, 48, 80, 0, 6, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("STMICRO_LSM330DLC"))
    {
      this.mAccelerometer = new Accelerometer(12, -154, 154, -154, 154, 798, 1250, 0, 100, false);
      return;
    }
    if (this.mFeature_Accelerometer.equals("KIONIX_KXUD9"))
    {
      this.mAccelerometer = new Accelerometer(12, -151, 151, -151, 151, 642, 996, 0, 100, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("KIONIX_KXTF9"))
    {
      this.mAccelerometer = new Accelerometer(12, -133, 133, -133, 133, 846, 1202, 0, 100, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("INVENSENSE_MPU6050"))
    {
      this.mAccelerometer = new Accelerometer(16, -2054, 2054, -2054, 2054, 13302, 19466, 0, 1600, true);
      return;
    }
    if (this.mFeature_Accelerometer.equals("INVENSENSE_MPU6051"))
    {
      this.mAccelerometer = new Accelerometer(16, -2054, 2054, -2054, 2054, 13302, 19466, 0, 1600, true);
      return;
    }
    FtUtil.log_e("SensorSpec", "setSpecAccel", "feature : Unknown => return null");
    this.mAccelerometer = null;
  }
  
  private void setSpecLight()
  {
    if (this.mFeature_Light == null)
    {
      this.mLight = null;
      return;
    }
    if (this.mFeature_Light.equals("SHARP_GP2AP002S00F"))
    {
      this.mLight = null;
      return;
    }
    if (this.mFeature_Light.equals("SHARP_GP2AP002A00F"))
    {
      this.mLight = new Light(0, false, false);
      return;
    }
    if (this.mFeature_Light.equals("SHARP_GP2AP030A00F"))
    {
      this.mLight = new Light(0, false, false);
      return;
    }
    if (this.mFeature_Light.equals("CAPELLA_CM3663"))
    {
      this.mLight = new Light(0, false, false);
      return;
    }
    if (this.mFeature_Light.equals("CAPELLA_CM36691"))
    {
      this.mLight = new Light(0, true, true);
      return;
    }
    if (this.mFeature_Light.equals("CAPELLA_CM36651"))
    {
      this.mLight = new Light(1, true, false);
      return;
    }
    if (this.mFeature_Light.equals("TAOS_TMD2672x"))
    {
      this.mLight = null;
      return;
    }
    if (this.mFeature_Light.equals("TAOS_TMD2771x"))
    {
      this.mLight = new Light(0, false, false);
      return;
    }
    if (this.mFeature_Light.equals("TAOS_TMD2772x"))
    {
      this.mLight = new Light(0, false, false);
      return;
    }
    this.mLight = null;
  }
  
  private void setSpecMagnetic()
  {
    if (this.mFeature_Magnetic == null)
    {
      this.mMagneticRangeDAC = null;
      this.mMagneticRangeADC = null;
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = null;
      return;
    }
    if (this.mFeature_Magnetic.equals("AK8963"))
    {
      this.mMagneticRangeDAC = null;
      this.mMagneticRangeADC = new Range(-6500, 6500, -6500, 6500, -6500, 6500);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = new Range(-200, 200, -200, 200, -3200, -800);
      return;
    }
    if (this.mFeature_Magnetic.equals("AK8963C"))
    {
      this.mMagneticRangeDAC = null;
      this.mMagneticRangeADC = new Range(-6500, 6500, -6500, 6500, -6500, 6500);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = new Range(-200, 200, -200, 200, -3200, -800);
      return;
    }
    if (this.mFeature_Magnetic.equals("AK8963C_MANAGER"))
    {
      this.mMagneticRangeDAC = null;
      this.mMagneticRangeADC = new Range(-6500, 6500, -6500, 6500, -6500, 6500);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = new Range(-200, 200, -200, 200, -3200, -800);
      return;
    }
    if (this.mFeature_Magnetic.equals("AK8973"))
    {
      this.mMagneticRangeDAC = new Range(0, 126, 0, 126, 0, 126, 128, 254, 128, 254, 128, 254);
      this.mMagneticRangeADC = new Range(88, 168, 88, 168, 88, 168);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = null;
      return;
    }
    if (this.mFeature_Magnetic.equals("AK8975"))
    {
      this.mMagneticRangeDAC = null;
      this.mMagneticRangeADC = new Range(-2000, 2000, -2000, 2000, -2000, 2000);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = new Range(-100, 100, -100, 100, -1000, -300);
      return;
    }
    if (this.mFeature_Magnetic.equals("YAS529"))
    {
      this.mMagneticRangeDAC = new Range(5, 32, 5, 32, 5, 32);
      this.mMagneticRangeADC = new Range(0, 359, 0, 0, 0, 0);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = new Range(80, this.RANGE_MAX_INTEGER, 107, this.RANGE_MAX_INTEGER, 0, 0);
      return;
    }
    if (this.mFeature_Magnetic.equals("YAS530"))
    {
      this.mMagneticRangeDAC = new Range(-30, 30, -30, 30, -30, 30);
      this.mMagneticRangeADC = new Range(0, 359, 0, 0, 0, 0);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = new Range(133, this.RANGE_MAX_INTEGER, 160, this.RANGE_MAX_INTEGER, 0, 0);
      return;
    }
    if (this.mFeature_Magnetic.equals("YAS530A"))
    {
      this.mMagneticRangeDAC = new Range(-30, 30, -30, 30, -30, 30);
      this.mMagneticRangeADC = new Range(0, 359, 0, 0, 0, 0);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = new Range(133, this.RANGE_MAX_INTEGER, 160, this.RANGE_MAX_INTEGER, 0, 0);
      return;
    }
    if (this.mFeature_Magnetic.equals("YAS530C"))
    {
      this.mMagneticRangeDAC = new Range(-30, 30, -30, 30, -30, 30);
      this.mMagneticRangeADC = new Range(0, 359, 0, 0, 0, 0);
      this.mMagneticRangeADC2 = new Range(-400, 400, -400, 400, -400, 400);
      this.mMagneticRangeSelf = new Range(133, this.RANGE_MAX_INTEGER, 160, this.RANGE_MAX_INTEGER, 0, 0);
      return;
    }
    if (this.mFeature_Magnetic.equals("YAS532"))
    {
      this.mMagneticRangeDAC = new Range(-30, 30, -30, 30, -30, 30);
      this.mMagneticRangeADC = new Range(0, 359, 0, 0, 0, 0);
      this.mMagneticRangeADC2 = new Range(-600, 600, -600, 600, -600, 600);
      this.mMagneticRangeSelf = new Range(17, this.RANGE_MAX_INTEGER, 22, this.RANGE_MAX_INTEGER, 0, 0);
      return;
    }
    if (this.mFeature_Magnetic.equals("YAS532B"))
    {
      this.mMagneticRangeDAC = new Range(-30, 30, -30, 30, -30, 30);
      this.mMagneticRangeADC = new Range(0, 359, 0, 0, 0, 0);
      this.mMagneticRangeADC2 = new Range(-600, 600, -600, 600, -600, 600);
      this.mMagneticRangeSelf = new Range(17, this.RANGE_MAX_INTEGER, 22, this.RANGE_MAX_INTEGER, 0, 0);
      return;
    }
    if (this.mFeature_Magnetic.equals("MMC3140MS"))
    {
      this.mMagneticRangeDAC = new Range(204, 614, 204, 614, 204, 614);
      this.mMagneticRangeADC = new Range(204, 614, 204, 614, 204, 614);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = null;
      return;
    }
    if (this.mFeature_Magnetic.equals("MMC3280MS"))
    {
      this.mMagneticRangeDAC = new Range(204, 614, 204, 614, 204, 614);
      this.mMagneticRangeADC = new Range(204, 614, 204, 614, 204, 614);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = null;
      return;
    }
    if (this.mFeature_Magnetic.equals("AMOTECH"))
    {
      this.mMagneticRangeDAC = null;
      this.mMagneticRangeADC = null;
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = null;
      return;
    }
    if (this.mFeature_Magnetic.equals("BMC022"))
    {
      this.mMagneticRangeDAC = null;
      this.mMagneticRangeADC = new Range(-4096, 4096, -4096, 4096, -16384, 16384);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = new Range(0, 0, 0, 0, 2880, 3840);
      return;
    }
    if (this.mFeature_Magnetic.equals("BMC050"))
    {
      this.mMagneticRangeDAC = null;
      this.mMagneticRangeADC = new Range(-4096, 4096, -4096, 4096, -16384, 16384);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = new Range(0, 0, 0, 0, 2880, 3840);
      return;
    }
    if (this.mFeature_Magnetic.equals("BMC150"))
    {
      this.mMagneticRangeDAC = null;
      this.mMagneticRangeADC = new Range(-4096, 4096, -4096, 4096, -16384, 16384);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = new Range(0, 0, 0, 0, 2880, 3840);
      return;
    }
    if (this.mFeature_Magnetic.equals("HSCDTD004A"))
    {
      this.mMagneticRangeDAC = null;
      this.mMagneticRangeADC = new Range(-2000, 2000, -2000, 2000, -2000, 2000);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = null;
      return;
    }
    if (this.mFeature_Magnetic.equals("HSCDTD006A"))
    {
      this.mMagneticRangeDAC = null;
      this.mMagneticRangeADC = new Range(-2000, 2000, -2000, 2000, -2000, 2000);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = null;
      return;
    }
    if (this.mFeature_Magnetic.equals("HSCDTD008A"))
    {
      this.mMagneticRangeDAC = null;
      this.mMagneticRangeADC = new Range(-8192, 8192, -8192, 8192, -8192, 8192);
      this.mMagneticRangeADC2 = null;
      this.mMagneticRangeSelf = null;
      return;
    }
    this.mMagneticRangeDAC = null;
    this.mMagneticRangeADC = null;
    this.mMagneticRangeADC2 = null;
    this.mMagneticRangeSelf = null;
  }
  
  private void setSpecProximity()
  {
    if (this.mFeature_Proximity == null)
    {
      this.mProximity = null;
      return;
    }
    if (this.mFeature_Proximity.equals("SHARP_GP2AP002S00F"))
    {
      this.mProximity = new Proximity(false, false, false);
      return;
    }
    if (this.mFeature_Proximity.equals("SHARP_GP2AP002A00F"))
    {
      this.mProximity = new Proximity(false, false, false);
      return;
    }
    if (this.mFeature_Proximity.equals("SHARP_GP2AP030A00F"))
    {
      this.mProximity = new Proximity(false, true, false);
      return;
    }
    if (this.mFeature_Proximity.equals("CAPELLA_CM3663"))
    {
      this.mProximity = new Proximity(false, true, false);
      return;
    }
    if (this.mFeature_Proximity.equals("CAPELLA_CM36691"))
    {
      this.mProximity = new Proximity(true, true, true);
      return;
    }
    if (this.mFeature_Proximity.equals("CAPELLA_CM36651"))
    {
      this.mProximity = new Proximity(true, true, false);
      return;
    }
    if (this.mFeature_Proximity.equals("TAOS_TMD2672x"))
    {
      this.mProximity = new Proximity(false, true, false);
      return;
    }
    if (this.mFeature_Proximity.equals("TAOS_TMD2771x"))
    {
      this.mProximity = new Proximity(false, true, false);
      return;
    }
    if (this.mFeature_Proximity.equals("TAOS_TMD2772x"))
    {
      this.mProximity = new Proximity(false, true, false);
      return;
    }
    this.mProximity = null;
  }
  
  public Accelerometer getSpecAccel()
  {
    return this.mAccelerometer;
  }
  
  public Range getSpecGeomagnetic_ADC()
  {
    return this.mMagneticRangeADC;
  }
  
  public Range getSpecGeomagnetic_ADC2()
  {
    return this.mMagneticRangeADC2;
  }
  
  public Range getSpecGeomagnetic_DAC()
  {
    return this.mMagneticRangeDAC;
  }
  
  public Range getSpecGeomagnetic_Self()
  {
    return this.mMagneticRangeSelf;
  }
  
  public class Accelerometer
  {
    public int mBitCount;
    public int mFlatness_Max;
    public int mFlatness_Min;
    public boolean mIsSupport_INT_Pin;
    public SensorSpec.Range mRange = null;
    
    public Accelerometer(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, boolean paramBoolean)
    {
      this.mBitCount = paramInt1;
      this.mRange = new SensorSpec.Range(SensorSpec.this, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
      this.mFlatness_Min = paramInt8;
      this.mFlatness_Max = paramInt9;
      this.mIsSupport_INT_Pin = paramBoolean;
    }
  }
  
  public class Light
  {
    public int mIsSensorTypeLighte;
    public boolean mIsSupportOffset;
    public boolean mIsSupportZeroDistance;
    
    public Light(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.mIsSensorTypeLighte = paramInt;
      this.mIsSupportOffset = paramBoolean1;
      this.mIsSupportZeroDistance = paramBoolean2;
    }
  }
  
  public class Proximity
  {
    public boolean mIsSupportADC;
    public boolean mIsSupportOffset;
    public boolean mIsSupportZeroDistance;
    
    public Proximity(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      this.mIsSupportOffset = paramBoolean1;
      this.mIsSupportADC = paramBoolean2;
      this.mIsSupportZeroDistance = paramBoolean3;
    }
  }
  
  public class Range
  {
    public boolean mIsSupportRange1_X;
    public boolean mIsSupportRange1_Y;
    public boolean mIsSupportRange1_Z;
    public boolean mIsSupportRange2_X;
    public boolean mIsSupportRange2_Y;
    public boolean mIsSupportRange2_Z;
    public int mRange1_X_Max;
    public int mRange1_X_Min;
    public int mRange1_Y_Max;
    public int mRange1_Y_Min;
    public int mRange1_Z_Max;
    public int mRange1_Z_Min;
    public int mRange2_X_Max;
    public int mRange2_X_Min;
    public int mRange2_Y_Max;
    public int mRange2_Y_Min;
    public int mRange2_Z_Max;
    public int mRange2_Z_Min;
    public int mRangeCount;
    
    public Range(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
    {
      this.mRange1_X_Min = paramInt1;
      this.mRange1_X_Max = paramInt2;
      this.mRange1_Y_Min = paramInt3;
      this.mRange1_Y_Max = paramInt4;
      this.mRange1_Z_Min = paramInt5;
      this.mRange1_Z_Max = paramInt6;
      this.mRangeCount = 1;
      if (this.mRange1_X_Min != this.mRange1_X_Max) {
        this.mIsSupportRange1_X = true;
      }
      if (this.mRange1_Y_Min != this.mRange1_Y_Max) {
        this.mIsSupportRange1_Y = true;
      }
      if (this.mRange1_Z_Min != this.mRange1_Z_Max) {
        this.mIsSupportRange1_Z = true;
      }
    }
    
    public Range(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12)
    {
      this(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
      this.mRange2_X_Min = paramInt7;
      this.mRange2_X_Max = paramInt8;
      this.mRange2_Y_Min = paramInt9;
      this.mRange2_Y_Max = paramInt10;
      this.mRange2_Z_Min = paramInt11;
      this.mRange2_Z_Max = paramInt12;
      this.mRangeCount = 2;
      if (this.mRange2_X_Min != this.mRange2_X_Max) {
        this.mIsSupportRange2_X = true;
      }
      if (this.mRange2_Y_Min != this.mRange2_Y_Max) {
        this.mIsSupportRange2_Y = true;
      }
      if (this.mRange2_Z_Min != this.mRange2_Z_Max) {
        this.mIsSupportRange2_Z = true;
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.SensorSpec
 * JD-Core Version:    0.7.1
 */