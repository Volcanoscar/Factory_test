package com.sec.android.app.sysscope.service;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class SysScopeResultInfo
  implements Parcelable, Serializable
{
  public static final Parcelable.Creator<SysScopeResultInfo> CREATOR = new Parcelable.Creator()
  {
    public SysScopeResultInfo createFromParcel(Parcel paramAnonymousParcel)
    {
      return new SysScopeResultInfo(paramAnonymousParcel);
    }
    
    public SysScopeResultInfo[] newArray(int paramAnonymousInt)
    {
      return new SysScopeResultInfo[paramAnonymousInt];
    }
  };
  private static final long serialVersionUID = -4933786939071730150L;
  private ArrayList<ResultCode> mResultCodes = new ArrayList();
  Date mTime = new Date();
  
  public SysScopeResultInfo() {}
  
  public SysScopeResultInfo(Parcel paramParcel)
  {
    readFromParcel(paramParcel);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int getResult()
  {
    if (this.mResultCodes == null) {}
    Iterator localIterator;
    do
    {
      while (!localIterator.hasNext())
      {
        return 1;
        localIterator = this.mResultCodes.iterator();
      }
    } while (((ResultCode)localIterator.next()).equals(ResultCode.OK));
    return 2;
  }
  
  public void readFromParcel(Parcel paramParcel)
  {
    setDate(paramParcel.readLong());
    int i = paramParcel.readInt();
    this.mResultCodes = new ArrayList();
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return;
      }
      ResultCode localResultCode = ResultCode.fromInt(paramParcel.readInt());
      localResultCode.setDescription(paramParcel.readString());
      this.mResultCodes.add(localResultCode);
    }
  }
  
  public void setDate(long paramLong)
  {
    this.mTime = new Date(paramLong);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (this.mTime == null) {
      return "";
    }
    localStringBuilder.append(this.mTime.toString());
    localStringBuilder.append("\n");
    Iterator localIterator = this.mResultCodes.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return localStringBuilder.toString();
      }
      ResultCode localResultCode = (ResultCode)localIterator.next();
      localStringBuilder.append(localResultCode.name());
      String str = localResultCode.getDescription();
      if ((str != null) && (!str.equals("")))
      {
        localStringBuilder.append(": ");
        localStringBuilder.append(str);
      }
      localStringBuilder.append("\n");
    }
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(this.mTime.getTime());
    paramParcel.writeInt(this.mResultCodes.size());
    Iterator localIterator = this.mResultCodes.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      ResultCode localResultCode = (ResultCode)localIterator.next();
      paramParcel.writeInt(localResultCode.value());
      paramParcel.writeString(localResultCode.getDescription());
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.sysscope.service.SysScopeResultInfo
 * JD-Core Version:    0.7.1
 */