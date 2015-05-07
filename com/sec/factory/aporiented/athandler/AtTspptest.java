package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Spec;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class AtTspptest
  extends AtCommandHandler
{
  private String mTspManufacture = Support.Feature.getString("TSP_MANUFACTURE");
  
  public AtTspptest(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "TSPPTEST";
    this.CLASS_NAME = "AtTspptest";
    this.NUM_ARGS = 3;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    Object localObject2;
    String str2;
    ArrayList localArrayList;
    for (String str1 = "";; str1 = mModuleDevice.startTSPTest("run_rawcap_read")) {
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
        {
          if (mModuleDevice.startTSPTest("module_off_master").equals("NG"))
          {
            str2 = responseNG(paramArrayOfString[0]);
            break label2077;
          }
          str2 = responseOK(paramArrayOfString[0]);
          break label2077;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0" }))
        {
          if (mModuleDevice.startTSPTest("module_on_master").equals("NG"))
          {
            str2 = responseNG(paramArrayOfString[0]);
            break label2077;
          }
          str2 = responseOK(paramArrayOfString[0]);
          break label2077;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "2", "0" }))
        {
          if (mModuleDevice.startTSPTest("module_off_slave").equals("NG"))
          {
            str2 = responseNG(paramArrayOfString[0]);
            break label2077;
          }
          str2 = responseOK(paramArrayOfString[0]);
          break label2077;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "3", "0" }))
        {
          if (mModuleDevice.startTSPTest("module_on_slave").equals("NG"))
          {
            str2 = responseNG(paramArrayOfString[0]);
            break label2077;
          }
          str2 = responseOK(paramArrayOfString[0]);
          break label2077;
        }
        if (!checkArgu(paramArrayOfString, new String[] { "0", "4", "0" })) {
          break label513;
        }
        localArrayList = new ArrayList();
        if ("MELFAS".equals(this.mTspManufacture)) {
          str1 = mModuleDevice.startTSPTest("run_cm_delta_read");
        }
        while (!"SYNAPTICS".equals(this.mTspManufacture))
        {
          StringTokenizer localStringTokenizer = new StringTokenizer(str1, ",");
          while (localStringTokenizer.hasMoreTokens()) {
            localArrayList.add(new Ref_value(localStringTokenizer.nextToken()));
          }
        }
      }
      finally {}
    }
    int i = Integer.parseInt(((Ref_value)localArrayList.get(0)).get_string());
    int j = Support.Spec.getInt("TSP_CONNECTION_MIN");
    FtUtil.log_d(this.CLASS_NAME, "handleCommand", "connection_value : " + i + "tspconnection_spec : " + j);
    label513:
    int i12;
    label776:
    label837:
    int i8;
    label1162:
    label1223:
    int i4;
    label1509:
    int k;
    if (i > j)
    {
      str2 = responseOK(paramArrayOfString[0]);
    }
    else
    {
      str2 = responseNG(paramArrayOfString[0]);
      break label2077;
      if (checkArgu(paramArrayOfString, new String[] { "1", "0", "0" }))
      {
        String str5 = mModuleDevice.startTSPTest("get_chip_vendor");
        String str6 = str5 + "," + mModuleDevice.startTSPTest("get_chip_name");
        str2 = responseString(paramArrayOfString[0], str6);
      }
      else
      {
        boolean bool1 = checkArgu(paramArrayOfString, new String[] { "1", "1" });
        if (bool1)
        {
          int i10;
          try
          {
            int i13 = Integer.parseInt(paramArrayOfString[2], 10);
            i10 = i13 - 1;
          }
          catch (NumberFormatException localNumberFormatException4)
          {
            for (;;)
            {
              FtUtil.log_d(this.CLASS_NAME, "handleCommand", "error - parameter X_AXIS : " + 0);
              responseNG(paramArrayOfString[0]);
              i10 = 0;
            }
          }
          if ("MELFAS".equals(this.mTspManufacture))
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "READ 1 : CM_ABS_RUN");
            str1 = mModuleDevice.startTSPTest("run_cm_abs_read");
          }
          for (;;)
          {
            if (!str1.equals("NG")) {
              break label837;
            }
            str2 = responseNG(paramArrayOfString[0]);
            break label2077;
            if ("SYNAPTICS".equals(this.mTspManufacture))
            {
              int i11 = Integer.parseInt(mModuleDevice.startTSPTest("get_x_num"));
              if ((i10 < 0) || (i10 >= i11)) {
                break label2084;
              }
              i12 = 1;
              if (i12 == 0)
              {
                localObject2 = responseNA();
                break;
              }
              str1 = mModuleDevice.startTSPTest("run_rawcap_read");
              continue;
            }
            if ("STM".equals(this.mTspManufacture))
            {
              FtUtil.log_d(this.CLASS_NAME, "handleCommand", "READ 1 : RUN_REFERENCE");
              str1 = mModuleDevice.startTSPTest("run_reference_read");
            }
          }
          if ("MELFAS".equals(this.mTspManufacture))
          {
            FtUtil.log_d(this.CLASS_NAME, "handleCommand", "READ 1 : CM_ABS_READ");
            str1 = mModuleDevice.startTSPReadTest("get_cm_abs", i10);
          }
          while (str1.equals("NG"))
          {
            str2 = responseNG(paramArrayOfString[0]);
            break label2077;
            if ("SYNAPTICS".equals(this.mTspManufacture))
            {
              str1 = mModuleDevice.startTSPReadTest("get_rawcap", i10);
            }
            else if ("STM".equals(this.mTspManufacture))
            {
              FtUtil.log_d(this.CLASS_NAME, "handleCommand", "READ 1 : GET_REFERENCE");
              str1 = mModuleDevice.startTSPReadTest("get_reference", i10);
            }
          }
          FtUtil.log_d(this.CLASS_NAME, "handleCommand", "READ 1 : " + str1);
          str2 = responseString(paramArrayOfString[0], str1);
        }
        else
        {
          boolean bool2 = checkArgu(paramArrayOfString, new String[] { "1", "2" });
          if (bool2)
          {
            int i6;
            try
            {
              int i9 = Integer.parseInt(paramArrayOfString[2]);
              i6 = i9 - 1;
            }
            catch (NumberFormatException localNumberFormatException3)
            {
              for (;;)
              {
                FtUtil.log_d(this.CLASS_NAME, "handleCommand", "error - parameter X_AXIS : " + 0);
                responseNG(paramArrayOfString[0]);
                i6 = 0;
              }
            }
            if ("MELFAS".equals(this.mTspManufacture)) {
              str1 = mModuleDevice.startTSPTest("run_cm_delta_read");
            }
            for (;;)
            {
              if (!str1.equals("NG")) {
                break label1223;
              }
              str2 = responseNG(paramArrayOfString[0]);
              break label2077;
              if ("SYNAPTICS".equals(this.mTspManufacture))
              {
                int i7 = Integer.parseInt(mModuleDevice.startTSPTest("get_y_num"));
                if ((i6 < 0) || (i6 >= i7)) {
                  break label2090;
                }
                i8 = 1;
                if (i8 == 0)
                {
                  localObject2 = responseNA();
                  break;
                }
                str1 = mModuleDevice.startTSPTest("run_rx_to_rx_read");
                continue;
              }
              if ("STM".equals(this.mTspManufacture))
              {
                FtUtil.log_d(this.CLASS_NAME, "handleCommand", "READ 1 : RUN_RAW");
                str1 = mModuleDevice.startTSPTest("run_raw_read");
              }
            }
            if ("MELFAS".equals(this.mTspManufacture)) {
              str1 = mModuleDevice.startTSPReadTest("get_cm_delta", i6);
            }
            while (str1.equals("NG"))
            {
              str2 = responseNG(paramArrayOfString[0]);
              break label2077;
              if ("SYNAPTICS".equals(this.mTspManufacture))
              {
                str1 = mModuleDevice.startTSPReadTest("get_rx_to_rx", i6);
              }
              else if ("STM".equals(this.mTspManufacture))
              {
                FtUtil.log_d(this.CLASS_NAME, "handleCommand", "READ 1 : GET_RAW");
                str1 = mModuleDevice.startTSPReadTest("get_raw", i6);
              }
            }
            str2 = responseString(paramArrayOfString[0], str1);
          }
          else
          {
            boolean bool3 = checkArgu(paramArrayOfString, new String[] { "1", "3" });
            if (bool3)
            {
              int i2;
              try
              {
                int i5 = Integer.parseInt(paramArrayOfString[2]);
                i2 = i5 - 1;
              }
              catch (NumberFormatException localNumberFormatException2)
              {
                for (;;)
                {
                  FtUtil.log_d(this.CLASS_NAME, "handleCommand", "error - parameter X_AXIS : " + 0);
                  responseNG(paramArrayOfString[0]);
                  i2 = 0;
                }
              }
              if ("MELFAS".equals(this.mTspManufacture)) {
                str1 = mModuleDevice.startTSPTest("run_intensity_read");
              }
              for (;;)
              {
                if (!str1.equals("NG")) {
                  break label1570;
                }
                str2 = responseNG(paramArrayOfString[0]);
                break label2077;
                if ("SYNAPTICS".equals(this.mTspManufacture))
                {
                  int i3 = Integer.parseInt(mModuleDevice.startTSPTest("get_x_num"));
                  if ((i2 < 0) || (i2 >= i3)) {
                    break label2096;
                  }
                  i4 = 1;
                  if (i4 == 0)
                  {
                    localObject2 = responseNA();
                    break;
                  }
                  str1 = mModuleDevice.startTSPTest("run_tx_to_tx_read");
                  continue;
                }
                if ("STM".equals(this.mTspManufacture))
                {
                  FtUtil.log_d(this.CLASS_NAME, "handleCommand", "READ 1 : RUN_DELTA");
                  str1 = mModuleDevice.startTSPTest("run_delta_read");
                }
              }
              label1570:
              if ("MELFAS".equals(this.mTspManufacture)) {
                str1 = mModuleDevice.startTSPReadTest("get_intensity", i2);
              }
              for (;;)
              {
                if ("STM".equals(this.mTspManufacture))
                {
                  FtUtil.log_d(this.CLASS_NAME, "handleCommand", "READ 1 : GET_DELTA");
                  str1 = mModuleDevice.startTSPReadTest("get_delta", i2);
                }
                if (!str1.equals("NG")) {
                  break;
                }
                str2 = responseNG(paramArrayOfString[0]);
                break label2077;
                if ("SYNAPTICS".equals(this.mTspManufacture)) {
                  str1 = mModuleDevice.startTSPTest("get_tx_to_tx," + i2);
                }
              }
              str2 = responseString(paramArrayOfString[0], str1);
            }
            else
            {
              boolean bool4 = checkArgu(paramArrayOfString, new String[] { "1", "4" });
              if (bool4)
              {
                try
                {
                  int i1 = Integer.parseInt(paramArrayOfString[2]);
                  k = i1 - 1;
                }
                catch (NumberFormatException localNumberFormatException1)
                {
                  label1758:
                  do
                  {
                    for (;;)
                    {
                      FtUtil.log_d(this.CLASS_NAME, "handleCommand", "error - parameter X_AXIS : " + 0);
                      responseNG(paramArrayOfString[0]);
                      k = 0;
                    }
                  } while (!"SYNAPTICS".equals(this.mTspManufacture));
                  m = Integer.parseInt(mModuleDevice.startTSPTest("get_x_num"));
                  if (k < 0) {
                    break label2102;
                  }
                }
                if ("MELFAS".equals(this.mTspManufacture))
                {
                  str1 = "NA";
                  if (str1.equals("NA"))
                  {
                    str2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
                    break label2077;
                  }
                }
                else
                {
                  int m;
                  if (k >= m) {
                    break label2102;
                  }
                }
              }
            }
          }
        }
      }
    }
    label2077:
    label2084:
    label2090:
    label2096:
    label2102:
    for (int n = 1;; n = 0)
    {
      if (n == 0)
      {
        localObject2 = responseNA();
        break;
      }
      str1 = mModuleDevice.startTSPTest("run_tx_to_gnd_read");
      break label1758;
      if (str1.equals("NG"))
      {
        str2 = responseNG(paramArrayOfString[0]);
      }
      else
      {
        if ("SYNAPTICS".equals(this.mTspManufacture))
        {
          mModuleDevice.startTSPReadTest("get_tx_to_gnd", k);
          str1 = mModuleDevice.startTSPTest("get_tx_to_gnd," + k);
        }
        if (str1.equals("NG"))
        {
          str2 = responseNG(paramArrayOfString[0]);
        }
        else
        {
          str2 = responseString(paramArrayOfString[0], str1);
          break label2077;
          if (checkArgu(paramArrayOfString, new String[] { "1", "5", "0" }))
          {
            String str3 = mModuleDevice.startTSPTest("get_x_num");
            String str4 = str3 + "," + mModuleDevice.startTSPTest("get_y_num");
            str2 = responseString(paramArrayOfString[0], str4);
          }
          else
          {
            str2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          }
        }
      }
      localObject2 = str2;
      break;
      i12 = 0;
      break label776;
      i8 = 0;
      break label1162;
      i4 = 0;
      break label1509;
    }
  }
  
  class Ref_value
  {
    String ref_string;
    
    public Ref_value(String paramString)
    {
      this.ref_string = paramString;
    }
    
    String get_string()
    {
      return this.ref_string;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtTspptest
 * JD-Core Version:    0.7.1
 */