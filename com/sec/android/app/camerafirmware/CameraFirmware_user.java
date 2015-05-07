package com.sec.android.app.camerafirmware;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class CameraFirmware_user
  extends CameraFirmware
{
  View.OnClickListener mClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      Log.i("CameraFirmware_user", "Button Clicked ");
      switch (paramAnonymousView.getId())
      {
      default: 
        return;
      case 2131296273: 
        if (CameraFirmware_user.this.mCurrentToast != null) {
          CameraFirmware_user.this.mCurrentToast.cancel();
        }
        if (CameraFirmware_user.this.mCameraDevice == null) {
          break;
        }
      }
      try
      {
        Log.i("CameraFirmware_user", "mCameraDevice.setFirmwareMode(CameraDeviceController.FIRMWAREMODE_VERSION)");
        CameraFirmware_user.this.mCameraDevice.setFirmwareMode(1);
        label94:
        CameraFirmware_user.this.mCamFirmMgr = CameraFirmware_user.this.mRear;
        String str3 = "Rear\nCam FW Ver : " + CameraFirmware_user.this.mCamFirmMgr.getCamFWVer() + "\n" + "Phone FW Ver : " + CameraFirmware_user.this.mCamFirmMgr.getPhoneFWVer();
        CameraFirmware_user.this.mCamFirmMgr = CameraFirmware_user.this.mFront;
        String str4 = str3 + "\n\nFront\nCam FW Ver : " + CameraFirmware_user.this.mCamFirmMgr.getCamFWVer() + "\n" + "Phone FW Ver : " + CameraFirmware_user.this.mCamFirmMgr.getPhoneFWVer();
        CameraFirmware_user.access$002(CameraFirmware_user.this, Toast.makeText(CameraFirmware_user.this, str4, 1));
        CameraFirmware_user.this.mCurrentToast.show();
        return;
        if (paramAnonymousView.getId() == 2131296274) {
          CameraFirmware_user.this.mCamFirmMgr = CameraFirmware_user.this.mRear;
        }
        for (;;)
        {
          if (CameraFirmware_user.this.mCameraDevice != null) {}
          try
          {
            Log.i("CameraFirmware_user", "mCameraDevice.setFirmwareMode(CameraDeviceController.FIRMWAREMODE_VERSION)");
            CameraFirmware_user.this.mCameraDevice.setFirmwareMode(1);
            try
            {
              label314:
              if (!CameraFirmware_user.this.ChkUserFirmwareFile()) {
                break label565;
              }
              if (!CameraFirmware_user.this.getCamFWVendor().equals(CameraFirmware_user.this.getPhoneFWVendor())) {
                break label547;
              }
              Log.i("CameraFirmware_user", "valid vendor");
              switch (CameraFirmware_user.this.IsNewFirmwareDate())
              {
              case -1: 
                Log.i("CameraFirmware_user", "SDCARD: This is the latest version..");
                CameraFirmware_user.this.dialogErrorPopup(2131165199);
                return;
              }
            }
            catch (Exception localException)
            {
              Log.e("CameraFirmware_user", "Something goes wrong");
              CameraFirmware_user.this.dialogErrorPopup(2131165198);
              return;
            }
            CameraFirmware_user.this.mCamFirmMgr = CameraFirmware_user.this.mFront;
            continue;
            Log.i("CameraFirmware_user", "SDCARD: Updating New Firmwareversion...");
            CameraFirmware_user.access$102(CameraFirmware_user.this, CameraFirmware_user.this.DialogPopup(2131165200));
            CameraFirmware_user.this.mPopup.show();
            return;
            Log.i("CameraFirmware_user", "SDCARD: Same date..");
            if (CameraFirmware_user.this.IsNewFirmwareVersion())
            {
              Log.i("CameraFirmware_user", "SDCARD: Updating New Firmwareversion...");
              CameraFirmware_user.access$102(CameraFirmware_user.this, CameraFirmware_user.this.DialogPopup(2131165200));
              CameraFirmware_user.this.mPopup.show();
              return;
            }
            Log.i("CameraFirmware_user", "SDCARD: This is the latest version..");
            CameraFirmware_user.this.dialogErrorPopup(2131165199);
            return;
            label547:
            Log.e("CameraFirmware_user", "SDCARD: Invalid vendor");
            CameraFirmware_user.this.dialogErrorPopup(2131165197);
            return;
            label565:
            if (CameraFirmware_user.this.getCamFWVendor().equals(CameraFirmware_user.this.getPhoneFWVendor())) {
              Log.i("CameraFirmware_user", "valid vendor");
            }
            switch (CameraFirmware_user.this.IsNewFirmwareDate())
            {
            case -1: 
              Log.i("CameraFirmware_user", "This is the latest version..");
              CameraFirmware_user.this.dialogErrorPopup(2131165199);
              return;
            case 1: 
              Log.i("CameraFirmware_user", "Updating New Firmwareversion...");
              CameraFirmware_user.this.mCamFirmMgr.setManageMode(FirmwareFileMgr.CAM_FLAG_FIRMWARE_UPDATE);
              CameraFirmware_user.this.showUpdateProgress();
              CameraFirmware_user.this.startUpdateThread();
              return;
            case 0: 
              Log.i("CameraFirmware_user", "Same date..");
              if (CameraFirmware_user.this.IsNewFirmwareVersion())
              {
                Log.i("CameraFirmware_user", "Updating New Firmwareversion...");
                CameraFirmware_user.this.mCamFirmMgr.setManageMode(FirmwareFileMgr.CAM_FLAG_FIRMWARE_UPDATE);
                CameraFirmware_user.this.showUpdateProgress();
                CameraFirmware_user.this.startUpdateThread();
                return;
              }
              Log.i("CameraFirmware_user", "This is the latest version..");
              CameraFirmware_user.this.dialogErrorPopup(2131165199);
              return;
              Log.e("CameraFirmware_user", "Invalid vendor");
              CameraFirmware_user.this.dialogErrorPopup(2131165197);
              return;
              if (CameraFirmware_user.this.mCurrentToast != null) {
                CameraFirmware_user.this.mCurrentToast.cancel();
              }
              if (CameraFirmware_user.this.mCameraDevice != null) {}
              try
              {
                Log.i("CameraFirmware_user", "mCameraDevice.setFirmwareMode(CameraDeviceController.FIRMWAREMODE_VERSION)");
                CameraFirmware_user.this.mCameraDevice.setFirmwareMode(1);
                label821:
                CameraFirmware_user.this.mCamFirmMgr = CameraFirmware_user.this.mRear;
                String str1 = "Rear\nPhone FW Ver : " + CameraFirmware_user.this.mCamFirmMgr.getPhoneFWVer();
                CameraFirmware_user.this.mCamFirmMgr = CameraFirmware_user.this.mFront;
                String str2 = str1 + "\n\nFront\nPhone FW Ver : " + CameraFirmware_user.this.mCamFirmMgr.getPhoneFWVer();
                CameraFirmware_user.access$002(CameraFirmware_user.this, Toast.makeText(CameraFirmware_user.this, str2, 1));
                CameraFirmware_user.this.mCurrentToast.show();
                return;
              }
              catch (IllegalArgumentException localIllegalArgumentException1)
              {
                break label821;
              }
            }
          }
          catch (IllegalArgumentException localIllegalArgumentException2)
          {
            break label314;
          }
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException3)
      {
        break label94;
        return;
      }
    }
  };
  private Toast mCurrentToast = null;
  private AlertDialog mErrorPopup = null;
  private AlertDialog mPopup = null;
  
  private AlertDialog DialogPopup(int paramInt)
  {
    Log.v("CameraFirmware_user", "DialogPopup");
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
        CameraFirmware_user.this.mCamFirmMgr.setManageMode(FirmwareFileMgr.CAM_FLAG_FIRMWARE_UPDATE);
        CameraFirmware_user.this.showUpdateProgress();
        CameraFirmware_user.this.startUpdateThread();
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
    Log.v("CameraFirmware_user", "dialogErrorPopup");
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
        CameraFirmware_user.this.finish();
      }
    });
    localBuilder.setCancelable(false);
    this.mErrorPopup = localBuilder.create();
    this.mErrorPopup.show();
  }
  
  private void init()
  {
    findViewById(2131296273).setOnClickListener(this.mClickListener);
    findViewById(2131296274).setOnClickListener(this.mClickListener);
    findViewById(2131296275).setOnClickListener(this.mClickListener);
    findViewById(2131296276).setOnClickListener(this.mClickListener);
    this.mRear.resetFWInfo();
    this.mFront.resetFWInfo();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903043);
    init();
  }
  
  protected void updateFirmwareUpdateCount()
  {
    int j;
    if (this.mCamFirmMgr == this.mRear)
    {
      int k = getUpdateCount(this.PREF_KEY_UPCOUNT_USER);
      String str2 = this.PREF_KEY_UPCOUNT_USER;
      j = k + 1;
      setUpdateCount(str2, j);
    }
    for (;;)
    {
      Log.e("CameraFirmware_user", "updateFirmwareUpdateCount - PREF_KEY_UPCOUNT_USER:[" + j + "]");
      return;
      int i = getUpdateCount(this.PREF_KEY_UPCOUNT_USER_FRONT);
      String str1 = this.PREF_KEY_UPCOUNT_USER_FRONT;
      j = i + 1;
      setUpdateCount(str1, j);
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.camerafirmware.CameraFirmware_user
 * JD-Core Version:    0.7.1
 */