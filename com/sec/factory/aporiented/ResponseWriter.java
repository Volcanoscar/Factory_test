package com.sec.factory.aporiented;

import android.net.LocalSocket;
import com.sec.factory.support.FtUtil;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseWriter
{
  private DataOutputStream out;
  
  public ResponseWriter(LocalSocket paramLocalSocket)
  {
    FtUtil.log_d("ResponseWriter", "ResponseWriter", "Create ResponseWriter");
    if (paramLocalSocket.isConnected()) {
      try
      {
        this.out = new DataOutputStream(new BufferedOutputStream(paramLocalSocket.getOutputStream(), 8192));
        return;
      }
      catch (IOException localIOException)
      {
        FtUtil.log_e(localIOException);
        return;
      }
    }
    FtUtil.log_i("ResponseWriter", "ResponseWriter", "Socket is closed");
  }
  
  public boolean write(String paramString)
  {
    if (this.out != null) {
      try
      {
        this.out.writeBytes(paramString);
        this.out.flush();
        return true;
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          FtUtil.log_e(localIOException);
        }
      }
    }
    FtUtil.log_i("ResponseWriter", "write", "out is null");
    return false;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.aporiented.ResponseWriter
 * JD-Core Version:    0.7.1
 */