package com.sec.factory.aporiented;

import android.content.Context;
import android.content.Intent;
import android.os.SystemProperties;
import com.sec.factory.aporiented.athandler.AtAcsensor;
import com.sec.factory.aporiented.athandler.AtArotatec;
import com.sec.factory.aporiented.athandler.AtBakupchk;
import com.sec.factory.aporiented.athandler.AtBarcodeE;
import com.sec.factory.aporiented.athandler.AtBaromete;
import com.sec.factory.aporiented.athandler.AtBattcali;
import com.sec.factory.aporiented.athandler.AtBatttest;
import com.sec.factory.aporiented.athandler.AtBtidtest;
import com.sec.factory.aporiented.athandler.AtCalidate;
import com.sec.factory.aporiented.athandler.AtCallConn;
import com.sec.factory.aporiented.athandler.AtCametest;
import com.sec.factory.aporiented.athandler.AtCommandHandler;
import com.sec.factory.aporiented.athandler.AtCorectrl;
import com.sec.factory.aporiented.athandler.AtDebugLvC;
import com.sec.factory.aporiented.athandler.AtDisptest;
import com.sec.factory.aporiented.athandler.AtEmemtest;
import com.sec.factory.aporiented.athandler.AtEthernet;
import com.sec.factory.aporiented.athandler.AtEwriteck;
import com.sec.factory.aporiented.athandler.AtFactolog;
import com.sec.factory.aporiented.athandler.AtFactorst;
import com.sec.factory.aporiented.athandler.AtFaildump;
import com.sec.factory.aporiented.athandler.AtFailhist;
import com.sec.factory.aporiented.athandler.AtFcbttest;
import com.sec.factory.aporiented.athandler.AtFceptest;
import com.sec.factory.aporiented.athandler.AtFceptest_dual;
import com.sec.factory.aporiented.athandler.AtFcestest;
import com.sec.factory.aporiented.athandler.AtFcfmtest;
import com.sec.factory.aporiented.athandler.AtFcmptest;
import com.sec.factory.aporiented.athandler.AtFeliCaRfCal;
import com.sec.factory.aporiented.athandler.AtFirmvers;
import com.sec.factory.aporiented.athandler.AtFsbuildc;
import com.sec.factory.aporiented.athandler.AtFuelgaic;
import com.sec.factory.aporiented.athandler.AtFunctest;
import com.sec.factory.aporiented.athandler.AtGeomagss;
import com.sec.factory.aporiented.athandler.AtGpsstest;
import com.sec.factory.aporiented.athandler.AtGyroscop;
import com.sec.factory.aporiented.athandler.AtHdmitest;
import com.sec.factory.aporiented.athandler.AtHumitemp;
import com.sec.factory.aporiented.athandler.AtHwindick;
import com.sec.factory.aporiented.athandler.AtIRledcheck;
import com.sec.factory.aporiented.athandler.AtImemtest;
import com.sec.factory.aporiented.athandler.AtIsdbtest;
import com.sec.factory.aporiented.athandler.AtKey;
import com.sec.factory.aporiented.athandler.AtKeyhold;
import com.sec.factory.aporiented.athandler.AtKeyshort;
import com.sec.factory.aporiented.athandler.AtKeyulock;
import com.sec.factory.aporiented.athandler.AtKstringb;
import com.sec.factory.aporiented.athandler.AtLedlampt;
import com.sec.factory.aporiented.athandler.AtLightest;
import com.sec.factory.aporiented.athandler.AtLockCode;
import com.sec.factory.aporiented.athandler.AtLooptest;
import com.sec.factory.aporiented.athandler.AtLooptest_dual;
import com.sec.factory.aporiented.athandler.AtLtepower;
import com.sec.factory.aporiented.athandler.AtMainmenu;
import com.sec.factory.aporiented.athandler.AtMaxpower;
import com.sec.factory.aporiented.athandler.AtMemoSize;
import com.sec.factory.aporiented.athandler.AtMicsd;
import com.sec.factory.aporiented.athandler.AtNfcmtest;
import com.sec.factory.aporiented.athandler.AtOtggtest;
import com.sec.factory.aporiented.athandler.AtPayments;
import com.sec.factory.aporiented.athandler.AtPowreset;
import com.sec.factory.aporiented.athandler.AtPreconfg;
import com.sec.factory.aporiented.athandler.AtProximit;
import com.sec.factory.aporiented.athandler.AtRamsizec;
import com.sec.factory.aporiented.athandler.AtSensorHb;
import com.sec.factory.aporiented.athandler.AtSerialno;
import com.sec.factory.aporiented.athandler.AtSms;
import com.sec.factory.aporiented.athandler.AtSpkstest;
import com.sec.factory.aporiented.athandler.AtSwdlmode;
import com.sec.factory.aporiented.athandler.AtSysscope;
import com.sec.factory.aporiented.athandler.AtSyssleep;
import com.sec.factory.aporiented.athandler.AtTdmbtest;
import com.sec.factory.aporiented.athandler.AtTemptest;
import com.sec.factory.aporiented.athandler.AtTouch;
import com.sec.factory.aporiented.athandler.AtTspptest;
import com.sec.factory.aporiented.athandler.AtTspptestStandard;
import com.sec.factory.aporiented.athandler.AtTspptest_atmel;
import com.sec.factory.aporiented.athandler.AtUartswit;
import com.sec.factory.aporiented.athandler.AtVersname;
import com.sec.factory.aporiented.athandler.AtVibrtest;
import com.sec.factory.aporiented.athandler.AtWidcount;
import com.sec.factory.aporiented.athandler.AtWifiidrw;
import com.sec.factory.aporiented.athandler.AtWifitest;
import com.sec.factory.aporiented.athandler.AtWprotect;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.CommandFilter;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.TestCase;
import java.util.HashMap;

