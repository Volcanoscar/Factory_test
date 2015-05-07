package com.sec.factory.aporiented.athandler;

import android.app.BarBeamFactory;
import android.app.Hop;
import android.content.Context;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.FtUtil;

public class AtBarcodeE
  extends AtCommandHandler
{
  public static int Barquietzone = 10;
  private static final byte[][] barcodeByte = { { -1, -84, -37, 33, 92, -99, 66, -82, -37, -117, 26, 100, -102, -1 }, { -1, -84, -37, 33, 92, -99, 66, -82, -37, -117, 26, 100, -102, -1 }, { -1, -83, -112, -79, 78, -11, -70, -83, -59, -115, 50, 77, -70, -1 }, { -1, -21, 54, -56, 87, 42, -59, 123, -79, -85, -1 }, { -1, -9, 68, 81, 23, 81, 69, -43, 29, 17, 71, 87, 81, 17, 29, 84, 116, 87, 68, 95, -1 }, { -1, -53, 123, -71, 112, -81, 101, 55, -78, 54, 50, 99, 69, -104, -39, 56, -89, -1 }, { -1, -11, 45, 76, -106, -78, -51, 90, -103, 47, -1 }, { -1, -23, -75, 77, 90, 77, 84, -76, -83, 106, 90, 86, 84, -75, 86, 83, 111, -1 }, { -34, -69, -56, 7, -90, 122, 17, -67, -38, 31, -116, -10, -28, -128, -93, -127, -21 } };
  private static String[] barcodeType = { "EAN_13", "UPC_1", "EAN", "EAN_8", "CODE_39", "CODE_128", "INTERLEAVED2of5", "CODABAR", "DATABAR" };
  private Hop[] hopSequenceArray;
  
  public AtBarcodeE(Context paramContext)
  {
    super(paramContext);
    Hop[] arrayOfHop = new Hop[10];
    arrayOfHop[0] = new Hop(0, 50, 50, 20, 31);
    arrayOfHop[1] = new Hop(20, 20, 50, 10, 31);
    arrayOfHop[2] = new Hop(2, 50, 50, 12, 31);
    arrayOfHop[3] = new Hop(8, 50, 50, 10, 31);
    arrayOfHop[4] = new Hop(12, 30, 50, 10, 31);
    arrayOfHop[5] = new Hop(1, 50, 50, 16, 31);
    arrayOfHop[6] = new Hop(14, 30, 50, 10, 31);
    arrayOfHop[7] = new Hop(4, 50, 50, 10, 31);
    arrayOfHop[8] = new Hop(10, 40, 50, 10, 31);
    arrayOfHop[9] = new Hop(6, 50, 50, 10, 31);
    this.hopSequenceArray = arrayOfHop;
    this.CMD_NAME = "BARCODEE";
    this.CLASS_NAME = "AtBarcodeE";
    this.NUM_ARGS = 3;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    Object localObject3;
    String str1;
    try
    {
      localBarBeamFactory = new BarBeamFactory(this.context);
    }
    catch (Exception localException6)
    {
      for (;;)
      {
        try
        {
          localBarBeamFactory.setHopSequence(this.hopSequenceArray);
          if (paramArrayOfString.length == this.NUM_ARGS) {
            continue;
          }
          localObject3 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject3;
        }
        catch (Exception localException1) {}finally
        {
          BarBeamFactory localBarBeamFactory;
          boolean bool1;
          int i;
          boolean bool2;
          continue;
        }
        localException6 = localException6;
        FtUtil.log_i(this.CLASS_NAME, "handleCommand", "Fail to get BarBeamFactory instance");
        localObject3 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        continue;
        if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
        {
          FtUtil.log_i(this.CLASS_NAME, "handleCommand", "start Barcode Emul self test");
          try
          {
            String str3 = mModuleDevice.readModuleFirmwareVersion(5);
            str2 = str3;
          }
          catch (Exception localException5)
          {
            FtUtil.log_i(this.CLASS_NAME, "handleCommand", "Barcode Emul self test is fail!!");
            String str2 = "NG";
            continue;
          }
          FtUtil.log_i(this.CLASS_NAME, "handleCommand", "Barcode Emulator firmware version: " + str2);
          if (str2.length() == 0) {
            break label499;
          }
          str1 = responseString(paramArrayOfString[0], "OK_" + str2);
          break label492;
        }
        bool1 = checkArgu(paramArrayOfString, new String[] { "0", "1" });
        if (bool1)
        {
          try
          {
            i = Integer.parseInt(paramArrayOfString[2]);
            if ((i < barcodeType.length) && (i > 0)) {
              continue;
            }
            FtUtil.log_i(this.CLASS_NAME, "handleCommand", "Barcode type is invalid - out of index");
            localObject3 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          }
          catch (Exception localException3)
          {
            FtUtil.log_i(this.CLASS_NAME, "handleCommand", "Barcode type is invalid type: " + paramArrayOfString[2]);
            localObject3 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          }
          continue;
          try
          {
            if (localBarBeamFactory.StartBarBeamFactory(barcodeByte[i]))
            {
              FtUtil.log_i(this.CLASS_NAME, "handleCommand", "Success to startBarBeamFactory - type: " + barcodeType[i]);
              str1 = responseString(paramArrayOfString[0], "OK");
            }
            else
            {
              str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
            }
          }
          catch (Exception localException4)
          {
            FtUtil.log_i(this.CLASS_NAME, "handleCommand", "Fail to startBarBeamFactory");
            localObject3 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          }
        }
        else
        {
          bool2 = checkArgu(paramArrayOfString, new String[] { "0", "2", "0" });
          if (!bool2) {
            continue;
          }
          try
          {
            if (localBarBeamFactory.StopBarBeamFactory()) {
              str1 = responseString(paramArrayOfString[0], "OK");
            } else {
              str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
            }
          }
          catch (Exception localException2)
          {
            FtUtil.log_i(this.CLASS_NAME, "handleCommand", "Fail to stopBarBeamFactory");
            localObject3 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          }
        }
      }
      str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
      break label492;
    }
    finally {}
    throw ((Throwable)localObject1);
    for (;;)
    {
      label492:
      localObject3 = str1;
      break;
      label499:
      str1 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtBarcodeE
 * JD-Core Version:    0.7.1
 */