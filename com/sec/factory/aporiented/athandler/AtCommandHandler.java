package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.modules.ModuleCommunication;
import com.sec.factory.modules.ModuleDFT;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.modules.ModulePower;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.modules.ModuleTouchScreen;
import com.sec.factory.support.FtUtil;

public abstract class AtCommandHandler
{
  public static int N_RESULT = -1;
  protected static ModuleAudio mModuleAudio = null;
  protected static ModuleCommon mModuleCommon = null;
  protected static ModuleCommunication mModuleCommunication = null;
  protected static ModuleDFT mModuleDFT = null;
  protected static ModuleDevice mModuleDevice = null;
  protected static ModulePower mModulePower = null;
  protected static ModuleSensor mModuleSensor = null;
  protected static ModuleTouchScreen mModuleTouchScreen = null;
  protected String CLASS_NAME = "AtCommandHandler";
  protected String CMD_NAME = "CMD_NAME";
  public int CMD_TYPE = 1;
  protected final String DELIMITER = ",";
  public int NUM_ARGS = 0;
  protected Context context;
  public int mResult = -1;
  protected ResponseWriter writer;
  protected ResponseWriterCPO writerCpo;
  
  public AtCommandHandler(Context paramContext)
  {
    this.context = paramContext;
    this.writer = null;
    this.writerCpo = null;
    ModuleAudio localModuleAudio;
    ModuleCommon localModuleCommon;
    label78:
    ModuleCommunication localModuleCommunication;
    label94:
    ModuleDevice localModuleDevice;
    label111:
    ModuleDFT localModuleDFT;
    label128:
    ModulePower localModulePower;
    label145:
    ModuleSensor localModuleSensor;
    if (mModuleAudio == null)
    {
      localModuleAudio = ModuleAudio.instance(paramContext);
      mModuleAudio = localModuleAudio;
      if (mModuleCommon != null) {
        break label192;
      }
      localModuleCommon = ModuleCommon.instance(paramContext);
      mModuleCommon = localModuleCommon;
      if (mModuleCommunication != null) {
        break label199;
      }
      localModuleCommunication = ModuleCommunication.instance(paramContext);
      mModuleCommunication = localModuleCommunication;
      if (mModuleDevice != null) {
        break label207;
      }
      localModuleDevice = ModuleDevice.instance(paramContext);
      mModuleDevice = localModuleDevice;
      if (mModuleDFT != null) {
        break label215;
      }
      localModuleDFT = ModuleDFT.instance(paramContext);
      mModuleDFT = localModuleDFT;
      if (mModulePower != null) {
        break label223;
      }
      localModulePower = ModulePower.instance(paramContext);
      mModulePower = localModulePower;
      if (mModuleSensor != null) {
        break label231;
      }
      localModuleSensor = ModuleSensor.instance(paramContext);
      label162:
      mModuleSensor = localModuleSensor;
      if (mModuleTouchScreen != null) {
        break label239;
      }
    }
    label192:
    label199:
    label207:
    label215:
    label223:
    label231:
    label239:
    for (ModuleTouchScreen localModuleTouchScreen = ModuleTouchScreen.instance(paramContext);; localModuleTouchScreen = mModuleTouchScreen)
    {
      mModuleTouchScreen = localModuleTouchScreen;
      return;
      localModuleAudio = mModuleAudio;
      break;
      localModuleCommon = mModuleCommon;
      break label78;
      localModuleCommunication = mModuleCommunication;
      break label94;
      localModuleDevice = mModuleDevice;
      break label111;
      localModuleDFT = mModuleDFT;
      break label128;
      localModulePower = mModulePower;
      break label145;
      localModuleSensor = mModuleSensor;
      break label162;
    }
  }
  
  protected boolean checkArgu(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    for (int i = 0; i < paramArrayOfString2.length; i++) {
      if (!paramArrayOfString1[i].equalsIgnoreCase(paramArrayOfString2[i])) {
        return false;
      }
    }
    return true;
  }
  
  public int getCmdType()
  {
    return this.CMD_TYPE;
  }
  
  public int getResultType()
  {
    return N_RESULT;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    return null;
  }
  
