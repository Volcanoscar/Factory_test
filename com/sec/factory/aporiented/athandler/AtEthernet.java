package com.sec.factory.aporiented.athandler;

import android.content.Context;
import com.sec.factory.modules.ModuleCommunication;

public class AtEthernet
  extends AtCommandHandler
{
  public AtEthernet(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "ETHERNET";
    this.CLASS_NAME = "AtEthernet";
    this.NUM_ARGS = 3;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      String str;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        if (checkArgu(paramArrayOfString, new String[] { "2", "0" }))
        {
          if (mModuleCommunication.WriteEthernetMac(paramArrayOfString[2])) {
            str = responseOK(paramArrayOfString[0]);
          } else {
            str = responseNG(paramArrayOfString[0]);
          }
        }
        else if (checkArgu(paramArrayOfString, new String[] { "1", "0", "0" })) {
          str = responseString(paramArrayOfString[0], mModuleCommunication.ReadEthernetMac());
        } else if (checkArgu(paramArrayOfString, new String[] { "0", "0", "0" }))
        {
          if (mModuleCommunication.ConnectionCheckEthernet()) {
            str = responseOK(paramArrayOfString[0]);
          } else {
            str = responseNG(paramArrayOfString[0]);
          }
        }
        else {
          str = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      Object localObject2 = str;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtEthernet
 * JD-Core Version:    0.7.1
 */