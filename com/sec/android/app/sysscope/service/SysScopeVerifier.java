package com.sec.android.app.sysscope.service;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SysScopeVerifier
{
  private Context mContext;
  
  public SysScopeVerifier(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private boolean parseScopeApk(String paramString)
  {
    for (;;)
    {
      try
      {
        localMessageDigest = MessageDigest.getInstance("SHA-1");
        ((Certificate[])null);
        arrayOfByte = new byte[8192];
        JarFile localJarFile = new JarFile(paramString, true);
        Enumeration localEnumeration = localJarFile.entries();
        if (!localEnumeration.hasMoreElements()) {
          return true;
        }
        localJarEntry = (JarEntry)localEnumeration.nextElement();
        if (localJarEntry.isDirectory()) {
          continue;
        }
        localBufferedInputStream = new BufferedInputStream(localJarFile.getInputStream(localJarEntry));
        localMessageDigest.reset();
      }
      catch (IOException localIOException)
      {
        MessageDigest localMessageDigest;
        byte[] arrayOfByte;
        JarEntry localJarEntry;
        BufferedInputStream localBufferedInputStream;
        int i;
        Log.w("SysScopeVerifier", "Exception: " + localIOException.toString());
        return false;
        localMessageDigest.update(arrayOfByte, 0, i);
        continue;
      }
      catch (RuntimeException localRuntimeException)
      {
        Log.w("SysScopeVerifier", "Exception: " + localRuntimeException.toString());
        return false;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        Log.w("SysScopeVerifier", "Exception: " + localNoSuchAlgorithmException.toString());
      }
      i = localBufferedInputStream.read(arrayOfByte, 0, arrayOfByte.length);
      if (i != -1) {
        continue;
      }
      localBufferedInputStream.close();
      Base64.encodeToString(localMessageDigest.digest(), 0);
      localMessageDigest.reset();
      if ((!localJarEntry.getName().startsWith("META-INF/")) && (!localJarEntry.getName().equals("AndroidManifest.xml")) && (!localJarEntry.getName().equals("resources.arsc"))) {
        localJarEntry.getName().equals("classes.dex");
      }
    }
    return false;
  }
  
  private boolean verifyWithPmKey()
  {
    return this.mContext.getPackageManager().checkSignatures("com.android.settings", "com.sec.android.app.sysscope") == 0;
  }
  
  final boolean verifySysScopeService()
  {
    String str;
    if (new File("/data/app/com.sec.android.app.sysscope-1.apk").exists()) {
      str = "/data/app/com.sec.android.app.sysscope-1.apk";
    }
    while (!parseScopeApk(str))
    {
      Log.e("SysScopeVerifier", "parse error");
      return false;
      if (new File("/data/app/com.sec.android.app.sysscope-2.apk").exists()) {
        str = "/data/app/com.sec.android.app.sysscope-2.apk";
      } else {
        str = "/system/app/SysScope.apk";
      }
    }
    if (!verifyWithPmKey())
    {
      Log.e("SysScopeVerifier", "verifyWithPmKey error");
      return false;
    }
    return true;
  }
}


/* Location:           bin\dex2jar\jar\FactoryTest.jar
 * Qualified Name:     com.sec.android.app.sysscope.service.SysScopeVerifier
 * JD-Core Version:    0.7.1
 */