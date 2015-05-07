package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleDFT;
import com.sec.factory.support.FtUtil;
import com.sec.factory.support.Support.Feature;

public class AtTouch
  extends AtCommandHandler
{
  private String PROTOCOL = Support.Feature.getString("FACTORY_TEST_PROTOCOL");
  int touchMoveStatus = 0;
  
  public AtTouch(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "TOUCH";
    this.CLASS_NAME = "AtTouch";
    if ((this.PROTOCOL.equals("NEW_AT")) && (FtUtil.isFactoryAppAPO() == true))
    {
      this.NUM_ARGS = 3;
      return;
    }
    this.NUM_ARGS = 11;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      Object localObject2;
      Object localObject3;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "wrong";
          return (String)localObject2;
        }
        if ((!this.PROTOCOL.equals("NEW_AT")) || (FtUtil.isFactoryAppAPO() != true)) {
          break label157;
        }
        if (paramArrayOfString[2].equals("0"))
        {
          mModuleDFT.DftTouchDown(paramArrayOfString[0], paramArrayOfString[1]);
          if ((!this.PROTOCOL.equals("NEW_AT")) || (FtUtil.isFactoryAppAPO() != true)) {
            break label325;
          }
          localObject3 = "OK\r\n";
          break label587;
        }
        if (paramArrayOfString[2].equals("1"))
        {
          mModuleDFT.DftTouchUp(paramArrayOfString[0], paramArrayOfString[1]);
          continue;
        }
        if (!paramArrayOfString[2].equals("2")) {
          break label137;
        }
      }
      finally {}
      mModuleDFT.DftTouchMove();
      continue;
      label137:
      if (paramArrayOfString[2].equals("3"))
      {
        mModuleDFT.DftTouchLong();
        continue;
        label157:
        if (paramArrayOfString[0].equalsIgnoreCase("0F"))
        {
          mModuleDFT.DftTouchDown(paramArrayOfString[3] + paramArrayOfString[2], paramArrayOfString[5] + paramArrayOfString[4]);
        }
        else if (paramArrayOfString[0].equals("11"))
        {
          mModuleDFT.DftTouchUp(paramArrayOfString[3] + paramArrayOfString[2], paramArrayOfString[5] + paramArrayOfString[4]);
        }
        else if (paramArrayOfString[0].equals("10"))
        {
          mModuleDFT.DftTouchMove();
        }
        else if (paramArrayOfString[0].equals("12"))
        {
          mModuleDFT.DftTouchLong();
          continue;
          label325:
          if ((this.PROTOCOL.equals("NEW_AT")) && (!FtUtil.isFactoryAppAPO()))
          {
            responseOK(paramArrayOfString[0]);
            localObject3 = null;
          }
          else if (this.PROTOCOL.equals("NEW_DM"))
          {
            String str1 = "";
            int i = paramArrayOfString.length;
            for (int j = 0; j < i; j++)
            {
              String str2 = paramArrayOfString[j];
              str1 = str1 + (char)Integer.parseInt(str2, 16);
            }
            localObject3 = responseString(paramArrayOfString[0], str1);
          }
          else
          {
            boolean bool = this.PROTOCOL.equals("NEW_ETS");
            localObject3 = null;
            if (bool)
            {
              String str3 = responseString(paramArrayOfString[0], paramArrayOfString[0].toString() + paramArrayOfString[1].toString() + paramArrayOfString[2].toString() + paramArrayOfString[3].toString() + paramArrayOfString[4].toString() + paramArrayOfString[5].toString() + paramArrayOfString[6].toString() + paramArrayOfString[7].toString() + paramArrayOfString[8].toString() + paramArrayOfString[9].toString() + paramArrayOfString[10].toString());
              localObject3 = str3;
            }
          }
          label587:
          localObject2 = localObject3;
        }
      }
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtTouch
 * JD-Core Version:    0.7.1
 */