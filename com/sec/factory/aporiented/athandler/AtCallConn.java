package com.sec.factory.aporiented.athandler;

import android.content.Context;

public class AtCallConn
  extends AtCommandHandler
{
  private int mCallStatus;
  
  public AtCallConn(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "CALLCONN";
    this.CLASS_NAME = "AtCallConn";
    this.NUM_ARGS = 3;
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
    //   5: getfield 24	com/sec/factory/aporiented/athandler/AtCallConn:NUM_ARGS	I
    //   8: if_icmpeq +10 -> 18
    //   11: ldc 32
    //   13: astore_3
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_3
    //   17: areturn
    //   18: aload_0
    //   19: getfield 36	com/sec/factory/aporiented/athandler/AtCallConn:context	Landroid/content/Context;
    //   22: ldc 38
    //   24: invokevirtual 44	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   27: checkcast 46	android/telephony/TelephonyManager
    //   30: astore 4
    //   32: aload_0
    //   33: aload_1
    //   34: iconst_3
    //   35: anewarray 48	java/lang/String
    //   38: dup
    //   39: iconst_0
    //   40: ldc 50
    //   42: aastore
    //   43: dup
    //   44: iconst_1
    //   45: ldc 50
    //   47: aastore
    //   48: dup
    //   49: iconst_2
    //   50: ldc 50
    //   52: aastore
    //   53: invokevirtual 54	com/sec/factory/aporiented/athandler/AtCallConn:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   56: ifeq +156 -> 212
    //   59: aload_0
    //   60: getfield 21	com/sec/factory/aporiented/athandler/AtCallConn:CLASS_NAME	Ljava/lang/String;
    //   63: ldc 55
    //   65: ldc 57
    //   67: invokestatic 63	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   70: aload_0
    //   71: aload 4
    //   73: invokevirtual 67	android/telephony/TelephonyManager:getCallState	()I
    //   76: putfield 69	com/sec/factory/aporiented/athandler/AtCallConn:mCallStatus	I
    //   79: aload_0
    //   80: getfield 69	com/sec/factory/aporiented/athandler/AtCallConn:mCallStatus	I
    //   83: istore 10
    //   85: iload 10
    //   87: iconst_2
    //   88: if_icmpne +99 -> 187
    //   91: aload 4
    //   93: invokevirtual 75	java/lang/Object:getClass	()Ljava/lang/Class;
    //   96: invokevirtual 81	java/lang/Class:getName	()Ljava/lang/String;
    //   99: invokestatic 85	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   102: ldc 87
    //   104: iconst_0
    //   105: anewarray 77	java/lang/Class
    //   108: invokevirtual 91	java/lang/Class:getDeclaredMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   111: astore 12
    //   113: aload 12
    //   115: iconst_1
    //   116: invokevirtual 97	java/lang/reflect/Method:setAccessible	(Z)V
    //   119: aload 12
    //   121: aload 4
    //   123: iconst_0
    //   124: anewarray 71	java/lang/Object
    //   127: invokevirtual 101	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    //   130: checkcast 103	com/android/internal/telephony/ITelephony
    //   133: invokeinterface 107 1 0
    //   138: pop
    //   139: aload_0
    //   140: aload_1
    //   141: iconst_0
    //   142: aaload
    //   143: ldc 109
    //   145: invokevirtual 113	com/sec/factory/aporiented/athandler/AtCallConn:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   148: astore 14
    //   150: aload 14
    //   152: astore 5
    //   154: aload 5
    //   156: astore_3
    //   157: goto -143 -> 14
    //   160: astore 11
    //   162: aload_0
    //   163: getfield 21	com/sec/factory/aporiented/athandler/AtCallConn:CLASS_NAME	Ljava/lang/String;
    //   166: ldc 55
    //   168: ldc 115
    //   170: invokestatic 63	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   173: aload_0
    //   174: aload_1
    //   175: iconst_0
    //   176: aaload
    //   177: ldc 117
    //   179: invokevirtual 113	com/sec/factory/aporiented/athandler/AtCallConn:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   182: astore 5
    //   184: goto -30 -> 154
    //   187: aload_0
    //   188: getfield 21	com/sec/factory/aporiented/athandler/AtCallConn:CLASS_NAME	Ljava/lang/String;
    //   191: ldc 55
    //   193: ldc 119
    //   195: invokestatic 63	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   198: aload_0
    //   199: aload_1
    //   200: iconst_0
    //   201: aaload
    //   202: ldc 117
    //   204: invokevirtual 113	com/sec/factory/aporiented/athandler/AtCallConn:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   207: astore 5
    //   209: goto -55 -> 154
    //   212: aload_0
    //   213: aload_1
    //   214: iconst_2
    //   215: anewarray 48	java/lang/String
    //   218: dup
    //   219: iconst_0
    //   220: ldc 50
    //   222: aastore
    //   223: dup
    //   224: iconst_1
    //   225: ldc 121
    //   227: aastore
    //   228: invokevirtual 54	com/sec/factory/aporiented/athandler/AtCallConn:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   231: ifeq +144 -> 375
    //   234: aload_0
    //   235: getfield 21	com/sec/factory/aporiented/athandler/AtCallConn:CLASS_NAME	Ljava/lang/String;
    //   238: ldc 55
    //   240: new 123	java/lang/StringBuilder
    //   243: dup
    //   244: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   247: ldc 128
    //   249: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   252: aload_1
    //   253: iconst_2
    //   254: aaload
    //   255: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   258: invokevirtual 135	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   261: invokestatic 63	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   264: new 137	android/content/Intent
    //   267: dup
    //   268: ldc 139
    //   270: new 123	java/lang/StringBuilder
    //   273: dup
    //   274: invokespecial 126	java/lang/StringBuilder:<init>	()V
    //   277: ldc 141
    //   279: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   282: aload_1
    //   283: iconst_2
    //   284: aaload
    //   285: invokevirtual 132	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   288: invokevirtual 135	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   291: invokestatic 147	android/net/Uri:parse	(Ljava/lang/String;)Landroid/net/Uri;
    //   294: invokespecial 150	android/content/Intent:<init>	(Ljava/lang/String;Landroid/net/Uri;)V
    //   297: astore 6
    //   299: aload 6
    //   301: ldc 151
    //   303: invokevirtual 155	android/content/Intent:setFlags	(I)Landroid/content/Intent;
    //   306: pop
    //   307: aload 6
    //   309: bipush 32
    //   311: invokevirtual 158	android/content/Intent:addFlags	(I)Landroid/content/Intent;
    //   314: pop
    //   315: aload_0
    //   316: getfield 36	com/sec/factory/aporiented/athandler/AtCallConn:context	Landroid/content/Context;
    //   319: aload 6
    //   321: invokevirtual 162	android/content/Context:startActivity	(Landroid/content/Intent;)V
    //   324: ldc2_w 163
    //   327: invokestatic 170	java/lang/Thread:sleep	(J)V
    //   330: aload_0
    //   331: aload 4
    //   333: invokevirtual 67	android/telephony/TelephonyManager:getCallState	()I
    //   336: putfield 69	com/sec/factory/aporiented/athandler/AtCallConn:mCallStatus	I
    //   339: aload_0
    //   340: getfield 69	com/sec/factory/aporiented/athandler/AtCallConn:mCallStatus	I
    //   343: iconst_2
    //   344: if_icmpne +17 -> 361
    //   347: aload_0
    //   348: aload_1
    //   349: iconst_0
    //   350: aaload
    //   351: ldc 109
    //   353: invokevirtual 113	com/sec/factory/aporiented/athandler/AtCallConn:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   356: astore 5
    //   358: goto -204 -> 154
    //   361: aload_0
    //   362: aload_1
    //   363: iconst_0
    //   364: aaload
    //   365: ldc 117
    //   367: invokevirtual 113	com/sec/factory/aporiented/athandler/AtCallConn:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   370: astore 5
    //   372: goto -218 -> 154
    //   375: aload_0
    //   376: aload_1
    //   377: iconst_3
    //   378: anewarray 48	java/lang/String
    //   381: dup
    //   382: iconst_0
    //   383: ldc 121
    //   385: aastore
    //   386: dup
    //   387: iconst_1
    //   388: ldc 50
    //   390: aastore
    //   391: dup
    //   392: iconst_2
    //   393: ldc 50
    //   395: aastore
    //   396: invokevirtual 54	com/sec/factory/aporiented/athandler/AtCallConn:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   399: ifeq +8 -> 407
    //   402: aconst_null
    //   403: astore_3
    //   404: goto -390 -> 14
    //   407: ldc 32
    //   409: astore 5
    //   411: goto -257 -> 154
    //   414: astore 9
    //   416: goto -86 -> 330
    //   419: astore_2
    //   420: aload_0
    //   421: monitorexit
    //   422: aload_2
    //   423: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	424	0	this	AtCallConn
    //   0	424	1	paramArrayOfString	String[]
    //   419	4	2	localObject1	Object
    //   13	391	3	localObject2	Object
    //   30	302	4	localTelephonyManager	android.telephony.TelephonyManager
    //   152	258	5	str1	String
    //   297	23	6	localIntent	android.content.Intent
    //   414	1	9	localInterruptedException	InterruptedException
    //   83	6	10	i	int
    //   160	1	11	localException	Exception
    //   111	9	12	localMethod	java.lang.reflect.Method
    //   148	3	14	str2	String
    // Exception table:
    //   from	to	target	type
    //   91	150	160	java/lang/Exception
    //   324	330	414	java/lang/InterruptedException
    //   2	11	419	finally
    //   18	85	419	finally
    //   91	150	419	finally
    //   162	184	419	finally
    //   187	209	419	finally
    //   212	324	419	finally
    //   324	330	419	finally
    //   330	358	419	finally
    //   361	372	419	finally
    //   375	402	419	finally
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtCallConn
 * JD-Core Version:    0.7.1
 */