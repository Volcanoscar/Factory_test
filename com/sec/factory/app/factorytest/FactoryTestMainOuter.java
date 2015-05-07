package com.sec.factory.app.factorytest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.sec.factory.app.ui.UIBarcodeEmulator;
import com.sec.factory.app.ui.UIBarometer;
import com.sec.factory.app.ui.UIBtlesearch;
import com.sec.factory.app.ui.UIDmbDetach;
import com.sec.factory.app.ui.UIFMIntenna;
import com.sec.factory.app.ui.UIFeliCa;
import com.sec.factory.app.ui.UIGeomagneticGyro;
import com.sec.factory.app.ui.UIGrip;
import com.sec.factory.app.ui.UIHDMI_Landscape;
import com.sec.factory.app.ui.UIHDMI_Portrait;
import com.sec.factory.app.ui.UIIrLedTest;
import com.sec.factory.app.ui.UIIrLedTestOld;
import com.sec.factory.app.ui.UIKey;
import com.sec.factory.app.ui.UINFC;
import com.sec.factory.app.ui.UIProximityLight;
import com.sec.factory.app.ui.UIRgb;
import com.sec.factory.app.ui.UISPDIF;
import com.sec.factory.app.ui.UISensorHub;
import com.sec.factory.app.ui.UISimple;
import com.sec.factory.app.ui.UISpenAccuracyTest;
import com.sec.factory.app.ui.UISpenDetectionTest;
import com.sec.factory.app.ui.UISpenHoveringDrawTest;
import com.sec.factory.app.ui.UISpenTypeTest;
import com.sec.factory.app.ui.UITSP;
import com.sec.factory.app.ui.UIUSB;
import com.sec.factory.app.ui.UIWirelessCharge;
import com.sec.factory.app.ui.UIWirelessChargeNFCRead;
import com.sec.factory.app.ui.UiHallICTest;
import com.sec.factory.app.ui.oldconcept.UIMagneticAlps;
import com.sec.factory.modules.SensorDeviceInfo;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.FactoryTestMenu;
import com.sec.factory.support.Support.Feature;

public class FactoryTestMainOuter
{
  private final String CLASS_NAME = "FactoryTestMainOuter";
  private Context mContext;
  
  public FactoryTestMainOuter(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public void startBarcodeEmulator()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startBarcodeEmulator", "Start BarcodeEmulator");
    Intent localIntent = new Intent(this.mContext, UIBarcodeEmulator.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 50);
  }
  
