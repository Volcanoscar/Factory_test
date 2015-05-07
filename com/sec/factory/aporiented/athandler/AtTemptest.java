package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModulePower;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;

public class AtTemptest
  extends AtCommandHandler
{
  private final int NEW_DM_DATA_LEN = 10;
  private boolean isMSM8930 = "MSM8930".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isMSM8960 = "MSM8960".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isPegaPrime = "PegaPrime".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isPegasus = "Pegasus".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isPegasusPrime = "PegasusPrime".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isSC5C210ABB = "SC5C210ABB-A040".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  
  public AtTemptest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "TEMPTEST";
    this.CLASS_NAME = "AtTemptest";
    this.NUM_ARGS = 3;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    Object localObject2;
    String str1;
    try
    {
      if (paramArrayOfString.length != this.NUM_ARGS)
      {
        localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        return (String)localObject2;
      }
      if (checkArgu(paramArrayOfString, new String[] { "1", "0", "0" }))
      {
        String str9 = mModulePower.readBatteryTemp();
        str1 = responseString(paramArrayOfString[0], str9);
        if (("NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))) || ("NEW_ETS".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL")))) {
          str1 = FtUtil.addDummyValue(str1, 10, '\000');
        }
      }
      else if (checkArgu(paramArrayOfString, new String[] { "1", "0", "3" }))
      {
        String str8 = String.valueOf(Integer.valueOf(mModulePower.readApChipTemp()).intValue() / 10);
        str1 = responseString(paramArrayOfString[0], str8);
        if (("NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))) || ("NEW_ETS".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL")))) {
          str1 = FtUtil.addDummyValue(str1, 10, '\000');
        }
      }
      else if (checkArgu(paramArrayOfString, new String[] { "1", "0", "6" }))
      {
        String str7 = mModulePower.readExternalApChipTemp();
        str1 = responseString(paramArrayOfString[0], str7);
        if (("NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))) || ("NEW_ETS".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL")))) {
          str1 = FtUtil.addDummyValue(str1, 10, '\000');
        }
      }
      else if (checkArgu(paramArrayOfString, new String[] { "1", "1", "0" }))
      {
        String str6 = mModulePower.readBatteryTempAdc();
        str1 = responseString(paramArrayOfString[0], str6);
        if (("NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))) || ("NEW_ETS".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL")))) {
          str1 = FtUtil.addDummyValue(str1, 10, '\000');
        }
      }
      else
      {
        if (checkArgu(paramArrayOfString, new String[] { "1", "0", "1" })) {
          break label997;
        }
        if (checkArgu(paramArrayOfString, new String[] { "1", "0", "2" })) {
          break label997;
        }
        if (checkArgu(paramArrayOfString, new String[] { "1", "1", "1" })) {
          break label997;
        }
        if (checkArgu(paramArrayOfString, new String[] { "1", "1", "2" })) {
          break label997;
        }
        if ((this.isPegasus) || (this.isPegasusPrime) || (this.isPegaPrime) || ((FtUtil.isFactoryAppAPO()) && (this.isSC5C210ABB)))
        {
          if (checkArgu(paramArrayOfString, new String[] { "1", "1", "4" })) {
            break label1003;
          }
          if (checkArgu(paramArrayOfString, new String[] { "1", "1", "5" })) {
            break label1003;
          }
          if (checkArgu(paramArrayOfString, new String[] { "1", "1", "7" })) {
            break label1003;
          }
        }
        if ((this.isMSM8960) || (this.isMSM8930)) {
          if (checkArgu(paramArrayOfString, new String[] { "1", "1", "4" }))
          {
            String str4 = Support.Kernel.read("PA0_THERMISTER_ADC");
            FtUtil.log_i(this.CLASS_NAME, "PA0_THERMISTER_ADC", "adc=" + str4);
            int j = str4.indexOf("Raw:");
            String str5 = str4.substring(j + "Raw:".length());
            FtUtil.log_i(this.CLASS_NAME, "PA0_THERMISTER_ADC", "start=" + j + ", res=" + str5);
            str1 = responseString(paramArrayOfString[0], str5);
            if ((!"NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))) && (!"NEW_ETS".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL")))) {
              break label991;
            }
            str1 = FtUtil.addDummyValue(str1, 10, '\000');
            break label991;
          }
        }
        if ((this.isMSM8960) || (this.isMSM8930)) {
          if (checkArgu(paramArrayOfString, new String[] { "1", "1", "5" }))
          {
            String str2 = Support.Kernel.read("PA1_THERMISTER_ADC");
            FtUtil.log_i(this.CLASS_NAME, "PA1_THERMISTER_ADC", "adc=" + str2);
            int i = str2.indexOf("Raw:");
            String str3 = str2.substring(i + "Raw:".length());
            FtUtil.log_i(this.CLASS_NAME, "PA1_THERMISTER_ADC", "start=" + i + ", res=" + str3);
            str1 = responseString(paramArrayOfString[0], str3);
            if ((!"NEW_DM".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL"))) && (!"NEW_ETS".equals(Support.Feature.getString("FACTORY_TEST_PROTOCOL")))) {
              break label991;
            }
            str1 = FtUtil.addDummyValue(str1, 10, '\000');
            break label991;
          }
        }
        str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      }
    }
    finally {}
    for (;;)
    {
      label991:
      localObject2 = str1;
      break;
      label997:
      str1 = null;
      continue;
      label1003:
      str1 = null;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtTemptest
 * JD-Core Version:    0.7.1
 */