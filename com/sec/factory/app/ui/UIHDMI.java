package com.sec.factory.app.ui;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemProperties;
import android.widget.Toast;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;

public class UIHDMI
  extends UIBase
{
  private boolean FLAG_NV_WRITE = false;
  private final String HDMI_MSG = "HDMI Pattern Display On";
  private boolean isCapri = "Capri".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isMSM8260A = "MSM8260A".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isMSM8930 = "MSM8930".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isMSM8960 = "MSM8960".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isPegaPrime = "PegaPrime".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isPegasus = "Pegasus".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isPegasusPrime = "PegasusPrime".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    /* Error */
    public void onReceive(android.content.Context paramAnonymousContext, android.content.Intent paramAnonymousIntent)
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_2
      //   3: invokevirtual 27	android/content/Intent:getAction	()Ljava/lang/String;
      //   6: astore 4
      //   8: ldc 29
      //   10: aload 4
      //   12: invokevirtual 35	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   15: ifeq +27 -> 42
      //   18: aload_0
      //   19: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   22: getfield 39	com/sec/factory/app/ui/UIHDMI:CLASS_NAME	Ljava/lang/String;
      //   25: ldc 40
      //   27: ldc 29
      //   29: invokestatic 46	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   32: aload_0
      //   33: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   36: invokevirtual 49	com/sec/factory/app/ui/UIHDMI:finish	()V
      //   39: aload_0
      //   40: monitorexit
      //   41: return
      //   42: aload 4
      //   44: ldc 51
      //   46: invokevirtual 35	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   49: ifeq -10 -> 39
      //   52: aload_2
      //   53: ldc 53
      //   55: iconst_m1
      //   56: invokevirtual 57	android/content/Intent:getIntExtra	(Ljava/lang/String;I)I
      //   59: istore 6
      //   61: aload_0
      //   62: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   65: getfield 39	com/sec/factory/app/ui/UIHDMI:CLASS_NAME	Ljava/lang/String;
      //   68: ldc 40
      //   70: new 59	java/lang/StringBuilder
      //   73: dup
      //   74: invokespecial 60	java/lang/StringBuilder:<init>	()V
      //   77: ldc 62
      //   79: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   82: iload 6
      //   84: invokevirtual 69	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   87: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   90: invokestatic 46	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   93: aload_0
      //   94: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   97: invokevirtual 76	com/sec/factory/app/ui/UIHDMI:isFinishing	()Z
      //   100: ifeq +17 -> 117
      //   103: aload_0
      //   104: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   107: getfield 39	com/sec/factory/app/ui/UIHDMI:CLASS_NAME	Ljava/lang/String;
      //   110: ldc 40
      //   112: ldc 78
      //   114: invokestatic 46	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   117: iload 6
      //   119: iconst_1
      //   120: if_icmpne +239 -> 359
      //   123: aload_0
      //   124: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   127: invokestatic 82	com/sec/factory/app/ui/UIHDMI:access$000	(Lcom/sec/factory/app/ui/UIHDMI;)Z
      //   130: ifne +67 -> 197
      //   133: aload_0
      //   134: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   137: invokestatic 85	com/sec/factory/app/ui/UIHDMI:access$100	(Lcom/sec/factory/app/ui/UIHDMI;)Z
      //   140: ifne +57 -> 197
      //   143: aload_0
      //   144: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   147: invokestatic 88	com/sec/factory/app/ui/UIHDMI:access$200	(Lcom/sec/factory/app/ui/UIHDMI;)Z
      //   150: ifne +47 -> 197
      //   153: aload_0
      //   154: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   157: invokestatic 91	com/sec/factory/app/ui/UIHDMI:access$300	(Lcom/sec/factory/app/ui/UIHDMI;)Z
      //   160: ifne +37 -> 197
      //   163: aload_0
      //   164: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   167: invokestatic 94	com/sec/factory/app/ui/UIHDMI:access$400	(Lcom/sec/factory/app/ui/UIHDMI;)Z
      //   170: ifne +27 -> 197
      //   173: aload_0
      //   174: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   177: invokestatic 97	com/sec/factory/app/ui/UIHDMI:access$500	(Lcom/sec/factory/app/ui/UIHDMI;)Z
      //   180: ifne +17 -> 197
      //   183: aload_0
      //   184: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   187: invokestatic 100	com/sec/factory/app/ui/UIHDMI:access$600	(Lcom/sec/factory/app/ui/UIHDMI;)Z
      //   190: istore 9
      //   192: iload 9
      //   194: ifeq +23 -> 217
      //   197: aload_0
      //   198: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   201: getfield 39	com/sec/factory/app/ui/UIHDMI:CLASS_NAME	Ljava/lang/String;
      //   204: ldc 102
      //   206: ldc 104
      //   208: invokestatic 46	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   211: ldc2_w 105
      //   214: invokestatic 112	java/lang/Thread:sleep	(J)V
      //   217: aload_0
      //   218: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   221: invokevirtual 116	com/sec/factory/app/ui/UIHDMI:getApplicationContext	()Landroid/content/Context;
      //   224: invokestatic 122	com/sec/factory/modules/ModuleAudio:instance	(Landroid/content/Context;)Lcom/sec/factory/modules/ModuleAudio;
      //   227: invokevirtual 125	com/sec/factory/modules/ModuleAudio:stopMedia	()V
      //   230: aload_0
      //   231: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   234: invokevirtual 116	com/sec/factory/app/ui/UIHDMI:getApplicationContext	()Landroid/content/Context;
      //   237: invokestatic 122	com/sec/factory/modules/ModuleAudio:instance	(Landroid/content/Context;)Lcom/sec/factory/modules/ModuleAudio;
      //   240: iconst_3
      //   241: invokevirtual 129	com/sec/factory/modules/ModuleAudio:setAudioPath	(I)V
      //   244: aload_0
      //   245: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   248: invokevirtual 116	com/sec/factory/app/ui/UIHDMI:getApplicationContext	()Landroid/content/Context;
      //   251: invokestatic 122	com/sec/factory/modules/ModuleAudio:instance	(Landroid/content/Context;)Lcom/sec/factory/modules/ModuleAudio;
      //   254: ldc 130
      //   256: iconst_1
      //   257: invokevirtual 134	com/sec/factory/modules/ModuleAudio:playMedia	(IZ)V
      //   260: aload_0
      //   261: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   264: invokestatic 137	com/sec/factory/app/ui/UIHDMI:access$700	(Lcom/sec/factory/app/ui/UIHDMI;)Z
      //   267: ifne -228 -> 39
      //   270: aload_0
      //   271: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   274: invokevirtual 141	com/sec/factory/app/ui/UIHDMI:getIntent	()Landroid/content/Intent;
      //   277: ldc 143
      //   279: iconst_0
      //   280: invokevirtual 147	android/content/Intent:getBooleanExtra	(Ljava/lang/String;Z)Z
      //   283: ifeq -244 -> 39
      //   286: aload_0
      //   287: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   290: bipush 80
      //   292: invokevirtual 151	com/sec/factory/app/ui/UIHDMI:setTestResult	(B)V
      //   295: aload_0
      //   296: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   299: iconst_1
      //   300: invokestatic 155	com/sec/factory/app/ui/UIHDMI:access$702	(Lcom/sec/factory/app/ui/UIHDMI;Z)Z
      //   303: pop
      //   304: goto -265 -> 39
      //   307: astore 5
      //   309: aload_0
      //   310: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   313: getfield 39	com/sec/factory/app/ui/UIHDMI:CLASS_NAME	Ljava/lang/String;
      //   316: ldc 157
      //   318: new 59	java/lang/StringBuilder
      //   321: dup
      //   322: invokespecial 60	java/lang/StringBuilder:<init>	()V
      //   325: ldc 159
      //   327: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   330: aload 5
      //   332: invokevirtual 162	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   335: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   338: invokestatic 46	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   341: goto -302 -> 39
      //   344: astore_3
      //   345: aload_0
      //   346: monitorexit
      //   347: aload_3
      //   348: athrow
      //   349: astore 7
      //   351: aload 7
      //   353: invokevirtual 165	java/lang/InterruptedException:printStackTrace	()V
      //   356: goto -139 -> 217
      //   359: iload 6
      //   361: ifne -322 -> 39
      //   364: aload_0
      //   365: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   368: invokevirtual 116	com/sec/factory/app/ui/UIHDMI:getApplicationContext	()Landroid/content/Context;
      //   371: invokestatic 122	com/sec/factory/modules/ModuleAudio:instance	(Landroid/content/Context;)Lcom/sec/factory/modules/ModuleAudio;
      //   374: invokevirtual 125	com/sec/factory/modules/ModuleAudio:stopMedia	()V
      //   377: ldc2_w 166
      //   380: invokestatic 112	java/lang/Thread:sleep	(J)V
      //   383: aload_0
      //   384: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   387: iconst_0
      //   388: invokestatic 170	com/sec/factory/app/ui/UIHDMI:access$802	(Lcom/sec/factory/app/ui/UIHDMI;Z)Z
      //   391: pop
      //   392: aload_0
      //   393: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   396: invokestatic 173	com/sec/factory/app/ui/UIHDMI:access$900	(Lcom/sec/factory/app/ui/UIHDMI;)V
      //   399: ldc2_w 166
      //   402: invokestatic 112	java/lang/Thread:sleep	(J)V
      //   405: aload_0
      //   406: getfield 12	com/sec/factory/app/ui/UIHDMI$1:this$0	Lcom/sec/factory/app/ui/UIHDMI;
      //   409: invokevirtual 116	com/sec/factory/app/ui/UIHDMI:getApplicationContext	()Landroid/content/Context;
      //   412: invokestatic 122	com/sec/factory/modules/ModuleAudio:instance	(Landroid/content/Context;)Lcom/sec/factory/modules/ModuleAudio;
      //   415: ldc 130
      //   417: iconst_1
      //   418: invokevirtual 134	com/sec/factory/modules/ModuleAudio:playMedia	(IZ)V
      //   421: goto -382 -> 39
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	424	0	this	1
      //   0	424	1	paramAnonymousContext	android.content.Context
      //   0	424	2	paramAnonymousIntent	android.content.Intent
      //   344	4	3	localObject	Object
      //   6	37	4	str	String
      //   307	24	5	localException	Exception
      //   59	301	6	i	int
      //   349	3	7	localInterruptedException	InterruptedException
      //   190	3	9	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   8	39	307	java/lang/Exception
      //   42	117	307	java/lang/Exception
      //   123	192	307	java/lang/Exception
      //   197	217	307	java/lang/Exception
      //   217	304	307	java/lang/Exception
      //   351	356	307	java/lang/Exception
      //   364	421	307	java/lang/Exception
      //   2	8	344	finally
      //   8	39	344	finally
      //   42	117	344	finally
      //   123	192	344	finally
      //   197	217	344	finally
      //   217	304	344	finally
      //   309	341	344	finally
      //   351	356	344	finally
      //   364	421	344	finally
      //   197	217	349	java/lang/InterruptedException
    }
  };
  private boolean mIsTurnOffAudioPath = false;
  
  public UIHDMI()
  {
    super("UIHDMI", 23);
  }
  
  public UIHDMI(String paramString, int paramInt)
  {
    super(paramString, paramInt);
  }
  
  private void stopMedia()
  {
    new Thread()
    {
      public void run()
      {
        ModuleAudio.instance(UIHDMI.this.getApplicationContext()).stopMedia();
        UIHDMI.this.turnOffAudioPath();
      }
    }.start();
  }
  
  /* Error */
  private void turnOffAudioPath()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual 111	com/sec/factory/app/ui/UIHDMI:isFinishing	()Z
    //   6: ifeq +28 -> 34
    //   9: aload_0
    //   10: getfield 114	com/sec/factory/app/ui/UIHDMI:CLASS_NAME	Ljava/lang/String;
    //   13: ldc 115
    //   15: ldc 117
    //   17: invokestatic 123	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   20: aload_0
    //   21: invokevirtual 127	com/sec/factory/app/ui/UIHDMI:getApplicationContext	()Landroid/content/Context;
    //   24: invokestatic 133	com/sec/factory/modules/ModuleAudio:instance	(Landroid/content/Context;)Lcom/sec/factory/modules/ModuleAudio;
    //   27: iconst_4
    //   28: invokevirtual 137	com/sec/factory/modules/ModuleAudio:setAudioPath	(I)V
    //   31: aload_0
    //   32: monitorexit
    //   33: return
    //   34: aload_0
    //   35: getfield 33	com/sec/factory/app/ui/UIHDMI:mIsTurnOffAudioPath	Z
    //   38: ifne -7 -> 31
    //   41: aload_0
    //   42: iconst_1
    //   43: putfield 33	com/sec/factory/app/ui/UIHDMI:mIsTurnOffAudioPath	Z
    //   46: aload_0
    //   47: invokevirtual 127	com/sec/factory/app/ui/UIHDMI:getApplicationContext	()Landroid/content/Context;
    //   50: invokestatic 133	com/sec/factory/modules/ModuleAudio:instance	(Landroid/content/Context;)Lcom/sec/factory/modules/ModuleAudio;
    //   53: iconst_4
    //   54: invokevirtual 137	com/sec/factory/modules/ModuleAudio:setAudioPath	(I)V
    //   57: aload_0
    //   58: getfield 114	com/sec/factory/app/ui/UIHDMI:CLASS_NAME	Ljava/lang/String;
    //   61: ldc 139
    //   63: new 141	java/lang/StringBuilder
    //   66: dup
    //   67: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   70: ldc 145
    //   72: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: aload_0
    //   76: getfield 33	com/sec/factory/app/ui/UIHDMI:mIsTurnOffAudioPath	Z
    //   79: invokevirtual 152	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   82: invokevirtual 156	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   85: invokestatic 123	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   88: goto -57 -> 31
    //   91: astore_1
    //   92: aload_0
    //   93: monitorexit
    //   94: aload_1
    //   95: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	96	0	this	UIHDMI
    //   91	4	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	31	91	finally
    //   34	88	91	finally
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    String str = SystemProperties.get("ro.board.platform");
    if ((str != null) && (str.equals("capri"))) {
      setContentView(2130903068);
    }
    for (;;)
    {
      FtUtil.setRemoveSystemUI(getWindow(), true);
      ModuleAudio.instance(getApplicationContext()).setStreamMusicVolumeMax();
      return;
      setContentView(2130903067);
    }
  }
  
  public void onPause()
  {
    super.onPause();
    unregisterReceiver(this.mBroadcastReceiver);
    if ("TI".equalsIgnoreCase(Support.Feature.getString("CHIPSET_MANUFACTURE"))) {
      ModuleCommon.instance(getApplicationContext()).setHDMIPatternSwitch("0");
    }
    stopMedia();
  }
  
  public void onResume()
  {
    super.onResume();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.android.samsungtest.HDMITEST_STOP");
    localIntentFilter.addAction("android.intent.action.HDMI_AUDIO_PLUG");
    registerReceiver(this.mBroadcastReceiver, localIntentFilter);
    if ("TI".equalsIgnoreCase(Support.Feature.getString("CHIPSET_MANUFACTURE"))) {
      ModuleCommon.instance(getApplicationContext()).setHDMIPatternSwitch("4");
    }
    this.mIsTurnOffAudioPath = false;
    FtUtil.log_i(this.CLASS_NAME, "onResume()", "mIsTurnOffAudioPath = " + this.mIsTurnOffAudioPath);
    ModuleAudio.instance(getApplicationContext()).playMedia(2131034114, true);
    Toast.makeText(getApplicationContext(), "HDMI Pattern Display On", 0).show();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIHDMI
 * JD-Core Version:    0.7.1
 */