package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.cporiented.ResponseWriterCPO;
import com.sec.factory.support.FtUtil;

public class AtGpsstest
  extends AtCommandHandler
{
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      String str2;
      if ("com.android.samsungtest.GPS_OK".equals(str1))
      {
        if (AtGpsstest.this.getCmdType() == 0) {
          AtGpsstest.this.writerCpo.write(3, "15", "00", null);
        }
      }
      else if ("com.android.samsungtest.GPS_RESPONSE".equals(str1))
      {
        str2 = paramAnonymousIntent.getStringExtra("RES");
        if (AtGpsstest.this.getCmdType() != 0) {
          break label122;
        }
        AtGpsstest.this.writerCpo.write(4, "15", "01", str2);
      }
      for (;;)
      {
        AtGpsstest.this.stopReceiver(AtGpsstest.this.mReceiver);
        return;
        AtGpsstest.this.writer.write(AtGpsstest.this.responseStringNoArg("0"));
        break;
        label122:
        AtGpsstest.this.writer.write(AtGpsstest.this.responseString("1", str2));
      }
    }
  };
  
  public AtGpsstest(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "GPSSTEST";
    this.CLASS_NAME = "AtGpsstest";
    this.NUM_ARGS = 4;
    this.writer = paramResponseWriter;
  }
  
  public AtGpsstest(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "GPSSTEST";
    this.CLASS_NAME = "AtGpsstest";
    this.NUM_ARGS = 4;
    this.writerCpo = paramResponseWriterCPO;
  }
  
  /* Error */
  private void saveGlonassMultiFCN(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 54	java/lang/String:trim	()Ljava/lang/String;
    //   4: invokestatic 60	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   7: istore_2
    //   8: aload_0
    //   9: getfield 29	com/sec/factory/aporiented/athandler/AtGpsstest:CLASS_NAME	Ljava/lang/String;
    //   12: ldc 61
    //   14: new 63	java/lang/StringBuilder
    //   17: dup
    //   18: invokespecial 66	java/lang/StringBuilder:<init>	()V
    //   21: ldc 68
    //   23: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   26: iload_2
    //   27: invokevirtual 75	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   30: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   33: invokestatic 84	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   36: new 86	java/io/FileOutputStream
    //   39: dup
    //   40: new 88	java/io/File
    //   43: dup
    //   44: ldc 90
    //   46: invokespecial 92	java/io/File:<init>	(Ljava/lang/String;)V
    //   49: invokespecial 95	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   52: astore_3
    //   53: new 97	java/io/PrintWriter
    //   56: dup
    //   57: aload_3
    //   58: invokespecial 100	java/io/PrintWriter:<init>	(Ljava/io/OutputStream;)V
    //   61: astore 4
    //   63: aload 4
    //   65: iload_2
    //   66: invokevirtual 104	java/io/PrintWriter:println	(I)V
    //   69: aload 4
    //   71: invokevirtual 107	java/io/PrintWriter:flush	()V
    //   74: aload 4
    //   76: invokevirtual 110	java/io/PrintWriter:close	()V
    //   79: aload_3
    //   80: invokevirtual 111	java/io/FileOutputStream:close	()V
    //   83: ldc 90
    //   85: sipush 511
    //   88: iconst_m1
    //   89: iconst_m1
    //   90: invokestatic 117	android/os/FileUtils:setPermissions	(Ljava/lang/String;III)I
    //   93: pop
    //   94: return
    //   95: astore 9
    //   97: aload_0
    //   98: getfield 29	com/sec/factory/aporiented/athandler/AtGpsstest:CLASS_NAME	Ljava/lang/String;
    //   101: ldc 61
    //   103: ldc 119
    //   105: invokestatic 84	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   108: return
    //   109: astore 8
    //   111: aload_0
    //   112: getfield 29	com/sec/factory/aporiented/athandler/AtGpsstest:CLASS_NAME	Ljava/lang/String;
    //   115: ldc 61
    //   117: ldc 121
    //   119: invokestatic 84	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   122: return
    //   123: astore 6
    //   125: goto -14 -> 111
    //   128: astore 5
    //   130: goto -33 -> 97
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	133	0	this	AtGpsstest
    //   0	133	1	paramString	String
    //   7	59	2	i	int
    //   52	28	3	localFileOutputStream	java.io.FileOutputStream
    //   61	14	4	localPrintWriter	java.io.PrintWriter
    //   128	1	5	localFileNotFoundException1	java.io.FileNotFoundException
    //   123	1	6	localIOException1	java.io.IOException
    //   109	1	8	localIOException2	java.io.IOException
    //   95	1	9	localFileNotFoundException2	java.io.FileNotFoundException
    // Exception table:
    //   from	to	target	type
    //   36	53	95	java/io/FileNotFoundException
    //   36	53	109	java/io/IOException
    //   53	94	123	java/io/IOException
    //   53	94	128	java/io/FileNotFoundException
  }
  
  protected boolean checkArgu(String[] paramArrayOfString1, String[] paramArrayOfString2, int paramInt)
  {
    FtUtil.log_i(this.CLASS_NAME, "checkArgu", "checkArgu(String argu[" + paramArrayOfString1.length + "], String argv[" + paramArrayOfString2.length + "], int checkLen(" + paramInt + ")");
    if ((paramArrayOfString1.length < paramInt) || (paramArrayOfString2.length < paramInt)) {
      return false;
    }
    for (int i = 0;; i++)
    {
      if (i >= paramInt) {
        break label96;
      }
      if (!paramArrayOfString1[i].equalsIgnoreCase(paramArrayOfString2[i])) {
        break;
      }
    }
    label96:
    return true;
  }
  
  /* Error */
  public String handleCommand(String[] paramArrayOfString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: iconst_3
    //   5: anewarray 50	java/lang/String
    //   8: dup
    //   9: iconst_0
    //   10: ldc 140
    //   12: aastore
    //   13: dup
    //   14: iconst_1
    //   15: ldc 142
    //   17: aastore
    //   18: dup
    //   19: iconst_2
    //   20: ldc 142
    //   22: aastore
    //   23: iconst_3
    //   24: invokevirtual 144	com/sec/factory/aporiented/athandler/AtGpsstest:checkArgu	([Ljava/lang/String;[Ljava/lang/String;I)Z
    //   27: ifeq +36 -> 63
    //   30: aload_0
    //   31: getfield 29	com/sec/factory/aporiented/athandler/AtGpsstest:CLASS_NAME	Ljava/lang/String;
    //   34: ldc 146
    //   36: ldc 148
    //   38: invokestatic 84	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   41: aload_0
    //   42: invokevirtual 151	com/sec/factory/aporiented/athandler/AtGpsstest:startReceiver	()V
    //   45: getstatic 155	com/sec/factory/aporiented/athandler/AtGpsstest:mModuleCommunication	Lcom/sec/factory/modules/ModuleCommunication;
    //   48: aload_0
    //   49: getfield 159	com/sec/factory/aporiented/athandler/AtGpsstest:context	Landroid/content/Context;
    //   52: iconst_1
    //   53: invokevirtual 165	com/sec/factory/modules/ModuleCommunication:gpsActivation	(Landroid/content/Context;I)V
    //   56: ldc 167
    //   58: astore_3
    //   59: aload_0
    //   60: monitorexit
    //   61: aload_3
    //   62: areturn
    //   63: aload_0
    //   64: aload_1
    //   65: iconst_3
    //   66: anewarray 50	java/lang/String
    //   69: dup
    //   70: iconst_0
    //   71: ldc 140
    //   73: aastore
    //   74: dup
    //   75: iconst_1
    //   76: ldc 142
    //   78: aastore
    //   79: dup
    //   80: iconst_2
    //   81: ldc 169
    //   83: aastore
    //   84: iconst_3
    //   85: invokevirtual 144	com/sec/factory/aporiented/athandler/AtGpsstest:checkArgu	([Ljava/lang/String;[Ljava/lang/String;I)Z
    //   88: ifeq +35 -> 123
    //   91: aload_0
    //   92: getfield 29	com/sec/factory/aporiented/athandler/AtGpsstest:CLASS_NAME	Ljava/lang/String;
    //   95: ldc 146
    //   97: ldc 171
    //   99: invokestatic 84	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   102: aload_0
    //   103: invokevirtual 151	com/sec/factory/aporiented/athandler/AtGpsstest:startReceiver	()V
    //   106: getstatic 155	com/sec/factory/aporiented/athandler/AtGpsstest:mModuleCommunication	Lcom/sec/factory/modules/ModuleCommunication;
    //   109: aload_0
    //   110: getfield 159	com/sec/factory/aporiented/athandler/AtGpsstest:context	Landroid/content/Context;
    //   113: iconst_0
    //   114: invokevirtual 165	com/sec/factory/modules/ModuleCommunication:gpsActivation	(Landroid/content/Context;I)V
    //   117: ldc 167
    //   119: astore_3
    //   120: goto -61 -> 59
    //   123: aload_0
    //   124: aload_1
    //   125: iconst_3
    //   126: anewarray 50	java/lang/String
    //   129: dup
    //   130: iconst_0
    //   131: ldc 140
    //   133: aastore
    //   134: dup
    //   135: iconst_1
    //   136: ldc 142
    //   138: aastore
    //   139: dup
    //   140: iconst_2
    //   141: ldc 173
    //   143: aastore
    //   144: iconst_3
    //   145: invokevirtual 144	com/sec/factory/aporiented/athandler/AtGpsstest:checkArgu	([Ljava/lang/String;[Ljava/lang/String;I)Z
    //   148: ifeq +61 -> 209
    //   151: aload_0
    //   152: getfield 29	com/sec/factory/aporiented/athandler/AtGpsstest:CLASS_NAME	Ljava/lang/String;
    //   155: ldc 146
    //   157: new 63	java/lang/StringBuilder
    //   160: dup
    //   161: invokespecial 66	java/lang/StringBuilder:<init>	()V
    //   164: ldc 175
    //   166: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   169: aload_1
    //   170: iconst_3
    //   171: aaload
    //   172: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   175: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   178: invokestatic 84	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   181: aload_0
    //   182: aload_1
    //   183: iconst_3
    //   184: aaload
    //   185: invokespecial 177	com/sec/factory/aporiented/athandler/AtGpsstest:saveGlonassMultiFCN	(Ljava/lang/String;)V
    //   188: aload_0
    //   189: invokevirtual 151	com/sec/factory/aporiented/athandler/AtGpsstest:startReceiver	()V
    //   192: getstatic 155	com/sec/factory/aporiented/athandler/AtGpsstest:mModuleCommunication	Lcom/sec/factory/modules/ModuleCommunication;
    //   195: aload_0
    //   196: getfield 159	com/sec/factory/aporiented/athandler/AtGpsstest:context	Landroid/content/Context;
    //   199: iconst_0
    //   200: invokevirtual 165	com/sec/factory/modules/ModuleCommunication:gpsActivation	(Landroid/content/Context;I)V
    //   203: ldc 167
    //   205: astore_3
    //   206: goto -147 -> 59
    //   209: aload_0
    //   210: aload_1
    //   211: iconst_3
    //   212: anewarray 50	java/lang/String
    //   215: dup
    //   216: iconst_0
    //   217: ldc 140
    //   219: aastore
    //   220: dup
    //   221: iconst_1
    //   222: ldc 140
    //   224: aastore
    //   225: dup
    //   226: iconst_2
    //   227: ldc 142
    //   229: aastore
    //   230: iconst_3
    //   231: invokevirtual 144	com/sec/factory/aporiented/athandler/AtGpsstest:checkArgu	([Ljava/lang/String;[Ljava/lang/String;I)Z
    //   234: ifne +59 -> 293
    //   237: aload_0
    //   238: aload_1
    //   239: iconst_3
    //   240: anewarray 50	java/lang/String
    //   243: dup
    //   244: iconst_0
    //   245: ldc 140
    //   247: aastore
    //   248: dup
    //   249: iconst_1
    //   250: ldc 140
    //   252: aastore
    //   253: dup
    //   254: iconst_2
    //   255: ldc 169
    //   257: aastore
    //   258: iconst_3
    //   259: invokevirtual 144	com/sec/factory/aporiented/athandler/AtGpsstest:checkArgu	([Ljava/lang/String;[Ljava/lang/String;I)Z
    //   262: ifne +31 -> 293
    //   265: aload_0
    //   266: aload_1
    //   267: iconst_3
    //   268: anewarray 50	java/lang/String
    //   271: dup
    //   272: iconst_0
    //   273: ldc 140
    //   275: aastore
    //   276: dup
    //   277: iconst_1
    //   278: ldc 140
    //   280: aastore
    //   281: dup
    //   282: iconst_2
    //   283: ldc 173
    //   285: aastore
    //   286: iconst_3
    //   287: invokevirtual 144	com/sec/factory/aporiented/athandler/AtGpsstest:checkArgu	([Ljava/lang/String;[Ljava/lang/String;I)Z
    //   290: ifeq +20 -> 310
    //   293: getstatic 155	com/sec/factory/aporiented/athandler/AtGpsstest:mModuleCommunication	Lcom/sec/factory/modules/ModuleCommunication;
    //   296: invokevirtual 180	com/sec/factory/modules/ModuleCommunication:gpsDeactivation	()V
    //   299: aload_0
    //   300: aload_1
    //   301: iconst_0
    //   302: aaload
    //   303: invokevirtual 184	com/sec/factory/aporiented/athandler/AtGpsstest:responseStringNoArg	(Ljava/lang/String;)Ljava/lang/String;
    //   306: astore_3
    //   307: goto -248 -> 59
    //   310: aload_0
    //   311: aload_1
    //   312: iconst_3
    //   313: anewarray 50	java/lang/String
    //   316: dup
    //   317: iconst_0
    //   318: ldc 142
    //   320: aastore
    //   321: dup
    //   322: iconst_1
    //   323: ldc 140
    //   325: aastore
    //   326: dup
    //   327: iconst_2
    //   328: ldc 142
    //   330: aastore
    //   331: iconst_3
    //   332: invokevirtual 144	com/sec/factory/aporiented/athandler/AtGpsstest:checkArgu	([Ljava/lang/String;[Ljava/lang/String;I)Z
    //   335: ifne +31 -> 366
    //   338: aload_0
    //   339: aload_1
    //   340: iconst_3
    //   341: anewarray 50	java/lang/String
    //   344: dup
    //   345: iconst_0
    //   346: ldc 142
    //   348: aastore
    //   349: dup
    //   350: iconst_1
    //   351: ldc 140
    //   353: aastore
    //   354: dup
    //   355: iconst_2
    //   356: ldc 169
    //   358: aastore
    //   359: iconst_3
    //   360: invokevirtual 144	com/sec/factory/aporiented/athandler/AtGpsstest:checkArgu	([Ljava/lang/String;[Ljava/lang/String;I)Z
    //   363: ifeq +54 -> 417
    //   366: aload_0
    //   367: iconst_2
    //   368: invokevirtual 187	com/sec/factory/aporiented/athandler/AtGpsstest:setResultType	(I)V
    //   371: getstatic 155	com/sec/factory/aporiented/athandler/AtGpsstest:mModuleCommunication	Lcom/sec/factory/modules/ModuleCommunication;
    //   374: invokevirtual 190	com/sec/factory/modules/ModuleCommunication:gpsReadCN	()Ljava/lang/String;
    //   377: astore 4
    //   379: aload 4
    //   381: ifnull +13 -> 394
    //   384: aload 4
    //   386: ldc 192
    //   388: invokevirtual 196	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   391: ifeq +13 -> 404
    //   394: aload_0
    //   395: ldc 198
    //   397: invokevirtual 201	com/sec/factory/aporiented/athandler/AtGpsstest:responseNG	(Ljava/lang/String;)Ljava/lang/String;
    //   400: astore_3
    //   401: goto -342 -> 59
    //   404: aload_0
    //   405: aload_1
    //   406: iconst_0
    //   407: aaload
    //   408: aload 4
    //   410: invokevirtual 205	com/sec/factory/aporiented/athandler/AtGpsstest:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   413: astore_3
    //   414: goto -355 -> 59
    //   417: ldc 207
    //   419: astore_3
    //   420: goto -361 -> 59
    //   423: astore_2
    //   424: aload_0
    //   425: monitorexit
    //   426: aload_2
    //   427: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	428	0	this	AtGpsstest
    //   0	428	1	paramArrayOfString	String[]
    //   423	4	2	localObject	Object
    //   58	362	3	str1	String
    //   377	32	4	str2	String
    // Exception table:
    //   from	to	target	type
    //   2	56	423	finally
    //   63	117	423	finally
    //   123	203	423	finally
    //   209	293	423	finally
    //   293	307	423	finally
    //   310	366	423	finally
    //   366	379	423	finally
    //   384	394	423	finally
    //   394	401	423	finally
    //   404	414	423	finally
  }
  
  public void startReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.android.samsungtest.GPS_OK");
    localIntentFilter.addAction("com.android.samsungtest.GPS_RESPONSE");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtGpsstest
 * JD-Core Version:    0.7.1
 */