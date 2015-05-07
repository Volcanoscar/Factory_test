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
import android.database.sqlite.SQLiteFullException;
import android.hardware.Camera;
import android.hardware.Camera.ErrorCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager.WakeLock;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.Window;
import android.widget.TextView;
import com.sec.android.app.camera.framework.CameraSettings;
import com.sec.android.app.camera.framework.ShutterButton;
import com.sec.android.app.camera.framework.ShutterButton.OnShutterButtonListener;
import com.sec.factory.support.FtUtil;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

public class CamcorderPreviewTest
  extends Activity
  implements MediaRecorder.OnErrorListener, MediaRecorder.OnInfoListener, SurfaceHolder.Callback, ShutterButton.OnShutterButtonListener
{
  protected static final String CAMERA_IMAGE_BUCKET_NAME_PHONE = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera";
  public static String TAG = "CamcorderPreviewTest";
  private final int REQUEST_CAMCORDER_PREVIEW_TEST = 1;
  private boolean bSentAck;
  private int[][] camcorderpreview;
  private int cameraType = -1;
  private Camera mCameraDevice;
  private CameraSettings mCameraSettings;
  private FileDescriptor mCameraVideoFileDescriptor;
  private String mCameraVideoFilename;
  private ContentResolver mContentResolver;
  private int mCurResolution;
  private long mCurrentTime;
  private String mCurrentVideoFilename;
  private ContentValues mCurrentVideoValues;
  private ErrorCallback mErrorCallback;
  private AlertDialog mErrorPopup;
  private boolean mIsPressedBackkey;
  private boolean mIsRecordingStarted;
  private Handler mMainHandler;
  private MediaRecorder mMediaRecorder;
  private boolean mMediaRecorderRecording;
  private Camera.Parameters mParameters;
  private boolean mPausing;
  private Thread mPrepareRecordingThread;
  private PreviewCallback mPreviewCallback;
  private boolean mPreviewing;
  private final BroadcastReceiver mReceiver;
  private ShutterButton mShutterButton;
  private ShutterCallback mShutterCallback;
  Runnable mStartCheck;
  private Thread mStartRecordingThread;
  private ShutterButton mStopButton;
  private boolean mStopCamera;
  private SurfaceHolder mSurfaceHolder = null;
  private VideoPreview mSurfaceView;
  protected Handler mTimerHandler;
  private long mVideoRecordingTimeInMiliSecond;
  protected PowerManager.WakeLock mWakeLock;
  private boolean mchkopencamera;
  
  public CamcorderPreviewTest()
  {
    int[] arrayOfInt = { Feature.TEST_RESOLUTION.length, 2 };
    this.camcorderpreview = ((int[][])Array.newInstance(Integer.TYPE, arrayOfInt));
    this.mCurResolution = 0;
    this.mMainHandler = new MainHandler(null);
    this.mErrorPopup = null;
    this.mIsPressedBackkey = false;
    this.mCurrentTime = 0L;
    this.mchkopencamera = false;
    this.mShutterCallback = new ShutterCallback(null);
    this.mIsRecordingStarted = false;
    this.mVideoRecordingTimeInMiliSecond = 0L;
    this.mPrepareRecordingThread = null;
    this.mStartRecordingThread = null;
    this.mMediaRecorderRecording = false;
    this.mContentResolver = null;
    this.mPausing = false;
    this.bSentAck = false;
    this.mStopCamera = false;
    this.mPreviewing = false;
    this.mPreviewCallback = new PreviewCallback();
    this.mErrorCallback = new ErrorCallback();
    this.mCameraSettings = new CameraSettings(this);
    this.mStartCheck = new Runnable()
    {
      public void run()
      {
        Log.i(CamcorderPreviewTest.TAG, "Camera Start");
        CamcorderPreviewTest.this.sendBroadCastAck("com.android.samsungtest.CAMERA_GOOD");
        CamcorderPreviewTest.this.mCameraDevice.setPreviewCallback(null);
      }
    };
    this.mReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        String str = paramAnonymousIntent.getAction();
        Log.i(CamcorderPreviewTest.TAG, "onReceive" + str);
        if (str.equals("com.android.samsungtest.CameraStop"))
        {
          Log.i(CamcorderPreviewTest.TAG, "onReceive - get Stop Camera");
          CamcorderPreviewTest.access$1802(CamcorderPreviewTest.this, true);
          CamcorderPreviewTest.this.finish();
          return;
        }
        Log.e(CamcorderPreviewTest.TAG, "onReceive - this action[" + str + "] is not defined");
      }
    };
    this.mTimerHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        Log.e(CamcorderPreviewTest.TAG, "handleMessage: mTimerHandler -msg:" + paramAnonymousMessage.what);
        switch (paramAnonymousMessage.what)
        {
        default: 
          return;
        }
        Log.e(CamcorderPreviewTest.TAG, "handleMessage: KEY_TIMER_EXPIRED -mIsCaptureEnble:" + CamcorderPreviewTest.this.mIsPressedBackkey);
        CamcorderPreviewTest.access$1902(CamcorderPreviewTest.this, false);
      }
    };
  }
  
  private void cleanupEmptyFile()
  {
    if (this.mCameraVideoFilename != null)
    {
      File localFile = new File(this.mCameraVideoFilename);
      if ((localFile.length() == 0L) && (localFile.delete())) {
        this.mCameraVideoFilename = null;
      }
    }
  }
  
  private void cleanupTempFile()
  {
    if (new File(getTempFileName()).delete()) {}
  }
  
  private void closeCamera()
  {
    Log.i(TAG, "closeCamera");
    if (this.mCameraDevice != null)
    {
      this.mCameraDevice.release();
      this.mCameraDevice = null;
    }
  }
  
  private void createVideoPath()
  {
    long l = System.currentTimeMillis();
    String str1 = CAMERA_IMAGE_BUCKET_NAME_PHONE;
    new File(str1).mkdirs();
    Date localDate = new Date(l);
    String str2 = DateFormat.format("yyyyMMdd_kkmmss", localDate).toString();
    String str3 = str1 + "/" + str2 + ".mp4";
    Object localObject = str2;
    String str4 = (String)localObject + ".mp4";
    File localFile = new File(str3);
    int j;
    for (int i = 0; localFile.exists(); i = j)
    {
      Log.e(TAG, "Duplicated file name found: " + str3);
      localDate.setTime(l);
      String str5 = DateFormat.format("yyyyMMdd_kkmmss", localDate).toString();
      StringBuilder localStringBuilder = new StringBuilder().append(str1).append("/").append(str5).append("(");
      j = i + 1;
      str3 = i + ")" + ".mp4";
      localObject = str5;
      str4 = (String)localObject + ".mp4";
      localFile = new File(str3);
    }
    ContentValues localContentValues = new ContentValues(7);
    localContentValues.put("title", (String)localObject);
    localContentValues.put("_display_name", str4);
    localContentValues.put("datetaken", Long.valueOf(l));
    localContentValues.put("mime_type", "video/mp4");
    localContentValues.put("_data", str3);
    this.mCameraVideoFilename = str3;
    this.mCurrentVideoValues = localContentValues;
  }
  
  private void dialogErrorPopup(int paramInt)
  {
    Log.v(TAG, "dialogErrorPopup");
    if ((this.mErrorPopup != null) && (this.mErrorPopup.isShowing())) {
      this.mErrorPopup.dismiss();
    }
    this.mMainHandler.removeCallbacks(this.mStartCheck);
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
          Intent localIntent = CamcorderPreviewTest.this.getIntent();
          CamcorderPreviewTest.this.setResult(0, localIntent);
          CamcorderPreviewTest.this.finish();
        }
      });
      localBuilder.setCancelable(false);
      this.mErrorPopup = localBuilder.create();
      this.mErrorPopup.show();
    }
    this.mchkopencamera = false;
  }
  
  private String getTempFileName()
  {
    String str = CAMERA_IMAGE_BUCKET_NAME_PHONE;
    return str + "/" + "temp_video";
  }
  
  private void initializeMediaRecorder()
  {
    Log.v(TAG, "initializeRecorder");
    if (this.mMediaRecorder != null) {
      return;
    }
    this.mMediaRecorder = new MediaRecorder();
    this.mCameraDevice.unlock();
    this.mMediaRecorder.setCamera(this.mCameraDevice);
    this.mMediaRecorder.setOnErrorListener(this);
    this.mMediaRecorder.setOnInfoListener(this);
    this.mMediaRecorder.setAudioSource(5);
    this.mMediaRecorder.setVideoSource(1);
    this.mMediaRecorder.setOutputFormat(2);
    this.mMediaRecorder.setMaxDuration(30000);
    if (this.mCameraVideoFileDescriptor != null) {
      this.mMediaRecorder.setOutputFile(this.mCameraVideoFileDescriptor);
    }
    for (;;)
    {
      createVideoPath();
      this.mMediaRecorder.setOutputFile(getTempFileName());
      this.mMediaRecorder.setVideoFrameRate(30);
      this.mMediaRecorder.setVideoSize(this.camcorderpreview[this.mCurResolution][0], this.camcorderpreview[this.mCurResolution][1]);
      if ((this.camcorderpreview[this.mCurResolution][0] == 1920) && (this.camcorderpreview[this.mCurResolution][1] == 1080))
      {
        this.mMediaRecorder.setVideoEncodingBitRate(17000000);
        this.mMediaRecorder.setAudioEncodingBitRate(128000);
        this.mMediaRecorder.setAudioChannels(2);
        this.mMediaRecorder.setAudioSamplingRate(48000);
        this.mMediaRecorder.setVideoEncoder(2);
        this.mMediaRecorder.setAudioEncoder(3);
        label256:
        this.mMediaRecorder.setPreviewDisplay(this.mSurfaceHolder.getSurface());
      }
      try
      {
        this.mMediaRecorder.setMaxFileSize(4294967295L);
        if (this.mMediaRecorder != null) {
          this.mMediaRecorder.setOrientationHint(0);
        }
        if (this.mMediaRecorder == null) {}
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        try
        {
          this.mMediaRecorder.prepare();
          this.mMediaRecorderRecording = false;
          return;
          createVideoPath();
          this.mMediaRecorder.setOutputFile(getTempFileName());
          continue;
          if ((this.camcorderpreview[this.mCurResolution][0] == 1280) && (this.camcorderpreview[this.mCurResolution][1] == 720))
          {
            this.mMediaRecorder.setVideoEncodingBitRate(12000000);
            this.mMediaRecorder.setAudioEncodingBitRate(128000);
            this.mMediaRecorder.setAudioChannels(2);
            this.mMediaRecorder.setAudioSamplingRate(48000);
            this.mMediaRecorder.setVideoEncoder(2);
            this.mMediaRecorder.setAudioEncoder(3);
            break label256;
          }
          if ((this.camcorderpreview[this.mCurResolution][0] == 720) && (this.camcorderpreview[this.mCurResolution][1] == 480))
          {
            this.mMediaRecorder.setVideoEncodingBitRate(3449000);
            this.mMediaRecorder.setAudioEncodingBitRate(128000);
            this.mMediaRecorder.setAudioChannels(2);
            this.mMediaRecorder.setAudioSamplingRate(48000);
            this.mMediaRecorder.setVideoEncoder(2);
            this.mMediaRecorder.setAudioEncoder(3);
            break label256;
          }
          if ((this.camcorderpreview[this.mCurResolution][0] == 640) && (this.camcorderpreview[this.mCurResolution][1] == 480))
          {
            this.mMediaRecorder.setVideoEncodingBitRate(3078000);
            this.mMediaRecorder.setAudioEncodingBitRate(128000);
            this.mMediaRecorder.setAudioChannels(2);
            this.mMediaRecorder.setAudioSamplingRate(48000);
            this.mMediaRecorder.setVideoEncoder(2);
            this.mMediaRecorder.setAudioEncoder(3);
            break label256;
          }
          if ((this.camcorderpreview[this.mCurResolution][0] != 320) || (this.camcorderpreview[this.mCurResolution][1] != 240)) {
            break label256;
          }
          this.mMediaRecorder.setVideoEncodingBitRate(767000);
          this.mMediaRecorder.setAudioEncodingBitRate(128000);
          this.mMediaRecorder.setAudioChannels(2);
          this.mMediaRecorder.setAudioSamplingRate(48000);
          this.mMediaRecorder.setVideoEncoder(2);
          this.mMediaRecorder.setAudioEncoder(3);
          break label256;
          localIllegalArgumentException = localIllegalArgumentException;
          Log.e(TAG, localIllegalArgumentException.toString());
          releaseMediaRecorder();
        }
        catch (IOException localIOException)
        {
          for (;;)
          {
            Log.e(TAG, localIOException.toString());
            releaseMediaRecorder();
            doStopVideoRecordingSync();
            try
            {
              this.mCameraDevice.startPreview();
              this.mPreviewing = true;
            }
            catch (Throwable localThrowable)
            {
              Log.e(TAG, "exception while startPreview", localIOException);
              dialogErrorPopup(2131165193);
            }
          }
        }
      }
    }
  }
  
  private void registerVideo()
  {
    Log.v(TAG, "registerVideo");
    Uri localUri;
    if (this.mCameraVideoFileDescriptor == null)
    {
      localUri = Uri.parse("content://media/external/video/media");
      this.mCurrentVideoValues.put("_size", Long.valueOf(new File(this.mCurrentVideoFilename).length()));
      this.mCurrentVideoValues.put("duration", Long.valueOf(this.mVideoRecordingTimeInMiliSecond));
      this.mCurrentVideoValues.put("resolution", Integer.toString(this.camcorderpreview[this.mCurResolution][0]) + "x" + Integer.toString(this.camcorderpreview[this.mCurResolution][1]));
    }
    try
    {
      sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", getContentResolver().insert(localUri, this.mCurrentVideoValues)));
      this.mCurrentVideoValues = null;
      return;
    }
    catch (SQLiteFullException localSQLiteFullException)
    {
      for (;;)
      {
        Log.e(TAG, "Not enough space in database");
      }
    }
    catch (UnsupportedOperationException localUnsupportedOperationException)
    {
      for (;;)
      {
        Log.e(TAG, "insert failed");
      }
    }
    catch (IllegalStateException localIllegalStateException)
    {
      for (;;)
      {
        Log.e(TAG, "insert failed");
      }
    }
  }
  
  private void releaseMediaRecorder()
  {
    if (this.mMediaRecorder != null)
    {
      cleanupEmptyFile();
      this.mMediaRecorder.reset();
      this.mMediaRecorder.release();
      this.mMediaRecorder = null;
    }
    if (this.mCameraDevice != null) {}
    try
    {
      this.mCameraDevice.reconnect();
      return;
    }
    catch (IOException localIOException)
    {
      Log.e(TAG, "mCameraDevice.reconnect() failed");
    }
  }
  
  private void renameTempFile()
  {
    new File(getTempFileName()).renameTo(new File(this.mCameraVideoFilename));
  }
  
  private void sendBroadCastAck(String paramString)
  {
    Log.e(TAG, "sendBroadCastAck - " + paramString);
    sendBroadcast(new Intent(paramString).putExtra("TYPE", getIntent().getIntExtra("TYPE", -1)));
    this.bSentAck = true;
  }
  
  private void stopPreview()
  {
    if ((this.mCameraDevice != null) && (this.mPreviewing))
    {
      this.mCameraDevice.stopPreview();
      this.mCameraDevice.setPreviewCallback(null);
    }
    this.mPreviewing = false;
    Log.i(TAG, "stopPreview : mPreviewing set false");
  }
  
  public void doPrepareVideoRecordingAsync()
  {
    Log.v(TAG, "doPrepareVideoRecordingAsync");
    if (this.mMediaRecorder != null)
    {
      Log.w(TAG, "mMediaRecorder is already initialized.");
      Log.w(TAG, "Releasing mMediaRecorder...");
      releaseMediaRecorder();
    }
    if (this.mCameraDevice != null)
    {
      this.mPrepareRecordingThread = new Thread(new Runnable()
      {
        public void run()
        {
          CamcorderPreviewTest.this.initializeMediaRecorder();
        }
      });
      this.mPrepareRecordingThread.setName("PrepareRecordingThread");
      this.mPrepareRecordingThread.start();
    }
  }
  
  public void doStartVideoRecordingAsync()
  {
    Log.v(TAG, "doStartVideoRecordingAsync");
    this.mVideoRecordingTimeInMiliSecond = 0L;
    try
    {
      if (this.mPrepareRecordingThread != null) {
        this.mPrepareRecordingThread.join();
      }
      label29:
      this.mShutterButton.setVisibility(4);
      this.mStopButton.setVisibility(0);
      Log.v(TAG, "start video recording");
      if (!this.mMediaRecorderRecording)
      {
        this.mStartRecordingThread = new Thread(new Runnable()
        {
          public void run()
          {
            if (CamcorderPreviewTest.this.mMediaRecorder == null)
            {
              Log.e(CamcorderPreviewTest.TAG, "MediaRecorder is not initialized.");
              return;
            }
            CamcorderPreviewTest.access$702(CamcorderPreviewTest.this, true);
            try
            {
              CamcorderPreviewTest.this.mMediaRecorder.start();
              CamcorderPreviewTest.access$1102(CamcorderPreviewTest.this, true);
              return;
            }
            catch (RuntimeException localRuntimeException)
            {
              Log.e(CamcorderPreviewTest.TAG, "Could not start media recorder. ", localRuntimeException);
              CamcorderPreviewTest.this.releaseMediaRecorder();
              CamcorderPreviewTest.this.doStopVideoRecordingSync();
              try
              {
                CamcorderPreviewTest.this.mCameraDevice.startPreview();
                CamcorderPreviewTest.access$1002(CamcorderPreviewTest.this, true);
                return;
              }
              catch (Throwable localThrowable)
              {
                Log.e(CamcorderPreviewTest.TAG, "exception while startPreview", localRuntimeException);
                CamcorderPreviewTest.this.dialogErrorPopup(2131165193);
              }
            }
          }
        });
        this.mStartRecordingThread.setName("StartRecordingThread");
        this.mStartRecordingThread.start();
      }
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label29;
    }
  }
  
  public void doStopVideoRecordingSync()
  {
    Log.v(TAG, "doStopVideoRecordingSync");
    if (!isRecording()) {
      return;
    }
    this.mShutterButton.setVisibility(0);
    this.mStopButton.setVisibility(4);
    Log.v(TAG, "Stopping VideoRecording...");
    if ((this.mMediaRecorderRecording) && (this.mMediaRecorder != null)) {}
    try
    {
      this.mMediaRecorder.setOnErrorListener(null);
      this.mMediaRecorder.setOnInfoListener(null);
      this.mMediaRecorder.stop();
      renameTempFile();
      this.mCurrentVideoFilename = this.mCameraVideoFilename;
      Log.v(TAG, "Setting current video filename: " + this.mCurrentVideoFilename);
      this.mMediaRecorderRecording = false;
      this.mIsRecordingStarted = false;
      releaseMediaRecorder();
      registerVideo();
      this.mCameraVideoFilename = null;
      this.mCameraVideoFileDescriptor = null;
      Log.v(TAG, "Stopping VideoRecording is completed!");
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      Log.e(TAG, "stop failed: " + localRuntimeException.getMessage());
      this.mMediaRecorderRecording = false;
      releaseMediaRecorder();
      cleanupTempFile();
      this.mCameraVideoFilename = null;
      this.mCameraVideoFileDescriptor = null;
    }
  }
  
  protected int[] findBestFpsRange(Camera.Parameters paramParameters, int paramInt1, int paramInt2)
  {
    Log.i(TAG, "Requsted fps range : " + paramInt1 + ", " + paramInt2);
    int[] arrayOfInt = new int[2];
    new int[2];
    List localList = paramParameters.getSupportedPreviewFpsRange();
    if (localList == null)
    {
      Log.v(TAG, "supported preview fps range is null");
      return null;
    }
    for (int i = -1 + localList.size(); i >= 0; i--)
    {
      arrayOfInt[0] = ((int[])localList.get(i))[0];
      arrayOfInt[1] = ((int[])localList.get(i))[1];
      if (paramInt2 == arrayOfInt[1])
      {
        if (paramInt1 == arrayOfInt[0])
        {
          Log.i(TAG, "find best fps range : " + arrayOfInt[0] + ", " + arrayOfInt[1]);
          return arrayOfInt;
        }
        if (i == 0)
        {
          Log.i(TAG, "find best fps range : " + arrayOfInt[0] + ", " + arrayOfInt[1]);
          return arrayOfInt;
        }
        for (int j = i; j >= 0; j--)
        {
          arrayOfInt[0] = ((int[])localList.get(j))[0];
          arrayOfInt[1] = ((int[])localList.get(j))[1];
          if (paramInt1 == arrayOfInt[0])
          {
            Log.i(TAG, "find best fps range : " + arrayOfInt[0] + ", " + arrayOfInt[1]);
            return arrayOfInt;
          }
          if (paramInt1 > arrayOfInt[0])
          {
            Log.i(TAG, "find best fps range : " + arrayOfInt[0] + ", " + arrayOfInt[1]);
            return arrayOfInt;
          }
        }
        Log.i(TAG, "find best fps range : " + arrayOfInt[0] + ", " + arrayOfInt[1]);
        return arrayOfInt;
      }
      if (paramInt2 > arrayOfInt[1])
      {
        Log.i(TAG, "find best fps range : " + arrayOfInt[0] + ", " + arrayOfInt[1]);
        return arrayOfInt;
      }
    }
    Log.i(TAG, "find best fps range : " + arrayOfInt[0] + ", " + arrayOfInt[1]);
    return arrayOfInt;
  }
  
  public CameraSettings getCameraSettings()
  {
    return this.mCameraSettings;
  }
  
  public boolean isRecording()
  {
    return this.mIsRecordingStarted;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    FtUtil.setSystemKeyBlock(getComponentName(), 3);
    FtUtil.setSystemKeyBlock(getComponentName(), 26);
    this.cameraType = getIntent().getIntExtra("camera_id", -1);
    this.camcorderpreview = Feature.TEST_RESOLUTION;
    this.mCurResolution = 0;
    Log.i(TAG, "onCreate");
    Thread localThread = new Thread(new Runnable()
    {
      /* Error */
      public void run()
      {
        // Byte code:
        //   0: invokestatic 33	android/hardware/Camera:getNumberOfCameras	()I
        //   3: pop
        //   4: aload_0
        //   5: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   8: aload_0
        //   9: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   12: invokestatic 37	com/sec/android/app/camera/CamcorderPreviewTest:access$400	(Lcom/sec/android/app/camera/CamcorderPreviewTest;)I
        //   15: invokestatic 41	android/hardware/Camera:open	(I)Landroid/hardware/Camera;
        //   18: invokestatic 45	com/sec/android/app/camera/CamcorderPreviewTest:access$902	(Lcom/sec/android/app/camera/CamcorderPreviewTest;Landroid/hardware/Camera;)Landroid/hardware/Camera;
        //   21: pop
        //   22: aload_0
        //   23: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   26: invokestatic 49	com/sec/android/app/camera/CamcorderPreviewTest:access$900	(Lcom/sec/android/app/camera/CamcorderPreviewTest;)Landroid/hardware/Camera;
        //   29: iconst_0
        //   30: invokevirtual 53	android/hardware/Camera:setDisplayOrientation	(I)V
        //   33: aload_0
        //   34: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   37: aload_0
        //   38: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   41: invokestatic 49	com/sec/android/app/camera/CamcorderPreviewTest:access$900	(Lcom/sec/android/app/camera/CamcorderPreviewTest;)Landroid/hardware/Camera;
        //   44: invokevirtual 57	android/hardware/Camera:getParameters	()Landroid/hardware/Camera$Parameters;
        //   47: invokestatic 61	com/sec/android/app/camera/CamcorderPreviewTest:access$1302	(Lcom/sec/android/app/camera/CamcorderPreviewTest;Landroid/hardware/Camera$Parameters;)Landroid/hardware/Camera$Parameters;
        //   50: pop
        //   51: aload_0
        //   52: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   55: invokestatic 65	com/sec/android/app/camera/CamcorderPreviewTest:access$1300	(Lcom/sec/android/app/camera/CamcorderPreviewTest;)Landroid/hardware/Camera$Parameters;
        //   58: aload_0
        //   59: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   62: invokestatic 69	com/sec/android/app/camera/CamcorderPreviewTest:access$1400	(Lcom/sec/android/app/camera/CamcorderPreviewTest;)[[I
        //   65: iconst_0
        //   66: aaload
        //   67: iconst_0
        //   68: iaload
        //   69: aload_0
        //   70: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   73: invokestatic 69	com/sec/android/app/camera/CamcorderPreviewTest:access$1400	(Lcom/sec/android/app/camera/CamcorderPreviewTest;)[[I
        //   76: iconst_0
        //   77: aaload
        //   78: iconst_1
        //   79: iaload
        //   80: invokevirtual 75	android/hardware/Camera$Parameters:setPreviewSize	(II)V
        //   83: aload_0
        //   84: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   87: invokestatic 49	com/sec/android/app/camera/CamcorderPreviewTest:access$900	(Lcom/sec/android/app/camera/CamcorderPreviewTest;)Landroid/hardware/Camera;
        //   90: aload_0
        //   91: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   94: invokestatic 65	com/sec/android/app/camera/CamcorderPreviewTest:access$1300	(Lcom/sec/android/app/camera/CamcorderPreviewTest;)Landroid/hardware/Camera$Parameters;
        //   97: invokevirtual 79	android/hardware/Camera:setParameters	(Landroid/hardware/Camera$Parameters;)V
        //   100: aload_0
        //   101: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   104: invokestatic 49	com/sec/android/app/camera/CamcorderPreviewTest:access$900	(Lcom/sec/android/app/camera/CamcorderPreviewTest;)Landroid/hardware/Camera;
        //   107: aload_0
        //   108: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   111: invokestatic 83	com/sec/android/app/camera/CamcorderPreviewTest:access$1500	(Lcom/sec/android/app/camera/CamcorderPreviewTest;)Lcom/sec/android/app/camera/CamcorderPreviewTest$ErrorCallback;
        //   114: invokevirtual 87	android/hardware/Camera:setErrorCallback	(Landroid/hardware/Camera$ErrorCallback;)V
        //   117: return
        //   118: astore_2
        //   119: aload_0
        //   120: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   123: iconst_1
        //   124: invokestatic 91	com/sec/android/app/camera/CamcorderPreviewTest:access$1202	(Lcom/sec/android/app/camera/CamcorderPreviewTest;Z)Z
        //   127: pop
        //   128: aload_0
        //   129: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   132: ldc 92
        //   134: invokestatic 96	com/sec/android/app/camera/CamcorderPreviewTest:access$200	(Lcom/sec/android/app/camera/CamcorderPreviewTest;I)V
        //   137: aload_0
        //   138: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   141: invokevirtual 99	com/sec/android/app/camera/CamcorderPreviewTest:finish	()V
        //   144: return
        //   145: astore 7
        //   147: getstatic 103	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
        //   150: ldc 105
        //   152: invokestatic 111	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
        //   155: pop
        //   156: aload_0
        //   157: getfield 17	com/sec/android/app/camera/CamcorderPreviewTest$3:this$0	Lcom/sec/android/app/camera/CamcorderPreviewTest;
        //   160: invokestatic 115	com/sec/android/app/camera/CamcorderPreviewTest:access$300	(Lcom/sec/android/app/camera/CamcorderPreviewTest;)Landroid/os/Handler;
        //   163: bipush 9
        //   165: invokevirtual 121	android/os/Handler:sendEmptyMessage	(I)Z
        //   168: pop
        //   169: return
        //   170: astore 6
        //   172: goto -72 -> 100
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	175	0	this	3
        //   118	1	2	localException	Exception
        //   170	1	6	localIllegalArgumentException	IllegalArgumentException
        //   145	1	7	localRuntimeException	RuntimeException
        // Exception table:
        //   from	to	target	type
        //   4	22	118	java/lang/Exception
        //   83	100	145	java/lang/RuntimeException
        //   83	100	170	java/lang/IllegalArgumentException
      }
    });
    localThread.start();
    getWindow().addFlags(6815872);
    setContentView(2130903040);
    ((TextView)findViewById(2131296260)).setText(this.camcorderpreview[0][0] + "x" + this.camcorderpreview[0][1]);
    this.mSurfaceView = ((VideoPreview)findViewById(2131296257));
    SurfaceHolder localSurfaceHolder = this.mSurfaceView.getHolder();
    localSurfaceHolder.addCallback(this);
    localSurfaceHolder.setType(3);
    this.mShutterButton = ((ShutterButton)findViewById(2131296258));
    this.mShutterButton.setOnShutterButtonListener(this);
    this.mStopButton = ((ShutterButton)findViewById(2131296259));
    this.mStopButton.setOnShutterButtonListener(this);
    this.mStopButton.setVisibility(4);
    this.mShutterButton.setVisibility(4);
    try
    {
      localThread.join();
      label250:
      FtUtil.setRemoveSystemUI(getWindow(), true);
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label250;
    }
  }
  
  public void onError(MediaRecorder paramMediaRecorder, int paramInt1, int paramInt2)
  {
    Log.e(TAG, "MediaRecorder onError = " + paramInt1);
    dialogErrorPopup(2131165194);
  }
  
  public void onInfo(MediaRecorder paramMediaRecorder, int paramInt1, int paramInt2)
  {
    if (paramInt1 == 800)
    {
      Log.e(TAG, "onInfo - MEDIA_RECORDER_INFO_MAX_DURATION_REACHED");
      doStopVideoRecordingSync();
      sendBroadCastAck("com.android.samsungtest.CAMCORDER_GOOD");
      setResult(0, getIntent());
      finish();
    }
    while (paramInt1 != 801) {
      return;
    }
    Log.e(TAG, "onInfo - MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED");
  }
  
  /* Error */
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    // Byte code:
    //   0: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   3: ldc_w 833
    //   6: invokestatic 290	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   9: pop
    //   10: iload_1
    //   11: lookupswitch	default:+33->44, 4:+523->534, 24:+42->53, 25:+40->51
    //   45: iload_1
    //   46: aload_2
    //   47: invokespecial 835	android/app/Activity:onKeyDown	(ILandroid/view/KeyEvent;)Z
    //   50: ireturn
    //   51: iconst_1
    //   52: ireturn
    //   53: aload_0
    //   54: iconst_1
    //   55: aload_0
    //   56: getfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   59: iadd
    //   60: putfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   63: aload_0
    //   64: getfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   67: iconst_m1
    //   68: getstatic 120	com/sec/android/app/camera/Feature:TEST_RESOLUTION	[[I
    //   71: arraylength
    //   72: iadd
    //   73: if_icmple +94 -> 167
    //   76: aload_0
    //   77: iconst_m1
    //   78: getstatic 120	com/sec/android/app/camera/Feature:TEST_RESOLUTION	[[I
    //   81: arraylength
    //   82: iadd
    //   83: putfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   86: aload_0
    //   87: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   90: ifnull +11 -> 101
    //   93: aload_0
    //   94: invokespecial 836	com/sec/android/app/camera/CamcorderPreviewTest:stopPreview	()V
    //   97: aload_0
    //   98: invokespecial 838	com/sec/android/app/camera/CamcorderPreviewTest:closeCamera	()V
    //   101: new 571	android/content/Intent
    //   104: dup
    //   105: aload_0
    //   106: ldc_w 840
    //   109: invokespecial 843	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
    //   112: astore 17
    //   114: aload 17
    //   116: ldc_w 754
    //   119: iconst_0
    //   120: invokevirtual 626	android/content/Intent:putExtra	(Ljava/lang/String;I)Landroid/content/Intent;
    //   123: pop
    //   124: aload 17
    //   126: ldc_w 845
    //   129: iconst_1
    //   130: invokevirtual 848	android/content/Intent:putExtra	(Ljava/lang/String;Z)Landroid/content/Intent;
    //   133: pop
    //   134: aload 17
    //   136: ldc_w 850
    //   139: iconst_0
    //   140: invokevirtual 626	android/content/Intent:putExtra	(Ljava/lang/String;I)Landroid/content/Intent;
    //   143: pop
    //   144: aload 17
    //   146: ldc_w 852
    //   149: iconst_1
    //   150: invokevirtual 848	android/content/Intent:putExtra	(Ljava/lang/String;Z)Landroid/content/Intent;
    //   153: pop
    //   154: aload_0
    //   155: iconst_m1
    //   156: aload 17
    //   158: invokevirtual 823	com/sec/android/app/camera/CamcorderPreviewTest:setResult	(ILandroid/content/Intent;)V
    //   161: aload_0
    //   162: invokevirtual 826	com/sec/android/app/camera/CamcorderPreviewTest:finish	()V
    //   165: iconst_1
    //   166: ireturn
    //   167: aload_0
    //   168: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   171: ifnull +11 -> 182
    //   174: aload_0
    //   175: invokespecial 836	com/sec/android/app/camera/CamcorderPreviewTest:stopPreview	()V
    //   178: aload_0
    //   179: invokespecial 838	com/sec/android/app/camera/CamcorderPreviewTest:closeCamera	()V
    //   182: aload_0
    //   183: ldc_w 773
    //   186: invokevirtual 777	com/sec/android/app/camera/CamcorderPreviewTest:findViewById	(I)Landroid/view/View;
    //   189: checkcast 779	android/widget/TextView
    //   192: new 84	java/lang/StringBuilder
    //   195: dup
    //   196: invokespecial 87	java/lang/StringBuilder:<init>	()V
    //   199: aload_0
    //   200: getfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   203: aload_0
    //   204: getfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   207: aaload
    //   208: iconst_0
    //   209: iaload
    //   210: invokevirtual 340	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   213: ldc_w 569
    //   216: invokevirtual 103	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   219: aload_0
    //   220: getfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   223: aload_0
    //   224: getfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   227: aaload
    //   228: iconst_1
    //   229: iaload
    //   230: invokevirtual 340	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   233: invokevirtual 106	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   236: invokevirtual 783	android/widget/TextView:setText	(Ljava/lang/CharSequence;)V
    //   239: aload_0
    //   240: aload_0
    //   241: getfield 113	com/sec/android/app/camera/CamcorderPreviewTest:cameraType	I
    //   244: invokestatic 856	android/hardware/Camera:open	(I)Landroid/hardware/Camera;
    //   247: putfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   250: aload_0
    //   251: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   254: iconst_0
    //   255: invokevirtual 859	android/hardware/Camera:setDisplayOrientation	(I)V
    //   258: aload_0
    //   259: aload_0
    //   260: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   263: invokevirtual 863	android/hardware/Camera:getParameters	()Landroid/hardware/Camera$Parameters;
    //   266: putfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   269: aload_0
    //   270: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   273: aload_0
    //   274: getfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   277: aload_0
    //   278: getfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   281: aaload
    //   282: iconst_0
    //   283: iaload
    //   284: aload_0
    //   285: getfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   288: aload_0
    //   289: getfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   292: aaload
    //   293: iconst_1
    //   294: iaload
    //   295: invokevirtual 866	android/hardware/Camera$Parameters:setPreviewSize	(II)V
    //   298: aload_0
    //   299: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   302: aload_0
    //   303: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   306: invokevirtual 870	android/hardware/Camera:setParameters	(Landroid/hardware/Camera$Parameters;)V
    //   309: aload_0
    //   310: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   313: aload_0
    //   314: getfield 189	com/sec/android/app/camera/CamcorderPreviewTest:mErrorCallback	Lcom/sec/android/app/camera/CamcorderPreviewTest$ErrorCallback;
    //   317: invokevirtual 874	android/hardware/Camera:setErrorCallback	(Landroid/hardware/Camera$ErrorCallback;)V
    //   320: aload_0
    //   321: getfield 111	com/sec/android/app/camera/CamcorderPreviewTest:mSurfaceHolder	Landroid/view/SurfaceHolder;
    //   324: ifnull +131 -> 455
    //   327: aload_0
    //   328: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   331: aload_0
    //   332: getfield 111	com/sec/android/app/camera/CamcorderPreviewTest:mSurfaceHolder	Landroid/view/SurfaceHolder;
    //   335: invokevirtual 877	android/hardware/Camera:setPreviewDisplay	(Landroid/view/SurfaceHolder;)V
    //   338: aload_0
    //   339: aload_0
    //   340: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   343: invokevirtual 863	android/hardware/Camera:getParameters	()Landroid/hardware/Camera$Parameters;
    //   346: putfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   349: aload_0
    //   350: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   353: ldc_w 879
    //   356: ldc_w 881
    //   359: invokevirtual 884	android/hardware/Camera$Parameters:set	(Ljava/lang/String;Ljava/lang/String;)V
    //   362: aload_0
    //   363: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   366: aload_0
    //   367: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   370: invokevirtual 870	android/hardware/Camera:setParameters	(Landroid/hardware/Camera$Parameters;)V
    //   373: aload_0
    //   374: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   377: invokevirtual 534	android/hardware/Camera:startPreview	()V
    //   380: aload_0
    //   381: iconst_1
    //   382: putfield 177	com/sec/android/app/camera/CamcorderPreviewTest:mPreviewing	Z
    //   385: iconst_1
    //   386: ireturn
    //   387: astore 4
    //   389: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   392: ldc_w 886
    //   395: invokestatic 332	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   398: pop
    //   399: aload_0
    //   400: ldc_w 540
    //   403: invokespecial 242	com/sec/android/app/camera/CamcorderPreviewTest:dialogErrorPopup	(I)V
    //   406: iconst_1
    //   407: ireturn
    //   408: astore 14
    //   410: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   413: ldc_w 888
    //   416: invokestatic 290	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   419: pop
    //   420: aload_0
    //   421: getfield 144	com/sec/android/app/camera/CamcorderPreviewTest:mMainHandler	Landroid/os/Handler;
    //   424: bipush 9
    //   426: invokevirtual 892	android/os/Handler:sendEmptyMessage	(I)Z
    //   429: pop
    //   430: iconst_1
    //   431: ireturn
    //   432: astore 13
    //   434: aload_0
    //   435: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   438: ifnull +15 -> 453
    //   441: aload_0
    //   442: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   445: invokevirtual 295	android/hardware/Camera:release	()V
    //   448: aload_0
    //   449: aconst_null
    //   450: putfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   453: iconst_1
    //   454: ireturn
    //   455: aload_0
    //   456: getfield 788	com/sec/android/app/camera/CamcorderPreviewTest:mSurfaceView	Lcom/sec/android/app/camera/VideoPreview;
    //   459: aload_0
    //   460: getfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   463: aload_0
    //   464: getfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   467: aaload
    //   468: iconst_0
    //   469: iaload
    //   470: aload_0
    //   471: getfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   474: aload_0
    //   475: getfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   478: aaload
    //   479: iconst_1
    //   480: iaload
    //   481: invokevirtual 895	com/sec/android/app/camera/VideoPreview:setAspectRatio	(II)V
    //   484: goto -146 -> 338
    //   487: astore 10
    //   489: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   492: ldc_w 888
    //   495: invokestatic 290	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   498: pop
    //   499: aload_0
    //   500: getfield 144	com/sec/android/app/camera/CamcorderPreviewTest:mMainHandler	Landroid/os/Handler;
    //   503: bipush 9
    //   505: invokevirtual 892	android/os/Handler:sendEmptyMessage	(I)Z
    //   508: pop
    //   509: iconst_1
    //   510: ireturn
    //   511: astore 8
    //   513: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   516: ldc_w 536
    //   519: aload 8
    //   521: invokestatic 539	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   524: pop
    //   525: aload_0
    //   526: ldc_w 540
    //   529: invokespecial 242	com/sec/android/app/camera/CamcorderPreviewTest:dialogErrorPopup	(I)V
    //   532: iconst_1
    //   533: ireturn
    //   534: aload_0
    //   535: getfield 148	com/sec/android/app/camera/CamcorderPreviewTest:mIsPressedBackkey	Z
    //   538: ifne +24 -> 562
    //   541: aload_0
    //   542: invokestatic 901	java/util/Calendar:getInstance	()Ljava/util/Calendar;
    //   545: invokevirtual 904	java/util/Calendar:getTimeInMillis	()J
    //   548: putfield 150	com/sec/android/app/camera/CamcorderPreviewTest:mCurrentTime	J
    //   551: aload_0
    //   552: iconst_1
    //   553: putfield 148	com/sec/android/app/camera/CamcorderPreviewTest:mIsPressedBackkey	Z
    //   556: aload_0
    //   557: invokevirtual 907	com/sec/android/app/camera/CamcorderPreviewTest:startTimer	()V
    //   560: iconst_1
    //   561: ireturn
    //   562: aload_0
    //   563: iconst_0
    //   564: putfield 148	com/sec/android/app/camera/CamcorderPreviewTest:mIsPressedBackkey	Z
    //   567: invokestatic 901	java/util/Calendar:getInstance	()Ljava/util/Calendar;
    //   570: invokevirtual 904	java/util/Calendar:getTimeInMillis	()J
    //   573: ldc2_w 908
    //   576: aload_0
    //   577: getfield 150	com/sec/android/app/camera/CamcorderPreviewTest:mCurrentTime	J
    //   580: ladd
    //   581: lcmp
    //   582: ifgt -538 -> 44
    //   585: aload_0
    //   586: iconst_0
    //   587: aload_0
    //   588: invokevirtual 618	com/sec/android/app/camera/CamcorderPreviewTest:getIntent	()Landroid/content/Intent;
    //   591: invokevirtual 823	com/sec/android/app/camera/CamcorderPreviewTest:setResult	(ILandroid/content/Intent;)V
    //   594: goto -550 -> 44
    //   597: astore 7
    //   599: goto -226 -> 373
    //   602: astore 6
    //   604: goto -295 -> 309
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	607	0	this	CamcorderPreviewTest
    //   0	607	1	paramInt	int
    //   0	607	2	paramKeyEvent	KeyEvent
    //   387	1	4	localException	Exception
    //   602	1	6	localIllegalArgumentException1	IllegalArgumentException
    //   597	1	7	localIllegalArgumentException2	IllegalArgumentException
    //   511	9	8	localThrowable	Throwable
    //   487	1	10	localRuntimeException1	RuntimeException
    //   432	1	13	localIOException	IOException
    //   408	1	14	localRuntimeException2	RuntimeException
    //   112	45	17	localIntent	Intent
    // Exception table:
    //   from	to	target	type
    //   239	250	387	java/lang/Exception
    //   298	309	408	java/lang/RuntimeException
    //   327	338	432	java/io/IOException
    //   362	373	487	java/lang/RuntimeException
    //   373	380	511	java/lang/Throwable
    //   362	373	597	java/lang/IllegalArgumentException
    //   298	309	602	java/lang/IllegalArgumentException
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    switch (paramInt)
    {
    default: 
      bool = super.onKeyUp(paramInt, paramKeyEvent);
    }
    return bool;
  }
  
  protected void onPause()
  {
    Log.i(TAG, "onPause");
    if (this.mWakeLock != null)
    {
      if (this.mWakeLock.isHeld())
      {
        Log.i(TAG, "releaseWakelock");
        this.mWakeLock.release();
      }
      this.mWakeLock = null;
    }
    this.mPausing = true;
    this.mMainHandler.removeCallbacks(this.mStartCheck);
    if (this.mCameraDevice != null)
    {
      stopPreview();
      this.mCameraDevice.release();
      this.mCameraDevice = null;
    }
    try
    {
      unregisterReceiver(this.mReceiver);
      label96:
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
      break label96;
    }
  }
  
  /* Error */
  public void onResume()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 935	android/app/Activity:onResume	()V
    //   4: aload_0
    //   5: iconst_0
    //   6: putfield 173	com/sec/android/app/camera/CamcorderPreviewTest:bSentAck	Z
    //   9: aload_0
    //   10: iconst_0
    //   11: putfield 175	com/sec/android/app/camera/CamcorderPreviewTest:mStopCamera	Z
    //   14: aload_0
    //   15: aload_0
    //   16: ldc_w 937
    //   19: invokevirtual 941	com/sec/android/app/camera/CamcorderPreviewTest:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   22: checkcast 943	android/os/PowerManager
    //   25: ldc_w 944
    //   28: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   31: invokevirtual 948	android/os/PowerManager:newWakeLock	(ILjava/lang/String;)Landroid/os/PowerManager$WakeLock;
    //   34: putfield 916	com/sec/android/app/camera/CamcorderPreviewTest:mWakeLock	Landroid/os/PowerManager$WakeLock;
    //   37: aload_0
    //   38: getfield 916	com/sec/android/app/camera/CamcorderPreviewTest:mWakeLock	Landroid/os/PowerManager$WakeLock;
    //   41: invokevirtual 951	android/os/PowerManager$WakeLock:acquire	()V
    //   44: aload_0
    //   45: iconst_0
    //   46: putfield 171	com/sec/android/app/camera/CamcorderPreviewTest:mPausing	Z
    //   49: new 953	android/content/IntentFilter
    //   52: dup
    //   53: invokespecial 954	android/content/IntentFilter:<init>	()V
    //   56: astore_1
    //   57: aload_1
    //   58: ldc_w 956
    //   61: invokevirtual 959	android/content/IntentFilter:addAction	(Ljava/lang/String;)V
    //   64: aload_0
    //   65: aload_0
    //   66: getfield 206	com/sec/android/app/camera/CamcorderPreviewTest:mReceiver	Landroid/content/BroadcastReceiver;
    //   69: aload_1
    //   70: invokevirtual 963	com/sec/android/app/camera/CamcorderPreviewTest:registerReceiver	(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;
    //   73: pop
    //   74: aload_0
    //   75: getfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   78: ifnonnull +39 -> 117
    //   81: iconst_2
    //   82: newarray 慢⁤污潬慣楴湯
    //   85: iconst_0
    //   86: getstatic 120	com/sec/android/app/camera/Feature:TEST_RESOLUTION	[[I
    //   89: arraylength
    //   90: iastore
    //   91: dup
    //   92: iconst_1
    //   93: iconst_2
    //   94: iastore
    //   95: astore_3
    //   96: aload_0
    //   97: getstatic 126	java/lang/Integer:TYPE	Ljava/lang/Class;
    //   100: aload_3
    //   101: invokestatic 132	java/lang/reflect/Array:newInstance	(Ljava/lang/Class;[I)Ljava/lang/Object;
    //   104: checkcast 133	[[I
    //   107: putfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   110: aload_0
    //   111: getstatic 120	com/sec/android/app/camera/Feature:TEST_RESOLUTION	[[I
    //   114: putfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   117: aload_0
    //   118: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   121: ifnonnull +84 -> 205
    //   124: aload_0
    //   125: aload_0
    //   126: getfield 113	com/sec/android/app/camera/CamcorderPreviewTest:cameraType	I
    //   129: invokestatic 856	android/hardware/Camera:open	(I)Landroid/hardware/Camera;
    //   132: putfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   135: aload_0
    //   136: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   139: iconst_0
    //   140: invokevirtual 859	android/hardware/Camera:setDisplayOrientation	(I)V
    //   143: aload_0
    //   144: aload_0
    //   145: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   148: invokevirtual 863	android/hardware/Camera:getParameters	()Landroid/hardware/Camera$Parameters;
    //   151: putfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   154: aload_0
    //   155: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   158: aload_0
    //   159: getfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   162: aload_0
    //   163: getfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   166: aaload
    //   167: iconst_0
    //   168: iaload
    //   169: aload_0
    //   170: getfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   173: aload_0
    //   174: getfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   177: aaload
    //   178: iconst_1
    //   179: iaload
    //   180: invokevirtual 866	android/hardware/Camera$Parameters:setPreviewSize	(II)V
    //   183: aload_0
    //   184: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   187: aload_0
    //   188: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   191: invokevirtual 870	android/hardware/Camera:setParameters	(Landroid/hardware/Camera$Parameters;)V
    //   194: aload_0
    //   195: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   198: aload_0
    //   199: getfield 189	com/sec/android/app/camera/CamcorderPreviewTest:mErrorCallback	Lcom/sec/android/app/camera/CamcorderPreviewTest$ErrorCallback;
    //   202: invokevirtual 874	android/hardware/Camera:setErrorCallback	(Landroid/hardware/Camera$ErrorCallback;)V
    //   205: aload_0
    //   206: getfield 111	com/sec/android/app/camera/CamcorderPreviewTest:mSurfaceHolder	Landroid/view/SurfaceHolder;
    //   209: ifnull +226 -> 435
    //   212: aload_0
    //   213: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   216: aload_0
    //   217: getfield 111	com/sec/android/app/camera/CamcorderPreviewTest:mSurfaceHolder	Landroid/view/SurfaceHolder;
    //   220: invokevirtual 877	android/hardware/Camera:setPreviewDisplay	(Landroid/view/SurfaceHolder;)V
    //   223: aload_0
    //   224: aload_0
    //   225: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   228: invokevirtual 863	android/hardware/Camera:getParameters	()Landroid/hardware/Camera$Parameters;
    //   231: putfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   234: aload_0
    //   235: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   238: ldc_w 879
    //   241: ldc_w 881
    //   244: invokevirtual 884	android/hardware/Camera$Parameters:set	(Ljava/lang/String;Ljava/lang/String;)V
    //   247: aload_0
    //   248: aload_0
    //   249: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   252: sipush 7000
    //   255: sipush 30000
    //   258: invokevirtual 965	com/sec/android/app/camera/CamcorderPreviewTest:findBestFpsRange	(Landroid/hardware/Camera$Parameters;II)[I
    //   261: astore 4
    //   263: aload 4
    //   265: ifnull +18 -> 283
    //   268: aload_0
    //   269: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   272: aload 4
    //   274: iconst_0
    //   275: iaload
    //   276: aload 4
    //   278: iconst_1
    //   279: iaload
    //   280: invokevirtual 968	android/hardware/Camera$Parameters:setPreviewFpsRange	(II)V
    //   283: aload_0
    //   284: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   287: aload_0
    //   288: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   291: invokevirtual 870	android/hardware/Camera:setParameters	(Landroid/hardware/Camera$Parameters;)V
    //   294: aload_0
    //   295: invokevirtual 970	com/sec/android/app/camera/CamcorderPreviewTest:getCameraSettings	()Lcom/sec/android/app/camera/framework/CameraSettings;
    //   298: pop
    //   299: invokestatic 973	com/sec/android/app/camera/framework/CameraSettings:hasFlash	()Z
    //   302: ifeq +33 -> 335
    //   305: aload_0
    //   306: getfield 113	com/sec/android/app/camera/CamcorderPreviewTest:cameraType	I
    //   309: ifne +181 -> 490
    //   312: aload_0
    //   313: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   316: ldc_w 975
    //   319: ldc_w 977
    //   322: invokevirtual 884	android/hardware/Camera$Parameters:set	(Ljava/lang/String;Ljava/lang/String;)V
    //   325: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   328: ldc_w 979
    //   331: invokestatic 332	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   334: pop
    //   335: aload_0
    //   336: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   339: aload_0
    //   340: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   343: invokevirtual 870	android/hardware/Camera:setParameters	(Landroid/hardware/Camera$Parameters;)V
    //   346: aload_0
    //   347: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   350: invokevirtual 534	android/hardware/Camera:startPreview	()V
    //   353: aload_0
    //   354: iconst_1
    //   355: putfield 177	com/sec/android/app/camera/CamcorderPreviewTest:mPreviewing	Z
    //   358: aload_0
    //   359: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   362: aload_0
    //   363: getfield 184	com/sec/android/app/camera/CamcorderPreviewTest:mPreviewCallback	Lcom/sec/android/app/camera/CamcorderPreviewTest$PreviewCallback;
    //   366: invokevirtual 633	android/hardware/Camera:setPreviewCallback	(Landroid/hardware/Camera$PreviewCallback;)V
    //   369: return
    //   370: astore 15
    //   372: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   375: ldc_w 886
    //   378: invokestatic 332	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   381: pop
    //   382: aload_0
    //   383: ldc_w 540
    //   386: invokespecial 242	com/sec/android/app/camera/CamcorderPreviewTest:dialogErrorPopup	(I)V
    //   389: return
    //   390: astore 18
    //   392: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   395: ldc_w 888
    //   398: invokestatic 290	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   401: pop
    //   402: aload_0
    //   403: getfield 144	com/sec/android/app/camera/CamcorderPreviewTest:mMainHandler	Landroid/os/Handler;
    //   406: bipush 9
    //   408: invokevirtual 892	android/os/Handler:sendEmptyMessage	(I)Z
    //   411: pop
    //   412: return
    //   413: astore 14
    //   415: aload_0
    //   416: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   419: ifnull -50 -> 369
    //   422: aload_0
    //   423: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   426: invokevirtual 295	android/hardware/Camera:release	()V
    //   429: aload_0
    //   430: aconst_null
    //   431: putfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   434: return
    //   435: aload_0
    //   436: getfield 788	com/sec/android/app/camera/CamcorderPreviewTest:mSurfaceView	Lcom/sec/android/app/camera/VideoPreview;
    //   439: aload_0
    //   440: getfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   443: aload_0
    //   444: getfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   447: aaload
    //   448: iconst_0
    //   449: iaload
    //   450: aload_0
    //   451: getfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   454: aload_0
    //   455: getfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   458: aaload
    //   459: iconst_1
    //   460: iaload
    //   461: invokevirtual 895	com/sec/android/app/camera/VideoPreview:setAspectRatio	(II)V
    //   464: goto -241 -> 223
    //   467: astore 11
    //   469: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   472: ldc_w 888
    //   475: invokestatic 290	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   478: pop
    //   479: aload_0
    //   480: getfield 144	com/sec/android/app/camera/CamcorderPreviewTest:mMainHandler	Landroid/os/Handler;
    //   483: bipush 9
    //   485: invokevirtual 892	android/os/Handler:sendEmptyMessage	(I)Z
    //   488: pop
    //   489: return
    //   490: aload_0
    //   491: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   494: ldc_w 975
    //   497: ldc_w 981
    //   500: invokevirtual 884	android/hardware/Camera$Parameters:set	(Ljava/lang/String;Ljava/lang/String;)V
    //   503: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   506: ldc_w 983
    //   509: invokestatic 290	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   512: pop
    //   513: goto -178 -> 335
    //   516: astore 7
    //   518: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   521: ldc_w 536
    //   524: aload 7
    //   526: invokestatic 539	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   529: pop
    //   530: aload_0
    //   531: ldc_w 540
    //   534: invokespecial 242	com/sec/android/app/camera/CamcorderPreviewTest:dialogErrorPopup	(I)V
    //   537: return
    //   538: astore 5
    //   540: goto -246 -> 294
    //   543: astore 17
    //   545: goto -351 -> 194
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	548	0	this	CamcorderPreviewTest
    //   56	14	1	localIntentFilter	android.content.IntentFilter
    //   95	6	3	arrayOfInt1	int[]
    //   261	16	4	arrayOfInt2	int[]
    //   538	1	5	localIllegalArgumentException1	IllegalArgumentException
    //   516	9	7	localThrowable	Throwable
    //   467	1	11	localRuntimeException1	RuntimeException
    //   413	1	14	localIOException	IOException
    //   370	1	15	localException	Exception
    //   543	1	17	localIllegalArgumentException2	IllegalArgumentException
    //   390	1	18	localRuntimeException2	RuntimeException
    // Exception table:
    //   from	to	target	type
    //   124	135	370	java/lang/Exception
    //   183	194	390	java/lang/RuntimeException
    //   212	223	413	java/io/IOException
    //   283	294	467	java/lang/RuntimeException
    //   346	353	516	java/lang/Throwable
    //   283	294	538	java/lang/IllegalArgumentException
    //   183	194	543	java/lang/IllegalArgumentException
  }
  
  public void onShutterButtonClick(ShutterButton paramShutterButton)
  {
    Log.i(TAG, "call onShutterButtonClick");
    if (this.mPausing) {
      return;
    }
    switch (paramShutterButton.getId())
    {
    default: 
      return;
    case 2131296258: 
      if (!isRecording())
      {
        doPrepareVideoRecordingAsync();
        doStartVideoRecordingAsync();
        return;
      }
      Log.e(TAG, "shutter button is visible wrongly in recording state!");
      return;
    }
    if (isRecording())
    {
      doStopVideoRecordingSync();
      return;
    }
    Log.e(TAG, "stop button is visible wrongly in recording state!");
  }
  
  public void onShutterButtonFocus(ShutterButton paramShutterButton, boolean paramBoolean)
  {
    Log.i(TAG, "call onShutterButtonFocus");
    if (this.mPausing) {
      return;
    }
    paramShutterButton.getId();
  }
  
  protected void startTimer()
  {
    this.mTimerHandler.sendEmptyMessageDelayed(1, 2000L);
  }
  
  /* Error */
  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
  {
    // Byte code:
    //   0: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   3: ldc_w 1009
    //   6: invokestatic 290	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   9: pop
    //   10: iload_3
    //   11: iload 4
    //   13: if_icmpge +3 -> 16
    //   16: aload_0
    //   17: getfield 788	com/sec/android/app/camera/CamcorderPreviewTest:mSurfaceView	Lcom/sec/android/app/camera/VideoPreview;
    //   20: iconst_0
    //   21: invokevirtual 1010	com/sec/android/app/camera/VideoPreview:setVisibility	(I)V
    //   24: aload_0
    //   25: getfield 171	com/sec/android/app/camera/CamcorderPreviewTest:mPausing	Z
    //   28: ifeq +14 -> 42
    //   31: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   34: ldc_w 1012
    //   37: invokestatic 290	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   40: pop
    //   41: return
    //   42: aload_0
    //   43: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   46: ifnull +43 -> 89
    //   49: aload_0
    //   50: aload_0
    //   51: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   54: invokevirtual 863	android/hardware/Camera:getParameters	()Landroid/hardware/Camera$Parameters;
    //   57: putfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   60: aload_0
    //   61: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   64: aload_0
    //   65: getfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   68: aload_0
    //   69: getfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   72: aaload
    //   73: iconst_0
    //   74: iaload
    //   75: aload_0
    //   76: getfield 135	com/sec/android/app/camera/CamcorderPreviewTest:camcorderpreview	[[I
    //   79: aload_0
    //   80: getfield 137	com/sec/android/app/camera/CamcorderPreviewTest:mCurResolution	I
    //   83: aaload
    //   84: iconst_1
    //   85: iaload
    //   86: invokevirtual 866	android/hardware/Camera$Parameters:setPreviewSize	(II)V
    //   89: aload_0
    //   90: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   93: aload_0
    //   94: getfield 219	com/sec/android/app/camera/CamcorderPreviewTest:mParameters	Landroid/hardware/Camera$Parameters;
    //   97: invokevirtual 870	android/hardware/Camera:setParameters	(Landroid/hardware/Camera$Parameters;)V
    //   100: aload_0
    //   101: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   104: aload_0
    //   105: getfield 111	com/sec/android/app/camera/CamcorderPreviewTest:mSurfaceHolder	Landroid/view/SurfaceHolder;
    //   108: invokevirtual 877	android/hardware/Camera:setPreviewDisplay	(Landroid/view/SurfaceHolder;)V
    //   111: aload_0
    //   112: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   115: invokevirtual 534	android/hardware/Camera:startPreview	()V
    //   118: aload_0
    //   119: iconst_1
    //   120: putfield 177	com/sec/android/app/camera/CamcorderPreviewTest:mPreviewing	Z
    //   123: return
    //   124: astore 11
    //   126: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   129: ldc_w 888
    //   132: invokestatic 290	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   135: pop
    //   136: aload_0
    //   137: getfield 144	com/sec/android/app/camera/CamcorderPreviewTest:mMainHandler	Landroid/os/Handler;
    //   140: bipush 9
    //   142: invokevirtual 892	android/os/Handler:sendEmptyMessage	(I)Z
    //   145: pop
    //   146: return
    //   147: astore 7
    //   149: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   152: ldc_w 1014
    //   155: invokestatic 290	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   158: pop
    //   159: aload_0
    //   160: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   163: ifnull -122 -> 41
    //   166: aload_0
    //   167: getfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   170: invokevirtual 295	android/hardware/Camera:release	()V
    //   173: aload_0
    //   174: aconst_null
    //   175: putfield 263	com/sec/android/app/camera/CamcorderPreviewTest:mCameraDevice	Landroid/hardware/Camera;
    //   178: return
    //   179: astore 9
    //   181: getstatic 82	com/sec/android/app/camera/CamcorderPreviewTest:TAG	Ljava/lang/String;
    //   184: ldc_w 536
    //   187: aload 9
    //   189: invokestatic 539	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   192: pop
    //   193: aload_0
    //   194: ldc_w 540
    //   197: invokespecial 242	com/sec/android/app/camera/CamcorderPreviewTest:dialogErrorPopup	(I)V
    //   200: return
    //   201: astore 6
    //   203: goto -103 -> 100
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	206	0	this	CamcorderPreviewTest
    //   0	206	1	paramSurfaceHolder	SurfaceHolder
    //   0	206	2	paramInt1	int
    //   0	206	3	paramInt2	int
    //   0	206	4	paramInt3	int
    //   201	1	6	localIllegalArgumentException	IllegalArgumentException
    //   147	1	7	localIOException	IOException
    //   179	9	9	localThrowable	Throwable
    //   124	1	11	localRuntimeException	RuntimeException
    // Exception table:
    //   from	to	target	type
    //   89	100	124	java/lang/RuntimeException
    //   100	111	147	java/io/IOException
    //   111	118	179	java/lang/Throwable
    //   89	100	201	java/lang/IllegalArgumentException
  }
  
  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    Log.i(TAG, "surfaceCreated");
    this.mSurfaceHolder = paramSurfaceHolder;
  }
  
  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    Log.i(TAG, "surfaceDestroyed");
    stopPreview();
    this.mSurfaceHolder = null;
  }
  
  public final class ErrorCallback
    implements Camera.ErrorCallback
  {
    public ErrorCallback() {}
    
    public void onError(int paramInt, Camera paramCamera)
    {
      Log.w(CamcorderPreviewTest.TAG, "onError - " + paramInt);
      switch (paramInt)
      {
      default: 
        Log.e(CamcorderPreviewTest.TAG, "ErrorCallback - CAMERA BAD[" + paramInt + "]");
        CamcorderPreviewTest.this.dialogErrorPopup(2131165193);
      case 0: 
        return;
      case 1000: 
        Log.e(CamcorderPreviewTest.TAG, "ErrorCallback - CAMERA_ERROR_WRONG_FW");
        CamcorderPreviewTest.this.dialogErrorPopup(2131165195);
        return;
      case 2001: 
        Log.e(CamcorderPreviewTest.TAG, "ErrorCallback - CAMERA_ERROR_DATALINE_SUCCESS");
        CamcorderPreviewTest.this.mMainHandler.sendEmptyMessage(6);
        return;
      }
      Log.e(CamcorderPreviewTest.TAG, "ErrorCallback - CAMERA_ERROR_DATALINE_FAIL");
      if (CamcorderPreviewTest.this.cameraType == 1)
      {
        CamcorderPreviewTest.this.dialogErrorPopup(2131165192);
        return;
      }
      CamcorderPreviewTest.this.dialogErrorPopup(2131165191);
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
      default: 
        return;
      }
      CamcorderPreviewTest.this.dialogErrorPopup(2131165193);
    }
  }
  
  public final class PreviewCallback
    implements Camera.PreviewCallback
  {
    public PreviewCallback() {}
    
    public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera)
    {
      Log.e(CamcorderPreviewTest.TAG, "onPreviewFrame - get the preview image");
      if (!CamcorderPreviewTest.this.bSentAck) {
        CamcorderPreviewTest.this.sendBroadCastAck("com.android.samsungtest.CAMERA_GOOD");
      }
      paramCamera.setPreviewCallback(null);
    }
  }
  
  private final class ShutterCallback
    implements Camera.ShutterCallback
  {
    private ShutterCallback() {}
    
    public void onShutter()
    {
      Log.i(CamcorderPreviewTest.TAG, "ShutterCallback");
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.camera.CamcorderPreviewTest
 * JD-Core Version:    0.7.1
 */