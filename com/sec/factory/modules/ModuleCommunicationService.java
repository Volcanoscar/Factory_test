package com.sec.factory.modules;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemProperties;
import com.sec.factory.support.FtUtil;

public class ModuleCommunicationService
  extends Service
{
  private static int iDelayCount = 0;
  private static int mNFCTurnningOnCount = 0;
  private static NfcAdapter mNfcAdapter;
  private final int BT_DISCOVERY_TIMEOUT = 10000;
  private final int BT_LE_DISCOVERY_TIMEOUT = 20000;
  private final int BT_LE_DISCOVERY_TIMEOUT_ON_10S = 10000;
  private final byte MSG_BT_DISCOVERY_CANCEL_TIMEOUT = 0;
  private final byte MSG_GPS_READ = 12;
  private final byte MSG_GPS_START = 10;
  private final byte MSG_GPS_START_DELAY = 13;
  private final byte MSG_GPS_STOP = 11;
  private final byte NFC_ENABLE_DELAY = 14;
  private BroadcastReceiver mBtReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      if ("android.bluetooth.device.action.FOUND".equals(str))
      {
        FtUtil.log_i("ModuleCommunicationService", "mReceiver.onReceive", "BluetoothDevice.ACTION_FOUND");
        ModuleCommunicationService.this.bluetoothResponse("FOUND");
        ModuleCommunicationService.this.bluetoothDiscoveryStop();
      }
      do
      {
        int j;
        do
        {
          return;
          if (!"android.bluetooth.adapter.action.STATE_CHANGED".equals(str)) {
            break;
          }
          j = paramAnonymousIntent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
          FtUtil.log_i("ModuleCommunicationService", "mReceiver.onReceive", "BluetoothDevice.ACTTION_STATE_CHANGED, state=" + j);
          if (j == 12)
          {
            FtUtil.log_i("ModuleCommunicationService", "mReceiver.onReceive", "BT STATE = ON");
            FtUtil.log_i("ModuleCommunicationService", "mReceiver.onReceive", "Give Delay before setting discoverable");
            try
            {
              Thread.sleep(500L);
              FtUtil.log_i("ModuleCommunicationService", "mReceiver.onReceive", "Setting device to discoverable mode: timeout: " + ModuleCommunicationService.this.mDiscoverableTime);
              BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
              localBluetoothAdapter.setDiscoverableTimeout(ModuleCommunicationService.this.mDiscoverableTime);
              localBluetoothAdapter.setScanMode(23, 0);
              ModuleCommunicationService.this.bluetoothResponse("ON");
              return;
            }
            catch (InterruptedException localInterruptedException)
            {
              for (;;)
              {
                localInterruptedException.printStackTrace();
              }
            }
          }
          if (j == 10)
          {
            FtUtil.log_i("ModuleCommunicationService", "mReceiver.onReceive", "BT STATE = OFF");
            ModuleCommunicationService.this.bluetoothResponse("OFF");
            return;
          }
        } while (j != Integer.MIN_VALUE);
        ModuleCommunicationService.this.bluetoothResponse("NG");
        return;
        if ("android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(str))
        {
          FtUtil.log_i("ModuleCommunicationService", "mReceiver.onReceive", "ACTION_DISCOVERY_FINISHED");
          if ((ModuleCommunicationService.this.mIsBleSearchCount > 0) && (ModuleCommunicationService.this.mIsBleSearchCount <= 9))
          {
            FtUtil.log_i("ModuleCommunicationService", "mReceiver.onReceive", "IS BT LE SEARCH");
            ModuleCommunicationService.access$308(ModuleCommunicationService.this);
            ModuleCommunicationService.access$402(ModuleCommunicationService.this, 10000);
            BluetoothAdapter.getDefaultAdapter().startLeDiscovery();
            return;
          }
          FtUtil.log_i("ModuleCommunicationService", "mReceiver.onReceive", "IS NOT BT LE SEARCH");
          ModuleCommunicationService.this.bluetoothDiscoveryStop();
          ModuleCommunicationService.this.bluetoothResponse("NOT FOUND");
          return;
        }
      } while (!"android.bluetooth.adapter.action.LE_TESE_END_COMPLETED".equals(str));
      int i = paramAnonymousIntent.getIntExtra("android.bluetooth.adapter.extra.LE_PACKET_COUNTS", Integer.MIN_VALUE);
      FtUtil.log_i("ModuleCommunicationService", "mReceiver.onReceive", "LE Packet Counts");
      ModuleCommunicationService.this.bluetoothResponse(Integer.toString(i));
    }
  };
  private String mData;
  private int mDiscoverableTime = 0;
  private int mGpsRetryCount = 0;
  private Handler mHandler = new Handler()
  {
    /* Error */
    public void handleMessage(android.os.Message paramAnonymousMessage)
    {
      // Byte code:
      //   0: aload_1
      //   1: getfield 27	android/os/Message:what	I
      //   4: lookupswitch	default:+52->56, 0:+53->57, 10:+120->124, 12:+239->243, 13:+191->195, 14:+655->659
      //   57: ldc 29
      //   59: ldc 31
      //   61: new 33	java/lang/StringBuilder
      //   64: dup
      //   65: invokespecial 34	java/lang/StringBuilder:<init>	()V
      //   68: ldc 36
      //   70: invokevirtual 40	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   73: aload_0
      //   74: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   77: invokestatic 44	com/sec/factory/modules/ModuleCommunicationService:access$400	(Lcom/sec/factory/modules/ModuleCommunicationService;)I
      //   80: invokevirtual 47	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   83: invokevirtual 51	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   86: invokestatic 57	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   89: ldc 29
      //   91: ldc 31
      //   93: ldc 59
      //   95: invokestatic 57	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   98: invokestatic 65	android/bluetooth/BluetoothAdapter:getDefaultAdapter	()Landroid/bluetooth/BluetoothAdapter;
      //   101: invokevirtual 69	android/bluetooth/BluetoothAdapter:isDiscovering	()Z
      //   104: ifeq +10 -> 114
      //   107: aload_0
      //   108: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   111: invokestatic 72	com/sec/factory/modules/ModuleCommunicationService:access$100	(Lcom/sec/factory/modules/ModuleCommunicationService;)V
      //   114: aload_0
      //   115: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   118: ldc 74
      //   120: invokestatic 78	com/sec/factory/modules/ModuleCommunicationService:access$000	(Lcom/sec/factory/modules/ModuleCommunicationService;Ljava/lang/String;)V
      //   123: return
      //   124: ldc 29
      //   126: ldc 31
      //   128: ldc 80
      //   130: invokestatic 57	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   133: aload_0
      //   134: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   137: invokestatic 84	com/sec/factory/modules/ModuleCommunicationService:access$600	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/location/LocationManager;
      //   140: ldc 86
      //   142: ldc2_w 87
      //   145: fconst_0
      //   146: aload_0
      //   147: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   150: invokestatic 92	com/sec/factory/modules/ModuleCommunicationService:access$500	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/location/LocationListener;
      //   153: invokevirtual 98	android/location/LocationManager:requestLocationUpdates	(Ljava/lang/String;JFLandroid/location/LocationListener;)V
      //   156: aload_0
      //   157: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   160: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   163: bipush 12
      //   165: invokevirtual 106	android/os/Handler:removeMessages	(I)V
      //   168: aload_0
      //   169: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   172: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   175: aload_0
      //   176: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   179: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   182: bipush 12
      //   184: invokevirtual 110	android/os/Handler:obtainMessage	(I)Landroid/os/Message;
      //   187: ldc2_w 87
      //   190: invokevirtual 114	android/os/Handler:sendMessageDelayed	(Landroid/os/Message;J)Z
      //   193: pop
      //   194: return
      //   195: ldc 29
      //   197: ldc 31
      //   199: ldc 116
      //   201: invokestatic 57	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   204: aload_0
      //   205: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   208: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   211: bipush 13
      //   213: invokevirtual 106	android/os/Handler:removeMessages	(I)V
      //   216: aload_0
      //   217: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   220: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   223: aload_0
      //   224: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   227: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   230: bipush 10
      //   232: invokevirtual 110	android/os/Handler:obtainMessage	(I)Landroid/os/Message;
      //   235: ldc2_w 117
      //   238: invokevirtual 114	android/os/Handler:sendMessageDelayed	(Landroid/os/Message;J)Z
      //   241: pop
      //   242: return
      //   243: ldc 29
      //   245: ldc 31
      //   247: new 33	java/lang/StringBuilder
      //   250: dup
      //   251: invokespecial 34	java/lang/StringBuilder:<init>	()V
      //   254: ldc 120
      //   256: invokevirtual 40	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   259: aload_0
      //   260: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   263: invokestatic 123	com/sec/factory/modules/ModuleCommunicationService:access$800	(Lcom/sec/factory/modules/ModuleCommunicationService;)I
      //   266: invokevirtual 47	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   269: invokevirtual 51	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   272: invokestatic 57	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   275: aconst_null
      //   276: astore_2
      //   277: aload_0
      //   278: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   281: invokestatic 123	com/sec/factory/modules/ModuleCommunicationService:access$800	(Lcom/sec/factory/modules/ModuleCommunicationService;)I
      //   284: bipush 15
      //   286: if_icmple +63 -> 349
      //   289: aload_0
      //   290: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   293: ldc 125
      //   295: invokestatic 128	com/sec/factory/modules/ModuleCommunicationService:access$900	(Lcom/sec/factory/modules/ModuleCommunicationService;Ljava/lang/String;)V
      //   298: ldc 29
      //   300: ldc 31
      //   302: ldc 130
      //   304: invokestatic 57	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   307: aload_0
      //   308: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   311: iconst_0
      //   312: invokestatic 134	com/sec/factory/modules/ModuleCommunicationService:access$802	(Lcom/sec/factory/modules/ModuleCommunicationService;I)I
      //   315: pop
      //   316: aload_0
      //   317: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   320: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   323: bipush 12
      //   325: invokevirtual 106	android/os/Handler:removeMessages	(I)V
      //   328: aload_0
      //   329: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   332: invokestatic 84	com/sec/factory/modules/ModuleCommunicationService:access$600	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/location/LocationManager;
      //   335: aload_0
      //   336: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   339: invokestatic 92	com/sec/factory/modules/ModuleCommunicationService:access$500	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/location/LocationListener;
      //   342: invokevirtual 138	android/location/LocationManager:removeUpdates	(Landroid/location/LocationListener;)V
      //   345: invokestatic 143	com/sec/factory/modules/ModuleCommunication:deleteGpsFiles	()V
      //   348: return
      //   349: new 145	java/io/BufferedReader
      //   352: dup
      //   353: new 147	java/io/FileReader
      //   356: dup
      //   357: ldc 149
      //   359: invokespecial 152	java/io/FileReader:<init>	(Ljava/lang/String;)V
      //   362: sipush 8096
      //   365: invokespecial 155	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
      //   368: astore_3
      //   369: aload_0
      //   370: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   373: aload_3
      //   374: invokevirtual 158	java/io/BufferedReader:readLine	()Ljava/lang/String;
      //   377: invokestatic 162	com/sec/factory/modules/ModuleCommunicationService:access$1002	(Lcom/sec/factory/modules/ModuleCommunicationService;Ljava/lang/String;)Ljava/lang/String;
      //   380: pop
      //   381: aload_0
      //   382: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   385: invokestatic 166	com/sec/factory/modules/ModuleCommunicationService:access$1000	(Lcom/sec/factory/modules/ModuleCommunicationService;)Ljava/lang/String;
      //   388: ifnonnull +50 -> 438
      //   391: aload_0
      //   392: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   395: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   398: bipush 12
      //   400: invokevirtual 106	android/os/Handler:removeMessages	(I)V
      //   403: aload_0
      //   404: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   407: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   410: aload_0
      //   411: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   414: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   417: bipush 12
      //   419: invokevirtual 110	android/os/Handler:obtainMessage	(I)Landroid/os/Message;
      //   422: ldc2_w 167
      //   425: invokevirtual 114	android/os/Handler:sendMessageDelayed	(Landroid/os/Message;J)Z
      //   428: pop
      //   429: aload_3
      //   430: ifnull +267 -> 697
      //   433: aload_3
      //   434: invokevirtual 171	java/io/BufferedReader:close	()V
      //   437: return
      //   438: ldc 29
      //   440: ldc 31
      //   442: new 33	java/lang/StringBuilder
      //   445: dup
      //   446: invokespecial 34	java/lang/StringBuilder:<init>	()V
      //   449: ldc 173
      //   451: invokevirtual 40	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   454: aload_0
      //   455: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   458: invokestatic 166	com/sec/factory/modules/ModuleCommunicationService:access$1000	(Lcom/sec/factory/modules/ModuleCommunicationService;)Ljava/lang/String;
      //   461: invokevirtual 40	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   464: invokevirtual 51	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   467: invokestatic 57	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   470: aload_0
      //   471: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   474: aload_0
      //   475: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   478: invokestatic 166	com/sec/factory/modules/ModuleCommunicationService:access$1000	(Lcom/sec/factory/modules/ModuleCommunicationService;)Ljava/lang/String;
      //   481: invokestatic 128	com/sec/factory/modules/ModuleCommunicationService:access$900	(Lcom/sec/factory/modules/ModuleCommunicationService;Ljava/lang/String;)V
      //   484: goto -55 -> 429
      //   487: astore 10
      //   489: aload_3
      //   490: astore_2
      //   491: ldc 29
      //   493: ldc 31
      //   495: ldc 175
      //   497: invokestatic 57	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   500: aload_0
      //   501: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   504: invokestatic 178	com/sec/factory/modules/ModuleCommunicationService:access$808	(Lcom/sec/factory/modules/ModuleCommunicationService;)I
      //   507: pop
      //   508: aload_0
      //   509: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   512: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   515: bipush 12
      //   517: invokevirtual 106	android/os/Handler:removeMessages	(I)V
      //   520: aload_0
      //   521: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   524: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   527: aload_0
      //   528: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   531: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   534: bipush 12
      //   536: invokevirtual 110	android/os/Handler:obtainMessage	(I)Landroid/os/Message;
      //   539: ldc2_w 167
      //   542: invokevirtual 114	android/os/Handler:sendMessageDelayed	(Landroid/os/Message;J)Z
      //   545: pop
      //   546: aload_2
      //   547: ifnull -491 -> 56
      //   550: aload_2
      //   551: invokevirtual 171	java/io/BufferedReader:close	()V
      //   554: return
      //   555: astore 13
      //   557: aload 13
      //   559: invokevirtual 181	java/io/IOException:printStackTrace	()V
      //   562: return
      //   563: astore 15
      //   565: aload 15
      //   567: invokevirtual 181	java/io/IOException:printStackTrace	()V
      //   570: return
      //   571: astore 18
      //   573: aload_0
      //   574: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   577: invokestatic 178	com/sec/factory/modules/ModuleCommunicationService:access$808	(Lcom/sec/factory/modules/ModuleCommunicationService;)I
      //   580: pop
      //   581: aload_0
      //   582: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   585: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   588: bipush 12
      //   590: invokevirtual 106	android/os/Handler:removeMessages	(I)V
      //   593: aload_0
      //   594: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   597: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   600: aload_0
      //   601: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   604: invokestatic 102	com/sec/factory/modules/ModuleCommunicationService:access$700	(Lcom/sec/factory/modules/ModuleCommunicationService;)Landroid/os/Handler;
      //   607: bipush 12
      //   609: invokevirtual 110	android/os/Handler:obtainMessage	(I)Landroid/os/Message;
      //   612: ldc2_w 167
      //   615: invokevirtual 114	android/os/Handler:sendMessageDelayed	(Landroid/os/Message;J)Z
      //   618: pop
      //   619: aload_2
      //   620: ifnull -564 -> 56
      //   623: aload_2
      //   624: invokevirtual 171	java/io/BufferedReader:close	()V
      //   627: return
      //   628: astore 9
      //   630: aload 9
      //   632: invokevirtual 181	java/io/IOException:printStackTrace	()V
      //   635: return
      //   636: astore 5
      //   638: aload_2
      //   639: ifnull +7 -> 646
      //   642: aload_2
      //   643: invokevirtual 171	java/io/BufferedReader:close	()V
      //   646: aload 5
      //   648: athrow
      //   649: astore 6
      //   651: aload 6
      //   653: invokevirtual 181	java/io/IOException:printStackTrace	()V
      //   656: goto -10 -> 646
      //   659: ldc 29
      //   661: ldc 31
      //   663: ldc 183
      //   665: invokestatic 57	com/sec/factory/support/FtUtil:log_i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   668: aload_0
      //   669: getfield 12	com/sec/factory/modules/ModuleCommunicationService$2:this$0	Lcom/sec/factory/modules/ModuleCommunicationService;
      //   672: invokestatic 186	com/sec/factory/modules/ModuleCommunicationService:access$1100	(Lcom/sec/factory/modules/ModuleCommunicationService;)V
      //   675: return
      //   676: astore 5
      //   678: aload_3
      //   679: astore_2
      //   680: goto -42 -> 638
      //   683: astore 4
      //   685: aload_3
      //   686: astore_2
      //   687: goto -114 -> 573
      //   690: astore 17
      //   692: aconst_null
      //   693: astore_2
      //   694: goto -203 -> 491
      //   697: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	698	0	this	2
      //   0	698	1	paramAnonymousMessage	android.os.Message
      //   276	418	2	localObject1	Object
      //   368	318	3	localBufferedReader	java.io.BufferedReader
      //   683	1	4	localIOException1	java.io.IOException
      //   636	11	5	localObject2	Object
      //   676	1	5	localObject3	Object
      //   649	3	6	localIOException2	java.io.IOException
      //   628	3	9	localIOException3	java.io.IOException
      //   487	1	10	localFileNotFoundException1	java.io.FileNotFoundException
      //   555	3	13	localIOException4	java.io.IOException
      //   563	3	15	localIOException5	java.io.IOException
      //   690	1	17	localFileNotFoundException2	java.io.FileNotFoundException
      //   571	1	18	localIOException6	java.io.IOException
      // Exception table:
      //   from	to	target	type
      //   369	429	487	java/io/FileNotFoundException
      //   438	484	487	java/io/FileNotFoundException
      //   550	554	555	java/io/IOException
      //   433	437	563	java/io/IOException
      //   349	369	571	java/io/IOException
      //   623	627	628	java/io/IOException
      //   349	369	636	finally
      //   491	546	636	finally
      //   573	619	636	finally
      //   642	646	649	java/io/IOException
      //   369	429	676	finally
      //   438	484	676	finally
      //   369	429	683	java/io/IOException
      //   438	484	683	java/io/IOException
      //   349	369	690	java/io/FileNotFoundException
    }
  };
  private boolean mIsBleSearch = false;
  private int mIsBleSearchCount = -1;
  private boolean mIsBtReceiverRegistered = false;
  private LocationListener mLocationListener = new LocationListener()
  {
    public void onLocationChanged(Location paramAnonymousLocation) {}
    
    public void onProviderDisabled(String paramAnonymousString) {}
    
    public void onProviderEnabled(String paramAnonymousString) {}
    
    public void onStatusChanged(String paramAnonymousString, int paramAnonymousInt, Bundle paramAnonymousBundle) {}
  };
  private LocationManager mLocationManager = null;
  private int mTimeout = 0;
  
  private void bluetoothAudioTestStart()
  {
    Intent localIntent = new Intent();
    localIntent.setClassName("com.android.bluetoothtest", "com.android.bluetoothtest.BluetoothAudioTest");
    localIntent.setFlags(268435456);
    startActivity(localIntent);
  }
  
  private void bluetoothAudioTestStop()
  {
    sendBroadcast(new Intent("com.android.bluetoothtest.bluetoothAudioTestStop"));
  }
  
  private void bluetoothBleDiscoveryStart(boolean paramBoolean)
  {
    BluetoothAdapter.getDefaultAdapter().startLeDiscovery();
    this.mIsBleSearch = true;
    this.mIsBleSearchCount = 1;
    this.mTimeout = 20000;
    if (paramBoolean == true) {
      this.mHandler.sendEmptyMessageDelayed(0, this.mTimeout);
    }
  }
  
  private void bluetoothBleDiscoveryStartOn10s(boolean paramBoolean)
  {
    BluetoothAdapter.getDefaultAdapter().startLeDiscovery();
    this.mTimeout = 10000;
    if (paramBoolean == true) {
      this.mHandler.sendEmptyMessageDelayed(0, this.mTimeout);
    }
  }
  
  private void bluetoothDiscoveryStart(boolean paramBoolean)
  {
    BluetoothAdapter.getDefaultAdapter().startDiscovery();
    if (paramBoolean == true) {
      this.mHandler.sendEmptyMessageDelayed(0, 10000L);
    }
  }
  
  private void bluetoothDiscoveryStop()
  {
    BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    this.mHandler.removeMessages(0);
    this.mIsBleSearchCount = -1;
    this.mIsBleSearch = false;
    if (localBluetoothAdapter.isDiscovering()) {
      localBluetoothAdapter.cancelDiscovery();
    }
  }
  
  private void bluetoothLeDirectTestEnd()
  {
    BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if ((localBluetoothAdapter != null) && (localBluetoothAdapter.isEnabled()))
    {
      FtUtil.log_i("ModuleCommunicationService", "bluetoothLeDirectTestEnd", "bluetoothLeDirectTestEnd()");
      localBluetoothAdapter.leTestEnd();
      return;
    }
    FtUtil.log_i("ModuleCommunicationService", "bluetoothLeDirectTestEnd", "BtAdapter is null or not enabled.");
  }
  
  private void bluetoothLeRxTest(int paramInt)
  {
    BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if ((localBluetoothAdapter != null) && (localBluetoothAdapter.isEnabled()))
    {
      FtUtil.log_i("ModuleCommunicationService", "bluetoothLeRxTest", "bluetoothLeRxTest()");
      switch (paramInt)
      {
      default: 
        return;
      case 0: 
        SystemProperties.set("ctl.start", "LE_rx_ready_low");
        return;
      case 1: 
        SystemProperties.set("ctl.start", "LE_rx_ready_mid");
        return;
      }
      SystemProperties.set("ctl.start", "LE_rx_ready_max");
      return;
    }
    FtUtil.log_i("ModuleCommunicationService", "bluetoothLeRxTest", "BtAdapter is null or not enabled.");
  }
  
  private void bluetoothLeTxTest(int paramInt1, int paramInt2)
  {
    BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if ((localBluetoothAdapter != null) && (localBluetoothAdapter.isEnabled()))
    {
      FtUtil.log_i("ModuleCommunicationService", "bluetoothLeTxTest", "bluetoothLeTxTest()");
      switch (paramInt2)
      {
      default: 
        return;
      case 0: 
        switch (paramInt1)
        {
        default: 
          return;
        case 0: 
          SystemProperties.set("ctl.start", "LE_tx_low");
          return;
        case 1: 
          SystemProperties.set("ctl.start", "LE_tx_M_low");
          return;
        }
        SystemProperties.set("ctl.start", "LE_tx_M_1_low");
        return;
      case 1: 
        switch (paramInt1)
        {
        default: 
          return;
        case 0: 
          SystemProperties.set("ctl.start", "LE_tx_mid");
          return;
        case 1: 
          SystemProperties.set("ctl.start", "LE_tx_M_mid");
          return;
        }
        SystemProperties.set("ctl.start", "LE_tx_M_1_mid");
        return;
      case 2: 
        switch (paramInt1)
        {
        default: 
          return;
        case 0: 
          SystemProperties.set("ctl.start", "LE_tx_max");
          return;
        case 1: 
          SystemProperties.set("ctl.start", "LE_tx_M_max");
          return;
        }
        SystemProperties.set("ctl.start", "LE_tx_M_1_max");
        return;
      case 3: 
        SystemProperties.set("ctl.start", "LE_tx_I_low");
        return;
      }
      SystemProperties.set("ctl.start", "LE_tx_I_max");
      return;
    }
    FtUtil.log_i("ModuleCommunicationService", "bluetoothLeTxTest", "BtAdapter is null or not enabled.");
  }
  
  private void bluetoothOff()
  {
    BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (localBluetoothAdapter.isEnabled())
    {
      this.mHandler.removeMessages(0);
      localBluetoothAdapter.disable();
      return;
    }
    bluetoothResponse("OFF");
  }
  
  private void bluetoothOn()
  {
    BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (!localBluetoothAdapter.isEnabled())
    {
      localBluetoothAdapter.enable();
      return;
    }
    bluetoothResponse("ON");
  }
  
  private void bluetoothResponse(String paramString)
  {
    Intent localIntent = new Intent("com.sec.factory.intent.ACTION_BT_SERVICE_RESPONSE");
    localIntent.putExtra("result", paramString);
    sendBroadcast(localIntent);
    stopBtReceiver();
  }
  
  private void bluetoothSetDiscoverable()
  {
    BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if ((localBluetoothAdapter != null) && (localBluetoothAdapter.isEnabled()))
    {
      FtUtil.log_i("ModuleCommunicationService", "bluetoothSetDiscoverable", "setDiscoverableTimeout(0)");
      localBluetoothAdapter.setDiscoverableTimeout(0);
      localBluetoothAdapter.setScanMode(23, 0);
      return;
    }
    FtUtil.log_i("ModuleCommunicationService", "bluetoothSetDiscoverable", "BtAdapter is null or not enabled.");
  }
  
  private void gpsResponse(String paramString)
  {
    FtUtil.log_i("ModuleCommunicationService", "gpsResponse", "result  = " + paramString);
    Intent localIntent = new Intent("com.sec.factory.intent.ACTION_GPS_SERVICE_RESPONSE");
    localIntent.putExtra("result", paramString);
    sendBroadcast(localIntent);
  }
  
  public static boolean isEnabledBtDevice()
  {
    return BluetoothAdapter.getDefaultAdapter().isEnabled();
  }
  
  private void nfcDisable()
  {
    FtUtil.log_i("ModuleCommunicationService", "nfcDisable", "...");
    mNFCTurnningOnCount = -1 + mNFCTurnningOnCount;
    if (mNFCTurnningOnCount > 0)
    {
      FtUtil.log_i("ModuleCommunicationService", "nfcDisable", "mNFCTurnningOnCount = " + mNFCTurnningOnCount);
      return;
    }
    mNfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
    if ((mNfcAdapter != null) && (mNfcAdapter.isEnabled()))
    {
      new Thread()
      {
        public void run()
        {
          if (ModuleCommunicationService.mNfcAdapter.disable())
          {
            FtUtil.log_i("ModuleCommunicationService", "nfcDisable", "NFC off success!");
            return;
          }
          FtUtil.log_w("ModuleCommunicationService", "nfcDisable", "NFC off fail!");
        }
      }.start();
      return;
    }
    FtUtil.log_i("ModuleCommunicationService", "nfcDisable", "NFC state is already off.");
  }
  
  private void nfcEnable()
  {
    FtUtil.log_i("ModuleCommunicationService", "nfcEnable", "...");
    mNFCTurnningOnCount = 1 + mNFCTurnningOnCount;
    mNfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
    if ((mNfcAdapter != null) && (!mNfcAdapter.isEnabled()))
    {
      FtUtil.log_i("ModuleCommunicationService", "nfcEnable", "NFC is not enabled.");
      sendBroadcast(new Intent("com.sec.android.app.nfctest.NFC_ON_NO_DISCOVERY"));
    }
    try
    {
      Thread.sleep(100L);
      label75:
      new Thread()
      {
        public void run()
        {
          if (ModuleCommunicationService.mNfcAdapter.enable())
          {
            ModuleCommunicationService.access$1302(0);
            ModuleCommunicationService.this.sendBroadcast(new Intent("com.sec.android.app.nfctest.NFC_DISCOVERY_DISABLE"));
            FtUtil.log_i("ModuleCommunicationService", "nfcEnable", "NFC is turned on!");
            return;
          }
          FtUtil.log_w("ModuleCommunicationService", "nfcEnable", "NFC is not turned on!");
        }
      }.start();
      return;
      mNFCTurnningOnCount = -1 + mNFCTurnningOnCount;
      iDelayCount = 1 + iDelayCount;
      if (iDelayCount > 5)
      {
        iDelayCount = 0;
        sendBroadcast(new Intent("com.sec.android.app.nfctest.NFC_DISCOVERY_DISABLE"));
        FtUtil.log_i("ModuleCommunicationService", "nfcEnable", "NFC is enabled.");
        return;
      }
      this.mHandler.sendEmptyMessageDelayed(14, 1000L);
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label75;
    }
  }
  
  private void startBtReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.bluetooth.device.action.FOUND");
    localIntentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
    localIntentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
    localIntentFilter.addAction("android.bluetooth.adapter.action.LE_TESE_END_COMPLETED");
    registerReceiver(this.mBtReceiver, localIntentFilter);
    this.mIsBtReceiverRegistered = true;
  }
  
  private void stopBtReceiver()
  {
    if (this.mIsBtReceiverRegistered)
    {
      unregisterReceiver(this.mBtReceiver);
      this.mIsBtReceiverRegistered = false;
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    mNfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
    this.mLocationManager = ((LocationManager)getSystemService("location"));
  }
  
  public void onDestroy()
  {
    stopBtReceiver();
    this.mLocationManager.removeUpdates(this.mLocationListener);
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    String str = paramIntent.getStringExtra("action");
    if (str != null)
    {
      FtUtil.log_d("ModuleCommunicationService", "onStartCommand", "action=" + str);
      if (!str.equals("ACTION_BLUETOOTH_ON")) {
        break label69;
      }
      this.mDiscoverableTime = 0;
      startBtReceiver();
      bluetoothOn();
    }
    label69:
    do
    {
      return 2;
      if (str.equals("ACTION_BLUETOOTH_ON_WHERE_ATCMD"))
      {
        this.mDiscoverableTime = 120;
        startBtReceiver();
        bluetoothOn();
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_OFF"))
      {
        startBtReceiver();
        bluetoothOff();
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_DISCOVERY_START"))
      {
        startBtReceiver();
        bluetoothDiscoveryStart(false);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_DISCOVERY_START_WITH_ACK"))
      {
        startBtReceiver();
        bluetoothDiscoveryStart(true);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_BLE_DISCOVERY_START_WITH_ACK"))
      {
        startBtReceiver();
        bluetoothBleDiscoveryStart(false);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_BLE_DISCOVERY_START_WITH_ACK_ON_10S"))
      {
        startBtReceiver();
        bluetoothBleDiscoveryStartOn10s(true);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_DISCOVERY_STOP"))
      {
        startBtReceiver();
        bluetoothDiscoveryStop();
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_AUDIO_TEST_START"))
      {
        startBtReceiver();
        bluetoothAudioTestStart();
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_AUDIO_TEST_STOP"))
      {
        startBtReceiver();
        bluetoothAudioTestStop();
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_SET_DISCOVERABLE"))
      {
        bluetoothSetDiscoverable();
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_OUTPUT_LOW"))
      {
        bluetoothLeTxTest(0, 0);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_OUTPUT_MID"))
      {
        bluetoothLeTxTest(0, 1);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_OUTPUT_HIGH"))
      {
        bluetoothLeTxTest(0, 2);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_MOD_LOW"))
      {
        bluetoothLeTxTest(1, 0);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_MOD_MID"))
      {
        bluetoothLeTxTest(1, 1);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_MOD_HIGH"))
      {
        bluetoothLeTxTest(1, 2);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_MOD_LOW_AA"))
      {
        bluetoothLeTxTest(2, 0);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_MOD_MID_AA"))
      {
        bluetoothLeTxTest(2, 1);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_MOD_HIGH_AA"))
      {
        bluetoothLeTxTest(2, 2);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_CARRIER_LOW"))
      {
        bluetoothLeTxTest(2, 0);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_CARRIER_MID"))
      {
        bluetoothLeTxTest(2, 1);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_CARRIER_HIGH"))
      {
        bluetoothLeTxTest(2, 2);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_INBAND_LOW"))
      {
        bluetoothLeTxTest(0, 3);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_INBAND_MID"))
      {
        bluetoothLeTxTest(0, 1);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_INBAND_HIGH"))
      {
        bluetoothLeTxTest(0, 4);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_RX_LOW"))
      {
        bluetoothLeRxTest(0);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_RX_MID"))
      {
        bluetoothLeRxTest(1);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_RX_HIGH"))
      {
        bluetoothLeRxTest(2);
        return 2;
      }
      if (str.equals("ACTION_BLUETOOTH_LE_TEST_END"))
      {
        startBtReceiver();
        bluetoothLeDirectTestEnd();
        return 2;
      }
      if (str.equals("ACTION_NFC_ON"))
      {
        nfcEnable();
        return 2;
      }
      if (str.equals("ACTION_NFC_OFF"))
      {
        nfcDisable();
        return 2;
      }
      if (str.equals("ACTION_GPS_START"))
      {
        this.mHandler.removeMessages(10);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(10));
        return 2;
      }
    } while (!str.equals("ACTION_GPS_START_DELAY"));
    this.mHandler.removeMessages(13);
    this.mHandler.sendMessage(this.mHandler.obtainMessage(13));
    return 2;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.modules.ModuleCommunicationService
 * JD-Core Version:    0.7.1
 */