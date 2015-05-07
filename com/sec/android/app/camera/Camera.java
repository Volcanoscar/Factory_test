package com.sec.android.app.camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.location.Location;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.MediaStore.Images.Media;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.sec.android.app.camera.Interface.Capturer;
import com.sec.android.app.camera.framework.CameraSettings;
import com.sec.android.app.camera.framework.FocusRectangle;
import com.sec.android.app.camera.framework.ShutterButton;
import com.sec.android.app.camera.framework.ShutterButton.OnShutterButtonListener;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class Camera
  extends Activity
  implements SurfaceHolder.Callback, ShutterButton.OnShutterButtonListener
{
  private static final String DIRECTORY_CAMERA_DATA = Environment.getDataDirectory().toString() + "/log/DCIM/Camera";
  private static final String DIRECTORY_CAMERA_SDCARD = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/Camera";
  private static ContentResolver mContentResolver;
  public static String typeDevice = "phone";
  private final String DEFAULT_IMEI = "357858010034783";
  private final int FINISH_CAMCORDER_PREVIEW_TEST = 1;
  private final int FINISH_POSTVIEW_TEST = 2;
  private final String IMEI = "ril.IMEI";
  private final int REQUEST_CAMCORDER_PREVIEW_TEST = 1;
  private final int REQUEST_POSTVIEW_TEST = 2;
  private boolean bCheckDTP = false;
  private boolean bDoneDTP = true;
  private boolean bEnablePreviewCb = false;
  private boolean bSentAck = false;
  private boolean bUseSdcard = true;
  private int cameraType = -1;
  public byte[] capturedData;
  private CameraStorage cs;
  private String filePath = null;
  private int mAF_Fail_Count = 0;
  private AutoFocusCallback mAutoFocusCallback = new AutoFocusCallback(null);
  private android.hardware.Camera mCameraDevice;
  protected Camera.CameraInfo[] mCameraInfo;
  private CameraSettings mCameraSettings = new CameraSettings(this);
  private Capturer mCaptureObject;
  private long mCaptureStartTime;
  private long mCurrentTime = 0L;
  Runnable mDatalineCheck = new Runnable()
  {
    public void run()
    {
      Log.e("testCamera", "mDatalineCheck is called.");
      if (Camera.this.cameraType == 1) {
        Camera.this.dialogErrorPopup(2131165192);
      }
      for (;;)
      {
        Camera.this.mCameraDevice.setPreviewCallback(null);
        return;
        Camera.this.dialogErrorPopup(2131165191);
      }
    }
  };
  private ErrorCallback mErrorCallback = new ErrorCallback();
  private AlertDialog mErrorPopup = null;
  private boolean mFinishCamcorderPreviewTest = false;
  private boolean mFinishPostViewTest = false;
  private int mFlashEnable = 0;
  private Animation mFocusBlinkAnimation;
  private long mFocusCallbackTime;
  private FocusRectangle mFocusRectangle;
  private long mFocusStartTime;
  private int mFocusState = 0;
  private ToneGenerator mFocusToneGenerator;
  private ImageCapture mImageCapture = null;
  private boolean mIsCaptureEnable = false;
  private boolean mIsPressedBackkey = false;
  private long mJpegPictureCallbackTime;
  private Uri mLastContentUri = null;
  private int mLastOrientation = -1;
  private Handler mMainHandler = new MainHandler(null);
  protected int mNumberOfCameras;
  protected Thread mOpenCameraThread = null;
  protected OrientationEventListener mOrientationListener = null;
  private int mOrientationOnTake = -1;
  private int mOriginalViewFinderHeight;
  private int mOriginalViewFinderWidth;
  private Camera.Parameters mParameters;
  private boolean mPausing = false;
  private PreviewCallback mPreviewCallback = new PreviewCallback();
  private boolean mPreviewing = false;
  private RawPictureCallback mRawPictureCallback = new RawPictureCallback(null);
  private long mRawPictureCallbackTime;
  private final BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      Log.i("testCamera", "onReceive" + str);
      if (str.equals("com.android.samsungtest.CameraStop"))
      {
        Log.i("testCamera", "onReceive - get Stop Camera");
        Camera.access$1402(Camera.this, true);
        Camera.this.finish();
        return;
      }
      Log.e("testCamera", "onReceive - this action[" + str + "] is not defined");
    }
  };
  private boolean mShutterBtnlock = false;
  private ShutterButton mShutterButton;
  private ShutterCallback mShutterCallback = new ShutterCallback(null);
  private long mShutterCallbackTime;
  Runnable mStartCheck = new Runnable()
  {
    public void run()
    {
      Log.i("testCamera", "Camera Start");
      Camera.this.sendBroadCastAck("com.android.samsungtest.CAMERA_GOOD");
      Camera.this.mCameraDevice.setPreviewCallback(null);
    }
  };
  private int mStatus = 1;
  private boolean mStopCamera = false;
  private SurfaceHolder mSurfaceHolder = null;
  private VideoPreview mSurfaceView;
  protected Handler mTimerHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      Log.e("testCamera", "handleMessage: mTimerHandler -msg:" + paramAnonymousMessage.what);
      switch (paramAnonymousMessage.what)
      {
      default: 
        return;
      case 1: 
        Log.e("testCamera", "handleMessage: KEY_TIMER_EXPIRED -mIsCaptureEnble:" + Camera.this.mIsPressedBackkey);
        Camera.access$4902(Camera.this, false);
        return;
      case 2: 
        Log.e("testCamera", "handleMessage: KEY_TIMER_EXPIRED_CAPTUREBLOCK -mIsCaptureEnble:" + Camera.this.mIsCaptureEnable);
        Camera.access$5002(Camera.this, true);
        return;
      }
      Log.e("testCamera", "handleMessage: TIMER_START_PREVIEW");
      Camera.this.restartPreview();
    }
  };
  private int mViewFinderHeight;
  private int mViewFinderWidth;
  protected PowerManager.WakeLock mWakeLock;
  private int mZoomValue;
  private boolean mchkopencamera = false;
  private boolean ommisionTest = false;
  private String testType = "";
  
  private void autoFocus()
  {
    Log.i("testCamera", "call autoFocus");
    if ((this.mFocusState != 1) && (this.mFocusState != 2) && (this.mCameraDevice != null))
    {
      this.mFocusStartTime = System.currentTimeMillis();
      this.mFocusState = 1;
      updateFocusIndicator();
      this.mCameraDevice.autoFocus(this.mAutoFocusCallback);
    }
  }
  
  private boolean checkDataline(byte[] paramArrayOfByte)
  {
    Log.e("testCamera", "checkDataline - cameraType:" + this.cameraType);
    if (this.cameraType == 1) {}
    for (InputStream localInputStream = getResources().openRawResource(2131034116);; localInputStream = getResources().openRawResource(2131034112))
    {
      try
      {
        int j = localInputStream.available();
        i = j;
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          int i = 0;
        }
      }
      return Arrays.equals(streamToBytes(localInputStream, i), paramArrayOfByte);
    }
  }
  
  private void clearFocusState()
  {
    this.mFocusState = 0;
  }
  
  private void closeCamera()
  {
    Log.i("testCamera", "closeCamera");
    if (this.mCameraDevice != null)
    {
      this.mCameraDevice.release();
      this.mCameraDevice = null;
      this.mPreviewing = false;
      Log.i("testCamera", "closeCamera : mPreviewing set false");
    }
  }
  
  private static String createName(long paramLong)
  {
    return DateFormat.format("yyyy-MM-dd kk.mm.ss", paramLong).toString();
  }
  
  private void dialogErrorPopup(int paramInt)
  {
    Log.v("testCamera", "dialogErrorPopup");
    if ((this.mErrorPopup != null) && (this.mErrorPopup.isShowing())) {
      this.mErrorPopup.dismiss();
    }
    this.mMainHandler.removeCallbacks(this.mStartCheck);
    this.mMainHandler.removeCallbacks(this.mDatalineCheck);
    sendBroadCastAck("com.android.samsungtest.CAMERA_BAD");
    if (!this.mchkopencamera)
    {
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
      localBuilder.setTitle(2131165196);
      localBuilder.setMessage(paramInt);
      localBuilder.setPositiveButton(2131165185, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          Intent localIntent = Camera.this.getIntent();
          Camera.this.setResult(0, localIntent);
          Camera.this.finish();
        }
      });
      localBuilder.setCancelable(false);
      this.mErrorPopup = localBuilder.create();
      this.mErrorPopup.show();
    }
    this.mchkopencamera = false;
  }
  
  private void dialogFocusPopup()
  {
    Log.v("testCamera", "dialogFocusPopup");
    if ((this.mErrorPopup == null) || ((this.mErrorPopup != null) && (!this.mErrorPopup.isShowing())))
    {
      this.mMainHandler.removeCallbacks(this.mStartCheck);
      this.mMainHandler.removeCallbacks(this.mDatalineCheck);
      sendBroadCastAck("com.android.samsungtest.AUTOFOCUS_BAD");
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
      localBuilder.setTitle(2131165196);
      localBuilder.setMessage(2131165190);
      localBuilder.setPositiveButton(2131165185, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          Intent localIntent = Camera.this.getIntent();
          Camera.this.setResult(0, localIntent);
          Camera.this.finish();
        }
      });
      localBuilder.setCancelable(false);
      this.mErrorPopup = localBuilder.create();
      this.mErrorPopup.show();
    }
  }
  
  private void doFocus(boolean paramBoolean)
  {
    Log.i("testCamera", "doFocus - pressed:" + paramBoolean + " cameraType:" + this.cameraType + " mPreviewing:" + this.mPreviewing);
    setOrientationOnTake(getLastOrientation());
    if (paramBoolean) {
      if ((this.cameraType == 0) || (this.cameraType == -1))
      {
        getCameraSettings();
        if ((CameraSettings.getSupportedAutofocus()) && (this.mPreviewing)) {
          autoFocus();
        }
      }
    }
    while (this.mFocusState == 2)
    {
      do
      {
        return;
        if (this.mCaptureObject != null)
        {
          Log.i("testCamera", "AF not supported or preview not started");
          this.mCaptureObject.onSnap();
          return;
        }
        Log.e("testCamera", "CaptureObj NULL");
        return;
      } while (this.mCaptureObject == null);
      this.mCaptureObject.onSnap();
      return;
    }
    if (this.mCameraDevice != null) {
      this.mCameraDevice.cancelAutoFocus();
    }
    clearFocusState();
    updateFocusIndicator();
  }
  
  private void doSnap()
  {
    setOrientationOnTake(getLastOrientation());
    Log.w("testCamera", "doSnap()- mFocusState:" + this.mFocusState);
    if (this.cameraType == 1) {
      this.mCaptureObject.onSnap();
    }
    do
    {
      return;
      if ((this.mFocusState == 3) || (!this.mPreviewing))
      {
        if (this.mCaptureObject != null) {
          this.mCaptureObject.onSnap();
        }
        clearFocusState();
        updateFocusIndicator();
        return;
      }
      if (this.mFocusState == 4)
      {
        Log.w("testCamera", "doSnap()- FOCUS_FAIL : the shot is canceled");
        return;
      }
      if (this.mFocusState == 1)
      {
        this.mFocusState = 2;
        Log.w("testCamera", "doSnap()- FOCUSING : the shot is canceled");
        return;
      }
    } while (this.mFocusState != 0);
    Log.w("testCamera", "doSnap()- FOCUS_NOT_STARTED : the shot is canceled");
  }
  
  /* Error */
  private boolean ensureCameraDevice()
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_1
    //   2: ldc_w 457
    //   5: ldc_w 648
    //   8: invokestatic 465	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   11: pop
    //   12: aload_0
    //   13: getfield 409	com/sec/android/app/camera/Camera:mCameraDevice	Landroid/hardware/Camera;
    //   16: ifnonnull +192 -> 208
    //   19: ldc_w 457
    //   22: new 120	java/lang/StringBuilder
    //   25: dup
    //   26: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   29: ldc_w 650
    //   32: invokevirtual 139	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: aload_0
    //   36: getfield 231	com/sec/android/app/camera/Camera:cameraType	I
    //   39: invokevirtual 483	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   42: invokevirtual 142	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   45: invokestatic 486	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   48: pop
    //   49: aload_0
    //   50: aload_0
    //   51: getfield 231	com/sec/android/app/camera/Camera:cameraType	I
    //   54: invokestatic 654	android/hardware/Camera:open	(I)Landroid/hardware/Camera;
    //   57: putfield 409	com/sec/android/app/camera/Camera:mCameraDevice	Landroid/hardware/Camera;
    //   60: aload_0
    //   61: aload_0
    //   62: getfield 231	com/sec/android/app/camera/Camera:cameraType	I
    //   65: aload_0
    //   66: getfield 409	com/sec/android/app/camera/Camera:mCameraDevice	Landroid/hardware/Camera;
    //   69: invokestatic 658	com/sec/android/app/camera/Camera:setCameraDisplayOrientation	(Landroid/app/Activity;ILandroid/hardware/Camera;)V
    //   72: aload_0
    //   73: aload_0
    //   74: getfield 409	com/sec/android/app/camera/Camera:mCameraDevice	Landroid/hardware/Camera;
    //   77: invokevirtual 662	android/hardware/Camera:getParameters	()Landroid/hardware/Camera$Parameters;
    //   80: putfield 415	com/sec/android/app/camera/Camera:mParameters	Landroid/hardware/Camera$Parameters;
    //   83: aload_0
    //   84: getfield 415	com/sec/android/app/camera/Camera:mParameters	Landroid/hardware/Camera$Parameters;
    //   87: ldc_w 664
    //   90: ldc_w 666
    //   93: invokevirtual 672	android/hardware/Camera$Parameters:set	(Ljava/lang/String;Ljava/lang/String;)V
    //   96: aload_0
    //   97: getfield 415	com/sec/android/app/camera/Camera:mParameters	Landroid/hardware/Camera$Parameters;
    //   100: ldc_w 674
    //   103: ldc_w 676
    //   106: invokevirtual 672	android/hardware/Camera$Parameters:set	(Ljava/lang/String;Ljava/lang/String;)V
    //   109: aload_0
    //   110: invokevirtual 611	com/sec/android/app/camera/Camera:getCameraSettings	()Lcom/sec/android/app/camera/framework/CameraSettings;
    //   113: aload_0
    //   114: getfield 415	com/sec/android/app/camera/Camera:mParameters	Landroid/hardware/Camera$Parameters;
    //   117: invokevirtual 680	android/hardware/Camera$Parameters:getSupportedFocusModes	()Ljava/util/List;
    //   120: invokevirtual 684	com/sec/android/app/camera/framework/CameraSettings:isSupportedFocusModes	(Ljava/util/List;)Z
    //   123: ifeq +115 -> 238
    //   126: aload_0
    //   127: getfield 415	com/sec/android/app/camera/Camera:mParameters	Landroid/hardware/Camera$Parameters;
    //   130: aload_0
    //   131: invokevirtual 611	com/sec/android/app/camera/Camera:getCameraSettings	()Lcom/sec/android/app/camera/framework/CameraSettings;
    //   134: invokevirtual 687	com/sec/android/app/camera/framework/CameraSettings:getFocusMode	()Ljava/lang/String;
    //   137: invokevirtual 690	android/hardware/Camera$Parameters:setFocusMode	(Ljava/lang/String;)V
    //   140: ldc_w 692
    //   143: aload_0
    //   144: getfield 239	com/sec/android/app/camera/Camera:testType	Ljava/lang/String;
    //   147: invokevirtual 697	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   150: ifeq +119 -> 269
    //   153: aload_0
    //   154: getfield 231	com/sec/android/app/camera/Camera:cameraType	I
    //   157: iload_1
    //   158: if_icmpne +93 -> 251
    //   161: aload_0
    //   162: invokevirtual 611	com/sec/android/app/camera/Camera:getCameraSettings	()Lcom/sec/android/app/camera/framework/CameraSettings;
    //   165: aload_0
    //   166: getfield 231	com/sec/android/app/camera/Camera:cameraType	I
    //   169: aload_0
    //   170: getfield 415	com/sec/android/app/camera/Camera:mParameters	Landroid/hardware/Camera$Parameters;
    //   173: invokevirtual 701	com/sec/android/app/camera/framework/CameraSettings:setPictureSize	(ILandroid/hardware/Camera$Parameters;)V
    //   176: ldc_w 457
    //   179: ldc_w 703
    //   182: invokestatic 465	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   185: pop
    //   186: aload_0
    //   187: getfield 409	com/sec/android/app/camera/Camera:mCameraDevice	Landroid/hardware/Camera;
    //   190: aload_0
    //   191: getfield 415	com/sec/android/app/camera/Camera:mParameters	Landroid/hardware/Camera$Parameters;
    //   194: invokevirtual 707	android/hardware/Camera:setParameters	(Landroid/hardware/Camera$Parameters;)V
    //   197: aload_0
    //   198: getfield 409	com/sec/android/app/camera/Camera:mCameraDevice	Landroid/hardware/Camera;
    //   201: aload_0
    //   202: getfield 206	com/sec/android/app/camera/Camera:mErrorCallback	Lcom/sec/android/app/camera/Camera$ErrorCallback;
    //   205: invokevirtual 711	android/hardware/Camera:setErrorCallback	(Landroid/hardware/Camera$ErrorCallback;)V
    //   208: aload_0
    //   209: getfield 409	com/sec/android/app/camera/Camera:mCameraDevice	Landroid/hardware/Camera;
    //   212: ifnull +99 -> 311
    //   215: iload_1
    //   216: ireturn
    //   217: astore 4
    //   219: ldc_w 457
    //   222: ldc_w 713
    //   225: invokestatic 486	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   228: pop
    //   229: aload_0
    //   230: ldc_w 714
    //   233: invokespecial 454	com/sec/android/app/camera/Camera:dialogErrorPopup	(I)V
    //   236: iconst_0
    //   237: ireturn
    //   238: ldc_w 457
    //   241: ldc_w 716
    //   244: invokestatic 536	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   247: pop
    //   248: goto -108 -> 140
    //   251: aload_0
    //   252: invokevirtual 611	com/sec/android/app/camera/Camera:getCameraSettings	()Lcom/sec/android/app/camera/framework/CameraSettings;
    //   255: aload_0
    //   256: getfield 231	com/sec/android/app/camera/Camera:cameraType	I
    //   259: aload_0
    //   260: getfield 415	com/sec/android/app/camera/Camera:mParameters	Landroid/hardware/Camera$Parameters;
    //   263: invokevirtual 701	com/sec/android/app/camera/framework/CameraSettings:setPictureSize	(ILandroid/hardware/Camera$Parameters;)V
    //   266: goto -90 -> 176
    //   269: aload_0
    //   270: invokevirtual 611	com/sec/android/app/camera/Camera:getCameraSettings	()Lcom/sec/android/app/camera/framework/CameraSettings;
    //   273: aload_0
    //   274: getfield 231	com/sec/android/app/camera/Camera:cameraType	I
    //   277: aload_0
    //   278: getfield 415	com/sec/android/app/camera/Camera:mParameters	Landroid/hardware/Camera$Parameters;
    //   281: invokevirtual 701	com/sec/android/app/camera/framework/CameraSettings:setPictureSize	(ILandroid/hardware/Camera$Parameters;)V
    //   284: goto -108 -> 176
    //   287: astore 8
    //   289: ldc_w 457
    //   292: ldc_w 718
    //   295: invokestatic 465	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   298: pop
    //   299: aload_0
    //   300: getfield 281	com/sec/android/app/camera/Camera:mMainHandler	Landroid/os/Handler;
    //   303: bipush 9
    //   305: invokevirtual 722	android/os/Handler:sendEmptyMessage	(I)Z
    //   308: pop
    //   309: iconst_0
    //   310: ireturn
    //   311: iconst_0
    //   312: istore_1
    //   313: goto -98 -> 215
    //   316: astore 7
    //   318: goto -121 -> 197
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	321	0	this	Camera
    //   1	215	1	i	int
    //   312	1	1	j	int
    //   217	1	4	localException	Exception
    //   316	1	7	localIllegalArgumentException	IllegalArgumentException
    //   287	1	8	localRuntimeException	RuntimeException
    // Exception table:
    //   from	to	target	type
    //   49	60	217	java/lang/Exception
    //   176	197	287	java/lang/RuntimeException
    //   176	197	316	java/lang/IllegalArgumentException
  }
  
  public static long getAvailableSpaceSd()
  {
    long l1 = -1L;
    String str = Environment.getExternalStorageState();
    if ("checking".equals(str)) {
      l1 = -2L;
    }
    File localFile;
    do
    {
      do
      {
        return l1;
      } while (!"mounted".equals(str));
      localFile = new File(DIRECTORY_CAMERA_SDCARD);
      localFile.mkdirs();
    } while ((!localFile.isDirectory()) || (!localFile.canWrite()));
    try
    {
      StatFs localStatFs = new StatFs(DIRECTORY_CAMERA_SDCARD);
      long l2 = localStatFs.getAvailableBlocks() * localStatFs.getBlockSize();
      Log.d("testCamera", "getAvailableSpaceSd : space =" + l2);
      return l2;
    }
    catch (Exception localException)
    {
      Log.i("testCamera", "Fail to access external storage", localException);
    }
    return -3L;
  }
  
  private void getIntentInfo()
  {
    Log.i("testCamera", "getIntentInfo E");
    this.testType = getIntent().getStringExtra("test_type");
    this.cameraType = getIntent().getIntExtra("camera_id", -1);
    this.ommisionTest = getIntent().getBooleanExtra("ommision_test", false);
    this.mFlashEnable = getIntent().getIntExtra("torch_on", 0);
    this.mFinishCamcorderPreviewTest = getIntent().getBooleanExtra("camcorder_preview_test", false);
    this.mFinishPostViewTest = getIntent().getBooleanExtra("postview_test", false);
    Log.i("testCamera", "getIntentInfo EX : cameraType[" + this.cameraType + "] ommisionTest[" + this.ommisionTest + "] mFlashEnable[" + this.mFlashEnable + "]" + "mFinishCamcorderPreviewTest[" + this.mFinishCamcorderPreviewTest + "]");
  }
  
  private void initDTPsetting()
  {
    getCameraSettings();
    if (!CameraSettings.needToCheckDTP(this.cameraType)) {}
    for (boolean bool = true;; bool = false)
    {
      this.bDoneDTP = bool;
      this.bCheckDTP = true;
      if (this.mFlashEnable != 1) {
        break;
      }
      Log.i("testCamera", "FlashEnable = true");
      this.bEnablePreviewCb = false;
      return;
    }
    this.bEnablePreviewCb = true;
  }
  
  private void initLayoutSetting()
  {
    getWindow().addFlags(6815872);
    setContentView(2130903041);
    if ((!"selftest".equals(this.testType)) && (typeDevice.equals("tablet"))) {
      setRemoveSystemUI(getWindow(), true);
    }
    this.mSurfaceView = ((VideoPreview)findViewById(2131296257));
    SurfaceHolder localSurfaceHolder = this.mSurfaceView.getHolder();
    localSurfaceHolder.addCallback(this);
    localSurfaceHolder.setType(3);
    this.mShutterButton = ((ShutterButton)findViewById(2131296258));
    this.mShutterButton.setOnShutterButtonListener(this);
    this.mFocusBlinkAnimation = AnimationUtils.loadAnimation(this, 2130968576);
    this.mFocusBlinkAnimation.setRepeatCount(-1);
    this.mFocusBlinkAnimation.setRepeatMode(2);
    try
    {
      this.mOpenCameraThread.join();
      label142:
      this.mZoomValue = 0;
      this.mFocusRectangle = ((FocusRectangle)findViewById(2131296261));
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label142;
    }
  }
  
  private void restartPreview()
  {
    VideoPreview localVideoPreview = this.mSurfaceView;
    int i;
    int j;
    if ("selftest".equals(this.testType)) {
      if (this.cameraType == 1)
      {
        i = 352;
        j = 288;
        if (this.mSurfaceHolder == null) {
          break label175;
        }
        if (!"selftest".equals(this.testType)) {
          break label141;
        }
        if (this.cameraType != 1) {
          break label131;
        }
        setViewFinder(localVideoPreview.getMeasuredWidth(), localVideoPreview.getMeasuredHeight(), true);
      }
    }
    for (;;)
    {
      this.mStatus = 1;
      if (this.mOrientationListener != null) {
        this.mOrientationListener.enable();
      }
      return;
      i = 640;
      j = 480;
      break;
      i = this.mParameters.getPictureSize().width;
      j = this.mParameters.getPictureSize().height;
      break;
      label131:
      setViewFinder(i, j, true);
      continue;
      label141:
      if (this.cameraType == 1)
      {
        setViewFinder(localVideoPreview.getMeasuredWidth(), localVideoPreview.getMeasuredHeight(), true);
      }
      else
      {
        setViewFinder(i, j, true);
        continue;
        label175:
        if (this.cameraType == 1) {
          localVideoPreview.setAspectRatio(i, j);
        } else {
          localVideoPreview.setAspectRatio(i, j);
        }
      }
    }
  }
  
  public static int roundOrientation(int paramInt)
  {
    return 90 * ((paramInt + 45) / 90) % 360;
  }
  
  private void sendBroadCastAck(String paramString)
  {
    Log.e("testCamera", "sendBroadCastAck - " + paramString);
    sendBroadcast(new Intent(paramString).putExtra("TYPE", getIntent().getIntExtra("TYPE", -1)));
    this.bSentAck = true;
  }
  
  public static void setCameraDisplayOrientation(Activity paramActivity, int paramInt, android.hardware.Camera paramCamera)
  {
    paramCamera.setDisplayOrientation(0);
  }
  
  private void setLastOrientation(int paramInt)
  {
    this.mLastOrientation = paramInt;
  }
  
  public static void setRemoveSystemUI(Window paramWindow, boolean paramBoolean)
  {
    localLayoutParams = paramWindow.getAttributes();
    for (;;)
    {
      try
      {
        Field localField = View.class.getField("SYSTEM_UI_FLAG_REMOVE_NAVIGATION");
        if (localField == null) {
          continue;
        }
        int j = localField.getInt(localField);
        i = j;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        int i = 0;
        continue;
        if ((i & localLayoutParams.systemUiVisibility) == 0) {
          continue;
        }
        localLayoutParams.systemUiVisibility = (i ^ localLayoutParams.systemUiVisibility);
        continue;
      }
      if (!paramBoolean) {
        continue;
      }
      localLayoutParams.systemUiVisibility = (i | localLayoutParams.systemUiVisibility);
      paramWindow.setAttributes(localLayoutParams);
      return;
      i = 2;
    }
  }
  
  private void setStoragepath()
  {
    this.bUseSdcard = true;
  }
  
  private void setViewFinder(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Log.i("testCamera", "setViewFinder - w: " + paramInt1 + " h: " + paramInt2 + " startPreview: " + paramBoolean);
    if (this.mPausing)
    {
      Log.i("testCamera", "setViewFinder - mPausing == true, return");
      return;
    }
    if ((this.mPreviewing) && (paramInt1 == this.mViewFinderWidth))
    {
      int j = this.mViewFinderHeight;
      if (paramInt2 == j)
      {
        Log.i("testCamera", "mPreviewing && w == mViewFinderWidth && h == mViewFinderHeight");
        Log.i("testCamera", "mPreviewing(" + this.mPreviewing + ")" + " mViewFinderWidth(" + this.mViewFinderWidth + ")" + " mViewFinderHeight(" + this.mViewFinderHeight + ")");
        return;
      }
    }
    if (!ensureCameraDevice())
    {
      Log.i("testCamera", "!ensureCameraDevice()");
      return;
    }
    if (this.mSurfaceHolder == null)
    {
      Log.i("testCamera", "mSurfaceHolder == null");
      return;
    }
    if (isFinishing())
    {
      Log.i("testCamera", "isFinishing()");
      return;
    }
    if (this.mPausing)
    {
      Log.i("testCamera", "setViewFinder - mPausing == true, return");
      return;
    }
    Log.i("testCamera", "set ViewFinderWidth(" + paramInt1 + "," + paramInt2 + ")");
    this.mViewFinderWidth = paramInt1;
    this.mViewFinderHeight = paramInt2;
    if (this.mOriginalViewFinderHeight == 0)
    {
      Log.i("testCamera", "set OriginalViewFinderWidth(" + paramInt1 + "," + paramInt2 + ")");
      this.mOriginalViewFinderWidth = paramInt1;
      this.mOriginalViewFinderHeight = paramInt2;
    }
    if (!paramBoolean)
    {
      Log.i("testCamera", "startPreview == false");
      return;
    }
    if (this.mPreviewing)
    {
      Log.i("testCamera", "mPreviewing == true, stopPreview()");
      stopPreview();
    }
    this.mParameters = this.mCameraDevice.getParameters();
    if (paramInt1 < paramInt2) {
      Log.i("testCamera", "Swapped dimensions W = " + paramInt2 + " H = " + paramInt1);
    }
    for (;;)
    {
      for (;;)
      {
        if (this.mParameters != null)
        {
          List localList = this.mParameters.getSupportedPreviewSizes();
          if (localList == null)
          {
            Log.v("testCamera", "supported preview size is null");
            return;
          }
          Camera.Size localSize1 = this.mParameters.getPictureSize();
          Camera.Size localSize2 = findOptimalPreviewSize(localList, localSize1.width / localSize1.height);
          this.mParameters.setPreviewSize(localSize2.width, localSize2.height);
          if (("KOR".equals("")) && (this.cameraType == 1)) {
            this.mParameters.set("selftestmode", 1);
          }
          if (("selftest".equals(this.testType)) && (this.cameraType == 1))
          {
            Log.i("testCamera", "FRONT_CAMERA_MODE: 2");
            Log.i("testCamera", "FRONT_CAMERA_PREIVEW_WIDTH: 352, VT_CAMERA_PREIVEW_HEIGHT: 288");
            Log.i("testCamera", "FRONT_CAMERA_MIN_FPS: 15000, VT_CAMERA_MAX_FPS: 15000");
            this.mParameters.set("vtmode", 2);
            this.mParameters.setPreviewSize(352, 288);
            this.mParameters.setPreviewFpsRange(15000, 15000);
          }
        }
        Log.i("testCamera", "mParameters.setPreviewSize W = " + paramInt2 + " H = " + paramInt1);
        if ((!this.bDoneDTP) && (this.mFlashEnable != 1)) {
          this.mParameters.set("chk_dataline", 1);
        }
        try
        {
          for (;;)
          {
            for (;;)
            {
              Log.i("testCamera", "mCameraDevice.setParameters(mParameters)");
              this.mCameraDevice.setParameters(this.mParameters);
              label732:
              final Object localObject1;
              try
              {
                Log.i("testCamera", "mCameraDevice.setPreviewDisplay");
                this.mCameraDevice.setPreviewDisplay(this.mSurfaceHolder);
                l1 = SystemClock.elapsedRealtime();
                l2 = Debug.threadCpuTimeNanos();
                localObject1 = new Object();
                new Thread(new Runnable()
                {
                  /* Error */
                  public void run()
                  {
                    // Byte code:
                    //   0: ldc 33
                    //   2: ldc 35
                    //   4: invokestatic 41	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
                    //   7: pop
                    //   8: iconst_1
                    //   9: istore_2
                    //   10: aload_0
                    //   11: getfield 23	com/sec/android/app/camera/Camera$7:val$watchDogSync	Ljava/lang/Object;
                    //   14: astore 7
                    //   16: aload 7
                    //   18: monitorenter
                    //   19: aload_0
                    //   20: getfield 23	com/sec/android/app/camera/Camera$7:val$watchDogSync	Ljava/lang/Object;
                    //   23: ldc2_w 42
                    //   26: invokevirtual 47	java/lang/Object:wait	(J)V
                    //   29: aload 7
                    //   31: monitorexit
                    //   32: aload_0
                    //   33: getfield 21	com/sec/android/app/camera/Camera$7:this$0	Lcom/sec/android/app/camera/Camera;
                    //   36: invokestatic 51	com/sec/android/app/camera/Camera:access$2300	(Lcom/sec/android/app/camera/Camera;)Z
                    //   39: ifeq +16 -> 55
                    //   42: return
                    //   43: astore 8
                    //   45: aload 7
                    //   47: monitorexit
                    //   48: aload 8
                    //   50: athrow
                    //   51: astore_3
                    //   52: goto -20 -> 32
                    //   55: invokestatic 57	android/os/SystemClock:elapsedRealtime	()J
                    //   58: aload_0
                    //   59: getfield 25	com/sec/android/app/camera/Camera$7:val$wallTimeStart	J
                    //   62: lsub
                    //   63: l2i
                    //   64: sipush 1000
                    //   67: idiv
                    //   68: istore 4
                    //   70: iload 4
                    //   72: iload_2
                    //   73: if_icmplt -63 -> 10
                    //   76: iload 4
                    //   78: bipush 120
                    //   80: if_icmpge +56 -> 136
                    //   83: ldc 33
                    //   85: new 59	java/lang/StringBuilder
                    //   88: dup
                    //   89: invokespecial 60	java/lang/StringBuilder:<init>	()V
                    //   92: ldc 62
                    //   94: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
                    //   97: iload 4
                    //   99: invokevirtual 69	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
                    //   102: ldc 71
                    //   104: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
                    //   107: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
                    //   110: invokestatic 78	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
                    //   113: pop
                    //   114: iload_2
                    //   115: bipush 60
                    //   117: if_icmpge +56 -> 173
                    //   120: iload_2
                    //   121: iconst_1
                    //   122: ishl
                    //   123: istore_2
                    //   124: iload_2
                    //   125: bipush 16
                    //   127: if_icmpne -117 -> 10
                    //   130: bipush 15
                    //   132: istore_2
                    //   133: goto -123 -> 10
                    //   136: ldc 33
                    //   138: new 59	java/lang/StringBuilder
                    //   141: dup
                    //   142: invokespecial 60	java/lang/StringBuilder:<init>	()V
                    //   145: ldc 62
                    //   147: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
                    //   150: iload 4
                    //   152: bipush 60
                    //   154: idiv
                    //   155: invokevirtual 69	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
                    //   158: ldc 80
                    //   160: invokevirtual 66	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
                    //   163: invokevirtual 75	java/lang/StringBuilder:toString	()Ljava/lang/String;
                    //   166: invokestatic 78	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
                    //   169: pop
                    //   170: goto -56 -> 114
                    //   173: iinc 2 60
                    //   176: goto -166 -> 10
                    // Local variable table:
                    //   start	length	slot	name	signature
                    //   0	179	0	this	7
                    //   9	165	2	i	int
                    //   51	1	3	localInterruptedException	InterruptedException
                    //   68	87	4	j	int
                    //   43	6	8	localObject2	Object
                    // Exception table:
                    //   from	to	target	type
                    //   19	32	43	finally
                    //   45	48	43	finally
                    //   10	19	51	java/lang/InterruptedException
                    //   48	51	51	java/lang/InterruptedException
                  }
                }).start();
                Log.i("testCamera", "calling mCameraDevice.startPreview");
              }
              catch (IOException localIOException)
              {
                final long l1;
                long l2;
                long l3;
                long l4;
                Log.i("testCamera", "mCameraDevice.setPreviewDisplay exception!!!");
                this.mCameraDevice.release();
                this.mCameraDevice = null;
                return;
              }
              try
              {
                Log.i("testCamera", "mCameraDevice.startPreview()");
                if (this.bEnablePreviewCb)
                {
                  this.mCameraDevice.setPreviewCallback(this.mPreviewCallback);
                  Log.e("testCamera", "setViewFinder :: postDelayed-mDatalineCheck()");
                  this.mMainHandler.removeCallbacks(this.mDatalineCheck);
                  this.mMainHandler.postDelayed(this.mDatalineCheck, 5000L);
                }
                this.mCameraDevice.startPreview();
                if (!this.bEnablePreviewCb)
                {
                  this.mMainHandler.removeCallbacks(this.mStartCheck);
                  this.mMainHandler.postDelayed(this.mStartCheck, 1000L);
                }
                getCameraSettings();
                if (!CameraSettings.needToCheckDTP(this.cameraType)) {
                  startTimerCaptureBlock();
                }
                Log.i("testCamera", "setViewFinder : set mPreviewing = true");
                this.mPreviewing = true;
              }
              catch (Throwable localThrowable)
              {
                Log.e("testCamera", "exception while startPreview", localThrowable);
                dialogErrorPopup(2131165193);
                return;
              }
              try
              {
                localObject1.notify();
                l3 = Debug.threadCpuTimeNanos();
                l4 = SystemClock.elapsedRealtime();
                if (l4 - l1 <= 3000L) {
                  break;
                }
                Log.w("testCamera", "startPreview() to " + (l4 - l1) + " ms. Thread time was" + (l3 - l2) / 1000000L + " ms.");
                return;
              }
              finally {}
            }
            this.mParameters.set("chk_dataline", 0);
          }
        }
        catch (RuntimeException localRuntimeException)
        {
          Log.i("testCamera", "setParameter fail");
          this.mMainHandler.sendEmptyMessage(9);
          return;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          break label732;
        }
      }
      int i = paramInt2;
      paramInt2 = paramInt1;
      paramInt1 = i;
    }
  }
  
  private void startCamera()
  {
    this.mOpenCameraThread = new Thread(new Runnable()
    {
      /* Error */
      public void run()
      {
        // Byte code:
        //   0: invokestatic 32	android/hardware/Camera:getNumberOfCameras	()I
        //   3: istore_1
        //   4: ldc 34
        //   6: new 36	java/lang/StringBuilder
        //   9: dup
        //   10: invokespecial 37	java/lang/StringBuilder:<init>	()V
        //   13: ldc 39
        //   15: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   18: aload_0
        //   19: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   22: invokestatic 47	com/sec/android/app/camera/Camera:access$1200	(Lcom/sec/android/app/camera/Camera;)I
        //   25: invokevirtual 50	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   28: ldc 52
        //   30: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   33: iload_1
        //   34: invokevirtual 50	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   37: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   40: invokestatic 62	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   43: pop
        //   44: aload_0
        //   45: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   48: aload_0
        //   49: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   52: invokestatic 47	com/sec/android/app/camera/Camera:access$1200	(Lcom/sec/android/app/camera/Camera;)I
        //   55: invokestatic 66	android/hardware/Camera:open	(I)Landroid/hardware/Camera;
        //   58: invokestatic 70	com/sec/android/app/camera/Camera:access$4302	(Lcom/sec/android/app/camera/Camera;Landroid/hardware/Camera;)Landroid/hardware/Camera;
        //   61: pop
        //   62: aload_0
        //   63: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   66: aload_0
        //   67: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   70: invokestatic 47	com/sec/android/app/camera/Camera:access$1200	(Lcom/sec/android/app/camera/Camera;)I
        //   73: aload_0
        //   74: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   77: invokestatic 74	com/sec/android/app/camera/Camera:access$4300	(Lcom/sec/android/app/camera/Camera;)Landroid/hardware/Camera;
        //   80: invokestatic 78	com/sec/android/app/camera/Camera:setCameraDisplayOrientation	(Landroid/app/Activity;ILandroid/hardware/Camera;)V
        //   83: aload_0
        //   84: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   87: aload_0
        //   88: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   91: invokestatic 74	com/sec/android/app/camera/Camera:access$4300	(Lcom/sec/android/app/camera/Camera;)Landroid/hardware/Camera;
        //   94: invokevirtual 82	android/hardware/Camera:getParameters	()Landroid/hardware/Camera$Parameters;
        //   97: invokestatic 86	com/sec/android/app/camera/Camera:access$4402	(Lcom/sec/android/app/camera/Camera;Landroid/hardware/Camera$Parameters;)Landroid/hardware/Camera$Parameters;
        //   100: pop
        //   101: aload_0
        //   102: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   105: invokestatic 90	com/sec/android/app/camera/Camera:access$4400	(Lcom/sec/android/app/camera/Camera;)Landroid/hardware/Camera$Parameters;
        //   108: ldc 92
        //   110: ldc 94
        //   112: invokevirtual 100	android/hardware/Camera$Parameters:set	(Ljava/lang/String;Ljava/lang/String;)V
        //   115: aload_0
        //   116: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   119: invokestatic 90	com/sec/android/app/camera/Camera:access$4400	(Lcom/sec/android/app/camera/Camera;)Landroid/hardware/Camera$Parameters;
        //   122: ldc 102
        //   124: ldc 104
        //   126: invokevirtual 100	android/hardware/Camera$Parameters:set	(Ljava/lang/String;Ljava/lang/String;)V
        //   129: aload_0
        //   130: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   133: invokevirtual 108	com/sec/android/app/camera/Camera:getCameraSettings	()Lcom/sec/android/app/camera/framework/CameraSettings;
        //   136: aload_0
        //   137: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   140: invokestatic 90	com/sec/android/app/camera/Camera:access$4400	(Lcom/sec/android/app/camera/Camera;)Landroid/hardware/Camera$Parameters;
        //   143: invokevirtual 112	android/hardware/Camera$Parameters:getSupportedFocusModes	()Ljava/util/List;
        //   146: invokevirtual 118	com/sec/android/app/camera/framework/CameraSettings:isSupportedFocusModes	(Ljava/util/List;)Z
        //   149: ifeq +151 -> 300
        //   152: aload_0
        //   153: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   156: invokestatic 90	com/sec/android/app/camera/Camera:access$4400	(Lcom/sec/android/app/camera/Camera;)Landroid/hardware/Camera$Parameters;
        //   159: aload_0
        //   160: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   163: invokevirtual 108	com/sec/android/app/camera/Camera:getCameraSettings	()Lcom/sec/android/app/camera/framework/CameraSettings;
        //   166: invokevirtual 121	com/sec/android/app/camera/framework/CameraSettings:getFocusMode	()Ljava/lang/String;
        //   169: invokevirtual 125	android/hardware/Camera$Parameters:setFocusMode	(Ljava/lang/String;)V
        //   172: ldc 127
        //   174: aload_0
        //   175: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   178: invokestatic 131	com/sec/android/app/camera/Camera:access$1800	(Lcom/sec/android/app/camera/Camera;)Ljava/lang/String;
        //   181: invokevirtual 137	java/lang/String:equals	(Ljava/lang/Object;)Z
        //   184: ifeq +154 -> 338
        //   187: aload_0
        //   188: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   191: invokestatic 47	com/sec/android/app/camera/Camera:access$1200	(Lcom/sec/android/app/camera/Camera;)I
        //   194: iconst_1
        //   195: if_icmpne +116 -> 311
        //   198: aload_0
        //   199: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   202: invokevirtual 108	com/sec/android/app/camera/Camera:getCameraSettings	()Lcom/sec/android/app/camera/framework/CameraSettings;
        //   205: aload_0
        //   206: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   209: invokestatic 47	com/sec/android/app/camera/Camera:access$1200	(Lcom/sec/android/app/camera/Camera;)I
        //   212: aload_0
        //   213: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   216: invokestatic 90	com/sec/android/app/camera/Camera:access$4400	(Lcom/sec/android/app/camera/Camera;)Landroid/hardware/Camera$Parameters;
        //   219: invokevirtual 141	com/sec/android/app/camera/framework/CameraSettings:setPictureSize	(ILandroid/hardware/Camera$Parameters;)V
        //   222: ldc 34
        //   224: ldc 143
        //   226: invokestatic 146	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
        //   229: pop
        //   230: aload_0
        //   231: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   234: invokestatic 74	com/sec/android/app/camera/Camera:access$4300	(Lcom/sec/android/app/camera/Camera;)Landroid/hardware/Camera;
        //   237: aload_0
        //   238: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   241: invokestatic 90	com/sec/android/app/camera/Camera:access$4400	(Lcom/sec/android/app/camera/Camera;)Landroid/hardware/Camera$Parameters;
        //   244: invokevirtual 150	android/hardware/Camera:setParameters	(Landroid/hardware/Camera$Parameters;)V
        //   247: aload_0
        //   248: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   251: invokestatic 74	com/sec/android/app/camera/Camera:access$4300	(Lcom/sec/android/app/camera/Camera;)Landroid/hardware/Camera;
        //   254: aload_0
        //   255: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   258: invokestatic 154	com/sec/android/app/camera/Camera:access$4800	(Lcom/sec/android/app/camera/Camera;)Lcom/sec/android/app/camera/Camera$ErrorCallback;
        //   261: invokevirtual 158	android/hardware/Camera:setErrorCallback	(Landroid/hardware/Camera$ErrorCallback;)V
        //   264: return
        //   265: astore_3
        //   266: ldc 34
        //   268: ldc 160
        //   270: invokestatic 62	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
        //   273: pop
        //   274: aload_0
        //   275: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   278: iconst_1
        //   279: invokestatic 164	com/sec/android/app/camera/Camera:access$4702	(Lcom/sec/android/app/camera/Camera;Z)Z
        //   282: pop
        //   283: aload_0
        //   284: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   287: ldc 165
        //   289: invokestatic 169	com/sec/android/app/camera/Camera:access$900	(Lcom/sec/android/app/camera/Camera;I)V
        //   292: aload_0
        //   293: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   296: invokevirtual 172	com/sec/android/app/camera/Camera:finish	()V
        //   299: return
        //   300: ldc 34
        //   302: ldc 174
        //   304: invokestatic 177	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
        //   307: pop
        //   308: goto -136 -> 172
        //   311: aload_0
        //   312: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   315: invokevirtual 108	com/sec/android/app/camera/Camera:getCameraSettings	()Lcom/sec/android/app/camera/framework/CameraSettings;
        //   318: aload_0
        //   319: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   322: invokestatic 47	com/sec/android/app/camera/Camera:access$1200	(Lcom/sec/android/app/camera/Camera;)I
        //   325: aload_0
        //   326: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   329: invokestatic 90	com/sec/android/app/camera/Camera:access$4400	(Lcom/sec/android/app/camera/Camera;)Landroid/hardware/Camera$Parameters;
        //   332: invokevirtual 141	com/sec/android/app/camera/framework/CameraSettings:setPictureSize	(ILandroid/hardware/Camera$Parameters;)V
        //   335: goto -113 -> 222
        //   338: aload_0
        //   339: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   342: invokevirtual 108	com/sec/android/app/camera/Camera:getCameraSettings	()Lcom/sec/android/app/camera/framework/CameraSettings;
        //   345: aload_0
        //   346: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   349: invokestatic 47	com/sec/android/app/camera/Camera:access$1200	(Lcom/sec/android/app/camera/Camera;)I
        //   352: aload_0
        //   353: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   356: invokestatic 90	com/sec/android/app/camera/Camera:access$4400	(Lcom/sec/android/app/camera/Camera;)Landroid/hardware/Camera$Parameters;
        //   359: invokevirtual 141	com/sec/android/app/camera/framework/CameraSettings:setPictureSize	(ILandroid/hardware/Camera$Parameters;)V
        //   362: goto -140 -> 222
        //   365: astore 10
        //   367: ldc 34
        //   369: ldc 179
        //   371: invokestatic 146	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
        //   374: pop
        //   375: aload_0
        //   376: getfield 17	com/sec/android/app/camera/Camera$3:this$0	Lcom/sec/android/app/camera/Camera;
        //   379: invokestatic 183	com/sec/android/app/camera/Camera:access$500	(Lcom/sec/android/app/camera/Camera;)Landroid/os/Handler;
        //   382: bipush 9
        //   384: invokevirtual 189	android/os/Handler:sendEmptyMessage	(I)Z
        //   387: pop
        //   388: return
        //   389: astore 9
        //   391: goto -144 -> 247
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	394	0	this	3
        //   3	31	1	i	int
        //   265	1	3	localException	Exception
        //   389	1	9	localIllegalArgumentException	IllegalArgumentException
        //   365	1	10	localRuntimeException	RuntimeException
        // Exception table:
        //   from	to	target	type
        //   44	62	265	java/lang/Exception
        //   222	247	365	java/lang/RuntimeException
        //   222	247	389	java/lang/IllegalArgumentException
      }
    });
  }
  
  private void stopPreview()
  {
    if ((this.mCameraDevice != null) && (this.mPreviewing)) {
      this.mCameraDevice.stopPreview();
    }
    this.mPreviewing = false;
    Log.i("testCamera", "stopPreview : mPreviewing set false");
    clearFocusState();
    if (this.mOrientationListener != null) {
      this.mOrientationListener.disable();
    }
  }
  
  private void updateFocusIndicator()
  {
    if (this.mFocusRectangle == null) {
      return;
    }
    if ((this.mFocusState == 1) || (this.mFocusState == 2))
    {
      this.mFocusRectangle.showStart();
      return;
    }
    if (this.mFocusState == 3)
    {
      this.mFocusRectangle.showSuccess();
      return;
    }
    if (this.mFocusState == 4)
    {
      this.mFocusRectangle.showFail();
      return;
    }
    this.mFocusRectangle.clear();
  }
  
  /* Error */
  private void writeImage(String paramString1, String paramString2, byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: ldc_w 457
    //   3: new 120	java/lang/StringBuilder
    //   6: dup
    //   7: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   10: ldc_w 1168
    //   13: invokevirtual 139	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: aload_1
    //   17: invokevirtual 139	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   20: invokevirtual 142	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   23: invokestatic 762	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   26: pop
    //   27: aconst_null
    //   28: astore 5
    //   30: aload_0
    //   31: new 120	java/lang/StringBuilder
    //   34: dup
    //   35: invokespecial 123	java/lang/StringBuilder:<init>	()V
    //   38: aload_1
    //   39: invokevirtual 139	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: ldc_w 1170
    //   45: invokevirtual 139	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   48: aload_2
    //   49: invokevirtual 139	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: invokevirtual 142	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   55: putfield 182	com/sec/android/app/camera/Camera:filePath	Ljava/lang/String;
    //   58: new 131	java/io/File
    //   61: dup
    //   62: aload_1
    //   63: invokespecial 736	java/io/File:<init>	(Ljava/lang/String;)V
    //   66: astore 6
    //   68: aload 6
    //   70: invokevirtual 1173	java/io/File:isFile	()Z
    //   73: istore 15
    //   75: aconst_null
    //   76: astore 5
    //   78: iload 15
    //   80: ifeq +21 -> 101
    //   83: aload 6
    //   85: invokevirtual 1176	java/io/File:delete	()Z
    //   88: pop
    //   89: new 131	java/io/File
    //   92: dup
    //   93: aload_1
    //   94: invokespecial 736	java/io/File:<init>	(Ljava/lang/String;)V
    //   97: invokevirtual 739	java/io/File:mkdirs	()Z
    //   100: pop
    //   101: aload 6
    //   103: invokevirtual 742	java/io/File:isDirectory	()Z
    //   106: istore 18
    //   108: aconst_null
    //   109: astore 5
    //   111: iload 18
    //   113: ifne +9 -> 122
    //   116: aload 6
    //   118: invokevirtual 739	java/io/File:mkdirs	()Z
    //   121: pop
    //   122: new 1178	java/io/FileOutputStream
    //   125: dup
    //   126: new 131	java/io/File
    //   129: dup
    //   130: aload_1
    //   131: aload_2
    //   132: invokespecial 1180	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   135: invokespecial 1183	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   138: astore 20
    //   140: aload 20
    //   142: aload_3
    //   143: invokevirtual 1189	java/io/OutputStream:write	([B)V
    //   146: aload 20
    //   148: invokevirtual 1192	java/io/OutputStream:close	()V
    //   151: return
    //   152: astore 21
    //   154: return
    //   155: astore 12
    //   157: ldc_w 457
    //   160: aload 12
    //   162: invokestatic 1195	android/util/Log:w	(Ljava/lang/String;Ljava/lang/Throwable;)I
    //   165: pop
    //   166: aload 5
    //   168: invokevirtual 1192	java/io/OutputStream:close	()V
    //   171: return
    //   172: astore 14
    //   174: return
    //   175: astore 9
    //   177: ldc_w 457
    //   180: aload 9
    //   182: invokestatic 1195	android/util/Log:w	(Ljava/lang/String;Ljava/lang/Throwable;)I
    //   185: pop
    //   186: aload 5
    //   188: invokevirtual 1192	java/io/OutputStream:close	()V
    //   191: return
    //   192: astore 11
    //   194: return
    //   195: astore 7
    //   197: aload 5
    //   199: invokevirtual 1192	java/io/OutputStream:close	()V
    //   202: aload 7
    //   204: athrow
    //   205: astore 8
    //   207: goto -5 -> 202
    //   210: astore 7
    //   212: aload 20
    //   214: astore 5
    //   216: goto -19 -> 197
    //   219: astore 9
    //   221: aload 20
    //   223: astore 5
    //   225: goto -48 -> 177
    //   228: astore 12
    //   230: aload 20
    //   232: astore 5
    //   234: goto -77 -> 157
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	237	0	this	Camera
    //   0	237	1	paramString1	String
    //   0	237	2	paramString2	String
    //   0	237	3	paramArrayOfByte	byte[]
    //   28	205	5	localObject1	Object
    //   66	51	6	localFile	File
    //   195	8	7	localObject2	Object
    //   210	1	7	localObject3	Object
    //   205	1	8	localIOException1	IOException
    //   175	6	9	localIOException2	IOException
    //   219	1	9	localIOException3	IOException
    //   192	1	11	localIOException4	IOException
    //   155	6	12	localFileNotFoundException1	java.io.FileNotFoundException
    //   228	1	12	localFileNotFoundException2	java.io.FileNotFoundException
    //   172	1	14	localIOException5	IOException
    //   73	6	15	bool1	boolean
    //   106	6	18	bool2	boolean
    //   138	93	20	localFileOutputStream	java.io.FileOutputStream
    //   152	1	21	localIOException6	IOException
    // Exception table:
    //   from	to	target	type
    //   146	151	152	java/io/IOException
    //   58	75	155	java/io/FileNotFoundException
    //   83	101	155	java/io/FileNotFoundException
    //   101	108	155	java/io/FileNotFoundException
    //   116	122	155	java/io/FileNotFoundException
    //   122	140	155	java/io/FileNotFoundException
    //   166	171	172	java/io/IOException
    //   58	75	175	java/io/IOException
    //   83	101	175	java/io/IOException
    //   101	108	175	java/io/IOException
    //   116	122	175	java/io/IOException
    //   122	140	175	java/io/IOException
    //   186	191	192	java/io/IOException
    //   58	75	195	finally
    //   83	101	195	finally
    //   101	108	195	finally
    //   116	122	195	finally
    //   122	140	195	finally
    //   157	166	195	finally
    //   177	186	195	finally
    //   197	202	205	java/io/IOException
    //   140	146	210	finally
    //   140	146	219	java/io/IOException
    //   140	146	228	java/io/FileNotFoundException
  }
  
  public Uri addImage(ContentResolver paramContentResolver, String paramString, long paramLong, byte[] paramArrayOfByte, int paramInt)
  {
    Log.e("testCamera", "addImage title:" + paramString);
    ContentValues localContentValues = new ContentValues(6);
    localContentValues.put("title", paramString);
    localContentValues.put("_display_name", paramString + ".jpg");
    localContentValues.put("datetaken", Long.valueOf(paramLong));
    localContentValues.put("mime_type", "image/jpeg");
    localContentValues.put("_size", Integer.valueOf(paramArrayOfByte.length));
    localContentValues.put("_data", this.filePath);
    localContentValues.put("orientation", Integer.valueOf(paramInt));
    try
    {
      Uri localUri2 = paramContentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, localContentValues);
      localUri1 = localUri2;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        Log.e("testCamera", "Failed to write MediaStore" + localThrowable);
        Uri localUri1 = null;
      }
    }
    Log.e("testCamera", "addImage uri:" + localUri1);
    sendBroadcast(new Intent("com.android.camera.NEW_PICTURE", localUri1));
    return localUri1;
  }
  
  protected int calculateOrientationForPicture(int paramInt)
  {
    int i = 0;
    Camera.CameraInfo localCameraInfo;
    if (paramInt != -1)
    {
      localCameraInfo = this.mCameraInfo[this.cameraType];
      if (localCameraInfo.facing == 1) {
        i = (360 + (localCameraInfo.orientation - paramInt)) % 360;
      }
    }
    else
    {
      return i;
    }
    return (paramInt + localCameraInfo.orientation) % 360;
  }
  
  public Camera.Size findOptimalPreviewSize(List<Camera.Size> paramList, double paramDouble)
  {
    double d1 = Double.MAX_VALUE;
    Object localObject1 = null;
    if (paramList == null) {
      return (Camera.Size)localObject1;
    }
    Display localDisplay = ((WindowManager)getApplicationContext().getSystemService("window")).getDefaultDisplay();
    int i = Math.max(localDisplay.getHeight(), localDisplay.getWidth());
    Log.v("testCamera", "display.getHeight() = " + localDisplay.getHeight() + " display.getWidth() = " + localDisplay.getWidth());
    if (i <= 0) {}
    for (int j = localDisplay.getHeight();; j = i)
    {
      Iterator localIterator1 = paramList.iterator();
      double d2 = d1;
      label119:
      Object localObject2;
      while (localIterator1.hasNext())
      {
        localObject2 = (Camera.Size)localIterator1.next();
        if (Math.abs(((Camera.Size)localObject2).width / ((Camera.Size)localObject2).height - paramDouble) <= 0.001D)
        {
          if (Math.abs(((Camera.Size)localObject2).height - j) >= d2) {
            break label289;
          }
          d2 = Math.abs(((Camera.Size)localObject2).height - j);
        }
      }
      for (;;)
      {
        localObject1 = localObject2;
        break label119;
        if (localObject1 != null) {
          break;
        }
        Log.w("testCamera", "No preview size match the aspect ratio");
        Iterator localIterator2 = paramList.iterator();
        while (localIterator2.hasNext())
        {
          Camera.Size localSize = (Camera.Size)localIterator2.next();
          if (Math.abs(localSize.height - j) < d1)
          {
            d1 = Math.abs(localSize.height - j);
            localObject1 = localSize;
          }
        }
        break;
        label289:
        localObject2 = localObject1;
      }
    }
  }
  
  public void finishPostViewTest()
  {
    this.cs = CameraStorage.getInstance();
    Intent localIntent = getIntent();
    localIntent.putExtra("data_filepath", this.cs.getFilePath(1));
    setResult(-1, localIntent);
    this.cs.clearFilePath();
    finish();
  }
  
  public CameraSettings getCameraSettings()
  {
    return this.mCameraSettings;
  }
  
  public int getLastOrientation()
  {
    return this.mLastOrientation;
  }
  
  public int getOrientationOnTake()
  {
    return this.mOrientationOnTake;
  }
  
  public void initialize()
  {
    this.bSentAck = false;
    this.mStopCamera = false;
    this.mShutterBtnlock = false;
    this.mWakeLock = ((PowerManager)getSystemService("power")).newWakeLock(805306394, "testCamera");
    this.mWakeLock.acquire();
    this.mPausing = false;
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.android.samsungtest.CameraStop");
    registerReceiver(this.mReceiver, localIntentFilter);
    if (mContentResolver == null) {
      mContentResolver = getContentResolver();
    }
    this.mImageCapture = new ImageCapture();
    if (!ensureCameraDevice())
    {
      Log.e("testCamera", "onResume() - ensureCameraDevice is failed");
      return;
    }
    this.mParameters = this.mCameraDevice.getParameters();
    getCameraSettings();
    if (CameraSettings.hasFlash())
    {
      if (this.cameraType != 0) {
        break label282;
      }
      if (this.mFlashEnable != 1) {
        break label256;
      }
      this.mParameters.set("flash-mode", "torch");
      Log.e("testCamera", "flash-mode is FLASH_TORCH");
    }
    for (;;)
    {
      this.mParameters.setZoom(0);
      this.mCameraDevice.setParameters(this.mParameters);
      restartPreview();
      updateFocusIndicator();
      try
      {
        this.mFocusToneGenerator = new ToneGenerator(1, 85);
        return;
      }
      catch (RuntimeException localRuntimeException)
      {
        Log.w("testCamera", "Exception caught while creating local tone generator: " + localRuntimeException);
        this.mFocusToneGenerator = null;
        return;
      }
      label256:
      this.mParameters.set("flash-mode", "on");
      Log.e("testCamera", "flash-mode is FLASH_ON");
      continue;
      label282:
      this.mParameters.set("flash-mode", "off");
      Log.e("testCamera", "flash-mode is FLASH_OFF");
    }
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    switch (paramInt1)
    {
    }
    do
    {
      return;
      if (paramInt2 == -1)
      {
        this.cameraType = paramIntent.getIntExtra("camera_id", -1);
        this.ommisionTest = paramIntent.getBooleanExtra("ommision_test", false);
        this.mFlashEnable = paramIntent.getIntExtra("torch_on", 0);
        this.mFinishCamcorderPreviewTest = paramIntent.getBooleanExtra("camcorder_preview_test", false);
        Log.i("testCamera", "onActivityResult : cameraType[" + this.cameraType + "] ommisionTest[" + this.ommisionTest + "] mFlashEnable[" + this.mFlashEnable + "]" + "mFinishCamcorderPreviewTest[" + this.mFinishCamcorderPreviewTest + "]");
        initDTPsetting();
        setStoragepath();
        startCamera();
        initLayoutSetting();
        initialize();
        return;
      }
      if (paramInt2 == 0)
      {
        finish();
        return;
      }
      dialogErrorPopup(2131165193);
      return;
    } while (paramInt2 != -1);
    finishPostViewTest();
  }
  
  public void onCheckDataLineDone()
  {
    Log.i("testCamera", "onChkDataLineDone");
    this.bDoneDTP = true;
    this.mParameters = this.mCameraDevice.getParameters();
    this.mParameters.dump();
    this.mParameters.set("chk_dataline", "0");
    getCameraSettings();
    if (CameraSettings.hasFlash()) {
      this.mParameters.set("flash-mode", "auto");
    }
    stopPreview();
    try
    {
      Log.i("testCamera", "mCameraDevice.setParameters(mParameters)");
      this.mCameraDevice.setParameters(this.mParameters);
      label95:
      getCameraSettings();
      if ((CameraSettings.needToCheckCamcorderPreviewTest()) && (!this.mFinishCamcorderPreviewTest) && (this.cameraType == 0))
      {
        Log.i("testCamera", "finish onchkdataline. go to camcorder preview test");
        Intent localIntent = new Intent(this, CamcorderPreviewTest.class);
        localIntent.putExtra("camera_id", 0);
        localIntent.putExtra("picture-size", 1);
        startActivityForResult(localIntent, 1);
        return;
      }
      this.mParameters = this.mCameraDevice.getParameters();
      getCameraSettings();
      if (CameraSettings.hasFlash())
      {
        if (this.cameraType != 0) {
          break label362;
        }
        if (this.mFlashEnable != 1) {
          break label336;
        }
        this.mParameters.set("flash-mode", "torch");
        Log.e("testCamera", "flash-mode is FLASH_TORCH");
      }
      try
      {
        for (;;)
        {
          Log.i("testCamera", "mCameraDevice.setParameters(mParameters)");
          this.mCameraDevice.setParameters(this.mParameters);
          try
          {
            label252:
            Log.i("testCamera", "mCameraDevice.startPreview()");
            if (this.bEnablePreviewCb) {
              this.mCameraDevice.setPreviewCallback(this.mPreviewCallback);
            }
            this.mCameraDevice.startPreview();
            if (this.mOrientationListener != null) {
              this.mOrientationListener.enable();
            }
            getCameraSettings();
            if (CameraSettings.needToCheckDTP(this.cameraType)) {
              startTimerCaptureBlock();
            }
            this.mPreviewing = true;
            Log.i("testCamera", "onChkDataLineDone : mPreviewing set true");
            return;
          }
          catch (Throwable localThrowable)
          {
            label336:
            Log.e("testCamera", "exception while startPreview", localThrowable);
            dialogErrorPopup(2131165193);
            return;
          }
          this.mParameters.set("flash-mode", "on");
          Log.i("testCamera", "flash-mode is FLASH_ON");
          continue;
          label362:
          this.mParameters.set("flash-mode", "off");
          Log.i("testCamera", "flash-mode is FLASH_OFF");
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException2)
      {
        break label252;
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      break label95;
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.i("testCamera", "onCreate");
    FtUtil.setSystemKeyBlock(getComponentName(), 3);
    FtUtil.setSystemKeyBlock(getComponentName(), 26);
    getIntentInfo();
    initDTPsetting();
    typeDevice = Support.Feature.getString("DEVICE_TYPE");
    if (typeDevice == null) {
      typeDevice = "phone";
    }
    if ("selftest".equals(this.testType))
    {
      this.bDoneDTP = true;
      this.bCheckDTP = false;
      this.bEnablePreviewCb = false;
    }
    do
    {
      setStoragepath();
      startCamera();
      initLayoutSetting();
      return;
      getCameraSettings();
    } while ((!CameraSettings.needToCheckCamcorderPreviewTest()) || (this.bDoneDTP != true) || (this.mFinishCamcorderPreviewTest) || (this.mFlashEnable == 1) || (this.cameraType != 0));
    Log.i("testCamera", "onCreate skip DTP test..go to camcorder preview test");
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    int i = 1;
    getWindow().addFlags(128);
    switch (paramInt)
    {
    default: 
      i = super.onKeyDown(paramInt, paramKeyEvent);
    }
    label519:
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
                                return i;
                              } while (this.mCameraDevice == null);
                              getCameraSettings();
                            } while ((!CameraSettings.hasZoom(this.cameraType)) || (this.cameraType != 0));
                            this.mParameters = this.mCameraDevice.getParameters();
                            this.mZoomValue = this.mParameters.getZoom();
                            Log.e("testCamera", "KEYCODE_VOLUME_DOWN - [zoom max]:[" + this.mZoomValue + " " + this.mParameters.getMaxZoom() + "]");
                            if (this.mZoomValue >= this.mParameters.getMaxZoom()) {
                              this.mParameters.setZoom(this.mParameters.getMaxZoom());
                            }
                            for (;;)
                            {
                              this.mCameraDevice.setParameters(this.mParameters);
                              return i;
                              Camera.Parameters localParameters2 = this.mParameters;
                              int k = 1 + this.mZoomValue;
                              this.mZoomValue = k;
                              localParameters2.setZoom(k);
                            }
                          } while (this.mCameraDevice == null);
                          getCameraSettings();
                        } while ((!CameraSettings.hasZoom(this.cameraType)) || (this.cameraType != 0));
                        this.mParameters = this.mCameraDevice.getParameters();
                        this.mZoomValue = this.mParameters.getZoom();
                        Log.e("testCamera", "KEYCODE_VOLUME_UP - zoom : " + this.mZoomValue);
                        if (this.mZoomValue <= 0) {
                          this.mParameters.setZoom(0);
                        }
                        for (;;)
                        {
                          this.mCameraDevice.setParameters(this.mParameters);
                          return i;
                          Camera.Parameters localParameters1 = this.mParameters;
                          int j = -1 + this.mZoomValue;
                          this.mZoomValue = j;
                          localParameters1.setZoom(j);
                        }
                      } while (!this.mPreviewing);
                      if (!this.ommisionTest) {
                        break label519;
                      }
                      if (!this.mIsPressedBackkey)
                      {
                        this.mCurrentTime = Calendar.getInstance().getTimeInMillis();
                        this.mIsPressedBackkey = i;
                        startTimer();
                        return i;
                      }
                      this.mIsPressedBackkey = false;
                      if (Calendar.getInstance().getTimeInMillis() > 2000L + this.mCurrentTime) {
                        break;
                      }
                    } while ((this.mStatus == 2) || (this.mStatus == 3));
                    setResult(-1, getIntent());
                    break;
                  } while ((this.mStatus == 2) || (this.mStatus == 3));
                  setResult(-1, getIntent());
                  break;
                } while (!this.mIsCaptureEnable);
                if ((this.mFocusState == i) || (this.mFocusState == 2) || (this.mStatus == 2))
                {
                  Log.i("testCamera", "ignore KEYCODE_FOCUS - mFocusState : " + this.mFocusState + " mStatus: " + this.mStatus);
                  return i;
                }
              } while (paramKeyEvent.getRepeatCount() != 0);
              doFocus(i);
              return i;
            } while (!this.mIsCaptureEnable);
            if (this.mStatus == 2)
            {
              Log.i("testCamera", "ignore KEYCODE_CAMERA - mFocusState : " + this.mFocusState + " mStatus: " + this.mStatus);
              return i;
            }
          } while (paramKeyEvent.getRepeatCount() != 0);
          doSnap();
          return i;
          if ((this.mFocusState == i) || (this.mFocusState == 2) || (this.mStatus == 2))
          {
            Log.i("testCamera", "ignore KEYCODE_DPAD_CENTER - mFocusState : " + this.mFocusState + " mStatus: " + this.mStatus);
            return i;
          }
        } while (paramKeyEvent.getRepeatCount() != 0);
        doFocus(i);
        if (this.mShutterButton.isInTouchMode()) {
          this.mShutterButton.requestFocusFromTouch();
        }
        for (;;)
        {
          this.mShutterButton.setPressed(i);
          return i;
          this.mShutterButton.requestFocus();
        }
        Log.v("testCamera", "KEYCODE_1 pressed");
      } while ((paramKeyEvent.getRepeatCount() != 0) || (this.mCameraDevice == null));
      this.mParameters = this.mCameraDevice.getParameters();
      if (getCameraSettings().isSupportedFocusModes("macro", this.mParameters.getSupportedFocusModes()))
      {
        this.mParameters.setFocusMode("macro");
        try
        {
          Log.i("testCamera", "mCameraDevice.setParameters(mParameters)");
          this.mCameraDevice.setParameters(this.mParameters);
          return i;
        }
        catch (IllegalArgumentException localIllegalArgumentException2)
        {
          return i;
        }
        catch (RuntimeException localRuntimeException2)
        {
          Log.i("testCamera", "setParameter fail");
          this.mMainHandler.sendEmptyMessage(9);
          return i;
        }
      }
      Log.v("testCamera", "not support macro mode");
      Toast.makeText(this, "Not support Macro Mode", 0).show();
      return i;
      Log.v("testCamera", "KEYCODE_2 pressed");
    } while ((paramKeyEvent.getRepeatCount() != 0) || (this.mCameraDevice == null));
    this.mParameters = this.mCameraDevice.getParameters();
    if (getCameraSettings().isSupportedFocusModes(this.mParameters.getSupportedFocusModes()))
    {
      this.mParameters.setFocusMode(getCameraSettings().getFocusMode());
      try
      {
        Log.i("testCamera", "mCameraDevice.setParameters(mParameters)");
        this.mCameraDevice.setParameters(this.mParameters);
        return i;
      }
      catch (IllegalArgumentException localIllegalArgumentException1)
      {
        return i;
      }
      catch (RuntimeException localRuntimeException1)
      {
        Log.i("testCamera", "setParameter fail");
        this.mMainHandler.sendEmptyMessage(9);
        return i;
      }
    }
    Log.v("testCamera", "not support focusmode");
    return i;
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    int i = 1;
    switch (paramInt)
    {
    default: 
      i = super.onKeyUp(paramInt, paramKeyEvent);
    }
    do
    {
      do
      {
        return i;
      } while (!this.mIsCaptureEnable);
      doFocus(false);
      return i;
      if ((this.cameraType == 0) && (this.mStatus == 4))
      {
        Intent localIntent = getIntent();
        localIntent.putExtra("data_filepath", this.cs.getFilePath(0));
        setResult(-1, localIntent);
        finish();
        return i;
      }
    } while ((this.cameraType != i) || (this.mStatus != 4));
    startPostViewTest();
    return i;
  }
  
  protected void onPause()
  {
    if (this.mCameraDevice != null) {
      this.mCameraDevice.setPreviewCallback(null);
    }
    Log.i("testCamera", "onPause()");
    if (this.mWakeLock != null)
    {
      if (this.mWakeLock.isHeld())
      {
        Log.i("testCamera", "releaseWakelock");
        this.mWakeLock.release();
      }
      this.mWakeLock = null;
    }
    this.mShutterBtnlock = false;
    this.mPausing = true;
    this.mMainHandler.removeCallbacks(this.mStartCheck);
    this.mMainHandler.removeCallbacks(this.mDatalineCheck);
    this.mTimerHandler.removeMessages(11);
    stopPreview();
    closeCamera();
    if ((this.mErrorPopup != null) && (this.mErrorPopup.isShowing())) {
      this.mErrorPopup.dismiss();
    }
    try
    {
      unregisterReceiver(this.mReceiver);
      label145:
      if (this.mFocusToneGenerator != null)
      {
        this.mFocusToneGenerator.release();
        this.mFocusToneGenerator = null;
      }
      Log.i("testCamera", "mImageCapture = null");
      this.mImageCapture = null;
      if (this.mStopCamera)
      {
        sendBroadCastAck("com.android.samsungtest.CAMERA_STOP_ACK");
        this.mStopCamera = false;
      }
      super.onPause();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      break label145;
    }
  }
  
  public void onResume()
  {
    int i = 0;
    super.onResume();
    Log.w("testCamera", "onResume()");
    getCameraSettings();
    if ((CameraSettings.needToCheckCamcorderPreviewTest()) && (this.bEnablePreviewCb) && (this.bDoneDTP == true) && (!this.mFinishCamcorderPreviewTest) && (this.mFlashEnable != 1) && (this.cameraType == 0))
    {
      Log.i("testCamera", "onResume skip DTP test..go to camcorder preview test");
      Intent localIntent = new Intent(this, CamcorderPreviewTest.class);
      localIntent.putExtra("camera_id", 0);
      localIntent.putExtra("picture-size", 1);
      startActivityForResult(localIntent, 1);
      overridePendingTransition(0, 0);
      return;
    }
    this.mNumberOfCameras = android.hardware.Camera.getNumberOfCameras();
    this.mCameraInfo = new Camera.CameraInfo[this.mNumberOfCameras];
    while (i < this.mNumberOfCameras)
    {
      this.mCameraInfo[i] = new Camera.CameraInfo();
      android.hardware.Camera.getCameraInfo(i, this.mCameraInfo[i]);
      i++;
    }
    setOrientationListener();
    initialize();
  }
  
  public void onShutterButtonClick(ShutterButton paramShutterButton)
  {
    Log.i("testCamera", "call onShutterButtonClick");
    if (this.mPausing) {
      return;
    }
    if (this.mStatus == 2)
    {
      Log.i("testCamera", "ignore onShutterButtonClick - mFocusState : " + this.mFocusState + " mStatus: " + this.mStatus);
      return;
    }
    if (!this.mIsCaptureEnable)
    {
      Log.e("testCamera", "mIsCaptureEnble is false");
      return;
    }
    if (this.mShutterBtnlock)
    {
      Log.e("testCamera", "mShutterBtnlock is true");
      return;
    }
    switch (paramShutterButton.getId())
    {
    default: 
      return;
    }
    doSnap();
  }
  
  public void onShutterButtonFocus(ShutterButton paramShutterButton, boolean paramBoolean)
  {
    Log.i("testCamera", "call onShutterButtonFocus");
    if (this.mPausing) {
      return;
    }
    if ((this.mFocusState == 1) || (this.mFocusState == 2) || (this.mStatus == 2))
    {
      Log.i("testCamera", "ignore onShutterButtonFocus - mFocusState : " + this.mFocusState + " mStatus: " + this.mStatus);
      this.mFocusState = 2;
      return;
    }
    if (!this.mIsCaptureEnable)
    {
      Log.e("testCamera", "mIsCaptureEnble is false");
      return;
    }
    if (this.mShutterBtnlock)
    {
      Log.e("testCamera", "mShutterBtnlock is true");
      return;
    }
    switch (paramShutterButton.getId())
    {
    default: 
      return;
    }
    doFocus(paramBoolean);
  }
  
  public void onStop()
  {
    Log.i("testCamera", "onStop()");
    super.onStop();
  }
  
  protected void setOrientationListener()
  {
    if (this.mOrientationListener == null) {
      this.mOrientationListener = new OrientationEventListener(this)
      {
        public void onOrientationChanged(int paramAnonymousInt)
        {
          if (paramAnonymousInt == -1)
          {
            Log.e("testCamera", "onOrientationChanged: orientation - unknown orientation");
            return;
          }
          Camera.this.setLastOrientation(Camera.roundOrientation(paramAnonymousInt));
        }
      };
    }
    this.mOrientationListener.disable();
  }
  
  protected void setOrientationOnTake(int paramInt)
  {
    this.mOrientationOnTake = paramInt;
  }
  
  public void shortSnapshotcancelAutoFocus()
  {
    Log.i("testCamera", "short snapshot cancelAutoFocus");
    if (this.mCameraDevice != null) {
      this.mCameraDevice.cancelAutoFocus();
    }
    this.mMainHandler.sendEmptyMessageDelayed(8, 1000L);
  }
  
  public void startPostViewTest()
  {
    startActivityForResult(new Intent(this, PostViewTest.class), 2);
  }
  
  protected void startTimer()
  {
    this.mTimerHandler.sendEmptyMessageDelayed(1, 2000L);
  }
  
  protected void startTimerCaptureBlock()
  {
    this.mIsCaptureEnable = false;
    this.mTimerHandler.removeMessages(2);
    this.mTimerHandler.sendEmptyMessageDelayed(2, 1000L);
  }
  
  public byte[] streamToBytes(InputStream paramInputStream, int paramInt)
  {
    localByteArrayOutputStream = new ByteArrayOutputStream(paramInt);
    byte[] arrayOfByte = new byte[paramInt];
    try
    {
      for (;;)
      {
        int i = paramInputStream.read(arrayOfByte);
        if (i < 0) {
          break;
        }
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
      return localByteArrayOutputStream.toByteArray();
    }
    catch (IOException localIOException) {}
  }
  
  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
  {
    Log.i("testCamera", "surfaceChanged");
    if (paramInt2 < paramInt3) {
      Log.i("testCamera", "swap - w : " + paramInt3 + " h : " + paramInt2);
    }
    for (;;)
    {
      this.mSurfaceView.setVisibility(0);
      if (this.mOriginalViewFinderWidth == 0)
      {
        this.mOriginalViewFinderWidth = paramInt3;
        this.mOriginalViewFinderHeight = paramInt2;
      }
      setViewFinder(paramInt3, paramInt2, true);
      this.mCaptureObject = this.mImageCapture;
      return;
      int i = paramInt3;
      paramInt3 = paramInt2;
      paramInt2 = i;
    }
  }
  
  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    Log.i("testCamera", "surfaceCreated");
    this.mSurfaceHolder = paramSurfaceHolder;
  }
  
  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    Log.i("testCamera", "surfaceDestroyed");
    stopPreview();
    this.mSurfaceHolder = null;
  }
  
  private final class AutoFocusCallback
    implements android.hardware.Camera.AutoFocusCallback
  {
    private AutoFocusCallback() {}
    
    public void onAutoFocus(boolean paramBoolean, android.hardware.Camera paramCamera)
    {
      Log.e("testCamera", "onAutoFocusL: " + paramBoolean);
      if (paramBoolean)
      {
        Camera.access$3602(Camera.this, 0);
        Camera.access$3702(Camera.this, System.currentTimeMillis());
        Log.i("testCamera", "Auto focus took " + (Camera.this.mFocusCallbackTime - Camera.this.mFocusStartTime) + " ms.");
        if (Camera.this.mCaptureObject == null)
        {
          Log.i("testCamera", "onAutoFocus - mCaptureObject == null ");
          Camera.access$602(Camera.this, Camera.this.mImageCapture);
        }
        if ((Camera.this.mFocusState != 2) || (Camera.this.mCaptureObject == null)) {
          break label274;
        }
        Log.i("testCamera", "onAutoFocus - mFocusState == FOCUSING_SNAP_ON_FINISH && mCaptureObject != null");
        if (!paramBoolean) {
          break label219;
        }
        Camera.access$4002(Camera.this, 3);
        Camera.this.updateFocusIndicator();
        Camera.this.mFocusToneGenerator.startTone(28);
        Camera.this.mCaptureObject.onSnap();
      }
      for (;;)
      {
        label200:
        Camera.this.updateFocusIndicator();
        label219:
        do
        {
          return;
          Camera.access$3608(Camera.this);
          break;
          Camera.access$4002(Camera.this, 4);
          Camera.this.updateFocusIndicator();
          if (Camera.this.mAF_Fail_Count > 2) {
            Camera.this.dialogFocusPopup();
          }
          Camera.this.mFocusToneGenerator.startTone(28);
          Camera.this.shortSnapshotcancelAutoFocus();
          return;
          if (Camera.this.mFocusState != 1) {
            break label433;
          }
          Log.i("testCamera", "onAutoFocus - mFocusState == FOCUSING");
          Camera.this.mFocusToneGenerator.startTone(28);
          if (paramBoolean)
          {
            Camera.access$4002(Camera.this, 3);
            Camera.access$3602(Camera.this, 0);
            Camera.this.updateFocusIndicator();
            break label200;
          }
          Camera.access$4002(Camera.this, 4);
          Camera.this.updateFocusIndicator();
          Log.i("testCamera", "CameraSettings.FOCUSING = " + Camera.this.mAF_Fail_Count);
          if (Camera.this.mAF_Fail_Count > 2)
          {
            Camera.access$3602(Camera.this, 0);
            Camera.this.dialogFocusPopup();
          }
        } while (Camera.this.mCameraDevice == null);
        label274:
        Camera.this.mCameraDevice.cancelAutoFocus();
        return;
        label433:
        if (Camera.this.mFocusState == 0)
        {
          Log.i("testCamera", "onAutoFocus - mFocusState == FOCUS_NOT_STARTED");
          if (Camera.this.mCameraDevice == null) {}
        }
      }
    }
  }
  
  public final class ErrorCallback
    implements android.hardware.Camera.ErrorCallback
  {
    public ErrorCallback() {}
    
    public void onError(int paramInt, android.hardware.Camera paramCamera)
    {
      Log.w("testCamera", "onError - " + paramInt);
      switch (paramInt)
      {
      default: 
        Log.e("testCamera", "ErrorCallback - CAMERA BAD[" + paramInt + "]");
        Camera.this.dialogErrorPopup(2131165193);
      case 0: 
        return;
      case 1000: 
        Log.e("testCamera", "ErrorCallback - CAMERA_ERROR_WRONG_FW");
        Camera.this.dialogErrorPopup(2131165195);
        return;
      case 2001: 
        Log.e("testCamera", "ErrorCallback - CAMERA_ERROR_DATALINE_SUCCESS");
        Camera.this.mMainHandler.sendEmptyMessage(6);
        return;
      }
      Log.e("testCamera", "ErrorCallback - CAMERA_ERROR_DATALINE_FAIL");
      if (Camera.this.cameraType == 1)
      {
        Camera.this.dialogErrorPopup(2131165192);
        return;
      }
      Camera.this.dialogErrorPopup(2131165191);
    }
  }
  
  private class ImageCapture
    implements Capturer
  {
    private boolean mCapturing = false;
    
    public ImageCapture() {}
    
    private void capture(boolean paramBoolean)
    {
      Camera.access$2302(Camera.this, false);
      Log.i("testCamera", "capture : mPreviewing set false");
      if ("selftest".equals(Camera.this.testType)) {
        if (Camera.this.cameraType == 1) {
          Camera.this.getCameraSettings().setPictureSize(Camera.this.cameraType, Camera.this.mParameters);
        }
      }
      for (;;)
      {
        Camera.this.mCameraDevice.setParameters(Camera.this.mParameters);
        Camera.this.mCameraDevice.takePicture(Camera.this.mShutterCallback, Camera.this.mRawPictureCallback, new Camera.JpegPictureCallback(Camera.this, null));
        return;
        Camera.this.getCameraSettings().setPictureSize(Camera.this.cameraType, Camera.this.mParameters);
        continue;
        Camera.this.getCameraSettings().setPictureSize(Camera.this.cameraType, Camera.this.mParameters);
      }
    }
    
    public void dismissFreezeFrame()
    {
      Log.i("testCamera", "dismissFreezeFrame");
      if (Camera.this.mStatus == 2)
      {
        Camera.this.mMainHandler.sendEmptyMessage(3);
        return;
      }
      Camera.this.restartPreview();
    }
    
    public void initiate(boolean paramBoolean)
    {
      if (Camera.this.mCameraDevice == null) {
        return;
      }
      this.mCapturing = true;
      capture(paramBoolean);
    }
    
    public void onSnap()
    {
      Log.w("testCamera", "onSnap()");
      if (Camera.this.mPausing)
      {
        Log.w("testCamera", "onSnap() - mPausing == true, return ");
        return;
      }
      Camera.access$1502(Camera.this, System.currentTimeMillis());
      if ((Camera.this.mStatus == 2) || (Camera.this.mStatus == 4))
      {
        Camera.this.mMainHandler.sendEmptyMessage(3);
        return;
      }
      Log.i("testCamera", "mStatus = SNAPSHOT_IN_PROGRESS");
      Camera.access$402(Camera.this, 2);
      Camera.this.mImageCapture.initiate(true);
    }
  }
  
  private final class JpegPictureCallback
    implements Camera.PictureCallback
  {
    byte[] mJpegData = null;
    
    public JpegPictureCallback(Location paramLocation) {}
    
    public void SavingImageForCaptureIntent(android.hardware.Camera paramCamera)
    {
      if (this.mJpegData != null)
      {
        Camera.this.capturedData = this.mJpegData;
        this.mJpegData = null;
      }
      Log.i("testCamera", "mStatus = SNAPSHOT_COMPLETED");
      Camera.access$402(Camera.this, 4);
      storeImage(Camera.this.capturedData, paramCamera);
      if ("selftest".equals(Camera.this.testType))
      {
        Camera.this.clearFocusState();
        Camera.this.updateFocusIndicator();
        Camera.this.restartPreview();
        return;
      }
      Camera.access$2002(Camera.this, CameraStorage.getInstance());
      Camera.this.cs.setFilePath(Camera.this.cameraType, Camera.this.filePath);
      if (!Camera.this.mFinishPostViewTest)
      {
        Camera.access$2202(Camera.this, true);
        Camera.access$2302(Camera.this, true);
        Camera.this.stopPreview();
        return;
      }
      if (Camera.this.cameraType == 0)
      {
        Intent localIntent = Camera.this.getIntent();
        localIntent.putExtra("data_filepath", Camera.this.filePath);
        Camera.this.setResult(-1, localIntent);
      }
      Camera.this.mMainHandler.sendEmptyMessage(10);
    }
    
    public void onPictureTaken(byte[] paramArrayOfByte, android.hardware.Camera paramCamera)
    {
      if (Camera.this.mPausing) {
        return;
      }
      Camera.access$1002(Camera.this, System.currentTimeMillis());
      Log.i("testCamera", Camera.this.mJpegPictureCallbackTime - Camera.this.mRawPictureCallbackTime + "ms elapsed between" + " RawPictureCallback and JpegPictureCallback.");
      this.mJpegData = paramArrayOfByte;
      SavingImageForCaptureIntent(paramCamera);
    }
    
    public void storeImage(byte[] paramArrayOfByte, android.hardware.Camera paramCamera)
    {
      Log.d("testCamera", "storeImage : bUseSdcard[" + Camera.this.bUseSdcard + "]");
      long l = System.currentTimeMillis();
      String str = Camera.createName(l) + ".jpg";
      try
      {
        if (Camera.this.bUseSdcard) {
          if (Camera.getAvailableSpaceSd() > 0L) {
            Camera.this.writeImage(Camera.DIRECTORY_CAMERA_SDCARD, str, paramArrayOfByte);
          }
        }
        for (;;)
        {
          int i = Camera.this.calculateOrientationForPicture(Camera.this.getOrientationOnTake());
          Camera.this.addImage(Camera.mContentResolver, str, l, paramArrayOfByte, i);
          return;
          Camera.this.writeImage(Camera.DIRECTORY_CAMERA_DATA, str, paramArrayOfByte);
        }
      }
      catch (Exception localException)
      {
        for (;;)
        {
          Log.e("testCamera", "Exception while compressing image.", localException);
        }
      }
    }
  }
  
  private class MainHandler
    extends Handler
  {
    private MainHandler() {}
    
    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      case 5: 
      default: 
      case 3: 
        do
        {
          return;
          if (Camera.this.mStatus == 2)
          {
            Camera.this.mMainHandler.sendEmptyMessageDelayed(3, 100L);
            return;
          }
        } while (Camera.this.mStatus != 4);
        Camera.this.mCaptureObject.dismissFreezeFrame();
        return;
      case 4: 
        Camera.this.getWindow().clearFlags(128);
        return;
      case 6: 
        Camera.this.onCheckDataLineDone();
        return;
      case 7: 
        Camera.this.finish();
        return;
      case 8: 
        Camera.this.clearFocusState();
        Camera.this.updateFocusIndicator();
        return;
      case 9: 
        Camera.this.dialogErrorPopup(2131165193);
        return;
      }
      if ((Camera.this.mJpegPictureCallbackTime - Camera.this.mShutterCallbackTime < 200L) && (Camera.this.mJpegPictureCallbackTime - Camera.this.mShutterCallbackTime > 0L))
      {
        Log.e("testCamera", "CameraSettings.CLOSE_CAMERA shutter delay is " + (200L - (Camera.this.mJpegPictureCallbackTime - Camera.this.mShutterCallbackTime)));
        Camera.this.mMainHandler.sendEmptyMessageDelayed(10, 200L - (Camera.this.mJpegPictureCallbackTime - Camera.this.mShutterCallbackTime));
        Camera.access$1002(Camera.this, 0L);
        Camera.access$1102(Camera.this, 0L);
        return;
      }
      if (Camera.this.cameraType == 0)
      {
        Camera.this.finish();
        return;
      }
      if (Camera.this.mFinishPostViewTest)
      {
        Camera.this.finishPostViewTest();
        return;
      }
      Camera.this.startPostViewTest();
    }
  }
  
  public final class PreviewCallback
    implements android.hardware.Camera.PreviewCallback
  {
    public PreviewCallback() {}
    
    public void onPreviewFrame(byte[] paramArrayOfByte, android.hardware.Camera paramCamera)
    {
      Log.e("testCamera", "onPreviewFrame - get the preview image");
      Camera.this.mMainHandler.removeCallbacks(Camera.this.mDatalineCheck);
      if ((!Camera.this.bDoneDTP) && (Camera.this.bCheckDTP == true))
      {
        if (Camera.this.checkDataline(paramArrayOfByte)) {
          break label194;
        }
        long l = System.currentTimeMillis();
        String str = Camera.createName(l) + ".dtp";
        Camera.this.writeImage(Camera.DIRECTORY_CAMERA_DATA, str, paramArrayOfByte);
        Log.e("testCamera", "checkDataline - false");
        if (Camera.this.cameraType != 1) {
          break label182;
        }
        Camera.this.dialogErrorPopup(2131165192);
      }
      for (;;)
      {
        Camera.access$3202(Camera.this, false);
        if ((Camera.this.bDoneDTP == true) && (!Camera.this.bSentAck))
        {
          Camera.this.sendBroadCastAck("com.android.samsungtest.CAMERA_GOOD");
          Camera.access$2202(Camera.this, false);
        }
        paramCamera.setPreviewCallback(null);
        return;
        label182:
        Camera.this.dialogErrorPopup(2131165191);
        continue;
        label194:
        Log.e("testCamera", "checkDataline - success");
        Camera.this.mMainHandler.sendEmptyMessage(6);
      }
    }
  }
  
  private final class RawPictureCallback
    implements Camera.PictureCallback
  {
    private RawPictureCallback() {}
    
    public void onPictureTaken(byte[] paramArrayOfByte, android.hardware.Camera paramCamera)
    {
      Log.i("testCamera", "got RawPictureCallback...");
      Camera.access$1602(Camera.this, System.currentTimeMillis());
      Log.i("testCamera", Camera.this.mRawPictureCallbackTime - Camera.this.mShutterCallbackTime + "ms elapsed between" + " ShutterCallback and RawPictureCallback.");
    }
  }
  
  private final class ShutterCallback
    implements android.hardware.Camera.ShutterCallback
  {
    private ShutterCallback() {}
    
    public void onShutter()
    {
      Camera.access$1102(Camera.this, System.currentTimeMillis());
      Log.i("testCamera", "Shutter lag was " + (Camera.this.mShutterCallbackTime - Camera.this.mCaptureStartTime) + " ms.");
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.camera.Camera
 * JD-Core Version:    0.7.1
 */