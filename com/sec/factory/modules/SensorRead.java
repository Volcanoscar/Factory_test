package com.sec.factory.modules;

import android.content.Context;
import android.hardware.SensorManager;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Kernel;
import com.sec.factory.support.Support.TestCase;
import java.text.DecimalFormat;

public class SensorRead
{
  private final String CLASS_NAME = "SensorRead";
  private final int RETURN_DATA_ARRAY_SIZE_MAX = 16;
  private String mAccelBitCntXML = Support.TestCase.getString("SENSOR_TEST_ACC_BIT");
  private String mFeature_Gyroscope = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_GYRO, SensorDeviceInfo.TARGET_XML);
  private String mFeature_Magnetic = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_GEOMAGNETIC, SensorDeviceInfo.TARGET_XML);
  private boolean mIsAccelReverse = Support.TestCase.getEnabled("IS_SENSOR_TEST_ACC_REVERSE");
  private String[] mReturnData = new String[16];
  private SensorReadFile mSensorReadFile = null;
  private SensorReadIntent mSensorReadIntent = null;
  private SensorReadManager mSensorReadManager = null;
  private float[] mTemp_Float = null;
  private int[] mTemp_Int = new int[3];
  private SensorReadManager.MagneticExpansionData mTemp_Magnetic = null;
  private String[] mTemp_String = null;
  
  private String dataCheck(String[] paramArrayOfString)
  {
    String str = "";
    int i = Integer.parseInt(paramArrayOfString[0]);
    if (ModuleSensor.LOG_LEVEL <= 1) {
      FtUtil.log_d("SensorRead", "dataCheck", "length : " + i);
    }
    if (paramArrayOfString != null) {
      for (int j = 0; j < i + 1; j++)
      {
        str = str + paramArrayOfString[j];
        if (j < i) {
          str = str + " , ";
        }
      }
    }
    return str;
  }
  
  private void replaceData(String[] paramArrayOfString)
  {
    int i = Integer.parseInt(paramArrayOfString[0]);
    if (paramArrayOfString != null) {
      for (int j = 0; j < i + 1; j++) {
        paramArrayOfString[j] = paramArrayOfString[j].replaceAll(",", ".");
      }
    }
    FtUtil.log_d("SensorRead", "replaceData", "data : " + dataCheck(paramArrayOfString));
  }
  
  public void SensorOff()
  {
    if (this.mSensorReadFile != null)
    {
      this.mSensorReadFile.sensorOff();
      this.mSensorReadFile = null;
      FtUtil.log_e("SensorRead", "SensorOff", "File");
    }
    if (this.mSensorReadManager != null)
    {
      this.mSensorReadManager.sensorOff();
      this.mSensorReadManager = null;
      FtUtil.log_e("SensorRead", "SensorOff", "Manager");
    }
  }
  
  public void SensorOff_Intent(int paramInt)
  {
    if (((paramInt == ModuleSensor.ID_INTENT__CP_ACCELEROMETER) || (paramInt == ModuleSensor.ID_INTENT__GRIP)) && (this.mSensorReadIntent != null))
    {
      FtUtil.log_e("SensorRead", "SensorOff_Intent", "SensorOff_Intent");
      if (paramInt != ModuleSensor.ID_INTENT__GRIP) {
        break label50;
      }
      this.mSensorReadIntent.sensorOff();
    }
    for (;;)
    {
      this.mSensorReadIntent = null;
      return;
      label50:
      if (paramInt == ModuleSensor.ID_INTENT__CP_ACCELEROMETER) {
        this.mSensorReadIntent.disableReceiver_CPsAccelerometer();
      }
    }
  }
  
  public void SensorOn(Context paramContext, SensorManager paramSensorManager, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    if (paramArrayOfInt1 != null) {
      this.mSensorReadManager = new SensorReadManager(paramArrayOfInt1, paramSensorManager);
    }
    if (paramArrayOfInt2 != null) {
      this.mSensorReadFile = new SensorReadFile(paramArrayOfInt2);
    }
    if (paramArrayOfInt3 != null) {
      this.mSensorReadIntent = new SensorReadIntent(paramContext, paramArrayOfInt3);
    }
    FtUtil.log_d("SensorRead", "SensorOn", "mAccelBitCntXML : " + this.mAccelBitCntXML);
    FtUtil.log_d("SensorRead", "SensorOn", "mIsAccelReverse : " + this.mIsAccelReverse);
  }
  
  public String[] getAccelermeterCal(int paramInt)
  {
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {}
    do
    {
      do
      {
        return null;
      } while ((paramInt != 2) || (this.mSensorReadFile == null));
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____ACCELEROMETER_CAL);
      if (this.mTemp_String != null)
      {
        if (this.mTemp_String.length <= 1) {
          this.mTemp_String = this.mTemp_String[0].split(" ");
        }
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getAccelermeterCal", "Count : " + this.mTemp_String.length);
        }
        this.mReturnData[0] = "4";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = this.mTemp_String[0];
        this.mReturnData[3] = this.mTemp_String[1];
        this.mReturnData[4] = this.mTemp_String[2];
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getAccelermeterCal", dataCheck(this.mReturnData));
        }
        return this.mReturnData;
      }
    } while (ModuleSensor.LOG_LEVEL > 2);
    FtUtil.log_d("SensorRead", "getAccelermeterCal", "null");
    return null;
  }
  
  public String[] getAccelermeterIntpin(int paramInt)
  {
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {}
    do
    {
      do
      {
        return null;
      } while ((paramInt != 2) || (this.mSensorReadFile == null));
      if (Support.Kernel.write("ACCEL_SENSOR_INTPIN", "2")) {}
      try
      {
        Thread.sleep(300L);
        this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____ACCELEROMETER_INTPIN);
        if (this.mTemp_String != null)
        {
          if (ModuleSensor.LOG_LEVEL <= 1) {
            FtUtil.log_d("SensorRead", "getAccelermeterIntpin", "Count : " + this.mTemp_String.length);
          }
          this.mReturnData[0] = "2";
          this.mReturnData[1] = "None";
          this.mReturnData[2] = this.mTemp_String[0];
          Support.Kernel.write("ACCEL_SENSOR_INTPIN", "0");
          if (ModuleSensor.LOG_LEVEL <= 2) {
            FtUtil.log_d("SensorRead", "getAccelermeterIntpin", dataCheck(this.mReturnData));
          }
          return this.mReturnData;
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;)
        {
          localInterruptedException.printStackTrace();
        }
      }
    } while (ModuleSensor.LOG_LEVEL > 2);
    FtUtil.log_d("SensorRead", "getAccelermeterIntpin", "null");
    return null;
  }
  
  public String[] getAccelermeterSelf(int paramInt)
  {
    this.mTemp_String = getAccelermeterXYZ(paramInt);
    if (this.mTemp_String != null)
    {
      this.mTemp_Int[0] = Integer.parseInt(this.mTemp_String[2].trim());
      this.mTemp_Int[1] = Integer.parseInt(this.mTemp_String[3].trim());
      this.mTemp_Int[2] = Integer.parseInt(this.mTemp_String[4].trim());
      this.mReturnData[0] = "2";
      this.mReturnData[1] = "None";
      this.mReturnData[2] = SensorCalculator.getResultAccelerometerSelf(this.mTemp_Int[0], this.mTemp_Int[1], this.mTemp_Int[2]);
      if (ModuleSensor.LOG_LEVEL <= 2) {
        FtUtil.log_d("SensorRead", "getAccelermeterSelf", dataCheck(this.mReturnData));
      }
      return this.mReturnData;
    }
    if (ModuleSensor.LOG_LEVEL <= 2) {
      FtUtil.log_d("SensorRead", "getAccelermeterSelf", "null");
    }
    return null;
  }
  
  public String[] getAccelermeterXYZ(int paramInt)
  {
    double d;
    label238:
    String[] arrayOfString;
    if ((paramInt == 1) && (this.mSensorReadManager != null))
    {
      this.mTemp_Float = this.mSensorReadManager.returnAccelermeter();
      if (this.mTemp_Float != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getAccelermeterXYZ", " Count : " + this.mTemp_Float.length);
        }
        this.mReturnData[0] = "4";
        this.mReturnData[1] = "None";
        if (this.mAccelBitCntXML.equals(""))
        {
          if (ModuleSensor.LOG_LEVEL <= 1) {
            FtUtil.log_d("SensorRead", "getAccelermeterXYZ", " bitCount(feature) : Unknown");
          }
          d = SensorCalculator.getAccelerometerRawDataWeight_Spec();
          if (ModuleSensor.LOG_LEVEL <= 1) {
            FtUtil.log_d("SensorRead", "getAccelermeterXYZ", " Weight : " + d);
          }
          if (!this.mIsAccelReverse) {
            break label330;
          }
          this.mReturnData[2] = ("" + -1 * (int)(d * this.mTemp_Float[1]));
          this.mReturnData[3] = ("" + -1 * (int)(d * this.mTemp_Float[0]));
          this.mReturnData[4] = ("" + (int)(d * this.mTemp_Float[2]));
          label273:
          if (ModuleSensor.LOG_LEVEL <= 2) {
            FtUtil.log_d("SensorRead", "getAccelermeterXYZ", dataCheck(this.mReturnData));
          }
          arrayOfString = this.mReturnData;
        }
      }
    }
    label330:
    int i;
    do
    {
      SensorReadFile localSensorReadFile;
      do
      {
        do
        {
          int j;
          do
          {
            return arrayOfString;
            if (ModuleSensor.LOG_LEVEL <= 1) {
              FtUtil.log_d("SensorRead", "getAccelermeterXYZ", " bitCount(feature) : Known");
            }
            d = SensorCalculator.getAccelerometerRawDataWeight_Feature();
            break;
            this.mReturnData[2] = ("" + (int)(d * this.mTemp_Float[0]));
            this.mReturnData[3] = ("" + (int)(d * this.mTemp_Float[1]));
            break label238;
            j = ModuleSensor.LOG_LEVEL;
            arrayOfString = null;
          } while (j > 2);
          FtUtil.log_d("SensorRead", "getAccelermeterXYZ", "null");
          return null;
          arrayOfString = null;
        } while (paramInt != 2);
        localSensorReadFile = this.mSensorReadFile;
        arrayOfString = null;
      } while (localSensorReadFile == null);
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____ACCELEROMETER);
      if (this.mTemp_String != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getAccelermeterXYZ", "Count : " + this.mTemp_String.length);
        }
        this.mReturnData[0] = "4";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = this.mTemp_String[0];
        this.mReturnData[3] = this.mTemp_String[1];
        this.mReturnData[4] = this.mTemp_String[2];
        break label273;
      }
      i = ModuleSensor.LOG_LEVEL;
      arrayOfString = null;
    } while (i > 2);
    FtUtil.log_d("SensorRead", "getAccelermeterXYZ", "null");
    return null;
  }
  
  public String[] getAccelermeterXYZnAngle(int paramInt)
  {
    this.mTemp_String = getAccelermeterXYZ(paramInt);
    if (this.mTemp_String != null)
    {
      this.mTemp_Int[0] = Integer.parseInt(this.mTemp_String[2].trim());
      this.mTemp_Int[1] = Integer.parseInt(this.mTemp_String[3].trim());
      this.mTemp_Int[2] = Integer.parseInt(this.mTemp_String[4].trim());
      this.mTemp_String = SensorCalculator.getAccelerometerAngle(this.mTemp_Int);
      if ((paramInt != 1) || (this.mSensorReadManager == null)) {
        break label425;
      }
      this.mTemp_Float = this.mSensorReadManager.returnAccelermeter();
      if ((this.mTemp_Float != null) && (this.mTemp_String != null))
      {
        this.mReturnData[0] = "10";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = ("" + this.mTemp_Int[0]);
        this.mReturnData[3] = ("" + this.mTemp_Int[1]);
        this.mReturnData[4] = ("" + this.mTemp_Int[2]);
        this.mReturnData[5] = this.mTemp_String[0];
        this.mReturnData[6] = this.mTemp_String[1];
        this.mReturnData[7] = this.mTemp_String[2];
        this.mReturnData[8] = ("" + SensorCalculator.getAccelerometerAngleMagnitude(this.mTemp_Float[0], this.mTemp_Float[1], this.mTemp_Float[2]));
        this.mReturnData[9] = ("" + SensorCalculator.getAccelerometerAngleDeviation(Float.valueOf(this.mReturnData[8]).floatValue()));
        this.mReturnData[10] = ("" + SensorCalculator.getAccelerometerAngleXY(this.mTemp_Float[0], this.mTemp_Float[1]));
      }
    }
    for (;;)
    {
      if (ModuleSensor.LOG_LEVEL <= 3) {
        FtUtil.log_d("SensorRead", "getAccelermeterSelf", dataCheck(this.mReturnData));
      }
      return this.mReturnData;
      if (paramInt == 3) {
        break;
      }
      return null;
      label425:
      if ((paramInt != 2) || (this.mSensorReadFile == null)) {
        break label634;
      }
      if (this.mTemp_String == null) {
        break label615;
      }
      this.mReturnData[0] = "7";
      this.mReturnData[1] = "None";
      this.mReturnData[2] = ("" + this.mTemp_Int[0]);
      this.mReturnData[3] = ("" + this.mTemp_Int[1]);
      this.mReturnData[4] = ("" + this.mTemp_Int[2]);
      this.mReturnData[5] = this.mTemp_String[0];
      this.mReturnData[6] = this.mTemp_String[1];
      this.mReturnData[7] = this.mTemp_String[2];
      if (ModuleSensor.LOG_LEVEL <= 2) {
        FtUtil.log_d("SensorRead", "getAccelermeterXYZNAngle", dataCheck(this.mReturnData));
      }
    }
    label615:
    if (ModuleSensor.LOG_LEVEL <= 2) {
      FtUtil.log_d("SensorRead", "getAccelermeterXYZnAngle", "null");
    }
    return null;
    label634:
    if ((paramInt == 3) && (this.mSensorReadIntent != null))
    {
      this.mTemp_Int = this.mSensorReadIntent.returnCPsAccelerometerData();
      if (this.mTemp_Int != null) {
        this.mTemp_String = SensorCalculator.getAccelerometerAngle(this.mTemp_Int);
      }
      if ((this.mTemp_Int != null) && (this.mTemp_String != null))
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getAccelermeterXYZnAngle", "mTemp_Float.length : " + this.mTemp_Float.length);
        }
        this.mReturnData[0] = "10";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = ("" + this.mTemp_Int[0]);
        this.mReturnData[3] = ("" + this.mTemp_Int[1]);
        this.mReturnData[4] = ("" + this.mTemp_Int[2]);
        this.mReturnData[5] = this.mTemp_String[0];
        this.mReturnData[6] = this.mTemp_String[1];
        this.mReturnData[7] = this.mTemp_String[2];
        if ("12".equals(Support.TestCase.getString("SENSOR_TEST_ACC_BIT"))) {}
        for (double d = 104.48979591836734D;; d = 24.0D)
        {
          if (this.mTemp_Float == null) {
            this.mTemp_Float = new float[3];
          }
          this.mTemp_Float[0] = (-1.0F * (float)(this.mTemp_Int[1] / d));
          this.mTemp_Float[1] = (-1.0F * (float)(this.mTemp_Int[0] / d));
          this.mTemp_Float[2] = ((float)(this.mTemp_Int[2] / d));
          this.mReturnData[8] = ("" + SensorCalculator.getAccelerometerAngleMagnitude(this.mTemp_Float[0], this.mTemp_Float[1], this.mTemp_Float[2]));
          this.mReturnData[9] = ("" + SensorCalculator.getAccelerometerAngleDeviation(Float.valueOf(this.mReturnData[8]).floatValue()));
          this.mReturnData[10] = ("" + SensorCalculator.getAccelerometerAngleXY(this.mTemp_Float[0], this.mTemp_Float[1]));
          FtUtil.log_i("SensorRead", "getAccelermeterXYZnAngle", "CP: coordinates_x :" + this.mReturnData[2] + ", coordinates_y :" + this.mReturnData[3] + ", coordinates_z :" + this.mReturnData[4]);
          FtUtil.log_i("SensorRead", "getAccelermeterXYZnAngle", "CP: X_Angle :" + this.mReturnData[5] + ", Y_Angle :" + this.mReturnData[6] + ", z_Angle :" + this.mReturnData[7]);
          FtUtil.log_i("SensorRead", "getAccelermeterXYZnAngle", "CP: magnitude :" + this.mReturnData[8] + ", deviation :" + this.mReturnData[9]);
          FtUtil.log_i("SensorRead", "getAccelermeterXYZnAngle", "CP: XY_value :" + this.mReturnData[10]);
          break;
        }
      }
      if (ModuleSensor.LOG_LEVEL <= 2) {
        FtUtil.log_d("SensorRead", "getAccelermeterXYZnAngle", "null");
      }
      return null;
    }
    return null;
  }
  
  public String[] getBarometer(int paramInt)
  {
    String[] arrayOfString;
    if ((paramInt == 1) && (this.mSensorReadManager != null))
    {
      this.mTemp_Float = this.mSensorReadManager.returnBarometer();
      if (this.mTemp_Float != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getBarometer", "Count : " + this.mTemp_Float.length);
        }
        DecimalFormat localDecimalFormat = new DecimalFormat("#.##");
        this.mReturnData[0] = "4";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = String.valueOf(localDecimalFormat.format(this.mTemp_Float[0]));
        this.mReturnData[3] = String.valueOf(localDecimalFormat.format(this.mTemp_Float[1]));
        this.mReturnData[4] = String.valueOf(localDecimalFormat.format(this.mTemp_Float[2]));
        replaceData(this.mReturnData);
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getBarometer", dataCheck(this.mReturnData));
        }
        arrayOfString = this.mReturnData;
      }
    }
    SensorReadFile localSensorReadFile;
    do
    {
      do
      {
        int i;
        do
        {
          return arrayOfString;
          i = ModuleSensor.LOG_LEVEL;
          arrayOfString = null;
        } while (i > 2);
        FtUtil.log_d("SensorRead", "getBarometer", "null");
        return null;
        arrayOfString = null;
      } while (paramInt != 2);
      localSensorReadFile = this.mSensorReadFile;
      arrayOfString = null;
    } while (localSensorReadFile == null);
    return null;
  }
  
  public String[] getBarometerEEPROM(int paramInt)
  {
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {}
    do
    {
      do
      {
        return null;
      } while ((paramInt != 2) || (this.mSensorReadFile == null));
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____BAROMETER_EEPROM);
      if (this.mTemp_String != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getBarometerEEPROM", "Count : " + this.mTemp_String.length);
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = this.mTemp_String[0];
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getBarometerEEPROM", dataCheck(this.mReturnData));
        }
        return this.mReturnData;
      }
    } while (ModuleSensor.LOG_LEVEL > 2);
    FtUtil.log_d("SensorRead", "getBarometerEEPROM", "null");
    return null;
  }
  
  public String[] getGrip(int paramInt)
  {
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {}
    do
    {
      do
      {
        return null;
      } while (((paramInt == 2) && (this.mSensorReadFile != null)) || (paramInt != 3) || (this.mSensorReadIntent == null));
      this.mTemp_Int = this.mSensorReadIntent.returnGrip();
      if (this.mTemp_Int != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getGrip", "Count : " + this.mTemp_Int.length);
        }
        this.mReturnData[0] = "3";
        this.mReturnData[1] = ("" + this.mTemp_Int[0]);
        this.mReturnData[2] = ("" + this.mTemp_Int[1]);
        this.mReturnData[3] = ("" + this.mTemp_Int[2]);
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getGyro", dataCheck(this.mReturnData));
        }
        return this.mReturnData;
      }
    } while (ModuleSensor.LOG_LEVEL > 2);
    FtUtil.log_d("SensorRead", "getGrip", "null");
    return null;
  }
  
  public String[] getGyro(int paramInt)
  {
    String[] arrayOfString;
    if ((paramInt == 1) && (this.mSensorReadManager != null))
    {
      this.mTemp_Float = this.mSensorReadManager.returnGyro();
      if (this.mTemp_Float != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getGyro", "Count : " + this.mTemp_Float.length);
        }
        this.mReturnData[0] = "4";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = ("" + 57.295776F * this.mTemp_Float[0]);
        this.mReturnData[3] = ("" + 57.295776F * this.mTemp_Float[1]);
        this.mReturnData[4] = ("" + 57.295776F * this.mTemp_Float[2]);
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getGyro", dataCheck(this.mReturnData));
        }
        arrayOfString = this.mReturnData;
      }
    }
    SensorReadFile localSensorReadFile;
    do
    {
      do
      {
        int i;
        do
        {
          return arrayOfString;
          i = ModuleSensor.LOG_LEVEL;
          arrayOfString = null;
        } while (i > 2);
        FtUtil.log_d("SensorRead", "getGyro", "null");
        return null;
        arrayOfString = null;
      } while (paramInt != 2);
      localSensorReadFile = this.mSensorReadFile;
      arrayOfString = null;
    } while (localSensorReadFile == null);
    return null;
  }
  
  public String[] getGyroExpansion(int paramInt)
  {
    SensorReadManager.GyroExpansionData localGyroExpansionData;
    String[] arrayOfString;
    if ((paramInt == 1) && (this.mSensorReadManager != null))
    {
      localGyroExpansionData = this.mSensorReadManager.returnGyroExpansion();
      if ((localGyroExpansionData.mNoiseBias != null) && (localGyroExpansionData.mData != null) && (localGyroExpansionData.mRMSValue != null))
      {
        this.mReturnData[0] = "10";
        this.mReturnData[1] = ("" + localGyroExpansionData.mReturnValue);
        this.mReturnData[2] = ("" + localGyroExpansionData.mNoiseBias[0]);
        this.mReturnData[3] = ("" + localGyroExpansionData.mNoiseBias[1]);
        this.mReturnData[4] = ("" + localGyroExpansionData.mNoiseBias[2]);
        this.mReturnData[5] = ("" + localGyroExpansionData.mData[0]);
        this.mReturnData[6] = ("" + localGyroExpansionData.mData[1]);
        this.mReturnData[7] = ("" + localGyroExpansionData.mData[2]);
        this.mReturnData[8] = ("" + localGyroExpansionData.mRMSValue[0]);
        this.mReturnData[9] = ("" + localGyroExpansionData.mRMSValue[1]);
        this.mReturnData[10] = ("" + localGyroExpansionData.mRMSValue[2]);
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getGyroExpansion", dataCheck(this.mReturnData));
        }
        arrayOfString = this.mReturnData;
      }
    }
    SensorReadFile localSensorReadFile;
    do
    {
      do
      {
        float[] arrayOfFloat;
        do
        {
          int i;
          do
          {
            return arrayOfString;
            i = ModuleSensor.LOG_LEVEL;
            arrayOfString = null;
          } while (i > 2);
          if (localGyroExpansionData.mNoiseBias == null) {
            FtUtil.log_d("SensorRead", "getGyroExpansion", "Noise Bias null");
          }
          if (localGyroExpansionData.mData == null) {
            FtUtil.log_d("SensorRead", "getGyroExpansion", "Data null");
          }
          arrayOfFloat = localGyroExpansionData.mRMSValue;
          arrayOfString = null;
        } while (arrayOfFloat != null);
        FtUtil.log_d("SensorRead", "getGyroExpansion", "RMS null");
        return null;
        arrayOfString = null;
      } while (paramInt != 2);
      localSensorReadFile = this.mSensorReadFile;
      arrayOfString = null;
    } while (localSensorReadFile == null);
    return null;
  }
  
  public String[] getGyroPower(int paramInt)
  {
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {}
    do
    {
      do
      {
        return null;
      } while ((paramInt != 2) || (this.mSensorReadFile == null));
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____GYRO_POWER);
      if (this.mTemp_String != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getGyroPower", "Count : " + this.mTemp_String.length);
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = this.mTemp_String[0];
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getGyroPower", dataCheck(this.mReturnData));
        }
        return this.mReturnData;
      }
    } while (ModuleSensor.LOG_LEVEL > 2);
    FtUtil.log_d("SensorRead", "getGyroPower", "null");
    return null;
  }
  
  public String[] getGyroSelf(int paramInt)
  {
    SensorReadManager.GyroExpansionData localGyroExpansionData;
    if ((paramInt == 1) && (this.mSensorReadManager != null))
    {
      localGyroExpansionData = this.mSensorReadManager.returnGyroExpansion();
      if ((localGyroExpansionData.mNoiseBias != null) && (localGyroExpansionData.mRMSValue != null))
      {
        this.mReturnData[0] = "7";
        this.mReturnData[1] = ("" + localGyroExpansionData.mReturnValue);
        this.mReturnData[2] = ("" + localGyroExpansionData.mNoiseBias[0]);
        this.mReturnData[3] = ("" + localGyroExpansionData.mNoiseBias[1]);
        this.mReturnData[4] = ("" + localGyroExpansionData.mNoiseBias[2]);
        this.mReturnData[5] = ("" + localGyroExpansionData.mRMSValue[0]);
        this.mReturnData[6] = ("" + localGyroExpansionData.mRMSValue[1]);
        this.mReturnData[7] = ("" + localGyroExpansionData.mRMSValue[2]);
        if ((this.mFeature_Gyroscope == "STMICRO_SMARTPHONE") || (this.mFeature_Gyroscope == "STMICRO_TABLET"))
        {
          this.mReturnData[0] = "8";
          this.mReturnData[8] = "";
        }
      }
    }
    label762:
    do
    {
      if (ModuleSensor.LOG_LEVEL <= 2) {
        FtUtil.log_d("SensorRead", "getGyroSelf", dataCheck(this.mReturnData));
      }
      return this.mReturnData;
      if (ModuleSensor.LOG_LEVEL <= 2)
      {
        if (localGyroExpansionData.mNoiseBias == null) {
          FtUtil.log_d("SensorRead", "getGyroSelf", "Noise Bias null");
        }
        if (localGyroExpansionData.mRMSValue == null) {
          FtUtil.log_d("SensorRead", "getGyroSelf", "RMS null");
        }
      }
      return null;
      if ((paramInt != 2) || (this.mSensorReadFile == null)) {
        break label968;
      }
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____GYRO_SELFTEST);
      if (this.mTemp_String == null) {
        break;
      }
      if (ModuleSensor.LOG_LEVEL <= 1) {
        FtUtil.log_d("SensorRead", "getGyroSelf", "Count : " + this.mTemp_String.length);
      }
      if ((this.mTemp_String[6].equals("1")) && (this.mTemp_String[7].equals("1"))) {}
      for (String str1 = "0";; str1 = "-1")
      {
        this.mReturnData[0] = "7";
        this.mReturnData[1] = ("" + str1);
        this.mReturnData[2] = ("" + this.mTemp_String[0]);
        this.mReturnData[3] = ("" + this.mTemp_String[1]);
        this.mReturnData[4] = ("" + this.mTemp_String[2]);
        this.mReturnData[5] = ("" + this.mTemp_String[3]);
        this.mReturnData[6] = ("" + this.mTemp_String[4]);
        this.mReturnData[7] = ("" + this.mTemp_String[5]);
        if (!this.mFeature_Gyroscope.contains("STMICRO_SMARTPHONE")) {
          break label762;
        }
        this.mReturnData[0] = "8";
        this.mReturnData[8] = ("" + this.mTemp_String[6]);
        break;
      }
    } while (!this.mFeature_Gyroscope.contains("STMICRO_TABLET"));
    FtUtil.log_d("SensorRead", "getGyroSelfTest", "FEATURE TABLET(CAL)");
    this.mReturnData[0] = "8";
    if ((this.mTemp_String[9].equals("1")) && (this.mTemp_String[10].equals("1"))) {}
    for (String str2 = "0";; str2 = "-1")
    {
      this.mReturnData[1] = ("" + str2);
      this.mReturnData[8] = ("" + this.mTemp_String[10]);
      String str3 = "";
      for (int i = 0; i < this.mReturnData.length; i++) {
        str3 = str3 + " " + this.mReturnData[i];
      }
      break;
    }
    if (ModuleSensor.LOG_LEVEL <= 2) {
      FtUtil.log_d("SensorRead", "getGyroSelfTest", "null");
    }
    return null;
    label968:
    return null;
  }
  
  public String[] getGyroTemperature(int paramInt)
  {
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {}
    do
    {
      do
      {
        return null;
      } while ((paramInt != 2) || (this.mSensorReadFile == null));
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____GYRO_TEMPERATURE);
      if (this.mTemp_String != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getGyroTemperature", "Count : " + this.mTemp_String.length);
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = this.mTemp_String[0];
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getGyroTemperature", dataCheck(this.mReturnData));
        }
        return this.mReturnData;
      }
    } while (ModuleSensor.LOG_LEVEL > 2);
    FtUtil.log_d("SensorRead", "getGyroTemperature", "null");
    return null;
  }
  
  public String[] getHumidity(int paramInt)
  {
    String[] arrayOfString;
    if ((paramInt == 1) && (this.mSensorReadManager != null))
    {
      this.mTemp_Float = this.mSensorReadManager.returnHumidity();
      if (this.mTemp_Float != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getHumidity", "Count : " + this.mReturnData.length);
        }
        DecimalFormat localDecimalFormat = new DecimalFormat("#.##");
        this.mReturnData[0] = "2";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = String.valueOf(localDecimalFormat.format(this.mTemp_Float[0]));
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getHumidity", dataCheck(this.mReturnData));
        }
        arrayOfString = this.mReturnData;
      }
    }
    SensorReadFile localSensorReadFile;
    do
    {
      do
      {
        int i;
        do
        {
          return arrayOfString;
          i = ModuleSensor.LOG_LEVEL;
          arrayOfString = null;
        } while (i > 2);
        FtUtil.log_d("SensorRead", "getHumidity", "null");
        return null;
        arrayOfString = null;
      } while (paramInt != 2);
      localSensorReadFile = this.mSensorReadFile;
      arrayOfString = null;
    } while (localSensorReadFile == null);
    return null;
  }
  
  public String[] getLight(int paramInt)
  {
    String[] arrayOfString = new String[16];
    if ((paramInt == 1) && (this.mSensorReadManager != null))
    {
      this.mTemp_Float = this.mSensorReadManager.returnLight();
      if (this.mTemp_Float != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getLight", "Count : " + this.mTemp_Float.length);
        }
        arrayOfString[0] = "2";
        arrayOfString[1] = "None";
        arrayOfString[2] = ("" + this.mTemp_Float[0]);
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getLight", dataCheck(arrayOfString));
        }
        return arrayOfString;
      }
      if (ModuleSensor.LOG_LEVEL <= 2) {
        FtUtil.log_d("SensorRead", "getLight", "null");
      }
      return null;
    }
    if ((paramInt == 2) && (this.mSensorReadFile != null)) {
      return null;
    }
    return null;
  }
  
  public String[] getLightADC(int paramInt)
  {
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {}
    do
    {
      do
      {
        return null;
      } while ((paramInt != 2) || (this.mSensorReadFile == null));
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____LIGHT_ADC);
      if (this.mTemp_String != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getLightADC", "Count : " + this.mTemp_String.length);
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = ("" + this.mTemp_String[0]);
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getLightADC", dataCheck(this.mReturnData));
        }
        return this.mReturnData;
      }
    } while (ModuleSensor.LOG_LEVEL > 2);
    FtUtil.log_d("SensorRead", "getLightADC", "null");
    return null;
  }
  
  public String[] getLightCCT(int paramInt)
  {
    String[] arrayOfString;
    if ((paramInt == 1) && (this.mSensorReadManager != null))
    {
      this.mTemp_Float = this.mSensorReadManager.returnLight();
      if (this.mTemp_Float != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getLightCCT", "Count : " + this.mTemp_Float.length);
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = ("" + this.mTemp_Float[1]);
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getLightCCT", dataCheck(this.mReturnData));
        }
        arrayOfString = this.mReturnData;
      }
    }
    SensorReadFile localSensorReadFile;
    do
    {
      do
      {
        int i;
        do
        {
          return arrayOfString;
          i = ModuleSensor.LOG_LEVEL;
          arrayOfString = null;
        } while (i > 2);
        FtUtil.log_d("SensorRead", "getLightCCT", "null");
        return null;
        arrayOfString = null;
      } while (paramInt != 2);
      localSensorReadFile = this.mSensorReadFile;
      arrayOfString = null;
    } while (localSensorReadFile == null);
    return null;
  }
  
  public String[] getLightRGBW(int paramInt)
  {
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {}
    do
    {
      do
      {
        return null;
      } while ((paramInt != 2) || (this.mSensorReadFile == null));
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____LIGHT_RGBW);
      if (this.mTemp_String != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getLightRGBW", "Count : " + this.mTemp_String.length);
        }
        this.mReturnData[0] = "5";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = ("" + this.mTemp_String[0]);
        this.mReturnData[3] = ("" + this.mTemp_String[1]);
        this.mReturnData[4] = ("" + this.mTemp_String[2]);
        this.mReturnData[5] = ("" + this.mTemp_String[3]);
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getLightRGBW", dataCheck(this.mReturnData));
        }
        return this.mReturnData;
      }
    } while (ModuleSensor.LOG_LEVEL > 2);
    FtUtil.log_d("SensorRead", "getLightRGBW", "null");
    return null;
  }
  
  public String[] getMagnetic(int paramInt)
  {
    String[] arrayOfString;
    if ((paramInt == 1) && (this.mSensorReadManager != null))
    {
      this.mTemp_Float = this.mSensorReadManager.returnMagnetic();
      if (this.mTemp_Float != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getMagnetic", "Count : " + this.mTemp_Float.length);
        }
        this.mReturnData[0] = "4";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = ("" + this.mTemp_Float[0]);
        this.mReturnData[3] = ("" + this.mTemp_Float[1]);
        this.mReturnData[4] = ("" + this.mTemp_Float[2]);
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getMagnetic", dataCheck(this.mReturnData));
        }
        arrayOfString = this.mReturnData;
      }
    }
    SensorReadFile localSensorReadFile;
    do
    {
      do
      {
        int i;
        do
        {
          return arrayOfString;
          i = ModuleSensor.LOG_LEVEL;
          arrayOfString = null;
        } while (i > 2);
        FtUtil.log_d("SensorRead", "getMagnetic", "null");
        return null;
        arrayOfString = null;
      } while (paramInt != 2);
      localSensorReadFile = this.mSensorReadFile;
      arrayOfString = null;
    } while (localSensorReadFile == null);
    return null;
  }
  
  public String[] getMagneticADC(int paramInt)
  {
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {
      if ((this.mFeature_Magnetic.equals("AK8963")) || (this.mFeature_Magnetic.equals("AK8963C")) || (this.mFeature_Magnetic.equals("AK8963C_MANAGER")) || (this.mFeature_Magnetic.equals("AK8973")) || (this.mFeature_Magnetic.equals("AK8975")))
      {
        this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Asahi(5);
        if ((this.mTemp_Magnetic == null) || (this.mTemp_Magnetic.mADC == null)) {
          break label418;
        }
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getMagneticADC", "Count : " + this.mTemp_Magnetic.mADC.length);
        }
        this.mReturnData[0] = "4";
        this.mReturnData[1] = this.mTemp_Magnetic.mADC[0];
        this.mReturnData[2] = this.mTemp_Magnetic.mADC[1];
        this.mReturnData[3] = this.mTemp_Magnetic.mADC[2];
        this.mReturnData[4] = this.mTemp_Magnetic.mADC[3];
      }
    }
    for (;;)
    {
      if (ModuleSensor.LOG_LEVEL <= 2) {
        FtUtil.log_d("SensorRead", "getMagneticADC", dataCheck(this.mReturnData));
      }
      return this.mReturnData;
      if ((this.mFeature_Magnetic.equals("YAS529")) || (this.mFeature_Magnetic.equals("YAS530")) || (this.mFeature_Magnetic.equals("YAS530C")) || (this.mFeature_Magnetic.equals("YAS532B")) || (this.mFeature_Magnetic.equals("YAS532")))
      {
        this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Yamaha(5);
        break;
      }
      if ((this.mFeature_Magnetic.equals("HSCDTD004")) || (this.mFeature_Magnetic.equals("HSCDTD004A")) || (this.mFeature_Magnetic.equals("HSCDTD006A")) || (this.mFeature_Magnetic.equals("HSCDTD008A")))
      {
        this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Alps(5);
        break;
      }
      if (!this.mFeature_Magnetic.equals("BMC150")) {
        break;
      }
      this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Bosch(5);
      break;
      label418:
      if (ModuleSensor.LOG_LEVEL <= 2) {
        FtUtil.log_d("SensorRead", "getMagneticADC", "null");
      }
      return null;
      if ((paramInt != 2) || (this.mSensorReadFile == null)) {
        break label586;
      }
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____MAGNETIC_ADC);
      if (this.mTemp_String == null) {
        break label567;
      }
      if (ModuleSensor.LOG_LEVEL <= 1) {
        FtUtil.log_d("SensorRead", "getMagneticADC", "Count : " + this.mTemp_String.length);
      }
      this.mReturnData[0] = "4";
      this.mReturnData[1] = this.mTemp_String[0];
      this.mReturnData[2] = this.mTemp_String[1];
      this.mReturnData[3] = this.mTemp_String[2];
      this.mReturnData[4] = this.mTemp_String[3];
    }
    label567:
    if (ModuleSensor.LOG_LEVEL <= 2) {
      FtUtil.log_d("SensorRead", "getMagneticADC", "null");
    }
    return null;
    label586:
    return null;
  }
  
  public String[] getMagneticDAC(int paramInt)
  {
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {
      if ((this.mFeature_Magnetic.equals("AK8963")) || (this.mFeature_Magnetic.equals("AK8963C")) || (this.mFeature_Magnetic.equals("AK8963C_MANAGER")) || (this.mFeature_Magnetic.equals("AK8973")) || (this.mFeature_Magnetic.equals("AK8975")))
      {
        this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Asahi(4);
        if ((this.mTemp_Magnetic == null) || (this.mTemp_Magnetic.mDAC == null)) {
          break label390;
        }
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getMagneticDAC", "Count : " + this.mTemp_Magnetic.mDAC.length);
        }
        this.mReturnData[0] = "4";
        this.mReturnData[1] = this.mTemp_Magnetic.mDAC[0];
        this.mReturnData[2] = this.mTemp_Magnetic.mDAC[1];
        this.mReturnData[3] = this.mTemp_Magnetic.mDAC[2];
        this.mReturnData[4] = this.mTemp_Magnetic.mDAC[3];
      }
    }
    for (;;)
    {
      if (ModuleSensor.LOG_LEVEL <= 2) {
        FtUtil.log_d("SensorRead", "getMagneticDAC", dataCheck(this.mReturnData));
      }
      return this.mReturnData;
      if ((this.mFeature_Magnetic.equals("YAS529")) || (this.mFeature_Magnetic.equals("YAS530")) || (this.mFeature_Magnetic.equals("YAS530C")) || (this.mFeature_Magnetic.equals("YAS532B")) || (this.mFeature_Magnetic.equals("YAS532")))
      {
        this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Yamaha(4);
        break;
      }
      if ((!this.mFeature_Magnetic.equals("HSCDTD004")) && (!this.mFeature_Magnetic.equals("HSCDTD004A")) && (!this.mFeature_Magnetic.equals("HSCDTD006A")) && (!this.mFeature_Magnetic.equals("HSCDTD008A"))) {
        break;
      }
      this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Alps(4);
      break;
      label390:
      if (ModuleSensor.LOG_LEVEL <= 2) {
        FtUtil.log_d("SensorRead", "getMagneticDAC", "null");
      }
      return null;
      if ((paramInt != 2) || (this.mSensorReadFile == null)) {
        break label684;
      }
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____MAGNETIC_DAC);
      if (this.mTemp_String == null) {
        break label665;
      }
      if (ModuleSensor.LOG_LEVEL <= 1) {
        FtUtil.log_d("SensorRead", "getMagneticDAC", "Count : " + this.mTemp_String.length);
      }
      if ((this.mFeature_Magnetic.equals("AK8963")) || (this.mFeature_Magnetic.equals("AK8963C")) || (this.mFeature_Magnetic.equals("AK8963C_MANAGER")) || (this.mFeature_Magnetic.equals("AK8975")))
      {
        this.mReturnData[0] = "4";
        this.mReturnData[1] = this.mTemp_String[0];
        if (this.mTemp_String[0].equals("OK")) {
          this.mReturnData[2] = "1";
        }
        for (;;)
        {
          this.mReturnData[3] = "0";
          this.mReturnData[4] = "0";
          break;
          this.mReturnData[2] = "0";
        }
      }
      this.mReturnData[0] = "4";
      this.mReturnData[1] = this.mTemp_String[0];
      this.mReturnData[2] = this.mTemp_String[1];
      this.mReturnData[3] = this.mTemp_String[2];
      this.mReturnData[4] = this.mTemp_String[3];
    }
    label665:
    if (ModuleSensor.LOG_LEVEL <= 2) {
      FtUtil.log_d("SensorRead", "getMagneticDAC", "null");
    }
    return null;
    label684:
    return null;
  }
  
  public String[] getMagneticOffsetH(int paramInt)
  {
    String[] arrayOfString;
    if ((paramInt == 1) && (this.mSensorReadManager != null))
    {
      if ((this.mFeature_Magnetic.equals("YAS530C")) || (this.mFeature_Magnetic.equals("YAS532B")) || (this.mFeature_Magnetic.equals("YAS532"))) {
        this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Yamaha(7);
      }
      if ((this.mTemp_Magnetic != null) && (this.mTemp_Magnetic.mOffset_H != null))
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getMagneticOffsetH", "Count : " + this.mTemp_Magnetic.mOffset_H.length);
        }
        this.mReturnData[0] = "4";
        this.mReturnData[1] = this.mTemp_Magnetic.mOffset_H[0];
        this.mReturnData[2] = this.mTemp_Magnetic.mOffset_H[1];
        this.mReturnData[3] = this.mTemp_Magnetic.mOffset_H[2];
        this.mReturnData[4] = this.mTemp_Magnetic.mOffset_H[3];
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getMagneticOffsetH", dataCheck(this.mReturnData));
        }
        arrayOfString = this.mReturnData;
      }
    }
    SensorReadFile localSensorReadFile;
    do
    {
      do
      {
        int i;
        do
        {
          return arrayOfString;
          i = ModuleSensor.LOG_LEVEL;
          arrayOfString = null;
        } while (i > 2);
        FtUtil.log_d("SensorRead", "getMagneticOffsetH", "null");
        return null;
        arrayOfString = null;
      } while (paramInt != 2);
      localSensorReadFile = this.mSensorReadFile;
      arrayOfString = null;
    } while (localSensorReadFile == null);
    return null;
  }
  
  public String[] getMagneticPowerOff(int paramInt)
  {
    label185:
    String[] arrayOfString;
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {
      if ((this.mFeature_Magnetic.equals("AK8963")) || (this.mFeature_Magnetic.equals("AK8963C")) || (this.mFeature_Magnetic.equals("AK8963C_MANAGER")) || (this.mFeature_Magnetic.equals("AK8973")) || (this.mFeature_Magnetic.equals("AK8975")))
      {
        this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Asahi(2);
        if ((this.mTemp_Magnetic == null) || (this.mTemp_Magnetic.mPowerOff == null)) {
          break label362;
        }
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getMagneticPowerOff", "Count : " + this.mTemp_Magnetic.mPowerOff.length);
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = this.mTemp_Magnetic.mPowerOff[0];
        this.mReturnData[2] = this.mTemp_Magnetic.mPowerOff[1];
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getMagneticPowerOff", dataCheck(this.mReturnData));
        }
        arrayOfString = this.mReturnData;
      }
    }
    label362:
    int i;
    do
    {
      SensorReadFile localSensorReadFile;
      do
      {
        do
        {
          int j;
          do
          {
            return arrayOfString;
            if ((this.mFeature_Magnetic.equals("YAS529")) || (this.mFeature_Magnetic.equals("YAS530")) || (this.mFeature_Magnetic.equals("YAS530C")) || (this.mFeature_Magnetic.equals("YAS532B")) || (this.mFeature_Magnetic.equals("YAS532")))
            {
              this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Yamaha(2);
              break;
            }
            if ((!this.mFeature_Magnetic.equals("HSCDTD004")) && (!this.mFeature_Magnetic.equals("HSCDTD004A")) && (!this.mFeature_Magnetic.equals("HSCDTD006A")) && (!this.mFeature_Magnetic.equals("HSCDTD008A"))) {
              break;
            }
            this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Alps(2);
            break;
            j = ModuleSensor.LOG_LEVEL;
            arrayOfString = null;
          } while (j > 2);
          FtUtil.log_d("SensorRead", "getMagneticPowerOff", "null");
          return null;
          arrayOfString = null;
        } while (paramInt != 2);
        localSensorReadFile = this.mSensorReadFile;
        arrayOfString = null;
      } while (localSensorReadFile == null);
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____MAGNETIC_POWER_OFF);
      if (this.mTemp_String != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getMagneticPowerOff", "Count : " + this.mTemp_String.length);
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = this.mTemp_String[0];
        break label185;
      }
      i = ModuleSensor.LOG_LEVEL;
      arrayOfString = null;
    } while (i > 2);
    FtUtil.log_d("SensorRead", "getMagneticPowerOff", "null");
    return null;
  }
  
  public String[] getMagneticPowerOn(int paramInt)
  {
    label185:
    String[] arrayOfString;
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {
      if ((this.mFeature_Magnetic.equals("AK8963")) || (this.mFeature_Magnetic.equals("AK8963C")) || (this.mFeature_Magnetic.equals("AK8963C_MANAGER")) || (this.mFeature_Magnetic.equals("AK8973")) || (this.mFeature_Magnetic.equals("AK8975")))
      {
        this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Asahi(1);
        if ((this.mTemp_Magnetic == null) || (this.mTemp_Magnetic.mPowerOn == null)) {
          break label390;
        }
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getMagneticPowerOn", "Count : " + this.mTemp_Magnetic.mPowerOn.length);
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = this.mTemp_Magnetic.mPowerOn[0];
        this.mReturnData[2] = this.mTemp_Magnetic.mPowerOn[1];
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getMagneticPowerOn", dataCheck(this.mReturnData));
        }
        arrayOfString = this.mReturnData;
      }
    }
    label390:
    int i;
    do
    {
      SensorReadFile localSensorReadFile;
      do
      {
        do
        {
          int j;
          do
          {
            return arrayOfString;
            if ((this.mFeature_Magnetic.equals("YAS529")) || (this.mFeature_Magnetic.equals("YAS530")) || (this.mFeature_Magnetic.equals("YAS530C")) || (this.mFeature_Magnetic.equals("YAS532B")) || (this.mFeature_Magnetic.equals("YAS532")))
            {
              this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Yamaha(1);
              break;
            }
            if ((this.mFeature_Magnetic.equals("HSCDTD004")) || (this.mFeature_Magnetic.equals("HSCDTD004A")) || (this.mFeature_Magnetic.equals("HSCDTD006A")) || (this.mFeature_Magnetic.equals("HSCDTD008A")))
            {
              this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Alps(1);
              break;
            }
            if (!this.mFeature_Magnetic.equals("BMC150")) {
              break;
            }
            this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Bosch(1);
            break;
            j = ModuleSensor.LOG_LEVEL;
            arrayOfString = null;
          } while (j > 2);
          FtUtil.log_d("SensorRead", "getMagneticPowerOn", "null");
          return null;
          arrayOfString = null;
        } while (paramInt != 2);
        localSensorReadFile = this.mSensorReadFile;
        arrayOfString = null;
      } while (localSensorReadFile == null);
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____MAGNETIC_POWER_ON);
      if (this.mTemp_String != null)
      {
        if (ModuleSensor.LOG_LEVEL > 1) {
          break label185;
        }
        FtUtil.log_d("SensorRead", "getMagneticPowerOn", "Count : " + this.mTemp_String.length);
        break label185;
      }
      i = ModuleSensor.LOG_LEVEL;
      arrayOfString = null;
    } while (i > 2);
    FtUtil.log_d("SensorRead", "getMagneticPowerOn", "null");
    return null;
  }
  
  public String[] getMagneticSelf(int paramInt)
  {
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {
      if ((this.mFeature_Magnetic.equals("AK8963")) || (this.mFeature_Magnetic.equals("AK8963C")) || (this.mFeature_Magnetic.equals("AK8963C_MANAGER")) || (this.mFeature_Magnetic.equals("AK8973")) || (this.mFeature_Magnetic.equals("AK8975")))
      {
        this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Asahi(6);
        if ((this.mTemp_Magnetic == null) || (this.mTemp_Magnetic.mSelf == null)) {
          break label476;
        }
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getMagneticSelf", "Count : " + this.mTemp_Magnetic.mSelf.length);
        }
        if (!this.mFeature_Magnetic.equals("BMC150")) {
          break label405;
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = this.mTemp_Magnetic.mSelf[0];
        this.mReturnData[2] = this.mTemp_Magnetic.mSelf[1];
      }
    }
    for (;;)
    {
      if (ModuleSensor.LOG_LEVEL <= 2) {
        FtUtil.log_d("SensorRead", "getMagneticSelf", dataCheck(this.mReturnData));
      }
      return this.mReturnData;
      if ((this.mFeature_Magnetic.equals("YAS529")) || (this.mFeature_Magnetic.equals("YAS530")) || (this.mFeature_Magnetic.equals("YAS530C")) || (this.mFeature_Magnetic.equals("YAS532B")) || (this.mFeature_Magnetic.equals("YAS532")))
      {
        this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Yamaha(6);
        break;
      }
      if (this.mFeature_Magnetic.equals("BMC150"))
      {
        this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Bosch(6);
        break;
      }
      if ((!this.mFeature_Magnetic.equals("HSCDTD004")) && (!this.mFeature_Magnetic.equals("HSCDTD004A")) && (!this.mFeature_Magnetic.equals("HSCDTD006A")) && (!this.mFeature_Magnetic.equals("HSCDTD008A"))) {
        break;
      }
      this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Alps(6);
      break;
      label405:
      this.mReturnData[0] = "4";
      this.mReturnData[1] = this.mTemp_Magnetic.mSelf[0];
      this.mReturnData[2] = this.mTemp_Magnetic.mSelf[1];
      this.mReturnData[3] = this.mTemp_Magnetic.mSelf[2];
      this.mReturnData[4] = this.mTemp_Magnetic.mSelf[3];
      continue;
      label476:
      if (ModuleSensor.LOG_LEVEL <= 2) {
        FtUtil.log_d("SensorRead", "getMagneticSelf", "null");
      }
      return null;
      if ((paramInt != 2) || (this.mSensorReadFile == null)) {
        break label727;
      }
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____MAGNETIC_SELF);
      if (this.mTemp_String == null) {
        break label708;
      }
      if (ModuleSensor.LOG_LEVEL <= 1) {
        FtUtil.log_d("SensorRead", "getMagneticSelf", "Count : " + this.mTemp_String.length);
      }
      if ((this.mFeature_Magnetic.equals("HSCDTD004")) || (this.mFeature_Magnetic.equals("HSCDTD004A")) || (this.mFeature_Magnetic.equals("HSCDTD006A")) || (this.mFeature_Magnetic.equals("HSCDTD008A")))
      {
        this.mReturnData[0] = "2";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = this.mTemp_String[0];
      }
      else
      {
        this.mReturnData[0] = "4";
        this.mReturnData[1] = this.mTemp_String[0];
        this.mReturnData[2] = this.mTemp_String[1];
        this.mReturnData[3] = this.mTemp_String[2];
        this.mReturnData[4] = this.mTemp_String[3];
      }
    }
    label708:
    if (ModuleSensor.LOG_LEVEL <= 2) {
      FtUtil.log_d("SensorRead", "getMagneticSelf", "null");
    }
    return null;
    label727:
    return null;
  }
  
  public String[] getMagneticStatus(int paramInt)
  {
    label185:
    String[] arrayOfString;
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {
      if ((this.mFeature_Magnetic.equals("AK8963")) || (this.mFeature_Magnetic.equals("AK8963C")) || (this.mFeature_Magnetic.equals("AK8963C_MANAGER")) || (this.mFeature_Magnetic.equals("AK8973")) || (this.mFeature_Magnetic.equals("AK8975")))
      {
        this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Asahi(3);
        if ((this.mTemp_Magnetic == null) || (this.mTemp_Magnetic.mStatus == null)) {
          break label390;
        }
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getMagneticStatus", "Count : " + this.mTemp_Magnetic.mStatus.length);
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = this.mTemp_Magnetic.mStatus[0];
        this.mReturnData[2] = this.mTemp_Magnetic.mStatus[1];
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getMagneticStatus", dataCheck(this.mReturnData));
        }
        arrayOfString = this.mReturnData;
      }
    }
    label390:
    int i;
    do
    {
      SensorReadFile localSensorReadFile;
      do
      {
        do
        {
          int j;
          do
          {
            return arrayOfString;
            if ((this.mFeature_Magnetic.equals("YAS529")) || (this.mFeature_Magnetic.equals("YAS530")) || (this.mFeature_Magnetic.equals("YAS530C")) || (this.mFeature_Magnetic.equals("YAS532B")) || (this.mFeature_Magnetic.equals("YAS532")))
            {
              this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Yamaha(3);
              break;
            }
            if ((this.mFeature_Magnetic.equals("HSCDTD004")) || (this.mFeature_Magnetic.equals("HSCDTD004A")) || (this.mFeature_Magnetic.equals("HSCDTD006A")) || (this.mFeature_Magnetic.equals("HSCDTD008A")))
            {
              this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Alps(3);
              break;
            }
            if (!this.mFeature_Magnetic.equals("BMC150")) {
              break;
            }
            this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Bosch(3);
            break;
            j = ModuleSensor.LOG_LEVEL;
            arrayOfString = null;
          } while (j > 2);
          FtUtil.log_d("SensorRead", "getMagneticStatus", "null");
          return null;
          arrayOfString = null;
        } while (paramInt != 2);
        localSensorReadFile = this.mSensorReadFile;
        arrayOfString = null;
      } while (localSensorReadFile == null);
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____MAGNETIC_STATUS);
      if (this.mTemp_String != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getMagneticStatus", "Count : " + this.mTemp_String.length);
        }
        if ((this.mFeature_Magnetic.equals("HSCDTD004")) || (this.mFeature_Magnetic.equals("HSCDTD004A")) || (this.mFeature_Magnetic.equals("HSCDTD006A")) || (this.mFeature_Magnetic.equals("HSCDTD008A")))
        {
          this.mReturnData[0] = "2";
          if (this.mTemp_String[0].equals("1")) {
            this.mReturnData[1] = "OK";
          }
          for (;;)
          {
            this.mReturnData[2] = this.mTemp_String[0];
            break;
            this.mReturnData[1] = "NG";
          }
        }
        this.mReturnData[0] = "2";
        if (this.mTemp_String[0].equals("OK"))
        {
          this.mReturnData[1] = "OK";
          this.mReturnData[2] = "1";
          break label185;
        }
        this.mReturnData[1] = "NG";
        this.mReturnData[2] = "0";
        break label185;
      }
      i = ModuleSensor.LOG_LEVEL;
      arrayOfString = null;
    } while (i > 2);
    FtUtil.log_d("SensorRead", "getMagneticStatus", "null");
    return null;
  }
  
  public String[] getMagneticTemperature(int paramInt)
  {
    label159:
    String[] arrayOfString;
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {
      if ((this.mFeature_Magnetic.equals("YAS530C")) || (this.mFeature_Magnetic.equals("YAS532B")) || (this.mFeature_Magnetic.equals("YAS532")))
      {
        this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Yamaha(2);
        if ((this.mTemp_Magnetic == null) || (this.mTemp_Magnetic.mTemperature == null)) {
          break label256;
        }
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getMagneticTemperature", "Count : " + this.mTemp_Magnetic.mTemperature.length);
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = this.mTemp_Magnetic.mTemperature[0];
        this.mReturnData[2] = this.mTemp_Magnetic.mTemperature[1];
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getMagneticTemperature", dataCheck(this.mReturnData));
        }
        arrayOfString = this.mReturnData;
      }
    }
    label256:
    int i;
    do
    {
      SensorReadFile localSensorReadFile;
      do
      {
        do
        {
          int j;
          do
          {
            return arrayOfString;
            if ((!this.mFeature_Magnetic.equals("HSCDTD004")) && (!this.mFeature_Magnetic.equals("HSCDTD004A")) && (!this.mFeature_Magnetic.equals("HSCDTD006A")) && (!this.mFeature_Magnetic.equals("HSCDTD008A"))) {
              break;
            }
            this.mTemp_Magnetic = this.mSensorReadManager.returnMagneticExpansion_Alps(2);
            break;
            j = ModuleSensor.LOG_LEVEL;
            arrayOfString = null;
          } while (j > 2);
          FtUtil.log_d("SensorRead", "getMagneticTemperature", "null");
          return null;
          arrayOfString = null;
        } while (paramInt != 2);
        localSensorReadFile = this.mSensorReadFile;
        arrayOfString = null;
      } while (localSensorReadFile == null);
      if ((!this.mFeature_Magnetic.equals("AK8973")) && (!this.mFeature_Magnetic.equals("YAS529"))) {
        break label458;
      }
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____MAGNETIC_TEMPERATURE);
      if (this.mTemp_String != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getMagneticTemperature", "Count : " + this.mTemp_String.length);
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = ("" + this.mTemp_String[0]);
        break label159;
      }
      i = ModuleSensor.LOG_LEVEL;
      arrayOfString = null;
    } while (i > 2);
    FtUtil.log_d("SensorRead", "getMagneticTemperature", "null");
    return null;
    label458:
    this.mReturnData[0] = "2";
    this.mReturnData[1] = "None";
    this.mReturnData[2] = "0";
    if (ModuleSensor.LOG_LEVEL <= 2) {
      FtUtil.log_d("SensorRead", "getMagneticTemperature", dataCheck(this.mReturnData));
    }
    return this.mReturnData;
  }
  
  public String[] getProximity(int paramInt)
  {
    String[] arrayOfString;
    if ((paramInt == 1) && (this.mSensorReadManager != null))
    {
      this.mTemp_Float = this.mSensorReadManager.returnProximity();
      if (this.mTemp_Float != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getProximity", "Count : " + this.mTemp_Float.length);
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = ("" + this.mTemp_Float[0]);
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getProximity", dataCheck(this.mReturnData));
        }
        arrayOfString = this.mReturnData;
      }
    }
    SensorReadFile localSensorReadFile;
    do
    {
      do
      {
        int i;
        do
        {
          return arrayOfString;
          i = ModuleSensor.LOG_LEVEL;
          arrayOfString = null;
        } while (i > 2);
        FtUtil.log_d("SensorRead", "getProximity", "null");
        return null;
        arrayOfString = null;
      } while (paramInt != 2);
      localSensorReadFile = this.mSensorReadFile;
      arrayOfString = null;
    } while (localSensorReadFile == null);
    return null;
  }
  
  public String[] getProximityADC(int paramInt)
  {
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {}
    do
    {
      do
      {
        return null;
      } while ((paramInt != 2) || (this.mSensorReadFile == null));
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____PROXIMITY_ADC);
      if (this.mTemp_String != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getProximityADC", "Count : " + this.mTemp_String.length);
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = this.mTemp_String[0];
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getProximityADC", dataCheck(this.mReturnData));
        }
        return this.mReturnData;
      }
    } while (ModuleSensor.LOG_LEVEL > 2);
    FtUtil.log_d("SensorRead", "getProximityADC", "null");
    return null;
  }
  
  public String[] getProximityAVG(int paramInt)
  {
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {}
    do
    {
      do
      {
        return null;
      } while ((paramInt != 2) || (this.mSensorReadFile == null));
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____PROXIMITY_AVG);
      if (this.mTemp_String != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getProximityAVG", "Count : " + this.mTemp_String.length);
        }
        this.mReturnData[0] = "2";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = this.mTemp_String[0];
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getProximityAVG", dataCheck(this.mReturnData));
        }
        return this.mReturnData;
      }
    } while (ModuleSensor.LOG_LEVEL > 2);
    FtUtil.log_d("SensorRead", "getProximityAVG", "null");
    return null;
  }
  
  public String[] getProximityOffset(int paramInt)
  {
    if ((paramInt == 1) && (this.mSensorReadManager != null)) {}
    do
    {
      do
      {
        return null;
      } while ((paramInt != 2) || (this.mSensorReadFile == null));
      this.mTemp_String = this.mSensorReadFile.returnData(ModuleSensor.ID_FILE____PROXIMITY_OFFSET);
      if (this.mTemp_String != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getProximityOffset", "Count : " + this.mTemp_String.length);
        }
        this.mReturnData[0] = "3";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = this.mTemp_String[0];
        this.mReturnData[3] = this.mTemp_String[1];
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getProximityOffset", dataCheck(this.mReturnData));
        }
        return this.mReturnData;
      }
    } while (ModuleSensor.LOG_LEVEL > 2);
    FtUtil.log_d("SensorRead", "getProximityOffset", "null");
    return null;
  }
  
  public String[] getTemperature(int paramInt)
  {
    String[] arrayOfString;
    if ((paramInt == 1) && (this.mSensorReadManager != null))
    {
      this.mTemp_Float = this.mSensorReadManager.returnTemperature();
      if (this.mTemp_Float != null)
      {
        if (ModuleSensor.LOG_LEVEL <= 1) {
          FtUtil.log_d("SensorRead", "getTemperature", "Count : " + this.mReturnData.length);
        }
        DecimalFormat localDecimalFormat = new DecimalFormat("#.##");
        this.mReturnData[0] = "2";
        this.mReturnData[1] = "None";
        this.mReturnData[2] = String.valueOf(localDecimalFormat.format(this.mTemp_Float[0]));
        if (ModuleSensor.LOG_LEVEL <= 2) {
          FtUtil.log_d("SensorRead", "getTemperature", dataCheck(this.mReturnData));
        }
        arrayOfString = this.mReturnData;
      }
    }
    SensorReadFile localSensorReadFile;
    do
    {
      do
      {
        int i;
        do
        {
          return arrayOfString;
          i = ModuleSensor.LOG_LEVEL;
          arrayOfString = null;
        } while (i > 2);
        FtUtil.log_d("SensorRead", "getTemperature", "null");
        return null;
        arrayOfString = null;
      } while (paramInt != 2);
      localSensorReadFile = this.mSensorReadFile;
      arrayOfString = null;
    } while (localSensorReadFile == null);
    return null;
  }
  
  public boolean isSensorOn(int paramInt)
  {
    if ((this.mSensorReadManager != null) && (ModuleSensor.ID_SCOPE_MIN <= paramInt) && (paramInt < ModuleSensor.ID_COUNT_MANAGER)) {
      return this.mSensorReadManager.isSensorOn(paramInt);
    }
    if ((this.mSensorReadFile != null) && (ModuleSensor.ID_COUNT_MANAGER <= paramInt) && (paramInt < ModuleSensor.ID_COUNT_MANAGER + ModuleSensor.ID_COUNT_FILE)) {
      return this.mSensorReadFile.isSensorOn(paramInt);
    }
    if ((this.mSensorReadIntent != null) && (ModuleSensor.ID_COUNT_MANAGER + ModuleSensor.ID_COUNT_FILE <= paramInt) && (paramInt <= ModuleSensor.ID_SCOPE_MAX)) {
      return this.mSensorReadIntent.isSensorOn(paramInt);
    }
    FtUtil.log_e("SensorRead", "SensorOn", "null / ID unknown");
    return false;
  }
  
  public void setLoop_ReadFile()
  {
    if (this.mSensorReadFile != null) {
      this.mSensorReadFile.startLoop();
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.SensorRead
 * JD-Core Version:    0.7.1
 */