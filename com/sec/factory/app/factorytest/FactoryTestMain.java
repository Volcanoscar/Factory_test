package com.sec.factory.app.factorytest;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.sec.factory.app.ui.UIBase;
import com.sec.factory.modules.ModuleAudio;
import com.sec.factory.modules.ModuleCommunication;
import com.sec.factory.modules.ModuleDevice;
import com.sec.factory.modules.ModulePower;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;
import com.sec.factory.support.Support.Kernel;
import com.sec.factory.support.Support.Properties;
import com.sec.factory.support.Support.Version;

public class FactoryTestMain
  extends UIBase
  implements AdapterView.OnItemClickListener
{
  public static int CURRENT_TEST_ID = 0;
  private static int DUMMY = 0;
  public static boolean IsLoopBack2 = false;
  public static final int WHAT_RESULT;
  public static final int WHAT_RESULT_FAIL_CASE;
  public static final int WHAT_RESULT_WITHOUT_NV;
  public static final int WHAT_SCOPE_MAX;
  public static final int WHAT_SCOPE_MIN = DUMMY;
  private static int mAutoBrightnessMode;
  private static int mSystemScreenBrightness;
  private boolean IsKeyRelease = true;
  boolean isSupportMIC2 = false;
  boolean isSupportRCV = true;
  private AudioManager mAudioManager;
  private int mBatteryStatus = 1;
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      FtUtil.log_d(FactoryTestMain.this.CLASS_NAME, "mBroadcastReceiver.onReceive", "action=" + str);
      if (str.equals("com.sec.samsung.STOP_FACTORY_TEST"))
      {
        FtUtil.log_i(FactoryTestMain.this.CLASS_NAME, "mBroadcastReceiver.onReceive", "STOP 15TEST");
        FactoryTestMain.this.finish();
      }
      if (FactoryTestManager.getAllitemPass()) {
        FactoryTestMain.this.mListView.setBackgroundColor(-16776961);
      }
      for (FactoryTestMainAdapter.ALL_PASS_STATE = true;; FactoryTestMainAdapter.ALL_PASS_STATE = false)
      {
        FactoryTestManager.notifyDataSetChanged();
        return;
        FactoryTestMain.this.mListView.setBackgroundColor(-1);
      }
    }
  };
  private AlertDialog mCapacityAlertDialog;
  private Context mContext;
  private Handler mFeedback = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      FtUtil.log_d(FactoryTestMain.this.CLASS_NAME, "mFeedback.handleMessage", "msg what=" + paramAnonymousMessage.what);
      switch (paramAnonymousMessage.what)
      {
      default: 
        if (FactoryTestManager.getAllitemPass()) {
          FactoryTestMain.this.mListView.setBackgroundColor(-16776961);
        }
        break;
      }
      for (FactoryTestMainAdapter.ALL_PASS_STATE = true;; FactoryTestMainAdapter.ALL_PASS_STATE = false)
      {
        FactoryTestManager.notifyDataSetChanged();
        return;
        FtUtil.log_i(FactoryTestMain.this.CLASS_NAME, "mFeedback.handleMessage", "MSG_START_EAR_KEY_FEED_BACK");
        if (ModuleAudio.instance(FactoryTestMain.this).isPlayingSound()) {
          break;
        }
        ModuleAudio.instance(FactoryTestMain.this).setStreamMusicVolumeMin();
        FactoryTestMain.this.playSound(2131034114, false);
        ModuleDevice.instance(FactoryTestMain.this).startVibrate();
        break;
        FtUtil.log_i(FactoryTestMain.this.CLASS_NAME, "mFeedback.handleMessage", "MSG_STOP_EAR_KEY_FEED_BACK");
        if (ModuleAudio.instance(FactoryTestMain.this).isPlayingSound())
        {
          ModuleAudio.instance(FactoryTestMain.this).stopMedia();
          if (!ModuleAudio.instance(FactoryTestMain.this).isDoingLoopback()) {
            FactoryTestMain.this.mFeedback.sendEmptyMessage(4);
          }
        }
        ModuleAudio.instance(FactoryTestMain.this).setStreamMusicVolumeMax();
        ModuleDevice.instance(FactoryTestMain.this).stopVibrate();
        break;
        FtUtil.log_i(FactoryTestMain.this.CLASS_NAME, "mFeedback.handleMessage", "MSG_SHOW_TOAST_FEED_BACK, msg=" + paramAnonymousMessage.obj.toString());
        Toast.makeText(FactoryTestMain.this, paramAnonymousMessage.obj.toString(), 0).show();
        break;
        FtUtil.log_i(FactoryTestMain.this.CLASS_NAME, "mFeedback.handleMessage", "MSG_START_PROGRESS_FEED_BACK, msg=" + paramAnonymousMessage.obj.toString());
        FactoryTestMain.this.mProgress.setVisibility(0);
        FactoryTestMain.this.mFeedbackText.setText(paramAnonymousMessage.obj.toString());
        FactoryTestMain.this.mFeedbackText.setVisibility(0);
        break;
        FtUtil.log_i(FactoryTestMain.this.CLASS_NAME, "mFeedback.handleMessage", "MSG_STOP_PROGRESS_FEDD_BACK");
        FactoryTestMain.this.mProgress.setVisibility(4);
        FactoryTestMain.this.mFeedbackText.setVisibility(4);
        break;
        FtUtil.log_i(FactoryTestMain.this.CLASS_NAME, "mFeedback.handleMessage", "MSG_COMPLETE_FIRST_NV_UPDATE_FEED_BACK");
        if (FtUtil.isFactoryAppAPO()) {
          break;
        }
        FactoryTestMain.this.mHandler.postDelayed(new Runnable()
        {
          public void run()
          {
            for (int i = 0; i < FactoryTestManager.size(); i++) {
              if (FactoryTestManager.isAutoTest(i)) {
                FactoryTestMain.this.launchTest(FactoryTestManager.getItemID(i));
              }
            }
          }
        }, 100L);
        break;
        FactoryTestMain.this.mListView.setBackgroundColor(-1);
      }
    }
  };
  private TextView mFeedbackText;
  Handler mHandler = new Handler();
  private Handler mInnerTestResult = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == FactoryTestMain.WHAT_RESULT)
      {
        FtUtil.log_e(FactoryTestMain.this.CLASS_NAME, "mInnerTestResult.handleMessage", "WHAT_RESULT = " + (byte)paramAnonymousMessage.arg1 + " , " + (byte)paramAnonymousMessage.arg2);
        FactoryTestMain.this.setTestResult((byte)paramAnonymousMessage.arg1, (byte)paramAnonymousMessage.arg2);
      }
      do
      {
        return;
        if (paramAnonymousMessage.what == FactoryTestMain.WHAT_RESULT_FAIL_CASE)
        {
          FtUtil.log_e(FactoryTestMain.this.CLASS_NAME, "mInnerTestResult.handleMessage", "WHAT_RESULT_FAIL_CASE");
          FactoryTestMain.this.setTestResultFailCase((byte)paramAnonymousMessage.arg1);
          return;
        }
      } while (paramAnonymousMessage.what != FactoryTestMain.WHAT_RESULT_WITHOUT_NV);
      FtUtil.log_e(FactoryTestMain.this.CLASS_NAME, "mInnerTestResult.handleMessage", "WHAT_RESULT_WITHOUT_NV");
      FactoryTestMain.this.setTestResultWithoutNV((byte)paramAnonymousMessage.arg1, (byte)paramAnonymousMessage.arg2);
    }
  };
  private ListView mListView;
  private FactoryTestMainNV mMainNV;
  private ModuleCommunication mModuleCommunication;
  private ProgressBar mProgress;
  private FactoryTestMainInner mTestInner;
  private FactoryTestMainInnerBT mTestInnerBT;
  private FactoryTestMainInnerEarphone mTestInnerEarphone;
  private FactoryTestMainInnerFMRadio mTestInnerFMRadio;
  private FactoryTestMainInnerLoopback mTestInnerLoopback;
  private FactoryTestMainInnerOTG mTestInnerOTG;
  private FactoryTestMainOuter mTestOuter;
  private TextView mTestResultText;
  
  static
  {
    int i = DUMMY;
    DUMMY = i + 1;
    WHAT_RESULT = i;
    int j = DUMMY;
    DUMMY = j + 1;
    WHAT_RESULT_FAIL_CASE = j;
    int k = DUMMY;
    DUMMY = k + 1;
    WHAT_RESULT_WITHOUT_NV = k;
    WHAT_SCOPE_MAX = DUMMY;
  }
  
  public FactoryTestMain()
  {
    super("FactoryTestMain");
  }
  
  private boolean checkCPStatus()
  {
    return "Unknown".equalsIgnoreCase(Support.Properties.get("SW_VERSION"));
  }
  
  private void checkCapacity()
  {
    String str = Support.Kernel.read("BATTERY_CAPACITY");
    FtUtil.log_i(this.CLASS_NAME, "checkCapacity()", "result = " + str);
    int i = Integer.parseInt(str);
    int j = 30;
    Integer.parseInt(Support.Version.getString("FACTORY_TEST_APP").substring(2, 4));
    if ("phone".equalsIgnoreCase(Support.Feature.getString("DEVICE_TYPE"))) {
      j = 20;
    }
    if (i < j)
    {
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
      localBuilder.setTitle("Battery Low");
      localBuilder.setMessage("Need to Charge " + j + "% Over");
      localBuilder.setPositiveButton("OK", new EmptyListener());
      localBuilder.setOnKeyListener(new DialogInterface.OnKeyListener()
      {
        public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          FactoryTestMain.this.finish();
          return true;
        }
      });
      this.mCapacityAlertDialog = localBuilder.create();
      this.mCapacityAlertDialog.show();
    }
  }
  
  private boolean isEarKey(InputDevice paramInputDevice)
  {
    if (paramInputDevice == null) {
      FtUtil.log_e(this.CLASS_NAME, "isEarKey", "Error : Device is null");
    }
    String str;
    do
    {
      return false;
      str = paramInputDevice.getName();
      FtUtil.log_d(this.CLASS_NAME, "onKeyDown", "Device name: " + str);
    } while ((str == null) || (!str.equals("sec_jack")));
    return true;
  }
  
  private void launchTest(int paramInt)
  {
    switch (paramInt)
    {
    case 11: 
    case 12: 
    case 13: 
    case 15: 
    case 16: 
    case 29: 
    case 36: 
    case 43: 
    default: 
      return;
    case 14: 
      this.mTestInner.startBattery();
      return;
    case 33: 
      this.mTestInner.startLowFrequency();
      return;
    case 3: 
      this.mTestInner.startSDCard();
      return;
    case 25: 
      this.mTestInner.startSIMCard();
      return;
    case 47: 
      this.mTestInner.startSIMCard2();
      return;
    case 4: 
      this.mTestInner.startSpeaker();
      return;
    case 5: 
      this.mTestInner.startSpeaker_r();
      return;
    case 18: 
      this.mTestOuter.startUSB();
      return;
    case 2: 
      this.mTestInnerBT.startBluetooth();
      return;
    case 1: 
      this.mTestInnerLoopback.startLoopback();
      return;
    case 42: 
      this.mTestInnerLoopback.startLoopback();
      return;
    case 24: 
      this.mTestInnerOTG.startOTG();
      return;
    case 53: 
      this.mTestInnerFMRadio.startFMRADIO();
      return;
    case 22: 
      this.mTestOuter.startBarometer();
      return;
    case 38: 
      this.mTestOuter.startBtLeSearch();
      return;
    case 39: 
      this.mTestOuter.startChargeNFCRead();
      return;
    case 7: 
      this.mTestOuter.startDMB1();
      return;
    case 8: 
      this.mTestOuter.startDMB2();
      return;
    case 54: 
      this.mTestOuter.startDMB3();
      return;
    case 37: 
      this.mTestOuter.startGeomagneticGyro();
      return;
    case 34: 
      this.mTestOuter.startGrip();
      return;
    case 21: 
      this.mTestOuter.startGyroscope();
      return;
    case 19: 
      this.mTestOuter.startHallIC();
      return;
    case 23: 
      this.mTestOuter.startHDMI();
      return;
    case 31: 
      this.mTestOuter.startIRLED();
      return;
    case 6: 
      this.mTestOuter.startKey(paramInt);
      return;
    case 45: 
      this.mTestOuter.startKey(paramInt);
      return;
    case 46: 
      this.mTestOuter.startLcd();
      return;
    case 48: 
      this.mTestOuter.startMacroAF();
      return;
    case 20: 
      this.mTestOuter.startMagnetic();
      return;
    case 9: 
      this.mTestOuter.startNFC();
      return;
    case 17: 
      this.mTestOuter.startProximityLight();
      return;
    case 35: 
      this.mTestOuter.startRGB();
      return;
    case 49: 
      this.mTestOuter.startSensorHub();
      return;
    case 0: 
      this.mTestOuter.startSimple();
      return;
    case 30: 
      this.mTestOuter.startSPDIFAudioTest();
      return;
    case 26: 
      this.mTestOuter.startSPenDrawing();
      return;
    case 28: 
      this.mTestOuter.startSPenEraser();
      return;
    case 27: 
      this.mTestOuter.startSPenHovering();
      return;
    case 41: 
      this.mTestOuter.startSPenDetection();
      return;
    case 50: 
      this.mTestOuter.startBarcodeEmulator();
      return;
    case 10: 
      this.mTestOuter.startTSP(paramInt);
      return;
    case 44: 
      this.mTestOuter.startTSP(paramInt);
      return;
    case 32: 
      this.mTestOuter.startWirelessCharge();
      return;
    case 40: 
      this.mTestOuter.startFeliCa();
      return;
    case 52: 
      this.mTestOuter.startDmbDetach();
      return;
    }
    this.mTestOuter.startFMIntennaTest();
  }
  
  private void playSound(int paramInt, boolean paramBoolean)
  {
    ModuleAudio localModuleAudio = ModuleAudio.instance(this);
    if (!localModuleAudio.isPlayingSound()) {
      localModuleAudio.stopMedia();
    }
    localModuleAudio.playMedia(paramInt, true);
    if (paramBoolean) {
      this.mFeedback.sendMessage(this.mFeedback.obtainMessage(3, "Media playing"));
    }
  }
  
  private void registerTestReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.sec.factory.app.factorytest.finsh");
    localIntentFilter.addAction("com.sec.samsung.STOP_FACTORY_TEST");
    registerReceiver(this.mBroadcastReceiver, localIntentFilter);
  }
  
  private void showCpUnknownDialog()
  {
    FtUtil.log_d(this.CLASS_NAME, "showCpUnknownDialog", "showCpUnknownDialog");
    AlertDialogFragment.newInstance(2131165188).show(getFragmentManager(), "dialog");
  }
  
  private void stopSound()
  {
    ModuleAudio localModuleAudio = ModuleAudio.instance(this);
    if (localModuleAudio.isPlayingSound())
    {
      localModuleAudio.stopMedia();
      this.mFeedback.sendEmptyMessage(4);
    }
  }
  
  private void unregisterTestReceiver()
  {
    if (this.mBroadcastReceiver != null)
    {
      unregisterReceiver(this.mBroadcastReceiver);
      this.mBroadcastReceiver = null;
      FtUtil.log_i(this.CLASS_NAME, "unregisterTestReceiver", null);
    }
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    FtUtil.log_e(this.CLASS_NAME, "onActivityResult", "requestCode = " + paramInt1);
    switch (paramInt1)
    {
    }
    for (;;)
    {
      FactoryTestManager.notifyDataSetChanged();
      return;
      if (paramInt2 == -1)
      {
        setTestResult((byte)7, (byte)80);
      }
      else
      {
        setTestResultFailCase((byte)7);
        continue;
        if (paramInt2 == -1)
        {
          setTestResult((byte)8, (byte)80);
        }
        else
        {
          setTestResultFailCase((byte)8);
          continue;
          if (paramInt2 == -1)
          {
            setTestResult((byte)54, (byte)80);
          }
          else
          {
            setTestResultFailCase((byte)54);
            continue;
            if (paramInt2 == -1)
            {
              setTestResult((byte)31, (byte)80);
            }
            else
            {
              setTestResultFailCase((byte)31);
              continue;
              if (paramInt2 == -1)
              {
                setTestResult((byte)48, (byte)80);
              }
              else
              {
                setTestResultFailCase((byte)48);
                continue;
                if (paramInt2 == -1) {
                  setTestResult((byte)53, (byte)80);
                } else {
                  setTestResultFailCase((byte)53);
                }
              }
            }
          }
        }
      }
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903057);
    ModuleAudio.instance(this).sendToAudioManagerFTAOnOff(true);
    FactoryTestMainAdapter localFactoryTestMainAdapter = new FactoryTestMainAdapter(this);
    FactoryTestManager.setAdapter(localFactoryTestMainAdapter);
    FactoryTestManager.initialize();
    this.mAudioManager = ((AudioManager)getSystemService("audio"));
    this.mListView = ((ListView)findViewById(2131296395));
    this.mListView.setAdapter(localFactoryTestMainAdapter);
    this.mListView.setOnItemClickListener(this);
    this.mListView.setTranscriptMode(1);
    this.mTestResultText = ((TextView)findViewById(2131296392));
    this.mProgress = ((ProgressBar)findViewById(2131296393));
    this.mFeedbackText = ((TextView)findViewById(2131296394));
    this.mContext = this;
    this.mMainNV = new FactoryTestMainNV(this.mContext, this.mFeedback, this.mTestResultText, this.mListView, this.mFactoryPhone);
    this.mTestInner = new FactoryTestMainInner(this.mContext, this.mInnerTestResult, this.mFeedback, this.mListView);
    this.mTestInnerBT = new FactoryTestMainInnerBT(this.mContext, this.mInnerTestResult, this.mFeedback, this.mListView);
    this.mTestInnerLoopback = new FactoryTestMainInnerLoopback(this.mContext, this.mInnerTestResult, this.mFeedback, this.mListView);
    this.mTestInnerEarphone = new FactoryTestMainInnerEarphone(this.mContext, this.mInnerTestResult, this.mFeedback, this.mListView, this.mTestInnerLoopback);
    this.mTestInnerOTG = new FactoryTestMainInnerOTG(this.mContext, this.mInnerTestResult, this.mFeedback);
    this.mTestInnerFMRadio = new FactoryTestMainInnerFMRadio(this.mContext, this.mInnerTestResult, this.mFeedback);
    this.mTestOuter = new FactoryTestMainOuter(this.mContext);
    this.mModuleCommunication = ModuleCommunication.instance(this);
    if (FtUtil.isFactoryAppAPO()) {
      this.mTestResultText.setText(this.mMainNV.formatTestResultAPO());
    }
    if (FactoryTestMainAdapter.FONT_SIZE != 0.0F) {
      this.mTestResultText.setTextSize(FactoryTestMainAdapter.FONT_SIZE);
    }
    registerTestReceiver();
    if ((!FtUtil.isFactoryAppAPO()) && (checkCPStatus()))
    {
      FtUtil.log_d(this.CLASS_NAME, "onCreate", "Something wrong with CP");
      showCpUnknownDialog();
      return;
    }
    checkCapacity();
    if (FactoryTestManager.getEnable(9)) {
      this.mModuleCommunication.nfcOn();
    }
    if (FtUtil.isFactoryAppAPO()) {
      for (int i = 0; i < FactoryTestManager.size(); i++) {
        if (FactoryTestManager.isAutoTest(i)) {
          launchTest(FactoryTestManager.getItemID(i));
        }
      }
    }
    this.isSupportRCV = Support.Feature.getBoolean("SUPPORT_RCV", true);
    this.isSupportMIC2 = ModuleAudio.isSupportSecondMicTest();
    mAutoBrightnessMode = ModulePower.instance(this).getScreenBrightnessMode();
    mSystemScreenBrightness = ModulePower.instance(this).getBrightness();
    ModulePower.instance(this).setScreenBrightnessMode(0);
    this.mHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        ModulePower.instance(FactoryTestMain.this.mContext).setBrightness(255);
      }
    }, 100L);
  }
  
  protected void onDestroy()
  {
    ModuleAudio.instance(this).setEarKeyState(0);
    ModulePower.instance(this).setScreenBrightnessMode(mAutoBrightnessMode);
    ModulePower.instance(this).setBrightness(mSystemScreenBrightness);
    unregisterTestReceiver();
    this.mTestInnerEarphone.unregisterTestReceiver();
    this.mTestInner.unregisterTestReceiver();
    this.mTestInnerOTG.unregisterOTGReceiver();
    this.mTestInnerFMRadio.unregisterFMRADIOReceiver();
    this.mTestInnerBT.stopBluetooth();
    if (!FtUtil.isFactoryAppAPO()) {
      this.mMainNV.unregisterReceiverNRequestCPO();
    }
    if (this.mFactoryPhone != null)
    {
      this.mFactoryPhone.DestroySecPhoneService();
      this.mFactoryPhone = null;
      FtUtil.log_i(this.CLASS_NAME, "onDestroy", "Unbind SecPhoneService");
    }
    if (FactoryTestManager.getEnable(9)) {
      this.mModuleCommunication.nfcOff();
    }
    ModuleAudio.instance(this).sendToAudioManagerFTAOnOff(false);
    if (this.mCapacityAlertDialog != null)
    {
      this.mCapacityAlertDialog.dismiss();
      this.mCapacityAlertDialog = null;
    }
    super.onDestroy();
  }
  
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (!FactoryTestManager.getClickable(paramInt)) {}
    int i;
    do
    {
      return;
      i = FactoryTestManager.getItemID(paramInt);
      FtUtil.log_d(this.CLASS_NAME, "onItemClick", "position=" + paramInt + ", testID=" + i);
    } while ((ModuleAudio.instance(this).isDoingLoopback()) && (i != 1) && (i != 42));
    CURRENT_TEST_ID = i;
    FtUtil.log_d(this.CLASS_NAME, "onItemClick", "position=" + paramInt + ", CURRENT_TEST_ID=" + CURRENT_TEST_ID);
    if ((ModuleAudio.instance(this).isPlayingSound()) && (i != 4) && (i != 5) && (i != 33))
    {
      stopSound();
      ModuleAudio.instance(this).setAudioPath(4);
    }
    String str = FactoryTestManager.getItemName_Position(paramInt).trim();
    if (str.indexOf("[") > -1) {
      str = str.substring(0, str.indexOf("[")).replace(" ", "");
    }
    savePositionData(str);
    FtUtil.log_d(this.CLASS_NAME, "onItemClick", "currentStage: " + str);
    launchTest(i);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    FtUtil.log_d(this.CLASS_NAME, "onKeyDown", "keyCode=" + paramInt);
    if (!this.IsKeyRelease) {
      return true;
    }
    this.IsKeyRelease = false;
    switch (paramInt)
    {
    default: 
      return super.onKeyDown(paramInt, paramKeyEvent);
    case 24: 
      FtUtil.log_i(this.CLASS_NAME, "onKeyDown", "KEYCODE_VOLUME_UP");
      ModuleAudio localModuleAudio2 = ModuleAudio.instance(this);
      int i = localModuleAudio2.getMicCount();
      FtUtil.log_d(this.CLASS_NAME, "onKeyDown", "getCurrentLoopbackPath = " + localModuleAudio2.getCurrentLoopbackPath());
      if ((localModuleAudio2.isDoingLoopback()) && (!localModuleAudio2.isEarphonePlugged()) && (this.isSupportRCV) && (localModuleAudio2.getCurrentLoopbackPath() != 5) && (localModuleAudio2.getCurrentLoopbackPath() != i - 1))
      {
        this.mTestInnerLoopback.changeLoopbackRoute(true);
        FactoryTestManager.notifyDataSetChanged();
      }
      if ((localModuleAudio2.isEarphonePlugged()) && (Support.Feature.getBoolean("SUPPORT_EAR_VOLUME_CONTROL")))
      {
        if ((localModuleAudio2.getEarKeyState() != 0) && (localModuleAudio2.getEarKeyState() != 4)) {
          break label288;
        }
        localModuleAudio2.setEarKeyState(1);
      }
      for (;;)
      {
        this.mTestInnerEarphone.startEarkey(true);
        FactoryTestManager.notifyDataSetChanged();
        if ((!localModuleAudio2.isDoingLoopback()) && (!isEarKey(paramKeyEvent.getDevice()))) {
          this.mListView.smoothScrollToPosition(0);
        }
        FtUtil.log_i(this.CLASS_NAME, "onKeyDown", "KEYCODE_VOLUME_UP = return true");
        return super.onKeyDown(paramInt, paramKeyEvent);
        label288:
        if ((localModuleAudio2.getEarKeyState() == 2) || (localModuleAudio2.getEarKeyState() == 3)) {
          localModuleAudio2.setEarKeyState(4);
        }
      }
    }
    FtUtil.log_i(this.CLASS_NAME, "onKeyDown", "KEYCODE_VOLUME_DOWN");
    ModuleAudio localModuleAudio1 = ModuleAudio.instance(this);
    if ((localModuleAudio1.isEarphonePlugged()) && (Support.Feature.getBoolean("SUPPORT_EAR_VOLUME_CONTROL")))
    {
      if ((localModuleAudio1.getEarKeyState() != 0) && (localModuleAudio1.getEarKeyState() != 3)) {
        break label427;
      }
      localModuleAudio1.setEarKeyState(2);
    }
    for (;;)
    {
      this.mTestInnerEarphone.startEarkey(true);
      FactoryTestManager.notifyDataSetChanged();
      if ((!localModuleAudio1.isDoingLoopback()) && (!isEarKey(paramKeyEvent.getDevice()))) {
        this.mListView.smoothScrollToPosition(this.mListView.getCount());
      }
      FtUtil.log_i(this.CLASS_NAME, "onKeyDown", "KEYCODE_VOLUME_DOWN = return true");
      return true;
      label427:
      if ((localModuleAudio1.getEarKeyState() == 1) || (localModuleAudio1.getEarKeyState() == 4)) {
        localModuleAudio1.setEarKeyState(3);
      }
    }
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    FtUtil.log_d(this.CLASS_NAME, "onKeyUp", "keyCode=" + paramInt);
    this.IsKeyRelease = bool;
    switch (paramInt)
    {
    default: 
      bool = super.onKeyUp(paramInt, paramKeyEvent);
    }
    do
    {
      return bool;
      if (Support.Feature.getBoolean("SUPPORT_EAR_VOLUME_CONTROL"))
      {
        FtUtil.log_i(this.CLASS_NAME, "onKeyUp", "KEYCODE_VOLUME_UP");
        this.mTestInnerEarphone.startEarkey(false);
        FtUtil.log_i(this.CLASS_NAME, "onKeyUp", "KEYCODE_VOLUME_UP = return true");
      }
      return super.onKeyUp(paramInt, paramKeyEvent);
    } while (!Support.Feature.getBoolean("SUPPORT_EAR_VOLUME_CONTROL"));
    FtUtil.log_i(this.CLASS_NAME, "onKeyUp", "KEYCODE_VOLUME_DOWN");
    this.mTestInnerEarphone.startEarkey(false);
    FtUtil.log_i(this.CLASS_NAME, "onKeyUp", "KEYCODE_VOLUME_DOWN = return true");
    return bool;
  }
  
  protected void onNewIntent(Intent paramIntent)
  {
    FtUtil.log_d(this.CLASS_NAME, "onNewIntent", "onNewIntent");
    this.mListView.smoothScrollToPosition(0);
    this.mTestInnerBT.setEnable(false);
    this.mFeedback.sendEmptyMessage(4);
    FactoryTestManager.initialize();
    if (this.mCapacityAlertDialog != null) {
      this.mCapacityAlertDialog.dismiss();
    }
    super.onNewIntent(paramIntent);
  }
  
  protected void onPause()
  {
    super.onPause();
    ModuleAudio localModuleAudio = ModuleAudio.instance(this);
    if (localModuleAudio.isDoingLoopback())
    {
      localModuleAudio.stopLoopback();
      FtUtil.log_i(this.CLASS_NAME, "onPause", "Stop Loopback");
    }
    stopSound();
    ModuleAudio.instance(this).setAudioPath(4);
  }
  
  protected void onResume()
  {
    super.onResume();
    ModulePower.instance(this.mContext).setBrightness(255);
    if ((this.mModuleCommunication.isEnabledBtDevice()) && (this.mTestInnerBT.getEnable()))
    {
      setTestResult((byte)2, (byte)80);
      this.mTestInnerBT.setEnable(false);
    }
    if (FactoryTestManager.getAllitemPass()) {
      this.mListView.setBackgroundColor(-16776961);
    }
    for (FactoryTestMainAdapter.ALL_PASS_STATE = true;; FactoryTestMainAdapter.ALL_PASS_STATE = false)
    {
      if (!FtUtil.isFactoryAppAPO()) {
        this.mMainNV.registerReceiverNRequestCPO();
      }
      if (ModuleAudio.instance(this).isConnectionModeNone()) {
        this.mAudioManager.setRingerMode(2);
      }
      FactoryTestManager.notifyDataSetChanged();
      return;
      this.mListView.setBackgroundColor(-1);
    }
  }
  
  public void startEarKey(boolean paramBoolean)
  {
    super.startEarKey(paramBoolean);
    this.mTestInnerEarphone.startEarkey(paramBoolean);
  }
  
  public static class AlertDialogFragment
    extends DialogFragment
  {
    public static AlertDialogFragment newInstance(int paramInt)
    {
      AlertDialogFragment localAlertDialogFragment = new AlertDialogFragment();
      Bundle localBundle = new Bundle();
      localBundle.putInt("title", paramInt);
      localAlertDialogFragment.setArguments(localBundle);
      return localAlertDialogFragment;
    }
    
    public Dialog onCreateDialog(Bundle paramBundle)
    {
      int i = getArguments().getInt("title");
      new AlertDialog.Builder(getActivity()).setTitle(i).setMessage("CP Unknown").setPositiveButton("OK", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          ((FactoryTestMain)FactoryTestMain.AlertDialogFragment.this.getActivity()).finish();
          FactoryTestMain.AlertDialogFragment.this.dismiss();
        }
      }).setOnKeyListener(new DialogInterface.OnKeyListener()
      {
        public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          ((FactoryTestMain)FactoryTestMain.AlertDialogFragment.this.getActivity()).finish();
          FactoryTestMain.AlertDialogFragment.this.dismiss();
          return true;
        }
      }).create();
    }
  }
  
  public class EmptyListener
    implements DialogInterface.OnClickListener
  {
    public EmptyListener() {}
    
    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      FactoryTestMain.this.finish();
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.app.factorytest.FactoryTestMain
 * JD-Core Version:    0.7.1
 */