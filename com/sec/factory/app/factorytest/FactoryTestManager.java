package com.sec.factory.app.factorytest;

import com.sec.factory.support.FtUtil;
import com.sec.factory.support.NVAccessor;
import com.sec.factory.support.Support.FactoryTestMenu;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FactoryTestManager
{
  private static int INVISIBLE_ITEM_COUNT = 0;
  private static FactoryTestMainAdapter mAdapter = null;
  private static FactoryTestItemInfo[] mFactoryTestItemInfo;
  
  public static String convertorID_XML(byte paramByte)
  {
    String str = new DecimalFormat("00").format(paramByte);
    FtUtil.log_d("FactoryTestManager", "convertorID_XML", "ID : " + str);
    return str;
  }
  
  public static String convertorNVValue(byte paramByte)
  {
    if (paramByte == 78) {
      return "[N]";
    }
    if (paramByte == 69) {
      return "[E]";
    }
    if (paramByte == 80) {
      return "[P]";
    }
    if (paramByte == 70) {
      return "[F]";
    }
    return "[None]";
  }
  
  public static boolean getAllitemPass()
  {
    boolean bool = true;
    FtUtil.log_i("FactoryTestManager", "getAllitemPass", "getAllitemPass check");
    for (int i = 0; i < mFactoryTestItemInfo.length; i++) {
      if (mFactoryTestItemInfo[i].mResult != 80) {
        bool = false;
      }
    }
    return bool;
  }
  
  public static FactoryTestItemInfo[] getChildItems(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    for (FactoryTestItemInfo localFactoryTestItemInfo : mFactoryTestItemInfo) {
      if (localFactoryTestItemInfo.mParentID == paramInt) {
        localArrayList.add(localFactoryTestItemInfo);
      }
    }
    return (FactoryTestItemInfo[])localArrayList.toArray(new FactoryTestItemInfo[0]);
  }
  
  public static boolean getClickable(int paramInt)
  {
    return mFactoryTestItemInfo[paramInt].mClickable;
  }
  
  public static boolean getEnable(int paramInt)
  {
    for (int i = 0; i < mFactoryTestItemInfo.length; i++) {
      if (mFactoryTestItemInfo[i].mID == paramInt) {
        return true;
      }
    }
    return false;
  }
  
  public static int getItemCount()
  {
    return mFactoryTestItemInfo.length - INVISIBLE_ITEM_COUNT;
  }
  
  public static int getItemID(int paramInt)
  {
    return mFactoryTestItemInfo[paramInt].mID;
  }
  
  public static byte getItemNVKey(int paramInt)
  {
    return mFactoryTestItemInfo[paramInt].mNVKey;
  }
  
  public static String getItemName_Position(int paramInt)
  {
    if (mFactoryTestItemInfo[paramInt].mNVKey != -1) {
      return mFactoryTestItemInfo[paramInt].mName + "[" + mFactoryTestItemInfo[paramInt].mNVKey + "]";
    }
    FactoryTestItemInfo[] arrayOfFactoryTestItemInfo = getChildItems(mFactoryTestItemInfo[paramInt].mID);
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; i < arrayOfFactoryTestItemInfo.length; i++)
    {
      localStringBuilder.append(arrayOfFactoryTestItemInfo[i].mNVKey);
      localStringBuilder.append(",");
    }
    localStringBuilder.delete(-1 + localStringBuilder.length(), localStringBuilder.length());
    return mFactoryTestItemInfo[paramInt].mName + "[" + localStringBuilder + "]";
  }
  
  public static int getItemPosition_ID(int paramInt)
  {
    for (int i = 0; i < mFactoryTestItemInfo.length; i++) {
      if (mFactoryTestItemInfo[i].mID == paramInt) {
        return i;
      }
    }
    return -1;
  }
  
  public static byte getItemResult(int paramInt)
  {
    return mFactoryTestItemInfo[paramInt].mResult;
  }
  
  public static void initialize()
  {
    FtUtil.log_i("FactoryTestManager", "initialize", "FactoryTestMain Initialize");
    INVISIBLE_ITEM_COUNT = 0;
    String[] arrayOfString1 = Support.FactoryTestMenu.getFactoryTestInfo();
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    int i = arrayOfString1.length;
    int j = 0;
    if (j < i)
    {
      String[] arrayOfString2 = arrayOfString1[j].split(",");
      int k = Integer.parseInt(arrayOfString2[0]);
      String str1 = arrayOfString2[1];
      byte b1 = (byte)(0xFF & Integer.parseInt(arrayOfString2[2].substring(2), 16));
      byte b2 = 78;
      if (FtUtil.isFactoryAppAPO()) {
        b2 = NVAccessor.getNV(b1);
      }
      int m;
      label123:
      boolean bool1;
      boolean bool3;
      if (arrayOfString2[4].equals("null"))
      {
        m = -1;
        String str2 = arrayOfString2[3];
        bool1 = true;
        boolean bool2 = str2.toLowerCase().contains("auto");
        bool3 = false;
        if (bool2)
        {
          bool3 = true;
          bool1 = false;
        }
        if (str2.toLowerCase().contains("clickable")) {
          bool1 = true;
        }
        if (str2.toLowerCase().contains("notclickable")) {
          bool1 = false;
        }
        if (!str2.toLowerCase().contains("invisibility")) {
          break label255;
        }
        localArrayList2.add(new FactoryTestItemInfo(k, str1, b1, b2, bool3, bool1, m));
        INVISIBLE_ITEM_COUNT = 1 + INVISIBLE_ITEM_COUNT;
      }
      for (;;)
      {
        j++;
        break;
        m = Integer.parseInt(arrayOfString2[4]);
        break label123;
        label255:
        localArrayList1.add(new FactoryTestItemInfo(k, str1, b1, b2, bool3, bool1, m));
      }
    }
    ArrayList localArrayList3 = new ArrayList();
    localArrayList3.addAll(localArrayList1);
    localArrayList3.addAll(localArrayList2);
    mFactoryTestItemInfo = (FactoryTestItemInfo[])localArrayList3.toArray(new FactoryTestItemInfo[0]);
    setResultParent();
  }
  
  public static boolean isAutoTest(int paramInt)
  {
    return mFactoryTestItemInfo[paramInt].mAutoTest;
  }
  
  public static boolean isTest(int paramInt)
  {
    for (int i = 0; i < mFactoryTestItemInfo.length; i++) {
      if (mFactoryTestItemInfo[i].mID == paramInt) {
        return true;
      }
    }
    return false;
  }
  
  public static void notifyDataSetChanged()
  {
    mAdapter.notifyDataSetChanged();
  }
  
  public static void setAdapter(FactoryTestMainAdapter paramFactoryTestMainAdapter)
  {
    mAdapter = paramFactoryTestMainAdapter;
  }
  
  public static void setItemResult(int paramInt, byte paramByte)
  {
    FtUtil.log_e("FactoryTestManager", "setItemResult", "position:" + paramInt + ", itemResult:" + paramByte);
    if (paramInt > -1)
    {
      if (mFactoryTestItemInfo[paramInt].mResult == 80) {
        break label119;
      }
      FactoryTestItemInfo.access$102(mFactoryTestItemInfo[paramInt], paramByte);
      FtUtil.log_d("FactoryTestManager", "setItemResult", mFactoryTestItemInfo[paramInt].mName + " = write : " + paramByte);
    }
    for (;;)
    {
      if (FtUtil.isFactoryAppAPO()) {
        NVAccessor.setNV(mFactoryTestItemInfo[paramInt].mNVKey, paramByte);
      }
      return;
      label119:
      FtUtil.log_d("FactoryTestManager", "setItemResult", mFactoryTestItemInfo[paramInt].mName + " = PASS");
    }
  }
  
  public static void setItemResultWithoutNVUpdate(int paramInt, byte paramByte)
  {
    FtUtil.log_e("FactoryTestManager", "setItemResultWithoutNVUpdate", "position:" + paramInt + ", itemResult:" + paramByte);
    if (mFactoryTestItemInfo[paramInt].mResult != 80)
    {
      FactoryTestItemInfo.access$102(mFactoryTestItemInfo[paramInt], paramByte);
      FtUtil.log_d("FactoryTestManager", "setItemResultWithoutNVUpdate", mFactoryTestItemInfo[paramInt].mName + " = write : " + paramByte);
      return;
    }
    FtUtil.log_d("FactoryTestManager", "setItemResultWithoutNVUpdate", mFactoryTestItemInfo[paramInt].mName + " = PASS");
  }
  
  public static void setResultParent()
  {
    FtUtil.log_i("FactoryTestManager", "setResultParent", null);
    int i = 0;
    if (i < mFactoryTestItemInfo.length)
    {
      int j;
      int k;
      int m;
      if (mFactoryTestItemInfo[i].mParentID >= 0)
      {
        j = mFactoryTestItemInfo[i].mParentID;
        k = 1;
        m = i;
        label42:
        if ((m < mFactoryTestItemInfo.length) && (mFactoryTestItemInfo[m].mParentID >= 0)) {
          break label109;
        }
        label61:
        if (k == 0) {
          break label142;
        }
        setItemResultWithoutNVUpdate(getItemPosition_ID(j), (byte)80);
        FtUtil.log_i("FactoryTestManager", "setResultParent", "PASS => (ID)" + j);
      }
      for (;;)
      {
        i = m;
        i++;
        break;
        label109:
        if (j != mFactoryTestItemInfo[m].mParentID) {
          break label61;
        }
        if (mFactoryTestItemInfo[m].mResult != 80) {
          k = 0;
        }
        m++;
        break label42;
        label142:
        FtUtil.log_i("FactoryTestManager", "setResultParent", "FAIL => (ID)" + j);
      }
    }
  }
  
  public static int size()
  {
    return mFactoryTestItemInfo.length;
  }
  
  public static class FactoryTestItemInfo
  {
    private boolean mAutoTest;
    private boolean mClickable;
    private int mID;
    private byte mNVKey;
    private String mName;
    private int mParentID;
    private byte mResult;
    
    public FactoryTestItemInfo(int paramInt1, String paramString, byte paramByte1, byte paramByte2, boolean paramBoolean1, boolean paramBoolean2, int paramInt2)
    {
      this.mID = paramInt1;
      this.mName = paramString;
      this.mNVKey = paramByte1;
      this.mResult = paramByte2;
      this.mAutoTest = paramBoolean1;
      this.mClickable = paramBoolean2;
      this.mParentID = paramInt2;
      FtUtil.log_i("FactoryTestManager", "FactoryTestItemInfo", "id=" + paramInt1 + ", name=" + paramString + ", nv=" + paramByte1 + ", result=" + paramByte2 + ", pID=" + paramInt2);
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestManager
 * JD-Core Version:    0.7.1
 */