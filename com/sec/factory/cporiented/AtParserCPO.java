package com.sec.factory.cporiented;

import android.content.Context;
import android.content.Intent;
import com.sec.factory.aporiented.athandler.AtAcsensor;
import com.sec.factory.aporiented.athandler.AtArotatec;
import com.sec.factory.aporiented.athandler.AtBarcodeE;
import com.sec.factory.aporiented.athandler.AtBaromete;
import com.sec.factory.aporiented.athandler.AtBattcali;
import com.sec.factory.aporiented.athandler.AtBatttest;
import com.sec.factory.aporiented.athandler.AtBtidtest;
import com.sec.factory.aporiented.athandler.AtCallConn;
import com.sec.factory.aporiented.athandler.AtCametest;
import com.sec.factory.aporiented.athandler.AtCommandHandler;
import com.sec.factory.aporiented.athandler.AtCorectrl;
import com.sec.factory.aporiented.athandler.AtDisptest;
import com.sec.factory.aporiented.athandler.AtEmemtest;
import com.sec.factory.aporiented.athandler.AtEwriteck;
import com.sec.factory.aporiented.athandler.AtFactolog;
import com.sec.factory.aporiented.athandler.AtFactorst;
import com.sec.factory.aporiented.athandler.AtFaildump;
import com.sec.factory.aporiented.athandler.AtFailhist;
import com.sec.factory.aporiented.athandler.AtFcbttest;
import com.sec.factory.aporiented.athandler.AtFceptest;
import com.sec.factory.aporiented.athandler.AtFcestest;
import com.sec.factory.aporiented.athandler.AtFcfmtest;
import com.sec.factory.aporiented.athandler.AtFcmptest;
import com.sec.factory.aporiented.athandler.AtFirmvers;
import com.sec.factory.aporiented.athandler.AtFsbuildc;
import com.sec.factory.aporiented.athandler.AtFuelgaic;
import com.sec.factory.aporiented.athandler.AtFunctest;
import com.sec.factory.aporiented.athandler.AtGeomagss;
import com.sec.factory.aporiented.athandler.AtGpsstest;
import com.sec.factory.aporiented.athandler.AtGyroscop;
import com.sec.factory.aporiented.athandler.AtHDCPtest;
import com.sec.factory.aporiented.athandler.AtHdmitest;
import com.sec.factory.aporiented.athandler.AtHwindick;
import com.sec.factory.aporiented.athandler.AtImemtest;
import com.sec.factory.aporiented.athandler.AtKey;
import com.sec.factory.aporiented.athandler.AtKeyshort;
import com.sec.factory.aporiented.athandler.AtKeyulock;
import com.sec.factory.aporiented.athandler.AtKstringb;
import com.sec.factory.aporiented.athandler.AtLedlampt;
import com.sec.factory.aporiented.athandler.AtLightest;
import com.sec.factory.aporiented.athandler.AtLockCode;
import com.sec.factory.aporiented.athandler.AtLooptest;
import com.sec.factory.aporiented.athandler.AtLtepower;
import com.sec.factory.aporiented.athandler.AtMainmenu;
import com.sec.factory.aporiented.athandler.AtMemoSize;
import com.sec.factory.aporiented.athandler.AtMicsd;
import com.sec.factory.aporiented.athandler.AtNfcmtest;
import com.sec.factory.aporiented.athandler.AtOtggtest;
import com.sec.factory.aporiented.athandler.AtPowreset;
import com.sec.factory.aporiented.athandler.AtProximit;
import com.sec.factory.aporiented.athandler.AtRamsizec;
import com.sec.factory.aporiented.athandler.AtRtcctest;
import com.sec.factory.aporiented.athandler.AtSensorHb;
import com.sec.factory.aporiented.athandler.AtSerialno;
import com.sec.factory.aporiented.athandler.AtSimdetec;
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
import com.sec.factory.aporiented.athandler.AtUartswit;
import com.sec.factory.aporiented.athandler.AtUsbmodec;
import com.sec.factory.aporiented.athandler.AtVersname;
import com.sec.factory.aporiented.athandler.AtVibrtest;
import com.sec.factory.aporiented.athandler.AtWidcount;
import com.sec.factory.aporiented.athandler.AtWifiidrw;
import com.sec.factory.aporiented.athandler.AtWifitest;
import com.sec.factory.aporiented.athandler.AtWprotect;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.TestCase;
import java.math.BigInteger;
import java.util.HashMap;

public class AtParserCPO
{
  private final HashMap<String, AtCommandHandler> atHandlers = new HashMap();
  private Context mContext = null;
  
