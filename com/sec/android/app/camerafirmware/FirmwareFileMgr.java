package com.sec.android.app.camerafirmware;

import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

public class FirmwareFileMgr
{
  protected static final String CAMERA_FIRMWARE_INFO_USER_FILE = Environment.getExternalStorageDirectory().toString() + "/";
  protected static byte[] CAM_FLAG_FIRMWARE_DUMP = { 50, 0 };
  protected static byte[] CAM_FLAG_FIRMWARE_UPDATE = { 49, 0 };
  protected String CAMERA_FIRMWARE_INFO_FILE = null;
  protected String CAMERA_FIRMWARE_TYPE_FILE = null;
  protected int FW_INFO_TOKENS_NUM = 2;
  protected String mCAMFWCalAF;
  protected String mCAMFWCalSEN;
  protected String mCamFWVer;
  protected byte[] mFWManageMode = CAM_FLAG_FIRMWARE_UPDATE;
  protected String mFWUpdateCount;
  protected String mISPVer1;
  protected String mISPVer2;
  protected String mISPVoltage;
  protected String mPhoneFWVer;
  protected boolean mValidResult = false;
  
  FirmwareFileMgr()
  {
    Log.e("FirmwareFileMgr", "FirmwareFileMgr");
    this.CAMERA_FIRMWARE_INFO_FILE = "/sys/class/camera/rear/rear_camfw";
    this.CAMERA_FIRMWARE_TYPE_FILE = "/sys/class/camera/rear/rear_camtype";
  }
  
  public String getCAMFWCalAF()
  {
    Log.i("FirmwareFileMgr", "getCAMFWCalAF() - mCAMFWCalAF[" + this.mCAMFWCalAF + "]");
    getFWInfo();
    return this.mCAMFWCalAF;
  }
  
  public String getCAMFWCalSEN()
  {
    Log.i("FirmwareFileMgr", "getCAMFWCalSEN() - mCAMFWCalSEN[" + this.mCAMFWCalSEN + "]");
    getFWInfo();
    return this.mCAMFWCalSEN;
  }
  
  public String getCamFWVer()
  {
    Log.i("FirmwareFileMgr", "getCamFWVer() - mCamFWVer[" + this.mCamFWVer + "]");
    getFWInfo();
    return this.mCamFWVer;
  }
  
