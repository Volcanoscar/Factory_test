package com.sec.factory.aporiented.athandler;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import com.android.internal.widget.LockPatternUtils;

public class AtLockCode
  extends AtCommandHandler
{
  private KeyguardManager.KeyguardLock mKeyguardLock = null;
  private KeyguardManager mKeyguardManager;
  private LockPatternUtils mLockPatternUtils;
  
  public AtLockCode(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "LOCKCODE";
    this.CLASS_NAME = "AtLockCode";
    this.NUM_ARGS = 2;
  }
  
  /* Error */
  public String handleCommand(String[] paramArrayOfString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: new 35	com/android/internal/widget/LockPatternUtils
    //   6: dup
    //   7: aload_0
    //   8: getfield 39	com/sec/factory/aporiented/athandler/AtLockCode:context	Landroid/content/Context;
    //   11: iconst_1
    //   12: invokespecial 42	com/android/internal/widget/LockPatternUtils:<init>	(Landroid/content/Context;Z)V
    //   15: putfield 44	com/sec/factory/aporiented/athandler/AtLockCode:mLockPatternUtils	Lcom/android/internal/widget/LockPatternUtils;
    //   18: aload_0
    //   19: aload_0
    //   20: getfield 39	com/sec/factory/aporiented/athandler/AtLockCode:context	Landroid/content/Context;
    //   23: ldc 46
    //   25: invokevirtual 52	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   28: checkcast 54	android/app/KeyguardManager
    //   31: putfield 56	com/sec/factory/aporiented/athandler/AtLockCode:mKeyguardManager	Landroid/app/KeyguardManager;
    //   34: aload_1
    //   35: arraylength
    //   36: aload_0
    //   37: getfield 31	com/sec/factory/aporiented/athandler/AtLockCode:NUM_ARGS	I
    //   40: if_icmpeq +14 -> 54
    //   43: aload_0
    //   44: getfield 27	com/sec/factory/aporiented/athandler/AtLockCode:CLASS_NAME	Ljava/lang/String;
    //   47: ldc 57
    //   49: ldc 59
    //   51: invokestatic 65	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   54: aload_0
    //   55: aload_1
    //   56: iconst_2
    //   57: anewarray 67	java/lang/String
    //   60: dup
    //   61: iconst_0
    //   62: ldc 69
    //   64: aastore
    //   65: dup
    //   66: iconst_1
    //   67: ldc 69
    //   69: aastore
    //   70: invokevirtual 73	com/sec/factory/aporiented/athandler/AtLockCode:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   73: ifeq +111 -> 184
    //   76: aload_0
    //   77: getfield 27	com/sec/factory/aporiented/athandler/AtLockCode:CLASS_NAME	Ljava/lang/String;
    //   80: ldc 57
    //   82: ldc 75
    //   84: invokestatic 65	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   87: aload_0
    //   88: getfield 44	com/sec/factory/aporiented/athandler/AtLockCode:mLockPatternUtils	Lcom/android/internal/widget/LockPatternUtils;
    //   91: iconst_1
    //   92: invokevirtual 79	com/android/internal/widget/LockPatternUtils:clearLock	(Z)V
    //   95: aload_0
    //   96: getfield 44	com/sec/factory/aporiented/athandler/AtLockCode:mLockPatternUtils	Lcom/android/internal/widget/LockPatternUtils;
    //   99: iconst_0
    //   100: invokevirtual 82	com/android/internal/widget/LockPatternUtils:setPermanentlyLocked	(Z)V
    //   103: aload_0
    //   104: aload_0
    //   105: getfield 56	com/sec/factory/aporiented/athandler/AtLockCode:mKeyguardManager	Landroid/app/KeyguardManager;
    //   108: ldc 84
    //   110: invokevirtual 88	android/app/KeyguardManager:newKeyguardLock	(Ljava/lang/String;)Landroid/app/KeyguardManager$KeyguardLock;
    //   113: putfield 16	com/sec/factory/aporiented/athandler/AtLockCode:mKeyguardLock	Landroid/app/KeyguardManager$KeyguardLock;
    //   116: aload_0
    //   117: getfield 16	com/sec/factory/aporiented/athandler/AtLockCode:mKeyguardLock	Landroid/app/KeyguardManager$KeyguardLock;
    //   120: invokevirtual 94	android/app/KeyguardManager$KeyguardLock:disableKeyguard	()V
    //   123: aload_0
    //   124: getfield 56	com/sec/factory/aporiented/athandler/AtLockCode:mKeyguardManager	Landroid/app/KeyguardManager;
    //   127: aconst_null
    //   128: invokevirtual 98	android/app/KeyguardManager:exitKeyguardSecurely	(Landroid/app/KeyguardManager$OnKeyguardExitResult;)V
    //   131: new 100	android/content/Intent
    //   134: dup
    //   135: ldc 102
    //   137: aconst_null
    //   138: invokespecial 105	android/content/Intent:<init>	(Ljava/lang/String;Landroid/net/Uri;)V
    //   141: astore 4
    //   143: aload 4
    //   145: ldc 107
    //   147: invokevirtual 111	android/content/Intent:addCategory	(Ljava/lang/String;)Landroid/content/Intent;
    //   150: pop
    //   151: aload 4
    //   153: ldc 112
    //   155: invokevirtual 116	android/content/Intent:addFlags	(I)Landroid/content/Intent;
    //   158: pop
    //   159: aload_0
    //   160: getfield 39	com/sec/factory/aporiented/athandler/AtLockCode:context	Landroid/content/Context;
    //   163: aload 4
    //   165: invokevirtual 120	android/content/Context:startActivity	(Landroid/content/Intent;)V
    //   168: aload_0
    //   169: aload_1
    //   170: iconst_0
    //   171: aaload
    //   172: invokevirtual 124	com/sec/factory/aporiented/athandler/AtLockCode:responseOK	(Ljava/lang/String;)Ljava/lang/String;
    //   175: astore 7
    //   177: aload 7
    //   179: astore_3
    //   180: aload_0
    //   181: monitorexit
    //   182: aload_3
    //   183: areturn
    //   184: aload_0
    //   185: aload_1
    //   186: iconst_2
    //   187: anewarray 67	java/lang/String
    //   190: dup
    //   191: iconst_0
    //   192: ldc 126
    //   194: aastore
    //   195: dup
    //   196: iconst_1
    //   197: ldc 69
    //   199: aastore
    //   200: invokevirtual 73	com/sec/factory/aporiented/athandler/AtLockCode:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   203: ifeq +56 -> 259
    //   206: aload_0
    //   207: getfield 27	com/sec/factory/aporiented/athandler/AtLockCode:CLASS_NAME	Ljava/lang/String;
    //   210: ldc 57
    //   212: ldc 128
    //   214: invokestatic 65	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   217: aload_0
    //   218: getfield 44	com/sec/factory/aporiented/athandler/AtLockCode:mLockPatternUtils	Lcom/android/internal/widget/LockPatternUtils;
    //   221: invokevirtual 132	com/android/internal/widget/LockPatternUtils:isLockPatternEnabled	()Z
    //   224: ifne +13 -> 237
    //   227: aload_0
    //   228: getfield 44	com/sec/factory/aporiented/athandler/AtLockCode:mLockPatternUtils	Lcom/android/internal/widget/LockPatternUtils;
    //   231: invokevirtual 135	com/android/internal/widget/LockPatternUtils:isLockPasswordEnabled	()Z
    //   234: ifeq +14 -> 248
    //   237: aload_0
    //   238: aload_1
    //   239: iconst_0
    //   240: aaload
    //   241: invokevirtual 138	com/sec/factory/aporiented/athandler/AtLockCode:responseNG	(Ljava/lang/String;)Ljava/lang/String;
    //   244: astore_3
    //   245: goto -65 -> 180
    //   248: aload_0
    //   249: aload_1
    //   250: iconst_0
    //   251: aaload
    //   252: invokevirtual 124	com/sec/factory/aporiented/athandler/AtLockCode:responseOK	(Ljava/lang/String;)Ljava/lang/String;
    //   255: astore_3
    //   256: goto -76 -> 180
    //   259: aload_0
    //   260: getfield 27	com/sec/factory/aporiented/athandler/AtLockCode:CLASS_NAME	Ljava/lang/String;
    //   263: ldc 57
    //   265: ldc 140
    //   267: invokestatic 65	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   270: ldc 142
    //   272: astore_3
    //   273: goto -93 -> 180
    //   276: astore_2
    //   277: aload_0
    //   278: monitorexit
    //   279: aload_2
    //   280: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	281	0	this	AtLockCode
    //   0	281	1	paramArrayOfString	String[]
    //   276	4	2	localObject	Object
    //   179	94	3	str1	String
    //   141	23	4	localIntent	android.content.Intent
    //   175	3	7	str2	String
    // Exception table:
    //   from	to	target	type
    //   2	54	276	finally
    //   54	177	276	finally
    //   184	237	276	finally
    //   237	245	276	finally
    //   248	256	276	finally
    //   259	270	276	finally
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtLockCode
 * JD-Core Version:    0.7.1
 */