public class AtParser
{
  private final HashMap<String, AtCommandHandler> atHandlers = new HashMap();
  private String device = SystemProperties.get("ro.product.device", "Unknown").trim().toLowerCase();
  private Context mContext;
  
  private boolean compareArgu(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString)
  {
    int i = 0;
    try
    {
      while (i < Integer.parseInt(paramString))
      {
        FtUtil.log_d("AtParser", "compareArgu", "compareArgu : argu[" + i + "]=" + paramArrayOfString1[i] + ", para[" + i + "]=" + paramArrayOfString2[i]);
        boolean bool = paramArrayOfString1[i].equals(paramArrayOfString2[i]);
        if (bool != true) {
          return false;
        }
        i++;
      }
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return false;
    }
  }
  
  private boolean isFilteredCommand(String paramString1, String paramString2)
  {
    String[] arrayOfString1 = splitArgu(paramString1);
    String[] arrayOfString2 = Support.CommandFilter.getFilteredCommands();
    if (arrayOfString2 == null) {}
    for (;;)
    {
      return false;
      int i = arrayOfString2.length;
      for (int j = 0; j < i; j++)
      {
        String str = arrayOfString2[j];
        FtUtil.log_d("AtParser", "isFilteredCommand", "---filtered CMD= " + str + "  , commandName=" + paramString2);
        String[] arrayOfString3 = str.split("/");
        if ((paramString2.equals(arrayOfString3[0])) && (compareArgu(arrayOfString1, arrayOfString3[1].split(","), arrayOfString3[2]))) {
          return true;
        }
      }
    }
  }
  