  protected void getFWInfo()
  {
    if (!this.mValidResult)
    {
      Log.i("FirmwareFileMgr", "getFWInfo() - the fw infor will be updated");
      String str4;
      StringTokenizer localStringTokenizer;
      try
      {
        String str1 = this.CAMERA_FIRMWARE_INFO_FILE;
        String str2 = this.CAMERA_FIRMWARE_TYPE_FILE;
        FileReader localFileReader1 = new FileReader(str1);
        BufferedReader localBufferedReader1 = new BufferedReader(localFileReader1);
        FileReader localFileReader2 = new FileReader(str2);
        BufferedReader localBufferedReader2 = new BufferedReader(localFileReader2);
        String str3 = localBufferedReader1.readLine();
        str4 = localBufferedReader2.readLine();
        localBufferedReader1.close();
        localFileReader1.close();
        localBufferedReader2.close();
        localFileReader2.close();
        if ((str3 == null) || (str4 == null))
        {
          Log.e("FirmwareFileMgr", "getFWInfo() - can not get the FW info");
          return;
        }
        Log.e("FirmwareFileMgr", "getFWInfo() - FW info[" + str3 + "::" + str4 + "]");
        localStringTokenizer = new StringTokenizer(str3, " ", false);
        if (localStringTokenizer.countTokens() < 2)
        {
          Log.e("FirmwareFileMgr", "invalid FW Info!");
          this.mCamFWVer = "NONE";
          this.mPhoneFWVer = "NONE";
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        Log.i("FirmwareFileMgr", "file reading error");
        return;
      }
      this.mCamFWVer = localStringTokenizer.nextToken();
      this.mPhoneFWVer = localStringTokenizer.nextToken();
      this.mISPVer1 = new StringTokenizer(str4, " ", false).nextToken();
      this.mValidResult = true;
      return;
    }
    Log.e("FirmwareFileMgr", "getFWInfo() - do not need the firmware info");
  }
  
  public String getISPVer1()
  {
    Log.i("FirmwareFileMgr", "getISPVer1() - mISPVer1[" + this.mISPVer1 + "]");
    getFWInfo();
    return this.mISPVer1;
  }
  
  public String getISPVoltage()
  {
    Log.i("FirmwareFileMgr", "getISPVoltageInfo() - mISPVoltage[" + this.mISPVoltage + "]");
    getISPVoltageInfo();
    return this.mISPVoltage;
  }
  
  /* Error */
  protected void getISPVoltageInfo()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aconst_null
    //   3: astore_2
    //   4: new 111	java/io/FileReader
    //   7: dup
    //   8: ldc 176
    //   10: invokespecial 114	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   13: astore_3
    //   14: new 116	java/io/BufferedReader
    //   17: dup
    //   18: aload_3
    //   19: invokespecial 119	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   22: astore 4
    //   24: aload 4
    //   26: ifnull +12 -> 38
    //   29: aload_0
    //   30: aload 4
    //   32: invokevirtual 122	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   35: putfield 169	com/sec/android/app/camerafirmware/FirmwareFileMgr:mISPVoltage	Ljava/lang/String;
    //   38: aload_3
    //   39: ifnull +17 -> 56
    //   42: aload 4
    //   44: ifnull +12 -> 56
    //   47: aload_3
    //   48: invokevirtual 126	java/io/FileReader:close	()V
    //   51: aload 4
    //   53: invokevirtual 125	java/io/BufferedReader:close	()V
    //   56: aload_0
    //   57: getfield 169	com/sec/android/app/camerafirmware/FirmwareFileMgr:mISPVoltage	Ljava/lang/String;
    //   60: ifnonnull +30 -> 90
    //   63: ldc 72
    //   65: ldc 178
    //   67: invokestatic 78	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   70: pop
    //   71: aload_3
    //   72: ifnull +17 -> 89
    //   75: aload 4
    //   77: ifnull +12 -> 89
    //   80: aload_3
    //   81: invokevirtual 126	java/io/FileReader:close	()V
    //   84: aload 4
    //   86: invokevirtual 125	java/io/BufferedReader:close	()V
    //   89: return
    //   90: ldc 72
    //   92: new 28	java/lang/StringBuilder
    //   95: dup
    //   96: invokespecial 31	java/lang/StringBuilder:<init>	()V
    //   99: ldc 180
    //   101: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   104: aload_0
    //   105: getfield 169	com/sec/android/app/camerafirmware/FirmwareFileMgr:mISPVoltage	Ljava/lang/String;
    //   108: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: ldc 89
    //   113: invokevirtual 47	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: invokevirtual 50	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   119: invokestatic 78	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   122: pop
    //   123: aload_3
    //   124: ifnull +17 -> 141
    //   127: aload 4
    //   129: ifnull +12 -> 141
    //   132: aload_3
    //   133: invokevirtual 126	java/io/FileReader:close	()V
    //   136: aload 4
    //   138: invokevirtual 125	java/io/BufferedReader:close	()V
    //   141: return
    //   142: astore 11
    //   144: return
    //   145: astore 5
    //   147: aload 5
    //   149: invokevirtual 152	java/lang/Exception:printStackTrace	()V
    //   152: ldc 72
    //   154: ldc 154
    //   156: invokestatic 92	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   159: pop
    //   160: aload_1
    //   161: ifnull -72 -> 89
    //   164: aload_2
    //   165: ifnull -76 -> 89
    //   168: aload_1
    //   169: invokevirtual 126	java/io/FileReader:close	()V
    //   172: aload_2
    //   173: invokevirtual 125	java/io/BufferedReader:close	()V
    //   176: return
    //   177: astore 9
    //   179: return
    //   180: astore 6
    //   182: aload_1
    //   183: ifnull +15 -> 198
    //   186: aload_2
    //   187: ifnull +11 -> 198
    //   190: aload_1
    //   191: invokevirtual 126	java/io/FileReader:close	()V
    //   194: aload_2
    //   195: invokevirtual 125	java/io/BufferedReader:close	()V
    //   198: aload 6
    //   200: athrow
    //   201: astore 7
    //   203: goto -5 -> 198
    //   206: astore 6
    //   208: aload_3
    //   209: astore_1
    //   210: aconst_null
    //   211: astore_2
    //   212: goto -30 -> 182
    //   215: astore 6
    //   217: aload 4
    //   219: astore_2
    //   220: aload_3
    //   221: astore_1
    //   222: goto -40 -> 182
    //   225: astore 5
    //   227: aload_3
    //   228: astore_1
    //   229: aconst_null
    //   230: astore_2
    //   231: goto -84 -> 147
    //   234: astore 5
    //   236: aload 4
    //   238: astore_2
    //   239: aload_3
    //   240: astore_1
    //   241: goto -94 -> 147
    //   244: astore 13
    //   246: goto -157 -> 89
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	249	0	this	FirmwareFileMgr
    //   1	240	1	localObject1	Object
    //   3	236	2	localObject2	Object
    //   13	227	3	localFileReader	FileReader
    //   22	215	4	localBufferedReader	BufferedReader
    //   145	3	5	localException1	Exception
    //   225	1	5	localException2	Exception
    //   234	1	5	localException3	Exception
    //   180	19	6	localObject3	Object
    //   206	1	6	localObject4	Object
    //   215	1	6	localObject5	Object
    //   201	1	7	localIOException1	java.io.IOException
    //   177	1	9	localIOException2	java.io.IOException
    //   142	1	11	localIOException3	java.io.IOException
    //   244	1	13	localIOException4	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   132	141	142	java/io/IOException
    //   4	14	145	java/lang/Exception
    //   168	176	177	java/io/IOException
    //   4	14	180	finally
    //   147	160	180	finally
    //   190	198	201	java/io/IOException
    //   14	24	206	finally
    //   29	38	215	finally
    //   47	56	215	finally
    //   56	71	215	finally
    //   90	123	215	finally
    //   14	24	225	java/lang/Exception
    //   29	38	234	java/lang/Exception
    //   47	56	234	java/lang/Exception
    //   56	71	234	java/lang/Exception
    //   90	123	234	java/lang/Exception
    //   80	89	244	java/io/IOException
  }
  
  public byte[] getManageMode()
  {
    return this.mFWManageMode;
  }
  
  public String getPhoneFWVer()
  {
    Log.i("FirmwareFileMgr", "getPhoneFWVer() - mPhoneFWVer[" + this.mPhoneFWVer + "]");
    getFWInfo();
    return this.mPhoneFWVer;
  }
  
  public void resetFWInfo()
  {
    Log.e("FirmwareFileMgr", "resetFWInfo() called!");
    this.mFWManageMode = CAM_FLAG_FIRMWARE_UPDATE;
    this.mValidResult = false;
    this.mCamFWVer = null;
    this.mPhoneFWVer = null;
    this.mISPVer1 = null;
    this.mISPVer2 = null;
    this.mFWUpdateCount = null;
    this.mCAMFWCalAF = null;
    this.mCAMFWCalSEN = null;
  }
  
  public void setManageMode(byte[] paramArrayOfByte)
  {
    this.mFWManageMode = paramArrayOfByte;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.camerafirmware.FirmwareFileMgr
 * JD-Core Version:    0.7.1
 */