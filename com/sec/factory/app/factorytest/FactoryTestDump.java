package com.sec.factory.app.factorytest;

import android.content.Context;
import com.sec.factory.support.FtUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Timer;

public class FactoryTestDump
{
  private byte[] buf = new byte[1024];
  private FileInputStream fin = null;
  private FileInputStream fis = null;
  private FileOutputStream fos = null;
  private FileOutputStream fout = null;
  private Context mContext;
  private String mCurrentStage = "FactoryTestMain";
  private String mDumpTime = "";
  private int mLogResult = -1;
  private String mPastStage = "FactoryTestMain";
  Timer mTimer = new Timer();
  
  public FactoryTestDump(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  /* Error */
  private boolean WriteToSDcard(String paramString1, String paramString2, String paramString3)
  {
    // Byte code:
    //   0: ldc 64
    //   2: ldc 65
    //   4: new 67	java/lang/StringBuilder
    //   7: dup
    //   8: invokespecial 68	java/lang/StringBuilder:<init>	()V
    //   11: ldc 70
    //   13: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: aload_3
    //   17: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   20: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   23: invokestatic 84	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   26: iconst_1
    //   27: istore 4
    //   29: aload_0
    //   30: new 86	java/io/FileInputStream
    //   33: dup
    //   34: aload_1
    //   35: invokespecial 89	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   38: putfield 41	com/sec/factory/app/factorytest/FactoryTestDump:fis	Ljava/io/FileInputStream;
    //   41: aload_0
    //   42: new 91	java/io/FileOutputStream
    //   45: dup
    //   46: aload_2
    //   47: invokespecial 92	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   50: putfield 43	com/sec/factory/app/factorytest/FactoryTestDump:fos	Ljava/io/FileOutputStream;
    //   53: aload_0
    //   54: getfield 41	com/sec/factory/app/factorytest/FactoryTestDump:fis	Ljava/io/FileInputStream;
    //   57: aload_0
    //   58: getfield 49	com/sec/factory/app/factorytest/FactoryTestDump:buf	[B
    //   61: invokevirtual 96	java/io/FileInputStream:read	([B)I
    //   64: istore 16
    //   66: iload 16
    //   68: iconst_m1
    //   69: if_icmple +108 -> 177
    //   72: aload_0
    //   73: getfield 43	com/sec/factory/app/factorytest/FactoryTestDump:fos	Ljava/io/FileOutputStream;
    //   76: aload_0
    //   77: getfield 49	com/sec/factory/app/factorytest/FactoryTestDump:buf	[B
    //   80: iconst_0
    //   81: iload 16
    //   83: invokevirtual 100	java/io/FileOutputStream:write	([BII)V
    //   86: goto -33 -> 53
    //   89: astore 12
    //   91: ldc 64
    //   93: ldc 65
    //   95: new 67	java/lang/StringBuilder
    //   98: dup
    //   99: invokespecial 68	java/lang/StringBuilder:<init>	()V
    //   102: ldc 102
    //   104: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: aload 12
    //   109: invokevirtual 105	java/io/FileNotFoundException:getMessage	()Ljava/lang/String;
    //   112: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   115: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   118: invokestatic 108	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   121: getstatic 114	java/lang/System:err	Ljava/io/PrintStream;
    //   124: ldc 116
    //   126: invokevirtual 121	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   129: aload_0
    //   130: getfield 43	com/sec/factory/app/factorytest/FactoryTestDump:fos	Ljava/io/FileOutputStream;
    //   133: ifnull +20 -> 153
    //   136: aload_0
    //   137: getfield 43	com/sec/factory/app/factorytest/FactoryTestDump:fos	Ljava/io/FileOutputStream;
    //   140: invokevirtual 125	java/io/FileOutputStream:getFD	()Ljava/io/FileDescriptor;
    //   143: invokevirtual 130	java/io/FileDescriptor:sync	()V
    //   146: aload_0
    //   147: getfield 43	com/sec/factory/app/factorytest/FactoryTestDump:fos	Ljava/io/FileOutputStream;
    //   150: invokevirtual 133	java/io/FileOutputStream:close	()V
    //   153: aload_0
    //   154: getfield 41	com/sec/factory/app/factorytest/FactoryTestDump:fis	Ljava/io/FileInputStream;
    //   157: astore 13
    //   159: iconst_0
    //   160: istore 4
    //   162: aload 13
    //   164: ifnull +10 -> 174
    //   167: aload_0
    //   168: getfield 41	com/sec/factory/app/factorytest/FactoryTestDump:fis	Ljava/io/FileInputStream;
    //   171: invokevirtual 134	java/io/FileInputStream:close	()V
    //   174: iload 4
    //   176: ireturn
    //   177: aload_0
    //   178: getfield 43	com/sec/factory/app/factorytest/FactoryTestDump:fos	Ljava/io/FileOutputStream;
    //   181: ifnull +20 -> 201
    //   184: aload_0
    //   185: getfield 43	com/sec/factory/app/factorytest/FactoryTestDump:fos	Ljava/io/FileOutputStream;
    //   188: invokevirtual 125	java/io/FileOutputStream:getFD	()Ljava/io/FileDescriptor;
    //   191: invokevirtual 130	java/io/FileDescriptor:sync	()V
    //   194: aload_0
    //   195: getfield 43	com/sec/factory/app/factorytest/FactoryTestDump:fos	Ljava/io/FileOutputStream;
    //   198: invokevirtual 133	java/io/FileOutputStream:close	()V
    //   201: aload_0
    //   202: getfield 41	com/sec/factory/app/factorytest/FactoryTestDump:fis	Ljava/io/FileInputStream;
    //   205: ifnull -31 -> 174
    //   208: aload_0
    //   209: getfield 41	com/sec/factory/app/factorytest/FactoryTestDump:fis	Ljava/io/FileInputStream;
    //   212: invokevirtual 134	java/io/FileInputStream:close	()V
    //   215: iload 4
    //   217: ireturn
    //   218: astore 17
    //   220: iload 4
    //   222: ireturn
    //   223: astore 8
    //   225: aload 8
    //   227: invokevirtual 137	java/lang/Exception:printStackTrace	()V
    //   230: aload_0
    //   231: getfield 43	com/sec/factory/app/factorytest/FactoryTestDump:fos	Ljava/io/FileOutputStream;
    //   234: ifnull +20 -> 254
    //   237: aload_0
    //   238: getfield 43	com/sec/factory/app/factorytest/FactoryTestDump:fos	Ljava/io/FileOutputStream;
    //   241: invokevirtual 125	java/io/FileOutputStream:getFD	()Ljava/io/FileDescriptor;
    //   244: invokevirtual 130	java/io/FileDescriptor:sync	()V
    //   247: aload_0
    //   248: getfield 43	com/sec/factory/app/factorytest/FactoryTestDump:fos	Ljava/io/FileOutputStream;
    //   251: invokevirtual 133	java/io/FileOutputStream:close	()V
    //   254: aload_0
    //   255: getfield 41	com/sec/factory/app/factorytest/FactoryTestDump:fis	Ljava/io/FileInputStream;
    //   258: astore 9
    //   260: iconst_0
    //   261: istore 4
    //   263: aload 9
    //   265: ifnull -91 -> 174
    //   268: aload_0
    //   269: getfield 41	com/sec/factory/app/factorytest/FactoryTestDump:fis	Ljava/io/FileInputStream;
    //   272: invokevirtual 134	java/io/FileInputStream:close	()V
    //   275: iconst_0
    //   276: ireturn
    //   277: astore 10
    //   279: iconst_0
    //   280: ireturn
    //   281: astore 5
    //   283: aload_0
    //   284: getfield 43	com/sec/factory/app/factorytest/FactoryTestDump:fos	Ljava/io/FileOutputStream;
    //   287: ifnull +20 -> 307
    //   290: aload_0
    //   291: getfield 43	com/sec/factory/app/factorytest/FactoryTestDump:fos	Ljava/io/FileOutputStream;
    //   294: invokevirtual 125	java/io/FileOutputStream:getFD	()Ljava/io/FileDescriptor;
    //   297: invokevirtual 130	java/io/FileDescriptor:sync	()V
    //   300: aload_0
    //   301: getfield 43	com/sec/factory/app/factorytest/FactoryTestDump:fos	Ljava/io/FileOutputStream;
    //   304: invokevirtual 133	java/io/FileOutputStream:close	()V
    //   307: aload_0
    //   308: getfield 41	com/sec/factory/app/factorytest/FactoryTestDump:fis	Ljava/io/FileInputStream;
    //   311: ifnull +10 -> 321
    //   314: aload_0
    //   315: getfield 41	com/sec/factory/app/factorytest/FactoryTestDump:fis	Ljava/io/FileInputStream;
    //   318: invokevirtual 134	java/io/FileInputStream:close	()V
    //   321: aload 5
    //   323: athrow
    //   324: astore 6
    //   326: goto -5 -> 321
    //   329: astore 7
    //   331: goto -24 -> 307
    //   334: astore 11
    //   336: goto -82 -> 254
    //   339: astore 14
    //   341: iconst_0
    //   342: ireturn
    //   343: astore 15
    //   345: goto -192 -> 153
    //   348: astore 18
    //   350: goto -149 -> 201
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	353	0	this	FactoryTestDump
    //   0	353	1	paramString1	String
    //   0	353	2	paramString2	String
    //   0	353	3	paramString3	String
    //   27	235	4	bool	boolean
    //   281	41	5	localObject	Object
    //   324	1	6	localException1	Exception
    //   329	1	7	localException2	Exception
    //   223	3	8	localException3	Exception
    //   258	6	9	localFileInputStream1	FileInputStream
    //   277	1	10	localException4	Exception
    //   334	1	11	localException5	Exception
    //   89	19	12	localFileNotFoundException	java.io.FileNotFoundException
    //   157	6	13	localFileInputStream2	FileInputStream
    //   339	1	14	localException6	Exception
    //   343	1	15	localException7	Exception
    //   64	18	16	i	int
    //   218	1	17	localException8	Exception
    //   348	1	18	localException9	Exception
    // Exception table:
    //   from	to	target	type
    //   29	53	89	java/io/FileNotFoundException
    //   53	66	89	java/io/FileNotFoundException
    //   72	86	89	java/io/FileNotFoundException
    //   208	215	218	java/lang/Exception
    //   29	53	223	java/lang/Exception
    //   53	66	223	java/lang/Exception
    //   72	86	223	java/lang/Exception
    //   268	275	277	java/lang/Exception
    //   29	53	281	finally
    //   53	66	281	finally
    //   72	86	281	finally
    //   91	129	281	finally
    //   225	230	281	finally
    //   314	321	324	java/lang/Exception
    //   290	307	329	java/lang/Exception
    //   237	254	334	java/lang/Exception
    //   167	174	339	java/lang/Exception
    //   136	153	343	java/lang/Exception
    //   184	201	348	java/lang/Exception
  }
  
  /* Error */
  private void copyDirectory(File paramFile1, File paramFile2)
  {
    // Byte code:
    //   0: ldc 64
    //   2: ldc 140
    //   4: new 67	java/lang/StringBuilder
    //   7: dup
    //   8: invokespecial 68	java/lang/StringBuilder:<init>	()V
    //   11: ldc 142
    //   13: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: aload_1
    //   17: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   20: ldc 147
    //   22: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   25: aload_2
    //   26: invokevirtual 145	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   29: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   32: invokestatic 150	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   35: aload_1
    //   36: invokevirtual 156	java/io/File:isDirectory	()Z
    //   39: ifeq +148 -> 187
    //   42: aload_2
    //   43: invokevirtual 159	java/io/File:exists	()Z
    //   46: ifne +8 -> 54
    //   49: aload_2
    //   50: invokevirtual 162	java/io/File:mkdir	()Z
    //   53: pop
    //   54: aload_1
    //   55: invokevirtual 166	java/io/File:list	()[Ljava/lang/String;
    //   58: astore 16
    //   60: aload_2
    //   61: invokevirtual 166	java/io/File:list	()[Ljava/lang/String;
    //   64: astore 17
    //   66: aload 16
    //   68: ifnull +9 -> 77
    //   71: aload 16
    //   73: arraylength
    //   74: ifgt +4 -> 78
    //   77: return
    //   78: iconst_0
    //   79: istore 18
    //   81: iload 18
    //   83: aload 16
    //   85: arraylength
    //   86: if_icmpge -9 -> 77
    //   89: aload 17
    //   91: ifnull +63 -> 154
    //   94: iload 18
    //   96: aload 17
    //   98: arraylength
    //   99: if_icmpge +55 -> 154
    //   102: aload 16
    //   104: iload 18
    //   106: aaload
    //   107: aload 17
    //   109: iload 18
    //   111: aaload
    //   112: invokevirtual 172	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   115: ifeq +39 -> 154
    //   118: ldc 64
    //   120: ldc 140
    //   122: new 67	java/lang/StringBuilder
    //   125: dup
    //   126: invokespecial 68	java/lang/StringBuilder:<init>	()V
    //   129: ldc 174
    //   131: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: aload 17
    //   136: iload 18
    //   138: aaload
    //   139: invokevirtual 74	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   142: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   145: invokestatic 150	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   148: iinc 18 1
    //   151: goto -70 -> 81
    //   154: aload_0
    //   155: new 152	java/io/File
    //   158: dup
    //   159: aload_1
    //   160: aload 16
    //   162: iload 18
    //   164: aaload
    //   165: invokespecial 177	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   168: new 152	java/io/File
    //   171: dup
    //   172: aload_2
    //   173: aload 16
    //   175: iload 18
    //   177: aaload
    //   178: invokespecial 177	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   181: invokespecial 179	com/sec/factory/app/factorytest/FactoryTestDump:copyDirectory	(Ljava/io/File;Ljava/io/File;)V
    //   184: goto -36 -> 148
    //   187: aload_0
    //   188: new 86	java/io/FileInputStream
    //   191: dup
    //   192: aload_1
    //   193: invokespecial 182	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   196: putfield 45	com/sec/factory/app/factorytest/FactoryTestDump:fin	Ljava/io/FileInputStream;
    //   199: aload_0
    //   200: new 91	java/io/FileOutputStream
    //   203: dup
    //   204: aload_2
    //   205: invokespecial 183	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   208: putfield 47	com/sec/factory/app/factorytest/FactoryTestDump:fout	Ljava/io/FileOutputStream;
    //   211: sipush 1024
    //   214: newarray 慢⁤污潬慣楴湯
    //   217: fconst_1
    //   218: aload_0
    //   219: getfield 45	com/sec/factory/app/factorytest/FactoryTestDump:fin	Ljava/io/FileInputStream;
    //   222: aload 12
    //   224: invokevirtual 96	java/io/FileInputStream:read	([B)I
    //   227: istore 13
    //   229: iload 13
    //   231: ifle +60 -> 291
    //   234: aload_0
    //   235: getfield 47	com/sec/factory/app/factorytest/FactoryTestDump:fout	Ljava/io/FileOutputStream;
    //   238: aload 12
    //   240: iconst_0
    //   241: iload 13
    //   243: invokevirtual 100	java/io/FileOutputStream:write	([BII)V
    //   246: goto -28 -> 218
    //   249: astore 9
    //   251: getstatic 114	java/lang/System:err	Ljava/io/PrintStream;
    //   254: ldc 116
    //   256: invokevirtual 121	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   259: aload_0
    //   260: getfield 45	com/sec/factory/app/factorytest/FactoryTestDump:fin	Ljava/io/FileInputStream;
    //   263: ifnull +10 -> 273
    //   266: aload_0
    //   267: getfield 45	com/sec/factory/app/factorytest/FactoryTestDump:fin	Ljava/io/FileInputStream;
    //   270: invokevirtual 134	java/io/FileInputStream:close	()V
    //   273: aload_0
    //   274: getfield 47	com/sec/factory/app/factorytest/FactoryTestDump:fout	Ljava/io/FileOutputStream;
    //   277: ifnull -200 -> 77
    //   280: aload_0
    //   281: getfield 47	com/sec/factory/app/factorytest/FactoryTestDump:fout	Ljava/io/FileOutputStream;
    //   284: invokevirtual 133	java/io/FileOutputStream:close	()V
    //   287: return
    //   288: astore 10
    //   290: return
    //   291: aload_0
    //   292: getfield 45	com/sec/factory/app/factorytest/FactoryTestDump:fin	Ljava/io/FileInputStream;
    //   295: invokevirtual 134	java/io/FileInputStream:close	()V
    //   298: aload_0
    //   299: getfield 47	com/sec/factory/app/factorytest/FactoryTestDump:fout	Ljava/io/FileOutputStream;
    //   302: invokevirtual 133	java/io/FileOutputStream:close	()V
    //   305: aload_0
    //   306: getfield 45	com/sec/factory/app/factorytest/FactoryTestDump:fin	Ljava/io/FileInputStream;
    //   309: ifnull +10 -> 319
    //   312: aload_0
    //   313: getfield 45	com/sec/factory/app/factorytest/FactoryTestDump:fin	Ljava/io/FileInputStream;
    //   316: invokevirtual 134	java/io/FileInputStream:close	()V
    //   319: aload_0
    //   320: getfield 47	com/sec/factory/app/factorytest/FactoryTestDump:fout	Ljava/io/FileOutputStream;
    //   323: ifnull -246 -> 77
    //   326: aload_0
    //   327: getfield 47	com/sec/factory/app/factorytest/FactoryTestDump:fout	Ljava/io/FileOutputStream;
    //   330: invokevirtual 133	java/io/FileOutputStream:close	()V
    //   333: return
    //   334: astore 14
    //   336: return
    //   337: astore 6
    //   339: aload 6
    //   341: invokevirtual 137	java/lang/Exception:printStackTrace	()V
    //   344: aload_0
    //   345: getfield 45	com/sec/factory/app/factorytest/FactoryTestDump:fin	Ljava/io/FileInputStream;
    //   348: ifnull +10 -> 358
    //   351: aload_0
    //   352: getfield 45	com/sec/factory/app/factorytest/FactoryTestDump:fin	Ljava/io/FileInputStream;
    //   355: invokevirtual 134	java/io/FileInputStream:close	()V
    //   358: aload_0
    //   359: getfield 47	com/sec/factory/app/factorytest/FactoryTestDump:fout	Ljava/io/FileOutputStream;
    //   362: ifnull -285 -> 77
    //   365: aload_0
    //   366: getfield 47	com/sec/factory/app/factorytest/FactoryTestDump:fout	Ljava/io/FileOutputStream;
    //   369: invokevirtual 133	java/io/FileOutputStream:close	()V
    //   372: return
    //   373: astore 7
    //   375: return
    //   376: astore_3
    //   377: aload_0
    //   378: getfield 45	com/sec/factory/app/factorytest/FactoryTestDump:fin	Ljava/io/FileInputStream;
    //   381: ifnull +10 -> 391
    //   384: aload_0
    //   385: getfield 45	com/sec/factory/app/factorytest/FactoryTestDump:fin	Ljava/io/FileInputStream;
    //   388: invokevirtual 134	java/io/FileInputStream:close	()V
    //   391: aload_0
    //   392: getfield 47	com/sec/factory/app/factorytest/FactoryTestDump:fout	Ljava/io/FileOutputStream;
    //   395: ifnull +10 -> 405
    //   398: aload_0
    //   399: getfield 47	com/sec/factory/app/factorytest/FactoryTestDump:fout	Ljava/io/FileOutputStream;
    //   402: invokevirtual 133	java/io/FileOutputStream:close	()V
    //   405: aload_3
    //   406: athrow
    //   407: astore 4
    //   409: goto -4 -> 405
    //   412: astore 5
    //   414: goto -23 -> 391
    //   417: astore 8
    //   419: goto -61 -> 358
    //   422: astore 11
    //   424: goto -151 -> 273
    //   427: astore 15
    //   429: goto -110 -> 319
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	432	0	this	FactoryTestDump
    //   0	432	1	paramFile1	File
    //   0	432	2	paramFile2	File
    //   376	30	3	localObject	Object
    //   407	1	4	localException1	Exception
    //   412	1	5	localException2	Exception
    //   337	3	6	localException3	Exception
    //   373	1	7	localException4	Exception
    //   417	1	8	localException5	Exception
    //   249	1	9	localFileNotFoundException	java.io.FileNotFoundException
    //   288	1	10	localException6	Exception
    //   422	1	11	localException7	Exception
    //   216	23	12	arrayOfByte	byte[]
    //   227	15	13	i	int
    //   334	1	14	localException8	Exception
    //   427	1	15	localException9	Exception
    //   58	116	16	arrayOfString1	String[]
    //   64	71	17	arrayOfString2	String[]
    //   79	97	18	j	int
    // Exception table:
    //   from	to	target	type
    //   187	218	249	java/io/FileNotFoundException
    //   218	229	249	java/io/FileNotFoundException
    //   234	246	249	java/io/FileNotFoundException
    //   291	305	249	java/io/FileNotFoundException
    //   280	287	288	java/lang/Exception
    //   326	333	334	java/lang/Exception
    //   187	218	337	java/lang/Exception
    //   218	229	337	java/lang/Exception
    //   234	246	337	java/lang/Exception
    //   291	305	337	java/lang/Exception
    //   365	372	373	java/lang/Exception
    //   187	218	376	finally
    //   218	229	376	finally
    //   234	246	376	finally
    //   251	259	376	finally
    //   291	305	376	finally
    //   339	344	376	finally
    //   398	405	407	java/lang/Exception
    //   384	391	412	java/lang/Exception
    //   351	358	417	java/lang/Exception
    //   266	273	422	java/lang/Exception
    //   312	319	427	java/lang/Exception
  }
  
  public int DoShellCmd(String paramString)
  {
    FtUtil.log_i("FactoryTestDump", "DoShellCmd", "DoShellCmd : " + paramString);
    String[] arrayOfString = { "/system/bin/sh", "-c", paramString };
    try
    {
      FtUtil.log_i("FactoryTestDump", "DoShellCmd", "exec command");
      Runtime.getRuntime().exec(arrayOfString).waitFor();
      FtUtil.log_i("FactoryTestDump", "DoShellCmd", "exec done");
      FtUtil.log_i("FactoryTestDump", "DoShellCmd", "DoShellCmd done");
      return 1;
    }
    catch (IOException localIOException)
    {
      FtUtil.log_e("FactoryTestDump", "DoShellCmd", "DoShellCmd - IOException");
      return -1;
    }
    catch (SecurityException localSecurityException)
    {
      FtUtil.log_e("FactoryTestDump", "DoShellCmd", "DoShellCmd - SecurityException");
      return -1;
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
    return -1;
  }
  
  public boolean doCopyDump()
  {
    File localFile1 = new File("/data/log");
    File localFile2 = new File("/data/log/err");
    File localFile3 = new File("/efs/root/ERR");
    File localFile4 = new File("/tombstones/mdm");
    File localFile5 = new File("/storage/sdcard0/log");
    File localFile6 = new File("/storage/sdcard0/log/err");
    File localFile7 = new File("/storage/sdcard0/log/cp");
    File localFile8 = new File("/data/app/bt.log");
    copyDirectory(localFile1, localFile5);
    copyDirectory(localFile4, localFile7);
    copyDirectory(localFile2, localFile6);
    if (!localFile3.getPath().contains(localFile1.getPath())) {
      copyDirectory(localFile3, localFile5);
    }
    if (localFile8.exists())
    {
      FtUtil.log_i("FactoryTestDump", "doCopyDump", "btlog.exists == true");
      WriteToSDcard("/data/app/bt.log", "/mnt/sdcard/log/bt.log", "bt.log");
    }
    return true;
  }
  
  public String getTimestamp()
  {
    Calendar localCalendar = Calendar.getInstance();
    String str1 = new DecimalFormat("00").format(1 + localCalendar.get(2));
    String str2 = new DecimalFormat("00").format(localCalendar.get(5));
    String str3 = new DecimalFormat("00").format(localCalendar.get(11));
    String str4 = new DecimalFormat("00").format(localCalendar.get(12));
    String str5 = new DecimalFormat("00").format(localCalendar.get(13));
    return localCalendar.get(1) + str1 + str2 + str3 + str4 + str5;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestDump
 * JD-Core Version:    0.7.1
 */