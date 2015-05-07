package com.sec.factory.modules;

import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.TestCase;

public class SensorCalculator
{
  private static String RESULT_VALUE_NG = "NG";
  private static String RESULT_VALUE_NOTSUPPORTED = "None";
  private static String RESULT_VALUE_OK = "OK";
  private static float mAccelerometerRawDataWeight = -1.0F;
  private static SensorSpec mSensorSpec;
  
  public static String checkSpecMagneticADC(int paramInt1, int paramInt2, int paramInt3)
  {
    FtUtil.log_d("SensorCalculator", "checkSpecMagneticADC", null);
    specLog(mSensorSpec.getSpecGeomagnetic_ADC(), "MagneticADC");
    return getResult(paramInt1, paramInt2, paramInt3, mSensorSpec.getSpecGeomagnetic_ADC());
  }
  
  public static String checkSpecMagneticDAC(int paramInt1, int paramInt2, int paramInt3)
  {
    FtUtil.log_d("SensorCalculator", "checkSpecMagneticDAC", null);
    specLog(mSensorSpec.getSpecGeomagnetic_DAC(), "MagneticDAC");
    return getResult(paramInt1, paramInt2, paramInt3, mSensorSpec.getSpecGeomagnetic_DAC());
  }
  
  public static String checkSpecMagneticSelf(int paramInt1, int paramInt2, int paramInt3)
  {
    FtUtil.log_d("SensorCalculator", "checkSpecMagneticSelf", null);
    specLog(mSensorSpec.getSpecGeomagnetic_Self(), "MagneticSelf");
    return getResult(paramInt1, paramInt2, paramInt3, mSensorSpec.getSpecGeomagnetic_Self());
  }
  
  public static String[] getAccelerometerAngle(int[] paramArrayOfInt)
  {
    String[] arrayOfString = new String[3];
    float f = (float)Math.sqrt(paramArrayOfInt[0] * paramArrayOfInt[0] + paramArrayOfInt[1] * paramArrayOfInt[1] + paramArrayOfInt[2] * paramArrayOfInt[2]);
    arrayOfString[0] = ("" + (int)(57.29578F * (float)Math.asin(paramArrayOfInt[0] / f)));
    arrayOfString[1] = ("" + (int)(57.29578F * (float)Math.asin(paramArrayOfInt[1] / f)));
    arrayOfString[2] = ("" + (int)(-1.0F * (57.29578F * (float)Math.acos(paramArrayOfInt[2] / f) - 90.0F)));
    return arrayOfString;
  }
  
  public static float getAccelerometerAngleDeviation(float paramFloat)
  {
    return Math.abs(paramFloat - 9.80665F);
  }
  
