package com.sec.factory.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.modules.ModulePower;
import com.sec.factory.support.FtUtil;
import java.io.File;

public class UIDisptestActivity
  extends Activity
{
  private ImageView mImageView;
  
  private void initSetting()
  {
    this.mImageView = ((ImageView)findViewById(2131296385));
    this.mImageView.setBackgroundColor(-65536);
    FtUtil.setRemoveSystemUI(getWindow(), true);
  }
  
  private void performRequest(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    default: 
      return;
    case 0: 
      this.mImageView.setBackgroundColor(-65536);
      return;
    case 1: 
      this.mImageView.setBackgroundColor(-16711936);
      return;
    case 2: 
      this.mImageView.setBackgroundColor(-16776961);
      return;
    case 3: 
      this.mImageView.setBackgroundColor(-1);
      return;
    case 4: 
      this.mImageView.setBackgroundColor(-16777216);
      return;
    }
    ModuleDevice localModuleDevice = ModuleDevice.instance(this);
    String str1;
    if (localModuleDevice.isMountedStorage(1))
    {
      str1 = localModuleDevice.getStoragePath(1);
      label110:
      String str2 = str1 + "/" + paramInt2 + ".bmp";
      Log.i("UIDisptestActivity", "filepath: " + str2);
      if (!new File(str2).exists()) {
        break label240;
      }
      Log.i("UIDisptestActivity", "Create image from src");
      readImageFromMemory(str2);
      if (paramInt2 != 0) {
        break label232;
      }
    }
    label232:
    for (int i = 30;; i = 255)
    {
      ModulePower.instance(this).setBrightness(i);
      return;
      if (!localModuleDevice.isMountedStorage(0)) {
        break;
      }
      str1 = localModuleDevice.getStoragePath(0);
      break label110;
    }
    label240:
    Log.i("UIDisptestActivity", "File does not exist!");
    Toast.makeText(this, "File not found!", 0).show();
  }
  
  private void readImageFromMemory(String paramString)
  {
    Display localDisplay = ((WindowManager)getSystemService("window")).getDefaultDisplay();
    Point localPoint = new Point();
    localDisplay.getRealSize(localPoint);
    Bitmap localBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(paramString), localPoint.x, localPoint.y, true);
    this.mImageView.setImageBitmap(localBitmap);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903055);
    initSetting();
    int i = getIntent().getIntExtra("VALUE", -1);
    int j = getIntent().getIntExtra("TEST_TYPE", -1);
    Log.i("UIDisptestActivity", "testType: " + j + "value: " + i);
    performRequest(j, i);
  }
  
  protected void onPause()
  {
    super.onPause();
  }
  
  protected void onResume()
  {
    super.onResume();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIDisptestActivity
 * JD-Core Version:    0.7.1
 */