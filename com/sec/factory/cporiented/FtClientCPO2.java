package com.sec.factory.cporiented;

import com.sec.factory.support.FtUtil;

public class FtClientCPO2
  extends FtClientCPO
{
  public FtClientCPO2()
  {
    setClassName("FtClientCPO2");
    setBindSvcName("com.sec.phone.SecPhoneService2");
    FtUtil.log_d(this.CLASS_NAME, "FtClientCPO2", "with " + this.BIND_SVC_NAME);
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.factory.cporiented.FtClientCPO2
 * JD-Core Version:    0.7.1
 */