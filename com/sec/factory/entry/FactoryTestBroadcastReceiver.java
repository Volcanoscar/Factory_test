package com.sec.factory.entry;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import com.sec.factory.aporiented.FtClient;
import com.sec.factory.app.factorytest.FactoryTestMain;
import com.sec.factory.app.factorytest.FactoryTestSlaveService;
import com.sec.factory.app.factorytest.FactoryTestSupportService;
import com.sec.factory.app.systeminfo.SystemInfoService;
import com.sec.factory.app.ui.UIFactoryHistory;
import com.sec.factory.app.ui.UIFactoryTestNVView;
import com.sec.factory.modules.ModuleCommon;
import com.sec.factory.modules.ModulePower;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;
import com.sec.factory.support.XMLDataStorage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FactoryTestBroadcastReceiver
  extends BroadcastReceiver
{
  private static boolean mAlreadyReceivedCSC_COMPLETED = false;
  private static boolean mAlreadyReceivedMEIDA_SCANNING = false;
  private static boolean mIsFirstMediaScan = true;
  private static boolean mIsSendingBootMsg = false;
  private static boolean mPrepare15Test = false;
  private static final String mSalesCode = SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase();
  private static boolean misRunningFtClient = false;
  private static final String model = SystemProperties.get("ro.product.model", "Unknown").trim().toLowerCase();
  private final String ACTION_GET_FTA = "android.intent.action.GET_FTA";
  private final String ACTION_REQUEST_RECONNECT = "com.sec.atd.request_reconnect";
  private final String ACTION_SECPHONE_READY = "android.intent.action.SECPHONE_READY";
  private final String ACTION_SECRET_CODE = "android.provider.Telephony.SECRET_CODE";
  private final String ACTION_SIM_STATE_CHANGED = "android.intent.action.SIM_STATE_CHANGED";
  private final String REQUEST_FACTORY_RESET = "com.sec.factory.entry.REQUEST_FACTORY_RESET";
  private Context mContext;
  private Handler mHandler = new Handler();
  
  private Boolean SecondAckFileCheck()
  {
    File localFile = new File("/efs/FactoryApp/reset_flag");
    FtUtil.log_i("FactoryTestBroadcastReceiver", "SecondAckFileCheck", "SecondAckFileCheck() - in");
    if (localFile.exists() == true)
    {
      FtUtil.log_i("FactoryTestBroadcastReceiver", "SecondAckFileCheck", "SecondAckFileCheck() - true");
      localFile.delete();
      return Boolean.valueOf(true);
    }
    FtUtil.log_i("FactoryTestBroadcastReceiver", "SecondAckFileCheck", "SecondAckFileCheck() - false");
    return Boolean.valueOf(false);
  }
  
  private void createFileNotiCheck()
  {
    File localFile = new File("/efs/FactoryApp/reset_flag");
    try
    {
      if (!localFile.exists())
      {
        localFile.createNewFile();
        localFile.setReadable(true, false);
        localFile.setExecutable(true, true);
      }
      FtUtil.log_i("FactoryTestBroadcastReceiver", "createFileNotiCheck", "createFileNotiCheck - SecondAckFile");
      return;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  private void launchFactoryHistoryView()
  {
    if (XMLDataStorage.instance().wasCompletedParsing())
    {
      FtUtil.log_d("FactoryTestBroadcastReceiver", "launchFactoryHistoryView", "Launch UIFactoryTestNVView");
      Intent localIntent = new Intent(this.mContext, UIFactoryTestNVView.class);
      localIntent.setFlags(268435456);
      this.mContext.startActivity(localIntent);
      return;
    }
    FtUtil.log_d("FactoryTestBroadcastReceiver", "launchFactoryHistoryView", "XML data parsing was not completed.");
    Toast.makeText(this.mContext, "XML data parsing was not completed.", 0);
  }
  
  private void launchFactoryTest()
  {
    if (XMLDataStorage.instance().wasCompletedParsing())
    {
      FtUtil.log_d("FactoryTestBroadcastReceiver", "launchFactoryTest", "Launch FactoryTestMain");
      Intent localIntent = new Intent("android.intent.action.MAIN");
      localIntent.setClass(this.mContext, FactoryTestMain.class);
      localIntent.setFlags(872415232);
      localIntent.addFlags(32);
      this.mContext.startActivity(localIntent);
      return;
    }
    FtUtil.log_d("FactoryTestBroadcastReceiver", "launchFactoryTest", "XML data parsing was not completed.");
    Toast.makeText(this.mContext, "XML data parsing was not completed.", 0);
  }
  
  private void launchMainHistoryView()
  {
    if (XMLDataStorage.instance().wasCompletedParsing())
    {
      FtUtil.log_d("FactoryTestBroadcastReceiver", "launchMainHistoryView", "Launch UIFactoryHistory");
      Intent localIntent = new Intent(this.mContext, UIFactoryHistory.class);
      localIntent.setFlags(268435456);
      this.mContext.startActivity(localIntent);
      return;
    }
    FtUtil.log_d("FactoryTestBroadcastReceiver", "launchMainHistoryView", "XML data parsing was not completed.");
    Toast.makeText(this.mContext, "XML data parsing was not completed.", 0);
  }
  
  private void launchSystemInfo()
  {
    FtUtil.log_i("FactoryTestBroadcastReceiver", "launchSystemInfo", "Start SystemInfoService");
    this.mContext.startService(new Intent(this.mContext, SystemInfoService.class));
  }
  
  private void sendDisableKeyguardIntent()
  {
    FtUtil.log_d("FactoryTestBroadcastReceiver", "sendDisableKeyguardIntent", "com.android.samsungtest.DISABLE_KEYGUARD_FACTORY");
    Intent localIntent = new Intent("com.android.samsungtest.DISABLE_KEYGUARD_FACTORY");
    this.mContext.sendBroadcast(localIntent);
  }
  
  private void sendFactoryTestModeIntent()
  {
    FtUtil.log_i("FactoryTestBroadcastReceiver", "sendFactoryTestModeIntent", "android.intent.action.SET_FACTORY_SIM_MODE");
    Intent localIntent = new Intent("android.intent.action.SET_FACTORY_SIM_MODE");
    localIntent.addFlags(32);
    this.mContext.sendBroadcast(localIntent);
  }
  
  private void sendRTCAlarmOffIntent()
  {
    FtUtil.log_d("FactoryTestBroadcastReceiver", "sendRTCAlarmOffIntent", "android.intent.action.START_FACTORY_TEST");
    Intent localIntent = new Intent("android.intent.action.START_FACTORY_TEST");
    this.mContext.sendBroadcast(localIntent);
  }
  
  private void startDummyFtClientForBootCompleted()
  {
    FtUtil.log_i("FactoryTestBroadcastReceiver", "startDummyFtClientForBootCompleted", "start DummyFtClient service for APO");
    Intent localIntent = new Intent();
    localIntent.setClassName("com.sec.factory", "com.sec.factory.aporiented.DummyFtClient");
    localIntent.addFlags(32);
    this.mContext.startService(localIntent);
  }
  
  private void startFactoryTestClientServiceAPO()
  {
    FtUtil.log_i("FactoryTestBroadcastReceiver", "startFactoryTestClientServiceAPO", "start FactoryTestClient service for APO");
    misRunningFtClient = true;
    Intent localIntent = new Intent();
    localIntent.setClassName("com.sec.factory", "com.sec.factory.aporiented.FtClient");
    localIntent.addFlags(32);
    this.mContext.startService(localIntent);
  }
  
  private void startFactoryTestSlaveService(int paramInt)
  {
    FtUtil.log_i("FactoryTestBroadcastReceiver", "startFactoryTestSlaveService", "Start FactoryTestSlaveService");
    Intent localIntent = new Intent(this.mContext, FactoryTestSlaveService.class);
    localIntent.setFlags(268435456);
    localIntent.addFlags(32);
    if (paramInt == 1) {
      localIntent.putExtra("DUALMODE", true);
    }
    this.mContext.startService(localIntent);
  }
  
  private void startFactoryTestSupportService()
  {
    FtUtil.log_i("FactoryTestBroadcastReceiver", "startFactoryTestSupportService", "Start FactoryTestSupportService");
    Intent localIntent = new Intent(this.mContext, FactoryTestSupportService.class);
    localIntent.setFlags(268435456);
    localIntent.addFlags(32);
    this.mContext.startService(localIntent);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    this.mContext = paramContext;
    String str1 = paramIntent.getAction();
    FtUtil.log_d("FactoryTestBroadcastReceiver", "onReceive", "onReceive action=" + str1);
    if (str1.equals("android.intent.action.BOOT_COMPLETED"))
    {
      if (!XMLDataStorage.instance().parseXML(paramContext)) {
        FtUtil.log_d("FactoryTestBroadcastReceiver", "onReceive", "ACTION_BOOT_COMPLETED => XML data parsing was failed.");
      }
      ModuleCommon.instance(paramContext).disable15Test();
      if ((ModuleCommon.instance(paramContext).getFtClientEnableState()) || (ModuleCommon.instance(paramContext).connectedJIG()))
      {
        if (Support.Feature.getBoolean("SUPPORT_BOOST_MEDIASCAN"))
        {
          FtUtil.log_i("FactoryTestBroadcastReceiver", "wake lock", "SUPPORT_BOOST_MEDIASCAN");
          ModulePower.instance(paramContext).doMediaScanWakeLock(true);
        }
        if (FtUtil.isFactoryAppAPO()) {
          startFactoryTestClientServiceAPO();
        }
      }
    }
    label296:
    label884:
    do
    {
      do
      {
        String str4;
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    do
                    {
                      break label296;
                      break label296;
                      break label296;
                      break label296;
                      break label296;
                      break label296;
                      break label296;
                      break label296;
                      break label296;
                      break label296;
                      break label296;
                      if ((ModuleCommon.instance(paramContext).isFactorySim()) || (ModuleCommon.instance(paramContext).isFactoryMode()))
                      {
                        FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "ACTION_BOOT_COMPLETED : isFactorySim && isFactoryMode == true");
                        ModuleCommon.instance(paramContext).enableFtClient();
                        sendRTCAlarmOffIntent();
                        startFactoryTestSupportService();
                        if ("CTC".equals(SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase()))
                        {
                          FtUtil.log_d("FactoryTestBroadcastReceiver", "onReceive", "ACTION_BOOT_COMPLETED => Factory mode: CDMA model timezone restored");
                          ((AlarmManager)this.mContext.getSystemService("alarm")).setTimeZone("Asia/Shanghai");
                        }
                        if ("TMB".equals(SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase()))
                        {
                          ModuleCommon.instance(paramContext).disableApplication("com.tmobile.pr.mytmobile");
                          ModuleCommon.instance(paramContext).disableApplication("com.zynga.msc.widget.tmobile");
                        }
                        launchSystemInfo();
                      }
                      for (;;)
                      {
                        if (Support.Feature.getBoolean("SUPPORT_SWITCH_KERNEL_FTM")) {
                          ModuleCommon.instance(paramContext).notifyFactoryMode2Kernel();
                        }
                        return;
                        if (!ModuleCommon.instance(paramContext).isConnectionModeNone()) {
                          break;
                        }
                        FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "ACTION_BOOT_COMPLETED startFactoryTestClientServiceAPO");
                        startFactoryTestClientServiceAPO();
                        break;
                        ModuleCommon.instance(paramContext).disableFtClient();
                      }
                      if (str1.equals("android.intent.action.SIM_STATE_CHANGED"))
                      {
                        if (!XMLDataStorage.instance().parseXML(paramContext)) {
                          FtUtil.log_d("FactoryTestBroadcastReceiver", "onReceive", "ACTION_SIM_STATE_CHANGED => XML data parsing was failed.");
                        }
                        String str5 = paramIntent.getStringExtra("ss");
                        String str6 = ((TelephonyManager)paramContext.getSystemService("phone")).getSubscriberId();
                        if ((new File(Support.Kernel.getFilePath("TESTSIM_NOTI")).exists()) && (str6 != null)) {
                          if ((str6.startsWith("45001")) || (str6.startsWith("00101")) || ("999999999999999".equals(str6)))
                          {
                            FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "Test SIM/Fact SIM Detected");
                            Support.Kernel.write("TESTSIM_NOTI", "1");
                          }
                        }
                        for (;;)
                        {
                          if (((str5.equals("IMSI")) || (str5.equals("LOADED"))) && (ModuleCommon.instance(paramContext).isFactorySim()))
                          {
                            sendDisableKeyguardIntent();
                            sendRTCAlarmOffIntent();
                            startFactoryTestSupportService();
                            launchSystemInfo();
                            ModuleCommon.instance(paramContext).enableFtClient();
                          }
                          if (!"999999999999999".equals(str6)) {
                            break;
                          }
                          FtUtil.log_i("FactoryTestBroadcastReceiver", "write", "write FACTORY_START after SIM_STATE_CHANGED");
                          ModuleCommon.instance(paramContext).setSwitchFactoryState();
                          return;
                          FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "Not a Test SIM");
                          Support.Kernel.write("TESTSIM_NOTI", "0");
                          continue;
                          FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "File does not exist or SIM not inserted");
                        }
                      }
                      if (str1.equals("com.sec.atd.request_reconnect"))
                      {
                        FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "com.sec.atd.request_reconnect");
                        paramContext.stopService(new Intent(paramContext, FtClient.class));
                        paramContext.startService(new Intent(paramContext, FtClient.class));
                        FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "re-start FtClient");
                        return;
                      }
                      if (!str1.equals("android.intent.action.CSC_MODEM_SETTING")) {
                        break;
                      }
                      FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "intent.action.CSC_MODEM_SETTING");
                      if (!XMLDataStorage.instance().parseXML(paramContext)) {
                        FtUtil.log_d("FactoryTestBroadcastReceiver", "onReceive", "intent.action.CSC_MODEM_SETTING => XML data parsing was failed.");
                      }
                    } while (!ModuleCommon.instance(paramContext).isConnectionModeNone());
                    mAlreadyReceivedCSC_COMPLETED = true;
                    FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "mAlreadyReceivedCSC_COMPLETED" + mAlreadyReceivedCSC_COMPLETED);
                  } while ((!mAlreadyReceivedCSC_COMPLETED) || (!mAlreadyReceivedMEIDA_SCANNING));
                  FtUtil.log_i("FactoryTestBroadcastReceiver", "send", "ACTION_SEND_RESET_COMPLETED");
                  paramContext.sendBroadcast(new Intent("com.sec.factory.entry.SEND_RESET_COMPLETED"));
                  return;
                  if (str1.equals("android.intent.action.MEDIA_SCANNER_FINISHED"))
                  {
                    FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "Intent.ACTION_MEDIA_SCANNER_FINISHED");
                    if (!XMLDataStorage.instance().parseXML(paramContext)) {
                      FtUtil.log_d("FactoryTestBroadcastReceiver", "onReceive", "ACTION_MEDIA_SCANNER_FINISHED => XML data parsing was failed.");
                    }
                    if (mIsFirstMediaScan == true)
                    {
                      mIsFirstMediaScan = false;
                      paramContext.sendBroadcast(new Intent("com.sec.factory.entry.SEND_SET_FOREGROUND"));
                      return;
                    }
                    if ("ATT".equals(mSalesCode)) {
                      if (SecondAckFileCheck().booleanValue())
                      {
                        FtUtil.log_i("FactoryTestBroadcastReceiver", "ACTION_MEDIA_SCANNER_FINISHED", "This time is factory mode(IMSI is 999999999999999) - 2nd  ACK Noti Start.");
                        DisplayNotiBar.createNotification(paramContext);
                        if (!mIsSendingBootMsg)
                        {
                          if ((FtUtil.isFactoryAppAPO()) && (!misRunningFtClient))
                          {
                            FtUtil.log_i("FactoryTestBroadcastReceiver", "USER MODE", "BOOT_COMPLETE");
                            if (!ModuleCommon.instance(paramContext).isConnectionModeNone()) {
                              startDummyFtClientForBootCompleted();
                            }
                          }
                          mIsSendingBootMsg = true;
                          FtUtil.log_i("FactoryTestBroadcastReceiver", "send", "ACTION_SEND_BOOT_COMPLETED");
                          paramContext.sendBroadcast(new Intent("com.sec.factory.entry.SEND_BOOT_COMPLETED"));
                          if (ModuleCommon.instance(paramContext).isFactoryMode())
                          {
                            FtUtil.log_i("FactoryTestBroadcastReceiver", "write", "write FACTORY_START after SEND_BOOT_COMPLETED");
                            ModuleCommon.instance(paramContext).setSwitchFactoryState();
                          }
                          if (model.contains("n7102")) {
                            paramContext.sendBroadcast(new Intent("com.sec.factory.entry.SEND_BOOT_COMPLETED_RIL2"));
                          }
                          if (Support.Feature.getBoolean("SUPPORT_BOOST_MEDIASCAN"))
                          {
                            FtUtil.log_i("FactoryTestBroadcastReceiver", "wake lock", "SUPPORT_BOOST_MEDIASCAN");
                            this.mHandler.postDelayed(new Runnable()
                            {
                              public void run()
                              {
                                ModulePower.instance(FactoryTestBroadcastReceiver.this.mContext).doMediaScanWakeLock(false);
                              }
                            }, 5000L);
                          }
                          if ((!ModuleCommon.instance(paramContext).isFactorySim()) && (!ModuleCommon.instance(paramContext).isFactoryMode())) {
                            break label1320;
                          }
                          if (mPrepare15Test == true)
                          {
                            launchFactoryTest();
                            mPrepare15Test = false;
                          }
                          if (Support.Feature.getBoolean("SUPPORT_DUALMODE"))
                          {
                            FtUtil.log_d("FactoryTestBroadcastReceiver", "onReceive", "ACTION_BOOT_COMPLETED => Factory mode: Change Dual mode");
                            startFactoryTestSlaveService(1);
                          }
                        }
                      }
                    }
                    for (;;)
                    {
                      ModuleCommon.instance(paramContext).enable15Test();
                      if ((!Support.Feature.getBoolean("NEED_PRELOAD_INSTALL", false)) && ((ModuleCommon.instance(paramContext).isFactoryMode()) || ("eng".equalsIgnoreCase(ModuleCommon.instance(paramContext).getBinaryType()))) && (!ModuleCommon.instance(paramContext).FactoryShortcutExists()) && (!ModuleCommon.instance(paramContext).isVoiceCapable()))
                      {
                        FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "CreateFactoyShortCut - test / PRELOAD_INSTALL_MODEL = false");
                        ModuleCommon.instance(paramContext).CreateFactoyShortCut();
                      }
                      if (ModuleCommon.instance(paramContext).isFirstBoot() != true) {
                        break;
                      }
                      if (ModuleCommon.instance(paramContext).isConnectionModeNone())
                      {
                        mAlreadyReceivedMEIDA_SCANNING = true;
                        FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "mAlreadyReceivedMEIDA_SCANNING" + mAlreadyReceivedMEIDA_SCANNING);
                        if ((mAlreadyReceivedCSC_COMPLETED) && (mAlreadyReceivedMEIDA_SCANNING))
                        {
                          FtUtil.log_i("FactoryTestBroadcastReceiver", "send", "ACTION_SEND_RESET_COMPLETED");
                          paramContext.sendBroadcast(new Intent("com.sec.factory.entry.SEND_RESET_COMPLETED"));
                        }
                      }
                      ModuleCommon.instance(paramContext).setFirstBoot();
                      return;
                      FtUtil.log_i("FactoryTestBroadcastReceiver", "ACTION_MEDIA_SCANNER_FINISHED", "reset_flag is Not exit!!!");
                      break label884;
                      FtUtil.log_i("FactoryTestBroadcastReceiver", "ACTION_MEDIA_SCANNER_FINISHED", "Not ATT MODEL ");
                      break label884;
                      if ("TMB".equals(SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase()))
                      {
                        ModuleCommon.instance(paramContext).enableApplication("com.tmobile.pr.mytmobile");
                        ModuleCommon.instance(paramContext).enableApplication("com.zynga.msc.widget.tmobile");
                      }
                    }
                  }
                  if (!str1.equals("android.intent.action.PREINSTALLER_FINISH")) {
                    break;
                  }
                  if (!XMLDataStorage.instance().parseXML(paramContext)) {
                    FtUtil.log_d("FactoryTestBroadcastReceiver", "onReceive", "ACTION_BOOT_COMPLETED => XML data parsing was failed.");
                  }
                } while (((!ModuleCommon.instance(paramContext).isFactoryMode()) && (!"eng".equalsIgnoreCase(ModuleCommon.instance(paramContext).getBinaryType()))) || (ModuleCommon.instance(paramContext).isVoiceCapable()) || (ModuleCommon.instance(paramContext).FactoryShortcutExists()));
                FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "CreateFactoyShortCut - test / PRELOAD_INSTALL_MODEL = true");
                ModuleCommon.instance(paramContext).CreateFactoyShortCut();
                return;
                if (!str1.equals("android.intent.action.PACKAGE_CHANGED")) {
                  break;
                }
                if (!XMLDataStorage.instance().parseXML(paramContext)) {
                  FtUtil.log_d("FactoryTestBroadcastReceiver", "onReceive", "PACKAGE_CHANGED => XML data parsing was failed.");
                }
              } while (!paramIntent.getData().getSchemeSpecificPart().equals("com.google.android.setupwizard"));
              FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "android.intent.action.PACKAGE_CHANGED - setupWizard");
            } while (((!ModuleCommon.instance(paramContext).isFactoryMode()) && (!"eng".equalsIgnoreCase(ModuleCommon.instance(paramContext).getBinaryType()))) || (ModuleCommon.instance(paramContext).isVoiceCapable()));
            FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "CreateFactoyShortCut");
            ModuleCommon.instance(paramContext).CreateFactoyShortCut();
            return;
            if (!str1.equals("android.provider.Telephony.SECRET_CODE")) {
              break label1804;
            }
            FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "android.provider.Telephony.SECRET_CODE");
            str4 = paramIntent.getData().getHost();
            FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "Host=" + str4);
            if (!XMLDataStorage.instance().parseXML(paramContext)) {
              FtUtil.log_i("FactoryTestBroadcastReceiver", "onReceive", "ACTION_SECRET_CODE => XML data parsing was failed.");
            }
            if (!str4.equals("$$15")) {
              break label1717;
            }
            if (!"SPRD".equals(Support.Feature.getString("CHIPSET_NAME_CP"))) {
              break;
            }
          } while (!ModuleCommon.instance(paramContext).isFactorySim());
          launchFactoryTest();
          return;
          if (ModuleCommon.instance(paramContext).is15TestEnable())
          {
            launchFactoryTest();
            mPrepare15Test = false;
            return;
          }
          mPrepare15Test = true;
          return;
          if (str4.equals("7547"))
          {
            launchMainHistoryView();
            return;
          }
          if (str4.equals("46744674"))
          {
            ModuleCommon.instance(paramContext).setFactoryMode();
            startFactoryTestSupportService();
            launchSystemInfo();
            sendFactoryTestModeIntent();
            return;
          }
          if (str4.equals("12345"))
          {
            startFactoryTestSupportService();
            launchSystemInfo();
            sendFactoryTestModeIntent();
            return;
          }
        } while (!str4.equals("08"));
        launchFactoryHistoryView();
        return;
      } while (str1.equals("android.intent.action.SECPHONE_READY"));
      if (str1.equals("android.intent.action.GET_FTA"))
      {
        String str2 = Support.Feature.getString("FTA_HW_VER");
        String str3 = Support.Feature.getString("FTA_SW_VER");
        Intent localIntent = new Intent("android.intent.action.GET_FTA_RESPONSE");
        localIntent.putExtra("fta_hw_ver", str2);
        localIntent.putExtra("fta_sw_ver", str3);
        this.mContext.sendBroadcast(localIntent);
        return;
      }
      if (str1.equals("com.sec.factory.entry.REQUEST_FACTORY_RESET"))
      {
        createFileNotiCheck();
        return;
      }
    } while (!str1.equals("com.sec.android.app.factoryTest.NOTI_CLEAR"));
    label1320:
    label1717:
    DisplayNotiBar.clearNotification(paramContext);
    label1804:
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.entry.FactoryTestBroadcastReceiver
 * JD-Core Version:    0.7.1
 */