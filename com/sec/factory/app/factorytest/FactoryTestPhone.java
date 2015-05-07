package com.sec.factory.app.factorytest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.Settings.System;
import com.sec.android.app.CscFeature;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class FactoryTestPhone
{
  private static Queue<byte[]> mQueue = null;
  private String CLASS_NAME = "FactoryTestPhone";
  private boolean IS_CAL2_TEST_PASS = false;
  private boolean IS_CAL_TEST_PASS = false;
  private boolean IS_FINAL_TEST_PASS = false;
  private boolean IS_LTECAL_TEST_PASS = false;
  private boolean IS_LTEFINAL_TEST_PASS = false;
  private boolean IS_PBA_TEST_PASS = false;
  private boolean IS_SMD_TEST_PASS = false;
  private String device = SystemProperties.get("ro.product.device", "Unknown").trim().toLowerCase();
  private boolean isConnected = false;
  Context mContext;
  private byte[] mData;
  public Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
      case 200: 
      case 203: 
      case 201: 
      case 202: 
      case 205: 
      case 204: 
      case 206: 
      case 207: 
        do
        {
          do
          {
            return;
            FactoryTestPhone.access$902(FactoryTestPhone.this, (byte[])paramAnonymousMessage.getData().getByteArray("response"));
            if (FactoryTestPhone.this.mResultData != null)
            {
              FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "Received Test NV Values from RIL");
              FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "mResultData: " + FactoryTestPhone.this.mResultData);
              return;
            }
            FtUtil.log_e(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "Fail");
            return;
            FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "Received Test NV Value from RIL");
            FactoryTestPhone.access$902(FactoryTestPhone.this, (byte[])paramAnonymousMessage.getData().getByteArray("response"));
            return;
            FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "Received Test NV Values from RIL");
            return;
            FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "Item NV update success");
            return;
            FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "Girp request Success");
            return;
            FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "Unique Number get success");
            FactoryTestPhone.access$902(FactoryTestPhone.this, (byte[])paramAnonymousMessage.getData().getByteArray("response"));
          } while (FactoryTestPhone.this.mResultData == null);
          FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "Received Unique Number from RIL");
          FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "mResultData: " + FactoryTestPhone.this.mResultData);
          return;
          FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "LTESupportHandler - what : HND_LTE_POWER_CMD");
          FactoryTestPhone.access$902(FactoryTestPhone.this, (byte[])paramAnonymousMessage.getData().getByteArray("response"));
          Intent localIntent = new Intent("com.sec.factory.aporiented.athandler.AtLtepower.SetLTEPower");
          if (FactoryTestPhone.this.mResultData != null)
          {
            FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "pass");
            localIntent.putExtra("EXTRA_KEY", "OK");
          }
          for (;;)
          {
            FactoryTestPhone.this.mContext.sendBroadcast(localIntent);
            return;
            FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "fail");
            localIntent.putExtra("EXTRA_KEY", "NG");
          }
          FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "Modem dump success");
          FactoryTestPhone.access$902(FactoryTestPhone.this, (byte[])paramAnonymousMessage.getData().getByteArray("response"));
        } while (FactoryTestPhone.this.mResultData == null);
        return;
      }
      FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mHandler.handleMessage", "DUAL MODE Changed");
    }
  };
  private final HashMap<String, Byte> mNVKeyToItemIDHashmap = new HashMap();
  private Message mResponse;
  private byte[] mResultData;
  private ServiceConnection mSecPhoneServiceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mSecPhoneServiceConnection.onServiceConnected", null);
      FactoryTestPhone.access$102(FactoryTestPhone.this, new Messenger(paramAnonymousIBinder));
      FactoryTestPhone.access$202(FactoryTestPhone.this, true);
      FtUtil.log_v(FactoryTestPhone.this.CLASS_NAME, "mSecPhoneServiceConnection.onServiceConnected", "isConnected == true");
      while (FactoryTestPhone.mQueue.peek() != null)
      {
        FtUtil.log_v(FactoryTestPhone.this.CLASS_NAME, "mSecPhoneServiceConnection.onServiceConnected", "mData: " + FactoryTestPhone.this.mData + "mResponse" + FactoryTestPhone.this.mResponse);
        FactoryTestPhone.this.sendMessageToRil(FactoryTestPhone.this.mData, FactoryTestPhone.this.mResponse);
      }
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "mSecPhoneServiceConnection.onServiceDisconnected", null);
      FactoryTestPhone.access$202(FactoryTestPhone.this, false);
      FactoryTestPhone.access$102(FactoryTestPhone.this, null);
      FactoryTestPhone.this.rebindToSecPhoneService();
    }
  };
  private Messenger mServiceMessenger = null;
  private Messenger mSvcModeMessenger;
  private String operator = SystemProperties.get("ro.csc.sales_code", "Unknown").trim().toLowerCase();
  
  public FactoryTestPhone(Context paramContext)
  {
    this.mContext = paramContext;
    this.mSvcModeMessenger = new Messenger(this.mHandler);
    mQueue = new LinkedList();
    this.mData = null;
    this.mResponse = null;
  }
  
  private boolean checkResultData(final byte[] paramArrayOfByte, final int paramInt)
  {
    if (paramInt == 0) {
      return false;
    }
    if (paramArrayOfByte == null)
    {
      FtUtil.log_i(this.CLASS_NAME, "checkResultData", "Entering Loop : " + paramInt);
      this.mHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          if (paramArrayOfByte == null) {
            FactoryTestPhone.this.checkResultData(paramArrayOfByte, -1 + paramInt);
          }
        }
      }, 500L);
      return false;
    }
    return true;
  }
  
  private void invokeOemRilRequestRaw(byte[] paramArrayOfByte, Message paramMessage)
  {
    FtUtil.log_i(this.CLASS_NAME, "invokeOemRilRequestRaw", null);
    if (!mQueue.offer(paramArrayOfByte)) {
      FtUtil.log_e(this.CLASS_NAME, "invokeOemRilRequestRaw", "failed offer a item");
    }
    for (;;)
    {
      return;
      if (this.isConnected != true) {
        break;
      }
      FtUtil.log_v(this.CLASS_NAME, "invokeOemRilRequestRaw", "isConnected == true");
      while (mQueue.peek() != null) {
        sendMessageToRil(paramArrayOfByte, paramMessage);
      }
    }
    FtUtil.log_v(this.CLASS_NAME, "invokeOemRilRequestRaw", "isConnected == false");
    bindSecPhoneService();
  }
  
  private void rebindToSecPhoneService()
  {
    if (this.mServiceMessenger != null) {
      return;
    }
    new Thread()
    {
      public void run()
      {
        while (FactoryTestPhone.this.mServiceMessenger == null)
        {
          FactoryTestPhone.this.bindSecPhoneService();
          try
          {
            Thread.sleep(500L);
          }
          catch (InterruptedException localInterruptedException)
          {
            FtUtil.log_i(FactoryTestPhone.this.CLASS_NAME, "rebindToSecPhoneService", "rebindToSecPhoneService : sleep interrupted.");
          }
        }
      }
    }.start();
  }
  
  private void sendMessageToRil(byte[] paramArrayOfByte, Message paramMessage)
  {
    FtUtil.log_i(this.CLASS_NAME, "sendMessageToRil", null);
    if (paramMessage == null)
    {
      FtUtil.log_i(this.CLASS_NAME, "sendMessageToRil", "Create Default Request");
      paramMessage = this.mHandler.obtainMessage(300);
    }
    Bundle localBundle = paramMessage.getData();
    FtUtil.log_i(this.CLASS_NAME, "sendMessageToRil", "current Queue size before : " + mQueue.size());
    if (mQueue.peek() != null)
    {
      byte[] arrayOfByte = (byte[])mQueue.poll();
      FtUtil.log_i(this.CLASS_NAME, "sendMessageToRil", "data: " + arrayOfByte);
      if (arrayOfByte == null)
      {
        FtUtil.log_i(this.CLASS_NAME, "sendMessageToRil", "Data null..Return");
        return;
      }
      localBundle.putByteArray("request", arrayOfByte);
      paramMessage.setData(localBundle);
      paramMessage.replyTo = this.mSvcModeMessenger;
    }
    try
    {
      if (this.mServiceMessenger != null)
      {
        FtUtil.log_i(this.CLASS_NAME, "sendMessageToRil", "sendMessage() to RIL");
        this.mServiceMessenger.send(paramMessage);
      }
      for (;;)
      {
        label196:
        if (mQueue.peek() == null) {}
        FtUtil.log_i(this.CLASS_NAME, "sendMessageToRil", "current Queue size after : " + mQueue.size());
        return;
        FtUtil.log_e(this.CLASS_NAME, "sendMessageToRil", "sendMessage : mQueue is empty!!!");
      }
    }
    catch (RemoteException localRemoteException)
    {
      break label196;
    }
  }
  
  private void sendToRilControlData(byte[] paramArrayOfByte)
  {
    FtUtil.log_i(this.CLASS_NAME, "sendToRilControlData", null);
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    for (;;)
    {
      try
      {
        localDataOutputStream.writeByte(18);
        localDataOutputStream.writeByte(9);
        localDataOutputStream.writeShort(8);
        localDataOutputStream.write(paramArrayOfByte);
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        if (localDataOutputStream == null) {
          continue;
        }
        try
        {
          localDataOutputStream.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        continue;
      }
      finally
      {
        if (localDataOutputStream == null) {
          break label142;
        }
      }
      try
      {
        localDataOutputStream.close();
        this.mData = localByteArrayOutputStream.toByteArray();
        this.mResponse = this.mHandler.obtainMessage(205);
        invokeOemRilRequestRaw(localByteArrayOutputStream.toByteArray(), this.mResponse);
        return;
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
      }
    }
    try
    {
      localDataOutputStream.close();
      label142:
      throw ((Throwable)localObject);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        localException1.printStackTrace();
      }
    }
  }
  
  private void setUpHashforNVkeyToItemId()
  {
    this.mNVKeyToItemIDHashmap.put("15", Byte.valueOf((byte)0));
    this.mNVKeyToItemIDHashmap.put("16", Byte.valueOf((byte)1));
    this.mNVKeyToItemIDHashmap.put("20", Byte.valueOf((byte)12));
    this.mNVKeyToItemIDHashmap.put("21", Byte.valueOf((byte)13));
    this.mNVKeyToItemIDHashmap.put("17", Byte.valueOf((byte)2));
    this.mNVKeyToItemIDHashmap.put("32", Byte.valueOf((byte)20));
    this.mNVKeyToItemIDHashmap.put("22", Byte.valueOf((byte)10));
    this.mNVKeyToItemIDHashmap.put("19", Byte.valueOf((byte)4));
    if (this.device.contains("d2dcm")) {
      this.mNVKeyToItemIDHashmap.put("1C", Byte.valueOf((byte)8));
    }
    for (;;)
    {
      this.mNVKeyToItemIDHashmap.put("1B", Byte.valueOf((byte)6));
      this.mNVKeyToItemIDHashmap.put("30", Byte.valueOf((byte)23));
      this.mNVKeyToItemIDHashmap.put("2E", Byte.valueOf((byte)24));
      this.mNVKeyToItemIDHashmap.put("31", Byte.valueOf((byte)21));
      this.mNVKeyToItemIDHashmap.put("26", Byte.valueOf((byte)17));
      this.mNVKeyToItemIDHashmap.put("1E", Byte.valueOf((byte)9));
      this.mNVKeyToItemIDHashmap.put("29", Byte.valueOf((byte)22));
      this.mNVKeyToItemIDHashmap.put("1D", Byte.valueOf((byte)16));
      this.mNVKeyToItemIDHashmap.put("18", Byte.valueOf((byte)3));
      this.mNVKeyToItemIDHashmap.put("27", Byte.valueOf((byte)18));
      this.mNVKeyToItemIDHashmap.put("28", Byte.valueOf((byte)19));
      this.mNVKeyToItemIDHashmap.put("2D", Byte.valueOf((byte)30));
      this.mNVKeyToItemIDHashmap.put("2C", Byte.valueOf((byte)32));
      this.mNVKeyToItemIDHashmap.put("34", Byte.valueOf((byte)35));
      this.mNVKeyToItemIDHashmap.put("2A", Byte.valueOf((byte)26));
      this.mNVKeyToItemIDHashmap.put("2F", Byte.valueOf((byte)27));
      this.mNVKeyToItemIDHashmap.put("11", Byte.valueOf((byte)33));
      this.mNVKeyToItemIDHashmap.put("33", Byte.valueOf((byte)34));
      this.mNVKeyToItemIDHashmap.put("38", Byte.valueOf((byte)38));
      if (ModuleDevice.instance(this.mContext).getSpeakerCount() > 1) {
        this.mNVKeyToItemIDHashmap.put("2D", Byte.valueOf((byte)5));
      }
      this.mNVKeyToItemIDHashmap.put("35", Byte.valueOf((byte)42));
      this.mNVKeyToItemIDHashmap.put("37", Byte.valueOf((byte)43));
      this.mNVKeyToItemIDHashmap.put("3B", Byte.valueOf((byte)44));
      this.mNVKeyToItemIDHashmap.put("3C", Byte.valueOf((byte)45));
      this.mNVKeyToItemIDHashmap.put("3D", Byte.valueOf((byte)46));
      this.mNVKeyToItemIDHashmap.put("2B", Byte.valueOf((byte)31));
      this.mNVKeyToItemIDHashmap.put("12", Byte.valueOf((byte)41));
      this.mNVKeyToItemIDHashmap.put("25", Byte.valueOf((byte)25));
      this.mNVKeyToItemIDHashmap.put("36", Byte.valueOf((byte)47));
      this.mNVKeyToItemIDHashmap.put("24", Byte.valueOf((byte)48));
      this.mNVKeyToItemIDHashmap.put("3E", Byte.valueOf((byte)50));
      this.mNVKeyToItemIDHashmap.put("3F", Byte.valueOf((byte)52));
      if (!this.operator.contains("ctc")) {
        this.mNVKeyToItemIDHashmap.put("14", Byte.valueOf((byte)49));
      }
      if (!this.operator.contains("ctc")) {
        break;
      }
      this.mNVKeyToItemIDHashmap.put("13", Byte.valueOf((byte)49));
      return;
      if (CscFeature.getInstance().getString("CscFeature_MobileTV_EnableServiceAs").contains("SBTVD")) {
        this.mNVKeyToItemIDHashmap.put("1C", Byte.valueOf((byte)54));
      } else {
        this.mNVKeyToItemIDHashmap.put("1C", Byte.valueOf((byte)7));
      }
    }
    this.mNVKeyToItemIDHashmap.put("13", Byte.valueOf((byte)28));
    this.mNVKeyToItemIDHashmap.put("13", Byte.valueOf((byte)51));
  }
  
  public void DestroySecPhoneService()
  {
    unbindSecPhoneService();
    this.mSecPhoneServiceConnection = null;
    this.mData = null;
  }
  
  public void bindSecPhoneService()
  {
    FtUtil.log_i(this.CLASS_NAME, "bindSecPhoneService", null);
    Intent localIntent = new Intent();
    localIntent.setClassName("com.sec.phone", "com.sec.phone.SecPhoneService");
    if ((Support.Feature.getBoolean("SUPPORT_2ND_RIL")) && (!Support.Feature.getBoolean("SUPPORT_DUAL_STANBY")) && (Settings.System.getInt(this.mContext.getContentResolver(), "CURRENT_NETWORK", 0) != 0))
    {
      FtUtil.log_i(this.CLASS_NAME, "bindSecPhoneService", "use com.sec.phone.SecPhoneService2");
      localIntent.setClassName("com.sec.phone", "com.sec.phone.SecPhoneService2");
    }
    this.mContext.bindService(localIntent, this.mSecPhoneServiceConnection, 1);
  }
  
  public void changeDualModeState()
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    for (;;)
    {
      try
      {
        localDataOutputStream.writeByte(12);
        localDataOutputStream.writeByte(14);
        localDataOutputStream.writeShort(5);
        localDataOutputStream.writeByte(1);
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        if (localDataOutputStream == null) {
          continue;
        }
        try
        {
          localDataOutputStream.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        continue;
      }
      finally
      {
        if (localDataOutputStream == null) {
          break label129;
        }
      }
      try
      {
        localDataOutputStream.close();
        this.mData = localByteArrayOutputStream.toByteArray();
        this.mResponse = this.mHandler.obtainMessage(208);
        invokeOemRilRequestRaw(localByteArrayOutputStream.toByteArray(), this.mResponse);
        return;
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
      }
    }
    try
    {
      localDataOutputStream.close();
      label129:
      throw ((Throwable)localObject);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        localException1.printStackTrace();
      }
    }
  }
  
  public void getCPDump()
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    for (;;)
    {
      try
      {
        localDataOutputStream.writeByte(7);
        localDataOutputStream.writeByte(18);
        localDataOutputStream.writeShort(5);
        localDataOutputStream.writeByte(0);
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        if (localDataOutputStream == null) {
          continue;
        }
        try
        {
          localDataOutputStream.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        continue;
      }
      finally
      {
        if (localDataOutputStream == null) {
          break label129;
        }
      }
      try
      {
        localDataOutputStream.close();
        this.mData = localByteArrayOutputStream.toByteArray();
        this.mResponse = this.mHandler.obtainMessage(207);
        invokeOemRilRequestRaw(localByteArrayOutputStream.toByteArray(), this.mResponse);
        return;
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
      }
    }
    try
    {
      localDataOutputStream.close();
      label129:
      throw ((Throwable)localObject);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        localException1.printStackTrace();
      }
    }
  }
  
  public boolean getCpDumpResponse()
  {
    return this.mResultData != null;
  }
  
  public int getItemID(String paramString)
  {
    int i = -1;
    setUpHashforNVkeyToItemId();
    if (this.mNVKeyToItemIDHashmap.containsKey(paramString)) {
      i = ((Byte)this.mNVKeyToItemIDHashmap.get(paramString)).byteValue();
    }
    return i;
  }
  
  public byte getNVResult(String paramString)
  {
    if (!checkResultData(this.mResultData, 3)) {
      return 78;
    }
    int i = Integer.parseInt(paramString, 16);
    FtUtil.log_i(this.CLASS_NAME, "getNVResult", "itemNV: " + i);
    return this.mResultData[(i + 3)];
  }
  
  public boolean getResultForPGMItems(String paramString)
  {
    switch (Integer.parseInt(paramString, 16))
    {
    case 2: 
    case 3: 
    case 5: 
    case 6: 
    case 9: 
    default: 
      return false;
    case 1: 
      return this.IS_SMD_TEST_PASS;
    case 4: 
      return this.IS_PBA_TEST_PASS;
    case 7: 
      return this.IS_CAL_TEST_PASS;
    case 8: 
      return this.IS_CAL2_TEST_PASS;
    case 10: 
      return this.IS_FINAL_TEST_PASS;
    case 11: 
      return this.IS_LTECAL_TEST_PASS;
    }
    return this.IS_LTEFINAL_TEST_PASS;
  }
  
  public String getUniqueNumber()
  {
    if (!checkResultData(this.mResultData, 3)) {
      return null;
    }
    return new String(this.mResultData);
  }
  
  public void requestCPsAccelerometer(boolean paramBoolean)
  {
    FtUtil.log_i(this.CLASS_NAME, "requestCPsAccelerometer", null);
    byte[] arrayOfByte;
    if (paramBoolean)
    {
      arrayOfByte = new byte[4];
      arrayOfByte[0] = 2;
      arrayOfByte[1] = 0;
      arrayOfByte[2] = 12;
      arrayOfByte[3] = 1;
    }
    for (;;)
    {
      sendToRilControlData(arrayOfByte);
      return;
      arrayOfByte = new byte[] { 2, 0, 12, 0 };
    }
  }
  
  public void requestForUniqueNumber()
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    for (;;)
    {
      try
      {
        localDataOutputStream.writeByte(12);
        localDataOutputStream.writeByte(5);
        localDataOutputStream.writeShort(5);
        localDataOutputStream.writeByte(1);
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        if (localDataOutputStream == null) {
          continue;
        }
        try
        {
          localDataOutputStream.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        continue;
      }
      finally
      {
        if (localDataOutputStream == null) {
          break label128;
        }
      }
      try
      {
        localDataOutputStream.close();
        this.mData = localByteArrayOutputStream.toByteArray();
        this.mResponse = this.mHandler.obtainMessage(204);
        invokeOemRilRequestRaw(localByteArrayOutputStream.toByteArray(), this.mResponse);
        return;
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
      }
    }
    try
    {
      localDataOutputStream.close();
      label128:
      throw ((Throwable)localObject);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        localException1.printStackTrace();
      }
    }
  }
  
  public void requestGripSensorOn(boolean paramBoolean)
  {
    FtUtil.log_i(this.CLASS_NAME, "requestGripSensorOn", "sendToRilGripSensorStart()");
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    byte[] arrayOfByte;
    if (paramBoolean)
    {
      arrayOfByte = new byte[4];
      arrayOfByte[0] = 2;
      arrayOfByte[1] = 0;
      arrayOfByte[2] = 11;
      arrayOfByte[3] = 1;
    }
    for (;;)
    {
      try
      {
        localDataOutputStream.writeByte(18);
        localDataOutputStream.writeByte(9);
        localDataOutputStream.writeShort(8);
        localDataOutputStream.write(arrayOfByte);
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        if (localDataOutputStream == null) {
          continue;
        }
        try
        {
          localDataOutputStream.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        continue;
      }
      finally
      {
        if (localDataOutputStream == null) {
          break label214;
        }
      }
      try
      {
        localDataOutputStream.close();
        this.mData = localByteArrayOutputStream.toByteArray();
        this.mResponse = this.mHandler.obtainMessage(205);
        invokeOemRilRequestRaw(localByteArrayOutputStream.toByteArray(), this.mResponse);
        return;
        arrayOfByte = new byte[] { 2, 0, 11, 0 };
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
      }
    }
    try
    {
      localDataOutputStream.close();
      label214:
      throw ((Throwable)localObject);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        localException1.printStackTrace();
      }
    }
  }
  
  public void requestHistoryNvViewToRil()
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    for (;;)
    {
      try
      {
        localDataOutputStream.writeByte(18);
        localDataOutputStream.writeByte(3);
        localDataOutputStream.writeShort(7);
        localDataOutputStream.writeByte(1);
        localDataOutputStream.writeByte(0);
        localDataOutputStream.writeByte(3);
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        if (localDataOutputStream == null) {
          continue;
        }
        try
        {
          localDataOutputStream.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        continue;
      }
      finally
      {
        if (localDataOutputStream == null) {
          break label139;
        }
      }
      try
      {
        localDataOutputStream.close();
        this.mData = localByteArrayOutputStream.toByteArray();
        this.mResponse = this.mHandler.obtainMessage(201);
        invokeOemRilRequestRaw(localByteArrayOutputStream.toByteArray(), this.mResponse);
        return;
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
      }
    }
    try
    {
      localDataOutputStream.close();
      label139:
      throw ((Throwable)localObject);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        localException1.printStackTrace();
      }
    }
  }
  
  public void requestTestNvViewToRil()
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    for (;;)
    {
      try
      {
        localDataOutputStream.writeByte(18);
        localDataOutputStream.writeByte(3);
        localDataOutputStream.writeShort(7);
        localDataOutputStream.writeByte(1);
        localDataOutputStream.writeByte(0);
        localDataOutputStream.writeByte(2);
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        if (localDataOutputStream == null) {
          continue;
        }
        try
        {
          localDataOutputStream.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        continue;
      }
      finally
      {
        if (localDataOutputStream == null) {
          break label139;
        }
      }
      try
      {
        localDataOutputStream.close();
        this.mData = localByteArrayOutputStream.toByteArray();
        this.mResponse = this.mHandler.obtainMessage(200);
        invokeOemRilRequestRaw(localByteArrayOutputStream.toByteArray(), this.mResponse);
        return;
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
      }
    }
    try
    {
      localDataOutputStream.close();
      label139:
      throw ((Throwable)localObject);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        localException1.printStackTrace();
      }
    }
  }
  
  public void sendLteRilCommand_POWER(String paramString)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    int i = -1;
    int j = -1;
    try
    {
      String str = "" + paramString.charAt(0);
      i = (byte)Integer.parseInt(str + paramString.charAt(1));
      int k = Integer.parseInt("" + paramString.charAt(3));
      j = (byte)k;
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        try
        {
          localDataOutputStream.writeByte(118);
          localDataOutputStream.writeByte(4);
          localDataOutputStream.writeShort(10);
          localDataOutputStream.writeByte(4);
          localDataOutputStream.writeByte(0);
          localDataOutputStream.writeByte(83);
          localDataOutputStream.writeByte(0);
          localDataOutputStream.writeByte(i);
          localDataOutputStream.writeByte(j);
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
          if (localDataOutputStream == null) {
            continue;
          }
          try
          {
            localDataOutputStream.close();
          }
          catch (Exception localException3)
          {
            localException3.printStackTrace();
          }
          continue;
        }
        finally
        {
          if (localDataOutputStream == null) {
            break label335;
          }
        }
        try
        {
          localDataOutputStream.close();
          this.mData = localByteArrayOutputStream.toByteArray();
          this.mResponse = this.mHandler.obtainMessage(206);
          invokeOemRilRequestRaw(localByteArrayOutputStream.toByteArray(), this.mResponse);
          return;
          localException1 = localException1;
          FtUtil.log_e(this.CLASS_NAME, "sendLteRilCommand_POWER", "Array Index error!!");
        }
        catch (Exception localException4)
        {
          localException4.printStackTrace();
        }
      }
    }
    FtUtil.log_i(this.CLASS_NAME, "sendLteRilCommand_POWER", "sendLteRilCommand_POWER - arg[" + paramString + "] , " + "RFband[" + i + "] , Power[" + j + "]");
    try
    {
      localDataOutputStream.close();
      label335:
      throw ((Throwable)localObject);
    }
    catch (Exception localException2)
    {
      for (;;)
      {
        localException2.printStackTrace();
      }
    }
  }
  
  public void setResultForPGMItems(String paramString, boolean paramBoolean)
  {
    switch (Integer.parseInt(paramString, 16))
    {
    case 2: 
    case 3: 
    case 5: 
    case 6: 
    case 9: 
    default: 
      return;
    case 1: 
      this.IS_SMD_TEST_PASS = paramBoolean;
      return;
    case 4: 
      this.IS_PBA_TEST_PASS = paramBoolean;
      return;
    case 7: 
      this.IS_CAL_TEST_PASS = paramBoolean;
      return;
    case 8: 
      this.IS_CAL2_TEST_PASS = paramBoolean;
      return;
    case 10: 
      this.IS_FINAL_TEST_PASS = paramBoolean;
      return;
    case 11: 
      this.IS_LTECAL_TEST_PASS = paramBoolean;
      return;
    }
    this.IS_LTEFINAL_TEST_PASS = paramBoolean;
  }
  
  public void unbindSecPhoneService()
  {
    FtUtil.log_i(this.CLASS_NAME, "unbindSecPhoneService", null);
    try
    {
      this.mContext.unbindService(this.mSecPhoneServiceConnection);
      this.isConnected = false;
      return;
    }
    catch (Exception localException) {}
  }
  
  public void updateNVItem(byte paramByte1, byte paramByte2)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    for (;;)
    {
      try
      {
        localDataOutputStream.writeByte(18);
        localDataOutputStream.writeByte(4);
        localDataOutputStream.writeShort(9);
        localDataOutputStream.writeByte(3);
        localDataOutputStream.writeByte(0);
        localDataOutputStream.writeByte(1);
        localDataOutputStream.writeByte(paramByte1);
        localDataOutputStream.writeByte(paramByte2);
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
        if (localDataOutputStream == null) {
          continue;
        }
        try
        {
          localDataOutputStream.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        continue;
      }
      finally
      {
        if (localDataOutputStream == null) {
          break label165;
        }
      }
      try
      {
        localDataOutputStream.close();
        this.mData = localByteArrayOutputStream.toByteArray();
        this.mResponse = this.mHandler.obtainMessage(202);
        invokeOemRilRequestRaw(localByteArrayOutputStream.toByteArray(), this.mResponse);
        return;
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
      }
    }
    try
    {
      localDataOutputStream.close();
      label165:
      throw ((Throwable)localObject);
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        localException1.printStackTrace();
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestPhone
 * JD-Core Version:    0.7.1
 */