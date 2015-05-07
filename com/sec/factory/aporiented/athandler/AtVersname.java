package com.sec.factory.aporiented.athandler;

import android.content.Context;
import android.hardware.Camera;
import android.os.SystemProperties;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;
import java.io.File;

public class AtVersname
  extends AtCommandHandler
{
  private final int NEW_DM_DATA_LEN = 20;
  private String device = SystemProperties.get("ro.product.device", "Unknown").trim().toLowerCase();
  private boolean isCPName_IMC = "IMC".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME_CP"));
  private boolean isMSM7227A = "MSM7227A".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isMSM8225 = "MSM8225".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isMSM8260A = "MSM8260A".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isMSM8930 = "MSM8930".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isMSM8960 = "MSM8960".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isNon_LiveDemo = "false".equalsIgnoreCase(Support.Feature.getString("LIVE_DEMO_MODEL"));
  private boolean isOMAP4470 = "OMAP4470".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isPegaPrime = "PegaPrime".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isPegasus = "Pegasus".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isPegasusPrime = "PegasusPrime".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isSC5C210ABB = "SC5C210ABB-A040".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private boolean isU8420 = "U8420".equalsIgnoreCase(Support.Feature.getString("CHIPSET_NAME"));
  private Camera mCameraDevice;
  
  public AtVersname(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "VERSNAME";
    this.CLASS_NAME = "AtVersname";
    this.NUM_ARGS = 3;
    if (!new File(Support.Kernel.getFilePath("EFS_HW_PATH")).exists())
    {
      mModuleCommon.setHWVer(Support.Feature.getString("MODEL_HARDWARE_REVISION"));
      Support.Kernel.setPermission(Support.Kernel.getFilePath("EFS_HW_PATH"), true, true, true, true, true, false);
      FtUtil.log_d(this.CLASS_NAME, "AtVersname", "EFS_HW_PATH is created...");
      return;
    }
    FtUtil.log_d(this.CLASS_NAME, "AtVersname", "EFS_HW_PATHis already existed...");
  }
  
  private boolean startCameraforFwRead()
  {
    FtUtil.log_d(this.CLASS_NAME, "startCameraforFwRead", "openCameraDevice");
    if (this.mCameraDevice == null) {}
    try
    {
      this.mCameraDevice = Camera.open(0);
      Camera localCamera = this.mCameraDevice;
      boolean bool = false;
      if (localCamera != null) {
        bool = true;
      }
      return bool;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        FtUtil.log_e(localException);
      }
    }
  }
  
  private boolean stopCameraforFwRead()
  {
    FtUtil.log_d(this.CLASS_NAME, "stopCameraforFwRead", "releaseCameraDevice");
    if (this.mCameraDevice != null)
    {
      this.mCameraDevice.release();
      this.mCameraDevice = null;
    }
    return true;
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
    //   5: getfield 132	com/sec/factory/aporiented/athandler/AtVersname:NUM_ARGS	I
    //   8: if_icmpeq +10 -> 18
    //   11: ldc 203
    //   13: astore_3
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_3
    //   17: areturn
    //   18: aload_0
    //   19: aload_1
    //   20: iconst_3
    //   21: anewarray 42	java/lang/String
    //   24: dup
    //   25: iconst_0
    //   26: ldc 205
    //   28: aastore
    //   29: dup
    //   30: iconst_1
    //   31: ldc 205
    //   33: aastore
    //   34: dup
    //   35: iconst_2
    //   36: ldc 207
    //   38: aastore
    //   39: invokevirtual 211	com/sec/factory/aporiented/athandler/AtVersname:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   42: ifeq +175 -> 217
    //   45: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   48: invokevirtual 214	com/sec/factory/modules/ModuleCommon:getHWVer	()Ljava/lang/String;
    //   51: astore 115
    //   53: aload 115
    //   55: ifnull +11 -> 66
    //   58: aload 115
    //   60: invokevirtual 218	java/lang/String:length	()I
    //   63: ifne +140 -> 203
    //   66: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   69: ldc 154
    //   71: invokestatic 40	com/sec/factory/support/Support$Feature:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   74: invokevirtual 159	com/sec/factory/modules/ModuleCommon:setHWVer	(Ljava/lang/String;)Z
    //   77: pop
    //   78: aload_0
    //   79: aload_1
    //   80: iconst_0
    //   81: aaload
    //   82: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   85: invokevirtual 214	com/sec/factory/modules/ModuleCommon:getHWVer	()Ljava/lang/String;
    //   88: invokevirtual 221	com/sec/factory/aporiented/athandler/AtVersname:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   91: astore 76
    //   93: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   96: invokevirtual 224	com/sec/factory/modules/ModuleCommon:isConnectionModeNone	()Z
    //   99: ifne +3488 -> 3587
    //   102: aload_0
    //   103: getfield 48	com/sec/factory/aporiented/athandler/AtVersname:isMSM8960	Z
    //   106: ifne +85 -> 191
    //   109: aload_0
    //   110: getfield 56	com/sec/factory/aporiented/athandler/AtVersname:isMSM8260A	Z
    //   113: ifne +78 -> 191
    //   116: aload_0
    //   117: getfield 68	com/sec/factory/aporiented/athandler/AtVersname:isPegasus	Z
    //   120: ifne +71 -> 191
    //   123: aload_0
    //   124: getfield 60	com/sec/factory/aporiented/athandler/AtVersname:isPegasusPrime	Z
    //   127: ifne +64 -> 191
    //   130: aload_0
    //   131: getfield 64	com/sec/factory/aporiented/athandler/AtVersname:isPegaPrime	Z
    //   134: ifne +57 -> 191
    //   137: invokestatic 227	com/sec/factory/support/FtUtil:isFactoryAppAPO	()Z
    //   140: ifeq +10 -> 150
    //   143: aload_0
    //   144: getfield 94	com/sec/factory/aporiented/athandler/AtVersname:isCPName_IMC	Z
    //   147: ifne +44 -> 191
    //   150: aload_0
    //   151: getfield 72	com/sec/factory/aporiented/athandler/AtVersname:isU8420	Z
    //   154: ifne +37 -> 191
    //   157: aload_0
    //   158: getfield 84	com/sec/factory/aporiented/athandler/AtVersname:isMSM7227A	Z
    //   161: ifne +30 -> 191
    //   164: aload_0
    //   165: getfield 88	com/sec/factory/aporiented/athandler/AtVersname:isMSM8225	Z
    //   168: ifne +23 -> 191
    //   171: invokestatic 227	com/sec/factory/support/FtUtil:isFactoryAppAPO	()Z
    //   174: ifeq +3413 -> 3587
    //   177: aload_0
    //   178: getfield 80	com/sec/factory/aporiented/athandler/AtVersname:isOMAP4470	Z
    //   181: ifne +10 -> 191
    //   184: aload_0
    //   185: getfield 76	com/sec/factory/aporiented/athandler/AtVersname:isSC5C210ABB	Z
    //   188: ifeq +3399 -> 3587
    //   191: aload_0
    //   192: getfield 100	com/sec/factory/aporiented/athandler/AtVersname:isNon_LiveDemo	Z
    //   195: ifeq +3392 -> 3587
    //   198: aconst_null
    //   199: astore_3
    //   200: goto -186 -> 14
    //   203: aload_0
    //   204: aload_1
    //   205: iconst_0
    //   206: aaload
    //   207: aload 115
    //   209: invokevirtual 221	com/sec/factory/aporiented/athandler/AtVersname:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   212: astore 76
    //   214: goto -121 -> 93
    //   217: aload_0
    //   218: aload_1
    //   219: iconst_3
    //   220: anewarray 42	java/lang/String
    //   223: dup
    //   224: iconst_0
    //   225: ldc 205
    //   227: aastore
    //   228: dup
    //   229: iconst_1
    //   230: ldc 229
    //   232: aastore
    //   233: dup
    //   234: iconst_2
    //   235: ldc 207
    //   237: aastore
    //   238: invokevirtual 211	com/sec/factory/aporiented/athandler/AtVersname:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   241: ifeq +147 -> 388
    //   244: aload_0
    //   245: getfield 119	com/sec/factory/aporiented/athandler/AtVersname:device	Ljava/lang/String;
    //   248: ldc 231
    //   250: invokevirtual 235	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   253: ifeq +17 -> 270
    //   256: aload_0
    //   257: aload_1
    //   258: iconst_0
    //   259: aaload
    //   260: ldc 237
    //   262: invokevirtual 221	com/sec/factory/aporiented/athandler/AtVersname:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   265: astore 76
    //   267: goto +3320 -> 3587
    //   270: aload_0
    //   271: getfield 119	com/sec/factory/aporiented/athandler/AtVersname:device	Ljava/lang/String;
    //   274: ldc 239
    //   276: invokevirtual 235	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   279: ifne +27 -> 306
    //   282: aload_0
    //   283: getfield 119	com/sec/factory/aporiented/athandler/AtVersname:device	Ljava/lang/String;
    //   286: ldc 241
    //   288: invokevirtual 235	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   291: ifne +15 -> 306
    //   294: aload_0
    //   295: getfield 119	com/sec/factory/aporiented/athandler/AtVersname:device	Ljava/lang/String;
    //   298: ldc 243
    //   300: invokevirtual 235	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   303: ifeq +17 -> 320
    //   306: aload_0
    //   307: aload_1
    //   308: iconst_0
    //   309: aaload
    //   310: ldc 245
    //   312: invokevirtual 221	com/sec/factory/aporiented/athandler/AtVersname:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   315: astore 76
    //   317: goto +3270 -> 3587
    //   320: aload_0
    //   321: getfield 119	com/sec/factory/aporiented/athandler/AtVersname:device	Ljava/lang/String;
    //   324: ldc 247
    //   326: invokevirtual 235	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   329: ifne +27 -> 356
    //   332: aload_0
    //   333: getfield 119	com/sec/factory/aporiented/athandler/AtVersname:device	Ljava/lang/String;
    //   336: ldc 249
    //   338: invokevirtual 235	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   341: ifne +15 -> 356
    //   344: aload_0
    //   345: getfield 119	com/sec/factory/aporiented/athandler/AtVersname:device	Ljava/lang/String;
    //   348: ldc 251
    //   350: invokevirtual 235	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   353: ifeq +17 -> 370
    //   356: aload_0
    //   357: aload_1
    //   358: iconst_0
    //   359: aaload
    //   360: ldc 253
    //   362: invokevirtual 221	com/sec/factory/aporiented/athandler/AtVersname:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   365: astore 76
    //   367: goto +3220 -> 3587
    //   370: aload_0
    //   371: aload_1
    //   372: iconst_0
    //   373: aaload
    //   374: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   377: invokevirtual 256	com/sec/factory/modules/ModuleCommon:getModelName	()Ljava/lang/String;
    //   380: invokevirtual 221	com/sec/factory/aporiented/athandler/AtVersname:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   383: astore 76
    //   385: goto +3202 -> 3587
    //   388: aload_0
    //   389: aload_1
    //   390: iconst_3
    //   391: anewarray 42	java/lang/String
    //   394: dup
    //   395: iconst_0
    //   396: ldc 205
    //   398: aastore
    //   399: dup
    //   400: iconst_1
    //   401: ldc_w 258
    //   404: aastore
    //   405: dup
    //   406: iconst_2
    //   407: ldc 207
    //   409: aastore
    //   410: invokevirtual 211	com/sec/factory/aporiented/athandler/AtVersname:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   413: ifeq +21 -> 434
    //   416: aload_0
    //   417: aload_1
    //   418: iconst_0
    //   419: aaload
    //   420: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   423: invokevirtual 261	com/sec/factory/modules/ModuleCommon:getMainChipName	()Ljava/lang/String;
    //   426: invokevirtual 221	com/sec/factory/aporiented/athandler/AtVersname:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   429: astore 76
    //   431: goto +3156 -> 3587
    //   434: aload_0
    //   435: aload_1
    //   436: iconst_3
    //   437: anewarray 42	java/lang/String
    //   440: dup
    //   441: iconst_0
    //   442: ldc 205
    //   444: aastore
    //   445: dup
    //   446: iconst_1
    //   447: ldc_w 263
    //   450: aastore
    //   451: dup
    //   452: iconst_2
    //   453: ldc 207
    //   455: aastore
    //   456: invokevirtual 211	com/sec/factory/aporiented/athandler/AtVersname:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   459: ifeq +61 -> 520
    //   462: aload_0
    //   463: aload_1
    //   464: iconst_0
    //   465: aaload
    //   466: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   469: invokevirtual 266	com/sec/factory/modules/ModuleCommon:getContentsVer	()Ljava/lang/String;
    //   472: invokevirtual 221	com/sec/factory/aporiented/athandler/AtVersname:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   475: astore 76
    //   477: ldc_w 268
    //   480: ldc_w 270
    //   483: invokestatic 40	com/sec/factory/support/Support$Feature:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   486: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   489: ifne +18 -> 507
    //   492: ldc_w 276
    //   495: ldc_w 270
    //   498: invokestatic 40	com/sec/factory/support/Support$Feature:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   501: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   504: ifeq +3083 -> 3587
    //   507: aload 76
    //   509: bipush 20
    //   511: iconst_0
    //   512: invokestatic 280	com/sec/factory/support/FtUtil:addDummyValue	(Ljava/lang/String;IC)Ljava/lang/String;
    //   515: astore 76
    //   517: goto +3070 -> 3587
    //   520: aload_0
    //   521: aload_1
    //   522: iconst_3
    //   523: anewarray 42	java/lang/String
    //   526: dup
    //   527: iconst_0
    //   528: ldc 205
    //   530: aastore
    //   531: dup
    //   532: iconst_1
    //   533: ldc_w 282
    //   536: aastore
    //   537: dup
    //   538: iconst_2
    //   539: ldc 207
    //   541: aastore
    //   542: invokevirtual 211	com/sec/factory/aporiented/athandler/AtVersname:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   545: ifeq +61 -> 606
    //   548: aload_0
    //   549: aload_1
    //   550: iconst_0
    //   551: aaload
    //   552: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   555: invokevirtual 285	com/sec/factory/modules/ModuleCommon:getPDAVer	()Ljava/lang/String;
    //   558: invokevirtual 221	com/sec/factory/aporiented/athandler/AtVersname:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   561: astore 76
    //   563: ldc_w 268
    //   566: ldc_w 270
    //   569: invokestatic 40	com/sec/factory/support/Support$Feature:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   572: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   575: ifne +18 -> 593
    //   578: ldc_w 276
    //   581: ldc_w 270
    //   584: invokestatic 40	com/sec/factory/support/Support$Feature:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   587: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   590: ifeq +2997 -> 3587
    //   593: aload 76
    //   595: bipush 20
    //   597: iconst_0
    //   598: invokestatic 280	com/sec/factory/support/FtUtil:addDummyValue	(Ljava/lang/String;IC)Ljava/lang/String;
    //   601: astore 76
    //   603: goto +2984 -> 3587
    //   606: aload_0
    //   607: aload_1
    //   608: iconst_3
    //   609: anewarray 42	java/lang/String
    //   612: dup
    //   613: iconst_0
    //   614: ldc 205
    //   616: aastore
    //   617: dup
    //   618: iconst_1
    //   619: ldc_w 287
    //   622: aastore
    //   623: dup
    //   624: iconst_2
    //   625: ldc 207
    //   627: aastore
    //   628: invokevirtual 211	com/sec/factory/aporiented/athandler/AtVersname:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   631: ifeq +61 -> 692
    //   634: aload_0
    //   635: aload_1
    //   636: iconst_0
    //   637: aaload
    //   638: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   641: invokevirtual 290	com/sec/factory/modules/ModuleCommon:getCSCVer	()Ljava/lang/String;
    //   644: invokevirtual 221	com/sec/factory/aporiented/athandler/AtVersname:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   647: astore 76
    //   649: ldc_w 268
    //   652: ldc_w 270
    //   655: invokestatic 40	com/sec/factory/support/Support$Feature:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   658: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   661: ifne +18 -> 679
    //   664: ldc_w 276
    //   667: ldc_w 270
    //   670: invokestatic 40	com/sec/factory/support/Support$Feature:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   673: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   676: ifeq +2911 -> 3587
    //   679: aload 76
    //   681: bipush 20
    //   683: iconst_0
    //   684: invokestatic 280	com/sec/factory/support/FtUtil:addDummyValue	(Ljava/lang/String;IC)Ljava/lang/String;
    //   687: astore 76
    //   689: goto +2898 -> 3587
    //   692: aload_0
    //   693: aload_1
    //   694: iconst_3
    //   695: anewarray 42	java/lang/String
    //   698: dup
    //   699: iconst_0
    //   700: ldc 205
    //   702: aastore
    //   703: dup
    //   704: iconst_1
    //   705: ldc_w 292
    //   708: aastore
    //   709: dup
    //   710: iconst_2
    //   711: ldc 207
    //   713: aastore
    //   714: invokevirtual 211	com/sec/factory/aporiented/athandler/AtVersname:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   717: ifeq +21 -> 738
    //   720: aload_0
    //   721: aload_1
    //   722: iconst_0
    //   723: aaload
    //   724: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   727: invokevirtual 295	com/sec/factory/modules/ModuleCommon:getMainSWVer	()Ljava/lang/String;
    //   730: invokevirtual 221	com/sec/factory/aporiented/athandler/AtVersname:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   733: astore 76
    //   735: goto +2852 -> 3587
    //   738: aload_0
    //   739: aload_1
    //   740: iconst_2
    //   741: anewarray 42	java/lang/String
    //   744: dup
    //   745: iconst_0
    //   746: ldc 229
    //   748: aastore
    //   749: dup
    //   750: iconst_1
    //   751: ldc 205
    //   753: aastore
    //   754: invokevirtual 211	com/sec/factory/aporiented/athandler/AtVersname:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   757: ifeq +153 -> 910
    //   760: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   763: aload_1
    //   764: iconst_2
    //   765: aaload
    //   766: invokevirtual 159	com/sec/factory/modules/ModuleCommon:setHWVer	(Ljava/lang/String;)Z
    //   769: ifeq +129 -> 898
    //   772: aload_0
    //   773: aload_1
    //   774: iconst_0
    //   775: aaload
    //   776: invokevirtual 298	com/sec/factory/aporiented/athandler/AtVersname:responseOK	(Ljava/lang/String;)Ljava/lang/String;
    //   779: astore 76
    //   781: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   784: invokevirtual 224	com/sec/factory/modules/ModuleCommon:isConnectionModeNone	()Z
    //   787: ifne +2800 -> 3587
    //   790: aload_0
    //   791: getfield 48	com/sec/factory/aporiented/athandler/AtVersname:isMSM8960	Z
    //   794: ifne +92 -> 886
    //   797: aload_0
    //   798: getfield 52	com/sec/factory/aporiented/athandler/AtVersname:isMSM8930	Z
    //   801: ifne +85 -> 886
    //   804: aload_0
    //   805: getfield 56	com/sec/factory/aporiented/athandler/AtVersname:isMSM8260A	Z
    //   808: ifne +78 -> 886
    //   811: aload_0
    //   812: getfield 68	com/sec/factory/aporiented/athandler/AtVersname:isPegasus	Z
    //   815: ifne +71 -> 886
    //   818: aload_0
    //   819: getfield 60	com/sec/factory/aporiented/athandler/AtVersname:isPegasusPrime	Z
    //   822: ifne +64 -> 886
    //   825: aload_0
    //   826: getfield 64	com/sec/factory/aporiented/athandler/AtVersname:isPegaPrime	Z
    //   829: ifne +57 -> 886
    //   832: invokestatic 227	com/sec/factory/support/FtUtil:isFactoryAppAPO	()Z
    //   835: ifeq +10 -> 845
    //   838: aload_0
    //   839: getfield 94	com/sec/factory/aporiented/athandler/AtVersname:isCPName_IMC	Z
    //   842: ifne +44 -> 886
    //   845: aload_0
    //   846: getfield 72	com/sec/factory/aporiented/athandler/AtVersname:isU8420	Z
    //   849: ifne +37 -> 886
    //   852: aload_0
    //   853: getfield 84	com/sec/factory/aporiented/athandler/AtVersname:isMSM7227A	Z
    //   856: ifne +30 -> 886
    //   859: aload_0
    //   860: getfield 88	com/sec/factory/aporiented/athandler/AtVersname:isMSM8225	Z
    //   863: ifne +23 -> 886
    //   866: invokestatic 227	com/sec/factory/support/FtUtil:isFactoryAppAPO	()Z
    //   869: ifeq +2718 -> 3587
    //   872: aload_0
    //   873: getfield 80	com/sec/factory/aporiented/athandler/AtVersname:isOMAP4470	Z
    //   876: ifne +10 -> 886
    //   879: aload_0
    //   880: getfield 76	com/sec/factory/aporiented/athandler/AtVersname:isSC5C210ABB	Z
    //   883: ifeq +2704 -> 3587
    //   886: aload_0
    //   887: getfield 100	com/sec/factory/aporiented/athandler/AtVersname:isNon_LiveDemo	Z
    //   890: ifeq +2697 -> 3587
    //   893: aconst_null
    //   894: astore_3
    //   895: goto -881 -> 14
    //   898: aload_0
    //   899: aload_1
    //   900: iconst_0
    //   901: aaload
    //   902: invokevirtual 301	com/sec/factory/aporiented/athandler/AtVersname:responseNG	(Ljava/lang/String;)Ljava/lang/String;
    //   905: astore 76
    //   907: goto -126 -> 781
    //   910: aload_0
    //   911: aload_1
    //   912: iconst_3
    //   913: anewarray 42	java/lang/String
    //   916: dup
    //   917: iconst_0
    //   918: ldc_w 258
    //   921: aastore
    //   922: dup
    //   923: iconst_1
    //   924: ldc 207
    //   926: aastore
    //   927: dup
    //   928: iconst_2
    //   929: ldc 207
    //   931: aastore
    //   932: invokevirtual 211	com/sec/factory/aporiented/athandler/AtVersname:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   935: ifeq +21 -> 956
    //   938: aload_0
    //   939: aload_1
    //   940: iconst_0
    //   941: aaload
    //   942: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   945: invokevirtual 304	com/sec/factory/modules/ModuleCommon:getStandardVer	()Ljava/lang/String;
    //   948: invokevirtual 221	com/sec/factory/aporiented/athandler/AtVersname:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   951: astore 76
    //   953: goto +2634 -> 3587
    //   956: aload_0
    //   957: aload_1
    //   958: iconst_3
    //   959: anewarray 42	java/lang/String
    //   962: dup
    //   963: iconst_0
    //   964: ldc_w 258
    //   967: aastore
    //   968: dup
    //   969: iconst_1
    //   970: ldc 205
    //   972: aastore
    //   973: dup
    //   974: iconst_2
    //   975: ldc 207
    //   977: aastore
    //   978: invokevirtual 211	com/sec/factory/aporiented/athandler/AtVersname:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   981: ifeq +61 -> 1042
    //   984: aload_0
    //   985: aload_1
    //   986: iconst_0
    //   987: aaload
    //   988: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   991: invokevirtual 307	com/sec/factory/modules/ModuleCommon:getBootloaderVer	()Ljava/lang/String;
    //   994: invokevirtual 221	com/sec/factory/aporiented/athandler/AtVersname:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   997: astore 76
    //   999: ldc_w 268
    //   1002: ldc_w 270
    //   1005: invokestatic 40	com/sec/factory/support/Support$Feature:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   1008: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1011: ifne +18 -> 1029
    //   1014: ldc_w 276
    //   1017: ldc_w 270
    //   1020: invokestatic 40	com/sec/factory/support/Support$Feature:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   1023: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1026: ifeq +2561 -> 3587
    //   1029: aload 76
    //   1031: bipush 20
    //   1033: iconst_0
    //   1034: invokestatic 280	com/sec/factory/support/FtUtil:addDummyValue	(Ljava/lang/String;IC)Ljava/lang/String;
    //   1037: astore 76
    //   1039: goto +2548 -> 3587
    //   1042: aload_0
    //   1043: aload_1
    //   1044: iconst_3
    //   1045: anewarray 42	java/lang/String
    //   1048: dup
    //   1049: iconst_0
    //   1050: ldc_w 258
    //   1053: aastore
    //   1054: dup
    //   1055: iconst_1
    //   1056: ldc 229
    //   1058: aastore
    //   1059: dup
    //   1060: iconst_2
    //   1061: ldc 207
    //   1063: aastore
    //   1064: invokevirtual 211	com/sec/factory/aporiented/athandler/AtVersname:checkArgu	([Ljava/lang/String;[Ljava/lang/String;)Z
    //   1067: ifeq +2516 -> 3583
    //   1070: new 309	java/lang/StringBuffer
    //   1073: dup
    //   1074: bipush 30
    //   1076: invokespecial 312	java/lang/StringBuffer:<init>	(I)V
    //   1079: astore 4
    //   1081: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   1084: invokevirtual 256	com/sec/factory/modules/ModuleCommon:getModelName	()Ljava/lang/String;
    //   1087: astore 5
    //   1089: aload 5
    //   1091: ldc 104
    //   1093: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1096: ifne +14 -> 1110
    //   1099: aload 5
    //   1101: ldc_w 314
    //   1104: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1107: ifeq +1920 -> 3027
    //   1110: aload 4
    //   1112: bipush 78
    //   1114: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1117: pop
    //   1118: aload 4
    //   1120: bipush 44
    //   1122: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1125: pop
    //   1126: aload_0
    //   1127: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   1130: ldc_w 319
    //   1133: new 321	java/lang/StringBuilder
    //   1136: dup
    //   1137: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   1140: aload 4
    //   1142: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1145: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1148: ldc_w 331
    //   1151: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1154: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1157: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   1160: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   1163: invokevirtual 214	com/sec/factory/modules/ModuleCommon:getHWVer	()Ljava/lang/String;
    //   1166: astore 8
    //   1168: aload 8
    //   1170: ldc 104
    //   1172: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1175: ifne +14 -> 1189
    //   1178: aload 8
    //   1180: ldc_w 314
    //   1183: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1186: ifeq +1857 -> 3043
    //   1189: aload 4
    //   1191: bipush 78
    //   1193: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1196: pop
    //   1197: aload 4
    //   1199: bipush 44
    //   1201: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1204: pop
    //   1205: aload_0
    //   1206: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   1209: ldc_w 319
    //   1212: new 321	java/lang/StringBuilder
    //   1215: dup
    //   1216: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   1219: aload 4
    //   1221: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1224: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1227: ldc_w 334
    //   1230: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1233: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1236: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   1239: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   1242: invokevirtual 337	com/sec/factory/modules/ModuleCommon:getBootVer	()Ljava/lang/String;
    //   1245: astore 11
    //   1247: aload 11
    //   1249: ldc 104
    //   1251: invokevirtual 46	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   1254: ifne +14 -> 1268
    //   1257: aload 11
    //   1259: ldc_w 314
    //   1262: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1265: ifeq +1789 -> 3054
    //   1268: aload 4
    //   1270: bipush 78
    //   1272: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1275: pop
    //   1276: aload 4
    //   1278: bipush 44
    //   1280: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1283: pop
    //   1284: aload_0
    //   1285: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   1288: ldc_w 319
    //   1291: new 321	java/lang/StringBuilder
    //   1294: dup
    //   1295: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   1298: aload 4
    //   1300: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1303: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1306: ldc_w 339
    //   1309: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1312: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1315: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   1318: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   1321: invokevirtual 285	com/sec/factory/modules/ModuleCommon:getPDAVer	()Ljava/lang/String;
    //   1324: astore 14
    //   1326: aload 14
    //   1328: ldc 104
    //   1330: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1333: ifne +14 -> 1347
    //   1336: aload 14
    //   1338: ldc_w 314
    //   1341: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1344: ifeq +1721 -> 3065
    //   1347: aload 4
    //   1349: bipush 78
    //   1351: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1354: pop
    //   1355: aload 4
    //   1357: bipush 44
    //   1359: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1362: pop
    //   1363: aload_0
    //   1364: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   1367: ldc_w 319
    //   1370: new 321	java/lang/StringBuilder
    //   1373: dup
    //   1374: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   1377: aload 4
    //   1379: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1382: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1385: ldc_w 341
    //   1388: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1391: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1394: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   1397: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   1400: invokevirtual 344	com/sec/factory/modules/ModuleCommon:getPhoneVer	()Ljava/lang/String;
    //   1403: astore 17
    //   1405: aload 17
    //   1407: ldc 104
    //   1409: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1412: ifne +14 -> 1426
    //   1415: aload 17
    //   1417: ldc_w 314
    //   1420: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1423: ifeq +1653 -> 3076
    //   1426: aload 4
    //   1428: bipush 78
    //   1430: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1433: pop
    //   1434: ldc_w 346
    //   1437: invokestatic 349	com/sec/factory/support/Support$Feature:getBoolean	(Ljava/lang/String;)Z
    //   1440: ifeq +83 -> 1523
    //   1443: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   1446: invokevirtual 352	com/sec/factory/modules/ModuleCommon:getPhone2Ver	()Ljava/lang/String;
    //   1449: astore 106
    //   1451: aload 4
    //   1453: ldc_w 354
    //   1456: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1459: pop
    //   1460: aload 106
    //   1462: ldc 104
    //   1464: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1467: ifne +14 -> 1481
    //   1470: aload 106
    //   1472: ldc_w 314
    //   1475: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1478: ifeq +1609 -> 3087
    //   1481: aload 4
    //   1483: bipush 78
    //   1485: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1488: pop
    //   1489: aload_0
    //   1490: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   1493: ldc_w 319
    //   1496: new 321	java/lang/StringBuilder
    //   1499: dup
    //   1500: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   1503: aload 4
    //   1505: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1508: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1511: ldc_w 359
    //   1514: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1517: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1520: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   1523: aload 4
    //   1525: bipush 44
    //   1527: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1530: pop
    //   1531: aload_0
    //   1532: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   1535: ldc_w 319
    //   1538: new 321	java/lang/StringBuilder
    //   1541: dup
    //   1542: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   1545: aload 4
    //   1547: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1550: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1553: ldc_w 361
    //   1556: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1559: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1562: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   1565: aload 4
    //   1567: bipush 78
    //   1569: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1572: pop
    //   1573: aload 4
    //   1575: bipush 44
    //   1577: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1580: pop
    //   1581: aload_0
    //   1582: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   1585: ldc_w 319
    //   1588: new 321	java/lang/StringBuilder
    //   1591: dup
    //   1592: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   1595: aload 4
    //   1597: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1600: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1603: ldc_w 363
    //   1606: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1609: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1612: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   1615: ldc 104
    //   1617: ldc 104
    //   1619: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1622: ifne +14 -> 1636
    //   1625: ldc 104
    //   1627: ldc_w 314
    //   1630: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1633: ifeq +1465 -> 3098
    //   1636: aload 4
    //   1638: bipush 78
    //   1640: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1643: pop
    //   1644: aload 4
    //   1646: bipush 44
    //   1648: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1651: pop
    //   1652: aload_0
    //   1653: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   1656: ldc_w 319
    //   1659: new 321	java/lang/StringBuilder
    //   1662: dup
    //   1663: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   1666: aload 4
    //   1668: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1671: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1674: ldc_w 365
    //   1677: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1680: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1683: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   1686: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   1689: invokevirtual 290	com/sec/factory/modules/ModuleCommon:getCSCVer	()Ljava/lang/String;
    //   1692: astore 24
    //   1694: aload 24
    //   1696: ldc 104
    //   1698: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1701: ifne +14 -> 1715
    //   1704: aload 24
    //   1706: ldc_w 314
    //   1709: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1712: ifeq +1397 -> 3109
    //   1715: aload 4
    //   1717: bipush 78
    //   1719: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1722: pop
    //   1723: aload 4
    //   1725: bipush 44
    //   1727: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1730: pop
    //   1731: aload_0
    //   1732: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   1735: ldc_w 319
    //   1738: new 321	java/lang/StringBuilder
    //   1741: dup
    //   1742: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   1745: aload 4
    //   1747: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1750: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1753: ldc_w 367
    //   1756: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1759: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1762: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   1765: ldc_w 369
    //   1768: iconst_1
    //   1769: invokestatic 372	com/sec/factory/support/Support$Feature:getBoolean	(Ljava/lang/String;Z)Z
    //   1772: ifeq +8 -> 1780
    //   1775: aload_0
    //   1776: invokespecial 374	com/sec/factory/aporiented/athandler/AtVersname:startCameraforFwRead	()Z
    //   1779: pop
    //   1780: aconst_null
    //   1781: astore 27
    //   1783: aconst_null
    //   1784: astore 28
    //   1786: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   1789: invokevirtual 377	com/sec/factory/modules/ModuleCommon:readCameraRearFWver	()Ljava/lang/String;
    //   1792: ldc_w 379
    //   1795: invokevirtual 235	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   1798: istore 101
    //   1800: aconst_null
    //   1801: astore 27
    //   1803: iload 101
    //   1805: ifeq +1315 -> 3120
    //   1808: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   1811: invokevirtual 377	com/sec/factory/modules/ModuleCommon:readCameraRearFWver	()Ljava/lang/String;
    //   1814: ldc_w 379
    //   1817: invokevirtual 383	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   1820: astore 27
    //   1822: aload 27
    //   1824: iconst_0
    //   1825: aaload
    //   1826: astore 30
    //   1828: ldc_w 369
    //   1831: iconst_1
    //   1832: invokestatic 372	com/sec/factory/support/Support$Feature:getBoolean	(Ljava/lang/String;Z)Z
    //   1835: istore 31
    //   1837: iload 31
    //   1839: ifeq +8 -> 1847
    //   1842: aload_0
    //   1843: invokespecial 385	com/sec/factory/aporiented/athandler/AtVersname:stopCameraforFwRead	()Z
    //   1846: pop
    //   1847: aload 30
    //   1849: ifnull +14 -> 1863
    //   1852: aload 30
    //   1854: ldc_w 387
    //   1857: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1860: ifeq +1335 -> 3195
    //   1863: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   1866: invokevirtual 390	com/sec/factory/modules/ModuleCommon:readCameraRearType	()Ljava/lang/String;
    //   1869: astore 32
    //   1871: aload 32
    //   1873: ifnull +1311 -> 3184
    //   1876: aload 32
    //   1878: ldc_w 392
    //   1881: invokevirtual 383	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   1884: astore 33
    //   1886: aload_0
    //   1887: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   1890: ldc_w 319
    //   1893: new 321	java/lang/StringBuilder
    //   1896: dup
    //   1897: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   1900: ldc_w 394
    //   1903: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1906: aload 33
    //   1908: iconst_1
    //   1909: aaload
    //   1910: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1913: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1916: invokestatic 397	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   1919: aload 4
    //   1921: aload 33
    //   1923: iconst_1
    //   1924: aaload
    //   1925: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   1928: pop
    //   1929: aload 4
    //   1931: bipush 44
    //   1933: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   1936: pop
    //   1937: aload_0
    //   1938: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   1941: ldc_w 319
    //   1944: new 321	java/lang/StringBuilder
    //   1947: dup
    //   1948: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   1951: aload 4
    //   1953: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   1956: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1959: ldc_w 399
    //   1962: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1965: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1968: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   1971: ldc_w 401
    //   1974: iconst_1
    //   1975: invokestatic 372	com/sec/factory/support/Support$Feature:getBoolean	(Ljava/lang/String;Z)Z
    //   1978: ifeq +1239 -> 3217
    //   1981: aload 27
    //   1983: ifnull +1610 -> 3593
    //   1986: aload 27
    //   1988: iconst_1
    //   1989: aaload
    //   1990: astore 36
    //   1992: aload 36
    //   1994: ifnonnull +1212 -> 3206
    //   1997: aload 4
    //   1999: bipush 78
    //   2001: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2004: pop
    //   2005: aload 4
    //   2007: bipush 44
    //   2009: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2012: pop
    //   2013: aload_0
    //   2014: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   2017: ldc_w 319
    //   2020: new 321	java/lang/StringBuilder
    //   2023: dup
    //   2024: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   2027: aload 4
    //   2029: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   2032: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2035: ldc_w 403
    //   2038: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2041: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2044: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   2047: aconst_null
    //   2048: astore 39
    //   2050: aconst_null
    //   2051: astore 40
    //   2053: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   2056: invokevirtual 406	com/sec/factory/modules/ModuleCommon:readCameraFrontFWver	()Ljava/lang/String;
    //   2059: ldc_w 379
    //   2062: invokevirtual 235	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   2065: istore 92
    //   2067: aconst_null
    //   2068: astore 39
    //   2070: iload 92
    //   2072: ifeq +1164 -> 3236
    //   2075: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   2078: invokevirtual 406	com/sec/factory/modules/ModuleCommon:readCameraFrontFWver	()Ljava/lang/String;
    //   2081: ldc_w 379
    //   2084: invokevirtual 383	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   2087: astore 39
    //   2089: aload 39
    //   2091: iconst_0
    //   2092: aaload
    //   2093: astore 42
    //   2095: aload 42
    //   2097: ifnull +14 -> 2111
    //   2100: aload 42
    //   2102: ldc_w 387
    //   2105: invokevirtual 274	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   2108: ifeq +1185 -> 3293
    //   2111: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   2114: invokevirtual 409	com/sec/factory/modules/ModuleCommon:readCameraFrontType	()Ljava/lang/String;
    //   2117: astore 43
    //   2119: aload 43
    //   2121: ifnull +1161 -> 3282
    //   2124: aload 43
    //   2126: ldc_w 392
    //   2129: invokevirtual 383	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   2132: astore 44
    //   2134: aload_0
    //   2135: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   2138: ldc_w 319
    //   2141: new 321	java/lang/StringBuilder
    //   2144: dup
    //   2145: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   2148: ldc_w 411
    //   2151: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2154: aload 44
    //   2156: iconst_1
    //   2157: aaload
    //   2158: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2161: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2164: invokestatic 397	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   2167: aload 4
    //   2169: aload 44
    //   2171: iconst_1
    //   2172: aaload
    //   2173: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   2176: pop
    //   2177: aload 4
    //   2179: bipush 44
    //   2181: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2184: pop
    //   2185: aload_0
    //   2186: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   2189: ldc_w 319
    //   2192: new 321	java/lang/StringBuilder
    //   2195: dup
    //   2196: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   2199: aload 4
    //   2201: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   2204: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2207: ldc_w 413
    //   2210: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2213: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2216: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   2219: ldc_w 401
    //   2222: iconst_1
    //   2223: invokestatic 372	com/sec/factory/support/Support$Feature:getBoolean	(Ljava/lang/String;Z)Z
    //   2226: ifeq +1089 -> 3315
    //   2229: aload 39
    //   2231: ifnull +1369 -> 3600
    //   2234: aload 39
    //   2236: iconst_1
    //   2237: aaload
    //   2238: astore 47
    //   2240: aload 47
    //   2242: ifnonnull +1062 -> 3304
    //   2245: aload 4
    //   2247: bipush 78
    //   2249: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2252: pop
    //   2253: aload 4
    //   2255: bipush 44
    //   2257: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2260: pop
    //   2261: aload_0
    //   2262: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   2265: ldc_w 319
    //   2268: new 321	java/lang/StringBuilder
    //   2271: dup
    //   2272: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   2275: aload 4
    //   2277: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   2280: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2283: ldc_w 399
    //   2286: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2289: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2292: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   2295: ldc_w 415
    //   2298: invokestatic 420	com/sec/factory/support/Support$TestCase:getEnabled	(Ljava/lang/String;)Z
    //   2301: ifeq +1064 -> 3365
    //   2304: ldc_w 422
    //   2307: invokestatic 420	com/sec/factory/support/Support$TestCase:getEnabled	(Ljava/lang/String;)Z
    //   2310: ifeq +1043 -> 3353
    //   2313: getstatic 426	com/sec/factory/aporiented/athandler/AtVersname:mModuleTouchScreen	Lcom/sec/factory/modules/ModuleTouchScreen;
    //   2316: invokevirtual 431	com/sec/factory/modules/ModuleTouchScreen:isTSPSupport	()Z
    //   2319: ifeq +1015 -> 3334
    //   2322: getstatic 426	com/sec/factory/aporiented/athandler/AtVersname:mModuleTouchScreen	Lcom/sec/factory/modules/ModuleTouchScreen;
    //   2325: invokevirtual 434	com/sec/factory/modules/ModuleTouchScreen:getTSPFirmwareVersionIC	()Ljava/lang/String;
    //   2328: astore 50
    //   2330: aload 50
    //   2332: ifnonnull +1045 -> 3377
    //   2335: aload 4
    //   2337: bipush 78
    //   2339: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2342: pop
    //   2343: aload 4
    //   2345: bipush 44
    //   2347: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2350: pop
    //   2351: aload_0
    //   2352: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   2355: ldc_w 319
    //   2358: new 321	java/lang/StringBuilder
    //   2361: dup
    //   2362: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   2365: aload 4
    //   2367: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   2370: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2373: ldc_w 436
    //   2376: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2379: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2382: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   2385: ldc_w 415
    //   2388: invokestatic 420	com/sec/factory/support/Support$TestCase:getEnabled	(Ljava/lang/String;)Z
    //   2391: ifeq +1028 -> 3419
    //   2394: ldc_w 422
    //   2397: invokestatic 420	com/sec/factory/support/Support$TestCase:getEnabled	(Ljava/lang/String;)Z
    //   2400: ifeq +1007 -> 3407
    //   2403: getstatic 426	com/sec/factory/aporiented/athandler/AtVersname:mModuleTouchScreen	Lcom/sec/factory/modules/ModuleTouchScreen;
    //   2406: invokevirtual 431	com/sec/factory/modules/ModuleTouchScreen:isTSPSupport	()Z
    //   2409: ifeq +979 -> 3388
    //   2412: getstatic 426	com/sec/factory/aporiented/athandler/AtVersname:mModuleTouchScreen	Lcom/sec/factory/modules/ModuleTouchScreen;
    //   2415: invokevirtual 439	com/sec/factory/modules/ModuleTouchScreen:getTSPFirmwareVersionBinary	()Ljava/lang/String;
    //   2418: astore 53
    //   2420: aload 53
    //   2422: ifnonnull +1009 -> 3431
    //   2425: aload 4
    //   2427: bipush 78
    //   2429: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2432: pop
    //   2433: aload 4
    //   2435: bipush 44
    //   2437: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2440: pop
    //   2441: aload_0
    //   2442: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   2445: ldc_w 319
    //   2448: new 321	java/lang/StringBuilder
    //   2451: dup
    //   2452: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   2455: aload 4
    //   2457: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   2460: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2463: ldc_w 441
    //   2466: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2469: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2472: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   2475: ldc_w 443
    //   2478: invokestatic 349	com/sec/factory/support/Support$Feature:getBoolean	(Ljava/lang/String;)Z
    //   2481: ifeq +961 -> 3442
    //   2484: aload 4
    //   2486: getstatic 447	com/sec/factory/aporiented/athandler/AtVersname:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   2489: iconst_3
    //   2490: invokevirtual 453	com/sec/factory/modules/ModuleDevice:readModuleFirmwareVersion	(I)Ljava/lang/String;
    //   2493: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   2496: pop
    //   2497: aload 4
    //   2499: bipush 44
    //   2501: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2504: pop
    //   2505: aload 4
    //   2507: getstatic 447	com/sec/factory/aporiented/athandler/AtVersname:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   2510: iconst_3
    //   2511: invokevirtual 456	com/sec/factory/modules/ModuleDevice:readModuleBinVersion	(I)Ljava/lang/String;
    //   2514: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   2517: pop
    //   2518: aload 4
    //   2520: bipush 44
    //   2522: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2525: pop
    //   2526: aload_0
    //   2527: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   2530: ldc_w 319
    //   2533: new 321	java/lang/StringBuilder
    //   2536: dup
    //   2537: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   2540: aload 4
    //   2542: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   2545: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2548: ldc_w 458
    //   2551: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2554: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2557: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   2560: ldc_w 422
    //   2563: invokestatic 420	com/sec/factory/support/Support$TestCase:getEnabled	(Ljava/lang/String;)Z
    //   2566: ifeq +930 -> 3496
    //   2569: getstatic 426	com/sec/factory/aporiented/athandler/AtVersname:mModuleTouchScreen	Lcom/sec/factory/modules/ModuleTouchScreen;
    //   2572: invokevirtual 431	com/sec/factory/modules/ModuleTouchScreen:isTSPSupport	()Z
    //   2575: ifeq +902 -> 3477
    //   2578: getstatic 426	com/sec/factory/aporiented/athandler/AtVersname:mModuleTouchScreen	Lcom/sec/factory/modules/ModuleTouchScreen;
    //   2581: invokevirtual 434	com/sec/factory/modules/ModuleTouchScreen:getTSPFirmwareVersionIC	()Ljava/lang/String;
    //   2584: astore 60
    //   2586: aload 60
    //   2588: ifnonnull +920 -> 3508
    //   2591: aload 4
    //   2593: bipush 78
    //   2595: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2598: pop
    //   2599: aload 4
    //   2601: bipush 44
    //   2603: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2606: pop
    //   2607: aload_0
    //   2608: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   2611: ldc_w 319
    //   2614: new 321	java/lang/StringBuilder
    //   2617: dup
    //   2618: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   2621: aload 4
    //   2623: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   2626: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2629: ldc_w 460
    //   2632: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2635: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2638: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   2641: ldc_w 422
    //   2644: invokestatic 420	com/sec/factory/support/Support$TestCase:getEnabled	(Ljava/lang/String;)Z
    //   2647: ifeq +891 -> 3538
    //   2650: getstatic 426	com/sec/factory/aporiented/athandler/AtVersname:mModuleTouchScreen	Lcom/sec/factory/modules/ModuleTouchScreen;
    //   2653: invokevirtual 431	com/sec/factory/modules/ModuleTouchScreen:isTSPSupport	()Z
    //   2656: ifeq +863 -> 3519
    //   2659: getstatic 426	com/sec/factory/aporiented/athandler/AtVersname:mModuleTouchScreen	Lcom/sec/factory/modules/ModuleTouchScreen;
    //   2662: invokevirtual 439	com/sec/factory/modules/ModuleTouchScreen:getTSPFirmwareVersionBinary	()Ljava/lang/String;
    //   2665: astore 63
    //   2667: aload 63
    //   2669: ifnonnull +881 -> 3550
    //   2672: aload 4
    //   2674: bipush 78
    //   2676: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2679: pop
    //   2680: aload 4
    //   2682: bipush 44
    //   2684: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2687: pop
    //   2688: aload_0
    //   2689: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   2692: ldc_w 319
    //   2695: new 321	java/lang/StringBuilder
    //   2698: dup
    //   2699: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   2702: aload 4
    //   2704: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   2707: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2710: ldc_w 462
    //   2713: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2716: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2719: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   2722: aload 4
    //   2724: bipush 78
    //   2726: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2729: pop
    //   2730: aload 4
    //   2732: bipush 44
    //   2734: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2737: pop
    //   2738: aload_0
    //   2739: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   2742: ldc_w 319
    //   2745: new 321	java/lang/StringBuilder
    //   2748: dup
    //   2749: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   2752: aload 4
    //   2754: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   2757: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2760: ldc_w 464
    //   2763: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2766: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2769: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   2772: aload 4
    //   2774: bipush 78
    //   2776: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2779: pop
    //   2780: aload 4
    //   2782: bipush 44
    //   2784: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2787: pop
    //   2788: aload_0
    //   2789: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   2792: ldc_w 319
    //   2795: new 321	java/lang/StringBuilder
    //   2798: dup
    //   2799: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   2802: aload 4
    //   2804: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   2807: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2810: ldc_w 466
    //   2813: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2816: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2819: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   2822: ldc_w 468
    //   2825: invokestatic 349	com/sec/factory/support/Support$Feature:getBoolean	(Ljava/lang/String;)Z
    //   2828: ifeq +733 -> 3561
    //   2831: aload 4
    //   2833: getstatic 447	com/sec/factory/aporiented/athandler/AtVersname:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   2836: iconst_4
    //   2837: invokevirtual 453	com/sec/factory/modules/ModuleDevice:readModuleFirmwareVersion	(I)Ljava/lang/String;
    //   2840: ldc_w 470
    //   2843: invokevirtual 383	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   2846: iconst_0
    //   2847: aaload
    //   2848: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   2851: pop
    //   2852: aload 4
    //   2854: bipush 44
    //   2856: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2859: pop
    //   2860: aload_0
    //   2861: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   2864: ldc_w 319
    //   2867: new 321	java/lang/StringBuilder
    //   2870: dup
    //   2871: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   2874: aload 4
    //   2876: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   2879: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2882: ldc_w 472
    //   2885: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2888: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2891: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   2894: ldc_w 468
    //   2897: invokestatic 349	com/sec/factory/support/Support$Feature:getBoolean	(Ljava/lang/String;)Z
    //   2900: ifeq +672 -> 3572
    //   2903: aload 4
    //   2905: getstatic 447	com/sec/factory/aporiented/athandler/AtVersname:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   2908: iconst_4
    //   2909: invokevirtual 453	com/sec/factory/modules/ModuleDevice:readModuleFirmwareVersion	(I)Ljava/lang/String;
    //   2912: ldc_w 470
    //   2915: invokevirtual 383	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   2918: iconst_1
    //   2919: aaload
    //   2920: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   2923: pop
    //   2924: aload 4
    //   2926: bipush 44
    //   2928: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   2931: pop
    //   2932: aload_0
    //   2933: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   2936: ldc_w 319
    //   2939: new 321	java/lang/StringBuilder
    //   2942: dup
    //   2943: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   2946: aload 4
    //   2948: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   2951: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2954: ldc_w 474
    //   2957: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2960: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2963: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   2966: aload 4
    //   2968: ldc_w 476
    //   2971: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   2974: pop
    //   2975: aload 4
    //   2977: invokevirtual 326	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   2980: astore 75
    //   2982: aload_0
    //   2983: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   2986: ldc_w 319
    //   2989: new 321	java/lang/StringBuilder
    //   2992: dup
    //   2993: invokespecial 323	java/lang/StringBuilder:<init>	()V
    //   2996: ldc_w 478
    //   2999: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3002: aload 75
    //   3004: invokevirtual 329	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3007: invokevirtual 332	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   3010: invokestatic 397	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   3013: aload_0
    //   3014: aload_1
    //   3015: iconst_0
    //   3016: aaload
    //   3017: aload 75
    //   3019: invokevirtual 221	com/sec/factory/aporiented/athandler/AtVersname:responseString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   3022: astore 76
    //   3024: goto +563 -> 3587
    //   3027: aload 4
    //   3029: aload 5
    //   3031: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3034: pop
    //   3035: goto -1917 -> 1118
    //   3038: astore_2
    //   3039: aload_0
    //   3040: monitorexit
    //   3041: aload_2
    //   3042: athrow
    //   3043: aload 4
    //   3045: aload 8
    //   3047: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3050: pop
    //   3051: goto -1854 -> 1197
    //   3054: aload 4
    //   3056: aload 11
    //   3058: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3061: pop
    //   3062: goto -1786 -> 1276
    //   3065: aload 4
    //   3067: aload 14
    //   3069: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3072: pop
    //   3073: goto -1718 -> 1355
    //   3076: aload 4
    //   3078: aload 17
    //   3080: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3083: pop
    //   3084: goto -1650 -> 1434
    //   3087: aload 4
    //   3089: aload 106
    //   3091: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3094: pop
    //   3095: goto -1606 -> 1489
    //   3098: aload 4
    //   3100: ldc 104
    //   3102: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3105: pop
    //   3106: goto -1462 -> 1644
    //   3109: aload 4
    //   3111: aload 24
    //   3113: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3116: pop
    //   3117: goto -1394 -> 1723
    //   3120: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   3123: invokevirtual 377	com/sec/factory/modules/ModuleCommon:readCameraRearFWver	()Ljava/lang/String;
    //   3126: astore 102
    //   3128: aload 102
    //   3130: astore 28
    //   3132: aload 28
    //   3134: astore 30
    //   3136: aconst_null
    //   3137: astore 27
    //   3139: goto -1311 -> 1828
    //   3142: astore 29
    //   3144: aload_0
    //   3145: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   3148: ldc_w 319
    //   3151: ldc_w 480
    //   3154: invokestatic 482	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   3157: aconst_null
    //   3158: astore 30
    //   3160: aconst_null
    //   3161: astore 28
    //   3163: goto -1335 -> 1828
    //   3166: astore 99
    //   3168: aload_0
    //   3169: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   3172: ldc_w 319
    //   3175: ldc_w 484
    //   3178: invokestatic 482	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   3181: goto -1334 -> 1847
    //   3184: aload 4
    //   3186: bipush 78
    //   3188: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   3191: pop
    //   3192: goto -1263 -> 1929
    //   3195: aload 4
    //   3197: aload 30
    //   3199: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3202: pop
    //   3203: goto -1274 -> 1929
    //   3206: aload 4
    //   3208: aload 36
    //   3210: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3213: pop
    //   3214: goto -1209 -> 2005
    //   3217: aload 4
    //   3219: bipush 78
    //   3221: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   3224: pop
    //   3225: aload 4
    //   3227: bipush 44
    //   3229: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   3232: pop
    //   3233: goto -1220 -> 2013
    //   3236: getstatic 152	com/sec/factory/aporiented/athandler/AtVersname:mModuleCommon	Lcom/sec/factory/modules/ModuleCommon;
    //   3239: invokevirtual 406	com/sec/factory/modules/ModuleCommon:readCameraFrontFWver	()Ljava/lang/String;
    //   3242: astore 93
    //   3244: aload 93
    //   3246: astore 40
    //   3248: aload 40
    //   3250: astore 42
    //   3252: aconst_null
    //   3253: astore 39
    //   3255: goto -1160 -> 2095
    //   3258: astore 41
    //   3260: aload_0
    //   3261: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   3264: ldc_w 319
    //   3267: ldc_w 486
    //   3270: invokestatic 482	com/sec/factory/support/FtUtil:log_e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   3273: aconst_null
    //   3274: astore 42
    //   3276: aconst_null
    //   3277: astore 40
    //   3279: goto -1184 -> 2095
    //   3282: aload 4
    //   3284: bipush 78
    //   3286: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   3289: pop
    //   3290: goto -1113 -> 2177
    //   3293: aload 4
    //   3295: aload 42
    //   3297: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3300: pop
    //   3301: goto -1124 -> 2177
    //   3304: aload 4
    //   3306: aload 47
    //   3308: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3311: pop
    //   3312: goto -1059 -> 2253
    //   3315: aload 4
    //   3317: bipush 78
    //   3319: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   3322: pop
    //   3323: aload 4
    //   3325: bipush 44
    //   3327: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   3330: pop
    //   3331: goto -1070 -> 2261
    //   3334: aload_0
    //   3335: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   3338: ldc_w 319
    //   3341: ldc_w 488
    //   3344: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   3347: aconst_null
    //   3348: astore 50
    //   3350: goto -1020 -> 2330
    //   3353: getstatic 447	com/sec/factory/aporiented/athandler/AtVersname:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   3356: iconst_1
    //   3357: invokevirtual 453	com/sec/factory/modules/ModuleDevice:readModuleFirmwareVersion	(I)Ljava/lang/String;
    //   3360: astore 50
    //   3362: goto -1032 -> 2330
    //   3365: getstatic 447	com/sec/factory/aporiented/athandler/AtVersname:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   3368: iconst_2
    //   3369: invokevirtual 453	com/sec/factory/modules/ModuleDevice:readModuleFirmwareVersion	(I)Ljava/lang/String;
    //   3372: astore 50
    //   3374: goto -1044 -> 2330
    //   3377: aload 4
    //   3379: aload 50
    //   3381: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3384: pop
    //   3385: goto -1042 -> 2343
    //   3388: aload_0
    //   3389: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   3392: ldc_w 319
    //   3395: ldc_w 490
    //   3398: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   3401: aconst_null
    //   3402: astore 53
    //   3404: goto -984 -> 2420
    //   3407: getstatic 447	com/sec/factory/aporiented/athandler/AtVersname:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   3410: iconst_1
    //   3411: invokevirtual 456	com/sec/factory/modules/ModuleDevice:readModuleBinVersion	(I)Ljava/lang/String;
    //   3414: astore 53
    //   3416: goto -996 -> 2420
    //   3419: getstatic 447	com/sec/factory/aporiented/athandler/AtVersname:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   3422: iconst_2
    //   3423: invokevirtual 456	com/sec/factory/modules/ModuleDevice:readModuleBinVersion	(I)Ljava/lang/String;
    //   3426: astore 53
    //   3428: goto -1008 -> 2420
    //   3431: aload 4
    //   3433: aload 53
    //   3435: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3438: pop
    //   3439: goto -1006 -> 2433
    //   3442: aload 4
    //   3444: bipush 78
    //   3446: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   3449: pop
    //   3450: aload 4
    //   3452: bipush 44
    //   3454: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   3457: pop
    //   3458: aload 4
    //   3460: bipush 78
    //   3462: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   3465: pop
    //   3466: aload 4
    //   3468: bipush 44
    //   3470: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   3473: pop
    //   3474: goto -948 -> 2526
    //   3477: aload_0
    //   3478: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   3481: ldc_w 319
    //   3484: ldc_w 488
    //   3487: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   3490: aconst_null
    //   3491: astore 60
    //   3493: goto -907 -> 2586
    //   3496: getstatic 447	com/sec/factory/aporiented/athandler/AtVersname:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   3499: iconst_1
    //   3500: invokevirtual 453	com/sec/factory/modules/ModuleDevice:readModuleFirmwareVersion	(I)Ljava/lang/String;
    //   3503: astore 60
    //   3505: goto -919 -> 2586
    //   3508: aload 4
    //   3510: aload 60
    //   3512: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3515: pop
    //   3516: goto -917 -> 2599
    //   3519: aload_0
    //   3520: getfield 129	com/sec/factory/aporiented/athandler/AtVersname:CLASS_NAME	Ljava/lang/String;
    //   3523: ldc_w 319
    //   3526: ldc_w 490
    //   3529: invokestatic 171	com/sec/factory/support/FtUtil:log_d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   3532: aconst_null
    //   3533: astore 63
    //   3535: goto -868 -> 2667
    //   3538: getstatic 447	com/sec/factory/aporiented/athandler/AtVersname:mModuleDevice	Lcom/sec/factory/modules/ModuleDevice;
    //   3541: iconst_1
    //   3542: invokevirtual 456	com/sec/factory/modules/ModuleDevice:readModuleBinVersion	(I)Ljava/lang/String;
    //   3545: astore 63
    //   3547: goto -880 -> 2667
    //   3550: aload 4
    //   3552: aload 63
    //   3554: invokevirtual 357	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   3557: pop
    //   3558: goto -878 -> 2680
    //   3561: aload 4
    //   3563: bipush 78
    //   3565: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   3568: pop
    //   3569: goto -717 -> 2852
    //   3572: aload 4
    //   3574: bipush 78
    //   3576: invokevirtual 318	java/lang/StringBuffer:append	(C)Ljava/lang/StringBuffer;
    //   3579: pop
    //   3580: goto -656 -> 2924
    //   3583: ldc 203
    //   3585: astore 76
    //   3587: aload 76
    //   3589: astore_3
    //   3590: goto -3576 -> 14
    //   3593: aload 28
    //   3595: astore 36
    //   3597: goto -1605 -> 1992
    //   3600: aload 40
    //   3602: astore 47
    //   3604: goto -1364 -> 2240
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	3607	0	this	AtVersname
    //   0	3607	1	paramArrayOfString	String[]
    //   3038	4	2	localObject1	Object
    //   13	3577	3	localObject2	Object
    //   1079	2494	4	localStringBuffer	StringBuffer
    //   1087	1943	5	str1	String
    //   1166	1880	8	str2	String
    //   1245	1812	11	str3	String
    //   1324	1744	14	str4	String
    //   1403	1676	17	str5	String
    //   1692	1420	24	str6	String
    //   1781	1357	27	arrayOfString1	String[]
    //   1784	1810	28	localObject3	Object
    //   3142	1	29	localException1	Exception
    //   1826	1372	30	localObject4	Object
    //   1835	3	31	bool1	boolean
    //   1869	8	32	str7	String
    //   1884	38	33	arrayOfString2	String[]
    //   1990	1606	36	localObject5	Object
    //   2048	1206	39	arrayOfString3	String[]
    //   2051	1550	40	localObject6	Object
    //   3258	1	41	localException2	Exception
    //   2093	1203	42	localObject7	Object
    //   2117	8	43	str8	String
    //   2132	38	44	arrayOfString4	String[]
    //   2238	1365	47	localObject8	Object
    //   2328	1052	50	str9	String
    //   2418	1016	53	str10	String
    //   2584	927	60	str11	String
    //   2665	888	63	str12	String
    //   2980	38	75	str13	String
    //   91	3497	76	str14	String
    //   2065	6	92	bool2	boolean
    //   3242	3	93	str15	String
    //   3166	1	99	localException3	Exception
    //   1798	6	101	bool3	boolean
    //   3126	3	102	str16	String
    //   1449	1641	106	str17	String
    //   51	157	115	str18	String
    // Exception table:
    //   from	to	target	type
    //   2	11	3038	finally
    //   18	53	3038	finally
    //   58	66	3038	finally
    //   66	93	3038	finally
    //   93	150	3038	finally
    //   150	191	3038	finally
    //   191	198	3038	finally
    //   203	214	3038	finally
    //   217	267	3038	finally
    //   270	306	3038	finally
    //   306	317	3038	finally
    //   320	356	3038	finally
    //   356	367	3038	finally
    //   370	385	3038	finally
    //   388	431	3038	finally
    //   434	507	3038	finally
    //   507	517	3038	finally
    //   520	593	3038	finally
    //   593	603	3038	finally
    //   606	679	3038	finally
    //   679	689	3038	finally
    //   692	735	3038	finally
    //   738	781	3038	finally
    //   781	845	3038	finally
    //   845	886	3038	finally
    //   886	893	3038	finally
    //   898	907	3038	finally
    //   910	953	3038	finally
    //   956	1029	3038	finally
    //   1029	1039	3038	finally
    //   1042	1110	3038	finally
    //   1110	1118	3038	finally
    //   1118	1189	3038	finally
    //   1189	1197	3038	finally
    //   1197	1268	3038	finally
    //   1268	1276	3038	finally
    //   1276	1347	3038	finally
    //   1347	1355	3038	finally
    //   1355	1426	3038	finally
    //   1426	1434	3038	finally
    //   1434	1481	3038	finally
    //   1481	1489	3038	finally
    //   1489	1523	3038	finally
    //   1523	1636	3038	finally
    //   1636	1644	3038	finally
    //   1644	1715	3038	finally
    //   1715	1723	3038	finally
    //   1723	1780	3038	finally
    //   1786	1800	3038	finally
    //   1808	1828	3038	finally
    //   1828	1837	3038	finally
    //   1842	1847	3038	finally
    //   1852	1863	3038	finally
    //   1863	1871	3038	finally
    //   1876	1929	3038	finally
    //   1929	1981	3038	finally
    //   1986	1992	3038	finally
    //   1997	2005	3038	finally
    //   2005	2013	3038	finally
    //   2013	2047	3038	finally
    //   2053	2067	3038	finally
    //   2075	2095	3038	finally
    //   2100	2111	3038	finally
    //   2111	2119	3038	finally
    //   2124	2177	3038	finally
    //   2177	2229	3038	finally
    //   2234	2240	3038	finally
    //   2245	2253	3038	finally
    //   2253	2261	3038	finally
    //   2261	2330	3038	finally
    //   2335	2343	3038	finally
    //   2343	2420	3038	finally
    //   2425	2433	3038	finally
    //   2433	2526	3038	finally
    //   2526	2586	3038	finally
    //   2591	2599	3038	finally
    //   2599	2667	3038	finally
    //   2672	2680	3038	finally
    //   2680	2852	3038	finally
    //   2852	2924	3038	finally
    //   2924	3024	3038	finally
    //   3027	3035	3038	finally
    //   3043	3051	3038	finally
    //   3054	3062	3038	finally
    //   3065	3073	3038	finally
    //   3076	3084	3038	finally
    //   3087	3095	3038	finally
    //   3098	3106	3038	finally
    //   3109	3117	3038	finally
    //   3120	3128	3038	finally
    //   3144	3157	3038	finally
    //   3168	3181	3038	finally
    //   3184	3192	3038	finally
    //   3195	3203	3038	finally
    //   3206	3214	3038	finally
    //   3217	3233	3038	finally
    //   3236	3244	3038	finally
    //   3260	3273	3038	finally
    //   3282	3290	3038	finally
    //   3293	3301	3038	finally
    //   3304	3312	3038	finally
    //   3315	3331	3038	finally
    //   3334	3347	3038	finally
    //   3353	3362	3038	finally
    //   3365	3374	3038	finally
    //   3377	3385	3038	finally
    //   3388	3401	3038	finally
    //   3407	3416	3038	finally
    //   3419	3428	3038	finally
    //   3431	3439	3038	finally
    //   3442	3474	3038	finally
    //   3477	3490	3038	finally
    //   3496	3505	3038	finally
    //   3508	3516	3038	finally
    //   3519	3532	3038	finally
    //   3538	3547	3038	finally
    //   3550	3558	3038	finally
    //   3561	3569	3038	finally
    //   3572	3580	3038	finally
    //   1786	1800	3142	java/lang/Exception
    //   1808	1828	3142	java/lang/Exception
    //   3120	3128	3142	java/lang/Exception
    //   1842	1847	3166	java/lang/Exception
    //   2053	2067	3258	java/lang/Exception
    //   2075	2095	3258	java/lang/Exception
    //   3236	3244	3258	java/lang/Exception
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtVersname
 * JD-Core Version:    0.7.1
 */