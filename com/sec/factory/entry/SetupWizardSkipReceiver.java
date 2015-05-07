package com.sec.factory.entry;

import android.app.StatusBarManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.os.FileUtils;
import android.os.Handler;
import android.os.SystemProperties;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Version;
import com.sec.factory.support.XMLDataStorage;
import java.io.File;
import java.io.IOException;

public class SetupWizardSkipReceiver
  extends BroadcastReceiver
{
  private static final SetupWizardList[] BASE_SETUPWIZARD_LIST;
  private static boolean isReceivedEnalbleIntent = false;
  private static SetupWizardList[] mSetupWizardList;
  private ContentObserver mContentObserver;
  private Context mContext;
  private boolean mDeviceProvisioned = true;
  Handler mHandler = new Handler();
  private StatusBarManager mStatusBarManager;
  private int mType = 0;
  
  static
  {
    SetupWizardList[] arrayOfSetupWizardList = new SetupWizardList[12];
    arrayOfSetupWizardList[0] = new SetupWizardList("*", "*", "com.sec.android.app.setupwizard", null, 1);
    arrayOfSetupWizardList[1] = new SetupWizardList("*", "*", "com.google.android.setupwizard", "com.google.android.setupwizard.SetupWizardActivity", 1);
    arrayOfSetupWizardList[2] = new SetupWizardList("*", "*", "com.google.android.setupwizard", "com.google.android.setupwizard.WelcomeActivity", 1);
    arrayOfSetupWizardList[3] = new SetupWizardList("*", "*", "com.sec.android.app.SecSetupWizard", "com.sec.android.app.SecSetupWizard.SecSetupWizardActivity", 2);
    arrayOfSetupWizardList[4] = new SetupWizardList("*", "*", "com.dashwire.config", "com.dashwire.config.ui.SetupWizardActivity", 3);
    arrayOfSetupWizardList[5] = new SetupWizardList("*", "DCM", "com.nttdocomo.android.mascot", null, 3);
    arrayOfSetupWizardList[6] = new SetupWizardList("*", "DCM", "com.nttdocomo.android.applicationmanager", null, 3);
    arrayOfSetupWizardList[7] = new SetupWizardList("*", "DCM", "com.nttdocomo.android.initialization", null, 3);
    arrayOfSetupWizardList[8] = new SetupWizardList("*", "DCM", "com.nttdocomo.android.paletteui", null, 3);
    arrayOfSetupWizardList[9] = new SetupWizardList("*", "*", "com.samsung.cce", "com.samsung.cce.DefaultActivity", 3);
    arrayOfSetupWizardList[10] = new SetupWizardList("*", "*", "com.samsung.cce", "com.samsung.cce.CIQ_InfoActivity", 3);
    arrayOfSetupWizardList[11] = new SetupWizardList("*", "ATT", "com.samsung.location", "com.samsung.location.LocationActivity", 3);
    BASE_SETUPWIZARD_LIST = arrayOfSetupWizardList;
  }
  
  private void CloseSetupWizard()
  {
    Log.i("SetupWizardSkipReceiver", "CloseSetupWizard ");
    PackageManager localPackageManager = this.mContext.getPackageManager();
    int i = 0;
    if (i < mSetupWizardList.length)
    {
      Log.i("SetupWizardSkipReceiver", "PackageName= " + mSetupWizardList[i].mPackageName + "ActivityName=" + mSetupWizardList[i].mComponentName + " Model=" + mSetupWizardList[i].mModelName + "index: " + i);
      if (!isTargetComponent(i)) {}
      for (;;)
      {
        i++;
        break;
        if (this.mType == -1) {
          Log.i("SetupWizardSkipReceiver", "No type set. Default settng");
        }
        for (;;)
        {
          for (;;)
          {
            if (mSetupWizardList[i].mComponentName == null) {
              break label277;
            }
            try
            {
              int k = localPackageManager.getComponentEnabledSetting(mSetupWizardList[i].mComponentName);
              if (k == 2) {
                break;
              }
              Log.d("SetupWizardSkipReceiver", "Finishing this component for factory mode: " + mSetupWizardList[i].mComponentName);
              localPackageManager.setComponentEnabledSetting(mSetupWizardList[i].mComponentName, 2, 0);
            }
            catch (IllegalArgumentException localIllegalArgumentException2)
            {
              Log.w("SetupWizardSkipReceiver", "IllegalArgumentException : Can't find  " + mSetupWizardList[i].mComponentName + " This component might not be installed!!)");
            }
          }
          if (this.mType != mSetupWizardList[i].mSetupwizType)
          {
            Log.i("SetupWizardSkipReceiver", "Next Loop since the current component is not included in the list");
            break;
            break;
          }
        }
        try
        {
          label277:
          int j = localPackageManager.getApplicationEnabledSetting(mSetupWizardList[i].mPackageName);
          if (j != 2)
          {
            Log.d("SetupWizardSkipReceiver", "Finishing this component for factory mode: " + mSetupWizardList[i].mComponentName);
            localPackageManager.setApplicationEnabledSetting(mSetupWizardList[i].mPackageName, 2, 0);
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException1)
        {
          Log.w("SetupWizardSkipReceiver", "IllegalArgumentException : Can't find  " + mSetupWizardList[i].mPackageName + " This package might not be installed!!)");
        }
      }
    }
    boolean bool = Settings.Secure.putInt(this.mContext.getContentResolver(), "device_provisioned", 1);
    Log.i("SetupWizardSkipReceiver", "CloseSetupWizard(Settings.Secure.putInt..Settings.Secure.DEVICE_PROVISIONED) : " + bool);
    setForcedSkipped(true);
    Log.i("SetupWizardSkipReceiver", "close DEVICE_PROVISIONED : " + Settings.Secure.getInt(this.mContext.getContentResolver(), "device_provisioned", 0));
    this.mStatusBarManager = ((StatusBarManager)this.mContext.getSystemService("statusbar"));
    this.mStatusBarManager.disable(0);
  }
  
  private void EnableSetupWizard()
  {
    Log.i("SetupWizardSkipReceiver", "EnableSetupWizard()");
    PackageManager localPackageManager = this.mContext.getPackageManager();
    label244:
    label377:
    for (int i = 0;; i++) {
      if (i < mSetupWizardList.length)
      {
        do
        {
          try
          {
            if (!isTargetComponent(i)) {
              break label377;
            }
            if (this.mType == -1)
            {
              Log.i("SetupWizardSkipReceiver", "No type set. Default settng");
              if (mSetupWizardList[i].mComponentName == null) {
                break label244;
              }
              if (localPackageManager.getComponentEnabledSetting(mSetupWizardList[i].mComponentName) != 2) {
                break;
              }
              localPackageManager.setComponentEnabledSetting(mSetupWizardList[i].mComponentName, 0, 1);
              Log.i("SetupWizardSkipReceiver", "Setup_Wizard (getComponentEnabledSetting) : " + localPackageManager.getComponentEnabledSetting(mSetupWizardList[i].mComponentName));
              if (isReceivedEnalbleIntent) {
                break label377;
              }
              Intent localIntent = new Intent().setComponent(mSetupWizardList[i].mComponentName);
              localIntent.addFlags(268435456);
              localIntent.setAction("android.intent.action.MAIN");
              localIntent.addCategory("android.intent.category.HOME");
              this.mContext.startActivity(localIntent);
            }
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            Log.w("SetupWizardSkipReceiver", "IllegalArgumentException : Can't find ComponentName (setupwizard may not be installed!!)");
          }
        } while (this.mType == mSetupWizardList[i].mSetupwizType);
        Log.i("SetupWizardSkipReceiver", "Next Loop since the current component is not included in the list");
        continue;
        Log.i("SetupWizardSkipReceiver", "No need to reset setupwizard. ComponentEnabledState is still avaiable!");
        continue;
        if (localPackageManager.getApplicationEnabledSetting(mSetupWizardList[i].mPackageName) == 2)
        {
          localPackageManager.setApplicationEnabledSetting(mSetupWizardList[i].mPackageName, 0, 1);
          Log.i("SetupWizardSkipReceiver", "Setup_Wizard (getApplicationEnabledSetting) : " + localPackageManager.getApplicationEnabledSetting(mSetupWizardList[i].mPackageName));
        }
        else
        {
          Log.i("SetupWizardSkipReceiver", "No need to reset setupwizard. ApplicationEnabledState is still avaiable!");
        }
      }
      else
      {
        setForcedSkipped(false);
        this.mContentObserver = new ContentObserver(this.mHandler)
        {
          public void onChange(boolean paramAnonymousBoolean)
          {
            super.onChange(paramAnonymousBoolean);
            SetupWizardSkipReceiver localSetupWizardSkipReceiver = SetupWizardSkipReceiver.this;
            int i = Settings.Secure.getInt(SetupWizardSkipReceiver.this.mContext.getContentResolver(), "device_provisioned", 0);
            boolean bool = false;
            if (i != 0) {
              bool = true;
            }
            SetupWizardSkipReceiver.access$402(localSetupWizardSkipReceiver, bool);
            if ((SetupWizardSkipReceiver.this.mDeviceProvisioned) && (SetupWizardSkipReceiver.this.mContentObserver != null))
            {
              SetupWizardSkipReceiver.this.mContext.getContentResolver().unregisterContentObserver(SetupWizardSkipReceiver.this.mContentObserver);
              SetupWizardSkipReceiver.access$602(SetupWizardSkipReceiver.this, null);
            }
            Log.d("SetupWizardSkipReceiver", "DEVICE_PROVISIONED state = " + SetupWizardSkipReceiver.this.mDeviceProvisioned);
          }
        };
        Log.d("SetupWizardSkipReceiver", "--registerContentObserver-- : ");
        this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("device_provisioned"), false, this.mContentObserver);
        return;
      }
    }
  }
  
  public static boolean getFactoryMode()
  {
    try
    {
      String str2 = FileUtils.readTextFile(new File("/efs/FactoryApp/factorymode"), 32, null);
      str1 = str2;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        String str1 = "OFF";
        Log.d("SetupWizardSkipReceiver", "cannot open file : /efs/FactoryApp/factorymode ");
      }
      Log.d("SetupWizardSkipReceiver", "Factory Mode");
    }
    if ((str1 != null) && (str1.contains("ON")))
    {
      Log.d("SetupWizardSkipReceiver", "User Mode");
      return false;
    }
    return true;
  }
  
  private int getSetupWizardType()
  {
    int i = -1;
    String str = Support.Feature.getString("SETUPWIZARD_INDEX_LIST");
    if (!"Unknown".equalsIgnoreCase(str))
    {
      str.split(",");
      i = Integer.parseInt(str);
    }
    return i;
  }
  
  private boolean isFactoryMode()
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)this.mContext.getSystemService("phone");
    localTelephonyManager.getDeviceId();
    String str = localTelephonyManager.getSubscriberId();
    if ((str != null) && (str.equals("999999999999999")))
    {
      Log.i("SetupWizardSkipReceiver", "isFactoryMode == true");
      return true;
    }
    Log.i("SetupWizardSkipReceiver", "isFactoryMode == false");
    return getFactoryMode();
  }
  
  private boolean isForcedSkipped()
  {
    boolean bool = this.mContext.getSharedPreferences("factory.skipped", 0).getBoolean("factory.wizard.skipped", false);
    Log.i("SetupWizardSkipReceiver", "isForcedSkipped() before : " + bool);
    return bool;
  }
  
  private boolean isTargetComponent(int paramInt)
  {
    Log.i("SetupWizardSkipReceiver", "isTargetComponent()");
    if ((mSetupWizardList[paramInt].mModelName.equals("*")) && (mSetupWizardList[paramInt].mSalesCode.equals("*"))) {}
    do
    {
      return true;
      if (mSetupWizardList[paramInt].mModelName.equals("*")) {
        break;
      }
    } while (mSetupWizardList[paramInt].mModelName.equalsIgnoreCase(SystemProperties.get("ro.product.model")));
    while ((mSetupWizardList[paramInt].mSalesCode.equals("*")) || (!mSetupWizardList[paramInt].mSalesCode.equalsIgnoreCase(SystemProperties.get("ro.csc.sales_code")))) {
      return false;
    }
    return true;
  }
  
  private void sendDisableKeyguardIntent()
  {
    Log.d("SetupWizardSkipReceiver", "sendDisableKeyguardIntent");
    Intent localIntent = new Intent("com.android.samsungtest.DISABLE_KEYGUARD_FACTORY");
    this.mContext.sendBroadcast(localIntent);
  }
  
  private void setForcedSkipped(boolean paramBoolean)
  {
    Log.i("SetupWizardSkipReceiver", "setForcedSkipped() : " + paramBoolean);
    SharedPreferences.Editor localEditor = this.mContext.getSharedPreferences("factory.skipped", 0).edit();
    localEditor.putBoolean("factory.wizard.skipped", paramBoolean);
    localEditor.commit();
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    this.mContext = paramContext;
    mSetupWizardList = BASE_SETUPWIZARD_LIST;
    String str = paramIntent.getAction();
    Log.e("SetupWizardSkipReceiver", "onReceive: " + str);
    SystemProperties.get("ro.csc.sales_code");
    int i = Settings.Secure.getInt(this.mContext.getContentResolver(), "device_provisioned", 0);
    if (!XMLDataStorage.instance().parseXML(paramContext)) {
      Log.i("SetupWizardSkipReceiver", "onReceive - XML parsing failed");
    }
    int j = Integer.parseInt(Support.Version.getString("FACTORY_TEST_APP").substring(2));
    this.mType = getSetupWizardType();
    if ((str.equals("android.intent.action.PRE_BOOT_COMPLETED")) || (str.equals("android.intent.action.BOOT_COMPLETED")) || (str.equals("android.intent.action.SIM_STATE_CHANGED"))) {
      if (((j >= 19) && (getFactoryMode() == true)) || ((j < 19) && (isFactoryMode())))
      {
        Log.i("SetupWizardSkipReceiver", "It is FactoryMode. isFactoryMode() == true");
        if (i == 1) {
          Log.i("SetupWizardSkipReceiver", "isdevprovisioned: true");
        }
      }
    }
    do
    {
      do
      {
        return;
        Log.i("SetupWizardSkipReceiver", "isdevprovisioned: false");
        CloseSetupWizard();
        return;
      } while (isForcedSkipped() != true);
      Log.i("SetupWizardSkipReceiver", "set DEVICE_PROVISIONED to 0");
      Settings.Secure.putInt(this.mContext.getContentResolver(), "device_provisioned", 0);
      EnableSetupWizard();
      sendDisableKeyguardIntent();
      return;
      if (str.equals("android.intent.action.CLOSE_SETUP_WIZARD"))
      {
        Log.e("SetupWizardSkipReceiver", "Ignored...................");
        CloseSetupWizard();
        return;
      }
    } while (!str.equals("android.intent.action.ENABLE_SETUP_WIZARD"));
    Log.e("SetupWizardSkipReceiver", "Ignored...................");
    isReceivedEnalbleIntent = true;
    EnableSetupWizard();
  }
  
  public static class SetupWizardList
  {
    private ComponentName mComponentName;
    private String mModelName;
    private String mPackageName;
    private String mSalesCode;
    private int mSetupwizType;
    
    public SetupWizardList(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
    {
      this.mModelName = paramString1;
      this.mSalesCode = paramString2;
      this.mPackageName = paramString3;
      if (paramString4 == null) {}
      for (this.mComponentName = null;; this.mComponentName = new ComponentName(paramString3, paramString4))
      {
        this.mSetupwizType = paramInt;
        return;
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.entry.SetupWizardSkipReceiver
 * JD-Core Version:    0.7.1
 */