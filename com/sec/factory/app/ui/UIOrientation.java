package com.sec.factory.app.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.sec.factory.app.factorytest.FactoryTestManager;
import com.sec.factory.app.factorytest.FactoryTestPhone;
import com.sec.factory.modules.ModulePower;
import com.sec.factory.modules.ModuleSensor;
import com.sec.factory.modules.SensorCalculator;
import com.sec.factory.modules.SensorNotification;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.TestCase;
import java.io.File;

public class UIOrientation
  extends UIBase
{
  public static String mOrientationType = "DEFAULT";
  public static String typeDevice = "phone";
  private int ANGLE_BASE_X = 0;
  private int ANGLE_BASE_Y = 0;
  private int ANGLE_BASE_Z = 0;
  private final boolean DEBUG = true;
  private final int DISPLAY_IMAGES = 98;
  private boolean FLAG_TEST_EXIT = false;
  private final int GET_IMAGE_TYPE_FILEPATH = 1;
  private final int GET_IMAGE_TYPE_RAWDATA = 2;
  private boolean IS_SUPPORT_CP_ACCEL = false;
  private final int LANDSCAPE_MODE_LEFT = 2;
  private final int LANDSCAPE_MODE_RIGHT = 1;
  private final int LOAD_IMAGES = 99;
  private final float MAX_DEVIATION_FROM_GRAVITY = 1.5F;
  private final int MAX_TILT = 65;
  private final String MESSAGE_0 = "Loading images...";
  private final String MESSAGE_1 = "Tilt toward body\nMore than 30 degrees";
  private final String MESSAGE_2_1 = "PASS!";
  private final String MESSAGE_2_2 = "Turn left or right\nMore than 60 degrees";
  private final String MESSAGE_Check_CP_Sensor = "Check CP Sensor";
  private int MESSAGE_TYPE_0 = 0;
  private int MESSAGE_TYPE_1 = 1;
  private int MESSAGE_TYPE_2 = 2;
  private int MESSAGE_TYPE_3 = 3;
  private int MESSAGE_TYPE_4 = 4;
  private final float MIN_ABS_ACCELERATION = 1.5F;
  private boolean POPUP_FLAG_TEST_XY;
  private boolean POPUP_FLAG_TEST_Z;
  private final int PORTRAIT_MODE = 0;
  private final int TESTSTATUS_STATUS_CP_PENDING_XY = 8;
  private final int TESTSTATUS_STATUS_CP_PENDING_Z = 6;
  private final int TESTSTATUS_STATUS_CP_TEST_XY = 9;
  private final int TESTSTATUS_STATUS_CP_TEST_Z = 7;
  private final int TESTSTATUS_STATUS_FINISH = 5;
  private final int TESTSTATUS_STATUS_PENDING_XY = 3;
  private final int TESTSTATUS_STATUS_PENDING_Z = 1;
  private final int TESTSTATUS_STATUS_TEST_XY = 4;
  private final int TESTSTATUS_STATUS_TEST_Z = 2;
  private final int WHAT_CP_START = 3 + SensorNotification.WHAT_NOTI_SENSOR_MAX;
  private final int WHAT_EXIT = 2 + SensorNotification.WHAT_NOTI_SENSOR_MAX;
  private final int WHAT_FIRST = 1 + SensorNotification.WHAT_NOTI_SENSOR_MAX;
  private float deviation = 0.0F;
  private boolean first_popup = false;
  private int mAngleCurrXY = 0;
  private int mAngleCurrZ = 0;
  private int mAngleDeltaZ = 0;
  private int mButtonLedTime;
  private Bitmap[] mCameraBitmap = new Bitmap[2];
  private String[] mCoordinates = null;
  private String mFilePathMainCam;
  private String mFilePathSubCam;
  private Thread mGetImageThread = null;
  private int mGetImageType = 1;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      FtUtil.log_e(UIOrientation.this.CLASS_NAME, "mHandler-handleMessage", "mStatus=[" + UIOrientation.this.mStatus + "]" + "msg.what=[" + paramAnonymousMessage.what + "]");
      if (paramAnonymousMessage.what == 99) {
        UIOrientation.this.popupShow(UIOrientation.this.MESSAGE_TYPE_0);
      }
      label196:
      label343:
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
                    return;
                    if (paramAnonymousMessage.what == 98)
                    {
                      UIOrientation.this.displayImage();
                      UIOrientation.access$402(UIOrientation.this, true);
                      UIOrientation.this.controlAccelerometerSensor(1);
                      UIOrientation.this.mHandler.sendEmptyMessage(UIOrientation.this.WHAT_FIRST);
                      return;
                    }
                    if ((paramAnonymousMessage.what != SensorNotification.WHAT_NOTI_SENSOR_UPDATAE) || (!UIOrientation.this.FLAG_TEST_EXIT)) {
                      break label1857;
                    }
                    if (!UIOrientation.this.isStatusCPSensorPending()) {
                      if (UIOrientation.this.misSimpleTestAccSysfs)
                      {
                        UIOrientation.access$1002(UIOrientation.this, UIOrientation.this.mModuleSensor.getData(ModuleSensor.ID_FILE____ACCELEROMETER_N_ANGLE));
                        if (UIOrientation.this.mCoordinates != null)
                        {
                          if (UIOrientation.this.isStatusCPSensorPending()) {
                            break label586;
                          }
                          UIOrientation.access$1202(UIOrientation.this, "AP [Coordinates] X:" + UIOrientation.this.mCoordinates[2] + "\t, Y:" + UIOrientation.this.mCoordinates[3] + "\t, Z:" + UIOrientation.this.mCoordinates[4] + "\n" + "AP [___Angle___] X:" + UIOrientation.this.mCoordinates[5] + "\t, Y:" + UIOrientation.this.mCoordinates[6] + "\t, Z:" + UIOrientation.this.mCoordinates[7]);
                          if (UIOrientation.this.mStatus != 2) {
                            break label727;
                          }
                          UIOrientation.access$1284(UIOrientation.this, "\n[AP Angle__Base] Z:" + UIOrientation.this.ANGLE_BASE_Z);
                        }
                      }
                    }
                    for (;;)
                    {
                      UIOrientation.this.mTextCoordinates.setText(UIOrientation.this.mSensorValue);
                      if (UIOrientation.this.mCoordinates == null) {
                        break;
                      }
                      if (UIOrientation.this.mStatus != 2) {
                        break label900;
                      }
                      if (UIOrientation.this.POPUP_FLAG_TEST_Z == true) {
                        UIOrientation.access$1702(UIOrientation.this, false);
                      }
                      if (!UIOrientation.this.startZTest30degree(UIOrientation.this.mCoordinates)) {
                        break;
                      }
                      FtUtil.log_e(UIOrientation.this.CLASS_NAME, "mHandler-handleMessage", "Z => Base : " + UIOrientation.this.ANGLE_BASE_Z + ", Curr : " + UIOrientation.this.mAngleCurrZ);
                      UIOrientation.this.popupShow(UIOrientation.this.MESSAGE_TYPE_2);
                      UIOrientation.access$002(UIOrientation.this, 3);
                      return;
                      UIOrientation.access$1002(UIOrientation.this, UIOrientation.this.mModuleSensor.getData(ModuleSensor.ID_MANAGER_ACCELEROMETER_N_ANGLE));
                      break label196;
                      UIOrientation.access$1002(UIOrientation.this, UIOrientation.this.mModuleSensor.getData(ModuleSensor.ID_INTENT__CP_ACCELEROMETER));
                      break label196;
                      if (UIOrientation.this.isStatusCPSensorPending() != true) {
                        break label343;
                      }
                      UIOrientation.access$1202(UIOrientation.this, "CP [Coordinates] X:" + UIOrientation.this.mCoordinates[2] + "\t, Y:" + UIOrientation.this.mCoordinates[3] + "\t, Z:" + UIOrientation.this.mCoordinates[4] + "\n" + "CP [___Angle___] X:" + UIOrientation.this.mCoordinates[5] + "\t, Y:" + UIOrientation.this.mCoordinates[6] + "\t, Z:" + UIOrientation.this.mCoordinates[7]);
                      break label343;
                      if (UIOrientation.this.mStatus == 4) {
                        UIOrientation.access$1284(UIOrientation.this, "\n[AP Angle__Base] X:" + UIOrientation.this.ANGLE_BASE_X + "\t, Y:" + UIOrientation.this.ANGLE_BASE_Y);
                      } else if (UIOrientation.this.mStatus == 7) {
                        UIOrientation.access$1284(UIOrientation.this, "\n[CP Angle__Base] Z:" + UIOrientation.this.ANGLE_BASE_Z);
                      } else if (UIOrientation.this.mStatus == 9) {
                        UIOrientation.access$1284(UIOrientation.this, "\n[CP Angle__Base] X:" + UIOrientation.this.ANGLE_BASE_X + "\t, Y:" + UIOrientation.this.ANGLE_BASE_Y);
                      }
                    }
                    if (UIOrientation.this.mStatus != 4) {
                      break;
                    }
                    if (UIOrientation.this.POPUP_FLAG_TEST_XY == true) {
                      UIOrientation.access$2102(UIOrientation.this, false);
                    }
                  } while (!UIOrientation.this.startXYTest60degree(UIOrientation.this.mCoordinates));
                  FtUtil.log_e(UIOrientation.this.CLASS_NAME, "mHandler-handleMessage", "X => Base : " + UIOrientation.this.ANGLE_BASE_X);
                  FtUtil.log_e(UIOrientation.this.CLASS_NAME, "mHandler-handleMessage", "Y => Base : " + UIOrientation.this.ANGLE_BASE_Y);
                  if (!UIOrientation.this.IS_SUPPORT_CP_ACCEL)
                  {
                    UIOrientation.access$002(UIOrientation.this, 5);
                    if (UIOrientation.this.mOrientationStatus == 1)
                    {
                      FtUtil.log_i(UIOrientation.this.CLASS_NAME, "mHandler-handleMessage", "AP - LANDSCAPE_MODE_RIGHT");
                      UIOrientation.this.setRequestedOrientation(8);
                      UIOrientation.this.mlinear[0].setVisibility(8);
                      UIOrientation.this.mlinear[1].setVisibility(0);
                      UIOrientation.this.mlinear[2].setVisibility(8);
                    }
                    for (;;)
                    {
                      sendEmptyMessageDelayed(UIOrientation.this.WHAT_EXIT, 1000L);
                      UIOrientation.access$402(UIOrientation.this, false);
                      return;
                      if (UIOrientation.this.mOrientationStatus == 2)
                      {
                        FtUtil.log_i(UIOrientation.this.CLASS_NAME, "mHandler-handleMessage", "AP - LANDSCAPE_MODE_LEFT");
                        UIOrientation.this.setRequestedOrientation(0);
                        UIOrientation.this.mlinear[0].setVisibility(8);
                        UIOrientation.this.mlinear[1].setVisibility(8);
                        UIOrientation.this.mlinear[2].setVisibility(0);
                      }
                    }
                  }
                } while (UIOrientation.this.IS_SUPPORT_CP_ACCEL != true);
                UIOrientation.this.controlAccelerometerSensor(0);
                try
                {
                  Thread.sleep(500L);
                  UIOrientation.this.controlAccelerometerSensor(3);
                  if (UIOrientation.this.mOrientationStatus == 1)
                  {
                    FtUtil.log_i(UIOrientation.this.CLASS_NAME, "mHandler-handleMessage", "CP - LANDSCAPE_MODE_RIGHT");
                    UIOrientation.this.setRequestedOrientation(8);
                    UIOrientation.this.mlinear[0].setVisibility(8);
                    UIOrientation.this.mlinear[1].setVisibility(0);
                    UIOrientation.this.mlinear[2].setVisibility(8);
                    sendEmptyMessageDelayed(UIOrientation.this.WHAT_CP_START, 1000L);
                    UIOrientation.access$002(UIOrientation.this, 6);
                    return;
                  }
                }
                catch (Exception localException)
                {
                  for (;;)
                  {
                    localException.printStackTrace();
                    continue;
                    if (UIOrientation.this.mOrientationStatus == 2)
                    {
                      FtUtil.log_i(UIOrientation.this.CLASS_NAME, "mHandler-handleMessage", "CP - LANDSCAPE_MODE_LEFT");
                      UIOrientation.this.setRequestedOrientation(0);
                      UIOrientation.this.mlinear[0].setVisibility(8);
                      UIOrientation.this.mlinear[1].setVisibility(8);
                      UIOrientation.this.mlinear[2].setVisibility(0);
                    }
                  }
                }
                if (UIOrientation.this.mStatus != 7) {
                  break;
                }
                if (UIOrientation.this.POPUP_FLAG_TEST_Z == true) {
                  UIOrientation.access$1702(UIOrientation.this, false);
                }
              } while (!UIOrientation.this.startZTest30degree(UIOrientation.this.mCoordinates));
              FtUtil.log_e(UIOrientation.this.CLASS_NAME, "mHandler-handleMessage", "Z => Base : " + UIOrientation.this.ANGLE_BASE_Z + ", Curr : " + UIOrientation.this.mAngleCurrZ);
              UIOrientation.this.popupShow(UIOrientation.this.MESSAGE_TYPE_4);
              UIOrientation.access$002(UIOrientation.this, 8);
              return;
            } while (UIOrientation.this.mStatus != 9);
            if (UIOrientation.this.POPUP_FLAG_TEST_XY == true) {
              UIOrientation.access$2102(UIOrientation.this, false);
            }
          } while (!UIOrientation.this.startXYTest60degree(UIOrientation.this.mCoordinates));
          FtUtil.log_e(UIOrientation.this.CLASS_NAME, "mHandler-handleMessage", "X => Base : " + UIOrientation.this.ANGLE_BASE_X);
          FtUtil.log_e(UIOrientation.this.CLASS_NAME, "mHandler-handleMessage", "Y => Base : " + UIOrientation.this.ANGLE_BASE_Y);
          UIOrientation.access$002(UIOrientation.this, 5);
          UIOrientation.this.controlAccelerometerSensor(2);
          if (UIOrientation.this.mOrientationStatus == 1)
          {
            UIOrientation.this.setRequestedOrientation(8);
            UIOrientation.this.mlinear[0].setVisibility(8);
            UIOrientation.this.mlinear[1].setVisibility(0);
            UIOrientation.this.mlinear[2].setVisibility(8);
          }
          for (;;)
          {
            sendEmptyMessageDelayed(UIOrientation.this.WHAT_EXIT, 1000L);
            UIOrientation.access$402(UIOrientation.this, false);
            return;
            if (UIOrientation.this.mOrientationStatus == 2)
            {
              UIOrientation.this.setRequestedOrientation(0);
              UIOrientation.this.mlinear[0].setVisibility(8);
              UIOrientation.this.mlinear[1].setVisibility(8);
              UIOrientation.this.mlinear[2].setVisibility(0);
            }
          }
          if (paramAnonymousMessage.what == UIOrientation.this.WHAT_FIRST)
          {
            UIOrientation.this.popupShow(UIOrientation.this.MESSAGE_TYPE_1);
            UIOrientation.access$3002(UIOrientation.this, true);
            return;
          }
          if (paramAnonymousMessage.what != UIOrientation.this.WHAT_EXIT) {
            break;
          }
        } while (UIOrientation.this.mStatus != 5);
        UIOrientation.this.setResult(-1);
        UIOrientation.this.finish();
        return;
      } while (paramAnonymousMessage.what != UIOrientation.this.WHAT_CP_START);
      label586:
      label727:
      UIOrientation.this.setResumeOrientation();
      label900:
      label1857:
      UIOrientation.access$1702(UIOrientation.this, true);
      UIOrientation.access$2102(UIOrientation.this, true);
      UIOrientation.this.popupShow(UIOrientation.this.MESSAGE_TYPE_3);
      UIOrientation.access$3002(UIOrientation.this, true);
    }
  };
  private ImageView[] mImageMainCam = new ImageView[3];
  private ImageView[] mImageSubCam = new ImageView[3];
  private final int mLogLevel = Support.FactoryTestMenu.getLogLevel(FactoryTestManager.convertorID_XML((byte)0));
  private ModuleSensor mModuleSensor;
  private int mOrientationStatus = 0;
  private PopupWindow mPopup;
  private View mPopupview;
  private int mScreenWidth;
  private String mSensorValue = "";
  private int mStatus;
  private TextView mTextCoordinates;
  private TextView mTextMessage1;
  private TextView mTextMessage2;
  private TextView mTextMessage3;
  private float mTilt = 0.0F;
  private float magnitude = 0.0F;
  private final boolean misSimpleTestAccSysfs = Support.TestCase.getEnabled("IS_SIMPLE_TEST_ACC_SYSFS");
  private LinearLayout[] mlinear = new LinearLayout[3];
  
  public UIOrientation()
  {
    super("UIOrientation");
  }
  
  private void controlAccelerometerSensor(int paramInt)
  {
    if (paramInt == 1) {
      if (!this.mFlag_BackKey_Twice) {}
    }
    do
    {
      do
      {
        return;
        int[] arrayOfInt2;
        if (this.misSimpleTestAccSysfs)
        {
          arrayOfInt2 = new int[1];
          arrayOfInt2[0] = ModuleSensor.ID_FILE____ACCELEROMETER;
        }
        for (;;)
        {
          this.mModuleSensor.SensorOn(arrayOfInt2, this.mHandler, 100, this.mLogLevel);
          return;
          arrayOfInt2 = new int[1];
          arrayOfInt2[0] = ModuleSensor.ID_MANAGER_ACCELEROMETER_N_ANGLE;
        }
        if (paramInt == 0)
        {
          this.mModuleSensor.SensorOff();
          return;
        }
        if (paramInt != 3) {
          break;
        }
      } while (this.mFlag_BackKey_Twice);
      if (this.mFactoryPhone == null)
      {
        this.mFactoryPhone = new FactoryTestPhone(getApplicationContext());
        this.mFactoryPhone.bindSecPhoneService();
      }
      this.mFactoryPhone.requestCPsAccelerometer(true);
      int[] arrayOfInt1 = new int[1];
      arrayOfInt1[0] = ModuleSensor.ID_INTENT__CP_ACCELEROMETER;
      this.mModuleSensor.SensorOn(arrayOfInt1, this.mHandler, 100, this.mLogLevel);
      return;
    } while (paramInt != 2);
    if (this.mFactoryPhone == null)
    {
      this.mFactoryPhone = new FactoryTestPhone(getApplicationContext());
      this.mFactoryPhone.bindSecPhoneService();
    }
    this.mFactoryPhone.requestCPsAccelerometer(false);
    ModuleSensor localModuleSensor = this.mModuleSensor;
    localModuleSensor.SensorOff_Intent(ModuleSensor.ID_INTENT__CP_ACCELEROMETER);
  }
  
  private void displayImage()
  {
    if (this.mCameraBitmap[0] != null) {
      if (typeDevice.equals("tablet"))
      {
        this.mImageMainCam[0].setBackgroundDrawable(new BitmapDrawable(this.mCameraBitmap[0]));
        this.mImageMainCam[1].setBackgroundDrawable(new BitmapDrawable(this.mCameraBitmap[0]));
        this.mImageMainCam[2].setBackgroundDrawable(new BitmapDrawable(this.mCameraBitmap[0]));
      }
    }
    while (this.mCameraBitmap[1] != null) {
      if (typeDevice.equals("tablet"))
      {
        this.mImageSubCam[0].setBackgroundDrawable(new BitmapDrawable(this.mCameraBitmap[1]));
        this.mImageSubCam[1].setBackgroundDrawable(new BitmapDrawable(this.mCameraBitmap[1]));
        this.mImageSubCam[2].setBackgroundDrawable(new BitmapDrawable(this.mCameraBitmap[1]));
        return;
        this.mImageMainCam[0].setImageBitmap(this.mCameraBitmap[0]);
        this.mImageMainCam[1].setImageBitmap(this.mCameraBitmap[0]);
        this.mImageMainCam[2].setImageBitmap(this.mCameraBitmap[0]);
        continue;
        this.mImageMainCam[0].setBackgroundColor(-16711936);
        this.mImageMainCam[1].setBackgroundColor(-16711936);
        this.mImageMainCam[2].setBackgroundColor(-16711936);
      }
      else
      {
        this.mImageSubCam[0].setImageBitmap(this.mCameraBitmap[1]);
        this.mImageSubCam[1].setImageBitmap(this.mCameraBitmap[1]);
        this.mImageSubCam[2].setImageBitmap(this.mCameraBitmap[1]);
        return;
      }
    }
    this.mImageSubCam[0].setBackgroundColor(-16776961);
    this.mImageSubCam[1].setBackgroundColor(-16776961);
    this.mImageSubCam[2].setBackgroundColor(-16776961);
  }
  
  private void getImage()
  {
    if (this.mGetImageType == 1)
    {
      typeDevice = Support.Feature.getString("DEVICE_TYPE");
      if (typeDevice == null) {
        typeDevice = "phone";
      }
      this.mFilePathMainCam = getIntent().getStringExtra("mega_filepath");
      if ((this.mFilePathMainCam != null) && (new File(this.mFilePathMainCam).exists()))
      {
        i = Integer.parseInt(Support.TestCase.getString("SIMPLE_TEST_MEGACAM_SCALE_VALUE"));
        localOptions = new BitmapFactory.Options();
        localOptions.inSampleSize = i;
        localBitmap2 = BitmapFactory.decodeFile(this.mFilePathMainCam, localOptions);
        localMatrix2 = new Matrix();
        localMatrix2.postRotate(90.0F);
        this.mCameraBitmap[0] = Bitmap.createBitmap(localBitmap2, 0, 0, localBitmap2.getWidth(), localBitmap2.getHeight(), localMatrix2, true);
      }
      this.mFilePathSubCam = getIntent().getStringExtra("vga_filepath");
      if ((this.mFilePathSubCam != null) && (new File(this.mFilePathSubCam).exists()))
      {
        localBitmap1 = BitmapFactory.decodeFile(this.mFilePathSubCam);
        localMatrix1 = new Matrix();
        localMatrix1.postRotate(270.0F);
        localMatrix1.postScale(-1.0F, 1.0F);
        this.mCameraBitmap[1] = Bitmap.createBitmap(localBitmap1, 0, 0, localBitmap1.getWidth(), localBitmap1.getHeight(), localMatrix1, true);
      }
    }
    while (this.mGetImageType != 2)
    {
      int i;
      BitmapFactory.Options localOptions;
      Bitmap localBitmap2;
      Matrix localMatrix2;
      Bitmap localBitmap1;
      Matrix localMatrix1;
      return;
    }
  }
  
  private void initialize()
  {
    this.mTextCoordinates = ((TextView)findViewById(2131296498));
    this.mTextCoordinates.setVisibility(0);
    this.mlinear[0] = ((LinearLayout)findViewById(2131296499));
    this.mlinear[1] = ((LinearLayout)findViewById(2131296502));
    this.mlinear[2] = ((LinearLayout)findViewById(2131296505));
    this.mImageMainCam[0] = ((ImageView)findViewById(2131296500));
    this.mImageSubCam[0] = ((ImageView)findViewById(2131296501));
    this.mImageMainCam[1] = ((ImageView)findViewById(2131296504));
    this.mImageSubCam[1] = ((ImageView)findViewById(2131296503));
    this.mImageMainCam[2] = ((ImageView)findViewById(2131296506));
    this.mImageSubCam[2] = ((ImageView)findViewById(2131296507));
    this.mStatus = 1;
    this.mModuleSensor = ModuleSensor.instance(this);
    this.mButtonLedTime = ModulePower.instance(this).getTouchLedTime();
    ModulePower.instance(this).setTouchLedTime(-1);
    mOrientationType = Support.Feature.getString("TABLET_DEFAULT_ORIENTATION");
    this.mGetImageThread = new Thread(new Runnable()
    {
      public void run()
      {
        FtUtil.log_d(UIOrientation.this.CLASS_NAME, "Thread Run", "Start");
        UIOrientation.this.getImage();
        if (!UIOrientation.this.mGetImageThread.isInterrupted()) {
          UIOrientation.this.mHandler.sendEmptyMessage(98);
        }
        FtUtil.log_d(UIOrientation.this.CLASS_NAME, "Thread Run", "End");
      }
    });
    this.mGetImageThread.setPriority(10);
    this.mGetImageThread.start();
  }
  
  private void initializePopup()
  {
    this.mPopupview = View.inflate(this, 2130903081, null);
    this.mTextMessage1 = ((TextView)this.mPopupview.findViewById(2131296508));
    this.mTextMessage3 = ((TextView)this.mPopupview.findViewById(2131296509));
    this.mTextMessage2 = ((TextView)this.mPopupview.findViewById(2131296510));
  }
  
  private boolean isStatusCPSensorPending()
  {
    if (this.mStatus < 6) {}
    while (this.mStatus < 6) {
      return false;
    }
    FtUtil.log_e(this.CLASS_NAME, "isStatusCPSensorPending", "true");
    return true;
  }
  
  private void popupShow(int paramInt)
  {
    if (this.mPopup != null) {
      this.mPopup.dismiss();
    }
    if (paramInt == this.MESSAGE_TYPE_1)
    {
      this.mTextMessage1.setText("Tilt toward body\nMore than 30 degrees");
      this.mTextMessage1.setTextColor(-16777216);
      this.mTextMessage1.setTextSize(14.0F);
      this.mTextMessage2.setVisibility(8);
      this.mScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
      if (this.mScreenWidth >= 270) {
        break label379;
      }
    }
    label379:
    for (this.mPopup = new PopupWindow(this.mPopupview, 180, 120, false);; this.mPopup = new PopupWindow(this.mPopupview, 270, 180, false))
    {
      this.mPopup.showAtLocation(this.mlinear[this.mOrientationStatus], 17, 0, 0);
      FtUtil.log_d(this.CLASS_NAME, "popupShow", "done");
      return;
      if (paramInt == this.MESSAGE_TYPE_2)
      {
        this.mTextMessage1.setText("PASS!");
        this.mTextMessage1.setTextColor(-16776961);
        this.mTextMessage1.setTextSize(20.0F);
        this.mTextMessage2.setVisibility(0);
        this.mTextMessage2.setText("Turn left or right\nMore than 60 degrees");
        this.mTextMessage2.setTextSize(14.0F);
        break;
      }
      if (paramInt == this.MESSAGE_TYPE_0)
      {
        this.mTextMessage1.setText("Loading images...");
        this.mTextMessage1.setTextColor(-16776961);
        this.mTextMessage1.setTextSize(14.0F);
        this.mTextMessage2.setVisibility(8);
        break;
      }
      if (paramInt == this.MESSAGE_TYPE_3)
      {
        this.mTextMessage1.setVisibility(8);
        this.mTextMessage3.setVisibility(0);
        this.mTextMessage3.setText("Check CP Sensor");
        this.mTextMessage3.setTextSize(18.0F);
        this.mTextMessage2.setText("Tilt toward body\nMore than 30 degrees");
        break;
      }
      if (paramInt != this.MESSAGE_TYPE_4) {
        break;
      }
      this.mTextMessage1.setText("PASS!");
      this.mTextMessage1.setTextColor(-16776961);
      this.mTextMessage1.setTextSize(20.0F);
      this.mTextMessage1.setVisibility(0);
      this.mTextMessage3.setText("Check CP Sensor");
      this.mTextMessage2.setText("Turn left or right\nMore than 60 degrees");
      break;
    }
  }
  
  private void setResumeOrientation()
  {
    setRequestedOrientation(1);
    this.mlinear[0].setVisibility(0);
    this.mlinear[1].setVisibility(8);
    this.mlinear[2].setVisibility(8);
  }
  
  private boolean startXYTest60degree(String[] paramArrayOfString)
  {
    if (this.misSimpleTestAccSysfs)
    {
      this.mAngleCurrZ = Integer.valueOf(paramArrayOfString[7]).intValue();
      this.mAngleCurrXY = SensorCalculator.getAccelerometerAngleXY(Integer.valueOf(paramArrayOfString[5]).intValue(), Integer.valueOf(paramArrayOfString[6]).intValue());
    }
    do
    {
      this.mTilt = Math.abs(this.mAngleCurrZ);
      if (this.mTilt <= 65.0F) {
        break;
      }
      FtUtil.log_d(this.CLASS_NAME, "startXYTest60degree", "return => mTilt :" + this.mTilt);
      return false;
      this.mAngleCurrZ = Integer.valueOf(paramArrayOfString[7]).intValue();
      this.magnitude = Float.valueOf(paramArrayOfString[8]).floatValue();
      this.deviation = Float.valueOf(paramArrayOfString[9]).floatValue();
      this.mAngleCurrXY = Integer.valueOf(paramArrayOfString[10]).intValue();
    } while ((this.magnitude >= 1.5F) && (this.deviation <= 1.5F));
    FtUtil.log_d(this.CLASS_NAME, "startXYTest60degree", "return => magnitude :" + this.magnitude + ", deviation :" + this.deviation);
    return false;
    if ((typeDevice.equals("tablet")) && (!mOrientationType.equals("portrait")))
    {
      FtUtil.log_i(this.CLASS_NAME, "startXYTest60degree", "typeDevice : " + typeDevice + ", orientationType : " + mOrientationType);
      this.mAngleCurrXY = (-90 + this.mAngleCurrXY);
    }
    if (this.mAngleCurrXY < 0) {
      this.mAngleCurrXY = (360 + this.mAngleCurrXY);
    }
    FtUtil.log_i(this.CLASS_NAME, "startXYTest60degree", "mAngleCurrXY :" + this.mAngleCurrXY);
    boolean bool;
    if ((this.mAngleCurrXY > 60) && (this.mAngleCurrXY < 100))
    {
      this.mOrientationStatus = 1;
      bool = true;
    }
    for (;;)
    {
      return bool;
      if ((this.mAngleCurrXY > 260) && (this.mAngleCurrXY < 300))
      {
        this.mOrientationStatus = 2;
        bool = true;
      }
      else
      {
        this.mOrientationStatus = 0;
        bool = false;
      }
    }
  }
  
  private boolean startZTest30degree(String[] paramArrayOfString)
  {
    if (this.misSimpleTestAccSysfs) {
      this.mAngleCurrZ = Integer.valueOf(paramArrayOfString[7]).intValue();
    }
    do
    {
      this.mTilt = Math.abs(this.mAngleCurrZ);
      if (this.mTilt <= 65.0F) {
        break;
      }
      FtUtil.log_d(this.CLASS_NAME, "startZTest30degree", "return => mTilt :" + this.mTilt);
      return false;
      this.mAngleCurrZ = Integer.valueOf(paramArrayOfString[7]).intValue();
      this.magnitude = Float.valueOf(paramArrayOfString[8]).floatValue();
      this.deviation = Float.valueOf(paramArrayOfString[9]).floatValue();
    } while ((this.magnitude >= 1.5F) && (this.deviation <= 1.5F));
    FtUtil.log_d(this.CLASS_NAME, "startZTest30degree", "return => magnitude :" + this.magnitude + ", deviation :" + this.deviation);
    return false;
    if (this.ANGLE_BASE_Z > this.mAngleCurrZ) {}
    for (this.mAngleDeltaZ = (this.ANGLE_BASE_Z - this.mAngleCurrZ);; this.mAngleDeltaZ = (this.mAngleCurrZ - this.ANGLE_BASE_Z))
    {
      FtUtil.log_i(this.CLASS_NAME, "startZTest30degree", "mAnglePrevZ :" + this.ANGLE_BASE_Z + ", mAngleCurrZ :" + this.mAngleCurrZ + ", mDeltaAngleZ :" + this.mAngleDeltaZ);
      int i = this.mAngleDeltaZ;
      boolean bool = false;
      if (i > 30) {
        bool = true;
      }
      return bool;
    }
  }
  
  public void onAttachedToWindow()
  {
    FtUtil.log_e(this.CLASS_NAME, "onAttachedToWindow", "...");
    super.onAttachedToWindow();
    this.mHandler.sendEmptyMessage(99);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903080);
    FtUtil.log_d(this.CLASS_NAME, "onCreate", "misSimpleTestAccSysfs : " + this.misSimpleTestAccSysfs);
    FtUtil.log_d(this.CLASS_NAME, "onCreate", "mLogLevel : " + this.mLogLevel);
    initializePopup();
    initialize();
    FtUtil.setRemoveSystemUI(getWindow(), true);
  }
  
  protected void onDestroy()
  {
    ModulePower.instance(this).setTouchLedTime(this.mButtonLedTime);
    if ((this.mGetImageThread != null) && (this.mGetImageThread.isAlive())) {
      this.mGetImageThread.interrupt();
    }
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      return super.onKeyDown(paramInt, paramKeyEvent);
      FtUtil.log_e(this.CLASS_NAME, "onKeyDown", null);
      if (this.mCoordinates != null)
      {
        if (this.first_popup) {
          if (this.mStatus == 1)
          {
            FtUtil.log_e(this.CLASS_NAME, "onKeyDown", "TESTSTATUS_STATUS_PENDING_Z");
            this.mStatus = 2;
            this.ANGLE_BASE_Z = Integer.valueOf(this.mCoordinates[7]).intValue();
            this.mPopup.dismiss();
          }
          else if (this.mStatus == 3)
          {
            FtUtil.log_e(this.CLASS_NAME, "onKeyDown", "TESTSTATUS_STATUS_PENDING_XY");
            this.mStatus = 4;
            this.ANGLE_BASE_X = Integer.valueOf(this.mCoordinates[5]).intValue();
            this.ANGLE_BASE_Y = Integer.valueOf(this.mCoordinates[6]).intValue();
            this.mPopup.dismiss();
          }
          else if (this.mStatus == 6)
          {
            FtUtil.log_e(this.CLASS_NAME, "onKeyDown", "TESTSTATUS_STATUS_CP_PENDING_Z");
            this.mStatus = 7;
            this.ANGLE_BASE_Z = Integer.valueOf(this.mCoordinates[7]).intValue();
            this.mPopup.dismiss();
          }
          else if (this.mStatus == 8)
          {
            FtUtil.log_e(this.CLASS_NAME, "onKeyDown", "TESTSTATUS_STATUS_CP_PENDING_XY");
            this.mStatus = 9;
            this.ANGLE_BASE_X = Integer.valueOf(this.mCoordinates[5]).intValue();
            this.ANGLE_BASE_Y = Integer.valueOf(this.mCoordinates[6]).intValue();
            this.mPopup.dismiss();
          }
        }
      }
      else {
        FtUtil.log_e(this.CLASS_NAME, "onKeyDown", "mCoordinates == null");
      }
    }
  }
  
  public void onPause()
  {
    super.onPause();
    if (this.mHandler.hasMessages(99)) {
      this.mHandler.removeMessages(99);
    }
    if (this.mHandler.hasMessages(98)) {
      this.mHandler.removeMessages(98);
    }
    if (this.mHandler.hasMessages(SensorNotification.WHAT_NOTI_SENSOR_UPDATAE)) {
      this.mHandler.removeMessages(SensorNotification.WHAT_NOTI_SENSOR_UPDATAE);
    }
    if (this.mHandler.hasMessages(this.WHAT_FIRST)) {
      this.mHandler.removeMessages(this.WHAT_FIRST);
    }
    if (this.mHandler.hasMessages(this.WHAT_EXIT)) {
      this.mHandler.removeMessages(this.WHAT_EXIT);
    }
    if (this.mHandler.hasMessages(this.WHAT_CP_START)) {
      this.mHandler.removeMessages(this.WHAT_CP_START);
    }
    this.FLAG_TEST_EXIT = false;
    if (!this.IS_SUPPORT_CP_ACCEL) {
      controlAccelerometerSensor(0);
    }
    for (;;)
    {
      if (this.mPopup != null) {
        this.mPopup.dismiss();
      }
      return;
      if (this.IS_SUPPORT_CP_ACCEL == true) {
        if (this.mStatus < 6) {
          controlAccelerometerSensor(0);
        } else if (this.mStatus >= 6) {
          controlAccelerometerSensor(2);
        }
      }
    }
  }
  
  public void onResume()
  {
    super.onResume();
    this.POPUP_FLAG_TEST_Z = true;
    this.POPUP_FLAG_TEST_XY = true;
    this.IS_SUPPORT_CP_ACCEL = Support.Feature.getBoolean("SUPPORT_CP_ACCELEROMETER");
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIOrientation
 * JD-Core Version:    0.7.1
 */