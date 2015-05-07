package com.sec.android.app.camera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import com.sec.factory.support.Support.TestCase;
import java.io.File;

public class PostViewTest
  extends Activity
{
  private Bitmap[] mCameraBitmap = new Bitmap[2];
  private String mFilePathMainCam;
  private String mFilePathSubCam;
  private boolean mFrontDisplayed = false;
  private ImageView mImageView;
  private boolean mRearDisplayed = false;
  protected PowerManager.WakeLock mWakeLock;
  
  private void displayImage(int paramInt)
  {
    Log.i("PostViewTest", "displayImage cameraType:%d" + paramInt);
    if (paramInt == 0)
    {
      if (this.mCameraBitmap[0] != null) {
        this.mImageView.setImageBitmap(this.mCameraBitmap[0]);
      }
      for (;;)
      {
        this.mRearDisplayed = true;
        return;
        this.mImageView.setBackgroundColor(-16711936);
      }
    }
    if (this.mCameraBitmap[1] != null) {
      this.mImageView.setImageBitmap(this.mCameraBitmap[1]);
    }
    for (;;)
    {
      this.mFrontDisplayed = true;
      return;
      this.mImageView.setBackgroundColor(-16776961);
    }
  }
  
  private void getImage(int paramInt)
  {
    Log.i("PostViewTest", "getImage cameraType:%d" + paramInt);
    CameraStorage localCameraStorage = CameraStorage.getInstance();
    Bitmap localBitmap2;
    Matrix localMatrix2;
    if (paramInt == 0)
    {
      this.mFilePathMainCam = localCameraStorage.getFilePath(0);
      if ((this.mFilePathMainCam != null) && (new File(this.mFilePathMainCam).exists()))
      {
        int i = Integer.parseInt(Support.TestCase.getString("SIMPLE_TEST_MEGACAM_SCALE_VALUE"));
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inSampleSize = i;
        localBitmap2 = BitmapFactory.decodeFile(this.mFilePathMainCam, localOptions);
        localMatrix2 = new Matrix();
        localMatrix2.postRotate(90.0F);
      }
    }
    do
    {
      this.mCameraBitmap[0] = Bitmap.createBitmap(localBitmap2, 0, 0, localBitmap2.getWidth(), localBitmap2.getHeight(), localMatrix2, true);
      do
      {
        return;
      } while (this.mFilePathMainCam != null);
      Log.i("PostViewTest", "mFilePathMainCam is null");
      return;
      this.mFilePathSubCam = localCameraStorage.getFilePath(1);
      if ((this.mFilePathSubCam != null) && (new File(this.mFilePathSubCam).exists()))
      {
        Bitmap localBitmap1 = BitmapFactory.decodeFile(this.mFilePathSubCam);
        Matrix localMatrix1 = new Matrix();
        localMatrix1.postRotate(270.0F);
        localMatrix1.postScale(-1.0F, 1.0F);
        this.mCameraBitmap[1] = Bitmap.createBitmap(localBitmap1, 0, 0, localBitmap1.getWidth(), localBitmap1.getHeight(), localMatrix1, true);
        return;
      }
    } while (this.mFilePathSubCam != null);
    Log.i("PostViewTest", "mFilePathSubCam is null");
  }
  
  private void initialize()
  {
    Log.i("PostViewTest", "initialize");
    this.mImageView = ((ImageView)findViewById(2131296374));
    getImage(0);
    displayImage(0);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.i("PostViewTest", "onCreate");
    setContentView(2130903052);
    this.mWakeLock = ((PowerManager)getSystemService("power")).newWakeLock(805306394, "PostViewTest");
    this.mWakeLock.acquire();
    initialize();
  }
  
  protected void onDestroy()
  {
    Log.i("PostViewTest", "onDestroy");
    if (this.mWakeLock.isHeld()) {
      this.mWakeLock.release();
    }
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    int i = 1;
    switch (paramInt)
    {
    default: 
      i = super.onKeyDown(paramInt, paramKeyEvent);
    }
    do
    {
      return i;
      if (this.mRearDisplayed)
      {
        getImage(i);
        displayImage(i);
        this.mRearDisplayed = false;
        return i;
      }
    } while (!this.mFrontDisplayed);
    setResult(-1, getIntent());
    this.mFrontDisplayed = false;
    finish();
    return i;
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
  
  public void onPause()
  {
    Log.i("PostViewTest", "onPause");
    if (this.mWakeLock.isHeld()) {
      this.mWakeLock.release();
    }
    super.onPause();
  }
  
  public void onResume()
  {
    Log.i("PostViewTest", "onResume");
    if (this.mWakeLock.isHeld()) {
      this.mWakeLock.release();
    }
    this.mWakeLock.acquire();
    super.onResume();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.camera.PostViewTest
 * JD-Core Version:    0.7.1
 */