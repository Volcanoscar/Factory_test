package com.sec.android.app.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View.MeasureSpec;

public class VideoPreview
  extends SurfaceView
{
  public static float DONT_CARE = 0.0F;
  private float mAspectRatio;
  private int mHeight = 0;
  private int mHorizontalTileSize = 1;
  private int mVerticalTileSize = 1;
  private int mWidth = 0;
  
  public VideoPreview(Context paramContext)
  {
    super(paramContext);
  }
  
  public VideoPreview(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public VideoPreview(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private int roundUpToTile(int paramInt1, int paramInt2, int paramInt3)
  {
    return Math.min(paramInt2 * ((-1 + (paramInt1 + paramInt2)) / paramInt2), paramInt3);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = i;
    int m = j;
    Log.v("VideoPreview", "Max onMeasure : WH[" + k + " " + m + "]");
    if ((k > 0) && (m > 0))
    {
      if ((this.mWidth != 0) && (this.mHeight != 0))
      {
        setMeasuredDimension(this.mWidth, this.mHeight);
        return;
      }
      float f = k / m;
      if (f < this.mAspectRatio) {
        m = (int)(k / this.mAspectRatio);
      }
      for (;;)
      {
        int n = roundUpToTile(k, this.mHorizontalTileSize, i);
        int i1 = roundUpToTile(m, this.mVerticalTileSize, j);
        Log.i("VideoPreview", "onMeasure : AR " + this.mAspectRatio + " setting size: " + n + 'x' + i1);
        setMeasuredDimension(n, i1);
        return;
        if (f > this.mAspectRatio) {
          k = (int)(m * this.mAspectRatio);
        }
      }
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void setAspectRatio(float paramFloat)
  {
    if (this.mAspectRatio != paramFloat)
    {
      this.mAspectRatio = paramFloat;
      requestLayout();
      invalidate();
    }
  }
  
  public void setAspectRatio(int paramInt1, int paramInt2)
  {
    setAspectRatio(paramInt1 / paramInt2);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.camera.VideoPreview
 * JD-Core Version:    0.7.1
 */