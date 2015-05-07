package com.sec.android.app.camerafirmware;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.IWindowManager;
import android.view.IWindowManager.Stub;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class CameraFirmware
  extends Activity
{
  private static final String mSalesCode = SystemProperties.get("ro.csc.sales_code", "NONE").trim().toUpperCase();
  protected String PREF_KEY_UPCOUNT_ENG = "pref_firmware_upcount_eng_key";
  protected String PREF_KEY_UPCOUNT_ENG_FRONT = "pref_firmware_upcount_eng_front_key";
  protected String PREF_KEY_UPCOUNT_USER = "pref_firmware_upcount_user_key";
  protected String PREF_KEY_UPCOUNT_USER_FRONT = "pref_firmware_upcount_user_front_key";
  FirmwareFileMgr mCamFirmMgr = null;
  CameraDeviceController mCameraDevice = new CameraDeviceController();
  View.OnClickListener mClickListener;
  private Toast mCurrentToast = null;
  private Date mDate = null;
  private AlertDialog mErrorPopup = null;
  FirmwareFileMgrFront mFront = new FirmwareFileMgrFront();
  private Handler mHandler;
  private boolean mKeystringBlockFlag;
  private AlertDialog mPopup = null;
  private final String mProductShip = SystemProperties.get("ro.product_ship", "FALSE").trim().toUpperCase();
  FirmwareFileMgr mRear = new FirmwareFileMgr();
  private PowerManager.WakeLock mWakeLock;
  private IWindowManager mWindowManager;
  private ProgressDialog progressDialog;
  
  public CameraFirmware()
  {
    if ((this.mProductShip.equalsIgnoreCase("TRUE")) && (isKeyStringBlocked())) {}
    for (boolean bool = true;; bool = false)
    {
      this.mKeystringBlockFlag = bool;
      this.mClickListener = new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Log.i("CameraFirmware", "Button Clicked ");
          switch (paramAnonymousView.getId())
          {
          default: 
            return;
          case 2131296264: 
            if (CameraFirmware.this.mCurrentToast != null) {
              CameraFirmware.this.mCurrentToast.cancel();
            }
            if (CameraFirmware.this.mCameraDevice != null) {
              CameraFirmware.this.mCameraDevice.setFirmwareMode(1);
            }
            CameraFirmware.this.mCamFirmMgr = CameraFirmware.this.mRear;
            String str6 = "Rear Camera\nCam FW Ver : " + CameraFirmware.this.mCamFirmMgr.getCamFWVer() + "\n" + "Phone FW Ver : " + CameraFirmware.this.mCamFirmMgr.getPhoneFWVer();
            CameraFirmware.this.mCamFirmMgr = CameraFirmware.this.mFront;
            String str7 = str6 + "\n\nFront Camera\nCam&Phone FW Ver : " + CameraFirmware.this.mCamFirmMgr.getCamFWVer();
            CameraFirmware.access$002(CameraFirmware.this, Toast.makeText(CameraFirmware.this, str7, 1));
            CameraFirmware.this.mCurrentToast.show();
            return;
          case 2131296265: 
          case 2131296266: 
            if (paramAnonymousView.getId() == 2131296265) {
              CameraFirmware.this.mCamFirmMgr = CameraFirmware.this.mRear;
            }
            break;
          }
          for (;;)
          {
            if (CameraFirmware.this.mCameraDevice != null) {}
            try
            {
              Log.i("CameraFirmware", "mCameraDevice.setFirmwareMode(CameraDeviceController.FIRMWAREMODE_VERSION)");
              CameraFirmware.this.mCameraDevice.setFirmwareMode(1);
              label303:
              if (CameraFirmware.this.ChkUserFirmwareFile())
              {
                CameraFirmware.access$102(CameraFirmware.this, CameraFirmware.this.DialogPopup(2131165200));
                CameraFirmware.this.mPopup.show();
                return;
                CameraFirmware.this.mCamFirmMgr = CameraFirmware.this.mFront;
              }
              else
              {
                try
                {
                  if (!CameraFirmware.this.isSameVendor()) {
                    break label554;
                  }
                  Log.i("CameraFirmware", "valid vendor");
                  switch (CameraFirmware.this.IsNewFirmwareDate())
                  {
                  case -1: 
                    Log.i("CameraFirmware", "This is the latest version..");
                    CameraFirmware.this.dialogErrorPopup(2131165199);
                    return;
                  }
                }
                catch (Exception localException)
                {
                  Log.e("CameraFirmware", "Something goes wrong");
                  CameraFirmware.this.dialogErrorPopup(2131165198);
                  return;
                }
                Log.i("CameraFirmware", "Updating New Firmwareversion...");
                CameraFirmware.this.mCamFirmMgr.setManageMode(FirmwareFileMgr.CAM_FLAG_FIRMWARE_UPDATE);
                CameraFirmware.this.showUpdateProgress();
                CameraFirmware.this.startUpdateThread();
                return;
                Log.i("CameraFirmware", "Same date..");
                if (CameraFirmware.this.IsNewFirmwareVersion())
                {
                  Log.i("CameraFirmware", "Updating New Firmwareversion...");
                  CameraFirmware.this.mCamFirmMgr.setManageMode(FirmwareFileMgr.CAM_FLAG_FIRMWARE_UPDATE);
                  CameraFirmware.this.showUpdateProgress();
                  CameraFirmware.this.startUpdateThread();
                  return;
                }
                Log.i("CameraFirmware", "This is the latest version..");
                CameraFirmware.this.dialogErrorPopup(2131165199);
                return;
                Log.e("CameraFirmware", "Invalid vendor");
                CameraFirmware.this.dialogErrorPopup(2131165197);
                return;
                if (CameraFirmware.this.mCurrentToast != null) {
                  CameraFirmware.this.mCurrentToast.cancel();
                }
                if (CameraFirmware.this.mCameraDevice != null) {
                  CameraFirmware.this.mCameraDevice.setFirmwareMode(1);
                }
                CameraFirmware.this.mCamFirmMgr = CameraFirmware.this.mRear;
                String str4 = "Rear Camera\n ISP Ver : " + CameraFirmware.this.mCamFirmMgr.getISPVer1();
                CameraFirmware.this.mCamFirmMgr = CameraFirmware.this.mFront;
                String str5 = str4 + "\n\nFront Camera\n ISP Ver : " + CameraFirmware.this.mCamFirmMgr.getISPVer1();
                CameraFirmware.access$002(CameraFirmware.this, Toast.makeText(CameraFirmware.this, str5, 1));
                CameraFirmware.this.mCurrentToast.show();
                return;
                if (CameraFirmware.this.mCurrentToast != null) {
                  CameraFirmware.this.mCurrentToast.cancel();
                }
                CameraFirmware.access$002(CameraFirmware.this, Toast.makeText(CameraFirmware.this, "Rear\n( " + CameraFirmware.this.getUpdateCount(CameraFirmware.this.PREF_KEY_UPCOUNT_USER) + ":" + CameraFirmware.this.getUpdateCount(CameraFirmware.this.PREF_KEY_UPCOUNT_ENG) + " )\n\nFront\n( " + CameraFirmware.this.getUpdateCount(CameraFirmware.this.PREF_KEY_UPCOUNT_USER_FRONT) + ":" + CameraFirmware.this.getUpdateCount(CameraFirmware.this.PREF_KEY_UPCOUNT_ENG_FRONT) + " )", 1));
                CameraFirmware.this.mCurrentToast.show();
                return;
                if (CameraFirmware.this.mCurrentToast != null) {
                  CameraFirmware.this.mCurrentToast.cancel();
                }
                if (CameraFirmware.this.mCameraDevice != null) {
                  CameraFirmware.this.mCameraDevice.setFirmwareMode(1);
                }
                CameraFirmware.this.mCamFirmMgr = CameraFirmware.this.mRear;
                String str2 = "Rear Camera\n" + CameraFirmware.this.mCamFirmMgr.getCAMFWCalAF() + "\n" + CameraFirmware.this.mCamFirmMgr.getCAMFWCalSEN();
                CameraFirmware.this.mCamFirmMgr = CameraFirmware.this.mFront;
                String str3 = str2 + "\n\nFront Camera\n" + CameraFirmware.this.mCamFirmMgr.getCAMFWCalAF() + "\n" + CameraFirmware.this.mCamFirmMgr.getCAMFWCalSEN();
                CameraFirmware.access$002(CameraFirmware.this, Toast.makeText(CameraFirmware.this, str3, 1));
                CameraFirmware.this.mCurrentToast.show();
                return;
                if (paramAnonymousView.getId() == 2131296270) {}
                for (CameraFirmware.this.mCamFirmMgr = CameraFirmware.this.mRear;; CameraFirmware.this.mCamFirmMgr = CameraFirmware.this.mFront)
                {
                  CameraFirmware.this.mCamFirmMgr.setManageMode(FirmwareFileMgr.CAM_FLAG_FIRMWARE_DUMP);
                  CameraFirmware.this.showUpdateProgress(true);
                  CameraFirmware.this.startUpdateThread();
                  return;
                }
                if (CameraFirmware.this.mCurrentToast != null) {
                  CameraFirmware.this.mCurrentToast.cancel();
                }
                CameraFirmware.this.mCamFirmMgr = CameraFirmware.this.mRear;
                String str1 = "\n\nRear Camera\nISP Core Voltage : " + CameraFirmware.this.mCamFirmMgr.getISPVoltage();
                CameraFirmware.access$002(CameraFirmware.this, Toast.makeText(CameraFirmware.this, str1, 1));
                CameraFirmware.this.mCurrentToast.show();
                return;
              }
            }
            catch (IllegalArgumentException localIllegalArgumentException)
            {
              label554:
              break label303;
            }
          }
        }
      };
      this.mHandler = new Handler()
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          switch (paramAnonymousMessage.what)
          {
          default: 
            return;
          case 1001: 
          case 1002: 
            Log.e("CameraFirmware", "handleMessage : updateFirmware - finish");
            if (CameraFirmware.this.mWakeLock.isHeld()) {
              CameraFirmware.this.mWakeLock.release();
            }
            CameraFirmware.this.hideUpdateProgress();
            if (paramAnonymousMessage.what == 1001)
            {
              Toast.makeText(CameraFirmware.this, "F/W Update complete.\nNeed to reboot!", 1).show();
              CameraFirmware.this.updateFirmwareUpdateCount();
            }
            for (;;)
            {
              CameraFirmware.this.setBlockHold(false);
              CameraFirmware.this.mCamFirmMgr.resetFWInfo();
              return;
              Toast.makeText(CameraFirmware.this, "F/W dump complete", 1).show();
            }
          }
          Log.d("CameraFirmware", "handle msg: " + paramAnonymousMessage.obj);
        }
      };
      return;
    }
  }
  
  private long CheckCamFWDate()
  {
    String str = this.mCamFirmMgr.getCamFWVer();
    this.mDate = new Date(2009 + ('ﾽ' + str.substring(2, 3).toCharArray()[0]), 1 + ('﾿' + str.substring(3, 4).toCharArray()[0]), 1);
    Log.i("CameraFirmware", "CamFW Date = " + this.mDate.getTime());
    return this.mDate.getTime();
  }
  
  private int CheckCamFWVersion()
  {
    char[] arrayOfChar = this.mCamFirmMgr.getCamFWVer().substring(4, 6).toCharArray();
    int i = '{' * arrayOfChar[0] + arrayOfChar[1];
    Log.i("CameraFirmware", "CheckCamFWVersion = " + i);
    return i;
  }
  
  private long CheckPhoneFWDate()
  {
    String str = this.mCamFirmMgr.getPhoneFWVer();
    this.mDate = new Date(2009 + ('ﾽ' + str.substring(2, 3).toCharArray()[0]), 1 + ('﾿' + str.substring(3, 4).toCharArray()[0]), 1);
    Log.i("CameraFirmware", "PhoneFW Date = " + this.mDate.getTime());
    return this.mDate.getTime();
  }
  
  private int CheckPhoneFWVersion()
  {
    char[] arrayOfChar = this.mCamFirmMgr.getPhoneFWVer().substring(4, 6).toCharArray();
    int i = '{' * arrayOfChar[0] + arrayOfChar[1];
    Log.i("CameraFirmware", "CheckPhoneFWVersion = " + i);
    return i;
  }
  
  private AlertDialog DialogPopup(int paramInt)
  {
    Log.v("CameraFirmware", "DialogPopup");
    if ((this.mPopup != null) && (this.mPopup.isShowing())) {
      this.mPopup.dismiss();
    }
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle(paramInt);
    localBuilder.setMessage("Cam FW Ver : " + this.mCamFirmMgr.getCamFWVer() + "\n" + "Phone FW Ver : " + this.mCamFirmMgr.getPhoneFWVer());
    localBuilder.setPositiveButton(2131165186, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        CameraFirmware.this.mCamFirmMgr.setManageMode(FirmwareFileMgr.CAM_FLAG_FIRMWARE_UPDATE);
        CameraFirmware.this.showUpdateProgress();
        CameraFirmware.this.startUpdateThread();
      }
    });
    localBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.cancel();
      }
    });
    return localBuilder.create();
  }
  
  private void dialogErrorPopup(int paramInt)
  {
    Log.v("CameraFirmware", "dialogErrorPopup");
    if ((this.mErrorPopup != null) && (this.mErrorPopup.isShowing())) {
      this.mErrorPopup.dismiss();
    }
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle(paramInt);
    localBuilder.setMessage("Cam FW Ver : " + this.mCamFirmMgr.getCamFWVer() + "\n" + "Phone FW Ver : " + this.mCamFirmMgr.getPhoneFWVer());
    localBuilder.setPositiveButton(2131165185, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        CameraFirmware.this.finish();
      }
    });
    localBuilder.setCancelable(false);
    this.mErrorPopup = localBuilder.create();
    this.mErrorPopup.show();
  }
  
  private void init()
  {
    this.mWakeLock = ((PowerManager)getSystemService("power")).newWakeLock(536870922, "TouchFirmware");
    this.mWindowManager = null;
    this.progressDialog = new ProgressDialog(this);
    this.progressDialog.setIndeterminate(true);
    this.progressDialog.setCancelable(false);
    findViewById(2131296264).setOnClickListener(this.mClickListener);
    findViewById(2131296265).setOnClickListener(this.mClickListener);
    findViewById(2131296266).setVisibility(8);
    findViewById(2131296267).setOnClickListener(this.mClickListener);
    findViewById(2131296268).setOnClickListener(this.mClickListener);
    findViewById(2131296269).setOnClickListener(this.mClickListener);
    findViewById(2131296270).setOnClickListener(this.mClickListener);
    findViewById(2131296271).setVisibility(8);
    findViewById(2131296272).setOnClickListener(this.mClickListener);
    this.mFront.resetFWInfo();
    this.mRear.resetFWInfo();
    if (("DCM".equals(mSalesCode)) && (this.mKeystringBlockFlag))
    {
      findViewById(2131296265).setVisibility(8);
      findViewById(2131296268).setVisibility(8);
      findViewById(2131296270).setVisibility(8);
    }
  }
  
  public static boolean isKeyStringBlocked()
  {
    try
    {
      str = FileUtils.readTextFile(new File("/efs/FactoryApp/keystr"), 32, null);
      Log.d("CameraFirmware", str);
      if (str.contains("ON"))
      {
        Log.d("CameraFirmware", "return true");
        return true;
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        String str = "OFF";
        Log.d("CameraFirmware", "cannot open file : /efs/FactoryApp/keystr ");
      }
      Log.d("CameraFirmware", "return false");
    }
    return false;
  }
  
  private boolean isSameVendor()
  {
    if (getCamFWVendor().equals(getPhoneFWVendor())) {}
    while (((getCamFWVendor().substring(0, 1).equals("O")) || (getCamFWVendor().substring(0, 1).equals("G"))) && ((getPhoneFWVendor().substring(0, 1).equals("O")) || (getPhoneFWVendor().substring(0, 1).equals("G"))) && (getCamFWVendor().substring(1, 1).equals(getPhoneFWVendor().substring(1, 1)))) {
      return true;
    }
    return false;
  }
  
  private void manageFirmware()
  {
    Message localMessage = new Message();
    if (!this.mWakeLock.isHeld()) {
      this.mWakeLock.acquire();
    }
    localMessage.obj = "Start the firmware update";
    localMessage.what = 1000;
    this.mHandler.dispatchMessage(localMessage);
    if (this.mCamFirmMgr.getManageMode() == FirmwareFileMgr.CAM_FLAG_FIRMWARE_UPDATE) {
      this.mHandler.sendEmptyMessageDelayed(1001, 20000L);
    }
    for (;;)
    {
      if (this.mCameraDevice != null)
      {
        if (this.mCamFirmMgr.getManageMode() != FirmwareFileMgr.CAM_FLAG_FIRMWARE_UPDATE) {
          break;
        }
        this.mCameraDevice.setFirmwareMode(2);
      }
      return;
      if (this.mCamFirmMgr.getManageMode() == FirmwareFileMgr.CAM_FLAG_FIRMWARE_DUMP) {
        this.mHandler.sendEmptyMessageDelayed(1002, 20000L);
      }
    }
    this.mCameraDevice.setFirmwareMode(3);
  }
  
  private boolean setBlockHold(boolean paramBoolean)
  {
    Log.e("CameraFirmware", "setBlockHold - bBlock: " + paramBoolean);
    if (this.mWindowManager == null) {
      this.mWindowManager = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
    }
    Log.e("CameraFirmware", "setBlockHold - bResult: " + false);
    return false;
  }
  
  protected boolean ChkUserFirmwareFile()
  {
    String str = FirmwareFileMgr.CAMERA_FIRMWARE_INFO_USER_FILE + "SlimISP.bin";
    Log.i("CameraFirmware", "ChkUserFirmwareFile : " + str);
    if (new File(str).exists())
    {
      Log.i("CameraFirmware", "User Firmware file exists");
      return true;
    }
    Log.i("CameraFirmware", "User Firmware file doesn't exist");
    return false;
  }
  
  protected int IsNewFirmwareDate()
  {
    Log.i("CameraFirmware", "IsNewFirmwareDate");
    if (CheckPhoneFWDate() - CheckCamFWDate() > 0L) {
      return 1;
    }
    if (CheckPhoneFWDate() - CheckCamFWDate() == 0L) {
      return 0;
    }
    return -1;
  }
  
  protected boolean IsNewFirmwareVersion()
  {
    Log.i("CameraFirmware", "IsNewFirmwareVersion");
    return CheckPhoneFWVersion() > CheckCamFWVersion();
  }
  
  protected String getCamFWVendor()
  {
    String str = this.mCamFirmMgr.getCamFWVer().substring(0, 2);
    Log.i("CameraFirmware", "getCamFWVendor() = " + str);
    return str;
  }
  
  protected String getPhoneFWVendor()
  {
    String str = this.mCamFirmMgr.getPhoneFWVer().substring(0, 2);
    Log.i("CameraFirmware", "getPhoneFWVendor() = " + str);
    return str;
  }
  
  protected int getUpdateCount(String paramString)
  {
    return PreferenceManager.getDefaultSharedPreferences(this).getInt(paramString, 0);
  }
  
  protected void hideUpdateProgress()
  {
    if ((this.progressDialog != null) && (this.progressDialog.isShowing())) {
      this.progressDialog.dismiss();
    }
    this.progressDialog = null;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903042);
    init();
  }
  
  protected void onDestroy()
  {
    Log.e("CameraFirmware", "onDestroy");
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    Log.d("CameraFirmware", "onKeyDown()-keyCode:" + paramInt);
    switch (paramInt)
    {
    default: 
      return super.onKeyDown(paramInt, paramKeyEvent);
    }
    return false;
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    Log.d("CameraFirmware", "onKeyDown()-keyCode:" + paramInt);
    switch (paramInt)
    {
    default: 
      return super.onKeyUp(paramInt, paramKeyEvent);
    }
    return false;
  }
  
  protected void onPause()
  {
    Log.e("CameraFirmware", "onPause");
    if (this.mWakeLock.isHeld()) {
      this.mWakeLock.release();
    }
    this.mCameraDevice.releaseCamera();
    setBlockHold(false);
    super.onPause();
  }
  
  protected void onResume()
  {
    Log.e("CameraFirmware", "onResume");
    if (!this.mCameraDevice.openCamera())
    {
      Log.e("CameraFirmware", "onResume - camera is not opened");
      Toast.makeText(this, 2131165201, 1).show();
    }
    super.onResume();
  }
  
  protected void setUpdateCount(String paramString, int paramInt)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
    localEditor.putInt(paramString, paramInt);
    localEditor.commit();
  }
  
  protected void showUpdateProgress()
  {
    showUpdateProgress(false);
  }
  
  protected void showUpdateProgress(boolean paramBoolean)
  {
    if (this.progressDialog == null)
    {
      this.progressDialog = new ProgressDialog(this);
      this.progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
      {
        public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          Log.e("CameraFirmware", "ProgressDialog - onKey : " + paramAnonymousInt);
          switch (paramAnonymousInt)
          {
          default: 
            return true;
          }
          return false;
        }
      });
      this.progressDialog.setIndeterminate(true);
      this.progressDialog.setCancelable(false);
    }
    if (!paramBoolean) {
      this.progressDialog.setMessage("Now Updating... Warning!\nDo not turn off!\nIt will take sometime.");
    }
    for (;;)
    {
      if (!this.progressDialog.isShowing()) {
        this.progressDialog.show();
      }
      return;
      this.progressDialog.setMessage("Now Dumping... Warning!\nDo not turn off!\nIt will take sometime.");
    }
  }
  
  protected void startUpdateThread()
  {
    setBlockHold(true);
    new Thread(new Runnable()
    {
      public void run()
      {
        CameraFirmware.this.manageFirmware();
      }
    }).start();
  }
  
  protected void updateFirmwareUpdateCount()
  {
    int j;
    if (this.mCamFirmMgr == this.mRear)
    {
      int k = getUpdateCount(this.PREF_KEY_UPCOUNT_ENG);
      String str2 = this.PREF_KEY_UPCOUNT_ENG;
      j = k + 1;
      setUpdateCount(str2, j);
    }
    for (;;)
    {
      Log.e("CameraFirmware", "updateFirmwareUpdateCount - PREF_KEY_UPCOUNT_ENG:[" + j + "]");
      return;
      int i = getUpdateCount(this.PREF_KEY_UPCOUNT_ENG_FRONT);
      String str1 = this.PREF_KEY_UPCOUNT_ENG_FRONT;
      j = i + 1;
      setUpdateCount(str1, j);
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.camerafirmware.CameraFirmware
 * JD-Core Version:    0.7.1
 */