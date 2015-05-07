package com.sec.factory.aporiented.athandler;

import android.content.Context;

public class AtSensorHb
  extends AtCommandHandler
{
  public AtSensorHb(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "SENSORHB";
    this.CLASS_NAME = "AtSensorHb";
    this.NUM_ARGS = 3;
  }
  
  /* Error */
  public String handleCommand(String[] paramArrayOfString)
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_2
    //   2: aload_0
    //   3: monitorenter
    //   4: aload_1
    //   5: arraylength
    //   6: aload_0
    //   7: getfield 23	com/sec/factory/aporiented/athandler/AtSensorHb:NUM_ARGS	I
    //   10: if_icmpeq +12 -> 22
    //   13: ldc 31
    //   15: astore 4
    //   17: aload_0
    //   18: monitorexit
    //   19: aload 4
    //   21: areturn
    //   22: aload_0
    //   23: aload_1
    //   24: iconst_3
    //   25: anewarray 33	java/lang/String
    //   28: dup
    //   29: iconst_0
    //   30: ldc 35
    //   32: aastore
    //   33: dup
    //   34: iconst_1
    //   35: ldc 35
    //   37: aastore
    //   38: dup
    //   39: iconst_2
    //   40: ldc 35
    //   42: aastore
    //   43: invokevirtual 39	com/sec/factory/aporiented/athandler/AtSensorHb:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   46: ifeq +637 -> 683
    //   49: aload_0
    //   50: getfield 19	com/sec/factory/aporiented/athandler/AtSensorHb:CLASS_NAME	Ljava/lang/String;
    //   53: ldc 40
    //   55: ldc 42
    //   57: invokestatic 48	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   60: getstatic 52	com/sec/factory/aporiented/athandler/AtSensorHb:mModulePower	Lcom/sec/factory/modules/ModulePower;
    //   63: invokevirtual 58	com/sec/factory/modules/ModulePower:isHeldWakeLock	()Z
    //   66: istore 5
    //   68: aload_0
    //   69: getfield 19	com/sec/factory/aporiented/athandler/AtSensorHb:CLASS_NAME	Ljava/lang/String;
    //   72: ldc 40
    //   74: new 60	java/lang/StringBuilder
    //   77: dup
    //   78: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   81: ldc 65
    //   83: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   86: iload 5
    //   88: invokevirtual 72	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   91: invokevirtual 76	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   94: invokestatic 48	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   97: getstatic 80	com/sec/factory/aporiented/athandler/AtSensorHb:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   100: invokevirtual 85	com/sec/factory/modules/ModuleCommon:isFactorySim	()Z
    //   103: ifne +12 -> 115
    //   106: getstatic 80	com/sec/factory/aporiented/athandler/AtSensorHb:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   109: invokevirtual 88	com/sec/factory/modules/ModuleCommon:isFactoryMode	()Z
    //   112: ifeq +583 -> 695
    //   115: aload_0
    //   116: getfield 19	com/sec/factory/aporiented/athandler/AtSensorHb:CLASS_NAME	Ljava/lang/String;
    //   119: ldc 40
    //   121: new 60	java/lang/StringBuilder
    //   124: dup
    //   125: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   128: ldc 90
    //   130: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   133: iload_2
    //   134: invokevirtual 72	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   137: invokevirtual 76	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   140: invokestatic 48	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   143: iload_2
    //   144: ifeq +34 -> 178
    //   147: new 92	android/content/Intent
    //   150: dup
    //   151: ldc 94
    //   153: invokespecial 97	android/content/Intent:<init>	(Ljava/lang/String;)V
    //   156: astore 6
    //   158: aload_0
    //   159: getfield 101	com/sec/factory/aporiented/athandler/AtSensorHb:context	Landroid/content/Context;
    //   162: aload 6
    //   164: invokevirtual 107	android/content/Context:sendBroadcast	(Landroid/content/Intent;)V
    //   167: aload_0
    //   168: getfield 19	com/sec/factory/aporiented/athandler/AtSensorHb:CLASS_NAME	Ljava/lang/String;
    //   171: ldc 40
    //   173: ldc 109
    //   175: invokestatic 48	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   178: getstatic 52	com/sec/factory/aporiented/athandler/AtSensorHb:mModulePower	Lcom/sec/factory/modules/ModulePower;
    //   181: iconst_1
    //   182: invokevirtual 113	com/sec/factory/modules/ModulePower:doPartialWakeLock	(Z)V
    //   185: getstatic 52	com/sec/factory/aporiented/athandler/AtSensorHb:mModulePower	Lcom/sec/factory/modules/ModulePower;
    //   188: iconst_0
    //   189: invokevirtual 116	com/sec/factory/modules/ModulePower:doWakeLock	(Z)V
    //   192: ldc 118
    //   194: invokestatic 124	com/sec/factory/support/Support$Feature:getBoolean	(Ljava/lang/String;)Z
    //   197: ifeq +11 -> 208
    //   200: getstatic 128	com/sec/factory/aporiented/athandler/AtSensorHb:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   203: ldc 35
    //   205: invokevirtual 133	com/sec/factory/modules/ModuleDevice:setLPAmode	(Ljava/lang/String;)V
    //   208: getstatic 128	com/sec/factory/aporiented/athandler/AtSensorHb:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   211: invokevirtual 136	com/sec/factory/modules/ModuleDevice:startSensorHubTest	()V
    //   214: new 92	android/content/Intent
    //   217: dup
    //   218: aload_0
    //   219: getfield 101	com/sec/factory/aporiented/athandler/AtSensorHb:context	Landroid/content/Context;
    //   222: ldc 138
    //   224: invokespecial 141	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
    //   227: astore 7
    //   229: aload 7
    //   231: ldc 143
    //   233: invokevirtual 147	android/content/Intent:setAction	(Ljava/lang/String;)Landroid/content/Intent;
    //   236: pop
    //   237: aload 7
    //   239: ldc 149
    //   241: iload 5
    //   243: invokevirtual 153	android/content/Intent:putExtra	(Ljava/lang/String;Z)Landroid/content/Intent;
    //   246: pop
    //   247: aload_0
    //   248: getfield 101	com/sec/factory/aporiented/athandler/AtSensorHb:context	Landroid/content/Context;
    //   251: iconst_0
    //   252: aload 7
    //   254: iconst_0
    //   255: invokestatic 159	android/app/PendingIntent:getBroadcast	(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;
    //   258: astore 10
    //   260: aload_0
    //   261: getfield 101	com/sec/factory/aporiented/athandler/AtSensorHb:context	Landroid/content/Context;
    //   264: ldc 161
    //   266: invokevirtual 165	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   269: checkcast 167	android/app/AlarmManager
    //   272: iconst_0
    //   273: ldc2_w 168
    //   276: invokestatic 175	java/lang/System:currentTimeMillis	()J
    //   279: ladd
    //   280: aload 10
    //   282: invokevirtual 179	android/app/AlarmManager:set	(IJLandroid/app/PendingIntent;)V
    //   285: aload_0
    //   286: getfield 101	com/sec/factory/aporiented/athandler/AtSensorHb:context	Landroid/content/Context;
    //   289: ldc 181
    //   291: invokevirtual 165	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   294: checkcast 183	android/os/PowerManager
    //   297: invokestatic 188	android/os/SystemClock:uptimeMillis	()J
    //   300: invokevirtual 192	android/os/PowerManager:goToSleep	(J)V
    //   303: ldc2_w 168
    //   306: invokestatic 197	java/lang/Thread:sleep	(J)V
    //   309: ldc 118
    //   311: invokestatic 124	com/sec/factory/support/Support$Feature:getBoolean	(Ljava/lang/String;)Z
    //   314: ifeq +11 -> 325
    //   317: getstatic 128	com/sec/factory/aporiented/athandler/AtSensorHb:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   320: ldc 199
    //   322: invokevirtual 133	com/sec/factory/modules/ModuleDevice:setLPAmode	(Ljava/lang/String;)V
    //   325: iload_2
    //   326: ifeq +34 -> 360
    //   329: new 92	android/content/Intent
    //   332: dup
    //   333: ldc 201
    //   335: invokespecial 97	android/content/Intent:<init>	(Ljava/lang/String;)V
    //   338: astore 12
    //   340: aload_0
    //   341: getfield 101	com/sec/factory/aporiented/athandler/AtSensorHb:context	Landroid/content/Context;
    //   344: aload 12
    //   346: invokevirtual 107	android/content/Context:sendBroadcast	(Landroid/content/Intent;)V
    //   349: aload_0
    //   350: getfield 19	com/sec/factory/aporiented/athandler/AtSensorHb:CLASS_NAME	Ljava/lang/String;
    //   353: ldc 40
    //   355: ldc 203
    //   357: invokestatic 48	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   360: getstatic 128	com/sec/factory/aporiented/athandler/AtSensorHb:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   363: invokevirtual 206	com/sec/factory/modules/ModuleDevice:readSensorHubResult	()Ljava/lang/String;
    //   366: ldc 208
    //   368: invokevirtual 212	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   371: astore 15
    //   373: aload 15
    //   375: astore 14
    //   377: aload_0
    //   378: getfield 19	com/sec/factory/aporiented/athandler/AtSensorHb:CLASS_NAME	Ljava/lang/String;
    //   381: ldc 40
    //   383: new 60	java/lang/StringBuilder
    //   386: dup
    //   387: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   390: ldc 214
    //   392: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   395: aload 14
    //   397: iconst_0
    //   398: aaload
    //   399: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   402: ldc 216
    //   404: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   407: aload 14
    //   409: iconst_1
    //   410: aaload
    //   411: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   414: ldc 218
    //   416: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   419: aload 14
    //   421: iconst_2
    //   422: aaload
    //   423: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   426: invokevirtual 76	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   429: invokestatic 48	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   432: ldc 220
    //   434: aload 14
    //   436: iconst_1
    //   437: aaload
    //   438: invokevirtual 224	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   441: ifeq +88 -> 529
    //   444: ldc 220
    //   446: aload 14
    //   448: iconst_2
    //   449: aaload
    //   450: invokevirtual 224	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   453: ifeq +76 -> 529
    //   456: aload_0
    //   457: aload_1
    //   458: iconst_0
    //   459: aaload
    //   460: new 60	java/lang/StringBuilder
    //   463: dup
    //   464: invokespecial 63	java/lang/StringBuilder:<init>	()V
    //   467: ldc 226
    //   469: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   472: aload 14
    //   474: iconst_0
    //   475: aaload
    //   476: invokevirtual 69	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   479: invokevirtual 76	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   482: invokevirtual 230	com/sec/factory/aporiented/athandler/AtSensorHb:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   485: astore 4
    //   487: goto -470 -> 17
    //   490: astore 13
    //   492: aload_0
    //   493: getfield 19	com/sec/factory/aporiented/athandler/AtSensorHb:CLASS_NAME	Ljava/lang/String;
    //   496: ldc 40
    //   498: ldc 232
    //   500: invokestatic 48	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   503: iconst_3
    //   504: anewarray 33	java/lang/String
    //   507: astore 14
    //   509: aload 14
    //   511: iconst_1
    //   512: ldc 234
    //   514: aastore
    //   515: aload 14
    //   517: iconst_2
    //   518: ldc 220
    //   520: aastore
    //   521: goto -144 -> 377
    //   524: astore_3
    //   525: aload_0
    //   526: monitorexit
    //   527: aload_3
    //   528: athrow
    //   529: ldc 234
    //   531: aload 14
    //   533: iconst_0
    //   534: aaload
    //   535: invokevirtual 224	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   538: ifeq +17 -> 555
    //   541: aload_0
    //   542: aload_1
    //   543: iconst_0
    //   544: aaload
    //   545: ldc 236
    //   547: invokevirtual 230	com/sec/factory/aporiented/athandler/AtSensorHb:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   550: astore 4
    //   552: goto -535 -> 17
    //   555: ldc 234
    //   557: aload 14
    //   559: iconst_1
    //   560: aaload
    //   561: invokevirtual 224	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   564: ifeq +29 -> 593
    //   567: ldc 234
    //   569: aload 14
    //   571: iconst_2
    //   572: aaload
    //   573: invokevirtual 224	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   576: ifeq +17 -> 593
    //   579: aload_0
    //   580: aload_1
    //   581: iconst_0
    //   582: aaload
    //   583: ldc 238
    //   585: invokevirtual 230	com/sec/factory/aporiented/athandler/AtSensorHb:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   588: astore 4
    //   590: goto -573 -> 17
    //   593: ldc 220
    //   595: aload 14
    //   597: iconst_1
    //   598: aaload
    //   599: invokevirtual 224	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   602: ifeq +29 -> 631
    //   605: ldc 234
    //   607: aload 14
    //   609: iconst_2
    //   610: aaload
    //   611: invokevirtual 224	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   614: ifeq +17 -> 631
    //   617: aload_0
    //   618: aload_1
    //   619: iconst_0
    //   620: aaload
    //   621: ldc 238
    //   623: invokevirtual 230	com/sec/factory/aporiented/athandler/AtSensorHb:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   626: astore 4
    //   628: goto -611 -> 17
    //   631: ldc 234
    //   633: aload 14
    //   635: iconst_1
    //   636: aaload
    //   637: invokevirtual 224	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   640: ifeq +29 -> 669
    //   643: ldc 220
    //   645: aload 14
    //   647: iconst_2
    //   648: aaload
    //   649: invokevirtual 224	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   652: ifeq +17 -> 669
    //   655: aload_0
    //   656: aload_1
    //   657: iconst_0
    //   658: aaload
    //   659: ldc 236
    //   661: invokevirtual 230	com/sec/factory/aporiented/athandler/AtSensorHb:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   664: astore 4
    //   666: goto -649 -> 17
    //   669: aload_0
    //   670: aload_1
    //   671: iconst_0
    //   672: aaload
    //   673: ldc 236
    //   675: invokevirtual 230	com/sec/factory/aporiented/athandler/AtSensorHb:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   678: astore 4
    //   680: goto -663 -> 17
    //   683: ldc 31
    //   685: astore 4
    //   687: goto -670 -> 17
    //   690: astore 11
    //   692: goto -383 -> 309
    //   695: iconst_0
    //   696: istore_2
    //   697: goto -582 -> 115
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	700	0	this	AtSensorHb
    //   0	700	1	paramArrayOfString	String[]
    //   1	696	2	bool1	boolean
    //   524	4	3	localObject	Object
    //   15	671	4	str	String
    //   66	176	5	bool2	boolean
    //   156	7	6	localIntent1	android.content.Intent
    //   227	26	7	localIntent2	android.content.Intent
    //   258	23	10	localPendingIntent	android.app.PendingIntent
    //   690	1	11	localInterruptedException	InterruptedException
    //   338	7	12	localIntent3	android.content.Intent
    //   490	1	13	localException	Exception
    //   375	271	14	arrayOfString1	String[]
    //   371	3	15	arrayOfString2	String[]
    // Exception table:
    //   from	to	target	type
    //   360	373	490	java/lang/Exception
    //   4	13	524	finally
    //   22	115	524	finally
    //   115	143	524	finally
    //   147	178	524	finally
    //   178	208	524	finally
    //   208	303	524	finally
    //   303	309	524	finally
    //   309	325	524	finally
    //   329	360	524	finally
    //   360	373	524	finally
    //   377	487	524	finally
    //   492	521	524	finally
    //   529	552	524	finally
    //   555	590	524	finally
    //   593	628	524	finally
    //   631	666	524	finally
    //   669	680	524	finally
    //   303	309	690	java/lang/InterruptedException
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtSensorHb
 * JD-Core Version:    0.7.1
 */