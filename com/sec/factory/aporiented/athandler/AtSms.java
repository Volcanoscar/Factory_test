package com.sec.factory.aporiented.athandler;

import android.content.Context;
import android.net.Uri;
import com.sec.factory.modules.ModuleDFT;

public class AtSms
  extends AtCommandHandler
{
  public AtSms(Context paramContext)
  {
    super(paramContext);
    this.CMD_NAME = "SMS";
    this.CLASS_NAME = "AtSms";
    this.NUM_ARGS = 3;
  }
  
  public String handleCommand(String[] paramArrayOfString)
  {
    for (;;)
    {
      String str4;
      try
      {
        if (paramArrayOfString.length != this.NUM_ARGS)
        {
          localObject2 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
          return (String)localObject2;
        }
        String str1 = paramArrayOfString[0].replaceAll("\"", "");
        String str2 = paramArrayOfString[1].replaceAll("\"", "");
        String str3 = paramArrayOfString[2];
        if (str1.equals("INBOX"))
        {
          if (mModuleDFT.makeSMS(Uri.parse("content://sms/inbox"), str2, str3)) {
            str4 = responseOK(paramArrayOfString[0]);
          } else {
            str4 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (str1.equals("DRAFTS"))
        {
          if (mModuleDFT.makeSMS(Uri.parse("content://sms/draft"), str2, str3)) {
            str4 = responseOK(paramArrayOfString[0]);
          } else {
            str4 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (str1.equals("OUTBOX"))
        {
          if (mModuleDFT.makeSMS(Uri.parse("content://sms/outbox"), str2, str3)) {
            str4 = responseOK(paramArrayOfString[0]);
          } else {
            str4 = responseNG(paramArrayOfString[0]);
          }
        }
        else if (str1.equals("SENTBOX"))
        {
          if (mModuleDFT.makeSMS(Uri.parse("content://sms/sent"), str2, str3)) {
            str4 = responseOK(paramArrayOfString[0]);
          } else {
            str4 = responseNG(paramArrayOfString[0]);
          }
        }
        else {
          str4 = "\r\n+CME Error:NA\r\n\r\nOK\r\n";
        }
      }
      finally {}
      Object localObject2 = str4;
    }
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.athandler.AtSms
 * JD-Core Version:    0.7.1
 */