package com.sec.factory.app.factorytest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.modules.ModuleCommunication;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;

public class FactoryTestMainAdapter
  extends BaseAdapter
{
  public static boolean ALL_PASS_STATE = false;
  public static int COLOR_PASS_BACKGROUND;
  public static int COLOR_PASS_TEXT;
  public static final float FONT_SIZE;
  public static int SIMPLE_FUNCTION_TEST = 0;
  private Context mContext;
  
  static
  {
    COLOR_PASS_BACKGROUND = -16776961;
    COLOR_PASS_TEXT = -1;
    FONT_SIZE = Support.Feature.getFactoryTextSize();
  }
  
  public FactoryTestMainAdapter(Context paramContext)
  {
    this.mContext = paramContext;
    FtUtil.log_i("FactoryTestMainAdapter", "FactoryTestMainAdapter", "Factorytest FONT_SIZE:" + FONT_SIZE);
  }
  
  public int getCount()
  {
    return FactoryTestManager.getItemCount();
  }
  
  public Object getItem(int paramInt)
  {
    return null;
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    View localView = paramView;
    if (localView == null) {}
    TextView localTextView;
    label350:
    for (;;)
    {
      try
      {
        localView = ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903058, paramViewGroup, false);
        LinearLayout localLinearLayout = (LinearLayout)localView.findViewById(2131296396);
        localTextView = (TextView)localView.findViewById(2131296397);
        localTextView.setWillNotCacheDrawing(true);
        if (FONT_SIZE != 0.0F) {
          localTextView.setTextSize(FONT_SIZE);
        }
        String str1 = String.valueOf(paramInt + 1) + ". " + FactoryTestManager.getItemName_Position(paramInt);
        localTextView.setText(str1);
        if (FactoryTestManager.getItemResult(paramInt) == 80)
        {
          FtUtil.log_d("FactoryTestMainAdapter", "getView", str1 + " => O");
          FtUtil.log_i("FactoryTestMainAdapter", "getView", "PASS ITEM : position=" + paramInt + ", ALL_PASS_STATE=" + ALL_PASS_STATE);
          localTextView.setTextColor(-1);
          localTextView.setBackgroundColor(-16776961);
          if (!ALL_PASS_STATE) {
            break label350;
          }
          localLinearLayout.setBackgroundColor(-16776961);
        }
        switch (FactoryTestManager.getItemID(paramInt))
        {
        case 1: 
          FtUtil.log_d("FactoryTestMainAdapter", "getView", str1 + " => X");
          FtUtil.log_i("FactoryTestMainAdapter", "getView", "NorF ITEM : position=" + paramInt + ", ALL_PASS_STATE=" + ALL_PASS_STATE);
          localTextView.setTextColor(-16777216);
          localTextView.setBackgroundColor(0);
          continue;
          localLinearLayout.setBackgroundColor(0);
        }
      }
      catch (Exception localException)
      {
        FtUtil.log_e(localException);
        return localView;
      }
    }
    ModuleAudio localModuleAudio3 = ModuleAudio.instance(this.mContext);
    int k = localModuleAudio3.getCurrentLoopbackPath();
    int m = localModuleAudio3.getMicCount();
    boolean bool = ModuleAudio.isSupportSecondMicTest();
    FtUtil.log_i("FactoryTestMainAdapter", "getView", "getCurrentLoopbackPath : " + k + ", nMic : " + m);
    String[] arrayOfString3;
    if (m == 3) {
      arrayOfString3 = new String[] { " RCV", " SPK_F", " SPK_R", " ", " ", " E/P" };
    }
    for (;;)
    {
      FtUtil.log_i("FactoryTestMainAdapter", "getView", "FactoryTestMain.IsLoopBack2 " + FactoryTestMain.IsLoopBack2);
      if ((FactoryTestMain.IsLoopBack2) || (k == -1)) {
        break;
      }
      localTextView.setText(localTextView.getText() + arrayOfString3[k]);
      return localView;
      if (bool) {
        arrayOfString3 = new String[] { " RCV1", " SPK", " ", " ", " ", " E/P", "RCV_2MIC" };
      } else {
        arrayOfString3 = new String[] { " RCV", " SPK", " ", " ", " ", " E/P" };
      }
    }
    ModuleAudio localModuleAudio2 = ModuleAudio.instance(this.mContext);
    int i = localModuleAudio2.getCurrentLoopbackPath();
    int j = localModuleAudio2.getMicCount();
    FtUtil.log_i("FactoryTestMainAdapter", "getView", "getCurrentLoopbackPathloopback2 : " + i + ", nMic : " + j);
    if (j == 3) {}
    for (String[] arrayOfString2 = { " RCV", " SPK_F", " SPK_R", " ", " ", " E/P" };; arrayOfString2 = new String[] { " RCV", " SPK", " ", " ", " ", " E/P" })
    {
      FtUtil.log_i("FactoryTestMainAdapter", "getView", "FactoryTestMain.IsLoopBack2 " + FactoryTestMain.IsLoopBack2);
      if ((true != FactoryTestMain.IsLoopBack2) || (i == -1)) {
        break;
      }
      localTextView.setText(localTextView.getText() + arrayOfString2[i]);
      return localView;
    }
    StringBuilder localStringBuilder = new StringBuilder().append(localTextView.getText());
    if (ModuleCommunication.instance(this.mContext).isEnabledBtDevice()) {}
    for (String str2 = " ON";; str2 = " OFF")
    {
      localTextView.setText(str2);
      return localView;
      ModuleAudio localModuleAudio1 = ModuleAudio.instance(this.mContext);
      String[] arrayOfString1 = { " ", " ", " ", " UP DOWN", " DOWN UP" };
      localTextView.setText(localTextView.getText() + arrayOfString1[localModuleAudio1.getEarKeyState()]);
      return localView;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestMainAdapter
 * JD-Core Version:    0.7.1
 */