  private String hexStringToString(String paramString)
  {
    if (paramString.length() > 0)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      byte[] arrayOfByte = new BigInteger(paramString, 16).toByteArray();
      for (int i = 0; i < arrayOfByte.length; i++) {
        localStringBuffer.append((char)arrayOfByte[i]);
      }
      return localStringBuffer.toString();
    }
    return "0";
  }
  
  private String hexStringToStringIgnoreNull(String paramString)
  {
    if (paramString.length() > 0)
    {
      StringBuffer localStringBuffer = new StringBuffer();
      byte[] arrayOfByte = new BigInteger(paramString, 16).toByteArray();
      for (int i = 0;; i++)
      {
        if ((i >= arrayOfByte.length) || ((char)arrayOfByte[i] == 0)) {
          return localStringBuffer.toString();
        }
        localStringBuffer.append((char)arrayOfByte[i]);
      }
    }
    return "0";
  }
  
  public void process(String paramString, ResponseWriterCPO paramResponseWriterCPO, boolean paramBoolean)
  {
    FtUtil.log_d("AtParserCPO", "process cmd=", paramString);
    String str1;
    Object localObject;
    String str2;
    if (paramString.length() > 6)
    {
      String str6 = paramString.substring(2, 8);
      String str7 = paramString.substring(4, 6);
      String str8 = paramString.substring(6, 8);
      str1 = str7;
      localObject = str6;
      str2 = str8;
      if (paramBoolean)
      {
        FtUtil.log_d("AtParserCPO", "DFT", "This CMD is DFT");
        str1 = "F7" + str1;
      }
      FtUtil.log_i("AtParserCPO", "mainCMD= ", str1);
      if ((localObject == null) || ((!this.atHandlers.containsKey(str1)) && (!this.atHandlers.containsKey(str1.toUpperCase())))) {
        break label385;
      }
      if (!this.atHandlers.containsKey(str1.toUpperCase())) {
        break label468;
      }
    }
    label385:
    label468:
    for (String str3 = str1.toUpperCase();; str3 = str1)
    {
      AtCommandHandler localAtCommandHandler = (AtCommandHandler)this.atHandlers.get(str3);
      localAtCommandHandler.setCmdType(0);
      FtUtil.log_d("AtParserCPO", "process()", "SplitPoint = 6, handler.NUM_ARGS = " + localAtCommandHandler.NUM_ARGS + ", isdft = " + paramBoolean);
      String str4 = localAtCommandHandler.handleCommand(splitArgu(paramString, 6, localAtCommandHandler.NUM_ARGS, paramBoolean));
      int i = localAtCommandHandler.getResultType();
      FtUtil.log_i("AtParserCPO", "mainCMD= ", str3);
      FtUtil.log_i("AtParserCPO", "attr= ", str2);
      FtUtil.log_i("AtParserCPO", "resData= ", str4);
      FtUtil.log_i("AtParserCPO", "result= ", "" + i);
      if (!"WAIT".equalsIgnoreCase(str4)) {
        paramResponseWriterCPO.write(i, str3, str2, str4);
      }
      return;
      if ((paramBoolean) && ("02".equals(paramString.substring(4, 6))))
      {
        String str5 = paramString.substring(2);
        str1 = paramString.substring(4, 6);
        localObject = str5;
        str2 = "00";
        break;
      }
      FtUtil.log_e("AtParserCPO", "process", "Command is too short!!!");
      str1 = null;
      localObject = null;
      str2 = null;
      break;
      FtUtil.log_i("AtParserCPO", "process", "Not available CMD: " + str1);
      Intent localIntent = new Intent("com.sec.factory.RECEIVED_FROM_RIL");
      localIntent.putExtra("command", paramString);
      this.mContext.sendBroadcast(localIntent);
      FtUtil.log_d("AtParserCPO", "runCmd: ", "Broadcast unregistered command: " + paramString);
      return;
    }
  }
  
  public void registerAllHandler(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    FtUtil.log_d("AtParserCPO", "registerAllHandler", "Register IPC command handler");
    this.mContext = paramContext;
    this.atHandlers.put("F704", new AtKey(paramContext));
    this.atHandlers.put("F705", new AtTouch(paramContext));
    this.atHandlers.put("F702", new AtMicsd(paramContext));
    this.atHandlers.put("F701", new AtSms(paramContext));
    this.atHandlers.put("02", new AtVersname(paramContext));
    this.atHandlers.put("03", new AtPowreset(paramContext));
    this.atHandlers.put("04", new AtSyssleep(paramContext));
    this.atHandlers.put("06", new AtFcbttest(paramContext, paramResponseWriterCPO));
    this.atHandlers.put("07", new AtFcmptest(paramContext));
    this.atHandlers.put("08", new AtFcfmtest(paramContext, paramResponseWriterCPO));
    this.atHandlers.put("09", new AtFceptest(paramContext));
    this.atHandlers.put("0A", new AtFcestest(paramContext));
    this.atHandlers.put("0B", new AtRtcctest(paramContext));
    this.atHandlers.put("0C", new AtTemptest(paramContext));
    this.atHandlers.put("0E", new AtSpkstest(paramContext));
    this.atHandlers.put("0F", new AtDisptest(paramContext));
    this.atHandlers.put("10", new AtCametest(paramContext, paramResponseWriterCPO));
    this.atHandlers.put("11", new AtVibrtest(paramContext));
    this.atHandlers.put("13", new AtLooptest(paramContext));
    this.atHandlers.put("14", new AtSimdetec(paramContext));
    this.atHandlers.put("15", new AtGpsstest(paramContext, paramResponseWriterCPO));
    this.atHandlers.put("18", new AtWifitest(paramContext, paramResponseWriterCPO));
    this.atHandlers.put("19", new AtEmemtest(paramContext));
    this.atHandlers.put("1A", new AtImemtest(paramContext));
    this.atHandlers.put("1B", new AtBatttest(paramContext));
    this.atHandlers.put("1C", new AtSwdlmode(paramContext));
    this.atHandlers.put("1D", new AtMainmenu(paramContext));
    this.atHandlers.put("1E", new AtAcsensor(paramContext));
    this.atHandlers.put("1F", new AtLightest(paramContext));
    this.atHandlers.put("20", new AtUsbmodec(paramContext));
    this.atHandlers.put("22", new AtFirmvers(paramContext));
    this.atHandlers.put("27", new AtBtidtest(paramContext, paramResponseWriterCPO));
    this.atHandlers.put("28", new AtWifiidrw(paramContext, paramResponseWriterCPO));
    this.atHandlers.put("31", new AtFuelgaic(paramContext));
    this.atHandlers.put("32", new AtGeomagss(paramContext));
    this.atHandlers.put("33", new AtFsbuildc(paramContext));
    this.atHandlers.put("35", new AtKeyulock(paramContext));
    this.atHandlers.put("36", new AtSerialno(paramContext));
    this.atHandlers.put("38", new AtKeyshort(paramContext));
    this.atHandlers.put("3D", new AtFactorst(paramContext));
    this.atHandlers.put("40", new AtProximit(paramContext));
    this.atHandlers.put("41", new AtTdmbtest(paramContext, paramResponseWriterCPO));
    this.atHandlers.put("42", new AtBattcali(paramContext));
    this.atHandlers.put("4B", new AtHwindick(paramContext));
    this.atHandlers.put("4C", new AtGyroscop(paramContext));
    this.atHandlers.put("56", new AtHdmitest(paramContext));
    this.atHandlers.put("57", new AtArotatec(paramContext));
    this.atHandlers.put("58", new AtFunctest(paramContext));
    this.atHandlers.put("59", new AtKstringb(paramContext));
    this.atHandlers.put("5A", new AtFaildump(paramContext, paramResponseWriterCPO));
    this.atHandlers.put("5B", new AtFactolog(paramContext));
    this.atHandlers.put("5D", new AtNfcmtest(paramContext, paramResponseWriterCPO));
    this.atHandlers.put("5F", new AtLedlampt(paramContext));
    this.atHandlers.put("60", new AtHDCPtest(paramContext));
    this.atHandlers.put("61", new AtOtggtest(paramContext, paramResponseWriterCPO));
    this.atHandlers.put("70", new AtEwriteck(paramContext, paramResponseWriterCPO));
    if (Support.TestCase.getEnabled("IS_TSP_STANDARD")) {
      this.atHandlers.put("69", new AtTspptestStandard(paramContext));
    }
    for (;;)
    {
      this.atHandlers.put("6D", new AtBaromete(paramContext, paramResponseWriterCPO));
      this.atHandlers.put("73", new AtCallConn(paramContext));
      this.atHandlers.put("76", new AtWprotect(paramContext));
      this.atHandlers.put("7C", new AtCorectrl(paramContext));
      this.atHandlers.put("45", new AtLockCode(paramContext));
      this.atHandlers.put("51", new AtMemoSize(paramContext));
      this.atHandlers.put("48", new AtUartswit(paramContext, paramResponseWriterCPO));
      this.atHandlers.put("81", new AtWidcount(paramContext, paramResponseWriterCPO));
      this.atHandlers.put("84", new AtSysscope(paramContext, paramResponseWriterCPO));
      this.atHandlers.put("8A", new AtSensorHb(paramContext));
      this.atHandlers.put("8B", new AtBarcodeE(paramContext));
      this.atHandlers.put("90", new AtFailhist(paramContext, paramResponseWriterCPO));
      this.atHandlers.put("92", new AtRamsizec(paramContext));
      this.atHandlers.put("53", new AtLtepower(paramContext, paramResponseWriterCPO));
      return;
      this.atHandlers.put("69", new AtTspptest(paramContext));
    }
  }
  
  public String[] splitArgu(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = 0;
    String[] arrayOfString = new String[paramInt2];
    if ((paramString.substring(paramInt1 - 2, paramInt1).equals("15")) || (paramString.substring(paramInt1 - 2, paramInt1).equals("08")) || ((paramString.substring(paramInt1 - 2, paramInt1).equals("18")) && (paramString.substring(paramInt1).length() > paramInt2 * 2)))
    {
      FtUtil.log_i("AtParserCPO", "splitArgu", "For GPS command && FM Radio");
      String str1 = paramString.substring(paramInt1, paramString.length());
      for (;;)
      {
        if (i < paramInt2)
        {
          if (i == 3) {
            arrayOfString[i] = hexStringToString(str1.substring(6));
          }
        }
        else {
          return arrayOfString;
        }
        arrayOfString[i] = str1.substring(1 + i * 2, 2 + i * 2);
        FtUtil.log_i("AtParserCPO", "splitArgu res=", arrayOfString[i]);
        i++;
      }
    }
    if (paramString.substring(paramInt1 - 2, paramInt1).equals("69"))
    {
      String str4 = paramString.substring(paramInt1, paramString.length());
      FtUtil.log_i("AtParserCPO", "args= ", str4);
      label205:
      if (i < paramInt2)
      {
        if (i != paramInt2 - 1) {
          break label256;
        }
        arrayOfString[i] = hexStringToStringIgnoreNull(str4.substring(i * 2));
      }
      for (;;)
      {
        FtUtil.log_i("AtParserCPO", "splitArgu res=", arrayOfString[i]);
        i++;
        break label205;
        break;
        label256:
        arrayOfString[i] = str4.substring(1 + i * 2, 2 + i * 2);
      }
    }
    if (paramString.substring(paramInt1 - 2, paramInt1).equals("6D"))
    {
      String str3 = paramString.substring(paramInt1, paramString.length());
      FtUtil.log_i("AtParserCPO", "args= ", str3);
      label319:
      if (i < paramInt2)
      {
        if (i != paramInt2 - 1) {
          break label418;
        }
        if ((str3.length() == 4) || (!"0200".equals(paramString.substring(paramInt1, paramInt1 + 4)))) {
          break label393;
        }
        arrayOfString[i] = hexStringToString(str3.substring(4));
      }
      for (;;)
      {
        FtUtil.log_i("AtParserCPO", "splitArgu res=", arrayOfString[i]);
        i++;
        break label319;
        break;
        label393:
        arrayOfString[i] = str3.substring(1 + i * 2, 2 + i * 2);
        continue;
        label418:
        arrayOfString[i] = str3.substring(1 + i * 2, 2 + i * 2);
      }
    }
    if ((paramString != null) && (paramString.length() > -1 + (paramInt1 + paramInt2 * 2)))
    {
      String str2 = paramString.substring(paramInt1, paramString.length());
      FtUtil.log_i("AtParserCPO", "args= ", str2);
      label482:
      if (i < paramInt2)
      {
        if (!paramBoolean) {
          break label563;
        }
        if (2 + i * 2 < str2.length()) {
          break label540;
        }
        arrayOfString[i] = str2.substring(i * 2);
      }
      for (;;)
      {
        FtUtil.log_i("AtParserCPO", "splitArgu res=", arrayOfString[i]);
        i++;
        break label482;
        break;
        label540:
        arrayOfString[i] = str2.substring(i * 2, 2 + i * 2);
        continue;
        label563:
        if ((i == paramInt2 - 1) && (str2.substring(i * 2).length() > 2)) {
          arrayOfString[i] = hexStringToString(str2.substring(i * 2));
        } else {
          arrayOfString[i] = str2.substring(1 + i * 2, 2 + i * 2);
        }
      }
    }
    FtUtil.log_i("AtParserCPO", "splitArgu =", " no args");
    return new String[0];
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.cporiented.AtParserCPO
 * JD-Core Version:    0.7.1
 */