package com.sec.factory.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.sec.factory.app.factorytest.FactoryTestPhone;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.NVAccessor;
import com.sec.factory.support.Support.FactoryTestMenu;
import java.util.ArrayList;
import java.util.List;

public class UIFactoryHistory
  extends UIBase
{
  private final String GET_HISTORY_VIEW_ITEM_ACTION = "com.android.samsungtest.RilOmissionCommand";
  private HistoryAdaptor mAdaptor;
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      FtUtil.log_e(UIFactoryHistory.this.CLASS_NAME, "Action: ", str1);
      if (str1.equals("com.android.samsungtest.RilOmissionCommand"))
      {
        String str2 = paramAnonymousIntent.getStringExtra("COMMAND").substring(6);
        FtUtil.log_e(UIFactoryHistory.this.CLASS_NAME, "valueString: ", str2);
        UIFactoryHistory.this.parseNVHistoryCPO(str2);
        UIFactoryHistory.this.stopReceiver();
      }
    }
  };
  private List<HistoryItem> mHistoryList = new ArrayList();
  private ListView mListView;
  
  public UIFactoryHistory()
  {
    super("FactoryTestMainHistoryView");
  }
  
  private String convertResult(String paramString)
  {
    String str = "N";
    if ("50".equals(paramString)) {
      str = "P";
    }
    do
    {
      return str;
      if ("45".equals(paramString)) {
        return "E";
      }
      if ("46".equals(paramString)) {
        return "F";
      }
    } while (!"4E".equals(paramString));
    return "N";
  }
  
  private String getItemIDforPGM(String paramString)
  {
    String str = "NA";
    if ("01".contains(paramString)) {
      str = "SMD FUNCTION";
    }
    do
    {
      return str;
      if ("04".contains(paramString)) {
        return "PBA TEST";
      }
      if ("07".contains(paramString)) {
        return "RF CAL";
      }
      if ("08".contains(paramString)) {
        return "RF CAL2";
      }
      if ("0A".contains(paramString.toUpperCase())) {
        return "LTE CAL";
      }
      if ("0B".contains(paramString.toUpperCase())) {
        return "FINAL TEST";
      }
      if ("0C".contains(paramString.toUpperCase())) {
        return "LTE FINAL";
      }
      if ("0E".contains(paramString.toUpperCase())) {
        return "WLAN";
      }
      if ("0F".contains(paramString.toUpperCase())) {
        return "GPS";
      }
      if ("10".contains(paramString.toUpperCase())) {
        return "IMEI WRITE";
      }
      if ("12".contains(paramString.toUpperCase())) {
        return "FUNC 3";
      }
      if ("13".contains(paramString.toUpperCase())) {
        return "FUNC 1";
      }
      if ("14".contains(paramString.toUpperCase())) {
        return "FUNC 2";
      }
      if ("62".contains(paramString.toUpperCase())) {
        return "PBA Repair";
      }
    } while (!"63".contains(paramString.toUpperCase()));
    return "MAIN Repair";
  }
  
  private void parseNVHistory()
  {
    FtUtil.log_e(this.CLASS_NAME, "parseNVHistory", "Response (NV History) : " + SystemClock.uptimeMillis() + " millisecond");
    String str1 = NVAccessor.getNVHistory();
    if (str1 == null)
    {
      FtUtil.log_e(this.CLASS_NAME, "parseNVHistory", "No Item");
      return;
    }
    FtUtil.log_i(this.CLASS_NAME, "parseNVHistory", "[Data] size: " + str1.length() + ", value: " + str1);
    for (int i = 0; i < str1.length(); i += 3)
    {
      String str2 = str1.substring(i, i + 2);
      String str3 = str1.substring(i + 2, i + 3);
      FtUtil.log_i(this.CLASS_NAME, "parseNVHistory", "NVKey : " + str2 + ", NVValue : " + str3);
      String str4 = Integer.toHexString(Integer.parseInt(str2, 10));
      this.mHistoryList.add(new HistoryItem(str4, str3));
    }
    this.mAdaptor.notifyDataSetChanged();
  }
  
  private void parseNVHistoryCPO(String paramString)
  {
    FtUtil.log_e(this.CLASS_NAME, "parseNVHistory", "Response (NV History) : " + SystemClock.uptimeMillis() + " millisecond");
    if (paramString == null)
    {
      FtUtil.log_e(this.CLASS_NAME, "parseNVHistory", "No Item");
      return;
    }
    FtUtil.log_i(this.CLASS_NAME, "parseNVHistory", "[Data] size: " + paramString.length() + ", value: " + paramString);
    for (int i = 0; i < paramString.length(); i += 4)
    {
      String str1 = paramString.substring(i, i + 2);
      String str2 = paramString.substring(i + 2, i + 4);
      FtUtil.log_i(this.CLASS_NAME, "parseNVHistory", "NVKey : " + str1 + ", NVValue : " + str2);
      if (!"00".equals(str1)) {
        this.mHistoryList.add(new HistoryItem(str1, convertResult(str2)));
      }
    }
    this.mAdaptor.notifyDataSetChanged();
  }
  
  private void setupReceiver()
  {
    registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.android.samsungtest.RilOmissionCommand"));
  }
  
  private void stopReceiver()
  {
    unregisterReceiver(this.mBroadcastReceiver);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903078);
    this.mAdaptor = new HistoryAdaptor(this, 2130903079, this.mHistoryList);
    this.mListView = ((ListView)findViewById(2131296495));
    this.mListView.setAdapter(this.mAdaptor);
  }
  
  protected void onResume()
  {
    super.onResume();
    this.mHistoryList.clear();
    FtUtil.log_e(this.CLASS_NAME, "onResume", "Request (NV History) : " + SystemClock.uptimeMillis() + " millisecond");
    if (FtUtil.isFactoryAppAPO())
    {
      parseNVHistory();
      return;
    }
    setupReceiver();
    if (this.mFactoryPhone == null)
    {
      this.mFactoryPhone = new FactoryTestPhone(getApplicationContext());
      this.mFactoryPhone.bindSecPhoneService();
    }
    this.mFactoryPhone.requestHistoryNvViewToRil();
  }
  
  private class HistoryAdaptor
    extends ArrayAdapter<UIFactoryHistory.HistoryItem>
  {
    public HistoryAdaptor(int paramInt, List<UIFactoryHistory.HistoryItem> paramList)
    {
      super(paramList, localList);
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null) {}
      for (LinearLayout localLinearLayout = (LinearLayout)LayoutInflater.from(getContext()).inflate(2130903079, paramViewGroup, false);; localLinearLayout = (LinearLayout)paramView)
      {
        TextView localTextView1 = (TextView)localLinearLayout.findViewById(2131296496);
        TextView localTextView2 = (TextView)localLinearLayout.findViewById(2131296497);
        TextView localTextView3 = (TextView)localLinearLayout.findViewById(2131296300);
        UIFactoryHistory.HistoryItem localHistoryItem = (UIFactoryHistory.HistoryItem)UIFactoryHistory.this.mHistoryList.get(paramInt);
        localTextView1.setText(UIFactoryHistory.HistoryItem.access$400(localHistoryItem));
        localTextView2.setText(UIFactoryHistory.HistoryItem.access$500(localHistoryItem));
        localTextView3.setText(UIFactoryHistory.HistoryItem.access$600(localHistoryItem));
        FtUtil.log_i(UIFactoryHistory.this.CLASS_NAME, "HistoryAdaptor-getView", "[" + paramInt + "] 'NV Key' / 'Name' / 'NV Value' : " + UIFactoryHistory.HistoryItem.access$400(localHistoryItem) + " / " + UIFactoryHistory.HistoryItem.access$500(localHistoryItem) + " / " + UIFactoryHistory.HistoryItem.access$600(localHistoryItem));
        return localLinearLayout;
      }
    }
  }
  
  private class HistoryItem
  {
    private String mItemName;
    private String mNVKey;
    private String mNVValue;
    
    public HistoryItem(String paramString1, String paramString2)
    {
      this.mItemName = UIFactoryHistory.this.getItemIDforPGM(paramString1);
      if ("NA".equals(this.mItemName))
      {
        this.mNVKey = Integer.toHexString(Integer.parseInt(paramString1, 16)).toUpperCase();
        this.mItemName = Support.FactoryTestMenu.getFactoryTestName("nv", "0x" + this.mNVKey);
      }
      if (paramString1.equals("e"))
      {
        this.mNVKey = Integer.toHexString(Integer.parseInt(paramString1, 16)).toUpperCase();
        this.mItemName = "Wlan";
      }
      this.mNVKey = String.valueOf(Integer.parseInt(paramString1, 16)).toUpperCase();
      if (this.mItemName == null) {
        this.mItemName = "UNKNOWN";
      }
      this.mNVValue = paramString2;
      FtUtil.log_i(UIFactoryHistory.this.CLASS_NAME, "HistoryItem-HistoryItem", "NV Key (Hexa/Decimal) : " + paramString1 + "/" + this.mNVKey + ", Name : " + this.mItemName + ", NV Value : " + this.mNVValue);
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.ui.UIFactoryHistory
 * JD-Core Version:    0.7.1
 */