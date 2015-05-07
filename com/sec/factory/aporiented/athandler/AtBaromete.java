package com.sec.factory.aporiented.athandler;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;
import java.io.File;
import java.text.DecimalFormat;

public class AtBaromete
  extends AtCommandHandler
{
  private int WHAT_GET_DATA = 2;
  private int WHAT_UPDATE = 1;
  private final char dummyData = ' ';
  public int mCurrentIndex = 0;
  float[] mPressureValue = new float[5];
  
  public AtBaromete(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "BAROMETE";
    this.CLASS_NAME = "AtBaromete";
    this.NUM_ARGS = 3;
    if (!new File(Support.Kernel.getFilePath("BAROMETE_DELTA")).exists())
    {
      mModuleCommon.writemBaroDelta("0");
      Support.Kernel.setPermission(Support.Kernel.getFilePath("BAROMETE_DELTA"), true, true, true, true, true, false);
      FtUtil.log_d(this.CLASS_NAME, "AtBaromete", "BAROMETE_DELTA is created...");
    }
    for (;;)
    {
      this.writer = paramResponseWriter;
      return;
      FtUtil.log_d(this.CLASS_NAME, "AtBaromete", "BAROMETE_DELTAis already existed...");
    }
  }
  
  public AtBaromete(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "BAROMETE";
    this.CLASS_NAME = "AtBaromete";
    this.NUM_ARGS = 3;
    if (!new File(Support.Kernel.getFilePath("BAROMETE_DELTA")).exists())
    {
      mModuleCommon.writemBaroDelta("0");
      Support.Kernel.setPermission(Support.Kernel.getFilePath("BAROMETE_DELTA"), true, true, true, true, true, false);
      FtUtil.log_d(this.CLASS_NAME, "AtBaromete", "BAROMETE_DELTA is created...");
    }
    for (;;)
    {
      this.writerCpo = paramResponseWriterCPO;
      return;
      FtUtil.log_d(this.CLASS_NAME, "AtBaromete", "BAROMETE_DELTAis already existed...");
    }
  }
  
  private void getDataBaro()
  {
    String[] arrayOfString = mModuleSensor.getData(ModuleSensor.ID_MANAGER_BAROMETER);
    if (arrayOfString != null)
    {
      this.mPressureValue[this.mCurrentIndex] = Float.valueOf(arrayOfString[2]).floatValue();
      FtUtil.log_i(this.CLASS_NAME, "getDataBaro : " + this.mCurrentIndex, "mPressureValue:" + this.mPressureValue[this.mCurrentIndex]);
      this.mCurrentIndex = (1 + this.mCurrentIndex);
    }
  }
  
  private void getDataBaroHandler()
  {
    Handler local1 = new Handler(this.context.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what == AtBaromete.this.WHAT_GET_DATA) {
          AtBaromete.this.getDataBaro();
        }
        while (paramAnonymousMessage.what != AtBaromete.this.WHAT_UPDATE) {
          return;
        }
        FtUtil.log_e(AtBaromete.this.CLASS_NAME, "mHandler.handleMessage", "WHAT_UPDATE");
        AtBaromete.this.getPressureResultUpdate();
        AtBaromete.this.mCurrentIndex = 0;
      }
    };
    local1.sendEmptyMessageDelayed(this.WHAT_GET_DATA, 100L);
    local1.sendEmptyMessageDelayed(this.WHAT_GET_DATA, 200L);
    local1.sendEmptyMessageDelayed(this.WHAT_GET_DATA, 300L);
    local1.sendEmptyMessageDelayed(this.WHAT_GET_DATA, 400L);
    local1.sendEmptyMessageDelayed(this.WHAT_GET_DATA, 500L);
    local1.sendEmptyMessageDelayed(this.WHAT_UPDATE, 600L);
  }
  
  private void getPressureResultUpdate()
  {
    float f1 = 0.0F;
    int i = this.mPressureValue.length;
    for (int j = 0; j < i; j++) {
      f1 += this.mPressureValue[j];
    }
    float f2 = f1 / i;
    String str1 = String.valueOf(new DecimalFormat("#.##").format(f2)).replace(",", ".");
    FtUtil.log_i(this.CLASS_NAME, "getPressureResultUpdate", "result :" + str1);
    if (getCmdType() == 0)
    {
      if (("NEW_ETS".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))) || ("NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))))
      {
        String str2 = FtUtil.addDummyValue(str1, 10, ' ');
        FtUtil.log_d(this.CLASS_NAME, "getPressureResultUpdate", "Pressure = " + str2);
        this.writerCpo.write(2, "6D", "01", str2);
        return;
      }
      this.writerCpo.write(2, "6D", "01", str1);
      return;
    }
    this.writer.write(responseString("1", str1));
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    Object localObject2;
    try
    {
      if ((paramArrayOfString.length != this.NUM_ARGS) && (paramArrayOfString.length != 3))
      {
        localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        return (String)localObject2;
      }
      if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
      {
        mModuleSensor.SensorOff();
        if (mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_BAROMETER))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,0 - Sensor Off : NG");
          str1 = responseNG(paramArrayOfString[0]);
          break label1188;
        }
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,0 - Sensor Off : OK");
        str1 = responseOK(paramArrayOfString[0]);
        break label1188;
      }
      if (!checkArgu(paramArrayOfString, new String[] { "0", "1", "0" })) {
        break label282;
      }
      mModuleSensor.SensorOff();
      mModuleCommon.writemBaroDelta("0");
      int[] arrayOfInt = new int[2];
      arrayOfInt[0] = ModuleSensor.ID_MANAGER_BAROMETER;
      arrayOfInt[1] = ModuleSensor.ID_FILE____BAROMETER_EEPROM;
      mModuleSensor.SensorOn(arrayOfInt);
      FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,1,0 ");
      try
      {
        Thread.sleep(500L);
        if (mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_BAROMETER))
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,1 - Sensor ON : OK");
          str1 = responseOK(paramArrayOfString[0]);
        }
      }
      catch (InterruptedException localInterruptedException2)
      {
        for (;;)
        {
          localInterruptedException2.printStackTrace();
        }
      }
      FtUtil.log_d(this.CLASS_NAME, "handleCommand", "0,1 - Sensor ON : NG");
    }
    finally {}
    String str1 = responseNG(paramArrayOfString[0]);
    break label1188;
    label282:
    String str2;
    float f1;
    if (checkArgu(paramArrayOfString, new String[] { "1", "0", "0" }))
    {
      if (mModuleSensor.isSensorOn(ModuleSensor.ID_FILE____BAROMETER_EEPROM))
      {
        String[] arrayOfString2 = mModuleSensor.getData(ModuleSensor.ID_FILE____BAROMETER_EEPROM);
        if (arrayOfString2 != null)
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Total Read : " + arrayOfString2[1]);
          if (arrayOfString2[2].equals("1")) {
            str1 = responseOK(paramArrayOfString[0]);
          } else {
            str1 = responseNG(paramArrayOfString[0]);
          }
        }
        else
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Total Read : NG (null)");
          str1 = responseNG(paramArrayOfString[0]);
        }
      }
      else
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,0 - Total Read : NG (Sensor off)");
        str1 = responseNG(paramArrayOfString[0]);
      }
    }
    else if (checkArgu(paramArrayOfString, new String[] { "1", "1", "0" }))
    {
      if (mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_BAROMETER))
      {
        String[] arrayOfString1 = mModuleSensor.getData(ModuleSensor.ID_MANAGER_BAROMETER);
        if (arrayOfString1 != null)
        {
          if (("NEW_ETS".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))) || ("NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))))
          {
            String str8 = FtUtil.addDummyValue(arrayOfString1[4], 10, ' ');
            str1 = responseString(paramArrayOfString[0], str8);
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,1 - Test result : Temperature = " + str8);
          }
          else
          {
            str1 = responseString(paramArrayOfString[0], arrayOfString1[4]);
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,1 - Test result : Temperature = " + arrayOfString1[4]);
          }
        }
        else
        {
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,1 - Test result : NG (null)");
          str1 = responseNG(paramArrayOfString[0]);
        }
      }
      else
      {
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,1 - Test result : NG (Sensor Off)");
        str1 = responseNG(paramArrayOfString[0]);
      }
    }
    else if (checkArgu(paramArrayOfString, new String[] { "1", "2", "0" }))
    {
      FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,2,0 ");
      try
      {
        Thread.sleep(500L);
        if (mModuleSensor.isSensorOn(ModuleSensor.ID_MANAGER_BAROMETER)) {
          if (mModuleSensor.getData(ModuleSensor.ID_MANAGER_BAROMETER) != null)
          {
            getDataBaroHandler();
            int i = getCmdType();
            str1 = null;
            if (i != 0) {
              break label1188;
            }
            str1 = "WAIT";
          }
        }
      }
      catch (InterruptedException localInterruptedException1)
      {
        for (;;)
        {
          localInterruptedException1.printStackTrace();
        }
        FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,2 - Test result : NG (null)");
        str1 = responseNG(paramArrayOfString[0]);
      }
      FtUtil.log_d(this.CLASS_NAME, "handleCommand", "1,2 - Test result : NG (Sensor Off)");
      str1 = responseNG(paramArrayOfString[0]);
    }
    else if (checkArgu(paramArrayOfString, new String[] { "1", "3", "0" }))
    {
      String str4 = mModuleCommon.readmBaroDelta();
      String str5 = Support.Kernel.read("BAROMETE_VENDOR");
      FtUtil.log_i(this.CLASS_NAME, "readmBaroDelta", "vendor=" + str5);
      float f2 = Float.parseFloat(str4);
      if ("STM".equals(str5)) {}
      String str6;
      for (f2 /= 4096.0F;; f2 /= 100.0F) {
        do
        {
          FtUtil.log_i(this.CLASS_NAME, "readmBaroDelta", "result=" + f2);
          str6 = Float.toString(f2);
          if ((!"NEW_ETS".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))) && (!"NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL")))) {
            break;
          }
          String str7 = FtUtil.addDummyValue(str6, 10, ' ');
          str1 = responseString(paramArrayOfString[0], str7);
          break label1188;
        } while ((!"BOCH".equals(str5)) && (!"BOSCH".equals(str5)));
      }
      str1 = responseString(paramArrayOfString[0], str6);
    }
    else if (checkArgu(paramArrayOfString, new String[] { "2", "0" }))
    {
      str2 = Support.Kernel.read("BAROMETE_VENDOR");
      f1 = Float.parseFloat(paramArrayOfString[2]);
      if ("STM".equals(str2)) {
        f1 *= 4096.0F;
      }
    }
    for (;;)
    {
      String str3 = Integer.toString((int)f1);
      mModuleCommon.writemBaroDelta(str3);
      str1 = responseOK(paramArrayOfString[0]);
      if (!"BOCH".equals(str2))
      {
        if (!"BOSCH".equals(str2)) {
          continue;
        }
        break label1194;
        str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        label1188:
        localObject2 = str1;
        break;
      }
      label1194:
      f1 *= 100.0F;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtBaromete
 * JD-Core Version:    0.7.1
 */