  private boolean runCmd(String paramString, ResponseWriter paramResponseWriter)
  {
    FtUtil.log_d("AtParser", "runCmd", paramString);
    if (paramString != null) {
      for (;;)
      {
        try
        {
          if (paramString.length() < 1) {
            break;
          }
          String str1;
          if ((paramString.contains("=")) && (paramString.indexOf("=") != 0))
          {
            str1 = paramString.substring(0, paramString.indexOf("=")).toUpperCase();
            FtUtil.log_d("AtParser", "process", "command : " + str1);
            if (isFilteredCommand(paramString, str1))
            {
              FtUtil.log_d("AtParser", "runCmd", paramString + "- is filtered");
              return false;
            }
          }
          else
          {
            if (paramString.contains("?"))
            {
              str1 = paramString.substring(0, paramString.indexOf("?")).toUpperCase();
              continue;
            }
            str1 = paramString.trim().substring(0, paramString.length()).toUpperCase();
            continue;
          }
          if ((str1 != null) && (this.atHandlers.containsKey(str1)))
          {
            str2 = ((AtCommandHandler)this.atHandlers.get(str1)).handleCommand(splitArgu(paramString));
            bool = true;
            if ((str2 == null) || (str2.equals("WAIT"))) {
              return bool;
            }
            paramResponseWriter.write(str2);
            return bool;
          }
        }
        catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException)
        {
          FtUtil.log_e(localStringIndexOutOfBoundsException);
          return false;
        }
        FtUtil.log_d("AtParser", "runCmd: ", paramString + " is not registered in the atHandler.");
        Intent localIntent = new Intent("com.sec.factory.RECEIVED_FROM_RIL");
        localIntent.putExtra("command", paramString);
        this.mContext.sendBroadcast(localIntent);
        FtUtil.log_d("AtParser", "runCmd: ", "Broadcast unregistered command: " + paramString);
        bool = false;
        String str2 = null;
      }
    }
    boolean bool = false;
    return bool;
  }
  
  public boolean process(String paramString, ResponseWriter paramResponseWriter)
  {
    FtUtil.log_d("AtParser", "process", paramString);
    if (paramString != null) {}
    for (;;)
    {
      int i;
      try
      {
        if (paramString.length() < 1) {
          return false;
        }
        if (paramString.contains(";"))
        {
          String[] arrayOfString = paramString.split(";");
          if (arrayOfString == null) {
            break label127;
          }
          i = 0;
          bool = false;
          if ((arrayOfString.length > i) && (arrayOfString[i] != null))
          {
            if (!runCmd(arrayOfString[i], paramResponseWriter)) {
              break label135;
            }
            FtUtil.log_d("AtParser", "process done successfully. cmdList = ", arrayOfString[i]);
            bool = true;
            break label135;
          }
        }
        else
        {
          if (!runCmd(paramString, paramResponseWriter)) {
            break label127;
          }
          FtUtil.log_d("AtParser", "process done successfully. cmd = ", paramString);
          bool = true;
        }
        return bool;
      }
      catch (StringIndexOutOfBoundsException localStringIndexOutOfBoundsException)
      {
        FtUtil.log_e(localStringIndexOutOfBoundsException);
        return false;
      }
      label127:
      boolean bool = false;
      continue;
      return false;
      label135:
      i++;
    }
  }
  
  public void registerAllHandler(Context paramContext, ResponseWriter paramResponseWriter)
  {
    FtUtil.log_d("AtParser", "registerAllHandler", "Register AT command handler");
    this.mContext = paramContext;
    this.atHandlers.put("AT+KEY", new AtKey(paramContext));
    this.atHandlers.put("AT+KEYHOLD", new AtKeyhold(paramContext));
    this.atHandlers.put("AT+TOUCH", new AtTouch(paramContext));
    this.atHandlers.put("AT+MICSD", new AtMicsd(paramContext));
    this.atHandlers.put("AT+SMS", new AtSms(paramContext));
    this.atHandlers.put("AT+POWRESET", new AtPowreset(paramContext));
    this.atHandlers.put("AT+SYSSLEEP", new AtSyssleep(paramContext));
    this.atHandlers.put("AT+FCBTTEST", new AtFcbttest(paramContext, paramResponseWriter));
    this.atHandlers.put("AT+FCMPTEST", new AtFcmptest(paramContext));
    this.atHandlers.put("AT+FCESTEST", new AtFcestest(paramContext));
    this.atHandlers.put("AT+TEMPTEST", new AtTemptest(paramContext));
    this.atHandlers.put("AT+SPKSTEST", new AtSpkstest(paramContext));
    this.atHandlers.put("AT+VIBRTEST", new AtVibrtest(paramContext));
    this.atHandlers.put("AT+IMEMTEST", new AtImemtest(paramContext));
    this.atHandlers.put("AT+MEMOSIZE", new AtMemoSize(paramContext));
    this.atHandlers.put("AT+BATTTEST", new AtBatttest(paramContext));
    this.atHandlers.put("AT+BATTCALI", new AtBattcali(paramContext));
    this.atHandlers.put("AT+MAINMENU", new AtMainmenu(paramContext));
    this.atHandlers.put("AT+ACSENSOR", new AtAcsensor(paramContext));
    this.atHandlers.put("AT+LIGHTEST", new AtLightest(paramContext));
    this.atHandlers.put("AT+BTIDTEST", new AtBtidtest(paramContext, paramResponseWriter));
    this.atHandlers.put("AT+WIFIIDRW", new AtWifiidrw(paramContext, paramResponseWriter));
    this.atHandlers.put("AT+FUELGAIC", new AtFuelgaic(paramContext));
    this.atHandlers.put("AT+KEYULOCK", new AtKeyulock(paramContext));
    this.atHandlers.put("AT+PROXIMIT", new AtProximit(paramContext));
    this.atHandlers.put("AT+AROTATEC", new AtArotatec(paramContext));
    this.atHandlers.put("AT+FSBUILDC", new AtFsbuildc(paramContext));
    this.atHandlers.put("AT+SERIALNO", new AtSerialno(paramContext));
    this.atHandlers.put("AT+KSTRINGB", new AtKstringb(paramContext));
    this.atHandlers.put("AT+WIFITEST", new AtWifitest(paramContext, paramResponseWriter));
    this.atHandlers.put("AT+EMEMTEST", new AtEmemtest(paramContext));
    this.atHandlers.put("AT+KEYSHORT", new AtKeyshort(paramContext));
    this.atHandlers.put("AT+GYROSCOP", new AtGyroscop(paramContext));
    this.atHandlers.put("AT+HWINDICK", new AtHwindick(paramContext));
    if (Support.TestCase.getEnabled("AT_GPSSTEST")) {
      this.atHandlers.put("AT+GPSSTEST", new AtGpsstest(paramContext, paramResponseWriter));
    }
    this.atHandlers.put("AT+VERSNAME", new AtVersname(paramContext));
    if (Support.Feature.getBoolean("SUPPORT_DUALMODE"))
    {
      this.atHandlers.put("AT+FCEPTEST", new AtFceptest_dual(paramContext));
      this.atHandlers.put("AT+CAMETEST", new AtCametest(paramContext, paramResponseWriter));
      this.atHandlers.put("AT+FCFMTEST", new AtFcfmtest(paramContext, paramResponseWriter));
      this.atHandlers.put("AT+GEOMAGSS", new AtGeomagss(paramContext));
      if (!Support.Feature.getBoolean("SUPPORT_DUALMODE")) {
        break label1667;
      }
      this.atHandlers.put("AT+LOOPTEST", new AtLooptest_dual(paramContext));
      label819:
      this.atHandlers.put("AT+FACTOLOG", new AtFactolog(paramContext));
      this.atHandlers.put("AT+SWDLMODE", new AtSwdlmode(paramContext));
      this.atHandlers.put("AT+FUNCTEST", new AtFunctest(paramContext));
      this.atHandlers.put("AT+OTGGTEST", new AtOtggtest(paramContext, paramResponseWriter));
      this.atHandlers.put("AT+HDMITEST", new AtHdmitest(paramContext));
      this.atHandlers.put("AT+NFCMTEST", new AtNfcmtest(paramContext, paramResponseWriter));
      this.atHandlers.put("AT+BAROMETE", new AtBaromete(paramContext, paramResponseWriter));
      if (!Support.TestCase.getEnabled("IS_TSP_STANDARD")) {
        break label1689;
      }
      this.atHandlers.put("AT+TSPPTEST", new AtTspptestStandard(paramContext));
    }
    for (;;)
    {
      this.atHandlers.put("AT+LEDLAMPT", new AtLedlampt(paramContext));
      this.atHandlers.put("AT+WPROTECT", new AtWprotect(paramContext));
      this.atHandlers.put("AT+DISPTEST", new AtDisptest(paramContext));
      this.atHandlers.put("AT+UARTSWIT", new AtUartswit(paramContext, paramResponseWriter));
      this.atHandlers.put("AT+TDMBTEST", new AtTdmbtest(paramContext, paramResponseWriter));
      this.atHandlers.put("AT+LOCKCODE", new AtLockCode(paramContext));
      if (ModuleCommon.instance(paramContext).isConnectionModeNone() == true)
      {
        this.atHandlers.put("AT+PRECONFG", new AtPreconfg(paramContext, paramResponseWriter));
        if (Support.TestCase.getEnabled("AT_CALIDATE")) {
          this.atHandlers.put("AT+CALIDATE", new AtCalidate(paramContext));
        }
      }
      this.atHandlers.put("AT+FACTORST", new AtFactorst(paramContext));
      this.atHandlers.put("AT+CORECTRL", new AtCorectrl(paramContext));
      this.atHandlers.put("AT+IRLEDCHK", new AtIRledcheck(paramContext));
      this.atHandlers.put("AT+FAILDUMP", new AtFaildump(paramContext, paramResponseWriter));
      this.atHandlers.put("AT+FAILHIST", new AtFailhist(paramContext, paramResponseWriter));
      this.atHandlers.put("AT+RAMSIZEC", new AtRamsizec(paramContext));
      if ((this.device.contains("superior")) || (this.device.equalsIgnoreCase("superior"))) {
        this.atHandlers.put("AT+MAXPOWER", new AtMaxpower(paramContext));
      }
      this.atHandlers.put("AT+LTEPOWER", new AtLtepower(paramContext, paramResponseWriter));
      this.atHandlers.put("AT+FIRMVERS", new AtFirmvers(paramContext));
      this.atHandlers.put("AT+EWRITECK", new AtEwriteck(paramContext, paramResponseWriter));
      if (Support.Feature.getBoolean("SUPPORT_NVBACKUP_CMD")) {
        this.atHandlers.put("AT+BAKUPCHK", new AtBakupchk(paramContext));
      }
      this.atHandlers.put("AT+WIDCOUNT", new AtWidcount(paramContext, paramResponseWriter));
      this.atHandlers.put("AT+SYSSCOPE", new AtSysscope(paramContext, paramResponseWriter));
      this.atHandlers.put("AT+FLCRFCAL", new AtFeliCaRfCal(paramContext, paramResponseWriter));
      if ((this.device.contains("d2dcm")) || (this.device.contains("sc-"))) {
        this.atHandlers.put("AT+ISDBTEST", new AtIsdbtest(paramContext));
      }
      this.atHandlers.put("AT+HUMITEMP", new AtHumitemp(paramContext));
      this.atHandlers.put("AT+PAYMENTS", new AtPayments(paramContext));
      this.atHandlers.put("AT+SENSORHB", new AtSensorHb(paramContext));
      this.atHandlers.put("AT+BARCODEE", new AtBarcodeE(paramContext));
      this.atHandlers.put("AT+CALLCONN", new AtCallConn(paramContext));
      this.atHandlers.put("AT+DEBUGLVC", new AtDebugLvC(paramContext));
      this.atHandlers.put("AT+ETHERNET", new AtEthernet(paramContext));
      return;
      this.atHandlers.put("AT+FCEPTEST", new AtFceptest(paramContext));
      break;
      label1667:
      this.atHandlers.put("AT+LOOPTEST", new AtLooptest(paramContext));
      break label819;
      label1689:
      if (Support.Feature.getString("TSP_MANUFACTURE").equals("ATMEL")) {
        this.atHandlers.put("AT+TSPPTEST", new AtTspptest_atmel(paramContext));
      } else {
        this.atHandlers.put("AT+TSPPTEST", new AtTspptest(paramContext));
      }
    }
  }
  
  public String[] splitArgu(String paramString)
  {
    if (paramString.contains("="))
    {
      String[] arrayOfString = paramString.substring(1 + paramString.indexOf("="), paramString.length()).split(",");
      FtUtil.log_d("AtParser", "splitArgu()", "args : " + arrayOfString.length);
      return arrayOfString;
    }
    return new String[0];
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.AtParser
 * JD-Core Version:    0.7.1
 */