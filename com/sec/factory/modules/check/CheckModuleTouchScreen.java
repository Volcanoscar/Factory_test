package com.sec.factory.modules.check;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import com.sec.factory.modules.ModuleTouchScreen;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Spec;

public class CheckModuleTouchScreen
  extends Activity
{
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      CheckModuleTouchScreen.this.startTest();
    }
  };
  private ModuleTouchScreen mModuleTouchScreen;
  private int mStep = 0;
  
  private String resultRead(int paramInt)
  {
    String str1;
    String str2;
    if (this.mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__VENDOR_NAME).equalsIgnoreCase("SYNAPTICS"))
    {
      str1 = this.mModuleTouchScreen.getTSPChannelCountX();
      if ((!str1.equals("NG")) && (!str1.equals("NA"))) {
        break label72;
      }
      str2 = "error => Channel";
    }
    for (;;)
    {
      return str2;
      str1 = this.mModuleTouchScreen.getTSPChannelCountY();
      break;
      label72:
      int i = Integer.valueOf(this.mModuleTouchScreen.getTSPChannelCountY()).intValue();
      str2 = "";
      for (int j = 1; j <= i; j++)
      {
        str2 = str2 + "  Line " + j + " : " + this.mModuleTouchScreen.getTSPResult_Read(paramInt, j);
        if (j < i) {
          str2 = str2 + "\n";
        }
      }
    }
  }
  
  private void startTest()
  {
    switch (this.mStep)
    {
    }
    for (;;)
    {
      if (this.mStep < 7) {
        this.mHandler.sendEmptyMessageDelayed(0, 500L);
      }
      this.mStep = (1 + this.mStep);
      return;
      ((TextView)findViewById(2131296278)).setText("1. XML Setting\n  (1) Vendor : " + Support.Feature.getString("TSP_MANUFACTURE") + "\n" + "  (2) Panel Type : " + Support.Feature.getString("PANEL_TYPE") + "\n" + "  (3) Device Type : " + Support.Feature.getString("DEVICE_TYPE"));
      continue;
      ((TextView)findViewById(2131296279)).setText("2. Common\n  (1) Vendor : " + this.mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__VENDOR_NAME) + "\n" + "  (2) Chip : " + this.mModuleTouchScreen.getTSPChipName() + "\n" + "  (3) FW Ver Bin : " + this.mModuleTouchScreen.getTSPFirmwareVersionBinary() + "\n" + "  (4) FW Ver IC : " + this.mModuleTouchScreen.getTSPFirmwareVersionIC() + "\n" + "  (7) Channel X : " + this.mModuleTouchScreen.getTSPChannelCountX() + "\n" + "  (8) Channel Y : " + this.mModuleTouchScreen.getTSPChannelCountY());
      continue;
      ((TextView)findViewById(2131296280)).setText("3. Connection\n  (1) Spec(MIN/MAX) : " + Support.Spec.getInt("TSP_SELFTEST_MIN") + "/" + Support.Spec.getInt("TSP_SELFTEST_MAX") + "\n" + "  (2) Spec(check) : " + this.mModuleTouchScreen.getTSPResult(ModuleTouchScreen.TSP_ID__EXPANSION__CONNECTION));
      continue;
      ((TextView)findViewById(2131296281)).setText("4. Read1\n" + resultRead(1));
      continue;
      ((TextView)findViewById(2131296282)).setText("5. Read2\n" + resultRead(2));
      continue;
      ((TextView)findViewById(2131296283)).setText("6. Read3\n" + resultRead(3));
      continue;
      ((TextView)findViewById(2131296284)).setText("7. Read4\n" + resultRead(4));
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903044);
    this.mModuleTouchScreen = ModuleTouchScreen.instance(this);
    this.mHandler.sendEmptyMessageDelayed(0, 1000L);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.check.CheckModuleTouchScreen
 * JD-Core Version:    0.7.1
 */