  public static float getAccelerometerAngleMagnitude(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2 + paramFloat3 * paramFloat3);
  }
  
  public static int getAccelerometerAngleXY(float paramFloat1, float paramFloat2)
  {
    return (int)(57.29578F * (float)-Math.atan2(paramFloat1, paramFloat2));
  }
  
  private static float getAccelerometerRawDataWeight(int paramInt)
  {
    return (float)Math.pow(2.0D, paramInt - 2) / 9.80665F;
  }
  
  public static float getAccelerometerRawDataWeight_Feature()
  {
    String str;
    if (mAccelerometerRawDataWeight < 0.0F)
    {
      str = Support.TestCase.getString("SENSOR_TEST_ACC_BIT");
      if (!str.equals("")) {
        break label40;
      }
      FtUtil.log_d("SensorCalculator", "getAccelerometerRawDataWeight_Feature", " bitCount(feature) : None => return 0");
    }
    for (mAccelerometerRawDataWeight = 0.0F;; mAccelerometerRawDataWeight = getAccelerometerRawDataWeight(Integer.valueOf(str).intValue()))
    {
      return mAccelerometerRawDataWeight;
      label40:
      FtUtil.log_d("SensorCalculator", "getAccelerometerRawDataWeight_Feature", " bitCount(feature) : " + str);
    }
  }
  
  public static float getAccelerometerRawDataWeight_Spec()
  {
    if (mAccelerometerRawDataWeight < 0.0F)
    {
      if (mSensorSpec.getSpecAccel() == null) {
        break label70;
      }
      FtUtil.log_d("SensorCalculator", "getAccelerometerRawDataWeight_Spec", "bitCount(Spec) : " + mSensorSpec.getSpecAccel().mBitCount);
    }
    for (mAccelerometerRawDataWeight = getAccelerometerRawDataWeight(mSensorSpec.getSpecAccel().mBitCount);; mAccelerometerRawDataWeight = 0.0F)
    {
      return mAccelerometerRawDataWeight;
      label70:
      FtUtil.log_e("SensorCalculator", "getAccelerometerRawDataWeight_Spec", "bitCount(Spec) : None => return 0");
    }
  }
  
  private static String getResult(int paramInt1, int paramInt2, int paramInt3, SensorSpec.Range paramRange)
  {
    if (paramRange != null)
    {
      if (paramRange.mRangeCount == 1)
      {
        FtUtil.log_e("SensorCalculator", "getResult", "mRangeCount : 1");
        if ((paramRange.mIsSupportRange1_X) && (!isSpecIn(paramInt1, paramRange.mRange1_X_Min, paramRange.mRange1_X_Max)))
        {
          FtUtil.log_e("SensorCalculator", "getResult", "Fail - X");
          return RESULT_VALUE_NG;
        }
        if ((paramRange.mIsSupportRange1_Y) && (!isSpecIn(paramInt2, paramRange.mRange1_Y_Min, paramRange.mRange1_Y_Max)))
        {
          FtUtil.log_e("SensorCalculator", "getResult", "Fail - Y");
          return RESULT_VALUE_NG;
        }
        if ((paramRange.mIsSupportRange1_Z) && (!isSpecIn(paramInt3, paramRange.mRange1_Z_Min, paramRange.mRange1_Z_Max)))
        {
          FtUtil.log_e("SensorCalculator", "getResult", "Fail - Z");
          return RESULT_VALUE_NG;
        }
        return RESULT_VALUE_OK;
      }
      if (paramRange.mRangeCount == 2)
      {
        FtUtil.log_e("SensorCalculator", "getResult", "mRangeCount : 2");
        if ((paramRange.mIsSupportRange1_X) && (paramRange.mIsSupportRange2_X))
        {
          if ((!isSpecIn(paramInt1, paramRange.mRange1_X_Min, paramRange.mRange1_X_Max)) && (!isSpecIn(paramInt1, paramRange.mRange2_X_Min, paramRange.mRange2_X_Max)))
          {
            FtUtil.log_e("SensorCalculator", "getResult", "[All] Fail - X");
            return RESULT_VALUE_NG;
          }
        }
        else if ((paramRange.mIsSupportRange1_X) && (!paramRange.mIsSupportRange2_X))
        {
          if (!isSpecIn(paramInt1, paramRange.mRange1_X_Min, paramRange.mRange1_X_Max))
          {
            FtUtil.log_e("SensorCalculator", "getResult", "[Range1] Fail - X");
            return RESULT_VALUE_NG;
          }
        }
        else if ((!paramRange.mIsSupportRange1_X) && (paramRange.mIsSupportRange2_X) && (!isSpecIn(paramInt1, paramRange.mRange2_X_Min, paramRange.mRange2_X_Max)))
        {
          FtUtil.log_e("SensorCalculator", "getResult", "[Range2] Fail - X");
          return RESULT_VALUE_NG;
        }
        if ((paramRange.mIsSupportRange1_Y) && (paramRange.mIsSupportRange2_Y))
        {
          if ((!isSpecIn(paramInt2, paramRange.mRange1_Y_Min, paramRange.mRange1_Y_Max)) && (!isSpecIn(paramInt2, paramRange.mRange2_Y_Min, paramRange.mRange2_Y_Max)))
          {
            FtUtil.log_e("SensorCalculator", "getResult", "[All] Fail - Y");
            return RESULT_VALUE_NG;
          }
        }
        else if ((paramRange.mIsSupportRange1_Y) && (!paramRange.mIsSupportRange2_Y))
        {
          if (!isSpecIn(paramInt2, paramRange.mRange1_Y_Min, paramRange.mRange1_Y_Max))
          {
            FtUtil.log_e("SensorCalculator", "getResult", "[Range1] Fail - Y");
            return RESULT_VALUE_NG;
          }
        }
        else if ((!paramRange.mIsSupportRange1_Y) && (paramRange.mIsSupportRange2_Y) && (!isSpecIn(paramInt2, paramRange.mRange2_Y_Min, paramRange.mRange2_Y_Max)))
        {
          FtUtil.log_e("SensorCalculator", "getResult", "[Range2] Fail - Y");
          return RESULT_VALUE_NG;
        }
        if ((paramRange.mIsSupportRange1_Z) && (paramRange.mIsSupportRange2_Z))
        {
          if ((!isSpecIn(paramInt3, paramRange.mRange1_Z_Min, paramRange.mRange1_Z_Max)) && (!isSpecIn(paramInt3, paramRange.mRange2_Z_Min, paramRange.mRange2_Z_Max)))
          {
            FtUtil.log_e("SensorCalculator", "getResult", "[All] Fail - Z");
            return RESULT_VALUE_NG;
          }
        }
        else if ((paramRange.mIsSupportRange1_Z) && (!paramRange.mIsSupportRange2_Z))
        {
          if (!isSpecIn(paramInt3, paramRange.mRange1_Z_Min, paramRange.mRange1_Z_Max))
          {
            FtUtil.log_e("SensorCalculator", "getResult", "[Range1] Fail - Z");
            return RESULT_VALUE_NG;
          }
        }
        else if ((!paramRange.mIsSupportRange1_Z) && (paramRange.mIsSupportRange2_Z) && (!isSpecIn(paramInt3, paramRange.mRange2_Z_Min, paramRange.mRange2_Z_Max)))
        {
          FtUtil.log_e("SensorCalculator", "getResult", "[Range2] Fail - Z");
          return RESULT_VALUE_NG;
        }
        return RESULT_VALUE_OK;
      }
      FtUtil.log_e("SensorCalculator", "getResult", "mRangeCount : Unknown");
      return RESULT_VALUE_NOTSUPPORTED;
    }
    FtUtil.log_e("SensorCalculator", "getResult", "Fail - Spec null");
    return RESULT_VALUE_NOTSUPPORTED;
  }
  
  public static String getResultAccelerometerSelf(int paramInt1, int paramInt2, int paramInt3)
  {
    specLog(mSensorSpec.getSpecAccel(), "AccelSelf");
    SensorSpec.Accelerometer localAccelerometer = mSensorSpec.getSpecAccel();
    if (localAccelerometer != null)
    {
      String str1;
      label137:
      String str2;
      label192:
      label251:
      String str3;
      if (localAccelerometer.mRange.mIsSupportRange1_X) {
        if ((paramInt1 < localAccelerometer.mRange.mRange1_X_Min) || (localAccelerometer.mRange.mRange1_X_Max < paramInt1))
        {
          str1 = "" + "F";
          FtUtil.log_e("SensorCalculator", "getResultAccelerometerSelf", "" + localAccelerometer.mRange.mRange1_X_Min + " [" + paramInt1 + "] " + localAccelerometer.mRange.mRange1_X_Max);
          if (!localAccelerometer.mRange.mIsSupportRange1_Y) {
            break label456;
          }
          if ((paramInt2 >= localAccelerometer.mRange.mRange1_Y_Min) && (localAccelerometer.mRange.mRange1_Y_Max >= paramInt2)) {
            break label430;
          }
          str2 = str1 + "F";
          FtUtil.log_e("SensorCalculator", "getResultAccelerometerSelf", "" + localAccelerometer.mRange.mRange1_Y_Min + " [" + paramInt2 + "] " + localAccelerometer.mRange.mRange1_Y_Max);
          if (!localAccelerometer.mRange.mIsSupportRange1_Z) {
            break label508;
          }
          if ((paramInt3 >= localAccelerometer.mRange.mRange1_Z_Min) && (localAccelerometer.mRange.mRange1_Z_Max >= paramInt3)) {
            break label482;
          }
          str3 = str2 + "F";
          label306:
          FtUtil.log_e("SensorCalculator", "getResultAccelerometerSelf", "" + localAccelerometer.mRange.mRange1_Z_Min + " [" + paramInt3 + "] " + localAccelerometer.mRange.mRange1_Z_Max);
        }
      }
      for (;;)
      {
        FtUtil.log_e("SensorCalculator", "getResultAccelerometerSelf", str3);
        return str3;
        str1 = "" + "P";
        break;
        str1 = "" + "P";
        break label137;
        label430:
        str2 = str1 + "P";
        break label192;
        label456:
        str2 = str1 + "P";
        break label251;
        label482:
        str3 = str2 + "P";
        break label306;
        label508:
        str3 = str2 + "P";
      }
    }
    FtUtil.log_e("SensorCalculator", "getResultAccelerometerSelf", "FFF - Spec null");
    return "FFF";
  }
  
  public static void initialize()
  {
    FtUtil.log_d("SensorCalculator", "initialize", null);
    mSensorSpec = new SensorSpec();
    specLog(mSensorSpec.getSpecAccel(), "AccelSelf");
    specLog(mSensorSpec.getSpecGeomagnetic_DAC(), "MagneticDAC");
    specLog(mSensorSpec.getSpecGeomagnetic_ADC(), "MagneticADC");
    specLog(mSensorSpec.getSpecGeomagnetic_ADC2(), "MagneticADC2");
    specLog(mSensorSpec.getSpecGeomagnetic_Self(), "MagneticSelf");
  }
  
  private static boolean isSpecIn(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt2 <= paramInt1) && (paramInt1 <= paramInt3))
    {
      FtUtil.log_e("SensorCalculator", "isSpecIn", "Pass => [" + paramInt2 + " <= (" + paramInt1 + ") <= " + paramInt3 + "]");
      return true;
    }
    FtUtil.log_e("SensorCalculator", "isSpecIn", "Fail => [" + paramInt2 + " <= (" + paramInt1 + ") <= " + paramInt3 + "]");
    return false;
  }
  
  private static void specLog(SensorSpec.Accelerometer paramAccelerometer, String paramString)
  {
    if (paramAccelerometer != null)
    {
      specLog(paramAccelerometer.mRange, paramString);
      return;
    }
    specLog((SensorSpec.Range)null, paramString);
  }
  
  private static void specLog(SensorSpec.Range paramRange, String paramString)
  {
    String str1 = "<" + paramString + "> ";
    String str3;
    String str4;
    label142:
    String str2;
    if (paramRange != null) {
      if (paramRange.mIsSupportRange1_X)
      {
        str3 = str1 + "X(" + paramRange.mRange1_X_Min + "," + paramRange.mRange1_X_Max + ") , ";
        if (!paramRange.mIsSupportRange1_X) {
          break label232;
        }
        str4 = str3 + "Y(" + paramRange.mRange1_Y_Min + "," + paramRange.mRange1_Y_Max + ") , ";
        if (!paramRange.mIsSupportRange1_X) {
          break label258;
        }
        str2 = str4 + "Z(" + paramRange.mRange1_Z_Min + "," + paramRange.mRange1_Z_Max + ")";
      }
    }
    for (;;)
    {
      FtUtil.log_d("SensorCalculator", "specLog", str2);
      return;
      str3 = str1 + "X(not supported) , ";
      break;
      label232:
      str4 = str3 + "Y(not supported) , ";
      break label142;
      label258:
      str2 = str4 + "Z(not supported)";
      continue;
      str2 = "<" + paramString + "> null";
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.SensorCalculator
 * JD-Core Version:    0.7.1
 */