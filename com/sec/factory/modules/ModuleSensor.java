package com.sec.factory.modules;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Handler;
import com.sec.factory.support.FtUtil;

public class ModuleSensor
  extends ModuleObject
{
  private static int DUMMY;
  public static final int ID_COUNT_FILE;
  public static final int ID_COUNT_INTENT = DUMMY - (ID_COUNT_MANAGER + ID_COUNT_FILE);
  public static final int ID_COUNT_MANAGER;
  public static final int ID_FILE____ACCELEROMETER;
  public static final int ID_FILE____ACCELEROMETER_CAL;
  public static final int ID_FILE____ACCELEROMETER_INTPIN;
  public static final int ID_FILE____ACCELEROMETER_N_ANGLE;
  public static final int ID_FILE____ACCELEROMETER_SELF;
  public static final int ID_FILE____BAROMETER_EEPROM;
  public static final int ID_FILE____GYRO_POWER;
  public static final int ID_FILE____GYRO_SELFTEST;
  public static final int ID_FILE____GYRO_TEMPERATURE;
  public static final int ID_FILE____LIGHT_ADC;
  public static final int ID_FILE____LIGHT_RGBW;
  public static final int ID_FILE____MAGNETIC_ADC;
  public static final int ID_FILE____MAGNETIC_DAC;
  public static final int ID_FILE____MAGNETIC_POWER_OFF;
  public static final int ID_FILE____MAGNETIC_POWER_ON;
  public static final int ID_FILE____MAGNETIC_SELF;
  public static final int ID_FILE____MAGNETIC_STATUS;
  public static final int ID_FILE____MAGNETIC_TEMPERATURE;
  public static final int ID_FILE____PROXIMITY_ADC;
  public static final int ID_FILE____PROXIMITY_AVG;
  public static final int ID_FILE____PROXIMITY_OFFSET;
  public static final int ID_INTENT__CP_ACCELEROMETER;
  public static final int ID_INTENT__GRIP;
  public static final int ID_MANAGER_ACCELEROMETER;
  public static final int ID_MANAGER_ACCELEROMETER_N_ANGLE;
  public static final int ID_MANAGER_ACCELEROMETER_SELF;
  public static final int ID_MANAGER_BAROMETER;
  public static final int ID_MANAGER_GYRO;
  public static final int ID_MANAGER_GYRO_EXPANSION;
  public static final int ID_MANAGER_GYRO_POWER;
  public static final int ID_MANAGER_GYRO_SELF;
  public static final int ID_MANAGER_GYRO_TEMPERATURE;
  public static final int ID_MANAGER_HUMIDITY;
  public static final int ID_MANAGER_LIGHT;
  public static final int ID_MANAGER_LIGHT_CCT;
  public static final int ID_MANAGER_MAGNETIC;
  public static final int ID_MANAGER_MAGNETIC_ADC;
  public static final int ID_MANAGER_MAGNETIC_DAC;
  public static final int ID_MANAGER_MAGNETIC_OFFSETH;
  public static final int ID_MANAGER_MAGNETIC_POWER_OFF;
  public static final int ID_MANAGER_MAGNETIC_POWER_ON;
  public static final int ID_MANAGER_MAGNETIC_SELF;
  public static final int ID_MANAGER_MAGNETIC_STATUS;
  public static final int ID_MANAGER_MAGNETIC_TEMPERATURE;
  public static final int ID_MANAGER_PROXIMITY;
  public static final int ID_MANAGER_TEMPERATURE;
  public static final int ID_SCOPE_MAX = -1 + DUMMY;
  public static final int ID_SCOPE_MIN;
  public static int LOG_LEVEL = 1;
  private static ModuleSensor mInstance = null;
  private final int DEFAULT_LOG_LEVEL = 1;
  private final int TARGET_FILE = 2;
  private final int TARGET_INTENT = 3;
  private final int TARGET_MANAGER = 1;
  private int[] mIDArray_File = null;
  private int[] mIDArray_Intent = null;
  private int[] mIDArray_Manager = null;
  private SensorNotification mSensorNotification = null;
  private SensorRead mSensorRead = null;
  
  static
  {
    DUMMY = 0;
    ID_SCOPE_MIN = DUMMY;
    int i = DUMMY;
    DUMMY = i + 1;
    ID_MANAGER_ACCELEROMETER = i;
    int j = DUMMY;
    DUMMY = j + 1;
    ID_MANAGER_ACCELEROMETER_N_ANGLE = j;
    int k = DUMMY;
    DUMMY = k + 1;
    ID_MANAGER_ACCELEROMETER_SELF = k;
    int m = DUMMY;
    DUMMY = m + 1;
    ID_MANAGER_BAROMETER = m;
    int n = DUMMY;
    DUMMY = n + 1;
    ID_MANAGER_GYRO = n;
    int i1 = DUMMY;
    DUMMY = i1 + 1;
    ID_MANAGER_GYRO_POWER = i1;
    int i2 = DUMMY;
    DUMMY = i2 + 1;
    ID_MANAGER_GYRO_EXPANSION = i2;
    int i3 = DUMMY;
    DUMMY = i3 + 1;
    ID_MANAGER_GYRO_SELF = i3;
    int i4 = DUMMY;
    DUMMY = i4 + 1;
    ID_MANAGER_GYRO_TEMPERATURE = i4;
    int i5 = DUMMY;
    DUMMY = i5 + 1;
    ID_MANAGER_LIGHT = i5;
    int i6 = DUMMY;
    DUMMY = i6 + 1;
    ID_MANAGER_LIGHT_CCT = i6;
    int i7 = DUMMY;
    DUMMY = i7 + 1;
    ID_MANAGER_MAGNETIC = i7;
    int i8 = DUMMY;
    DUMMY = i8 + 1;
    ID_MANAGER_MAGNETIC_POWER_ON = i8;
    int i9 = DUMMY;
    DUMMY = i9 + 1;
    ID_MANAGER_MAGNETIC_STATUS = i9;
    int i10 = DUMMY;
    DUMMY = i10 + 1;
    ID_MANAGER_MAGNETIC_TEMPERATURE = i10;
    int i11 = DUMMY;
    DUMMY = i11 + 1;
    ID_MANAGER_MAGNETIC_DAC = i11;
    int i12 = DUMMY;
    DUMMY = i12 + 1;
    ID_MANAGER_MAGNETIC_ADC = i12;
    int i13 = DUMMY;
    DUMMY = i13 + 1;
    ID_MANAGER_MAGNETIC_SELF = i13;
    int i14 = DUMMY;
    DUMMY = i14 + 1;
    ID_MANAGER_MAGNETIC_OFFSETH = i14;
    int i15 = DUMMY;
    DUMMY = i15 + 1;
    ID_MANAGER_MAGNETIC_POWER_OFF = i15;
    int i16 = DUMMY;
    DUMMY = i16 + 1;
    ID_MANAGER_PROXIMITY = i16;
    int i17 = DUMMY;
    DUMMY = i17 + 1;
    ID_MANAGER_TEMPERATURE = i17;
    int i18 = DUMMY;
    DUMMY = i18 + 1;
    ID_MANAGER_HUMIDITY = i18;
    ID_COUNT_MANAGER = DUMMY;
    int i19 = DUMMY;
    DUMMY = i19 + 1;
    ID_FILE____ACCELEROMETER = i19;
    int i20 = DUMMY;
    DUMMY = i20 + 1;
    ID_FILE____ACCELEROMETER_N_ANGLE = i20;
    int i21 = DUMMY;
    DUMMY = i21 + 1;
    ID_FILE____ACCELEROMETER_SELF = i21;
    int i22 = DUMMY;
    DUMMY = i22 + 1;
    ID_FILE____ACCELEROMETER_CAL = i22;
    int i23 = DUMMY;
    DUMMY = i23 + 1;
    ID_FILE____ACCELEROMETER_INTPIN = i23;
    int i24 = DUMMY;
    DUMMY = i24 + 1;
    ID_FILE____BAROMETER_EEPROM = i24;
    int i25 = DUMMY;
    DUMMY = i25 + 1;
    ID_FILE____GYRO_POWER = i25;
    int i26 = DUMMY;
    DUMMY = i26 + 1;
    ID_FILE____GYRO_TEMPERATURE = i26;
    int i27 = DUMMY;
    DUMMY = i27 + 1;
    ID_FILE____GYRO_SELFTEST = i27;
    int i28 = DUMMY;
    DUMMY = i28 + 1;
    ID_FILE____LIGHT_ADC = i28;
    int i29 = DUMMY;
    DUMMY = i29 + 1;
    ID_FILE____LIGHT_RGBW = i29;
    int i30 = DUMMY;
    DUMMY = i30 + 1;
    ID_FILE____MAGNETIC_POWER_ON = i30;
    int i31 = DUMMY;
    DUMMY = i31 + 1;
    ID_FILE____MAGNETIC_POWER_OFF = i31;
    int i32 = DUMMY;
    DUMMY = i32 + 1;
    ID_FILE____MAGNETIC_STATUS = i32;
    int i33 = DUMMY;
    DUMMY = i33 + 1;
    ID_FILE____MAGNETIC_TEMPERATURE = i33;
    int i34 = DUMMY;
    DUMMY = i34 + 1;
    ID_FILE____MAGNETIC_DAC = i34;
    int i35 = DUMMY;
    DUMMY = i35 + 1;
    ID_FILE____MAGNETIC_ADC = i35;
    int i36 = DUMMY;
    DUMMY = i36 + 1;
    ID_FILE____MAGNETIC_SELF = i36;
    int i37 = DUMMY;
    DUMMY = i37 + 1;
    ID_FILE____PROXIMITY_ADC = i37;
    int i38 = DUMMY;
    DUMMY = i38 + 1;
    ID_FILE____PROXIMITY_AVG = i38;
    int i39 = DUMMY;
    DUMMY = i39 + 1;
    ID_FILE____PROXIMITY_OFFSET = i39;
    ID_COUNT_FILE = DUMMY - ID_COUNT_MANAGER;
    int i40 = DUMMY;
    DUMMY = i40 + 1;
    ID_INTENT__CP_ACCELEROMETER = i40;
    int i41 = DUMMY;
    DUMMY = i41 + 1;
    ID_INTENT__GRIP = i41;
  }
  
  private ModuleSensor(Context paramContext)
  {
    super(paramContext, "ModuleSensor");
    FtUtil.log_i(this.CLASS_NAME, "ModuleSensor", null);
    this.mSensorRead = new SensorRead();
    SensorCalculator.initialize();
  }
  
  public static String getString_ID(int paramInt)
  {
    if (paramInt == ID_MANAGER_ACCELEROMETER) {
      return "ID_MANAGER_ACCELEROMETER";
    }
    if (paramInt == ID_FILE____ACCELEROMETER) {
      return "ID_FILE____ACCELEROMETER";
    }
    if (paramInt == ID_MANAGER_ACCELEROMETER_N_ANGLE) {
      return "ID_MANAGER_ACCELEROMETER_N_ANGLE";
    }
    if (paramInt == ID_FILE____ACCELEROMETER_N_ANGLE) {
      return "ID_FILE____ACCELEROMETER_N_ANGLE";
    }
    if (paramInt == ID_MANAGER_ACCELEROMETER_SELF) {
      return "ID_MANAGER_ACCELEROMETER_SELF";
    }
    if (paramInt == ID_FILE____ACCELEROMETER_SELF) {
      return "ID_FILE____ACCELEROMETER_SELF";
    }
    if (paramInt == ID_FILE____ACCELEROMETER_CAL) {
      return "ID_FILE____ACCELEROMETER_CAL";
    }
    if (paramInt == ID_FILE____ACCELEROMETER_INTPIN) {
      return "ID_FILE____ACCELEROMETER_INTPIN";
    }
    if (paramInt == ID_INTENT__CP_ACCELEROMETER) {
      return "ID_INTENT__CP_ACCELEROMETER";
    }
    if (paramInt == ID_MANAGER_BAROMETER) {
      return "ID_MANAGER_BAROMETER";
    }
    if (paramInt == ID_FILE____BAROMETER_EEPROM) {
      return "ID_FILE____BAROMETER_EEPROM";
    }
    if (paramInt == ID_INTENT__GRIP) {
      return "ID_INTENT__GRIP";
    }
    if (paramInt == ID_MANAGER_GYRO) {
      return "ID_MANAGER_GYRO";
    }
    if (paramInt == ID_MANAGER_GYRO_POWER) {
      return "ID_MANAGER_GYRO_POWER";
    }
    if (paramInt == ID_FILE____GYRO_POWER) {
      return "ID_FILE____GYRO_POWER";
    }
    if (paramInt == ID_MANAGER_GYRO_EXPANSION) {
      return "ID_MANAGER_GYRO_EXPANSION";
    }
    if (paramInt == ID_MANAGER_GYRO_TEMPERATURE) {
      return "ID_MANAGER_GYRO_TEMPERATURE";
    }
    if (paramInt == ID_FILE____GYRO_TEMPERATURE) {
      return "ID_FILE____GYRO_TEMPERATURE";
    }
    if (paramInt == ID_MANAGER_GYRO_SELF) {
      return "ID_MANAGER_GYRO_SELF";
    }
    if (paramInt == ID_FILE____GYRO_SELFTEST) {
      return "ID_FILE____GYRO_SELFTEST";
    }
    if (paramInt == ID_MANAGER_LIGHT) {
      return "ID_MANAGER_LIGHT";
    }
    if (paramInt == ID_MANAGER_LIGHT_CCT) {
      return "ID_MANAGER_LIGHT_CCT";
    }
    if (paramInt == ID_FILE____LIGHT_ADC) {
      return "ID_FILE____LIGHT_ADC";
    }
    if (paramInt == ID_FILE____LIGHT_RGBW) {
      return "ID_FILE____LIGHT_RGBW";
    }
    if (paramInt == ID_MANAGER_MAGNETIC) {
      return "ID_MANAGER_MAGNETIC";
    }
    if (paramInt == ID_MANAGER_MAGNETIC_POWER_ON) {
      return "ID_MANAGER_MAGNETIC_POWER_ON";
    }
    if (paramInt == ID_FILE____MAGNETIC_POWER_ON) {
      return "ID_FILE____MAGNETIC_POWER_ON";
    }
    if (paramInt == ID_MANAGER_MAGNETIC_POWER_OFF) {
      return "ID_MANAGER_MAGNETIC_POWER_OFF";
    }
    if (paramInt == ID_FILE____MAGNETIC_POWER_OFF) {
      return "ID_FILE____MAGNETIC_POWER_OFF";
    }
    if (paramInt == ID_MANAGER_MAGNETIC_STATUS) {
      return "ID_MANAGER_MAGNETIC_STATUS";
    }
    if (paramInt == ID_FILE____MAGNETIC_STATUS) {
      return "ID_FILE____MAGNETIC_STATUS";
    }
    if (paramInt == ID_MANAGER_MAGNETIC_TEMPERATURE) {
      return "ID_MANAGER_MAGNETIC_TEMPERATURE";
    }
    if (paramInt == ID_FILE____MAGNETIC_TEMPERATURE) {
      return "ID_FILE____MAGNETIC_TEMPERATURE";
    }
    if (paramInt == ID_MANAGER_MAGNETIC_DAC) {
      return "ID_MANAGER_MAGNETIC_DAC";
    }
    if (paramInt == ID_FILE____MAGNETIC_DAC) {
      return "ID_FILE____MAGNETIC_DAC";
    }
    if (paramInt == ID_MANAGER_MAGNETIC_ADC) {
      return "ID_MANAGER_MAGNETIC_ADC";
    }
    if (paramInt == ID_FILE____MAGNETIC_ADC) {
      return "ID_FILE____MAGNETIC_ADC";
    }
    if (paramInt == ID_MANAGER_MAGNETIC_SELF) {
      return "ID_MANAGER_MAGNETIC_SELF";
    }
    if (paramInt == ID_FILE____MAGNETIC_SELF) {
      return "ID_FILE____MAGNETIC_SELF";
    }
    if (paramInt == ID_MANAGER_MAGNETIC_OFFSETH) {
      return "ID_MANAGER_MAGNETIC_OFFSETH";
    }
    if (paramInt == ID_MANAGER_PROXIMITY) {
      return "ID_MANAGER_PROXIMITY";
    }
    if (paramInt == ID_FILE____PROXIMITY_ADC) {
      return "ID_FILE____PROXIMITY_ADC";
    }
    if (paramInt == ID_FILE____PROXIMITY_AVG) {
      return "ID_FILE____PROXIMITY_AVG";
    }
    if (paramInt == ID_FILE____PROXIMITY_OFFSET) {
      return "ID_FILE____PROXIMITY_OFFSET";
    }
    if (paramInt == ID_MANAGER_TEMPERATURE) {
      return "ID_MANAGER_TEMPERATURE";
    }
    if (paramInt == ID_MANAGER_HUMIDITY) {
      return "ID_MANAGER_HUMIDITY";
    }
    return "Unknown => id : " + paramInt;
  }
  
  public static ModuleSensor instance(Context paramContext)
  {
    if (mInstance == null) {
      mInstance = new ModuleSensor(paramContext);
    }
    return mInstance;
  }
  
  public void SensorOff()
  {
    if (this.mSensorNotification != null)
    {
      this.mSensorNotification.interrup();
      this.mSensorNotification = null;
    }
    this.mSensorRead.SensorOff();
    this.mIDArray_Manager = null;
    this.mIDArray_File = null;
    this.mIDArray_Intent = null;
  }
  
  public void SensorOff_Intent(int paramInt)
  {
    this.mSensorRead.SensorOff_Intent(paramInt);
    if ((this.mSensorNotification != null) && (this.mSensorNotification.isAlive()))
    {
      this.mSensorNotification.interrup();
      this.mSensorNotification = null;
    }
  }
  
  public void SensorOn(int[] paramArrayOfInt)
  {
    SensorOn(paramArrayOfInt, 1);
  }
  
  public void SensorOn(int[] paramArrayOfInt, int paramInt)
  {
    LOG_LEVEL = paramInt;
    FtUtil.log_e(this.CLASS_NAME, "SensorOn", "LOG_LEVEL : " + LOG_LEVEL);
    SensorOff();
    int[] arrayOfInt1 = new int[ID_COUNT_MANAGER];
    int[] arrayOfInt2 = new int[ID_COUNT_FILE];
    int[] arrayOfInt3 = new int[ID_COUNT_INTENT];
    int i = 0;
    int j = 0;
    int k = 0;
    if (paramArrayOfInt != null)
    {
      int m = 0;
      if (m < paramArrayOfInt.length)
      {
        if ((ID_SCOPE_MIN <= paramArrayOfInt[m]) && (paramArrayOfInt[m] < ID_COUNT_MANAGER))
        {
          int i8 = i + 1;
          arrayOfInt1[i] = paramArrayOfInt[m];
          i = i8;
        }
        for (;;)
        {
          m++;
          break;
          if ((ID_COUNT_MANAGER <= paramArrayOfInt[m]) && (paramArrayOfInt[m] < ID_COUNT_MANAGER + ID_COUNT_FILE))
          {
            int i7 = j + 1;
            arrayOfInt2[j] = paramArrayOfInt[m];
            j = i7;
          }
          else if ((ID_COUNT_MANAGER + ID_COUNT_FILE <= paramArrayOfInt[m]) && (paramArrayOfInt[m] <= ID_SCOPE_MAX))
          {
            int i6 = k + 1;
            arrayOfInt3[k] = paramArrayOfInt[m];
            k = i6;
          }
          else
          {
            FtUtil.log_e(this.CLASS_NAME, "SensorOn", "ID : Unknown (" + paramArrayOfInt[m] + ")");
          }
        }
      }
      if (i > 0)
      {
        FtUtil.log_e(this.CLASS_NAME, "SensorOn", "Manager");
        this.mIDArray_Manager = new int[i];
        for (int i4 = 0; i4 < i; i4++) {
          this.mIDArray_Manager[i4] = arrayOfInt1[i4];
        }
        FtUtil.log_d(this.CLASS_NAME, "SensorOn", " Manager ID");
        for (int i5 = 0; i5 < this.mIDArray_Manager.length; i5++) {
          FtUtil.log_d(this.CLASS_NAME, "SensorOn", "   [" + i5 + "] " + getString_ID(this.mIDArray_Manager[i5]));
        }
      }
      this.mIDArray_Manager = null;
      if (j > 0)
      {
        FtUtil.log_e(this.CLASS_NAME, "SensorOn", "File");
        this.mIDArray_File = new int[j];
        for (int i2 = 0; i2 < j; i2++) {
          this.mIDArray_File[i2] = arrayOfInt2[i2];
        }
        FtUtil.log_d(this.CLASS_NAME, "SensorOn", " File ID");
        for (int i3 = 0; i3 < this.mIDArray_File.length; i3++) {
          FtUtil.log_d(this.CLASS_NAME, "SensorOn", "   [" + i3 + "] " + getString_ID(this.mIDArray_File[i3]));
        }
      }
      this.mIDArray_File = null;
      if (k > 0)
      {
        FtUtil.log_e(this.CLASS_NAME, "SensorOn", "Intent");
        this.mIDArray_Intent = new int[k];
        for (int n = 0; n < k; n++) {
          this.mIDArray_Intent[n] = arrayOfInt3[n];
        }
        FtUtil.log_d(this.CLASS_NAME, "SensorOn", " Intent ID");
        for (int i1 = 0; i1 < this.mIDArray_Intent.length; i1++) {
          FtUtil.log_d(this.CLASS_NAME, "SensorOn", "   [" + i1 + "] " + getString_ID(this.mIDArray_Intent[i1]));
        }
      }
      this.mIDArray_Intent = null;
      this.mSensorRead.SensorOn(mContext, (SensorManager)getSystemService("sensor"), this.mIDArray_Manager, this.mIDArray_File, this.mIDArray_Intent);
    }
  }
  
  public void SensorOn(int[] paramArrayOfInt, Handler paramHandler, int paramInt)
  {
    SensorOn(paramArrayOfInt, paramHandler, paramInt, 1);
  }
  
  public void SensorOn(int[] paramArrayOfInt, Handler paramHandler, int paramInt1, int paramInt2)
  {
    SensorOn(paramArrayOfInt, paramInt2);
    if (paramInt1 > 0) {
      this.mSensorRead.setLoop_ReadFile();
    }
    for (;;)
    {
      if (paramArrayOfInt != null)
      {
        this.mSensorNotification = new SensorNotification(mContext, this.mIDArray_Manager, this.mIDArray_File);
        this.mSensorNotification.setLoopDelay(paramInt1);
        this.mSensorNotification.setHandler(paramHandler);
        this.mSensorNotification.setDaemon(true);
        this.mSensorNotification.start();
      }
      return;
      this.mIDArray_File = null;
    }
  }
  
  public String[] getData(int paramInt)
  {
    if (LOG_LEVEL <= 2) {
      FtUtil.log_d(this.CLASS_NAME, "getData", "id : " + getString_ID(paramInt));
    }
    if (paramInt == ID_MANAGER_ACCELEROMETER) {
      return this.mSensorRead.getAccelermeterXYZ(1);
    }
    if (paramInt == ID_FILE____ACCELEROMETER) {
      return this.mSensorRead.getAccelermeterXYZ(2);
    }
    if (paramInt == ID_MANAGER_ACCELEROMETER_N_ANGLE) {
      return this.mSensorRead.getAccelermeterXYZnAngle(1);
    }
    if (paramInt == ID_FILE____ACCELEROMETER_N_ANGLE) {
      return this.mSensorRead.getAccelermeterXYZnAngle(2);
    }
    if (paramInt == ID_MANAGER_ACCELEROMETER_SELF) {
      return this.mSensorRead.getAccelermeterSelf(1);
    }
    if (paramInt == ID_FILE____ACCELEROMETER_SELF) {
      return this.mSensorRead.getAccelermeterSelf(2);
    }
    if (paramInt == ID_FILE____ACCELEROMETER_CAL) {
      return this.mSensorRead.getAccelermeterCal(2);
    }
    if (paramInt == ID_FILE____ACCELEROMETER_INTPIN) {
      return this.mSensorRead.getAccelermeterIntpin(2);
    }
    if (paramInt == ID_INTENT__CP_ACCELEROMETER) {
      return this.mSensorRead.getAccelermeterXYZnAngle(3);
    }
    if (paramInt == ID_MANAGER_BAROMETER) {
      return this.mSensorRead.getBarometer(1);
    }
    if (paramInt == ID_FILE____BAROMETER_EEPROM) {
      return this.mSensorRead.getBarometerEEPROM(2);
    }
    if (paramInt == ID_INTENT__GRIP) {
      return this.mSensorRead.getGrip(3);
    }
    if (paramInt == ID_MANAGER_GYRO) {
      return this.mSensorRead.getGyro(1);
    }
    if (paramInt == ID_FILE____GYRO_POWER) {
      return this.mSensorRead.getGyroPower(2);
    }
    if (paramInt == ID_MANAGER_GYRO_EXPANSION) {
      return this.mSensorRead.getGyroExpansion(1);
    }
    if (paramInt == ID_MANAGER_GYRO_TEMPERATURE) {
      return this.mSensorRead.getGyroTemperature(1);
    }
    if (paramInt == ID_FILE____GYRO_TEMPERATURE) {
      return this.mSensorRead.getGyroTemperature(2);
    }
    if (paramInt == ID_MANAGER_GYRO_SELF) {
      return this.mSensorRead.getGyroSelf(1);
    }
    if (paramInt == ID_FILE____GYRO_SELFTEST) {
      return this.mSensorRead.getGyroSelf(2);
    }
    if (paramInt == ID_MANAGER_LIGHT) {
      return this.mSensorRead.getLight(1);
    }
    if (paramInt == ID_MANAGER_LIGHT_CCT) {
      return this.mSensorRead.getLightCCT(1);
    }
    if (paramInt == ID_FILE____LIGHT_ADC) {
      return this.mSensorRead.getLightADC(2);
    }
    if (paramInt == ID_FILE____LIGHT_RGBW) {
      return this.mSensorRead.getLightRGBW(2);
    }
    if (paramInt == ID_MANAGER_MAGNETIC) {
      return this.mSensorRead.getMagnetic(1);
    }
    if (paramInt == ID_MANAGER_MAGNETIC_POWER_ON) {
      return this.mSensorRead.getMagneticPowerOn(1);
    }
    if (paramInt == ID_FILE____MAGNETIC_POWER_ON) {
      return this.mSensorRead.getMagneticPowerOn(2);
    }
    if (paramInt == ID_MANAGER_MAGNETIC_POWER_OFF) {
      return this.mSensorRead.getMagneticPowerOff(1);
    }
    if (paramInt == ID_FILE____MAGNETIC_POWER_OFF) {
      return this.mSensorRead.getMagneticPowerOff(2);
    }
    if (paramInt == ID_MANAGER_MAGNETIC_STATUS) {
      return this.mSensorRead.getMagneticStatus(1);
    }
    if (paramInt == ID_FILE____MAGNETIC_STATUS) {
      return this.mSensorRead.getMagneticStatus(2);
    }
    if (paramInt == ID_MANAGER_MAGNETIC_TEMPERATURE) {
      return this.mSensorRead.getMagneticTemperature(1);
    }
    if (paramInt == ID_FILE____MAGNETIC_TEMPERATURE) {
      return this.mSensorRead.getMagneticTemperature(2);
    }
    if (paramInt == ID_MANAGER_MAGNETIC_DAC) {
      return this.mSensorRead.getMagneticDAC(1);
    }
    if (paramInt == ID_FILE____MAGNETIC_DAC) {
      return this.mSensorRead.getMagneticDAC(2);
    }
    if (paramInt == ID_MANAGER_MAGNETIC_ADC) {
      return this.mSensorRead.getMagneticADC(1);
    }
    if (paramInt == ID_FILE____MAGNETIC_ADC) {
      return this.mSensorRead.getMagneticADC(2);
    }
    if (paramInt == ID_MANAGER_MAGNETIC_SELF) {
      return this.mSensorRead.getMagneticSelf(1);
    }
    if (paramInt == ID_FILE____MAGNETIC_SELF) {
      return this.mSensorRead.getMagneticSelf(2);
    }
    if (paramInt == ID_MANAGER_MAGNETIC_OFFSETH) {
      return this.mSensorRead.getMagneticOffsetH(1);
    }
    if (paramInt == ID_MANAGER_PROXIMITY) {
      return this.mSensorRead.getProximity(1);
    }
    if (paramInt == ID_FILE____PROXIMITY_ADC) {
      return this.mSensorRead.getProximityADC(2);
    }
    if (paramInt == ID_FILE____PROXIMITY_AVG) {
      return this.mSensorRead.getProximityAVG(2);
    }
    if (paramInt == ID_FILE____PROXIMITY_OFFSET) {
      return this.mSensorRead.getProximityOffset(2);
    }
    if (paramInt == ID_MANAGER_TEMPERATURE) {
      return this.mSensorRead.getTemperature(1);
    }
    if (paramInt == ID_MANAGER_HUMIDITY) {
      return this.mSensorRead.getHumidity(1);
    }
    FtUtil.log_e(this.CLASS_NAME, "getData", "id : Unknown");
    return null;
  }
  
  public boolean isSensorOn(int paramInt)
  {
    return this.mSensorRead.isSensorOn(paramInt);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.ModuleSensor
 * JD-Core Version:    0.7.1
 */