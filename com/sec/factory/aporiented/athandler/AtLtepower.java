package com.sec.factory.aporiented.athandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.sec.factory.aporiented.ResponseWriter;
import com.sec.factory.app.factorytest.FactoryTestPhone;
import com.sec.factory.cporiented.ResponseWriterCPO;

public class AtLtepower
  extends AtCommandHandler
{
  protected FactoryTestPhone mFactoryPhone;
  public BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str;
      if ("com.sec.factory.aporiented.athandler.AtLtepower.SetLTEPower".equals(paramAnonymousIntent.getAction()))
      {
        str = paramAnonymousIntent.getStringExtra("EXTRA_KEY");
        if (!str.equals("OK")) {
          break label76;
        }
        if (AtLtepower.this.getCmdType() != 0) {
          break label55;
        }
        AtLtepower.this.writerCpo.write(2, "53", "00", null);
      }
      label55:
      label76:
      while (!str.equals("NG"))
      {
        return;
        AtLtepower.this.writer.write(AtLtepower.this.responseOK("0"));
        return;
      }
      if (AtLtepower.this.getCmdType() == 0)
      {
        AtLtepower.this.writerCpo.write(4, "53", "00", null);
        return;
      }
      AtLtepower.this.writer.write(AtLtepower.this.responseNG("0"));
    }
  };
  
  public AtLtepower(Context paramContext, ResponseWriter paramResponseWriter)
  {
    super(paramContext);
    this.CMD_NAME = "LTEPOWER";
    this.CLASS_NAME = "AtLtepower";
    this.NUM_ARGS = 3;
    this.writer = paramResponseWriter;
  }
  
  public AtLtepower(Context paramContext, ResponseWriterCPO paramResponseWriterCPO)
  {
    super(paramContext);
    this.CMD_NAME = "LTEPOWER";
    this.CLASS_NAME = "AtLtepower";
    this.NUM_ARGS = 3;
    this.writerCpo = paramResponseWriterCPO;
    startReceiver();
  }
  
  /* Error */
  public String handleCommand(String[] paramArrayOfString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: iconst_1
    //   5: anewarray 52	java/lang/String
    //   8: dup
    //   9: iconst_0
    //   10: ldc 54
    //   12: aastore
    //   13: invokevirtual 58	com/sec/factory/aporiented/athandler/AtLtepower:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   16: ifeq +89 -> 105
    //   19: aload_0
    //   20: getfield 60	com/sec/factory/aporiented/athandler/AtLtepower:mFactoryPhone	Lcom/sec/factory/app/factorytest/FactoryTestPhone;
    //   23: ifnonnull +25 -> 48
    //   26: aload_0
    //   27: new 62	com/sec/factory/app/factorytest/FactoryTestPhone
    //   30: dup
    //   31: aload_0
    //   32: getfield 66	com/sec/factory/aporiented/athandler/AtLtepower:context	Landroid/content/Context;
    //   35: invokespecial 67	com/sec/factory/app/factorytest/FactoryTestPhone:<init>	(Landroid/content/Context;)V
    //   38: putfield 60	com/sec/factory/aporiented/athandler/AtLtepower:mFactoryPhone	Lcom/sec/factory/app/factorytest/FactoryTestPhone;
    //   41: aload_0
    //   42: getfield 60	com/sec/factory/aporiented/athandler/AtLtepower:mFactoryPhone	Lcom/sec/factory/app/factorytest/FactoryTestPhone;
    //   45: invokevirtual 70	com/sec/factory/app/factorytest/FactoryTestPhone:bindSecPhoneService	()V
    //   48: aload_1
    //   49: iconst_1
    //   50: aaload
    //   51: astore 4
    //   53: new 72	java/lang/StringBuilder
    //   56: dup
    //   57: invokespecial 74	java/lang/StringBuilder:<init>	()V
    //   60: aload 4
    //   62: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   65: aload_1
    //   66: iconst_2
    //   67: aaload
    //   68: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   74: astore 5
    //   76: aload_0
    //   77: getfield 60	com/sec/factory/aporiented/athandler/AtLtepower:mFactoryPhone	Lcom/sec/factory/app/factorytest/FactoryTestPhone;
    //   80: aload 5
    //   82: invokevirtual 86	com/sec/factory/app/factorytest/FactoryTestPhone:sendLteRilCommand_POWER	(Ljava/lang/String;)V
    //   85: aload_0
    //   86: invokevirtual 90	com/sec/factory/aporiented/athandler/AtLtepower:getCmdType	()I
    //   89: istore 6
    //   91: aconst_null
    //   92: astore_3
    //   93: iload 6
    //   95: ifne +6 -> 101
    //   98: ldc 92
    //   100: astore_3
    //   101: aload_0
    //   102: monitorexit
    //   103: aload_3
    //   104: areturn
    //   105: ldc 94
    //   107: astore_3
    //   108: goto -7 -> 101
    //   111: astore_2
    //   112: aload_0
    //   113: monitorexit
    //   114: aload_2
    //   115: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	116	0	this	AtLtepower
    //   0	116	1	paramArrayOfString	String[]
    //   111	4	2	localObject	Object
    //   92	16	3	str1	String
    //   51	10	4	str2	String
    //   74	7	5	str3	String
    //   89	5	6	i	int
    // Exception table:
    //   from	to	target	type
    //   2	48	111	finally
    //   48	91	111	finally
  }
  
  public void startReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.sec.factory.aporiented.athandler.AtLtepower.SetLTEPower");
    this.context.registerReceiver(this.mReceiver, localIntentFilter);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtLtepower
 * JD-Core Version:    0.7.1
 */