  /* Error */
  protected String readOneLine(String paramString)
  {
    // Byte code:
    //   0: ldc 146
    //   2: astore_2
    //   3: aconst_null
    //   4: astore_3
    //   5: new 148	java/io/BufferedReader
    //   8: dup
    //   9: new 150	java/io/FileReader
    //   12: dup
    //   13: aload_1
    //   14: invokespecial 153	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   17: sipush 8096
    //   20: invokespecial 156	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   23: astore 4
    //   25: aload 4
    //   27: ifnull +16 -> 43
    //   30: aload 4
    //   32: invokevirtual 160	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   35: invokevirtual 163	java/lang/String:trim	()Ljava/lang/String;
    //   38: astore 14
    //   40: aload 14
    //   42: astore_2
    //   43: aload 4
    //   45: ifnull +166 -> 211
    //   48: aload 4
    //   50: invokevirtual 166	java/io/BufferedReader:close	()V
    //   53: aload_2
    //   54: ifnonnull +6 -> 60
    //   57: ldc 146
    //   59: astore_2
    //   60: aload_2
    //   61: areturn
    //   62: astore 5
    //   64: aload 5
    //   66: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   69: goto -16 -> 53
    //   72: astore 6
    //   74: aload 6
    //   76: invokevirtual 170	java/io/FileNotFoundException:printStackTrace	()V
    //   79: aload_3
    //   80: ifnull -27 -> 53
    //   83: aload_3
    //   84: invokevirtual 166	java/io/BufferedReader:close	()V
    //   87: goto -34 -> 53
    //   90: astore 9
    //   92: aload 9
    //   94: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   97: goto -44 -> 53
    //   100: astore 10
    //   102: aload 10
    //   104: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   107: aload_3
    //   108: ifnull -55 -> 53
    //   111: aload_3
    //   112: invokevirtual 166	java/io/BufferedReader:close	()V
    //   115: goto -62 -> 53
    //   118: astore 11
    //   120: aload 11
    //   122: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   125: goto -72 -> 53
    //   128: astore 12
    //   130: aload 12
    //   132: invokevirtual 171	java/lang/NullPointerException:printStackTrace	()V
    //   135: aload_3
    //   136: ifnull -83 -> 53
    //   139: aload_3
    //   140: invokevirtual 166	java/io/BufferedReader:close	()V
    //   143: goto -90 -> 53
    //   146: astore 13
    //   148: aload 13
    //   150: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   153: goto -100 -> 53
    //   156: astore 7
    //   158: aload_3
    //   159: ifnull +7 -> 166
    //   162: aload_3
    //   163: invokevirtual 166	java/io/BufferedReader:close	()V
    //   166: aload 7
    //   168: athrow
    //   169: astore 8
    //   171: aload 8
    //   173: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   176: goto -10 -> 166
    //   179: astore 7
    //   181: aload 4
    //   183: astore_3
    //   184: goto -26 -> 158
    //   187: astore 12
    //   189: aload 4
    //   191: astore_3
    //   192: goto -62 -> 130
    //   195: astore 10
    //   197: aload 4
    //   199: astore_3
    //   200: goto -98 -> 102
    //   203: astore 6
    //   205: aload 4
    //   207: astore_3
    //   208: goto -134 -> 74
    //   211: goto -158 -> 53
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	214	0	this	AtCommandHandler
    //   0	214	1	paramString	String
    //   2	59	2	localObject1	Object
    //   4	204	3	localObject2	Object
    //   23	183	4	localBufferedReader	java.io.BufferedReader
    //   62	3	5	localIOException1	java.io.IOException
    //   72	3	6	localFileNotFoundException1	java.io.FileNotFoundException
    //   203	1	6	localFileNotFoundException2	java.io.FileNotFoundException
    //   156	11	7	localObject3	Object
    //   179	1	7	localObject4	Object
    //   169	3	8	localIOException2	java.io.IOException
    //   90	3	9	localIOException3	java.io.IOException
    //   100	3	10	localIOException4	java.io.IOException
    //   195	1	10	localIOException5	java.io.IOException
    //   118	3	11	localIOException6	java.io.IOException
    //   128	3	12	localNullPointerException1	NullPointerException
    //   187	1	12	localNullPointerException2	NullPointerException
    //   146	3	13	localIOException7	java.io.IOException
    //   38	3	14	str	String
    // Exception table:
    //   from	to	target	type
    //   48	53	62	java/io/IOException
    //   5	25	72	java/io/FileNotFoundException
    //   83	87	90	java/io/IOException
    //   5	25	100	java/io/IOException
    //   111	115	118	java/io/IOException
    //   5	25	128	java/lang/NullPointerException
    //   139	143	146	java/io/IOException
    //   5	25	156	finally
    //   74	79	156	finally
    //   102	107	156	finally
    //   130	135	156	finally
    //   162	166	169	java/io/IOException
    //   30	40	179	finally
    //   30	40	187	java/lang/NullPointerException
    //   30	40	195	java/io/IOException
    //   30	40	203	java/io/FileNotFoundException
  }
  
  protected String responseNA()
  {
    if (getCmdType() == 0)
    {
      setResultType(5);
      return null;
    }
    return "\r\n+CME Error:NA\r\n\r\nOK\r\n";
  }
  
  protected String responseNG(String paramString)
  {
    if (getCmdType() == 0)
    {
      setResultType(2);
      return "NG";
    }
    return "\r\n+" + this.CMD_NAME + ":" + paramString + ",NG\r\n\r\nOK" + "\r\n";
  }
  
