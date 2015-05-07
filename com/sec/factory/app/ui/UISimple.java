package com.sec.factory.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.sec.android.app.camera.Camera;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;

public class UISimple
  extends UIBase
{
  private final int FINISH_BLACK_VIBRATE_TEST;
  private final int FINISH_FRONT_CAMERA_TEST;
  private final int FINISH_ORIENTATION_TEST;
  private final int FINISH_REAR_CAMERA_TEST;
  private final int FINISH_VIBRATE_TEST;
  private final String KEY_FRONT_PHOTO_PATH;
  private final String KEY_PHOTO_PATH;
  private final String KEY_REAR_PHOTO_PATH;
  private final int REQUEST_BLACK_VIBRATE_TEST = 0;
  private final int REQUEST_FRONT_CAMERA_TEST;
  private final int REQUEST_ORIENTATION_TEST;
  private final int REQUEST_REAR_CAMERA_TEST;
  private final int REQUEST_VIBRATE_TEST;
  private boolean isMotorSupport;
  private String mFrontCameraPhotoPath;
  private ModuleDevice mModuleDevice;
  private String mRearCameraPhotoPath;
  public BroadcastReceiver mReceiver;
  private String mTestCase;
  
  public UISimple()
  {
    super("UISimple", 0);
    this.REQUEST_VIBRATE_TEST = i;
    this.REQUEST_REAR_CAMERA_TEST = 2;
    this.REQUEST_FRONT_CAMERA_TEST = 3;
    this.REQUEST_ORIENTATION_TEST = 4;
    this.FINISH_BLACK_VIBRATE_TEST = 0;
    this.FINISH_VIBRATE_TEST = i;
    this.FINISH_REAR_CAMERA_TEST = 2;
    this.FINISH_FRONT_CAMERA_TEST = 3;
    this.FINISH_ORIENTATION_TEST = 4;
    this.KEY_PHOTO_PATH = "data_filepath";
    this.KEY_REAR_PHOTO_PATH = "mega_filepath";
    this.KEY_FRONT_PHOTO_PATH = "vga_filepath";
    this.mTestCase = Support.FactoryTestMenu.getTestCase(FactoryTestManager.convertorID_XML((byte)0));
    if ((this.mTestCase != null) && (this.mTestCase.contains("MOTOR"))) {}
    for (;;)
    {
      this.isMotorSupport = i;
      this.mReceiver = new BroadcastReceiver()
      {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
        {
          String str = paramAnonymousIntent.getAction();
          if ("com.android.samsungtest.CAMERA_GOOD".equals(str)) {
            FtUtil.log_d(UISimple.this.CLASS_NAME, "onReceive", "CAMERA_GOOD");
          }
          while (!"com.android.samsungtest.CAMERA_BAD".equals(str)) {
            return;
          }
          FtUtil.log_d(UISimple.this.CLASS_NAME, "onReceive", "CAMERA_BAD");
        }
      };
      return;
      i = 0;
    }
  }
  
  private void finishKeyCode()
  {
    FactoryTestManager.getItemResult(FactoryTestManager.getItemPosition_ID(this.TEST_ID));
    finish();
  }
  
  private void start(int paramInt)
  {
    switch (paramInt)
    {
    }
    do
    {
      setTestResult((byte)80);
      finish();
      return;
      if ((this.mTestCase.contains("LED")) || (this.mTestCase.contains("RGB_ONLY")))
      {
        if ((this.isMotorSupport) && (!this.mModuleDevice.isVibrating())) {
          this.mModuleDevice.startVibrate(2147483647L);
        }
        startActivityForResult(new Intent(this, UILedTest.class), 0);
        return;
      }
      if ((this.mTestCase.contains("MOTOR")) || (this.mTestCase.contains("RGB_ONLY")))
      {
        if ((this.isMotorSupport) && (!this.mModuleDevice.isVibrating())) {
          this.mModuleDevice.startVibrate(2147483647L);
        }
        startActivityForResult(new Intent(this, UIVibrate.class), 1);
        return;
      }
      if (this.mTestCase.contains("REAR_CAM"))
      {
        Intent localIntent3 = new Intent(this, Camera.class);
        localIntent3.putExtra("camera_id", 0);
        localIntent3.putExtra("ommision_test", true);
        startActivityForResult(localIntent3, 2);
        return;
      }
      if (this.mTestCase.contains("FRONT_CAM"))
      {
        Intent localIntent2 = new Intent(this, Camera.class);
        localIntent2.putExtra("camera_id", 1);
        localIntent2.putExtra("ommision_test", true);
        startActivityForResult(localIntent2, 3);
        return;
      }
    } while (!this.mTestCase.contains("ORIEN"));
    Intent localIntent1 = new Intent(this, UIOrientation.class);
    localIntent1.putExtra("mega_filepath", this.mRearCameraPhotoPath);
    localIntent1.putExtra("vga_filepath", this.mFrontCameraPhotoPath);
    startActivityForResult(localIntent1, 4);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    switch (paramInt1)
    {
    default: 
      return;
    case 0: 
      if (paramInt2 == -1)
      {
        start(1);
        return;
      }
      ModuleDevice.instance(this).stopVibrate();
      finishKeyCode();
      return;
    case 1: 
      if (paramInt2 == -1)
      {
        if (this.isMotorSupport) {
          this.mModuleDevice.stopVibrate();
        }
        start(2);
        return;
      }
      ModuleDevice.instance(this).stopVibrate();
      finishKeyCode();
      return;
    case 2: 
      if (paramIntent != null) {}
      for (this.mRearCameraPhotoPath = paramIntent.getStringExtra("data_filepath"); (paramInt2 == -1) && (this.mRearCameraPhotoPath != null); this.mRearCameraPhotoPath = null)
      {
        start(3);
        return;
      }
      finishKeyCode();
      return;
    case 3: 
      if (paramIntent != null) {}
      for (this.mFrontCameraPhotoPath = paramIntent.getStringExtra("data_filepath"); (paramInt2 == -1) && (this.mFrontCameraPhotoPath != null); this.mFrontCameraPhotoPath = null)
      {
        start(4);
        return;
      }
      finishKeyCode();
      return;
    }
    if (paramInt2 == -1) {
      setTestResult((byte)80);
    }
    for (;;)
    {
      finish();
      return;
      setTestResultFailCase((byte)0);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mModuleDevice = ModuleDevice.instance(this);
    int i = getIntent().getByteExtra("requestCode", (byte)0);
    if (46 == i)
    {
      setTestId(i);
      this.mTestCase = Support.FactoryTestMenu.getTestCase(FactoryTestManager.convertorID_XML((byte)46));
      FtUtil.log_d("UISimple", "onCreate", "Act as LCD TEST, TEST_ID = " + i);
    }
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.android.samsungtest.CAMERA_GOOD");
    localIntentFilter.addAction("com.android.samsungtest.CAMERA_BAD");
    registerReceiver(this.mReceiver, localIntentFilter);
    if ((this.mTestCase != null) && (this.mTestCase.contains("LED"))) {
      start(0);
    }
    for (;;)
    {
      FtUtil.log_v("UISimple", "onCreate", "Test case : " + this.mTestCase);
      return;
      start(1);
    }
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    unregisterReceiver(this.mReceiver);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UISimple
 * JD-Core Version:    0.7.1
 */