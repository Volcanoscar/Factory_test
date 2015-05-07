package com.sec.factory.aporiented.athandler;

import android.content.Context;

public class AtIRledcheck
  extends AtCommandHandler
{
  public AtIRledcheck(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "IRLEDCHK";
    this.CLASS_NAME = "AtIRledcheck";
    this.NUM_ARGS = 2;
  }
  
  /* Error */
  public String handleCommand(String[] paramArrayOfString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: arraylength
    //   4: aload_0
    //   5: getfield 23	com/sec/factory/aporiented/athandler/AtIRledcheck:NUM_ARGS	I
    //   8: if_icmpeq +10 -> 18
    //   11: ldc 29
    //   13: astore_3
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_3
    //   17: areturn
    //   18: aload_0
    //   19: aload_1
    //   20: iconst_2
    //   21: anewarray 31	java/lang/String
    //   24: dup
    //   25: iconst_0
    //   26: ldc 33
    //   28: aastore
    //   29: dup
    //   30: iconst_1
    //   31: ldc 33
    //   33: aastore
    //   34: invokevirtual 37	com/sec/factory/aporiented/athandler/AtIRledcheck:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   37: istore 4
    //   39: iload 4
    //   41: ifeq +103 -> 144
    //   44: getstatic 41	com/sec/factory/aporiented/athandler/AtIRledcheck:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   47: ldc 43
    //   49: invokevirtual 49	com/sec/factory/modules/ModuleDevice:controlIRLED	(Ljava/lang/String;)Z
    //   52: ifeq +51 -> 103
    //   55: aload_0
    //   56: aload_1
    //   57: iconst_0
    //   58: aaload
    //   59: invokevirtual 53	com/sec/factory/aporiented/athandler/AtIRledcheck:responseOK	(Ljava/lang/String;)Ljava/lang/String;
    //   62: astore 18
    //   64: aload 18
    //   66: astore 9
    //   68: aload_0
    //   69: getfield 19	com/sec/factory/aporiented/athandler/AtIRledcheck:CLASS_NAME	Ljava/lang/String;
    //   72: ldc 54
    //   74: new 56	java/lang/StringBuilder
    //   77: dup
    //   78: invokespecial 59	java/lang/StringBuilder:<init>	()V
    //   81: ldc 61
    //   83: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: aload 9
    //   88: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: invokevirtual 69	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   94: invokestatic 75	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   97: aload 9
    //   99: astore_3
    //   100: goto -86 -> 14
    //   103: aload_0
    //   104: aload_1
    //   105: iconst_0
    //   106: aaload
    //   107: invokevirtual 78	com/sec/factory/aporiented/athandler/AtIRledcheck:responseNG	(Ljava/lang/String;)Ljava/lang/String;
    //   110: astore 17
    //   112: aload 17
    //   114: astore 9
    //   116: goto -48 -> 68
    //   119: astore 16
    //   121: aload_0
    //   122: getfield 19	com/sec/factory/aporiented/athandler/AtIRledcheck:CLASS_NAME	Ljava/lang/String;
    //   125: ldc 54
    //   127: ldc 80
    //   129: invokestatic 75	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   132: aload_0
    //   133: aload_1
    //   134: iconst_0
    //   135: aaload
    //   136: invokevirtual 78	com/sec/factory/aporiented/athandler/AtIRledcheck:responseNG	(Ljava/lang/String;)Ljava/lang/String;
    //   139: astore 9
    //   141: goto -73 -> 68
    //   144: aload_0
    //   145: aload_1
    //   146: iconst_2
    //   147: anewarray 31	java/lang/String
    //   150: dup
    //   151: iconst_0
    //   152: ldc 33
    //   154: aastore
    //   155: dup
    //   156: iconst_1
    //   157: ldc 82
    //   159: aastore
    //   160: invokevirtual 37	com/sec/factory/aporiented/athandler/AtIRledcheck:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   163: istore 5
    //   165: iload 5
    //   167: ifeq +67 -> 234
    //   170: getstatic 41	com/sec/factory/aporiented/athandler/AtIRledcheck:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   173: ldc 84
    //   175: invokevirtual 49	com/sec/factory/modules/ModuleDevice:controlIRLED	(Ljava/lang/String;)Z
    //   178: ifeq +15 -> 193
    //   181: aload_0
    //   182: aload_1
    //   183: iconst_0
    //   184: aaload
    //   185: invokevirtual 53	com/sec/factory/aporiented/athandler/AtIRledcheck:responseOK	(Ljava/lang/String;)Ljava/lang/String;
    //   188: astore 9
    //   190: goto -122 -> 68
    //   193: aload_0
    //   194: aload_1
    //   195: iconst_0
    //   196: aaload
    //   197: invokevirtual 78	com/sec/factory/aporiented/athandler/AtIRledcheck:responseNG	(Ljava/lang/String;)Ljava/lang/String;
    //   200: astore 15
    //   202: aload 15
    //   204: astore 9
    //   206: goto -138 -> 68
    //   209: astore 14
    //   211: aload_0
    //   212: getfield 19	com/sec/factory/aporiented/athandler/AtIRledcheck:CLASS_NAME	Ljava/lang/String;
    //   215: ldc 54
    //   217: ldc 86
    //   219: invokestatic 75	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   222: aload_0
    //   223: aload_1
    //   224: iconst_0
    //   225: aaload
    //   226: invokevirtual 78	com/sec/factory/aporiented/athandler/AtIRledcheck:responseNG	(Ljava/lang/String;)Ljava/lang/String;
    //   229: astore 9
    //   231: goto -163 -> 68
    //   234: aload_0
    //   235: aload_1
    //   236: iconst_2
    //   237: anewarray 31	java/lang/String
    //   240: dup
    //   241: iconst_0
    //   242: ldc 33
    //   244: aastore
    //   245: dup
    //   246: iconst_1
    //   247: ldc 88
    //   249: aastore
    //   250: invokevirtual 37	com/sec/factory/aporiented/athandler/AtIRledcheck:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   253: istore 6
    //   255: iload 6
    //   257: ifeq +67 -> 324
    //   260: getstatic 41	com/sec/factory/aporiented/athandler/AtIRledcheck:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   263: ldc 90
    //   265: invokevirtual 49	com/sec/factory/modules/ModuleDevice:controlIRLED	(Ljava/lang/String;)Z
    //   268: ifeq +15 -> 283
    //   271: aload_0
    //   272: aload_1
    //   273: iconst_0
    //   274: aaload
    //   275: invokevirtual 53	com/sec/factory/aporiented/athandler/AtIRledcheck:responseOK	(Ljava/lang/String;)Ljava/lang/String;
    //   278: astore 9
    //   280: goto -212 -> 68
    //   283: aload_0
    //   284: aload_1
    //   285: iconst_0
    //   286: aaload
    //   287: invokevirtual 78	com/sec/factory/aporiented/athandler/AtIRledcheck:responseNG	(Ljava/lang/String;)Ljava/lang/String;
    //   290: astore 13
    //   292: aload 13
    //   294: astore 9
    //   296: goto -228 -> 68
    //   299: astore 12
    //   301: aload_0
    //   302: getfield 19	com/sec/factory/aporiented/athandler/AtIRledcheck:CLASS_NAME	Ljava/lang/String;
    //   305: ldc 54
    //   307: ldc 92
    //   309: invokestatic 75	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   312: aload_0
    //   313: aload_1
    //   314: iconst_0
    //   315: aaload
    //   316: invokevirtual 78	com/sec/factory/aporiented/athandler/AtIRledcheck:responseNG	(Ljava/lang/String;)Ljava/lang/String;
    //   319: astore 9
    //   321: goto -253 -> 68
    //   324: aload_0
    //   325: aload_1
    //   326: iconst_2
    //   327: anewarray 31	java/lang/String
    //   330: dup
    //   331: iconst_0
    //   332: ldc 33
    //   334: aastore
    //   335: dup
    //   336: iconst_1
    //   337: ldc 94
    //   339: aastore
    //   340: invokevirtual 37	com/sec/factory/aporiented/athandler/AtIRledcheck:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   343: istore 7
    //   345: iload 7
    //   347: ifeq +67 -> 414
    //   350: getstatic 41	com/sec/factory/aporiented/athandler/AtIRledcheck:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   353: ldc 96
    //   355: invokevirtual 49	com/sec/factory/modules/ModuleDevice:controlIRLED	(Ljava/lang/String;)Z
    //   358: ifeq +15 -> 373
    //   361: aload_0
    //   362: aload_1
    //   363: iconst_0
    //   364: aaload
    //   365: invokevirtual 53	com/sec/factory/aporiented/athandler/AtIRledcheck:responseOK	(Ljava/lang/String;)Ljava/lang/String;
    //   368: astore 9
    //   370: goto -302 -> 68
    //   373: aload_0
    //   374: aload_1
    //   375: iconst_0
    //   376: aaload
    //   377: invokevirtual 78	com/sec/factory/aporiented/athandler/AtIRledcheck:responseNG	(Ljava/lang/String;)Ljava/lang/String;
    //   380: astore 11
    //   382: aload 11
    //   384: astore 9
    //   386: goto -318 -> 68
    //   389: astore 10
    //   391: aload_0
    //   392: getfield 19	com/sec/factory/aporiented/athandler/AtIRledcheck:CLASS_NAME	Ljava/lang/String;
    //   395: ldc 54
    //   397: ldc 98
    //   399: invokestatic 75	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   402: aload_0
    //   403: aload_1
    //   404: iconst_0
    //   405: aaload
    //   406: invokevirtual 78	com/sec/factory/aporiented/athandler/AtIRledcheck:responseNG	(Ljava/lang/String;)Ljava/lang/String;
    //   409: astore 9
    //   411: goto -343 -> 68
    //   414: aload_0
    //   415: invokevirtual 101	com/sec/factory/aporiented/athandler/AtIRledcheck:responseNA	()Ljava/lang/String;
    //   418: astore 8
    //   420: aload 8
    //   422: astore 9
    //   424: goto -356 -> 68
    //   427: astore_2
    //   428: aload_0
    //   429: monitorexit
    //   430: aload_2
    //   431: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	432	0	this	AtIRledcheck
    //   0	432	1	paramArrayOfString	String[]
    //   427	4	2	localObject1	Object
    //   13	87	3	localObject2	Object
    //   37	3	4	bool1	boolean
    //   163	3	5	bool2	boolean
    //   253	3	6	bool3	boolean
    //   343	3	7	bool4	boolean
    //   418	3	8	str1	String
    //   66	357	9	localObject3	Object
    //   389	1	10	localException1	Exception
    //   380	3	11	str2	String
    //   299	1	12	localException2	Exception
    //   290	3	13	str3	String
    //   209	1	14	localException3	Exception
    //   200	3	15	str4	String
    //   119	1	16	localException4	Exception
    //   110	3	17	str5	String
    //   62	3	18	str6	String
    // Exception table:
    //   from	to	target	type
    //   44	64	119	java/lang/Exception
    //   103	112	119	java/lang/Exception
    //   170	190	209	java/lang/Exception
    //   193	202	209	java/lang/Exception
    //   260	280	299	java/lang/Exception
    //   283	292	299	java/lang/Exception
    //   350	370	389	java/lang/Exception
    //   373	382	389	java/lang/Exception
    //   2	11	427	finally
    //   18	39	427	finally
    //   44	64	427	finally
    //   68	97	427	finally
    //   103	112	427	finally
    //   121	141	427	finally
    //   144	165	427	finally
    //   170	190	427	finally
    //   193	202	427	finally
    //   211	231	427	finally
    //   234	255	427	finally
    //   260	280	427	finally
    //   283	292	427	finally
    //   301	321	427	finally
    //   324	345	427	finally
    //   350	370	427	finally
    //   373	382	427	finally
    //   391	411	427	finally
    //   414	420	427	finally
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtIRledcheck
 * JD-Core Version:    0.7.1
 */