  protected String responseOK(String paramString)
  {
    if (getCmdType() == 0)
    {
      setResultType(3);
      return "OK";
    }
    return "\r\n+" + this.CMD_NAME + ":" + paramString + ",OK\r\n\r\nOK" + "\r\n";
  }
  
  protected String responseString(String paramString1, String paramString2)
  {
    if (getCmdType() == 0)
    {
      setResultType(2);
      FtUtil.log_i(this.CLASS_NAME, "responseString", "arg: " + paramString1 + "  input: " + paramString2);
      return paramString2;
    }
    return "\r\n+" + this.CMD_NAME + ":" + paramString1 + "," + paramString2 + "\r\n\r\nOK" + "\r\n";
  }
  
  protected String responseStringCMDNG()
  {
    if (getCmdType() == 0)
    {
      setResultType(4);
      return null;
    }
    return "\r\n+CME Error:NG\r\n\r\nOK\r\n";
  }
  
  protected String responseStringNoArg(String paramString)
  {
    if (getCmdType() == 0)
    {
      setResultType(6);
      return null;
    }
    return "\r\n+" + this.CMD_NAME + ":" + paramString + "\r\n\r\nOK" + "\r\n";
  }
  
  protected String responseStringNoArgAndNoNewLine(String paramString)
  {
    if (getCmdType() == 0)
    {
      setResultType(6);
      return null;
    }
    return "\r\n+" + this.CMD_NAME + ":" + paramString + "\r\n\r\nOK";
  }
  
  public void setCmdType(int paramInt)
  {
    this.CMD_TYPE = paramInt;
  }
  
  public void setResultType(int paramInt)
  {
    N_RESULT = paramInt;
  }
  
  protected void startReceiver() {}
  
  protected void stopReceiver(BroadcastReceiver paramBroadcastReceiver)
  {
    if (paramBroadcastReceiver != null) {
      this.context.unregisterReceiver(paramBroadcastReceiver);
    }
  }
  
  /* Error */
  protected boolean writeFile(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: new 239	java/io/FileWriter
    //   5: dup
    //   6: aload_1
    //   7: invokespecial 240	java/io/FileWriter:<init>	(Ljava/lang/String;)V
    //   10: astore 4
    //   12: aload 4
    //   14: aload_2
    //   15: invokevirtual 243	java/io/FileWriter:write	(Ljava/lang/String;)V
    //   18: aload 4
    //   20: invokevirtual 246	java/io/FileWriter:flush	()V
    //   23: aload 4
    //   25: ifnull +8 -> 33
    //   28: aload 4
    //   30: invokevirtual 247	java/io/FileWriter:close	()V
    //   33: iconst_1
    //   34: istore 8
    //   36: iload 8
    //   38: ireturn
    //   39: astore 10
    //   41: aload 10
    //   43: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   46: goto -13 -> 33
    //   49: astore 5
    //   51: aload 5
    //   53: invokestatic 251	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/Exception;)V
    //   56: iconst_0
    //   57: istore 8
    //   59: aload_3
    //   60: ifnull -24 -> 36
    //   63: aload_3
    //   64: invokevirtual 247	java/io/FileWriter:close	()V
    //   67: iconst_0
    //   68: ireturn
    //   69: astore 9
    //   71: aload 9
    //   73: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   76: iconst_0
    //   77: ireturn
    //   78: astore 6
    //   80: aload_3
    //   81: ifnull +7 -> 88
    //   84: aload_3
    //   85: invokevirtual 247	java/io/FileWriter:close	()V
    //   88: aload 6
    //   90: athrow
    //   91: astore 7
    //   93: aload 7
    //   95: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   98: goto -10 -> 88
    //   101: astore 6
    //   103: aload 4
    //   105: astore_3
    //   106: goto -26 -> 80
    //   109: astore 5
    //   111: aload 4
    //   113: astore_3
    //   114: goto -63 -> 51
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	117	0	this	AtCommandHandler
    //   0	117	1	paramString1	String
    //   0	117	2	paramString2	String
    //   1	113	3	localObject1	Object
    //   10	102	4	localFileWriter	java.io.FileWriter
    //   49	3	5	localIOException1	java.io.IOException
    //   109	1	5	localIOException2	java.io.IOException
    //   78	11	6	localObject2	Object
    //   101	1	6	localObject3	Object
    //   91	3	7	localIOException3	java.io.IOException
    //   34	24	8	bool	boolean
    //   69	3	9	localIOException4	java.io.IOException
    //   39	3	10	localIOException5	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   28	33	39	java/io/IOException
    //   2	12	49	java/io/IOException
    //   63	67	69	java/io/IOException
    //   2	12	78	finally
    //   51	56	78	finally
    //   84	88	91	java/io/IOException
    //   12	23	101	finally
    //   12	23	109	java/io/IOException
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtCommandHandler
 * JD-Core Version:    0.7.1
 */