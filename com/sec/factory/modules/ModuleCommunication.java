package com.sec.factory.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.FileUtils;
import android.provider.Settings.Secure;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Kernel;
import java.io.File;
import java.io.IOException;

public class ModuleCommunication
  extends ModuleObject
{
  private static final String[] FM_RADIO_TARGET = { "earphone", "speaker" };
  private static ModuleCommunication mInstance;
  private BroadcastReceiver mBtReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (paramAnonymousIntent.getAction().equals("com.sec.factory.intent.ACTION_BT_SERVICE_RESPONSE"))
      {
        String str = paramAnonymousIntent.getStringExtra("result");
        FtUtil.log_i(ModuleCommunication.this.CLASS_NAME, "mBtReceiver.onReceive", "ACTION_BT_SERVICE_RESPONSE result=" + str);
        Intent localIntent = new Intent("com.sec.factory.intent.ACTION_BT_RESPONSE");
        localIntent.putExtra("result", str);
        ModuleCommunication.this.sendBroadcast(localIntent);
        ModuleCommunication.access$002(ModuleCommunication.this, false);
        if (str.equals("OFF")) {
          ModuleCommunication.this.stopService(new Intent(ModuleCommunication.this.getApplicationContext(), ModuleCommunicationService.class));
        }
      }
    }
  };
  public boolean mGpsEnabledByFactory = false;
  private BroadcastReceiver mGpsReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (paramAnonymousIntent.getAction().equals("com.sec.factory.intent.ACTION_GPS_SERVICE_RESPONSE"))
      {
        String str = paramAnonymousIntent.getStringExtra("result");
        FtUtil.log_i(ModuleCommunication.this.CLASS_NAME, "mGpsReceiver.onReceive", "ACTION_GPS_SERVICE_RESPONSE result=" + str);
        if (!str.equals("NG")) {
          break label92;
        }
        ModuleCommunication.this.sendResponse("NG");
      }
      for (;;)
      {
        ModuleCommunication.this.stopService(new Intent(ModuleCommunication.this.getApplicationContext(), ModuleCommunicationService.class));
        return;
        label92:
        ModuleCommunication.this.sendStartOK();
      }
    }
  };
  private boolean mIsRunningBtDevice = false;
  private BroadcastReceiver mWifiReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if ("android.intent.action.WIFI_DRIVER_INDICATION".equals(paramAnonymousIntent.getAction()))
      {
        FtUtil.log_i(ModuleCommunication.this.CLASS_NAME, "mWifiReceiver.onReceive", "ACTION_WIFI_DRIVER_INDICATION");
        if (paramAnonymousIntent.getStringExtra("STATUS").equals("ready")) {
          ModuleCommunication.access$102(ModuleCommunication.this, 1);
        }
      }
      else
      {
        return;
      }
      ModuleCommunication.access$102(ModuleCommunication.this, 0);
    }
  };
  private int mWlanTest;
  
  private ModuleCommunication(Context paramContext)
  {
    super(paramContext, "ModuleCommunication");
    FtUtil.log_i(this.CLASS_NAME, "ModuleCommunication", "Create ModuleCommunication");
    registerReceiver(this.mBtReceiver, new IntentFilter("com.sec.factory.intent.ACTION_BT_SERVICE_RESPONSE"));
    registerReceiver(this.mWifiReceiver, new IntentFilter("android.intent.action.WIFI_DRIVER_INDICATION"));
    registerReceiver(this.mGpsReceiver, new IntentFilter("com.sec.factory.intent.ACTION_GPS_SERVICE_RESPONSE"));
  }
  
  private String ConvertAddressColonFormat(String paramString)
  {
    String str = "";
    int i = 0;
    if (i < paramString.length())
    {
      if (i == -2 + paramString.length()) {}
      for (str = str + paramString.substring(i);; str = str + paramString.substring(i, i + 2) + ":")
      {
        i += 2;
        break;
      }
    }
    return str;
  }
  
  private void btAction(String paramString)
  {
    Intent localIntent = new Intent(getApplicationContext(), ModuleCommunicationService.class);
    localIntent.putExtra("action", paramString);
    startService(localIntent);
  }
  
  public static void deleteGpsFiles()
  {
    try
    {
      new File("/data/sv_cno.info").delete();
      new File("/data/gps_started").delete();
      new File("/data/glonass_started").delete();
      new File("/data/glonass_fcn").delete();
      return;
    }
    catch (Exception localException)
    {
      FtUtil.log_i("ModuleCommunication", "deleteGpsFiles", "Exception");
    }
  }
  
  private void gpsAction(String paramString)
  {
    Intent localIntent = new Intent(getApplicationContext(), ModuleCommunicationService.class);
    localIntent.putExtra("action", paramString);
    startService(localIntent);
  }
  
  public static ModuleCommunication instance(Context paramContext)
  {
    if (mInstance == null) {
      mInstance = new ModuleCommunication(paramContext);
    }
    return mInstance;
  }
  
  public boolean ConnectionCheckEthernet()
  {
    String str = Support.Kernel.read("ETHERNET_CONNECTION");
    boolean bool1 = false;
    if (str != null)
    {
      boolean bool2 = str.equals("1");
      bool1 = false;
      if (bool2) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public String ReadEthernetMac()
  {
    String str1 = Support.Kernel.read("ETHERNET_MAC_ADDRESS").replaceAll(":", "").toUpperCase();
    String str2 = Support.Kernel.read("ETHERNET_MAC_ADDRESS_EFS");
    if ((str1 == null) || (str1.equals("")) || (!str1.equals(str2))) {
      str1 = "NG";
    }
    return str1;
  }
  
  public boolean WriteEthernetMac(String paramString)
  {
    File localFile = new File("/efs/eth");
    if (!localFile.exists())
    {
      FtUtil.log_i(this.CLASS_NAME, "WriteEthernetMac", "Create ETHERNET_MAC_ADDRESS_EFS directory...");
      localFile.mkdir();
      localFile.setExecutable(true, true);
      localFile.setWritable(true, true);
      localFile.setReadable(true, false);
    }
    boolean bool = Support.Kernel.write("ETHERNET_MAC_ADDRESS_EFS", paramString.toUpperCase());
    if (bool) {
      bool = Support.Kernel.write("ETHERNET_MAC_ADDRESS", paramString.toUpperCase());
    }
    return bool;
  }
  
  public void bleSearchStartWithAck()
  {
    FtUtil.log_i(this.CLASS_NAME, "bleSearchStartWithAck", "Start Bluetooth BLE Search timeout=20sec");
    btAction("ACTION_BLUETOOTH_BLE_DISCOVERY_START_WITH_ACK");
  }
  
  public void bleSearchStartWithAckOn10s()
  {
    FtUtil.log_i(this.CLASS_NAME, "bleSearchStartWithAck", "Start Bluetooth BLE Search timeout=10sec");
    btAction("ACTION_BLUETOOTH_BLE_DISCOVERY_START_WITH_ACK_ON_10S");
  }
  
  public void btActivation()
  {
    FtUtil.log_i(this.CLASS_NAME, "btActivation", "Bluetooth Activation");
    this.mIsRunningBtDevice = true;
    btAction("ACTION_BLUETOOTH_ON");
  }
  
  public void btActivationWhereAtcmd()
  {
    FtUtil.log_i(this.CLASS_NAME, "btActivationWhereAtcmd", "Bluetooth Activation where Atcmd");
    this.mIsRunningBtDevice = true;
    btAction("ACTION_BLUETOOTH_ON_WHERE_ATCMD");
  }
  
  public void btAudioTestStart()
  {
    FtUtil.log_i(this.CLASS_NAME, "btAudioTestStart", "Start Bluetooth Audio Test");
    btAction("ACTION_BLUETOOTH_AUDIO_TEST_START");
  }
  
  public void btAudioTestStop()
  {
    FtUtil.log_i(this.CLASS_NAME, "btAudioTestStop", "Stop Bluetooth Audio Test");
    btAction("ACTION_BLUETOOTH_AUDIO_TEST_STOP");
  }
  
  public void btDeactivation()
  {
    FtUtil.log_i(this.CLASS_NAME, "btDeactivation", "Bluetooth Deactivation");
    this.mIsRunningBtDevice = true;
    btAction("ACTION_BLUETOOTH_OFF");
  }
  
  public void btLeRxHigh()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeRxHigh", "btLeRxHigh()");
    btAction("ACTION_BLUETOOTH_LE_RX_HIGH");
  }
  
  public void btLeRxLow()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeRxLow", "btLeRxLow()");
    btAction("ACTION_BLUETOOTH_LE_RX_LOW");
  }
  
  public void btLeRxMid()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeRxMid", "btLeRxMid()");
    btAction("ACTION_BLUETOOTH_LE_RX_MID");
  }
  
  public void btLeTestEnd()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTestEnd", "btLeTestEnd()");
    btAction("ACTION_BLUETOOTH_LE_TEST_END");
  }
  
  public void btLeTxCarrierChHigh()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxCarrierChHigh", "btLeTxCarrierChHigh()");
    btAction("ACTION_BLUETOOTH_LE_CARRIER_HIGH");
  }
  
  public void btLeTxCarrierChLow()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxCarrierChLow", "btLeTxCarrierChLow()");
    btAction("ACTION_BLUETOOTH_LE_CARRIER_LOW");
  }
  
  public void btLeTxCarrierChMid()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxCarrierChLow", "btLeTxCarrierChLow()");
    btAction("ACTION_BLUETOOTH_LE_CARRIER_MID");
  }
  
  public void btLeTxInBandChHigh()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxInBandChHigh", "btLeTxInBandChHigh()");
    btAction("ACTION_BLUETOOTH_LE_INBAND_HIGH");
  }
  
  public void btLeTxInBandChLow()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxInBandChLow", "btLeTxInBandChLow()");
    btAction("ACTION_BLUETOOTH_LE_INBAND_LOW");
  }
  
  public void btLeTxInBandChMid()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxInBandChMid", "btLeTxInBandChMid()");
    btAction("ACTION_BLUETOOTH_LE_INBAND_MID");
  }
  
  public void btLeTxModChHigh()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxModChMid", "btLeTxModChMid()");
    btAction("ACTION_BLUETOOTH_LE_MOD_HIGH");
  }
  
  public void btLeTxModChHighAA()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxModChLowAA", "btLeTxModChLowAA()");
    btAction("ACTION_BLUETOOTH_LE_MOD_HIGH_AA");
  }
  
  public void btLeTxModChLow()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxModChLow", "btLeTxModChLow()");
    btAction("ACTION_BLUETOOTH_LE_MOD_LOW");
  }
  
  public void btLeTxModChLowAA()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxModChLowAA", "btLeTxModChLowAA()");
    btAction("ACTION_BLUETOOTH_LE_MOD_LOW_AA");
  }
  
  public void btLeTxModChMid()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxModChMid", "btLeTxModChMid()");
    btAction("ACTION_BLUETOOTH_LE_MOD_MID");
  }
  
  public void btLeTxModChMidAA()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxModChLowAA", "btLeTxModChLowAA()");
    btAction("ACTION_BLUETOOTH_LE_MOD_MID_AA");
  }
  
  public void btLeTxOutputChHigh()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxOutputChHigh", "btLeTxOutputChHigh()");
    btAction("ACTION_BLUETOOTH_LE_OUTPUT_HIGH");
  }
  
  public void btLeTxOutputChLow()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxOutputChLow", "btLeTxOutputChLow()");
    btAction("ACTION_BLUETOOTH_LE_OUTPUT_LOW");
  }
  
  public void btLeTxOutputChMid()
  {
    FtUtil.log_i(this.CLASS_NAME, "btLeTxOutputChMid", "btLeTxOutputChMid()");
    btAction("ACTION_BLUETOOTH_LE_OUTPUT_MID");
  }
  
  public void btRfTestStart()
  {
    FtUtil.log_i(this.CLASS_NAME, "btAudioTestStart", "Start Bluetooth RF Test");
    Intent localIntent = new Intent();
    localIntent.setClassName("com.sec.android.app.bluetoothtest", "com.sec.android.app.bluetoothtest.BluetoothRfTest");
    localIntent.setFlags(268435456);
    startActivity(localIntent);
  }
  
  public void btRfTestStop()
  {
    FtUtil.log_i(this.CLASS_NAME, "btAudioTestStart", "Stop Bluetooth RF Test");
    sendBroadcast(new Intent("com.android.samsungtest.BluetoothRfTestOff"));
  }
  
  public void btSearchStart()
  {
    FtUtil.log_i(this.CLASS_NAME, "btSearchStart", "Start Bluetooth Search");
    btAction("ACTION_BLUETOOTH_DISCOVERY_START");
  }
  
  public void btSearchStartWithAck()
  {
    FtUtil.log_i(this.CLASS_NAME, "btSearchStartWithAck", "Start Bluetooth Search timeout=10sec");
    btAction("ACTION_BLUETOOTH_DISCOVERY_START_WITH_ACK");
  }
  
  public void btSearchStop()
  {
    FtUtil.log_i(this.CLASS_NAME, "btSearchStop", "Stop Bluetooth Search");
    btAction("ACTION_BLUETOOTH_DISCOVERY_STOP");
  }
  
  public void btSetDiscoverable()
  {
    FtUtil.log_i(this.CLASS_NAME, "btSetDiscoverable", "btSetDiscoverable()");
    btAction("ACTION_BLUETOOTH_SET_DISCOVERABLE");
  }
  
  protected void finalize()
    throws Throwable
  {
    unregisterReceiver(this.mBtReceiver);
    unregisterReceiver(this.mWifiReceiver);
    unregisterReceiver(this.mGpsReceiver);
    mInstance = null;
    super.finalize();
  }
  
  public void fmRadioOff()
  {
    Intent localIntent = new Intent("test.mode.radio.off");
    localIntent.setFlags(268435456);
    startActivity(localIntent);
  }
  
  public void fmRadioOn(String paramString, int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "fmRadioOn", "frequency: " + paramString);
    Intent localIntent = new Intent("test.mode.radio.on.freq");
    localIntent.putExtra("frequency", paramString);
    localIntent.putExtra("output", FM_RADIO_TARGET[paramInt]);
    localIntent.setFlags(268435456);
    startActivity(localIntent);
  }
  
  public void fmRadioReadFactoryRssi()
  {
    Intent localIntent = new Intent("test.mode.radio.factoryrssi");
    localIntent.setFlags(268435456);
    startActivity(localIntent);
  }
  
  public void fmRadioReadFrequencyIntensity(String paramString)
  {
    Intent localIntent = new Intent("test.mode.radio.freq");
    localIntent.putExtra("frequency", paramString);
    localIntent.setFlags(268435456);
    startActivity(localIntent);
  }
  
  public void fmRadioWriteFactoryRssi(String paramString)
  {
    Intent localIntent = new Intent("test.mode.radio.factoryrssi");
    localIntent.putExtra("signal_strength", paramString);
    localIntent.setFlags(268435456);
    startActivity(localIntent);
  }
  
  public void gpsActivation(Context paramContext, int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "[GPS debug #1]gpsActivation", "GPS Activation - mode(GPS : 1, GLONASS : 0) = " + paramInt);
    new File("/data/sv_cno.info").delete();
    new File("/data/gps_started").delete();
    new File("/data/glonass_started").delete();
    File localFile;
    if (paramInt == 1) {
      localFile = new File("/data/gps_started");
    }
    try
    {
      for (;;)
      {
        localFile.createNewFile();
        FileUtils.setPermissions(localFile.toString(), 511, -1, -1);
        if (Settings.Secure.isLocationProviderEnabled(getContentResolver(), "gps")) {
          break;
        }
        Settings.Secure.setLocationProviderEnabled(getContentResolver(), "gps", true);
        this.mGpsEnabledByFactory = true;
        gpsAction("ACTION_GPS_START_DELAY");
        return;
        localFile = null;
        if (paramInt == 0) {
          localFile = new File("/data/glonass_started");
        }
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        localIOException.printStackTrace();
      }
      this.mGpsEnabledByFactory = false;
      gpsAction("ACTION_GPS_START");
    }
  }
  
  public void gpsDeactivation()
  {
    FtUtil.log_i(this.CLASS_NAME, "gpsDeactivation", "GPS Deactivation");
    deleteGpsFiles();
    if (this.mGpsEnabledByFactory == true)
    {
      Settings.Secure.setLocationProviderEnabled(getContentResolver(), "gps", false);
      this.mGpsEnabledByFactory = false;
    }
  }
  
  public String gpsReadCN()
  {
    String str = readOneLine("/data/sv_cno.info");
    if ((str == null) || (str.equals(""))) {
      str = "NG";
    }
    FtUtil.log_i(this.CLASS_NAME, "gpsReadCN_SingleChannle", "CNO=" + str);
    return str;
  }
  
  public boolean isEnabledBtDevice()
  {
    return ModuleCommunicationService.isEnabledBtDevice();
  }
  
  public void nfcOff()
  {
    Intent localIntent = new Intent(getApplicationContext(), ModuleCommunicationService.class);
    localIntent.putExtra("action", "ACTION_NFC_OFF");
    startService(localIntent);
  }
  
  public void nfcOn()
  {
    Intent localIntent = new Intent(getApplicationContext(), ModuleCommunicationService.class);
    localIntent.putExtra("action", "ACTION_NFC_ON");
    startService(localIntent);
  }
  
  public void readBtId()
  {
    sendBroadcast(new Intent("com.sec.android.app.bluetoothtest.BT_ID_READ"));
  }
  
  /* Error */
  protected String readOneLine(String paramString)
  {
    // Byte code:
    //   0: ldc 86
    //   2: astore_2
    //   3: aconst_null
    //   4: astore_3
    //   5: new 529	java/io/BufferedReader
    //   8: dup
    //   9: new 531	java/io/FileReader
    //   12: dup
    //   13: aload_1
    //   14: invokespecial 532	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   17: sipush 8096
    //   20: invokespecial 535	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   23: astore 4
    //   25: aload 4
    //   27: ifnull +16 -> 43
    //   30: aload 4
    //   32: invokevirtual 538	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   35: invokevirtual 541	java/lang/String:trim	()Ljava/lang/String;
    //   38: astore 12
    //   40: aload 12
    //   42: astore_2
    //   43: aload 4
    //   45: ifnull +178 -> 223
    //   48: aload 4
    //   50: invokevirtual 544	java/io/BufferedReader:close	()V
    //   53: aload_2
    //   54: ifnonnull +6 -> 60
    //   57: ldc 86
    //   59: astore_2
    //   60: aload_2
    //   61: areturn
    //   62: astore 5
    //   64: aload_0
    //   65: getfield 59	com/sec/factory/modules/ModuleCommunication:CLASS_NAME	Ljava/lang/String;
    //   68: ldc_w 545
    //   71: ldc_w 547
    //   74: invokestatic 67	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   77: goto -24 -> 53
    //   80: astore 14
    //   82: aload_0
    //   83: getfield 59	com/sec/factory/modules/ModuleCommunication:CLASS_NAME	Ljava/lang/String;
    //   86: ldc_w 545
    //   89: ldc_w 549
    //   92: invokestatic 67	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   95: aload_3
    //   96: ifnull -43 -> 53
    //   99: aload_3
    //   100: invokevirtual 544	java/io/BufferedReader:close	()V
    //   103: goto -50 -> 53
    //   106: astore 9
    //   108: aload_0
    //   109: getfield 59	com/sec/factory/modules/ModuleCommunication:CLASS_NAME	Ljava/lang/String;
    //   112: ldc_w 545
    //   115: ldc_w 547
    //   118: invokestatic 67	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   121: goto -68 -> 53
    //   124: astore 13
    //   126: aload_0
    //   127: getfield 59	com/sec/factory/modules/ModuleCommunication:CLASS_NAME	Ljava/lang/String;
    //   130: ldc_w 545
    //   133: ldc_w 547
    //   136: invokestatic 67	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   139: aload_3
    //   140: ifnull -87 -> 53
    //   143: aload_3
    //   144: invokevirtual 544	java/io/BufferedReader:close	()V
    //   147: goto -94 -> 53
    //   150: astore 11
    //   152: aload_0
    //   153: getfield 59	com/sec/factory/modules/ModuleCommunication:CLASS_NAME	Ljava/lang/String;
    //   156: ldc_w 545
    //   159: ldc_w 547
    //   162: invokestatic 67	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   165: goto -112 -> 53
    //   168: astore 7
    //   170: aload_3
    //   171: ifnull +7 -> 178
    //   174: aload_3
    //   175: invokevirtual 544	java/io/BufferedReader:close	()V
    //   178: aload 7
    //   180: athrow
    //   181: astore 8
    //   183: aload_0
    //   184: getfield 59	com/sec/factory/modules/ModuleCommunication:CLASS_NAME	Ljava/lang/String;
    //   187: ldc_w 545
    //   190: ldc_w 547
    //   193: invokestatic 67	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   196: goto -18 -> 178
    //   199: astore 7
    //   201: aload 4
    //   203: astore_3
    //   204: goto -34 -> 170
    //   207: astore 10
    //   209: aload 4
    //   211: astore_3
    //   212: goto -86 -> 126
    //   215: astore 6
    //   217: aload 4
    //   219: astore_3
    //   220: goto -138 -> 82
    //   223: goto -170 -> 53
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	226	0	this	ModuleCommunication
    //   0	226	1	paramString	String
    //   2	59	2	localObject1	Object
    //   4	216	3	localObject2	Object
    //   23	195	4	localBufferedReader	java.io.BufferedReader
    //   62	1	5	localIOException1	IOException
    //   215	1	6	localFileNotFoundException1	java.io.FileNotFoundException
    //   168	11	7	localObject3	Object
    //   199	1	7	localObject4	Object
    //   181	1	8	localIOException2	IOException
    //   106	1	9	localIOException3	IOException
    //   207	1	10	localIOException4	IOException
    //   150	1	11	localIOException5	IOException
    //   38	3	12	str	String
    //   124	1	13	localIOException6	IOException
    //   80	1	14	localFileNotFoundException2	java.io.FileNotFoundException
    // Exception table:
    //   from	to	target	type
    //   48	53	62	java/io/IOException
    //   5	25	80	java/io/FileNotFoundException
    //   99	103	106	java/io/IOException
    //   5	25	124	java/io/IOException
    //   143	147	150	java/io/IOException
    //   5	25	168	finally
    //   82	95	168	finally
    //   126	139	168	finally
    //   174	178	181	java/io/IOException
    //   30	40	199	finally
    //   30	40	207	java/io/IOException
    //   30	40	215	java/io/FileNotFoundException
  }
  
  public void readWifiId()
  {
    sendBroadcast(new Intent("com.sec.android.app.wlantest.WIFI_ID_READ"));
  }
  
  protected void sendResponse(String paramString)
  {
    Intent localIntent = new Intent("com.android.samsungtest.GPS_RESPONSE");
    localIntent.putExtra("RES", paramString);
    sendBroadcast(localIntent);
  }
  
  protected void sendStartOK()
  {
    sendBroadcast(new Intent("com.android.samsungtest.GPS_OK"));
  }
  
  public void writeBtId(String paramString)
  {
    String str = ConvertAddressColonFormat(paramString);
    FtUtil.log_i(this.CLASS_NAME, "writeBtId", "colonadded=" + str);
    Intent localIntent = new Intent("com.sec.android.app.bluetoothtest.BT_ID_WRITE");
    localIntent.putExtra("MAC_DATA", str);
    sendBroadcast(localIntent);
  }
  
  public void writeWifiId(String paramString)
  {
    String str = ConvertAddressColonFormat(paramString);
    Intent localIntent = new Intent("com.sec.android.app.wlantest.WIFI_ID_WRITE");
    localIntent.putExtra("MAC_DATA", str);
    sendBroadcast(localIntent);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.ModuleCommunication
 * JD-Core Version:    0.7.1
 */