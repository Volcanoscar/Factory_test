package com.sec.factory.app.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.sec.factory.app.factorytest.FactoryTestPhone;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.NVAccessor;
import java.util.ArrayList;
import java.util.List;

public class UIFactoryTestNVView
  extends Activity
  implements View.OnClickListener
{
  private final String CLASS_NAME = "FactoryTestNVView";
  private final int ITEM_MAX_SIZE = 90;
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      FtUtil.log_d("FactoryTestNVView", "mBroadcastReceiver.onReceive", "action=" + str);
      if (str.equals("com.android.samsungtest.RilOmissionCommand"))
      {
        FtUtil.log_d("FactoryTestNVView", "mBroadcastReceiver.onReceive", "ACTION_RECEIVE_TESTNV");
        UIFactoryTestNVView.this.getFactoryTestNVValueCPO(paramAnonymousIntent);
        UIFactoryTestNVView.this.getFactoryTestNVValueCPO();
      }
    }
  };
  private FactoryTestPhone mFactoryPhone;
  private byte[] mItemValue = new byte[90];
  List<String> mPassItemValue = new ArrayList();
  private char[] mResultValue = new char[90];
  private TextView mView1;
  private TextView mView2;
  private TextView mView3;
  private TextView mView4;
  
  private String Change_NV_Value(byte paramByte)
  {
    if (78 == paramByte) {
      return "N";
    }
    if (69 == paramByte) {
      return "E";
    }
    if (80 == paramByte) {
      return "P";
    }
    if (70 == paramByte) {
      return "F";
    }
    return "NONE";
  }
  
  private void getFactoryTestNVValue()
  {
    FtUtil.log_d("FactoryTestNVView", "getFactoryTestNVValue", null);
    this.mView1.setText(get_NV_Value(1, 23));
    this.mView2.setText(get_NV_Value(24, 46));
    this.mView3.setText(get_NV_Value(47, 69));
    this.mView4.setText(get_NV_Value(70, 90));
  }
  
  private void getFactoryTestNVValueCPO()
  {
    FtUtil.log_d("FactoryTestNVView", "getFactoryTestNVValueCPO", null);
    this.mView1.setText(get_NV_ValueCPO(0, 22));
    this.mView2.setText(get_NV_ValueCPO(23, 45));
    this.mView3.setText(get_NV_ValueCPO(46, 68));
    this.mView4.setText(get_NV_ValueCPO(69, 89));
  }
  
  private void getFactoryTestNVValueCPO(Intent paramIntent)
  {
    FtUtil.log_d("FactoryTestNVView", "getFactoryTestNVValueCPO", null);
    String str1 = paramIntent.getStringExtra("COMMAND");
    if (str1 == null) {
      str1 = "";
    }
    int i = 6;
    if (i < str1.length())
    {
      String str2 = str1.substring(i + 2, i + 4);
      FtUtil.log_d("FactoryTestNVView", "getFactoryTestNVValueCPO", "mTempResultValue : " + str2);
      if ("50".equals(str2)) {
        this.mPassItemValue.add("PASS");
      }
      for (;;)
      {
        i += 4;
        break;
        this.mPassItemValue.add("FAIL");
      }
    }
    int j = 0;
    if (j < 90)
    {
      FtUtil.log_d("FactoryTestNVView", "getFactoryTestNVValueCPO", "i : " + j);
      FtUtil.log_d("FactoryTestNVView", "getFactoryTestNVValueCPO", "mPassItemValue.get(i) : " + (String)this.mPassItemValue.get(j));
      if ("PASS".equals(this.mPassItemValue.get(j)))
      {
        this.mItemValue[j] = ((byte)(j + 1));
        this.mResultValue[j] = 'P';
      }
      for (;;)
      {
        j++;
        break;
        this.mItemValue[j] = ((byte)(j + 1));
        this.mResultValue[j] = 'N';
      }
    }
  }
  
  private String get_NV_Value(int paramInt1, int paramInt2)
  {
    FtUtil.log_d("FactoryTestNVView", "get_NV_Value", null);
    String str = "";
    while (paramInt1 <= paramInt2)
    {
      byte b = NVAccessor.getNV((byte)paramInt1);
      FtUtil.log_d("FactoryTestNVView", "get_NV_Value", "key: " + paramInt1 + "NV: " + b);
      str = str + "" + paramInt1 + " / " + Change_NV_Value(b);
      if (paramInt1 < paramInt2) {
        str = str + "\n";
      }
      paramInt1++;
    }
    return str;
  }
  
  private String get_NV_ValueCPO(int paramInt1, int paramInt2)
  {
    FtUtil.log_d("FactoryTestNVView", "get_NV_ValueCPO", null);
    String str = "";
    for (int i = paramInt1; i <= paramInt2; i++)
    {
      char c = this.mResultValue[i];
      FtUtil.log_d("FactoryTestNVView", "get_NV_ValueCPO", "key: " + i + "NV: " + c);
      str = str + "" + (i + 1) + " / " + c;
      if (paramInt1 < paramInt2) {
        str = str + "\n";
      }
    }
    return str;
  }
  
  private void set_NV_Value(byte paramByte)
  {
    NVAccessor.setNV(paramByte, (byte)80);
    FtUtil.log_d("FactoryTestNVView", "set_NV_Value", "key: " + paramByte);
  }
  
  private void set_NV_ValueCPO(byte paramByte1, byte paramByte2)
  {
    FactoryTestPhone localFactoryTestPhone = new FactoryTestPhone(this);
    localFactoryTestPhone.bindSecPhoneService();
    localFactoryTestPhone.updateNVItem(paramByte1, paramByte2);
  }
  
  public void onClick(View paramView)
  {
    FtUtil.log_d("FactoryTestNVView", "onClick", "start_update_in_nvitem");
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131296758: 
      if (FtUtil.isFactoryAppAPO())
      {
        set_NV_Value((byte)1);
        getFactoryTestNVValue();
        return;
      }
      set_NV_ValueCPO((byte)1, (byte)80);
      return;
    }
    if (FtUtil.isFactoryAppAPO())
    {
      set_NV_Value((byte)4);
      getFactoryTestNVValue();
      return;
    }
    set_NV_ValueCPO((byte)4, (byte)80);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    FtUtil.log_d("FactoryTestNVView", "onCreate", null);
    setContentView(2130903091);
    findViewById(2131296758).setOnClickListener(this);
    findViewById(2131296759).setOnClickListener(this);
    this.mView1 = ((TextView)findViewById(2131296754));
    this.mView2 = ((TextView)findViewById(2131296755));
    this.mView3 = ((TextView)findViewById(2131296756));
    this.mView4 = ((TextView)findViewById(2131296757));
  }
  
  protected void onPause()
  {
    super.onPause();
  }
  
  protected void onResume()
  {
    super.onResume();
    if (FtUtil.isFactoryAppAPO())
    {
      getFactoryTestNVValue();
      return;
    }
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.android.samsungtest.RilOmissionCommand");
    registerReceiver(this.mBroadcastReceiver, localIntentFilter);
    this.mFactoryPhone = new FactoryTestPhone(this);
    this.mFactoryPhone.bindSecPhoneService();
    FtUtil.log_d("FactoryTestNVView", "onResume", "Request for TestNV!");
    this.mFactoryPhone.requestTestNvViewToRil();
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIFactoryTestNVView
 * JD-Core Version:    0.7.1
 */