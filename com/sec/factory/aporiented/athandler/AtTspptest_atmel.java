package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDevice;

public class AtTspptest_atmel
  extends AtCommandHandler
{
  public AtTspptest_atmel(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "TSPPTEST";
    this.CLASS_NAME = "AtTspptest_atmel";
    this.NUM_ARGS = 3;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      String str1;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
        {
          mModuleDevice.tsp_module_mode(1);
          str1 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "1", "0" }))
        {
          mModuleDevice.tsp_module_mode(0);
          str1 = responseOK(paramArrayOfString[0]);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "2", "0" }))
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "3", "0" }))
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
        else if (checkArgu(paramArrayOfString, new String[] { "0", "4", "0" }))
        {
          if (mModuleDevice.startTSPConnectionTest_atmel_temporary().equals("NG")) {
            str1 = responseNG(paramArrayOfString[0]);
          } else {
            str1 = responseOK(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "0", "0" }))
        {
          String str6 = mModuleDevice.tsp_module_mode(2);
          str1 = responseString(paramArrayOfString[0], str6);
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "1" }))
        {
          String str5 = mModuleDevice.tsp_module_data_read(paramArrayOfString[2], "TSP_COMMAND_REFER_SET", "TSP_COMMAND_REFER_DATA");
          if (str5.equals("NG")) {
            str1 = responseNG(paramArrayOfString[0]);
          } else {
            str1 = responseString(paramArrayOfString[0], str5);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "2" }))
        {
          String str4 = mModuleDevice.tsp_module_data_read(paramArrayOfString[2], "TSP_COMMAND_DELTA_SET", "TSP_COMMAND_DELTA_DATA");
          if (str4.equals("NG")) {
            str1 = responseNG(paramArrayOfString[0]);
          } else {
            str1 = responseString(paramArrayOfString[0], str4);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "3" }))
        {
          String str3 = mModuleDevice.tsp_module_refer_diff(paramArrayOfString[2], "TSP_COMMAND_REFER_SET", "TSP_COMMAND_REFER_DATA");
          if (str3.equals("NG")) {
            str1 = responseNG(paramArrayOfString[0]);
          } else {
            str1 = responseString(paramArrayOfString[0], str3);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "4" }))
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "5", "0" }))
        {
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(ModuleDevice.TSP_NODE_X);
          arrayOfObject[1] = Integer.valueOf(ModuleDevice.TSP_NODE_Y);
          String str2 = String.format("%d,%d", arrayOfObject);
          str1 = responseString(paramArrayOfString[0], str2);
        }
        else
        {
          str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      Object localObject2 = str1;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtTspptest_atmel
 * JD-Core Version:    0.7.1
 */