  public void startBarometer()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startBarometer", null);
    Intent localIntent = new Intent(this.mContext, UIBarometer.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 22);
  }
  
  public void startBtLeSearch()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startBtLeSearch", null);
    Intent localIntent = new Intent();
    localIntent.setClass(this.mContext, UIBtlesearch.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 38);
  }
  
  public void startChargeNFCRead()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startChargeNFCRead", null);
    Intent localIntent = new Intent(this.mContext, UIWirelessChargeNFCRead.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 39);
  }
  
  public void startDMB1()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startDMB1", null);
    Intent localIntent = new Intent("android.intent.action.FACTORYTEST");
    localIntent.putExtra("STARTMODE", "FactoryTest");
    ((Activity)this.mContext).startActivityForResult(localIntent, 7);
  }
  
  public void startDMB2()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startDMB2", null);
    Intent localIntent = new Intent();
    localIntent.setClassName("com.samsung.sec.mtv", "com.samsung.sec.mtv.ui.TestMode.MtvUiTestMode");
    localIntent.putExtra("fromFactoryTest", true);
    ((Activity)this.mContext).startActivityForResult(localIntent, 8);
  }
  
  public void startDMB3()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startDMB3", null);
    Intent localIntent = new Intent();
    localIntent.setClassName("com.samsung.mobiletv", "com.samsung.mobiletv.testmode.MtvUiTestMode");
    localIntent.putExtra("fromFactoryTest", true);
    ((Activity)this.mContext).startActivityForResult(localIntent, 54);
  }
  
  public void startDmbDetach()
  {
    FtUtil.log_d("FactoryTestMainOuter", "startDMB_DETACH", null);
    Intent localIntent = new Intent(this.mContext, UIDmbDetach.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 52);
  }
  
  public void startFMIntennaTest()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startFMIntennaTest", null);
    Intent localIntent = new Intent(this.mContext, UIFMIntenna.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 51);
  }
  
  public void startFeliCa()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startFeliCa", null);
    Intent localIntent = new Intent(this.mContext, UIFeliCa.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 40);
  }
  
  public void startGeomagneticGyro()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startGeomagneticGyro", null);
    Intent localIntent = new Intent(this.mContext, UIGeomagneticGyro.class);
    localIntent.addFlags(65536);
    ((Activity)this.mContext).startActivityForResult(localIntent, 37);
  }
  
  public void startGrip()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startGrip", null);
    Intent localIntent = new Intent(this.mContext, UIGrip.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 34);
  }
  
  public void startGyroscope()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startGyroscope", "DO NOT USE [ startGyroscope() => startGeomagneticGyro() ]");
  }
  
  public void startHDMI()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startHDMI", null);
    Intent localIntent = new Intent();
    String str = Support.FactoryTestMenu.getTestCase(FactoryTestManager.convertorID_XML((byte)23));
    if ((str != null) && (str.contains("LAND"))) {
      localIntent.setClass(this.mContext, UIHDMI_Landscape.class);
    }
    for (;;)
    {
      localIntent.putExtra("Enter15Mode", true);
      ((Activity)this.mContext).startActivityForResult(localIntent, 23);
      return;
      localIntent.setClass(this.mContext, UIHDMI_Portrait.class);
    }
  }
  
  public void startHallIC()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startHallIC", null);
    Intent localIntent = new Intent(this.mContext, UiHallICTest.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 19);
  }
  
  public void startIRLED()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startIRLED", null);
    boolean bool = Support.Feature.getBoolean("IRLED_OLD_CONCEPT", false);
    Intent localIntent = new Intent();
    if (bool) {
      localIntent.setClass(this.mContext, UIIrLedTestOld.class);
    }
    for (;;)
    {
      ((Activity)this.mContext).startActivityForResult(localIntent, 31);
      return;
      localIntent.setClass(this.mContext, UIIrLedTest.class);
    }
  }
  
  public void startKey(int paramInt)
  {
    FtUtil.log_e("FactoryTestMainOuter", "startKey", "test id = " + paramInt);
    Intent localIntent = new Intent(this.mContext, UIKey.class);
    localIntent.putExtra("requestCode", paramInt);
    ((Activity)this.mContext).startActivityForResult(localIntent, paramInt);
  }
  
  public void startLcd()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startLcd", null);
    Intent localIntent = new Intent(this.mContext, UISimple.class);
    localIntent.putExtra("requestCode", (byte)46);
    ((Activity)this.mContext).startActivityForResult(localIntent, 46);
  }
  
  public void startMacroAF()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startMacroAF", null);
    if (Support.Feature.getBoolean("SUPPORT_NCR"))
    {
      Intent localIntent = new Intent();
      localIntent.setClassName("com.bst.ncr", "com.sec.android.app.ncr.camera.NcrCamera");
      localIntent.putExtra("fromFactoryTest", true);
      ((Activity)this.mContext).startActivityForResult(localIntent, 48);
      return;
    }
    Toast.makeText(this.mContext, "No app for Macro AF test", 0).show();
  }
  
  public void startMagnetic()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startMagnetic", "DO NOT USE [ startMagnetic() => startGeomagneticGyro() ]");
    String str = SensorDeviceInfo.getSensorName(SensorDeviceInfo.TYPE_GEOMAGNETIC, SensorDeviceInfo.TARGET_XML);
    FtUtil.log_e("FactoryTestMainOuter", "startMagnetic", "Start Magnetic Sensor - feature : " + str);
    Intent localIntent = new Intent();
    if ((str.equals("HSCDTD004")) || (str.equals("HSCDTD004A")) || (str.equals("HSCDTD006A")) || (str.equals("HSCDTD008A")))
    {
      localIntent.setClass(this.mContext, UIMagneticAlps.class);
      ((Activity)this.mContext).startActivityForResult(localIntent, 20);
    }
  }
  
  public void startNFC()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startNFC", null);
    Intent localIntent = new Intent(this.mContext, UINFC.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 9);
  }
  
  public void startProximityLight()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startProximityLight", null);
    Intent localIntent = new Intent(this.mContext, UIProximityLight.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 17);
  }
  
  public void startRGB()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startRGB", null);
    Intent localIntent = new Intent(this.mContext, UIRgb.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 35);
  }
  
  public void startSPDIFAudioTest()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startSPDIFAudioTest", null);
    Intent localIntent = new Intent(this.mContext, UISPDIF.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 30);
  }
  
  public void startSPenDetection()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startSPenDectection", "Start SPenDetection");
    Intent localIntent = new Intent(this.mContext, UISpenDetectionTest.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 41);
  }
  
  public void startSPenDrawing()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startSPenDrawing", "Start SPenDrawing(Pen-Type)");
    Intent localIntent = new Intent(this.mContext, UISpenAccuracyTest.class);
    localIntent.putExtra("TYPE", 0);
    ((Activity)this.mContext).startActivityForResult(localIntent, 26);
  }
  
  public void startSPenEraser()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startSPenEraser", "Start SPenEraser(Eraser-Type)");
    Intent localIntent = new Intent(this.mContext, UISpenTypeTest.class);
    localIntent.putExtra("TYPE", 1);
    ((Activity)this.mContext).startActivityForResult(localIntent, 28);
  }
  
  public void startSPenHovering()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startSPenHovering", "Start SPenHovering");
    Intent localIntent = new Intent(this.mContext, UISpenHoveringDrawTest.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 27);
  }
  
  public void startSensorHub()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startSensorHub", null);
    Intent localIntent = new Intent(this.mContext, UISensorHub.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 49);
  }
  
  public void startSimple()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startSimple", null);
    Intent localIntent = new Intent(this.mContext, UISimple.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 0);
  }
  
  public void startTSP(int paramInt)
  {
    FtUtil.log_e("FactoryTestMainOuter", "startTSP", "test id = " + paramInt);
    Intent localIntent = new Intent(this.mContext, UITSP.class);
    localIntent.addFlags(65536);
    localIntent.putExtra("requestCode", paramInt);
    ((Activity)this.mContext).startActivityForResult(localIntent, paramInt);
  }
  
  public void startUSB()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startUSB", null);
    Intent localIntent = new Intent(this.mContext, UIUSB.class);
    ((Activity)this.mContext).startActivity(localIntent);
  }
  
  public void startWirelessCharge()
  {
    FtUtil.log_e("FactoryTestMainOuter", "startWirelessCharge", null);
    Intent localIntent = new Intent(this.mContext, UIWirelessCharge.class);
    ((Activity)this.mContext).startActivityForResult(localIntent, 32);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestMainOuter
 * JD-Core Version:    0.7.1
 */